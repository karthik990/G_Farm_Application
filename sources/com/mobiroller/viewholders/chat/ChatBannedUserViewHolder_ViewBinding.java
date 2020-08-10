package com.mobiroller.viewholders.chat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChatBannedUserViewHolder_ViewBinding implements Unbinder {
    private ChatBannedUserViewHolder target;
    private View view7f0a01da;
    private View view7f0a01dc;

    public ChatBannedUserViewHolder_ViewBinding(final ChatBannedUserViewHolder chatBannedUserViewHolder, View view) {
        this.target = chatBannedUserViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer' and method 'openChatActivity'");
        chatBannedUserViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatBannedUserViewHolder.openChatActivity();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.dialogAvatar, "field 'dialogAvatar' and method 'showImage'");
        chatBannedUserViewHolder.dialogAvatar = (CircleImageView) C0812Utils.castView(findRequiredView2, R.id.dialogAvatar, "field 'dialogAvatar'", CircleImageView.class);
        this.view7f0a01da = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatBannedUserViewHolder.showImage();
            }
        });
        chatBannedUserViewHolder.dialogName = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogName, "field 'dialogName'", TextView.class);
        chatBannedUserViewHolder.dialogUnreadBubble = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogUnreadBubble, "field 'dialogUnreadBubble'", TextView.class);
    }

    public void unbind() {
        ChatBannedUserViewHolder chatBannedUserViewHolder = this.target;
        if (chatBannedUserViewHolder != null) {
            this.target = null;
            chatBannedUserViewHolder.dialogContainer = null;
            chatBannedUserViewHolder.dialogAvatar = null;
            chatBannedUserViewHolder.dialogName = null;
            chatBannedUserViewHolder.dialogUnreadBubble = null;
            this.view7f0a01dc.setOnClickListener(null);
            this.view7f0a01dc = null;
            this.view7f0a01da.setOnClickListener(null);
            this.view7f0a01da = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
