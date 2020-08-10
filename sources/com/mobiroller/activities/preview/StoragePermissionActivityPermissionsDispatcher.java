package com.mobiroller.activities.preview;

import android.app.Activity;
import androidx.core.app.ActivityCompat;
import java.lang.ref.WeakReference;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;

final class StoragePermissionActivityPermissionsDispatcher {
    /* access modifiers changed from: private */
    public static final String[] PERMISSION_ASKLOCATIONPERMISSION = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private static final int REQUEST_ASKLOCATIONPERMISSION = 3;

    private static final class StoragePermissionActivityAskLocationPermissionPermissionRequest implements PermissionRequest {
        private final WeakReference<StoragePermissionActivity> weakTarget;

        private StoragePermissionActivityAskLocationPermissionPermissionRequest(StoragePermissionActivity storagePermissionActivity) {
            this.weakTarget = new WeakReference<>(storagePermissionActivity);
        }

        public void proceed() {
            StoragePermissionActivity storagePermissionActivity = (StoragePermissionActivity) this.weakTarget.get();
            if (storagePermissionActivity != null) {
                ActivityCompat.requestPermissions(storagePermissionActivity, StoragePermissionActivityPermissionsDispatcher.PERMISSION_ASKLOCATIONPERMISSION, 3);
            }
        }

        public void cancel() {
            StoragePermissionActivity storagePermissionActivity = (StoragePermissionActivity) this.weakTarget.get();
            if (storagePermissionActivity != null) {
                storagePermissionActivity.showDeniedForStorage();
            }
        }
    }

    private StoragePermissionActivityPermissionsDispatcher() {
    }

    static void askLocationPermissionWithPermissionCheck(StoragePermissionActivity storagePermissionActivity) {
        if (PermissionUtils.hasSelfPermissions(storagePermissionActivity, PERMISSION_ASKLOCATIONPERMISSION)) {
            storagePermissionActivity.askLocationPermission();
        } else if (PermissionUtils.shouldShowRequestPermissionRationale((Activity) storagePermissionActivity, PERMISSION_ASKLOCATIONPERMISSION)) {
            storagePermissionActivity.showRationaleForStorage(new StoragePermissionActivityAskLocationPermissionPermissionRequest(storagePermissionActivity));
        } else {
            ActivityCompat.requestPermissions(storagePermissionActivity, PERMISSION_ASKLOCATIONPERMISSION, 3);
        }
    }

    static void onRequestPermissionsResult(StoragePermissionActivity storagePermissionActivity, int i, int[] iArr) {
        if (i == 3) {
            if (PermissionUtils.verifyPermissions(iArr)) {
                storagePermissionActivity.askLocationPermission();
            } else if (!PermissionUtils.shouldShowRequestPermissionRationale((Activity) storagePermissionActivity, PERMISSION_ASKLOCATIONPERMISSION)) {
                storagePermissionActivity.showNeverAskForStorage();
            } else {
                storagePermissionActivity.showDeniedForStorage();
            }
        }
    }
}
