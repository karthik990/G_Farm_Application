package com.startapp.android.publish.adsCommon.p090j;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import com.startapp.android.publish.common.metaData.C5111a;
import com.startapp.android.publish.common.metaData.C5121g;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.C5183d;
import java.util.HashMap;
import org.json.JSONArray;

/* renamed from: com.startapp.android.publish.adsCommon.j.b */
/* compiled from: StartAppSDK */
public class C5048b {

    /* renamed from: a */
    protected C5047a f3301a = new C5047a();

    /* renamed from: b */
    protected SensorManager f3302b;

    /* renamed from: c */
    protected C5183d f3303c;

    /* renamed from: d */
    private HashMap<Integer, C5050a> f3304d = null;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public int f3305e;

    /* renamed from: f */
    private SensorEventListener f3306f = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (C5048b.this.f3301a.mo62355a(sensorEvent) == C5048b.this.f3305e) {
                C5048b.this.mo62358b();
                if (C5048b.this.f3303c != null) {
                    C5048b.this.f3303c.mo62338a(C5048b.this.mo62359c());
                }
            }
        }
    };

    /* renamed from: com.startapp.android.publish.adsCommon.j.b$a */
    /* compiled from: StartAppSDK */
    private class C5050a {

        /* renamed from: b */
        private int f3309b;

        /* renamed from: c */
        private int f3310c;

        public C5050a(int i, int i2) {
            this.f3309b = i;
            this.f3310c = i2;
        }

        /* renamed from: a */
        public int mo62362a() {
            return this.f3309b;
        }

        /* renamed from: b */
        public int mo62363b() {
            return this.f3310c;
        }
    }

    public C5048b(Context context, C5183d dVar) {
        this.f3302b = (SensorManager) context.getSystemService("sensor");
        this.f3303c = dVar;
        this.f3305e = 0;
        m3328d();
    }

    /* renamed from: a */
    public void mo62357a() {
        for (Integer intValue : this.f3304d.keySet()) {
            int intValue2 = intValue.intValue();
            C5050a aVar = (C5050a) this.f3304d.get(Integer.valueOf(intValue2));
            if (VERSION.SDK_INT >= aVar.mo62362a()) {
                Sensor defaultSensor = this.f3302b.getDefaultSensor(intValue2);
                if (defaultSensor != null) {
                    this.f3302b.registerListener(this.f3306f, defaultSensor, aVar.mo62363b());
                    this.f3305e++;
                }
            }
        }
    }

    /* renamed from: b */
    public void mo62358b() {
        this.f3302b.unregisterListener(this.f3306f);
    }

    /* renamed from: c */
    public JSONArray mo62359c() {
        try {
            return this.f3301a.mo62356a();
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: d */
    private void m3328d() {
        this.f3304d = new HashMap<>();
        C5121g sensorsConfig = MetaData.getInstance().getSensorsConfig();
        m3327a(13, sensorsConfig.mo62652c());
        m3327a(9, sensorsConfig.mo62653d());
        m3327a(5, sensorsConfig.mo62654e());
        m3327a(10, sensorsConfig.mo62655f());
        m3327a(2, sensorsConfig.mo62656g());
        m3327a(6, sensorsConfig.mo62657h());
        m3327a(12, sensorsConfig.mo62658i());
        m3327a(11, sensorsConfig.mo62659j());
        m3327a(16, sensorsConfig.mo62660k());
    }

    /* renamed from: a */
    private void m3327a(int i, C5111a aVar) {
        if (aVar.mo62640c()) {
            this.f3304d.put(Integer.valueOf(i), new C5050a(aVar.mo62639b(), aVar.mo62638a()));
        }
    }
}
