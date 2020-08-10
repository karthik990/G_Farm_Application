package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.common.util.PlatformVersion;

public final class FirebaseInstanceIdReceiver extends WakefulBroadcastReceiver {
    private static boolean zzbf = false;
    private static zzh zzbg;
    private static zzh zzbh;

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Parcelable parcelableExtra = intent.getParcelableExtra("wrapped_intent");
            Intent intent2 = parcelableExtra instanceof Intent ? (Intent) parcelableExtra : null;
            if (intent2 != null) {
                zza(context, intent2, intent.getAction());
            } else {
                zza(context, intent, intent.getAction());
            }
        }
    }

    private final void zza(Context context, Intent intent, String str) {
        String str2 = null;
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        String str3 = "gcm.rawData64";
        String stringExtra = intent.getStringExtra(str3);
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra(str3);
        }
        String str4 = "com.google.firebase.MESSAGING_EVENT";
        String str5 = "com.google.firebase.INSTANCE_ID_EVENT";
        if ("google.com/iid".equals(intent.getStringExtra("from")) || str5.equals(str)) {
            str2 = str5;
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(str) || str4.equals(str)) {
            str2 = str4;
        } else {
            Log.d("FirebaseInstanceId", "Unexpected intent");
        }
        int i = -1;
        if (str2 != null) {
            i = zza(this, context, str2, intent);
        }
        if (isOrderedBroadcast()) {
            setResultCode(i);
        }
    }

    public static int zza(BroadcastReceiver broadcastReceiver, Context context, String str, Intent intent) {
        boolean z = true;
        boolean z2 = PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26;
        if ((intent.getFlags() & 268435456) == 0) {
            z = false;
        }
        if (z2 && !z) {
            return zzb(broadcastReceiver, context, str, intent);
        }
        int zzb = zzav.zzai().zzb(context, str, intent);
        if (!PlatformVersion.isAtLeastO() || zzb != 402) {
            return zzb;
        }
        zzb(broadcastReceiver, context, str, intent);
        return 403;
    }

    private static int zzb(BroadcastReceiver broadcastReceiver, Context context, String str, Intent intent) {
        String str2 = "FirebaseInstanceId";
        if (Log.isLoggable(str2, 3)) {
            String str3 = "Binding to service: ";
            String valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        if (broadcastReceiver.isOrderedBroadcast()) {
            broadcastReceiver.setResultCode(-1);
        }
        zza(context, str).zza(intent, broadcastReceiver.goAsync());
        return -1;
    }

    private static synchronized zzh zza(Context context, String str) {
        synchronized (FirebaseInstanceIdReceiver.class) {
            if ("com.google.firebase.MESSAGING_EVENT".equals(str)) {
                if (zzbh == null) {
                    zzbh = new zzh(context, str);
                }
                zzh zzh = zzbh;
                return zzh;
            }
            if (zzbg == null) {
                zzbg = new zzh(context, str);
            }
            zzh zzh2 = zzbg;
            return zzh2;
        }
    }
}
