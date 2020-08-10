package com.mobiroller.module;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.scopes.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity appCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity2) {
        this.appCompatActivity = appCompatActivity2;
    }

    /* access modifiers changed from: 0000 */
    @PerActivity
    @Provides
    public Context providesApplicationContext() {
        return this.appCompatActivity;
    }

    /* access modifiers changed from: 0000 */
    @PerActivity
    @Provides
    public Activity providesAppCompatActivity() {
        return this.appCompatActivity;
    }

    /* access modifiers changed from: 0000 */
    @PerActivity
    @Provides
    public LayoutHelper providesLayoutHelper(SharedPrefHelper sharedPrefHelper, Activity activity, ApiRequestManager apiRequestManager) {
        return new LayoutHelper(sharedPrefHelper, activity, apiRequestManager);
    }

    /* access modifiers changed from: 0000 */
    @PerActivity
    @Provides
    public MenuHelper providesMenuHelper(Activity activity, NetworkHelper networkHelper, JSONParser jSONParser, SharedPrefHelper sharedPrefHelper, ApiRequestManager apiRequestManager, LocalizationHelper localizationHelper, MobiRollerApplication mobiRollerApplication, ScreenHelper screenHelper) {
        MenuHelper menuHelper = new MenuHelper(activity, networkHelper, jSONParser, sharedPrefHelper, apiRequestManager, localizationHelper, mobiRollerApplication, screenHelper);
        return menuHelper;
    }
}
