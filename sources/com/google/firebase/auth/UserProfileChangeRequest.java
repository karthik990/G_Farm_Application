package com.google.firebase.auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class UserProfileChangeRequest extends AbstractSafeParcelable {
    public static final Creator<UserProfileChangeRequest> CREATOR = new zzaf();
    private String zzjv;
    private Uri zzjz;
    private boolean zzka;
    private boolean zzkb;
    private String zzkc;

    public static class Builder {
        private String zzjv;
        private Uri zzjz;
        private boolean zzka;
        private boolean zzkb;

        public Builder setDisplayName(String str) {
            if (str == null) {
                this.zzka = true;
            } else {
                this.zzjv = str;
            }
            return this;
        }

        public Builder setPhotoUri(Uri uri) {
            if (uri == null) {
                this.zzkb = true;
            } else {
                this.zzjz = uri;
            }
            return this;
        }

        public UserProfileChangeRequest build() {
            String str = this.zzjv;
            Uri uri = this.zzjz;
            return new UserProfileChangeRequest(str, uri == null ? null : uri.toString(), this.zzka, this.zzkb);
        }
    }

    UserProfileChangeRequest(String str, String str2, boolean z, boolean z2) {
        this.zzjv = str;
        this.zzkc = str2;
        this.zzka = z;
        this.zzkb = z2;
        this.zzjz = TextUtils.isEmpty(str2) ? null : Uri.parse(str2);
    }

    public String getDisplayName() {
        return this.zzjv;
    }

    public final String zzam() {
        return this.zzkc;
    }

    public Uri getPhotoUri() {
        return this.zzjz;
    }

    public final boolean zzde() {
        return this.zzka;
    }

    public final boolean zzdf() {
        return this.zzkb;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getDisplayName(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzkc, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzka);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzkb);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
