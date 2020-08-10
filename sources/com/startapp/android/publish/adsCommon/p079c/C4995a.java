package com.startapp.android.publish.adsCommon.p079c;

import android.bluetooth.BluetoothDevice;
import com.braintreepayments.api.models.PostalAddressParser;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.startapp.android.publish.adsCommon.c.a */
/* compiled from: StartAppSDK */
public class C4995a {

    /* renamed from: a */
    private Set<BluetoothDevice> f3188a;

    /* renamed from: b */
    private Set<BluetoothDevice> f3189b;

    /* renamed from: a */
    public void mo62209a(BluetoothDevice bluetoothDevice) {
        if (this.f3189b == null) {
            this.f3189b = new HashSet();
        }
        this.f3189b.add(bluetoothDevice);
    }

    /* renamed from: a */
    public void mo62210a(Set<BluetoothDevice> set) {
        this.f3188a = set;
    }

    /* renamed from: a */
    public JSONObject mo62208a() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.f3188a != null && this.f3188a.size() > 0) {
                jSONObject.put("paired", m3144b(this.f3188a));
            }
            if (this.f3189b != null && this.f3189b.size() > 0) {
                jSONObject.put("available", m3144b(this.f3189b));
            }
        } catch (Exception unused) {
        }
        if (jSONObject.length() > 0) {
            return jSONObject;
        }
        return null;
    }

    /* renamed from: b */
    private JSONArray m3144b(Set<BluetoothDevice> set) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (BluetoothDevice bluetoothDevice : set) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("bluetoothClass", bluetoothDevice.getBluetoothClass().getDeviceClass());
                jSONObject.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, bluetoothDevice.getName());
                jSONObject.put("mac", bluetoothDevice.getAddress());
                jSONObject.put("bondState", bluetoothDevice.getBondState());
                jSONArray.put(jSONObject);
            }
            return jSONArray;
        } catch (Exception unused) {
            return null;
        }
    }
}
