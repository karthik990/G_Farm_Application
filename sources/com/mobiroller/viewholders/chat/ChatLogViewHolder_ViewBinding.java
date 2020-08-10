package com.mobiroller.viewholders.chat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChatLogViewHolder_ViewBinding implements Unbinder {
    private ChatLogViewHolder target;
    private View view7f0a01dc;

    public ChatLogViewHolder_ViewBinding(final ChatLogViewHolder chatLogViewHolder, View view) {
        this.target = chatLogViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer' and method 'openReceiverProfile'");
        chatLogViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatLogViewHolder.openReceiverProfile();
            }
        });
        chatLogViewHolder.dialogSenderAvatar = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogSenderAvatar, "field 'dialogSenderAvatar'", CircleImageView.class);
        chatLogViewHolder.dialogLastMessage = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogLastMessage, "field 'dialogLastMessage'", TextView.class);
        chatLogViewHolder.dialogDate = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogDate, "field 'dialogDate'", TextView.class);
        chatLogViewHolder.senderLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.senderLayout, "field 'senderLayout'", RelativeLayout.class);
        chatLogViewHolder.messageLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.messageLayout, "field 'messageLayout'", RelativeLayout.class);
    }

    public void unbind() {
        ChatLogViewHolder chatLogViewHolder = this.target;
        if (chatLogViewHolder != null) {
            this.target = null;
            chatLogViewHolder.dialogContainer = null;
            chatLogViewHolder.dialogSenderAvatar = null;
            chatLogViewHolder.dialogLastMessage = null;
            chatLogViewHolder.dialogDate = null;
            chatLogViewHolder.senderLayout = null;
            chatLogViewHolder.messageLayout = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
