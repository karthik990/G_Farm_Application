package com.google.firebase.database.logging;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface Logger {

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        NONE
    }

    Level getLogLevel();

    void onLogMessage(Level level, String str, String str2, long j);
}
