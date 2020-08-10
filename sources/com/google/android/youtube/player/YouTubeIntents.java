package com.google.android.youtube.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.youtube.player.internal.C2838z;
import java.util.List;

public final class YouTubeIntents {
    private YouTubeIntents() {
    }

    /* renamed from: a */
    static Intent m1427a(Intent intent, Context context) {
        String str = "app_version";
        String str2 = "client_library_version";
        intent.putExtra("app_package", context.getPackageName()).putExtra(str, C2838z.m1757d(context)).putExtra(str2, C2838z.m1749a());
        return intent;
    }

    /* renamed from: a */
    private static Uri m1428a(String str) {
        String valueOf = String.valueOf(str);
        String str2 = "https://www.youtube.com/playlist?list=";
        return Uri.parse(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }

    /* renamed from: a */
    private static String m1429a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return C2838z.m1755b(packageManager) ? "com.google.android.youtube.tv" : C2838z.m1752a(packageManager) ? "com.google.android.youtube.googletv" : "com.google.android.youtube";
    }

    /* renamed from: a */
    private static boolean m1430a(Context context, Intent intent) {
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 65536);
        return queryIntentActivities != null && !queryIntentActivities.isEmpty();
    }

    /* renamed from: a */
    private static boolean m1431a(Context context, Uri uri) {
        return m1430a(context, new Intent("android.intent.action.VIEW", uri).setPackage(m1429a(context)));
    }

    /* renamed from: b */
    private static Intent m1432b(Context context, Uri uri) {
        return m1427a(new Intent("android.intent.action.VIEW", uri).setPackage(m1429a(context)), context);
    }

    public static boolean canResolveChannelIntent(Context context) {
        return m1431a(context, Uri.parse("https://www.youtube.com/channel/"));
    }

    public static boolean canResolveOpenPlaylistIntent(Context context) {
        return m1431a(context, Uri.parse("https://www.youtube.com/playlist?list="));
    }

    public static boolean canResolvePlayPlaylistIntent(Context context) {
        int installedYouTubeVersionCode = getInstalledYouTubeVersionCode(context);
        return (!C2838z.m1752a(context.getPackageManager()) ? installedYouTubeVersionCode >= 4000 : installedYouTubeVersionCode >= 4700) && canResolveOpenPlaylistIntent(context);
    }

    public static boolean canResolvePlayVideoIntent(Context context) {
        return m1431a(context, Uri.parse("https://www.youtube.com/watch?v="));
    }

    public static boolean canResolvePlayVideoIntentWithOptions(Context context) {
        int installedYouTubeVersionCode = getInstalledYouTubeVersionCode(context);
        PackageManager packageManager = context.getPackageManager();
        return (C2838z.m1755b(packageManager) || (!C2838z.m1752a(packageManager) ? installedYouTubeVersionCode >= 3300 : installedYouTubeVersionCode >= Integer.MAX_VALUE)) && canResolvePlayVideoIntent(context);
    }

    public static boolean canResolveSearchIntent(Context context) {
        if (!C2838z.m1752a(context.getPackageManager()) || getInstalledYouTubeVersionCode(context) >= 4700) {
            return m1430a(context, new Intent("android.intent.action.SEARCH").setPackage(m1429a(context)));
        }
        return false;
    }

    public static boolean canResolveUploadIntent(Context context) {
        return m1430a(context, new Intent("com.google.android.youtube.intent.action.UPLOAD").setPackage(m1429a(context)).setType("video/*"));
    }

    public static boolean canResolveUserIntent(Context context) {
        return m1431a(context, Uri.parse("https://www.youtube.com/user/"));
    }

    public static Intent createChannelIntent(Context context, String str) {
        String valueOf = String.valueOf(str);
        String str2 = "https://www.youtube.com/channel/";
        return m1432b(context, Uri.parse(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2)));
    }

    public static Intent createOpenPlaylistIntent(Context context, String str) {
        return m1427a(m1432b(context, m1428a(str)), context);
    }

    public static Intent createPlayPlaylistIntent(Context context, String str) {
        return m1427a(m1432b(context, m1428a(str).buildUpon().appendQueryParameter("playnext", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).build()), context);
    }

    public static Intent createPlayVideoIntent(Context context, String str) {
        return createPlayVideoIntentWithOptions(context, str, false, false);
    }

    public static Intent createPlayVideoIntentWithOptions(Context context, String str, boolean z, boolean z2) {
        String valueOf = String.valueOf(str);
        String str2 = "https://www.youtube.com/watch?v=";
        return m1432b(context, Uri.parse(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2))).putExtra("force_fullscreen", z).putExtra("finish_on_ended", z2);
    }

    public static Intent createSearchIntent(Context context, String str) {
        return m1427a(new Intent("android.intent.action.SEARCH").setPackage(m1429a(context)).putExtra("query", str), context);
    }

    public static Intent createUploadIntent(Context context, Uri uri) throws IllegalArgumentException {
        if (uri == null) {
            throw new IllegalArgumentException("videoUri parameter cannot be null.");
        } else if (uri.toString().startsWith("content://media/")) {
            return m1427a(new Intent("com.google.android.youtube.intent.action.UPLOAD").setPackage(m1429a(context)).setDataAndType(uri, "video/*"), context);
        } else {
            throw new IllegalArgumentException("videoUri parameter must be a URI pointing to a valid video file. It must begin with \"content://media/\"");
        }
    }

    public static Intent createUserIntent(Context context, String str) {
        String valueOf = String.valueOf(str);
        String str2 = "https://www.youtube.com/user/";
        return m1432b(context, Uri.parse(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2)));
    }

    public static int getInstalledYouTubeVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(m1429a(context), 0).versionCode;
        } catch (NameNotFoundException unused) {
            return -1;
        }
    }

    public static String getInstalledYouTubeVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(m1429a(context), 0).versionName;
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    public static boolean isYouTubeInstalled(Context context) {
        try {
            context.getPackageManager().getApplicationInfo(m1429a(context), 0);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }
}
