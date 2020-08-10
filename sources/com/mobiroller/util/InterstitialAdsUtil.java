package com.mobiroller.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.ConnectionRequired;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.AdManager.InterstitialAdCallBack;
import java.util.Date;

public class InterstitialAdsUtil {
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public FragmentManager fragmentManager;
    private NetworkHelper networkHelper = UtilManager.networkHelper();
    private SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();

    public InterstitialAdsUtil(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
        this.fragmentManager = appCompatActivity.getSupportFragmentManager();
    }

    public InterstitialAdsUtil(Activity activity2) {
        this.activity = activity2;
        this.fragmentManager = ((AppCompatActivity) activity2).getSupportFragmentManager();
    }

    private void increaseClickCount() {
        SharedPrefHelper sharedPrefHelper2 = this.sharedPrefHelper;
        sharedPrefHelper2.setInterstitialClickCount(sharedPrefHelper2.getInterstitialClickCount() + 1);
    }

    public void checkInterstitialAds(Intent intent) {
        increaseClickCount();
        if (checkConditions()) {
            loadInterstitialAds(intent);
        } else if (this.networkHelper.isConnected() || !checkConnectionRequired(intent)) {
            checkFirstAd(intent, null);
        } else {
            Activity activity2 = this.activity;
            activity2.startActivity(new Intent(activity2, ConnectionRequired.class).putExtra("intent", intent));
        }
    }

