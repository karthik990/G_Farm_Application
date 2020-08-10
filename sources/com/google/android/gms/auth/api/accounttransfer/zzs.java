package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException;
import java.util.HashSet;

public final class zzs implements Creator<zzr> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzr[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        zzt zzt = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(readHeader);
            if (fieldId == 1) {
                i = SafeParcelReader.readInt(parcel, readHeader);
                hashSet.add(Integer.valueOf(1));
            } else if (fieldId == 2) {
                zzt = (zzt) SafeParcelReader.createParcelable(parcel, readHeader, zzt.CREATOR);
                hashSet.add(Integer.valueOf(2));
            } else if (fieldId == 3) {
                str = SafeParcelReader.createString(parcel, readHeader);
                hashSet.add(Integer.valueOf(3));
            } else if (fieldId == 4) {
                str2 = SafeParcelReader.createString(parcel, readHeader);
                hashSet.add(Integer.valueOf(4));
            } else if (fieldId != 5) {
                SafeParcelReader.skipUnknownField(parcel, readHeader);
            } else {
                str3 = SafeParcelReader.createString(parcel, readHeader);
                hashSet.add(Integer.valueOf(5));
            }
        }
        if (parcel.dataPosition() == validateObjectHeader) {
            zzr zzr = new zzr(hashSet, i, zzt, str, str2, str3);
            return zzr;
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(validateObjectHeader);
        throw new ParseException(sb.toString(), parcel);
    }
}
