package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Map;
import org.json.JSONObject;

final class zzacw implements zzv<zzaqw> {
    private final /* synthetic */ zzaqw zzcbq;
    private final /* synthetic */ zzaoj zzcbr;
    private final /* synthetic */ zzacq zzcbs;

    zzacw(zzacq zzacq, zzaqw zzaqw, zzaoj zzaoj) {
        this.zzcbs = zzacq;
        this.zzcbq = zzaqw;
        this.zzcbr = zzaoj;
    }

    public final /* synthetic */ void zza(Object obj, Map map) {
        JSONObject jSONObject;
        boolean z;
        String str = Param.SUCCESS;
        try {
            String str2 = (String) map.get(str);
            String str3 = (String) map.get("failure");
            if (!TextUtils.isEmpty(str3)) {
                jSONObject = new JSONObject(str3);
                z = false;
            } else {
                jSONObject = new JSONObject(str2);
                z = true;
            }
            if (this.zzcbs.zzaae.equals(jSONObject.optString("ads_id", ""))) {
                this.zzcbq.zzb("/nativeAdPreProcess", this);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(str, z);
                jSONObject2.put("json", jSONObject);
                this.zzcbr.set(jSONObject2);
            }
        } catch (Throwable th) {
            zzakb.zzb("Error while preprocessing json.", th);
            this.zzcbr.setException(th);
        }
    }
}
