package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.ScrollingTextView;

public class aveRadioBroadcastViewFragment_ViewBinding implements Unbinder {
    private aveRadioBroadcastViewFragment target;
    private View view7f0a04ca;

    public aveRadioBroadcastViewFragment_ViewBinding(final aveRadioBroadcastViewFragment averadiobroadcastviewfragment, View view) {
        this.target = averadiobroadcastviewfragment;
        averadiobroadcastviewfragment.radioTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.radio_broadcast_title, "field 'radioTitle'", TextView.class);
        averadiobroadcastviewfragment.radioBroadcastText = (ScrollingTextView) C0812Utils.findRequiredViewAsType(view, R.id.radio_broadcast_text, "field 'radioBroadcastText'", ScrollingTextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.radio_play, "field 'playButton' and method 'onClickPlayButton'");
        averadiobroadcastviewfragment.playButton = (ImageButton) C0812Utils.castView(findRequiredView, R.id.radio_play, "field 'playButton'", ImageButton.class);
        this.view7f0a04ca = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                averadiobroadcastviewfragment.onClickPlayButton();
            }
        });
        averadiobroadcastviewfragment.volumeMuteButton = (ImageButton) C0812Utils.findRequiredViewAsType(view, R.id.volume_mute, "field 'volumeMuteButton'", ImageButton.class);
        averadiobroadcastviewfragment.volumeSeekbar = (SeekBar) C0812Utils.findRequiredViewAsType(view, R.id.volume_seek_bar, "field 'volumeSeekbar'", SeekBar.class);
        averadiobroadcastviewfragment.volumeUpButton = (ImageButton) C0812Utils.findRequiredViewAsType(view, R.id.volume_up, "field 'volumeUpButton'", ImageButton.class);
        averadiobroadcastviewfragment.broadcastLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.broadcast_cover_layout, "field 'broadcastLayout'", LinearLayout.class);
        averadiobroadcastviewfragment.relativeLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.video_broadcast_layout, "field 'relativeLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveRadioBroadcastViewFragment averadiobroadcastviewfragment = this.target;
        if (averadiobroadcastviewfragment != null) {
            this.target = null;
            averadiobroadcastviewfragment.radioTitle = null;
            averadiobroadcastviewfragment.radioBroadcastText = null;
            averadiobroadcastviewfragment.playButton = null;
            averadiobroadcastviewfragment.volumeMuteButton = null;
            averadiobroadcastviewfragment.volumeSeekbar = null;
            averadiobroadcastviewfragment.volumeUpButton = null;
            averadiobroadcastviewfragment.broadcastLayout = null;
            averadiobroadcastviewfragment.relativeLayout = null;
            this.view7f0a04ca.setOnClickListener(null);
            this.view7f0a04ca = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
