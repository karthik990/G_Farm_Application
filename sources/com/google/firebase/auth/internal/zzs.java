package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzf;
import java.util.ArrayList;

public final class zzs implements Creator<zzp> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzp[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList arrayList = null;
        zzr zzr = null;
        String str = null;
        zzf zzf = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(readHeader);
            if (fieldId == 1) {
                arrayList = SafeParcelReader.createTypedList(parcel, readHeader, zzac.CREATOR);
            } else if (fieldId == 2) {
                zzr = (zzr) SafeParcelReader.createParcelable(parcel, readHeader, zzr.CREATOR);
            } else if (fieldId == 3) {
                str = SafeParcelReader.createString(parcel, readHeader);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, readHeader);
            } else {
                zzf = (zzf) SafeParcelReader.createParcelable(parcel, readHeader, zzf.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzp(arrayList, zzr, str, zzf);
    }
}
