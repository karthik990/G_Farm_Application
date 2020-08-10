package com.mobiroller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.AppBarLayout;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.BackHandledFragment;
import com.mobiroller.fragments.BackHandledFragment.BackHandlerInterface;
import com.mobiroller.fragments.avePDFViewFragment;
import com.mobiroller.helpers.FragmentHandlerHelper;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.ShowToolbarEvent;
import com.mobiroller.views.custom.MobirollerToolbar;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GenericActivity extends AveActivity implements BackHandlerInterface {
    Fragment fragment;
    @Inject
    LocalizationHelper localizationHelper;
    MobirollerToolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;

    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.toolbar = (MobirollerToolbar) findViewById(R.id.toolbar_top);
        setMobirollerToolbar(this.toolbar);
        this.mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        if (this.screenModel != null) {
            this.toolbarHelper.setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
        } else {
            this.toolbarHelper.setToolbarTitle(this, "");
        }
        String str = "pdf_url";
        if (getIntent().hasExtra(str)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(str, getIntent().getStringExtra(str));
            this.fragment = new avePDFViewFragment();
            this.fragment.setArguments(bundle2);
            setFragment(this.fragment, "Fragment_");
            return;
        }
        Bundle bundle3 = new Bundle();
        this.toolbarHelper.setStatusBar(this);
        JSONStorage.putScreenModel(this.screenId, this.screenModel);
        bundle3.putString(Constants.KEY_SCREEN_TYPE, this.screenType);
        bundle3.putString(Constants.KEY_SUB_SCREEN_TYPE, this.subScreenType);
        bundle3.putString(Constants.KEY_SCREEN_ID, this.screenId);
        this.fragment = FragmentHandlerHelper.getFragment(this.screenType, this.subScreenType);
        this.fragment.setArguments(bundle3);
        Fragment fragment2 = this.fragment;
        StringBuilder sb = new StringBuilder();
        sb.append(this.screenType);
        sb.append("_Fragment_");
        sb.append(this.screenId);
        setFragment(fragment2, sb.toString());
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.fragment.onActivityResult(i, i2, intent);
    }

    @Subscribe
    public void onPostShowToolbarEvent(ShowToolbarEvent showToolbarEvent) {
        this.toolbar.setVisibility(0);
        setSupportActionBar(this.toolbar);
        this.toolbarHelper.setStatusBar(this);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GenericActivity.this.finish();
            }
        });
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.fragment.onRequestPermissionsResult(i, strArr, iArr);
    }
}
