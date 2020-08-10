package p043io.netty.channel.socket;

import java.net.InetSocketAddress;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.channel.DefaultAddressedEnvelope;

/* renamed from: io.netty.channel.socket.DatagramPacket */
public final class DatagramPacket extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress> implements ByteBufHolder {
    public /* bridge */ /* synthetic */ ByteBuf content() {
        return (ByteBuf) super.content();
    }

    public DatagramPacket(ByteBuf byteBuf, InetSocketAddress inetSocketAddress) {
        super(byteBuf, inetSocketAddress);
    }

    public DatagramPacket(ByteBuf byteBuf, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        super(byteBuf, inetSocketAddress, inetSocketAddress2);
    }

    public DatagramPacket copy() {
        return replace(((ByteBuf) content()).copy());
    }

    public DatagramPacket duplicate() {
        return replace(((ByteBuf) content()).duplicate());
    }

    public DatagramPacket retainedDuplicate() {
        return replace(((ByteBuf) content()).retainedDuplicate());
    }

    public DatagramPacket replace(ByteBuf byteBuf) {
        return new DatagramPacket(byteBuf, (InetSocketAddress) recipient(), (InetSocketAddress) sender());
    }

    public DatagramPacket retain() {
        super.retain();
        return this;
    }

    public DatagramPacket retain(int i) {
        super.retain(i);
        return this;
    }

    public DatagramPacket touch() {
        super.touch();
        return this;
    }

    public DatagramPacket touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
