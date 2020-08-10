package p043io.netty.channel;

import java.net.SocketAddress;
import p043io.netty.util.ReferenceCounted;

/* renamed from: io.netty.channel.AddressedEnvelope */
public interface AddressedEnvelope<M, A extends SocketAddress> extends ReferenceCounted {
    M content();

    A recipient();

    AddressedEnvelope<M, A> retain();

    AddressedEnvelope<M, A> retain(int i);

    A sender();

    AddressedEnvelope<M, A> touch();

    AddressedEnvelope<M, A> touch(Object obj);
}
