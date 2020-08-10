package com.mobiroller.views;

import android.content.Context;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager.CustomActionReceiver;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager.MediaDescriptionAdapter;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager.NotificationListener;
import java.util.List;

public class MobirollerPlayerNotificationManager extends PlayerNotificationManager {
    public boolean isRadio = false;

    public MobirollerPlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter) {
        super(context, str, i, mediaDescriptionAdapter);
    }

    public MobirollerPlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter, NotificationListener notificationListener) {
        super(context, str, i, mediaDescriptionAdapter, notificationListener);
    }

    public MobirollerPlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter, CustomActionReceiver customActionReceiver) {
        super(context, str, i, mediaDescriptionAdapter, customActionReceiver);
    }

    public MobirollerPlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter, NotificationListener notificationListener, CustomActionReceiver customActionReceiver) {
        super(context, str, i, mediaDescriptionAdapter, notificationListener, customActionReceiver);
    }

    /* access modifiers changed from: protected */
    public List<String> getActions(Player player) {
        List<String> actions = super.getActions(player);
        if (this.isRadio) {
            actions.remove(PlayerNotificationManager.ACTION_PREVIOUS);
            actions.remove(PlayerNotificationManager.ACTION_NEXT);
            actions.remove(PlayerNotificationManager.ACTION_FAST_FORWARD);
            actions.remove(PlayerNotificationManager.ACTION_REWIND);
        }
        return actions;
    }
}
