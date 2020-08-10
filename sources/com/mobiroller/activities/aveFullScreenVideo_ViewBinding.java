package com.mobiroller.activities;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveFullScreenVideo_ViewBinding implements Unbinder {
    private aveFullScreenVideo target;

    public aveFullScreenVideo_ViewBinding(aveFullScreenVideo avefullscreenvideo) {
        this(avefullscreenvideo, avefullscreenvideo.getWindow().getDecorView());
    }

    public aveFullScreenVideo_ViewBinding(aveFullScreenVideo avefullscreenvideo, View view) {
        this.target = avefullscreenvideo;
        avefullscreenvideo.mVideoView = (VideoView) C0812Utils.findRequiredViewAsType(view, R.id.video_broadcast_view, "field 'mVideoView'", VideoView.class);
        avefullscreenvideo.spinnerView = (ProgressBar) C0812Utils.findRequiredViewAsType(view, R.id.my_spinner, "field 'spinnerView'", ProgressBar.class);
        avefullscreenvideo.playButton = (ImageButton) C0812Utils.findRequiredViewAsType(view, R.id.play_tv, "field 'playButton'", ImageButton.class);
        avefullscreenvideo.videoBroadcastLayout = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.video_broadcast_layout, "field 'videoBroadcastLayout'", FrameLayout.class);
    }

    public void unbind() {
        aveFullScreenVideo avefullscreenvideo = this.target;
        if (avefullscreenvideo != null) {
            this.target = null;
            avefullscreenvideo.mVideoView = null;
            avefullscreenvideo.spinnerView = null;
            avefullscreenvideo.playButton = null;
            avefullscreenvideo.videoBroadcastLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
