package com.p021b.p022a.p023a.p024a.p030e;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.view.WindowManager;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.b.a.a.a.e.b */
public class C0987b {

    /* renamed from: a */
    static float f164a = Resources.getSystem().getDisplayMetrics().density;

    /* renamed from: b */
    private static WindowManager f165b;

    /* renamed from: c */
    private static String[] f166c = {"x", "y", SettingsJsonConstants.ICON_WIDTH_KEY, SettingsJsonConstants.ICON_HEIGHT_KEY};

    /* renamed from: com.b.a.a.a.e.b$a */
    private static class C0988a {

        /* renamed from: a */
        final float f167a;

        /* renamed from: b */
        final float f168b;

        C0988a(float f, float f2) {
            this.f167a = f;
            this.f168b = f2;
        }
    }

    /* renamed from: a */
    static float m235a(int i) {
        return ((float) i) / f164a;
    }

    /* renamed from: a */
    public static JSONObject m236a(int i, int i2, int i3, int i4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("x", (double) m235a(i));
            jSONObject.put("y", (double) m235a(i2));
            jSONObject.put(SettingsJsonConstants.ICON_WIDTH_KEY, (double) m235a(i3));
            jSONObject.put(SettingsJsonConstants.ICON_HEIGHT_KEY, (double) m235a(i4));
        } catch (JSONException e) {
            C0989c.m251a("Error with creating viewStateObject", e);
        }
        return jSONObject;
    }

    /* renamed from: a */
    public static void m237a(Context context) {
        if (context != null) {
            f164a = context.getResources().getDisplayMetrics().density;
            f165b = (WindowManager) context.getSystemService("window");
        }
    }

    /* renamed from: a */
    public static void m238a(JSONObject jSONObject) {
        C0988a b = m244b(jSONObject);
        try {
            jSONObject.put(SettingsJsonConstants.ICON_WIDTH_KEY, (double) b.f167a);
            jSONObject.put(SettingsJsonConstants.ICON_HEIGHT_KEY, (double) b.f168b);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public static void m239a(JSONObject jSONObject, String str) {
        try {
            jSONObject.put("adSessionId", str);
        } catch (JSONException e) {
            C0989c.m251a("Error with setting ad session id", e);
        }
    }

    /* renamed from: a */
    public static void m240a(JSONObject jSONObject, String str, Object obj) {
        try {
            jSONObject.put(str, obj);
        } catch (JSONException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONException during JSONObject.put for name [");
            sb.append(str);
            sb.append("]");
            C0989c.m251a(sb.toString(), e);
        }
    }

    /* renamed from: a */
    public static void m241a(JSONObject jSONObject, List<String> list) {
        JSONArray jSONArray = new JSONArray();
        for (String put : list) {
            jSONArray.put(put);
        }
        try {
            jSONObject.put("isFriendlyObstructionFor", jSONArray);
        } catch (JSONException e) {
            C0989c.m251a("Error with setting friendly obstruction", e);
        }
    }

    /* renamed from: a */
    public static void m242a(JSONObject jSONObject, JSONObject jSONObject2) {
        String str = "childViews";
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray(str);
            if (optJSONArray == null) {
                optJSONArray = new JSONArray();
                jSONObject.put(str, optJSONArray);
            }
            optJSONArray.put(jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    private static boolean m243a(JSONArray jSONArray, JSONArray jSONArray2) {
        boolean z = true;
        if (jSONArray == null && jSONArray2 == null) {
            return true;
        }
        if (jSONArray == null || jSONArray2 == null) {
            return false;
        }
        if (jSONArray.length() != jSONArray2.length()) {
            z = false;
        }
        return z;
    }

    /* renamed from: b */
    private static C0988a m244b(JSONObject jSONObject) {
        float f;
        float f2 = 0.0f;
        if (VERSION.SDK_INT < 17) {
            JSONArray optJSONArray = jSONObject.optJSONArray("childViews");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                float f3 = 0.0f;
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        double optDouble = optJSONObject.optDouble("x");
                        double optDouble2 = optJSONObject.optDouble("y");
                        double optDouble3 = optJSONObject.optDouble(SettingsJsonConstants.ICON_WIDTH_KEY);
                        double optDouble4 = optJSONObject.optDouble(SettingsJsonConstants.ICON_HEIGHT_KEY);
                        f2 = Math.max(f2, (float) (optDouble + optDouble3));
                        f3 = Math.max(f3, (float) (optDouble2 + optDouble4));
                    }
                }
                f = f3;
                return new C0988a(f2, f);
            }
        } else if (f165b != null) {
            Point point = new Point(0, 0);
            f165b.getDefaultDisplay().getRealSize(point);
            f2 = m235a(point.x);
            f = m235a(point.y);
            return new C0988a(f2, f);
        }
        f = 0.0f;
        return new C0988a(f2, f);
    }

    /* renamed from: b */
    public static boolean m245b(JSONObject jSONObject, JSONObject jSONObject2) {
        boolean z = true;
        if (jSONObject == null && jSONObject2 == null) {
            return true;
        }
        if (jSONObject == null || jSONObject2 == null) {
            return false;
        }
        if (!m246c(jSONObject, jSONObject2) || !m247d(jSONObject, jSONObject2) || !m248e(jSONObject, jSONObject2) || !m249f(jSONObject, jSONObject2)) {
            z = false;
        }
        return z;
    }

    /* renamed from: c */
    private static boolean m246c(JSONObject jSONObject, JSONObject jSONObject2) {
        String[] strArr;
        for (String str : f166c) {
            if (jSONObject.optDouble(str) != jSONObject2.optDouble(str)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: d */
    private static boolean m247d(JSONObject jSONObject, JSONObject jSONObject2) {
        String str = "";
        String str2 = "adSessionId";
        return jSONObject.optString(str2, str).equals(jSONObject2.optString(str2, str));
    }

    /* renamed from: e */
    private static boolean m248e(JSONObject jSONObject, JSONObject jSONObject2) {
        String str = "isFriendlyObstructionFor";
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        JSONArray optJSONArray2 = jSONObject2.optJSONArray(str);
        if (optJSONArray == null && optJSONArray2 == null) {
            return true;
        }
        if (!m243a(optJSONArray, optJSONArray2)) {
            return false;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            String str2 = "";
            if (!optJSONArray.optString(i, str2).equals(optJSONArray2.optString(i, str2))) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: f */
    private static boolean m249f(JSONObject jSONObject, JSONObject jSONObject2) {
        String str = "childViews";
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        JSONArray optJSONArray2 = jSONObject2.optJSONArray(str);
        if (optJSONArray == null && optJSONArray2 == null) {
            return true;
        }
        if (!m243a(optJSONArray, optJSONArray2)) {
            return false;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            if (!m245b(optJSONArray.optJSONObject(i), optJSONArray2.optJSONObject(i))) {
                return false;
            }
        }
        return true;
    }
}
