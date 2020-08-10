package com.mobiroller.models.events;

import com.mobiroller.jcplayer.JcAudio;

public class MP3Event {
    private JcAudio currentJcAudio;
    private int currentTime;
    private int duration;
    private boolean isPlaying;
    private String screenId;

    public MP3Event(String str, boolean z, int i, int i2, JcAudio jcAudio) {
        this.isPlaying = z;
        this.duration = i;
        this.currentTime = i2;
        this.currentJcAudio = jcAudio;
        this.screenId = str;
    }

    public String getScreenId() {
        return this.screenId;
    }

    public void setScreenId(String str) {
        this.screenId = str;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean z) {
        this.isPlaying = z;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public int getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(int i) {
        this.currentTime = i;
    }

    public JcAudio getCurrentJcAudio() {
        return this.currentJcAudio;
    }

    public void setCurrentJcAudio(JcAudio jcAudio) {
        this.currentJcAudio = jcAudio;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MP3Event{isPlaying=");
        sb.append(this.isPlaying);
        sb.append(", duration=");
        sb.append(this.duration);
        sb.append(", currentTime=");
        sb.append(this.currentTime);
        sb.append(", currentJcAudio=");
        sb.append(this.currentJcAudio);
        sb.append(", screenId='");
        sb.append(this.screenId);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
