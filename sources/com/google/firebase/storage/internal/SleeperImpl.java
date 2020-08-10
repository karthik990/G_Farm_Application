package com.google.firebase.storage.internal;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class SleeperImpl implements Sleeper {
    public void sleep(int i) throws InterruptedException {
        Thread.sleep((long) i);
    }
}
