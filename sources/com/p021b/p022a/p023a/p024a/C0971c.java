package com.p021b.p022a.p023a.p024a;

import android.content.Context;
import com.p021b.p022a.p023a.p024a.p028c.C0973b;
import com.p021b.p022a.p023a.p024a.p028c.C0976c;
import com.p021b.p022a.p023a.p024a.p028c.C0979e;
import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import com.p021b.p022a.p023a.p024a.p030e.C0991e;

/* renamed from: com.b.a.a.a.c */
public class C0971c {

    /* renamed from: a */
    private boolean f131a;

    /* renamed from: b */
    private void m162b(String str, Context context) {
        m163c(str);
        C0991e.m256a((Object) context, "Application Context cannot be null");
    }

    /* renamed from: c */
    private void m163c(String str) {
        C0991e.m256a((Object) str, "Version cannot be null");
        if (!str.matches("[0-9]+\\.[0-9]+\\.[0-9]+.*")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid version format : ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public String mo11506a() {
        return "1.2.0-Startapp";
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo11507a(boolean z) {
        this.f131a = z;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo11508a(String str) {
        return mo11510b(mo11506a()) == mo11510b(str);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo11509a(String str, Context context) {
        m162b(str, context);
        if (!mo11508a(str)) {
            return false;
        }
        if (!mo11511b()) {
            mo11507a(true);
            C0979e.m206a().mo11541a(context);
            C0973b.m177a().mo11518a(context);
            C0987b.m237a(context);
            C0976c.m189a().mo11525a(context);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public int mo11510b(String str) {
        m163c(str);
        return Integer.parseInt(str.split("\\.", 2)[0]);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public boolean mo11511b() {
        return this.f131a;
    }
}
