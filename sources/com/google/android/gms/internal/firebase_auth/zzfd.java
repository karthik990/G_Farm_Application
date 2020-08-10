package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzj;
import com.google.firebase.auth.api.internal.zzdv;

public final class zzfd extends AbstractSafeParcelable implements zzdv<zzfd, zzj> {
    public static final Creator<zzfd> CREATOR = new zzff();
    private String zzif;
    private String zzku;
    private String zzru;

    public zzfd() {
    }

    zzfd(String str, String str2, String str3) {
        this.zzif = str;
        this.zzku = str2;
        this.zzru = str3;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String zzae() {
        return this.zzku;
    }

    public final String zzey() {
        return this.zzru;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzku, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzru, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzjm<zzj> zzee() {
        return zzj.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzj) {
            zzj zzj = (zzj) zzjc;
            this.zzif = Strings.emptyToNull(zzj.getEmail());
            this.zzku = Strings.emptyToNull(zzj.zzae());
            int i = zzfc.zzry[zzj.zzaf().ordinal()];
            String str = i != 1 ? i != 2 ? null : "PASSWORD_RESET" : "VERIFY_EMAIL";
            this.zzru = str;
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of ResetPasswordResponse.");
    }
}
