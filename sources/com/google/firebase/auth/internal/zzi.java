package com.google.firebase.auth.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzbl;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzew;
import com.google.firebase.auth.UserInfo;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzi extends AbstractSafeParcelable implements UserInfo {
    public static final Creator<zzi> CREATOR = new zzh();
    private String zzia;
    private String zzif;
    private String zzjo;
    private String zzjv;
    private Uri zzjz;
    private String zzkc;
    private boolean zzrp;
    private String zzsd;
    private String zztg;

    public zzi(String str, String str2, String str3, String str4, String str5, String str6, boolean z, String str7) {
        this.zztg = str;
        this.zzia = str2;
        this.zzif = str3;
        this.zzjo = str4;
        this.zzjv = str5;
        this.zzkc = str6;
        if (!TextUtils.isEmpty(this.zzkc)) {
            this.zzjz = Uri.parse(this.zzkc);
        }
        this.zzrp = z;
        this.zzsd = str7;
    }

    public zzi(zzem zzem, String str) {
        Preconditions.checkNotNull(zzem);
        Preconditions.checkNotEmpty(str);
        this.zztg = Preconditions.checkNotEmpty(zzem.getLocalId());
        this.zzia = str;
        this.zzif = zzem.getEmail();
        this.zzjv = zzem.getDisplayName();
        Uri photoUri = zzem.getPhotoUri();
        if (photoUri != null) {
            this.zzkc = photoUri.toString();
            this.zzjz = photoUri;
        }
        this.zzrp = zzem.isEmailVerified();
        this.zzsd = null;
        this.zzjo = zzem.getPhoneNumber();
    }

    public zzi(zzew zzew) {
        Preconditions.checkNotNull(zzew);
        this.zztg = zzew.zzbo();
        this.zzia = Preconditions.checkNotEmpty(zzew.getProviderId());
        this.zzjv = zzew.getDisplayName();
        Uri photoUri = zzew.getPhotoUri();
        if (photoUri != null) {
            this.zzkc = photoUri.toString();
            this.zzjz = photoUri;
        }
        this.zzif = zzew.getEmail();
        this.zzjo = zzew.getPhoneNumber();
        this.zzrp = false;
        this.zzsd = zzew.getRawUserInfo();
    }

    public final String getUid() {
        return this.zztg;
    }

    public final String getProviderId() {
        return this.zzia;
    }

    public final String getDisplayName() {
        return this.zzjv;
    }

    public final Uri getPhotoUrl() {
        if (!TextUtils.isEmpty(this.zzkc) && this.zzjz == null) {
            this.zzjz = Uri.parse(this.zzkc);
        }
        return this.zzjz;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String getPhoneNumber() {
        return this.zzjo;
    }

    public final boolean isEmailVerified() {
        return this.zzrp;
    }

    public final String getRawUserInfo() {
        return this.zzsd;
    }

    public final String zzew() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("userId", this.zztg);
            jSONObject.putOpt("providerId", this.zzia);
            jSONObject.putOpt("displayName", this.zzjv);
            jSONObject.putOpt("photoUrl", this.zzkc);
            jSONObject.putOpt("email", this.zzif);
            jSONObject.putOpt(PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY, this.zzjo);
            jSONObject.putOpt("isEmailVerified", Boolean.valueOf(this.zzrp));
            jSONObject.putOpt("rawUserInfo", this.zzsd);
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.d("DefaultAuthUserInfo", "Failed to jsonify this object");
            throw new zzbl((Throwable) e);
        }
    }

    public static zzi zzda(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            zzi zzi = new zzi(jSONObject.optString("userId"), jSONObject.optString("providerId"), jSONObject.optString("email"), jSONObject.optString(PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY), jSONObject.optString("displayName"), jSONObject.optString("photoUrl"), jSONObject.optBoolean("isEmailVerified"), jSONObject.optString("rawUserInfo"));
            return zzi;
        } catch (JSONException e) {
            Log.d("DefaultAuthUserInfo", "Failed to unpack UserInfo from JSON");
            throw new zzbl((Throwable) e);
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getUid(), false);
        SafeParcelWriter.writeString(parcel, 2, getProviderId(), false);
        SafeParcelWriter.writeString(parcel, 3, getDisplayName(), false);
        SafeParcelWriter.writeString(parcel, 4, this.zzkc, false);
        SafeParcelWriter.writeString(parcel, 5, getEmail(), false);
        SafeParcelWriter.writeString(parcel, 6, getPhoneNumber(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, isEmailVerified());
        SafeParcelWriter.writeString(parcel, 8, this.zzsd, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
