package com.mobiroller.activities;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.aveCallNowViewFragment;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import javax.inject.Inject;

public class aveCallNowView extends AveActivity {
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        ButterKnife.bind((Activity) this);
        if (this.screenModel != null) {
            this.toolbarHelper.setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
        } else {
            this.toolbarHelper.setToolbarTitle(this, "");
        }
        Bundle bundle2 = new Bundle();
        JSONStorage.putScreenModel(this.screenId, this.screenModel);
        bundle2.putString(Constants.KEY_SCREEN_TYPE, this.screenType);
        bundle2.putString(Constants.KEY_SCREEN_ID, this.screenId);
        aveCallNowViewFragment avecallnowviewfragment = new aveCallNowViewFragment();
        avecallnowviewfragment.setArguments(bundle2);
        setFragment(avecallnowviewfragment, "callNowViewFragment");
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }
}
