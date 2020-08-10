package lib.android.paypal.com.magnessdk.network;

import android.content.Context;
import android.os.Handler;
import com.startapp.android.publish.common.metaData.MetaData;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lib.android.paypal.com.magnessdk.C5989c;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import lib.android.paypal.com.magnessdk.p100a.C5986c;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: lib.android.paypal.com.magnessdk.network.i */
public final class C6005i {

    /* renamed from: a */
    private static final String f4485a = "CONFIG_TIME";

    /* renamed from: b */
    private static final String f4486b = "4.0";

    /* renamed from: c */
    private static final int f4487c = 1000;

    /* renamed from: d */
    private static final String f4488d = "CONFIG_DATA";

    /* renamed from: e */
    private static final String f4489e = "QW5kcm9pZE1hZ25lcw==";

    /* renamed from: f */
    private static final int f4490f = 3600;

    /* renamed from: g */
    private static final int f4491g = 1800;

    /* renamed from: h */
    private static final int f4492h = 86400;

    /* renamed from: i */
    private static final int f4493i = 500;

    /* renamed from: j */
    private static final int f4494j = 1800;

    /* renamed from: k */
    private static final String f4495k = "https://c.paypal.com/r/v1/device/client-metadata";

    /* renamed from: l */
    private Context f4496l;

    /* renamed from: m */
    private JSONObject f4497m;

    /* renamed from: n */
    private Handler f4498n;

    /* renamed from: o */
    private boolean f4499o = false;

    public C6005i(Context context) {
        this.f4496l = context;
        String f = m4117f();
        if (!f.isEmpty()) {
            mo72563a(f);
            this.f4497m = new JSONObject(f);
            return;
        }
        throw new IOException("no valid remote config found!");
    }

    public C6005i(Context context, Handler handler, boolean z) {
        this.f4496l = context;
        this.f4498n = handler;
        this.f4499o = z;
        this.f4497m = m4115d();
        try {
            C5988a.m4031a(getClass(), 0, this.f4497m.toString(2));
        } catch (JSONException unused) {
        }
    }

    /* renamed from: a */
    private boolean m4113a(String str, String str2) {
        C5988a.m4031a(getClass(), 0, "entering shouldUseCachedConfiguration");
        String str3 = "\\.";
        String[] split = str.split(str3);
        String[] split2 = str2.split(str3);
        Class cls = getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("Comparing Cached version is ");
        sb.append(str);
        sb.append(" default version is ");
        sb.append(str2);
        C5988a.m4031a(cls, 0, sb.toString());
        int i = 0;
        while (i < split.length && i < split2.length && split[i].equals(split2[i])) {
            i++;
        }
        return Integer.valueOf(Integer.signum((i >= split.length || i >= split2.length) ? split.length - split2.length : Integer.valueOf(split[i]).compareTo(Integer.valueOf(split2[i])))).intValue() >= 0;
    }

    /* renamed from: a */
    private boolean m4114a(JSONObject jSONObject) {
        return System.currentTimeMillis() > Long.parseLong(m4122k()) + (jSONObject.optLong("conf_refresh_time_interval", 0) * 1000);
    }

