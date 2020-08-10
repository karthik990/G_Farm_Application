package com.mobiroller.activities;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mobiroller.mobi942763453128.R;

public class VideoActivity_ViewBinding implements Unbinder {
    private VideoActivity target;

    public VideoActivity_ViewBinding(VideoActivity videoActivity) {
        this(videoActivity, videoActivity.getWindow().getDecorView());
    }

    public VideoActivity_ViewBinding(VideoActivity videoActivity, View view) {
        this.target = videoActivity;
        videoActivity.youtubeView = (YouTubePlayerView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_view, "field 'youtubeView'", YouTubePlayerView.class);
    }

    public void unbind() {
        VideoActivity videoActivity = this.target;
        if (videoActivity != null) {
            this.target = null;
            videoActivity.youtubeView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
