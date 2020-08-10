package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class PhoneAuthCredential extends AuthCredential implements Cloneable {
    public static final Creator<PhoneAuthCredential> CREATOR = new zzaa();
    private String zzjl;
    private String zzjm;
    private boolean zzjn;
    private String zzjo;
    private boolean zzjp;
    private String zzjq;

    PhoneAuthCredential(String str, String str2, boolean z, String str3, boolean z2, String str4) {
        Preconditions.checkArgument((z && !TextUtils.isEmpty(str3)) || (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) || (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4)), "Cannot create PhoneAuthCredential without either verificationProof, sessionInfo, ortemprary proof.");
        this.zzjl = str;
        this.zzjm = str2;
        this.zzjn = z;
        this.zzjo = str3;
        this.zzjp = z2;
        this.zzjq = str4;
    }

    public String getProvider() {
        return "phone";
    }

    public String getSignInMethod() {
        return "phone";
    }

    public String getSmsCode() {
        return this.zzjm;
    }

    public final PhoneAuthCredential zzn(boolean z) {
        this.zzjp = false;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzjl, false);
        SafeParcelWriter.writeString(parcel, 2, getSmsCode(), false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzjn);
        SafeParcelWriter.writeString(parcel, 4, this.zzjo, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzjp);
        SafeParcelWriter.writeString(parcel, 6, this.zzjq, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        PhoneAuthCredential phoneAuthCredential = new PhoneAuthCredential(this.zzjl, getSmsCode(), this.zzjn, this.zzjo, this.zzjp, this.zzjq);
        return phoneAuthCredential;
    }
}
