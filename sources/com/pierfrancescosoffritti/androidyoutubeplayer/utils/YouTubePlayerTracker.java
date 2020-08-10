package com.pierfrancescosoffritti.androidyoutubeplayer.utils;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;

public class YouTubePlayerTracker extends AbstractYouTubePlayerListener {

    /* renamed from: a */
    private PlayerState f2354a = PlayerState.UNKNOWN;

    /* renamed from: b */
    private float f2355b;

    /* renamed from: c */
    private float f2356c;

    /* renamed from: d */
    private String f2357d;

    public float getCurrentSecond() {
        return this.f2355b;
    }

    public PlayerState getState() {
        return this.f2354a;
    }

    public float getVideoDuration() {
        return this.f2356c;
    }

    public String getVideoId() {
        return this.f2357d;
    }

    public void onCurrentSecond(float f) {
        this.f2355b = f;
    }

    public void onStateChange(PlayerState playerState) {
        this.f2354a = playerState;
    }

    public void onVideoDuration(float f) {
        this.f2356c = f;
    }

    public void onVideoId(String str) {
        this.f2357d = str;
    }
}
