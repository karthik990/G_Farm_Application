package com.google.android.exoplayer2.audio;

import android.support.p009v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.audio.AudioProcessor.AudioFormat;
import com.google.android.exoplayer2.audio.AudioProcessor.UnhandledAudioFormatException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public final class SonicAudioProcessor implements AudioProcessor {
    private static final float CLOSE_THRESHOLD = 0.01f;
    public static final float MAXIMUM_PITCH = 8.0f;
    public static final float MAXIMUM_SPEED = 8.0f;
    public static final float MINIMUM_PITCH = 0.1f;
    public static final float MINIMUM_SPEED = 0.1f;
    private static final int MIN_BYTES_FOR_SPEEDUP_CALCULATION = 1024;
    public static final int SAMPLE_RATE_NO_CHANGE = -1;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private AudioFormat inputAudioFormat = AudioFormat.NOT_SET;
    private long inputBytes;
    private boolean inputEnded;
    private AudioFormat outputAudioFormat = AudioFormat.NOT_SET;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private long outputBytes;
    private AudioFormat pendingInputAudioFormat = AudioFormat.NOT_SET;
    private AudioFormat pendingOutputAudioFormat = AudioFormat.NOT_SET;
    private int pendingOutputSampleRate = -1;
    private boolean pendingSonicRecreation;
    private float pitch = 1.0f;
    private ShortBuffer shortBuffer = this.buffer.asShortBuffer();
    private Sonic sonic;
    private float speed = 1.0f;

    public float setSpeed(float f) {
        float constrainValue = Util.constrainValue(f, 0.1f, 8.0f);
        if (this.speed != constrainValue) {
            this.speed = constrainValue;
            this.pendingSonicRecreation = true;
        }
        return constrainValue;
    }

    public float setPitch(float f) {
        float constrainValue = Util.constrainValue(f, 0.1f, 8.0f);
        if (this.pitch != constrainValue) {
            this.pitch = constrainValue;
            this.pendingSonicRecreation = true;
        }
        return constrainValue;
    }

    public void setOutputSampleRateHz(int i) {
        this.pendingOutputSampleRate = i;
    }

    public long scaleDurationForSpeedup(long j) {
        long j2;
        if (this.outputBytes >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            if (this.outputAudioFormat.sampleRate == this.inputAudioFormat.sampleRate) {
                j2 = Util.scaleLargeTimestamp(j, this.inputBytes, this.outputBytes);
            } else {
                j2 = Util.scaleLargeTimestamp(j, this.inputBytes * ((long) this.outputAudioFormat.sampleRate), this.outputBytes * ((long) this.inputAudioFormat.sampleRate));
            }
            return j2;
        }
        double d = (double) this.speed;
        double d2 = (double) j;
        Double.isNaN(d);
        Double.isNaN(d2);
        return (long) (d * d2);
    }

    public AudioFormat configure(AudioFormat audioFormat) throws UnhandledAudioFormatException {
        if (audioFormat.encoding == 2) {
            int i = this.pendingOutputSampleRate;
            if (i == -1) {
                i = audioFormat.sampleRate;
            }
            this.pendingInputAudioFormat = audioFormat;
            this.pendingOutputAudioFormat = new AudioFormat(i, audioFormat.channelCount, 2);
            this.pendingSonicRecreation = true;
            return this.pendingOutputAudioFormat;
        }
        throw new UnhandledAudioFormatException(audioFormat);
    }

    public boolean isActive() {
        return this.pendingOutputAudioFormat.sampleRate != -1 && (Math.abs(this.speed - 1.0f) >= CLOSE_THRESHOLD || Math.abs(this.pitch - 1.0f) >= CLOSE_THRESHOLD || this.pendingOutputAudioFormat.sampleRate != this.pendingInputAudioFormat.sampleRate);
    }

    public void queueInput(ByteBuffer byteBuffer) {
        Sonic sonic2 = (Sonic) Assertions.checkNotNull(this.sonic);
        if (byteBuffer.hasRemaining()) {
            ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
            int remaining = byteBuffer.remaining();
            this.inputBytes += (long) remaining;
            sonic2.queueInput(asShortBuffer);
            byteBuffer.position(byteBuffer.position() + remaining);
        }
        int outputSize = sonic2.getOutputSize();
        if (outputSize > 0) {
            if (this.buffer.capacity() < outputSize) {
                this.buffer = ByteBuffer.allocateDirect(outputSize).order(ByteOrder.nativeOrder());
                this.shortBuffer = this.buffer.asShortBuffer();
            } else {
                this.buffer.clear();
                this.shortBuffer.clear();
            }
            sonic2.getOutput(this.shortBuffer);
            this.outputBytes += (long) outputSize;
            this.buffer.limit(outputSize);
            this.outputBuffer = this.buffer;
        }
    }

    public void queueEndOfStream() {
        Sonic sonic2 = this.sonic;
        if (sonic2 != null) {
            sonic2.queueEndOfStream();
        }
        this.inputEnded = true;
    }

    public ByteBuffer getOutput() {
        ByteBuffer byteBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return byteBuffer;
    }

    public boolean isEnded() {
        if (this.inputEnded) {
            Sonic sonic2 = this.sonic;
            if (sonic2 == null || sonic2.getOutputSize() == 0) {
                return true;
            }
        }
        return false;
    }

    public void flush() {
        if (isActive()) {
            this.inputAudioFormat = this.pendingInputAudioFormat;
            this.outputAudioFormat = this.pendingOutputAudioFormat;
            if (this.pendingSonicRecreation) {
                Sonic sonic2 = new Sonic(this.inputAudioFormat.sampleRate, this.inputAudioFormat.channelCount, this.speed, this.pitch, this.outputAudioFormat.sampleRate);
                this.sonic = sonic2;
            } else {
                Sonic sonic3 = this.sonic;
                if (sonic3 != null) {
                    sonic3.flush();
                }
            }
        }
        this.outputBuffer = EMPTY_BUFFER;
        this.inputBytes = 0;
        this.outputBytes = 0;
        this.inputEnded = false;
    }

    public void reset() {
        this.speed = 1.0f;
        this.pitch = 1.0f;
        this.pendingInputAudioFormat = AudioFormat.NOT_SET;
        this.pendingOutputAudioFormat = AudioFormat.NOT_SET;
        this.inputAudioFormat = AudioFormat.NOT_SET;
        this.outputAudioFormat = AudioFormat.NOT_SET;
        this.buffer = EMPTY_BUFFER;
        this.shortBuffer = this.buffer.asShortBuffer();
        this.outputBuffer = EMPTY_BUFFER;
        this.pendingOutputSampleRate = -1;
        this.pendingSonicRecreation = false;
        this.sonic = null;
        this.inputBytes = 0;
        this.outputBytes = 0;
        this.inputEnded = false;
    }
}
