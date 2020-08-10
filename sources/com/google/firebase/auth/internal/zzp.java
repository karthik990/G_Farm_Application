package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzf;
import com.google.firebase.auth.zzw;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;

public final class zzp extends zzw {
    public static final Creator<zzp> CREATOR = new zzs();
    private final zzf zzkw;
    private final String zztj;
    private final List<zzac> zzts = new ArrayList();
    private final zzr zztt;

    public zzp(List<zzac> list, zzr zzr, String str, zzf zzf) {
        for (zzx zzx : list) {
            if (zzx instanceof zzac) {
                this.zzts.add((zzac) zzx);
            }
        }
        this.zztt = (zzr) Preconditions.checkNotNull(zzr);
        this.zztj = Preconditions.checkNotEmpty(str);
        this.zzkw = zzf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzts, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zztt, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zztj, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzkw, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
