package com.mobiroller.models;

import java.io.Serializable;

public class MainModel implements Serializable {
    private String GATrackingId;
    private String adMobBannerCode;
    private String adMobCode;
    private String adMobNativeCode;
    private String adType;
    private boolean askBeforeExit;
    private int autoIntAdClickInterval;
    private boolean autoIntAdStatus;
    private int autoIntAdTimeInterval;
    private boolean autoInterstitialStatus;
    private String autoInterstitialType;
    private int autoInterstitialValue;
    public String badgeInfo;
    private String defaultLanguage;
    public String ecommerceScreenID;
    private IntroModel introMessage;
    private boolean isAdEnabled;
    public boolean isBadgeActive;
    private boolean isBannerAdEnabled;
    private boolean isBleshActive;
    private boolean isChatActive;
    public boolean isECommerceActive;
    private boolean isFavoriteActive;
    public Boolean isInAppPurchaseActive;
    private boolean isNativeAdEnabled;
    private boolean isPollServiceEnabled;
    private boolean isTrialExpired;
    private String localeCodes;
    private ImageModel loginBackgroundImage;
    private int loginLayoutType;
    public LoginProviderModel loginProviders;
    private ImageModel logoImage;
    private ColorModel navBarTintColor;
    private String pollServiceKey;
    private ColorModel progressAnimationColor;
    private int progressAnimationType;
    private boolean rateApp;
    private boolean returnAdsStatus;
    private Boolean selectLangOnStart;
    private boolean splashAdsStatus;
    private ImageModel splashImage;
    private ImageModel tabBarBackgroundImageName;
    private boolean thirdPartyAdsService;
    private String updateDate;
    public UserAgreementModel userAgreementForm;
    private boolean videoAdsStatus;

    public IntroModel getIntroMessage() {
        return this.introMessage;
    }

    public void setIntroMessage(IntroModel introModel) {
        this.introMessage = introModel;
    }

    public boolean isPollServiceEnabled() {
        return this.isPollServiceEnabled;
    }

    public void setPollServiceEnabled(boolean z) {
        this.isPollServiceEnabled = z;
    }

    public String getPollServiceKey() {
        return this.pollServiceKey;
    }

    public void setPollServiceKey(String str) {
        this.pollServiceKey = str;
    }

    public int getLoginLayoutType() {
        return this.loginLayoutType;
    }

    public void setLoginLayoutType(int i) {
        this.loginLayoutType = i;
    }

    public boolean isChatActive() {
        return this.isChatActive;
    }

    public void setChatActive(boolean z) {
        this.isChatActive = z;
    }

    public ImageModel getLoginBackgroundImage() {
        return this.loginBackgroundImage;
    }

    public void setLoginBackgroundImage(ImageModel imageModel) {
        this.loginBackgroundImage = imageModel;
    }

    public Boolean getSelectLangOnStart() {
        return this.selectLangOnStart;
    }

    public void setSelectLangOnStart(Boolean bool) {
        this.selectLangOnStart = bool;
    }

    public boolean isAutoIntAdStatus() {
        return this.autoIntAdStatus;
    }

    public boolean getInterstitialMultipleDisplayEnabled() {
        return this.autoIntAdStatus;
    }

    public void setAutoIntAdStatus(boolean z) {
        this.autoIntAdStatus = z;
    }

    public int getAutoIntAdTimeInterval() {
        return this.autoIntAdTimeInterval;
    }

    public void setAutoIntAdTimeInterval(int i) {
        this.autoIntAdTimeInterval = i;
    }

    public int getAutoIntAdClickInterval() {
        return this.autoIntAdClickInterval;
    }

    public void setAutoIntAdClickInterval(int i) {
        this.autoIntAdClickInterval = i;
    }

    public Boolean isSelectLangOnStart() {
        Boolean bool = this.selectLangOnStart;
        return bool == null ? Boolean.valueOf(false) : bool;
    }

    public void setSelectLangOnStart(boolean z) {
        this.selectLangOnStart = Boolean.valueOf(z);
    }

    public boolean isNativeAdEnabled() {
        return this.isNativeAdEnabled;
    }

    public void setNativeAdEnabled(boolean z) {
        this.isNativeAdEnabled = z;
    }

