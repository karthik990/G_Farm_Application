package com.google.firebase.remoteconfig;

public class FirebaseRemoteConfigFetchThrottledException extends FirebaseRemoteConfigFetchException {
    private final long zzjm;

    public FirebaseRemoteConfigFetchThrottledException(long j) {
        this("Fetch was throttled.", j);
    }

    public FirebaseRemoteConfigFetchThrottledException(String str, long j) {
        super(str);
        this.zzjm = j;
    }

    public long getThrottleEndTimeMillis() {
        return this.zzjm;
    }
}
