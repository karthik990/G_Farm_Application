package com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackQuality;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackRate;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerError;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState;

public interface YouTubePlayerListener {
    void onApiChange();

    void onCurrentSecond(float f);

    void onError(PlayerError playerError);

    void onPlaybackQualityChange(PlaybackQuality playbackQuality);

    void onPlaybackRateChange(PlaybackRate playbackRate);

    void onReady();

    void onStateChange(PlayerState playerState);

    void onVideoDuration(float f);

    void onVideoId(String str);

    void onVideoLoadedFraction(float f);
}
