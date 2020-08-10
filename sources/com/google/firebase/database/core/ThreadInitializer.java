package com.google.firebase.database.core;

import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface ThreadInitializer {
    public static final ThreadInitializer defaultInstance = new ThreadInitializer() {
        public void setName(Thread thread, String str) {
            thread.setName(str);
        }

        public void setDaemon(Thread thread, boolean z) {
            thread.setDaemon(z);
        }

        public void setUncaughtExceptionHandler(Thread thread, UncaughtExceptionHandler uncaughtExceptionHandler) {
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    };

    void setDaemon(Thread thread, boolean z);

    void setName(Thread thread, String str);

    void setUncaughtExceptionHandler(Thread thread, UncaughtExceptionHandler uncaughtExceptionHandler);
}
