package com.p021b.p022a.p023a.p024a.p033h;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.p021b.p022a.p023a.p024a.p029d.C0981a;
import com.p021b.p022a.p023a.p024a.p029d.C0981a.C0982a;
import com.p021b.p022a.p023a.p024a.p029d.C0983b;
import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import com.p021b.p022a.p023a.p024a.p030e.C0990d;
import com.p021b.p022a.p023a.p024a.p030e.C0992f;
import com.p021b.p022a.p023a.p024a.p033h.p034a.C1009c;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.h.a */
public class C1000a implements C0982a {

    /* renamed from: a */
    private static C1000a f183a = new C1000a();

    /* renamed from: b */
    private static Handler f184b = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static Handler f185c = null;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public static final Runnable f186j = new Runnable() {
        public void run() {
            C1000a.m294a().m304i();
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: k */
    public static final Runnable f187k = new Runnable() {
        public void run() {
            if (C1000a.f185c != null) {
                C1000a.f185c.post(C1000a.f186j);
                C1000a.f185c.postDelayed(C1000a.f187k, 200);
            }
        }
    };

    /* renamed from: d */
    private List<C1004a> f188d = new ArrayList();

    /* renamed from: e */
    private int f189e;

    /* renamed from: f */
    private C0983b f190f = new C0983b();

    /* renamed from: g */
    private C1013b f191g = new C1013b();
    /* access modifiers changed from: private */

    /* renamed from: h */
    public C1014c f192h = new C1014c(new C1009c());

    /* renamed from: i */
    private double f193i;

    /* renamed from: com.b.a.a.a.h.a$a */
    public interface C1004a {
        /* renamed from: a */
        void mo11579a(int i, long j);
    }

    C1000a() {
    }

    /* renamed from: a */
    public static C1000a m294a() {
        return f183a;
    }

    /* renamed from: a */
    private void m296a(long j) {
        if (this.f188d.size() > 0) {
            for (C1004a a : this.f188d) {
                a.mo11579a(this.f189e, j);
            }
        }
    }

    /* renamed from: a */
    private void m297a(View view, C0981a aVar, JSONObject jSONObject, C1015d dVar) {
        aVar.mo11546a(view, jSONObject, this, dVar == C1015d.PARENT_VIEW);
    }

    /* renamed from: a */
    private boolean m298a(View view, JSONObject jSONObject) {
        String a = this.f191g.mo11594a(view);
        if (a == null) {
            return false;
        }
        C0987b.m239a(jSONObject, a);
        this.f191g.mo11601e();
        return true;
    }

    /* renamed from: b */
    private void m299b(View view, JSONObject jSONObject) {
        ArrayList b = this.f191g.mo11596b(view);
        if (b != null) {
            C0987b.m241a(jSONObject, (List<String>) b);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void m304i() {
        m305j();
        mo11575e();
        m306k();
    }

    /* renamed from: j */
    private void m305j() {
        this.f189e = 0;
        this.f193i = C0990d.m252a();
    }

    /* renamed from: k */
    private void m306k() {
        m296a((long) (C0990d.m252a() - this.f193i));
    }

    /* renamed from: l */
    private void m307l() {
        if (f185c == null) {
            f185c = new Handler(Looper.getMainLooper());
            f185c.post(f186j);
            f185c.postDelayed(f187k, 200);
        }
    }

    /* renamed from: m */
    private void m308m() {
        Handler handler = f185c;
        if (handler != null) {
            handler.removeCallbacks(f187k);
            f185c = null;
        }
    }

    /* renamed from: a */
    public void mo11547a(View view, C0981a aVar, JSONObject jSONObject) {
        if (C0992f.m269d(view)) {
            C1015d c = this.f191g.mo11598c(view);
            if (c != C1015d.UNDERLYING_VIEW) {
                JSONObject a = aVar.mo11545a(view);
                C0987b.m242a(jSONObject, a);
                if (!m298a(view, a)) {
                    m299b(view, a);
                    m297a(view, aVar, a, c);
                }
                this.f189e++;
            }
        }
    }

    /* renamed from: b */
    public void mo11572b() {
        m307l();
    }

    /* renamed from: c */
    public void mo11573c() {
        mo11574d();
        this.f188d.clear();
        f184b.post(new Runnable() {
            public void run() {
                C1000a.this.f192h.mo11603b();
            }
        });
    }

    /* renamed from: d */
    public void mo11574d() {
        m308m();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public void mo11575e() {
        this.f191g.mo11599c();
        double a = C0990d.m252a();
        C0981a a2 = this.f190f.mo11548a();
        if (this.f191g.mo11597b().size() > 0) {
            this.f192h.mo11604b(a2.mo11545a(null), this.f191g.mo11597b(), a);
        }
        if (this.f191g.mo11595a().size() > 0) {
            JSONObject a3 = a2.mo11545a(null);
            m297a(null, a2, a3, C1015d.PARENT_VIEW);
            C0987b.m238a(a3);
            this.f192h.mo11602a(a3, this.f191g.mo11595a(), a);
        } else {
            this.f192h.mo11603b();
        }
        this.f191g.mo11600d();
    }
}
