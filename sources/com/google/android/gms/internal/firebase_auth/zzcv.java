package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzcv extends AbstractSafeParcelable {
    public static final Creator<zzcv> CREATOR = new zzcu();
    private final zzfe zzkl;

    public zzcv(zzfe zzfe) {
        this.zzkl = zzfe;
    }

    public final zzfe zzdk() {
        return this.zzkl;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkl, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
