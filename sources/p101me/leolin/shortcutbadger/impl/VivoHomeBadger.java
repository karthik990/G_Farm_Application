package p101me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.anjlab.android.iab.p020v3.Constants;
import java.util.Arrays;
import java.util.List;
import p101me.leolin.shortcutbadger.Badger;
import p101me.leolin.shortcutbadger.ShortcutBadgeException;

/* renamed from: me.leolin.shortcutbadger.impl.VivoHomeBadger */
public class VivoHomeBadger implements Badger {
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra(Constants.RESPONSE_PACKAGE_NAME, context.getPackageName());
        intent.putExtra("className", componentName.getClassName());
        intent.putExtra("notificationNum", i);
        context.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.vivo.launcher"});
    }
}
