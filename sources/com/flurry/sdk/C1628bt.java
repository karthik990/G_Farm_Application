package com.flurry.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import com.anjlab.android.iab.p020v3.Constants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.bt */
public final class C1628bt {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static Object f714a = new Object();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static List<C1632b> f715b = new ArrayList();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static IInAppBillingService f716c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public static ServiceConnection f717d;

    /* renamed from: com.flurry.sdk.bt$a */
    public static abstract class C1631a {
        /* renamed from: a */
        public abstract void mo16314a(int i, C1634c cVar);
    }

    /* renamed from: com.flurry.sdk.bt$b */
    static abstract class C1632b {
        /* renamed from: a */
        public abstract void mo16311a(int i, IInAppBillingService iInAppBillingService);

        private C1632b() {
        }

        /* synthetic */ C1632b(byte b) {
            this();
        }

        /* renamed from: b */
        public final void mo16315b(final int i, final IInAppBillingService iInAppBillingService) {
            new Thread(new Runnable() {
                public final void run() {
                    C1632b.this.mo16311a(i, iInAppBillingService);
                }
            }).start();
        }
    }

    /* renamed from: com.flurry.sdk.bt$c */
    public static class C1634c {

        /* renamed from: a */
        public final String f724a;

        /* renamed from: b */
        public final long f725b;

        /* renamed from: c */
        public final String f726c;

        /* renamed from: d */
        public final String f727d;

        /* renamed from: e */
        private final String f728e;

        /* renamed from: f */
        private final String f729f;

        /* renamed from: g */
        private final String f730g;

        /* renamed from: h */
        private final String f731h;

        /* renamed from: i */
        private final String f732i;

        public C1634c(String str, String str2) throws JSONException {
            this.f728e = str;
            this.f732i = str2;
            JSONObject jSONObject = new JSONObject(this.f732i);
            this.f729f = jSONObject.optString("productId");
            this.f724a = jSONObject.optString("type");
            this.f730g = jSONObject.optString("price");
            this.f725b = jSONObject.optLong(Constants.RESPONSE_PRICE_MICROS);
            this.f726c = jSONObject.optString(Constants.RESPONSE_PRICE_CURRENCY);
            this.f727d = jSONObject.optString("title");
            this.f731h = jSONObject.optString(Constants.RESPONSE_DESCRIPTION);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SkuDetails:");
            sb.append(this.f732i);
            return sb.toString();
        }
    }

    /* renamed from: a */
    public static void m594a(final Context context, final String str, final C1631a aVar) {
        try {
            Class.forName("com.android.vending.billing.IInAppBillingService");
            C1685cy.m754a(3, "GooglePlayIap", "Google play billing library is available");
            C16291 r0 = new C1632b() {
                /* renamed from: a */
                public final void mo16311a(int i, IInAppBillingService iInAppBillingService) {
                    if (i == 0) {
                        C1634c a = C1628bt.m595b(iInAppBillingService, context, "inapp", str);
                        if (a == null) {
                            a = C1628bt.m595b(iInAppBillingService, context, "subs", str);
                        }
                        aVar.mo16314a(i, a);
                        return;
                    }
                    aVar.mo16314a(i, null);
                }
            };
            Boolean bool = Boolean.FALSE;
            synchronized (f714a) {
                if (f717d == null) {
                    f717d = new ServiceConnection() {
                        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            synchronized (C1628bt.f714a) {
                                C1628bt.f716c = Stub.asInterface(iBinder);
                                for (C1632b b : C1628bt.f715b) {
                                    b.mo16315b(0, C1628bt.f716c);
                                }
                                C1628bt.f715b.clear();
                            }
                        }

                        public final void onServiceDisconnected(ComponentName componentName) {
                            synchronized (C1628bt.f714a) {
                                C1628bt.f717d = null;
                                C1628bt.f716c = null;
                                for (C1632b b : C1628bt.f715b) {
                                    b.mo16315b(1, null);
                                }
                                C1628bt.f715b.clear();
                            }
                        }
                    };
                    bool = Boolean.TRUE;
                }
                if (f716c == null) {
                    f715b.add(r0);
                } else {
                    r0.mo16315b(0, f716c);
                }
                if (bool.booleanValue()) {
                    Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
                    intent.setPackage("com.android.vending");
                    List queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
                    if (queryIntentServices == null || queryIntentServices.isEmpty()) {
                        r0.mo16315b(1, null);
                        f717d = null;
                    } else {
                        context.bindService(intent, f717d, 1);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            C1685cy.m762b("GooglePlayIap", "Could not find google play billing library");
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static C1634c m595b(IInAppBillingService iInAppBillingService, Context context, String str, String str2) {
        String str3 = "DETAILS_LIST";
        String str4 = "GooglePlayIap";
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        bundle.putStringArrayList(Constants.PRODUCTS_LIST, arrayList);
        try {
            Bundle skuDetails = iInAppBillingService.getSkuDetails(3, context.getPackageName(), str, bundle);
            if (skuDetails.containsKey(str3)) {
                ArrayList stringArrayList = skuDetails.getStringArrayList(str3);
                if (stringArrayList.size() > 0) {
                    return new C1634c(str, (String) stringArrayList.get(0));
                }
            }
            return null;
        } catch (RemoteException e) {
            C1685cy.m757a(str4, "RemoteException getting SKU Details", (Throwable) e);
            return null;
        } catch (JSONException e2) {
            C1685cy.m757a(str4, "JSONException parsing SKU Details", (Throwable) e2);
            return null;
        }
    }
}
