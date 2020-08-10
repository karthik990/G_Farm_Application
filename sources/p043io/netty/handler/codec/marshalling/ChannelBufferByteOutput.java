package p043io.netty.handler.codec.marshalling;

import java.io.IOException;
import org.jboss.marshalling.ByteOutput;
import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.marshalling.ChannelBufferByteOutput */
class ChannelBufferByteOutput implements ByteOutput {
    private final ByteBuf buffer;

    public void close() throws IOException {
    }

    public void flush() throws IOException {
    }

    ChannelBufferByteOutput(ByteBuf byteBuf) {
        this.buffer = byteBuf;
    }

    public void write(int i) throws IOException {
        this.buffer.writeByte(i);
    }

    public void write(byte[] bArr) throws IOException {
        this.buffer.writeBytes(bArr);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.buffer.writeBytes(bArr, i, i2);
    }

    /* access modifiers changed from: 0000 */
    public ByteBuf getBuffer() {
        return this.buffer;
    }
}
