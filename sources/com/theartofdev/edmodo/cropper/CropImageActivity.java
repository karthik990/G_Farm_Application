package com.theartofdev.edmodo.cropper;

import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.theartofdev.edmodo.cropper.CropImageView.CropResult;
import com.theartofdev.edmodo.cropper.CropImageView.OnCropImageCompleteListener;
import com.theartofdev.edmodo.cropper.CropImageView.OnSetImageUriCompleteListener;
import java.io.File;
import java.io.IOException;

public class CropImageActivity extends AppCompatActivity implements OnSetImageUriCompleteListener, OnCropImageCompleteListener {
    private Uri mCropImageUri;
    private CropImageView mCropImageView;
    private CropImageOptions mOptions;

    public void onCreate(Bundle bundle) {
        CharSequence charSequence;
        super.onCreate(bundle);
        setContentView(C3011R.layout.crop_image_activity);
        this.mCropImageView = (CropImageView) findViewById(C3011R.C3014id.cropImageView);
        Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        this.mCropImageUri = (Uri) bundleExtra.getParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE);
        this.mOptions = (CropImageOptions) bundleExtra.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);
        if (bundle == null) {
            Uri uri = this.mCropImageUri;
            if (uri == null || uri.equals(Uri.EMPTY)) {
                if (CropImage.isExplicitCameraPermissionRequired(this)) {
                    requestPermissions(new String[]{"android.permission.CAMERA"}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                } else {
                    CropImage.startPickImageActivity(this);
                }
            } else if (CropImage.isReadExternalStoragePermissionsRequired(this, this.mCropImageUri)) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 201);
            } else {
                this.mCropImageView.setImageUriAsync(this.mCropImageUri);
            }
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (this.mOptions.activityTitle == null || this.mOptions.activityTitle.length() <= 0) {
                charSequence = getResources().getString(C3011R.C3016string.crop_image_activity_title);
            } else {
                charSequence = this.mOptions.activityTitle;
            }
            supportActionBar.setTitle(charSequence);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mCropImageView.setOnSetImageUriCompleteListener(this);
        this.mCropImageView.setOnCropImageCompleteListener(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mCropImageView.setOnSetImageUriCompleteListener(null);
        this.mCropImageView.setOnCropImageCompleteListener(null);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C3011R.C3015menu.crop_image_menu, menu);
        if (!this.mOptions.allowRotation) {
            menu.removeItem(C3011R.C3014id.crop_image_menu_rotate_left);
            menu.removeItem(C3011R.C3014id.crop_image_menu_rotate_right);
        } else if (this.mOptions.allowCounterRotation) {
            menu.findItem(C3011R.C3014id.crop_image_menu_rotate_left).setVisible(true);
        }
        if (!this.mOptions.allowFlipping) {
            menu.removeItem(C3011R.C3014id.crop_image_menu_flip);
        }
        Drawable drawable = null;
        try {
            TypedValue typedValue = new TypedValue();
            getResources().getValue(C3011R.C3013drawable.crop_image_menu_crop, typedValue, false);
            if (typedValue.data != C3011R.C3013drawable.crop_image_menu_crop_stub) {
                drawable = ContextCompat.getDrawable(this, C3011R.C3013drawable.crop_image_menu_crop);
                menu.findItem(C3011R.C3014id.crop_image_menu_crop).setIcon(drawable);
            }
        } catch (Exception e) {
            Log.w("AIC", "Failed to read menu crop drawable", e);
        }
        if (this.mOptions.activityMenuIconColor != 0) {
            updateMenuItemIconColor(menu, C3011R.C3014id.crop_image_menu_rotate_left, this.mOptions.activityMenuIconColor);
            updateMenuItemIconColor(menu, C3011R.C3014id.crop_image_menu_rotate_right, this.mOptions.activityMenuIconColor);
            updateMenuItemIconColor(menu, C3011R.C3014id.crop_image_menu_flip, this.mOptions.activityMenuIconColor);
            if (drawable != null) {
                updateMenuItemIconColor(menu, C3011R.C3014id.crop_image_menu_crop, this.mOptions.activityMenuIconColor);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == C3011R.C3014id.crop_image_menu_crop) {
            cropImage();
            return true;
        } else if (menuItem.getItemId() == C3011R.C3014id.crop_image_menu_rotate_left) {
            rotateImage(-this.mOptions.rotationDegrees);
            return true;
        } else if (menuItem.getItemId() == C3011R.C3014id.crop_image_menu_rotate_right) {
            rotateImage(this.mOptions.rotationDegrees);
            return true;
        } else if (menuItem.getItemId() == C3011R.C3014id.crop_image_menu_flip_horizontally) {
            this.mCropImageView.flipImageHorizontally();
            return true;
        } else if (menuItem.getItemId() == C3011R.C3014id.crop_image_menu_flip_vertically) {
            this.mCropImageView.flipImageVertically();
            return true;
        } else if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            setResultCancel();
            return true;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 200) {
            if (i2 == 0) {
                setResultCancel();
            }
            if (i2 == -1) {
                this.mCropImageUri = CropImage.getPickImageResultUri(this, intent);
                if (CropImage.isReadExternalStoragePermissionsRequired(this, this.mCropImageUri)) {
                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 201);
                    return;
                }
                this.mCropImageView.setImageUriAsync(this.mCropImageUri);
            }
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 201) {
            Uri uri = this.mCropImageUri;
            if (uri == null || iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(this, "Cancelling, required permissions are not granted", 1).show();
                setResultCancel();
            } else {
                this.mCropImageView.setImageUriAsync(uri);
            }
        }
        if (i == 2011) {
            CropImage.startPickImageActivity(this);
        }
    }

    public void onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception exc) {
        if (exc == null) {
            if (this.mOptions.initialCropWindowRectangle != null) {
                this.mCropImageView.setCropRect(this.mOptions.initialCropWindowRectangle);
            }
            if (this.mOptions.initialRotation > -1) {
                this.mCropImageView.setRotatedDegrees(this.mOptions.initialRotation);
                return;
            }
            return;
        }
        setResult(null, exc, 1);
    }

    public void onCropImageComplete(CropImageView cropImageView, CropResult cropResult) {
        setResult(cropResult.getUri(), cropResult.getError(), cropResult.getSampleSize());
    }

    /* access modifiers changed from: protected */
    public void cropImage() {
        if (this.mOptions.noOutputImage) {
            setResult(null, null, 1);
            return;
        }
        this.mCropImageView.saveCroppedImageAsync(getOutputUri(), this.mOptions.outputCompressFormat, this.mOptions.outputCompressQuality, this.mOptions.outputRequestWidth, this.mOptions.outputRequestHeight, this.mOptions.outputRequestSizeOptions);
    }

    /* access modifiers changed from: protected */
    public void rotateImage(int i) {
        this.mCropImageView.rotateImage(i);
    }

    /* access modifiers changed from: protected */
    public Uri getOutputUri() {
        Uri uri = this.mOptions.outputUri;
        if (uri != null && !uri.equals(Uri.EMPTY)) {
            return uri;
        }
        try {
            String str = this.mOptions.outputCompressFormat == CompressFormat.JPEG ? ".jpg" : this.mOptions.outputCompressFormat == CompressFormat.PNG ? ".png" : ".webp";
            return Uri.fromFile(File.createTempFile("cropped", str, getCacheDir()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file for output image", e);
        }
    }

    /* access modifiers changed from: protected */
    public void setResult(Uri uri, Exception exc, int i) {
        setResult(exc == null ? -1 : 204, getResultIntent(uri, exc, i));
        finish();
    }

    /* access modifiers changed from: protected */
    public void setResultCancel() {
        setResult(0);
        finish();
    }

    /* access modifiers changed from: protected */
    public Intent getResultIntent(Uri uri, Exception exc, int i) {
        ActivityResult activityResult = new ActivityResult(this.mCropImageView.getImageUri(), uri, exc, this.mCropImageView.getCropPoints(), this.mCropImageView.getCropRect(), this.mCropImageView.getRotatedDegrees(), this.mCropImageView.getWholeImageRect(), i);
        Intent intent = new Intent();
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, activityResult);
        return intent;
    }

    private void updateMenuItemIconColor(Menu menu, int i, int i2) {
        MenuItem findItem = menu.findItem(i);
        if (findItem != null) {
            Drawable icon = findItem.getIcon();
            if (icon != null) {
                try {
                    icon.mutate();
                    icon.setColorFilter(i2, Mode.SRC_ATOP);
                    findItem.setIcon(icon);
                } catch (Exception e) {
                    Log.w("AIC", "Failed to update menu item color", e);
                }
            }
        }
    }
}