    /* renamed from: d */
    private JSONObject m4115d() {
        try {
            JSONObject g = m4118g();
            if (g == null) {
                m4116e();
            } else if (m4113a(g.optString("conf_version", ""), f4486b)) {
                boolean a = m4114a(g);
                if (!this.f4499o && a) {
                    m4116e();
                }
                Class cls = getClass();
                StringBuilder sb = new StringBuilder();
                sb.append("Using cached config due to isDisableRemoteConfig : ");
                sb.append(this.f4499o);
                sb.append(" or isConfigExpried : ");
                sb.append(a);
                C5988a.m4031a(cls, 0, sb.toString());
                return g;
            } else {
                m4121j();
            }
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        return m4119h();
    }

    /* renamed from: e */
    private void m4116e() {
        C5988a.m4031a(getClass(), 0, "entering fetchRemoteConfig");
        new C6006j(this.f4496l, this.f4498n).mo72559d();
    }

    /* renamed from: f */
    private String m4117f() {
        InputStream inputStream;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            inputStream = new URL("https://www.paypalobjects.com/digitalassets/c/rda-magnes/magnes_config_android_v4.json").openStream();
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine != null) {
                            sb.append(readLine);
                        } else {
                            C5989c.m4036a(getClass(), (Closeable) inputStream);
                            C5989c.m4036a(getClass(), (Closeable) bufferedReader2);
                            C5988a.m4031a(getClass(), 0, "leaving getRemoteConfig successfully");
                            return sb.toString();
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        C5989c.m4036a(getClass(), (Closeable) inputStream);
                        C5989c.m4036a(getClass(), (Closeable) bufferedReader);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                C5989c.m4036a(getClass(), (Closeable) inputStream);
                C5989c.m4036a(getClass(), (Closeable) bufferedReader);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            C5989c.m4036a(getClass(), (Closeable) inputStream);
            C5989c.m4036a(getClass(), (Closeable) bufferedReader);
            throw th;
        }
    }

    /* renamed from: g */
    private JSONObject m4118g() {
        C5988a.m4031a(getClass(), 0, "entering getCachedConfiguration");
        try {
            String i = m4120i();
            if (!i.isEmpty()) {
                C5988a.m4031a(getClass(), 0, "leaving getCachedConfiguration,cached config load successfully");
                return new JSONObject(i);
            }
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        C5988a.m4031a(getClass(), 0, "leaving getCachedConfiguration,cached config load failed");
        return null;
    }

    /* renamed from: h */
    private JSONObject m4119h() {
        C5988a.m4031a(getClass(), 0, "entering getDefaultRemoteConfig");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("conf_version", f4486b);
            jSONObject.put("async_update_time_interval", 3600);
            jSONObject.put("forced_full_update_time_interval", MetaData.DEFAULT_SESSION_MAX_BACKGROUND_TIME);
            jSONObject.put("conf_refresh_time_interval", f4492h);
            jSONObject.put("location_min_accuracy", 500);
            jSONObject.put("location_max_cache_age", MetaData.DEFAULT_SESSION_MAX_BACKGROUND_TIME);
            jSONObject.put("endpoint_url", "https://c.paypal.com/r/v1/device/client-metadata");
        } catch (JSONException e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        return jSONObject;
    }

    /* renamed from: i */
    private String m4120i() {
        C5988a.m4031a(getClass(), 0, "Loading loadCachedConfigDataFromDisk");
        return C5986c.m4026a(new File(this.f4496l.getFilesDir(), f4488d));
    }

    /* renamed from: j */
    private boolean m4121j() {
        C5988a.m4031a(getClass(), 0, "entering deleteCachedConfigDataFromDisk");
        return C5986c.m4028b(new File(this.f4496l.getFilesDir(), f4488d)) && C5986c.m4028b(new File(this.f4496l.getFilesDir(), f4485a));
    }

    /* renamed from: k */
    private String m4122k() {
        return C5986c.m4026a(new File(this.f4496l.getFilesDir(), f4485a));
    }

    /* renamed from: a */
    public String mo72562a() {
        return this.f4497m.optString("conf_version", "");
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo72563a(String str) {
        C5988a.m4031a(getClass(), 0, "entering saveConfigDataToDisk");
        File file = new File(this.f4496l.getFilesDir(), f4488d);
        File file2 = new File(this.f4496l.getFilesDir(), f4485a);
        C5986c.m4027a(file, str);
        C5986c.m4027a(file2, String.valueOf(System.currentTimeMillis()));
    }

    /* renamed from: b */
    public List<String> mo72564b() {
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = this.f4497m.optJSONArray("android_apps_to_check");
        int i = 0;
        while (optJSONArray != null && i < optJSONArray.length()) {
            arrayList.add(optJSONArray.getString(i));
            i++;
        }
        return arrayList;
    }

    /* renamed from: c */
    public String mo72565c() {
        return this.f4497m.optString("m", f4489e);
    }
}
