package com.mobiroller.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.aveRSSViewFragment;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;
import javax.inject.Inject;

public class aveRSSView extends AveActivity {
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        setMobirollerToolbar((MobirollerToolbar) findViewById(R.id.toolbar_top));
        if (this.screenModel != null) {
            this.toolbarHelper.setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
        } else {
            this.toolbarHelper.setToolbarTitle(this, "");
        }
        Bundle bundle2 = new Bundle();
        JSONStorage.putScreenModel(this.screenId, this.screenModel);
        bundle2.putString(Constants.KEY_SCREEN_TYPE, this.screenType);
        bundle2.putString(Constants.KEY_SCREEN_ID, this.screenId);
        Intent intent = getIntent();
        String str = Constants.KEY_SCREEN_RSS_PUSH_TITLE;
        if (intent.hasExtra(str)) {
            bundle2.putString(str, getIntent().getStringExtra(str));
        }
        aveRSSViewFragment averssviewfragment = new aveRSSViewFragment();
        averssviewfragment.setArguments(bundle2);
        setFragment(averssviewfragment, "rssFragment");
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }
}
