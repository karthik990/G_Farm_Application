package com.mobiroller.fragments.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.mobiroller.mobi942763453128.R;

public class ChatUserListFragment_ViewBinding implements Unbinder {
    private ChatUserListFragment target;

    public ChatUserListFragment_ViewBinding(ChatUserListFragment chatUserListFragment, View view) {
        this.target = chatUserListFragment;
        chatUserListFragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.top_layout, "field 'mainLayout'", RelativeLayout.class);
        chatUserListFragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        chatUserListFragment.chatList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.chat_list, "field 'chatList'", RecyclerView.class);
        chatUserListFragment.emptyView = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_list_empty_view, "field 'emptyView'", LinearLayout.class);
        chatUserListFragment.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_text, "field 'emptyTextView'", TextView.class);
        chatUserListFragment.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_image, "field 'emptyImageView'", ImageView.class);
        chatUserListFragment.loadMoreProgressView = (SpinKitView) C0812Utils.findRequiredViewAsType(view, R.id.load_more_progress_view, "field 'loadMoreProgressView'", SpinKitView.class);
    }

    public void unbind() {
        ChatUserListFragment chatUserListFragment = this.target;
        if (chatUserListFragment != null) {
            this.target = null;
            chatUserListFragment.mainLayout = null;
            chatUserListFragment.overlayLayout = null;
            chatUserListFragment.chatList = null;
            chatUserListFragment.emptyView = null;
            chatUserListFragment.emptyTextView = null;
            chatUserListFragment.emptyImageView = null;
            chatUserListFragment.loadMoreProgressView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
