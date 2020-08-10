package com.mobiroller.viewholders.chat;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class ChatArchivedDialogsViewHolder_ViewBinding implements Unbinder {
    private ChatArchivedDialogsViewHolder target;
    private View view7f0a009d;

    public ChatArchivedDialogsViewHolder_ViewBinding(final ChatArchivedDialogsViewHolder chatArchivedDialogsViewHolder, View view) {
        this.target = chatArchivedDialogsViewHolder;
        chatArchivedDialogsViewHolder.archivedCountTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.archived_item_count_text_view, "field 'archivedCountTextView'", TextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.archived_item_main_layout, "field 'archivedMainLayout' and method 'openChatActivity'");
        chatArchivedDialogsViewHolder.archivedMainLayout = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.archived_item_main_layout, "field 'archivedMainLayout'", RelativeLayout.class);
        this.view7f0a009d = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                chatArchivedDialogsViewHolder.openChatActivity();
            }
        });
    }

    public void unbind() {
        ChatArchivedDialogsViewHolder chatArchivedDialogsViewHolder = this.target;
        if (chatArchivedDialogsViewHolder != null) {
            this.target = null;
            chatArchivedDialogsViewHolder.archivedCountTextView = null;
            chatArchivedDialogsViewHolder.archivedMainLayout = null;
            this.view7f0a009d.setOnClickListener(null);
            this.view7f0a009d = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
