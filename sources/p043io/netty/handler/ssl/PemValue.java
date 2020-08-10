package p043io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.util.AbstractReferenceCounted;
import p043io.netty.util.IllegalReferenceCountException;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.PemValue */
class PemValue extends AbstractReferenceCounted implements PemEncoded {
    private final ByteBuf content;
    private final boolean sensitive;

    public PemValue(ByteBuf byteBuf, boolean z) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, Param.CONTENT);
        this.sensitive = z;
    }

    public boolean isSensitive() {
        return this.sensitive;
    }

    public ByteBuf content() {
        int refCnt = refCnt();
        if (refCnt > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(refCnt);
    }

    public PemValue copy() {
        return replace(this.content.copy());
    }

    public PemValue duplicate() {
        return replace(this.content.duplicate());
    }

    public PemValue retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    public PemValue replace(ByteBuf byteBuf) {
        return new PemValue(byteBuf, this.sensitive);
    }

    public PemValue touch() {
        return (PemValue) super.touch();
    }

    public PemValue touch(Object obj) {
        this.content.touch(obj);
        return this;
    }

    public PemValue retain() {
        return (PemValue) super.retain();
    }

    public PemValue retain(int i) {
        return (PemValue) super.retain(i);
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        if (this.sensitive) {
            SslUtils.zeroout(this.content);
        }
        this.content.release();
    }
}
