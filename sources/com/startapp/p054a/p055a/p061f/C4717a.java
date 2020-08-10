package com.startapp.p054a.p055a.p061f;

import com.startapp.p054a.p055a.p056a.C4698c;
import com.startapp.p054a.p055a.p059d.C4711e;
import com.startapp.p054a.p055a.p060e.C4713b;
import com.startapp.p054a.p055a.p062g.C4719a;
import com.startapp.p054a.p055a.p062g.C4722c;

/* renamed from: com.startapp.a.a.f.a */
/* compiled from: StartAppSDK */
public class C4717a {

    /* renamed from: a */
    private final C4713b f2422a;

    /* renamed from: b */
    private final C4722c f2423b;

    public C4717a(C4713b bVar, C4722c cVar) {
        this.f2423b = cVar;
        this.f2422a = bVar;
    }

    /* renamed from: a */
    public String mo61125a(C4719a aVar, C4698c cVar, long j) {
        String str = "-";
        try {
            String a = this.f2422a.mo61119a(cVar);
            C4711e b = this.f2423b.mo61132b(aVar);
            StringBuilder sb = new StringBuilder();
            sb.append(j);
            sb.append(str);
            sb.append(aVar.mo61126a());
            sb.append(str);
            sb.append(b.mo61116a(a));
            return sb.toString();
        } catch (Throwable unused) {
            return null;
        }
    }
}
