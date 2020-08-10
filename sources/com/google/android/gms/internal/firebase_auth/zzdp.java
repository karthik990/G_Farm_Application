package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzdp extends AbstractSafeParcelable {
    public static final Creator<zzdp> CREATOR = new zzdo();
    private final String zzju;
    private final String zzkf;

    public zzdp(String str, String str2) {
        this.zzkf = str;
        this.zzju = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkf, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzju, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
