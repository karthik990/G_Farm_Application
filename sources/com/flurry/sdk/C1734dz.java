package com.flurry.sdk;

import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import com.google.common.base.Ascii;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/* renamed from: com.flurry.sdk.dz */
public final class C1734dz {
    /* renamed from: a */
    public static void m869a() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Must be called from a background thread!");
        }
    }

    /* renamed from: a */
    public static String m867a(String str) {
        if (str == null) {
            return "";
        }
        if (str.length() <= 255) {
            return str;
        }
        return str.substring(0, 255);
    }

    /* renamed from: a */
    public static void m871a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: b */
    public static String m874b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), 2);
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder("Unsupported UTF-8: ");
            sb.append(e.getMessage());
            C1685cy.m754a(5, "GeneralUtil", sb.toString());
            return "";
        }
    }

    /* renamed from: a */
    public static String m868a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (byte b : bArr) {
            byte b2 = (byte) (b & Ascii.f1875SI);
            sb.append(cArr[(byte) ((b & 240) >> 4)]);
            sb.append(cArr[b2]);
        }
        return sb.toString();
    }

    /* renamed from: c */
    public static byte[] m875c(String str) {
        byte[] bArr = new byte[(str.length() / 2)];
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i += 2) {
            StringBuilder sb = new StringBuilder(2);
            sb.append(charArray[i]);
            sb.append(charArray[i + 1]);
            bArr[i / 2] = (byte) Integer.parseInt(sb.toString(), 16);
        }
        return bArr;
    }

    /* renamed from: a */
    public static boolean m872a(int i) {
        return VERSION.SDK_INT >= i;
    }

    /* renamed from: d */
    public static String m876d(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            StringBuilder sb = new StringBuilder("Cannot decode '");
            sb.append(str);
            sb.append("'");
            C1685cy.m754a(5, "GeneralUtil", sb.toString());
            return "";
        }
    }

    /* renamed from: e */
    public static long m877e(String str) {
        if (str == null) {
            return 0;
        }
        long j = 1125899906842597L;
        for (int i = 0; i < str.length(); i++) {
            j = (j * 31) + ((long) str.charAt(i));
        }
        return j;
    }

    /* renamed from: a */
    public static long m866a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read < 0) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    /* renamed from: a */
    public static boolean m873a(String str, String str2) {
        String str3 = "12.3.0";
        if (str2.equals(str3)) {
            return true;
        }
        StringBuilder sb = new StringBuilder("Flurry version mismatch detected: ");
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        sb.append(", flurryAnalytics: ");
        sb.append(str3);
        String str4 = "GeneralUtil";
        C1685cy.m769e(str4, sb.toString());
        C1685cy.m769e(str4, "Please use same version name for all flurry modules");
        return false;
    }

    /* renamed from: a */
    public static void m870a(int i, String str, String str2, boolean z, boolean z2) {
        String a = C1948n.m1229a().f1425k.mo16243a();
        long b = C1775fe.m939b("last_streaming_session_id", Long.MIN_VALUE);
        HashMap hashMap = new HashMap();
        hashMap.put("data.source", z ? "streaming" : "legacy");
        hashMap.put("status.code", String.valueOf(i));
        hashMap.put("message", str);
        hashMap.put("report.identifier", str2);
        hashMap.put("current.streaming.session.id", a);
        if (b != Long.MIN_VALUE) {
            hashMap.put("last.streaming.session.id", String.valueOf(b));
        }
        C1588be.m517a();
        StringBuilder sb = new StringBuilder("Server Error in ");
        sb.append(z2 ? "current session" : "previous session");
        C1588be.m519a(sb.toString(), hashMap);
    }
}
