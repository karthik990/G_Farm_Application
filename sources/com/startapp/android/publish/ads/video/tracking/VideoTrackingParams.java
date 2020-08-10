package com.startapp.android.publish.ads.video.tracking;

import com.startapp.android.publish.adsCommon.p080d.C5002b;

/* compiled from: StartAppSDK */
public class VideoTrackingParams extends C5002b {
    private static final String REPLAY_PARAMETER_PLACEHOLDER = "%startapp_replay_count%";
    private static final long serialVersionUID = 1;
    private int completed;
    protected boolean internalParamsIndicator;
    private String replayParameter;
    private boolean shouldAppendOffset;
    private String videoPlayingMode;

    public VideoTrackingParams(String str, int i, int i2, String str2) {
        super(str);
        setOffset(i2);
        this.completed = i;
        this.videoPlayingMode = str2;
    }

    public int getCompleted() {
        return this.completed;
    }

    public boolean getInternalTrackingParamsIndicator() {
        return this.internalParamsIndicator;
    }

    public VideoTrackingParams setShouldAppendOffset(boolean z) {
        this.shouldAppendOffset = z;
        return this;
    }

    public VideoTrackingParams setReplayParameter(String str) {
        this.replayParameter = str;
        return this;
    }

    public VideoTrackingParams setInternalTrackingParamsIndicator(boolean z) {
        this.internalParamsIndicator = z;
        return this;
    }

    public String getQueryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCompletedQuery());
        sb.append(getVideoPlayingModeQuery());
        return getQueryString(sb.toString());
    }

    /* access modifiers changed from: protected */
    public String getOffsetQuery() {
        if (!this.shouldAppendOffset) {
            return "";
        }
        String str = this.replayParameter;
        if (str == null) {
            return super.getOffsetQuery();
        }
        return str.replace(REPLAY_PARAMETER_PLACEHOLDER, new Integer(getOffset()).toString());
    }

    /* access modifiers changed from: protected */
    public String getCompletedQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("&cp=");
        sb.append(this.completed);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getVideoPlayingModeQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("&vpm=");
        sb.append(this.videoPlayingMode);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getQueryString(String str) {
        if (!this.internalParamsIndicator) {
            return getOffsetQuery();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(super.getQueryString());
        sb.append(str);
        return sb.toString();
    }
}
