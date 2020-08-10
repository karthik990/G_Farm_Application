package p043io.netty.handler.codec.compression;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.util.ByteProcessor;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.codec.compression.ByteBufChecksum */
abstract class ByteBufChecksum implements Checksum {
    private static final Method ADLER32_UPDATE_METHOD = updateByteBuffer(new Adler32());
    private static final Method CRC32_UPDATE_METHOD = updateByteBuffer(new CRC32());
    private final ByteProcessor updateProcessor = new ByteProcessor() {
        public boolean process(byte b) throws Exception {
            ByteBufChecksum.this.update(b);
            return true;
        }
    };

    /* renamed from: io.netty.handler.codec.compression.ByteBufChecksum$ReflectiveByteBufChecksum */
    private static final class ReflectiveByteBufChecksum extends SlowByteBufChecksum {
        private final Method method;

        ReflectiveByteBufChecksum(Checksum checksum, Method method2) {
            super(checksum);
            this.method = method2;
        }

        public void update(ByteBuf byteBuf, int i, int i2) {
            if (byteBuf.hasArray()) {
                update(byteBuf.array(), byteBuf.arrayOffset() + i, i2);
                return;
            }
            try {
                this.method.invoke(this.checksum, new Object[]{CompressionUtil.safeNioBuffer(byteBuf)});
            } catch (Throwable unused) {
                throw new Error();
            }
        }
    }

    /* renamed from: io.netty.handler.codec.compression.ByteBufChecksum$SlowByteBufChecksum */
    private static class SlowByteBufChecksum extends ByteBufChecksum {
        protected final Checksum checksum;

        SlowByteBufChecksum(Checksum checksum2) {
            this.checksum = checksum2;
        }

        public void update(int i) {
            this.checksum.update(i);
        }

        public void update(byte[] bArr, int i, int i2) {
            this.checksum.update(bArr, i, i2);
        }

        public long getValue() {
            return this.checksum.getValue();
        }

        public void reset() {
            this.checksum.reset();
        }
    }

    ByteBufChecksum() {
    }

    private static Method updateByteBuffer(Checksum checksum) {
        if (PlatformDependent.javaVersion() >= 8) {
            try {
                Method declaredMethod = checksum.getClass().getDeclaredMethod("update", new Class[]{ByteBuffer.class});
                declaredMethod.invoke(declaredMethod, new Object[]{ByteBuffer.allocate(1)});
                return declaredMethod;
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    static ByteBufChecksum wrapChecksum(Checksum checksum) {
        ObjectUtil.checkNotNull(checksum, "checksum");
        if (checksum instanceof Adler32) {
            Method method = ADLER32_UPDATE_METHOD;
            if (method != null) {
                return new ReflectiveByteBufChecksum(checksum, method);
            }
        }
        if (checksum instanceof CRC32) {
            Method method2 = CRC32_UPDATE_METHOD;
            if (method2 != null) {
                return new ReflectiveByteBufChecksum(checksum, method2);
            }
        }
        return new SlowByteBufChecksum(checksum);
    }

    public void update(ByteBuf byteBuf, int i, int i2) {
        if (byteBuf.hasArray()) {
            update(byteBuf.array(), byteBuf.arrayOffset() + i, i2);
        } else {
            byteBuf.forEachByte(i, i2, this.updateProcessor);
        }
    }
}
