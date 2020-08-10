package com.mobiroller.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.StartMedia;
import com.mobiroller.models.events.StopMedia;
import com.mobiroller.util.AdManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveFullScreenVideo extends AveActivity implements OnErrorListener, OnCompletionListener, OnTouchListener, OnPreparedListener {
    @BindView(2131363362)
    VideoView mVideoView;
    @BindView(2131362970)
    ImageButton playButton;
    @BindView(2131362784)
    ProgressBar spinnerView;
    private Uri streamUri;
    @BindView(2131363361)
    FrameLayout videoBroadcastLayout;

    @Subscribe
    public void onPostStartMedia(StartMedia startMedia) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.video_broadcast_view);
        ButterKnife.bind((Activity) this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.playButton.setClickable(false);
        this.playButton.setVisibility(8);
        this.mVideoView.setOnErrorListener(this);
        this.mVideoView.setOnCompletionListener(this);
        this.mVideoView.setOnTouchListener(this);
        if (UtilManager.networkHelper().isConnected()) {
            this.screenModel = JSONStorage.getScreenModel(this.screenId);
            this.streamUri = Uri.parse(this.screenModel.getTvBroadcastLink());
            try {
                this.mVideoView.setVideoURI(this.streamUri);
                this.mVideoView.requestFocus();
                this.mVideoView.setOnPreparedListener(this);
                setRequestedOrientation(0);
            } catch (Exception unused) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_your_stream_link), 1).show();
            }
            if (AdManager.isAdOpen) {
                VideoView videoView = this.mVideoView;
                if (videoView != null) {
                    videoView.pause();
                    return;
                }
                return;
            }
            return;
        }
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_your_internet_connection), 1).show();
        finish();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    @Subscribe
    public void onPostStopMedia(StopMedia stopMedia) {
        VideoView videoView = this.mVideoView;
        if (videoView != null) {
            videoView.pause();
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        try {
            this.mVideoView.clearFocus();
            onBackPressed();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_your_stream_link), 1).show();
        } catch (Exception unused) {
            onBackPressed();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_your_stream_link), 1).show();
        }
        return true;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        this.spinnerView.setVisibility(8);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            return (action == 1 || action == 2) ? false : true;
        }
        this.mVideoView.requestFocus();
        if (this.mVideoView.isPlaying()) {
            this.mVideoView.stopPlayback();
            this.spinnerView.setVisibility(8);
            this.playButton.setVisibility(0);
        } else {
            this.spinnerView.setVisibility(0);
            this.playButton.setVisibility(8);
            this.mVideoView.setVideoURI(this.streamUri);
        }
        return true;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.spinnerView.setVisibility(8);
        this.mVideoView.start();
    }
}
