package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.io.InputStream;

@zzadh
public final class zzhi extends AbstractSafeParcelable {
    public static final Creator<zzhi> CREATOR = new zzhj();
    private ParcelFileDescriptor zzaju;

    public zzhi() {
        this(null);
    }

    public zzhi(ParcelFileDescriptor parcelFileDescriptor) {
        this.zzaju = parcelFileDescriptor;
    }

    private final synchronized ParcelFileDescriptor zzhk() {
        return this.zzaju;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, zzhk(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final synchronized boolean zzhi() {
        return this.zzaju != null;
    }

    public final synchronized InputStream zzhj() {
        if (this.zzaju == null) {
            return null;
        }
        AutoCloseInputStream autoCloseInputStream = new AutoCloseInputStream(this.zzaju);
        this.zzaju = null;
        return autoCloseInputStream;
    }
}
