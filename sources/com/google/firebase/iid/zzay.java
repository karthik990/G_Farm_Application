package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import java.io.IOException;

final class zzay implements Runnable {
    private final zzan zzan;
    private final zzba zzaq;
    private final long zzdh;
    private final WakeLock zzdi = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private final FirebaseInstanceId zzdj;

    zzay(FirebaseInstanceId firebaseInstanceId, zzan zzan2, zzba zzba, long j) {
        this.zzdj = firebaseInstanceId;
        this.zzan = zzan2;
        this.zzaq = zzba;
        this.zzdh = j;
        this.zzdi.setReferenceCounted(false);
    }

    public final void run() {
        this.zzdi.acquire();
        try {
            this.zzdj.zza(true);
            if (!this.zzdj.zzn()) {
                this.zzdj.zza(false);
            } else if (!zzao()) {
                new zzaz(this).zzap();
                this.zzdi.release();
            } else {
                if (!zzam() || !zzan() || !this.zzaq.zzc(this.zzdj)) {
                    this.zzdj.zza(this.zzdh);
                } else {
                    this.zzdj.zza(false);
                }
                this.zzdi.release();
            }
        } finally {
            this.zzdi.release();
        }
    }

    private final boolean zzam() {
        try {
            if (!this.zzdj.zzo()) {
                this.zzdj.zzp();
            }
            return true;
        } catch (IOException e) {
            String str = "Build channel failed: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.e("FirebaseInstanceId", valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return false;
        }
    }

    private final boolean zzan() {
        String str = "FirebaseInstanceId";
        zzax zzj = this.zzdj.zzj();
        if (zzj != null && !zzj.zzj(this.zzan.zzad())) {
            return true;
        }
        try {
            String zzk = this.zzdj.zzk();
            if (zzk == null) {
                Log.e(str, "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Token successfully retrieved");
            }
            if (zzj == null || (zzj != null && !zzk.equals(zzj.zzbq))) {
                Context context = getContext();
                Intent intent = new Intent("com.google.firebase.messaging.NEW_TOKEN");
                intent.putExtra("token", zzk);
                zzav.zzc(context, intent);
                zzav.zzb(context, new Intent("com.google.firebase.iid.TOKEN_REFRESH"));
            }
            return true;
        } catch (IOException | SecurityException e) {
            String str2 = "Token retrieval failed: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final Context getContext() {
        return this.zzdj.zzh().getApplicationContext();
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzao() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
