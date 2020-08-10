package com.mobiroller.activities.chat;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ChatAdminActivity_ViewBinding implements Unbinder {
    private ChatAdminActivity target;

    public ChatAdminActivity_ViewBinding(ChatAdminActivity chatAdminActivity) {
        this(chatAdminActivity, chatAdminActivity.getWindow().getDecorView());
    }

    public ChatAdminActivity_ViewBinding(ChatAdminActivity chatAdminActivity, View view) {
        this.target = chatAdminActivity;
        chatAdminActivity.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.recycler_view, "field 'recyclerView'", RecyclerView.class);
    }

    public void unbind() {
        ChatAdminActivity chatAdminActivity = this.target;
        if (chatAdminActivity != null) {
            this.target = null;
            chatAdminActivity.recyclerView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
