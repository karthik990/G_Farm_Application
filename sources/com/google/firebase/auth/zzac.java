package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzbl;
import com.mobiroller.constants.ChatConstants;
import javax.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzac extends zzx {
    public static final Creator<zzac> CREATOR = new zzab();
    private final String zzjo;
    private final String zzju;
    @Nullable
    private final String zzjv;
    private final long zzjw;

    public zzac(String str, @Nullable String str2, long j, String str3) {
        this.zzju = Preconditions.checkNotEmpty(str);
        this.zzjv = str2;
        this.zzjw = j;
        this.zzjo = Preconditions.checkNotEmpty(str3);
    }

    public static zzac zza(JSONObject jSONObject) {
        String str = "enrollmentTimestamp";
        if (jSONObject.has(str)) {
            zzac zzac = new zzac(jSONObject.optString(ChatConstants.ARG_USER_INFO_UID), jSONObject.optString("displayName"), jSONObject.optLong(str), jSONObject.optString(PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY));
            return zzac;
        }
        throw new IllegalArgumentException("An enrollment timestamp in seconds of UTC time since Unix epoch is required to build a PhoneMultiFactorInfo instance.");
    }

    @Nullable
    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("factorIdKey", "phone");
            jSONObject.putOpt(ChatConstants.ARG_USER_INFO_UID, this.zzju);
            jSONObject.putOpt("displayName", this.zzjv);
            jSONObject.putOpt("enrollmentTimestamp", Long.valueOf(this.zzjw));
            jSONObject.putOpt(PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY, this.zzjo);
            return jSONObject;
        } catch (JSONException e) {
            Log.d("PhoneMultiFactorInfo", "Failed to jsonify this object");
            throw new zzbl((Throwable) e);
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzju, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzjv, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzjw);
        SafeParcelWriter.writeString(parcel, 4, this.zzjo, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
