package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

@zzadh
public final class zzmq extends AbstractSafeParcelable {
    public static final Creator<zzmq> CREATOR = new zzmr();
    public final String zzatn;

    public zzmq(SearchAdRequest searchAdRequest) {
        this.zzatn = searchAdRequest.getQuery();
    }

    zzmq(String str) {
        this.zzatn = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 15, this.zzatn, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
