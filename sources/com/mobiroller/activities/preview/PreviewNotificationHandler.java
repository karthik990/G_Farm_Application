package com.mobiroller.activities.preview;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.SplashApp;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.util.PreviewUtil;

public class PreviewNotificationHandler extends AppCompatActivity {
    LocalizationHelper localizationHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new SharedPrefHelper(this);
        this.localizationHelper = new LocalizationHelper();
        if (MobiRollerApplication.isAppKilled()) {
            Intent intent = new Intent(this, SplashApp.class);
            if (getIntent().hasExtra(Constants.MobiRoller_Preview_Notification_Model)) {
                intent.putExtra(Constants.MobiRoller_Preview_Notification_Model, getIntent().getSerializableExtra(Constants.MobiRoller_Preview_Notification_Model));
            }
            startActivity(intent);
            finish();
            return;
        }
        PreviewUtil.openWebFromNotification(this);
        finish();
    }
}
