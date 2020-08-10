package com.mobiroller.viewholders.chat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChatReportViewHolder_ViewBinding implements Unbinder {
    private ChatReportViewHolder target;
    private View view7f0a01dc;
    private View view7f0a0530;

    public ChatReportViewHolder_ViewBinding(final ChatReportViewHolder chatReportViewHolder, View view) {
        this.target = chatReportViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer' and method 'openMessageLayout'");
        chatReportViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatReportViewHolder.openMessageLayout();
            }
        });
        chatReportViewHolder.dialogSenderAvatar = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogSenderAvatar, "field 'dialogSenderAvatar'", CircleImageView.class);
        chatReportViewHolder.dialogLastMessage = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogLastMessage, "field 'dialogLastMessage'", TextView.class);
        chatReportViewHolder.reportedName = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.reported_name, "field 'reportedName'", TextView.class);
        chatReportViewHolder.dialogDate = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogDate, "field 'dialogDate'", TextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.senderLayout, "field 'senderLayout' and method 'openReceiverProfile'");
        chatReportViewHolder.senderLayout = (RelativeLayout) C0812Utils.castView(findRequiredView2, R.id.senderLayout, "field 'senderLayout'", RelativeLayout.class);
        this.view7f0a0530 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatReportViewHolder.openReceiverProfile();
            }
        });
        chatReportViewHolder.messageLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.messageLayout, "field 'messageLayout'", RelativeLayout.class);
    }

    public void unbind() {
        ChatReportViewHolder chatReportViewHolder = this.target;
        if (chatReportViewHolder != null) {
            this.target = null;
            chatReportViewHolder.dialogContainer = null;
            chatReportViewHolder.dialogSenderAvatar = null;
            chatReportViewHolder.dialogLastMessage = null;
            chatReportViewHolder.reportedName = null;
            chatReportViewHolder.dialogDate = null;
            chatReportViewHolder.senderLayout = null;
            chatReportViewHolder.messageLayout = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc = null;
            this.view7f0a0530.setOnClickListener(null);
            this.view7f0a0530 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
