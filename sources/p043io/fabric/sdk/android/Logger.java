package p043io.fabric.sdk.android;

/* renamed from: io.fabric.sdk.android.Logger */
public interface Logger {
    /* renamed from: d */
    void mo64074d(String str, String str2);

    /* renamed from: d */
    void mo64075d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo64076e(String str, String str2);

    /* renamed from: e */
    void mo64077e(String str, String str2, Throwable th);

    int getLogLevel();

    /* renamed from: i */
    void mo64079i(String str, String str2);

    /* renamed from: i */
    void mo64080i(String str, String str2, Throwable th);

    boolean isLoggable(String str, int i);

    void log(int i, String str, String str2);

    void log(int i, String str, String str2, boolean z);

    void setLogLevel(int i);

    /* renamed from: v */
    void mo64085v(String str, String str2);

    /* renamed from: v */
    void mo64086v(String str, String str2, Throwable th);

    /* renamed from: w */
    void mo64087w(String str, String str2);

    /* renamed from: w */
    void mo64088w(String str, String str2, Throwable th);
}
