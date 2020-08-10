package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

@zzadh
public final class zzmu extends AbstractSafeParcelable {
    public static final Creator<zzmu> CREATOR = new zzmv();
    public final boolean zzato;
    public final boolean zzatp;
    public final boolean zzatq;

    public zzmu(VideoOptions videoOptions) {
        this(videoOptions.getStartMuted(), videoOptions.getCustomControlsRequested(), videoOptions.getClickToExpandRequested());
    }

    public zzmu(boolean z, boolean z2, boolean z3) {
        this.zzato = z;
        this.zzatp = z2;
        this.zzatq = z3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzato);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzatp);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzatq);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}