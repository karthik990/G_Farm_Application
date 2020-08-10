package com.flurry.sdk;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.jc */
public final class C1916jc extends C1926jl {

    /* renamed from: a */
    public final Map<String, List<String>> f1310a;

    public C1916jc(Map<String, List<String>> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        this.f1310a = map;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : this.f1310a.entrySet()) {
            JSONArray jSONArray = new JSONArray();
            for (String str : (List) entry.getValue()) {
                if (!TextUtils.isEmpty(str)) {
                    jSONArray.put(str);
                }
            }
            if (jSONArray.length() > 0) {
                jSONObject.put((String) entry.getKey(), jSONArray);
            }
        }
        if (jSONObject.length() > 0) {
            a.put("fl.referrer.map", jSONObject);
        }
        return a;
    }
}
