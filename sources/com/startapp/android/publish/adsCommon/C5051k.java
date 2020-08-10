package com.startapp.android.publish.adsCommon;

import android.content.Context;
import android.content.SharedPreferences;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.startapp.android.publish.adsCommon.k */
/* compiled from: StartAppSDK */
public class C5051k {

    /* renamed from: a */
    private static SharedPreferences f3311a;

    /* renamed from: a */
    public static SharedPreferences m3334a(Context context) {
        if (f3311a == null) {
            f3311a = context.getApplicationContext().getSharedPreferences("com.startapp.android.publish", 0);
        }
        return f3311a;
    }

    /* renamed from: a */
    public static Boolean m3335a(Context context, String str, Boolean bool) {
        return Boolean.valueOf(m3334a(context).getBoolean(str, bool.booleanValue()));
    }

    /* renamed from: b */
    public static void m3342b(Context context, String str, Boolean bool) {
        C4946i.m2916a(m3334a(context).edit().putBoolean(str, bool.booleanValue()));
    }

    /* renamed from: a */
    public static String m3339a(Context context, String str, String str2) {
        return m3334a(context).getString(str, str2);
    }

    /* renamed from: b */
    public static void m3346b(Context context, String str, String str2) {
        C4946i.m2916a(m3334a(context).edit().putString(str, str2));
    }

    /* renamed from: a */
    public static Integer m3337a(Context context, String str, Integer num) {
        return Integer.valueOf(m3334a(context).getInt(str, num.intValue()));
    }

    /* renamed from: b */
    public static void m3344b(Context context, String str, Integer num) {
        C4946i.m2916a(m3334a(context).edit().putInt(str, num.intValue()));
    }

    /* renamed from: a */
    public static Float m3336a(Context context, String str, Float f) {
        return Float.valueOf(m3334a(context).getFloat(str, f.floatValue()));
    }

    /* renamed from: b */
    public static void m3343b(Context context, String str, Float f) {
        C4946i.m2916a(m3334a(context).edit().putFloat(str, f.floatValue()));
    }

    /* renamed from: a */
    public static Long m3338a(Context context, String str, Long l) {
        return Long.valueOf(m3334a(context).getLong(str, l.longValue()));
    }

    /* renamed from: b */
    public static void m3345b(Context context, String str, Long l) {
        C4946i.m2916a(m3334a(context).edit().putLong(str, l.longValue()));
    }

    /* renamed from: a */
    public static void m3340a(Context context, String str, Map<String, String> map) {
        m3346b(context, str, new JSONObject(map).toString());
    }

    /* renamed from: b */
    public static Map<String, String> m3341b(Context context, String str, Map<String, String> map) {
        try {
            JSONObject jSONObject = new JSONObject(m3334a(context).getString(str, null));
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                map.put(str2, (String) jSONObject.get(str2));
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Throwable unused) {
        }
        return map;
    }
}
