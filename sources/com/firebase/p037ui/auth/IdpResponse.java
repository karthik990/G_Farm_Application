package com.firebase.p037ui.auth;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

/* renamed from: com.firebase.ui.auth.IdpResponse */
public class IdpResponse implements Parcelable {
    public static final Creator<IdpResponse> CREATOR = new Creator<IdpResponse>() {
        public IdpResponse createFromParcel(Parcel parcel) {
            User user = (User) parcel.readParcelable(User.class.getClassLoader());
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            IdpResponse idpResponse = new IdpResponse(user, readString, readString2, z, (FirebaseUiException) parcel.readSerializable(), (AuthCredential) parcel.readParcelable(AuthCredential.class.getClassLoader()));
            return idpResponse;
        }

        public IdpResponse[] newArray(int i) {
            return new IdpResponse[i];
        }
    };
    private final FirebaseUiException mException;
    /* access modifiers changed from: private */
    public final boolean mIsNewUser;
    /* access modifiers changed from: private */
    public final AuthCredential mPendingCredential;
    /* access modifiers changed from: private */
    public final String mSecret;
    /* access modifiers changed from: private */
    public final String mToken;
    /* access modifiers changed from: private */
    public final User mUser;

    /* renamed from: com.firebase.ui.auth.IdpResponse$Builder */
    public static class Builder {
        private boolean mIsNewUser;
        private final AuthCredential mPendingCredential;
        private String mSecret;
        private String mToken;
        private final User mUser;

        public Builder(User user) {
            this.mUser = user;
            this.mPendingCredential = null;
        }

        public Builder(AuthCredential authCredential) {
            this.mUser = null;
            this.mPendingCredential = authCredential;
        }

        public Builder(IdpResponse idpResponse) {
            this.mUser = idpResponse.mUser;
            this.mToken = idpResponse.mToken;
            this.mSecret = idpResponse.mSecret;
            this.mIsNewUser = idpResponse.mIsNewUser;
            this.mPendingCredential = idpResponse.mPendingCredential;
        }

        public Builder setNewUser(boolean z) {
            this.mIsNewUser = z;
            return this;
        }

        public Builder setToken(String str) {
            this.mToken = str;
            return this;
        }

        public Builder setSecret(String str) {
            this.mSecret = str;
            return this;
        }

