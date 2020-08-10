package com.google.android.gms.internal.gtm;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

final class zzbr implements Logger {
    private boolean zzrv;
    private int zzyr = 2;

    zzbr() {
    }

    public final void error(Exception exc) {
    }

    public final void error(String str) {
    }

    public final void info(String str) {
    }

    public final void verbose(String str) {
    }

    public final void warn(String str) {
    }

    public final void setLogLevel(int i) {
        this.zzyr = i;
        if (!this.zzrv) {
            String str = (String) zzby.zzzb.get();
            String str2 = (String) zzby.zzzb.get();
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 91);
            sb.append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            sb.append(str2);
            sb.append(" DEBUG");
            Log.i(str, sb.toString());
            this.zzrv = true;
        }
    }

    public final int getLogLevel() {
        return this.zzyr;
    }
}
