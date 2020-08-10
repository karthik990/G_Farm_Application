package lib.android.paypal.com.magnessdk.network;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import java.util.HashMap;
import java.util.Map;
import lib.android.paypal.com.magnessdk.C5983a;
import lib.android.paypal.com.magnessdk.MagnesSettings;
import lib.android.paypal.com.magnessdk.network.httpclient.MagnesNetworking;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import org.json.JSONObject;

/* renamed from: lib.android.paypal.com.magnessdk.network.a */
public final class C5995a extends C6002f {

    /* renamed from: b */
    private static final String f4421b = "pairing_id";

    /* renamed from: c */
    private static final String f4422c = "ip_addrs";

    /* renamed from: d */
    private static final int f4423d = 0;

    /* renamed from: e */
    private static final long f4424e = 1000;

    /* renamed from: f */
    private JSONObject f4425f;

    /* renamed from: g */
    private int f4426g;

    /* renamed from: h */
    private Map<String, String> f4427h = new HashMap();

    /* renamed from: i */
    private Handler f4428i;

    /* renamed from: j */
    private MagnesNetworkingFactoryImpl f4429j;

    /* renamed from: k */
    private MagnesSettings f4430k;

    public C5995a(JSONObject jSONObject, MagnesSettings magnesSettings, Handler handler) {
        this.f4426g = magnesSettings.getMagnesSource();
        this.f4425f = jSONObject;
        this.f4428i = handler;
        this.f4430k = magnesSettings;
        this.f4429j = magnesSettings.getMagnesNetworkingFactoryImpl() == null ? new MagnesNetworkingFactoryImpl() : magnesSettings.getMagnesNetworkingFactoryImpl();
    }

    /* renamed from: a */
    public void mo72547a() {
        String str = "app_version";
        this.f4427h.put("User-Agent", String.format("%s/%s/%s/%s/Android", new Object[]{this.f4425f.optString("app_id"), this.f4425f.optString(str), this.f4425f.optString(str), this.f4425f.optString("app_guid")}));
        this.f4427h.put("Accept-Language", "en-us");
    }

    /* renamed from: b */
    public void mo72548b() {
        if (this.f4430k.isEnableNetworkOnCallerThread()) {
            mo72549c();
        } else {
            mo72559d();
        }
    }

    /* renamed from: c */
    public void mo72549c() {
        String str = "Beacon return non-200 status code : ";
        try {
            mo72547a();
            StringBuilder sb = new StringBuilder(C5983a.f4220g);
            sb.append("?p=");
            sb.append(this.f4425f.optString(f4421b));
            sb.append("&i=");
            sb.append(this.f4425f.optString(f4422c));
            sb.append("&t=");
            sb.append(String.valueOf(System.currentTimeMillis() / 1000));
            if (this.f4426g == 0) {
                sb.append("&s=");
                sb.append(this.f4425f.optString("app_id"));
            } else {
                sb.append("&a=");
                sb.append(this.f4426g);
            }
            if (this.f4428i != null) {
                this.f4428i.sendMessage(Message.obtain(this.f4428i, 20, sb));
            }
            MagnesNetworking createHttpClient = this.f4429j.createHttpClient("GET");
            createHttpClient.setHeader(this.f4427h);
            createHttpClient.setUri(Uri.parse(sb.toString()));
            Class cls = getClass();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Sending BeaconRequest : ");
            sb2.append(sb.toString());
            C5988a.m4031a(cls, 0, sb2.toString());
            int execute = createHttpClient.execute(null);
            String str2 = "BeaconRequest returned HTTP";
            if (execute == 200) {
                String str3 = new String(createHttpClient.getResponseContent(), "UTF-8");
                Class cls2 = getClass();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(execute);
                sb3.append(" ,responseString: ");
                sb3.append(str3);
                C5988a.m4031a(cls2, 0, sb3.toString());
                if (this.f4428i != null) {
                    this.f4428i.sendMessage(Message.obtain(this.f4428i, 22, str3));
                    return;
                }
                return;
            }
            if (this.f4428i != null) {
                Handler handler = this.f4428i;
                Handler handler2 = this.f4428i;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(execute);
                handler.sendMessage(Message.obtain(handler2, 21, sb4.toString()));
            }
            Class cls3 = getClass();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str2);
            sb5.append(execute);
            C5988a.m4031a(cls3, 3, sb5.toString());
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
            Handler handler3 = this.f4428i;
            if (handler3 != null) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str);
                sb6.append(e);
                handler3.sendMessage(Message.obtain(handler3, 21, sb6.toString()));
            }
        }
    }

    public void run() {
        if (this.f4428i != null) {
            mo72549c();
        }
    }
}
