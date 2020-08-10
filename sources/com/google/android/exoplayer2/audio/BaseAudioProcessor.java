package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor.AudioFormat;
import com.google.android.exoplayer2.audio.AudioProcessor.UnhandledAudioFormatException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class BaseAudioProcessor implements AudioProcessor {
    private ByteBuffer buffer = EMPTY_BUFFER;
    protected AudioFormat inputAudioFormat = AudioFormat.NOT_SET;
    private boolean inputEnded;
    protected AudioFormat outputAudioFormat = AudioFormat.NOT_SET;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private AudioFormat pendingInputAudioFormat = AudioFormat.NOT_SET;
    private AudioFormat pendingOutputAudioFormat = AudioFormat.NOT_SET;

    /* access modifiers changed from: protected */
    public void onFlush() {
    }

    /* access modifiers changed from: protected */
    public void onQueueEndOfStream() {
    }

    /* access modifiers changed from: protected */
    public void onReset() {
    }

    public final AudioFormat configure(AudioFormat audioFormat) throws UnhandledAudioFormatException {
        this.pendingInputAudioFormat = audioFormat;
        this.pendingOutputAudioFormat = onConfigure(audioFormat);
        return isActive() ? this.pendingOutputAudioFormat : AudioFormat.NOT_SET;
    }

    public boolean isActive() {
        return this.pendingOutputAudioFormat != AudioFormat.NOT_SET;
    }

    public final void queueEndOfStream() {
        this.inputEnded = true;
        onQueueEndOfStream();
    }

    public ByteBuffer getOutput() {
        ByteBuffer byteBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return byteBuffer;
    }

    public boolean isEnded() {
        return this.inputEnded && this.outputBuffer == EMPTY_BUFFER;
    }

    public final void flush() {
        this.outputBuffer = EMPTY_BUFFER;
        this.inputEnded = false;
        this.inputAudioFormat = this.pendingInputAudioFormat;
        this.outputAudioFormat = this.pendingOutputAudioFormat;
        onFlush();
    }

    public final void reset() {
        flush();
        this.buffer = EMPTY_BUFFER;
        this.pendingInputAudioFormat = AudioFormat.NOT_SET;
        this.pendingOutputAudioFormat = AudioFormat.NOT_SET;
        this.inputAudioFormat = AudioFormat.NOT_SET;
        this.outputAudioFormat = AudioFormat.NOT_SET;
        onReset();
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer replaceOutputBuffer(int i) {
        if (this.buffer.capacity() < i) {
            this.buffer = ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
        } else {
            this.buffer.clear();
        }
        ByteBuffer byteBuffer = this.buffer;
        this.outputBuffer = byteBuffer;
        return byteBuffer;
    }

    /* access modifiers changed from: protected */
    public final boolean hasPendingOutput() {
        return this.outputBuffer.hasRemaining();
    }

    /* access modifiers changed from: protected */
    public AudioFormat onConfigure(AudioFormat audioFormat) throws UnhandledAudioFormatException {
        return AudioFormat.NOT_SET;
    }
}
