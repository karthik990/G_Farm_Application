package com.startapp.android.publish.adsCommon.p090j;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.HashMap;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.startapp.android.publish.adsCommon.j.a */
/* compiled from: StartAppSDK */
public class C5047a {

    /* renamed from: a */
    private HashMap<Integer, SensorEvent> f3300a = new HashMap<>();

    /* renamed from: a */
    public int mo62355a(SensorEvent sensorEvent) {
        int size;
        synchronized (this) {
            int type = sensorEvent.sensor.getType();
            SensorEvent sensorEvent2 = (SensorEvent) this.f3300a.get(Integer.valueOf(type));
            if (sensorEvent2 == null || sensorEvent2.accuracy <= sensorEvent.accuracy) {
                this.f3300a.put(Integer.valueOf(type), sensorEvent);
            }
            size = this.f3300a.size();
        }
        return size;
    }

    /* renamed from: a */
    public JSONArray mo62356a() {
        JSONArray jSONArray = new JSONArray();
        for (SensorEvent sensorEvent : this.f3300a.values()) {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            Sensor sensor = sensorEvent.sensor;
            jSONObject2.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, sensor.getName());
            jSONObject2.put("vendor", sensor.getVendor());
            jSONObject2.put(ClientCookie.VERSION_ATTR, sensor.getVersion());
            jSONObject2.put("maximum range", (double) sensor.getMaximumRange());
            jSONObject2.put("power", (double) sensor.getPower());
            jSONObject2.put("resolution", (double) sensor.getResolution());
            jSONObject2.put("accuracy", sensorEvent.accuracy);
            jSONObject2.put(Param.TIMESTAMP, sensorEvent.timestamp);
            JSONArray jSONArray2 = new JSONArray();
            for (float f : sensorEvent.values) {
                jSONArray2.put((double) f);
            }
            jSONObject2.put("values", jSONArray2);
            jSONObject.put(String.valueOf(sensor.getType()), jSONObject2);
            jSONArray.put(jSONObject);
        }
        if (jSONArray.length() > 0) {
            return jSONArray;
        }
        return null;
    }
}
