package com.p021b.p022a.p023a.p024a.p026b.p027a;

import com.p021b.p022a.p023a.p024a.p026b.C0963b;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import com.p021b.p022a.p023a.p024a.p028c.C0979e;
import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import com.p021b.p022a.p023a.p024a.p030e.C0991e;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.b.a.a */
public final class C0962a {

    /* renamed from: a */
    private final C0970i f100a;

    private C0962a(C0970i iVar) {
        this.f100a = iVar;
    }

    /* renamed from: a */
    public static C0962a m105a(C0963b bVar) {
        C0970i iVar = (C0970i) bVar;
        C0991e.m256a((Object) bVar, "AdSession is null");
        C0991e.m264g(iVar);
        C0991e.m255a(iVar);
        C0991e.m259b(iVar);
        C0991e.m262e(iVar);
        C0962a aVar = new C0962a(iVar);
        iVar.mo11498f().mo11553a(aVar);
        return aVar;
    }

    /* renamed from: b */
    private void m106b(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Invalid Video duration");
        }
    }

    /* renamed from: c */
    private void m107c(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Invalid Video volume");
        }
    }

    /* renamed from: a */
    public void mo11465a() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("firstQuartile");
    }

    /* renamed from: a */
    public void mo11466a(float f) {
        m107c(f);
        C0991e.m260c(this.f100a);
        JSONObject jSONObject = new JSONObject();
        C0987b.m240a(jSONObject, "videoPlayerVolume", Float.valueOf(f));
        C0987b.m240a(jSONObject, "deviceVolume", Float.valueOf(C0979e.m206a().mo11544d()));
        this.f100a.mo11498f().mo11559a("volumeChange", jSONObject);
    }

    /* renamed from: a */
    public void mo11467a(float f, float f2) {
        m106b(f);
        m107c(f2);
        C0991e.m260c(this.f100a);
        JSONObject jSONObject = new JSONObject();
        C0987b.m240a(jSONObject, "duration", Float.valueOf(f));
        C0987b.m240a(jSONObject, "videoPlayerVolume", Float.valueOf(f2));
        C0987b.m240a(jSONObject, "deviceVolume", Float.valueOf(C0979e.m206a().mo11544d()));
        this.f100a.mo11498f().mo11559a(TtmlNode.START, jSONObject);
    }

    /* renamed from: b */
    public void mo11468b() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("midpoint");
    }

    /* renamed from: c */
    public void mo11469c() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("thirdQuartile");
    }

    /* renamed from: d */
    public void mo11470d() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("complete");
    }

    /* renamed from: e */
    public void mo11471e() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("pause");
    }

    /* renamed from: f */
    public void mo11472f() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("bufferStart");
    }

    /* renamed from: g */
    public void mo11473g() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("bufferFinish");
    }

    /* renamed from: h */
    public void mo11474h() {
        C0991e.m260c(this.f100a);
        this.f100a.mo11498f().mo11557a("skipped");
    }
}
