package com.mobiroller.activities.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ChatBlockedUserListActivity_ViewBinding implements Unbinder {
    private ChatBlockedUserListActivity target;

    public ChatBlockedUserListActivity_ViewBinding(ChatBlockedUserListActivity chatBlockedUserListActivity) {
        this(chatBlockedUserListActivity, chatBlockedUserListActivity.getWindow().getDecorView());
    }

    public ChatBlockedUserListActivity_ViewBinding(ChatBlockedUserListActivity chatBlockedUserListActivity, View view) {
        this.target = chatBlockedUserListActivity;
        chatBlockedUserListActivity.chatList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.chat_list, "field 'chatList'", RecyclerView.class);
        chatBlockedUserListActivity.emptyView = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_list_empty_view, "field 'emptyView'", LinearLayout.class);
        chatBlockedUserListActivity.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_text, "field 'emptyTextView'", TextView.class);
        chatBlockedUserListActivity.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_image, "field 'emptyImageView'", ImageView.class);
        chatBlockedUserListActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", MobirollerToolbar.class);
    }

    public void unbind() {
        ChatBlockedUserListActivity chatBlockedUserListActivity = this.target;
        if (chatBlockedUserListActivity != null) {
            this.target = null;
            chatBlockedUserListActivity.chatList = null;
            chatBlockedUserListActivity.emptyView = null;
            chatBlockedUserListActivity.emptyTextView = null;
            chatBlockedUserListActivity.emptyImageView = null;
            chatBlockedUserListActivity.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
