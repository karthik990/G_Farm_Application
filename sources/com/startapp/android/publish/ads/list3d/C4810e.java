package com.startapp.android.publish.ads.list3d;

import android.content.Context;
import android.graphics.Bitmap;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.common.model.AdDetails;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.android.publish.ads.list3d.e */
/* compiled from: StartAppSDK */
public class C4810e {

    /* renamed from: a */
    private C4799a f2661a = new C4799a();

    /* renamed from: b */
    private List<ListItem> f2662b;

    /* renamed from: c */
    private String f2663c = "";

    /* renamed from: a */
    public void mo61483a() {
        this.f2662b = new ArrayList();
        this.f2663c = "";
    }

    /* renamed from: a */
    public void mo61486a(AdDetails adDetails) {
        ListItem listItem = new ListItem(adDetails);
        this.f2662b.add(listItem);
        this.f2661a.mo61433a(this.f2662b.size() - 1, listItem.mo61404a(), listItem.mo61413i());
    }

    /* renamed from: b */
    public void mo61488b() {
        this.f2661a.mo61438b();
    }

    /* renamed from: c */
    public void mo61490c() {
        this.f2661a.mo61439c();
    }

    /* renamed from: d */
    public void mo61491d() {
        this.f2661a.mo61440d();
    }

    /* renamed from: e */
    public List<ListItem> mo61492e() {
        return this.f2662b;
    }

    /* renamed from: a */
    public Bitmap mo61482a(int i, String str, String str2) {
        return this.f2661a.mo61433a(i, str, str2);
    }

    /* renamed from: a */
    public void mo61484a(Context context, String str, String str2, C5002b bVar, long j) {
        C4799a aVar = this.f2661a;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(this.f2663c);
        aVar.mo61435a(context, str, sb.toString(), bVar, j);
    }

    /* renamed from: a */
    public void mo61487a(String str) {
        C4799a aVar = this.f2661a;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(this.f2663c);
        aVar.mo61437a(sb.toString());
    }

    /* renamed from: a */
    public void mo61485a(C4812g gVar, boolean z) {
        this.f2661a.mo61436a(gVar, z);
    }

    /* renamed from: b */
    public void mo61489b(String str) {
        this.f2663c = str;
    }
}
