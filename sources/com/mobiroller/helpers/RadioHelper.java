package com.mobiroller.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import androidx.core.internal.view.SupportMenu;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioAttributes.Builder;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager.BitmapCallback;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager.MediaDescriptionAdapter;
import com.google.android.exoplayer2.p041ui.PlayerNotificationManager.MediaDescriptionAdapter.CC;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.MobirollerPlayerNotificationManager;
import okhttp3.Call;
import okhttp3.OkHttpClient;

public class RadioHelper {
    public static final int RADIO_NOTIFICATION_ID = 44;
    /* access modifiers changed from: private */
    public Context context;
    private Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    /* access modifiers changed from: private */
    public String mContentText = "";
    private EventListener mEventListener;
    private MetadataOutput mMetadataOutputListener;
    private SimpleExoPlayer mPlayer;
    private MobirollerPlayerNotificationManager mPlayerNotificationManager;
    private String mRadioLink;
    private String mScreenId;

    RadioHelper(Context context2) {
        this.context = context2;
    }

    private void createPlayer(String str, String str2) {
        this.mScreenId = str;
        createNotificationChannelForRadioPlayer(str, str2);
        createPlayerNotificationManager(str, str2);
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        this.mPlayer = ExoPlayerFactory.newSimpleInstance(this.context, (TrackSelector) new DefaultTrackSelector((TrackSelection.Factory) new AdaptiveTrackSelection.Factory(defaultBandwidthMeter)), (LoadControl) new DefaultLoadControl());
        this.mPlayer.setHandleWakeLock(true);
        AudioAttributes build = new Builder().setContentType(2).build();
        this.mPlayer.setHandleWakeLock(true);
        this.mPlayer.setAudioAttributes(build, true);
        this.mPlayerNotificationManager.setPlayer(this.mPlayer);
        this.dataSourceFactory = new DefaultDataSourceFactory(this.context, (TransferListener) null, (Factory) new OkHttpDataSourceFactory((Call.Factory) new OkHttpClient(), "SomeUserAgent", (TransferListener) defaultBandwidthMeter));
        this.extractorsFactory = new DefaultExtractorsFactory();
        EventListener eventListener = this.mEventListener;
        if (eventListener != null) {
            this.mPlayer.addListener(eventListener);
        }
        MetadataOutput metadataOutput = this.mMetadataOutputListener;
        if (metadataOutput != null) {
            this.mPlayer.addMetadataOutput(metadataOutput);
        }
    }

