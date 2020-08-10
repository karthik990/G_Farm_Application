package com.google.android.gms.tagmanager;

import android.util.Log;

public final class zzba implements zzdj {
    private int zzyr = 5;

    public final void zzav(String str) {
        if (this.zzyr <= 6) {
            Log.e("GoogleTagManager", str);
        }
    }

    public final void zza(String str, Throwable th) {
        if (this.zzyr <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    public final void zzac(String str) {
        if (this.zzyr <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }

    public final void zzb(String str, Throwable th) {
        if (this.zzyr <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }

    public final void zzaw(String str) {
        if (this.zzyr <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    public final void zzax(String str) {
        if (this.zzyr <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    public final void zzab(String str) {
        if (this.zzyr <= 2) {
            Log.v("GoogleTagManager", str);
        }
    }

    public final void setLogLevel(int i) {
        this.zzyr = i;
    }
}
