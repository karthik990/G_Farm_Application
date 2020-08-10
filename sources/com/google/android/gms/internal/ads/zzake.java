package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.json.JSONException;
import org.json.JSONObject;

final class zzake extends zzakg {
    private final /* synthetic */ Context zzcrg;
    private final /* synthetic */ zzakd zzcrh;

    zzake(zzakd zzakd, Context context) {
        this.zzcrh = zzakd;
        this.zzcrg = context;
        super(null);
    }

    public final void zzdn() {
        SharedPreferences sharedPreferences = this.zzcrg.getSharedPreferences("admob", 0);
        Editor edit = sharedPreferences.edit();
        synchronized (this.zzcrh.mLock) {
            this.zzcrh.zzatw = sharedPreferences;
            this.zzcrh.zzcqw = edit;
            this.zzcrh.zzcqx = zzakd.zzqq();
            this.zzcrh.zzcik = this.zzcrh.zzatw.getBoolean("use_https", this.zzcrh.zzcik);
            this.zzcrh.zzcil = this.zzcrh.zzatw.getBoolean("content_url_opted_out", this.zzcrh.zzcil);
            this.zzcrh.zzcqy = this.zzcrh.zzatw.getString("content_url_hashes", this.zzcrh.zzcqy);
            this.zzcrh.zzcit = this.zzcrh.zzatw.getBoolean("auto_collect_location", this.zzcrh.zzcit);
            this.zzcrh.zzcim = this.zzcrh.zzatw.getBoolean("content_vertical_opted_out", this.zzcrh.zzcim);
            this.zzcrh.zzcqz = this.zzcrh.zzatw.getString("content_vertical_hashes", this.zzcrh.zzcqz);
            this.zzcrh.zzcrd = this.zzcrh.zzatw.getInt("version_code", this.zzcrh.zzcrd);
            this.zzcrh.zzcpj = this.zzcrh.zzatw.getString("app_settings_json", this.zzcrh.zzcpj);
            this.zzcrh.zzcra = this.zzcrh.zzatw.getLong("app_settings_last_update_ms", this.zzcrh.zzcra);
            this.zzcrh.zzcrb = this.zzcrh.zzatw.getLong("app_last_background_time_ms", this.zzcrh.zzcrb);
            this.zzcrh.zzcqg = this.zzcrh.zzatw.getInt("request_in_session_count", this.zzcrh.zzcqg);
            this.zzcrh.zzcrc = this.zzcrh.zzatw.getLong("first_ad_req_time_ms", this.zzcrh.zzcrc);
            this.zzcrh.zzcre = this.zzcrh.zzatw.getStringSet("never_pool_slots", this.zzcrh.zzcre);
            try {
                this.zzcrh.zzcrf = new JSONObject(this.zzcrh.zzatw.getString("native_advanced_settings", "{}"));
            } catch (JSONException e) {
                zzakb.zzc("Could not convert native advanced settings to json object", e);
            }
            this.zzcrh.zze(this.zzcrh.zzqs());
        }
    }
}
