package com.google.android.exoplayer2.offline;

import java.util.Comparator;

/* renamed from: com.google.android.exoplayer2.offline.-$$Lambda$DownloadManager$InternalHandler$NXQcmC9peGeDWV0s_8pBfzdJpS0 */
/* compiled from: lambda */
public final /* synthetic */ class C2065x7c64c816 implements Comparator {
    public static final /* synthetic */ C2065x7c64c816 INSTANCE = new C2065x7c64c816();

    private /* synthetic */ C2065x7c64c816() {
    }

    public final int compare(Object obj, Object obj2) {
        return InternalHandler.compareStartTimes((Download) obj, (Download) obj2);
    }
}
