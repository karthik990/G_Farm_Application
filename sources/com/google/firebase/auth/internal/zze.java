package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.AdditionalUserInfo;
import java.util.Map;
import javax.annotation.Nullable;

public final class zze implements AdditionalUserInfo {
    public static final Creator<zze> CREATOR = new zzd();
    private final String zzia;
    private boolean zzrg;
    private final String zzsd;
    private Map<String, Object> zztc;

    public zze(String str, String str2, boolean z) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        this.zzia = str;
        this.zzsd = str2;
        this.zztc = zzam.zzde(str2);
        this.zzrg = z;
    }

    public final int describeContents() {
        return 0;
    }

    public zze(boolean z) {
        this.zzrg = z;
        this.zzsd = null;
        this.zzia = null;
        this.zztc = null;
    }

    @Nullable
    public final String getProviderId() {
        return this.zzia;
    }

    @Nullable
    public final Map<String, Object> getProfile() {
        return this.zztc;
    }

    @Nullable
    public final String getUsername() {
        if ("github.com".equals(this.zzia)) {
            return (String) this.zztc.get("login");
        }
        if ("twitter.com".equals(this.zzia)) {
            return (String) this.zztc.get("screen_name");
        }
        return null;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getProviderId(), false);
        SafeParcelWriter.writeString(parcel, 2, this.zzsd, false);
        SafeParcelWriter.writeBoolean(parcel, 3, isNewUser());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
