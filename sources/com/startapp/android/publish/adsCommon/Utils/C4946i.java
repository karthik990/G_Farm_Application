package com.startapp.android.publish.adsCommon.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.startapp.android.publish.GeneratedConstants;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.C5186e;
import com.startapp.common.p042c.C5180b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/* renamed from: com.startapp.android.publish.adsCommon.Utils.i */
/* compiled from: StartAppSDK */
public class C4946i {

    /* renamed from: a */
    protected static int f3054a;

    /* renamed from: b */
    private static Map<Activity, Integer> f3055b = new WeakHashMap();

    /* renamed from: c */
    private static boolean f3056c = false;

    /* renamed from: com.startapp.android.publish.adsCommon.Utils.i$a */
    /* compiled from: StartAppSDK */
    public interface C4951a {
        /* renamed from: a */
        void mo62044a();

        /* renamed from: a */
        void mo62045a(String str);
    }

    /* renamed from: d */
    public static String m2933d() {
        return GeneratedConstants.INAPP_PACKAGING;
    }

    /* renamed from: a */
    public static boolean m2922a() {
        return new BigInteger(AdsConstants.f3025i, 10).intValue() == 0;
    }

    /* renamed from: b */
    public static String m2928b() {
        StringBuilder sb = new StringBuilder();
        sb.append("SDK version: [");
        String str = GeneratedConstants.INAPP_VERSION;
        sb.append(str);
        sb.append("]");
        C5155g.m3805a(3, sb.toString());
        return str;
    }

    /* renamed from: c */
    public static String m2931c() {
        StringBuilder sb = new StringBuilder();
        sb.append("SDK Flavor: [");
        String str = GeneratedConstants.INAPP_FLAVOR;
        sb.append(str);
        sb.append("]");
        C5155g.m3805a(3, sb.toString());
        return str;
    }

    /* renamed from: a */
    public static boolean m2923a(long j) {
        String str = AdsConstants.f3025i;
        if (str.equals("${flavor}") || (j & new BigInteger(str, 2).longValue()) != 0) {
            return true;
        }
        return false;
    }

    /* renamed from: e */
    public static boolean m2935e() {
        return m2923a(2) || m2923a(16) || m2923a(32) || m2923a(4);
    }

    /* renamed from: a */
    public static String m2907a(Double d) {
        if (d == null) {
            return null;
        }
        return String.format(Locale.US, "%.2f", new Object[]{d});
    }

