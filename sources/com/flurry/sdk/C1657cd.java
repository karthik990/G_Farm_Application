package com.flurry.sdk;

import com.flurry.sdk.C1638bx.C1641a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.cd */
public final class C1657cd {
    /* renamed from: a */
    public static List<C1638bx> m670a(C1671cp cpVar, C1641a aVar, C1653ca caVar, C1666ck ckVar) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new C1663ci(cpVar, aVar, caVar, ckVar));
        return arrayList;
    }

    /* renamed from: a */
    public static JSONArray m671a() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Entry entry : C1948n.m1229a().f1421g.mo16243a().mo16260a().entrySet()) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(TtmlNode.ATTR_ID, entry.getValue());
            jSONObject.put("type", ((C1544al) entry.getKey()).f513d);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }
}
