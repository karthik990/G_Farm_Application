package com.twitter.sdk.android.tweetui.internal;

import java.util.Locale;

final class MediaTimeUtils {
    private static final String TIME_FORMAT_LONG = "%1$d:%2$02d:%3$02d";
    private static final String TIME_FORMAT_SHORT = "%1$d:%2$02d";

    private MediaTimeUtils() {
    }

    static String getPlaybackTime(long j) {
        int i = (int) (j / 1000);
        int i2 = i % 60;
        int i3 = (i / 60) % 60;
        int i4 = i / 3600;
        if (i4 > 0) {
            return String.format(Locale.getDefault(), TIME_FORMAT_LONG, new Object[]{Integer.valueOf(i4), Integer.valueOf(i3), Integer.valueOf(i2)});
        }
        return String.format(Locale.getDefault(), TIME_FORMAT_SHORT, new Object[]{Integer.valueOf(i3), Integer.valueOf(i2)});
    }
}
