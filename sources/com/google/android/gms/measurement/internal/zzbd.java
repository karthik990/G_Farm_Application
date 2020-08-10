package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Pair;
import androidx.work.WorkRequest;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

final class zzbd extends zzcs {
    static final Pair<String, Long> zzana = new Pair<>("", Long.valueOf(0));
    private SharedPreferences zzabr;
    public zzbh zzanb;
    public final zzbg zzanc = new zzbg(this, "last_upload", 0);
    public final zzbg zzand = new zzbg(this, "last_upload_attempt", 0);
    public final zzbg zzane = new zzbg(this, "backoff", 0);
    public final zzbg zzanf = new zzbg(this, "last_delete_stale", 0);
    public final zzbg zzang = new zzbg(this, "midnight_offset", 0);
    public final zzbg zzanh = new zzbg(this, "first_open_time", 0);
    public final zzbg zzani = new zzbg(this, "app_install_time", 0);
    public final zzbi zzanj = new zzbi(this, "app_instance_id", null);
    private String zzank;
    private boolean zzanl;
    private long zzanm;
    public final zzbg zzann = new zzbg(this, "time_before_start", WorkRequest.MIN_BACKOFF_MILLIS);
    public final zzbg zzano = new zzbg(this, "session_timeout", 1800000);
    public final zzbf zzanp = new zzbf(this, "start_new_session", true);
    public final zzbg zzanq = new zzbg(this, "last_pause_time", 0);
    public final zzbg zzanr = new zzbg(this, "time_active", 0);
    public boolean zzans;
    public zzbf zzant = new zzbf(this, "app_backgrounded", false);

    /* access modifiers changed from: 0000 */
    public final Pair<String, Boolean> zzbz(String str) {
        String str2 = "";
        zzaf();
        long elapsedRealtime = zzbx().elapsedRealtime();
        String str3 = this.zzank;
        if (str3 != null && elapsedRealtime < this.zzanm) {
            return new Pair<>(str3, Boolean.valueOf(this.zzanl));
        }
        this.zzanm = elapsedRealtime + zzgv().zza(str, zzai.zzaiv);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzank = advertisingIdInfo.getId();
                this.zzanl = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzank == null) {
                this.zzank = str2;
            }
        } catch (Exception e) {
            zzgt().zzjn().zzg("Unable to get advertising id", e);
            this.zzank = str2;
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzank, Boolean.valueOf(this.zzanl));
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final String zzca(String str) {
        zzaf();
        String str2 = (String) zzbz(str).first;
        MessageDigest messageDigest = zzfx.getMessageDigest();
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, messageDigest.digest(str2.getBytes()))});
    }

    zzbd(zzbw zzbw) {
        super(zzbw);
    }

    /* access modifiers changed from: protected */
    public final void zzgz() {
        this.zzabr = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        String str = "has_been_opened";
        this.zzans = this.zzabr.getBoolean(str, false);
        if (!this.zzans) {
            Editor edit = this.zzabr.edit();
            edit.putBoolean(str, true);
            edit.apply();
        }
        zzbh zzbh = new zzbh(this, "health_monitor", Math.max(0, ((Long) zzai.zzaiw.get()).longValue()));
        this.zzanb = zzbh;
    }

    /* access modifiers changed from: private */
    public final SharedPreferences zzju() {
        zzaf();
        zzcl();
        return this.zzabr;
    }

    /* access modifiers changed from: 0000 */
    public final void zzcb(String str) {
        zzaf();
        Editor edit = zzju().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    /* access modifiers changed from: 0000 */
    public final String zzjv() {
        zzaf();
        return zzju().getString("gmp_app_id", null);
    }

    /* access modifiers changed from: 0000 */
    public final void zzcc(String str) {
        zzaf();
        Editor edit = zzju().edit();
        edit.putString("admob_app_id", str);
        edit.apply();
    }

    /* access modifiers changed from: 0000 */
    public final String zzjw() {
        zzaf();
        return zzju().getString("admob_app_id", null);
    }

    /* access modifiers changed from: 0000 */
    public final Boolean zzjx() {
        zzaf();
        String str = "use_service";
        if (!zzju().contains(str)) {
            return null;
        }
        return Boolean.valueOf(zzju().getBoolean(str, false));
    }

    /* access modifiers changed from: 0000 */
    public final void zzg(boolean z) {
        zzaf();
        zzgt().zzjo().zzg("Setting useService", Boolean.valueOf(z));
        Editor edit = zzju().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    /* access modifiers changed from: 0000 */
    public final void zzjy() {
        zzaf();
        zzgt().zzjo().zzby("Clearing collection preferences.");
        if (zzgv().zza(zzai.zzale)) {
            Boolean zzjz = zzjz();
            Editor edit = zzju().edit();
            edit.clear();
            edit.apply();
            if (zzjz != null) {
                setMeasurementEnabled(zzjz.booleanValue());
            }
            return;
        }
        boolean contains = zzju().contains("measurement_enabled");
        boolean z = true;
        if (contains) {
            z = zzh(true);
        }
        Editor edit2 = zzju().edit();
        edit2.clear();
        edit2.apply();
        if (contains) {
            setMeasurementEnabled(z);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void setMeasurementEnabled(boolean z) {
        zzaf();
        zzgt().zzjo().zzg("Setting measurementEnabled", Boolean.valueOf(z));
        Editor edit = zzju().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzh(boolean z) {
        zzaf();
        return zzju().getBoolean("measurement_enabled", z);
    }

    /* access modifiers changed from: 0000 */
    public final Boolean zzjz() {
        zzaf();
        String str = "measurement_enabled";
        if (zzju().contains(str)) {
            return Boolean.valueOf(zzju().getBoolean(str, true));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final String zzka() {
        zzaf();
        String str = "previous_os_version";
        String string = zzju().getString(str, null);
        zzgp().zzcl();
        String str2 = VERSION.RELEASE;
        if (!TextUtils.isEmpty(str2) && !str2.equals(string)) {
            Editor edit = zzju().edit();
            edit.putString(str, str2);
            edit.apply();
        }
        return string;
    }

    /* access modifiers changed from: 0000 */
    public final void zzi(boolean z) {
        zzaf();
        zzgt().zzjo().zzg("Updating deferred analytics collection", Boolean.valueOf(z));
        Editor edit = zzju().edit();
        edit.putBoolean("deferred_analytics_collection", z);
        edit.apply();
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzkb() {
        zzaf();
        return zzju().getBoolean("deferred_analytics_collection", false);
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzkc() {
        return this.zzabr.contains("deferred_analytics_collection");
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzaf(long j) {
        return j - this.zzano.get() > this.zzanq.get();
    }
}
