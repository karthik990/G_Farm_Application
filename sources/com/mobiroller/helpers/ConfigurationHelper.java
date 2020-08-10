package com.mobiroller.helpers;

import android.content.Context;
import com.mobiroller.constants.Constants;
import com.mobiroller.models.MainModel;

public class ConfigurationHelper {
    Context context;
    ScreenHelper screenHelper;
    SharedPrefHelper sharedPrefHelper;

    public ConfigurationHelper(SharedPrefHelper sharedPrefHelper2, Context context2, ScreenHelper screenHelper2) {
        this.sharedPrefHelper = sharedPrefHelper2;
        this.context = context2;
        this.screenHelper = screenHelper2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x01dd A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01ec A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0205 A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x021d A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0247 A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f8 A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0114 A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0130 A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0173 A[Catch:{ Exception -> 0x017d }] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x018d A[Catch:{ Exception -> 0x02fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01b5 A[Catch:{ Exception -> 0x02fc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAppSettings(com.mobiroller.models.MainModel r6) {
        /*
            r5 = this;
            java.lang.String r0 = r6.ecommerceScreenID     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0008
            java.lang.String r0 = r6.ecommerceScreenID     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.constants.Constants.E_COMMERCE_SCREEN_ID = r0     // Catch:{ Exception -> 0x02fc }
        L_0x0008:
            boolean r0 = r6.isECommerceActive     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.AppSettingsHelper.setIsECommerceActive(r0)     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.SharedPrefHelper r0 = com.mobiroller.helpers.UtilManager.sharedPrefHelper()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = com.mobiroller.constants.Constants.MobiRoller_Preferences_Show_MobiRoller_Badge     // Catch:{ Exception -> 0x02fc }
            boolean r2 = r6.isBadgeActive     // Catch:{ Exception -> 0x02fc }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r6.isBadgeActive     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0025
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = com.mobiroller.constants.Constants.MobiRoller_Preferences_MobiRoller_Badge_Url     // Catch:{ Exception -> 0x02fc }
            java.lang.String r2 = r6.badgeInfo     // Catch:{ Exception -> 0x02fc }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x02fc }
        L_0x0025:
            com.mobiroller.models.ImageModel r0 = r6.getLogoImage()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = ""
            if (r0 == 0) goto L_0x003b
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.models.ImageModel r2 = r6.getLogoImage()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r2 = r2.getImageURL()     // Catch:{ Exception -> 0x02fc }
            r0.setLogoURL(r2)     // Catch:{ Exception -> 0x02fc }
            goto L_0x0040
        L_0x003b:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setLogoURL(r1)     // Catch:{ Exception -> 0x02fc }
        L_0x0040:
            com.mobiroller.models.ImageModel r0 = r6.getLoginBackgroundImage()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0054
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.models.ImageModel r1 = r6.getLoginBackgroundImage()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = r1.getImageURL()     // Catch:{ Exception -> 0x02fc }
            r0.setLoginBackgroundURL(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x0059
        L_0x0054:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setLoginBackgroundURL(r1)     // Catch:{ Exception -> 0x02fc }
        L_0x0059:
            com.mobiroller.models.LoginProviderModel r0 = r6.loginProviders     // Catch:{ Exception -> 0x02fc }
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0071
            com.mobiroller.models.LoginProviderModel r0 = r6.loginProviders     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.google     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x006b
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setGoogleSignInActive(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x0076
        L_0x006b:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setGoogleSignInActive(r2)     // Catch:{ Exception -> 0x02fc }
            goto L_0x0076
        L_0x0071:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setGoogleSignInActive(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x0076:
            com.mobiroller.models.UserAgreementModel r0 = r6.userAgreementForm     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0099
            com.mobiroller.models.UserAgreementModel r0 = r6.userAgreementForm     // Catch:{ Exception -> 0x02fc }
            java.lang.Boolean r0 = r0.isActive     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0099
            com.mobiroller.models.UserAgreementModel r0 = r6.userAgreementForm     // Catch:{ Exception -> 0x02fc }
            java.lang.Boolean r0 = r0.isActive     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0099
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsUserAgremeentActive(r1)     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.models.UserAgreementModel r3 = r6.userAgreementForm     // Catch:{ Exception -> 0x02fc }
            java.lang.String r3 = r3.url     // Catch:{ Exception -> 0x02fc }
            r0.setUserAgremeent(r3)     // Catch:{ Exception -> 0x02fc }
            goto L_0x009e
        L_0x0099:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsUserAgremeentActive(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x009e:
            boolean r0 = com.mobiroller.DynamicConstants.MobiRoller_Stage     // Catch:{ Exception -> 0x02fc }
            java.lang.String r3 = "null"
            if (r0 != 0) goto L_0x0252
            java.lang.Boolean r0 = r6.isBleshServiceActive()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x00ba
            java.lang.Boolean r0 = r6.isBleshServiceActive()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x00ba
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setBleshServiceStatus(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x00bf
        L_0x00ba:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setBleshServiceStatus(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x00bf:
            java.lang.Boolean r0 = r6.isThirdPartyAdsService()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0182
            java.lang.Boolean r0 = r6.isThirdPartyAdsService()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x00d1
            goto L_0x0182
        L_0x00d1:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setThirdPartyAdsStatus(r1)     // Catch:{ Exception -> 0x02fc }
            java.lang.Boolean r0 = r6.isVideoAdsEnabled()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x00ed
            java.lang.Boolean r0 = r6.isVideoAdsEnabled()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x00e7
            goto L_0x00ed
        L_0x00e7:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setVideoAdsStatus(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x00f2
        L_0x00ed:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setVideoAdsStatus(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x00f2:
            java.lang.Boolean r0 = r6.isSplashAdsEnabled()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0109
            java.lang.Boolean r0 = r6.isSplashAdsEnabled()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x0103
            goto L_0x0109
        L_0x0103:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setSplashAdsStatus(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x010e
        L_0x0109:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setSplashAdsStatus(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x010e:
            java.lang.Boolean r0 = r6.isReturnAdsEnabled()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0125
            java.lang.Boolean r0 = r6.isReturnAdsEnabled()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x011f
            goto L_0x0125
        L_0x011f:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setReturnAdsStatus(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x012a
        L_0x0125:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setReturnAdsStatus(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x012a:
            java.lang.Boolean r0 = r6.isAutoInterstitialEnabled()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0141
            java.lang.Boolean r0 = r6.isAutoInterstitialEnabled()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x013b
            goto L_0x0141
        L_0x013b:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setAutoInterstitialStatus(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x0146
        L_0x0141:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setAutoInterstitialStatus(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x0146:
            java.lang.String r0 = r6.getAutoInterstitialType()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x016d
            java.lang.String r0 = r6.getAutoInterstitialType()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = "time"
            boolean r0 = r0.equals(r4)     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x0164
            java.lang.String r0 = r6.getAutoInterstitialType()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = "activities"
            boolean r0 = r0.equals(r4)     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x016d
        L_0x0164:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = r6.getAutoInterstitialType()     // Catch:{ Exception -> 0x02fc }
            r0.setAutoInterstitialType(r4)     // Catch:{ Exception -> 0x02fc }
        L_0x016d:
            int r0 = r6.getAutoInterstitialValue()     // Catch:{ Exception -> 0x017d }
            if (r0 == 0) goto L_0x0187
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x017d }
            int r4 = r6.getAutoInterstitialValue()     // Catch:{ Exception -> 0x017d }
            r0.setAutoInterstitialValue(r4)     // Catch:{ Exception -> 0x017d }
            goto L_0x0187
        L_0x017d:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x02fc }
            goto L_0x0187
        L_0x0182:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setThirdPartyAdsStatus(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x0187:
            java.lang.String r0 = r6.getAdMobCode()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x01aa
            java.lang.String r0 = r6.getAdMobCode()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.equals(r3)     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x01aa
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = r6.getAdMobCode()     // Catch:{ Exception -> 0x02fc }
            r0.setAdUnitID(r4)     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r4 = r6.isAdEnabled()     // Catch:{ Exception -> 0x02fc }
            r0.setIsAdmobInterstitialAdEnabled(r4)     // Catch:{ Exception -> 0x02fc }
            goto L_0x01af
        L_0x01aa:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsAdmobInterstitialAdEnabled(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x01af:
            java.lang.String r0 = r6.getAdMobBannerCode()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x01d2
            java.lang.String r0 = r6.getAdMobBannerCode()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.equals(r3)     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x01d2
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = r6.getAdMobBannerCode()     // Catch:{ Exception -> 0x02fc }
            r0.setBannerAdUnitID(r4)     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r4 = r6.isBannerAdEnabled()     // Catch:{ Exception -> 0x02fc }
            r0.setIsBannerAdEnabled(r4)     // Catch:{ Exception -> 0x02fc }
            goto L_0x01d7
        L_0x01d2:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsBannerAdEnabled(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x01d7:
            int r0 = r6.getAutoIntAdClickInterval()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x01e6
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            int r4 = r6.getAutoIntAdClickInterval()     // Catch:{ Exception -> 0x02fc }
            r0.setInterstitialClickInterval(r4)     // Catch:{ Exception -> 0x02fc }
        L_0x01e6:
            int r0 = r6.getAutoIntAdTimeInterval()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x01f5
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            int r4 = r6.getAutoIntAdTimeInterval()     // Catch:{ Exception -> 0x02fc }
            r0.setInterstitialTimeInterval(r4)     // Catch:{ Exception -> 0x02fc }
        L_0x01f5:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.util.Date r4 = new java.util.Date     // Catch:{ Exception -> 0x02fc }
            r4.<init>()     // Catch:{ Exception -> 0x02fc }
            r0.setInterstitialTimer(r4)     // Catch:{ Exception -> 0x02fc }
            int r0 = r6.getLoginLayoutType()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x020e
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            int r4 = r6.getLoginLayoutType()     // Catch:{ Exception -> 0x02fc }
            r0.setLoginLayoutType(r4)     // Catch:{ Exception -> 0x02fc }
        L_0x020e:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r4 = r6.getInterstitialMultipleDisplayEnabled()     // Catch:{ Exception -> 0x02fc }
            r0.setInterstitialMultipleDisplayEnabled(r4)     // Catch:{ Exception -> 0x02fc }
            java.lang.String r0 = r6.getAdMobNativeCode()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x023a
            java.lang.String r0 = r6.getAdMobNativeCode()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.equals(r3)     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x023a
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = r6.getAdMobNativeCode()     // Catch:{ Exception -> 0x02fc }
            r0.setNativeAddUnitID(r4)     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r4 = r6.isNativeAdEnabled()     // Catch:{ Exception -> 0x02fc }
            r0.setIsNativeAdEnabled(r4)     // Catch:{ Exception -> 0x02fc }
            goto L_0x023f
        L_0x023a:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsNativeAdEnabled(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x023f:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.getIsNativeAdEnabled()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0252
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.String r0 = r0.getNativeAddUnitID()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r4 = "ca-"
            r0.startsWith(r4)     // Catch:{ Exception -> 0x02fc }
        L_0x0252:
            com.mobiroller.models.ColorModel r0 = r6.getNavBarTintColor()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0271
            com.mobiroller.models.ColorModel r0 = r6.getNavBarTintColor()     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.equals(r3)     // Catch:{ Exception -> 0x02fc }
            if (r0 != 0) goto L_0x0271
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.ScreenHelper r3 = r5.screenHelper     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.models.ColorModel r3 = r6.getNavBarTintColor()     // Catch:{ Exception -> 0x02fc }
            int r3 = com.mobiroller.helpers.ScreenHelper.setColorUnselected(r3)     // Catch:{ Exception -> 0x02fc }
            r0.setActionBarColor(r3)     // Catch:{ Exception -> 0x02fc }
        L_0x0271:
            boolean r0 = r6.isChatActive()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x027d
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsChatActive(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x0282
        L_0x027d:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsChatActive(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x0282:
            java.lang.Boolean r0 = r6.isInAppPurchaseActive     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0297
            java.lang.Boolean r0 = r6.isInAppPurchaseActive     // Catch:{ Exception -> 0x02fc }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0297
            r5.clearInAppPurchaseCache()     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsInAppPurchaseActive(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x029c
        L_0x0297:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsInAppPurchaseActive(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x029c:
            boolean r0 = r6.isFavoriteActive()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x02a8
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsFavoriteActive(r1)     // Catch:{ Exception -> 0x02fc }
            goto L_0x02ad
        L_0x02a8:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            r0.setIsFavoriteActive(r2)     // Catch:{ Exception -> 0x02fc }
        L_0x02ad:
            int r0 = r6.getProgressAnimationType()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x02bc
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            int r1 = r6.getProgressAnimationType()     // Catch:{ Exception -> 0x02fc }
            r0.setProgressAnimationType(r1)     // Catch:{ Exception -> 0x02fc }
        L_0x02bc:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r1 = r6.isAskBeforeExit()     // Catch:{ Exception -> 0x02fc }
            r0.setAskBeforeExit(r1)     // Catch:{ Exception -> 0x02fc }
            java.lang.Boolean r0 = r6.isSelectLangOnStart()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x02d8
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            java.lang.Boolean r1 = r6.isSelectLangOnStart()     // Catch:{ Exception -> 0x02fc }
            boolean r1 = r1.booleanValue()     // Catch:{ Exception -> 0x02fc }
            r0.setSelectLangOnStart(r1)     // Catch:{ Exception -> 0x02fc }
        L_0x02d8:
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02fc }
            boolean r1 = r6.isRateApp()     // Catch:{ Exception -> 0x02fc }
            r0.setRateApp(r1)     // Catch:{ Exception -> 0x02fc }
            com.mobiroller.models.ColorModel r0 = r6.getProgressAnimationColor()     // Catch:{ Exception -> 0x02fc }
            if (r0 == 0) goto L_0x0300
            com.mobiroller.helpers.ScreenHelper r0 = r5.screenHelper     // Catch:{ Exception -> 0x02f7 }
            com.mobiroller.models.ColorModel r6 = r6.getProgressAnimationColor()     // Catch:{ Exception -> 0x02f7 }
            int r6 = com.mobiroller.helpers.ScreenHelper.setColorUnselected(r6)     // Catch:{ Exception -> 0x02f7 }
            com.mobiroller.helpers.SharedPrefHelper r0 = r5.sharedPrefHelper     // Catch:{ Exception -> 0x02f7 }
            r0.setProgressAnimationColor(r6)     // Catch:{ Exception -> 0x02f7 }
            goto L_0x0300
        L_0x02f7:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ Exception -> 0x02fc }
            goto L_0x0300
        L_0x02fc:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0300:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.ConfigurationHelper.setAppSettings(com.mobiroller.models.MainModel):void");
    }

    public void setAppLanguage(MainModel mainModel) {
        try {
            this.sharedPrefHelper.setDefaultLang(mainModel.getDefaultLanguage());
            this.sharedPrefHelper.setLocaleCodes(mainModel.getLocaleCodes());
            LocaleHelper.setLocale(this.context);
        } catch (Exception e) {
            e.printStackTrace();
            String str = "tr";
            this.sharedPrefHelper.setDefaultLang(str);
            this.sharedPrefHelper.setLocaleCodes(str);
            if (!UtilManager.sharedPrefHelper().getLanguageSetByUser()) {
                UtilManager.sharedPrefHelper().put(Constants.DISPLAY_LANGUAGE, this.sharedPrefHelper.getDefaultLang().toLowerCase());
            }
        }
    }

    private void clearInAppPurchaseCache() {
        Context context2 = this.context;
        StringBuilder sb = new StringBuilder();
        sb.append(this.context.getPackageName());
        sb.append("_preferences");
        context2.getSharedPreferences(sb.toString(), 0).edit().clear().apply();
    }
}
