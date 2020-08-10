package lib.android.paypal.com.magnessdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import androidx.core.p012os.EnvironmentCompat;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import lib.android.paypal.com.magnessdk.p100a.C5985b;
import org.json.JSONException;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.common.CommonUtils;

/* renamed from: lib.android.paypal.com.magnessdk.e */
final class C5991e extends C5990d {

    /* renamed from: aL */
    private static C5991e f4331aL;

    /* renamed from: aJ */
    String f4332aJ;

    /* renamed from: aK */
    String f4333aK;

    /* renamed from: aM */
    private String f4334aM;

    /* renamed from: aN */
    private String f4335aN;

    /* renamed from: aO */
    private String f4336aO;

    /* renamed from: aP */
    private String f4337aP;

    /* renamed from: aQ */
    private String f4338aQ;

    /* renamed from: aR */
    private String f4339aR;

    /* renamed from: aS */
    private String f4340aS;

    /* renamed from: aT */
    private String f4341aT;

    /* renamed from: aU */
    private String f4342aU;

    /* renamed from: aV */
    private String f4343aV;

    /* renamed from: aW */
    private String f4344aW;

    /* renamed from: aX */
    private String f4345aX;

    /* renamed from: aY */
    private String f4346aY = "Android";

    /* renamed from: aZ */
    private int f4347aZ = -1;

    /* renamed from: ba */
    private boolean f4348ba;

    /* renamed from: bb */
    private boolean f4349bb;

    /* renamed from: bc */
    private boolean f4350bc;

    /* renamed from: bd */
    private boolean f4351bd;

    /* renamed from: be */
    private long f4352be;

    /* renamed from: bf */
    private long f4353bf;

    /* renamed from: bg */
    private long f4354bg;

    /* renamed from: bh */
    private JSONObject f4355bh;

    /* renamed from: lib.android.paypal.com.magnessdk.e$a */
    static final class C5992a {
        private C5992a() {
        }

        /* renamed from: a */
        static boolean m4059a() {
            return m4060b() || m4061c() || m4062d() || m4063e() || m4064f() || m4065g() || m4066h() || m4067i();
        }

        /* renamed from: b */
        private static boolean m4060b() {
            return Build.MANUFACTURER.equals(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MANUFACTURER.equals("Genymotion") || Build.MANUFACTURER.contains("AndyOS");
        }

        /* renamed from: c */
        private static boolean m4061c() {
            return Build.BRAND.equals("generic") || Build.BRAND.equals("generic_x86") || Build.BRAND.equals("Android") || Build.BRAND.equals("AndyOS");
        }

        /* renamed from: d */
        private static boolean m4062d() {
            return Build.DEVICE.equals("AndyOSX") || Build.DEVICE.equals("Droid4X") || Build.DEVICE.equals("generic") || Build.DEVICE.equals("generic_x86") || Build.DEVICE.equals("vbox86p");
        }

        /* renamed from: e */
        private static boolean m4063e() {
            return Build.HARDWARE.equals("goldfish") || Build.HARDWARE.equals("vbox86") || Build.HARDWARE.equals("andy");
        }

        /* renamed from: f */
        private static boolean m4064f() {
            return Build.MODEL.equals(CommonUtils.SDK) || Build.MODEL.equals(CommonUtils.GOOGLE_SDK) || Build.MODEL.equals("Android SDK built for x86");
        }

        /* renamed from: g */
        private static boolean m4065g() {
            return Build.FINGERPRINT.startsWith("generic");
        }

        /* renamed from: h */
        private static boolean m4066h() {
            return Build.PRODUCT.matches(".*_?sdk_?.*") || Build.PRODUCT.equals("vbox86p") || Build.PRODUCT.equals("Genymotion") || Build.PRODUCT.equals("Driod4X") || Build.PRODUCT.equals("AndyOSX");
        }

        /* renamed from: i */
        private static boolean m4067i() {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().toString());
            sb.append(File.separatorChar);
            sb.append("windows");
            sb.append(File.separatorChar);
            sb.append("BstSharedFolder");
            return new File(sb.toString()).exists();
        }
    }

    /* renamed from: lib.android.paypal.com.magnessdk.e$b */
    static final class C5993b {
        private C5993b() {
        }

        /* renamed from: a */
        private static String m4068a(String str) {
            Properties properties = new Properties();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(C5985b.f4235h.getBytes("UTF-8"));
            try {
                properties.load(byteArrayInputStream);
            } catch (Exception e) {
                C5988a.m4032a(C5993b.class, 3, (Throwable) e);
            } catch (Throwable th) {
                byteArrayInputStream.close();
                throw th;
            }
            byteArrayInputStream.close();
            return properties.getProperty(str);
        }

