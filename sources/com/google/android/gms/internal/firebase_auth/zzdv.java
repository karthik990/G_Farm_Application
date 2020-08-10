package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.UserProfileChangeRequest;

public final class zzdv extends AbstractSafeParcelable {
    public static final Creator<zzdv> CREATOR = new zzdu();
    private final String zzii;
    private final UserProfileChangeRequest zzkt;

    public zzdv(UserProfileChangeRequest userProfileChangeRequest, String str) {
        this.zzkt = userProfileChangeRequest;
        this.zzii = str;
    }

    public final UserProfileChangeRequest zzdn() {
        return this.zzkt;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkt, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
