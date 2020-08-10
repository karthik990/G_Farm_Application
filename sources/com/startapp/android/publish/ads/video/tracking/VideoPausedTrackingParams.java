package com.startapp.android.publish.ads.video.tracking;

/* compiled from: StartAppSDK */
public class VideoPausedTrackingParams extends VideoTrackingParams {
    private static final long serialVersionUID = 1;
    private int pauseNum;
    private PauseOrigin pauseOrigin;

    /* compiled from: StartAppSDK */
    public enum PauseOrigin {
        INAPP,
        EXTERNAL
    }

    public VideoPausedTrackingParams(String str, int i, int i2, int i3, PauseOrigin pauseOrigin2, String str2) {
        super(str, i, i2, str2);
        this.pauseNum = i3;
        this.pauseOrigin = pauseOrigin2;
    }

    public int getPauseNum() {
        return this.pauseNum;
    }

    public PauseOrigin getPauseOrigin() {
        return this.pauseOrigin;
    }

    public String getQueryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCompletedQuery());
        sb.append(getPauseOriginQuery());
        sb.append(getPauseNumQuery());
        sb.append(getVideoPlayingModeQuery());
        return getQueryString(sb.toString());
    }

    private String getPauseNumQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("&pn=");
        sb.append(getPauseNum());
        return sb.toString();
    }

    private String getPauseOriginQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("&po=");
        sb.append(getPauseOrigin().toString());
        return sb.toString();
    }
}
