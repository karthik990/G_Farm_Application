package com.startapp.android.publish.common.metaData;

import com.startapp.common.p042c.C2362f;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.common.metaData.g */
/* compiled from: StartAppSDK */
public class C5121g implements Serializable {
    private static final long serialVersionUID = 1;
    @C2362f(mo20785a = true)
    private C5111a ambientTemperatureSensor = new C5111a(14);
    private boolean enabled = false;
    @C2362f(mo20785a = true)
    private C5111a gravitySensor = new C5111a(9);
    @C2362f(mo20785a = true)
    private C5111a gyroscopeUncalibratedSensor = new C5111a(18);
    @C2362f(mo20785a = true)
    private C5111a lightSensor = new C5111a(3);
    @C2362f(mo20785a = true)
    private C5111a linearAccelerationSensor = new C5111a(9);
    @C2362f(mo20785a = true)
    private C5111a magneticFieldSensor = new C5111a(3);
    @C2362f(mo20785a = true)
    private C5111a pressureSensor = new C5111a(9);
    @C2362f(mo20785a = true)
    private C5111a relativeHumiditySensor = new C5111a(14);
    @C2362f(mo20785a = true)
    private C5111a rotationVectorSensor = new C5111a(9);
    private int timeoutInSec = 10;

    /* renamed from: a */
    public int mo62650a() {
        return this.timeoutInSec;
    }

    /* renamed from: b */
    public boolean mo62651b() {
        return this.enabled;
    }

    /* renamed from: c */
    public C5111a mo62652c() {
        return this.ambientTemperatureSensor;
    }

    /* renamed from: d */
    public C5111a mo62653d() {
        return this.gravitySensor;
    }

    /* renamed from: e */
    public C5111a mo62654e() {
        return this.lightSensor;
    }

    /* renamed from: f */
    public C5111a mo62655f() {
        return this.linearAccelerationSensor;
    }

    /* renamed from: g */
    public C5111a mo62656g() {
        return this.magneticFieldSensor;
    }

    /* renamed from: h */
    public C5111a mo62657h() {
        return this.pressureSensor;
    }

    /* renamed from: i */
    public C5111a mo62658i() {
        return this.relativeHumiditySensor;
    }

    /* renamed from: j */
    public C5111a mo62659j() {
        return this.rotationVectorSensor;
    }

    /* renamed from: k */
    public C5111a mo62660k() {
        return this.gyroscopeUncalibratedSensor;
    }
}
