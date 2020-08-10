package p043io.netty.handler.ssl;

import javax.net.ssl.X509ExtendedKeyManager;
import javax.security.auth.x500.X500Principal;

/* renamed from: io.netty.handler.ssl.OpenSslExtendedKeyMaterialManager */
final class OpenSslExtendedKeyMaterialManager extends OpenSslKeyMaterialManager {
    private final X509ExtendedKeyManager keyManager;

    OpenSslExtendedKeyMaterialManager(X509ExtendedKeyManager x509ExtendedKeyManager, String str) {
        super(x509ExtendedKeyManager, str);
        this.keyManager = x509ExtendedKeyManager;
    }

    /* access modifiers changed from: protected */
    public String chooseClientAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String[] strArr, X500Principal[] x500PrincipalArr) {
        return this.keyManager.chooseEngineClientAlias(strArr, x500PrincipalArr, referenceCountedOpenSslEngine);
    }

    /* access modifiers changed from: protected */
    public String chooseServerAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String str) {
        return this.keyManager.chooseEngineServerAlias(str, null, referenceCountedOpenSslEngine);
    }
}
