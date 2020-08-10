package com.mobiroller.helpers;

import android.content.Context;
import android.util.Log;
import com.applyze.ApplyzeAnalytics.UserInfoBuilder;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mobiroller.DynamicConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import java.util.HashMap;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHelper {
    private static String ACCOUNT_NAME_FIELD = "accountName";
    private static final int BACKOFF_MILLI_SECONDS = 10;
    private static String LOCALE_FIELD = "locale";
    private static final int MAX_ATTEMPTS = 5;
    private static String OS_TYPE_FIELD = "OSType";
    private static String TOKEN_FIELD = "token";
    private static String UDID_FIELD = "udid";
    private static String USER_ID_FIELD = "userId";
    private static final Random random = new Random();
    /* access modifiers changed from: private */
    public static String sRegistrationId;

    public static void sendToken(Context context) {
        if (UtilManager.networkHelper().isConnected() && !DynamicConstants.MobiRoller_Stage) {
            try {
                sRegistrationId = UtilManager.sharedPrefHelper().getString(Constants.MobiRoller_Preferences_FCM_Token, "");
                if (sRegistrationId.length() == 0) {
                    registerBackground(context);
                } else {
                    register(context, context.getResources().getString(R.string.mobiroller_username), sRegistrationId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerBackground(final Context context) {
        if (UtilManager.networkHelper().isConnected()) {
            FirebaseInstanceId.getInstance(FirebaseApp.getInstance("main")).getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                public void onComplete(Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Log.w(FirebaseMessaging.INSTANCE_ID_SCOPE, "getInstanceId failed", task.getException());
                        return;
                    }
                    UtilManager.sharedPrefHelper().setFCMToken(((InstanceIdResult) task.getResult()).getToken());
                    Context context = context;
                    NotificationHelper.register(context, context.getString(R.string.mobiroller_username), NotificationHelper.sRegistrationId);
                }
            });
        }
    }

    public static void register(Context context, String str, String str2) {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        if (sharedPrefHelper.getDeviceId() != null && str2 != null) {
            try {
                if (!DynamicConstants.MobiRoller_Stage) {
                    new UserInfoBuilder().token(str2).build();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap hashMap = new HashMap();
            hashMap.put(TOKEN_FIELD, str2);
            hashMap.put(ACCOUNT_NAME_FIELD, str);
            hashMap.put(UDID_FIELD, sharedPrefHelper.getDeviceId());
            hashMap.put(LOCALE_FIELD, LocaleHelper.getLocale().toUpperCase());
            if (UserHelper.getUserId() != null) {
                hashMap.put(USER_ID_FIELD, UserHelper.getUserId());
            }
            hashMap.put(OS_TYPE_FIELD, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
            long nextInt = (long) (random.nextInt(10) + 10);
            int i = 1;
            while (i <= 5) {
                try {
                    new RxJavaRequestStatsHelper(context, sharedPrefHelper).getAPIService().serverRegister(hashMap).enqueue(new Callback<Void>() {
                        public void onFailure(Call<Void> call, Throwable th) {
                        }

                        public void onResponse(Call<Void> call, Response<Void> response) {
                        }
                    });
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (i == 5) {
                        break;
                    }
                    try {
                        Thread.sleep(nextInt);
                        nextInt *= 2;
                        i++;
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
