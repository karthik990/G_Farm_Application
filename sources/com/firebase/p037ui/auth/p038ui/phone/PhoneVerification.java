package com.firebase.p037ui.auth.p038ui.phone;

import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.firebase.ui.auth.ui.phone.PhoneVerification */
public final class PhoneVerification {
    private final PhoneAuthCredential mCredential;
    private final boolean mIsAutoVerified;
    private final String mNumber;

    public PhoneVerification(String str, PhoneAuthCredential phoneAuthCredential, boolean z) {
        this.mNumber = str;
        this.mCredential = phoneAuthCredential;
        this.mIsAutoVerified = z;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public PhoneAuthCredential getCredential() {
        return this.mCredential;
    }

    public boolean isAutoVerified() {
        return this.mIsAutoVerified;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PhoneVerification phoneVerification = (PhoneVerification) obj;
        if (this.mIsAutoVerified != phoneVerification.mIsAutoVerified || !this.mNumber.equals(phoneVerification.mNumber) || !this.mCredential.equals(phoneVerification.mCredential)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((this.mNumber.hashCode() * 31) + this.mCredential.hashCode()) * 31) + (this.mIsAutoVerified ? 1 : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PhoneVerification{mNumber='");
        sb.append(this.mNumber);
        sb.append('\'');
        sb.append(", mCredential=");
        sb.append(this.mCredential);
        sb.append(", mIsAutoVerified=");
        sb.append(this.mIsAutoVerified);
        sb.append('}');
        return sb.toString();
    }
}
