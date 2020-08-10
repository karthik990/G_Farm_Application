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
import com.mobiroller.views.custom.MobirollerToolbar;

public class ChatArchivedDialogActivity_ViewBinding implements Unbinder {
    private ChatArchivedDialogActivity target;

    public ChatArchivedDialogActivity_ViewBinding(ChatArchivedDialogActivity chatArchivedDialogActivity) {
        this(chatArchivedDialogActivity, chatArchivedDialogActivity.getWindow().getDecorView());
    }

    public ChatArchivedDialogActivity_ViewBinding(ChatArchivedDialogActivity chatArchivedDialogActivity, View view) {
        this.target = chatArchivedDialogActivity;
        chatArchivedDialogActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", MobirollerToolbar.class);
        chatArchivedDialogActivity.chatArchivedList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.chat_archived_list, "field 'chatArchivedList'", RecyclerView.class);
        chatArchivedDialogActivity.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.archived_main_layout, "field 'mainLayout'", RelativeLayout.class);
        chatArchivedDialogActivity.overlayLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.archived_overlay_layout, "field 'overlayLayout'", LinearLayout.class);
        chatArchivedDialogActivity.emptyView = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_list_empty_view, "field 'emptyView'", LinearLayout.class);
        chatArchivedDialogActivity.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_text, "field 'emptyTextView'", TextView.class);
        chatArchivedDialogActivity.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view_image, "field 'emptyImageView'", ImageView.class);
    }

    public void unbind() {
        ChatArchivedDialogActivity chatArchivedDialogActivity = this.target;
        if (chatArchivedDialogActivity != null) {
            this.target = null;
            chatArchivedDialogActivity.toolbar = null;
            chatArchivedDialogActivity.chatArchivedList = null;
            chatArchivedDialogActivity.mainLayout = null;
            chatArchivedDialogActivity.overlayLayout = null;
            chatArchivedDialogActivity.emptyView = null;
            chatArchivedDialogActivity.emptyTextView = null;
            chatArchivedDialogActivity.emptyImageView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
