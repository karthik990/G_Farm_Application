package com.applyze;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import com.braintreepayments.api.models.PostalAddressParser;
import java.util.HashMap;
import java.util.Map.Entry;
import org.greenrobot.eventbus.EventBus;

public class ApplyzeAnalytics implements ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final String TAG = "ApplyzeAnalytics";
    private static String apiKey;
    private static String appKey;
    private static Application application;
    private static Context context;
    private static volatile ApplyzeAnalytics instance;
    private static boolean isInitialized;

    public static class UserInfoBuilder {
        private String birthYear;
        private HashMap<String, String> customFields = new HashMap<>();
        private String email;
        private String fullName;
        private String gender;
        private String phoneNumber;
        private String token;
        private String userName;

        public UserInfoBuilder token(String str) {
            this.token = str;
            return this;
        }

        public UserInfoBuilder phoneNumber(String str) {
            this.phoneNumber = str;
            return this;
        }

        public UserInfoBuilder birthYear(String str) {
            this.birthYear = str;
            return this;
        }

        public UserInfoBuilder email(String str) {
            this.email = str;
            return this;
        }

        public UserInfoBuilder userName(String str) {
            this.userName = str;
            return this;
        }

        public UserInfoBuilder fullName(String str) {
            this.fullName = str;
            return this;
        }

        public UserInfoBuilder gender(String str) {
            this.gender = str;
            return this;
        }

        public UserInfoBuilder customField(String str, String str2) {
            this.customFields.put(str, str2);
            return this;
        }

        public void build() {
            if (ApplyzeAnalytics.checkIsInitialized()) {
                if (this.token != null) {
                    EventBus.getDefault().post(new TokenEvent(this.token));
                }
                if (this.phoneNumber != null) {
                    EventBus.getDefault().post(new CustomUserEvent(PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY, this.phoneNumber));
                }
                if (this.birthYear != null) {
                    EventBus.getDefault().post(new CustomUserEvent("birthYear", this.birthYear));
                }
                if (this.email != null) {
                    EventBus.getDefault().post(new CustomUserEvent("email", this.email));
                }
                if (this.userName != null) {
                    EventBus.getDefault().post(new CustomUserEvent("userName", this.userName));
                }
                if (this.fullName != null) {
                    EventBus.getDefault().post(new CustomUserEvent("fullName", this.fullName));
                }
                if (this.gender != null) {
                    EventBus.getDefault().post(new CustomUserEvent("gender", this.gender));
                }
                if (this.customFields.size() != 0) {
                    for (Entry entry : this.customFields.entrySet()) {
                        EventBus.getDefault().post(new CustomField((String) entry.getKey(), (String) entry.getValue()));
                    }
                }
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public static ApplyzeAnalytics getInstance() {
        if (instance == null) {
            synchronized (ApplyzeAnalytics.class) {
                if (instance == null) {
                    instance = new ApplyzeAnalytics();
                }
            }
        }
        return instance;
    }

    public static ApplyzeAnalytics with(Application application2) {
        application = application2;
        ApplyzeAnalytics applyzeAnalytics = new ApplyzeAnalytics();
        instance = applyzeAnalytics;
        return applyzeAnalytics;
    }

    public ApplyzeAnalytics setApiKey(String str) {
        apiKey = str;
        return this;
    }

    public ApplyzeAnalytics setAppKey(String str) {
        appKey = str;
        return this;
    }

    public void init() {
        try {
            validateValues();
            application.registerActivityLifecycleCallbacks(this);
            startAnalyticsService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validateValues() throws Exception {
        if (application != null) {
            String str = apiKey;
            if (str == null || str.isEmpty()) {
                throw new Exception("ApiKey cannot be null or empty");
            }
            String str2 = appKey;
            if (str2 == null || str2.isEmpty()) {
                throw new Exception("AppKey cannot be null or empty");
            }
            context = application.getApplicationContext();
            return;
        }
        throw new Exception("Application cannot be null");
    }

    private void startAnalyticsService() {
        ServiceUtil.finishSession(context, false);
        Intent intent = new Intent(context, ApplyzeAnalyticsService.class);
        intent.putExtra("AnalyticsAPIKey", apiKey);
        intent.putExtra("AnalyticsAPPKey", appKey);
        if (!ServiceUtil.isAnalyticsServiceRunning(context)) {
            context.startService(intent);
        }
        isInitialized = true;
        Log.d(TAG, "Applyze Analytics Service running.");
        application.registerComponentCallbacks(this);
    }

    /* access modifiers changed from: 0000 */
    public void startSession() {
        EventBus.getDefault().post(new SessionStart(apiKey, appKey, true));
    }

    public void sendScreenEvent(String str) {
        if (checkIsInitialized()) {
            EventBus.getDefault().post(new ScreenDisplay(str));
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        EventBus.getDefault().post(new SessionStart(apiKey, appKey, false));
    }

    public void onActivityResumed(Activity activity) {
        EventBus.getDefault().post(new SessionStart(apiKey, appKey, true));
    }

    public void onTrimMemory(int i) {
        ServiceUtil.finishSession(context, false);
    }

    public void onLowMemory() {
        ServiceUtil.finishSession(context, false);
    }

    /* access modifiers changed from: private */
    public static boolean checkIsInitialized() {
        if (!isInitialized) {
            Log.w(TAG, "You need to initialize SDK first.");
        }
        return isInitialized;
    }
}
