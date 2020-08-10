package com.google.android.gms.tagmanager;

import android.content.Context;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.Constants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzgk extends zzgh {

    /* renamed from: ID */
    private static final String f1578ID = zza.UNIVERSAL_ANALYTICS.toString();
    private static final String zzalx = zzb.ACCOUNT.toString();
    private static final String zzaly = zzb.ANALYTICS_PASS_THROUGH.toString();
    private static final String zzalz = zzb.ENABLE_ECOMMERCE.toString();
    private static final String zzama = zzb.ECOMMERCE_USE_DATA_LAYER.toString();
    private static final String zzamb = zzb.ECOMMERCE_MACRO_DATA.toString();
    private static final String zzamc = zzb.ANALYTICS_FIELDS.toString();
    private static final String zzamd = zzb.TRACK_TRANSACTION.toString();
    private static final String zzame = zzb.TRANSACTION_DATALAYER_MAP.toString();
    private static final String zzamf = zzb.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static final List<String> zzamg = Arrays.asList(new String[]{ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, "checkout_option", "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND});
    private static final Pattern zzamh = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzami = Pattern.compile("metric(\\d+)");
    private static Map<String, String> zzamj;
    private static Map<String, String> zzamk;
    private final DataLayer zzaed;
    private final Set<String> zzaml;
    private final zzgf zzamm;

    public zzgk(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzgf(context));
    }

    private zzgk(Context context, DataLayer dataLayer, zzgf zzgf) {
        super(f1578ID, new String[0]);
        this.zzaed = dataLayer;
        this.zzamm = zzgf;
        this.zzaml = new HashSet();
        this.zzaml.add("");
        this.zzaml.add("0");
        this.zzaml.add("false");
    }

    private static boolean zzc(Map<String, zzl> map, String str) {
        zzl zzl = (zzl) map.get(str);
        if (zzl == null) {
            return false;
        }
        return zzgj.zzg(zzl).booleanValue();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0181  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzd(java.util.Map<java.lang.String, com.google.android.gms.internal.gtm.zzl> r14) {
        /*
            r13 = this;
            java.lang.String r0 = "actionField"
            java.lang.String r1 = "&t"
            com.google.android.gms.tagmanager.zzgf r2 = r13.zzamm
            java.lang.String r3 = "_GTM_DEFAULT_TRACKER_"
            com.google.android.gms.analytics.Tracker r2 = r2.zzbm(r3)
            java.lang.String r3 = "collect_adid"
            boolean r3 = zzc(r14, r3)
            r2.enableAdvertisingIdCollection(r3)
            java.lang.String r3 = zzalz
            boolean r3 = zzc(r14, r3)
            r4 = 0
            java.lang.String r5 = "name"
            r6 = 0
            java.lang.String r7 = "&cu"
            if (r3 == 0) goto L_0x02b2
            com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder r1 = new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder
            r1.<init>()
            java.lang.String r3 = zzamc
            java.lang.Object r3 = r14.get(r3)
            com.google.android.gms.internal.gtm.zzl r3 = (com.google.android.gms.internal.gtm.zzl) r3
            java.util.Map r3 = r13.zzj(r3)
            r1.setAll(r3)
            java.lang.String r8 = zzama
            boolean r8 = zzc(r14, r8)
            if (r8 == 0) goto L_0x004e
            com.google.android.gms.tagmanager.DataLayer r14 = r13.zzaed
            java.lang.String r8 = "ecommerce"
            java.lang.Object r14 = r14.get(r8)
            boolean r8 = r14 instanceof java.util.Map
            if (r8 == 0) goto L_0x0061
            java.util.Map r14 = (java.util.Map) r14
            goto L_0x0062
        L_0x004e:
            java.lang.String r8 = zzamb
            java.lang.Object r14 = r14.get(r8)
            com.google.android.gms.internal.gtm.zzl r14 = (com.google.android.gms.internal.gtm.zzl) r14
            java.lang.Object r14 = com.google.android.gms.tagmanager.zzgj.zzh(r14)
            boolean r8 = r14 instanceof java.util.Map
            if (r8 == 0) goto L_0x0061
            java.util.Map r14 = (java.util.Map) r14
            goto L_0x0062
        L_0x0061:
            r14 = r6
        L_0x0062:
            if (r14 == 0) goto L_0x02aa
            java.lang.Object r3 = r3.get(r7)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 != 0) goto L_0x0074
            java.lang.String r3 = "currencyCode"
            java.lang.Object r3 = r14.get(r3)
            java.lang.String r3 = (java.lang.String) r3
        L_0x0074:
            if (r3 == 0) goto L_0x0079
            r1.set(r7, r3)
        L_0x0079:
            java.lang.String r3 = "impressions"
            java.lang.Object r3 = r14.get(r3)
            boolean r7 = r3 instanceof java.util.List
            java.lang.String r8 = "Failed to extract a product from DataLayer. "
            java.lang.String r9 = "list"
            if (r7 == 0) goto L_0x00c4
            java.util.List r3 = (java.util.List) r3
            java.util.Iterator r3 = r3.iterator()
        L_0x008d:
            boolean r7 = r3.hasNext()
            if (r7 == 0) goto L_0x00c4
            java.lang.Object r7 = r3.next()
            java.util.Map r7 = (java.util.Map) r7
            com.google.android.gms.analytics.ecommerce.Product r10 = zzf(r7)     // Catch:{ RuntimeException -> 0x00a7 }
            java.lang.Object r7 = r7.get(r9)     // Catch:{ RuntimeException -> 0x00a7 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ RuntimeException -> 0x00a7 }
            r1.addImpression(r10, r7)     // Catch:{ RuntimeException -> 0x00a7 }
            goto L_0x008d
        L_0x00a7:
            r7 = move-exception
            java.lang.String r7 = r7.getMessage()
            java.lang.String r7 = java.lang.String.valueOf(r7)
            int r10 = r7.length()
            if (r10 == 0) goto L_0x00bb
            java.lang.String r7 = r8.concat(r7)
            goto L_0x00c0
        L_0x00bb:
            java.lang.String r7 = new java.lang.String
            r7.<init>(r8)
        L_0x00c0:
            com.google.android.gms.tagmanager.zzdi.zzav(r7)
            goto L_0x008d
        L_0x00c4:
            java.lang.String r3 = "promoClick"
            boolean r7 = r14.containsKey(r3)
            java.lang.String r10 = "promotions"
            if (r7 == 0) goto L_0x00db
            java.lang.Object r6 = r14.get(r3)
            java.util.Map r6 = (java.util.Map) r6
            java.lang.Object r6 = r6.get(r10)
            java.util.List r6 = (java.util.List) r6
            goto L_0x00ef
        L_0x00db:
            java.lang.String r7 = "promoView"
            boolean r11 = r14.containsKey(r7)
            if (r11 == 0) goto L_0x00ef
            java.lang.Object r6 = r14.get(r7)
            java.util.Map r6 = (java.util.Map) r6
            java.lang.Object r6 = r6.get(r10)
            java.util.List r6 = (java.util.List) r6
        L_0x00ef:
            java.lang.String r7 = "id"
            if (r6 == 0) goto L_0x017e
            java.util.Iterator r6 = r6.iterator()
        L_0x00f7:
            boolean r10 = r6.hasNext()
            if (r10 == 0) goto L_0x016b
            java.lang.Object r10 = r6.next()
            java.util.Map r10 = (java.util.Map) r10
            com.google.android.gms.analytics.ecommerce.Promotion r11 = new com.google.android.gms.analytics.ecommerce.Promotion     // Catch:{ RuntimeException -> 0x014c }
            r11.<init>()     // Catch:{ RuntimeException -> 0x014c }
            java.lang.Object r12 = r10.get(r7)     // Catch:{ RuntimeException -> 0x014c }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ RuntimeException -> 0x014c }
            if (r12 == 0) goto L_0x0117
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ RuntimeException -> 0x014c }
            r11.setId(r12)     // Catch:{ RuntimeException -> 0x014c }
        L_0x0117:
            java.lang.Object r12 = r10.get(r5)     // Catch:{ RuntimeException -> 0x014c }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ RuntimeException -> 0x014c }
            if (r12 == 0) goto L_0x0126
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ RuntimeException -> 0x014c }
            r11.setName(r12)     // Catch:{ RuntimeException -> 0x014c }
        L_0x0126:
            java.lang.String r12 = "creative"
            java.lang.Object r12 = r10.get(r12)     // Catch:{ RuntimeException -> 0x014c }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ RuntimeException -> 0x014c }
            if (r12 == 0) goto L_0x0137
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ RuntimeException -> 0x014c }
            r11.setCreative(r12)     // Catch:{ RuntimeException -> 0x014c }
        L_0x0137:
            java.lang.String r12 = "position"
            java.lang.Object r10 = r10.get(r12)     // Catch:{ RuntimeException -> 0x014c }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ RuntimeException -> 0x014c }
            if (r10 == 0) goto L_0x0148
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ RuntimeException -> 0x014c }
            r11.setPosition(r10)     // Catch:{ RuntimeException -> 0x014c }
        L_0x0148:
            r1.addPromotion(r11)     // Catch:{ RuntimeException -> 0x014c }
            goto L_0x00f7
        L_0x014c:
            r10 = move-exception
            java.lang.String r11 = "Failed to extract a promotion from DataLayer. "
            java.lang.String r10 = r10.getMessage()
            java.lang.String r10 = java.lang.String.valueOf(r10)
            int r12 = r10.length()
            if (r12 == 0) goto L_0x0162
            java.lang.String r10 = r11.concat(r10)
            goto L_0x0167
        L_0x0162:
            java.lang.String r10 = new java.lang.String
            r10.<init>(r11)
        L_0x0167:
            com.google.android.gms.tagmanager.zzdi.zzav(r10)
            goto L_0x00f7
        L_0x016b:
            boolean r3 = r14.containsKey(r3)
            java.lang.String r5 = "&promoa"
            if (r3 == 0) goto L_0x0179
            java.lang.String r3 = "click"
            r1.set(r5, r3)
            goto L_0x017f
        L_0x0179:
            java.lang.String r3 = "view"
            r1.set(r5, r3)
        L_0x017e:
            r4 = 1
        L_0x017f:
            if (r4 == 0) goto L_0x02aa
            java.util.List<java.lang.String> r3 = zzamg
            java.util.Iterator r3 = r3.iterator()
        L_0x0187:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x02aa
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = r14.containsKey(r4)
            if (r5 == 0) goto L_0x0187
            java.lang.Object r14 = r14.get(r4)
            java.util.Map r14 = (java.util.Map) r14
            java.lang.String r3 = "products"
            java.lang.Object r3 = r14.get(r3)
            java.util.List r3 = (java.util.List) r3
            if (r3 == 0) goto L_0x01de
            java.util.Iterator r3 = r3.iterator()
        L_0x01ad:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x01de
            java.lang.Object r5 = r3.next()
            java.util.Map r5 = (java.util.Map) r5
            com.google.android.gms.analytics.ecommerce.Product r5 = zzf(r5)     // Catch:{ RuntimeException -> 0x01c1 }
            r1.addProduct(r5)     // Catch:{ RuntimeException -> 0x01c1 }
            goto L_0x01ad
        L_0x01c1:
            r5 = move-exception
            java.lang.String r5 = r5.getMessage()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            int r6 = r5.length()
            if (r6 == 0) goto L_0x01d5
            java.lang.String r5 = r8.concat(r5)
            goto L_0x01da
        L_0x01d5:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r8)
        L_0x01da:
            com.google.android.gms.tagmanager.zzdi.zzav(r5)
            goto L_0x01ad
        L_0x01de:
            boolean r3 = r14.containsKey(r0)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x0283
            java.lang.Object r14 = r14.get(r0)     // Catch:{ RuntimeException -> 0x028c }
            java.util.Map r14 = (java.util.Map) r14     // Catch:{ RuntimeException -> 0x028c }
            com.google.android.gms.analytics.ecommerce.ProductAction r0 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x028c }
            r0.<init>(r4)     // Catch:{ RuntimeException -> 0x028c }
            java.lang.Object r3 = r14.get(r7)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x01fc
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ RuntimeException -> 0x028c }
            r0.setTransactionId(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x01fc:
            java.lang.String r3 = "affiliation"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x020b
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ RuntimeException -> 0x028c }
            r0.setTransactionAffiliation(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x020b:
            java.lang.String r3 = "coupon"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x021a
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ RuntimeException -> 0x028c }
            r0.setTransactionCouponCode(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x021a:
            java.lang.Object r3 = r14.get(r9)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x0227
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ RuntimeException -> 0x028c }
            r0.setProductActionList(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x0227:
            java.lang.String r3 = "option"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x0236
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ RuntimeException -> 0x028c }
            r0.setCheckoutOptions(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x0236:
            java.lang.String r3 = "revenue"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x0249
            java.lang.Double r3 = zzm(r3)     // Catch:{ RuntimeException -> 0x028c }
            double r3 = r3.doubleValue()     // Catch:{ RuntimeException -> 0x028c }
            r0.setTransactionRevenue(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x0249:
            java.lang.String r3 = "tax"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x025c
            java.lang.Double r3 = zzm(r3)     // Catch:{ RuntimeException -> 0x028c }
            double r3 = r3.doubleValue()     // Catch:{ RuntimeException -> 0x028c }
            r0.setTransactionTax(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x025c:
            java.lang.String r3 = "shipping"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r3 == 0) goto L_0x026f
            java.lang.Double r3 = zzm(r3)     // Catch:{ RuntimeException -> 0x028c }
            double r3 = r3.doubleValue()     // Catch:{ RuntimeException -> 0x028c }
            r0.setTransactionShipping(r3)     // Catch:{ RuntimeException -> 0x028c }
        L_0x026f:
            java.lang.String r3 = "step"
            java.lang.Object r14 = r14.get(r3)     // Catch:{ RuntimeException -> 0x028c }
            if (r14 == 0) goto L_0x0288
            java.lang.Integer r14 = zzn(r14)     // Catch:{ RuntimeException -> 0x028c }
            int r14 = r14.intValue()     // Catch:{ RuntimeException -> 0x028c }
            r0.setCheckoutStep(r14)     // Catch:{ RuntimeException -> 0x028c }
            goto L_0x0288
        L_0x0283:
            com.google.android.gms.analytics.ecommerce.ProductAction r0 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x028c }
            r0.<init>(r4)     // Catch:{ RuntimeException -> 0x028c }
        L_0x0288:
            r1.setProductAction(r0)     // Catch:{ RuntimeException -> 0x028c }
            goto L_0x02aa
        L_0x028c:
            r14 = move-exception
            java.lang.String r0 = "Failed to extract a product action from DataLayer. "
            java.lang.String r14 = r14.getMessage()
            java.lang.String r14 = java.lang.String.valueOf(r14)
            int r3 = r14.length()
            if (r3 == 0) goto L_0x02a2
            java.lang.String r14 = r0.concat(r14)
            goto L_0x02a7
        L_0x02a2:
            java.lang.String r14 = new java.lang.String
            r14.<init>(r0)
        L_0x02a7:
            com.google.android.gms.tagmanager.zzdi.zzav(r14)
        L_0x02aa:
            java.util.Map r14 = r1.build()
            r2.send(r14)
            return
        L_0x02b2:
            java.lang.String r0 = zzaly
            boolean r0 = zzc(r14, r0)
            if (r0 == 0) goto L_0x02ca
            java.lang.String r0 = zzamc
            java.lang.Object r14 = r14.get(r0)
            com.google.android.gms.internal.gtm.zzl r14 = (com.google.android.gms.internal.gtm.zzl) r14
            java.util.Map r14 = r13.zzj(r14)
            r2.send(r14)
            return
        L_0x02ca:
            java.lang.String r0 = zzamd
            boolean r0 = zzc(r14, r0)
            if (r0 == 0) goto L_0x045a
            java.lang.String r0 = "transactionId"
            java.lang.String r3 = r13.zzbr(r0)
            if (r3 != 0) goto L_0x02e0
            java.lang.String r14 = "Cannot find transactionId in data layer."
            com.google.android.gms.tagmanager.zzdi.zzav(r14)
            return
        L_0x02e0:
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.lang.String r9 = zzamc     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r9 = r14.get(r9)     // Catch:{ IllegalArgumentException -> 0x0453 }
            com.google.android.gms.internal.gtm.zzl r9 = (com.google.android.gms.internal.gtm.zzl) r9     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Map r9 = r13.zzj(r9)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = "transaction"
            r9.put(r1, r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = zzame     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r10 = r14.get(r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            com.google.android.gms.internal.gtm.zzl r10 = (com.google.android.gms.internal.gtm.zzl) r10     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r10 == 0) goto L_0x0305
            java.util.Map r0 = zzi(r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            goto L_0x0338
        L_0x0305:
            java.util.Map<java.lang.String, java.lang.String> r10 = zzamj     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r10 != 0) goto L_0x0336
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ IllegalArgumentException -> 0x0453 }
            r10.<init>()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "&ti"
            r10.put(r0, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionAffiliation"
            java.lang.String r11 = "&ta"
            r10.put(r0, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionTax"
            java.lang.String r11 = "&tt"
            r10.put(r0, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionShipping"
            java.lang.String r11 = "&ts"
            r10.put(r0, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionTotal"
            java.lang.String r11 = "&tr"
            r10.put(r0, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionCurrency"
            r10.put(r0, r7)     // Catch:{ IllegalArgumentException -> 0x0453 }
            zzamj = r10     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0336:
            java.util.Map<java.lang.String, java.lang.String> r0 = zzamj     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0338:
            java.util.Set r0 = r0.entrySet()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0340:
            boolean r10 = r0.hasNext()     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r10 == 0) goto L_0x0360
            java.lang.Object r10 = r0.next()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r11 = r10.getValue()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r10 = r10.getKey()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = r13.zzbr(r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            zzd(r9, r11, r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            goto L_0x0340
        L_0x0360:
            r8.add(r9)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionProducts"
            com.google.android.gms.tagmanager.DataLayer r9 = r13.zzaed     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r0 = r9.get(r0)     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r0 != 0) goto L_0x036e
            goto L_0x0393
        L_0x036e:
            boolean r6 = r0 instanceof java.util.List     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r6 == 0) goto L_0x044b
            r6 = r0
            java.util.List r6 = (java.util.List) r6     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0379:
            boolean r9 = r6.hasNext()     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r9 == 0) goto L_0x0390
            java.lang.Object r9 = r6.next()     // Catch:{ IllegalArgumentException -> 0x0453 }
            boolean r9 = r9 instanceof java.util.Map     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r9 == 0) goto L_0x0388
            goto L_0x0379
        L_0x0388:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "Each element of transactionProducts should be of type Map."
            r14.<init>(r0)     // Catch:{ IllegalArgumentException -> 0x0453 }
            throw r14     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0390:
            r6 = r0
            java.util.List r6 = (java.util.List) r6     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0393:
            if (r6 == 0) goto L_0x0436
            java.util.Iterator r0 = r6.iterator()     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0399:
            boolean r6 = r0.hasNext()     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r6 == 0) goto L_0x0436
            java.lang.Object r6 = r0.next()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Map r6 = (java.util.Map) r6     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r9 = r6.get(r5)     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r9 != 0) goto L_0x03b1
            java.lang.String r14 = "Unable to send transaction item hit due to missing 'name' field."
            com.google.android.gms.tagmanager.zzdi.zzav(r14)     // Catch:{ IllegalArgumentException -> 0x0453 }
            return
        L_0x03b1:
            java.lang.String r9 = zzamc     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r9 = r14.get(r9)     // Catch:{ IllegalArgumentException -> 0x0453 }
            com.google.android.gms.internal.gtm.zzl r9 = (com.google.android.gms.internal.gtm.zzl) r9     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Map r9 = r13.zzj(r9)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = "item"
            r9.put(r1, r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = "&ti"
            r9.put(r10, r3)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r10 = zzamf     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r10 = r14.get(r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            com.google.android.gms.internal.gtm.zzl r10 = (com.google.android.gms.internal.gtm.zzl) r10     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r10 == 0) goto L_0x03d6
            java.util.Map r10 = zzi(r10)     // Catch:{ IllegalArgumentException -> 0x0453 }
            goto L_0x0409
        L_0x03d6:
            java.util.Map<java.lang.String, java.lang.String> r10 = zzamk     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r10 != 0) goto L_0x0407
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ IllegalArgumentException -> 0x0453 }
            r10.<init>()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "&in"
            r10.put(r5, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "sku"
            java.lang.String r12 = "&ic"
            r10.put(r11, r12)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "category"
            java.lang.String r12 = "&iv"
            r10.put(r11, r12)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "price"
            java.lang.String r12 = "&ip"
            r10.put(r11, r12)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "quantity"
            java.lang.String r12 = "&iq"
            r10.put(r11, r12)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = "currency"
            r10.put(r11, r7)     // Catch:{ IllegalArgumentException -> 0x0453 }
            zzamk = r10     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0407:
            java.util.Map<java.lang.String, java.lang.String> r10 = zzamk     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0409:
            java.util.Set r10 = r10.entrySet()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Iterator r10 = r10.iterator()     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0411:
            boolean r11 = r10.hasNext()     // Catch:{ IllegalArgumentException -> 0x0453 }
            if (r11 == 0) goto L_0x0431
            java.lang.Object r11 = r10.next()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.util.Map$Entry r11 = (java.util.Map.Entry) r11     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r12 = r11.getValue()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r11 = r11.getKey()     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.Object r11 = r6.get(r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ IllegalArgumentException -> 0x0453 }
            zzd(r9, r12, r11)     // Catch:{ IllegalArgumentException -> 0x0453 }
            goto L_0x0411
        L_0x0431:
            r8.add(r9)     // Catch:{ IllegalArgumentException -> 0x0453 }
            goto L_0x0399
        L_0x0436:
            java.util.ArrayList r8 = (java.util.ArrayList) r8     // Catch:{ IllegalArgumentException -> 0x0453 }
            int r14 = r8.size()     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x043c:
            if (r4 >= r14) goto L_0x044a
            java.lang.Object r0 = r8.get(r4)     // Catch:{ IllegalArgumentException -> 0x0453 }
            int r4 = r4 + 1
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ IllegalArgumentException -> 0x0453 }
            r2.send(r0)     // Catch:{ IllegalArgumentException -> 0x0453 }
            goto L_0x043c
        L_0x044a:
            return
        L_0x044b:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException -> 0x0453 }
            java.lang.String r0 = "transactionProducts should be of type List."
            r14.<init>(r0)     // Catch:{ IllegalArgumentException -> 0x0453 }
            throw r14     // Catch:{ IllegalArgumentException -> 0x0453 }
        L_0x0453:
            r14 = move-exception
            java.lang.String r0 = "Unable to send transaction"
            com.google.android.gms.tagmanager.zzdi.zza(r0, r14)
            return
        L_0x045a:
            java.lang.String r14 = "Ignoring unknown tag."
            com.google.android.gms.tagmanager.zzdi.zzac(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzgk.zzd(java.util.Map):void");
    }

    private final String zzbr(String str) {
        Object obj = this.zzaed.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private static void zzd(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private static Product zzf(Map<String, Object> map) {
        Product product = new Product();
        Object obj = map.get(TtmlNode.ATTR_ID);
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        Object obj2 = map.get(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        if (obj2 != null) {
            product.setName(String.valueOf(obj2));
        }
        Object obj3 = map.get("brand");
        if (obj3 != null) {
            product.setBrand(String.valueOf(obj3));
        }
        Object obj4 = map.get("category");
        if (obj4 != null) {
            product.setCategory(String.valueOf(obj4));
        }
        Object obj5 = map.get("variant");
        if (obj5 != null) {
            product.setVariant(String.valueOf(obj5));
        }
        Object obj6 = map.get(Param.COUPON);
        if (obj6 != null) {
            product.setCouponCode(String.valueOf(obj6));
        }
        Object obj7 = map.get(Constants.KEY_RSS_POSITION);
        if (obj7 != null) {
            product.setPosition(zzn(obj7).intValue());
        }
        Object obj8 = map.get("price");
        if (obj8 != null) {
            product.setPrice(zzm(obj8).doubleValue());
        }
        Object obj9 = map.get(Param.QUANTITY);
        if (obj9 != null) {
            product.setQuantity(zzn(obj9).intValue());
        }
        for (String str : map.keySet()) {
            Matcher matcher = zzamh.matcher(str);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str)));
                } catch (NumberFormatException unused) {
                    String str2 = "illegal number in custom dimension value: ";
                    String valueOf = String.valueOf(str);
                    zzdi.zzac(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            } else {
                Matcher matcher2 = zzami.matcher(str);
                if (matcher2.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher2.group(1)), zzn(map.get(str)).intValue());
                    } catch (NumberFormatException unused2) {
                        String str3 = "illegal number in custom metric value: ";
                        String valueOf2 = String.valueOf(str);
                        zzdi.zzac(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
                    }
                }
            }
        }
        return product;
    }

    private static Map<String, String> zzi(zzl zzl) {
        Object zzh = zzgj.zzh(zzl);
        if (!(zzh instanceof Map)) {
            return null;
        }
        Map map = (Map) zzh;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private final Map<String, String> zzj(zzl zzl) {
        if (zzl == null) {
            return new HashMap();
        }
        Map<String, String> zzi = zzi(zzl);
        if (zzi == null) {
            return new HashMap();
        }
        String str = "&aip";
        String str2 = (String) zzi.get(str);
        if (str2 != null && this.zzaml.contains(str2.toLowerCase())) {
            zzi.remove(str);
        }
        return zzi;
    }

    private static Double zzm(Object obj) {
        String str = "Cannot convert the object to Double: ";
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                String valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        } else {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            String valueOf2 = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
        }
    }

    private static Integer zzn(Object obj) {
        String str = "Cannot convert the object to Integer: ";
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                String valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            String valueOf2 = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
        }
    }

    public final /* bridge */ /* synthetic */ zzl zzb(Map map) {
        return super.zzb(map);
    }

    public final /* bridge */ /* synthetic */ boolean zzgw() {
        return super.zzgw();
    }

    public final /* bridge */ /* synthetic */ Set zzig() {
        return super.zzig();
    }

    public final /* bridge */ /* synthetic */ String zzif() {
        return super.zzif();
    }
}
