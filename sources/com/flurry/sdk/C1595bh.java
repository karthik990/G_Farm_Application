package com.flurry.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.TimeZone;

/* renamed from: com.flurry.sdk.bh */
public final class C1595bh extends C1942m<String> {

    /* renamed from: a */
    protected BroadcastReceiver f659a = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            C1595bh.this.notifyObservers(TimeZone.getDefault().getID());
        }
    };

    public C1595bh() {
        String str = "TimeZoneProvider";
        super(str);
        Context a = C1576b.m502a();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.TIMEZONE_CHANGED");
        if (a != null) {
            a.registerReceiver(this.f659a, intentFilter);
        } else {
            C1685cy.m754a(6, str, "Context is null when initializing.");
        }
    }

    public final void subscribe(final C1949o<String> oVar) {
        super.subscribe(oVar);
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                oVar.mo16242a(TimeZone.getDefault().getID());
            }
        });
    }

    public final void destroy() {
        super.destroy();
        C1576b.m502a().unregisterReceiver(this.f659a);
    }
}
