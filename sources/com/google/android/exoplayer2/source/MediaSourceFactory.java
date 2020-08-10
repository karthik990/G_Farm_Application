package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.offline.StreamKey;
import java.util.List;

public interface MediaSourceFactory {

    /* renamed from: com.google.android.exoplayer2.source.MediaSourceFactory$-CC reason: invalid class name */
    public final /* synthetic */ class CC {
        public static MediaSourceFactory $default$setStreamKeys(MediaSourceFactory mediaSourceFactory, List list) {
            return mediaSourceFactory;
        }
    }

    MediaSource createMediaSource(Uri uri);

    int[] getSupportedTypes();

    MediaSourceFactory setStreamKeys(List<StreamKey> list);
}
