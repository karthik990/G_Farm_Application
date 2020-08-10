package com.p021b.p022a.p023a.p024a.p028c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;

/* renamed from: com.b.a.a.a.c.b */
public class C0973b {

    /* renamed from: a */
    private static C0973b f135a = new C0973b();

    /* renamed from: b */
    private Context f136b;

    /* renamed from: c */
    private BroadcastReceiver f137c;

    /* renamed from: d */
    private boolean f138d;

    /* renamed from: e */
    private boolean f139e;

    /* renamed from: f */
    private C0975a f140f;

    /* renamed from: com.b.a.a.a.c.b$a */
    public interface C0975a {
        /* renamed from: a */
        void mo11524a(boolean z);
    }

    private C0973b() {
    }

    /* renamed from: a */
    public static C0973b m177a() {
        return f135a;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m179a(boolean z) {
        if (this.f139e != z) {
            this.f139e = z;
            if (this.f138d) {
                m182g();
                C0975a aVar = this.f140f;
                if (aVar != null) {
                    aVar.mo11524a(mo11522d());
                }
            }
        }
    }

    /* renamed from: e */
    private void m180e() {
        this.f137c = new BroadcastReceiver() {
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0043, code lost:
                if (r3.inKeyguardRestrictedInputMode() == false) goto L_0x0023;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onReceive(android.content.Context r3, android.content.Intent r4) {
                /*
                    r2 = this;
                    if (r4 != 0) goto L_0x0003
                    return
                L_0x0003:
                    java.lang.String r0 = r4.getAction()
                    java.lang.String r1 = "android.intent.action.SCREEN_OFF"
                    boolean r0 = r1.equals(r0)
                    if (r0 == 0) goto L_0x0016
                    com.b.a.a.a.c.b r3 = com.p021b.p022a.p023a.p024a.p028c.C0973b.this
                    r4 = 1
                    r3.m179a(r4)
                    goto L_0x0046
                L_0x0016:
                    java.lang.String r0 = r4.getAction()
                    java.lang.String r1 = "android.intent.action.USER_PRESENT"
                    boolean r0 = r1.equals(r0)
                    r1 = 0
                    if (r0 == 0) goto L_0x0029
                L_0x0023:
                    com.b.a.a.a.c.b r3 = com.p021b.p022a.p023a.p024a.p028c.C0973b.this
                    r3.m179a(r1)
                    goto L_0x0046
                L_0x0029:
                    java.lang.String r4 = r4.getAction()
                    java.lang.String r0 = "android.intent.action.SCREEN_ON"
                    boolean r4 = r0.equals(r4)
                    if (r4 == 0) goto L_0x0046
                    java.lang.String r4 = "keyguard"
                    java.lang.Object r3 = r3.getSystemService(r4)
                    android.app.KeyguardManager r3 = (android.app.KeyguardManager) r3
                    if (r3 == 0) goto L_0x0046
                    boolean r3 = r3.inKeyguardRestrictedInputMode()
                    if (r3 != 0) goto L_0x0046
                    goto L_0x0023
                L_0x0046:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.p021b.p022a.p023a.p024a.p028c.C0973b.C09741.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.f136b.registerReceiver(this.f137c, intentFilter);
    }

    /* renamed from: f */
    private void m181f() {
        Context context = this.f136b;
        if (context != null) {
            BroadcastReceiver broadcastReceiver = this.f137c;
            if (broadcastReceiver != null) {
                context.unregisterReceiver(broadcastReceiver);
                this.f137c = null;
            }
        }
    }

    /* renamed from: g */
    private void m182g() {
        boolean z = !this.f139e;
        for (C0970i f : C0972a.m170a().mo11513b()) {
            f.mo11498f().mo11560a(z);
        }
    }

    /* renamed from: a */
    public void mo11518a(Context context) {
        this.f136b = context.getApplicationContext();
    }

    /* renamed from: a */
    public void mo11519a(C0975a aVar) {
        this.f140f = aVar;
    }

    /* renamed from: b */
    public void mo11520b() {
        m180e();
        this.f138d = true;
        m182g();
    }

    /* renamed from: c */
    public void mo11521c() {
        m181f();
        this.f138d = false;
        this.f139e = false;
        this.f140f = null;
    }

    /* renamed from: d */
    public boolean mo11522d() {
        return !this.f139e;
    }
}
