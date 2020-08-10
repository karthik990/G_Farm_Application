package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.exifinterface.media.ExifInterface;
import com.anjlab.android.iab.p020v3.Constants;
import com.flurry.android.FlurryEventRecordStatus;
import com.flurry.sdk.C1628bt.C1631a;
import com.flurry.sdk.C1628bt.C1634c;
import com.flurry.sdk.C1839gm.C1840a;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gl */
public final class C1837gl extends C1927jm {

    /* renamed from: a */
    private static final AtomicInteger f1201a = new AtomicInteger(0);

    private C1837gl(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public static FlurryEventRecordStatus m1075a(String str, Map<String, String> map, boolean z, boolean z2, long j, long j2) {
        HashMap hashMap;
        if (map.size() > 10) {
            hashMap = new HashMap();
            hashMap.put("fl.parameter.limit.exceeded", String.valueOf(map.size()));
            map.clear();
        } else {
            hashMap = null;
        }
        Map<String, String> map2 = map;
        C1839gm gmVar = new C1839gm(C1734dz.m867a(str), f1201a.incrementAndGet(), C1840a.CUSTOM_EVENT, map2, hashMap, new ArrayList(), z, z2, j, j2);
        C1771fb.m926a().mo16467a(new C1837gl(gmVar));
        return FlurryEventRecordStatus.kFlurryEventRecorded;
    }

    /* renamed from: a */
    public static void m1078a(int i, Intent intent, Map<String, String> map, long j, long j2) {
        int i2 = i;
        Intent intent2 = intent;
        if (!(intent2 == null || intent.getExtras() == null)) {
            Object obj = intent.getExtras().get("RESPONSE_CODE");
            int i3 = 0;
            String str = "StreamingEventFrame";
            if (obj == null) {
                C1685cy.m762b(str, "Intent with no response code, assuming OK (known issue)");
            } else if (obj instanceof Integer) {
                i3 = ((Integer) obj).intValue();
            } else if (obj instanceof Long) {
                i3 = (int) ((Long) obj).longValue();
            }
            final String stringExtra = intent2.getStringExtra(Constants.INAPP_PURCHASE_DATA);
            final String stringExtra2 = intent2.getStringExtra(Constants.RESPONSE_INAPP_SIGNATURE);
            JSONObject jSONObject = new JSONObject();
            if (stringExtra != null) {
                try {
                    jSONObject = new JSONObject(stringExtra);
                } catch (Throwable th) {
                    C1685cy.m757a(str, "Failed to log event: Flurry.purchase", th);
                    return;
                }
            }
            String optString = jSONObject.optString("productId");
            final String optString2 = jSONObject.optString("orderId");
            if (i2 == -1 && i3 == 0) {
                Context a = C1576b.m502a();
                final Map<String, String> map2 = map;
                final String str2 = optString;
                final long j3 = j;
                final long j4 = j2;
                C18381 r5 = new C1631a() {
                    /* renamed from: a */
                    public final void mo16314a(int i, C1634c cVar) {
                        if (cVar != null) {
                            double d = (double) cVar.f725b;
                            Double.isNaN(d);
                            double d2 = d / 1000000.0d;
                            HashMap hashMap = new HashMap();
                            if (map2.size() > 10) {
                                hashMap.put("fl.parameter.limit.exceeded", String.valueOf(map2.size()));
                                map2.clear();
                            }
                            hashMap.put("fl.Quantity", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                            hashMap.put("fl.ProductID", str2);
                            hashMap.put("fl.Price", String.format(Locale.ENGLISH, "%1$.2f", new Object[]{Double.valueOf(d2)}));
                            hashMap.put("fl.Currency", cVar.f726c);
                            hashMap.put("fl.ProductName", cVar.f727d);
                            hashMap.put("fl.ProductType", cVar.f724a);
                            hashMap.put("fl.TransactionIdentifier", optString2);
                            hashMap.put("fl.OrderJSON", stringExtra);
                            hashMap.put("fl.OrderJSONSignature", stringExtra2);
                            hashMap.put("fl.StoreId", ExifInterface.GPS_MEASUREMENT_2D);
                            StringBuilder sb = new StringBuilder();
                            sb.append(stringExtra2);
                            sb.append(com.mobiroller.constants.Constants.NEW_LINE);
                            sb.append(stringExtra);
                            hashMap.put("fl.Receipt", sb.toString());
                            C1837gl.m1079b(map2, hashMap, j3, j4, new ArrayList());
                            return;
                        }
                        StringBuilder sb2 = new StringBuilder("Failed to load SKU Details from Google for '");
                        sb2.append(str2);
                        sb2.append("'. Result: ");
                        sb2.append(i);
                        C1685cy.m762b("StreamingEventFrame", sb2.toString());
                    }
                };
                C1628bt.m594a(a, optString, r5);
                return;
            }
            StringBuilder sb = new StringBuilder("Invalid logPayment call. resultCode:");
            sb.append(i);
            sb.append(", responseCode:");
            sb.append(i3);
            sb.append(", purchaseData:");
            sb.append(stringExtra);
            sb.append(", dataSignature:");
            sb.append(stringExtra2);
            C1685cy.m762b(str, sb.toString());
        }
    }

    /* renamed from: a */
    public static FlurryEventRecordStatus m1074a(String str, String str2, int i, double d, String str3, String str4, Map<String, String> map, long j, long j2) {
        HashMap hashMap = new HashMap();
        if (map.size() > 10) {
            hashMap.put("fl.parameter.limit.exceeded", String.valueOf(map.size()));
            map.clear();
        }
        try {
            hashMap.put("fl.ProductName", str);
            hashMap.put("fl.ProductID", str2);
            hashMap.put("fl.Quantity", String.valueOf(i));
            hashMap.put("fl.Price", String.format(Locale.ENGLISH, "%1$.2f", new Object[]{Double.valueOf(d)}));
            hashMap.put("fl.Currency", str3);
            hashMap.put("fl.TransactionIdentifier", str4);
            return m1079b(map, hashMap, j, j2, new ArrayList());
        } catch (Throwable th) {
            C1685cy.m757a("StreamingEventFrame", "Failed to log event: Flurry.purchase", th);
            return FlurryEventRecordStatus.kFlurryEventRecorded;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static FlurryEventRecordStatus m1079b(Map<String, String> map, Map<String, String> map2, long j, long j2, List<String> list) {
        C1839gm gmVar = new C1839gm("Flurry.purchase", f1201a.incrementAndGet(), C1840a.PURCHASE_EVENT, map, map2, list, false, false, j, j2);
        C1771fb.m926a().mo16467a(new C1837gl(gmVar));
        return FlurryEventRecordStatus.kFlurryEventRecorded;
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.ANALYTICS_EVENT;
    }

    /* renamed from: a */
    public static C1837gl m1077a(String str, int i, Map<String, String> map, Map<String, String> map2, long j, long j2) {
        C1839gm gmVar = new C1839gm(str, i, map, map2, j, SystemClock.elapsedRealtime(), j2);
        return new C1837gl(gmVar);
    }
}
