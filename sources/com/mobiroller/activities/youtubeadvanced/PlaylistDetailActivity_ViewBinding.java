package com.mobiroller.activities.youtubeadvanced;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class PlaylistDetailActivity_ViewBinding implements Unbinder {
    private PlaylistDetailActivity target;

    public PlaylistDetailActivity_ViewBinding(PlaylistDetailActivity playlistDetailActivity) {
        this(playlistDetailActivity, playlistDetailActivity.getWindow().getDecorView());
    }

    public PlaylistDetailActivity_ViewBinding(PlaylistDetailActivity playlistDetailActivity, View view) {
        this.target = playlistDetailActivity;
        playlistDetailActivity.mRecyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.playlist_list, "field 'mRecyclerView'", RecyclerView.class);
    }

    public void unbind() {
        PlaylistDetailActivity playlistDetailActivity = this.target;
        if (playlistDetailActivity != null) {
            this.target = null;
            playlistDetailActivity.mRecyclerView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
