package com.google.android.gms.auth.api.credentials;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.Auth.AuthCredentialsOptions;

public class Credentials {
    public static CredentialsClient getClient(Activity activity) {
        return new CredentialsClient(activity, (AuthCredentialsOptions) CredentialsOptions.DEFAULT);
    }

    public static CredentialsClient getClient(Activity activity, CredentialsOptions credentialsOptions) {
        return new CredentialsClient(activity, (AuthCredentialsOptions) credentialsOptions);
    }

    public static CredentialsClient getClient(Context context) {
        return new CredentialsClient(context, (AuthCredentialsOptions) CredentialsOptions.DEFAULT);
    }

    public static CredentialsClient getClient(Context context, CredentialsOptions credentialsOptions) {
        return new CredentialsClient(context, (AuthCredentialsOptions) credentialsOptions);
    }
}
