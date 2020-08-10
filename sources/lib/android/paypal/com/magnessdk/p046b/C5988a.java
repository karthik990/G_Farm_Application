package lib.android.paypal.com.magnessdk.p046b;

import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: lib.android.paypal.com.magnessdk.b.a */
public final class C5988a {

    /* renamed from: a */
    public static final int f4238a = 0;

    /* renamed from: b */
    public static final int f4239b = 1;

    /* renamed from: c */
    public static final int f4240c = 2;

    /* renamed from: d */
    public static final int f4241d = 3;

    /* renamed from: e */
    private static final String f4242e = "****MAGNES DEBUGGING MESSAGE****";

    /* renamed from: f */
    private static boolean f4243f = Boolean.valueOf(System.getProperty("magnes.debug.mode", Boolean.FALSE.toString())).booleanValue();

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: lib.android.paypal.com.magnessdk.b.a$a */
    @interface C2390a {
    }

    private C5988a() {
    }

    /* renamed from: a */
    public static void m4031a(Class<?> cls, int i, String str) {
        boolean z = f4243f;
        if (z) {
            String str2 = "****MAGNES DEBUGGING MESSAGE**** : ";
            if (i == 0) {
                String simpleName = cls.getSimpleName();
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str);
                Log.d(simpleName, sb.toString());
            } else if (i == 1) {
                String simpleName2 = cls.getSimpleName();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(str);
                Log.i(simpleName2, sb2.toString());
            } else if (i == 2) {
                String simpleName3 = cls.getSimpleName();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(str);
                Log.w(simpleName3, sb3.toString());
            } else if (i == 3 && z) {
                String simpleName4 = cls.getSimpleName();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str2);
                sb4.append(str);
                Log.e(simpleName4, sb4.toString());
            }
        }
    }

    /* renamed from: a */
    public static void m4032a(Class<?> cls, int i, Throwable th) {
        boolean z = f4243f;
        if (z) {
            String str = "****MAGNES DEBUGGING MESSAGE**** : ";
            if (i == 0) {
                String simpleName = cls.getSimpleName();
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(th.getMessage());
                Log.d(simpleName, sb.toString(), th);
            } else if (i == 1) {
                String simpleName2 = cls.getSimpleName();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(th.getMessage());
                Log.i(simpleName2, sb2.toString(), th);
            } else if (i == 2) {
                String simpleName3 = cls.getSimpleName();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(th.getMessage());
                Log.w(simpleName3, sb3.toString(), th);
            } else if (i == 3 && z) {
                String simpleName4 = cls.getSimpleName();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(th.getMessage());
                Log.e(simpleName4, sb4.toString(), th);
            }
        }
    }
}
