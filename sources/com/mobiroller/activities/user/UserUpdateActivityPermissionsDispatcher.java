package com.mobiroller.activities.user;

import androidx.core.app.ActivityCompat;
import permissions.dispatcher.PermissionUtils;

final class UserUpdateActivityPermissionsDispatcher {
    private static final String[] PERMISSION_OPENGALLERY = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private static final int REQUEST_OPENGALLERY = 4;

    private UserUpdateActivityPermissionsDispatcher() {
    }

    static void openGalleryWithPermissionCheck(UserUpdateActivity userUpdateActivity) {
        if (PermissionUtils.hasSelfPermissions(userUpdateActivity, PERMISSION_OPENGALLERY)) {
            userUpdateActivity.openGallery();
        } else {
            ActivityCompat.requestPermissions(userUpdateActivity, PERMISSION_OPENGALLERY, 4);
        }
    }

    static void onRequestPermissionsResult(UserUpdateActivity userUpdateActivity, int i, int[] iArr) {
        if (i == 4 && PermissionUtils.verifyPermissions(iArr)) {
            userUpdateActivity.openGallery();
        }
    }
}
