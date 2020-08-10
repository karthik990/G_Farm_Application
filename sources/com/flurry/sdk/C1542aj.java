package com.flurry.sdk;

import android.os.Build.VERSION;
import android.security.keystore.KeyGenParameterSpec.Builder;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.KeyGenerator;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* renamed from: com.flurry.sdk.aj */
public final class C1542aj {

    /* renamed from: a */
    private KeyStore f506a;

    C1542aj() {
        String str = "fl.install.id.sec.key";
        String str2 = "KeystoreProvider";
        String str3 = "AndroidKeyStore";
        if (VERSION.SDK_INT >= 23) {
            try {
                this.f506a = KeyStore.getInstance(str3);
                this.f506a.load(null);
                if (this.f506a != null && !this.f506a.containsAlias(str)) {
                    KeyGenerator instance = KeyGenerator.getInstance("AES", str3);
                    instance.init(new Builder(str, 3).setBlockModes(new String[]{"CBC"}).setEncryptionPaddings(new String[]{"PKCS7Padding"}).setRandomizedEncryptionRequired(false).setDigests(new String[]{"SHA-256", MessageDigestAlgorithms.SHA_512}).build());
                    instance.generateKey();
                }
            } catch (IOException | NullPointerException | InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertificateException e) {
                StringBuilder sb = new StringBuilder("Error while generating Key");
                sb.append(e.getMessage());
                C1685cy.m755a(5, str2, sb.toString(), e);
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder("Unknown Error while generating Key");
                sb2.append(e2.getMessage());
                C1685cy.m755a(5, str2, sb2.toString(), e2);
            }
        }
    }

    /* renamed from: a */
    public final Key mo16259a() {
        if (VERSION.SDK_INT < 23) {
            return null;
        }
        KeyStore keyStore = this.f506a;
        if (keyStore == null) {
            return null;
        }
        try {
            return keyStore.getKey("fl.install.id.sec.key", null);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException unused) {
            C1685cy.m754a(6, "KeystoreProvider", "Error in getting key.");
            return null;
        }
    }
}
