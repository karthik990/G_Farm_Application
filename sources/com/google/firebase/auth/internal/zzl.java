package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.zzf;
import java.util.List;

public final class zzl implements Creator<zzm> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzm[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        zzes zzes = null;
        zzi zzi = null;
        String str = null;
        String str2 = null;
        List list = null;
        List list2 = null;
        String str3 = null;
        Boolean bool = null;
        zzo zzo = null;
        zzf zzf = null;
        zzao zzao = null;
        boolean z = false;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    zzes = (zzes) SafeParcelReader.createParcelable(parcel2, readHeader, zzes.CREATOR);
                    break;
                case 2:
                    zzi = (zzi) SafeParcelReader.createParcelable(parcel2, readHeader, zzi.CREATOR);
                    break;
                case 3:
                    str = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 4:
                    str2 = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 5:
                    list = SafeParcelReader.createTypedList(parcel2, readHeader, zzi.CREATOR);
                    break;
                case 6:
                    list2 = SafeParcelReader.createStringList(parcel2, readHeader);
                    break;
                case 7:
                    str3 = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 8:
                    bool = SafeParcelReader.readBooleanObject(parcel2, readHeader);
                    break;
                case 9:
                    zzo = (zzo) SafeParcelReader.createParcelable(parcel2, readHeader, zzo.CREATOR);
                    break;
                case 10:
                    z = SafeParcelReader.readBoolean(parcel2, readHeader);
                    break;
                case 11:
                    zzf = (zzf) SafeParcelReader.createParcelable(parcel2, readHeader, zzf.CREATOR);
                    break;
                case 12:
                    zzao = (zzao) SafeParcelReader.createParcelable(parcel2, readHeader, zzao.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel2, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel2, validateObjectHeader);
        zzm zzm = new zzm(zzes, zzi, str, str2, list, list2, str3, bool, zzo, z, zzf, zzao);
        return zzm;
    }
}
