package com.flurry.sdk;

import com.braintreepayments.api.models.PostalAddressParser;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.cb */
public final class C1654cb {
    /* renamed from: a */
    public static List<C1665cj> m668a(JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray("variants");
        if (optJSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(m669b(optJSONObject));
            }
        }
        return arrayList;
    }

    /* renamed from: b */
    private static C1665cj m669b(JSONObject jSONObject) {
        C1665cj cjVar = new C1665cj(C1660cg.m675a(jSONObject.optString("document", C1660cg.f812a.toString())));
        cjVar.f827b = jSONObject.optInt(TtmlNode.ATTR_ID);
        cjVar.f828c = jSONObject.optInt(ClientCookie.VERSION_ATTR);
        cjVar.f829d = jSONObject;
        JSONArray optJSONArray = jSONObject.optJSONArray("items");
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    String optString = optJSONObject.optString(PostalAddressParser.USER_ADDRESS_NAME_KEY, null);
                    if (optString != null) {
                        cjVar.f830e.put(optString, new C1642by(optJSONObject));
                    }
                }
            }
        }
        return cjVar;
    }
}
