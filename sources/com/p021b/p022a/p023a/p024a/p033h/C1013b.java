package com.p021b.p022a.p023a.p024a.p033h;

import android.view.View;
import android.view.ViewParent;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import com.p021b.p022a.p023a.p024a.p028c.C0972a;
import com.p021b.p022a.p023a.p024a.p030e.C0992f;
import com.p021b.p022a.p023a.p024a.p031f.C0993a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/* renamed from: com.b.a.a.a.h.b */
public class C1013b {

    /* renamed from: a */
    private final HashMap<View, String> f204a = new HashMap<>();

    /* renamed from: b */
    private final HashMap<View, ArrayList<String>> f205b = new HashMap<>();

    /* renamed from: c */
    private final HashSet<View> f206c = new HashSet<>();

    /* renamed from: d */
    private final HashSet<String> f207d = new HashSet<>();

    /* renamed from: e */
    private final HashSet<String> f208e = new HashSet<>();

    /* renamed from: f */
    private boolean f209f;

    /* renamed from: a */
    private void m331a(View view, C0970i iVar) {
        ArrayList arrayList = (ArrayList) this.f205b.get(view);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.f205b.put(view, arrayList);
        }
        arrayList.add(iVar.mo11499g());
    }

    /* renamed from: a */
    private void m332a(C0970i iVar) {
        for (C0993a aVar : iVar.mo11496d()) {
            View view = (View) aVar.get();
            if (view != null) {
                m331a(view, iVar);
            }
        }
    }

    /* renamed from: d */
    private boolean m333d(View view) {
        if (!view.hasWindowFocus()) {
            return false;
        }
        HashSet hashSet = new HashSet();
        while (view != null) {
            if (!C0992f.m269d(view)) {
                return false;
            }
            hashSet.add(view);
            ViewParent parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        this.f206c.addAll(hashSet);
        return true;
    }

    /* renamed from: a */
    public String mo11594a(View view) {
        if (this.f204a.size() == 0) {
            return null;
        }
        String str = (String) this.f204a.get(view);
        if (str != null) {
            this.f204a.remove(view);
        }
        return str;
    }

    /* renamed from: a */
    public HashSet<String> mo11595a() {
        return this.f207d;
    }

    /* renamed from: b */
    public ArrayList<String> mo11596b(View view) {
        if (this.f205b.size() == 0) {
            return null;
        }
        ArrayList<String> arrayList = (ArrayList) this.f205b.get(view);
        if (arrayList != null) {
            this.f205b.remove(view);
            Collections.sort(arrayList);
        }
        return arrayList;
    }

    /* renamed from: b */
    public HashSet<String> mo11597b() {
        return this.f208e;
    }

    /* renamed from: c */
    public C1015d mo11598c(View view) {
        if (this.f206c.contains(view)) {
            return C1015d.PARENT_VIEW;
        }
        return this.f209f ? C1015d.OBSTRUCTION_VIEW : C1015d.UNDERLYING_VIEW;
    }

    /* renamed from: c */
    public void mo11599c() {
        C0972a a = C0972a.m170a();
        if (a != null) {
            for (C0970i iVar : a.mo11515c()) {
                View h = iVar.mo11500h();
                if (iVar.mo11501i()) {
                    if (h == null || !m333d(h)) {
                        this.f208e.add(iVar.mo11499g());
                    } else {
                        this.f207d.add(iVar.mo11499g());
                        this.f204a.put(h, iVar.mo11499g());
                        m332a(iVar);
                    }
                }
            }
        }
    }

    /* renamed from: d */
    public void mo11600d() {
        this.f204a.clear();
        this.f205b.clear();
        this.f206c.clear();
        this.f207d.clear();
        this.f208e.clear();
        this.f209f = false;
    }

    /* renamed from: e */
    public void mo11601e() {
        this.f209f = true;
    }
}
