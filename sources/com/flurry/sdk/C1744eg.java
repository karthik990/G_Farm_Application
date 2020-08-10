package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import com.flurry.android.FlurryPrivacySession.C1477a;
import com.flurry.android.FlurryPrivacySession.Request;
import com.flurry.sdk.C1691dd.C1693a;
import com.flurry.sdk.C1696df.C1699a;
import com.flurry.sdk.C1740ed.C1741a;
import com.flurry.sdk.C1756ex.C1758a;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.eg */
public final class C1744eg extends C1766f {

    /* renamed from: a */
    private static C1744eg f1001a = new C1744eg();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public Request f1002b;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final C1949o<C1543ak> f1003d = new C1949o<C1543ak>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            C1948n.m1229a().f1421g.unsubscribe(C1744eg.this.f1003d);
            C1744eg.this.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    Map b = C1744eg.m890b(C1744eg.this.f1002b);
                    C1691dd ddVar = new C1691dd();
                    ddVar.f897f = "https://api.login.yahoo.com/oauth2/device_session";
                    ddVar.f898g = C1699a.kPost;
                    ddVar.mo16403a("Content-Type", "application/json");
                    ddVar.f883b = new JSONObject(b).toString();
                    ddVar.f885d = new C1725dt();
                    ddVar.f884c = new C1725dt();
                    ddVar.f882a = new C1693a<String, String>() {
                        /* renamed from: a */
                        public final /* synthetic */ void mo16300a(C1691dd ddVar, Object obj) {
                            String str = "PrivacyManager";
                            String str2 = (String) obj;
                            try {
                                int i = ddVar.f904m;
                                if (i == 200) {
                                    JSONObject jSONObject = new JSONObject(str2);
                                    C1744eg.m888a(C1744eg.this, new C1477a(jSONObject.getString("device_session_id"), jSONObject.getLong("expires_in"), C1744eg.this.f1002b));
                                    C1744eg.this.f1002b.callback.success();
                                    return;
                                }
                                C1685cy.m769e(str, "Error in getting privacy dashboard url. Error code = ".concat(String.valueOf(i)));
                                C1744eg.this.f1002b.callback.failure();
                            } catch (JSONException e) {
                                C1685cy.m763b(str, "Error in getting privacy dashboard url. ", (Throwable) e);
                                C1744eg.this.f1002b.callback.failure();
                            }
                        }
                    };
                    C1675ct.m738a().mo16389a(C1744eg.this, ddVar);
                }
            });
        }
    };

    private C1744eg() {
        super("PrivacyManager", C1756ex.m904a(C1758a.MISC));
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static void m891b(Context context, C1477a aVar) {
        Intent intent = new Intent("android.intent.action.VIEW", aVar.f311a);
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    /* renamed from: a */
    public static void m886a(Request request) {
        C1744eg egVar = f1001a;
        egVar.f1002b = request;
        egVar.runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                if (C1948n.m1229a().f1421g.mo16247c()) {
                    C1744eg.this.runAsync(new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() throws Exception {
                            Map b = C1744eg.m890b(C1744eg.this.f1002b);
                            C1691dd ddVar = new C1691dd();
                            ddVar.f897f = "https://api.login.yahoo.com/oauth2/device_session";
                            ddVar.f898g = C1699a.kPost;
                            ddVar.mo16403a("Content-Type", "application/json");
                            ddVar.f883b = new JSONObject(b).toString();
                            ddVar.f885d = new C1725dt();
                            ddVar.f884c = new C1725dt();
                            ddVar.f882a = new C1693a<String, String>() {
                                /* renamed from: a */
                                public final /* synthetic */ void mo16300a(C1691dd ddVar, Object obj) {
                                    String str = "PrivacyManager";
                                    String str2 = (String) obj;
                                    try {
                                        int i = ddVar.f904m;
                                        if (i == 200) {
                                            JSONObject jSONObject = new JSONObject(str2);
                                            C1744eg.m888a(C1744eg.this, new C1477a(jSONObject.getString("device_session_id"), jSONObject.getLong("expires_in"), C1744eg.this.f1002b));
                                            C1744eg.this.f1002b.callback.success();
                                            return;
                                        }
                                        C1685cy.m769e(str, "Error in getting privacy dashboard url. Error code = ".concat(String.valueOf(i)));
                                        C1744eg.this.f1002b.callback.failure();
                                    } catch (JSONException e) {
                                        C1685cy.m763b(str, "Error in getting privacy dashboard url. ", (Throwable) e);
                                        C1744eg.this.f1002b.callback.failure();
                                    }
                                }
                            };
                            C1675ct.m738a().mo16389a(C1744eg.this, ddVar);
                        }
                    });
                    return;
                }
                C1685cy.m754a(3, "PrivacyManager", "Waiting for ID provider.");
                C1948n.m1229a().f1421g.subscribe(C1744eg.this.f1003d);
            }
        });
    }

    /* renamed from: b */
    static /* synthetic */ Map m890b(Request request) {
        HashMap hashMap = new HashMap();
        hashMap.put("device_verifier", request.verifier);
        HashMap hashMap2 = new HashMap();
        C1543ak a = C1948n.m1229a().f1421g.mo16243a();
        String str = (String) a.mo16260a().get(C1544al.AndroidAdvertisingId);
        if (str != null) {
            hashMap2.put("gpaid", str);
        }
        String str2 = (String) a.mo16260a().get(C1544al.DeviceId);
        if (str2 != null) {
            hashMap2.put("andid", str2);
        }
        hashMap.putAll(hashMap2);
        HashMap hashMap3 = new HashMap();
        byte[] bytes = ((String) C1948n.m1229a().f1421g.mo16243a().mo16260a().get(C1544al.AndroidInstallationId)).getBytes();
        if (bytes != null) {
            hashMap3.put("flurry_guid", C1734dz.m868a(bytes));
        }
        hashMap3.put("flurry_project_api_key", C1948n.m1229a().f1422h.f444a);
        hashMap.putAll(hashMap3);
        Context context = request.context;
        HashMap hashMap4 = new HashMap();
        hashMap4.put("src", "flurryandroidsdk");
        hashMap4.put("srcv", "12.3.0");
        hashMap4.put("appsrc", context.getPackageName());
        C1601bl.m537a();
        hashMap4.put("appsrcv", C1601bl.m538a(context));
        hashMap.putAll(hashMap4);
        return hashMap;
    }

    /* renamed from: a */
    static /* synthetic */ void m888a(C1744eg egVar, final C1477a aVar) {
        Context a = C1576b.m502a();
        if (C1740ed.m881a(a)) {
            C1740ed.m880a(a, new Builder().setShowTitle(true).build(), Uri.parse(aVar.f311a.toString()), new C1741a() {
                /* renamed from: a */
                public final void mo16453a(Context context) {
                    C1744eg.m891b(context, aVar);
                }
            });
        } else {
            m891b(a, aVar);
        }
    }
}
