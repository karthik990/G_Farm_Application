package com.mobiroller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.github.ybq.android.spinkit.SpinKitView;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.AsyncRequestHelper;
import com.mobiroller.helpers.ConfigurationHelper;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.AdManager;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MenuUtil;
import com.mobiroller.util.MobirollerIntent;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import java.util.HashMap;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import p101me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SplashApp extends AveActivity {
    private static final String TAG = "SplashApp";
    @Inject
    ApiRequestManager apiRequestManager;
    @Inject
    MobiRollerApplication app;
    @Inject
    FileDownloader fileDownloader;
    @Inject
    ImageManager imageManager;
    private boolean isChatReportNotified = false;
    private boolean isLocal = false;
    private boolean isResumeLoaded;
    @Inject
    JSONParser jParserNew;
    /* access modifiers changed from: private */
    public SpinKitView mProgress;
    /* access modifiers changed from: private */
    public MainModel mainModel;
    @Inject
    MenuHelper menuHelper;
    /* access modifiers changed from: private */
    public Intent menuIntent;
    /* access modifiers changed from: private */
    public NavigationModel navigationModel;
    NetworkHelper networkHelper;
    private boolean pushNotified = false;
    @Inject
    ScreenHelper screenHelper;
    SharedPrefHelper sharedPrefHelper;
    private boolean showIntroMsg = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.splash);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.networkHelper = UtilManager.networkHelper();
        Timber.tag(TAG).mo23218d("onCreate", new Object[0]);
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
        }
        this.mProgress = (SpinKitView) findViewById(R.id.progress_bar);
        this.mProgress.post(new Runnable() {
            public void run() {
                SplashApp.this.mProgress.setIndeterminateDrawable(ProgressViewHelper.getProgressDrawable());
            }
        });
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Timber.tag(TAG).mo23218d("onResume", new Object[0]);
        if (!this.isResumeLoaded) {
            this.isResumeLoaded = true;
            if (getIntent().hasExtra("action")) {
                this.pushNotified = true;
            }
            if (getIntent().hasExtra("chatIntent")) {
                this.isChatReportNotified = true;
            }
            loadApp();
        }
    }

    public void loadApp() {
        Timber.tag(TAG).mo23218d("loadApp", new Object[0]);
        if (this.networkHelper.isConnected()) {
            loadAppFromLive();
        } else {
            loadAppFromLocal();
        }
    }

    public void saveMainAndNavigationJsonToStorage() {
        Timber.tag(TAG).mo23218d("saveMainAndNavigationJsonToStorage", new Object[0]);
        JSONStorage.putMainModel(this.mainModel);
        JSONStorage.putNavigationModel(MenuUtil.setMenuTypeFromType(this.navigationModel));
    }

    public MainModel getMainModelFromResource() {
        Timber.tag(TAG).mo23218d("getMainModelFromResource", new Object[0]);
        try {
            MainModel jSONMainOffline = this.jParserNew.getJSONMainOffline(getResources().openRawResource(getResources().getIdentifier(TtmlNode.START, "raw", getPackageName())));
            this.isLocal = true;
            if (this.sharedPrefHelper.getFirstTime()) {
                JSONStorage.putMainModel(jSONMainOffline);
                this.sharedPrefHelper.setFirstTime();
            }
            return jSONMainOffline;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public InAppPurchaseModel getInAppPurchaseModelFromAssets() {
        Timber.tag(TAG).mo23218d("getInAppPurchaseModelFromAssets", new Object[0]);
        InAppPurchaseModel localInAppPurchaseJSON = this.jParserNew.getLocalInAppPurchaseJSON(this, this.sharedPrefHelper.getUsername());
        JSONStorage.putInAppPurchase(localInAppPurchaseJSON);
        return localInAppPurchaseJSON;
    }

    private void startThirdPartyAds() {
        Timber.tag(TAG).mo23218d("startThirdPartyAds", new Object[0]);
        try {
            String str = "time";
            if (this.sharedPrefHelper.getThirdPartyAdsStatus()) {
                if (!this.sharedPrefHelper.getIsAdmobInterstitialAdEnabled()) {
                    if (!this.sharedPrefHelper.getIsAdmobBannerAdEnabled() || this.sharedPrefHelper.getReturnAdsStatus()) {
                        String str2 = "204383130";
                        if (this.sharedPrefHelper.getReturnAdsStatus()) {
                            StartAppSDK.init((Activity) this, str2, true);
                        } else {
                            StartAppSDK.init((Activity) this, str2, false);
                        }
                        if (this.sharedPrefHelper.getVideoAdsStatus() || !this.sharedPrefHelper.getSplashAdsStatus()) {
                            StartAppAd.disableSplash();
                        }
                        if (this.sharedPrefHelper.getAutoInterstitialStatus()) {
                            if (this.sharedPrefHelper.getAutoInterstitialType().equalsIgnoreCase(str)) {
                                StartAppAd.setAutoInterstitialPreferences(new AutoInterstitialPreferences().setSecondsBetweenAds(this.sharedPrefHelper.getAutoInterstitialValue()));
                            } else {
                                StartAppAd.setAutoInterstitialPreferences(new AutoInterstitialPreferences().setActivitiesBetweenAds(this.sharedPrefHelper.getAutoInterstitialValue()));
                            }
                            StartAppAd.enableAutoInterstitial();
                            return;
                        }
                        StartAppAd.disableAutoInterstitial();
                        return;
                    }
                    return;
                }
            }
            this.sharedPrefHelper.setVideoAdsStatus(false);
            this.sharedPrefHelper.setSplashAdsStatus(false);
            this.sharedPrefHelper.setReturnAdsStatus(false);
            this.sharedPrefHelper.setAutoInterstitialStatus(false);
            this.sharedPrefHelper.setAutoInterstitialType(str);
            this.sharedPrefHelper.setAutoInterstitialValue(60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanNotificationCount() {
        Timber.tag(TAG).mo23218d("cleanNotificationCount", new Object[0]);
        try {
            this.sharedPrefHelper.setUnreadNotificationCount(0);
            if (ShortcutBadger.isBadgeCounterSupported(this)) {
                ShortcutBadger.removeCount(this);
            }
        } catch (Exception e) {
            Log.e("Badger clean error : ", e.getLocalizedMessage());
        }
    }

    private void setApplicationSettings() {
        Timber.tag(TAG).mo23218d("setApplicationSettings", new Object[0]);
        ConfigurationHelper configurationHelper = new ConfigurationHelper(this.sharedPrefHelper, this, this.screenHelper);
        configurationHelper.setAppLanguage(this.mainModel);
        configurationHelper.setAppSettings(this.mainModel);
    }

    /* access modifiers changed from: private */
    public MainModel getMainModelFromLocal() {
        Timber.tag(TAG).mo23218d("getMainModelFromLocal", new Object[0]);
        this.mainModel = JSONStorage.getMainModel();
        if (this.mainModel == null) {
            this.mainModel = getMainModelFromResource();
        }
        return this.mainModel;
    }

    private InAppPurchaseModel getInAppPurchaseModelFromLocal() {
        InAppPurchaseModel inAppPurchaseModel = JSONStorage.getInAppPurchaseModel();
        return inAppPurchaseModel == null ? getInAppPurchaseModelFromAssets() : inAppPurchaseModel;
    }

    /* access modifiers changed from: private */
    public NavigationModel getNavigationModelFromLocal() {
        try {
            this.navigationModel = JSONStorage.getNavigationModel();
            if (this.navigationModel != null) {
                return this.navigationModel;
            }
            this.navigationModel = this.jParserNew.getNavigationJSONFromLocal(this, this.sharedPrefHelper.getUsername(), true, false, false);
            if (this.navigationModel == null) {
                this.navigationModel = this.jParserNew.getLocalNavigationJSON(this, this.sharedPrefHelper.getUsername());
            }
            return this.navigationModel;
        } catch (Exception unused) {
            finish();
            return null;
        }
    }

    private Intent getApplicationMenuIntent() {
        Intent menuIntentFromMenuType = MenuUtil.getMenuIntentFromMenuType(this.navigationModel, this);
        menuIntentFromMenuType.putExtra("localObj", this.menuHelper.checkNavItems(this.navigationModel));
        menuIntentFromMenuType.putExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, this.showIntroMsg);
        menuIntentFromMenuType.putExtra("isLocal", this.isLocal);
        if (this.pushNotified) {
            String str = "action";
            menuIntentFromMenuType.putExtra(str, getIntent().getSerializableExtra(str));
            String str2 = "title";
            menuIntentFromMenuType.putExtra(str2, getIntent().getStringExtra(str2));
            menuIntentFromMenuType.putExtra(Constants.NOTIFICATION_CONTENT, getIntent().getStringExtra(Constants.NOTIFICATION_CONTENT));
            menuIntentFromMenuType.putExtra("pushNotified", true);
        } else if (this.isChatReportNotified) {
            menuIntentFromMenuType.putExtra("chatIntentReport", true);
        }
        Intent intent = getIntent();
        String str3 = ChatConstants.ARG_NOTIFICATION_MODEL;
        if (intent.hasExtra(str3)) {
            menuIntentFromMenuType.putExtra(str3, getIntent().getSerializableExtra(str3));
        }
        return menuIntentFromMenuType;
    }

    public void loadAppFromLive() {
        Timber.tag(TAG).mo23218d("loadAppFromLive", new Object[0]);
        try {
            this.apiRequestManager.getMainJSONAsync(getResources().getString(R.string.mobiroller_username)).enqueue(new Callback<MainModel>() {
                public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                    if (response == null || response.body() == null) {
                        SplashApp splashApp = SplashApp.this;
                        splashApp.mainModel = splashApp.getMainModelFromLocal();
                        SplashApp splashApp2 = SplashApp.this;
                        splashApp2.navigationModel = splashApp2.getNavigationModelFromLocal();
                        SplashApp.this.applyJSONs();
                        return;
                    }
                    SplashApp.this.mainModel = (MainModel) response.body();
                    JSONStorage.putMainModel(SplashApp.this.mainModel);
                    SplashApp.this.sharedPrefHelper.setFirstTime();
                    SplashApp.this.apiRequestManager.getNavigationJSONAsync(SplashApp.this.getResources().getString(R.string.mobiroller_username)).enqueue(new Callback<NavigationModel>() {
                        public void onResponse(Call<NavigationModel> call, Response<NavigationModel> response) {
                            if (response == null || response.body() == null) {
                                SplashApp.this.navigationModel = SplashApp.this.getNavigationModelFromLocal();
                                SplashApp.this.saveMainAndNavigationJsonToStorage();
                                SplashApp.this.applyJSONs();
                                return;
                            }
                            SplashApp.this.navigationModel = (NavigationModel) response.body();
                            SplashApp.this.saveMainAndNavigationJsonToStorage();
                            if (!SplashApp.this.sharedPrefHelper.getUserLoginStatus() || !SplashApp.this.networkHelper.isConnected()) {
                                SplashApp.this.applyJSONs();
                            } else {
                                SplashApp.this.login();
                            }
                        }

                        public void onFailure(Call<NavigationModel> call, Throwable th) {
                            SplashApp.this.navigationModel = SplashApp.this.getNavigationModelFromLocal();
                            SplashApp.this.saveMainAndNavigationJsonToStorage();
                            SplashApp.this.applyJSONs();
                        }
                    });
                }

                public void onFailure(Call<MainModel> call, Throwable th) {
                    SplashApp splashApp = SplashApp.this;
                    splashApp.mainModel = splashApp.getMainModelFromLocal();
                    SplashApp splashApp2 = SplashApp.this;
                    splashApp2.navigationModel = splashApp2.getNavigationModelFromLocal();
                    SplashApp.this.applyJSONs();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.mainModel = getMainModelFromLocal();
            this.navigationModel = getNavigationModelFromLocal();
            applyJSONs();
        }
    }

    private void loadAppFromLocal() {
        Timber.tag(TAG).mo23218d("loadAppFromLocal", new Object[0]);
        this.mainModel = getMainModelFromLocal();
        this.navigationModel = getNavigationModelFromLocal();
        applyJSONs();
    }

    /* access modifiers changed from: private */
    public void applyJSONs() {
        Timber.tag(TAG).mo23218d("applyJSONs", new Object[0]);
        setApplicationSettings();
        if (this.sharedPrefHelper.getUnreadNotificationCount() > 0) {
            cleanNotificationCount();
        }
        this.sharedPrefHelper.setUserLoginRegistrationActive(this.navigationModel.isRegistrationActive());
        this.sharedPrefHelper.setUserLoginActive(this.navigationModel.isLoginActive());
        if (!(this.mainModel.getIntroMessage() == null || this.mainModel.getIntroMessage().getIntroMessage() == null || this.mainModel.getIntroMessage().getIntroMessage().equals("null"))) {
            if (this.sharedPrefHelper.getIntroMessage() == null) {
                this.sharedPrefHelper.setIntroMessage(this.mainModel.getIntroMessage());
                this.showIntroMsg = true;
            } else if (!this.sharedPrefHelper.getIntroMessage().equals(this.mainModel.getIntroMessage())) {
                this.sharedPrefHelper.setIntroMessage(this.mainModel.getIntroMessage());
                this.showIntroMsg = true;
            }
        }
        if (this.sharedPrefHelper.getIsInAppPurchaseActive()) {
            if (JSONStorage.getInAppPurchaseModel() == null) {
                getInAppPurchaseModelFromLocal();
                if (JSONStorage.getInAppPurchaseModel() == null) {
                    getInAppPurchaseModelFromAssets();
                }
            }
            AsyncRequestHelper.getInAppPurchaseItems();
        }
        if (this.sharedPrefHelper.getBoolean(Constants.MobiRoller_Preferences_Show_MobiRoller_Badge) && this.sharedPrefHelper.getString(Constants.MobiRoller_Preferences_MobiRoller_Badge_Url, null) != null) {
            AsyncRequestHelper.getBadgeModel(this.sharedPrefHelper.getString(Constants.MobiRoller_Preferences_MobiRoller_Badge_Url));
        }
        if (this.sharedPrefHelper.getChatValidated() && this.sharedPrefHelper.getIsChatActive()) {
            AsyncRequestHelper.getChatRoles();
        }
        this.menuIntent = getApplicationMenuIntent();
        loadAds();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Timber.tag(TAG).mo23218d("onDestroy", new Object[0]);
        super.onDestroy();
    }

    private void loadAds() {
        Timber.tag(TAG).mo23218d("loadAds", new Object[0]);
        if (this.networkHelper.isConnected()) {
            if (this.sharedPrefHelper.getIsAdmobInterstitialAdEnabled() && this.sharedPrefHelper.getAdUnitID() != null) {
                AdManager.getInstance().createInterstitialAd();
            }
            startThirdPartyAds();
        }
        startMenuActivity();
    }

    private void startMenuActivity() {
        Timber.tag(TAG).mo23218d("startMenuActivity", new Object[0]);
        runOnUiThread(new Runnable() {
            public void run() {
                if (SplashApp.this.menuIntent != null) {
                    SplashApp splashApp = SplashApp.this;
                    splashApp.startActivity(splashApp.menuIntent);
                    SplashApp.this.finish();
                    return;
                }
                Toast.makeText(SplashApp.this.getBaseContext(), SplashApp.this.getResources().getString(R.string.there_is_problem_try_again), 1).show();
            }
        });
    }

    public void login() {
        Timber.tag(TAG).mo23218d("login", new Object[0]);
        ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService();
        HashMap hashMap = new HashMap();
        hashMap.put("sessionKey", UserHelper.getUserSessionToken());
        hashMap.put(TtmlNode.ATTR_ID, UserHelper.getUserId());
        hashMap.put("apiKey", getString(R.string.applyze_api_key));
        hashMap.put("appKey", getString(R.string.applyze_app_key));
        hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
        hashMap.put("lang", LocaleHelper.getLocale().toUpperCase());
        applyzeUserAPIService.validateSession(hashMap).enqueue(new Callback<UserLoginResponse>() {
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    SplashApp.this.saveInfo((UserLoginResponse) response.body());
                    return;
                }
                UserHelper.logout(SplashApp.this.sharedPrefHelper, SplashApp.this);
                SplashApp.this.applyJSONs();
            }

            public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                UserHelper.logout(SplashApp.this.sharedPrefHelper, SplashApp.this);
                Toast.makeText(SplashApp.this, R.string.common_error, 0).show();
                SplashApp.this.applyJSONs();
            }
        });
    }

    public void saveInfo(UserLoginResponse userLoginResponse) {
        Timber.tag(TAG).mo23218d("saveInfo", new Object[0]);
        UserHelper.saveLoginCredentials(this, userLoginResponse);
        if (!this.sharedPrefHelper.getChatValidated() || !this.sharedPrefHelper.getIsChatActive()) {
            EventBus.getDefault().post(new LoginEvent());
            applyJSONs();
            return;
        }
        startActivityForResult(MobirollerIntent.getFirebaseSignInIntent(this, userLoginResponse), 134);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 5) {
            if (this.networkHelper.isConnected()) {
                loadAppFromLive();
            } else {
                loadAppFromLocal();
            }
        } else if (i == 134) {
            EventBus.getDefault().post(new LoginEvent());
            applyJSONs();
        }
    }
}
