package lib.android.paypal.com.magnessdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.HashMap;
import lib.android.paypal.com.magnessdk.MagnesSettings.Builder;
import lib.android.paypal.com.magnessdk.network.C5995a;
import lib.android.paypal.com.magnessdk.network.C5996b;
import lib.android.paypal.com.magnessdk.network.C6004h;
import lib.android.paypal.com.magnessdk.network.C6005i;
import lib.android.paypal.com.magnessdk.p046b.C5988a;
import org.json.JSONException;
import org.json.JSONObject;

public final class MagnesSDK {

    /* renamed from: c */
    private static final int f4207c = 32;

    /* renamed from: d */
    private static JSONObject f4208d;

    /* renamed from: e */
    private static MagnesSDK f4209e;

    /* renamed from: a */
    C6005i f4210a;

    /* renamed from: b */
    MagnesSettings f4211b;

    /* renamed from: f */
    private Handler f4212f;

    /* renamed from: g */
    private HandlerThread f4213g;

    private MagnesSDK() {
    }

    /* renamed from: a */
    private void m4018a() {
        if (this.f4213g == null) {
            this.f4213g = new HandlerThread("MagnesHandlerThread");
            this.f4213g.start();
            this.f4212f = C6004h.m4112a(this.f4213g.getLooper(), this);
        }
    }

    /* renamed from: a */
    private void m4019a(JSONObject jSONObject) {
        C5996b bVar = new C5996b(jSONObject, this.f4211b, this.f4212f);
        C5995a aVar = new C5995a(jSONObject, this.f4211b, this.f4212f);
        if (m4020b()) {
            aVar.mo72548b();
        }
        bVar.mo72548b();
    }

    /* renamed from: b */
    private boolean m4020b() {
        return !this.f4211b.isDisableBeacon() && this.f4211b.getEnvironment() == Environment.LIVE;
    }

    public static synchronized MagnesSDK getInstance() {
        MagnesSDK magnesSDK;
        synchronized (MagnesSDK.class) {
            if (f4209e == null) {
                f4209e = new MagnesSDK();
            }
            magnesSDK = f4209e;
        }
        return magnesSDK;
    }

    public MagnesResult collect(Context context) {
        return collect(context, null, null);
    }

    public MagnesResult collect(Context context, String str, HashMap<String, String> hashMap) {
        Class cls = getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("COLLECT method called with paypalClientMetaDataId : ");
        sb.append(str);
        sb.append(" , Is pass in additionalData null? : ");
        sb.append(Boolean.toString(hashMap == null));
        C5988a.m4031a(cls, 0, sb.toString());
        if (this.f4211b == null) {
            C5988a.m4031a(getClass(), 2, "No MagnesSettings specified, using platform default.");
            this.f4211b = new Builder(context).build();
            setUp(this.f4211b);
        }
        C5994f b = C5994f.m4082b();
        b.mo72545a(context, str, hashMap);
        JSONObject a = b.mo72540a(f4208d);
        String str2 = null;
        try {
            Class cls2 = getClass();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Device Info JSONObject : ");
            sb2.append(a.toString(2));
            C5988a.m4031a(cls2, 0, sb2.toString());
            str2 = a.getString("pairing_id");
        } catch (JSONException e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
        return new MagnesResult().setDeviceInfo(a).setPaypalClientMetaDataId(str2);
    }

    public MagnesResult collectAndSubmit(Context context) {
        return collectAndSubmit(context, null, null);
    }

    public MagnesResult collectAndSubmit(Context context, String str, HashMap<String, String> hashMap) {
        Class cls = getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("SUBMIT method called with paypalClientMetaDataId : ");
        sb.append(str);
        sb.append(" , Is pass in additionalData null? : ");
        sb.append(Boolean.toString(hashMap == null));
        C5988a.m4031a(cls, 0, sb.toString());
        MagnesResult collect = collect(context, str, hashMap);
        m4019a(collect.getDeviceInfo());
        return collect;
    }

    public MagnesSettings setUp(MagnesSettings magnesSettings) {
        this.f4211b = magnesSettings;
        m4018a();
        this.f4210a = new C6005i(magnesSettings.getContext(), this.f4212f, magnesSettings.isDisableRemoteConfig());
        f4208d = C5991e.m4046b().mo72539a(magnesSettings.getContext());
        return magnesSettings;
    }
}
