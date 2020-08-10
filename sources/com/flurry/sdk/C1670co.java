package com.flurry.sdk;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.List;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.co */
public final class C1670co {
    /* renamed from: a */
    public static String m715a(String str) {
        String str2 = TtmlNode.ATTR_ID;
        String str3 = "ParameterProvider";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Param.TIMESTAMP, System.currentTimeMillis() / 1000);
            jSONObject.put("guid", str);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put("APP");
            try {
                Class.forName("com.flurry.android.config.killswitch.KillSwitch");
                jSONArray.put("KILLSWITCH");
            } catch (ClassNotFoundException unused) {
            }
            jSONObject.put("documents", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put(C1673cr.m727a() ? "com.flurry.configkey.prod.ec.2" : "com.flurry.configkey.prod.rot.7");
            jSONArray2.put("com.flurry.configkey.prod.fs.0");
            jSONObject.put("signatureKeys", jSONArray2);
            C1601bl a = C1601bl.m537a();
            Context a2 = C1576b.m502a();
            C1644bz a3 = C1644bz.m630a();
            C1653ca caVar = a3.f761a;
            if (C1673cr.m729a(caVar.mo16347d())) {
                String str4 = null;
                if (caVar.f790a != null) {
                    str4 = caVar.f790a.getString("lastETag", null);
                }
                if (str4 != null) {
                    jSONObject.put("etag", str4);
                }
            }
            jSONObject.put("apiKey", C1948n.m1229a().f1422h.f444a);
            jSONObject.put("appVersion", a.mo16290b());
            jSONObject.put("appBuild", Integer.toString(C1601bl.m539b(a2)));
            jSONObject.put("sdkVersion", FlurryAgent.getAgentVersion());
            jSONObject.put("platform", 3);
            jSONObject.put("platformVersion", VERSION.RELEASE);
            jSONObject.put("deviceIds", C1657cd.m671a());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("brand", Build.BRAND);
            jSONObject2.put("device", Build.DEVICE);
            jSONObject2.put(str2, Build.ID);
            jSONObject2.put("model", Build.MODEL);
            jSONObject2.put("product", Build.PRODUCT);
            jSONObject2.put("version_release", VERSION.RELEASE);
            jSONObject.put("deviceTags", jSONObject2);
            jSONObject.put("bundleId", C1731dx.m857a(a2));
            C1948n.m1229a();
            jSONObject.put("locale", C1554ar.m459a());
            String str5 = C1948n.m1229a().f1422h.f445b;
            if (!TextUtils.isEmpty(str5)) {
                jSONObject.put("publisherUserId", str5);
            }
            List<C1665cj> e = a3.mo16339e();
            if (e != null && e.size() > 0) {
                JSONArray jSONArray3 = new JSONArray();
                for (C1665cj cjVar : e) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(str2, cjVar.f827b);
                    jSONObject3.put(ClientCookie.VERSION_ATTR, cjVar.f828c);
                    jSONArray3.put(jSONObject3);
                }
                jSONObject.put("currentVariants", jSONArray3);
            }
        } catch (JSONException e2) {
            C1685cy.m757a(str3, "ParameterProvider error", (Throwable) e2);
        }
        String jSONObject4 = jSONObject.toString();
        C1685cy.m756a(str3, "Request Parameters: ".concat(String.valueOf(jSONObject4)));
        return jSONObject4;
    }
}
