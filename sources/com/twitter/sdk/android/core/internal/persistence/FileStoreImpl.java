package com.twitter.sdk.android.core.internal.persistence;

import android.content.Context;
import android.os.Environment;
import com.twitter.sdk.android.core.Twitter;
import java.io.File;

public class FileStoreImpl implements FileStore {
    private final Context context;

    public FileStoreImpl(Context context2) {
        if (context2 != null) {
            this.context = context2;
            return;
        }
        throw new IllegalArgumentException("Context must not be null");
    }

    public File getCacheDir() {
        return prepare(this.context.getCacheDir());
    }

    public File getExternalCacheDir() {
        if (isExternalStorageAvailable()) {
            return prepare(this.context.getExternalCacheDir());
        }
        return prepare(null);
    }

    public File getFilesDir() {
        return prepare(this.context.getFilesDir());
    }

    public File getExternalFilesDir() {
        if (isExternalStorageAvailable()) {
            return prepare(this.context.getExternalFilesDir(null));
        }
        return prepare(null);
    }

    /* access modifiers changed from: 0000 */
    public File prepare(File file) {
        String str = "Twitter";
        if (file == null) {
            Twitter.getLogger().mo20817d(str, "Null File");
        } else if (file.exists() || file.mkdirs()) {
            return file;
        } else {
            Twitter.getLogger().mo20830w(str, "Couldn't create file");
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean isExternalStorageAvailable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        Twitter.getLogger().mo20830w("Twitter", "External Storage is not mounted and/or writable\nHave you declared android.permission.WRITE_EXTERNAL_STORAGE in the manifest?");
        return false;
    }
}
