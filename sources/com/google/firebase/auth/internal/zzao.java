package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzay;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;

public final class zzao extends AbstractSafeParcelable {
    public static final Creator<zzao> CREATOR = new zzar();
    private final List<zzac> zzts;

    zzao(List<zzac> list) {
        if (list == null) {
            list = zzay.zzce();
        }
        this.zzts = list;
    }

    public static zzao zzf(List<zzx> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (zzx zzx : list) {
            if (zzx instanceof zzac) {
                arrayList.add((zzac) zzx);
            }
        }
        return new zzao(arrayList);
    }

    public final List<zzx> zzdp() {
        ArrayList arrayList = new ArrayList();
        for (zzac add : this.zzts) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzts, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
