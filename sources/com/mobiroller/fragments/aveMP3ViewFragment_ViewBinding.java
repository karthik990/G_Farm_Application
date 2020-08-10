package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.jcplayer.JcPlayerView;
import com.mobiroller.mobi942763453128.R;

public class aveMP3ViewFragment_ViewBinding implements Unbinder {
    private aveMP3ViewFragment target;

    public aveMP3ViewFragment_ViewBinding(aveMP3ViewFragment avemp3viewfragment, View view) {
        this.target = avemp3viewfragment;
        avemp3viewfragment.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list_mp3, "field 'list'", RecyclerView.class);
        avemp3viewfragment.mp3AdLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.mp3_ad_layout, "field 'mp3AdLayout'", RelativeLayout.class);
        avemp3viewfragment.mp3Layout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.mp3_layout, "field 'mp3Layout'", RelativeLayout.class);
        avemp3viewfragment.jcplayerView = (JcPlayerView) C0812Utils.findRequiredViewAsType(view, R.id.jcplayer, "field 'jcplayerView'", JcPlayerView.class);
        avemp3viewfragment.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'image'", ImageView.class);
    }

    public void unbind() {
        aveMP3ViewFragment avemp3viewfragment = this.target;
        if (avemp3viewfragment != null) {
            this.target = null;
            avemp3viewfragment.list = null;
            avemp3viewfragment.mp3AdLayout = null;
            avemp3viewfragment.mp3Layout = null;
            avemp3viewfragment.jcplayerView = null;
            avemp3viewfragment.image = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
