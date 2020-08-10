package com.anjlab.android.iab.p020v3;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/* renamed from: com.anjlab.android.iab.v3.BillingBase */
class BillingBase {
    private Context context;

    BillingBase(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: 0000 */
    public Context getContext() {
        return this.context;
    }

    /* access modifiers changed from: 0000 */
    public String getPreferencesBaseKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getPackageName());
        sb.append("_preferences");
        return sb.toString();
    }

    private SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    /* access modifiers changed from: 0000 */
    public boolean saveString(String str, String str2) {
        SharedPreferences preferences = getPreferences();
        if (preferences == null) {
            return false;
        }
        Editor edit = preferences.edit();
        edit.putString(str, str2);
        edit.commit();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public String loadString(String str, String str2) {
        SharedPreferences preferences = getPreferences();
        return preferences != null ? preferences.getString(str, str2) : str2;
    }

    /* access modifiers changed from: 0000 */
    public boolean saveBoolean(String str, Boolean bool) {
        SharedPreferences preferences = getPreferences();
        if (preferences == null) {
            return false;
        }
        Editor edit = preferences.edit();
        edit.putBoolean(str, bool.booleanValue());
        edit.commit();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean loadBoolean(String str, boolean z) {
        SharedPreferences preferences = getPreferences();
        return preferences != null ? preferences.getBoolean(str, z) : z;
    }
}
