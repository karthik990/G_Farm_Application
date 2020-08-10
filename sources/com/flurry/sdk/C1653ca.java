package com.flurry.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.mobiroller.constants.Constants;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: com.flurry.sdk.ca */
public final class C1653ca {

    /* renamed from: a */
    public final SharedPreferences f790a;

    /* renamed from: b */
    int f791b = 0;

    /* renamed from: c */
    long f792c;

    /* renamed from: d */
    private Timer f793d;

    /* renamed from: e */
    private final Object f794e = new Object();

    public C1653ca() {
        Context a = C1576b.m502a();
        this.f790a = a.getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0);
        C1601bl.m537a();
        this.f791b = C1601bl.m539b(a);
        SharedPreferences sharedPreferences = this.f790a;
        long j = Constants.REGISTRATION_EXPIRY_TIME_MS;
        if (sharedPreferences != null) {
            j = sharedPreferences.getLong("refreshFetch", Constants.REGISTRATION_EXPIRY_TIME_MS);
        }
        this.f792c = j;
    }

    /* renamed from: a */
    public final int mo16342a() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("appVersion", 0);
        }
        return 0;
    }

    /* renamed from: e */
    private void m656e() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("appVersion").apply();
        }
    }

    /* renamed from: a */
    public final synchronized void mo16344a(TimerTask timerTask, long j) {
        synchronized (this.f794e) {
            String str = "ConfigMeta";
            StringBuilder sb = new StringBuilder("Record retry after ");
            sb.append(j);
            sb.append(" msecs.");
            C1685cy.m756a(str, sb.toString());
            this.f793d = new Timer("retry-scheduler");
            this.f793d.schedule(timerTask, j);
        }
    }

    /* renamed from: b */
    public final void mo16345b() {
        synchronized (this.f794e) {
            if (this.f793d != null) {
                C1685cy.m754a(3, "ConfigMeta", "Clear retry.");
                this.f793d.cancel();
                this.f793d.purge();
                this.f793d = null;
            }
        }
    }

    /* renamed from: c */
    public final void mo16346c() {
        C1685cy.m756a("ConfigMeta", "Clear all ConfigMeta data.");
        mo16345b();
        m656e();
        m657f();
        m658g();
        m659h();
        m660i();
        m661j();
    }

    /* renamed from: f */
    private void m657f() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("lastFetch").apply();
        }
    }

    /* renamed from: a */
    public final void mo16343a(long j) {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().putLong("lastFetch", j).apply();
        }
    }

    /* renamed from: g */
    private void m658g() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("lastETag").apply();
        }
    }

    /* renamed from: d */
    public final String mo16347d() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            return sharedPreferences.getString("lastKeyId", null);
        }
        return null;
    }

    /* renamed from: h */
    private void m659h() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("lastKeyId").apply();
        }
    }

    /* renamed from: i */
    private void m660i() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("lastRSA").apply();
        }
    }

    /* renamed from: j */
    private void m661j() {
        SharedPreferences sharedPreferences = this.f790a;
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("com.flurry.sdk.variant_ids").apply();
        }
    }
}
