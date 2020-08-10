package com.mobiroller.activities.base;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.helpers.LocaleHelper;

public class ECommerceBaseActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleHelper.setLocale(context));
    }
}
