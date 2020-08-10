package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfw;

public class ActionCodeSettings extends AbstractSafeParcelable {
    public static final Creator<ActionCodeSettings> CREATOR = new zzc();
    private final String url;
    private final String zzhk;
    private final String zzhl;
    private final String zzhm;
    private final boolean zzhn;
    private final String zzho;
    private final boolean zzhp;
    private String zzhq;
    private int zzhr;
    private String zzhs;

    public static class Builder {
        /* access modifiers changed from: private */
        public String url;
        /* access modifiers changed from: private */
        public String zzhk;
        /* access modifiers changed from: private */
        public String zzhm;
        /* access modifiers changed from: private */
        public boolean zzhn;
        /* access modifiers changed from: private */
        public String zzho;
        /* access modifiers changed from: private */
        public boolean zzhp;
        /* access modifiers changed from: private */
        public String zzhs;

        private Builder() {
            this.zzhp = false;
        }

        public Builder setUrl(String str) {
            this.url = str;
            return this;
        }

        public Builder setIOSBundleId(String str) {
            this.zzhk = str;
            return this;
        }

        public Builder setAndroidPackageName(String str, boolean z, String str2) {
            this.zzhm = str;
            this.zzhn = z;
            this.zzho = str2;
            return this;
        }

        public Builder setHandleCodeInApp(boolean z) {
            this.zzhp = z;
            return this;
        }

        public Builder setDynamicLinkDomain(String str) {
            this.zzhs = str;
            return this;
        }

        public ActionCodeSettings build() {
            if (this.url != null) {
                return new ActionCodeSettings(this);
            }
            throw new IllegalArgumentException("Cannot build ActionCodeSettings with null URL. Call #setUrl(String) before calling build()");
        }
    }

    ActionCodeSettings(String str, String str2, String str3, String str4, boolean z, String str5, boolean z2, String str6, int i, String str7) {
        this.url = str;
        this.zzhk = str2;
        this.zzhl = str3;
        this.zzhm = str4;
        this.zzhn = z;
        this.zzho = str5;
        this.zzhp = z2;
        this.zzhq = str6;
        this.zzhr = i;
        this.zzhs = str7;
    }

    private ActionCodeSettings(Builder builder) {
        this.url = builder.url;
        this.zzhk = builder.zzhk;
        this.zzhl = null;
        this.zzhm = builder.zzhm;
        this.zzhn = builder.zzhn;
        this.zzho = builder.zzho;
        this.zzhp = builder.zzhp;
        this.zzhs = builder.zzhs;
    }

    public static ActionCodeSettings zzcj() {
        return new ActionCodeSettings(new Builder());
    }

    public String getUrl() {
        return this.url;
    }

    public String getIOSBundle() {
        return this.zzhk;
    }

    public final String zzck() {
        return this.zzhl;
    }

    public String getAndroidPackageName() {
        return this.zzhm;
    }

    public boolean getAndroidInstallApp() {
        return this.zzhn;
    }

    public String getAndroidMinimumVersion() {
        return this.zzho;
    }

    public boolean canHandleCodeInApp() {
        return this.zzhp;
    }

    public final void zzbq(String str) {
        this.zzhq = str;
    }

    public final String zzcl() {
        return this.zzhq;
    }

    public final void zzb(zzfw zzfw) {
        this.zzhr = zzfw.zzbq();
    }

    public final int getRequestType() {
        return this.zzhr;
    }

    public final String zzcm() {
        return this.zzhs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getUrl(), false);
        SafeParcelWriter.writeString(parcel, 2, getIOSBundle(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhl, false);
        SafeParcelWriter.writeString(parcel, 4, getAndroidPackageName(), false);
        SafeParcelWriter.writeBoolean(parcel, 5, getAndroidInstallApp());
        SafeParcelWriter.writeString(parcel, 6, getAndroidMinimumVersion(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, canHandleCodeInApp());
        SafeParcelWriter.writeString(parcel, 8, this.zzhq, false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzhr);
        SafeParcelWriter.writeString(parcel, 10, this.zzhs, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
