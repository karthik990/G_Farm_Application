package com.mobiroller.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.mobi942763453128.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.protocol.HTTP;

public class ShareUtil {
    public static void shareURL(AppCompatActivity appCompatActivity, String str, String str2) {
        InterstitialAdsUtil interstitialAdsUtil = new InterstitialAdsUtil(appCompatActivity);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.putExtra("android.intent.extra.TEXT", str2);
        interstitialAdsUtil.checkInterstitialAds(Intent.createChooser(intent, appCompatActivity.getString(R.string.action_share)));
    }

    public static void shareImage(AppCompatActivity appCompatActivity, String str, Bitmap bitmap, String str2) {
        String str3 = "images";
        InterstitialAdsUtil interstitialAdsUtil = new InterstitialAdsUtil(appCompatActivity);
        String str4 = "android.intent.action.SEND";
        Intent intent = new Intent(str4);
        intent.setType("image/jpeg");
        try {
            File file = new File(appCompatActivity.getCacheDir(), str3);
            file.mkdirs();
            StringBuilder sb = new StringBuilder();
            sb.append(file);
            sb.append("/image.png");
            FileOutputStream fileOutputStream = new FileOutputStream(sb.toString());
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uriForFile = FileProvider.getUriForFile(appCompatActivity, appCompatActivity.getPackageName(), new File(new File(appCompatActivity.getCacheDir(), str3), "image.png"));
        appCompatActivity.grantUriPermission(appCompatActivity.getPackageName(), uriForFile, 3);
        if (uriForFile != null) {
            Intent intent2 = new Intent();
            intent2.setAction(str4);
            intent2.addFlags(1);
            intent2.setDataAndType(uriForFile, appCompatActivity.getContentResolver().getType(uriForFile));
            intent.putExtra("android.intent.extra.TEXT", str);
            intent2.putExtra("android.intent.extra.STREAM", uriForFile);
            interstitialAdsUtil.checkInterstitialAds(Intent.createChooser(intent2, "Share Image"));
        }
    }

    public static String getMimeType(Uri uri, Context context) {
        String str;
        if (uri.getScheme().equals(Param.CONTENT)) {
            str = context.getContentResolver().getType(uri);
        } else {
            str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()).toLowerCase());
        }
        if (str != null && !str.equalsIgnoreCase("")) {
            return str;
        }
        Options options = new Options();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
            options.inJustDecodeBounds = true;
            InputStream inputStream = httpURLConnection.getInputStream();
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return options.outMimeType;
    }

    public static void getOpenFacebookIntent(Context context, String str) {
        String str2 = "android.intent.action.VIEW";
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            StringBuilder sb = new StringBuilder();
            sb.append("fb://page/");
            sb.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb.toString())));
        } catch (Exception unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://www.facebook.com/");
            sb2.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb2.toString())));
        }
    }

    public static void getOpenLinkedinIntent(Context context, String str) {
        String str2 = "android.intent.action.VIEW";
        try {
            context.getPackageManager().getPackageInfo("com.linkedin.android", 0);
            StringBuilder sb = new StringBuilder();
            sb.append("linkedin://profile/");
            sb.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb.toString())));
        } catch (Exception unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("http://www.linkedin.com/profile/view?id=");
            sb2.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb2.toString())));
        }
    }

    public static void getOpenTwitterIntent(Context context, String str) {
        String str2 = "android.intent.action.VIEW";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("twitter://user?screen_name=");
            sb.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb.toString())));
        } catch (Exception unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://twitter.com/#!/");
            sb2.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb2.toString())));
        }
    }

    public static void getOpenGooglePlusIntent(Context context, String str) {
        String str2 = "/";
        String str3 = "https://plus.google.com/";
        String str4 = "android.intent.action.VIEW";
        try {
            Intent intent = new Intent(str4);
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str);
            sb.append(str2);
            intent.setData(Uri.parse(sb.toString()));
            intent.setPackage("com.google.android.apps.plus");
            context.startActivity(intent);
        } catch (Exception unused) {
            Intent intent2 = new Intent(str4);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(str);
            sb2.append(str2);
            intent2.setData(Uri.parse(sb2.toString()));
            context.startActivity(intent2);
        }
    }

    public static void getOpenInstagramIntent(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://instagram.com/_u/");
        sb.append(str);
        String str2 = "android.intent.action.VIEW";
        Intent intent = new Intent(str2, Uri.parse(sb.toString()));
        intent.setPackage("com.instagram.android");
        try {
            context.startActivity(intent);
        } catch (Exception unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("http://instagram.com/");
            sb2.append(str);
            context.startActivity(new Intent(str2, Uri.parse(sb2.toString())));
        }
    }
}
