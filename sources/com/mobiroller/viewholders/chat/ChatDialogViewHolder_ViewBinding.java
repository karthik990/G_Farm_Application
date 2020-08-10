package com.mobiroller.viewholders.chat;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChatDialogViewHolder_ViewBinding implements Unbinder {
    private ChatDialogViewHolder target;
    private View view7f0a01da;
    private View view7f0a01dc;
    private View view7f0a01dd;
    private View view7f0a01e0;
    private View view7f0a01e3;

    public ChatDialogViewHolder_ViewBinding(final ChatDialogViewHolder chatDialogViewHolder, View view) {
        this.target = chatDialogViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer', method 'openChatActivity', and method 'openOptionsDialog'");
        chatDialogViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatDialogViewHolder.openChatActivity();
            }
        });
        findRequiredView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatDialogViewHolder.openOptionsDialog();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.dialogAvatar, "field 'dialogAvatar' and method 'showImage'");
        chatDialogViewHolder.dialogAvatar = (CircleImageView) C0812Utils.castView(findRequiredView2, R.id.dialogAvatar, "field 'dialogAvatar'", CircleImageView.class);
        this.view7f0a01da = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatDialogViewHolder.showImage();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.dialogName, "field 'dialogName', method 'openChatActivity', and method 'openOptionsDialog'");
        chatDialogViewHolder.dialogName = (TextView) C0812Utils.castView(findRequiredView3, R.id.dialogName, "field 'dialogName'", TextView.class);
        this.view7f0a01e3 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatDialogViewHolder.openChatActivity();
            }
        });
        findRequiredView3.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatDialogViewHolder.openOptionsDialog();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.dialogLastMessage, "field 'dialogLastMessage', method 'openChatActivity', and method 'openOptionsDialog'");
        chatDialogViewHolder.dialogLastMessage = (TextView) C0812Utils.castView(findRequiredView4, R.id.dialogLastMessage, "field 'dialogLastMessage'", TextView.class);
        this.view7f0a01e0 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatDialogViewHolder.openChatActivity();
            }
        });
        findRequiredView4.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatDialogViewHolder.openOptionsDialog();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.dialogDate, "field 'dialogDate', method 'openChatActivity', and method 'openOptionsDialog'");
        chatDialogViewHolder.dialogDate = (TextView) C0812Utils.castView(findRequiredView5, R.id.dialogDate, "field 'dialogDate'", TextView.class);
        this.view7f0a01dd = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatDialogViewHolder.openChatActivity();
            }
        });
        findRequiredView5.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatDialogViewHolder.openOptionsDialog();
            }
        });
        chatDialogViewHolder.dialogUnreadBubble = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogUnreadBubble, "field 'dialogUnreadBubble'", TextView.class);
        chatDialogViewHolder.dialogBadge = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogBadge, "field 'dialogBadge'", ImageView.class);
    }

    public void unbind() {
        ChatDialogViewHolder chatDialogViewHolder = this.target;
        if (chatDialogViewHolder != null) {
            this.target = null;
            chatDialogViewHolder.dialogContainer = null;
            chatDialogViewHolder.dialogAvatar = null;
            chatDialogViewHolder.dialogName = null;
            chatDialogViewHolder.dialogLastMessage = null;
            chatDialogViewHolder.dialogDate = null;
            chatDialogViewHolder.dialogUnreadBubble = null;
            chatDialogViewHolder.dialogBadge = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc.setOnLongClickListener(null);
            this.view7f0a01dc = null;
            this.view7f0a01da.setOnClickListener(null);
            this.view7f0a01da = null;
            this.view7f0a01e3.setOnClickListener(null);
            this.view7f0a01e3.setOnLongClickListener(null);
            this.view7f0a01e3 = null;
            this.view7f0a01e0.setOnClickListener(null);
            this.view7f0a01e0.setOnLongClickListener(null);
            this.view7f0a01e0 = null;
            this.view7f0a01dd.setOnClickListener(null);
            this.view7f0a01dd.setOnLongClickListener(null);
            this.view7f0a01dd = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
