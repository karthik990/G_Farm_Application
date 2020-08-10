package com.fasterxml.jackson.core.json;

import com.crashlytics.android.BuildConfig;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

public final class PackageVersion implements Versioned {
    public static final Version VERSION = VersionUtil.parseVersion(BuildConfig.VERSION_NAME, "com.fasterxml.jackson.core", "jackson-core");

    public Version version() {
        return VERSION;
    }
}
