package com.mobiroller.helpers;

import com.google.common.base.Ascii;
import p105se.simbio.encryption.Encryption;

public class EncryptionHelper {
    Encryption encryption = Encryption.getDefault("Mobiroller", "Salt", this.f2164iv);

    /* renamed from: iv */
    byte[] f2164iv = {-89, -19, 17, -83, 86, 106, -31, Ascii.f1874RS, -5, -111, 61, -75, -84, 95, 120, -53};

    public String encryptMessage(String str) {
        return this.encryption.encryptOrNull(str);
    }

    public String decryptMessage(String str) {
        return this.encryption.decryptOrNull(str);
    }
}
