package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;
import javax.annotation.Nonnull;

public class Credential extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<Credential> CREATOR = new zzc();
    public static final String EXTRA_KEY = "com.google.android.gms.credentials.Credential";
    /* access modifiers changed from: private */
    @Nonnull
    public final String mId;
    /* access modifiers changed from: private */
    public final String mName;
    /* access modifiers changed from: private */
    public final Uri zzo;
    /* access modifiers changed from: private */
    @Nonnull
    public final List<IdToken> zzp;
    /* access modifiers changed from: private */
    public final String zzq;
    /* access modifiers changed from: private */
    public final String zzr;
    /* access modifiers changed from: private */
    public final String zzs;
    /* access modifiers changed from: private */
    public final String zzt;

    public static class Builder {
        private final String mId;
        private String mName;
        private Uri zzo;
        private List<IdToken> zzp;
        private String zzq;
        private String zzr;
        private String zzs;
        private String zzt;

        public Builder(String str) {
            this.mId = str;
        }

        public Builder(Credential credential) {
            this.mId = credential.mId;
            this.mName = credential.mName;
            this.zzo = credential.zzo;
            this.zzp = credential.zzp;
            this.zzq = credential.zzq;
            this.zzr = credential.zzr;
            this.zzs = credential.zzs;
            this.zzt = credential.zzt;
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setProfilePictureUri(Uri uri) {
            this.zzo = uri;
            return this;
        }

        public Builder setPassword(String str) {
            this.zzq = str;
            return this;
        }

        public Builder setAccountType(String str) {
            this.zzr = str;
            return this;
        }

        public Credential build() {
            Credential credential = new Credential(this.mId, this.mName, this.zzo, this.zzp, this.zzq, this.zzr, this.zzs, this.zzt);
            return credential;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0069, code lost:
        if ("https".equalsIgnoreCase(r0.getScheme()) != false) goto L_0x006b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    Credential(java.lang.String r5, java.lang.String r6, android.net.Uri r7, java.util.List<com.google.android.gms.auth.api.credentials.IdToken> r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            r4 = this;
            r4.<init>()
            java.lang.String r0 = "credential identifier cannot be null"
            java.lang.Object r5 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r5, r0)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r5 = r5.trim()
            java.lang.String r0 = "credential identifier cannot be empty"
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5, r0)
            if (r9 == 0) goto L_0x0025
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L_0x001d
            goto L_0x0025
        L_0x001d:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Password must not be empty if set"
            r5.<init>(r6)
            throw r5
        L_0x0025:
            if (r10 == 0) goto L_0x007f
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            r1 = 0
            if (r0 != 0) goto L_0x006c
            android.net.Uri r0 = android.net.Uri.parse(r10)
            boolean r2 = r0.isAbsolute()
            if (r2 == 0) goto L_0x006c
            boolean r2 = r0.isHierarchical()
            if (r2 == 0) goto L_0x006c
            java.lang.String r2 = r0.getScheme()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x006c
            java.lang.String r2 = r0.getAuthority()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0053
            goto L_0x006c
        L_0x0053:
            java.lang.String r2 = r0.getScheme()
            java.lang.String r3 = "http"
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x006b
            java.lang.String r0 = r0.getScheme()
            java.lang.String r2 = "https"
            boolean r0 = r2.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x006c
        L_0x006b:
            r1 = 1
        L_0x006c:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0077
            goto L_0x007f
        L_0x0077:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Account type must be a valid Http/Https URI"
            r5.<init>(r6)
            throw r5
        L_0x007f:
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 != 0) goto L_0x0094
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L_0x008c
            goto L_0x0094
        L_0x008c:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Password and AccountType are mutually exclusive"
            r5.<init>(r6)
            throw r5
        L_0x0094:
            if (r6 == 0) goto L_0x00a1
            java.lang.String r0 = r6.trim()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00a1
            r6 = 0
        L_0x00a1:
            r4.mName = r6
            r4.zzo = r7
            if (r8 != 0) goto L_0x00ac
            java.util.List r6 = java.util.Collections.emptyList()
            goto L_0x00b0
        L_0x00ac:
            java.util.List r6 = java.util.Collections.unmodifiableList(r8)
        L_0x00b0:
            r4.zzp = r6
            r4.mId = r5
            r4.zzq = r9
            r4.zzr = r10
            r4.zzs = r11
            r4.zzt = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.credentials.Credential.<init>(java.lang.String, java.lang.String, android.net.Uri, java.util.List, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    @Nonnull
    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public Uri getProfilePictureUri() {
        return this.zzo;
    }

    @Nonnull
    public List<IdToken> getIdTokens() {
        return this.zzp;
    }

    public String getPassword() {
        return this.zzq;
    }

    public String getAccountType() {
        return this.zzr;
    }

    public String getGivenName() {
        return this.zzs;
    }

    public String getFamilyName() {
        return this.zzt;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getId(), false);
        SafeParcelWriter.writeString(parcel, 2, getName(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getProfilePictureUri(), i, false);
        SafeParcelWriter.writeTypedList(parcel, 4, getIdTokens(), false);
        SafeParcelWriter.writeString(parcel, 5, getPassword(), false);
        SafeParcelWriter.writeString(parcel, 6, getAccountType(), false);
        SafeParcelWriter.writeString(parcel, 9, getGivenName(), false);
        SafeParcelWriter.writeString(parcel, 10, getFamilyName(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Credential)) {
            return false;
        }
        Credential credential = (Credential) obj;
        return TextUtils.equals(this.mId, credential.mId) && TextUtils.equals(this.mName, credential.mName) && Objects.equal(this.zzo, credential.zzo) && TextUtils.equals(this.zzq, credential.zzq) && TextUtils.equals(this.zzr, credential.zzr);
    }

    public int hashCode() {
        return Objects.hashCode(this.mId, this.mName, this.zzo, this.zzq, this.zzr);
    }
}
