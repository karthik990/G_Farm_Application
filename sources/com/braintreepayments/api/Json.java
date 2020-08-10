package com.braintreepayments.api;

import org.json.JSONObject;

public class Json {
    public static String optString(JSONObject jSONObject, String str, String str2) {
        if (jSONObject.isNull(str)) {
            return str2;
        }
        return jSONObject.optString(str, str2);
    }
}
