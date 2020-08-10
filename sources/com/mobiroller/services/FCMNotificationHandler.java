package com.mobiroller.services;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.SplashApp;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.models.FCMNotificationModel.Action;
import com.mobiroller.util.NotificationActionUtil;
import javax.inject.Inject;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import p101me.leolin.shortcutbadger.ShortcutBadger;

public class FCMNotificationHandler extends AveActivity {
    private Action action;
    private String content;
    @Inject
    MenuHelper menuHelper;
    private String notificationId;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private String title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        cleanNotificationCount();
        getIntentValues();
        dismissNotification();
        boolean isAppBackground = MobiRollerApplication.isAppBackground();
        String str = SettingsJsonConstants.APP_KEY;
        if (isAppBackground) {
            try {
                startActivity(getActionIntent());
                finish();
            } catch (ActivityNotFoundException unused) {
                Action action2 = this.action;
                if (!(action2 == null || action2.getType() == null || !this.action.getType().equals(str))) {
                    startActivity(NotificationActionUtil.getAppIntentBrowser(this.action));
                    finish();
                }
            }
        } else {
            try {
                Intent actionIntent = NotificationActionUtil.getActionIntent(this, this.action, this.title, this.content);
                if (actionIntent != null) {
                    startActivity(actionIntent);
                }
                finish();
            } catch (ActivityNotFoundException unused2) {
                Action action3 = this.action;
                if (!(action3 == null || action3.getType() == null || !this.action.getType().equals(str))) {
                    startActivity(NotificationActionUtil.getAppIntentBrowser(this.action));
                    finish();
                }
            }
        }
        finish();
    }

    private void dismissNotification() {
        if (this.notificationId != null) {
            ((NotificationManager) getSystemService("notification")).cancel(this.notificationId.hashCode());
        }
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void getIntentValues() {
        String str = "action";
        if (getIntent().hasExtra(str)) {
            this.action = (Action) getIntent().getSerializableExtra(str);
        }
        this.title = getIntent().getStringExtra("title");
        this.content = getIntent().getStringExtra(Constants.NOTIFICATION_CONTENT);
        this.notificationId = getIntent().getStringExtra("notificationId");
    }

    private Intent getSplashIntent() {
        Intent intent = new Intent(this, SplashApp.class);
        intent.putExtra("action", this.action);
        intent.putExtra("title", this.title);
        intent.putExtra(Constants.NOTIFICATION_CONTENT, this.content);
        return intent;
    }

    private void cleanNotificationCount() {
        try {
            this.sharedPrefHelper.setUnreadNotificationCount(0);
            if (ShortcutBadger.isBadgeCounterSupported(this)) {
                ShortcutBadger.removeCount(this);
            }
        } catch (Exception unused) {
        }
    }

    private Intent getActionIntent() {
        Action action2 = this.action;
        if (action2 == null || action2.getType() == null) {
            return getSplashIntent();
        }
        if (!NotificationActionUtil.isExternal(this.action)) {
            return getSplashIntent();
        }
        Intent actionIntent = NotificationActionUtil.getActionIntent(this, this.action, this.title, this.content);
        if (actionIntent == null) {
            actionIntent = getSplashIntent();
        }
        return actionIntent;
    }
}
