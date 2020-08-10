package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.FirebaseUserMetadata;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzo implements FirebaseUserMetadata {
    public static final Creator<zzo> CREATOR = new zzn();
    private long zzrr;
    private long zztr;

    public zzo(long j, long j2) {
        this.zztr = j;
        this.zzrr = j2;
    }

    public final int describeContents() {
        return 0;
    }

    public final long getLastSignInTimestamp() {
        return this.zztr;
    }

    public final long getCreationTimestamp() {
        return this.zzrr;
    }

    public final JSONObject zzfg() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lastSignInTimestamp", this.zztr);
            jSONObject.put("creationTimestamp", this.zzrr);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public static zzo zzb(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return new zzo(jSONObject.getLong("lastSignInTimestamp"), jSONObject.getLong("creationTimestamp"));
        } catch (JSONException unused) {
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, getLastSignInTimestamp());
        SafeParcelWriter.writeLong(parcel, 2, getCreationTimestamp());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
