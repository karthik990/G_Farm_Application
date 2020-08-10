package com.google.android.gms.internal.firebase_remote_config;

import android.content.SharedPreferences;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder;
import java.util.Date;

public final class zzeu {
    private static final Date zzle = new Date(-1);
    public static final Date zzlf = new Date(-1);
    private final SharedPreferences zzlg;
    private final Object zzlh = new Object();
    private final Object zzli = new Object();

    public zzeu(SharedPreferences sharedPreferences) {
        this.zzlg = sharedPreferences;
    }

    public final boolean isDeveloperModeEnabled() {
        return this.zzlg.getBoolean("is_developer_mode_enabled", false);
    }

    public final String zzcx() {
        return this.zzlg.getString("last_fetch_etag", null);
    }

    public final void zzb(boolean z) {
        synchronized (this.zzlh) {
            this.zzlg.edit().putBoolean("is_developer_mode_enabled", z).apply();
        }
    }

    public final void zzm(int i) {
        synchronized (this.zzlh) {
            this.zzlg.edit().putInt("last_fetch_status", i).apply();
        }
    }

    public final void zzd(Date date) {
        synchronized (this.zzlh) {
            this.zzlg.edit().putLong("last_fetch_time_in_millis", date.getTime()).apply();
        }
    }

    public final void zzbf(String str) {
        this.zzlg.edit().putString("last_fetch_etag", str).apply();
    }

    public final FirebaseRemoteConfigInfo getInfo() {
        zzey zzdd;
        synchronized (this.zzlh) {
            long j = this.zzlg.getLong("last_fetch_time_in_millis", -1);
            int i = this.zzlg.getInt("last_fetch_status", 0);
            zzdd = new zzfa().zzn(i).zzc(j).zza(new Builder().setDeveloperModeEnabled(this.zzlg.getBoolean("is_developer_mode_enabled", false)).build()).zzdd();
        }
        return zzdd;
    }

    public final zzev zzcy() {
        zzev zzev;
        synchronized (this.zzli) {
            zzev = new zzev(this.zzlg.getInt("num_failed_fetches", 0), new Date(this.zzlg.getLong("backoff_end_time_in_millis", -1)));
        }
        return zzev;
    }

    public final void zza(int i, Date date) {
        synchronized (this.zzli) {
            this.zzlg.edit().putInt("num_failed_fetches", i).putLong("backoff_end_time_in_millis", date.getTime()).apply();
        }
    }
}
