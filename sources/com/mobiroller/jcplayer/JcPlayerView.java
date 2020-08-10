package com.mobiroller.jcplayer;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.jcplayer.JcPlayerExceptions.AudioListNullPointerException;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.MP3Event;
import com.mobiroller.models.events.MP3PositionEvent;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class JcPlayerView extends LinearLayout implements OnClickListener, OnSeekBarChangeListener {
    private static final String TAG = JcPlayerView.class.getSimpleName();
    private ImageButton btnNext;
    private LottieAnimationView btnPlay;
    private ImageButton btnPrev;
    private boolean isInitialized;
    /* access modifiers changed from: private */
    public JcAudioPlayer jcAudioPlayer;
    JcPlayerViewServiceListener jcPlayerViewServiceListener = new JcPlayerViewServiceListener() {
        public void onPreparedAudio(String str, int i, String str2) {
            StringBuilder sb;
            StringBuilder sb2;
            if (JcPlayerView.this.screenId.equalsIgnoreCase(str2)) {
                JcPlayerView.this.dismissProgressBar();
                JcPlayerView.this.resetPlayerInfo();
                long j = (long) (i / 1000);
                int i2 = (int) (j / 60);
                int i3 = (int) (j % 60);
                StringBuilder sb3 = new StringBuilder();
                String str3 = "0";
                String str4 = "";
                if (i2 < 10) {
                    sb = new StringBuilder();
                    sb.append(str3);
                    sb.append(i2);
                } else {
                    sb = new StringBuilder();
                    sb.append(i2);
                    sb.append(str4);
                }
                sb3.append(sb.toString());
                sb3.append(":");
                if (i3 < 10) {
                    sb2 = new StringBuilder();
                    sb2.append(str3);
                    sb2.append(i3);
                } else {
                    sb2 = new StringBuilder();
                    sb2.append(i3);
                    sb2.append(str4);
                }
                sb3.append(sb2.toString());
                final String sb4 = sb3.toString();
                JcPlayerView.this.seekBar.setMax(i);
                JcPlayerView.this.txtDuration.post(new Runnable() {
                    public void run() {
                        JcPlayerView.this.txtDuration.setText(sb4);
                    }
                });
            }
        }

        public void onCompletedAudio(String str) {
            if (JcPlayerView.this.screenId.equalsIgnoreCase(str)) {
                JcPlayerView.this.resetPlayerInfo();
                try {
                    JcPlayerView.this.jcAudioPlayer.nextAudio();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onPaused(String str) {
            if (JcPlayerView.this.screenId.equalsIgnoreCase(str)) {
                JcPlayerView.this.animate(false);
            }
        }

        public void onContinueAudio(String str) {
            JcPlayerView.this.dismissProgressBar();
        }

        public void onPlaying(String str) {
            if (JcPlayerView.this.screenId.equalsIgnoreCase(str)) {
                JcPlayerView.this.animate(true);
            }
        }

        public void onTimeChanged(long j, String str) {
            StringBuilder sb;
            final String str2;
            if (JcPlayerView.this.screenId.equalsIgnoreCase(str)) {
                long j2 = j / 1000;
                int i = (int) (j2 / 60);
                int i2 = (int) (j2 % 60);
                String str3 = "0";
                String str4 = "";
                if (i < 10) {
                    sb = new StringBuilder();
                    sb.append(str3);
                    sb.append(i);
                } else {
                    sb = new StringBuilder();
                    sb.append(i);
                    sb.append(str4);
                }
                final String sb2 = sb.toString();
                if (i2 < 10) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str3);
                    sb3.append(i2);
                    str2 = sb3.toString();
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(i2);
                    sb4.append(str4);
                    str2 = sb4.toString();
                }
                JcPlayerView.this.seekBar.setProgress((int) j);
                JcPlayerView.this.txtCurrentDuration.post(new Runnable() {
                    public void run() {
                        TextView access$600 = JcPlayerView.this.txtCurrentDuration;
                        StringBuilder sb = new StringBuilder();
                        sb.append(sb2);
                        sb.append(":");
                        sb.append(str2);
                        access$600.setText(String.valueOf(sb.toString()));
                    }
                });
            }
        }

        public void updateTitle(final String str, String str2) {
            if (JcPlayerView.this.screenId.equalsIgnoreCase(str2)) {
                JcPlayerView.this.txtCurrentMusic.post(new Runnable() {
                    public void run() {
                        JcPlayerView.this.txtCurrentMusic.setText(str);
                    }
                });
            }
        }
    };
    private OnInvalidPathListener onInvalidPathListener = new OnInvalidPathListener() {
        public void onPathError(JcAudio jcAudio) {
            JcPlayerView.this.dismissProgressBar();
        }
    };
    private ProgressBar progressBarPlayer;
    /* access modifiers changed from: private */
    public String screenId;
    /* access modifiers changed from: private */
    public SeekBar seekBar;
    /* access modifiers changed from: private */
    public TextView txtCurrentDuration;
    /* access modifiers changed from: private */
    public TextView txtCurrentMusic;
    /* access modifiers changed from: private */
    public TextView txtDuration;

    public interface JcPlayerViewServiceListener {
        void onCompletedAudio(String str);

        void onContinueAudio(String str);

        void onPaused(String str);

        void onPlaying(String str);

        void onPreparedAudio(String str, int i, String str2);

        void onTimeChanged(long j, String str);

        void updateTitle(String str, String str2);
    }

    public interface JcPlayerViewStatusListener {
        void onCompletedAudioStatus(JcStatus jcStatus, String str);

        void onContinueAudioStatus(JcStatus jcStatus, String str);

        void onPausedStatus(JcStatus jcStatus, String str);

        void onPlayingStatus(JcStatus jcStatus, String str);

        void onPreparedAudioStatus(JcStatus jcStatus, String str);

        void onTimeChangedStatus(JcStatus jcStatus, String str);
    }

    public interface OnInvalidPathListener {
        void onPathError(JcAudio jcAudio);
    }

    public JcPlayerView(Context context) {
        super(context);
        init();
    }

    public JcPlayerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public JcPlayerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void init() {
        inflate(getContext(), R.layout.view_jcplayer, this);
        this.progressBarPlayer = (ProgressBar) findViewById(R.id.progress_bar_player);
        this.btnNext = (ImageButton) findViewById(R.id.btn_next);
        this.btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        this.btnPlay = (LottieAnimationView) findViewById(R.id.btn_play);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        this.txtDuration = (TextView) findViewById(R.id.txt_total_duration);
        this.txtCurrentDuration = (TextView) findViewById(R.id.txt_current_duration);
        this.txtCurrentMusic = (TextView) findViewById(R.id.txt_current_music);
        this.seekBar = (SeekBar) findViewById(R.id.seek_bar);
        this.btnPlay.setTag(Integer.valueOf(R.drawable.ic_play_arrow_white_48dp));
        this.seekBar.getProgressDrawable().setColorFilter(-1, Mode.MULTIPLY);
        this.seekBar.getThumb().setColorFilter(-1, Mode.SRC_ATOP);
        this.progressBarPlayer.getIndeterminateDrawable().setColorFilter(-1, Mode.MULTIPLY);
        relativeLayout.setBackgroundColor(UtilManager.sharedPrefHelper().getActionBarColor());
        this.btnNext.setOnClickListener(this);
        this.btnPrev.setOnClickListener(this);
        this.btnPlay.setOnClickListener(this);
        this.seekBar.setOnSeekBarChangeListener(this);
    }

    public void initPlaylist(List<JcAudio> list, String str) {
        this.screenId = str;
        if (!isAlreadySorted(list)) {
            sortPlaylist(list);
        }
        this.jcAudioPlayer = new JcAudioPlayer(getContext().getApplicationContext(), list, this.jcPlayerViewServiceListener, str);
        this.jcAudioPlayer.setScreenId(str);
        this.jcAudioPlayer.registerInvalidPathListener(this.onInvalidPathListener);
        this.isInitialized = true;
        EventBus.getDefault().register(this);
    }

    public void playAudio(JcAudio jcAudio) {
        showProgressBar();
        createJcAudioPlayer();
        if (!this.jcAudioPlayer.getPlaylist().contains(jcAudio)) {
            this.jcAudioPlayer.getPlaylist().add(jcAudio);
        }
        try {
            this.jcAudioPlayer.playAudio(jcAudio);
        } catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
        createNotification();
    }

    public void next() {
        if (this.jcAudioPlayer.getCurrentAudio() != null) {
            resetPlayerInfo();
            showProgressBar();
            try {
                this.jcAudioPlayer.nextAudio();
            } catch (AudioListNullPointerException e) {
                dismissProgressBar();
                e.printStackTrace();
            }
        }
    }

    public void continueAudio() {
        showProgressBar();
        try {
            this.jcAudioPlayer.continueAudio();
        } catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    public void pause() {
        this.jcAudioPlayer.pauseAudio();
    }

    public void previous() {
        resetPlayerInfo();
        showProgressBar();
        try {
            this.jcAudioPlayer.previousAudio();
        } catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (this.isInitialized) {
            if (view.getId() == R.id.btn_play) {
                if (this.btnPlay.getTag().equals(Integer.valueOf(R.drawable.ic_pause_white_48dp))) {
                    pause();
                } else {
                    continueAudio();
                }
                createNotification();
            }
            if (view.getId() == R.id.btn_next) {
                next();
            }
            if (view.getId() == R.id.btn_prev) {
                previous();
            }
        }
    }

    public void createNotification() {
        if (this.jcAudioPlayer == null) {
            return;
        }
        if (VERSION.SDK_INT >= 21) {
            this.jcAudioPlayer.createNewNotification(R.drawable.icon);
        } else {
            this.jcAudioPlayer.createNewNotification(R.drawable.icon);
        }
    }

    public boolean isPlaying() {
        return this.jcAudioPlayer.isPlaying();
    }

    private void createJcAudioPlayer() {
        if (this.jcAudioPlayer == null) {
            this.jcAudioPlayer = new JcAudioPlayer(getContext(), new ArrayList(), this.jcPlayerViewServiceListener, this.screenId);
        }
        this.jcAudioPlayer.registerInvalidPathListener(this.onInvalidPathListener);
        this.isInitialized = true;
    }

    private void sortPlaylist(List<JcAudio> list) {
        for (int i = 0; i < list.size(); i++) {
            JcAudio jcAudio = (JcAudio) list.get(i);
            jcAudio.setId((long) i);
            jcAudio.setPosition(i);
        }
    }

    private boolean isAlreadySorted(List<JcAudio> list) {
        return (list == null || ((JcAudio) list.get(0)).getPosition() == -1) ? false : true;
    }

    private void showProgressBar() {
        if (this.isInitialized) {
            EventBus.getDefault().post(new MP3PositionEvent(this.jcAudioPlayer.getCurrentAudio(), this.screenId, isPlaying()));
            this.progressBarPlayer.setVisibility(0);
            this.btnPlay.setVisibility(8);
            this.btnNext.setClickable(false);
            this.btnPrev.setClickable(false);
        }
    }

    /* access modifiers changed from: private */
    public void dismissProgressBar() {
        if (this.isInitialized) {
            EventBus.getDefault().post(new MP3PositionEvent(this.jcAudioPlayer.getCurrentAudio(), this.screenId, isPlaying()));
            this.progressBarPlayer.setVisibility(8);
            this.btnPlay.setVisibility(0);
            this.btnNext.setClickable(true);
            this.btnPrev.setClickable(true);
        }
    }

    /* access modifiers changed from: private */
    public void resetPlayerInfo() {
        this.seekBar.setProgress(0);
        this.txtCurrentMusic.setText("");
        String str = "00:00";
        this.txtCurrentDuration.setText(str);
        this.txtDuration.setText(str);
    }

    public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
        if (this.isInitialized && z) {
            JcAudioPlayer jcAudioPlayer2 = this.jcAudioPlayer;
            if (jcAudioPlayer2 != null) {
                jcAudioPlayer2.seekTo(i);
            }
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar2) {
        showProgressBar();
    }

    public void onStopTrackingTouch(SeekBar seekBar2) {
        dismissProgressBar();
    }

    public void registerServiceListener(JcPlayerViewServiceListener jcPlayerViewServiceListener2) {
        JcAudioPlayer jcAudioPlayer2 = this.jcAudioPlayer;
        if (jcAudioPlayer2 != null) {
            jcAudioPlayer2.registerServiceListener(jcPlayerViewServiceListener2);
        }
    }

    public String getScreenId() {
        return this.screenId;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(MP3Event mP3Event) {
        if (mP3Event.getScreenId() == this.screenId) {
            if (mP3Event.getDuration() != 0) {
                this.seekBar.setMax(mP3Event.getDuration());
            }
            if (mP3Event.getCurrentJcAudio() != null) {
                this.txtCurrentMusic.setText(mP3Event.getCurrentJcAudio().getTitle());
            }
            if (mP3Event.isPlaying()) {
                animate(true, mP3Event);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void animate(boolean z) {
        if (z) {
            this.btnPlay.setTag(Integer.valueOf(R.drawable.ic_pause_white_48dp));
            this.btnPlay.setProgress(0.0f);
            this.btnPlay.setMinAndMaxProgress(0.0f, 0.5f);
        } else {
            this.btnPlay.setTag(Integer.valueOf(R.drawable.ic_play_arrow_white_48dp));
            this.btnPlay.setProgress(0.5f);
            this.btnPlay.setMinAndMaxProgress(0.5f, 1.0f);
        }
        this.btnPlay.playAnimation();
        if (this.jcAudioPlayer.getCurrentAudio() != null) {
            EventBus.getDefault().post(new MP3PositionEvent(this.jcAudioPlayer.getCurrentAudio(), this.screenId, z));
        }
    }

    public void animate(boolean z, MP3Event mP3Event) {
        if (z) {
            this.btnPlay.setTag(Integer.valueOf(R.drawable.ic_pause_white_48dp));
            this.btnPlay.setProgress(0.0f);
            this.btnPlay.setMinAndMaxProgress(0.0f, 0.5f);
        } else {
            this.btnPlay.setTag(Integer.valueOf(R.drawable.ic_play_arrow_white_48dp));
            this.btnPlay.setProgress(0.5f);
            this.btnPlay.setMinAndMaxProgress(0.5f, 1.0f);
        }
        this.btnPlay.playAnimation();
        if (mP3Event.getCurrentJcAudio() != null) {
            EventBus.getDefault().post(new MP3PositionEvent(mP3Event.getCurrentJcAudio(), this.screenId, z));
        }
    }
}
