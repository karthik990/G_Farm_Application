package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

@zzadh
public final class zzang extends AbstractSafeParcelable {
    public static final Creator<zzang> CREATOR = new zzanh();
    public int zzcve;
    public int zzcvf;
    public boolean zzcvg;
    public boolean zzcvh;
    public String zzcw;

    public zzang(int i, int i2, boolean z) {
        this(i, i2, z, false, false);
    }

    public zzang(int i, int i2, boolean z, boolean z2) {
        this((int) GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE, i2, true, false, z2);
    }

    private zzang(int i, int i2, boolean z, boolean z2, boolean z3) {
        String str = z ? "0" : IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
        StringBuilder sb = new StringBuilder(str.length() + 36);
        sb.append("afma-sdk-a-v");
        sb.append(i);
        String str2 = ".";
        sb.append(str2);
        sb.append(i2);
        sb.append(str2);
        sb.append(str);
        this(sb.toString(), i, i2, z, z3);
    }

    zzang(String str, int i, int i2, boolean z, boolean z2) {
        this.zzcw = str;
        this.zzcve = i;
        this.zzcvf = i2;
        this.zzcvg = z;
        this.zzcvh = z2;
    }

    public static zzang zzsl() {
        return new zzang(12451009, 12451009, true);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzcw, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzcve);
        SafeParcelWriter.writeInt(parcel, 4, this.zzcvf);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzcvg);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzcvh);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
