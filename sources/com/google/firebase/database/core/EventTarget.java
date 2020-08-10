package com.google.firebase.database.core;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface EventTarget {
    void postEvent(Runnable runnable);

    void restart();

    void shutdown();
}
