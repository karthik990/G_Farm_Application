package com.google.android.exoplayer2.util;

import java.util.Arrays;

public final class LibraryLoader {
    private static final String TAG = "LibraryLoader";
    private boolean isAvailable;
    private boolean loadAttempted;
    private String[] nativeLibraries;

    public LibraryLoader(String... strArr) {
        this.nativeLibraries = strArr;
    }

    public synchronized void setLibraries(String... strArr) {
        Assertions.checkState(!this.loadAttempted, "Cannot set libraries after loading");
        this.nativeLibraries = strArr;
    }

    public synchronized boolean isAvailable() {
        if (this.loadAttempted) {
            return this.isAvailable;
        }
        this.loadAttempted = true;
        try {
            for (String loadLibrary : this.nativeLibraries) {
                System.loadLibrary(loadLibrary);
            }
            this.isAvailable = true;
        } catch (UnsatisfiedLinkError unused) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to load ");
            sb.append(Arrays.toString(this.nativeLibraries));
            Log.m1396w(str, sb.toString());
        }
        return this.isAvailable;
    }
}
