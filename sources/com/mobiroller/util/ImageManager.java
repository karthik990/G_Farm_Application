package com.mobiroller.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ImageModel;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.views.theme.Theme;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import timber.log.Timber;

public class ImageManager {
    private static final String TAG = "ImageManager";

    static class SlidingMenuIconRequestListener {
        MenuItem menuItem;
        RequestListener requestListener = new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                return false;
            }

            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                SlidingMenuIconRequestListener.this.menuItem.setIcon(drawable);
                return false;
            }
        };

        SlidingMenuIconRequestListener(MenuItem menuItem2) {
            this.menuItem = menuItem2;
        }
    }

    public static void displayUserImage(Context context, String str, ImageView imageView) {
        if (str != null && !str.equals("")) {
            Glide.with(context).setDefaultRequestOptions((RequestOptions) ((RequestOptions) new RequestOptions().placeholder((int) R.drawable.defaultuser)).error((int) R.drawable.defaultuser)).load(str).into(imageView);
        }
    }

    public static void loadBackgroundImageFromImageModel(View view, ImageModel imageModel) {
        if (imageModel != null && imageModel.getImageURL() != null && view != null) {
            loadBackgroundImage(imageModel.getImageURL(), view);
        }
    }

    public static void loadBackgroundImage(String str, final View view) {
        String str2 = TAG;
        C43091 r1 = new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                return false;
            }

            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                view.setBackground(drawable);
                return false;
            }
        };
        try {
            String[] split = str.split("/");
            String str3 = split[split.length - 1];
            if (checkImageIsExistInAssets(str3)) {
                Timber.tag(str2).mo23218d("Loading image from assets. Image name : %s", str3);
                RequestManager with = Glide.with(view.getContext());
                StringBuilder sb = new StringBuilder();
                sb.append("file:///android_asset/Files/");
                sb.append(str3);
                with.load(Uri.parse(sb.toString())).listener(r1).submit();
                return;
            }
            Timber.tag(str2).mo23218d("Loading image from cache or internet. Image name : %s", str3);
            Glide.with(view.getContext()).load(str).listener(r1).submit();
        } catch (Exception e) {
            Timber.tag(str2).mo23218d("Loading from assets FAILED. Loading image from internet. Image url : %s", str);
            e.printStackTrace();
            Glide.with(view.getContext()).load(str).listener(r1).submit();
        }
    }

    public static void loadBackgroundImage(Context context, String str, final View view) {
        String str2 = TAG;
        C43102 r1 = new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                return false;
            }

            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                view.setBackgroundDrawable(drawable);
                return false;
            }
        };
        try {
            String[] split = str.split("/");
            String str3 = split[split.length - 1];
            if (checkImageIsExistInAssets(str3)) {
                Timber.tag(str2).mo23218d("Loading image from assets. Image name : %s", str3);
                RequestManager with = Glide.with(context);
                StringBuilder sb = new StringBuilder();
                sb.append("file:///android_asset/Files/");
                sb.append(str3);
                with.load(Uri.parse(sb.toString())).listener(r1).submit();
                return;
            }
            Timber.tag(str2).mo23218d("Loading image from cache or internet. Image name : %s", str3);
            Glide.with(context).load(str).listener(r1).submit();
        } catch (Exception e) {
            Timber.tag(str2).mo23218d("Loading from assets FAILED. Loading image from internet. Image url : %s", str);
            e.printStackTrace();
            Glide.with(context).load(str).listener(r1).submit();
        }
    }

    public static void loadImageView(Context context, String str, ImageView imageView) {
        String str2 = TAG;
        try {
            String[] split = str.split("/");
            String str3 = split[split.length - 1];
            if (checkImageIsExistInAssets(str3)) {
                Timber.tag(str2).mo23218d("Loading image from assets. Image name : %s", str3);
                RequestManager with = Glide.with(context);
                StringBuilder sb = new StringBuilder();
                sb.append("file:///android_asset/Files/");
                sb.append(str3);
                with.load(Uri.parse(sb.toString())).into(imageView);
                return;
            }
            Timber.tag(str2).mo23218d("Loading image from cache or internet. Image name : %s", str3);
            Glide.with(context).load(str).into(imageView);
        } catch (Exception e) {
            Timber.tag(str2).mo23218d("Loading from assets FAILED. Loading image from internet. Image url : %s", str);
            e.printStackTrace();
            Glide.with(context).load(str).into(imageView);
        }
    }

    public static void loadImageView(String str, ImageView imageView) {
        try {
            String[] split = str.split("/");
            String str2 = split[split.length - 1];
            if (checkImageIsExistInAssets(str2)) {
                RequestManager with = Glide.with(imageView.getContext());
                StringBuilder sb = new StringBuilder();
                sb.append("file:///android_asset/Files/");
                sb.append(str2);
                with.load(Uri.parse(sb.toString())).into(imageView);
                return;
            }
            Glide.with(imageView.getContext()).load(str).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(imageView.getContext()).load(str).into(imageView);
        }
    }

    public static void loadImageViewInAppPurchase(Context context, String str, ImageView imageView) {
        Glide.with(context).load(str).into(imageView);
    }

    public static File getFileFromUrl(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String str2 = "/";
        String[] split = str.split(str2);
        int length = split.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.Mobiroller_Preferences_FilePath);
        sb.append(str2);
        sb.append(split[length]);
        return new File(sb.toString());
    }

    private static void saveBitmapToStorage(Bitmap bitmap, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getImageSync(String str) {
        File fileFromUrl = getFileFromUrl(str);
        if (fileFromUrl == null) {
            return null;
        }
        if (!fileFromUrl.exists()) {
            return getBitmapFromURL(str, fileFromUrl);
        }
        return BitmapFactory.decodeFile(fileFromUrl.getPath());
    }

    public static Bitmap getBitmapFromURL(String str, File file) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            Bitmap decodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            saveBitmapToStorage(decodeStream, file);
            return decodeStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap resize(Bitmap bitmap, int i, int i2) {
        if (i2 <= 0 || i <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = ((float) width) / ((float) height);
        float f2 = (float) i;
        float f3 = (float) i2;
        if (width > height) {
            i2 = (int) (f2 / f);
        } else if (height > width) {
            i = (int) (f3 * f);
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }

    public static Bitmap resizeMaxDeviceSize(Bitmap bitmap) {
        if (ScreenUtil.getScreenHeight() <= 0 || ScreenUtil.getScreenWidth() - ScreenHelper.dpToPx(40) <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = ((float) width) / ((float) height);
        ScreenUtil.getScreenWidth();
        ScreenHelper.dpToPx(40);
        ScreenUtil.getScreenHeight();
        int screenWidth = ScreenUtil.getScreenWidth() - ScreenHelper.dpToPx(40);
        int screenHeight = ScreenUtil.getScreenHeight();
        if (width > height) {
            screenHeight = (int) ((((float) ScreenUtil.getScreenWidth()) - ((float) ScreenHelper.dpToPx(40))) / f);
        } else if (height > width) {
            screenWidth = (int) (((float) ScreenUtil.getScreenHeight()) * f);
        }
        return Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true);
    }

    public static boolean checkImageIsExistInAssets(String str) {
        return MobiRollerApplication.assetList != null && Arrays.asList(MobiRollerApplication.assetList).contains(str);
    }

    public static void loadUserImage(final ImageView imageView) {
        if (!UserHelper.getUserImageUrl().equalsIgnoreCase("")) {
            Glide.with((View) imageView).load(UserHelper.getUserImageUrl()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).addListener(new RequestListener<Drawable>() {
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    imageView.setImageResource(R.drawable.ic_account_circle_black_36dp);
                    imageView.setColorFilter(new PorterDuffColorFilter(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK, Mode.MULTIPLY));
                    return false;
                }
            }).into(imageView);
        }
    }

    public static void loadSlidingMenuIcons(Context context, List<MenuItem> list, ArrayList<NavigationItemModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!(arrayList.get(i) == null || ((NavigationItemModel) arrayList.get(i)).getIconImage() == null || ((NavigationItemModel) arrayList.get(i)).getIconImage().getImageURL() == null || list.get(i) == null)) {
                loadIcon(context, new SlidingMenuIconRequestListener((MenuItem) list.get(i)).requestListener, ((NavigationItemModel) arrayList.get(i)).getIconImage().getImageURL());
            }
        }
    }

    public static void loadMainMenuIcons(Context context, Menu menu, ArrayList<NavigationItemModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!(arrayList.get(i) == null || ((NavigationItemModel) arrayList.get(i)).getIconImage() == null || ((NavigationItemModel) arrayList.get(i)).getIconImage().getImageURL() == null || menu.findItem(i) == null)) {
                loadIcon(context, new SlidingMenuIconRequestListener(menu.findItem(i)).requestListener, ((NavigationItemModel) arrayList.get(i)).getIconImage().getImageURL());
            }
        }
    }

    private static void loadIcon(Context context, RequestListener requestListener, String str) {
        try {
            String[] split = str.split("/");
            String str2 = split[split.length - 1];
            if (checkImageIsExistInAssets(str2)) {
                RequestManager with = Glide.with(context);
                StringBuilder sb = new StringBuilder();
                sb.append("file:///android_asset/Files/");
                sb.append(str2);
                with.load(Uri.parse(sb.toString())).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).addListener(requestListener).submit();
                return;
            }
            Glide.with(context).load(str).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).addListener(requestListener).submit();
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(context).load(str).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).addListener(requestListener).submit();
        }
    }
}
