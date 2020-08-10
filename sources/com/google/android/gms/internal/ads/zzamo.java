package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.webkit.WebSettings;
import com.google.android.gms.common.util.SharedPreferencesUtils;
import java.util.concurrent.Callable;

final class zzamo implements Callable<String> {
    private final /* synthetic */ Context val$context;

    zzamo(zzamn zzamn, Context context) {
        this.val$context = context;
    }

    public final /* synthetic */ Object call() throws Exception {
        String str = "admob_user_agent";
        SharedPreferences sharedPreferences = this.val$context.getSharedPreferences(str, 0);
        String str2 = "user_agent";
        String string = sharedPreferences.getString(str2, "");
        if (TextUtils.isEmpty(string)) {
            zzakb.m1419v("User agent is not initialized on Google Play Services. Initializing.");
            String defaultUserAgent = WebSettings.getDefaultUserAgent(this.val$context);
            SharedPreferencesUtils.publishWorldReadableSharedPreferences(this.val$context, sharedPreferences.edit().putString(str2, defaultUserAgent), str);
            return defaultUserAgent;
        }
        zzakb.m1419v("User agent is already initialized on Google Play Services.");
        return string;
    }
}
