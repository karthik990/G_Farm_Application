package p043io.netty.handler.ssl.util;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.ssl.util.SimpleTrustManagerFactory */
public abstract class SimpleTrustManagerFactory extends TrustManagerFactory {
    private static final FastThreadLocal<SimpleTrustManagerFactorySpi> CURRENT_SPI = new FastThreadLocal<SimpleTrustManagerFactorySpi>() {
        /* access modifiers changed from: protected */
        public SimpleTrustManagerFactorySpi initialValue() {
            return new SimpleTrustManagerFactorySpi();
        }
    };
    private static final Provider PROVIDER;

    /* renamed from: io.netty.handler.ssl.util.SimpleTrustManagerFactory$SimpleTrustManagerFactorySpi */
    static final class SimpleTrustManagerFactorySpi extends TrustManagerFactorySpi {
        private SimpleTrustManagerFactory parent;
        private volatile TrustManager[] trustManagers;

        SimpleTrustManagerFactorySpi() {
        }

        /* access modifiers changed from: 0000 */
        public void init(SimpleTrustManagerFactory simpleTrustManagerFactory) {
            this.parent = simpleTrustManagerFactory;
        }

        /* access modifiers changed from: protected */
        public void engineInit(KeyStore keyStore) throws KeyStoreException {
            try {
                this.parent.engineInit(keyStore);
            } catch (KeyStoreException e) {
                throw e;
            } catch (Exception e2) {
                throw new KeyStoreException(e2);
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
            try {
                this.parent.engineInit(managerFactoryParameters);
            } catch (InvalidAlgorithmParameterException e) {
                throw e;
            } catch (Exception e2) {
                throw new InvalidAlgorithmParameterException(e2);
            }
        }

        /* access modifiers changed from: protected */
        public TrustManager[] engineGetTrustManagers() {
            TrustManager[] trustManagerArr = this.trustManagers;
            if (trustManagerArr == null) {
                trustManagerArr = this.parent.engineGetTrustManagers();
                if (PlatformDependent.javaVersion() >= 7) {
                    for (int i = 0; i < trustManagerArr.length; i++) {
                        TrustManager trustManager = trustManagerArr[i];
                        if ((trustManager instanceof X509TrustManager) && !(trustManager instanceof X509ExtendedTrustManager)) {
                            trustManagerArr[i] = new X509TrustManagerWrapper((X509TrustManager) trustManager);
                        }
                    }
                }
                this.trustManagers = trustManagerArr;
            }
            return (TrustManager[]) trustManagerArr.clone();
        }
    }

    /* access modifiers changed from: protected */
    public abstract TrustManager[] engineGetTrustManagers();

    /* access modifiers changed from: protected */
    public abstract void engineInit(KeyStore keyStore) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception;

    static {
        String str = "";
        PROVIDER = new Provider(str, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, str) {
            private static final long serialVersionUID = -2680540247105807895L;
        };
    }

    protected SimpleTrustManagerFactory() {
        this("");
    }

    protected SimpleTrustManagerFactory(String str) {
        super((TrustManagerFactorySpi) CURRENT_SPI.get(), PROVIDER, str);
        ((SimpleTrustManagerFactorySpi) CURRENT_SPI.get()).init(this);
        CURRENT_SPI.remove();
        if (str == null) {
            throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        }
    }
}
