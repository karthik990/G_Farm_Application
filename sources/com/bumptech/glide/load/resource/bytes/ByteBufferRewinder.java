package com.bumptech.glide.load.resource.bytes;

import com.bumptech.glide.load.data.DataRewinder;
import java.nio.ByteBuffer;

public class ByteBufferRewinder implements DataRewinder<ByteBuffer> {
    private final ByteBuffer buffer;

    public static class Factory implements com.bumptech.glide.load.data.DataRewinder.Factory<ByteBuffer> {
        public DataRewinder<ByteBuffer> build(ByteBuffer byteBuffer) {
            return new ByteBufferRewinder(byteBuffer);
        }

        public Class<ByteBuffer> getDataClass() {
            return ByteBuffer.class;
        }
    }

    public void cleanup() {
    }

    public ByteBufferRewinder(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
    }

    public ByteBuffer rewindAndGet() {
        this.buffer.position(0);
        return this.buffer;
    }
}
