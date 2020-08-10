package p043io.fabric.sdk.android;

import android.util.Log;

/* renamed from: io.fabric.sdk.android.DefaultLogger */
public class DefaultLogger implements Logger {
    private int logLevel;

    public DefaultLogger(int i) {
        this.logLevel = i;
    }

    public DefaultLogger() {
        this.logLevel = 4;
    }

    public boolean isLoggable(String str, int i) {
        return this.logLevel <= i || Log.isLoggable(str, i);
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int i) {
        this.logLevel = i;
    }

    /* renamed from: d */
    public void mo64075d(String str, String str2, Throwable th) {
        if (isLoggable(str, 3)) {
            Log.d(str, str2, th);
        }
    }

    /* renamed from: v */
    public void mo64086v(String str, String str2, Throwable th) {
        if (isLoggable(str, 2)) {
            Log.v(str, str2, th);
        }
    }

    /* renamed from: i */
    public void mo64080i(String str, String str2, Throwable th) {
        if (isLoggable(str, 4)) {
            Log.i(str, str2, th);
        }
    }

    /* renamed from: w */
    public void mo64088w(String str, String str2, Throwable th) {
        if (isLoggable(str, 5)) {
            Log.w(str, str2, th);
        }
    }

    /* renamed from: e */
    public void mo64077e(String str, String str2, Throwable th) {
        if (isLoggable(str, 6)) {
            Log.e(str, str2, th);
        }
    }

    /* renamed from: d */
    public void mo64074d(String str, String str2) {
        mo64075d(str, str2, null);
    }

    /* renamed from: v */
    public void mo64085v(String str, String str2) {
        mo64086v(str, str2, null);
    }

    /* renamed from: i */
    public void mo64079i(String str, String str2) {
        mo64080i(str, str2, null);
    }

    /* renamed from: w */
    public void mo64087w(String str, String str2) {
        mo64088w(str, str2, null);
    }

    /* renamed from: e */
    public void mo64076e(String str, String str2) {
        mo64077e(str, str2, null);
    }

    public void log(int i, String str, String str2) {
        log(i, str, str2, false);
    }

    public void log(int i, String str, String str2, boolean z) {
        if (z || isLoggable(str, i)) {
            Log.println(i, str, str2);
        }
    }
}
