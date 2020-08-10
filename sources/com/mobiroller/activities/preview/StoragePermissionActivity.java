package com.mobiroller.activities.preview;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.ScreenshotEvent;
import org.greenrobot.eventbus.EventBus;
import permissions.dispatcher.PermissionRequest;

public class StoragePermissionActivity extends AppCompatActivity {
    public static String sScreenshot = "Screenshot";
    private boolean mIsScreenshot;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().hasExtra(sScreenshot)) {
            this.mIsScreenshot = true;
        }
        StoragePermissionActivityPermissionsDispatcher.askLocationPermissionWithPermissionCheck(this);
    }

    public void askLocationPermission() {
        if (this.mIsScreenshot) {
            EventBus.getDefault().post(new ScreenshotEvent());
        }
        finish();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        StoragePermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    public void showRationaleForStorage(PermissionRequest permissionRequest) {
        permissionRequest.proceed();
    }

    public void showDeniedForStorage() {
        finish();
        if (this.mIsScreenshot) {
            Toast.makeText(this, R.string.permission_storage_denied_ss, 0).show();
        }
    }

    public void showNeverAskForStorage() {
        finish();
        if (this.mIsScreenshot) {
            Toast.makeText(this, R.string.permission_storage_denied_ss_never, 0).show();
        }
    }
}
