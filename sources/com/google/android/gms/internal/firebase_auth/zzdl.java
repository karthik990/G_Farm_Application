package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzdl extends AbstractSafeParcelable {
    public static final Creator<zzdl> CREATOR = new zzdk();
    private final String zzhq;
    private final String zzib;
    private final String zzjo;
    private final long zzko;
    private final boolean zzkp;
    private final boolean zzkq;

    public zzdl(String str, String str2, String str3, long j, boolean z, boolean z2) {
        this.zzib = str;
        this.zzjo = str2;
        this.zzhq = str3;
        this.zzko = j;
        this.zzkp = z;
        this.zzkq = z2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzjo, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhq, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzko);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzkp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzkq);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