        /* renamed from: a */
        static boolean m4069a() {
            return m4071c() || m4070b() || m4072d();
        }

        /* renamed from: b */
        private static boolean m4070b() {
            try {
                return new File(m4068a("suFileName")).exists();
            } catch (Exception e) {
                C5988a.m4032a(C5993b.class, 3, (Throwable) e);
                return false;
            }
        }

        /* renamed from: c */
        private static boolean m4071c() {
            return Build.TAGS != null && Build.TAGS.contains("test-keys");
        }

        /* renamed from: d */
        private static boolean m4072d() {
            try {
                return new File(m4068a("superUserApk")).exists();
            } catch (Exception e) {
                C5988a.m4032a(C5993b.class, 3, (Throwable) e);
                return false;
            }
        }
    }

    private C5991e() {
    }

    /* renamed from: b */
    private String m4044b(Context context) {
        return Secure.getString(context.getContentResolver(), C5985b.f4233f);
    }

    /* renamed from: b */
    private String m4045b(Context context, String str) {
        String str2 = C5985b.f4230c;
        SharedPreferences sharedPreferences = context.getSharedPreferences(str2, 0);
        String str3 = "";
        String string = sharedPreferences.getString(str2, str3);
        Editor edit = sharedPreferences.edit();
        if (str == null || str.equals(string)) {
            if (!string.equals(str3)) {
                return string;
            }
            str = C5989c.m4035a(Boolean.TRUE.booleanValue());
        }
        edit.putString(str2, str);
        edit.apply();
        return str;
    }

    /* renamed from: b */
    static C5991e m4046b() {
        if (f4331aL == null) {
            f4331aL = new C5991e();
            C5988a.m4031a(C5991e.class, 0, "creating RiskBlobCoreData instance");
        }
        return f4331aL;
    }

