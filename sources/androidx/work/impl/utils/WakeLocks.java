package androidx.work.impl.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import androidx.work.Logger;
import java.util.HashMap;
import java.util.WeakHashMap;

public class WakeLocks {
    private static final String TAG = Logger.tagWithPrefix("WakeLocks");
    private static final WeakHashMap<WakeLock, String> sWakeLocks = new WeakHashMap<>();

    public static WakeLock newWakeLock(Context context, String str) {
        PowerManager powerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
        StringBuilder sb = new StringBuilder();
        sb.append("WorkManager: ");
        sb.append(str);
        String sb2 = sb.toString();
        WakeLock newWakeLock = powerManager.newWakeLock(1, sb2);
        synchronized (sWakeLocks) {
            sWakeLocks.put(newWakeLock, sb2);
        }
        return newWakeLock;
    }

    public static void checkWakeLocks() {
        HashMap hashMap = new HashMap();
        synchronized (sWakeLocks) {
            hashMap.putAll(sWakeLocks);
        }
        for (WakeLock wakeLock : hashMap.keySet()) {
            if (wakeLock != null && wakeLock.isHeld()) {
                Logger.get().warning(TAG, String.format("WakeLock held for %s", new Object[]{hashMap.get(wakeLock)}), new Throwable[0]);
            }
        }
    }

    private WakeLocks() {
    }
}
