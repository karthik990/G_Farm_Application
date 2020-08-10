package com.startapp.android.publish.ads.video.tracking;

import com.startapp.common.p042c.C2362f;
import java.io.Serializable;

/* compiled from: StartAppSDK */
public abstract class VideoTrackingLink implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean appendReplayParameter;
    private String replayParameter;
    @C2362f(mo20786b = TrackingSource.class)
    private TrackingSource trackingSource;
    private String trackingUrl;

    /* compiled from: StartAppSDK */
    public enum TrackingSource {
        STARTAPP,
        EXTERNAL
    }

    public String getTrackingUrl() {
        return this.trackingUrl;
    }

    public void setTrackingUrl(String str) {
        this.trackingUrl = str;
    }

    public boolean shouldAppendReplay() {
        return this.appendReplayParameter;
    }

    public void setAppendReplayParameter(boolean z) {
        this.appendReplayParameter = z;
    }

    public String getReplayParameter() {
        return this.replayParameter;
    }

    public void setReplayParameter(String str) {
        this.replayParameter = str;
    }

    public TrackingSource getTrackingSource() {
        return this.trackingSource;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("trackingSource=");
        sb.append(this.trackingSource);
        sb.append(", trackingUrl=");
        sb.append(this.trackingUrl);
        sb.append(", replayParameter=");
        sb.append(this.replayParameter);
        sb.append(", appendReplayParameter=");
        sb.append(this.appendReplayParameter);
        return sb.toString();
    }
}