    /* renamed from: c */
    private long m4047c() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
    }

    /* renamed from: c */
    private String m4048c(Context context) {
        Uri uri;
        try {
            uri = Uri.parse("content://com.google.android.gsf.gservices");
        } catch (Exception unused) {
            uri = null;
        }
        if (uri == null || !mo72542a(context, "com.google.android.providers.gsf.permission.READ_GSERVICES")) {
            return null;
        }
        Cursor query = context.getContentResolver().query(uri, null, null, new String[]{C5985b.f4233f}, null);
        if (query == null) {
            return null;
        }
        try {
            if (query.moveToFirst()) {
                if (query.getColumnCount() >= 2) {
                    String hexString = Long.toHexString(Long.parseLong(query.getString(1)));
                    query.close();
                    return hexString;
                }
            }
            return null;
        } catch (NumberFormatException e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            return null;
        } finally {
            query.close();
        }
    }

    /* renamed from: d */
    private String m4049d(Context context) {
        WifiInfo connectionInfo = mo72542a(context, "android.permission.ACCESS_WIFI_STATE") ? ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo() : null;
        if (connectionInfo == null) {
            return null;
        }
        return connectionInfo.getMacAddress();
    }

    /* renamed from: e */
    private JSONObject m4050e(Context context) {
        String str = C5985b.f4231d;
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        String str2 = "";
        String string = sharedPreferences.getString(str, str2);
        String str3 = C5985b.f4232e;
        long j = sharedPreferences.getLong(str3, 0);
        if (string.equals(str2) && j == 0) {
            string = C5989c.m4035a(Boolean.TRUE.booleanValue());
            j = System.currentTimeMillis();
            Editor edit = sharedPreferences.edit();
            edit.putString(str, string);
            edit.putLong(str3, j);
            edit.apply();
        }
        HashMap hashMap = new HashMap();
        hashMap.put(TtmlNode.ATTR_ID, string);
        StringBuilder sb = new StringBuilder();
        sb.append(j);
        sb.append(str2);
        hashMap.put("created_at", sb.toString());
        return mo72544a(hashMap);
    }

    /* renamed from: f */
    private String m4051f(Context context) {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }

    /* renamed from: g */
    private long m4052g(Context context) {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
    }

    /* renamed from: h */
    private long m4053h(Context context) {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
    }

    /* renamed from: a */
    public JSONObject mo72538a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("app_guid", this.f4332aJ);
            jSONObject.put("app_id", this.f4333aK);
            jSONObject.put(C5985b.f4233f, this.f4338aQ);
            jSONObject.put("app_version", this.f4334aM);
            jSONObject.put("app_first_install_time", this.f4353bf);
            jSONObject.put("app_last_update_time", this.f4354bg);
            jSONObject.put("conf_url", this.f4344aW);
            jSONObject.put("comp_version", this.f4345aX);
            jSONObject.put("device_model", this.f4335aN);
            jSONObject.put("device_name", this.f4336aO);
            jSONObject.put("gsf_id", this.f4339aR);
            jSONObject.put("is_emulator", this.f4350bc);
            jSONObject.put("is_rooted", this.f4351bd);
            jSONObject.put("os_type", this.f4346aY);
            jSONObject.put("os_version", this.f4337aP);
            jSONObject.put("payload_type", this.f4341aT);
            jSONObject.put("sms_enabled", this.f4349bb);
            jSONObject.put("mac_addrs", this.f4340aS);
            jSONObject.put("magnes_guid", this.f4355bh);
            jSONObject.put("magnes_source", this.f4347aZ);
            jSONObject.put("notif_token", this.f4343aV);
            jSONObject.put("source_app_version", this.f4342aU);
            jSONObject.put("total_storage_space", this.f4352be);
            return jSONObject;
        } catch (JSONException e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            return jSONObject;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72539a(Context context) {
        return mo72543a(context, false);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72543a(Context context, boolean z) {
        if (!this.f4348ba) {
            C5988a.m4031a(getClass(), 0, "collecting RiskBlobCoreData");
            mo72541a(1, context);
            mo72541a(2, context);
            mo72541a(3, context);
            mo72541a(65, context);
            mo72541a(66, context);
            mo72541a(69, context);
            mo72541a(8, context);
            mo72541a(9, context);
            mo72541a(14, context);
            mo72541a(15, context);
            mo72541a(70, context);
            mo72541a(59, context);
            mo72541a(60, context);
            mo72541a(32, context);
            mo72541a(86, context);
            mo72541a(62, context);
            mo72541a(34, context);
            mo72541a(37, context);
            mo72541a(38, context);
            mo72541a(63, context);
            mo72541a(47, context);
            mo72541a(52, context);
            this.f4348ba = !z;
        }
        return mo72538a();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public JSONObject mo72544a(HashMap<String, String> hashMap) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"id\":");
            sb.append((String) hashMap.get(TtmlNode.ATTR_ID));
            sb.append(",\"created_at\":");
            sb.append((String) hashMap.get("created_at"));
            sb.append("}");
            return new JSONObject(sb.toString());
        } catch (JSONException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo72541a(int i, Context context) {
        if (i == 1) {
            this.f4332aJ = m4045b(context, MagnesSDK.getInstance().f4211b.getAppGuid());
        } else if (i == 2) {
            this.f4333aK = context.getPackageName();
        } else if (i == 3) {
            this.f4334aM = m4051f(context);
        } else if (i == 8) {
            this.f4345aX = C5983a.f4219f;
        } else if (i == 9) {
            this.f4344aW = "https://www.paypalobjects.com/digitalassets/c/rda-magnes/magnes_config_android_v4.json";
        } else if (i == 14) {
            this.f4335aN = Build.MODEL;
        } else if (i == 15) {
            this.f4336aO = Build.DEVICE;
        } else if (i == 32) {
            this.f4340aS = m4049d(context);
        } else if (i == 34) {
            this.f4343aV = MagnesSDK.getInstance().f4211b.getNotificationToken();
        } else if (i == 47) {
            this.f4349bb = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        } else if (i == 52) {
            this.f4352be = m4047c();
        } else if (i == 86) {
            this.f4355bh = m4050e(context);
        } else if (i == 37) {
            this.f4337aP = VERSION.RELEASE;
        } else if (i == 38) {
            this.f4341aT = C5985b.f4234g;
        } else if (i == 59) {
            this.f4350bc = C5992a.m4059a();
        } else if (i == 60) {
            this.f4351bd = C5993b.m4069a();
        } else if (i == 62) {
            this.f4347aZ = MagnesSDK.getInstance().f4211b.getMagnesSource();
        } else if (i == 63) {
            this.f4342aU = m4051f(context);
        } else if (i == 65) {
            this.f4353bf = m4053h(context);
        } else if (i == 66) {
            this.f4354bg = m4052g(context);
        } else if (i == 69) {
            this.f4338aQ = m4044b(context);
        } else if (i == 70) {
            try {
                this.f4339aR = m4048c(context);
            } catch (Exception e) {
                C5988a.m4032a(getClass(), 3, (Throwable) e);
            }
        }
    }
}
