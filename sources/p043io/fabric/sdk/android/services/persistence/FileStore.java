package p043io.fabric.sdk.android.services.persistence;

import java.io.File;

/* renamed from: io.fabric.sdk.android.services.persistence.FileStore */
public interface FileStore {
    File getCacheDir();

    File getExternalCacheDir();

    File getExternalFilesDir();

    File getFilesDir();
}
