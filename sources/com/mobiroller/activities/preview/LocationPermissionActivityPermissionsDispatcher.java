package com.mobiroller.activities.preview;

import android.app.Activity;
import androidx.core.app.ActivityCompat;
import java.lang.ref.WeakReference;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;

final class LocationPermissionActivityPermissionsDispatcher {
    /* access modifiers changed from: private */
    public static final String[] PERMISSION_ASKLOCATIONPERMISSION = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
    private static final int REQUEST_ASKLOCATIONPERMISSION = 2;

    private static final class LocationPermissionActivityAskLocationPermissionPermissionRequest implements PermissionRequest {
        private final WeakReference<LocationPermissionActivity> weakTarget;

        private LocationPermissionActivityAskLocationPermissionPermissionRequest(LocationPermissionActivity locationPermissionActivity) {
            this.weakTarget = new WeakReference<>(locationPermissionActivity);
        }

        public void proceed() {
            LocationPermissionActivity locationPermissionActivity = (LocationPermissionActivity) this.weakTarget.get();
            if (locationPermissionActivity != null) {
                ActivityCompat.requestPermissions(locationPermissionActivity, LocationPermissionActivityPermissionsDispatcher.PERMISSION_ASKLOCATIONPERMISSION, 2);
            }
        }

        public void cancel() {
            LocationPermissionActivity locationPermissionActivity = (LocationPermissionActivity) this.weakTarget.get();
            if (locationPermissionActivity != null) {
                locationPermissionActivity.showDeniedForStorage();
            }
        }
    }

    private LocationPermissionActivityPermissionsDispatcher() {
    }

    static void askLocationPermissionWithPermissionCheck(LocationPermissionActivity locationPermissionActivity) {
        if (PermissionUtils.hasSelfPermissions(locationPermissionActivity, PERMISSION_ASKLOCATIONPERMISSION)) {
            locationPermissionActivity.askLocationPermission();
        } else if (PermissionUtils.shouldShowRequestPermissionRationale((Activity) locationPermissionActivity, PERMISSION_ASKLOCATIONPERMISSION)) {
            locationPermissionActivity.showRationaleForStorage(new LocationPermissionActivityAskLocationPermissionPermissionRequest(locationPermissionActivity));
        } else {
            ActivityCompat.requestPermissions(locationPermissionActivity, PERMISSION_ASKLOCATIONPERMISSION, 2);
        }
    }

    static void onRequestPermissionsResult(LocationPermissionActivity locationPermissionActivity, int i, int[] iArr) {
        if (i == 2) {
            if (PermissionUtils.verifyPermissions(iArr)) {
                locationPermissionActivity.askLocationPermission();
            } else if (!PermissionUtils.shouldShowRequestPermissionRationale((Activity) locationPermissionActivity, PERMISSION_ASKLOCATIONPERMISSION)) {
                locationPermissionActivity.showNeverAskForStorage();
            } else {
                locationPermissionActivity.showDeniedForStorage();
            }
        }
    }
}
