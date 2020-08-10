package p101me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import java.util.Arrays;
import java.util.List;
import p101me.leolin.shortcutbadger.Badger;
import p101me.leolin.shortcutbadger.ShortcutBadgeException;
import p101me.leolin.shortcutbadger.util.BroadcastHelper;

/* renamed from: me.leolin.shortcutbadger.impl.DefaultBadger */
public class DefaultBadger implements Badger {
    private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.getClassName());
        if (BroadcastHelper.canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unable to resolve intent: ");
        sb.append(intent.toString());
        throw new ShortcutBadgeException(sb.toString());
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"fr.neamar.kiss", "com.quaap.launchtime", "com.quaap.launchtime_official"});
    }

    /* access modifiers changed from: 0000 */
    public boolean isSupported(Context context) {
        return BroadcastHelper.canResolveBroadcast(context, new Intent(INTENT_ACTION));
    }
}
