package com.mobiroller.fragments.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.mobiroller.mobi942763453128.R;

public class CreateGroupChatTwo extends Fragment {
    public boolean isValid() {
        return true;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.layout_group_chat_2, viewGroup, false);
    }
}
