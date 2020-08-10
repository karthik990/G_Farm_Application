package p043io.netty.handler.ssl.util;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.buffer.Unpooled;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.internal.EmptyArrays;

/* renamed from: io.netty.handler.ssl.util.FingerprintTrustManagerFactory */
public final class FingerprintTrustManagerFactory extends SimpleTrustManagerFactory {
    private static final Pattern FINGERPRINT_PATTERN = Pattern.compile("^[0-9a-fA-F:]+$");
    private static final Pattern FINGERPRINT_STRIP_PATTERN = Pattern.compile(":");
    private static final int SHA1_BYTE_LEN = 20;
    private static final int SHA1_HEX_LEN = 40;
    /* access modifiers changed from: private */
    public static final FastThreadLocal<MessageDigest> tlmd = new FastThreadLocal<MessageDigest>() {
        /* access modifiers changed from: protected */
        public MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                throw new Error(e);
            }
        }
    };
    /* access modifiers changed from: private */
    public final byte[][] fingerprints;

    /* renamed from: tm */
    private final TrustManager f3738tm;

    /* access modifiers changed from: protected */
    public void engineInit(KeyStore keyStore) throws Exception {
    }

    /* access modifiers changed from: protected */
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
    }

    public FingerprintTrustManagerFactory(Iterable<String> iterable) {
        this(toFingerprintArray(iterable));
    }

    public FingerprintTrustManagerFactory(String... strArr) {
        this(toFingerprintArray(Arrays.asList(strArr)));
    }

    public FingerprintTrustManagerFactory(byte[]... bArr) {
        this.f3738tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                checkTrusted("client", x509CertificateArr);
            }

            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                checkTrusted("server", x509CertificateArr);
            }

            private void checkTrusted(String str, X509Certificate[] x509CertificateArr) throws CertificateException {
                boolean z = false;
                X509Certificate x509Certificate = x509CertificateArr[0];
                byte[] fingerprint = fingerprint(x509Certificate);
                byte[][] access$000 = FingerprintTrustManagerFactory.this.fingerprints;
                int length = access$000.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (Arrays.equals(fingerprint, access$000[i])) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(" certificate with unknown fingerprint: ");
                    sb.append(x509Certificate.getSubjectDN());
                    throw new CertificateException(sb.toString());
                }
            }

            private byte[] fingerprint(X509Certificate x509Certificate) throws CertificateEncodingException {
                MessageDigest messageDigest = (MessageDigest) FingerprintTrustManagerFactory.tlmd.get();
                messageDigest.reset();
                return messageDigest.digest(x509Certificate.getEncoded());
            }

            public X509Certificate[] getAcceptedIssuers() {
                return EmptyArrays.EMPTY_X509_CERTIFICATES;
            }
        };
        if (bArr != null) {
            ArrayList arrayList = new ArrayList(bArr.length);
            int length = bArr.length;
            int i = 0;
            while (i < length) {
                byte[] bArr2 = bArr[i];
                if (bArr2 == null) {
                    break;
                } else if (bArr2.length == 20) {
                    arrayList.add(bArr2.clone());
                    i++;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("malformed fingerprint: ");
                    sb.append(ByteBufUtil.hexDump(Unpooled.wrappedBuffer(bArr2)));
                    sb.append(" (expected: SHA1)");
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            this.fingerprints = (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
            return;
        }
        throw new NullPointerException("fingerprints");
    }

    private static byte[][] toFingerprintArray(Iterable<String> iterable) {
        if (iterable != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : iterable) {
                if (str == null) {
                    break;
                }
                String str2 = "malformed fingerprint: ";
                if (FINGERPRINT_PATTERN.matcher(str).matches()) {
                    String replaceAll = FINGERPRINT_STRIP_PATTERN.matcher(str).replaceAll("");
                    if (replaceAll.length() == 40) {
                        byte[] bArr = new byte[20];
                        for (int i = 0; i < bArr.length; i++) {
                            int i2 = i << 1;
                            bArr[i] = (byte) Integer.parseInt(replaceAll.substring(i2, i2 + 2), 16);
                        }
                        arrayList.add(bArr);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(replaceAll);
                        sb.append(" (expected: SHA1)");
                        throw new IllegalArgumentException(sb.toString());
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append(str);
                    throw new IllegalArgumentException(sb2.toString());
                }
            }
            return (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
        }
        throw new NullPointerException("fingerprints");
    }

    /* access modifiers changed from: protected */
    public TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{this.f3738tm};
    }
}
