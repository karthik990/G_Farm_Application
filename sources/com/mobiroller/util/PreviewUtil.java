package com.mobiroller.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;
import androidx.browser.customtabs.CustomTabsIntent;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.mobiroller.activities.aveWebView;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.PreviewNotificationModel;
import com.mobiroller.models.WebContent;
import com.mobiroller.views.theme.Theme;

public class PreviewUtil {
    public static void showNotSupportedDialog(Context context) {
        new Builder(context).content((int) R.string.not_supported_on_preview).cancelable(false).positiveText((int) R.string.OK).show();
    }

    public static void openWebFromNotification(Activity activity) {
        if (activity.getIntent().hasExtra(Constants.MobiRoller_Preview_Notification_Model)) {
            PreviewNotificationModel previewNotificationModel = (PreviewNotificationModel) activity.getIntent().getSerializableExtra(Constants.MobiRoller_Preview_Notification_Model);
            if (!URLUtil.isValidUrl(previewNotificationModel.webUrl)) {
                return;
            }
            if (previewNotificationModel.external) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Theme.primaryColor);
                builder.setShowTitle(true);
                builder.addDefaultShareMenuItem();
                builder.build().launchUrl(activity, Uri.parse(previewNotificationModel.webUrl));
                return;
            }
            WebContent webContent = new WebContent(UtilManager.localizationHelper().getLocalizedTitle(previewNotificationModel.title), UtilManager.localizationHelper().getLocalizedTitle(previewNotificationModel.title), true);
            Intent intent = new Intent(activity, aveWebView.class);
            intent.putExtra("webContent", webContent);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
    }
}
