package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateRequestedCallback.KeyMaterial;
import io.netty.internal.tcnative.SSL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.net.ssl.SSLException;
import javax.net.ssl.X509KeyManager;
import javax.security.auth.x500.X500Principal;
import p043io.netty.buffer.ByteBufAllocator;

/* renamed from: io.netty.handler.ssl.OpenSslKeyMaterialManager */
class OpenSslKeyMaterialManager {
    private static final Map<String, String> KEY_TYPES = new HashMap();
    static final String KEY_TYPE_DH_RSA = "DH_RSA";
    static final String KEY_TYPE_EC = "EC";
    static final String KEY_TYPE_EC_EC = "EC_EC";
    static final String KEY_TYPE_EC_RSA = "EC_RSA";
    static final String KEY_TYPE_RSA = "RSA";
    private final X509KeyManager keyManager;
    private final String password;

    static {
        Map<String, String> map = KEY_TYPES;
        String str = KEY_TYPE_RSA;
        map.put(str, str);
        KEY_TYPES.put("DHE_RSA", str);
        KEY_TYPES.put("ECDHE_RSA", str);
        KEY_TYPES.put("ECDHE_ECDSA", KEY_TYPE_EC);
        KEY_TYPES.put("ECDH_RSA", KEY_TYPE_EC_RSA);
        KEY_TYPES.put("ECDH_ECDSA", KEY_TYPE_EC_EC);
        Map<String, String> map2 = KEY_TYPES;
        String str2 = KEY_TYPE_DH_RSA;
        map2.put(str2, str2);
    }

    OpenSslKeyMaterialManager(X509KeyManager x509KeyManager, String str) {
        this.keyManager = x509KeyManager;
        this.password = str;
    }

