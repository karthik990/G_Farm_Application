package com.startapp.android.publish.adsCommon.p078b;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.C5033h;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.startapp.android.publish.adsCommon.b.c */
/* compiled from: StartAppSDK */
public class C4987c {
    /* renamed from: a */
    public static boolean m3092a(Context context, C4925Ad ad) {
        boolean z = false;
        if (ad != null) {
            HashSet hashSet = new HashSet();
            if (ad instanceof C5003e) {
                return m3088a(context, m3091a(((C5003e) ad).mo62243f(), 0), 0, (Set<String>) hashSet, (List<C4984a>) new ArrayList<C4984a>()).booleanValue();
            }
            if (ad instanceof C5033h) {
                List a = m3090a(context, ((C5033h) ad).mo62336d(), 0, (Set<String>) hashSet, false);
                if (a == null || a.size() == 0) {
                    z = true;
                }
            }
        }
        return z;
    }

    /* renamed from: a */
    public static List<AdDetails> m3089a(Context context, List<AdDetails> list, int i, Set<String> set) {
        return m3090a(context, list, i, set, true);
    }

    /* renamed from: a */
    public static List<AdDetails> m3090a(Context context, List<AdDetails> list, int i, Set<String> set, boolean z) {
        Context context2 = context;
        int i2 = i;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        boolean z2 = false;
        for (AdDetails adDetails : list) {
            C4984a aVar = new C4984a(adDetails.getTrackingUrl(), adDetails.getAppPresencePackage(), i2, adDetails.getMinAppVersion());
            boolean z3 = adDetails.getAppPresencePackage() != null && adDetails.getAppPresencePackage().startsWith("!");
            String appPresencePackage = adDetails.getAppPresencePackage();
            if (z3) {
                appPresencePackage = appPresencePackage.substring(1);
            }
            boolean a = C5146c.m3761a(context2, appPresencePackage, adDetails.getMinAppVersion());
            boolean z4 = C4983b.m3032a().mo62151E() && ((a && !z3) || (!a && z3));
            arrayList3.add(aVar);
            String str = "]";
            String str2 = "AppPresenceUtil";
            if (z4) {
                aVar.mo62191b(a);
                aVar.mo62189a(false);
                if (!z3) {
                    arrayList2.add(adDetails);
                    arrayList4.add(aVar);
                }
                set.add(adDetails.getPackageName());
                StringBuilder sb = new StringBuilder();
                sb.append("App Presence:[");
                sb.append(adDetails.getPackageName());
                sb.append(str);
                C5155g.m3807a(str2, 3, sb.toString());
                z2 = true;
            } else {
                Set<String> set2 = set;
                arrayList.add(adDetails);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("App Not Presence:[");
            sb2.append(adDetails.getPackageName());
            sb2.append(str);
            C5155g.m3807a(str2, 3, sb2.toString());
        }
        if (arrayList.size() < 5 && (list.size() != 1 || i2 > 0)) {
            int min = Math.min(5 - arrayList.size(), arrayList2.size());
            arrayList.addAll(arrayList2.subList(0, min));
            for (C4984a a2 : arrayList4.subList(0, min)) {
                a2.mo62189a(true);
            }
        }
        if (z2) {
            C5053l.m3367c(context);
            if (z) {
                new C4985b(context2, arrayList3).mo62195a();
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static List<C4984a> m3091a(String str, int i) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = new String[0];
        String str2 = "@tracking@";
        String a = C4946i.m2908a(str, str2, str2);
        String str3 = ",";
        if (a != null) {
            strArr = a.split(str3);
        }
        String[] strArr2 = new String[0];
        String str4 = "@appPresencePackage@";
        String a2 = C4946i.m2908a(str, str4, str4);
        if (a2 != null) {
            strArr2 = a2.split(str3);
        }
        String[] strArr3 = new String[0];
        String str5 = "@minAppVersion@";
        String a3 = C4946i.m2908a(str, str5, str5);
        if (a3 != null) {
            strArr3 = a3.split(str3);
        }
        int i2 = 0;
        while (i2 < strArr2.length) {
            arrayList.add(new C4984a(strArr.length > i2 ? strArr[i2] : null, strArr2[i2], i, strArr3.length > i2 ? Integer.valueOf(strArr3[i2]).intValue() : 0));
            i2++;
        }
        while (i2 < strArr.length) {
            arrayList.add(new C4984a(strArr[i2], "", i, strArr3.length > i2 ? Integer.valueOf(strArr3[i2]).intValue() : 0));
            i2++;
        }
        return arrayList;
    }

    /* renamed from: a */
    public static Boolean m3088a(Context context, List<C4984a> list, int i, Set<String> set, List<C4984a> list2) {
        boolean z = false;
        for (C4984a aVar : list) {
            boolean startsWith = aVar.mo62190b().startsWith("!");
            boolean z2 = true;
            String b = aVar.mo62190b();
            if (startsWith) {
                b = b.substring(1);
            }
            boolean a = C5146c.m3761a(context, b, aVar.mo62194e());
            if ((!startsWith && a) || (startsWith && !a)) {
                C5155g.m3807a("AppPresenceUtil", 3, "in isAppPresent, skipAd is true");
                aVar.mo62191b(a);
                if (i != 0) {
                    z2 = false;
                }
                if (z2 && !startsWith) {
                    set.add(aVar.mo62190b());
                } else if (!z2 && aVar.mo62187a() != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(aVar.mo62187a());
                    sb.append("&isShown=");
                    sb.append(aVar.mo62192c());
                    sb.append("&appPresence=");
                    sb.append(aVar.mo62193d());
                    aVar.mo62188a(sb.toString());
                }
                z = z2;
            }
            list2.add(aVar);
        }
        if (z) {
            for (int i2 = 0; i2 < list2.size(); i2++) {
                ((C4984a) list2.get(i2)).mo62189a(false);
            }
        }
        return Boolean.valueOf(z);
    }
}
