package com.mobiroller.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiroller.DynamicConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.IntroModel;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.youtube.ChannelDetailModel;
import com.mobiroller.util.ScreenUtil;
import com.mobiroller.views.theme.Theme;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lib.android.paypal.com.magnessdk.p100a.C5985b;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public class SharedPrefHelper {
    private final String MobiRoller_Preferences = "MobiRoller_Preferences";
    private SharedPreferences appSharedPrefs;
    private Context context;

    public Context getContext() {
        return this.context;
    }

    public SharedPrefHelper(Context context2) {
        this.context = context2;
        getSharedPrefs(context2);
    }

    public boolean hasValue(String str) {
        return this.appSharedPrefs.contains(str);
    }

    public SharedPreferences getSharedPrefs(Context context2) {
        this.appSharedPrefs = context2.getSharedPreferences("MobiRoller_Preferences", 0);
        return this.appSharedPrefs;
    }

    public void setDefaultLang(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_DefaultLang, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getDefaultLang() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_DefaultLang, "TR");
    }

    public void setNotification(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Notification, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getNotification() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Notification, true);
    }

    public void setNotificationSound(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_NotificationSound, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getNotificationSound() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_NotificationSound, true);
    }

    public void setLocaleCodes(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_LocaleCodes, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getLocaleCodes() {
        String string = this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_LocaleCodes, null);
        if (string != null) {
            return string.toLowerCase();
        }
        return null;
    }

    public void setUserLoginStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserLoginStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getUserLoginStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserLoginStatus, false);
    }

    public void setIsChatActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsChatAvailable, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getIsChatActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsChatAvailable, false);
    }

    public void setIsInAppPurchaseActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_InAppPurchaseActive, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getIsInAppPurchaseActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_InAppPurchaseActive, false);
    }

    public void setIsUserAgremeentActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsUserAgremeentActive, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getIsUserAgremeentActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsUserAgremeentActive, false);
    }

    public void setUserAgremeent(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_UserAgremeent, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getUserAgremeent() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserAgremeent, "");
    }

    public void setUserIsAvailableForChat(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserIsAvailableForChat, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getUserIsAvailableForChat() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserIsAvailableForChat, false);
    }

    public String getUserRole() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserRole, "0");
    }

    public String getLogoURL() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_LogoURL, "");
    }

    public String getLoginBackgroundURL() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_LoginBackgroundURL, "");
    }

    public void setUserRole(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_UserRole, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setLogoURL(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_LogoURL, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setLoginBackgroundURL(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_LoginBackgroundURL, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setUserLoginRegistrationActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserLogin_IsRegisterationActive, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setUserLoginActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserLogin_IsLoginActive, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getUserLoginActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserLogin_IsLoginActive, false);
    }

    public boolean getUserLoginRegistrationActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserLogin_IsRegisterationActive, false);
    }

    public void setRefreshIntroStatus() {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_RefreshIntro, true).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getRefreshIntroStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_RefreshIntro, false);
    }

    public boolean getLanguageSetByUser() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_LanguageSetByUser, false);
    }

    public void languageSetByUser() {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_LanguageSetByUser, true).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setDeviceLang(Context context2, String str) {
        this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_CurrentLang, str.toLowerCase()).apply();
        LocaleHelper.setAppLanguage(context2, str);
    }

    public String getDeviceLang() {
        return UtilManager.sharedPrefHelper().getString(Constants.DISPLAY_LANGUAGE).toLowerCase();
    }

    public int getTabHeight() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_TabHeight, 0);
    }

    /* access modifiers changed from: 0000 */
    public int getStatusBarHeight() {
        try {
            return (int) Math.ceil((double) (this.context.getResources().getDisplayMetrics().density * 25.0f));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setActionBarColor(int i) {
        try {
            Theme.primaryColor = i;
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_ActionBarColor, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getActionBarColor() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_ActionBarColor, R.color.gray);
    }

    public String getDeviceId() {
        return Secure.getString(this.context.getContentResolver(), C5985b.f4233f);
    }

    public void setDeviceId() {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_Device_ID, Secure.getString(this.context.getContentResolver(), C5985b.f4233f)).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setMotionHeight(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_MotionHeight, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getMotionHeight() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_MotionHeight, 0);
    }

    public void setMotionWidth(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_MotionWidth, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getMotionWidth() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_MotionWidth, 0);
    }

    public void setTabActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.isTabActive, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getTabActive() {
        return this.appSharedPrefs.getBoolean(Constants.isTabActive, false);
    }

    public String getRegistrationId(Context context2) {
        String str = "";
        String string = this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_Registration_ID, str);
        String str2 = "AppMain";
        if (string.length() == 0) {
            Log.v(str2, "Registration not found.");
            return str;
        } else if (this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_App_Version, Integer.MIN_VALUE) == getAppVersion(this.context)) {
            return string;
        } else {
            Log.v(str2, "App version changed or registration expired.");
            return str;
        }
    }

    private int getAppVersion(Context context2) {
        try {
            return context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not get package name: ");
            sb.append(e);
            throw new RuntimeException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    @Deprecated
    public String getAppVersionName(Context context2) {
        try {
            return context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not get package name: ");
            sb.append(e);
            throw new RuntimeException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public String getAppVersionName() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not get package name: ");
            sb.append(e);
            throw new RuntimeException(sb.toString());
        }
    }

    public void setRegistrationId(Context context2, String str) {
        int appVersion = getAppVersion(context2);
        StringBuilder sb = new StringBuilder();
        sb.append("Saving regId on app version ");
        sb.append(appVersion);
        String str2 = "AppMain";
        Log.v(str2, sb.toString());
        Editor edit = this.appSharedPrefs.edit();
        edit.putString(Constants.MobiRoller_Preferences_Registration_ID, str);
        edit.putInt(Constants.MobiRoller_Preferences_App_Version, appVersion);
        long currentTimeMillis = System.currentTimeMillis() + Constants.REGISTRATION_EXPIRY_TIME_MS;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Setting registration expiry time to ");
        sb2.append(new Timestamp(currentTimeMillis));
        Log.v(str2, sb2.toString());
        edit.putLong(Constants.MobiRoller_Preferences_Expiration_Time, currentTimeMillis);
        edit.apply();
    }

    public boolean getFirstTime() {
        return this.appSharedPrefs.getBoolean(Constants.firstTime, true);
    }

    public void setFirstTime() {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.firstTime, false).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getLocationPermissionFirstTime() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Location_Permission_First_Time, true);
    }

    public void setLocationPermissionFirstTime() {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Location_Permission_First_Time, false).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean getFirstTimeForLanguage() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_First_Time_Language, true);
    }

    /* access modifiers changed from: 0000 */
    public void setFirstTimeForLanguage() {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_First_Time_Language, false).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setUserToken(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.USER_TOKEN, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getUserToken() {
        return this.appSharedPrefs.getString(Constants.USER_TOKEN, null);
    }

    public void setUserTokenSecret(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.USER_SECRET, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getUserTokenSecret() {
        return this.appSharedPrefs.getString(Constants.USER_SECRET, null);
    }

    public void setIntroMessage(IntroModel introModel) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_IntroMessage, new Gson().toJson((Object) introModel)).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public IntroModel getIntroMessage() {
        return (IntroModel) new Gson().fromJson(this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_IntroMessage, null), IntroModel.class);
    }

    /* access modifiers changed from: 0000 */
    public void setAdUnitID(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_AdUnitID, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getAdUnitID() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_AdUnitID, "");
    }

    /* access modifiers changed from: 0000 */
    public void setNativeAddUnitID(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_NativeAdUnitID, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getNativeAddUnitID() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_NativeAdUnitID, "");
    }

    /* access modifiers changed from: 0000 */
    public void setBannerAdUnitID(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_BannerAdUnitID, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getInterstitialClickCount() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_Interstitial_Click_Count, 0);
    }

    public void setInterstitialClickCount(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_Interstitial_Click_Count, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getInterstitialTimer(Date date) {
        return getDateDiff(new Date(this.appSharedPrefs.getLong(Constants.MobiRoller_Preferences_Interstitial_Timer, new Date().getTime())), date, TimeUnit.SECONDS) > ((long) getInterstitialTimeInterval());
    }

    public void setInterstitialTimer(Date date) {
        try {
            this.appSharedPrefs.edit().putLong(Constants.MobiRoller_Preferences_Interstitial_Timer, date.getTime()).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public AdView getBannerAd() {
        return Constants.MobiRoller_Preferences_BannerAdView;
    }

    public void setBannerAd(Context context2) {
        if (getIsAdmobBannerAdEnabled() && getBannerAdUnitID() != null && !getBannerAdUnitID().equalsIgnoreCase("")) {
            AdView adView = new AdView(context2);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(getBannerAdUnitID());
            adView.loadAd(new Builder().build());
            Constants.MobiRoller_Preferences_BannerAdView = adView;
        }
    }

    /* access modifiers changed from: 0000 */
    public void setBannerAd(Context context2, final View view, final RelativeLayout relativeLayout) {
        AdView adView = new AdView(context2);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getBannerAdUnitID());
        adView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (!SharedPrefHelper.this.getBannerAdUnitID().isEmpty() && SharedPrefHelper.this.getBannerAd() != null) {
                    LayoutParams layoutParams = new LayoutParams(-1, -2);
                    layoutParams.addRule(12);
                    if (SharedPrefHelper.this.getBannerAd().getParent() != null) {
                        ((ViewGroup) SharedPrefHelper.this.getBannerAd().getParent()).removeView(SharedPrefHelper.this.getBannerAd());
                    }
                    relativeLayout.addView(SharedPrefHelper.this.getBannerAd(), layoutParams);
                }
            }

            public void onAdFailedToLoad(int i) {
                View view = view;
                if (view != null) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    view.setLayoutParams(layoutParams);
                }
                Constants.MobiRoller_Preferences_BannerAdView = null;
            }
        });
        adView.loadAd(new Builder().build());
        Constants.MobiRoller_Preferences_BannerAdView = adView;
    }

    public String getBannerAdUnitID() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_BannerAdUnitID, "");
    }

    public void setIsAdmobInterstitialAdEnabled(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsAdEnabled, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getIsAdmobInterstitialAdEnabled() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsAdEnabled, false);
    }

    public void setIsBannerAdEnabled(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsBannerAdEnabled, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getIsAdmobBannerAdEnabled() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsBannerAdEnabled, false);
    }

    public boolean isBannerActive() {
        return getIsAdmobBannerAdEnabled() || getThirdPartyAdsStatus();
    }

    public void setIsNativeAdEnabled(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsNativeAdEnabled, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getIsNativeAdEnabled() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsNativeAdEnabled, false);
    }

    /* access modifiers changed from: 0000 */
    public void setInterstitialMultipleDisplayEnabled(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_InterstitialMultipleDisplayEnabled, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getInterstitialMultipleDisplayEnabled() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_InterstitialMultipleDisplayEnabled, false);
    }

    public String getDeviceModel() {
        SharedPreferences sharedPreferences = this.appSharedPrefs;
        String str = Constants.MobiRoller_Preferences_DeviceModel;
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append(" - ");
        sb.append(Build.MODEL);
        return sharedPreferences.getString(str, sb.toString());
    }

    public String getUsername() {
        if (!DynamicConstants.MobiRoller_Stage) {
            return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserName, this.context.getResources().getString(R.string.mobiroller_username));
        }
        return Constants.MobiRoller_Preview_UserName;
    }

    public String getUsernameForPreview() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserName, this.context.getResources().getString(R.string.mobiroller_username));
    }

    public void setUsername(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_UserName, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getPassword() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_Password, " ");
    }

    public void setPassword(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_Password, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getUserEmail() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserEmail, null);
    }

    public void setUserEmail(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_UserEmail, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setUserPassword(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_UserPassword, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getUserPassword() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserPassword, null);
    }

    /* access modifiers changed from: 0000 */
    public int getProgressAnimationType() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_ProgressAnimationType, 8);
    }

    public void setProgressAnimationType(int i) {
        this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_ProgressAnimationType, i).apply();
    }

    public String getUserId() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_UserId, "");
    }

    public void setUserId(String str) {
        this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_UserId, str).apply();
    }

    public String getFirebaseToken() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_FirebaseToken, "");
    }

    public void setFirebaseToken(String str) {
        this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_FirebaseToken, str).apply();
    }

    public boolean getAskBeforeExit() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_AskBeforeExit, false);
    }

    public void setAskBeforeExit(boolean z) {
        this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_AskBeforeExit, z).apply();
    }

    public boolean getIsChatVersionSupported() {
        return !this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Chat_Minimum_Version_Setted, false) || this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Chat_Minimum_Version, false);
    }

    public void setIsChatVersionSupported(boolean z) {
        this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Chat_Minimum_Version, z).apply();
        this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Chat_Minimum_Version_Setted, true).apply();
    }

    /* access modifiers changed from: 0000 */
    public boolean getRateApp() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_RateApp, true);
    }

    /* access modifiers changed from: 0000 */
    public void setRateApp(boolean z) {
        this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_RateApp, z).apply();
    }

    public void setProgressAnimationColor(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_ProgressAnimationColor, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /* access modifiers changed from: 0000 */
    public int getProgressAnimationColor() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_ProgressAnimationColor, R.color.gray);
    }

    public boolean getBleshServiceStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_BleshServiceStatus, true);
    }

    /* access modifiers changed from: 0000 */
    public void setBleshServiceStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_BleshServiceStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getThirdPartyAdsStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_ThirdPartyAdsStatus, false);
    }

    /* access modifiers changed from: 0000 */
    public void setThirdPartyAdsStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_ThirdPartyAdsStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getVideoAdsStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_VideoAdsStatus, false);
    }

    public void setVideoAdsStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_VideoAdsStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getSplashAdsStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_SplashAdsStatus, false);
    }

    public void setSplashAdsStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_SplashAdsStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getReturnAdsStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_ReturnAdsStatus, false);
    }

    public void setReturnAdsStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_ReturnAdsStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getPollfishStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_PollfishStatus, false);
    }

    /* access modifiers changed from: 0000 */
    public void setPollfishStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_PollfishStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getPollfishApiKey() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_PollfishApiKey, "");
    }

    /* access modifiers changed from: 0000 */
    public void setPollfishApiKey(String str) {
        if (str == null) {
            str = "";
        }
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_PollfishApiKey, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getAutoInterstitialStatus() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_AutoInterstitialStatus, false);
    }

    public void setAutoInterstitialStatus(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_AutoInterstitialStatus, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public String getAutoInterstitialType() {
        return this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_StappAdsAutoInterstitialType, "time");
    }

    public void setAutoInterstitialType(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_StappAdsAutoInterstitialType, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getAutoInterstitialValue() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_StappAdsAutoInterstitialValue, 60);
    }

    public void setAutoInterstitialValue(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_StappAdsAutoInterstitialValue, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getUnreadNotificationCount() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_NotificationCount, 0);
    }

    public void setUnreadNotificationCount(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_NotificationCount, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean getSelectLangOnStart() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_SelectLangOnStart, false);
    }

    /* access modifiers changed from: 0000 */
    public void setSelectLangOnStart(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_SelectLangOnStart, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getUserIsChatAdmin() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserIsChatAdmin, false);
    }

    public void setUserIsChatAdmin(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserIsChatAdmin, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getUserIsChatSuperUser() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserIsSuperUser, false);
    }

    public void setUserIsChatSuperUser(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserIsSuperUser, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getUserIsChatMod() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_UserIsChatMod, false);
    }

    public void setUserIsChatMod(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_UserIsChatMod, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private int getInterstitialTimeInterval() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_InterstitialTimeInterval, 60);
    }

    /* access modifiers changed from: 0000 */
    public void setInterstitialTimeInterval(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_InterstitialTimeInterval, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getInterstitialClickInterval() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_InterstitialClickInterval, 1);
    }

    /* access modifiers changed from: 0000 */
    public void setInterstitialClickInterval(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_InterstitialClickInterval, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getLoginLayoutType() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_InterstitialClickInterval, 2);
    }

    /* access modifiers changed from: 0000 */
    public void setLoginLayoutType(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_InterstitialClickInterval, i).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private long getDateDiff(Date date, Date date2, TimeUnit timeUnit) {
        return timeUnit.convert(date2.getTime() - date.getTime(), TimeUnit.MILLISECONDS);
    }

    public void setRadioBroadcastLink(String str) {
        try {
            this.appSharedPrefs.edit().putString("RadioLink", str).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRadioBroadcastLink() {
        return this.appSharedPrefs.getString("RadioLink", "");
    }

    public void setChatValidated(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsChatValidated, z).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getChatValidated() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsChatValidated, false);
    }

    public void setLocationPermissionAskCount(int i) {
        try {
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_Location_Permission_Count, i).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLocationPermissionAskCount() {
        return this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_Location_Permission_Count, 0);
    }

    public void setLocationPermission(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Location_Permission_Given, z).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLocationPermissionGiven() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Location_Permission_Given, false);
    }

    public void setStorageNeverAsk(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Storage_Never_Ask, z).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStorageNeverAsk() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Storage_Never_Ask, false);
    }

    public void setLocationNeverAsk(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Location_Never_Ask, z).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLocationNeverAsk() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Location_Never_Ask, false);
    }

    public void setUserLoginModel(UserLoginResponse userLoginResponse) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_User_Login_Model, new Gson().toJson((Object) userLoginResponse)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserLoginResponse getUserLoginModel() {
        try {
            return (UserLoginResponse) new Gson().fromJson(this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_User_Login_Model, null), UserLoginResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isFavoriteActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_Is_Favorite_Active, true);
    }

    public void setIsFavoriteActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_Is_Favorite_Active, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        GoogleSignInAccount googleSignInAccount;
        String string = this.appSharedPrefs.getString(Constants.MobiRoller_Preferences_Google_Sign_Account, null);
        try {
            Gson create = new GsonBuilder().registerTypeAdapter(Uri.class, new UriTypeHierarchyAdapter()).create();
            if (string == null) {
                googleSignInAccount = null;
            } else {
                googleSignInAccount = (GoogleSignInAccount) create.fromJson(string, GoogleSignInAccount.class);
            }
            return googleSignInAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        String str;
        try {
            Gson create = new GsonBuilder().registerTypeAdapter(Uri.class, new UriTypeHierarchyAdapter()).create();
            if (googleSignInAccount == null) {
                str = null;
            } else {
                str = create.toJson((Object) googleSignInAccount);
            }
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_Google_Sign_Account, str).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public ChannelDetailModel getYoutubeChannelInfo(String str) {
        SharedPreferences sharedPreferences = this.appSharedPrefs;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.MobiRoller_Preferences_Youtube_Channel_Detail);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str);
        String string = sharedPreferences.getString(sb.toString(), null);
        if (string == null) {
            return null;
        }
        return (ChannelDetailModel) new Gson().fromJson(string, ChannelDetailModel.class);
    }

    public void setYoutubeChannelInfo(String str, ChannelDetailModel channelDetailModel) {
        try {
            Editor edit = this.appSharedPrefs.edit();
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.MobiRoller_Preferences_Youtube_Channel_Detail);
            sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            sb.append(str);
            edit.putString(sb.toString(), new Gson().toJson((Object) channelDetailModel)).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void setGoogleSignInActive(boolean z) {
        try {
            this.appSharedPrefs.edit().putBoolean(Constants.MobiRoller_Preferences_IsGoogleSignInActive, z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getGoogleSignInActive() {
        return this.appSharedPrefs.getBoolean(Constants.MobiRoller_Preferences_IsGoogleSignInActive, false);
    }

    public void setYoutubeChannelSubscriptionStatus(String str, String str2, boolean z) {
        try {
            Editor edit = this.appSharedPrefs.edit();
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.MobiRoller_Preferences_IsYoutubeChannelSubscribed);
            sb.append(str);
            sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            sb.append(str2);
            edit.putBoolean(sb.toString(), z).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public boolean getYoutubeChannelSubscriptionStatus(String str, String str2) {
        SharedPreferences sharedPreferences = this.appSharedPrefs;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.MobiRoller_Preferences_IsYoutubeChannelSubscribed);
        sb.append(str);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str2);
        return sharedPreferences.getBoolean(sb.toString(), false);
    }

    public void setFCMToken(String str) {
        try {
            this.appSharedPrefs.edit().putString(Constants.MobiRoller_Preferences_FCM_Token, str).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMobiRollerBadgeY(float f, Activity activity) {
        float f2;
        int screenWidth;
        try {
            if (activity.getResources().getConfiguration().orientation == 1) {
                f2 = f * 100.0f;
                screenWidth = ScreenUtil.getScreenHeight();
            } else {
                f2 = f * 100.0f;
                screenWidth = ScreenUtil.getScreenWidth();
            }
            this.appSharedPrefs.edit().putInt(Constants.MobiRoller_Preferences_MobiRollerBadgeY, (int) (f2 / ((float) screenWidth))).apply();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public int getMobiRollerBadgeY(Activity activity) {
        int i;
        int i2;
        if (activity.getResources().getConfiguration().orientation == 1) {
            i = this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_MobiRollerBadgeY, 0);
            i2 = ScreenUtil.getScreenHeight();
        } else {
            i = this.appSharedPrefs.getInt(Constants.MobiRoller_Preferences_MobiRollerBadgeY, 0);
            i2 = ScreenUtil.getScreenWidth();
        }
        return (i * i2) / 100;
    }

    public void put(String str, String str2) {
        this.appSharedPrefs.edit().putString(str, str2).apply();
    }

    public void put(String str, int i) {
        this.appSharedPrefs.edit().putInt(str, i).apply();
    }

    public void put(String str, boolean z) {
        this.appSharedPrefs.edit().putBoolean(str, z).apply();
    }

    public void put(String str, float f) {
        this.appSharedPrefs.edit().putFloat(str, f).apply();
    }

    public void put(String str, double d) {
        this.appSharedPrefs.edit().putString(str, String.valueOf(d)).apply();
    }

    public void put(String str, long j) {
        this.appSharedPrefs.edit().putLong(str, j).apply();
    }

    public String getString(String str, String str2) {
        return this.appSharedPrefs.getString(str, str2);
    }

    public String getString(String str) {
        return this.appSharedPrefs.getString(str, null);
    }

    public int getInt(String str) {
        return this.appSharedPrefs.getInt(str, 0);
    }

    public int incrementInt(String str) {
        int i = this.appSharedPrefs.getInt(str, 0) + 1;
        put(str, i);
        return i;
    }

    public int getInt(String str, int i) {
        return this.appSharedPrefs.getInt(str, i);
    }

    public long getLong(String str) {
        return this.appSharedPrefs.getLong(str, 0);
    }

    public long getLong(String str, long j) {
        return this.appSharedPrefs.getLong(str, j);
    }

    public float getFloat(String str) {
        return this.appSharedPrefs.getFloat(str, 0.0f);
    }

    public float getFloat(String str, float f) {
        return this.appSharedPrefs.getFloat(str, f);
    }

    public double getDouble(String str) {
        return getDouble(str, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    }

    public double getDouble(String str, double d) {
        try {
            return Double.valueOf(this.appSharedPrefs.getString(str, String.valueOf(d))).doubleValue();
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public boolean getBoolean(String str, boolean z) {
        return this.appSharedPrefs.getBoolean(str, z);
    }

    public boolean getBoolean(String str) {
        return this.appSharedPrefs.getBoolean(str, false);
    }
}
