package p043io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.Unpooled;
import p043io.netty.util.CharsetUtil;
import p043io.netty.util.IllegalReferenceCountException;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.PemX509Certificate */
public final class PemX509Certificate extends X509Certificate implements PemEncoded {
    private static final byte[] BEGIN_CERT = "-----BEGIN CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_CERT = "\n-----END CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private final ByteBuf content;

    public boolean isSensitive() {
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static p043io.netty.handler.ssl.PemEncoded toPEM(p043io.netty.buffer.ByteBufAllocator r6, boolean r7, java.security.cert.X509Certificate... r8) throws java.security.cert.CertificateEncodingException {
        /*
            if (r8 == 0) goto L_0x0060
            int r0 = r8.length
            if (r0 == 0) goto L_0x0060
            int r0 = r8.length
            r1 = 1
            r2 = 0
            if (r0 != r1) goto L_0x0017
            r0 = r8[r2]
            boolean r1 = r0 instanceof p043io.netty.handler.ssl.PemEncoded
            if (r1 == 0) goto L_0x0017
            io.netty.handler.ssl.PemEncoded r0 = (p043io.netty.handler.ssl.PemEncoded) r0
            io.netty.handler.ssl.PemEncoded r6 = r0.retain()
            return r6
        L_0x0017:
            r0 = 0
            int r1 = r8.length     // Catch:{ all -> 0x0058 }
            r3 = r0
            r0 = 0
        L_0x001b:
            if (r0 >= r1) goto L_0x0050
            r4 = r8[r0]     // Catch:{ all -> 0x0056 }
            if (r4 == 0) goto L_0x0035
            boolean r5 = r4 instanceof p043io.netty.handler.ssl.PemEncoded     // Catch:{ all -> 0x0056 }
            if (r5 == 0) goto L_0x002d
            io.netty.handler.ssl.PemEncoded r4 = (p043io.netty.handler.ssl.PemEncoded) r4     // Catch:{ all -> 0x0056 }
            int r5 = r8.length     // Catch:{ all -> 0x0056 }
            io.netty.buffer.ByteBuf r3 = append(r6, r7, r4, r5, r3)     // Catch:{ all -> 0x0056 }
            goto L_0x0032
        L_0x002d:
            int r5 = r8.length     // Catch:{ all -> 0x0056 }
            io.netty.buffer.ByteBuf r3 = append(r6, r7, r4, r5, r3)     // Catch:{ all -> 0x0056 }
        L_0x0032:
            int r0 = r0 + 1
            goto L_0x001b
        L_0x0035:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0056 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0056 }
            r7.<init>()     // Catch:{ all -> 0x0056 }
            java.lang.String r0 = "Null element in chain: "
            r7.append(r0)     // Catch:{ all -> 0x0056 }
            java.lang.String r8 = java.util.Arrays.toString(r8)     // Catch:{ all -> 0x0056 }
            r7.append(r8)     // Catch:{ all -> 0x0056 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0056 }
            r6.<init>(r7)     // Catch:{ all -> 0x0056 }
            throw r6     // Catch:{ all -> 0x0056 }
        L_0x0050:
            io.netty.handler.ssl.PemValue r6 = new io.netty.handler.ssl.PemValue     // Catch:{ all -> 0x0056 }
            r6.<init>(r3, r2)     // Catch:{ all -> 0x0056 }
            return r6
        L_0x0056:
            r6 = move-exception
            goto L_0x005a
        L_0x0058:
            r6 = move-exception
            r3 = r0
        L_0x005a:
            if (r3 == 0) goto L_0x005f
            r3.release()
        L_0x005f:
            throw r6
        L_0x0060:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "X.509 certificate chain can't be null or empty"
            r6.<init>(r7)
            goto L_0x0069
        L_0x0068:
            throw r6
        L_0x0069:
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.PemX509Certificate.toPEM(io.netty.buffer.ByteBufAllocator, boolean, java.security.cert.X509Certificate[]):io.netty.handler.ssl.PemEncoded");
    }

    private static ByteBuf append(ByteBufAllocator byteBufAllocator, boolean z, PemEncoded pemEncoded, int i, ByteBuf byteBuf) {
        ByteBuf content2 = pemEncoded.content();
        if (byteBuf == null) {
            byteBuf = newBuffer(byteBufAllocator, z, content2.readableBytes() * i);
        }
        byteBuf.writeBytes(content2.slice());
        return byteBuf;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0038, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static p043io.netty.buffer.ByteBuf append(p043io.netty.buffer.ByteBufAllocator r2, boolean r3, java.security.cert.X509Certificate r4, int r5, p043io.netty.buffer.ByteBuf r6) throws java.security.cert.CertificateEncodingException {
        /*
            byte[] r4 = r4.getEncoded()
            io.netty.buffer.ByteBuf r4 = p043io.netty.buffer.Unpooled.wrappedBuffer(r4)
            io.netty.buffer.ByteBuf r0 = p043io.netty.handler.ssl.SslUtils.toBase64(r2, r4)     // Catch:{ all -> 0x0039 }
            if (r6 != 0) goto L_0x0020
            byte[] r6 = BEGIN_CERT     // Catch:{ all -> 0x0034 }
            int r6 = r6.length     // Catch:{ all -> 0x0034 }
            int r1 = r0.readableBytes()     // Catch:{ all -> 0x0034 }
            int r6 = r6 + r1
            byte[] r1 = END_CERT     // Catch:{ all -> 0x0034 }
            int r1 = r1.length     // Catch:{ all -> 0x0034 }
            int r6 = r6 + r1
            int r6 = r6 * r5
            io.netty.buffer.ByteBuf r6 = newBuffer(r2, r3, r6)     // Catch:{ all -> 0x0034 }
        L_0x0020:
            byte[] r2 = BEGIN_CERT     // Catch:{ all -> 0x0034 }
            r6.writeBytes(r2)     // Catch:{ all -> 0x0034 }
            r6.writeBytes(r0)     // Catch:{ all -> 0x0034 }
            byte[] r2 = END_CERT     // Catch:{ all -> 0x0034 }
            r6.writeBytes(r2)     // Catch:{ all -> 0x0034 }
            r0.release()     // Catch:{ all -> 0x0039 }
            r4.release()
            return r6
        L_0x0034:
            r2 = move-exception
            r0.release()     // Catch:{ all -> 0x0039 }
            throw r2     // Catch:{ all -> 0x0039 }
        L_0x0039:
            r2 = move-exception
            r4.release()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.PemX509Certificate.append(io.netty.buffer.ByteBufAllocator, boolean, java.security.cert.X509Certificate, int, io.netty.buffer.ByteBuf):io.netty.buffer.ByteBuf");
    }

    private static ByteBuf newBuffer(ByteBufAllocator byteBufAllocator, boolean z, int i) {
        return z ? byteBufAllocator.directBuffer(i) : byteBufAllocator.buffer(i);
    }

    public static PemX509Certificate valueOf(byte[] bArr) {
        return valueOf(Unpooled.wrappedBuffer(bArr));
    }

    public static PemX509Certificate valueOf(ByteBuf byteBuf) {
        return new PemX509Certificate(byteBuf);
    }

    private PemX509Certificate(ByteBuf byteBuf) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, Param.CONTENT);
    }

    public int refCnt() {
        return this.content.refCnt();
    }

    public ByteBuf content() {
        int refCnt = refCnt();
        if (refCnt > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(refCnt);
    }

    public PemX509Certificate copy() {
        return replace(this.content.copy());
    }

    public PemX509Certificate duplicate() {
        return replace(this.content.duplicate());
    }

    public PemX509Certificate retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    public PemX509Certificate replace(ByteBuf byteBuf) {
        return new PemX509Certificate(byteBuf);
    }

    public PemX509Certificate retain() {
        this.content.retain();
        return this;
    }

    public PemX509Certificate retain(int i) {
        this.content.retain(i);
        return this;
    }

    public PemX509Certificate touch() {
        this.content.touch();
        return this;
    }

    public PemX509Certificate touch(Object obj) {
        this.content.touch(obj);
        return this;
    }

    public boolean release() {
        return this.content.release();
    }

    public boolean release(int i) {
        return this.content.release(i);
    }

    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    public boolean hasUnsupportedCriticalExtension() {
        throw new UnsupportedOperationException();
    }

    public Set<String> getCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    public Set<String> getNonCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    public byte[] getExtensionValue(String str) {
        throw new UnsupportedOperationException();
    }

    public void checkValidity() {
        throw new UnsupportedOperationException();
    }

    public void checkValidity(Date date) {
        throw new UnsupportedOperationException();
    }

    public int getVersion() {
        throw new UnsupportedOperationException();
    }

    public BigInteger getSerialNumber() {
        throw new UnsupportedOperationException();
    }

    public Principal getIssuerDN() {
        throw new UnsupportedOperationException();
    }

    public Principal getSubjectDN() {
        throw new UnsupportedOperationException();
    }

    public Date getNotBefore() {
        throw new UnsupportedOperationException();
    }

    public Date getNotAfter() {
        throw new UnsupportedOperationException();
    }

    public byte[] getTBSCertificate() {
        throw new UnsupportedOperationException();
    }

    public byte[] getSignature() {
        throw new UnsupportedOperationException();
    }

    public String getSigAlgName() {
        throw new UnsupportedOperationException();
    }

    public String getSigAlgOID() {
        throw new UnsupportedOperationException();
    }

    public byte[] getSigAlgParams() {
        throw new UnsupportedOperationException();
    }

    public boolean[] getIssuerUniqueID() {
        throw new UnsupportedOperationException();
    }

    public boolean[] getSubjectUniqueID() {
        throw new UnsupportedOperationException();
    }

    public boolean[] getKeyUsage() {
        throw new UnsupportedOperationException();
    }

    public int getBasicConstraints() {
        throw new UnsupportedOperationException();
    }

    public void verify(PublicKey publicKey) {
        throw new UnsupportedOperationException();
    }

    public void verify(PublicKey publicKey, String str) {
        throw new UnsupportedOperationException();
    }

    public PublicKey getPublicKey() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PemX509Certificate)) {
            return false;
        }
        return this.content.equals(((PemX509Certificate) obj).content);
    }

    public int hashCode() {
        return this.content.hashCode();
    }

    public String toString() {
        return this.content.toString(CharsetUtil.UTF_8);
    }
}
