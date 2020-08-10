package com.mobiroller.activities.preview;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.MapUserLocationEvent;
import org.greenrobot.eventbus.EventBus;
import permissions.dispatcher.PermissionRequest;

public class LocationPermissionActivity extends AppCompatActivity {
    public static String sMapUserLocation = "MapUserLocation";
    private boolean mMapUserLocation;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().hasExtra(sMapUserLocation)) {
            this.mMapUserLocation = true;
        }
        LocationPermissionActivityPermissionsDispatcher.askLocationPermissionWithPermissionCheck(this);
    }

    public void askLocationPermission() {
        if (this.mMapUserLocation) {
            EventBus.getDefault().post(new MapUserLocationEvent());
        }
        finish();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        LocationPermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    public void showRationaleForStorage(PermissionRequest permissionRequest) {
        permissionRequest.proceed();
    }

    public void showDeniedForStorage() {
        finish();
        if (this.mMapUserLocation) {
            Toast.makeText(this, R.string.permission_location_denied_map_user_location, 0).show();
        }
    }

    public void showNeverAskForStorage() {
        finish();
        if (this.mMapUserLocation) {
            Toast.makeText(this, R.string.permission_location_denied_map_user_location_never, 0).show();
        }
    }
}
