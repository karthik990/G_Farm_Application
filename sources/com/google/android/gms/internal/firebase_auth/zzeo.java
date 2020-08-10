package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzeo extends AbstractSafeParcelable {
    public static final Creator<zzeo> CREATOR = new zzer();
    private List<zzem> zzrt;

    public zzeo() {
        this.zzrt = new ArrayList();
    }

    zzeo(List<zzem> list) {
        List<zzem> list2;
        if (list == null) {
            list2 = Collections.emptyList();
        } else {
            list2 = Collections.unmodifiableList(list);
        }
        this.zzrt = list2;
    }

    public final List<zzem> zzer() {
        return this.zzrt;
    }

    public static zzeo zza(zzeo zzeo) {
        Preconditions.checkNotNull(zzeo);
        List<zzem> list = zzeo.zzrt;
        zzeo zzeo2 = new zzeo();
        if (list != null && !list.isEmpty()) {
            zzeo2.zzrt.addAll(list);
        }
        return zzeo2;
    }

    public static zzeo zza(zzg zzg) {
        ArrayList arrayList = new ArrayList(zzg.zzy());
        for (int i = 0; i < zzg.zzy(); i++) {
            zzz zzb = zzg.zzb(i);
            zzem zzem = r4;
            zzem zzem2 = new zzem(Strings.emptyToNull(zzb.getLocalId()), Strings.emptyToNull(zzb.getEmail()), zzb.zzao(), Strings.emptyToNull(zzb.getDisplayName()), Strings.emptyToNull(zzb.zzam()), zzey.zze(zzb.zzal()), Strings.emptyToNull(zzb.zzbu()), Strings.emptyToNull(zzb.getPhoneNumber()), zzb.zzbt(), zzb.zzbs(), false, null, zzeu.zzd(zzb.zzbc()));
            arrayList.add(zzem);
        }
        return new zzeo(arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzrt, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
