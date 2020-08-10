package com.startapp.common.p092a;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import java.io.FilterInputStream;
import java.io.InputStream;

/* renamed from: com.startapp.common.a.d */
/* compiled from: StartAppSDK */
public class C5150d {

    /* renamed from: com.startapp.common.a.d$a */
    /* compiled from: StartAppSDK */
    static class C5151a extends FilterInputStream {
        public C5151a(InputStream inputStream) {
            super(inputStream);
        }

        public long skip(long j) {
            long j2 = 0;
            while (j2 < j) {
                long skip = this.in.skip(j - j2);
                if (skip == 0) {
                    if (read() < 0) {
                        break;
                    }
                    skip = 1;
                }
                j2 += skip;
            }
            return j2;
        }
    }

    /* renamed from: a */
    public static Bitmap m3783a(String str) {
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    /* renamed from: a */
    public static Drawable m3784a(Resources resources, String str) {
        return new BitmapDrawable(resources, m3783a(str));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003c, code lost:
        if (r5 != null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0027, code lost:
        if (r5 != null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0029, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap m3785b(java.lang.String r5) {
        /*
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Exception -> 0x003b, all -> 0x0031 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x003b, all -> 0x0031 }
            java.net.URLConnection r5 = r1.openConnection()     // Catch:{ Exception -> 0x003b, all -> 0x0031 }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ Exception -> 0x003b, all -> 0x0031 }
            r5.connect()     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            java.io.InputStream r1 = r5.getInputStream()     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            r2.<init>(r1)     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            com.startapp.common.a.d$a r3 = new com.startapp.common.a.d$a     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            r3.<init>(r1)     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r3)     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            r2.close()     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            r1.close()     // Catch:{ Exception -> 0x002f, all -> 0x002d }
            if (r5 == 0) goto L_0x003f
        L_0x0029:
            r5.disconnect()
            goto L_0x003f
        L_0x002d:
            r0 = move-exception
            goto L_0x0035
        L_0x002f:
            goto L_0x003c
        L_0x0031:
            r5 = move-exception
            r4 = r0
            r0 = r5
            r5 = r4
        L_0x0035:
            if (r5 == 0) goto L_0x003a
            r5.disconnect()
        L_0x003a:
            throw r0
        L_0x003b:
            r5 = r0
        L_0x003c:
            if (r5 == 0) goto L_0x003f
            goto L_0x0029
        L_0x003f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.common.p092a.C5150d.m3785b(java.lang.String):android.graphics.Bitmap");
    }
}
