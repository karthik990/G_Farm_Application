package com.mobiroller.viewholders.chat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChatGroupUserViewHolder_ViewBinding implements Unbinder {
    private ChatGroupUserViewHolder target;
    private View view7f0a01dc;

    public ChatGroupUserViewHolder_ViewBinding(final ChatGroupUserViewHolder chatGroupUserViewHolder, View view) {
        this.target = chatGroupUserViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer' and method 'openChatActivity'");
        chatGroupUserViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatGroupUserViewHolder.openChatActivity();
            }
        });
        chatGroupUserViewHolder.dialogAvatar = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogAvatar, "field 'dialogAvatar'", CircleImageView.class);
        chatGroupUserViewHolder.dialogName = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogName, "field 'dialogName'", TextView.class);
    }

    public void unbind() {
        ChatGroupUserViewHolder chatGroupUserViewHolder = this.target;
        if (chatGroupUserViewHolder != null) {
            this.target = null;
            chatGroupUserViewHolder.dialogContainer = null;
            chatGroupUserViewHolder.dialogAvatar = null;
            chatGroupUserViewHolder.dialogName = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
