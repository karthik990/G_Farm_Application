package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzk extends AbstractSafeParcelable {
    public static final Creator<zzk> CREATOR = new zzl();
    public final String packageName;
    public final long zzade;
    public final String zzafi;
    public final String zzafk;
    public final long zzafo;
    public final String zzafp;
    public final long zzafq;
    public final boolean zzafr;
    public final long zzafs;
    public final boolean zzaft;
    public final boolean zzafu;
    public final String zzafv;
    public final String zzagm;
    public final boolean zzagn;
    public final long zzago;
    public final int zzagp;
    public final boolean zzagq;
    public final String zzts;

    zzk(String str, String str2, String str3, long j, String str4, long j2, long j3, String str5, boolean z, boolean z2, String str6, long j4, long j5, int i, boolean z3, boolean z4, boolean z5, String str7) {
        Preconditions.checkNotEmpty(str);
        this.packageName = str;
        this.zzafi = TextUtils.isEmpty(str2) ? null : str2;
        this.zzts = str3;
        this.zzafo = j;
        this.zzafp = str4;
        this.zzade = j2;
        this.zzafq = j3;
        this.zzagm = str5;
        this.zzafr = z;
        this.zzagn = z2;
        this.zzafk = str6;
        this.zzafs = j4;
        this.zzago = j5;
        this.zzagp = i;
        this.zzaft = z3;
        this.zzafu = z4;
        this.zzagq = z5;
        this.zzafv = str7;
    }

    zzk(String str, String str2, String str3, String str4, long j, long j2, String str5, boolean z, boolean z2, long j3, String str6, long j4, long j5, int i, boolean z3, boolean z4, boolean z5, String str7) {
        this.packageName = str;
        this.zzafi = str2;
        this.zzts = str3;
        this.zzafo = j3;
        this.zzafp = str4;
        this.zzade = j;
        this.zzafq = j2;
        this.zzagm = str5;
        this.zzafr = z;
        this.zzagn = z2;
        this.zzafk = str6;
        this.zzafs = j4;
        this.zzago = j5;
        this.zzagp = i;
        this.zzaft = z3;
        this.zzafu = z4;
        this.zzagq = z5;
        this.zzafv = str7;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzafi, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzts, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzafp, false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzade);
        SafeParcelWriter.writeLong(parcel, 7, this.zzafq);
        SafeParcelWriter.writeString(parcel, 8, this.zzagm, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzafr);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzagn);
        SafeParcelWriter.writeLong(parcel, 11, this.zzafo);
        SafeParcelWriter.writeString(parcel, 12, this.zzafk, false);
        SafeParcelWriter.writeLong(parcel, 13, this.zzafs);
        SafeParcelWriter.writeLong(parcel, 14, this.zzago);
        SafeParcelWriter.writeInt(parcel, 15, this.zzagp);
        SafeParcelWriter.writeBoolean(parcel, 16, this.zzaft);
        SafeParcelWriter.writeBoolean(parcel, 17, this.zzafu);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzagq);
        SafeParcelWriter.writeString(parcel, 19, this.zzafv, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
