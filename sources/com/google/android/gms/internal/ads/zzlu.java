package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

@zzadh
public final class zzlu extends AbstractSafeParcelable {
    public static final Creator<zzlu> CREATOR = new zzlv();
    public final int zzasj;

    public zzlu(int i) {
        this.zzasj = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzasj);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
