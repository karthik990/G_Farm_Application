package com.flurry.sdk;

import android.location.Location;
import android.os.Build.VERSION;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.iu */
public final class C1907iu extends C1926jl {

    /* renamed from: a */
    public final int f1300a;

    /* renamed from: b */
    public boolean f1301b;

    /* renamed from: c */
    public boolean f1302c;

    /* renamed from: d */
    public final Location f1303d;

    public C1907iu(int i, boolean z, boolean z2, Location location) {
        this.f1300a = i;
        this.f1301b = z;
        this.f1302c = z2;
        this.f1303d = location;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        boolean z;
        double d;
        double d2;
        JSONObject a = super.mo16502a();
        a.put("fl.report.location.enabled", this.f1301b);
        if (this.f1301b) {
            a.put("fl.location.permission.status", this.f1302c);
            if (this.f1302c && this.f1303d != null) {
                int i = VERSION.SDK_INT;
                boolean z2 = false;
                double d3 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                if (i >= 26) {
                    d3 = (double) this.f1303d.getVerticalAccuracyMeters();
                    d2 = (double) this.f1303d.getBearingAccuracyDegrees();
                    d = (double) this.f1303d.getSpeedAccuracyMetersPerSecond();
                    z2 = this.f1303d.hasBearingAccuracy();
                    z = this.f1303d.hasSpeedAccuracy();
                } else {
                    d2 = 0.0d;
                    d = 0.0d;
                    z = false;
                }
                a.put("fl.precision.value", this.f1300a);
                a.put("fl.latitude.value", this.f1303d.getLatitude());
                a.put("fl.longitude.value", this.f1303d.getLongitude());
                a.put("fl.horizontal.accuracy.value", (double) this.f1303d.getAccuracy());
                a.put("fl.time.epoch.value", this.f1303d.getTime());
                if (VERSION.SDK_INT >= 17) {
                    a.put("fl.time.uptime.value", TimeUnit.NANOSECONDS.toMillis(this.f1303d.getElapsedRealtimeNanos()));
                }
                a.put("fl.altitude.value", this.f1303d.getAltitude());
                a.put("fl.vertical.accuracy.value", d3);
                a.put("fl.bearing.value", (double) this.f1303d.getBearing());
                a.put("fl.speed.value", (double) this.f1303d.getSpeed());
                a.put("fl.bearing.accuracy.available", z2);
                a.put("fl.speed.accuracy.available", z);
                a.put("fl.bearing.accuracy.degrees", d2);
                a.put("fl.speed.accuracy.meters.per.sec", d);
            }
        }
        return a;
    }
}
