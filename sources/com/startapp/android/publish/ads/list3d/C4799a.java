package com.startapp.android.publish.ads.list3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.startapp.android.publish.adsCommon.C5040i;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.common.p092a.C5150d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: com.startapp.android.publish.ads.list3d.a */
/* compiled from: StartAppSDK */
public class C4799a {

    /* renamed from: a */
    HashMap<String, C5040i> f2603a = new HashMap<>();

    /* renamed from: b */
    Hashtable<String, Bitmap> f2604b = new Hashtable<>();

    /* renamed from: c */
    Set<String> f2605c = new HashSet();

    /* renamed from: d */
    C4812g f2606d;

    /* renamed from: e */
    int f2607e = 0;

    /* renamed from: f */
    ConcurrentLinkedQueue<C4801b> f2608f = new ConcurrentLinkedQueue<>();

    /* renamed from: com.startapp.android.publish.ads.list3d.a$a */
    /* compiled from: StartAppSDK */
    private class C4800a extends AsyncTask<Void, Void, Bitmap> {

        /* renamed from: a */
        int f2609a = -1;

        /* renamed from: b */
        String f2610b;

        /* renamed from: c */
        String f2611c;

        public C4800a(int i, String str, String str2) {
            this.f2609a = i;
            this.f2610b = str;
            this.f2611c = str2;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Bitmap doInBackground(Void... voidArr) {
            return C5150d.m3785b(this.f2611c);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Bitmap bitmap) {
            C4799a.this.f2607e--;
            if (bitmap != null) {
                C4799a.this.f2604b.put(this.f2610b, bitmap);
                if (C4799a.this.f2606d != null) {
                    C4799a.this.f2606d.mo61390a(this.f2609a);
                }
                C4799a.this.mo61441e();
            }
        }
    }

    /* renamed from: com.startapp.android.publish.ads.list3d.a$b */
    /* compiled from: StartAppSDK */
    class C4801b {

        /* renamed from: a */
        int f2613a;

        /* renamed from: b */
        String f2614b;

        /* renamed from: c */
        String f2615c;

        public C4801b(int i, String str, String str2) {
            this.f2613a = i;
            this.f2614b = str;
            this.f2615c = str2;
        }
    }

    /* renamed from: a */
    public void mo61436a(C4812g gVar, boolean z) {
        this.f2606d = gVar;
        if (z) {
            mo61434a();
        }
    }

    /* renamed from: a */
    public void mo61434a() {
        this.f2605c.clear();
        this.f2607e = 0;
        this.f2608f.clear();
        HashMap<String, C5040i> hashMap = this.f2603a;
        if (hashMap != null) {
            for (String str : hashMap.keySet()) {
                ((C5040i) this.f2603a.get(str)).mo62345a(false);
            }
            this.f2603a.clear();
        }
    }

    /* renamed from: a */
    public void mo61435a(Context context, String str, String str2, C5002b bVar, long j) {
        if (!this.f2603a.containsKey(str2)) {
            C5040i iVar = new C5040i(context, new String[]{str2}, bVar, j);
            this.f2603a.put(str2, iVar);
            iVar.mo62344a();
        }
    }

    /* renamed from: a */
    public void mo61437a(String str) {
        HashMap<String, C5040i> hashMap = this.f2603a;
        if (hashMap != null && hashMap.containsKey(str) && this.f2603a.get(str) != null) {
            ((C5040i) this.f2603a.get(str)).mo62345a(true);
        }
    }

    /* renamed from: b */
    public void mo61438b() {
        for (String str : this.f2603a.keySet()) {
            if (this.f2603a.get(str) != null) {
                ((C5040i) this.f2603a.get(str)).mo62346b();
            }
        }
    }

    /* renamed from: c */
    public void mo61439c() {
        for (String str : this.f2603a.keySet()) {
            if (this.f2603a.get(str) != null) {
                ((C5040i) this.f2603a.get(str)).mo62344a();
            }
        }
    }

    /* renamed from: d */
    public void mo61440d() {
        for (String str : this.f2603a.keySet()) {
            if (this.f2603a.get(str) != null) {
                ((C5040i) this.f2603a.get(str)).mo62345a(false);
            }
        }
    }

    /* renamed from: a */
    public Bitmap mo61433a(int i, String str, String str2) {
        Bitmap bitmap = (Bitmap) this.f2604b.get(str);
        if (bitmap != null) {
            return bitmap;
        }
        if (!this.f2605c.contains(str)) {
            this.f2605c.add(str);
            int i2 = this.f2607e;
            if (i2 >= 15) {
                this.f2608f.add(new C4801b(i, str, str2));
            } else {
                this.f2607e = i2 + 1;
                new C4800a(i, str, str2).execute(new Void[0]);
            }
        }
        return null;
    }

    /* renamed from: e */
    public void mo61441e() {
        if (!this.f2608f.isEmpty()) {
            C4801b bVar = (C4801b) this.f2608f.poll();
            new C4800a(bVar.f2613a, bVar.f2614b, bVar.f2615c).execute(new Void[0]);
        }
    }
}
