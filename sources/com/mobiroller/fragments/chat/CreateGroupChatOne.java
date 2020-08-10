package com.mobiroller.fragments.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.mobiroller.adapters.chat.ChatGroupUserListAdapter;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.UserModel;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateGroupChatOne extends Fragment {
    private ChatGroupUserListAdapter adapter;
    @BindView(2131362480)
    RecyclerView groupChatPersonList;
    private ProgressViewHelper progressViewHelper;
    private Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_group_chat_1, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        EventBus.getDefault().register(this);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.progressViewHelper.show();
        setChatUserList();
        return inflate;
    }

    private void setChatUserList() {
        this.adapter = new ChatGroupUserListAdapter(new ArrayList(), getActivity());
        this.groupChatPersonList.setAdapter(this.adapter);
        this.groupChatPersonList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setUserAdapter(UserModel userModel) {
        if (userModel != null) {
            this.progressViewHelper.dismiss();
            if (this.adapter.getItemCount() == 0) {
                this.adapter.addItem(userModel);
            } else {
                this.adapter.checkItem(userModel);
            }
        }
    }

    public boolean isValid() {
        List list = this.adapter.getList();
        for (int i = 0; i < list.size(); i++) {
            if (((UserModel) list.get(i)).isSelected) {
                return true;
            }
        }
        Toast.makeText(getActivity(), "En az bir kişi seçmelisiniz", 0).show();
        return false;
    }
}
