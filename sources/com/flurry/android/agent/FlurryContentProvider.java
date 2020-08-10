package com.flurry.android.agent;

import android.app.ActivityManager.MemoryInfo;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.flurry.sdk.C1710dk;
import com.flurry.sdk.C1713dl;

public class FlurryContentProvider extends ContentProvider {
    public static final String PERFORMANCE_DATA_TYPE = "performance";
    public static final int RUNTIME_USED_MEMORY_COLUMN = 1;
    public static final int START_TIME_COLUMN = 0;
    public static final int SYSTEM_USED_MEMORY_COLUMN = 2;

    /* renamed from: a */
    private static UriMatcher f312a;

    /* renamed from: b */
    private static final long f313b = System.nanoTime();

    /* renamed from: c */
    private MatrixCursor f314c;

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public boolean onCreate() {
        String providerName = getProviderName(getContext());
        UriMatcher uriMatcher = new UriMatcher(-1);
        f312a = uriMatcher;
        uriMatcher.addURI(providerName, PERFORMANCE_DATA_TYPE, 1);
        Runtime runtime = Runtime.getRuntime();
        MemoryInfo a = C1713dl.m834a(getContext());
        this.f314c = new MatrixCursor(new String[]{"START_TIME", "RUNTIME_USED_MEMORY", "SYSTEM_USED_MEMORY"});
        this.f314c.newRow().add(Long.valueOf(f313b)).add(Long.valueOf(runtime.totalMemory() - runtime.freeMemory())).add(Long.valueOf(a.totalMem - a.availMem));
        C1710dk.m827a().mo16432a(getContext(), this.f314c);
        return true;
    }

    public static String getProviderName(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        StringBuilder sb = new StringBuilder();
        sb.append(packageName);
        sb.append(".FlurryContentProvider");
        return sb.toString();
    }

    public static Uri getProviderUri(Context context, String str) {
        String providerName = getProviderName(context);
        StringBuilder sb = new StringBuilder("content://");
        sb.append(providerName);
        sb.append("/");
        sb.append(str);
        return Uri.parse(sb.toString());
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (f312a.match(uri) != 1) {
            return null;
        }
        return this.f314c;
    }
}
