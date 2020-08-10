package com.mobiroller.module;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.scopes.PerFragment;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Activity activity;
    private Fragment fragment;

    public FragmentModule(Fragment fragment2, Activity activity2) {
        this.fragment = fragment2;
        this.activity = activity2;
    }

    /* access modifiers changed from: 0000 */
    @PerFragment
    @Provides
    public Activity providesAppCompatActivity() {
        return this.activity;
    }

    /* access modifiers changed from: 0000 */
    @PerFragment
    @Provides
    public LayoutHelper providesLayoutHelper(SharedPrefHelper sharedPrefHelper, Activity activity2, ApiRequestManager apiRequestManager) {
        return new LayoutHelper(sharedPrefHelper, activity2, apiRequestManager);
    }

    /* access modifiers changed from: 0000 */
    @PerFragment
    @Provides
    public MenuHelper providesMenuHelper(Activity activity2, NetworkHelper networkHelper, JSONParser jSONParser, SharedPrefHelper sharedPrefHelper, ApiRequestManager apiRequestManager, LocalizationHelper localizationHelper, MobiRollerApplication mobiRollerApplication, ScreenHelper screenHelper) {
        MenuHelper menuHelper = new MenuHelper(activity2, networkHelper, jSONParser, sharedPrefHelper, apiRequestManager, localizationHelper, mobiRollerApplication, screenHelper);
        return menuHelper;
    }
}
