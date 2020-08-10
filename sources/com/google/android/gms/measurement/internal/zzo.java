package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzo extends AbstractSafeParcelable {
    public static final Creator<zzo> CREATOR = new zzp();
    public boolean active;
    public long creationTimestamp;
    public String origin;
    public String packageName;
    public long timeToLive;
    public String triggerEventName;
    public long triggerTimeout;
    public zzfu zzags;
    public zzag zzagt;
    public zzag zzagu;
    public zzag zzagv;

    zzo(zzo zzo) {
        Preconditions.checkNotNull(zzo);
        this.packageName = zzo.packageName;
        this.origin = zzo.origin;
        this.zzags = zzo.zzags;
        this.creationTimestamp = zzo.creationTimestamp;
        this.active = zzo.active;
        this.triggerEventName = zzo.triggerEventName;
        this.zzagt = zzo.zzagt;
        this.triggerTimeout = zzo.triggerTimeout;
        this.zzagu = zzo.zzagu;
        this.timeToLive = zzo.timeToLive;
        this.zzagv = zzo.zzagv;
    }

    zzo(String str, String str2, zzfu zzfu, long j, boolean z, String str3, zzag zzag, long j2, zzag zzag2, long j3, zzag zzag3) {
        this.packageName = str;
        this.origin = str2;
        this.zzags = zzfu;
        this.creationTimestamp = j;
        this.active = z;
        this.triggerEventName = str3;
        this.zzagt = zzag;
        this.triggerTimeout = j2;
        this.zzagu = zzag2;
        this.timeToLive = j3;
        this.zzagv = zzag3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.origin, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzags, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.creationTimestamp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.active);
        SafeParcelWriter.writeString(parcel, 7, this.triggerEventName, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzagt, i, false);
        SafeParcelWriter.writeLong(parcel, 9, this.triggerTimeout);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzagu, i, false);
        SafeParcelWriter.writeLong(parcel, 11, this.timeToLive);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzagv, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
