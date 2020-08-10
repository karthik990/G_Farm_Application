package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzf implements Creator<zzg> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzg[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        zzm zzm = null;
        zze zze = null;
        com.google.firebase.auth.zzf zzf = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(readHeader);
            if (fieldId == 1) {
                zzm = (zzm) SafeParcelReader.createParcelable(parcel, readHeader, zzm.CREATOR);
            } else if (fieldId == 2) {
                zze = (zze) SafeParcelReader.createParcelable(parcel, readHeader, zze.CREATOR);
            } else if (fieldId != 3) {
                SafeParcelReader.skipUnknownField(parcel, readHeader);
            } else {
                zzf = (com.google.firebase.auth.zzf) SafeParcelReader.createParcelable(parcel, readHeader, com.google.firebase.auth.zzf.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzg(zzm, zze, zzf);
    }
}
