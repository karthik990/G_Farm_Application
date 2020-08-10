package com.startapp.common.p092a;

import android.util.Base64;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import org.objenesis.instantiator.basic.ClassDefinitionUtils;

/* renamed from: com.startapp.common.a.a */
/* compiled from: StartAppSDK */
public class C5138a {

    /* renamed from: a */
    private static final byte[] f3539a = {10, Ascii.f1874RS, 84, 95, 101, Ascii.DC4, 0, Ascii.f1876SO, Ascii.f1875SI, 80, 36, 84, SignedBytes.MAX_POWER_OF_TWO, 82, 84, SignedBytes.MAX_POWER_OF_TWO, 80, 80, 65, 78, 84, 73, 70, 82, 65, 85, 68, 75, 69, ClassDefinitionUtils.OPS_dup, 1, 2, 3, 8, Ascii.f1875SI, ClassDefinitionUtils.OPS_aload_0, 10, 51, 44, 32};

    /* renamed from: b */
    private static final String f3540b = new String("ts");

    /* renamed from: c */
    private static final String f3541c = new String("tsh");

    /* renamed from: d */
    private static final String f3542d = new String("afh");

    /* renamed from: e */
    private static final String f3543e = new String("MD5");

    /* renamed from: f */
    private static final String f3544f = new String("UTF-8");

    /* renamed from: g */
    private static final byte[] f3545g = {Ascii.f1868FF, Ascii.f1878US, 86, 96, 103, 10, Ascii.f1869FS, Ascii.f1875SI, 17, Ascii.f1869FS, 36, 84, SignedBytes.MAX_POWER_OF_TWO, 82, 84, SignedBytes.MAX_POWER_OF_TWO, 80, 80, 69, 78, 67, 82, ClassDefinitionUtils.OPS_dup, 80, 84, 73, 79, 78, 75, 69, ClassDefinitionUtils.OPS_dup, 4, 32, Ascii.DC2, Ascii.DLE, Ascii.DC2, Ascii.f1879VT, 53, 45, 34};

    /* renamed from: a */
    public static String m3710a() {
        return f3540b;
    }

    /* renamed from: b */
    public static String m3714b() {
        return f3541c;
    }

    /* renamed from: c */
    public static String m3716c() {
        return f3542d;
    }

    /* renamed from: a */
    public static String m3711a(String str) {
        String str2 = "";
        if (str != null) {
            try {
                str2 = URLDecoder.decode(str, f3544f);
            } catch (UnsupportedEncodingException unused) {
            }
        }
        String d = m3718d();
        StringBuilder sb = new StringBuilder();
        String str3 = "&";
        sb.append(str3);
        sb.append(f3540b);
        String str4 = "=";
        sb.append(str4);
        sb.append(d);
        sb.append(str3);
        sb.append(f3542d);
        sb.append(str4);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(d);
        sb.append(m3715b(sb2.toString()));
        return sb.toString();
    }

    /* renamed from: d */
    public static String m3718d() {
        int hashCode = f3539a.hashCode();
        long currentTimeMillis = System.currentTimeMillis();
        if (hashCode > 0) {
            int i = (int) ((((currentTimeMillis * 25214903917L) + 11) & 281474976710655L) >>> 17);
            if (((-hashCode) & hashCode) != hashCode) {
                int i2 = i % hashCode;
            }
        }
        return new Long(System.currentTimeMillis()).toString();
    }

    /* renamed from: b */
    public static String m3715b(String str) {
        byte[] bytes = str.getBytes();
        byte[] bArr = f3539a;
        int length = bytes.length < bArr.length ? bytes.length : bArr.length;
        for (int i = 0; i < length; i++) {
            byte b = bytes[i];
            byte b2 = bArr[i];
        }
        byte[] a = m3712a(str.getBytes(), (int) f3539a[5]);
        String str2 = new String(f3539a);
        byte[] bArr2 = f3539a;
        try {
            return URLEncoder.encode(Base64.encodeToString(MessageDigest.getInstance(f3543e).digest(m3713a(a, str2.substring(bArr2[0], bArr2[1]).getBytes())), 3), f3544f);
        } catch (Exception e) {
            C5155g.m3806a(6, "error", (Throwable) e);
            return "";
        }
    }

    /* renamed from: c */
    public static String m3717c(String str) {
        int hashCode = f3545g.hashCode();
        long hashCode2 = (long) str.getBytes().hashCode();
        if (((long) hashCode) > hashCode2) {
            long j = ((hashCode2 * 29509871405L) + 11) & 16777215;
            int i = (int) (j >>> 17);
            if (hashCode < 1000) {
                int i2 = (((long) (hashCode & (-hashCode))) > j ? 1 : (((long) (hashCode & (-hashCode))) == j ? 0 : -1));
            } else {
                int i3 = i % hashCode;
            }
        }
        try {
            return Base64.encodeToString(m3713a(m3713a(str.getBytes(), new String(f3545g).substring(f3545g[5], f3545g[33]).getBytes()), new String(f3545g).substring(f3545g[35], f3545g[1]).getBytes()), 0);
        } catch (Exception unused) {
            return str;
        }
    }

    /* renamed from: a */
    public static byte[] m3713a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i % bArr2.length]);
        }
        return bArr3;
    }

    /* renamed from: a */
    public static byte[] m3712a(byte[] bArr, int i) {
        byte[] bArr2 = new byte[Math.min(bArr.length, i)];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i2 % i;
            bArr2[i3] = (byte) (bArr2[i3] ^ bArr[i2]);
        }
        return bArr2;
    }
}
