package com.google.firebase.auth;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public interface AuthResult extends SafeParcelable {
    AdditionalUserInfo getAdditionalUserInfo();

    AuthCredential getCredential();

    FirebaseUser getUser();
}