    public void play(String str, String str2, String str3) {
        if (str == null) {
            throw new NullPointerException("radioLink cannot be null");
        } else if (str2 == null) {
            throw new NullPointerException("screenId cannot be null");
        } else if (str3 == null) {
            throw new NullPointerException("screenName cannot be null");
        } else if (this.mPlayer == null) {
            this.mRadioLink = str;
            createPlayer(str2, str3);
            setMediaSource();
            this.mPlayer.setPlayWhenReady(true);
        } else {
            String str4 = this.mRadioLink;
            if (str4 == null || str.equals(str4)) {
                this.mPlayer.setPlayWhenReady(true);
                return;
            }
            this.mRadioLink = str;
            setMediaSource();
            this.mPlayer.setPlayWhenReady(true);
        }
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [com.google.android.exoplayer2.source.MediaSource] */
    /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.exoplayer2.source.hls.HlsMediaSource] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setMediaSource() {
        /*
            r7 = this;
            java.lang.String r0 = r7.mRadioLink
            java.lang.String r1 = ".m3u8"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x001c
            com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory r0 = new com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory
            com.google.android.exoplayer2.upstream.DataSource$Factory r1 = r7.dataSourceFactory
            r0.<init>(r1)
            java.lang.String r1 = r7.mRadioLink
            android.net.Uri r1 = android.net.Uri.parse(r1)
            com.google.android.exoplayer2.source.hls.HlsMediaSource r0 = r0.createMediaSource(r1)
            goto L_0x002e
        L_0x001c:
            com.google.android.exoplayer2.source.ExtractorMediaSource r0 = new com.google.android.exoplayer2.source.ExtractorMediaSource
            java.lang.String r1 = r7.mRadioLink
            android.net.Uri r2 = android.net.Uri.parse(r1)
            com.google.android.exoplayer2.upstream.DataSource$Factory r3 = r7.dataSourceFactory
            com.google.android.exoplayer2.extractor.ExtractorsFactory r4 = r7.extractorsFactory
            r5 = 0
            r6 = 0
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6)
        L_0x002e:
            com.google.android.exoplayer2.SimpleExoPlayer r1 = r7.mPlayer
            r1.prepare(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.RadioHelper.setMediaSource():void");
    }

    public boolean getIsPlayWhenReady() {
        SimpleExoPlayer simpleExoPlayer = this.mPlayer;
        if (simpleExoPlayer == null) {
            return false;
        }
        return simpleExoPlayer.getPlayWhenReady();
    }

    public void setPlayWhenReady() {
        this.mPlayer.setPlayWhenReady(false);
    }

    public void setEventListener(EventListener eventListener) {
        EventListener eventListener2 = this.mEventListener;
        if (eventListener2 != null) {
            SimpleExoPlayer simpleExoPlayer = this.mPlayer;
            if (simpleExoPlayer != null) {
                simpleExoPlayer.removeListener(eventListener2);
            }
        }
        this.mEventListener = eventListener;
        SimpleExoPlayer simpleExoPlayer2 = this.mPlayer;
        if (simpleExoPlayer2 != null) {
            simpleExoPlayer2.addListener(this.mEventListener);
        }
    }

    public void setMetadataOutputListener(MetadataOutput metadataOutput) {
        MetadataOutput metadataOutput2 = this.mMetadataOutputListener;
        if (metadataOutput2 != null) {
            SimpleExoPlayer simpleExoPlayer = this.mPlayer;
            if (simpleExoPlayer != null) {
                simpleExoPlayer.removeMetadataOutput(metadataOutput2);
            }
        }
        this.mMetadataOutputListener = metadataOutput;
        SimpleExoPlayer simpleExoPlayer2 = this.mPlayer;
        if (simpleExoPlayer2 != null) {
            simpleExoPlayer2.addMetadataOutput(this.mMetadataOutputListener);
        }
    }

    private void createNotificationChannelForRadioPlayer(String str, String str2) {
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(str, str, 2);
            notificationChannel.setDescription(str2);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.setShowBadge(false);
            ((NotificationManager) this.context.getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
    }

    private void createPlayerNotificationManager(String str, final String str2) {
        this.mPlayerNotificationManager = new MobirollerPlayerNotificationManager(MobiRollerApplication.app, str, 44, new MediaDescriptionAdapter() {
            public PendingIntent createCurrentContentIntent(Player player) {
                return null;
            }

            public /* synthetic */ String getCurrentSubText(Player player) {
                return CC.$default$getCurrentSubText(this, player);
            }

            public String getCurrentContentTitle(Player player) {
                return UtilManager.localizationHelper().getLocalizedTitle(str2);
            }

            public String getCurrentContentText(Player player) {
                return RadioHelper.this.mContentText;
            }

            public Bitmap getCurrentLargeIcon(Player player, BitmapCallback bitmapCallback) {
                return BitmapFactory.decodeResource(RadioHelper.this.context.getResources(), R.drawable.icon);
            }
        });
        MobirollerPlayerNotificationManager mobirollerPlayerNotificationManager = this.mPlayerNotificationManager;
        mobirollerPlayerNotificationManager.isRadio = true;
        mobirollerPlayerNotificationManager.setUsePlayPauseActions(true);
    }

    public boolean isOwner(String str, String str2) {
        if (this.mPlayer != null) {
            String str3 = this.mRadioLink;
            if (!(str3 == null || this.mScreenId == null || str == null || str2 == null || !str3.equals(str) || !this.mScreenId.equals(str2))) {
                return true;
            }
        }
        return false;
    }

    public boolean getPlayerStatus(String str, String str2) {
        if (!isOwner(str, str2)) {
            return false;
        }
        return this.mPlayer.getPlayWhenReady();
    }

    public String getContentText(String str, String str2) {
        if (!isOwner(str, str2)) {
            return "";
        }
        return this.mContentText;
    }

    public void setContentText(String str) {
        if (str != null) {
            this.mContentText = str;
            this.mPlayerNotificationManager.invalidate();
        }
    }
}
