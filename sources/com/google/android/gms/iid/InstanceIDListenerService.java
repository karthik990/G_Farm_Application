package com.google.android.gms.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.api.client.googleapis.notifications.ResourceStates;

@Deprecated
public class InstanceIDListenerService extends zze {
    static void zzd(Context context, zzak zzak) {
        zzak.zzx();
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.putExtra("CMD", "RST");
        intent.setClassName(context, "com.google.android.gms.gcm.GcmReceiver");
        context.sendBroadcast(intent);
    }

    public void handleIntent(Intent intent) {
        if ("com.google.android.gms.iid.InstanceID".equals(intent.getAction())) {
            Bundle bundle = null;
            String str = "subtype";
            String stringExtra = intent.getStringExtra(str);
            if (stringExtra != null) {
                bundle = new Bundle();
                bundle.putString(str, stringExtra);
            }
            InstanceID instance = InstanceID.getInstance(this, bundle);
            String stringExtra2 = intent.getStringExtra("CMD");
            String str2 = "InstanceID";
            if (Log.isLoggable(str2, 3)) {
                StringBuilder sb = new StringBuilder(String.valueOf(stringExtra).length() + 34 + String.valueOf(stringExtra2).length());
                sb.append("Service command. subtype:");
                sb.append(stringExtra);
                sb.append(" command:");
                sb.append(stringExtra2);
                Log.d(str2, sb.toString());
            }
            if ("RST".equals(stringExtra2)) {
                instance.zzm();
            } else {
                if ("RST_FULL".equals(stringExtra2)) {
                    if (!InstanceID.zzn().isEmpty()) {
                        InstanceID.zzn().zzx();
                    }
                } else if (ResourceStates.SYNC.equals(stringExtra2)) {
                    InstanceID.zzn().zzi(String.valueOf(stringExtra).concat("|T|"));
                    onTokenRefresh();
                }
                return;
            }
            onTokenRefresh();
        }
    }

    public void onTokenRefresh() {
    }
}
