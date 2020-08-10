package com.mobiroller.activities.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ChatUserSearchResultActivity_ViewBinding implements Unbinder {
    private ChatUserSearchResultActivity target;

    public ChatUserSearchResultActivity_ViewBinding(ChatUserSearchResultActivity chatUserSearchResultActivity) {
        this(chatUserSearchResultActivity, chatUserSearchResultActivity.getWindow().getDecorView());
    }

    public ChatUserSearchResultActivity_ViewBinding(ChatUserSearchResultActivity chatUserSearchResultActivity, View view) {
        this.target = chatUserSearchResultActivity;
        chatUserSearchResultActivity.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.top_layout, "field 'mainLayout'", RelativeLayout.class);
        chatUserSearchResultActivity.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        chatUserSearchResultActivity.chatList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.chat_list, "field 'chatList'", RecyclerView.class);
        chatUserSearchResultActivity.emptyView = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_list_empty_view, "field 'emptyView'", LinearLayout.class);
        chatUserSearchResultActivity.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_text, "field 'emptyTextView'", TextView.class);
        chatUserSearchResultActivity.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_image, "field 'emptyImageView'", ImageView.class);
    }

    public void unbind() {
        ChatUserSearchResultActivity chatUserSearchResultActivity = this.target;
        if (chatUserSearchResultActivity != null) {
            this.target = null;
            chatUserSearchResultActivity.mainLayout = null;
            chatUserSearchResultActivity.overlayLayout = null;
            chatUserSearchResultActivity.chatList = null;
            chatUserSearchResultActivity.emptyView = null;
            chatUserSearchResultActivity.emptyTextView = null;
            chatUserSearchResultActivity.emptyImageView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
