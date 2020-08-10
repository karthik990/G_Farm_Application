package com.mobiroller.models.events;

import com.mobiroller.jcplayer.JcAudio;

public class MP3PositionEvent {
    private boolean isPlaying;
    private JcAudio jcAudio;
    private String screenId;

    public MP3PositionEvent(JcAudio jcAudio2, String str) {
        this.jcAudio = jcAudio2;
        this.screenId = str;
    }

    public MP3PositionEvent(JcAudio jcAudio2, String str, boolean z) {
        this.jcAudio = jcAudio2;
        this.screenId = str;
        this.isPlaying = z;
    }

    public JcAudio getJcAudio() {
        return this.jcAudio;
    }

    public void setJcAudio(JcAudio jcAudio2) {
        this.jcAudio = jcAudio2;
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
}
