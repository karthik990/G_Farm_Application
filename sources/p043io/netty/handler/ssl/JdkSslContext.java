package p043io.netty.handler.ssl;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.JdkSslContext */
public class JdkSslContext extends SslContext {
    static final List<String> DEFAULT_CIPHERS;
    static final String[] DEFAULT_PROTOCOLS;
    static final String PROTOCOL = "TLS";
    static final Set<String> SUPPORTED_CIPHERS;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
    private final JdkApplicationProtocolNegotiator apn;
    private final String[] cipherSuites;
    private final ClientAuth clientAuth;
    private final boolean isClient;
    private final String[] protocols;
    private final SSLContext sslContext;
    private final List<String> unmodifiableCipherSuites;

    /* renamed from: io.netty.handler.ssl.JdkSslContext$1 */
    static /* synthetic */ class C57161 {

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol */
        static final /* synthetic */ int[] f3728xc16482e4 = new int[Protocol.values().length];

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior */
        static final /* synthetic */ int[] f3729xcbdfafc1 = new int[SelectedListenerFailureBehavior.values().length];

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior */
        static final /* synthetic */ int[] f3730xb32e3251 = new int[SelectorFailureBehavior.values().length];
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ClientAuth = new int[ClientAuth.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|(2:1|2)|3|5|6|(2:7|8)|9|11|12|(2:13|14)|15|17|18|(2:19|20)|21|23|24|25|26|28) */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x005a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0077 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001f */
        static {
            /*
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol[] r0 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3728xc16482e4 = r0
                r0 = 1
                int[] r1 = f3728xc16482e4     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NONE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3728xc16482e4     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.ALPN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r2 = f3728xc16482e4     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NPN     // Catch:{ NoSuchFieldError -> 0x002a }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior[] r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3729xcbdfafc1 = r2
                int[] r2 = f3729xcbdfafc1     // Catch:{ NoSuchFieldError -> 0x003d }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT     // Catch:{ NoSuchFieldError -> 0x003d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x003d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x003d }
            L_0x003d:
                int[] r2 = f3729xcbdfafc1     // Catch:{ NoSuchFieldError -> 0x0047 }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior.FATAL_ALERT     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior[] r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3730xb32e3251 = r2
                int[] r2 = f3730xb32e3251     // Catch:{ NoSuchFieldError -> 0x005a }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior.FATAL_ALERT     // Catch:{ NoSuchFieldError -> 0x005a }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x005a }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x005a }
            L_0x005a:
                int[] r2 = f3730xb32e3251     // Catch:{ NoSuchFieldError -> 0x0064 }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE     // Catch:{ NoSuchFieldError -> 0x0064 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0064 }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0064 }
            L_0x0064:
                io.netty.handler.ssl.ClientAuth[] r2 = p043io.netty.handler.ssl.ClientAuth.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                $SwitchMap$io$netty$handler$ssl$ClientAuth = r2
                int[] r2 = $SwitchMap$io$netty$handler$ssl$ClientAuth     // Catch:{ NoSuchFieldError -> 0x0077 }
                io.netty.handler.ssl.ClientAuth r3 = p043io.netty.handler.ssl.ClientAuth.OPTIONAL     // Catch:{ NoSuchFieldError -> 0x0077 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0077 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0077 }
            L_0x0077:
                int[] r0 = $SwitchMap$io$netty$handler$ssl$ClientAuth     // Catch:{ NoSuchFieldError -> 0x0081 }
                io.netty.handler.ssl.ClientAuth r2 = p043io.netty.handler.ssl.ClientAuth.REQUIRE     // Catch:{ NoSuchFieldError -> 0x0081 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0081 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0081 }
            L_0x0081:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.JdkSslContext.C57161.<clinit>():void");
        }
    }

    static {
        String[] enabledCipherSuites;
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, null, null);
            SSLEngine createSSLEngine = instance.createSSLEngine();
            String[] supportedProtocols = createSSLEngine.getSupportedProtocols();
            HashSet hashSet = new HashSet(supportedProtocols.length);
            for (String add : supportedProtocols) {
                hashSet.add(add);
            }
            ArrayList arrayList = new ArrayList();
            addIfSupported(hashSet, arrayList, "TLSv1.2", "TLSv1.1", "TLSv1");
            if (!arrayList.isEmpty()) {
                DEFAULT_PROTOCOLS = (String[]) arrayList.toArray(new String[arrayList.size()]);
            } else {
                DEFAULT_PROTOCOLS = createSSLEngine.getEnabledProtocols();
            }
            String[] supportedCipherSuites = createSSLEngine.getSupportedCipherSuites();
            SUPPORTED_CIPHERS = new HashSet(supportedCipherSuites.length);
            for (String add2 : supportedCipherSuites) {
                SUPPORTED_CIPHERS.add(add2);
            }
            ArrayList arrayList2 = new ArrayList();
            addIfSupported(SUPPORTED_CIPHERS, arrayList2, "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA");
            if (arrayList2.isEmpty()) {
                for (String str : createSSLEngine.getEnabledCipherSuites()) {
                    if (!str.contains("_RC4_")) {
                        arrayList2.add(str);
                    }
                }
            }
            DEFAULT_CIPHERS = Collections.unmodifiableList(arrayList2);
            if (logger.isDebugEnabled()) {
                logger.debug("Default protocols (JDK): {} ", (Object) Arrays.asList(DEFAULT_PROTOCOLS));
                logger.debug("Default cipher suites (JDK): {}", (Object) DEFAULT_CIPHERS);
            }
        } catch (Exception e) {
            throw new Error("failed to initialize the default SSL context", e);
        }
    }

    private static void addIfSupported(Set<String> set, List<String> list, String... strArr) {
        for (String str : strArr) {
            if (set.contains(str)) {
                list.add(str);
            }
        }
    }

    public JdkSslContext(SSLContext sSLContext, boolean z, ClientAuth clientAuth2) {
        this(sSLContext, z, null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth2, null, false);
    }

    public JdkSslContext(SSLContext sSLContext, boolean z, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, ClientAuth clientAuth2) {
        this(sSLContext, z, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, !z), clientAuth2, null, false);
    }

    JdkSslContext(SSLContext sSLContext, boolean z, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, ClientAuth clientAuth2, String[] strArr, boolean z2) {
        super(z2);
        this.apn = (JdkApplicationProtocolNegotiator) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "apn");
        this.clientAuth = (ClientAuth) ObjectUtil.checkNotNull(clientAuth2, "clientAuth");
        this.cipherSuites = ((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter")).filterCipherSuites(iterable, DEFAULT_CIPHERS, SUPPORTED_CIPHERS);
        if (strArr == null) {
            strArr = DEFAULT_PROTOCOLS;
        }
        this.protocols = strArr;
        this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
        this.sslContext = (SSLContext) ObjectUtil.checkNotNull(sSLContext, "sslContext");
        this.isClient = z;
    }

    public final SSLContext context() {
        return this.sslContext;
    }

    public final boolean isClient() {
        return this.isClient;
    }

    public final SSLSessionContext sessionContext() {
        if (isServer()) {
            return context().getServerSessionContext();
        }
        return context().getClientSessionContext();
    }

    public final List<String> cipherSuites() {
        return this.unmodifiableCipherSuites;
    }

    public final long sessionCacheSize() {
        return (long) sessionContext().getSessionCacheSize();
    }

    public final long sessionTimeout() {
        return (long) sessionContext().getSessionTimeout();
    }

    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        return configureAndWrapEngine(context().createSSLEngine());
    }

    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i) {
        return configureAndWrapEngine(context().createSSLEngine(str, i));
    }

    private SSLEngine configureAndWrapEngine(SSLEngine sSLEngine) {
        sSLEngine.setEnabledCipherSuites(this.cipherSuites);
        sSLEngine.setEnabledProtocols(this.protocols);
        sSLEngine.setUseClientMode(isClient());
        if (isServer()) {
            int i = C57161.$SwitchMap$io$netty$handler$ssl$ClientAuth[this.clientAuth.ordinal()];
            if (i == 1) {
                sSLEngine.setWantClientAuth(true);
            } else if (i == 2) {
                sSLEngine.setNeedClientAuth(true);
            }
        }
        return this.apn.wrapperFactory().wrapSslEngine(sSLEngine, this.apn, isServer());
    }

    public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig applicationProtocolConfig, boolean z) {
        if (applicationProtocolConfig == null) {
            return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
        }
        int i = C57161.f3728xc16482e4[applicationProtocolConfig.protocol().ordinal()];
        if (i == 1) {
            return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
        }
        String str = " failure behavior";
        String str2 = "JDK provider does not support ";
        if (i != 2) {
            if (i != 3) {
                StringBuilder sb = new StringBuilder(str2);
                sb.append(applicationProtocolConfig.protocol());
                sb.append(" protocol");
                throw new UnsupportedOperationException(sb.toString());
            } else if (z) {
                int i2 = C57161.f3729xcbdfafc1[applicationProtocolConfig.selectedListenerFailureBehavior().ordinal()];
                if (i2 == 1) {
                    return new JdkNpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                }
                if (i2 == 2) {
                    return new JdkNpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                }
                StringBuilder sb2 = new StringBuilder(str2);
                sb2.append(applicationProtocolConfig.selectedListenerFailureBehavior());
                sb2.append(str);
                throw new UnsupportedOperationException(sb2.toString());
            } else {
                int i3 = C57161.f3730xb32e3251[applicationProtocolConfig.selectorFailureBehavior().ordinal()];
                if (i3 == 1) {
                    return new JdkNpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                }
                if (i3 == 2) {
                    return new JdkNpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                }
                StringBuilder sb3 = new StringBuilder(str2);
                sb3.append(applicationProtocolConfig.selectorFailureBehavior());
                sb3.append(str);
                throw new UnsupportedOperationException(sb3.toString());
            }
        } else if (z) {
            int i4 = C57161.f3730xb32e3251[applicationProtocolConfig.selectorFailureBehavior().ordinal()];
            if (i4 == 1) {
                return new JdkAlpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
            }
            if (i4 == 2) {
                return new JdkAlpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
            }
            StringBuilder sb4 = new StringBuilder(str2);
            sb4.append(applicationProtocolConfig.selectorFailureBehavior());
            sb4.append(str);
            throw new UnsupportedOperationException(sb4.toString());
        } else {
            int i5 = C57161.f3729xcbdfafc1[applicationProtocolConfig.selectedListenerFailureBehavior().ordinal()];
            if (i5 == 1) {
                return new JdkAlpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
            }
            if (i5 == 2) {
                return new JdkAlpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
            }
            StringBuilder sb5 = new StringBuilder(str2);
            sb5.append(applicationProtocolConfig.selectedListenerFailureBehavior());
            sb5.append(str);
            throw new UnsupportedOperationException(sb5.toString());
        }
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File file, File file2, String str, KeyManagerFactory keyManagerFactory) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
        String property = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (property == null) {
            property = "SunX509";
        }
        return buildKeyManagerFactory(file, property, file2, str, keyManagerFactory);
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File file, String str, File file2, String str2, KeyManagerFactory keyManagerFactory) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
        return buildKeyManagerFactory(toX509Certificates(file), str, toPrivateKey(file2, str2), str2, keyManagerFactory);
    }
}
