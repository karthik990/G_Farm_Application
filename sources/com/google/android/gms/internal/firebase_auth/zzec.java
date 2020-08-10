package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzb;
import com.google.firebase.auth.api.internal.zzdv;
import java.util.ArrayList;
import java.util.List;

public final class zzec extends AbstractSafeParcelable implements zzdv<zzec, zzb> {
    public static final Creator<zzec> CREATOR = new zzef();
    private String zzia;
    private String zzqy;
    private boolean zzqz;
    private boolean zzra;
    private zzfk zzrb;
    private List<String> zzrc;

    public zzec() {
        this.zzrb = zzfk.zzfa();
    }

    public zzec(String str, boolean z, String str2, boolean z2, zzfk zzfk, List<String> list) {
        this.zzqy = str;
        this.zzqz = z;
        this.zzia = str2;
        this.zzra = z2;
        this.zzrb = zzfk == null ? zzfk.zzfa() : zzfk.zza(zzfk);
        this.zzrc = list;
    }

    public final List<String> getSignInMethods() {
        return this.zzrc;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzqy, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzqz);
        SafeParcelWriter.writeString(parcel, 4, this.zzia, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzra);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzrb, i, false);
        SafeParcelWriter.writeStringList(parcel, 7, this.zzrc, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzjm<zzb> zzee() {
        return zzb.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        zzfk zzfk;
        if (zzjc instanceof zzb) {
            zzb zzb = (zzb) zzjc;
            this.zzqy = Strings.emptyToNull(zzb.zzf());
            this.zzqz = zzb.zzi();
            this.zzia = Strings.emptyToNull(zzb.getProviderId());
            this.zzra = zzb.zzj();
            if (zzb.zzh() == 0) {
                zzfk = zzfk.zzfa();
            } else {
                zzfk = new zzfk(1, new ArrayList(zzb.zzg()));
            }
            this.zzrb = zzfk;
            this.zzrc = zzb.zzl() == 0 ? new ArrayList<>(0) : zzb.zzk();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of CreateAuthUriResponse.");
    }
}
