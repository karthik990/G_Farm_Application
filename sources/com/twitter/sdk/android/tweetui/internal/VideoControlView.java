package com.twitter.sdk.android.tweetui.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.twitter.sdk.android.tweetui.C5234R;

public class VideoControlView extends FrameLayout {
    static final int FADE_DURATION_MS = 150;
    static final long PROGRESS_BAR_TICKS = 1000;
    private static final int SHOW_PROGRESS_MSG = 1001;
    TextView currentTime;
    TextView duration;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1001 && VideoControlView.this.player != null) {
                VideoControlView.this.updateProgress();
                VideoControlView.this.updateStateControl();
                if (VideoControlView.this.isShowing() && VideoControlView.this.player.isPlaying()) {
                    sendMessageDelayed(obtainMessage(1001), 500);
                }
            }
        }
    };
    MediaPlayerControl player;
    SeekBar seekBar;
    ImageButton stateControl;

    public interface MediaPlayerControl {
        int getBufferPercentage();

        int getCurrentPosition();

        int getDuration();

        boolean isPlaying();

        void pause();

        void seekTo(int i);

        void start();
    }

    public VideoControlView(Context context) {
        super(context);
    }

    public VideoControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VideoControlView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.player = mediaPlayerControl;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        initSubviews();
    }

    /* access modifiers changed from: 0000 */
    public void initSubviews() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C5234R.layout.tw__video_control, this);
        this.stateControl = (ImageButton) findViewById(C5234R.C5237id.tw__state_control);
        this.currentTime = (TextView) findViewById(C5234R.C5237id.tw__current_time);
        this.duration = (TextView) findViewById(C5234R.C5237id.tw__duration);
        this.seekBar = (SeekBar) findViewById(C5234R.C5237id.tw__progress);
        this.seekBar.setMax(1000);
        this.seekBar.setOnSeekBarChangeListener(createProgressChangeListener());
        this.stateControl.setOnClickListener(createStateControlClickListener());
        setDuration(0);
        setCurrentTime(0);
        setProgress(0, 0, 0);
    }

    /* access modifiers changed from: 0000 */
    public OnClickListener createStateControlClickListener() {
        return new OnClickListener() {
            public final void onClick(View view) {
                VideoControlView.this.lambda$createStateControlClickListener$0$VideoControlView(view);
            }
        };
    }

    public /* synthetic */ void lambda$createStateControlClickListener$0$VideoControlView(View view) {
        if (this.player.isPlaying()) {
            this.player.pause();
        } else {
            this.player.start();
        }
        show();
    }

    /* access modifiers changed from: 0000 */
    public OnSeekBarChangeListener createProgressChangeListener() {
        return new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    int duration = (int) (((long) (VideoControlView.this.player.getDuration() * i)) / 1000);
                    VideoControlView.this.player.seekTo(duration);
                    VideoControlView.this.setCurrentTime(duration);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                VideoControlView.this.handler.removeMessages(1001);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                VideoControlView.this.handler.sendEmptyMessage(1001);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public void updateProgress() {
        int duration2 = this.player.getDuration();
        int currentPosition = this.player.getCurrentPosition();
        int bufferPercentage = this.player.getBufferPercentage();
        setDuration(duration2);
        setCurrentTime(currentPosition);
        setProgress(currentPosition, duration2, bufferPercentage);
    }

    /* access modifiers changed from: 0000 */
    public void setDuration(int i) {
        this.duration.setText(MediaTimeUtils.getPlaybackTime((long) i));
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentTime(int i) {
        this.currentTime.setText(MediaTimeUtils.getPlaybackTime((long) i));
    }

    /* access modifiers changed from: 0000 */
    public void setProgress(int i, int i2, int i3) {
        this.seekBar.setProgress((int) (i2 > 0 ? (((long) i) * 1000) / ((long) i2) : 0));
        this.seekBar.setSecondaryProgress(i3 * 10);
    }

    /* access modifiers changed from: 0000 */
    public void updateStateControl() {
        if (this.player.isPlaying()) {
            setPauseDrawable();
        } else if (this.player.getCurrentPosition() > Math.max(this.player.getDuration() - 500, 0)) {
            setReplayDrawable();
        } else {
            setPlayDrawable();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setPlayDrawable() {
        this.stateControl.setImageResource(C5234R.C5236drawable.tw__video_play_btn);
        this.stateControl.setContentDescription(getContext().getString(C5234R.C5238string.tw__play));
    }

    /* access modifiers changed from: 0000 */
    public void setPauseDrawable() {
        this.stateControl.setImageResource(C5234R.C5236drawable.tw__video_pause_btn);
        this.stateControl.setContentDescription(getContext().getString(C5234R.C5238string.tw__pause));
    }

    /* access modifiers changed from: 0000 */
    public void setReplayDrawable() {
        this.stateControl.setImageResource(C5234R.C5236drawable.tw__video_replay_btn);
        this.stateControl.setContentDescription(getContext().getString(C5234R.C5238string.tw__replay));
    }

    /* access modifiers changed from: 0000 */
    public void hide() {
        this.handler.removeMessages(1001);
        AnimationUtils.fadeOut(this, 150);
    }

    /* access modifiers changed from: 0000 */
    public void show() {
        this.handler.sendEmptyMessage(1001);
        AnimationUtils.fadeIn(this, 150);
    }

    public boolean isShowing() {
        return getVisibility() == 0;
    }

    public void update() {
        this.handler.sendEmptyMessage(1001);
    }
}
