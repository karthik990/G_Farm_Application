package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class EmailAuthCredential extends AuthCredential {
    public static final Creator<EmailAuthCredential> CREATOR = new zzg();
    private String zzif;
    private String zzig;
    private final String zzih;
    private String zzii;
    private boolean zzij;

    EmailAuthCredential(String str, String str2, String str3, String str4, boolean z) {
        this.zzif = Preconditions.checkNotEmpty(str);
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            this.zzig = str2;
            this.zzih = str3;
            this.zzii = str4;
            this.zzij = z;
            return;
        }
        throw new IllegalArgumentException("Cannot create an EmailAuthCredential without a password or emailLink.");
    }

    public String getProvider() {
        return "password";
    }

    EmailAuthCredential(String str, String str2) {
        this(str, str2, null, null, false);
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String getPassword() {
        return this.zzig;
    }

    public final String zzco() {
        return this.zzih;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final boolean zzcq() {
        return this.zzij;
    }

    public final EmailAuthCredential zza(FirebaseUser firebaseUser) {
        this.zzii = firebaseUser.zzcz();
        this.zzij = true;
        return this;
    }

    public String getSignInMethod() {
        return !TextUtils.isEmpty(this.zzig) ? "password" : "emailLink";
    }

    public final boolean zzcr() {
        return !TextUtils.isEmpty(this.zzih);
    }

    public static boolean isSignInWithEmailLink(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        zzb zzbr = zzb.zzbr(str);
        if (zzbr == null || zzbr.getOperation() != 4) {
            return false;
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzih, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzii, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzij);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
