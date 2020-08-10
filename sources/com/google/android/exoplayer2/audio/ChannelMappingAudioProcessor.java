package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor.AudioFormat;
import com.google.android.exoplayer2.audio.AudioProcessor.UnhandledAudioFormatException;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

final class ChannelMappingAudioProcessor extends BaseAudioProcessor {
    private int[] outputChannels;
    private int[] pendingOutputChannels;

    ChannelMappingAudioProcessor() {
    }

    public void setChannelMap(int[] iArr) {
        this.pendingOutputChannels = iArr;
    }

    public AudioFormat onConfigure(AudioFormat audioFormat) throws UnhandledAudioFormatException {
        int[] iArr = this.pendingOutputChannels;
        if (iArr == null) {
            return AudioFormat.NOT_SET;
        }
        if (audioFormat.encoding == 2) {
            boolean z = audioFormat.channelCount != iArr.length;
            int i = 0;
            while (i < iArr.length) {
                int i2 = iArr[i];
                if (i2 < audioFormat.channelCount) {
                    z |= i2 != i;
                    i++;
                } else {
                    throw new UnhandledAudioFormatException(audioFormat);
                }
            }
            return z ? new AudioFormat(audioFormat.sampleRate, iArr.length, 2) : AudioFormat.NOT_SET;
        }
        throw new UnhandledAudioFormatException(audioFormat);
    }

    public void queueInput(ByteBuffer byteBuffer) {
        int[] iArr = (int[]) Assertions.checkNotNull(this.outputChannels);
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        ByteBuffer replaceOutputBuffer = replaceOutputBuffer(((limit - position) / this.inputAudioFormat.bytesPerFrame) * this.outputAudioFormat.bytesPerFrame);
        while (position < limit) {
            for (int i : iArr) {
                replaceOutputBuffer.putShort(byteBuffer.getShort((i * 2) + position));
            }
            position += this.inputAudioFormat.bytesPerFrame;
        }
        byteBuffer.position(limit);
        replaceOutputBuffer.flip();
    }

    /* access modifiers changed from: protected */
    public void onFlush() {
        this.outputChannels = this.pendingOutputChannels;
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        this.outputChannels = null;
        this.pendingOutputChannels = null;
    }
}