        public IdpResponse build() {
            AuthCredential authCredential = this.mPendingCredential;
            if (authCredential != null) {
                return new IdpResponse(authCredential, new FirebaseUiException(5));
            }
            String providerId = this.mUser.getProviderId();
            if (!AuthUI.SUPPORTED_PROVIDERS.contains(providerId)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown provider: ");
                sb.append(providerId);
                throw new IllegalStateException(sb.toString());
            } else if (AuthUI.SOCIAL_PROVIDERS.contains(providerId) && TextUtils.isEmpty(this.mToken)) {
                throw new IllegalStateException("Token cannot be null when using a non-email provider.");
            } else if (!providerId.equals("twitter.com") || !TextUtils.isEmpty(this.mSecret)) {
                IdpResponse idpResponse = new IdpResponse(this.mUser, this.mToken, this.mSecret, this.mIsNewUser);
                return idpResponse;
            } else {
                throw new IllegalStateException("Secret cannot be null when using the Twitter provider.");
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    private IdpResponse(FirebaseUiException firebaseUiException) {
        this(null, null, null, false, firebaseUiException, null);
    }

    private IdpResponse(User user, String str, String str2, boolean z) {
        this(user, str, str2, z, null, null);
    }

    private IdpResponse(AuthCredential authCredential, FirebaseUiException firebaseUiException) {
        this(null, null, null, false, firebaseUiException, authCredential);
    }

    private IdpResponse(User user, String str, String str2, boolean z, FirebaseUiException firebaseUiException, AuthCredential authCredential) {
        this.mUser = user;
        this.mToken = str;
        this.mSecret = str2;
        this.mIsNewUser = z;
        this.mException = firebaseUiException;
        this.mPendingCredential = authCredential;
    }

    public static IdpResponse fromResultIntent(Intent intent) {
        if (intent != null) {
            return (IdpResponse) intent.getParcelableExtra(ExtraConstants.IDP_RESPONSE);
        }
        return null;
    }

    public static IdpResponse from(Exception exc) {
        if (exc instanceof FirebaseUiException) {
            return new IdpResponse((FirebaseUiException) exc);
        }
        FirebaseUiException firebaseUiException = new FirebaseUiException(0, (Throwable) exc);
        firebaseUiException.setStackTrace(exc.getStackTrace());
        return new IdpResponse(firebaseUiException);
    }

    public static Intent getErrorIntent(Exception exc) {
        return from(exc).toIntent();
    }

    public IdpResponse withResult(AuthResult authResult) {
        return mutate().setNewUser(authResult.getAdditionalUserInfo().isNewUser()).build();
    }

    public Intent toIntent() {
        return new Intent().putExtra(ExtraConstants.IDP_RESPONSE, this);
    }

    public Builder mutate() {
        if (isSuccessful()) {
            return new Builder(this);
        }
        throw new IllegalStateException("Cannot mutate an unsuccessful response.");
    }

    public boolean isSuccessful() {
        return this.mException == null;
    }

    public User getUser() {
        return this.mUser;
    }

    public String getProviderType() {
        return this.mUser.getProviderId();
    }

    public boolean isNewUser() {
        return this.mIsNewUser;
    }

    public String getEmail() {
        return this.mUser.getEmail();
    }

    public String getPhoneNumber() {
        return this.mUser.getPhoneNumber();
    }

    public String getIdpToken() {
        return this.mToken;
    }

    public String getIdpSecret() {
        return this.mSecret;
    }

    public FirebaseUiException getError() {
        return this.mException;
    }

    public AuthCredential getCredentialForLinking() {
        return this.mPendingCredential;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:10|9|12|13|(0)|17|19) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0033, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0035 */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0069 A[SYNTHETIC, Splitter:B:15:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0074 A[SYNTHETIC, Splitter:B:21:0x0074] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeToParcel(android.os.Parcel r5, int r6) {
        /*
            r4 = this;
            com.firebase.ui.auth.data.model.User r0 = r4.mUser
            r5.writeParcelable(r0, r6)
            java.lang.String r6 = r4.mToken
            r5.writeString(r6)
            java.lang.String r6 = r4.mSecret
            r5.writeString(r6)
            boolean r6 = r4.mIsNewUser
            r5.writeInt(r6)
            r6 = 0
            r0 = 0
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0035 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0035 }
            r2.<init>()     // Catch:{ IOException -> 0x0035 }
            r1.<init>(r2)     // Catch:{ IOException -> 0x0035 }
            com.firebase.ui.auth.FirebaseUiException r6 = r4.mException     // Catch:{ IOException -> 0x0031, all -> 0x002e }
            r1.writeObject(r6)     // Catch:{ IOException -> 0x0031, all -> 0x002e }
            com.firebase.ui.auth.FirebaseUiException r6 = r4.mException     // Catch:{ IOException -> 0x0031, all -> 0x002e }
            r5.writeSerializable(r6)     // Catch:{ IOException -> 0x0031, all -> 0x002e }
            r1.close()     // Catch:{ IOException -> 0x006c }
            goto L_0x006c
        L_0x002e:
            r5 = move-exception
            r6 = r1
            goto L_0x0072
        L_0x0031:
            r6 = r1
            goto L_0x0035
        L_0x0033:
            r5 = move-exception
            goto L_0x0072
        L_0x0035:
            com.firebase.ui.auth.FirebaseUiException r1 = new com.firebase.ui.auth.FirebaseUiException     // Catch:{ all -> 0x0033 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0033 }
            r2.<init>()     // Catch:{ all -> 0x0033 }
            java.lang.String r3 = "Exception serialization error, forced wrapping. Original: "
            r2.append(r3)     // Catch:{ all -> 0x0033 }
            com.firebase.ui.auth.FirebaseUiException r3 = r4.mException     // Catch:{ all -> 0x0033 }
            r2.append(r3)     // Catch:{ all -> 0x0033 }
            java.lang.String r3 = ", original cause: "
            r2.append(r3)     // Catch:{ all -> 0x0033 }
            com.firebase.ui.auth.FirebaseUiException r3 = r4.mException     // Catch:{ all -> 0x0033 }
            java.lang.Throwable r3 = r3.getCause()     // Catch:{ all -> 0x0033 }
            r2.append(r3)     // Catch:{ all -> 0x0033 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0033 }
            r1.<init>(r0, r2)     // Catch:{ all -> 0x0033 }
            com.firebase.ui.auth.FirebaseUiException r2 = r4.mException     // Catch:{ all -> 0x0033 }
            java.lang.StackTraceElement[] r2 = r2.getStackTrace()     // Catch:{ all -> 0x0033 }
            r1.setStackTrace(r2)     // Catch:{ all -> 0x0033 }
            r5.writeSerializable(r1)     // Catch:{ all -> 0x0033 }
            if (r6 == 0) goto L_0x006c
            r6.close()     // Catch:{ IOException -> 0x006c }
        L_0x006c:
            com.google.firebase.auth.AuthCredential r6 = r4.mPendingCredential
            r5.writeParcelable(r6, r0)
            return
        L_0x0072:
            if (r6 == 0) goto L_0x0077
            r6.close()     // Catch:{ IOException -> 0x0077 }
        L_0x0077:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.IdpResponse.writeToParcel(android.os.Parcel, int):void");
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IdpResponse idpResponse = (IdpResponse) obj;
        User user = this.mUser;
        if (user != null ? user.equals(idpResponse.mUser) : idpResponse.mUser == null) {
            String str = this.mToken;
            if (str != null ? str.equals(idpResponse.mToken) : idpResponse.mToken == null) {
                String str2 = this.mSecret;
                if (str2 != null ? str2.equals(idpResponse.mSecret) : idpResponse.mSecret == null) {
                    if (this.mIsNewUser == idpResponse.mIsNewUser) {
                        FirebaseUiException firebaseUiException = this.mException;
                        if (firebaseUiException != null ? firebaseUiException.equals(idpResponse.mException) : idpResponse.mException == null) {
                            AuthCredential authCredential = this.mPendingCredential;
                            if (authCredential != null) {
                            }
                        }
                    }
                }
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        User user = this.mUser;
        int i = 0;
        int hashCode = (user == null ? 0 : user.hashCode()) * 31;
        String str = this.mToken;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mSecret;
        int hashCode3 = (((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + (this.mIsNewUser ? 1 : 0)) * 31;
        FirebaseUiException firebaseUiException = this.mException;
        int hashCode4 = (hashCode3 + (firebaseUiException == null ? 0 : firebaseUiException.hashCode())) * 31;
        AuthCredential authCredential = this.mPendingCredential;
        if (authCredential != null) {
            i = authCredential.getProvider().hashCode();
        }
        return hashCode4 + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdpResponse{mUser=");
        sb.append(this.mUser);
        sb.append(", mToken='");
        sb.append(this.mToken);
        sb.append('\'');
        sb.append(", mSecret='");
        sb.append(this.mSecret);
        sb.append('\'');
        sb.append(", mIsNewUser='");
        sb.append(this.mIsNewUser);
        sb.append('\'');
        sb.append(", mException=");
        sb.append(this.mException);
        sb.append(", mPendingCredential=");
        sb.append(this.mPendingCredential);
        sb.append('}');
        return sb.toString();
    }
}
