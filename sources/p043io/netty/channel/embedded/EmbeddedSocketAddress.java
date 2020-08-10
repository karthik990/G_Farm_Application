package p043io.netty.channel.embedded;

import java.net.SocketAddress;

/* renamed from: io.netty.channel.embedded.EmbeddedSocketAddress */
final class EmbeddedSocketAddress extends SocketAddress {
    private static final long serialVersionUID = 1400788804624980619L;

    public String toString() {
        return "embedded";
    }

    EmbeddedSocketAddress() {
    }
}