    private void checkFirstAd(final Intent intent, final Bundle bundle) {
        final AdManager instance = AdManager.getInstance();
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (!instance.isInterstitialAdReady() || MobiRollerApplication.isInterstitialShown) {
                    InterstitialAdsUtil.this.startActivity(intent, bundle);
                } else {
                    instance.showInterstitialAd(new InterstitialAdCallBack() {
                        public void onAdClosed() {
                            InterstitialAdsUtil.this.startActivity(intent, bundle);
                        }
                    });
                }
            }
        });
    }

    private void checkFirstAdFragment(final Fragment fragment) {
        final AdManager instance = AdManager.getInstance();
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (!instance.isInterstitialAdReady() || MobiRollerApplication.isInterstitialShown) {
                    InterstitialAdsUtil.this.fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commitAllowingStateLoss();
                } else {
                    instance.showInterstitialAd(new InterstitialAdCallBack() {
                        public void onAdClosed() {
                            InterstitialAdsUtil.this.fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commitAllowingStateLoss();
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void startActivity(Intent intent, Bundle bundle) {
        if (bundle != null) {
            this.activity.startActivity(intent, bundle);
        } else {
            this.activity.startActivity(intent);
        }
    }

    public void checkInterstitialAds(Intent intent, Bundle bundle) {
        increaseClickCount();
        if (checkConditions()) {
            loadInterstitialAds(intent, bundle);
        } else if (this.networkHelper.isConnected() || !checkConnectionRequired(intent)) {
            checkFirstAd(intent, bundle);
        } else {
            Activity activity2 = this.activity;
            activity2.startActivity(new Intent(activity2, ConnectionRequired.class).putExtra("intent", intent));
        }
    }

    public void checkInterstitialAds(Fragment fragment) {
        increaseClickCount();
        if (checkConditions()) {
            loadInterstitialAds(fragment);
        } else {
            checkFirstAdFragment(fragment);
        }
    }

    public boolean checkInterstitialAds(Fragment fragment, ScreenModel screenModel, String str, String str2, String str3, String str4) {
        increaseClickCount();
        if (checkConditions()) {
            loadInterstitialAds(fragment);
            return true;
        } else if (this.networkHelper.isConnected() || !checkConnectionRequired(str2)) {
            checkFirstAdFragment(fragment);
            return true;
        } else {
            Intent intent = new Intent(this.activity, ConnectionRequired.class);
            intent.putExtra(Constants.KEY_SCREEN_ID, str);
            if (screenModel != null) {
                JSONStorage.putScreenModel(str, screenModel);
            }
            intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
            intent.putExtra(Constants.KEY_SUB_SCREEN_TYPE, str3);
            intent.putExtra(Constants.KEY_UPDATE_DATE, str4);
            this.activity.startActivity(intent);
            return false;
        }
    }

    public boolean checkInterstitialAds(Fragment fragment, ScreenModel screenModel, NavigationItemModel navigationItemModel) {
        increaseClickCount();
        if (checkConditions()) {
            loadInterstitialAds(fragment);
            return true;
        } else if (this.networkHelper.isConnected() || !checkConnectionRequired(navigationItemModel.getScreenType())) {
            checkFirstAdFragment(fragment);
            return true;
        } else {
            Intent intent = new Intent(this.activity, ConnectionRequired.class);
            intent.putExtra(Constants.KEY_SCREEN_ID, navigationItemModel.getAccountScreenID());
            if (screenModel != null) {
                JSONStorage.putScreenModel(navigationItemModel.getAccountScreenID(), screenModel);
            }
            intent.putExtra(Constants.KEY_SCREEN_TYPE, navigationItemModel.getScreenType());
            intent.putExtra(Constants.KEY_SUB_SCREEN_TYPE, navigationItemModel.screenSubtype);
            intent.putExtra(Constants.KEY_UPDATE_DATE, navigationItemModel.getUpdateDate());
            this.activity.startActivity(intent);
            return false;
        }
    }

    private void loadInterstitialAds(final Intent intent) {
        final AdManager instance = AdManager.getInstance();
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (instance.isInterstitialAdReady()) {
                    instance.showInterstitialAd(new InterstitialAdCallBack() {
                        public void onAdClosed() {
                            InterstitialAdsUtil.this.activity.startActivity(intent);
                        }
                    });
                    return;
                }
                instance.createInterstitialAd();
                InterstitialAdsUtil.this.activity.startActivity(intent);
            }
        });
    }

    private void loadInterstitialAds(final Intent intent, final Bundle bundle) {
        final AdManager instance = AdManager.getInstance();
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (instance.isInterstitialAdReady()) {
                    instance.showInterstitialAd(new InterstitialAdCallBack() {
                        public void onAdClosed() {
                            InterstitialAdsUtil.this.activity.startActivity(intent, bundle);
                        }
                    });
                    return;
                }
                instance.createInterstitialAd();
                InterstitialAdsUtil.this.activity.startActivity(intent, bundle);
            }
        });
    }

    private void loadInterstitialAds(final Fragment fragment) {
        final AdManager instance = AdManager.getInstance();
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (instance.isInterstitialAdReady()) {
                    instance.showInterstitialAd(new InterstitialAdCallBack() {
                        public void onAdClosed() {
                            InterstitialAdsUtil.this.fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commitAllowingStateLoss();
                        }
                    });
                    return;
                }
                instance.createInterstitialAd();
                InterstitialAdsUtil.this.fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commitAllowingStateLoss();
            }
        });
    }

    private boolean checkConditions() {
        Log.e("checkConditions 1", String.valueOf(this.networkHelper.isConnected()));
        Log.e("checkConditions 2", String.valueOf(!DynamicConstants.MobiRoller_Stage));
        Log.e("checkConditions 3", String.valueOf(this.sharedPrefHelper.getIsAdmobInterstitialAdEnabled()));
        Log.e("checkConditions 4", String.valueOf(this.sharedPrefHelper.getInterstitialClickCount() >= this.sharedPrefHelper.getInterstitialClickInterval()));
        Log.e("checkConditions 5", String.valueOf(this.sharedPrefHelper.getInterstitialTimer(new Date())));
        Log.e("checkConditions 6", String.valueOf(this.sharedPrefHelper.getInterstitialMultipleDisplayEnabled()));
        Log.e("checkConditions 7", String.valueOf(MobiRollerApplication.isInterstitialShown));
        Log.e("checkConditions", "==========================");
        if (!this.networkHelper.isConnected() || DynamicConstants.MobiRoller_Stage || !this.sharedPrefHelper.getIsAdmobInterstitialAdEnabled() || this.sharedPrefHelper.getInterstitialClickCount() < this.sharedPrefHelper.getInterstitialClickInterval() || !this.sharedPrefHelper.getInterstitialTimer(new Date()) || !this.sharedPrefHelper.getInterstitialMultipleDisplayEnabled() || !MobiRollerApplication.isInterstitialShown) {
            return false;
        }
        return true;
    }

    private boolean checkConnectionRequired(Intent intent) {
        String[] stringArray;
        if (intent.getAction() != null && intent.getAction().equalsIgnoreCase("android.intent.action.DIAL")) {
            return false;
        }
        for (String str : this.activity.getResources().getStringArray(R.array.connection_required_activities)) {
            StringBuilder sb = new StringBuilder();
            sb.append("com.mobiroller.activities.");
            sb.append(str);
            if (sb.toString().equalsIgnoreCase(intent.getComponent().getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkConnectionRequired(String str) {
        for (String equalsIgnoreCase : this.activity.getResources().getStringArray(R.array.connection_required_activities)) {
            if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
