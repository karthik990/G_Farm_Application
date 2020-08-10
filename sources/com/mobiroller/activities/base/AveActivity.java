package com.mobiroller.activities.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.appbar.AppBarLayout;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.FontSizeHelper;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.interfaces.DaggerActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.events.MobirollerBadgeEvent;
import com.mobiroller.models.events.VolumeButtonEvent;
import com.mobiroller.module.ActivityModule;
import com.mobiroller.util.AnalyticsUtil;
import com.mobiroller.views.MobiRollerBadgeView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class AveActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    public AppBarLayout mAppBarLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    private MobirollerToolbar mToolbar;
    public ProgressViewHelper progressViewHelper;
    public String screenId;
    public ScreenModel screenModel;
    public String screenType;
    public String subScreenType;

    public abstract AppCompatActivity injectActivity(ActivityComponent activityComponent);

    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        if (Theme.primaryColor == 0) {
            Theme.primaryColor = UtilManager.sharedPrefHelper().getActionBarColor();
        }
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        if (!getClass().getSimpleName().equalsIgnoreCase("aveRssContentViewPager")) {
            getTheme().applyStyle(new FontSizeHelper(this).getFontStyle().getResId(), true);
        } else if (new FontSizeHelper(this).getContentFontOrder() != -1) {
            getTheme().applyStyle(new FontSizeHelper(this).getContentFontStyle().getResId(), true);
        } else {
            getTheme().applyStyle(new FontSizeHelper(this).getFontStyle().getResId(), true);
        }
        super.onCreate(bundle);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        injectActivity(activityComponent());
        Intent intent = getIntent();
        String str = Constants.KEY_SCREEN_ID;
        if (intent.hasExtra(str)) {
            this.screenId = getIntent().getStringExtra(str);
        }
        Intent intent2 = getIntent();
        String str2 = Constants.KEY_SCREEN_TYPE;
        if (intent2.hasExtra(str2)) {
            this.screenType = getIntent().getStringExtra(str2);
        }
        Intent intent3 = getIntent();
        String str3 = Constants.KEY_SUB_SCREEN_TYPE;
        if (intent3.hasExtra(str3)) {
            this.subScreenType = getIntent().getStringExtra(str3);
        }
        String str4 = this.screenId;
        if (str4 != null && JSONStorage.containsScreen(str4)) {
            this.screenModel = JSONStorage.getScreenModel(this.screenId);
        }
        checkStats();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        addMobiRollerBadge();
    }

    /* access modifiers changed from: protected */
    public final ActivityComponent activityComponent() {
        if (this.mActivityComponent == null) {
            this.mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).appComponent(MobiRollerApplication.getMainComponent()).build();
        }
        return this.mActivityComponent;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (EventBus.getDefault().hasSubscriberForEvent(VolumeButtonEvent.class)) {
            EventBus.getDefault().post(new VolumeButtonEvent(keyEvent, i));
        }
        if (i == 4) {
            ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
            if (progressViewHelper2 != null) {
                progressViewHelper2.dismiss();
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
        if (progressViewHelper2 != null) {
            progressViewHelper2.dismiss();
        }
    }

    public void setFragment(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_container, fragment, str);
        beginTransaction.commit();
    }

    public void closeKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: protected */
    public void checkStats() {
        try {
            if (this.screenId != null && this.screenModel != null) {
                AnalyticsUtil.sendStats(this, this.screenModel, this.screenType);
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    @Subscribe
    public void onPostMobirollerBadgeEvent(MobirollerBadgeEvent mobirollerBadgeEvent) {
        addMobiRollerBadge();
    }

    private void addMobiRollerBadge() {
        if (!UtilManager.sharedPrefHelper().getBoolean(Constants.MobiRoller_Preferences_Show_MobiRoller_Badge) || !isValidActivityForBadge()) {
            MobiRollerBadgeView.removeView(this);
        } else {
            MobiRollerBadgeView.addView(this);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (UtilManager.sharedPrefHelper().getBoolean(Constants.MobiRoller_Preferences_Show_MobiRoller_Badge) && isValidActivityForBadge()) {
            MobiRollerBadgeView.addView(this);
        }
    }

    private boolean isValidActivityForBadge() {
        boolean z = false;
        if (!UtilManager.sharedPrefHelper().getBoolean(Constants.MobiRoller_Preferences_MobiRoller_Badge_Check_Response, false)) {
            return false;
        }
        if (getClass().getSimpleName().equalsIgnoreCase("SplashApp")) {
            if (UtilManager.sharedPrefHelper().getInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Total_Count) < UtilManager.sharedPrefHelper().getInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Count)) {
                UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_Count, 0);
            }
            UtilManager.sharedPrefHelper().incrementInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Count);
            return false;
        } else if (UtilManager.sharedPrefHelper().getBoolean(Constants.MobiRoller_Preferences_MobiRoller_Badge_First_Open, true)) {
            UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_First_Open, false);
            return true;
        } else if (UtilManager.sharedPrefHelper().getInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Total_Count) == 0) {
            return true;
        } else {
            if (UtilManager.sharedPrefHelper().getInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Total_Count) == UtilManager.sharedPrefHelper().getInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Count)) {
                z = true;
            }
            return z;
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.setLocale(context));
    }

    public void setMobirollerToolbar(MobirollerToolbar mobirollerToolbar) {
        this.mToolbar = mobirollerToolbar;
    }

    public MobirollerToolbar getToolbar() {
        return this.mToolbar;
    }

    public AppBarLayout getAppBarLayout() {
        return this.mAppBarLayout;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return this.mDrawerToggle;
    }
}
