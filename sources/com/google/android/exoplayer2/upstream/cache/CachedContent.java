package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.io.File;
import java.util.TreeSet;

final class CachedContent {
    private static final String TAG = "CachedContent";
    private final TreeSet<SimpleCacheSpan> cachedSpans;

    /* renamed from: id */
    public final int f1529id;
    public final String key;
    private boolean locked;
    private DefaultContentMetadata metadata;

    public CachedContent(int i, String str) {
        this(i, str, DefaultContentMetadata.EMPTY);
    }

    public CachedContent(int i, String str, DefaultContentMetadata defaultContentMetadata) {
        this.f1529id = i;
        this.key = str;
        this.metadata = defaultContentMetadata;
        this.cachedSpans = new TreeSet<>();
    }

    public DefaultContentMetadata getMetadata() {
        return this.metadata;
    }

    public boolean applyMetadataMutations(ContentMetadataMutations contentMetadataMutations) {
        DefaultContentMetadata defaultContentMetadata = this.metadata;
        this.metadata = defaultContentMetadata.copyWithMutationsApplied(contentMetadataMutations);
        return !this.metadata.equals(defaultContentMetadata);
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean z) {
        this.locked = z;
    }

    public void addSpan(SimpleCacheSpan simpleCacheSpan) {
        this.cachedSpans.add(simpleCacheSpan);
    }

    public TreeSet<SimpleCacheSpan> getSpans() {
        return this.cachedSpans;
    }

    public SimpleCacheSpan getSpan(long j) {
        SimpleCacheSpan simpleCacheSpan;
        SimpleCacheSpan createLookup = SimpleCacheSpan.createLookup(this.key, j);
        SimpleCacheSpan simpleCacheSpan2 = (SimpleCacheSpan) this.cachedSpans.floor(createLookup);
        if (simpleCacheSpan2 != null && simpleCacheSpan2.position + simpleCacheSpan2.length > j) {
            return simpleCacheSpan2;
        }
        SimpleCacheSpan simpleCacheSpan3 = (SimpleCacheSpan) this.cachedSpans.ceiling(createLookup);
        if (simpleCacheSpan3 == null) {
            simpleCacheSpan = SimpleCacheSpan.createOpenHole(this.key, j);
        } else {
            simpleCacheSpan = SimpleCacheSpan.createClosedHole(this.key, j, simpleCacheSpan3.position - j);
        }
        return simpleCacheSpan;
    }

    public long getCachedBytesLength(long j, long j2) {
        SimpleCacheSpan span = getSpan(j);
        if (span.isHoleSpan()) {
            return -Math.min(span.isOpenEnded() ? Long.MAX_VALUE : span.length, j2);
        }
        long j3 = j + j2;
        long j4 = span.position + span.length;
        if (j4 < j3) {
            for (SimpleCacheSpan simpleCacheSpan : this.cachedSpans.tailSet(span, false)) {
                if (simpleCacheSpan.position <= j4) {
                    j4 = Math.max(j4, simpleCacheSpan.position + simpleCacheSpan.length);
                    if (j4 >= j3) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return Math.min(j4 - j, j2);
    }

    public SimpleCacheSpan setLastTouchTimestamp(SimpleCacheSpan simpleCacheSpan, long j, boolean z) {
        File file;
        Assertions.checkState(this.cachedSpans.remove(simpleCacheSpan));
        File file2 = simpleCacheSpan.file;
        if (z) {
            file = SimpleCacheSpan.getCacheFile(file2.getParentFile(), this.f1529id, simpleCacheSpan.position, j);
            if (!file2.renameTo(file)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to rename ");
                sb.append(file2);
                sb.append(" to ");
                sb.append(file);
                Log.m1396w(TAG, sb.toString());
            }
            SimpleCacheSpan copyWithFileAndLastTouchTimestamp = simpleCacheSpan.copyWithFileAndLastTouchTimestamp(file, j);
            this.cachedSpans.add(copyWithFileAndLastTouchTimestamp);
            return copyWithFileAndLastTouchTimestamp;
        }
        file = file2;
        SimpleCacheSpan copyWithFileAndLastTouchTimestamp2 = simpleCacheSpan.copyWithFileAndLastTouchTimestamp(file, j);
        this.cachedSpans.add(copyWithFileAndLastTouchTimestamp2);
        return copyWithFileAndLastTouchTimestamp2;
    }

    public boolean isEmpty() {
        return this.cachedSpans.isEmpty();
    }

    public boolean removeSpan(CacheSpan cacheSpan) {
        if (!this.cachedSpans.remove(cacheSpan)) {
            return false;
        }
        cacheSpan.file.delete();
        return true;
    }

    public int hashCode() {
        return (((this.f1529id * 31) + this.key.hashCode()) * 31) + this.metadata.hashCode();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CachedContent cachedContent = (CachedContent) obj;
        if (this.f1529id != cachedContent.f1529id || !this.key.equals(cachedContent.key) || !this.cachedSpans.equals(cachedContent.cachedSpans) || !this.metadata.equals(cachedContent.metadata)) {
            z = false;
        }
        return z;
    }
}
