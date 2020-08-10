package com.mobiroller.activities.chat;

import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.mobi942763453128.R;

public class CreateGroupChat_ViewBinding implements Unbinder {
    private CreateGroupChat target;
    private View view7f0a024c;

    public CreateGroupChat_ViewBinding(CreateGroupChat createGroupChat) {
        this(createGroupChat, createGroupChat.getWindow().getDecorView());
    }

    public CreateGroupChat_ViewBinding(final CreateGroupChat createGroupChat, View view) {
        this.target = createGroupChat;
        createGroupChat.frameContainer = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.frame_container, "field 'frameContainer'", FrameLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.fab_next, "field 'fabNext' and method 'fabOnClick'");
        createGroupChat.fabNext = (FloatingActionButton) C0812Utils.castView(findRequiredView, R.id.fab_next, "field 'fabNext'", FloatingActionButton.class);
        this.view7f0a024c = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                createGroupChat.fabOnClick();
            }
        });
    }

    public void unbind() {
        CreateGroupChat createGroupChat = this.target;
        if (createGroupChat != null) {
            this.target = null;
            createGroupChat.frameContainer = null;
            createGroupChat.fabNext = null;
            this.view7f0a024c.setOnClickListener(null);
            this.view7f0a024c = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
