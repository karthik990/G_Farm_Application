package p043io.netty.handler.codec.serialization;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufOutputStream;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToByteEncoder;

/* renamed from: io.netty.handler.codec.serialization.CompatibleObjectEncoder */
public class CompatibleObjectEncoder extends MessageToByteEncoder<Serializable> {
    private final int resetInterval;
    private int writtenObjects;

    public CompatibleObjectEncoder() {
        this(16);
    }

    public CompatibleObjectEncoder(int i) {
        if (i >= 0) {
            this.resetInterval = i;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("resetInterval: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public ObjectOutputStream newObjectOutputStream(OutputStream outputStream) throws Exception {
        return new ObjectOutputStream(outputStream);
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
        ObjectOutputStream newObjectOutputStream = newObjectOutputStream(new ByteBufOutputStream(byteBuf));
        try {
            if (this.resetInterval != 0) {
                this.writtenObjects++;
                if (this.writtenObjects % this.resetInterval == 0) {
                    newObjectOutputStream.reset();
                }
            }
            newObjectOutputStream.writeObject(serializable);
            newObjectOutputStream.flush();
        } finally {
            newObjectOutputStream.close();
        }
    }
}
