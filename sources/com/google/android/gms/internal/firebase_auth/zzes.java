package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzlg.zzb;
import com.google.firebase.auth.api.internal.zzdv;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzes extends AbstractSafeParcelable implements zzdv<zzes, zzb> {
    public static final Creator<zzes> CREATOR = new zzev();
    private String zzic;
    private String zzkh;
    private Long zzrv;
    private String zzrw;
    private Long zzrx;

    public zzes() {
        this.zzrx = Long.valueOf(System.currentTimeMillis());
    }

    public zzes(String str, String str2, Long l, String str3) {
        this(str, str2, l, str3, Long.valueOf(System.currentTimeMillis()));
    }

    zzes(String str, String str2, Long l, String str3, Long l2) {
        this.zzkh = str;
        this.zzic = str2;
        this.zzrv = l;
        this.zzrw = str3;
        this.zzrx = l2;
    }

    public final boolean isValid() {
        return DefaultClock.getInstance().currentTimeMillis() + PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS < this.zzrx.longValue() + (this.zzrv.longValue() * 1000);
    }

    public final void zzcm(String str) {
        this.zzkh = Preconditions.checkNotEmpty(str);
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final String getAccessToken() {
        return this.zzic;
    }

    public final long zzt() {
        Long l = this.zzrv;
        if (l == null) {
            return 0;
        }
        return l.longValue();
    }

    public final String zzeu() {
        return this.zzrw;
    }

    public final long zzev() {
        return this.zzrx.longValue();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzkh, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzic, false);
        SafeParcelWriter.writeLongObject(parcel, 4, Long.valueOf(zzt()), false);
        SafeParcelWriter.writeString(parcel, 5, this.zzrw, false);
        SafeParcelWriter.writeLongObject(parcel, 6, Long.valueOf(this.zzrx.longValue()), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzjm<zzb> zzee() {
        return zzb.zzm();
    }

    public final String zzew() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("refresh_token", this.zzkh);
            jSONObject.put("access_token", this.zzic);
            jSONObject.put("expires_in", this.zzrv);
            jSONObject.put("token_type", this.zzrw);
            jSONObject.put("issued_at", this.zzrx);
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.d("GetTokenResponse", "Failed to convert GetTokenResponse to JSON");
            throw new zzbl((Throwable) e);
        }
    }

    public static zzes zzcn(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            zzes zzes = new zzes();
            zzes.zzkh = jSONObject.optString("refresh_token", null);
            zzes.zzic = jSONObject.optString("access_token", null);
            zzes.zzrv = Long.valueOf(jSONObject.optLong("expires_in"));
            zzes.zzrw = jSONObject.optString("token_type", null);
            zzes.zzrx = Long.valueOf(jSONObject.optLong("issued_at"));
            return zzes;
        } catch (JSONException e) {
            Log.d("GetTokenResponse", "Failed to read GetTokenResponse from JSONObject");
            throw new zzbl((Throwable) e);
        }
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzb) {
            zzb zzb = (zzb) zzjc;
            this.zzkh = Strings.emptyToNull(zzb.zzs());
            this.zzic = Strings.emptyToNull(zzb.getAccessToken());
            this.zzrv = Long.valueOf(zzb.zzt());
            this.zzrw = Strings.emptyToNull(zzb.zzeu());
            this.zzrx = Long.valueOf(System.currentTimeMillis());
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of GrantTokenResponse.");
    }
}
