package lib.android.paypal.com.magnessdk.network;

import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import lib.android.paypal.com.magnessdk.C5983a;
import lib.android.paypal.com.magnessdk.Environment;
import lib.android.paypal.com.magnessdk.MagnesSettings;
import lib.android.paypal.com.magnessdk.network.httpclient.MagnesNetworking;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import org.json.JSONObject;

/* renamed from: lib.android.paypal.com.magnessdk.network.b */
public final class C5996b extends C6002f {

    /* renamed from: b */
    private static final String f4431b = "Dyson/%S (%S %S)";

    /* renamed from: c */
    private HashMap<String, String> f4432c;

    /* renamed from: d */
    private Map<String, String> f4433d;

    /* renamed from: e */
    private Handler f4434e;

    /* renamed from: f */
    private MagnesNetworkingFactoryImpl f4435f;

    /* renamed from: g */
    private MagnesSettings f4436g;

    /* renamed from: h */
    private String f4437h = "****MAGNES DEBUGGING MESSAGE****";

    public C5996b(JSONObject jSONObject, MagnesSettings magnesSettings, Handler handler) {
        this.f4435f = magnesSettings.getMagnesNetworkingFactoryImpl() == null ? new MagnesNetworkingFactoryImpl() : magnesSettings.getMagnesNetworkingFactoryImpl();
        this.f4432c = new HashMap<>();
        this.f4433d = new HashMap();
        this.f4434e = handler;
        this.f4436g = magnesSettings;
        this.f4432c.put("appGuid", jSONObject.optString("app_guid"));
        this.f4432c.put("libraryVersion", m4098a(jSONObject));
        this.f4432c.put("additionalData", jSONObject.toString());
    }

    /* renamed from: a */
    private String m4097a(HashMap<String, String> hashMap) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Entry entry : hashMap.entrySet()) {
            if (z) {
                z = false;
            } else {
                sb.append("&");
            }
            String str = "UTF-8";
            sb.append(URLEncoder.encode((String) entry.getKey(), str));
            sb.append("=");
            sb.append(URLEncoder.encode((String) entry.getValue(), str));
        }
        Class cls = getClass();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("encoded device info payload : ");
        sb2.append(sb.toString());
        C5988a.m4031a(cls, 0, sb2.toString());
        return sb.toString();
    }

    /* renamed from: a */
    private String m4098a(JSONObject jSONObject) {
        return String.format(Locale.US, f4431b, new Object[]{jSONObject.optString("comp_version"), jSONObject.optString("os_type"), VERSION.RELEASE});
    }

    /* renamed from: a */
    public void mo72547a() {
        String str = "NV";
        this.f4433d.put("X-PAYPAL-RESPONSE-DATA-FORMAT", str);
        this.f4433d.put("X-PAYPAL-REQUEST-DATA-FORMAT", str);
        this.f4433d.put("X-PAYPAL-SERVICE-VERSION", "1.0.0");
        this.f4433d.put("Content-Type", "application/x-www-form-urlencoded");
    }

    /* renamed from: b */
    public void mo72548b() {
        if (this.f4436g.isEnableNetworkOnCallerThread()) {
            mo72549c();
        } else {
            mo72559d();
        }
    }

    /* renamed from: c */
    public void mo72549c() {
        String str = "UTF-8";
        mo72547a();
        try {
            MagnesNetworking createHttpClient = this.f4435f.createHttpClient("POST");
            Handler handler = this.f4434e;
            String str2 = C5983a.f4223j;
            String str3 = C5983a.f4221h;
            if (handler != null) {
                if (this.f4436g.getEnvironment() == Environment.LIVE) {
                    this.f4434e.sendMessage(Message.obtain(this.f4434e, 0, str3));
                } else {
                    this.f4434e.sendMessage(Message.obtain(this.f4434e, 0, str2));
                    str3 = str2;
                }
            }
            createHttpClient.setUri(Uri.parse(str3));
            createHttpClient.setHeader(this.f4433d);
            int execute = createHttpClient.execute(m4097a(this.f4432c).getBytes(str));
            String str4 = this.f4437h;
            StringBuilder sb = new StringBuilder();
            sb.append("DeviceInfoRequest returned PayPal-Debug-Id: ");
            sb.append(createHttpClient.getPayPalDebugId());
            Log.d(str4, sb.toString());
            String str5 = "DeviceInfoRequest returned HTTP";
            if (execute == 200) {
                String str6 = new String(createHttpClient.getResponseContent(), str);
                if (this.f4434e != null) {
                    this.f4434e.sendMessage(Message.obtain(this.f4434e, 2, str6));
                }
                Class cls = getClass();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str5);
                sb2.append(execute);
                sb2.append(" ,responseString: ");
                sb2.append(str6);
                C5988a.m4031a(cls, 0, sb2.toString());
                return;
            }
            if (this.f4434e != null) {
                this.f4434e.sendMessage(Message.obtain(this.f4434e, 1, Integer.valueOf(execute)));
            }
            Class cls2 = getClass();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str5);
            sb3.append(execute);
            C5988a.m4031a(cls2, 3, sb3.toString());
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            Handler handler2 = this.f4434e;
            if (handler2 != null) {
                handler2.sendMessage(Message.obtain(handler2, 1, e));
            }
        }
    }

    public void run() {
        if (this.f4434e != null) {
            mo72549c();
        }
    }
}
