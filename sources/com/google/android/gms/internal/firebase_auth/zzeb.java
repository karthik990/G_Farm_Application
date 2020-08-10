package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.internal.zzap;
import com.google.firebase.auth.zzf;
import com.google.firebase.auth.zzx;
import java.util.List;

public final class zzeb extends AbstractSafeParcelable {
    public static final Creator<zzeb> CREATOR = new zzea();
    private zzf zzkw;
    private String zzkx;
    private List<zzeu> zzky;

    public zzeb(String str, List<zzeu> list, zzf zzf) {
        this.zzkx = str;
        this.zzky = list;
        this.zzkw = zzf;
    }

    public final String zzbb() {
        return this.zzkx;
    }

    public final zzf zzdo() {
        return this.zzkw;
    }

    public final List<zzx> zzdp() {
        return zzap.zzg(this.zzky);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkx, false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzky, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzkw, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