    public String getAdMobNativeCode() {
        return this.adMobNativeCode;
    }

    public void setAdMobNativeCode(String str) {
        this.adMobNativeCode = str;
    }

    public boolean isAskBeforeExit() {
        return this.askBeforeExit;
    }

    public void setAskBeforeExit(boolean z) {
        this.askBeforeExit = z;
    }

    public boolean isRateApp() {
        return this.rateApp;
    }

    public void setRateApp(boolean z) {
        this.rateApp = z;
    }

    public Boolean isBleshServiceActive() {
        return Boolean.valueOf(this.isBleshActive);
    }

    public void setBleshServiceStatus(boolean z) {
        this.isBleshActive = z;
    }

    public ImageModel getTabBarBackgroundImageName() {
        return this.tabBarBackgroundImageName;
    }

    public void setTabBarBackgroundImageName(ImageModel imageModel) {
        this.tabBarBackgroundImageName = imageModel;
    }

    public ImageModel getSplashImage() {
        return this.splashImage;
    }

    public void setSplashImage(ImageModel imageModel) {
        this.splashImage = imageModel;
    }

    public ImageModel getLogoImage() {
        return this.logoImage;
    }

    public void setLogoImage(ImageModel imageModel) {
        this.logoImage = imageModel;
    }

    public ColorModel getNavBarTintColor() {
        return this.navBarTintColor;
    }

