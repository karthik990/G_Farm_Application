package com.mobiroller.activities;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;

public class AuthorizationActivity extends AveActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.authorization_view);
        ButterKnife.bind((Activity) this);
        setToolbarContext();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public void setToolbarContext() {
        ToolbarHelper.setToolbar(this, (Toolbar) findViewById(R.id.toolbar_top));
        getSupportActionBar().setTitle((CharSequence) "Twitter");
    }
}
