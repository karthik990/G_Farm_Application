package com.twitter.sdk.android.core;

import android.util.Log;

public class DefaultLogger implements Logger {
    private int logLevel;

    public DefaultLogger(int i) {
        this.logLevel = i;
    }

    public DefaultLogger() {
        this.logLevel = 4;
    }

    public boolean isLoggable(String str, int i) {
        return this.logLevel <= i;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int i) {
        this.logLevel = i;
    }

    /* renamed from: d */
    public void mo20818d(String str, String str2, Throwable th) {
        if (isLoggable(str, 3)) {
            Log.d(str, str2, th);
        }
    }

    /* renamed from: v */
    public void mo20829v(String str, String str2, Throwable th) {
        if (isLoggable(str, 2)) {
            Log.v(str, str2, th);
        }
    }

    /* renamed from: i */
    public void mo20823i(String str, String str2, Throwable th) {
        if (isLoggable(str, 4)) {
            Log.i(str, str2, th);
        }
    }

    /* renamed from: w */
    public void mo20831w(String str, String str2, Throwable th) {
        if (isLoggable(str, 5)) {
            Log.w(str, str2, th);
        }
    }

    /* renamed from: e */
    public void mo20820e(String str, String str2, Throwable th) {
        if (isLoggable(str, 6)) {
            Log.e(str, str2, th);
        }
    }

    /* renamed from: d */
    public void mo20817d(String str, String str2) {
        mo20818d(str, str2, null);
    }

    /* renamed from: v */
    public void mo20828v(String str, String str2) {
        mo20829v(str, str2, null);
    }

    /* renamed from: i */
    public void mo20822i(String str, String str2) {
        mo20823i(str, str2, null);
    }

    /* renamed from: w */
    public void mo20830w(String str, String str2) {
        mo20831w(str, str2, null);
    }

    /* renamed from: e */
    public void mo20819e(String str, String str2) {
        mo20820e(str, str2, null);
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
