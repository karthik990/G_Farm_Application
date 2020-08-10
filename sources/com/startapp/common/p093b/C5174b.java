package com.startapp.common.p093b;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.startapp.common.b.b */
/* compiled from: StartAppSDK */
public class C5174b {

    /* renamed from: a */
    private final C5176a f3608a;

    /* renamed from: com.startapp.common.b.b$a */
    /* compiled from: StartAppSDK */
    public static class C5176a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public int f3609a;
        /* access modifiers changed from: private */

        /* renamed from: b */
        public Map<String, String> f3610b = new HashMap();
        /* access modifiers changed from: private */

        /* renamed from: c */
        public long f3611c;
        /* access modifiers changed from: private */

        /* renamed from: d */
        public long f3612d = 100;
        /* access modifiers changed from: private */

        /* renamed from: e */
        public boolean f3613e = false;
        /* access modifiers changed from: private */

        /* renamed from: f */
        public boolean f3614f = false;

        public C5176a(int i) {
            this.f3609a = i;
        }

        /* renamed from: a */
        public C5176a mo62894a(Map<String, String> map) {
            if (map != null) {
                this.f3610b.putAll(map);
            }
            return this;
        }

        /* renamed from: a */
        public C5176a mo62893a(String str, String str2) {
            this.f3610b.put(str, str2);
            return this;
        }

        /* renamed from: a */
        public C5176a mo62892a(long j) {
            this.f3611c = j;
            return this;
        }

        /* renamed from: a */
        public C5176a mo62895a(boolean z) {
            this.f3613e = z;
            return this;
        }

        /* renamed from: b */
        public C5176a mo62897b(boolean z) {
            this.f3614f = z;
            return this;
        }

        /* renamed from: a */
        public C5174b mo62896a() {
            return new C5174b(this);
        }
    }

    private C5174b(C5176a aVar) {
        this.f3608a = aVar;
    }

    /* renamed from: a */
    public int mo62885a() {
        return this.f3608a.f3609a;
    }

    /* renamed from: b */
    public Map<String, String> mo62886b() {
        return this.f3608a.f3610b;
    }

    /* renamed from: c */
    public long mo62887c() {
        return this.f3608a.f3611c;
    }

    /* renamed from: d */
    public long mo62888d() {
        return this.f3608a.f3612d;
    }

    /* renamed from: e */
    public boolean mo62889e() {
        return this.f3608a.f3613e;
    }

    /* renamed from: f */
    public boolean mo62890f() {
        return this.f3608a.f3614f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RunnerRequest: ");
        sb.append(this.f3608a.f3609a);
        String str = " ";
        sb.append(str);
        sb.append(this.f3608a.f3611c);
        sb.append(str);
        sb.append(this.f3608a.f3613e);
        sb.append(str);
        sb.append(this.f3608a.f3612d);
        sb.append(str);
        sb.append(this.f3608a.f3610b);
        return sb.toString();
    }
}
