package com.p021b.p022a.p023a.p024a.p032g;

import android.os.Handler;
import android.webkit.WebView;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.p021b.p022a.p023a.p024a.p026b.C0969h;
import com.p021b.p022a.p023a.p024a.p028c.C0976c;
import com.p021b.p022a.p023a.p024a.p028c.C0977d;
import java.util.List;

/* renamed from: com.b.a.a.a.g.c */
public class C0998c extends C0995a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public WebView f178a;

    /* renamed from: b */
    private List<C0969h> f179b;

    /* renamed from: c */
    private final String f180c;

    public C0998c(List<C0969h> list, String str) {
        this.f179b = list;
        this.f180c = str;
    }

    /* renamed from: a */
    public void mo11550a() {
        super.mo11550a();
        mo11570j();
    }

    /* renamed from: b */
    public void mo11561b() {
        super.mo11561b();
        new Handler().postDelayed(new Runnable() {

            /* renamed from: b */
            private WebView f182b = C0998c.this.f178a;

            public void run() {
                this.f182b.destroy();
            }
        }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        this.f178a = null;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: j */
    public void mo11570j() {
        this.f178a = new WebView(C0976c.m189a().mo11526b());
        this.f178a.getSettings().setJavaScriptEnabled(true);
        mo11552a(this.f178a);
        C0977d.m192a().mo11535a(this.f178a, this.f180c);
        for (C0969h b : this.f179b) {
            C0977d.m192a().mo11537b(this.f178a, b.mo11493b().toExternalForm());
        }
    }
}
