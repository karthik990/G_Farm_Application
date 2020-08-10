package p101me.leolin.shortcutbadger.util;

import android.content.Context;
import android.content.Intent;
import java.util.List;

/* renamed from: me.leolin.shortcutbadger.util.BroadcastHelper */
public class BroadcastHelper {
    public static boolean canResolveBroadcast(Context context, Intent intent) {
        List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) {
            return false;
        }
        return true;
    }
}
