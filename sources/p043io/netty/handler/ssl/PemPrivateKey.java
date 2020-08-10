package p043io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.security.PrivateKey;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.util.AbstractReferenceCounted;
import p043io.netty.util.CharsetUtil;
import p043io.netty.util.IllegalReferenceCountException;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.PemPrivateKey */
public final class PemPrivateKey extends AbstractReferenceCounted implements PrivateKey, PemEncoded {
    private static final byte[] BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final String PKCS8_FORMAT = "PKCS#8";
    private final ByteBuf content;

    public String getFormat() {
        return PKCS8_FORMAT;
    }

    public boolean isSensitive() {
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0048, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004c, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static p043io.netty.handler.ssl.PemEncoded toPEM(p043io.netty.buffer.ByteBufAllocator r3, boolean r4, java.security.PrivateKey r5) {
        /*
            boolean r0 = r5 instanceof p043io.netty.handler.ssl.PemEncoded
            if (r0 == 0) goto L_0x000b
            io.netty.handler.ssl.PemEncoded r5 = (p043io.netty.handler.ssl.PemEncoded) r5
            io.netty.handler.ssl.PemEncoded r3 = r5.retain()
            return r3
        L_0x000b:
            byte[] r5 = r5.getEncoded()
            io.netty.buffer.ByteBuf r5 = p043io.netty.buffer.Unpooled.wrappedBuffer(r5)
            io.netty.buffer.ByteBuf r0 = p043io.netty.handler.ssl.SslUtils.toBase64(r3, r5)     // Catch:{ all -> 0x0052 }
            byte[] r1 = BEGIN_PRIVATE_KEY     // Catch:{ all -> 0x004d }
            int r1 = r1.length     // Catch:{ all -> 0x004d }
            int r2 = r0.readableBytes()     // Catch:{ all -> 0x004d }
            int r1 = r1 + r2
            byte[] r2 = END_PRIVATE_KEY     // Catch:{ all -> 0x004d }
            int r2 = r2.length     // Catch:{ all -> 0x004d }
            int r1 = r1 + r2
            if (r4 == 0) goto L_0x002a
            io.netty.buffer.ByteBuf r3 = r3.directBuffer(r1)     // Catch:{ all -> 0x004d }
            goto L_0x002e
        L_0x002a:
            io.netty.buffer.ByteBuf r3 = r3.buffer(r1)     // Catch:{ all -> 0x004d }
        L_0x002e:
            byte[] r4 = BEGIN_PRIVATE_KEY     // Catch:{ all -> 0x0048 }
            r3.writeBytes(r4)     // Catch:{ all -> 0x0048 }
            r3.writeBytes(r0)     // Catch:{ all -> 0x0048 }
            byte[] r4 = END_PRIVATE_KEY     // Catch:{ all -> 0x0048 }
            r3.writeBytes(r4)     // Catch:{ all -> 0x0048 }
            io.netty.handler.ssl.PemValue r4 = new io.netty.handler.ssl.PemValue     // Catch:{ all -> 0x0048 }
            r1 = 1
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0048 }
            p043io.netty.handler.ssl.SslUtils.zerooutAndRelease(r0)     // Catch:{ all -> 0x0052 }
            p043io.netty.handler.ssl.SslUtils.zerooutAndRelease(r5)
            return r4
        L_0x0048:
            r4 = move-exception
            p043io.netty.handler.ssl.SslUtils.zerooutAndRelease(r3)     // Catch:{ all -> 0x004d }
            throw r4     // Catch:{ all -> 0x004d }
        L_0x004d:
            r3 = move-exception
            p043io.netty.handler.ssl.SslUtils.zerooutAndRelease(r0)     // Catch:{ all -> 0x0052 }
            throw r3     // Catch:{ all -> 0x0052 }
        L_0x0052:
            r3 = move-exception
            p043io.netty.handler.ssl.SslUtils.zerooutAndRelease(r5)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.PemPrivateKey.toPEM(io.netty.buffer.ByteBufAllocator, boolean, java.security.PrivateKey):io.netty.handler.ssl.PemEncoded");
    }

    public static PemPrivateKey valueOf(byte[] bArr) {
        return valueOf(Unpooled.wrappedBuffer(bArr));
    }

    public static PemPrivateKey valueOf(ByteBuf byteBuf) {
        return new PemPrivateKey(byteBuf);
    }

    private PemPrivateKey(ByteBuf byteBuf) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, Param.CONTENT);
    }

    public ByteBuf content() {
        int refCnt = refCnt();
        if (refCnt > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(refCnt);
    }

    public PemPrivateKey copy() {
        return replace(this.content.copy());
    }

    public PemPrivateKey duplicate() {
        return replace(this.content.duplicate());
    }

    public PemPrivateKey retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    public PemPrivateKey replace(ByteBuf byteBuf) {
        return new PemPrivateKey(byteBuf);
    }

    public PemPrivateKey touch() {
        this.content.touch();
        return this;
    }

    public PemPrivateKey touch(Object obj) {
        this.content.touch(obj);
        return this;
    }

    public PemPrivateKey retain() {
        return (PemPrivateKey) super.retain();
    }

    public PemPrivateKey retain(int i) {
        return (PemPrivateKey) super.retain(i);
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        SslUtils.zerooutAndRelease(this.content);
    }

    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    public String getAlgorithm() {
        throw new UnsupportedOperationException();
    }

    public void destroy() {
        release(refCnt());
    }

    public boolean isDestroyed() {
        return refCnt() == 0;
    }
}
