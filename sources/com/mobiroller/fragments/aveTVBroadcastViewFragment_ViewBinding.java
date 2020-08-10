package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveTVBroadcastViewFragment_ViewBinding implements Unbinder {
    private aveTVBroadcastViewFragment target;

    public aveTVBroadcastViewFragment_ViewBinding(aveTVBroadcastViewFragment avetvbroadcastviewfragment, View view) {
        this.target = avetvbroadcastviewfragment;
        avetvbroadcastviewfragment.spinnerView = (ProgressBar) C0812Utils.findRequiredViewAsType(view, R.id.my_spinner, "field 'spinnerView'", ProgressBar.class);
        avetvbroadcastviewfragment.playButton = (ImageButton) C0812Utils.findRequiredViewAsType(view, R.id.play_tv, "field 'playButton'", ImageButton.class);
        avetvbroadcastviewfragment.videoBroadcastLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.video_broadcast_layout, "field 'videoBroadcastLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveTVBroadcastViewFragment avetvbroadcastviewfragment = this.target;
        if (avetvbroadcastviewfragment != null) {
            this.target = null;
            avetvbroadcastviewfragment.spinnerView = null;
            avetvbroadcastviewfragment.playButton = null;
            avetvbroadcastviewfragment.videoBroadcastLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
