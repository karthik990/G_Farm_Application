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

public class ChatArchivedDialogViewHolder_ViewBinding implements Unbinder {
    private ChatArchivedDialogViewHolder target;
    private View view7f0a01da;
    private View view7f0a01dc;
    private View view7f0a01dd;
    private View view7f0a01e0;
    private View view7f0a01e3;

    public ChatArchivedDialogViewHolder_ViewBinding(final ChatArchivedDialogViewHolder chatArchivedDialogViewHolder, View view) {
        this.target = chatArchivedDialogViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer', method 'openChatActivity', and method 'openOptionsDialog'");
        chatArchivedDialogViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatArchivedDialogViewHolder.openChatActivity();
            }
        });
        findRequiredView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatArchivedDialogViewHolder.openOptionsDialog();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.dialogAvatar, "field 'dialogAvatar' and method 'showImage'");
        chatArchivedDialogViewHolder.dialogAvatar = (CircleImageView) C0812Utils.castView(findRequiredView2, R.id.dialogAvatar, "field 'dialogAvatar'", CircleImageView.class);
        this.view7f0a01da = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatArchivedDialogViewHolder.showImage();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.dialogName, "field 'dialogName' and method 'openOptionsDialog'");
        chatArchivedDialogViewHolder.dialogName = (TextView) C0812Utils.castView(findRequiredView3, R.id.dialogName, "field 'dialogName'", TextView.class);
        this.view7f0a01e3 = findRequiredView3;
        findRequiredView3.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatArchivedDialogViewHolder.openOptionsDialog();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.dialogLastMessage, "field 'dialogLastMessage' and method 'openOptionsDialog'");
        chatArchivedDialogViewHolder.dialogLastMessage = (TextView) C0812Utils.castView(findRequiredView4, R.id.dialogLastMessage, "field 'dialogLastMessage'", TextView.class);
        this.view7f0a01e0 = findRequiredView4;
        findRequiredView4.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatArchivedDialogViewHolder.openOptionsDialog();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.dialogDate, "field 'dialogDate' and method 'openOptionsDialog'");
        chatArchivedDialogViewHolder.dialogDate = (TextView) C0812Utils.castView(findRequiredView5, R.id.dialogDate, "field 'dialogDate'", TextView.class);
        this.view7f0a01dd = findRequiredView5;
        findRequiredView5.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return chatArchivedDialogViewHolder.openOptionsDialog();
            }
        });
        chatArchivedDialogViewHolder.dialogUnreadBubble = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogUnreadBubble, "field 'dialogUnreadBubble'", TextView.class);
        chatArchivedDialogViewHolder.dialogBadge = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogBadge, "field 'dialogBadge'", ImageView.class);
    }

    public void unbind() {
        ChatArchivedDialogViewHolder chatArchivedDialogViewHolder = this.target;
        if (chatArchivedDialogViewHolder != null) {
            this.target = null;
            chatArchivedDialogViewHolder.dialogContainer = null;
            chatArchivedDialogViewHolder.dialogAvatar = null;
            chatArchivedDialogViewHolder.dialogName = null;
            chatArchivedDialogViewHolder.dialogLastMessage = null;
            chatArchivedDialogViewHolder.dialogDate = null;
            chatArchivedDialogViewHolder.dialogUnreadBubble = null;
            chatArchivedDialogViewHolder.dialogBadge = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc.setOnLongClickListener(null);
            this.view7f0a01dc = null;
            this.view7f0a01da.setOnClickListener(null);
            this.view7f0a01da = null;
            this.view7f0a01e3.setOnLongClickListener(null);
            this.view7f0a01e3 = null;
            this.view7f0a01e0.setOnLongClickListener(null);
            this.view7f0a01e0 = null;
            this.view7f0a01dd.setOnLongClickListener(null);
            this.view7f0a01dd = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
