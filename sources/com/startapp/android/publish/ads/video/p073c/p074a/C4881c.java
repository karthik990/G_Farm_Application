package com.startapp.android.publish.ads.video.p073c.p074a;

import android.content.Context;
import android.util.DisplayMetrics;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4876b;
import com.startapp.common.p092a.C5156h;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.startapp.android.publish.ads.video.c.a.c */
/* compiled from: StartAppSDK */
public class C4881c {

    /* renamed from: a */
    protected int f2886a;

    /* renamed from: b */
    protected int f2887b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public int f2888c;

    /* renamed from: com.startapp.android.publish.ads.video.c.a.c$a */
    /* compiled from: StartAppSDK */
    private class C4883a implements Comparator<C4876b> {
        private C4883a() {
        }

        /* renamed from: a */
        public int compare(C4876b bVar, C4876b bVar2) {
            int intValue = bVar.mo61728d().intValue() * bVar.mo61730e().intValue();
            int intValue2 = bVar2.mo61728d().intValue() * bVar2.mo61730e().intValue();
            int abs = Math.abs(intValue - C4881c.this.f2888c);
            int abs2 = Math.abs(intValue2 - C4881c.this.f2888c);
            if (abs < abs2) {
                return -1;
            }
            return abs > abs2 ? 1 : 0;
        }
    }

    public C4881c(Context context) {
        m2685a(context);
    }

    /* renamed from: a */
    public C4876b mo61744a(List<C4876b> list) {
        if (list == null || mo61747c(list) == 0) {
            return null;
        }
        Collections.sort(list, mo61745a());
        return mo61746b(list);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Comparator<C4876b> mo61745a() {
        return new C4883a();
    }

    /* renamed from: a */
    private void m2685a(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.f2886a = displayMetrics.widthPixels;
        this.f2887b = displayMetrics.heightPixels;
        this.f2888c = this.f2886a * this.f2887b;
        if (!C5156h.m3812a(context).equals("WIFI")) {
            this.f2888c = (int) (((float) this.f2888c) * 0.75f);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public C4876b mo61746b(List<C4876b> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (C4876b) list.get(0);
    }

    /* renamed from: a */
    private boolean m2686a(C4876b bVar) {
        return bVar.mo61721b().matches("video/.*(?i)(mp4|3gpp|mp2t|webm|matroska)");
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public int mo61747c(List<C4876b> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C4876b bVar = (C4876b) it.next();
            if (!bVar.mo61732f() || !m2686a(bVar)) {
                it.remove();
            }
        }
        return list.size();
    }
}
