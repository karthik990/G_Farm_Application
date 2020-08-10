package com.google.android.gms.internal.ads;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.json.JSONObject;

final class zznb extends zzna<Boolean> {
    zznb(int i, String str, Boolean bool) {
        super(i, str, bool, null);
    }

    public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
        return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), ((Boolean) zzja()).booleanValue()));
    }

    public final /* synthetic */ void zza(Editor editor, Object obj) {
        editor.putBoolean(getKey(), ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ Object zzb(JSONObject jSONObject) {
        return Boolean.valueOf(jSONObject.optBoolean(getKey(), ((Boolean) zzja()).booleanValue()));
    }
}
