package com.mobiroller.viewholders.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChatUserViewHolder_ViewBinding implements Unbinder {
    private ChatUserViewHolder target;
    private View view7f0a01da;
    private View view7f0a01dc;
    private View view7f0a01e3;
    private View view7f0a01e6;

    public ChatUserViewHolder_ViewBinding(final ChatUserViewHolder chatUserViewHolder, View view) {
        this.target = chatUserViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer' and method 'openChatActivity'");
        chatUserViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatUserViewHolder.openChatActivity();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.dialogAvatar, "field 'dialogAvatar' and method 'showImage'");
        chatUserViewHolder.dialogAvatar = (CircleImageView) C0812Utils.castView(findRequiredView2, R.id.dialogAvatar, "field 'dialogAvatar'", CircleImageView.class);
        this.view7f0a01da = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatUserViewHolder.showImage();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.dialogName, "field 'dialogName' and method 'openChatActivity'");
        chatUserViewHolder.dialogName = (TextView) C0812Utils.castView(findRequiredView3, R.id.dialogName, "field 'dialogName'", TextView.class);
        this.view7f0a01e3 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatUserViewHolder.openChatActivity();
            }
        });
        chatUserViewHolder.dialogUnreadBubble = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogUnreadBubble, "field 'dialogUnreadBubble'", TextView.class);
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.dialogStatus, "field 'dialogStatus' and method 'openChatActivity'");
        chatUserViewHolder.dialogStatus = (TextView) C0812Utils.castView(findRequiredView4, R.id.dialogStatus, "field 'dialogStatus'", TextView.class);
        this.view7f0a01e6 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatUserViewHolder.openChatActivity();
            }
        });
        chatUserViewHolder.dialogBadge = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogBadge, "field 'dialogBadge'", ImageView.class);
    }

    public void unbind() {
        ChatUserViewHolder chatUserViewHolder = this.target;
        if (chatUserViewHolder != null) {
            this.target = null;
            chatUserViewHolder.dialogContainer = null;
            chatUserViewHolder.dialogAvatar = null;
            chatUserViewHolder.dialogName = null;
            chatUserViewHolder.dialogUnreadBubble = null;
            chatUserViewHolder.dialogStatus = null;
            chatUserViewHolder.dialogBadge = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc = null;
            this.view7f0a01da.setOnClickListener(null);
            this.view7f0a01da = null;
            this.view7f0a01e3.setOnClickListener(null);
            this.view7f0a01e3 = null;
            this.view7f0a01e6.setOnClickListener(null);
            this.view7f0a01e6 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
