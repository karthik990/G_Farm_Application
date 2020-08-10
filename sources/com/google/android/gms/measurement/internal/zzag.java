package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzag extends AbstractSafeParcelable {
    public static final Creator<zzag> CREATOR = new zzah();
    public final String name;
    public final String origin;
    public final zzad zzahu;
    public final long zzaig;

    public zzag(String str, zzad zzad, String str2, long j) {
        this.name = str;
        this.zzahu = zzad;
        this.origin = str2;
        this.zzaig = j;
    }

    zzag(zzag zzag, long j) {
        Preconditions.checkNotNull(zzag);
        this.name = zzag.name;
        this.zzahu = zzag.zzahu;
        this.origin = zzag.origin;
        this.zzaig = j;
    }

    public final String toString() {
        String str = this.origin;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzahu);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(valueOf).length());
        sb.append("origin=");
        sb.append(str);
        sb.append(",name=");
        sb.append(str2);
        sb.append(",params=");
        sb.append(valueOf);
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzahu, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.origin, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzaig);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
