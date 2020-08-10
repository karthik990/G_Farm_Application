package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.Intent;
import java.util.List;

public class AppHelper {
    public static boolean isIntentAvailable(Context context, Intent intent) {
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.size() != 1) {
            return false;
        }
        return true;
    }
}
