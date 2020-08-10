package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import androidx.browser.customtabs.CustomTabsIntent;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.wrappers.Wrappers;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@zzadh
public final class zzakk {
    public static final Handler zzcrm = new zzakc(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public String zzcpq;
    /* access modifiers changed from: private */
    public boolean zzcrn = true;
    private boolean zzcro = false;
    private boolean zzcrp = false;
    private Pattern zzcrq;
    private Pattern zzcrr;

    public static Bundle zza(zzgk zzgk) {
        String str;
        String str2;
        String str3;
        if (zzgk == null) {
            return null;
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzawq)).booleanValue()) {
            if (!((Boolean) zzkb.zzik().zzd(zznk.zzaws)).booleanValue()) {
                return null;
            }
        }
        if (zzbv.zzeo().zzqh().zzqu() && zzbv.zzeo().zzqh().zzqw()) {
            return null;
        }
        if (zzgk.zzha()) {
            zzgk.wakeup();
        }
        zzge zzgy = zzgk.zzgy();
        if (zzgy != null) {
            str3 = zzgy.getSignature();
            str2 = zzgy.zzgo();
            str = zzgy.zzgp();
            if (str3 != null) {
                zzbv.zzeo().zzqh().zzcn(str3);
            }
            if (str != null) {
                zzbv.zzeo().zzqh().zzco(str);
            }
        } else {
            str3 = zzbv.zzeo().zzqh().zzqv();
            str = zzbv.zzeo().zzqh().zzqx();
            str2 = null;
        }
        Bundle bundle = new Bundle(1);
        if (str != null) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzaws)).booleanValue() && !zzbv.zzeo().zzqh().zzqw()) {
                bundle.putString("v_fp_vertical", str);
            }
        }
        if (str3 != null) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzawq)).booleanValue() && !zzbv.zzeo().zzqh().zzqu()) {
                bundle.putString("fingerprint", str3);
                if (!str3.equals(str2)) {
                    bundle.putString("v_fp", str2);
                }
            }
        }
        if (!bundle.isEmpty()) {
            return bundle;
        }
        return null;
    }

    public static DisplayMetrics zza(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static PopupWindow zza(View view, int i, int i2, boolean z) {
        return new PopupWindow(view, i, i2, false);
    }

    public static String zza(Context context, View view, zzjn zzjn) {
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzaxi)).booleanValue()) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(SettingsJsonConstants.ICON_WIDTH_KEY, zzjn.width);
            jSONObject2.put(SettingsJsonConstants.ICON_HEIGHT_KEY, zzjn.height);
            jSONObject.put("size", jSONObject2);
            jSONObject.put("activity", zzap(context));
            if (!zzjn.zzarc) {
                JSONArray jSONArray = new JSONArray();
                while (view != null) {
                    ViewParent parent = view.getParent();
                    if (parent != null) {
                        int i = -1;
                        if (parent instanceof ViewGroup) {
                            i = ((ViewGroup) parent).indexOfChild(view);
                        }
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("type", parent.getClass().getName());
                        jSONObject3.put("index_of_child", i);
                        jSONArray.put(jSONObject3);
                    }
                    view = (parent == null || !(parent instanceof View)) ? null : (View) parent;
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("parents", jSONArray);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            zzakb.zzc("Fail to get view hierarchy json", e);
            return null;
        }
    }

    public static String zza(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder sb = new StringBuilder(8192);
        char[] cArr = new char[2048];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read == -1) {
                return sb.toString();
            }
            sb.append(cArr, 0, read);
        }
    }

    private final JSONArray zza(Collection<?> collection) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object zza : collection) {
            zza(jSONArray, zza);
        }
        return jSONArray;
    }

    public static void zza(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Throwable unused) {
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public static void zza(Context context, Uri uri) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbdy)).booleanValue()) {
                zzb(context, intent);
            }
            bundle.putString("com.android.browser.application_id", context.getPackageName());
            context.startActivity(intent);
            String uri2 = uri.toString();
            StringBuilder sb = new StringBuilder(String.valueOf(uri2).length() + 26);
            sb.append("Opening ");
            sb.append(uri2);
            sb.append(" in a new browser.");
            zzakb.zzck(sb.toString());
        } catch (ActivityNotFoundException e) {
            zzakb.zzb("No browser is found.", e);
        }
    }

    public static void zza(Context context, String str, List<String> list) {
        for (String zzami : list) {
            new zzami(context, str, zzami).zznt();
        }
    }

    public static void zza(Context context, Throwable th) {
        if (context != null) {
            boolean z = false;
            try {
                z = ((Boolean) zzkb.zzik().zzd(zznk.zzaui)).booleanValue();
            } catch (IllegalStateException unused) {
            }
            if (z) {
                CrashUtils.addDynamiteErrorToDropBox(context, th);
            }
        }
    }

    private final void zza(JSONArray jSONArray, Object obj) throws JSONException {
        Object zza;
        if (obj instanceof Bundle) {
            zza = zzf((Bundle) obj);
        } else if (obj instanceof Map) {
            zza = zzn((Map) obj);
        } else if (obj instanceof Collection) {
            zza = zza((Collection) obj);
        } else if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            JSONArray jSONArray2 = new JSONArray();
            for (Object zza2 : objArr) {
                zza(jSONArray2, zza2);
            }
            jSONArray.put(jSONArray2);
            return;
        } else {
            jSONArray.put(obj);
            return;
        }
        jSONArray.put(zza);
    }

    private final void zza(JSONObject jSONObject, String str, Object obj) throws JSONException {
        Collection asList;
        Object zza;
        if (obj instanceof Bundle) {
            zza = zzf((Bundle) obj);
        } else if (obj instanceof Map) {
            zza = zzn((Map) obj);
        } else {
            if (obj instanceof Collection) {
                if (str == null) {
                    str = "null";
                }
                asList = (Collection) obj;
            } else if (obj instanceof Object[]) {
                asList = Arrays.asList((Object[]) obj);
            } else {
                jSONObject.put(str, obj);
                return;
            }
            zza = zza(asList);
        }
        jSONObject.put(str, zza);
    }

    public static boolean zza(Activity activity, Configuration configuration) {
        zzkb.zzif();
        int zza = zzamu.zza((Context) activity, configuration.screenHeightDp);
        int zza2 = zzamu.zza((Context) activity, configuration.screenWidthDp);
        DisplayMetrics zza3 = zza((WindowManager) activity.getApplicationContext().getSystemService("window"));
        int i = zza3.heightPixels;
        int i2 = zza3.widthPixels;
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        int dimensionPixelSize = identifier > 0 ? activity.getResources().getDimensionPixelSize(identifier) : 0;
        double d = (double) activity.getResources().getDisplayMetrics().density;
        Double.isNaN(d);
        int round = ((int) Math.round(d + 0.5d)) * ((Integer) zzkb.zzik().zzd(zznk.zzbek)).intValue();
        return zzb(i, zza + dimensionPixelSize, round) && zzb(i2, zza2, round);
    }

    public static boolean zza(ClassLoader classLoader, Class<?> cls, String str) {
        try {
            return cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean zzaj(Context context) {
        String str;
        boolean z;
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        boolean z2 = false;
        try {
            ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
            if (resolveActivity == null || resolveActivity.activityInfo == null) {
                str = "Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.";
            } else {
                String str2 = "com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".";
                if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
                    zzakb.zzdk(String.format(str2, new Object[]{"keyboard"}));
                    z = false;
                } else {
                    z = true;
                }
                if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
                    zzakb.zzdk(String.format(str2, new Object[]{"keyboardHidden"}));
                    z = false;
                }
                if ((resolveActivity.activityInfo.configChanges & 128) == 0) {
                    zzakb.zzdk(String.format(str2, new Object[]{"orientation"}));
                    z = false;
                }
                if ((resolveActivity.activityInfo.configChanges & 256) == 0) {
                    zzakb.zzdk(String.format(str2, new Object[]{"screenLayout"}));
                    z = false;
                }
                if ((resolveActivity.activityInfo.configChanges & 512) == 0) {
                    zzakb.zzdk(String.format(str2, new Object[]{"uiMode"}));
                    z = false;
                }
                if ((resolveActivity.activityInfo.configChanges & 1024) == 0) {
                    zzakb.zzdk(String.format(str2, new Object[]{"screenSize"}));
                    z = false;
                }
                if ((resolveActivity.activityInfo.configChanges & 2048) == 0) {
                    str = String.format(str2, new Object[]{"smallestScreenSize"});
                } else {
                    z2 = z;
                    return z2;
                }
            }
            zzakb.zzdk(str);
            return z2;
        } catch (Exception e) {
            zzakb.zzc("Could not verify that com.google.android.gms.ads.AdActivity is declared in AndroidManifest.xml", e);
            zzbv.zzeo().zza(e, "AdUtil.hasAdActivity");
            return false;
        }
    }

    protected static String zzam(Context context) {
        try {
            return new WebView(context).getSettings().getUserAgentString();
        } catch (Throwable unused) {
            return zzrg();
        }
    }

    public static Builder zzan(Context context) {
        return new Builder(context);
    }

    public static zzmw zzao(Context context) {
        return new zzmw(context);
    }

    private static String zzap(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            }
            List runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks != null && !runningTasks.isEmpty()) {
                RunningTaskInfo runningTaskInfo = (RunningTaskInfo) runningTasks.get(0);
                if (!(runningTaskInfo == null || runningTaskInfo.topActivity == null)) {
                    return runningTaskInfo.topActivity.getClassName();
                }
            }
            return null;
        } catch (Exception unused) {
        }
    }

    public static boolean zzaq(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager != null) {
                if (keyguardManager != null) {
                    List runningAppProcesses = activityManager.getRunningAppProcesses();
                    if (runningAppProcesses != null) {
                        Iterator it = runningAppProcesses.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) it.next();
                            if (Process.myPid() == runningAppProcessInfo.pid) {
                                if (runningAppProcessInfo.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode()) {
                                    PowerManager powerManager = (PowerManager) context.getSystemService("power");
                                    if (powerManager == null ? false : powerManager.isScreenOn()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static Bitmap zzar(Context context) {
        Bitmap bitmap = null;
        if (!(context instanceof Activity)) {
            return null;
        }
        try {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbh)).booleanValue()) {
                Window window = ((Activity) context).getWindow();
                if (window != null) {
                    bitmap = zzv(window.getDecorView().getRootView());
                }
            } else {
                bitmap = zzu(((Activity) context).getWindow().getDecorView());
            }
        } catch (RuntimeException e) {
            zzakb.zzb("Fail to capture screen shot", e);
        }
        return bitmap;
    }

    public static int zzas(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return 0;
        }
        return applicationInfo.targetSdkVersion;
    }

    private static KeyguardManager zzat(Context context) {
        Object systemService = context.getSystemService("keyguard");
        if (systemService == null || !(systemService instanceof KeyguardManager)) {
            return null;
        }
        return (KeyguardManager) systemService;
    }

    public static boolean zzau(Context context) {
        if (context != null && PlatformVersion.isAtLeastJellyBean()) {
            KeyguardManager zzat = zzat(context);
            if (zzat != null && zzat.isKeyguardLocked()) {
                return true;
            }
        }
        return false;
    }

    public static boolean zzav(Context context) {
        try {
            context.getClassLoader().loadClass("com.google.android.gms.ads.internal.ClientApi");
            return false;
        } catch (ClassNotFoundException unused) {
            return true;
        } catch (Throwable th) {
            zzakb.zzb("Error loading class.", th);
            zzbv.zzeo().zza(th, "AdUtil.isLiteSdk");
            return false;
        }
    }

    public static WebResourceResponse zzb(HttpURLConnection httpURLConnection) throws IOException {
        zzbv.zzek();
        String contentType = httpURLConnection.getContentType();
        String str = "";
        String str2 = ";";
        String trim = TextUtils.isEmpty(contentType) ? str : contentType.split(str2)[0].trim();
        zzbv.zzek();
        String contentType2 = httpURLConnection.getContentType();
        if (!TextUtils.isEmpty(contentType2)) {
            String[] split = contentType2.split(str2);
            if (split.length != 1) {
                int i = 1;
                while (true) {
                    if (i >= split.length) {
                        break;
                    }
                    if (split[i].trim().startsWith(HttpRequest.PARAM_CHARSET)) {
                        String[] split2 = split[i].trim().split("=");
                        if (split2.length > 1) {
                            str = split2[1].trim();
                            break;
                        }
                    }
                    i++;
                }
            }
        }
        String str3 = str;
        Map headerFields = httpURLConnection.getHeaderFields();
        HashMap hashMap = new HashMap(headerFields.size());
        for (Entry entry : headerFields.entrySet()) {
            if (!(entry.getKey() == null || entry.getValue() == null || ((List) entry.getValue()).size() <= 0)) {
                hashMap.put((String) entry.getKey(), (String) ((List) entry.getValue()).get(0));
            }
        }
        return zzbv.zzem().zza(trim, str3, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage(), hashMap, httpURLConnection.getInputStream());
    }

    public static void zzb(Context context, Intent intent) {
        if (intent != null && PlatformVersion.isAtLeastJellyBeanMR2()) {
            Bundle extras = intent.getExtras() != null ? intent.getExtras() : new Bundle();
            extras.putBinder(CustomTabsIntent.EXTRA_SESSION, null);
            extras.putString("com.android.browser.application_id", context.getPackageName());
            intent.putExtras(extras);
        }
    }

    private static boolean zzb(int i, int i2, int i3) {
        return Math.abs(i - i2) <= i3;
    }

    public static String zzcu(String str) {
        return Uri.parse(str).buildUpon().query(null).build().toString();
    }

    public static int zzcv(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
            sb.append("Could not parse value:");
            sb.append(valueOf);
            zzakb.zzdk(sb.toString());
            return 0;
        }
    }

    public static boolean zzcw(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
    }

    public static void zzd(Context context, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        zza(context, str, (List<String>) arrayList);
    }

    public static void zzd(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            runnable.run();
        } else {
            zzaki.zzb(runnable);
        }
    }

    public static void zze(Context context, String str, String str2) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes("UTF-8"));
            openFileOutput.close();
        } catch (Exception e) {
            zzakb.zzb("Error writing to file in internal storage.", e);
        }
    }

    public static WebResourceResponse zzf(Context context, String str, String str2) {
        String str3 = "UTF-8";
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("User-Agent", zzbv.zzek().zzm(context, str));
            hashMap.put("Cache-Control", "max-stale=3600");
            String str4 = (String) new zzalt(context).zzc(str2, hashMap).get(60, TimeUnit.SECONDS);
            if (str4 != null) {
                return new WebResourceResponse("application/javascript", str3, new ByteArrayInputStream(str4.getBytes(str3)));
            }
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            zzakb.zzc("Could not fetch MRAID JS.", e);
        }
        return null;
    }

    private final JSONObject zzf(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            zza(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    public static int[] zzf(Activity activity) {
        Window window = activity.getWindow();
        if (window != null) {
            View findViewById = window.findViewById(16908290);
            if (findViewById != null) {
                return new int[]{findViewById.getWidth(), findViewById.getHeight()};
            }
        }
        return zzrj();
    }

    public static Map<String, String> zzg(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String str : zzbv.zzem().zzh(uri)) {
            hashMap.put(str, uri.getQueryParameter(str));
        }
        return hashMap;
    }

    public static boolean zzl(Context context, String str) {
        return Wrappers.packageManager(context).checkPermission(str, context.getPackageName()) == 0;
    }

    public static String zzn(Context context, String str) {
        try {
            return new String(IOUtils.readInputStreamFully(context.openFileInput(str), true), "UTF-8");
        } catch (IOException unused) {
            zzakb.zzck("Error reading from internal storage.");
            return "";
        }
    }

    private static String zzrg() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("Mozilla/5.0 (Linux; U; Android");
        if (VERSION.RELEASE != null) {
            sb.append(" ");
            sb.append(VERSION.RELEASE);
        }
        String str = "; ";
        sb.append(str);
        sb.append(Locale.getDefault());
        if (Build.DEVICE != null) {
            sb.append(str);
            sb.append(Build.DEVICE);
            if (Build.DISPLAY != null) {
                sb.append(" Build/");
                sb.append(Build.DISPLAY);
            }
        }
        sb.append(") AppleWebKit/533 Version/4.0 Safari/533");
        return sb.toString();
    }

    public static String zzrh() {
        return UUID.randomUUID().toString();
    }

    public static String zzri() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str)) {
            return str2;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        return sb.toString();
    }

    private static int[] zzrj() {
        return new int[]{0, 0};
    }

    public static Bundle zzrk() {
        Bundle bundle = new Bundle();
        try {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzavm)).booleanValue()) {
                MemoryInfo memoryInfo = new MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                bundle.putParcelable("debug_memory_info", memoryInfo);
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzavn)).booleanValue()) {
                Runtime runtime = Runtime.getRuntime();
                bundle.putLong("runtime_free_memory", runtime.freeMemory());
                bundle.putLong("runtime_max_memory", runtime.maxMemory());
                bundle.putLong("runtime_total_memory", runtime.totalMemory());
            }
            bundle.putInt("web_view_count", zzbv.zzeo().zzqg());
        } catch (Exception e) {
            zzakb.zzc("Unable to gather memory stats", e);
        }
        return bundle;
    }

    public static Bitmap zzs(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    public static Bitmap zzt(View view) {
        if (view == null) {
            return null;
        }
        Bitmap zzv = zzv(view);
        if (zzv == null) {
            zzv = zzu(view);
        }
        return zzv;
    }

    private static Bitmap zzu(View view) {
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width != 0) {
                if (height != 0) {
                    Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.RGB_565);
                    Canvas canvas = new Canvas(createBitmap);
                    view.layout(0, 0, width, height);
                    view.draw(canvas);
                    return createBitmap;
                }
            }
            zzakb.zzdk("Width or height of view is zero");
            return null;
        } catch (RuntimeException e) {
            zzakb.zzb("Fail to capture the webview", e);
            return null;
        }
    }

    private static Bitmap zzv(View view) {
        Bitmap bitmap = null;
        try {
            boolean isDrawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            Bitmap drawingCache = view.getDrawingCache();
            if (drawingCache != null) {
                bitmap = Bitmap.createBitmap(drawingCache);
            }
            view.setDrawingCacheEnabled(isDrawingCacheEnabled);
        } catch (RuntimeException e) {
            zzakb.zzb("Fail to capture the web view", e);
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0016 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zzw(android.view.View r2) {
        /*
            android.view.View r2 = r2.getRootView()
            r0 = 0
            if (r2 == 0) goto L_0x0012
            android.content.Context r2 = r2.getContext()
            boolean r1 = r2 instanceof android.app.Activity
            if (r1 == 0) goto L_0x0012
            android.app.Activity r2 = (android.app.Activity) r2
            goto L_0x0013
        L_0x0012:
            r2 = r0
        L_0x0013:
            r1 = 0
            if (r2 != 0) goto L_0x0017
            return r1
        L_0x0017:
            android.view.Window r2 = r2.getWindow()
            if (r2 != 0) goto L_0x001e
            goto L_0x0022
        L_0x001e:
            android.view.WindowManager$LayoutParams r0 = r2.getAttributes()
        L_0x0022:
            if (r0 == 0) goto L_0x002d
            int r2 = r0.flags
            r0 = 524288(0x80000, float:7.34684E-40)
            r2 = r2 & r0
            if (r2 == 0) goto L_0x002d
            r2 = 1
            return r2
        L_0x002d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzakk.zzw(android.view.View):boolean");
    }

    public static int zzx(View view) {
        if (view == null) {
            return -1;
        }
        ViewParent parent = view.getParent();
        while (parent != null && !(parent instanceof AdapterView)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            return -1;
        }
        return ((AdapterView) parent).getPositionForView(view);
    }

    public final JSONObject zza(Bundle bundle, JSONObject jSONObject) {
        if (bundle != null) {
            try {
                return zzf(bundle);
            } catch (JSONException e) {
                zzakb.zzb("Error converting Bundle to JSON", e);
            }
        }
        return null;
    }

    public final void zza(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(zzm(context, str));
    }

    public final void zza(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (z) {
            zzbv.zzek();
            bundle.putString("device", zzri());
            bundle.putString("eids", TextUtils.join(",", zznk.zzjb()));
        }
        zzkb.zzif();
        zzamu.zza(context, str, str2, bundle, z, new zzakn(this, context, str));
    }

    public final void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", zzm(context, str));
        httpURLConnection.setUseCaches(false);
    }

    public final void zza(Context context, List<String> list) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!TextUtils.isEmpty(zzbfw.zzbn(activity))) {
                if (list == null) {
                    zzakb.m1419v("Cannot ping urls: empty list.");
                } else if (!zzoh.zzh(context)) {
                    zzakb.m1419v("Cannot ping url because custom tabs is not supported");
                } else {
                    zzoh zzoh = new zzoh();
                    zzoh.zza((zzoi) new zzakl(this, list, zzoh, context));
                    zzoh.zzd(activity);
                }
            }
        }
    }

    public final boolean zza(View view, Context context) {
        Context applicationContext = context.getApplicationContext();
        return zza(view, applicationContext != null ? (PowerManager) applicationContext.getSystemService("power") : null, zzat(context));
    }

    public final boolean zza(View view, PowerManager powerManager, KeyguardManager keyguardManager) {
        boolean z;
        if (!zzbv.zzek().zzcrn) {
            if (keyguardManager == null ? false : keyguardManager.inKeyguardRestrictedInputMode()) {
                if (!((Boolean) zzkb.zzik().zzd(zznk.zzazu)).booleanValue() || !zzw(view)) {
                    z = false;
                    if (view.getVisibility() == 0 && view.isShown()) {
                        if ((powerManager != null || powerManager.isScreenOn()) && z) {
                            if (((Boolean) zzkb.zzik().zzd(zznk.zzazs)).booleanValue() || view.getLocalVisibleRect(new Rect()) || view.getGlobalVisibleRect(new Rect())) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        z = true;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzazs)).booleanValue()) {
        }
        return true;
    }

    public final boolean zzak(Context context) {
        if (this.zzcro) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver(new zzakp(this, null), intentFilter);
        this.zzcro = true;
        return true;
    }

    public final boolean zzal(Context context) {
        if (this.zzcrp) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.ads.intent.DEBUG_LOGGING_ENABLEMENT_CHANGED");
        context.getApplicationContext().registerReceiver(new zzako(this, null), intentFilter);
        this.zzcrp = true;
        return true;
    }

    public final void zzb(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (((Boolean) zzkb.zzik().zzd(zznk.zzazx)).booleanValue()) {
            zza(context, str, str2, bundle, z);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        if (((java.lang.String) com.google.android.gms.internal.ads.zzkb.zzik().zzd(com.google.android.gms.internal.ads.zznk.zzaxo)).equals(r3.zzcrq.pattern()) == false) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzcx(java.lang.String r4) {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            monitor-enter(r3)     // Catch:{ PatternSyntaxException -> 0x0046 }
            java.util.regex.Pattern r0 = r3.zzcrq     // Catch:{ all -> 0x0043 }
            if (r0 == 0) goto L_0x0025
            com.google.android.gms.internal.ads.zzna<java.lang.String> r0 = com.google.android.gms.internal.ads.zznk.zzaxo     // Catch:{ all -> 0x0043 }
            com.google.android.gms.internal.ads.zzni r2 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ all -> 0x0043 }
            java.lang.Object r0 = r2.zzd(r0)     // Catch:{ all -> 0x0043 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0043 }
            java.util.regex.Pattern r2 = r3.zzcrq     // Catch:{ all -> 0x0043 }
            java.lang.String r2 = r2.pattern()     // Catch:{ all -> 0x0043 }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x0043 }
            if (r0 != 0) goto L_0x0037
        L_0x0025:
            com.google.android.gms.internal.ads.zzna<java.lang.String> r0 = com.google.android.gms.internal.ads.zznk.zzaxo     // Catch:{ all -> 0x0043 }
            com.google.android.gms.internal.ads.zzni r2 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ all -> 0x0043 }
            java.lang.Object r0 = r2.zzd(r0)     // Catch:{ all -> 0x0043 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0043 }
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)     // Catch:{ all -> 0x0043 }
            r3.zzcrq = r0     // Catch:{ all -> 0x0043 }
        L_0x0037:
            java.util.regex.Pattern r0 = r3.zzcrq     // Catch:{ all -> 0x0043 }
            java.util.regex.Matcher r4 = r0.matcher(r4)     // Catch:{ all -> 0x0043 }
            boolean r4 = r4.matches()     // Catch:{ all -> 0x0043 }
            monitor-exit(r3)     // Catch:{ all -> 0x0043 }
            return r4
        L_0x0043:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0043 }
            throw r4     // Catch:{ PatternSyntaxException -> 0x0046 }
        L_0x0046:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzakk.zzcx(java.lang.String):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        if (((java.lang.String) com.google.android.gms.internal.ads.zzkb.zzik().zzd(com.google.android.gms.internal.ads.zznk.zzaxp)).equals(r3.zzcrr.pattern()) == false) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzcy(java.lang.String r4) {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            monitor-enter(r3)     // Catch:{ PatternSyntaxException -> 0x0046 }
            java.util.regex.Pattern r0 = r3.zzcrr     // Catch:{ all -> 0x0043 }
            if (r0 == 0) goto L_0x0025
            com.google.android.gms.internal.ads.zzna<java.lang.String> r0 = com.google.android.gms.internal.ads.zznk.zzaxp     // Catch:{ all -> 0x0043 }
            com.google.android.gms.internal.ads.zzni r2 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ all -> 0x0043 }
            java.lang.Object r0 = r2.zzd(r0)     // Catch:{ all -> 0x0043 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0043 }
            java.util.regex.Pattern r2 = r3.zzcrr     // Catch:{ all -> 0x0043 }
            java.lang.String r2 = r2.pattern()     // Catch:{ all -> 0x0043 }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x0043 }
            if (r0 != 0) goto L_0x0037
        L_0x0025:
            com.google.android.gms.internal.ads.zzna<java.lang.String> r0 = com.google.android.gms.internal.ads.zznk.zzaxp     // Catch:{ all -> 0x0043 }
            com.google.android.gms.internal.ads.zzni r2 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ all -> 0x0043 }
            java.lang.Object r0 = r2.zzd(r0)     // Catch:{ all -> 0x0043 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0043 }
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)     // Catch:{ all -> 0x0043 }
            r3.zzcrr = r0     // Catch:{ all -> 0x0043 }
        L_0x0037:
            java.util.regex.Pattern r0 = r3.zzcrr     // Catch:{ all -> 0x0043 }
            java.util.regex.Matcher r4 = r0.matcher(r4)     // Catch:{ all -> 0x0043 }
            boolean r4 = r4.matches()     // Catch:{ all -> 0x0043 }
            monitor-exit(r3)     // Catch:{ all -> 0x0043 }
            return r4
        L_0x0043:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0043 }
            throw r4     // Catch:{ PatternSyntaxException -> 0x0046 }
        L_0x0046:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzakk.zzcy(java.lang.String):boolean");
    }

    public final int[] zzg(Activity activity) {
        int[] zzf = zzf(activity);
        zzkb.zzif();
        zzkb.zzif();
        return new int[]{zzamu.zzb((Context) activity, zzf[0]), zzamu.zzb((Context) activity, zzf[1])};
    }

    public final int[] zzh(Activity activity) {
        int[] iArr;
        Window window = activity.getWindow();
        if (window != null) {
            View findViewById = window.findViewById(16908290);
            if (findViewById != null) {
                iArr = new int[]{findViewById.getTop(), findViewById.getBottom()};
                zzkb.zzif();
                zzkb.zzif();
                return new int[]{zzamu.zzb((Context) activity, iArr[0]), zzamu.zzb((Context) activity, iArr[1])};
            }
        }
        iArr = zzrj();
        zzkb.zzif();
        zzkb.zzif();
        return new int[]{zzamu.zzb((Context) activity, iArr[0]), zzamu.zzb((Context) activity, iArr[1])};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x003b, code lost:
        continue;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x001d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0045 */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0025 A[Catch:{ Exception -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059 A[Catch:{ Exception -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005e A[Catch:{ Exception -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a7 A[Catch:{ Exception -> 0x00b6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzm(android.content.Context r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            java.lang.String r1 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            if (r1 == 0) goto L_0x000b
            java.lang.String r5 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            return r5
        L_0x000b:
            if (r6 != 0) goto L_0x0013
            java.lang.String r5 = zzrg()     // Catch:{ all -> 0x00d2 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            return r5
        L_0x0013:
            com.google.android.gms.internal.ads.zzakq r1 = com.google.android.gms.ads.internal.zzbv.zzem()     // Catch:{ Exception -> 0x001d }
            java.lang.String r1 = r1.getDefaultUserAgent(r5)     // Catch:{ Exception -> 0x001d }
            r4.zzcpq = r1     // Catch:{ Exception -> 0x001d }
        L_0x001d:
            java.lang.String r1 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00d2 }
            if (r1 == 0) goto L_0x006e
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ all -> 0x00d2 }
            boolean r1 = com.google.android.gms.internal.ads.zzamu.zzsh()     // Catch:{ all -> 0x00d2 }
            if (r1 != 0) goto L_0x0068
            r1 = 0
            r4.zzcpq = r1     // Catch:{ all -> 0x00d2 }
            android.os.Handler r1 = zzcrm     // Catch:{ all -> 0x00d2 }
            com.google.android.gms.internal.ads.zzakm r2 = new com.google.android.gms.internal.ads.zzakm     // Catch:{ all -> 0x00d2 }
            r2.<init>(r4, r5)     // Catch:{ all -> 0x00d2 }
            r1.post(r2)     // Catch:{ all -> 0x00d2 }
        L_0x003b:
            java.lang.String r1 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            if (r1 != 0) goto L_0x006e
            java.lang.Object r1 = r4.mLock     // Catch:{ InterruptedException -> 0x0045 }
            r1.wait()     // Catch:{ InterruptedException -> 0x0045 }
            goto L_0x003b
        L_0x0045:
            java.lang.String r1 = zzrg()     // Catch:{ all -> 0x00d2 }
            r4.zzcpq = r1     // Catch:{ all -> 0x00d2 }
            java.lang.String r1 = "Interrupted, use default user agent: "
            java.lang.String r2 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00d2 }
            int r3 = r2.length()     // Catch:{ all -> 0x00d2 }
            if (r3 == 0) goto L_0x005e
            java.lang.String r1 = r1.concat(r2)     // Catch:{ all -> 0x00d2 }
            goto L_0x0064
        L_0x005e:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00d2 }
            r2.<init>(r1)     // Catch:{ all -> 0x00d2 }
            r1 = r2
        L_0x0064:
            com.google.android.gms.internal.ads.zzakb.zzdk(r1)     // Catch:{ all -> 0x00d2 }
            goto L_0x003b
        L_0x0068:
            java.lang.String r1 = zzam(r5)     // Catch:{ all -> 0x00d2 }
            r4.zzcpq = r1     // Catch:{ all -> 0x00d2 }
        L_0x006e:
            java.lang.String r1 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00d2 }
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00d2 }
            int r2 = r2.length()     // Catch:{ all -> 0x00d2 }
            int r2 = r2 + 10
            java.lang.String r3 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x00d2 }
            int r3 = r3.length()     // Catch:{ all -> 0x00d2 }
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r3.<init>(r2)     // Catch:{ all -> 0x00d2 }
            r3.append(r1)     // Catch:{ all -> 0x00d2 }
            java.lang.String r1 = " (Mobile; "
            r3.append(r1)     // Catch:{ all -> 0x00d2 }
            r3.append(r6)     // Catch:{ all -> 0x00d2 }
            java.lang.String r6 = r3.toString()     // Catch:{ all -> 0x00d2 }
            r4.zzcpq = r6     // Catch:{ all -> 0x00d2 }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r5 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r5)     // Catch:{ Exception -> 0x00b6 }
            boolean r5 = r5.isCallerInstantApp()     // Catch:{ Exception -> 0x00b6 }
            if (r5 == 0) goto L_0x00c0
            java.lang.String r5 = r4.zzcpq     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r6 = ";aia"
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x00b6 }
            r4.zzcpq = r5     // Catch:{ Exception -> 0x00b6 }
            goto L_0x00c0
        L_0x00b6:
            r5 = move-exception
            com.google.android.gms.internal.ads.zzajm r6 = com.google.android.gms.ads.internal.zzbv.zzeo()     // Catch:{ all -> 0x00d2 }
            java.lang.String r1 = "AdUtil.getUserAgent"
            r6.zza(r5, r1)     // Catch:{ all -> 0x00d2 }
        L_0x00c0:
            java.lang.String r5 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x00d2 }
            java.lang.String r6 = ")"
            java.lang.String r5 = r5.concat(r6)     // Catch:{ all -> 0x00d2 }
            r4.zzcpq = r5     // Catch:{ all -> 0x00d2 }
            java.lang.String r5 = r4.zzcpq     // Catch:{ all -> 0x00d2 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            return r5
        L_0x00d2:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            goto L_0x00d6
        L_0x00d5:
            throw r5
        L_0x00d6:
            goto L_0x00d5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzakk.zzm(android.content.Context, java.lang.String):java.lang.String");
    }

    public final JSONObject zzn(Map<String, ?> map) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : map.keySet()) {
                zza(jSONObject, str, map.get(str));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            String str2 = "Could not convert map to JSON: ";
            String valueOf = String.valueOf(e.getMessage());
            throw new JSONException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }
}
