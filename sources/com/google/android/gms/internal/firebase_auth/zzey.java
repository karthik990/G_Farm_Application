package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzey extends AbstractSafeParcelable {
    public static final Creator<zzey> CREATOR = new zzfb();
    private List<zzew> zzse;

    public zzey() {
        this.zzse = new ArrayList();
    }

    zzey(List<zzew> list) {
        if (list == null || list.isEmpty()) {
            this.zzse = Collections.emptyList();
        } else {
            this.zzse = Collections.unmodifiableList(list);
        }
    }

    public final List<zzew> zzes() {
        return this.zzse;
    }

    public static zzey zza(zzey zzey) {
        List<zzew> list = zzey.zzse;
        zzey zzey2 = new zzey();
        if (list != null) {
            zzey2.zzse.addAll(list);
        }
        return zzey2;
    }

    public static zzey zze(List<zzu> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            zzu zzu = (zzu) list.get(i);
            zzew zzew = new zzew(Strings.emptyToNull(zzu.zzbo()), Strings.emptyToNull(zzu.getDisplayName()), Strings.emptyToNull(zzu.zzam()), Strings.emptyToNull(zzu.getProviderId()), null, Strings.emptyToNull(zzu.getPhoneNumber()), Strings.emptyToNull(zzu.getEmail()));
            arrayList.add(zzew);
        }
        return new zzey(arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzse, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
