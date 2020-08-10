package com.mobiroller.jcplayer;

import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import com.google.android.gms.ads.formats.UnifiedNativeAdAssetNames;
import com.mobiroller.jcplayer.JcPlayerView.JcPlayerViewServiceListener;
import com.mobiroller.mobi942763453128.R;

public class JcNotificationPlayerService implements JcPlayerViewServiceListener {
    static final String ACTION = "ACTION";
    static final String CURRENT_AUDIO = "CURRENT_AUDIO";
    static final String DELETE = "DELETE";
    public static final int MP3_NOTIFICATION_ID = 100;
    static final String NEXT = "NEXT";
    private static final int NEXT_ID = 0;
    static final String PAUSE = "PAUSE";
    private static final int PAUSE_ID = 3;
    static final String PLAY = "PLAY";
    static final String PLAYLIST = "PLAYLIST";
    private static final int PLAY_ID = 2;
    static final String PREVIOUS = "PREVIOUS";
    private static final int PREVIOUS_ID = 1;
    private Context context;
    private boolean flag = false;
    private int iconResource;
    private boolean isPlaying = false;
    private NotificationManager notificationManager;
    private String title;

    public void onCompletedAudio(String str) {
    }

    public void onContinueAudio(String str) {
    }

    public void onPlaying(String str) {
    }

    public void onPreparedAudio(String str, int i, String str2) {
    }

    public void onTimeChanged(long j, String str) {
    }

    JcNotificationPlayerService(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: 0000 */
    public void createNotificationPlayer(String str, boolean z) {
        this.flag = true;
        this.isPlaying = z;
        createNotificationPlayer(str, (int) R.drawable.icon);
    }

    /* access modifiers changed from: 0000 */
    public void createNotificationPlayer(String str, int i) {
        Builder builder;
        this.title = str;
        this.iconResource = i;
        Context context2 = this.context;
        Intent intent = new Intent(context2, context2.getClass());
        intent.setFlags(536870912);
        JcAudioPlayer.getInstance().registerNotificationListener(this);
        if (this.notificationManager == null) {
            this.notificationManager = (NotificationManager) this.context.getSystemService("notification");
        }
        if (VERSION.SDK_INT < 21) {
            String str2 = "DELETE";
            if (VERSION.SDK_INT >= 26) {
                builder = new Builder(this.context, UnifiedNativeAdAssetNames.ASSET_BODY).setSmallIcon(i).setDeleteIntent(buildPendingIntent(str2, 5)).setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), i)).setContent(createNotificationPlayerView()).setContentIntent(PendingIntent.getActivity(this.context, 100, intent, 268435456));
            } else {
                builder = new Builder(this.context).setSmallIcon(i).setDeleteIntent(buildPendingIntent(str2, 5)).setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), i)).setContent(createNotificationPlayerView()).setContentIntent(PendingIntent.getActivity(this.context, 100, intent, 268435456));
            }
            this.notificationManager.notify(100, builder.build());
        } else if (!this.flag) {
            buildNotification(!JcAudioPlayer.getInstance().isPaused());
        } else {
            buildNotification(this.isPlaying);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateNotification() {
        this.flag = false;
        createNotificationPlayer(this.title, this.iconResource);
    }

    private RemoteViews createNotificationPlayerView() {
        RemoteViews remoteViews;
        boolean z = this.flag;
        String str = PLAY;
        String str2 = PAUSE;
        if (z) {
            if (!this.isPlaying) {
                remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.notification_play);
                remoteViews.setOnClickPendingIntent(R.id.btn_play_notification, buildPendingIntent(str, 2));
            } else {
                remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.notification_pause);
                remoteViews.setOnClickPendingIntent(R.id.btn_pause_notification, buildPendingIntent(str2, 3));
            }
        } else if (JcAudioPlayer.getInstance().isPaused()) {
            remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.notification_play);
            remoteViews.setOnClickPendingIntent(R.id.btn_play_notification, buildPendingIntent(str, 2));
        } else {
            remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.notification_pause);
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_notification, buildPendingIntent(str2, 3));
        }
        remoteViews.setTextViewText(R.id.txt_current_music_notification, this.title);
        remoteViews.setImageViewResource(R.id.icon_player, this.iconResource);
        remoteViews.setOnClickPendingIntent(R.id.btn_next_notification, buildPendingIntent(NEXT, 0));
        remoteViews.setOnClickPendingIntent(R.id.btn_prev_notification, buildPendingIntent(PREVIOUS, 1));
        return remoteViews;
    }

    private void buildNotification(boolean z) {
        int i;
        PendingIntent pendingIntent;
        Context context2 = this.context;
        Intent intent = new Intent(context2, context2.getClass());
        intent.setFlags(536870912);
        if (z) {
            i = R.drawable.ic_pause_white_48dp;
            pendingIntent = buildPendingIntent(PAUSE, 3);
        } else {
            i = R.drawable.ic_play_arrow_white_48dp;
            pendingIntent = buildPendingIntent(PLAY, 2);
        }
        getNotificationChannelMusic();
        String str = "next";
        this.notificationManager.notify(100, new NotificationCompat.Builder(this.context, "3034").setShowWhen(false).setStyle(new MediaStyle().setShowActionsInCompactView(0, 1, 2)).setColor(this.context.getResources().getColor(R.color.black)).setDeleteIntent(buildPendingIntent("DELETE", 5)).setSmallIcon(R.drawable.icon).setContentTitle(this.title).setContentIntent(PendingIntent.getActivity(this.context, 100, intent, 268435456)).addAction(R.drawable.ic_skip_previous_white_48dp, "previous", buildPendingIntent(PREVIOUS, 1)).addAction(i, "pause", pendingIntent).addAction(R.drawable.ic_skip_next_white_48dp, str, buildPendingIntent(NEXT, 0)).build());
    }

    private PendingIntent buildPendingIntent(String str, int i) {
        Intent intent = new Intent(this.context.getApplicationContext(), JcPlayerNotificationReceiver.class);
        intent.putExtra(ACTION, str);
        return PendingIntent.getBroadcast(this.context.getApplicationContext(), i, intent, 134217728);
    }

    public void onPaused(String str) {
        createNotificationPlayer(this.title, this.iconResource);
    }

    public void updateTitle(String str, String str2) {
        this.title = str;
        createNotificationPlayer(str, this.iconResource);
    }

    /* access modifiers changed from: 0000 */
    public void destroyNotificationIfExists() {
        NotificationManager notificationManager2 = this.notificationManager;
        if (notificationManager2 != null) {
            try {
                notificationManager2.cancel(100);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void getNotificationChannelMusic() {
        NotificationManager notificationManager2 = (NotificationManager) this.context.getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("3034", this.context.getString(R.string.notification_music), 2);
            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationManager2.createNotificationChannel(notificationChannel);
        }
    }
}
