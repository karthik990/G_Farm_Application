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

public class ChatSearchResultViewHolder_ViewBinding implements Unbinder {
    private ChatSearchResultViewHolder target;
    private View view7f0a01da;
    private View view7f0a01dc;
    private View view7f0a01e3;
    private View view7f0a01e6;

    public ChatSearchResultViewHolder_ViewBinding(final ChatSearchResultViewHolder chatSearchResultViewHolder, View view) {
        this.target = chatSearchResultViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.dialogContainer, "field 'dialogContainer' and method 'openChatActivity'");
        chatSearchResultViewHolder.dialogContainer = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.dialogContainer, "field 'dialogContainer'", RelativeLayout.class);
        this.view7f0a01dc = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatSearchResultViewHolder.openChatActivity();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.dialogAvatar, "field 'dialogAvatar' and method 'showImage'");
        chatSearchResultViewHolder.dialogAvatar = (CircleImageView) C0812Utils.castView(findRequiredView2, R.id.dialogAvatar, "field 'dialogAvatar'", CircleImageView.class);
        this.view7f0a01da = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatSearchResultViewHolder.showImage();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.dialogName, "field 'dialogName' and method 'openChatActivity'");
        chatSearchResultViewHolder.dialogName = (TextView) C0812Utils.castView(findRequiredView3, R.id.dialogName, "field 'dialogName'", TextView.class);
        this.view7f0a01e3 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatSearchResultViewHolder.openChatActivity();
            }
        });
        chatSearchResultViewHolder.dialogUnreadBubble = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.dialogUnreadBubble, "field 'dialogUnreadBubble'", TextView.class);
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.dialogStatus, "field 'dialogStatus' and method 'openChatActivity'");
        chatSearchResultViewHolder.dialogStatus = (TextView) C0812Utils.castView(findRequiredView4, R.id.dialogStatus, "field 'dialogStatus'", TextView.class);
        this.view7f0a01e6 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatSearchResultViewHolder.openChatActivity();
            }
        });
        chatSearchResultViewHolder.dialogBadge = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.dialogBadge, "field 'dialogBadge'", ImageView.class);
    }

    public void unbind() {
        ChatSearchResultViewHolder chatSearchResultViewHolder = this.target;
        if (chatSearchResultViewHolder != null) {
            this.target = null;
            chatSearchResultViewHolder.dialogContainer = null;
            chatSearchResultViewHolder.dialogAvatar = null;
            chatSearchResultViewHolder.dialogName = null;
            chatSearchResultViewHolder.dialogUnreadBubble = null;
            chatSearchResultViewHolder.dialogStatus = null;
            chatSearchResultViewHolder.dialogBadge = null;
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
