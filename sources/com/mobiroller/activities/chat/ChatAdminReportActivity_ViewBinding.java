package com.mobiroller.activities.chat;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ChatAdminReportActivity_ViewBinding implements Unbinder {
    private ChatAdminReportActivity target;

    public ChatAdminReportActivity_ViewBinding(ChatAdminReportActivity chatAdminReportActivity) {
        this(chatAdminReportActivity, chatAdminReportActivity.getWindow().getDecorView());
    }

    public ChatAdminReportActivity_ViewBinding(ChatAdminReportActivity chatAdminReportActivity, View view) {
        this.target = chatAdminReportActivity;
        chatAdminReportActivity.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.recycler_view, "field 'recyclerView'", RecyclerView.class);
    }

    public void unbind() {
        ChatAdminReportActivity chatAdminReportActivity = this.target;
        if (chatAdminReportActivity != null) {
            this.target = null;
            chatAdminReportActivity.recyclerView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
