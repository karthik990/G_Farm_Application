package com.mobiroller.activities.chat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.mobiroller.fragments.chat.ChatUserListFragment;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.mobi942763453128.R;

public class ChatUserListActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_fragment_with_toolbar);
        new ToolbarHelper().setToolbarTitle(this, getResources().getString(R.string.user_list));
        Bundle bundle2 = new Bundle();
        String str = "roles";
        if (getIntent().hasExtra(str)) {
            bundle2.putSerializable(str, getIntent().getSerializableExtra(str));
        }
        ChatUserListFragment chatUserListFragment = new ChatUserListFragment();
        chatUserListFragment.setArguments(bundle2);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_container, chatUserListFragment, "userListFragment");
        beginTransaction.commitAllowingStateLoss();
    }
}
