package com.flurry.sdk;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Locale;
import org.apache.http.client.config.CookieSpecs;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.by */
public final class C1642by {

    /* renamed from: b */
    private static String f749b;

    /* renamed from: c */
    private static String f750c;

    /* renamed from: a */
    C1643a f751a;

    /* renamed from: d */
    private Object f752d;

    /* renamed from: com.flurry.sdk.by$a */
    public enum C1643a {
        String("string"),
        Locale("localizedString"),
        Tombstone("tombstone");
        
        /* access modifiers changed from: private */

        /* renamed from: d */
        public String f757d;

        private C1643a(String str) {
            this.f757d = str;
        }

        public final String toString() {
            return this.f757d;
        }
    }

    public C1642by(JSONObject jSONObject) {
        String optString = jSONObject.optString("type");
        boolean equals = C1643a.String.f757d.equals(optString);
        String str = Param.VALUE;
        if (equals) {
            this.f751a = C1643a.String;
            this.f752d = jSONObject.optString(str);
        } else if (C1643a.Locale.f757d.equals(optString)) {
            this.f751a = C1643a.Locale;
            this.f752d = jSONObject.optJSONObject(str);
        } else if (C1643a.Tombstone.f757d.equals(optString)) {
            this.f751a = C1643a.Tombstone;
        } else {
            C1685cy.m762b("ConfigItem", "Unknown ConfigItem type: ".concat(String.valueOf(optString)));
        }
    }

    /* renamed from: a */
    public final String mo16330a() {
        if (this.f752d == null) {
            return null;
        }
        if (this.f751a != C1643a.Locale) {
            return (String) this.f752d;
        }
        if (f749b == null) {
            f749b = Locale.getDefault().toString();
            f750c = Locale.getDefault().getLanguage();
        }
        JSONObject jSONObject = (JSONObject) this.f752d;
        String optString = jSONObject.optString(f749b, null);
        if (optString == null) {
            optString = jSONObject.optString(f750c, null);
        }
        if (optString == null) {
            optString = jSONObject.optString(CookieSpecs.DEFAULT);
        }
        return optString;
    }

    /* renamed from: a */
    public final JSONObject mo16331a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, str);
            jSONObject.put("type", this.f751a.toString());
            jSONObject.put(Param.VALUE, this.f752d);
            return jSONObject;
        } catch (JSONException e) {
            C1685cy.m757a("ConfigItem", "Error to create JSON object.", (Throwable) e);
            return null;
        }
    }
}
