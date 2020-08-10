package com.mobiroller.activities.user;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.tabs.TabLayout;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.user.UserAddressPagerAdapter;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import javax.inject.Inject;

public class UserAddressActivity extends AveActivity {
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363209)
    TabLayout tabLayout;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;
    UserAddressPagerAdapter userAddressPagerAdapter;
    @BindView(2131363382)
    ViewPager viewPager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_address);
        ButterKnife.bind((Activity) this);
        new ToolbarHelper().setStatusBar(this);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserAddressActivity.this.onBackPressed();
            }
        });
        if (UtilManager.networkHelper().isConnected()) {
            getSupportActionBar().setTitle((CharSequence) getString(R.string.user_my_address_title));
            this.userAddressPagerAdapter = new UserAddressPagerAdapter(this, getSupportFragmentManager());
            this.viewPager.setAdapter(this.userAddressPagerAdapter);
            this.tabLayout.setupWithViewPager(this.viewPager);
            this.tabLayout.setBackgroundColor(Theme.primaryColor);
            int i = -1;
            this.tabLayout.setTabTextColors(ColorStateList.valueOf(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK));
            TabLayout tabLayout2 = this.tabLayout;
            if (!ColorUtil.isColorDark(Theme.primaryColor)) {
                i = ViewCompat.MEASURED_STATE_MASK;
            }
            tabLayout2.setSelectedTabIndicatorColor(i);
            return;
        }
        DialogUtil.showNoConnectionError(this);
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }
}
