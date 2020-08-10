package com.mobiroller.fragments.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ChatMessageListFragment_ViewBinding implements Unbinder {
    private ChatMessageListFragment target;

    public ChatMessageListFragment_ViewBinding(ChatMessageListFragment chatMessageListFragment, View view) {
        this.target = chatMessageListFragment;
        chatMessageListFragment.chatList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.chat_list, "field 'chatList'", RecyclerView.class);
        chatMessageListFragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.top_layout, "field 'mainLayout'", RelativeLayout.class);
        chatMessageListFragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        chatMessageListFragment.bannedLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.banned_layout, "field 'bannedLayout'", LinearLayout.class);
        chatMessageListFragment.emptyView = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_list_empty_view, "field 'emptyView'", LinearLayout.class);
        chatMessageListFragment.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_text, "field 'emptyTextView'", TextView.class);
        chatMessageListFragment.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_image, "field 'emptyImageView'", ImageView.class);
    }

    public void unbind() {
        ChatMessageListFragment chatMessageListFragment = this.target;
        if (chatMessageListFragment != null) {
            this.target = null;
            chatMessageListFragment.chatList = null;
            chatMessageListFragment.mainLayout = null;
            chatMessageListFragment.overlayLayout = null;
            chatMessageListFragment.bannedLayout = null;
            chatMessageListFragment.emptyView = null;
            chatMessageListFragment.emptyTextView = null;
            chatMessageListFragment.emptyImageView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
