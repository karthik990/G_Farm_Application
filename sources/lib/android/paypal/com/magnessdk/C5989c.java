package lib.android.paypal.com.magnessdk;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Base64;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lib.android.paypal.com.magnessdk.p046b.C5988a;

/* renamed from: lib.android.paypal.com.magnessdk.c */
public final class C5989c {
    private C5989c() {
    }

    /* renamed from: a */
    static <T> T m4033a(Object obj, Class<T> cls) {
        if (obj == null || !cls.isAssignableFrom(obj.getClass())) {
            return null;
        }
        return cls.cast(obj);
    }

    /* renamed from: a */
    static String m4034a(String str) {
        return new String(Base64.decode(str, 2), "UTF-8");
    }

    /* renamed from: a */
    static String m4035a(boolean z) {
        return z ? UUID.randomUUID().toString() : UUID.randomUUID().toString().replaceAll("-", "");
    }

    /* renamed from: a */
    public static void m4036a(Class<?> cls, Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                C5988a.m4032a(cls.getClass(), 3, (Throwable) e);
            }
        }
    }

    /* renamed from: a */
    static boolean m4037a(PackageManager packageManager, Intent intent) {
        List queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        return queryIntentActivities != null && queryIntentActivities.size() > 0;
    }

    /* renamed from: a */
    static boolean m4038a(Object obj) {
        boolean z = true;
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }
        if (obj instanceof Long) {
            if (((Long) obj).longValue() != 0) {
                z = false;
            }
            return z;
        }
        if ((obj instanceof Integer) && ((Integer) obj).intValue() != 0) {
            z = false;
        }
        return z;
    }
}
