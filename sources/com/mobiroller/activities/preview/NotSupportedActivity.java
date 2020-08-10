package com.mobiroller.activities.preview;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.preview.NotSupportedFragment;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.mobi942763453128.R;

public class NotSupportedActivity extends AppCompatActivity {
    LocalizationHelper localizationHelper;
    private String mScreenType;
    SharedPrefHelper sharedPrefHelper;
    ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        ButterKnife.bind((Activity) this);
        this.sharedPrefHelper = new SharedPrefHelper(this);
        this.toolbarHelper = new ToolbarHelper(this.sharedPrefHelper);
        this.localizationHelper = new LocalizationHelper();
        this.toolbarHelper.setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(getIntent().getStringExtra("title")));
        this.mScreenType = getIntent().getStringExtra(Constants.KEY_SCREEN_TYPE);
        NotSupportedFragment notSupportedFragment = new NotSupportedFragment();
        if (this.mScreenType.equalsIgnoreCase("aveChatView")) {
            notSupportedFragment.setModule(R.string.chat_module);
        } else if (this.mScreenType.equalsIgnoreCase("aveECommerceView")) {
            notSupportedFragment.setModule(R.string.ecommerce_module);
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_container, notSupportedFragment, "notSupported");
        beginTransaction.commit();
    }
}
