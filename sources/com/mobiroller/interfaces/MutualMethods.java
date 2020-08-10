package com.mobiroller.interfaces;

import android.content.Context;

public interface MutualMethods {
    void ScreenDisplayStats(Context context, String str, String str2);

    int getHeightForDevice(int i);

    String getLocalizedTitle(Context context, String str);

    int getWidthForDevice(int i);
}
