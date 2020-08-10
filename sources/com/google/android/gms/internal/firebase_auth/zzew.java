package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzew extends AbstractSafeParcelable {
    public static final Creator<zzew> CREATOR = new zzez();
    private String zzia;
    private String zzif;
    private String zzjo;
    private String zzjv;
    private String zzkc;
    private String zzsc;
    private String zzsd;

    public zzew() {
    }

    zzew(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.zzsc = str;
        this.zzjv = str2;
        this.zzkc = str3;
        this.zzia = str4;
        this.zzsd = str5;
        this.zzjo = str6;
        this.zzif = str7;
    }

    public final String zzbo() {
        return this.zzsc;
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

    public final String getProviderId() {
        return this.zzia;
    }

    public final String getPhoneNumber() {
        return this.zzjo;
    }

    public final void zzco(String str) {
        this.zzsd = str;
    }

    public final String getRawUserInfo() {
        return this.zzsd;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzsc, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzjv, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzkc, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzia, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzsd, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzjo, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzif, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
