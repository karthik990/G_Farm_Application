package com.mobiroller.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.snackbar.Snackbar;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import java.util.ArrayList;
import javax.inject.Inject;

public class ConnectionRequired extends AveActivity {
    private boolean hasScreenModel = true;
    private boolean hideTryAgain;
    private Intent intent;
    @Inject
    JSONParser jsonParser;
    @BindView(2131362186)
    LinearLayout mainLayout;
    @BindView(2131362741)
    Button mobileCheck;
    @Inject
    NetworkHelper networkHelper;
    private ProgressViewHelper progressViewHelper;
    private ArrayList<String> roles = new ArrayList<>();
    @Inject
    ScreenHelper screenHelper;
    private String screenId;
    private String screenType;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    /* access modifiers changed from: private */
    public Snackbar snackbar;
    private String subScreenType;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131363299)
    Button try_again;
    private String updateDate;
    @BindView(2131363404)
    Button wifiCheck;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.connection_required_layout);
        ButterKnife.bind((Activity) this);
        this.toolbarHelper.setToolbarTitle(this, "");
        setBackgroundColor();
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        String str = "intent";
        if (getIntent().hasExtra(str)) {
            this.intent = (Intent) getIntent().getParcelableExtra(str);
        } else {
            this.hasScreenModel = false;
        }
        Intent intent2 = getIntent();
        String str2 = Constants.KEY_SCREEN_ID;
        if (intent2.hasExtra(str2)) {
            this.screenId = getIntent().getStringExtra(str2);
        }
        Intent intent3 = getIntent();
        String str3 = Constants.KEY_SCREEN_TYPE;
        if (intent3.hasExtra(str3)) {
            this.screenType = getIntent().getStringExtra(str3);
        }
        Intent intent4 = getIntent();
        String str4 = Constants.KEY_SUB_SCREEN_TYPE;
        if (intent4.hasExtra(str4)) {
            this.subScreenType = getIntent().getStringExtra(str4);
        }
        Intent intent5 = getIntent();
        String str5 = Constants.KEY_UPDATE_DATE;
        if (intent5.hasExtra(str5)) {
            this.updateDate = getIntent().getStringExtra(str5);
        }
        String str6 = "roles";
        if (getIntent().hasExtra(str6)) {
            this.roles = getIntent().getStringArrayListExtra(str6);
        }
        if (this.intent == null && this.screenId == null) {
            this.hideTryAgain = true;
        }
        if (this.hideTryAgain) {
            this.try_again.setVisibility(8);
        }
        this.snackbar = Snackbar.make((View) this.mainLayout, (CharSequence) getString(R.string.connection_required_error), -1).setAction((CharSequence) getString(R.string.OK), (OnClickListener) new OnClickListener() {
            public void onClick(View view) {
                ConnectionRequired.this.snackbar.dismiss();
            }
        });
    }

    public void setBackgroundColor() {
        this.mainLayout.setBackground(ScreenHelper.getGradientBackground(this.sharedPrefHelper.getActionBarColor(), 0.0f, 0.5f));
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    @OnClick({2131363299})
    public void tryAgain() {
        if (this.networkHelper.isConnected()) {
            if (this.hasScreenModel) {
                Intent intent2 = this.intent;
                if (intent2 != null) {
                    startActivity(intent2);
                    finish();
                    return;
                }
            }
            Intent intent3 = getIntent();
            String str = Constants.KEY_SCREEN_RSS_PUSH_TITLE;
            if (intent3.hasExtra(str)) {
                ActivityHandler.startActivity((Context) this, this.screenId, this.screenType, this.subScreenType, this.updateDate, this.roles, getIntent().getStringExtra(str));
                return;
            }
            ActivityHandler.startActivity(this, this.screenId, this.screenType, this.subScreenType, this.updateDate, this.roles);
            return;
        }
        this.snackbar.show();
    }

    @OnClick({2131363404})
    public void openWifiSettings() {
        startActivity(new Intent("android.settings.WIFI_SETTINGS"));
    }

    @OnClick({2131362741})
    public void openMobileSettings() {
        startActivity(new Intent("android.settings.SETTINGS"));
    }
}
