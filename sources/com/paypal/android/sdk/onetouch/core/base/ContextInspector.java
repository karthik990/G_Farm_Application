package com.paypal.android.sdk.onetouch.core.base;

import android.content.Context;
import android.content.SharedPreferences;

public class ContextInspector {
    private final Context mContext;
    private final SharedPreferences mPreferences = this.mContext.getSharedPreferences("PayPalOTC", 0);

    public ContextInspector(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public String getStringPreference(String str) {
        return this.mPreferences.getString(str, null);
    }

    public long getLongPreference(String str, long j) {
        return this.mPreferences.getLong(str, j);
    }

    public boolean getBooleanPreference(String str, boolean z) {
        return this.mPreferences.getBoolean(str, z);
    }

    public void setPreference(String str, String str2) {
        this.mPreferences.edit().putString(str, str2).apply();
    }

    public void setPreference(String str, long j) {
        this.mPreferences.edit().putLong(str, j).apply();
    }

    public void setPreference(String str, boolean z) {
        this.mPreferences.edit().putBoolean(str, z).apply();
    }

    public Context getContext() {
        return this.mContext;
    }
}
