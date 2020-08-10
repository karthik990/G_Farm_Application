package com.mobiroller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.aveWebViewFragment;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.WebContent;
import com.mobiroller.util.ShareUtil;
import com.mobiroller.views.custom.MobirollerToolbar;
import javax.inject.Inject;

public class aveWebView extends AveActivity {
    private boolean isPreviewSignUp = false;
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    private WebContent webContent;
    private aveWebViewFragment webViewFragment;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        ButterKnife.bind((Activity) this);
        setToolbar();
        setMobirollerToolbar((MobirollerToolbar) findViewById(R.id.toolbar_top));
        new ToolbarHelper().setStatusBar(this);
        String str = "isPreviewSignUp";
        if (getIntent().hasExtra(str)) {
            this.isPreviewSignUp = true;
        }
        if (getIntent().hasExtra(Constants.NOTIFICATION_TYPE_WEB_CONTENT)) {
            this.webContent = (WebContent) getIntent().getSerializableExtra(Constants.NOTIFICATION_TYPE_WEB_CONTENT);
        }
        Bundle bundle2 = new Bundle();
        if (this.screenId != null && this.screenModel != null) {
            JSONStorage.putScreenModel(this.screenId, this.screenModel);
            bundle2.putString(Constants.KEY_SCREEN_TYPE, this.screenType);
            bundle2.putString(Constants.KEY_SCREEN_ID, this.screenId);
        } else if (this.webContent != null) {
            bundle2.putSerializable(Constants.NOTIFICATION_TYPE_WEB_CONTENT, this.webContent);
        }
        bundle2.putBoolean(str, this.isPreviewSignUp);
        this.webViewFragment = new aveWebViewFragment();
        this.webViewFragment.setArguments(bundle2);
        setFragment(this.webViewFragment, "webViewFragment");
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_share) {
            if (this.screenModel != null) {
                ShareUtil.shareURL(this, this.screenModel.getTitle(), this.screenModel.getURL());
            } else {
                WebContent webContent2 = this.webContent;
                if (webContent2 != null) {
                    ShareUtil.shareURL(this, webContent2.title, this.webContent.url);
                }
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.screenModel != null) {
            return this.screenModel.isRssContent();
        }
        WebContent webContent2 = this.webContent;
        if (webContent2 != null) {
            return webContent2.shareable;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0012, code lost:
        if (r2.shareable != false) goto L_0x0014;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onPrepareOptionsMenu(android.view.Menu r2) {
        /*
            r1 = this;
            com.mobiroller.models.ScreenModel r2 = r1.screenModel
            if (r2 == 0) goto L_0x000c
            com.mobiroller.models.ScreenModel r2 = r1.screenModel
            boolean r2 = r2.isRssContent()
            if (r2 != 0) goto L_0x0014
        L_0x000c:
            com.mobiroller.models.WebContent r2 = r1.webContent
            if (r2 == 0) goto L_0x001e
            boolean r2 = r2.shareable
            if (r2 == 0) goto L_0x001e
        L_0x0014:
            com.mobiroller.views.custom.MobirollerToolbar r2 = r1.getToolbar()
            r0 = 2131623954(0x7f0e0012, float:1.8875074E38)
            r2.inflateMenu(r0)
        L_0x001e:
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.activities.aveWebView.onPrepareOptionsMenu(android.view.Menu):boolean");
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        aveWebViewFragment avewebviewfragment = this.webViewFragment;
        if (avewebviewfragment != null) {
            avewebviewfragment.onActivityResult(i, i2, intent);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        if (this.screenModel != null) {
            if (this.screenModel.isRssContent()) {
                this.toolbarHelper.setToolbarTitle(this, this.screenModel.getTitle());
                toolbar.setNavigationIcon((int) R.drawable.ic_clear_white_24dp);
            } else if (this.screenModel.isHideToolbar()) {
                toolbar.setVisibility(8);
            } else {
                this.toolbarHelper.setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
                toolbar.setNavigationOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        aveWebView.this.onBackPressed();
                    }
                });
            }
        } else if (this.webContent != null) {
            this.toolbarHelper.setToolbarTitle(this, this.screenModel.getTitle());
            toolbar.setNavigationIcon((int) R.drawable.ic_clear_white_24dp);
        } else {
            this.toolbarHelper.setToolbarTitle(this, "");
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.webViewFragment.onRequestPermissionsResult(i, strArr, iArr);
    }
}
