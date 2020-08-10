package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzfk extends AbstractSafeParcelable {
    public static final Creator<zzfk> CREATOR = new zzfn();
    private final int versionCode;
    private List<String> zzsm;

    public final List<String> zzez() {
        return this.zzsm;
    }

    public zzfk() {
        this(null);
    }

    private zzfk(List<String> list) {
        this.versionCode = 1;
        this.zzsm = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.zzsm.addAll(list);
        }
    }

    zzfk(int i, List<String> list) {
        this.versionCode = i;
        if (list == null || list.isEmpty()) {
            this.zzsm = Collections.emptyList();
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            list.set(i2, Strings.emptyToNull((String) list.get(i2)));
        }
        this.zzsm = Collections.unmodifiableList(list);
    }

    public static zzfk zzfa() {
        return new zzfk(null);
    }

    public static zzfk zza(zzfk zzfk) {
        return new zzfk(zzfk != null ? zzfk.zzsm : null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeStringList(parcel, 2, this.zzsm, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
