package p043io.netty.buffer;

import p043io.netty.util.IllegalReferenceCountException;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.buffer.DefaultByteBufHolder */
public class DefaultByteBufHolder implements ByteBufHolder {
    private final ByteBuf data;

    public DefaultByteBufHolder(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.data = byteBuf;
            return;
        }
        throw new NullPointerException("data");
    }

    public ByteBuf content() {
        if (this.data.refCnt() > 0) {
            return this.data;
        }
        throw new IllegalReferenceCountException(this.data.refCnt());
    }

    public ByteBufHolder copy() {
        return replace(this.data.copy());
    }

    public ByteBufHolder duplicate() {
        return replace(this.data.duplicate());
    }

    public ByteBufHolder retainedDuplicate() {
        return replace(this.data.retainedDuplicate());
    }

    public ByteBufHolder replace(ByteBuf byteBuf) {
        return new DefaultByteBufHolder(byteBuf);
    }

    public int refCnt() {
        return this.data.refCnt();
    }

    public ByteBufHolder retain() {
        this.data.retain();
        return this;
    }

    public ByteBufHolder retain(int i) {
        this.data.retain(i);
        return this;
    }

    public ByteBufHolder touch() {
        this.data.touch();
        return this;
    }

    public ByteBufHolder touch(Object obj) {
        this.data.touch(obj);
        return this;
    }

    public boolean release() {
        return this.data.release();
    }

    public boolean release(int i) {
        return this.data.release(i);
    }

    /* access modifiers changed from: protected */
    public final String contentToString() {
        return this.data.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('(');
        sb.append(contentToString());
        sb.append(')');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ByteBufHolder) {
            return this.data.equals(((ByteBufHolder) obj).content());
        }
        return false;
    }

    public int hashCode() {
        return this.data.hashCode();
    }
}
