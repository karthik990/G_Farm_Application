package com.google.firebase.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.internal.FederatedSignInActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OAuthProvider extends FederatedAuthProvider {
    private final Bundle zzjk;

    public static class Builder {
        private final Bundle zzjk;
        private final FirebaseAuth zzjr;
        private final Bundle zzjs;

        private Builder(String str, FirebaseAuth firebaseAuth, GoogleApiAvailability googleApiAvailability) {
            this.zzjk = new Bundle();
            this.zzjs = new Bundle();
            this.zzjr = firebaseAuth;
            this.zzjk.putString("com.google.firebase.auth.KEY_API_KEY", this.zzjr.zzcu().getOptions().getApiKey());
            this.zzjk.putString("com.google.firebase.auth.KEY_PROVIDER_ID", str);
            this.zzjk.putBundle("com.google.firebase.auth.KEY_PROVIDER_CUSTOM_PARAMS", this.zzjs);
            this.zzjk.putString("com.google.firebase.auth.internal.CLIENT_VERSION", Integer.toString(googleApiAvailability.getClientVersion(this.zzjr.zzcu().getApplicationContext())));
            this.zzjk.putString("com.google.firebase.auth.KEY_TENANT_ID", this.zzjr.zzba());
        }

        public Builder setScopes(List<String> list) {
            this.zzjk.putStringArrayList("com.google.firebase.auth.KEY_PROVIDER_SCOPES", new ArrayList(list));
            return this;
        }

        public Builder addCustomParameter(String str, String str2) {
            this.zzjs.putString(str, str2);
            return this;
        }

        public Builder addCustomParameters(Map<String, String> map) {
            for (Entry entry : map.entrySet()) {
                this.zzjs.putString((String) entry.getKey(), (String) entry.getValue());
            }
            return this;
        }

        public OAuthProvider build() {
            return new OAuthProvider(this.zzjk);
        }
    }

    private OAuthProvider(Bundle bundle) {
        this.zzjk = bundle;
    }

    public static Builder newBuilder(String str) {
        return newBuilder(str, FirebaseAuth.getInstance());
    }

    public static Builder newBuilder(String str, FirebaseAuth firebaseAuth) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(firebaseAuth);
        if (!"facebook.com".equals(str)) {
            return new Builder(str, firebaseAuth, instance);
        }
        throw new IllegalArgumentException("Sign in with Facebook is not supported via this method; the Facebook TOS dictate that you must use the Facebook Android SDK for Facebook login.");
    }

    public String getProviderId() {
        Bundle bundle = this.zzjk;
        if (bundle == null) {
            return null;
        }
        return bundle.getString("com.google.firebase.auth.KEY_PROVIDER_ID", null);
    }

    public final void zza(Activity activity) {
        Intent intent = new Intent("com.google.firebase.auth.internal.SIGN_IN");
        intent.setPackage(activity.getPackageName());
        intent.setClass(activity, FederatedSignInActivity.class);
        intent.putExtras(this.zzjk);
        activity.startActivity(intent);
    }

    public final void zzb(Activity activity) {
        Intent intent = new Intent("com.google.firebase.auth.internal.LINK");
        intent.setPackage(activity.getPackageName());
        intent.setClass(activity, FederatedSignInActivity.class);
        intent.putExtras(this.zzjk);
        activity.startActivity(intent);
    }

    public final void zzc(Activity activity) {
        Intent intent = new Intent("com.google.firebase.auth.internal.REAUTHENTICATE");
        intent.setPackage(activity.getPackageName());
        intent.setClass(activity, FederatedSignInActivity.class);
        intent.putExtras(this.zzjk);
        activity.startActivity(intent);
    }

    public static AuthCredential getCredential(String str, String str2, String str3) {
        return zzf.zza(str, str2, str3);
    }
}
