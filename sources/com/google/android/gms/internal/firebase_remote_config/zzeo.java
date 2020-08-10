package com.google.android.gms.internal.firebase_remote_config;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzeo {
    /* access modifiers changed from: private */
    public static final Date zzkk = new Date(0);
    private JSONObject zzkl;
    private JSONObject zzkm;
    private Date zzkn;
    private JSONArray zzko;

    private zzeo(JSONObject jSONObject, Date date, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("configs_key", jSONObject);
        jSONObject2.put("fetch_time_key", date.getTime());
        jSONObject2.put("abt_experiments_key", jSONArray);
        this.zzkm = jSONObject;
        this.zzkn = date;
        this.zzko = jSONArray;
        this.zzkl = jSONObject2;
    }

    static zzeo zza(JSONObject jSONObject) throws JSONException {
        return new zzeo(jSONObject.getJSONObject("configs_key"), new Date(jSONObject.getLong("fetch_time_key")), jSONObject.getJSONArray("abt_experiments_key"));
    }

    public final JSONObject zzcq() {
        return this.zzkm;
    }

    public final Date zzcr() {
        return this.zzkn;
    }

    public final JSONArray zzcs() {
        return this.zzko;
    }

    public final String toString() {
        return this.zzkl.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzeo)) {
            return false;
        }
        return this.zzkl.toString().equals(((zzeo) obj).toString());
    }

    public final int hashCode() {
        return this.zzkl.hashCode();
    }

    public static zzeq zzct() {
        return new zzeq();
    }
}
