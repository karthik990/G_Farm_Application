package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks;

public final class zzmt extends zzls {
    private final VideoLifecycleCallbacks zzuy;

    public zzmt(VideoLifecycleCallbacks videoLifecycleCallbacks) {
        this.zzuy = videoLifecycleCallbacks;
    }

    public final void onVideoEnd() {
        this.zzuy.onVideoEnd();
    }

    public final void onVideoMute(boolean z) {
        this.zzuy.onVideoMute(z);
    }

    public final void onVideoPause() {
        this.zzuy.onVideoPause();
    }

    public final void onVideoPlay() {
        this.zzuy.onVideoPlay();
    }

    public final void onVideoStart() {
        this.zzuy.onVideoStart();
    }
}