    /* renamed from: a */
    public static boolean m2924a(Context context) {
        if (AdsConstants.OVERRIDE_HOST != null || AdsConstants.OVERRIDE_NETWORK.booleanValue()) {
            return true;
        }
        if (C5146c.m3760a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                C5017f.m3256a(context, C5015d.EXCEPTION, "Util.isNetworkAvailable - system service failed", e.getMessage(), "");
            }
        }
        return false;
    }

    /* renamed from: a */
    public static void m2916a(Editor editor) {
        C5146c.m3751a(editor);
    }

    /* renamed from: a */
    public static String m2908a(String str, String str2, String str3) {
        if (!(str == null || str2 == null || str3 == null)) {
            int indexOf = str.indexOf(str2);
            if (indexOf != -1) {
                int indexOf2 = str.indexOf(str3, str2.length() + indexOf);
                if (indexOf2 != -1) {
                    return str.substring(indexOf + str2.length(), indexOf2);
                }
            }
        }
        return null;
    }

    /* renamed from: b */
    public static String m2929b(Context context) {
        if (context.getResources().getConfiguration().orientation == 2) {
            return "landscape";
        }
        return context.getResources().getConfiguration().orientation == 1 ? "portrait" : "undefined";
    }

    /* renamed from: a */
    public static int m2901a(Activity activity, int i, boolean z) {
        if (z) {
            if (!f3055b.containsKey(activity)) {
                f3055b.put(activity, Integer.valueOf(activity.getRequestedOrientation()));
            }
            if (i == activity.getResources().getConfiguration().orientation) {
                return C5146c.m3742a(activity, i, false);
            }
            return C5146c.m3742a(activity, i, true);
        }
        int i2 = -1;
        if (f3055b.containsKey(activity)) {
            i2 = ((Integer) f3055b.get(activity)).intValue();
            C5146c.m3750a(activity, i2);
            f3055b.remove(activity);
        }
        return i2;
    }

    /* renamed from: a */
    public static void m2911a(Activity activity, boolean z) {
        m2901a(activity, activity.getResources().getConfiguration().orientation, z);
    }

    /* renamed from: a */
    private static List<Field> m2909a(Class cls) {
        return m2910a((List<Field>) new LinkedList<Field>(), cls);
    }

    /* renamed from: a */
    private static List<Field> m2910a(List<Field> list, Class<?> cls) {
        list.addAll(Arrays.asList(cls.getDeclaredFields()));
        if (cls.getSuperclass() != null) {
            m2910a(list, cls.getSuperclass());
        }
        return list;
    }

    /* renamed from: a */
    public static <T> boolean m2927a(T t, T t2) {
        boolean z = false;
        try {
            for (Field field : m2909a(t2.getClass())) {
                int modifiers = field.getModifiers();
                if (!Modifier.isTransient(modifiers)) {
                    if (!Modifier.isStatic(modifiers)) {
                        field.setAccessible(true);
                        if (field.get(t) == null) {
                            Object obj = field.get(t2);
                            if (obj != null) {
                                field.set(t, obj);
                                z = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Util.mergeDefaultValues failed: ");
            sb.append(e.getMessage());
            C5155g.m3805a(3, sb.toString());
        }
        return z;
    }

    /* renamed from: a */
    public static void m2913a(Context context, AdPreferences adPreferences) {
        String a = C5051k.m3339a(context, "shared_prefs_devId", (String) null);
        String a2 = C5051k.m3339a(context, "shared_prefs_appId", (String) null);
        if (adPreferences.getPublisherId() == null) {
            adPreferences.setPublisherId(a);
        }
        if (adPreferences.getProductId() == null) {
            adPreferences.setProductId(a2);
        }
        if (adPreferences.getProductId() == null && !f3056c) {
            f3056c = true;
            Log.e("StartApp", "Integration Error - App ID is missing");
        }
    }

    /* renamed from: a */
    public static void m2915a(Context context, String str, String str2) {
        String str3 = "shared_prefs_devId";
        if (str != null) {
            C5051k.m3346b(context, str3, str.trim());
        } else {
            C5051k.m3346b(context, str3, (String) null);
        }
        C5051k.m3346b(context, "shared_prefs_appId", str2.trim());
    }

    /* renamed from: a */
    public static void m2914a(Context context, String str, final C4951a aVar) {
        String str2 = "@doNotRender@";
        if ("true".equals(m2908a(str, str2, str2))) {
            aVar.mo62044a();
            return;
        }
        try {
            final WebView webView = new WebView(context);
            final Handler handler = new Handler();
            if (AdsConstants.OVERRIDE_NETWORK.booleanValue()) {
                f3054a = 25000;
                webView.getSettings().setBlockNetworkImage(false);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
            } else {
                f3054a = 0;
            }
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView webView, String str) {
                    super.onPageFinished(webView, str);
                    StringBuilder sb = new StringBuilder();
                    sb.append("onPageFinished url=[");
                    sb.append(str);
                    sb.append("]");
                    C5155g.m3807a("StartAppWall.Util", 4, sb.toString());
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            webView.destroy();
                            C5155g.m3807a("StartAppWall.Util", 4, "webview destroyed");
                            aVar.mo62044a();
                        }
                    }, (long) C4946i.f3054a);
                }

                public void onReceivedError(final WebView webView, int i, final String str, String str2) {
                    super.onReceivedError(webView, i, str, str2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("onReceivedError failingUrl=[");
                    sb.append(str2);
                    sb.append("], description=[");
                    sb.append(str);
                    sb.append("]");
                    C5155g.m3807a("StartAppWall.Util", 6, sb.toString());
                    handler.removeCallbacksAndMessages(null);
                    handler.post(new Runnable() {
                        public void run() {
                            webView.destroy();
                            aVar.mo62045a(str);
                        }
                    });
                }

                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("shouldOverrideUrlLoading url=[");
                    sb.append(str);
                    sb.append("]");
                    C5155g.m3807a("StartAppWall.Util", 4, sb.toString());
                    return super.shouldOverrideUrlLoading(webView, str);
                }
            });
            m2912a(context, webView, str);
            C5155g.m3807a("StartAppWall.Util", 4, "Data loaded to webview");
            handler.postDelayed(new Runnable() {
                public void run() {
                    webView.destroy();
                    aVar.mo62044a();
                    C5155g.m3807a("StartAppWall.Util", 4, "webview destroyed pos 2");
                }
            }, 25000);
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "Util.loadHtmlToCacheWebView - webview failed", e.getMessage(), "");
            aVar.mo62045a("WebView instantiation Error");
        }
    }

    /* renamed from: a */
    public static void m2912a(Context context, WebView webView, String str) {
        try {
            webView.loadDataWithBaseURL(MetaData.getInstance().getHostForWebview(), str, "text/html", "utf-8", null);
        } catch (Exception e) {
            if (context != null) {
                C5017f.m3256a(context, C5015d.EXCEPTION, "Util.loadDataToWebview failed", e.getMessage(), "");
            }
            C5155g.m3805a(6, "StartAppWall.UtilError while encoding html");
        }
    }

    /* renamed from: c */
    public static String m2932c(Context context) {
        String str = "";
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
            if (resolveActivity == null || resolveActivity.activityInfo == null) {
                return str;
            }
            String str2 = resolveActivity.activityInfo.packageName;
            return str2 != null ? str2.toLowerCase() : str2;
        } catch (Exception unused) {
            return str;
        }
    }

    /* renamed from: a */
    public static boolean m2926a(AdPreferences adPreferences, String str) {
        Object a = m2904a(adPreferences.getClass(), str, (Object) adPreferences);
        if (a == null || !(a instanceof Boolean)) {
            return false;
        }
        return ((Boolean) a).booleanValue();
    }

    /* renamed from: b */
    public static String m2930b(AdPreferences adPreferences, String str) {
        Object a = m2904a(adPreferences.getClass(), str, (Object) adPreferences);
        if (a == null || !(a instanceof String)) {
            return null;
        }
        return (String) a;
    }

    /* renamed from: a */
    public static void m2919a(AdPreferences adPreferences, String str, boolean z) {
        m2920a(adPreferences.getClass(), str, (Object) adPreferences, (Object) Boolean.valueOf(z));
    }

    /* renamed from: a */
    public static Object m2904a(Class cls, String str, Object obj) {
        String str2 = "StartAppWall.Util";
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (NoSuchFieldException e) {
            C5155g.m3807a(str2, 6, e.getLocalizedMessage());
            return null;
        } catch (IllegalArgumentException e2) {
            C5155g.m3807a(str2, 6, e2.getLocalizedMessage());
            return null;
        } catch (IllegalAccessException e3) {
            C5155g.m3807a(str2, 6, e3.getLocalizedMessage());
            return null;
        }
    }

    /* renamed from: a */
    public static void m2920a(Class cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, obj2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    /* renamed from: d */
    public static String m2934d(Context context) {
        return context.getPackageManager().getInstallerPackageName(context.getPackageName());
    }

    /* renamed from: a */
    public static void m2917a(WebView webView, String str, Object... objArr) {
        m2918a(webView, true, str, objArr);
    }

    /* renamed from: a */
    public static void m2918a(WebView webView, boolean z, String str, Object... objArr) {
        String str2 = "\"";
        String str3 = "StartAppWall.Util";
        if (webView != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("(");
                if (objArr != null) {
                    for (int i = 0; i < objArr.length; i++) {
                        if (!z || !(objArr[i] instanceof String)) {
                            sb.append(objArr[i]);
                        } else {
                            sb.append(str2);
                            sb.append(objArr[i]);
                            sb.append(str2);
                        }
                        if (i < objArr.length - 1) {
                            sb.append(",");
                        }
                    }
                }
                sb.append(")");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("runJavascript: ");
                sb2.append(sb.toString());
                C5155g.m3807a(str3, 3, sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append("javascript:");
                sb3.append(sb.toString());
                webView.loadUrl(sb3.toString());
            } catch (Exception e) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("runJavascript Exception: ");
                sb4.append(e.getMessage());
                C5155g.m3807a(str3, 6, sb4.toString());
            }
        }
    }

    /* renamed from: a */
    public static Class<?> m2903a(Context context, Class<? extends Activity> cls, Class<? extends Activity> cls2) {
        if (m2925a(context, cls) || !m2925a(context, cls2)) {
            return cls;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected activity ");
        sb.append(cls.getName());
        sb.append(" is missing from AndroidManifest.xml");
        Log.w("StartAppWall.Util", sb.toString());
        return cls2;
    }

    /* renamed from: a */
    public static boolean m2925a(Context context, Class<? extends Activity> cls) {
        try {
            for (ActivityInfo activityInfo : context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities) {
                if (activityInfo.name.equals(cls.getName())) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: e */
    public static boolean m2936e(Context context) {
        for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: f */
    public static boolean m2937f(Context context) {
        try {
            ActivityInfo[] activityInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities;
            boolean z = false;
            int i = 0;
            while (!z) {
                try {
                    if (i >= activityInfoArr.length) {
                        return z;
                    }
                    int i2 = i + 1;
                    ActivityInfo activityInfo = activityInfoArr[i];
                    if (activityInfo.name.equals("com.startapp.android.publish.AppWallActivity") || activityInfo.name.equals("com.startapp.android.publish.adsCommon.OverlayActivity") || activityInfo.name.equals("com.startapp.android.publish.FullScreenActivity")) {
                        z = (activityInfo.flags & 512) == 0;
                    }
                    i = i2;
                } catch (NameNotFoundException | Exception unused) {
                    return z;
                }
            }
            return z;
        } catch (NameNotFoundException | Exception unused2) {
            return false;
        }
    }

    /* renamed from: a */
    public String mo62037a(String str, Context context) {
        try {
            return new C5146c().mo62859a(str, context);
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "Util.getApkHash - system service failed", e.getMessage(), "");
            return null;
        }
    }

    /* renamed from: a */
    public static long m2902a(File file, long j) {
        return C5146c.m3744a(file, j);
    }

    /* renamed from: a */
    public static String m2906a(Context context, int i) {
        try {
            Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), i);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            decodeResource.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: a */
    public static <T> T m2905a(String str, Class<T> cls) {
        T a = C5180b.m3908a(str, cls);
        if (a != null) {
            return a;
        }
        throw new C5186e();
    }

    /* renamed from: a */
    public static void m2921a(Object obj, long j) {
        new Handler(Looper.getMainLooper()).postAtTime(null, obj, SystemClock.uptimeMillis() + j);
    }
}
