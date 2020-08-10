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

public class ChatAdminUserListActivity_ViewBinding implements Unbinder {
    private ChatAdminUserListActivity target;

    public ChatAdminUserListActivity_ViewBinding(ChatAdminUserListActivity chatAdminUserListActivity) {
        this(chatAdminUserListActivity, chatAdminUserListActivity.getWindow().getDecorView());
    }

    public ChatAdminUserListActivity_ViewBinding(ChatAdminUserListActivity chatAdminUserListActivity, View view) {
        this.target = chatAdminUserListActivity;
        chatAdminUserListActivity.chatList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.chat_list, "field 'chatList'", RecyclerView.class);
        chatAdminUserListActivity.emptyView = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_list_empty_view, "field 'emptyView'", LinearLayout.class);
        chatAdminUserListActivity.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_text, "field 'emptyTextView'", TextView.class);
        chatAdminUserListActivity.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_image, "field 'emptyImageView'", ImageView.class);
        chatAdminUserListActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", MobirollerToolbar.class);
    }

    public void unbind() {
        ChatAdminUserListActivity chatAdminUserListActivity = this.target;
        if (chatAdminUserListActivity != null) {
            this.target = null;
            chatAdminUserListActivity.chatList = null;
            chatAdminUserListActivity.emptyView = null;
            chatAdminUserListActivity.emptyTextView = null;
            chatAdminUserListActivity.emptyImageView = null;
            chatAdminUserListActivity.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
