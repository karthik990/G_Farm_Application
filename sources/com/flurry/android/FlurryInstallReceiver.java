package com.flurry.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.flurry.sdk.C1635bu;
import com.flurry.sdk.C1685cy;
import com.flurry.sdk.C1734dz;

public final class FlurryInstallReceiver extends BroadcastReceiver {
    public final void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder("Received an Install notification of ");
        sb.append(intent.getAction());
        String str = "FlurryInstallReceiver";
        C1685cy.m754a(4, str, sb.toString());
        String string = intent.getExtras().getString("referrer");
        C1685cy.m754a(4, str, "Received an Install referrer of ".concat(String.valueOf(string)));
        if (string != null) {
            if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
                if (!string.contains("=")) {
                    C1685cy.m754a(4, str, "referrer is before decoding: ".concat(String.valueOf(string)));
                    string = C1734dz.m876d(string);
                    C1685cy.m754a(4, str, "referrer is: ".concat(String.valueOf(string)));
                }
                new C1635bu(context).mo16319a(string);
                return;
            }
        }
        C1685cy.m754a(5, str, "referrer is null");
    }
}
