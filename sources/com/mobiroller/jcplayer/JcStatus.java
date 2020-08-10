package com.mobiroller.jcplayer;

public class JcStatus {
    private long currentPosition;
    private long duration;
    private JcAudio jcAudio;
    private PlayState playState;

    public enum PlayState {
        PLAY,
        PAUSE,
        STOP,
        UNINTIALIZED
    }

    public JcStatus() {
        this(null, 0, 0, PlayState.UNINTIALIZED);
    }

    public JcStatus(JcAudio jcAudio2, long j, long j2, PlayState playState2) {
        this.jcAudio = jcAudio2;
        this.duration = j;
        this.currentPosition = j2;
        this.playState = playState2;
    }

    public JcAudio getJcAudio() {
        return this.jcAudio;
    }

    public void setJcAudio(JcAudio jcAudio2) {
        this.jcAudio = jcAudio2;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public long getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(long j) {
        this.currentPosition = j;
    }

    public PlayState getPlayState() {
        return this.playState;
    }

    public void setPlayState(PlayState playState2) {
        this.playState = playState2;
    }
}
