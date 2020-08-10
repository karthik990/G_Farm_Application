package p043io.netty.handler.ssl.util;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.util.InsecureTrustManagerFactory */
public final class InsecureTrustManagerFactory extends SimpleTrustManagerFactory {
    public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(InsecureTrustManagerFactory.class);

    /* renamed from: tm */
    private static final TrustManager f3739tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
            InternalLogger access$000 = InsecureTrustManagerFactory.logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Accepting a client certificate: ");
            sb.append(x509CertificateArr[0].getSubjectDN());
            access$000.debug(sb.toString());
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            InternalLogger access$000 = InsecureTrustManagerFactory.logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Accepting a server certificate: ");
            sb.append(x509CertificateArr[0].getSubjectDN());
            access$000.debug(sb.toString());
        }

        public X509Certificate[] getAcceptedIssuers() {
            return EmptyArrays.EMPTY_X509_CERTIFICATES;
        }
    };

    /* access modifiers changed from: protected */
    public void engineInit(KeyStore keyStore) throws Exception {
    }

    /* access modifiers changed from: protected */
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
    }

    private InsecureTrustManagerFactory() {
    }

    /* access modifiers changed from: protected */
    public TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{f3739tm};
    }
}
