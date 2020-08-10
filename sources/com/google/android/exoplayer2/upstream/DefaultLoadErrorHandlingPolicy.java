package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.upstream.Loader.UnexpectedLoaderException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DefaultLoadErrorHandlingPolicy implements LoadErrorHandlingPolicy {
    private static final int DEFAULT_BEHAVIOR_MIN_LOADABLE_RETRY_COUNT = -1;
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT = 3;
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT_PROGRESSIVE_LIVE = 6;
    public static final long DEFAULT_TRACK_BLACKLIST_MS = 60000;
    private final int minimumLoadableRetryCount;

    public DefaultLoadErrorHandlingPolicy() {
        this(-1);
    }

    public DefaultLoadErrorHandlingPolicy(int i) {
        this.minimumLoadableRetryCount = i;
    }

    public long getBlacklistDurationMsFor(int i, long j, IOException iOException, int i2) {
        if (!(iOException instanceof InvalidResponseCodeException)) {
            return C1996C.TIME_UNSET;
        }
        int i3 = ((InvalidResponseCodeException) iOException).responseCode;
        if (i3 == 404 || i3 == 410) {
            return DEFAULT_TRACK_BLACKLIST_MS;
        }
        return C1996C.TIME_UNSET;
    }

    public long getRetryDelayMsFor(int i, long j, IOException iOException, int i2) {
        return ((iOException instanceof ParserException) || (iOException instanceof FileNotFoundException) || (iOException instanceof UnexpectedLoaderException)) ? C1996C.TIME_UNSET : (long) Math.min((i2 - 1) * 1000, 5000);
    }

    public int getMinimumLoadableRetryCount(int i) {
        int i2 = this.minimumLoadableRetryCount;
        if (i2 != -1) {
            return i2;
        }
        return i == 7 ? 6 : 3;
    }
}
