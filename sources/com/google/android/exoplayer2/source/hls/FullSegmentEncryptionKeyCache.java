package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import com.google.android.exoplayer2.util.Assertions;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

final class FullSegmentEncryptionKeyCache {
    private final LinkedHashMap<Uri, byte[]> backingMap;

    public FullSegmentEncryptionKeyCache(int i) {
        final int i2 = i;
        C24621 r0 = new LinkedHashMap<Uri, byte[]>(i + 1, 1.0f, false) {
            /* access modifiers changed from: protected */
            public boolean removeEldestEntry(Entry<Uri, byte[]> entry) {
                return size() > i2;
            }
        };
        this.backingMap = r0;
    }

    public byte[] get(Uri uri) {
        if (uri == null) {
            return null;
        }
        return (byte[]) this.backingMap.get(uri);
    }

    public byte[] put(Uri uri, byte[] bArr) {
        return (byte[]) this.backingMap.put(Assertions.checkNotNull(uri), Assertions.checkNotNull(bArr));
    }

    public boolean containsUri(Uri uri) {
        return this.backingMap.containsKey(Assertions.checkNotNull(uri));
    }

    public byte[] remove(Uri uri) {
        return (byte[]) this.backingMap.remove(Assertions.checkNotNull(uri));
    }
}
