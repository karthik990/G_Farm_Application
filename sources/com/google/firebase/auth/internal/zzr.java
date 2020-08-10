package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzx;
import com.google.firebase.auth.zzy;
import java.util.ArrayList;
import java.util.List;

public final class zzr extends zzy {
    public static final Creator<zzr> CREATOR = new zzu();
    private String zzib;
    private String zzkg;
    private List<zzac> zzts;

    private zzr() {
    }

    zzr(String str, String str2, List<zzac> list) {
        this.zzib = str;
        this.zzkg = str2;
        this.zzts = list;
    }

    public static zzr zza(List<zzx> list, String str) {
        Preconditions.checkNotNull(list);
        Preconditions.checkNotEmpty(str);
        zzr zzr = new zzr();
        zzr.zzts = new ArrayList();
        for (zzx zzx : list) {
            if (zzx instanceof zzac) {
                zzr.zzts.add((zzac) zzx);
            }
        }
        zzr.zzkg = str;
        return zzr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkg, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzts, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
