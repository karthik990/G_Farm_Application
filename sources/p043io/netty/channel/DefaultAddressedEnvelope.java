package p043io.netty.channel;

import java.net.SocketAddress;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.ReferenceCounted;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.DefaultAddressedEnvelope */
public class DefaultAddressedEnvelope<M, A extends SocketAddress> implements AddressedEnvelope<M, A> {
    private final M message;
    private final A recipient;
    private final A sender;

    public DefaultAddressedEnvelope(M m, A a, A a2) {
        if (m == null) {
            throw new NullPointerException("message");
        } else if (a == null && a2 == null) {
            throw new NullPointerException("recipient and sender");
        } else {
            this.message = m;
            this.sender = a2;
            this.recipient = a;
        }
    }

    public DefaultAddressedEnvelope(M m, A a) {
        this(m, a, null);
    }

    public M content() {
        return this.message;
    }

    public A sender() {
        return this.sender;
    }

    public A recipient() {
        return this.recipient;
    }

    public int refCnt() {
        M m = this.message;
        if (m instanceof ReferenceCounted) {
            return ((ReferenceCounted) m).refCnt();
        }
        return 1;
    }

    public AddressedEnvelope<M, A> retain() {
        ReferenceCountUtil.retain(this.message);
        return this;
    }

    public AddressedEnvelope<M, A> retain(int i) {
        ReferenceCountUtil.retain(this.message, i);
        return this;
    }

    public boolean release() {
        return ReferenceCountUtil.release(this.message);
    }

    public boolean release(int i) {
        return ReferenceCountUtil.release(this.message, i);
    }

    public AddressedEnvelope<M, A> touch() {
        ReferenceCountUtil.touch(this.message);
        return this;
    }

    public AddressedEnvelope<M, A> touch(Object obj) {
        ReferenceCountUtil.touch(this.message, obj);
        return this;
    }

    public String toString() {
        String str = ", ";
        if (this.sender != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtil.simpleClassName((Object) this));
            sb.append('(');
            sb.append(this.sender);
            sb.append(" => ");
            sb.append(this.recipient);
            sb.append(str);
            sb.append(this.message);
            sb.append(')');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(StringUtil.simpleClassName((Object) this));
        sb2.append("(=> ");
        sb2.append(this.recipient);
        sb2.append(str);
        sb2.append(this.message);
        sb2.append(')');
        return sb2.toString();
    }
}
