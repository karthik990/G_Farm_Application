package com.google.android.exoplayer2.upstream.cache;

import java.util.Comparator;

/* renamed from: com.google.android.exoplayer2.upstream.cache.-$$Lambda$LeastRecentlyUsedCacheEvictor$Tg2zljd4_hGIfz6LdtUQwAPogBo */
/* compiled from: lambda */
public final /* synthetic */ class C2498xdc8b2445 implements Comparator {
    public static final /* synthetic */ C2498xdc8b2445 INSTANCE = new C2498xdc8b2445();

    private /* synthetic */ C2498xdc8b2445() {
    }

    public final int compare(Object obj, Object obj2) {
        return LeastRecentlyUsedCacheEvictor.compare((CacheSpan) obj, (CacheSpan) obj2);
    }
}
