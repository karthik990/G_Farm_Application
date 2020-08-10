package com.mobiroller.jcplayer;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.google.android.exoplayer2.util.MimeTypes;
import com.mobiroller.constants.Constants;
import com.mobiroller.jcplayer.JcPlayerExceptions.AudioAssetsInvalidException;
import com.mobiroller.jcplayer.JcPlayerExceptions.AudioFilePathInvalidException;
import com.mobiroller.jcplayer.JcPlayerExceptions.AudioRawInvalidException;
import com.mobiroller.jcplayer.JcPlayerExceptions.AudioUrlInvalidException;
import com.mobiroller.jcplayer.JcPlayerView.JcPlayerViewServiceListener;
import com.mobiroller.jcplayer.JcPlayerView.JcPlayerViewStatusListener;
import com.mobiroller.jcplayer.JcPlayerView.OnInvalidPathListener;
import com.mobiroller.jcplayer.JcStatus.PlayState;
import com.mobiroller.models.events.MP3Event;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHost;
import org.greenrobot.eventbus.EventBus;

public class JcPlayerService extends Service implements OnPreparedListener, OnCompletionListener, OnBufferingUpdateListener, OnErrorListener, OnAudioFocusChangeListener {
    private static final String TAG = JcPlayerService.class.getSimpleName();
    private AudioManager audioManager;
    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (JcPlayerService.this.mediaPlayer != null && JcPlayerService.this.mediaPlayer.isPlaying()) {
                JcPlayerService jcPlayerService = JcPlayerService.this;
                jcPlayerService.pause(jcPlayerService.currentJcAudio);
            }
        }
    };
    /* access modifiers changed from: private */
    public JcAudio currentJcAudio;
    /* access modifiers changed from: private */
    public int currentTime;
    /* access modifiers changed from: private */
    public int duration;
    private List<OnInvalidPathListener> invalidPathListeners;
    private boolean isPausedFromState = false;
    /* access modifiers changed from: private */
    public boolean isPlaying;
    private JcNotificationPlayerService jcNotificationPlayer;
    /* access modifiers changed from: private */
    public List<JcPlayerViewServiceListener> jcPlayerServiceListeners;
    /* access modifiers changed from: private */
    public List<JcPlayerViewStatusListener> jcPlayerStatusListeners;
    /* access modifiers changed from: private */
    public JcStatus jcStatus = new JcStatus();
    private final IBinder mBinder = new JcPlayerServiceBinder();
    /* access modifiers changed from: private */
    public MediaPlayer mediaPlayer;
    /* access modifiers changed from: private */
    public JcPlayerViewServiceListener notificationListener;
    /* access modifiers changed from: private */
    public String screenId;

    public class JcPlayerServiceBinder extends Binder {
        public JcPlayerServiceBinder() {
        }

        public JcPlayerService getService() {
            EventBus eventBus = EventBus.getDefault();
            MP3Event mP3Event = new MP3Event(JcPlayerService.this.screenId, JcPlayerService.this.isPlaying, JcPlayerService.this.duration, JcPlayerService.this.currentTime, JcPlayerService.this.currentJcAudio);
            eventBus.post(mP3Event);
            return JcPlayerService.this;
        }
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer2, int i) {
    }

    public boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
        return false;
    }

    public void setScreenId(String str) {
        this.screenId = str;
    }

    public String getScreenId() {
        return this.screenId;
    }

    public void onAudioFocusChange(int i) {
        if (i != -3) {
            if (i == -2) {
                MediaPlayer mediaPlayer2 = this.mediaPlayer;
                if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
                    this.isPausedFromState = true;
                    pause(this.currentJcAudio);
                    this.jcNotificationPlayer.createNotificationPlayer(this.currentJcAudio.getTitle(), false);
                }
            } else if (i == -1) {
                MediaPlayer mediaPlayer3 = this.mediaPlayer;
                if (mediaPlayer3 != null && mediaPlayer3.isPlaying()) {
                    this.isPausedFromState = true;
                    pause(this.currentJcAudio);
                    this.jcNotificationPlayer.createNotificationPlayer(this.currentJcAudio.getTitle(), false);
                }
                this.audioManager.abandonAudioFocus(this);
            } else if (i == 1) {
                MediaPlayer mediaPlayer4 = this.mediaPlayer;
                if (mediaPlayer4 != null) {
                    if (!mediaPlayer4.isPlaying() && this.isPausedFromState) {
                        play(this.currentJcAudio);
                        this.jcNotificationPlayer.createNotificationPlayer(this.currentJcAudio.getTitle(), true);
                        this.isPausedFromState = false;
                    }
                    this.mediaPlayer.setVolume(1.0f, 1.0f);
                }
            }
        } else if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.setVolume(0.1f, 0.1f);
            this.jcNotificationPlayer.createNotificationPlayer(this.currentJcAudio.getTitle(), true);
        }
    }

    public void registerNotificationListener(JcPlayerViewServiceListener jcPlayerViewServiceListener) {
        this.notificationListener = jcPlayerViewServiceListener;
    }

    public void registerServicePlayerListener(JcPlayerViewServiceListener jcPlayerViewServiceListener) {
        if (this.jcPlayerServiceListeners == null) {
            this.jcPlayerServiceListeners = new ArrayList();
        }
        if (!this.jcPlayerServiceListeners.contains(jcPlayerViewServiceListener)) {
            this.jcPlayerServiceListeners.add(jcPlayerViewServiceListener);
        }
    }

    public void registerInvalidPathListener(OnInvalidPathListener onInvalidPathListener) {
        if (this.invalidPathListeners == null) {
            this.invalidPathListeners = new ArrayList();
        }
        if (!this.invalidPathListeners.contains(onInvalidPathListener)) {
            this.invalidPathListeners.add(onInvalidPathListener);
        }
    }

    public void registerStatusListener(JcPlayerViewStatusListener jcPlayerViewStatusListener) {
        if (this.jcPlayerStatusListeners == null) {
            this.jcPlayerStatusListeners = new ArrayList();
        }
        if (!this.jcPlayerStatusListeners.contains(jcPlayerViewStatusListener)) {
            this.jcPlayerStatusListeners.add(jcPlayerViewStatusListener);
        }
    }

    public IBinder onBind(Intent intent) {
        String str = Constants.KEY_SCREEN_ID;
        if (intent.hasExtra(str)) {
            this.screenId = intent.getStringExtra(str);
        }
        return this.mBinder;
    }

    public void onCreate() {
        super.onCreate();
        this.jcNotificationPlayer = new JcNotificationPlayerService(getApplicationContext());
        registerBecomingNoisyReceiver();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        String str = Constants.KEY_SCREEN_ID;
        if (intent.hasExtra(str)) {
            this.screenId = intent.getStringExtra(str);
        }
        return super.onStartCommand(intent, i, i2);
    }

    public void onDestroy() {
        super.onDestroy();
        ((NotificationManager) getSystemService("notification")).cancel(100);
        try {
            removeAudioFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            unregisterReceiver(this.becomingNoisyReceiver);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void pause(JcAudio jcAudio) {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.pause();
            this.duration = this.mediaPlayer.getDuration();
            this.currentTime = this.mediaPlayer.getCurrentPosition();
            this.isPlaying = false;
        }
        for (JcPlayerViewServiceListener onPaused : this.jcPlayerServiceListeners) {
            onPaused.onPaused(this.screenId);
        }
        JcPlayerViewServiceListener jcPlayerViewServiceListener = this.notificationListener;
        if (jcPlayerViewServiceListener != null) {
            jcPlayerViewServiceListener.onPaused(this.screenId);
        }
        List<JcPlayerViewStatusListener> list = this.jcPlayerStatusListeners;
        if (list != null) {
            for (JcPlayerViewStatusListener jcPlayerViewStatusListener : list) {
                this.jcStatus.setJcAudio(jcAudio);
                this.jcStatus.setDuration((long) this.duration);
                this.jcStatus.setCurrentPosition((long) this.currentTime);
                this.jcStatus.setPlayState(PlayState.PAUSE);
                jcPlayerViewStatusListener.onPausedStatus(this.jcStatus, this.screenId);
            }
        }
    }

    public void stop(JcAudio jcAudio) {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.pause();
            this.duration = this.mediaPlayer.getDuration();
            this.currentTime = this.mediaPlayer.getCurrentPosition();
            this.isPlaying = false;
        }
        for (JcPlayerViewServiceListener onPaused : this.jcPlayerServiceListeners) {
            onPaused.onPaused(this.screenId);
        }
        List<JcPlayerViewStatusListener> list = this.jcPlayerStatusListeners;
        if (list != null) {
            for (JcPlayerViewStatusListener jcPlayerViewStatusListener : list) {
                this.jcStatus.setJcAudio(jcAudio);
                this.jcStatus.setDuration((long) this.duration);
                this.jcStatus.setCurrentPosition((long) this.currentTime);
                this.jcStatus.setPlayState(PlayState.PAUSE);
                jcPlayerViewStatusListener.onPausedStatus(this.jcStatus, this.screenId);
            }
        }
    }

    public void destroy() {
        stop();
        stopSelf();
        ((NotificationManager) getSystemService("notification")).cancel(100);
    }

    public void stop() {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        this.isPlaying = false;
    }

    public void play(JcAudio jcAudio) {
        JcAudio jcAudio2 = this.currentJcAudio;
        this.currentJcAudio = jcAudio;
        requestAudioFocus();
        if (isAudioFileValid(jcAudio.getPath(), jcAudio.getOrigin())) {
            try {
                if (this.mediaPlayer == null) {
                    this.mediaPlayer = new MediaPlayer();
                    if (jcAudio.getOrigin() == Origin.URL) {
                        this.mediaPlayer.setDataSource(jcAudio.getPath());
                    }
                    this.mediaPlayer.prepareAsync();
                    this.mediaPlayer.setOnPreparedListener(this);
                    this.mediaPlayer.setOnBufferingUpdateListener(this);
                    this.mediaPlayer.setOnCompletionListener(this);
                    this.mediaPlayer.setOnErrorListener(this);
                } else if (this.isPlaying) {
                    stop();
                    play(jcAudio);
                } else if (jcAudio2 != jcAudio) {
                    stop();
                    play(jcAudio);
                } else {
                    this.mediaPlayer.start();
                    this.isPlaying = true;
                    if (this.jcPlayerServiceListeners != null) {
                        for (JcPlayerViewServiceListener onContinueAudio : this.jcPlayerServiceListeners) {
                            onContinueAudio.onContinueAudio(this.screenId);
                        }
                    }
                    if (this.jcPlayerStatusListeners != null) {
                        for (JcPlayerViewStatusListener jcPlayerViewStatusListener : this.jcPlayerStatusListeners) {
                            this.jcStatus.setJcAudio(jcAudio);
                            this.jcStatus.setPlayState(PlayState.PLAY);
                            this.jcStatus.setDuration((long) this.mediaPlayer.getDuration());
                            this.jcStatus.setCurrentPosition((long) this.mediaPlayer.getCurrentPosition());
                            jcPlayerViewStatusListener.onContinueAudioStatus(this.jcStatus, this.screenId);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateTimeAudio();
            for (JcPlayerViewServiceListener onPlaying : this.jcPlayerServiceListeners) {
                onPlaying.onPlaying(this.screenId);
            }
            List<JcPlayerViewStatusListener> list = this.jcPlayerStatusListeners;
            if (list != null) {
                for (JcPlayerViewStatusListener jcPlayerViewStatusListener2 : list) {
                    this.jcStatus.setJcAudio(jcAudio);
                    this.jcStatus.setPlayState(PlayState.PLAY);
                    this.jcStatus.setDuration(0);
                    this.jcStatus.setCurrentPosition(0);
                    jcPlayerViewStatusListener2.onPlayingStatus(this.jcStatus, this.screenId);
                }
            }
            JcPlayerViewServiceListener jcPlayerViewServiceListener = this.notificationListener;
            if (jcPlayerViewServiceListener != null) {
                jcPlayerViewServiceListener.onPlaying(this.screenId);
                return;
            }
            return;
        }
        try {
            throwError(jcAudio.getPath(), jcAudio.getOrigin());
        } catch (Exception e2) {
            Toast.makeText(this, e2.getLocalizedMessage(), 0).show();
        }
    }

    public void seekTo(int i) {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.seekTo(i);
        }
    }

    private void updateTimeAudio() {
        new Thread() {
            public void run() {
                while (JcPlayerService.this.isPlaying) {
                    try {
                        if (JcPlayerService.this.jcPlayerServiceListeners != null) {
                            for (JcPlayerViewServiceListener onTimeChanged : JcPlayerService.this.jcPlayerServiceListeners) {
                                onTimeChanged.onTimeChanged((long) JcPlayerService.this.mediaPlayer.getCurrentPosition(), JcPlayerService.this.screenId);
                            }
                        }
                        if (JcPlayerService.this.notificationListener != null) {
                            JcPlayerService.this.notificationListener.onTimeChanged((long) JcPlayerService.this.mediaPlayer.getCurrentPosition(), JcPlayerService.this.screenId);
                        }
                        if (JcPlayerService.this.jcPlayerStatusListeners != null) {
                            for (JcPlayerViewStatusListener jcPlayerViewStatusListener : JcPlayerService.this.jcPlayerStatusListeners) {
                                JcPlayerService.this.jcStatus.setPlayState(PlayState.PLAY);
                                JcPlayerService.this.jcStatus.setDuration((long) JcPlayerService.this.mediaPlayer.getDuration());
                                JcPlayerService.this.jcStatus.setCurrentPosition((long) JcPlayerService.this.mediaPlayer.getCurrentPosition());
                                jcPlayerViewStatusListener.onTimeChangedStatus(JcPlayerService.this.jcStatus, JcPlayerService.this.screenId);
                            }
                        }
                        Thread.sleep(200);
                    } catch (IllegalStateException | InterruptedException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void onCompletion(MediaPlayer mediaPlayer2) {
        List<JcPlayerViewServiceListener> list = this.jcPlayerServiceListeners;
        if (list != null) {
            for (JcPlayerViewServiceListener onCompletedAudio : list) {
                onCompletedAudio.onCompletedAudio(this.screenId);
            }
        }
        JcPlayerViewServiceListener jcPlayerViewServiceListener = this.notificationListener;
        if (jcPlayerViewServiceListener != null) {
            jcPlayerViewServiceListener.onCompletedAudio(this.screenId);
        }
        List<JcPlayerViewStatusListener> list2 = this.jcPlayerStatusListeners;
        if (list2 != null) {
            for (JcPlayerViewStatusListener onCompletedAudioStatus : list2) {
                onCompletedAudioStatus.onCompletedAudioStatus(this.jcStatus, this.screenId);
            }
        }
    }

    private void throwError(String str, Origin origin) {
        if (origin != Origin.URL) {
            if (origin == Origin.RAW) {
                try {
                    throw new AudioRawInvalidException(str);
                } catch (AudioRawInvalidException e) {
                    e.printStackTrace();
                }
            } else if (origin == Origin.ASSETS) {
                try {
                    throw new AudioAssetsInvalidException(str);
                } catch (AudioAssetsInvalidException e2) {
                    e2.printStackTrace();
                }
            } else if (origin == Origin.FILE_PATH) {
                try {
                    throw new AudioFilePathInvalidException(str);
                } catch (AudioFilePathInvalidException e3) {
                    e3.printStackTrace();
                }
            }
            List<OnInvalidPathListener> list = this.invalidPathListeners;
            if (list != null) {
                for (OnInvalidPathListener onPathError : list) {
                    onPathError.onPathError(this.currentJcAudio);
                }
                return;
            }
            return;
        }
        throw new AudioUrlInvalidException(str);
    }

    private boolean isAudioFileValid(String str, Origin origin) {
        boolean z = true;
        if (origin == Origin.URL) {
            if (!str.startsWith(HttpHost.DEFAULT_SCHEME_NAME) && !str.startsWith("https")) {
                z = false;
            }
            return z;
        } else if (origin == Origin.RAW) {
            if (getApplicationContext().getResources().openRawResourceFd(Integer.parseInt(str)) == null) {
                z = false;
            }
            return z;
        } else if (origin == Origin.ASSETS) {
            try {
                if (getApplicationContext().getAssets().openFd(str) == null) {
                    z = false;
                }
                return z;
            } catch (IOException unused) {
                return false;
            }
        } else if (origin == Origin.FILE_PATH) {
            return new File(str).exists();
        } else {
            return false;
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer2) {
        mediaPlayer2.start();
        this.isPlaying = true;
        this.duration = mediaPlayer2.getDuration();
        this.currentTime = mediaPlayer2.getCurrentPosition();
        updateTimeAudio();
        List<JcPlayerViewServiceListener> list = this.jcPlayerServiceListeners;
        if (list != null) {
            for (JcPlayerViewServiceListener jcPlayerViewServiceListener : list) {
                jcPlayerViewServiceListener.updateTitle(this.currentJcAudio.getTitle(), this.screenId);
                jcPlayerViewServiceListener.onPreparedAudio(this.currentJcAudio.getTitle(), mediaPlayer2.getDuration(), this.screenId);
            }
        }
        JcPlayerViewServiceListener jcPlayerViewServiceListener2 = this.notificationListener;
        if (jcPlayerViewServiceListener2 != null) {
            jcPlayerViewServiceListener2.updateTitle(this.currentJcAudio.getTitle(), this.screenId);
            this.notificationListener.onPreparedAudio(this.currentJcAudio.getTitle(), mediaPlayer2.getDuration(), this.screenId);
        }
        List<JcPlayerViewStatusListener> list2 = this.jcPlayerStatusListeners;
        if (list2 != null) {
            for (JcPlayerViewStatusListener jcPlayerViewStatusListener : list2) {
                this.jcStatus.setJcAudio(this.currentJcAudio);
                this.jcStatus.setPlayState(PlayState.PLAY);
                this.jcStatus.setDuration((long) this.duration);
                this.jcStatus.setCurrentPosition((long) this.currentTime);
                jcPlayerViewStatusListener.onPreparedAudioStatus(this.jcStatus, this.screenId);
            }
        }
    }

    public JcAudio getCurrentAudio() {
        return this.currentJcAudio;
    }

    private void requestAudioFocus() {
        this.audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.audioManager.requestAudioFocus(this, 3, 1);
    }

    private void removeAudioFocus() {
        try {
            this.audioManager.abandonAudioFocus(this);
        } catch (Exception unused) {
        }
    }

    private void registerBecomingNoisyReceiver() {
        registerReceiver(this.becomingNoisyReceiver, new IntentFilter("android.media.AUDIO_BECOMING_NOISY"));
    }
}
