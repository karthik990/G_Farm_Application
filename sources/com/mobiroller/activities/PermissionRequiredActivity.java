package com.mobiroller.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import javax.inject.Inject;

public class PermissionRequiredActivity extends AveActivity {
    @BindView(2131362917)
    TextView description;
    @BindView(2131362186)
    LinearLayout mainLayout;
    @BindView(2131362916)
    Button permissionCheck;
    @Inject
    ScreenHelper screenHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131363299)
    Button try_again;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.permission_required_layout);
        ButterKnife.bind((Activity) this);
        this.toolbarHelper.setToolbarTitlePermission(this, "");
        setBackgroundColor();
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.description.setText(Html.fromHtml(getString(R.string.permission_required_description, new Object[]{getString(R.string.app_name)})));
    }

    public void setBackgroundColor() {
        this.mainLayout.setBackground(ScreenHelper.getGradientBackground(Color.parseColor("#517fff"), 0.0f, 0.5f));
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    @OnClick({2131362916})
    public void openPermissionSettings() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @OnClick({2131363299})
    public void restartApp() {
        Intent launchIntentForPackage = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        launchIntentForPackage.addFlags(67108864);
        startActivity(launchIntentForPackage);
        finish();
    }
}
