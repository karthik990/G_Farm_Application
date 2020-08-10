package com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackQuality;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackRate;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerError;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState;

public abstract class AbstractYouTubePlayerListener implements YouTubePlayerListener {
    public void onApiChange() {
    }

    public void onCurrentSecond(float f) {
    }

    public void onError(PlayerError playerError) {
    }

    public void onPlaybackQualityChange(PlaybackQuality playbackQuality) {
    }

    public void onPlaybackRateChange(PlaybackRate playbackRate) {
    }

    public void onReady() {
    }

    public void onStateChange(PlayerState playerState) {
    }

    public void onVideoDuration(float f) {
    }

    public void onVideoId(String str) {
    }

    public void onVideoLoadedFraction(float f) {
    }
}
