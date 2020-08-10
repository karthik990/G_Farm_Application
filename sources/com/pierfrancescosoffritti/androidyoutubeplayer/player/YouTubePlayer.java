package com.pierfrancescosoffritti.androidyoutubeplayer.player;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener;

public interface YouTubePlayer {
    boolean addListener(YouTubePlayerListener youTubePlayerListener);

    void cueVideo(String str, float f);

    void loadVideo(String str, float f);

    void pause();

    void play();

    boolean removeListener(YouTubePlayerListener youTubePlayerListener);

    void seekTo(float f);

    void setVolume(int i);
}
