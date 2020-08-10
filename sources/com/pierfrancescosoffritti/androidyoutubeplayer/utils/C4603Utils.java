package com.pierfrancescosoffritti.androidyoutubeplayer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.utils.Utils */
public class C4603Utils {
    public static String formatTime(float f) {
        return String.format("%d:%02d", new Object[]{Integer.valueOf((int) (f / 60.0f)), Integer.valueOf((int) (f % 60.0f))});
    }

    public static boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
