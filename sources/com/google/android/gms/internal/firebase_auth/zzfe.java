package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzp.zzk;
import com.google.android.gms.internal.firebase_auth.zzp.zzk.zza;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfe extends AbstractSafeParcelable implements zzfd<zzk> {
    public static final Creator<zzfe> CREATOR = new zzfh();
    private final String zzhy;
    private final String zzjo;
    private final long zzko;
    private final boolean zzsf;
    private final String zzsg;

    public zzfe(String str, long j, boolean z, String str2, String str3) {
        this.zzjo = Preconditions.checkNotEmpty(str);
        this.zzko = j;
        this.zzsf = z;
        this.zzsg = str2;
        this.zzhy = str3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzjo, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzko);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzsf);
        SafeParcelWriter.writeString(parcel, 4, this.zzsg, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzak = zzk.zzah().zzak(this.zzjo);
        String str = this.zzhy;
        if (str != null) {
            zzak.zzal(str);
        }
        return (zzk) ((zzhs) zzak.zzih());
    }
}
