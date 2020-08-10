package com.mobiroller.activities;

import androidx.core.app.ActivityCompat;
import permissions.dispatcher.PermissionUtils;

final class ImagePagerActivityPermissionsDispatcher {
    private static final String[] PERMISSION_DOWNLOADIMAGE = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private static final int REQUEST_DOWNLOADIMAGE = 1;

    private ImagePagerActivityPermissionsDispatcher() {
    }

    static void downloadImageWithPermissionCheck(ImagePagerActivity imagePagerActivity) {
        if (PermissionUtils.hasSelfPermissions(imagePagerActivity, PERMISSION_DOWNLOADIMAGE)) {
            imagePagerActivity.downloadImage();
        } else {
            ActivityCompat.requestPermissions(imagePagerActivity, PERMISSION_DOWNLOADIMAGE, 1);
        }
    }

    static void onRequestPermissionsResult(ImagePagerActivity imagePagerActivity, int i, int[] iArr) {
        if (i == 1 && PermissionUtils.verifyPermissions(iArr)) {
            imagePagerActivity.downloadImage();
        }
    }
}
