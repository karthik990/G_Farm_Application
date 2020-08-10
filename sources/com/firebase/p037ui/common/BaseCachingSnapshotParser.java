package com.firebase.p037ui.common;

import android.util.LruCache;

/* renamed from: com.firebase.ui.common.BaseCachingSnapshotParser */
public abstract class BaseCachingSnapshotParser<S, T> implements BaseSnapshotParser<S, T> {
    private static final int MAX_CACHE_SIZE = 100;
    private final LruCache<String, T> mObjectCache = new LruCache<>(100);
    private final BaseSnapshotParser<S, T> mParser;

    public abstract String getId(S s);

    public BaseCachingSnapshotParser(BaseSnapshotParser<S, T> baseSnapshotParser) {
        this.mParser = baseSnapshotParser;
    }

    public T parseSnapshot(S s) {
        String id = getId(s);
        T t = this.mObjectCache.get(id);
        if (t != null) {
            return t;
        }
        T parseSnapshot = this.mParser.parseSnapshot(s);
        this.mObjectCache.put(id, parseSnapshot);
        return parseSnapshot;
    }

    public void clear() {
        this.mObjectCache.evictAll();
    }

    public void invalidate(S s) {
        this.mObjectCache.remove(getId(s));
    }
}
