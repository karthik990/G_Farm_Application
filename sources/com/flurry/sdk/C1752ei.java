package com.flurry.sdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/* renamed from: com.flurry.sdk.ei */
public final class C1752ei {

    /* renamed from: com.flurry.sdk.ei$a */
    public static class C1753a {
        /* renamed from: a */
        public static MessageDigest m898a(String str) {
            try {
                return MessageDigest.getInstance(str);
            } catch (NoSuchAlgorithmException unused) {
                return null;
            }
        }
    }

    /* renamed from: com.flurry.sdk.ei$b */
    public static class C1754b {

        /* renamed from: a */
        private static final Random f1012a = new SecureRandom();

        /* renamed from: b */
        private static final char[] f1013b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

        /* renamed from: a */
        public static String m899a() {
            char[] cArr = new char[32];
            for (int i = 0; i < 32; i++) {
                char[] cArr2 = f1013b;
                cArr[i] = cArr2[f1012a.nextInt(cArr2.length)];
            }
            return new String(cArr);
        }
    }
}
