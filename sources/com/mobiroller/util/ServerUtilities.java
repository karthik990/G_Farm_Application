package com.mobiroller.util;

import android.content.Context;
import com.applyze.ApplyzeAnalytics.UserInfoBuilder;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.mobiroller.DynamicConstants;
import com.mobiroller.helpers.RxJavaRequestStatsHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import java.util.HashMap;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ServerUtilities {
    private static final int BACKOFF_MILLI_SECONDS = 10;
    private static final int MAX_ATTEMPTS = 5;
    private static final Random random = new Random();

    public static void register(Context context, String str, String str2, String str3, String str4) {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        if (str2 != null && str4 != null) {
            try {
                if (!DynamicConstants.MobiRoller_Stage) {
                    new UserInfoBuilder().token(str4).build();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap hashMap = new HashMap();
            hashMap.put("token", str4);
            hashMap.put("accountName", str);
            hashMap.put("udid", str2);
            hashMap.put("locale", str3);
            if (UserHelper.getUserId() != null) {
                hashMap.put("userId", UserHelper.getUserId());
            }
            hashMap.put("OSType", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
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

    public static void sendUserData(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        if (str != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("IPNo", str);
            hashMap.put("imei", str2);
            hashMap.put("device", str3);
            hashMap.put(PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY, str4);
            hashMap.put("accountName", str5);
            long nextInt = (long) (random.nextInt(10) + 10);
            int i = 1;
            while (i <= 5) {
                try {
                    new RxJavaRequestStatsHelper(context, UtilManager.sharedPrefHelper()).getAPIService().sendUserData(hashMap).enqueue(new Callback<String>() {
                        public void onFailure(Call<String> call, Throwable th) {
                        }

                        public void onResponse(Call<String> call, Response<String> response) {
                        }
                    });
                    return;
                } catch (Exception unused) {
                    if (i == 5) {
                        break;
                    }
                    try {
                        Thread.sleep(nextInt);
                        nextInt *= 2;
                        i++;
                    } catch (InterruptedException unused2) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
