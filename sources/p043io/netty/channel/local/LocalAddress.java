package p043io.netty.channel.local;

import java.net.SocketAddress;
import p043io.netty.channel.Channel;

/* renamed from: io.netty.channel.local.LocalAddress */
public final class LocalAddress extends SocketAddress implements Comparable<LocalAddress> {
    public static final LocalAddress ANY = new LocalAddress("ANY");
    private static final long serialVersionUID = 4644331421130916435L;

    /* renamed from: id */
    private final String f3703id;
    private final String strVal;

    LocalAddress(Channel channel) {
        StringBuilder sb = new StringBuilder(16);
        sb.append("local:E");
        sb.append(Long.toHexString((((long) channel.hashCode()) & 4294967295L) | 4294967296L));
        sb.setCharAt(7, ':');
        this.f3703id = sb.substring(6);
        this.strVal = sb.toString();
    }

    public LocalAddress(String str) {
        if (str != null) {
            String lowerCase = str.trim().toLowerCase();
            if (!lowerCase.isEmpty()) {
                this.f3703id = lowerCase;
                StringBuilder sb = new StringBuilder();
                sb.append("local:");
                sb.append(lowerCase);
                this.strVal = sb.toString();
                return;
            }
            throw new IllegalArgumentException("empty id");
        }
        throw new NullPointerException(TtmlNode.ATTR_ID);
    }

    /* renamed from: id */
    public String mo66686id() {
        return this.f3703id;
    }

    public int hashCode() {
        return this.f3703id.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LocalAddress)) {
            return false;
        }
        return this.f3703id.equals(((LocalAddress) obj).f3703id);
    }

    public int compareTo(LocalAddress localAddress) {
        return this.f3703id.compareTo(localAddress.f3703id);
    }

    public String toString() {
        return this.strVal;
    }
}
