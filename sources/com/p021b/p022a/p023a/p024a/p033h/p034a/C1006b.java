package com.p021b.p022a.p023a.p024a.p033h.p034a;

import android.os.AsyncTask;
import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.h.a.b */
public abstract class C1006b extends AsyncTask<Object, Void, String> {

    /* renamed from: a */
    private C1007a f198a;

    /* renamed from: d */
    protected final C1008b f199d;

    /* renamed from: com.b.a.a.a.h.a.b$a */
    public interface C1007a {
        /* renamed from: a */
        void mo11584a(C1006b bVar);
    }

    /* renamed from: com.b.a.a.a.h.a.b$b */
    public interface C1008b {
        /* renamed from: a */
        JSONObject mo11585a();

        /* renamed from: a */
        void mo11586a(JSONObject jSONObject);
    }

    public C1006b(C1008b bVar) {
        this.f199d = bVar;
    }

    /* renamed from: a */
    public void mo11580a(C1007a aVar) {
        this.f198a = aVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        C1007a aVar = this.f198a;
        if (aVar != null) {
            aVar.mo11584a(this);
        }
    }

    /* renamed from: a */
    public void mo11582a(ThreadPoolExecutor threadPoolExecutor) {
        executeOnExecutor(threadPoolExecutor, new Object[0]);
    }
}