    /* access modifiers changed from: 0000 */
    public void setKeyMaterial(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) throws SSLException {
        long sslPointer = referenceCountedOpenSslEngine.sslPointer();
        String[] authenticationMethods = SSL.authenticationMethods(sslPointer);
        HashSet hashSet = new HashSet(authenticationMethods.length);
        for (String str : authenticationMethods) {
            String str2 = (String) KEY_TYPES.get(str);
            if (str2 != null) {
                String chooseServerAlias = chooseServerAlias(referenceCountedOpenSslEngine, str2);
                if (chooseServerAlias != null && hashSet.add(chooseServerAlias)) {
                    setKeyMaterial(sslPointer, chooseServerAlias);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public KeyMaterial keyMaterial(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String[] strArr, X500Principal[] x500PrincipalArr) throws SSLException {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        KeyMaterial keyMaterial;
        long j6;
        String chooseClientAlias = chooseClientAlias(referenceCountedOpenSslEngine, strArr, x500PrincipalArr);
        long j7 = 0;
        try {
            X509Certificate[] certificateChain = this.keyManager.getCertificateChain(chooseClientAlias);
            if (certificateChain != null) {
                if (certificateChain.length != 0) {
                    PrivateKey privateKey = this.keyManager.getPrivateKey(chooseClientAlias);
                    j3 = ReferenceCountedOpenSslContext.toBIO(certificateChain);
                    try {
                        j2 = SSL.parseX509Chain(j3);
                        if (privateKey != null) {
                            try {
                                j6 = ReferenceCountedOpenSslContext.toBIO(privateKey);
                                try {
                                    j = SSL.parsePrivateKey(j6, this.password);
                                } catch (SSLException e) {
                                    e = e;
                                    long j8 = j6;
                                    throw e;
                                } catch (Exception e2) {
                                    e = e2;
                                    j = 0;
                                    j7 = j6;
                                    throw new SSLException(e);
                                } catch (Throwable th) {
                                    th = th;
                                    j = 0;
                                    j7 = j6;
                                    ReferenceCountedOpenSslContext.freeBio(j7);
                                    ReferenceCountedOpenSslContext.freeBio(j3);
                                    SSL.freePrivateKey(j);
                                    SSL.freeX509Chain(j2);
                                    throw th;
                                }
                            } catch (SSLException e3) {
                                e = e3;
                                throw e;
                            } catch (Exception e4) {
                                e = e4;
                                j = 0;
                                throw new SSLException(e);
                            } catch (Throwable th2) {
                                th = th2;
                                j = 0;
                                ReferenceCountedOpenSslContext.freeBio(j7);
                                ReferenceCountedOpenSslContext.freeBio(j3);
                                SSL.freePrivateKey(j);
                                SSL.freeX509Chain(j2);
                                throw th;
                            }
                        } else {
                            j6 = 0;
                            j = 0;
                        }
                    } catch (SSLException e5) {
                        e = e5;
                        j4 = 0;
                        long j9 = j4;
                        throw e;
                    } catch (Exception e6) {
                        e = e6;
                        j5 = 0;
                        j = j2;
                        throw new SSLException(e);
                    } catch (Throwable th3) {
                        th = th3;
                        j2 = 0;
                        j = j2;
                        ReferenceCountedOpenSslContext.freeBio(j7);
                        ReferenceCountedOpenSslContext.freeBio(j3);
                        SSL.freePrivateKey(j);
                        SSL.freeX509Chain(j2);
                        throw th;
                    }
                    try {
                        keyMaterial = new KeyMaterial(j2, j);
                        ReferenceCountedOpenSslContext.freeBio(j6);
                        ReferenceCountedOpenSslContext.freeBio(j3);
                        SSL.freePrivateKey(0);
                        SSL.freeX509Chain(0);
                        return keyMaterial;
                    } catch (SSLException e7) {
                        e = e7;
                        long j82 = j6;
                        throw e;
                    } catch (Exception e8) {
                        e = e8;
                        j7 = j6;
                        throw new SSLException(e);
                    } catch (Throwable th4) {
                        th = th4;
                        j7 = j6;
                        ReferenceCountedOpenSslContext.freeBio(j7);
                        ReferenceCountedOpenSslContext.freeBio(j3);
                        SSL.freePrivateKey(j);
                        SSL.freeX509Chain(j2);
                        throw th;
                    }
                }
            }
            keyMaterial = null;
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
            SSL.freePrivateKey(0);
            SSL.freeX509Chain(0);
            return keyMaterial;
        } catch (SSLException e9) {
            e = e9;
            j4 = 0;
            long j92 = j4;
            throw e;
        } catch (Exception e10) {
            e = e10;
            j3 = 0;
            j5 = 0;
            j = j2;
            throw new SSLException(e);
        } catch (Throwable th5) {
            th = th5;
            ReferenceCountedOpenSslContext.freeBio(j7);
            ReferenceCountedOpenSslContext.freeBio(j3);
            SSL.freePrivateKey(j);
            SSL.freeX509Chain(j2);
            throw th;
        }
    }

    private void setKeyMaterial(long j, String str) throws SSLException {
        long j2;
        long j3;
        long j4;
        String str2 = str;
        long j5 = 0;
        try {
            X509Certificate[] certificateChain = this.keyManager.getCertificateChain(str2);
            if (certificateChain != null) {
                if (certificateChain.length != 0) {
                    PrivateKey privateKey = this.keyManager.getPrivateKey(str2);
                    PemEncoded pem = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, certificateChain);
                    try {
                        j2 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, pem.retain());
                        try {
                            long bio = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, pem.retain());
                            if (privateKey != null) {
                                try {
                                    j5 = ReferenceCountedOpenSslContext.toBIO(privateKey);
                                } catch (Throwable th) {
                                    th = th;
                                    j3 = bio;
                                    pem.release();
                                    throw th;
                                }
                            }
                            j4 = j;
                            j3 = bio;
                        } catch (Throwable th2) {
                            th = th2;
                            j3 = 0;
                            pem.release();
                            throw th;
                        }
                        try {
                            SSL.setCertificateBio(j4, j2, j5, this.password);
                            SSL.setCertificateChainBio(j4, j3, true);
                            try {
                                pem.release();
                                ReferenceCountedOpenSslContext.freeBio(j5);
                                ReferenceCountedOpenSslContext.freeBio(j2);
                                ReferenceCountedOpenSslContext.freeBio(j3);
                                return;
                            } catch (SSLException e) {
                                e = e;
                                throw e;
                            } catch (Exception e2) {
                                e = e2;
                                throw new SSLException(e);
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            pem.release();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        j3 = 0;
                        j2 = 0;
                        pem.release();
                        throw th;
                    }
                }
            }
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
        } catch (SSLException e3) {
            e = e3;
            throw e;
        } catch (Exception e4) {
            e = e4;
            j3 = 0;
            j2 = 0;
            throw new SSLException(e);
        } catch (Throwable th5) {
            th = th5;
            ReferenceCountedOpenSslContext.freeBio(j5);
            ReferenceCountedOpenSslContext.freeBio(j2);
            ReferenceCountedOpenSslContext.freeBio(j3);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public String chooseClientAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String[] strArr, X500Principal[] x500PrincipalArr) {
        return this.keyManager.chooseClientAlias(strArr, x500PrincipalArr, null);
    }

    /* access modifiers changed from: protected */
    public String chooseServerAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String str) {
        return this.keyManager.chooseServerAlias(str, null, null);
    }
}
