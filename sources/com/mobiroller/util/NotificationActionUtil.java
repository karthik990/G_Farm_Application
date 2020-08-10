package com.mobiroller.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.activities.aveWebView;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FCMNotificationModel.Action;
import com.mobiroller.models.WebContent;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import p043io.reactivex.annotations.SchedulerSupport;

public class NotificationActionUtil {
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Intent getActionIntent(android.content.Context r4, com.mobiroller.models.FCMNotificationModel.Action r5, java.lang.String r6, java.lang.String r7) {
        /*
            java.lang.String r0 = r5.getType()
            int r1 = r0.hashCode()
            r2 = 2
            r3 = 1
            switch(r1) {
                case 96801: goto L_0x002c;
                case 117588: goto L_0x0022;
                case 3387192: goto L_0x0018;
                case 951530617: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0036
        L_0x000e:
            java.lang.String r1 = "content"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0036
            r0 = 0
            goto L_0x0037
        L_0x0018:
            java.lang.String r1 = "none"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0036
            r0 = 3
            goto L_0x0037
        L_0x0022:
            java.lang.String r1 = "web"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0036
            r0 = 1
            goto L_0x0037
        L_0x002c:
            java.lang.String r1 = "app"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0036
            r0 = 2
            goto L_0x0037
        L_0x0036:
            r0 = -1
        L_0x0037:
            if (r0 == 0) goto L_0x0049
            if (r0 == r3) goto L_0x0044
            if (r0 == r2) goto L_0x003f
            r4 = 0
            return r4
        L_0x003f:
            android.content.Intent r4 = getAppIntent(r4, r5, r6)
            return r4
        L_0x0044:
            android.content.Intent r4 = getWebIntent(r4, r5, r6)
            return r4
        L_0x0049:
            android.content.Intent r4 = getContentIntent(r4, r5, r6, r7)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.util.NotificationActionUtil.getActionIntent(android.content.Context, com.mobiroller.models.FCMNotificationModel$Action, java.lang.String, java.lang.String):android.content.Intent");
    }

    private static Intent getAppIntent(Context context, Action action, String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        try {
            context.getPackageManager().getPackageInfo(action.getPackageName(), 0);
            intent.setPackage(action.getPackageName());
            intent.setData(Uri.parse(action.getInAppUrl()));
        } catch (NameNotFoundException e) {
            intent.setData(Uri.parse(action.getWebUrl()));
            e.printStackTrace();
        }
        intent.addFlags(268435456);
        return intent;
    }

    public static Intent getAppIntentBrowser(Action action) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(action.getWebUrl()));
        intent.addFlags(268435456);
        return intent;
    }

    private static Intent getContentIntent(Context context, Action action, String str, String str2) {
        if (!action.getScreenType().equals("aveRSSView")) {
            return ActivityHandler.getIntent(context, action.getId(), action.getScreenType(), action.getScreenSubType(), null);
        }
        return ActivityHandler.getIntent(context, action.getId(), action.getScreenType(), action.getScreenSubType(), null, null, str2);
    }

    private static Intent getWebIntent(Context context, Action action, String str) {
        if (action.getDisplayType().equals(Constants.NOTIFICATION_EXTERNAL)) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(action.getUrl()));
            intent.addFlags(268435456);
            return intent;
        } else if (!action.getDisplayType().equals(Constants.NOTIFICATION_INTERNAL)) {
            return null;
        } else {
            WebContent webContent = new WebContent(str, String.valueOf(Uri.parse(action.getUrl())), true);
            Intent intent2 = new Intent(context, aveWebView.class);
            intent2.putExtra(Constants.NOTIFICATION_TYPE_WEB_CONTENT, webContent);
            return intent2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isExternal(com.mobiroller.models.FCMNotificationModel.Action r5) {
        /*
            java.lang.String r0 = r5.getType()
            int r1 = r0.hashCode()
            r2 = 96801(0x17a21, float:1.35647E-40)
            r3 = 0
            r4 = 1
            if (r1 == r2) goto L_0x002e
            r2 = 117588(0x1cb54, float:1.64776E-40)
            if (r1 == r2) goto L_0x0024
            r2 = 951530617(0x38b73479, float:8.735894E-5)
            if (r1 == r2) goto L_0x001a
            goto L_0x0038
        L_0x001a:
            java.lang.String r1 = "content"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 2
            goto L_0x0039
        L_0x0024:
            java.lang.String r1 = "web"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 0
            goto L_0x0039
        L_0x002e:
            java.lang.String r1 = "app"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 1
            goto L_0x0039
        L_0x0038:
            r0 = -1
        L_0x0039:
            if (r0 == 0) goto L_0x003f
            if (r0 == r4) goto L_0x003e
            return r3
        L_0x003e:
            return r4
        L_0x003f:
            java.lang.String r5 = r5.getDisplayType()
            java.lang.String r0 = com.mobiroller.constants.Constants.NOTIFICATION_INTERNAL
            boolean r5 = r5.equals(r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.util.NotificationActionUtil.isExternal(com.mobiroller.models.FCMNotificationModel$Action):boolean");
    }

    public static boolean isValidAction(Context context, Action action) {
        boolean z = false;
        if (!(action == null || action.getType() == null)) {
            String type = action.getType();
            char c = 65535;
            switch (type.hashCode()) {
                case 96801:
                    if (type.equals(SettingsJsonConstants.APP_KEY)) {
                        c = 2;
                        break;
                    }
                    break;
                case 117588:
                    if (type.equals("web")) {
                        c = 1;
                        break;
                    }
                    break;
                case 3387192:
                    if (type.equals(SchedulerSupport.NONE)) {
                        c = 3;
                        break;
                    }
                    break;
                case 951530617:
                    if (type.equals(Param.CONTENT)) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c != 0) {
                if (c == 1) {
                    if (action.getDisplayType() != null && ((action.getDisplayType().equals(Constants.NOTIFICATION_EXTERNAL) || action.getDisplayType().equals(Constants.NOTIFICATION_INTERNAL)) && action.getUrl() != null)) {
                        z = true;
                    }
                    return z;
                } else if (c == 2) {
                    if (!(action.getPackageName() == null || action.getInAppUrl() == null || action.getWebUrl() == null)) {
                        z = true;
                    }
                    return z;
                } else if (c != 3) {
                    return false;
                } else {
                    return true;
                }
            } else if (!(action.getId() == null || action.getScreenType() == null || !isValidContent(context, action.getScreenType()))) {
                z = true;
            }
        }
        return z;
    }

    private static boolean isValidContent(Context context, String str) {
        for (String equalsIgnoreCase : context.getResources().getStringArray(R.array.activities)) {
            if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }
}