    public void setNavBarTintColor(ColorModel colorModel) {
        this.navBarTintColor = colorModel;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public String getLocaleCodes() {
        return this.localeCodes;
    }

    public void setLocaleCodes(String str) {
        this.localeCodes = str;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public void setDefaultLanguage(String str) {
        this.defaultLanguage = str;
    }

    public String getGATrackingId() {
        return this.GATrackingId;
    }

    public void setGATrackingId(String str) {
        this.GATrackingId = str;
    }

    public String getAdMobCode() {
        return this.adMobCode;
    }

    public void setAdMobCode(String str) {
        this.adMobCode = str;
    }

    public String getAdMobBannerCode() {
        return this.adMobBannerCode;
    }

    public void setAdMobBannerCode(String str) {
        this.adMobBannerCode = str;
    }

    public String getAdType() {
        return this.adType;
    }

    public void setAdType(String str) {
        this.adType = str;
    }

    public boolean isAdEnabled() {
        return this.isAdEnabled;
    }

    public void setAdEnabled(boolean z) {
        this.isAdEnabled = z;
    }

    public boolean isBannerAdEnabled() {
        return this.isBannerAdEnabled;
    }

    public void setBannerAdEnabled(boolean z) {
        this.isBannerAdEnabled = z;
    }

    public boolean isTrialExpired() {
        return this.isTrialExpired;
    }

    public void setTrialExpired(boolean z) {
        this.isTrialExpired = z;
    }

    public int getProgressAnimationType() {
        return this.progressAnimationType;
    }

    public void setProgressAnimationType(int i) {
        this.progressAnimationType = i;
    }

    public ColorModel getProgressAnimationColor() {
        return this.progressAnimationColor;
    }

    public void setProgressAnimationColor(ColorModel colorModel) {
        this.progressAnimationColor = colorModel;
    }

    public Boolean isThirdPartyAdsService() {
        return Boolean.valueOf(this.thirdPartyAdsService);
    }

    public void setThirdPartyAdsService(boolean z) {
        this.thirdPartyAdsService = z;
    }

    public Boolean isVideoAdsEnabled() {
        return Boolean.valueOf(this.videoAdsStatus);
    }

    public void setVideoAdsStatus(boolean z) {
        this.videoAdsStatus = z;
    }

    public Boolean isSplashAdsEnabled() {
        return Boolean.valueOf(this.splashAdsStatus);
    }

    public void setSplashAdsStatus(boolean z) {
        this.splashAdsStatus = z;
    }

    public Boolean isReturnAdsEnabled() {
        return Boolean.valueOf(this.returnAdsStatus);
    }

    public void setReturnAdsStatus(boolean z) {
        this.returnAdsStatus = z;
    }

    public Boolean isAutoInterstitialEnabled() {
        return Boolean.valueOf(this.autoInterstitialStatus);
    }

    public void setAutoInterstitialStatus(boolean z) {
        this.autoInterstitialStatus = z;
    }

    public String getAutoInterstitialType() {
        return this.autoInterstitialType;
    }

    public void setAutoInterstitialType(String str) {
        this.autoInterstitialType = str;
    }

    public int getAutoInterstitialValue() {
        return this.autoInterstitialValue;
    }

    public void setAutoInterstitialValue(int i) {
        this.autoInterstitialValue = i;
    }

    public boolean isFavoriteActive() {
        return this.isFavoriteActive;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MainModel{, tabBarBackgroundImageName=");
        sb.append(this.tabBarBackgroundImageName);
        sb.append(", splashImage=");
        sb.append(this.splashImage);
        sb.append(", logoImage=");
        sb.append(this.logoImage);
        sb.append(", loginBackgroundImage=");
        sb.append(this.loginBackgroundImage);
        sb.append(", navBarTintColor=");
        sb.append(this.navBarTintColor);
        sb.append(", updateDate='");
        sb.append(this.updateDate);
        sb.append('\'');
        sb.append(", localeCodes='");
        sb.append(this.localeCodes);
        sb.append('\'');
        sb.append(", defaultLanguage='");
        sb.append(this.defaultLanguage);
        sb.append('\'');
        sb.append(", GATrackingId='");
        sb.append(this.GATrackingId);
        sb.append('\'');
        sb.append(", adMobCode='");
        sb.append(this.adMobCode);
        sb.append('\'');
        sb.append(", adMobBannerCode='");
        sb.append(this.adMobBannerCode);
        sb.append('\'');
        sb.append(", adMobNativeCode='");
        sb.append(this.adMobNativeCode);
        sb.append('\'');
        sb.append(", adType='");
        sb.append(this.adType);
        sb.append('\'');
        sb.append(", isAdEnabled=");
        sb.append(this.isAdEnabled);
        sb.append(", isBannerAdEnabled=");
        sb.append(this.isBannerAdEnabled);
        sb.append(", isNativeAdEnabled=");
        sb.append(this.isNativeAdEnabled);
        sb.append(", isTrialExpired=");
        sb.append(this.isTrialExpired);
        sb.append(", progressAnimationType=");
        sb.append(this.progressAnimationType);
        sb.append(", progressAnimationColor=");
        sb.append(this.progressAnimationColor);
        sb.append(", askBeforeExit=");
        sb.append(this.askBeforeExit);
        sb.append(", rateApp=");
        sb.append(this.rateApp);
        sb.append(", selectLangOnStart=");
        sb.append(this.selectLangOnStart);
        sb.append(", autoIntAdTimeInterval=");
        sb.append(this.autoIntAdTimeInterval);
        sb.append(", autoIntAdClickInterval=");
        sb.append(this.autoIntAdClickInterval);
        sb.append(", autoIntAdStatus=");
        sb.append(this.autoIntAdStatus);
        sb.append(", thirdPartyAdsService=");
        sb.append(this.thirdPartyAdsService);
        sb.append(", videoAdsStatus=");
        sb.append(this.videoAdsStatus);
        sb.append(", splashAdsStatus=");
        sb.append(this.splashAdsStatus);
        sb.append(", returnAdsStatus=");
        sb.append(this.returnAdsStatus);
        sb.append(", autoInterstitialStatus=");
        sb.append(this.autoInterstitialStatus);
        sb.append(", autoInterstitialType='");
        sb.append(this.autoInterstitialType);
        sb.append('\'');
        sb.append(", autoInterstitialValue=");
        sb.append(this.autoInterstitialValue);
        sb.append(", isBleshActive=");
        sb.append(this.isBleshActive);
        sb.append(", loginLayoutType=");
        sb.append(this.loginLayoutType);
        sb.append(", isPollServiceEnabled=");
        sb.append(this.isPollServiceEnabled);
        sb.append(", pollServiceKey='");
        sb.append(this.pollServiceKey);
        sb.append('\'');
        sb.append(", introMessage=");
        sb.append(this.introMessage);
        sb.append('}');
        return sb.toString();
    }
}
