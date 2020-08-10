package com.startapp.android.publish.adsCommon.p078b;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.android.publish.adsCommon.b.b */
/* compiled from: StartAppSDK */
public class C4985b {

    /* renamed from: a */
    private List<C4984a> f3156a;

    /* renamed from: b */
    private Context f3157b;

    /* renamed from: c */
    private List<String> f3158c = new ArrayList();

    public C4985b(Context context, List<C4984a> list) {
        this.f3156a = list;
        this.f3157b = context;
    }

    /* renamed from: a */
    public void mo62195a() {
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                C4985b.this.mo62196b();
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public Boolean mo62196b() {
        boolean z;
        C5155g.m3805a(3, "in doInBackground handler");
        try {
            m3085c();
            z = true;
        } catch (Exception e) {
            C5017f.m3256a(this.f3157b, C5015d.EXCEPTION, "AppPresenceHandler.doInBackground - sendAdImpressions failed", e.getMessage(), "");
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* renamed from: c */
    private void m3085c() {
        m3084a(this.f3156a, this.f3158c);
        for (int i = 0; i < this.f3158c.size(); i++) {
            String str = (String) this.f3158c.get(i);
            if (str.length() != 0) {
                C4988c.m3101a(this.f3157b, str, new C5002b().setNonImpressionReason("APP_PRESENCE"));
            }
        }
    }

    /* renamed from: a */
    private void m3084a(List<C4984a> list, List<String> list2) {
        C5155g.m3805a(3, "in getAppPresenceDParameter()");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (C4984a aVar : list) {
            if (!aVar.mo62192c()) {
                String a = m3083a(aVar.mo62187a());
                String str = "d=";
                if (aVar.mo62193d()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(a);
                    arrayList.add(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(a);
                    arrayList2.add(sb2.toString());
                }
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("appPresence tracking size = ");
        sb3.append(arrayList.size());
        sb3.append(" normal size = ");
        sb3.append(arrayList2.size());
        C5155g.m3805a(3, sb3.toString());
        String str2 = "false";
        if (!arrayList.isEmpty()) {
            list2.addAll(C4988c.m3098a((List<String>) arrayList, str2, "true"));
        }
        if (!arrayList2.isEmpty()) {
            list2.addAll(C4988c.m3098a((List<String>) arrayList2, str2, str2));
        }
    }

    /* renamed from: a */
    private String m3083a(String str) {
        return str.split("tracking/adImpression[?]d=")[1];
    }
}
