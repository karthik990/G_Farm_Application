package p043io.fabric.sdk.android.services.common;

import android.content.Context;
import android.text.TextUtils;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.services.persistence.PreferenceStore;
import p043io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

/* renamed from: io.fabric.sdk.android.services.common.AdvertisingInfoProvider */
class AdvertisingInfoProvider {
    private static final String ADVERTISING_INFO_PREFERENCES = "TwitterAdvertisingInfoPreferences";
    private static final String PREFKEY_ADVERTISING_ID = "advertising_id";
    private static final String PREFKEY_LIMIT_AD_TRACKING = "limit_ad_tracking_enabled";
    private final Context context;
    private final PreferenceStore preferenceStore;

    public AdvertisingInfoProvider(Context context2) {
        this.context = context2.getApplicationContext();
        this.preferenceStore = new PreferenceStoreImpl(context2, ADVERTISING_INFO_PREFERENCES);
    }

    public AdvertisingInfo getAdvertisingInfo() {
        AdvertisingInfo infoFromPreferences = getInfoFromPreferences();
        if (isInfoValid(infoFromPreferences)) {
            Fabric.getLogger().mo64074d(Fabric.TAG, "Using AdvertisingInfo from Preference Store");
            refreshInfoIfNeededAsync(infoFromPreferences);
            return infoFromPreferences;
        }
        AdvertisingInfo advertisingInfoFromStrategies = getAdvertisingInfoFromStrategies();
        storeInfoToPreferences(advertisingInfoFromStrategies);
        return advertisingInfoFromStrategies;
    }

    private void refreshInfoIfNeededAsync(final AdvertisingInfo advertisingInfo) {
        new Thread(new BackgroundPriorityRunnable() {
            public void onRun() {
                AdvertisingInfo access$000 = AdvertisingInfoProvider.this.getAdvertisingInfoFromStrategies();
                if (!advertisingInfo.equals(access$000)) {
                    Fabric.getLogger().mo64074d(Fabric.TAG, "Asychronously getting Advertising Info and storing it to preferences");
                    AdvertisingInfoProvider.this.storeInfoToPreferences(access$000);
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void storeInfoToPreferences(AdvertisingInfo advertisingInfo) {
        boolean isInfoValid = isInfoValid(advertisingInfo);
        String str = PREFKEY_LIMIT_AD_TRACKING;
        String str2 = PREFKEY_ADVERTISING_ID;
        if (isInfoValid) {
            PreferenceStore preferenceStore2 = this.preferenceStore;
            preferenceStore2.save(preferenceStore2.edit().putString(str2, advertisingInfo.advertisingId).putBoolean(str, advertisingInfo.limitAdTrackingEnabled));
            return;
        }
        PreferenceStore preferenceStore3 = this.preferenceStore;
        preferenceStore3.save(preferenceStore3.edit().remove(str2).remove(str));
    }

    /* access modifiers changed from: protected */
    public AdvertisingInfo getInfoFromPreferences() {
        return new AdvertisingInfo(this.preferenceStore.get().getString(PREFKEY_ADVERTISING_ID, ""), this.preferenceStore.get().getBoolean(PREFKEY_LIMIT_AD_TRACKING, false));
    }

    public AdvertisingInfoStrategy getReflectionStrategy() {
        return new AdvertisingInfoReflectionStrategy(this.context);
    }

    public AdvertisingInfoStrategy getServiceStrategy() {
        return new AdvertisingInfoServiceStrategy(this.context);
    }

    private boolean isInfoValid(AdvertisingInfo advertisingInfo) {
        return advertisingInfo != null && !TextUtils.isEmpty(advertisingInfo.advertisingId);
    }

    /* access modifiers changed from: private */
    public AdvertisingInfo getAdvertisingInfoFromStrategies() {
        AdvertisingInfo advertisingInfo = getReflectionStrategy().getAdvertisingInfo();
        boolean isInfoValid = isInfoValid(advertisingInfo);
        String str = Fabric.TAG;
        if (!isInfoValid) {
            advertisingInfo = getServiceStrategy().getAdvertisingInfo();
            if (!isInfoValid(advertisingInfo)) {
                Fabric.getLogger().mo64074d(str, "AdvertisingInfo not present");
            } else {
                Fabric.getLogger().mo64074d(str, "Using AdvertisingInfo from Service Provider");
            }
        } else {
            Fabric.getLogger().mo64074d(str, "Using AdvertisingInfo from Reflection Provider");
        }
        return advertisingInfo;
    }
}
