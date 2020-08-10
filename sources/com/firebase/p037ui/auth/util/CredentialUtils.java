package com.firebase.p037ui.auth.util;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credential.Builder;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.util.CredentialUtils */
public class CredentialUtils {
    private CredentialUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static Credential buildCredential(FirebaseUser firebaseUser, String str, String str2) {
        String email = firebaseUser.getEmail();
        String phoneNumber = firebaseUser.getPhoneNumber();
        Uri parse = firebaseUser.getPhotoUrl() == null ? null : Uri.parse(firebaseUser.getPhotoUrl().toString());
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(phoneNumber)) {
            return null;
        }
        if (str == null && str2 == null) {
            return null;
        }
        if (TextUtils.isEmpty(email)) {
            email = phoneNumber;
        }
        Builder profilePictureUri = new Builder(email).setName(firebaseUser.getDisplayName()).setProfilePictureUri(parse);
        if (TextUtils.isEmpty(str)) {
            profilePictureUri.setAccountType(str2);
        } else {
            profilePictureUri.setPassword(str);
        }
        return profilePictureUri.build();
    }

    public static Credential buildCredentialOrThrow(FirebaseUser firebaseUser, String str, String str2) {
        Credential buildCredential = buildCredential(firebaseUser, str, str2);
        if (buildCredential != null) {
            return buildCredential;
        }
        throw new IllegalStateException("Unable to build credential");
    }
}
