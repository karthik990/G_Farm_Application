package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.zzf;
import java.util.List;

public final class zzg implements AuthResult {
    public static final Creator<zzg> CREATOR = new zzf();
    private zzm zztd;
    private zze zzte;
    private zzf zztf;

    zzg(zzm zzm, zze zze, zzf zzf) {
        this.zztd = zzm;
        this.zzte = zze;
        this.zztf = zzf;
    }

    public final int describeContents() {
        return 0;
    }

    public zzg(zzm zzm) {
        this.zztd = (zzm) Preconditions.checkNotNull(zzm);
        List zzff = this.zztd.zzff();
        this.zzte = null;
        for (int i = 0; i < zzff.size(); i++) {
            if (!TextUtils.isEmpty(((zzi) zzff.get(i)).getRawUserInfo())) {
                this.zzte = new zze(((zzi) zzff.get(i)).getProviderId(), ((zzi) zzff.get(i)).getRawUserInfo(), zzm.isNewUser());
            }
        }
        if (this.zzte == null) {
            this.zzte = new zze(zzm.isNewUser());
        }
        this.zztf = zzm.zzdo();
    }

    public final FirebaseUser getUser() {
        return this.zztd;
    }

    public final AdditionalUserInfo getAdditionalUserInfo() {
        return this.zzte;
    }

    public final AuthCredential getCredential() {
        return this.zztf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getUser(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getAdditionalUserInfo(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zztf, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
