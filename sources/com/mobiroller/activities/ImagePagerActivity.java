package com.mobiroller.activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.FavoriteHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.GalleryModel;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.ShareUtil;
import com.mobiroller.views.DepthPageTransformer;
import com.mobiroller.views.HackyViewPager;
import com.mobiroller.views.PullBackLayout;
import com.mobiroller.views.PullBackLayout.Callback;
import java.io.File;
import java.util.List;
import java.util.Map;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;

public class ImagePagerActivity extends AppCompatActivity implements Callback {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String TOOLBAR_TITLE = "ToolbarTitle";
    private Animation animSlideIn;
    private Animation animSlideOut;
    private String defaultMimeType = "image/jpeg";
    private boolean fromGalleryView = false;
    private List<GalleryModel> galleryModels;
    private boolean isDownloadable = true;
    /* access modifiers changed from: private */
    public boolean isTransitionActive = false;
    LocalizationHelper localizationHelper;
    private final SharedElementCallback mCallback = new SharedElementCallback() {
        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            if (ImagePagerActivity.this.mCurrentView != null) {
                PhotoView photoView = (PhotoView) ImagePagerActivity.this.mCurrentView.findViewById(R.id.image);
                String str = "galleryImage";
                if (((String) list.get(0)).equalsIgnoreCase(str)) {
                    map.put(str, photoView);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public View mCurrentView = null;
    private FavoriteHelper mFavoriteHelper;
    /* access modifiers changed from: private */
    public TextView mImageTextView;
    @BindView(2131362881)
    HackyViewPager pager;
    @BindView(2131363006)
    PullBackLayout puller;
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363276)
    Toolbar toolbar;
    ToolbarHelper toolbarHelper;

    private class ImagePagerAdapter extends PagerAdapter {
        private List<GalleryModel> images;
        LayoutInflater inflater;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        ImagePagerAdapter(List<GalleryModel> list) {
            this.inflater = (LayoutInflater) ImagePagerActivity.this.getSystemService("layout_inflater");
            this.images = list;
        }

        public int getCount() {
            return this.images.size();
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = this.inflater.inflate(R.layout.item_pager_image, viewGroup, false);
            PhotoView photoView = (PhotoView) inflate.findViewById(R.id.image);
            TextView textView = (TextView) inflate.findViewById(R.id.image_text);
            textView.setBackgroundColor(ColorUtil.getColorWithAlpha(ImagePagerActivity.this.sharedPrefHelper.getActionBarColor(), 0.2f));
            ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.loading);
            if (VERSION.SDK_INT >= 22) {
                if (ImagePagerActivity.this.pager.getCurrentItem() != i) {
                    photoView.setTransitionName("dummy");
                } else {
                    ImagePagerActivity.this.isTransitionActive = true;
                }
            }
            if (((GalleryModel) this.images.get(i)).getFile() != null) {
                Glide.with((FragmentActivity) ImagePagerActivity.this).load(((GalleryModel) this.images.get(i)).getFile()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(ImagePagerActivity.this.createCallBack(photoView, textView, (GalleryModel) this.images.get(i), progressBar)).into((ImageView) photoView);
            } else {
                Glide.with((FragmentActivity) ImagePagerActivity.this).load(((GalleryModel) this.images.get(i)).getURL()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(ImagePagerActivity.this.createCallBack(photoView, textView, (GalleryModel) this.images.get(i), progressBar)).into((ImageView) photoView);
            }
            viewGroup.addView(inflate, -1, -1);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            ImagePagerActivity.this.mCurrentView = (View) obj;
            ImagePagerActivity imagePagerActivity = ImagePagerActivity.this;
            imagePagerActivity.mImageTextView = (TextView) imagePagerActivity.mCurrentView.findViewById(R.id.image_text);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public void onPull(float f) {
    }

    public void onPullCancel() {
    }

    public void onPullStart() {
    }

    private void setupToolbar() {
        setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= 21) {
            LayoutParams layoutParams = (LayoutParams) this.toolbar.getLayoutParams();
            layoutParams.topMargin = getStatusBarHeight();
            this.toolbar.setLayoutParams(layoutParams);
        }
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        String str = "";
        this.toolbar.setTitle((CharSequence) str);
        if (VERSION.SDK_INT >= 19) {
            this.toolbar.getNavigationIcon().setAutoMirrored(true);
        }
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImagePagerActivity.this.finish();
                try {
                    View currentFocus = ImagePagerActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        ((InputMethodManager) ImagePagerActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) str);
        if (VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(1024);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(ColorUtil.getColorWithAlpha(this.sharedPrefHelper.getActionBarColor(), 0.3f));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.ac_image_pager);
        ButterKnife.bind((Activity) this);
        supportPostponeEnterTransition();
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.toolbarHelper = new ToolbarHelper(this.sharedPrefHelper);
        this.localizationHelper = UtilManager.localizationHelper();
        this.animSlideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        this.animSlideIn.setDuration(300);
        this.animSlideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        this.animSlideOut.setDuration(300);
        this.mFavoriteHelper = new FavoriteHelper(this);
        this.puller.setCallback(this);
        this.puller.setEnableSwipe(true);
        setEnterSharedElementCallback(this.mCallback);
        setupToolbar();
        Bundle extras = getIntent().getExtras();
        this.galleryModels = (List) extras.getSerializable("imageList");
        this.isDownloadable = extras.getBoolean("isDownloadable");
        this.fromGalleryView = extras.getBoolean("fromGalleryView");
        int i = extras.getInt(Constants.KEY_RSS_POSITION, 0);
        this.pager.setAdapter(new ImagePagerAdapter(this.galleryModels));
        this.pager.setCurrentItem(i);
        this.pager.setPageTransformer(true, new DepthPageTransformer());
        this.pager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                ImagePagerActivity.this.invalidateOptionsMenu();
            }

            public void onPageSelected(int i) {
                ImagePagerActivity.this.isTransitionActive = false;
            }
        });
    }

    public void onPullComplete() {
        onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.toolbar.inflateMenu(R.menu.image_menu);
        Menu menu2 = this.toolbar.getMenu();
        if (!this.isDownloadable) {
            menu2.findItem(R.id.action_download).setVisible(false);
        }
        if (!this.sharedPrefHelper.isFavoriteActive()) {
            menu2.findItem(R.id.action_favorite).setVisible(false);
        }
        if (!this.fromGalleryView) {
            menu2.findItem(R.id.action_favorite).setVisible(false);
        } else if (this.mFavoriteHelper.isImageContentAddedToList((GalleryModel) this.galleryModels.get(this.pager.getCurrentItem()))) {
            menu2.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_white_24dp);
        }
        return super.onCreateOptionsMenu(menu2);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Bitmap bitmap = ((BitmapDrawable) ((ImageView) this.mCurrentView.findViewById(R.id.image)).getDrawable()).getBitmap();
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_download) {
            ImagePagerActivityPermissionsDispatcher.downloadImageWithPermissionCheck(this);
            return true;
        } else if (itemId == R.id.action_favorite) {
            GalleryModel galleryModel = (GalleryModel) this.galleryModels.get(this.pager.getCurrentItem());
            if (this.mFavoriteHelper.isImageContentAddedToList(galleryModel)) {
                this.mFavoriteHelper.removeGalleryContent(galleryModel);
            } else {
                this.mFavoriteHelper.addImageContentToList(galleryModel);
            }
            invalidateOptionsMenu();
            return true;
        } else if (itemId != R.id.action_share) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            ShareUtil.shareImage(this, ((GalleryModel) this.galleryModels.get(this.pager.getCurrentItem())).getCaption(), bitmap, ((GalleryModel) this.galleryModels.get(this.pager.getCurrentItem())).getURL());
            return true;
        }
    }

    public void onBackPressed() {
        if (VERSION.SDK_INT < 21 || !this.isTransitionActive) {
            finish();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    public void downloadImage() {
        GalleryModel galleryModel = (GalleryModel) this.galleryModels.get(this.pager.getCurrentItem());
        String absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        String str = "/";
        sb.append(str);
        String str2 = "\\s+";
        sb.append(getString(R.string.app_name).replaceAll(str2, str));
        File file = new File(absolutePath, sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        String substring = galleryModel.getURL().substring(galleryModel.getURL().lastIndexOf(47) + 1, galleryModel.getURL().length());
        String str3 = ".";
        if (substring.contains(str3)) {
            substring = substring.substring(0, substring.lastIndexOf(46));
        }
        DownloadManager downloadManager = (DownloadManager) getSystemService("download");
        Request request = new Request(Uri.parse(galleryModel.getURL()));
        String mimeType = ShareUtil.getMimeType(Uri.parse(galleryModel.getURL()), this);
        String str4 = "";
        if (mimeType == null || mimeType.equalsIgnoreCase(str4)) {
            mimeType = this.defaultMimeType;
        }
        Request description = request.setAllowedNetworkTypes(3).setAllowedOverRoaming(false).setNotificationVisibility(1).setDescription(galleryModel.getCaption());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.DIRECTORY_PICTURES);
        sb2.append(str);
        sb2.append(getString(R.string.app_name).replaceAll(str2, str4));
        sb2.append(str);
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(substring);
        sb4.append(str3);
        sb4.append(mimeType.replaceFirst(".*/([^/?]+).*", "$1"));
        description.setDestinationInExternalPublicDir(sb3, sb4.toString());
        downloadManager.enqueue(request);
    }

    /* access modifiers changed from: private */
    public RequestListener<Drawable> createCallBack(ImageView imageView, TextView textView, GalleryModel galleryModel, ProgressBar progressBar) {
        final ProgressBar progressBar2 = progressBar;
        final ImageView imageView2 = imageView;
        final GalleryModel galleryModel2 = galleryModel;
        final TextView textView2 = textView;
        C38884 r0 = new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                ImagePagerActivity.this.supportStartPostponedEnterTransition();
                progressBar2.setVisibility(8);
                ImagePagerActivity imagePagerActivity = ImagePagerActivity.this;
                Toast.makeText(imagePagerActivity, imagePagerActivity.getString(R.string.common_error), 0).show();
                return false;
            }

            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                ImagePagerActivity.this.supportStartPostponedEnterTransition();
                new PhotoViewAttacher(imageView2).setOnPhotoTapListener(new OnPhotoTapListener() {
                    public void onPhotoTap(ImageView imageView, float f, float f2) {
                        ImagePagerActivity.this.hideOrShowStatusBar();
                    }
                });
                if (galleryModel2.getCaption() == null || galleryModel2.getCaption().equalsIgnoreCase("")) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setText(ImagePagerActivity.this.localizationHelper.getLocalizedTitle(galleryModel2.getCaption()));
                }
                if (ImagePagerActivity.this.toolbar.getVisibility() == 8) {
                    textView2.setVisibility(8);
                }
                progressBar2.setVisibility(8);
                return false;
            }
        };
        return r0;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        ImagePagerActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    public int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (identifier > 0) {
            return getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void hideOrShowStatusBar() {
        if (this.toolbar.getVisibility() == 0) {
            this.toolbar.setVisibility(8);
            if (VERSION.SDK_INT >= 21) {
                getWindow().setFlags(1024, 1024);
            }
            if (this.mImageTextView.getText().toString().length() > 1) {
                this.mImageTextView.startAnimation(this.animSlideOut);
                this.animSlideOut.setAnimationListener(new AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        ImagePagerActivity.this.mImageTextView.setVisibility(8);
                    }
                });
                return;
            }
            return;
        }
        if (VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(1024);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(ColorUtil.getColorWithAlpha(this.sharedPrefHelper.getActionBarColor(), 0.3f));
        }
        this.toolbar.setVisibility(0);
        if (this.mImageTextView.getText().toString().length() > 1) {
            this.mImageTextView.startAnimation(this.animSlideIn);
            this.animSlideIn.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ImagePagerActivity.this.mImageTextView.setVisibility(0);
                }
            });
        }
    }
}
