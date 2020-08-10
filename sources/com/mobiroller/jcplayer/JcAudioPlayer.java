package com.mobiroller.jcplayer;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.mobiroller.constants.Constants;
import com.mobiroller.jcplayer.JcPlayerExceptions.AudioListNullPointerException;
import com.mobiroller.jcplayer.JcPlayerService.JcPlayerServiceBinder;
import com.mobiroller.jcplayer.JcPlayerView.JcPlayerViewServiceListener;
import com.mobiroller.jcplayer.JcPlayerView.JcPlayerViewStatusListener;
import com.mobiroller.jcplayer.JcPlayerView.OnInvalidPathListener;
import java.io.Serializable;
import java.util.List;

class JcAudioPlayer {
    private static JcAudioPlayer instance;
    private Context context;
    private JcAudio currentJcAudio;
    private int currentPositionList;
    /* access modifiers changed from: private */
    public OnInvalidPathListener invalidPathListener;
    private JcNotificationPlayerService jcNotificationPlayer;
    /* access modifiers changed from: private */
    public JcPlayerService jcPlayerService;
    /* access modifiers changed from: private */
    public JcPlayerViewServiceListener listener;
    /* access modifiers changed from: private */
    public boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            JcAudioPlayer.this.jcPlayerService = ((JcPlayerServiceBinder) iBinder).getService();
            if (JcAudioPlayer.this.listener != null) {
                JcAudioPlayer.this.jcPlayerService.registerServicePlayerListener(JcAudioPlayer.this.listener);
            }
            if (JcAudioPlayer.this.invalidPathListener != null) {
                JcAudioPlayer.this.jcPlayerService.registerInvalidPathListener(JcAudioPlayer.this.invalidPathListener);
            }
            if (JcAudioPlayer.this.statusListener != null) {
                JcAudioPlayer.this.jcPlayerService.registerStatusListener(JcAudioPlayer.this.statusListener);
            }
            JcAudioPlayer.this.mBound = true;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            JcAudioPlayer.this.mBound = false;
            JcAudioPlayer.this.playing = false;
            JcAudioPlayer.this.paused = true;
        }
    };
    /* access modifiers changed from: private */
    public boolean paused;
    /* access modifiers changed from: private */
    public boolean playing;
    private List<JcAudio> playlist;
    private int position = 1;
    private String screenId;
    /* access modifiers changed from: private */
    public JcPlayerViewStatusListener statusListener;

    JcAudioPlayer(Context context2, List<JcAudio> list, JcPlayerViewServiceListener jcPlayerViewServiceListener, String str) {
        this.context = context2;
        this.screenId = str;
        this.playlist = list;
        this.listener = jcPlayerViewServiceListener;
        instance = this;
        this.jcNotificationPlayer = new JcNotificationPlayerService(context2);
        initService();
    }

    public void setInstance(JcAudioPlayer jcAudioPlayer) {
        instance = jcAudioPlayer;
    }

    /* access modifiers changed from: 0000 */
    public void registerNotificationListener(JcPlayerViewServiceListener jcPlayerViewServiceListener) {
        this.listener = jcPlayerViewServiceListener;
        if (this.jcNotificationPlayer != null) {
            this.jcPlayerService.registerNotificationListener(jcPlayerViewServiceListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerInvalidPathListener(OnInvalidPathListener onInvalidPathListener) {
        this.invalidPathListener = onInvalidPathListener;
        JcPlayerService jcPlayerService2 = this.jcPlayerService;
        if (jcPlayerService2 != null) {
            jcPlayerService2.registerInvalidPathListener(this.invalidPathListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerServiceListener(JcPlayerViewServiceListener jcPlayerViewServiceListener) {
        this.listener = jcPlayerViewServiceListener;
        JcPlayerService jcPlayerService2 = this.jcPlayerService;
        if (jcPlayerService2 != null) {
            jcPlayerService2.registerServicePlayerListener(jcPlayerViewServiceListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerStatusListener(JcPlayerViewStatusListener jcPlayerViewStatusListener) {
        this.statusListener = jcPlayerViewStatusListener;
        JcPlayerService jcPlayerService2 = this.jcPlayerService;
        if (jcPlayerService2 != null) {
            jcPlayerService2.registerStatusListener(jcPlayerViewStatusListener);
        }
    }

    public static JcAudioPlayer getInstance() {
        return instance;
    }

    /* access modifiers changed from: 0000 */
    public void playAudio(JcAudio jcAudio) throws AudioListNullPointerException {
        List<JcAudio> list = this.playlist;
        if (list == null || list.size() == 0) {
            throw new AudioListNullPointerException();
        }
        this.currentJcAudio = jcAudio;
        this.jcPlayerService.setScreenId(this.screenId);
        this.jcPlayerService.play(this.currentJcAudio);
        updatePositionAudioList();
        this.playing = true;
        this.paused = false;
    }

    private void initService() {
        if (!this.mBound) {
            startJcPlayerService();
            return;
        }
        this.mBound = true;
        JcPlayerService jcPlayerService2 = this.jcPlayerService;
        if (jcPlayerService2 != null && !this.screenId.equals(jcPlayerService2.getScreenId())) {
            this.mBound = false;
            kill();
            startJcPlayerService();
        }
    }

    /* access modifiers changed from: 0000 */
    public void nextAudio() throws AudioListNullPointerException {
        List<JcAudio> list = this.playlist;
        if (list == null || list.size() == 0) {
            throw new AudioListNullPointerException();
        }
        if (this.currentJcAudio != null) {
            try {
                JcAudio jcAudio = (JcAudio) this.playlist.get(this.currentPositionList + this.position);
                this.currentJcAudio = jcAudio;
                this.jcPlayerService.stop();
                this.jcPlayerService.play(jcAudio);
            } catch (IndexOutOfBoundsException e) {
                playAudio((JcAudio) this.playlist.get(0));
                e.printStackTrace();
            }
        }
        updatePositionAudioList();
        this.playing = true;
        this.paused = false;
    }

    /* access modifiers changed from: 0000 */
    public void previousAudio() throws AudioListNullPointerException {
        List<JcAudio> list = this.playlist;
        if (list == null || list.size() == 0) {
            throw new AudioListNullPointerException();
        }
        if (this.currentJcAudio != null) {
            try {
                JcAudio jcAudio = (JcAudio) this.playlist.get(this.currentPositionList - this.position);
                this.currentJcAudio = jcAudio;
                this.jcPlayerService.stop();
                this.jcPlayerService.play(jcAudio);
            } catch (IndexOutOfBoundsException e) {
                playAudio((JcAudio) this.playlist.get(0));
                e.printStackTrace();
            }
        }
        updatePositionAudioList();
        this.playing = true;
        this.paused = false;
    }

    /* access modifiers changed from: 0000 */
    public void pauseAudio() {
        this.jcPlayerService.pause(this.currentJcAudio);
        this.paused = true;
        this.playing = false;
    }

    /* access modifiers changed from: 0000 */
    public void stopAudio() {
        this.jcPlayerService.stop(this.currentJcAudio);
        this.paused = true;
        this.playing = false;
    }

    /* access modifiers changed from: 0000 */
    public void continueAudio() throws AudioListNullPointerException {
        List<JcAudio> list = this.playlist;
        if (list == null || list.size() == 0) {
            throw new AudioListNullPointerException();
        }
        if (this.currentJcAudio == null) {
            this.currentJcAudio = (JcAudio) this.playlist.get(0);
        }
        playAudio(this.currentJcAudio);
        this.playing = true;
        this.paused = false;
    }

    /* access modifiers changed from: 0000 */
    public void createNewNotification(int i) {
        JcAudio jcAudio = this.currentJcAudio;
        if (jcAudio != null) {
            this.jcNotificationPlayer.createNotificationPlayer(jcAudio.getTitle(), i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateNotification() {
        this.jcNotificationPlayer.updateNotification();
    }

    /* access modifiers changed from: 0000 */
    public void seekTo(int i) {
        JcPlayerService jcPlayerService2 = this.jcPlayerService;
        if (jcPlayerService2 != null) {
            jcPlayerService2.seekTo(i);
        }
    }

    private void updatePositionAudioList() {
        if (this.playlist != null) {
            for (int i = 0; i < this.playlist.size(); i++) {
                if (((JcAudio) this.playlist.get(i)).getId() == this.currentJcAudio.getId()) {
                    this.currentPositionList = i;
                }
            }
        }
    }

    private synchronized void startJcPlayerService() {
        if (!this.mBound) {
            Intent intent = new Intent(this.context.getApplicationContext(), JcPlayerService.class);
            intent.putExtra(Constants.KEY_SCREEN_ID, this.screenId);
            intent.putExtra("PLAYLIST", (Serializable) this.playlist);
            intent.putExtra("CURRENT_AUDIO", this.currentJcAudio);
            this.context.startService(intent);
            Context context2 = this.context;
            ServiceConnection serviceConnection = this.mConnection;
            this.context.getApplicationContext();
            context2.bindService(intent, serviceConnection, 1);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isPaused() {
        return this.paused;
    }

    /* access modifiers changed from: 0000 */
    public boolean isPlaying() {
        return this.playing;
    }

    /* access modifiers changed from: 0000 */
    public void kill() {
        JcPlayerService jcPlayerService2 = this.jcPlayerService;
        if (jcPlayerService2 != null) {
            jcPlayerService2.stop();
            this.jcPlayerService.destroy();
        }
        if (this.mBound) {
            try {
                this.context.unbindService(this.mConnection);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        JcNotificationPlayerService jcNotificationPlayerService = this.jcNotificationPlayer;
        if (jcNotificationPlayerService != null) {
            jcNotificationPlayerService.destroyNotificationIfExists();
        }
        if (getInstance() != null) {
            getInstance().setInstance(null);
        }
    }

    public List<JcAudio> getPlaylist() {
        return this.playlist;
    }

    /* access modifiers changed from: 0000 */
    public JcAudio getCurrentAudio() {
        return this.jcPlayerService.getCurrentAudio();
    }

    public void destroy() {
        try {
            ((NotificationManager) this.context.getSystemService("notification")).cancel(100);
            kill();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScreenId(String str) {
        this.screenId = str;
    }

    public String getScreenId() {
        return this.screenId;
    }
}
