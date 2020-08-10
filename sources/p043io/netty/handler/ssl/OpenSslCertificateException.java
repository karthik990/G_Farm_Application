package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateVerifier;
import java.security.cert.CertificateException;

/* renamed from: io.netty.handler.ssl.OpenSslCertificateException */
public final class OpenSslCertificateException extends CertificateException {
    private static final long serialVersionUID = 5542675253797129798L;
    private final int errorCode;

    public OpenSslCertificateException(int i) {
        this((String) null, i);
    }

    public OpenSslCertificateException(String str, int i) {
        super(str);
        this.errorCode = checkErrorCode(i);
    }

    public OpenSslCertificateException(String str, Throwable th, int i) {
        super(str, th);
        this.errorCode = checkErrorCode(i);
    }

    public OpenSslCertificateException(Throwable th, int i) {
        this(null, th, i);
    }

    public int errorCode() {
        return this.errorCode;
    }

    private static int checkErrorCode(int i) {
        if (CertificateVerifier.isValid(i)) {
            return i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("errorCode '");
        sb.append(i);
        sb.append("' invalid, see https://www.openssl.org/docs/man1.0.2/apps/verify.html.");
        throw new IllegalArgumentException(sb.toString());
    }
}
