package com.anjlab.android.iab.p020v3;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/* renamed from: com.anjlab.android.iab.v3.Security */
class Security {
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String TAG = "IABUtil/Security";

    Security() {
    }

    public static boolean verifyPurchase(String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str4)) {
            return verify(generatePublicKey(str2), str3, str4);
        }
        if (str.equals("android.test.purchased") || str.equals("android.test.canceled") || str.equals("android.test.refunded") || str.equals("android.test.item_unavailable")) {
            return true;
        }
        Log.e(TAG, "Purchase verification failed: missing data.");
        return false;
    }

    public static PublicKey generatePublicKey(String str) {
        String str2 = TAG;
        try {
            return KeyFactory.getInstance(KEY_FACTORY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e2) {
            Log.e(str2, "Invalid key specification.");
            throw new IllegalArgumentException(e2);
        } catch (IllegalArgumentException e3) {
            Log.e(str2, "Base64 decoding failed.");
            throw e3;
        }
    }

    public static boolean verify(PublicKey publicKey, String str, String str2) {
        String str3 = TAG;
        try {
            Signature instance = Signature.getInstance(SIGNATURE_ALGORITHM);
            instance.initVerify(publicKey);
            instance.update(str.getBytes());
            if (instance.verify(Base64.decode(str2, 0))) {
                return true;
            }
            Log.e(str3, "Signature verification failed.");
            return false;
        } catch (NoSuchAlgorithmException unused) {
            Log.e(str3, "NoSuchAlgorithmException.");
            return false;
        } catch (InvalidKeyException unused2) {
            Log.e(str3, "Invalid key specification.");
            return false;
        } catch (SignatureException unused3) {
            Log.e(str3, "Signature exception.");
            return false;
        } catch (IllegalArgumentException unused4) {
            Log.e(str3, "Base64 decoding failed.");
            return false;
        }
    }
}
