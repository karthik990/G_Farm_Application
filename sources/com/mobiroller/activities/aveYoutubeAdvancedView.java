package com.mobiroller.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.aveYoutubeAdvancedViewFragment;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import javax.inject.Inject;

public class aveYoutubeAdvancedView extends AveActivity {
    public static final String KEY_FROM_YOUTUBE_ACTIVITY = "fromYouTubeActivity";
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        findViewById(R.id.toolbar_top).setVisibility(8);
        if (this.screenModel != null) {
            this.toolbarHelper.setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
        } else {
            this.toolbarHelper.setToolbarTitle(this, "");
        }
        Bundle bundle2 = new Bundle();
        JSONStorage.putScreenModel(this.screenId, this.screenModel);
        bundle2.putString(Constants.KEY_SCREEN_TYPE, this.screenType);
        bundle2.putString(Constants.KEY_SCREEN_ID, this.screenId);
        bundle2.putBoolean(KEY_FROM_YOUTUBE_ACTIVITY, true);
        aveYoutubeAdvancedViewFragment aveyoutubeadvancedviewfragment = new aveYoutubeAdvancedViewFragment();
        aveyoutubeadvancedviewfragment.setArguments(bundle2);
        StringBuilder sb = new StringBuilder();
        sb.append(this.screenType);
        sb.append("_Fragment_");
        sb.append(this.screenId);
        setFragment(aveyoutubeadvancedviewfragment, sb.toString());
        invalidateOptionsMenu();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }
}
