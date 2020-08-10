package com.google.firebase.database.core.utilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class OffsetClock implements Clock {
    private final Clock baseClock;
    private long offset = 0;

    public OffsetClock(Clock clock, long j) {
        this.baseClock = clock;
        this.offset = j;
    }

    public void setOffset(long j) {
        this.offset = j;
    }

    public long millis() {
        return this.baseClock.millis() + this.offset;
    }
}
