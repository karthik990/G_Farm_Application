package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.webkit.WebSettings;
import java.util.concurrent.Callable;

final class zzamp implements Callable<String> {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ Context zzcub;

    zzamp(zzamn zzamn, Context context, Context context2) {
        this.zzcub = context;
        this.val$context = context2;
    }

    public final /* synthetic */ Object call() throws Exception {
        SharedPreferences sharedPreferences;
        String str = "admob_user_agent";
        boolean z = false;
        if (this.zzcub != null) {
            zzakb.m1419v("Attempting to read user agent from Google Play Services.");
            sharedPreferences = this.zzcub.getSharedPreferences(str, 0);
        } else {
            zzakb.m1419v("Attempting to read user agent from local cache.");
            sharedPreferences = this.val$context.getSharedPreferences(str, 0);
            z = true;
        }
        String str2 = "user_agent";
        String string = sharedPreferences.getString(str2, "");
        if (TextUtils.isEmpty(string)) {
            zzakb.m1419v("Reading user agent from WebSettings");
            string = WebSettings.getDefaultUserAgent(this.val$context);
            if (z) {
                sharedPreferences.edit().putString(str2, string).apply();
                zzakb.m1419v("Persisting user agent.");
            }
        }
        return string;
    }
}
