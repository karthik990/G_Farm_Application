package com.google.android.gms.internal.firebase_remote_config;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzeq {
    private JSONObject zzkp;
    private Date zzkq;
    private JSONArray zzkr;

    private zzeq() {
        this.zzkp = new JSONObject();
        this.zzkq = zzeo.zzkk;
        this.zzkr = new JSONArray();
    }

    public final zzeq zzc(Map<String, String> map) {
        this.zzkp = new JSONObject(map);
        return this;
    }

    public final zzeq zza(Date date) {
        this.zzkq = date;
        return this;
    }

    public final zzeq zzb(List<zzde> list) {
        JSONArray jSONArray = new JSONArray();
        for (zzde jSONObject : list) {
            jSONArray.put(new JSONObject(jSONObject));
        }
        this.zzkr = jSONArray;
        return this;
    }

    public final zzeo zzcv() throws JSONException {
        return new zzeo(this.zzkp, this.zzkq, this.zzkr);
    }
}
