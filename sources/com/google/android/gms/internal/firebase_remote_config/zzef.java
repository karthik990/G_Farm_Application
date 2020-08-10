package com.google.android.gms.internal.firebase_remote_config;

import java.util.List;

final class zzef extends zzec {
    private final zzed zziz = new zzed();

    zzef() {
    }

    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 != null) {
            this.zziz.zza(th, true).add(th2);
        } else {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
    }

    public final void zzb(Throwable th) {
        th.printStackTrace();
        List<Throwable> zza = this.zziz.zza(th, false);
        if (zza != null) {
            synchronized (zza) {
                for (Throwable th2 : zza) {
                    System.err.print("Suppressed: ");
                    th2.printStackTrace();
                }
            }
        }
    }
}
