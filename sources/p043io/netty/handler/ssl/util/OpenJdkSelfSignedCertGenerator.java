package p043io.netty.handler.ssl.util;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Date;
import org.apache.http.cookie.ClientCookie;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

/* renamed from: io.netty.handler.ssl.util.OpenJdkSelfSignedCertGenerator */
final class OpenJdkSelfSignedCertGenerator {
    static String[] generate(String str, KeyPair keyPair, SecureRandom secureRandom, Date date, Date date2) throws Exception {
        String str2 = "issuer";
        String str3 = "subject";
        PrivateKey privateKey = keyPair.getPrivate();
        X509CertInfo x509CertInfo = new X509CertInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("CN=");
        sb.append(str);
        X500Name x500Name = new X500Name(sb.toString());
        x509CertInfo.set(ClientCookie.VERSION_ATTR, new CertificateVersion(2));
        x509CertInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, secureRandom)));
        try {
            x509CertInfo.set(str3, new CertificateSubjectName(x500Name));
        } catch (CertificateException unused) {
            x509CertInfo.set(str3, x500Name);
        }
        try {
            x509CertInfo.set(str2, new CertificateIssuerName(x500Name));
        } catch (CertificateException unused2) {
            x509CertInfo.set(str2, x500Name);
        }
        x509CertInfo.set("validity", new CertificateValidity(date, date2));
        x509CertInfo.set("key", new CertificateX509Key(keyPair.getPublic()));
        x509CertInfo.set("algorithmID", new CertificateAlgorithmId(new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid)));
        X509CertImpl x509CertImpl = new X509CertImpl(x509CertInfo);
        String str4 = "SHA1withRSA";
        x509CertImpl.sign(privateKey, str4);
        x509CertInfo.set("algorithmID.algorithm", x509CertImpl.get("x509.algorithm"));
        X509CertImpl x509CertImpl2 = new X509CertImpl(x509CertInfo);
        x509CertImpl2.sign(privateKey, str4);
        x509CertImpl2.verify(keyPair.getPublic());
        return SelfSignedCertificate.newSelfSignedCertificate(str, privateKey, x509CertImpl2);
    }

    private OpenJdkSelfSignedCertGenerator() {
    }
}
