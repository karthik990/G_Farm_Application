package com.mobiroller.activities.chat;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ChatAdminLogActivity_ViewBinding implements Unbinder {
    private ChatAdminLogActivity target;

    public ChatAdminLogActivity_ViewBinding(ChatAdminLogActivity chatAdminLogActivity) {
        this(chatAdminLogActivity, chatAdminLogActivity.getWindow().getDecorView());
    }

    public ChatAdminLogActivity_ViewBinding(ChatAdminLogActivity chatAdminLogActivity, View view) {
        this.target = chatAdminLogActivity;
        chatAdminLogActivity.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.recycler_view, "field 'recyclerView'", RecyclerView.class);
    }

    public void unbind() {
        ChatAdminLogActivity chatAdminLogActivity = this.target;
        if (chatAdminLogActivity != null) {
            this.target = null;
            chatAdminLogActivity.recyclerView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
