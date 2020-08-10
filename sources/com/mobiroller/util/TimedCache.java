package com.mobiroller.util;

import android.util.LruCache;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimedCache<K, V> {
    private long mExpiryTimeInMillis;
    private LruCache mLruCache;
    private Map<K, Long> mTimeMap = new ConcurrentHashMap();

    TimedCache(int i, long j) {
        this.mLruCache = new LruCache(i);
        this.mExpiryTimeInMillis = j;
    }

    private boolean isValidKey(K k) {
        return k != null && this.mTimeMap.containsKey(k);
    }

    public synchronized V get(K k) {
        return getIfNotExpired(k, System.currentTimeMillis() - this.mExpiryTimeInMillis);
    }

    /* access modifiers changed from: 0000 */
    public synchronized V getIfNotExpired(K k, long j) {
        if (!isValidKey(k)) {
            return null;
        }
        if (((Long) this.mTimeMap.get(k)).longValue() >= j) {
            return this.mLruCache.get(k);
        }
        remove(k);
        return null;
    }

    public synchronized void put(K k, V v) {
        if (!(k == null || v == null)) {
            this.mLruCache.put(k, v);
            this.mTimeMap.put(k, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public synchronized void remove(K k) {
        if (k != null) {
            this.mLruCache.remove(k);
            this.mTimeMap.remove(k);
        }
    }

    public synchronized void clear() {
        this.mLruCache.evictAll();
        this.mTimeMap.clear();
    }
}
