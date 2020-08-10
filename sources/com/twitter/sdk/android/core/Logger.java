package com.twitter.sdk.android.core;

public interface Logger {
    /* renamed from: d */
    void mo20817d(String str, String str2);

    /* renamed from: d */
    void mo20818d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo20819e(String str, String str2);

    /* renamed from: e */
    void mo20820e(String str, String str2, Throwable th);

    int getLogLevel();

    /* renamed from: i */
    void mo20822i(String str, String str2);

    /* renamed from: i */
    void mo20823i(String str, String str2, Throwable th);

    boolean isLoggable(String str, int i);

    void log(int i, String str, String str2);

    void log(int i, String str, String str2, boolean z);

    void setLogLevel(int i);

    /* renamed from: v */
    void mo20828v(String str, String str2);

    /* renamed from: v */
    void mo20829v(String str, String str2, Throwable th);

    /* renamed from: w */
    void mo20830w(String str, String str2);

    /* renamed from: w */
    void mo20831w(String str, String str2, Throwable th);
}
