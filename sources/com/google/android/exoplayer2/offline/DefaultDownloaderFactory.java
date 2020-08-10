package com.google.android.exoplayer2.offline;

import android.net.Uri;
import java.lang.reflect.Constructor;
import java.util.List;

public class DefaultDownloaderFactory implements DownloaderFactory {
    private static final Constructor<? extends Downloader> DASH_DOWNLOADER_CONSTRUCTOR;
    private static final Constructor<? extends Downloader> HLS_DOWNLOADER_CONSTRUCTOR;
    private static final Constructor<? extends Downloader> SS_DOWNLOADER_CONSTRUCTOR;
    private final DownloaderConstructorHelper downloaderConstructorHelper;

    static {
        Constructor<? extends Downloader> constructor;
        Constructor<? extends Downloader> constructor2;
        Constructor<? extends Downloader> constructor3 = null;
        try {
            constructor = getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.dash.offline.DashDownloader"));
        } catch (ClassNotFoundException unused) {
            constructor = null;
        }
        DASH_DOWNLOADER_CONSTRUCTOR = constructor;
        try {
            constructor2 = getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.hls.offline.HlsDownloader"));
        } catch (ClassNotFoundException unused2) {
            constructor2 = null;
        }
        HLS_DOWNLOADER_CONSTRUCTOR = constructor2;
        try {
            constructor3 = getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloader"));
        } catch (ClassNotFoundException unused3) {
        }
        SS_DOWNLOADER_CONSTRUCTOR = constructor3;
    }

    public DefaultDownloaderFactory(DownloaderConstructorHelper downloaderConstructorHelper2) {
        this.downloaderConstructorHelper = downloaderConstructorHelper2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.offline.Downloader createDownloader(com.google.android.exoplayer2.offline.DownloadRequest r7) {
        /*
            r6 = this;
            java.lang.String r0 = r7.type
            int r1 = r0.hashCode()
            r2 = 3680(0xe60, float:5.157E-42)
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == r2) goto L_0x003b
            r2 = 103407(0x193ef, float:1.44904E-40)
            if (r1 == r2) goto L_0x0031
            r2 = 3075986(0x2eef92, float:4.310374E-39)
            if (r1 == r2) goto L_0x0027
            r2 = 1131547531(0x43720b8b, float:242.04509)
            if (r1 == r2) goto L_0x001d
            goto L_0x0046
        L_0x001d:
            java.lang.String r1 = "progressive"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0046
            r0 = 0
            goto L_0x0047
        L_0x0027:
            java.lang.String r1 = "dash"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0046
            r0 = 1
            goto L_0x0047
        L_0x0031:
            java.lang.String r1 = "hls"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0046
            r0 = 2
            goto L_0x0047
        L_0x003b:
            java.lang.String r1 = "ss"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0046
            r0 = 3
            goto L_0x0047
        L_0x0046:
            r0 = -1
        L_0x0047:
            if (r0 == 0) goto L_0x007d
            if (r0 == r5) goto L_0x0076
            if (r0 == r4) goto L_0x006f
            if (r0 != r3) goto L_0x0056
            java.lang.reflect.Constructor<? extends com.google.android.exoplayer2.offline.Downloader> r0 = SS_DOWNLOADER_CONSTRUCTOR
            com.google.android.exoplayer2.offline.Downloader r7 = r6.createDownloader(r7, r0)
            return r7
        L_0x0056:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unsupported type: "
            r1.append(r2)
            java.lang.String r7 = r7.type
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.<init>(r7)
            throw r0
        L_0x006f:
            java.lang.reflect.Constructor<? extends com.google.android.exoplayer2.offline.Downloader> r0 = HLS_DOWNLOADER_CONSTRUCTOR
            com.google.android.exoplayer2.offline.Downloader r7 = r6.createDownloader(r7, r0)
            return r7
        L_0x0076:
            java.lang.reflect.Constructor<? extends com.google.android.exoplayer2.offline.Downloader> r0 = DASH_DOWNLOADER_CONSTRUCTOR
            com.google.android.exoplayer2.offline.Downloader r7 = r6.createDownloader(r7, r0)
            return r7
        L_0x007d:
            com.google.android.exoplayer2.offline.ProgressiveDownloader r0 = new com.google.android.exoplayer2.offline.ProgressiveDownloader
            android.net.Uri r1 = r7.uri
            java.lang.String r7 = r7.customCacheKey
            com.google.android.exoplayer2.offline.DownloaderConstructorHelper r2 = r6.downloaderConstructorHelper
            r0.<init>(r1, r7, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DefaultDownloaderFactory.createDownloader(com.google.android.exoplayer2.offline.DownloadRequest):com.google.android.exoplayer2.offline.Downloader");
    }

    private Downloader createDownloader(DownloadRequest downloadRequest, Constructor<? extends Downloader> constructor) {
        if (constructor != null) {
            try {
                return (Downloader) constructor.newInstance(new Object[]{downloadRequest.uri, downloadRequest.streamKeys, this.downloaderConstructorHelper});
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to instantiate downloader for: ");
                sb.append(downloadRequest.type);
                throw new RuntimeException(sb.toString(), e);
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Module missing for: ");
            sb2.append(downloadRequest.type);
            throw new IllegalStateException(sb2.toString());
        }
    }

    private static Constructor<? extends Downloader> getDownloaderConstructor(Class<?> cls) {
        try {
            return cls.asSubclass(Downloader.class).getConstructor(new Class[]{Uri.class, List.class, DownloaderConstructorHelper.class});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Downloader constructor missing", e);
        }
    }
}
