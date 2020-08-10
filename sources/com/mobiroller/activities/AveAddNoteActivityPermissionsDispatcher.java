package com.mobiroller.activities;

import androidx.core.app.ActivityCompat;
import permissions.dispatcher.PermissionUtils;

final class AveAddNoteActivityPermissionsDispatcher {
    private static final String[] PERMISSION_OPENGALLERY = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private static final int REQUEST_OPENGALLERY = 0;

    private AveAddNoteActivityPermissionsDispatcher() {
    }

    static void openGalleryWithPermissionCheck(AveAddNoteActivity aveAddNoteActivity) {
        if (PermissionUtils.hasSelfPermissions(aveAddNoteActivity, PERMISSION_OPENGALLERY)) {
            aveAddNoteActivity.openGallery();
        } else {
            ActivityCompat.requestPermissions(aveAddNoteActivity, PERMISSION_OPENGALLERY, 0);
        }
    }

    static void onRequestPermissionsResult(AveAddNoteActivity aveAddNoteActivity, int i, int[] iArr) {
        if (i == 0 && PermissionUtils.verifyPermissions(iArr)) {
            aveAddNoteActivity.openGallery();
        }
    }
}
