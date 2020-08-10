package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.zzf;
import java.util.List;

public final class zzem extends AbstractSafeParcelable {
    public static final Creator<zzem> CREATOR = new zzep();
    private String zzif;
    private String zzig;
    private String zzjo;
    private String zzjv;
    private String zzkc;
    private zzf zzkw;
    private List<zzeu> zzky;
    private String zzrf;
    private boolean zzrg;
    private boolean zzrp;
    private zzey zzrq;
    private long zzrr;
    private long zzrs;

    public zzem() {
        this.zzrq = new zzey();
    }

    public zzem(String str, String str2, boolean z, String str3, String str4, zzey zzey, String str5, String str6, long j, long j2, boolean z2, zzf zzf, List<zzeu> list) {
        zzey zzey2;
        this.zzrf = str;
        this.zzif = str2;
        this.zzrp = z;
        this.zzjv = str3;
        this.zzkc = str4;
        if (zzey == null) {
            zzey2 = new zzey();
        } else {
            zzey2 = zzey.zza(zzey);
        }
        this.zzrq = zzey2;
        this.zzig = str5;
        this.zzjo = str6;
        this.zzrr = j;
        this.zzrs = j2;
        this.zzrg = z2;
        this.zzkw = zzf;
        if (list == null) {
            list = zzay.zzce();
        }
        this.zzky = list;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final boolean isEmailVerified() {
        return this.zzrp;
    }

    public final String getLocalId() {
        return this.zzrf;
    }

    public final String getDisplayName() {
        return this.zzjv;
    }

    public final Uri getPhotoUri() {
        if (!TextUtils.isEmpty(this.zzkc)) {
            return Uri.parse(this.zzkc);
        }
        return null;
    }

    public final String getPhoneNumber() {
        return this.zzjo;
    }

    public final long getCreationTimestamp() {
        return this.zzrr;
    }

    public final long getLastSignInTimestamp() {
        return this.zzrs;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final zzem zzcf(String str) {
        this.zzif = str;
        return this;
    }

    public final zzem zzcg(String str) {
        this.zzjv = str;
        return this;
    }

    public final zzem zzch(String str) {
        this.zzkc = str;
        return this;
    }

    public final zzem zzci(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzig = str;
        return this;
    }

    public final zzem zzc(List<zzew> list) {
        Preconditions.checkNotNull(list);
        this.zzrq = new zzey();
        this.zzrq.zzes().addAll(list);
        return this;
    }

    public final zzem zzo(boolean z) {
        this.zzrg = z;
        return this;
    }

    public final List<zzew> zzes() {
        return this.zzrq.zzes();
    }

    public final zzey zzet() {
        return this.zzrq;
    }

    public final zzf zzdo() {
        return this.zzkw;
    }

    public final zzem zza(zzf zzf) {
        this.zzkw = zzf;
        return this;
    }

    public final List<zzeu> zzbc() {
        return this.zzky;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzrf, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzif, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzrp);
        SafeParcelWriter.writeString(parcel, 5, this.zzjv, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzkc, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzrq, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzjo, false);
        SafeParcelWriter.writeLong(parcel, 10, this.zzrr);
        SafeParcelWriter.writeLong(parcel, 11, this.zzrs);
        SafeParcelWriter.writeBoolean(parcel, 12, this.zzrg);
        SafeParcelWriter.writeParcelable(parcel, 13, this.zzkw, i, false);
        SafeParcelWriter.writeTypedList(parcel, 14, this.zzky, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
