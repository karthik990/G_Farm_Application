package com.google.android.gms.internal.gtm;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class zzbl implements Creator<zzbk> {
    zzbl() {
    }

    @Deprecated
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbk[i];
    }

    @Deprecated
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzbk(parcel);
    }
}
