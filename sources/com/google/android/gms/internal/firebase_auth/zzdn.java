package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.zzac;

public final class zzdn extends AbstractSafeParcelable {
    public static final Creator<zzdn> CREATOR = new zzdm();
    private final String zzhq;
    private final String zzkg;
    private final long zzko;
    private final boolean zzkp;
    private final boolean zzkq;
    private zzac zzkr;

    public zzdn(zzac zzac, String str, String str2, long j, boolean z, boolean z2) {
        this.zzkr = zzac;
        this.zzkg = str;
        this.zzhq = str2;
        this.zzko = j;
        this.zzkp = z;
        this.zzkq = z2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkr, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkg, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhq, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzko);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzkp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzkq);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
