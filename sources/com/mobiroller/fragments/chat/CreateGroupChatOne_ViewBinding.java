package com.mobiroller.fragments.chat;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class CreateGroupChatOne_ViewBinding implements Unbinder {
    private CreateGroupChatOne target;

    public CreateGroupChatOne_ViewBinding(CreateGroupChatOne createGroupChatOne, View view) {
        this.target = createGroupChatOne;
        createGroupChatOne.groupChatPersonList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.group_chat_person_list, "field 'groupChatPersonList'", RecyclerView.class);
    }

    public void unbind() {
        CreateGroupChatOne createGroupChatOne = this.target;
        if (createGroupChatOne != null) {
            this.target = null;
            createGroupChatOne.groupChatPersonList = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
