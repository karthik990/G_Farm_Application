package com.google.android.exoplayer2.text;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities.CC;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

public final class TextRenderer extends BaseRenderer implements Callback {
    private static final int MSG_UPDATE_OUTPUT = 0;
    private static final int REPLACEMENT_STATE_NONE = 0;
    private static final int REPLACEMENT_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REPLACEMENT_STATE_WAIT_END_OF_STREAM = 2;
    private SubtitleDecoder decoder;
    private final SubtitleDecoderFactory decoderFactory;
    private int decoderReplacementState;
    private final FormatHolder formatHolder;
    private boolean inputStreamEnded;
    private SubtitleInputBuffer nextInputBuffer;
    private SubtitleOutputBuffer nextSubtitle;
    private int nextSubtitleEventIndex;
    private final TextOutput output;
    private final Handler outputHandler;
    private boolean outputStreamEnded;
    private Format streamFormat;
    private SubtitleOutputBuffer subtitle;

    public boolean isReady() {
        return true;
    }

    public TextRenderer(TextOutput textOutput, Looper looper) {
        this(textOutput, looper, SubtitleDecoderFactory.DEFAULT);
    }

    public TextRenderer(TextOutput textOutput, Looper looper, SubtitleDecoderFactory subtitleDecoderFactory) {
        Handler handler;
        super(3);
        this.output = (TextOutput) Assertions.checkNotNull(textOutput);
        if (looper == null) {
            handler = null;
        } else {
            handler = Util.createHandler(looper, this);
        }
        this.outputHandler = handler;
        this.decoderFactory = subtitleDecoderFactory;
        this.formatHolder = new FormatHolder();
    }

    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return CC.create(supportsFormatDrm(null, format.drmInitData) ? 4 : 2);
        } else if (MimeTypes.isText(format.sampleMimeType)) {
            return CC.create(1);
        } else {
            return CC.create(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formatArr, long j) {
        this.streamFormat = formatArr[0];
        if (this.decoder != null) {
            this.decoderReplacementState = 1;
        } else {
            this.decoder = this.decoderFactory.createDecoder(this.streamFormat);
        }
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long j, boolean z) {
        clearOutput();
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        if (this.decoderReplacementState != 0) {
            replaceDecoder();
            return;
        }
        releaseBuffers();
        this.decoder.flush();
    }

    public void render(long j, long j2) throws ExoPlaybackException {
        boolean z;
        if (!this.outputStreamEnded) {
            if (this.nextSubtitle == null) {
                this.decoder.setPositionUs(j);
                try {
                    this.nextSubtitle = (SubtitleOutputBuffer) this.decoder.dequeueOutputBuffer();
                } catch (SubtitleDecoderException e) {
                    throw createRendererException(e, this.streamFormat);
                }
            }
            if (getState() == 2) {
                if (this.subtitle != null) {
                    long nextEventTime = getNextEventTime();
                    z = false;
                    while (nextEventTime <= j) {
                        this.nextSubtitleEventIndex++;
                        nextEventTime = getNextEventTime();
                        z = true;
                    }
                } else {
                    z = false;
                }
                SubtitleOutputBuffer subtitleOutputBuffer = this.nextSubtitle;
                if (subtitleOutputBuffer != null) {
                    if (subtitleOutputBuffer.isEndOfStream()) {
                        if (!z && getNextEventTime() == Long.MAX_VALUE) {
                            if (this.decoderReplacementState == 2) {
                                replaceDecoder();
                            } else {
                                releaseBuffers();
                                this.outputStreamEnded = true;
                            }
                        }
                    } else if (this.nextSubtitle.timeUs <= j) {
                        SubtitleOutputBuffer subtitleOutputBuffer2 = this.subtitle;
                        if (subtitleOutputBuffer2 != null) {
                            subtitleOutputBuffer2.release();
                        }
                        this.subtitle = this.nextSubtitle;
                        this.nextSubtitle = null;
                        this.nextSubtitleEventIndex = this.subtitle.getNextEventTimeIndex(j);
                        z = true;
                    }
                }
                if (z) {
                    updateOutput(this.subtitle.getCues(j));
                }
                if (this.decoderReplacementState != 2) {
                    while (!this.inputStreamEnded) {
                        try {
                            if (this.nextInputBuffer == null) {
                                this.nextInputBuffer = (SubtitleInputBuffer) this.decoder.dequeueInputBuffer();
                                if (this.nextInputBuffer == null) {
                                    return;
                                }
                            }
                            if (this.decoderReplacementState == 1) {
                                this.nextInputBuffer.setFlags(4);
                                this.decoder.queueInputBuffer(this.nextInputBuffer);
                                this.nextInputBuffer = null;
                                this.decoderReplacementState = 2;
                                return;
                            }
                            int readSource = readSource(this.formatHolder, this.nextInputBuffer, false);
                            if (readSource == -4) {
                                if (this.nextInputBuffer.isEndOfStream()) {
                                    this.inputStreamEnded = true;
                                } else {
                                    this.nextInputBuffer.subsampleOffsetUs = this.formatHolder.format.subsampleOffsetUs;
                                    this.nextInputBuffer.flip();
                                }
                                this.decoder.queueInputBuffer(this.nextInputBuffer);
                                this.nextInputBuffer = null;
                            } else if (readSource == -3) {
                                break;
                            }
                        } catch (SubtitleDecoderException e2) {
                            throw createRendererException(e2, this.streamFormat);
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        this.streamFormat = null;
        clearOutput();
        releaseDecoder();
    }

    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    private void releaseBuffers() {
        this.nextInputBuffer = null;
        this.nextSubtitleEventIndex = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.subtitle;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.subtitle = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.nextSubtitle;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.nextSubtitle = null;
        }
    }

    private void releaseDecoder() {
        releaseBuffers();
        this.decoder.release();
        this.decoder = null;
        this.decoderReplacementState = 0;
    }

    private void replaceDecoder() {
        releaseDecoder();
        this.decoder = this.decoderFactory.createDecoder(this.streamFormat);
    }

    private long getNextEventTime() {
        int i = this.nextSubtitleEventIndex;
        if (i == -1 || i >= this.subtitle.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
    }

    private void updateOutput(List<Cue> list) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, list).sendToTarget();
        } else {
            invokeUpdateOutputInternal(list);
        }
    }

    private void clearOutput() {
        updateOutput(Collections.emptyList());
    }

    public boolean handleMessage(Message message) {
        if (message.what == 0) {
            invokeUpdateOutputInternal((List) message.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    private void invokeUpdateOutputInternal(List<Cue> list) {
        this.output.onCues(list);
    }
}
