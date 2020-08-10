package p043io.netty.handler.ssl;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Date;
import javax.security.cert.CertificateException;
import javax.security.cert.CertificateExpiredException;
import javax.security.cert.CertificateNotYetValidException;
import javax.security.cert.X509Certificate;

/* renamed from: io.netty.handler.ssl.OpenSslJavaxX509Certificate */
final class OpenSslJavaxX509Certificate extends X509Certificate {
    private final byte[] bytes;
    private X509Certificate wrapped;

    public OpenSslJavaxX509Certificate(byte[] bArr) {
        this.bytes = bArr;
    }

    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        unwrap().checkValidity();
    }

    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        unwrap().checkValidity(date);
    }

    public int getVersion() {
        return unwrap().getVersion();
    }

    public BigInteger getSerialNumber() {
        return unwrap().getSerialNumber();
    }

    public Principal getIssuerDN() {
        return unwrap().getIssuerDN();
    }

    public Principal getSubjectDN() {
        return unwrap().getSubjectDN();
    }

    public Date getNotBefore() {
        return unwrap().getNotBefore();
    }

    public Date getNotAfter() {
        return unwrap().getNotAfter();
    }

    public String getSigAlgName() {
        return unwrap().getSigAlgName();
    }

    public String getSigAlgOID() {
        return unwrap().getSigAlgOID();
    }

    public byte[] getSigAlgParams() {
        return unwrap().getSigAlgParams();
    }

    public byte[] getEncoded() {
        return (byte[]) this.bytes.clone();
    }

    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        unwrap().verify(publicKey);
    }

    public void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        unwrap().verify(publicKey, str);
    }

    public String toString() {
        return unwrap().toString();
    }

    public PublicKey getPublicKey() {
        return unwrap().getPublicKey();
    }

    private X509Certificate unwrap() {
        X509Certificate x509Certificate = this.wrapped;
        if (x509Certificate != null) {
            return x509Certificate;
        }
        try {
            X509Certificate instance = X509Certificate.getInstance(this.bytes);
            this.wrapped = instance;
            return instance;
        } catch (CertificateException e) {
            throw new IllegalStateException(e);
        }
    }
}
