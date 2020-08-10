package com.mobiroller.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FCMNotificationModel;
import com.mobiroller.models.NotificationModel;
import java.util.ArrayList;
import java.util.UUID;

public class NotificationUtil {
    private static void updateDb(ArrayList<NotificationModel> arrayList, Context context) {
        String json = new Gson().toJson((Object) arrayList, new TypeToken<ArrayList<NotificationModel>>() {
        }.getType());
        Editor edit = context.getSharedPreferences("NotificationPreferences", 0).edit();
        edit.putString("NotificationUtil", json);
        edit.apply();
    }

    public static ArrayList<NotificationModel> getDb(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("NotificationPreferences", 0);
        String str = "NotificationUtil";
        String string = sharedPreferences.getString(str, null);
        if (string == null) {
            return null;
        }
        try {
            return (ArrayList) new Gson().fromJson(string, new TypeToken<ArrayList<NotificationModel>>() {
            }.getType());
        } catch (Exception unused) {
            sharedPreferences.edit().remove(str).apply();
            return new ArrayList<>();
        }
    }

    public static void addNotification(NotificationModel notificationModel, Context context) {
        notificationModel.setUniqueId(UUID.randomUUID().toString());
        ArrayList db = getDb(context);
        if (db == null) {
            db = new ArrayList();
        }
        db.add(0, notificationModel);
        updateDb(db, context);
    }

    public static void addNotification(FCMNotificationModel fCMNotificationModel, Context context) {
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setMessage(fCMNotificationModel.getBody());
        notificationModel.notificationType = getNotificationType(fCMNotificationModel);
        if (fCMNotificationModel.getTitle() != null) {
            notificationModel.title = fCMNotificationModel.getTitle();
        }
        if (fCMNotificationModel.getAction() != null) {
            if (fCMNotificationModel.getAction().getType().equalsIgnoreCase(Constants.NOTIFICATION_TYPE_CONTENT)) {
                if (fCMNotificationModel.getAction().getId() != null) {
                    notificationModel.setScreenId(fCMNotificationModel.getAction().getId());
                }
                if (fCMNotificationModel.getAction().getScreenType() != null) {
                    notificationModel.setScreenType(fCMNotificationModel.getAction().getScreenType());
                }
                if (fCMNotificationModel.getAction().getScreenSubType() != null) {
                    notificationModel.subScreenType = fCMNotificationModel.getAction().getScreenSubType();
                }
            } else if (fCMNotificationModel.getAction().getType().equalsIgnoreCase(Constants.NOTIFICATION_TYPE_APP)) {
                if (fCMNotificationModel.getAction().getInAppUrl() != null) {
                    notificationModel.inAppUrl = fCMNotificationModel.getAction().getInAppUrl();
                }
                if (fCMNotificationModel.getAction().getWebUrl() != null) {
                    notificationModel.webURL = fCMNotificationModel.getAction().getWebUrl();
                }
                if (fCMNotificationModel.getAction().getPackageName() != null) {
                    notificationModel.packageName = fCMNotificationModel.getAction().getPackageName();
                }
            } else if (fCMNotificationModel.getAction().getType().equalsIgnoreCase(Constants.NOTIFICATION_TYPE_WEB)) {
                if (fCMNotificationModel.getAction().getUrl() != null) {
                    notificationModel.url = fCMNotificationModel.getAction().getUrl();
                }
                if (fCMNotificationModel.getAction().getDisplayType() != null) {
                    notificationModel.displayType = fCMNotificationModel.getAction().getDisplayType();
                }
            }
        }
        addNotification(notificationModel, context);
    }

    private static String getNotificationType(FCMNotificationModel fCMNotificationModel) {
        return (fCMNotificationModel.getAction() == null || fCMNotificationModel.getAction().getType() == null) ? "" : fCMNotificationModel.getAction().getType();
    }

    public static void removeFromList(NotificationModel notificationModel, Context context) {
        ArrayList db = getDb(context);
        if (db == null) {
            db = new ArrayList();
        }
        int i = 0;
        while (true) {
            if (i >= db.size()) {
                break;
            } else if (((NotificationModel) db.get(i)).getUniqueId().equalsIgnoreCase(notificationModel.getUniqueId())) {
                db.remove(i);
                break;
            } else {
                i++;
            }
        }
        updateDb(db, context);
    }

    public static void setRead(NotificationModel notificationModel, Context context) {
        ArrayList db = getDb(context);
        if (db == null) {
            db = new ArrayList();
        }
        int i = 0;
        while (true) {
            if (i >= db.size()) {
                break;
            } else if (((NotificationModel) db.get(i)).getUniqueId().equalsIgnoreCase(notificationModel.getUniqueId())) {
                ((NotificationModel) db.get(i)).setRead();
                break;
            } else {
                i++;
            }
        }
        updateDb(db, context);
    }

    public static void showPopup(final Activity activity, final NotificationModel notificationModel) {
        final MaterialDialog build = new Builder(activity).customView((int) R.layout.layout_custom_dialog, false).build();
        build.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        build.show();
        View customView = build.getCustomView();
        Button button = (Button) customView.findViewById(R.id.layout_custom_close_button);
        Button button2 = (Button) customView.findViewById(R.id.layout_custom_review_button);
        ((TextView) customView.findViewById(R.id.notification_message)).setText(notificationModel.getMessage());
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                build.dismiss();
            }
        });
        if (notificationModel != null && notificationModel.notificationType != null && !notificationModel.notificationType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_WEB) && !notificationModel.notificationType.equalsIgnoreCase(Constants.NOTIFICATION_TYPE_APP) && !checkActivity(activity, notificationModel.getScreenType())) {
            button2.setVisibility(8);
        }
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                build.dismiss();
                if (notificationModel.notificationType == null || notificationModel.getAction() == null) {
                    NotificationUtil.onClickReview(notificationModel, activity);
                } else {
                    activity.startActivity(NotificationActionUtil.getActionIntent(MobiRollerApplication.app, notificationModel.getAction(), notificationModel.title, notificationModel.getMessage()));
                }
            }
        });
    }

    private static boolean checkActivity(Context context, String str) {
        for (String equalsIgnoreCase : context.getResources().getStringArray(R.array.activities)) {
            if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static void onClickReview(NotificationModel notificationModel, Activity activity) {
        if (notificationModel.getScreenType().equals("aveRSSView")) {
            ActivityHandler.startActivity((Context) activity, notificationModel.getScreenId(), notificationModel.getScreenType(), notificationModel.subScreenType, (String) null, null, notificationModel.getMessage());
            return;
        }
        ActivityHandler.startActivity(activity, notificationModel.getScreenId(), notificationModel.getScreenType(), notificationModel.subScreenType, null, null);
    }
}
