package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.File;

public final class HardwareConfigState {
    private static final File FD_SIZE_LIST = new File("/proc/self/fd");
    private static final int MAXIMUM_FDS_FOR_HARDWARE_CONFIGS_O = 700;
    private static final int MAXIMUM_FDS_FOR_HARDWARE_CONFIGS_P = 20000;
    private static final int MINIMUM_DECODES_BETWEEN_FD_CHECKS = 50;
    static final int MIN_HARDWARE_DIMENSION_O = 128;
    private static final int MIN_HARDWARE_DIMENSION_P = 0;
    private static volatile HardwareConfigState instance;
    private int decodesSinceLastFdCheck;
    private final int fdCountLimit;
    private boolean isFdSizeBelowHardwareLimit = true;
    private final boolean isHardwareConfigAllowedByDeviceModel = isHardwareConfigAllowedByDeviceModel();
    private final int minHardwareDimension;

    public static HardwareConfigState getInstance() {
        if (instance == null) {
            synchronized (HardwareConfigState.class) {
                if (instance == null) {
                    instance = new HardwareConfigState();
                }
            }
        }
        return instance;
    }

    HardwareConfigState() {
        if (VERSION.SDK_INT >= 28) {
            this.fdCountLimit = MAXIMUM_FDS_FOR_HARDWARE_CONFIGS_P;
            this.minHardwareDimension = 0;
            return;
        }
        this.fdCountLimit = 700;
        this.minHardwareDimension = 128;
    }

    public boolean isHardwareConfigAllowed(int i, int i2, boolean z, boolean z2) {
        if (!z || !this.isHardwareConfigAllowedByDeviceModel || VERSION.SDK_INT < 26 || z2) {
            return false;
        }
        int i3 = this.minHardwareDimension;
        if (i < i3 || i2 < i3 || !isFdSizeBelowHardwareLimit()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean setHardwareConfigIfAllowed(int i, int i2, Options options, boolean z, boolean z2) {
        boolean isHardwareConfigAllowed = isHardwareConfigAllowed(i, i2, z, z2);
        if (isHardwareConfigAllowed) {
            options.inPreferredConfig = Config.HARDWARE;
            options.inMutable = false;
        }
        return isHardwareConfigAllowed;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isHardwareConfigAllowedByDeviceModel() {
        /*
            java.lang.String r0 = android.os.Build.MODEL
            r1 = 1
            if (r0 == 0) goto L_0x0073
            java.lang.String r0 = android.os.Build.MODEL
            int r0 = r0.length()
            r2 = 7
            if (r0 >= r2) goto L_0x0010
            goto L_0x0073
        L_0x0010:
            java.lang.String r0 = android.os.Build.MODEL
            r3 = 0
            java.lang.String r0 = r0.substring(r3, r2)
            r2 = -1
            int r4 = r0.hashCode()
            switch(r4) {
                case -1398613787: goto L_0x005c;
                case -1398431166: goto L_0x0052;
                case -1398431161: goto L_0x0048;
                case -1398431073: goto L_0x003e;
                case -1398431068: goto L_0x0034;
                case -1398343746: goto L_0x002a;
                case -1398222624: goto L_0x0020;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x0066
        L_0x0020:
            java.lang.String r4 = "SM-N935"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 0
            goto L_0x0067
        L_0x002a:
            java.lang.String r4 = "SM-J720"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 1
            goto L_0x0067
        L_0x0034:
            java.lang.String r4 = "SM-G965"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 3
            goto L_0x0067
        L_0x003e:
            java.lang.String r4 = "SM-G960"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 2
            goto L_0x0067
        L_0x0048:
            java.lang.String r4 = "SM-G935"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 4
            goto L_0x0067
        L_0x0052:
            java.lang.String r4 = "SM-G930"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 5
            goto L_0x0067
        L_0x005c:
            java.lang.String r4 = "SM-A520"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0066
            r0 = 6
            goto L_0x0067
        L_0x0066:
            r0 = -1
        L_0x0067:
            switch(r0) {
                case 0: goto L_0x006b;
                case 1: goto L_0x006b;
                case 2: goto L_0x006b;
                case 3: goto L_0x006b;
                case 4: goto L_0x006b;
                case 5: goto L_0x006b;
                case 6: goto L_0x006b;
                default: goto L_0x006a;
            }
        L_0x006a:
            return r1
        L_0x006b:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 26
            if (r0 == r2) goto L_0x0072
            goto L_0x0073
        L_0x0072:
            r1 = 0
        L_0x0073:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.HardwareConfigState.isHardwareConfigAllowedByDeviceModel():boolean");
    }

    private synchronized boolean isFdSizeBelowHardwareLimit() {
        int i = this.decodesSinceLastFdCheck + 1;
        this.decodesSinceLastFdCheck = i;
        if (i >= 50) {
            boolean z = false;
            this.decodesSinceLastFdCheck = 0;
            int length = FD_SIZE_LIST.list().length;
            if (length < this.fdCountLimit) {
                z = true;
            }
            this.isFdSizeBelowHardwareLimit = z;
            if (!this.isFdSizeBelowHardwareLimit && Log.isLoggable("Downsampler", 5)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors ");
                sb.append(length);
                sb.append(", limit ");
                sb.append(this.fdCountLimit);
                Log.w("Downsampler", sb.toString());
            }
        }
        return this.isFdSizeBelowHardwareLimit;
    }
}
