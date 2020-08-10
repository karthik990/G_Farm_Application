package com.google.firebase.database.core.utilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DefaultClock implements Clock {
    public long millis() {
        return System.currentTimeMillis();
    }
}
