package com.startapp.p054a.p055a.p060e;

import com.google.common.base.Ascii;

/* renamed from: com.startapp.a.a.e.c */
/* compiled from: StartAppSDK */
class C4714c {

    /* renamed from: a */
    private static final char[] f2420a = "0123456789abcdef".toCharArray();

    C4714c() {
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public byte[] mo61121a(String str) {
        if (m2118b(str)) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        int length = str.length();
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    /* renamed from: b */
    private boolean m2118b(String str) {
        return str.length() % 2 != 0;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public String mo61120a(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            char[] cArr2 = f2420a;
            cArr[i2] = cArr2[(bArr[i] & 240) >>> 4];
            cArr[i2 + 1] = cArr2[bArr[i] & Ascii.f1875SI];
        }
        return new String(cArr);
    }
}
