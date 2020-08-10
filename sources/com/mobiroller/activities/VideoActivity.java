package com.mobiroller.activities;

import android.app.Activity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.StartMedia;
import com.mobiroller.models.events.StopMedia;
import com.mobiroller.util.AdManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VideoActivity extends YouTubeFailureRecoveryActivity {
    private static final String TAG = "VideoActivity";
    private YouTubePlayer player;
    @BindView(2131363449)
    YouTubePlayerView youtubeView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.playerview_demo);
        ButterKnife.bind((Activity) this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.youtubeView.initialize(getString(R.string.youtube_key), this);
    }

    public void onInitializationSuccess(Provider provider, final YouTubePlayer youTubePlayer, boolean z) {
        this.player = youTubePlayer;
        String string = getIntent().getExtras().getString("web_url");
        youTubePlayer.setPlayerStyle(PlayerStyle.DEFAULT);
        youTubePlayer.setFullscreen(true);
        youTubePlayer.loadVideo(string);
        youTubePlayer.setPlayerStateChangeListener(new PlayerStateChangeListener() {
            public void onAdStarted() {
            }

            public void onError(ErrorReason errorReason) {
            }

            public void onLoaded(String str) {
            }

            public void onLoading() {
            }

            public void onVideoEnded() {
            }

            public void onVideoStarted() {
                if (AdManager.isAdOpen) {
                    YouTubePlayer youTubePlayer = youTubePlayer;
                    if (youTubePlayer != null) {
                        youTubePlayer.pause();
                    }
                }
            }
        });
        if (AdManager.isAdOpen && youTubePlayer != null) {
            youTubePlayer.pause();
        }
    }

    /* access modifiers changed from: protected */
    public Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Subscribe
    public void onPostStopMedia(StopMedia stopMedia) {
        YouTubePlayer youTubePlayer = this.player;
        if (youTubePlayer != null) {
            youTubePlayer.pause();
        }
    }

    @Subscribe
    public void onPostStartMedia(StartMedia startMedia) {
        YouTubePlayer youTubePlayer = this.player;
        if (youTubePlayer != null) {
            youTubePlayer.play();
        }
    }
}
