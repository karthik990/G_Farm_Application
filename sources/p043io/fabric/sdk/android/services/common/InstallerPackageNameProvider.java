package p043io.fabric.sdk.android.services.common;

import android.content.Context;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.services.cache.MemoryValueCache;
import p043io.fabric.sdk.android.services.cache.ValueLoader;

/* renamed from: io.fabric.sdk.android.services.common.InstallerPackageNameProvider */
public class InstallerPackageNameProvider {
    private static final String NO_INSTALLER_PACKAGE_NAME = "";
    private final MemoryValueCache<String> installerPackageNameCache = new MemoryValueCache<>();
    private final ValueLoader<String> installerPackageNameLoader = new ValueLoader<String>() {
        public String load(Context context) throws Exception {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return installerPackageName == null ? "" : installerPackageName;
        }
    };

    public String getInstallerPackageName(Context context) {
        try {
            String str = (String) this.installerPackageNameCache.get(context, this.installerPackageNameLoader);
            if ("".equals(str)) {
                str = null;
            }
            return str;
        } catch (Exception e) {
            Fabric.getLogger().mo64077e(Fabric.TAG, "Failed to determine installer package name", e);
            return null;
        }
    }
}
