package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzs;
import com.google.firebase.auth.api.internal.zzdv;

public final class zzfq extends AbstractSafeParcelable implements zzdv<zzfq, zzs> {
    public static final Creator<zzfq> CREATOR = new zzft();
    private String zzib;
    private String zzkh;
    private boolean zzrg;
    private long zzrh;

    public zzfq() {
    }

    zzfq(String str, String str2, long j, boolean z) {
        this.zzib = str;
        this.zzkh = str2;
        this.zzrh = j;
        this.zzrg = z;
    }

    public final String getIdToken() {
        return this.zzib;
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final long zzt() {
        return this.zzrh;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzkh, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzrh);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzrg);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzjm<zzs> zzee() {
        return zzs.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzs) {
            zzs zzs = (zzs) zzjc;
            this.zzib = Strings.emptyToNull(zzs.getIdToken());
            this.zzkh = Strings.emptyToNull(zzs.zzs());
            this.zzrh = zzs.zzt();
            this.zzrg = zzs.zzu();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of VerifyCustomTokenResponse.");
    }
}
