package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import java.io.File;

final class zzbr {
    private static int version() {
        try {
            return Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException unused) {
            String str = "Invalid version number: ";
            String valueOf = String.valueOf(VERSION.SDK);
            zzdi.zzav(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return 0;
        }
    }

    static boolean zzbb(String str) {
        if (version() < 9) {
            return false;
        }
        File file = new File(str);
        file.setReadable(false, false);
        file.setWritable(false, false);
        file.setReadable(true, true);
        file.setWritable(true, true);
        return true;
    }
}
