package com.flurry.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.Locale;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.flurry.sdk.ar */
public final class C1554ar extends C1942m<C1553aq> {

    /* renamed from: a */
    protected BroadcastReceiver f544a = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            C1554ar.this.notifyObservers(C1554ar.m461b());
        }
    };

    public C1554ar() {
        String str = "LocaleProvider";
        super(str);
        Context a = C1576b.m502a();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.LOCALE_CHANGED");
        if (a != null) {
            a.registerReceiver(this.f544a, intentFilter);
        } else {
            C1685cy.m754a(6, str, "Context is null when initializing.");
        }
    }

    public final void subscribe(final C1949o<C1553aq> oVar) {
        super.subscribe(oVar);
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                oVar.mo16242a(C1554ar.m461b());
            }
        });
    }

    public final void destroy() {
        super.destroy();
        C1576b.m502a().unregisterReceiver(this.f544a);
    }

    /* renamed from: a */
    public static String m459a() {
        StringBuilder sb = new StringBuilder();
        sb.append(Locale.getDefault().getLanguage());
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(Locale.getDefault().getCountry());
        return sb.toString();
    }

    /* renamed from: b */
    public static C1553aq m461b() {
        return new C1553aq(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
    }
}
