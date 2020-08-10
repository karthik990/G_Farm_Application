package com.mobiroller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import javax.inject.Inject;
import org.apache.http.protocol.HTTP;

public class aveShareView extends AveActivity {
    private String contentText;
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    private String url;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.screenModel = JSONStorage.getScreenModel(this.screenId);
        if (this.screenModel.getContentText() != null) {
            this.contentText = this.localizationHelper.getLocalizedTitle(this.screenModel.getContentText());
            this.url = this.screenModel.getGooglePlayLink();
            shareApp();
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.common_error), 0).show();
        finish();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void shareApp() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        String str = "";
        if (this.url == null) {
            this.url = str;
        }
        if (this.contentText == null) {
            this.contentText = str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.contentText);
        sb.append("  ");
        sb.append(this.url);
        intent.putExtra("android.intent.extra.TEXT", sb.toString());
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
        finish();
    }
}
