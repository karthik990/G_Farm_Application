package com.mobiroller.activities.chat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.fragments.chat.CreateGroupChatOne;
import com.mobiroller.fragments.chat.CreateGroupChatTwo;
import com.mobiroller.mobi942763453128.R;

public class CreateGroupChat extends AppCompatActivity {
    @BindView(2131362380)
    FloatingActionButton fabNext;
    @BindView(2131362460)
    FrameLayout frameContainer;
    boolean isFirstStep = true;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_create_chat_group);
        ButterKnife.bind((Activity) this);
        setFragment();
    }

    private void setFragment() {
        Fragment fragment;
        Bundle bundle = new Bundle();
        if (this.isFirstStep) {
            fragment = new CreateGroupChatOne();
        } else {
            this.isFirstStep = false;
            this.fabNext.setImageResource(R.drawable.ic_done_white_48dp);
            fragment = new CreateGroupChatTwo();
        }
        fragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_container, fragment, fragment.getClass().getName());
        beginTransaction.commit();
    }

    @OnClick({2131362380})
    public void fabOnClick() {
        if (isValid()) {
            setFragment();
        }
    }

    private boolean isValid() {
        if (this.isFirstStep) {
            return ((CreateGroupChatOne) getSupportFragmentManager().findFragmentById(R.id.frame_container)).isValid();
        }
        return ((CreateGroupChatTwo) getSupportFragmentManager().findFragmentById(R.id.frame_container)).isValid();
    }
}
