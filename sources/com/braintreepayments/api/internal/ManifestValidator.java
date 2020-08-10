package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class ManifestValidator {
    public static boolean isActivityDeclaredInAndroidManifest(Context context, Class cls) {
        return getActivityInfo(context, cls) != null;
    }

    public static boolean isUrlSchemeDeclaredInAndroidManifest(Context context, String str, Class cls) {
        Intent intent = new Intent("android.intent.action.VIEW");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("://");
        Intent addCategory = intent.setData(Uri.parse(sb.toString())).addCategory("android.intent.category.DEFAULT").addCategory("android.intent.category.BROWSABLE");
        ActivityInfo activityInfo = getActivityInfo(context, cls);
        return activityInfo != null && activityInfo.launchMode == 2 && AppHelper.isIntentAvailable(context, addCategory);
    }

    public static ActivityInfo getActivityInfo(Context context, Class cls) {
        try {
            ActivityInfo[] activityInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities;
            if (activityInfoArr != null) {
                for (ActivityInfo activityInfo : activityInfoArr) {
                    if (activityInfo.name.equals(cls.getName())) {
                        return activityInfo;
                    }
                }
            }
        } catch (NameNotFoundException unused) {
        }
        return null;
    }
}
