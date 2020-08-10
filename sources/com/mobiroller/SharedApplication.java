package com.mobiroller;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.helpers.UtilManager.Builder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.services.ClosingService;
import com.mobiroller.util.ChatRolesUtil;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import p043io.fabric.sdk.android.Fabric;

public class SharedApplication extends MultiDexApplication implements ActivityLifecycleCallbacks, LifecycleObserver {
    public static MobiRollerApplication app = null;
    public static String[] assetList = null;
    public static String aveChatViewId = null;
    private static List<ChatRoleModel> chatRoleModels = null;
    public static Context context = null;
    public static boolean isAppForeground = false;
    public static boolean isChatActivityOpen = false;
    public static boolean isInterstitialShown = false;
    public static List<String> jsonIdList = new ArrayList();
    public static int mScreensCounter;
    public static AppComponent mainComponent;
    public static String openChatId;
    private SharedPrefHelper sharedPrefHelper;

    public MobiRollerApplication getApp() {
        return null;
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Fabric.with(this, new Crashlytics());
        context = this;
        app = getApp();
        new Builder().setContext(this).build();
        if (!UtilManager.sharedPrefHelper().getLanguageSetByUser()) {
            UtilManager.sharedPrefHelper().put(Constants.DISPLAY_LANGUAGE, Locale.getDefault().getLanguage().toLowerCase());
        }
        try {
            assetList = getResources().getAssets().list("Files");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        registerActivityLifecycleCallbacks(this);
        setTheme(R.style.FullNoTitle);
        Charset.defaultCharset();
        Constants.Mobiroller_Preferences_FilePath = context.getFilesDir().getPath();
        Charset.forName(Constants.MobiRoller_Preferences_CharSet);
        Twitter.initialize(new TwitterConfig.Builder(this).logger(new DefaultLogger(3)).twitterAuthConfig(new TwitterAuthConfig(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET)).debug(true).build());
        Intent intent = new Intent(this, ClosingService.class);
        if (isAppForeground) {
            startService(intent);
        }
        destroyNotifications();
    }

    public static AppComponent getMainComponent() {
        return mainComponent;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        mScreensCounter++;
    }

    public void onActivityDestroyed(Activity activity) {
        mScreensCounter--;
        if (mScreensCounter < 0) {
            mScreensCounter = 0;
        }
    }

    public static boolean isAppBackgroundForChat() {
        return mScreensCounter == 1;
    }

    public static boolean isAppBackground() {
        return mScreensCounter == 1;
    }

    public static boolean isAppBackgroundChat() {
        return mScreensCounter >= 1;
    }

    public static boolean isAppKilled() {
        return mScreensCounter <= 1;
    }

    public static List<ChatRoleModel> getChatRoleModels() {
        if (chatRoleModels == null) {
            chatRoleModels = ChatRolesUtil.getDatabase(context);
        }
        return chatRoleModels;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LocaleHelper.setLocale(this);
    }

    @OnLifecycleEvent(Event.ON_START)
    public void appInStartState() {
        isAppForeground = true;
    }

    @OnLifecycleEvent(Event.ON_STOP)
    public void appInStopState() {
        isAppForeground = false;
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    public void appInDestroyState() {
        destroyNotifications();
    }

    private void destroyNotifications() {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        notificationManager.cancel(44);
        notificationManager.cancel(100);
    }
}
