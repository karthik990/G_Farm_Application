package com.google.android.exoplayer2.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RepeatModeUtil {
    public static final int REPEAT_TOGGLE_MODE_ALL = 2;
    public static final int REPEAT_TOGGLE_MODE_NONE = 0;
    public static final int REPEAT_TOGGLE_MODE_ONE = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatToggleModes {
    }

    public static boolean isRepeatModeEnabled(int i, int i2) {
        boolean z = true;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
                if ((i2 & 2) == 0) {
                    z = false;
                }
                return z;
            } else if ((i2 & 1) == 0) {
                z = false;
            }
        }
        return z;
    }

    private RepeatModeUtil() {
    }

    public static int getNextRepeatMode(int i, int i2) {
        for (int i3 = 1; i3 <= 2; i3++) {
            int i4 = (i + i3) % 3;
            if (isRepeatModeEnabled(i4, i2)) {
                return i4;
            }
        }
        return i;
    }
}
