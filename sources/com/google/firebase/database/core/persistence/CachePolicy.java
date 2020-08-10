package com.google.firebase.database.core.persistence;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface CachePolicy {
    public static final CachePolicy NONE = new CachePolicy() {
        public long getMaxNumberOfQueriesToKeep() {
            return Long.MAX_VALUE;
        }

        public float getPercentOfQueriesToPruneAtOnce() {
            return 0.0f;
        }

        public boolean shouldCheckCacheSize(long j) {
            return false;
        }

        public boolean shouldPrune(long j, long j2) {
            return false;
        }
    };

    long getMaxNumberOfQueriesToKeep();

    float getPercentOfQueriesToPruneAtOnce();

    boolean shouldCheckCacheSize(long j);

    boolean shouldPrune(long j, long j2);
}
