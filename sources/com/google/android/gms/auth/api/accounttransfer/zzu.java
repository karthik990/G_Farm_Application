package com.google.android.gms.auth.api.accounttransfer;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException;
import java.util.HashSet;

public final class zzu implements Creator<zzt> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzt[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        HashSet hashSet = new HashSet();
        String str = null;
        byte[] bArr = null;
        PendingIntent pendingIntent = null;
        DeviceMetaData deviceMetaData = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case 3:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case 4:
                    bArr = SafeParcelReader.createByteArray(parcel, readHeader);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case 5:
                    pendingIntent = (PendingIntent) SafeParcelReader.createParcelable(parcel, readHeader, PendingIntent.CREATOR);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case 6:
                    deviceMetaData = (DeviceMetaData) SafeParcelReader.createParcelable(parcel, readHeader, DeviceMetaData.CREATOR);
                    hashSet.add(Integer.valueOf(6));
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        if (parcel.dataPosition() == validateObjectHeader) {
            zzt zzt = new zzt(hashSet, i, str, i2, bArr, pendingIntent, deviceMetaData);
            return zzt;
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(validateObjectHeader);
        throw new ParseException(sb.toString(), parcel);
    }
}
