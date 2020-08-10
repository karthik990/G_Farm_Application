package com.mobiroller.util;

import android.content.Context;
import com.applyze.ApplyzeAnalytics;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ScreenModel;

public class AnalyticsUtil {
    public static void sendStats(Context context, ScreenModel screenModel, String str) {
        ApplyzeAnalytics.getInstance().sendScreenEvent(UtilManager.localizationHelper().getLocalizedTitle(screenModel.getTitle()));
    }

    private static String getScreenType(ScreenModel screenModel, String str) {
        return screenModel.getScreenType() != null ? screenModel.getScreenType() : str;
    }

    private static String getScreenTypeAnalyticsName(Context context, ScreenModel screenModel, String str) {
        String screenType = getScreenType(screenModel, str);
        String[] stringArray = context.getResources().getStringArray(R.array.module_analytics_name);
        String[] stringArray2 = context.getResources().getStringArray(R.array.module_analytics_value);
        for (int i = 0; i < stringArray.length; i++) {
            if (screenType.equalsIgnoreCase(stringArray[i])) {
                return stringArray2[i];
            }
        }
        return screenType;
    }
}
