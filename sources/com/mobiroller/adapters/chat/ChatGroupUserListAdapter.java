package com.mobiroller.adapters.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.UserModel;
import com.mobiroller.viewholders.chat.ChatGroupUserViewHolder;
import java.util.List;

public class ChatGroupUserListAdapter extends Adapter<ViewHolder> {
    private Context context;
    /* access modifiers changed from: private */
    public List<UserModel> dataList;

    public ChatGroupUserListAdapter(List<UserModel> list, Context context2) {
        this.dataList = list;
        this.context = context2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ChatGroupUserViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_user_list_item, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        ChatGroupUserViewHolder chatGroupUserViewHolder = (ChatGroupUserViewHolder) viewHolder;
        chatGroupUserViewHolder.bind((UserModel) this.dataList.get(i), this.context);
        viewHolder.itemView.setBackgroundColor(((UserModel) this.dataList.get(i)).isSelected ? -16711681 : -1);
        chatGroupUserViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((UserModel) ChatGroupUserListAdapter.this.dataList.get(i)).isSelected = !((UserModel) ChatGroupUserListAdapter.this.dataList.get(i)).isSelected;
                viewHolder.itemView.setBackgroundColor(((UserModel) ChatGroupUserListAdapter.this.dataList.get(i)).isSelected ? -16711681 : -1);
                ChatGroupUserListAdapter.this.notifyItemChanged(i);
            }
        });
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void addItem(UserModel userModel) {
        this.dataList.add(0, userModel);
        notifyItemInserted(0);
    }

    public void checkItem(UserModel userModel) {
        boolean z = false;
        for (int i = 0; i < this.dataList.size(); i++) {
            if (userModel.getUid().equalsIgnoreCase(((UserModel) this.dataList.get(i)).getUid())) {
                this.dataList.set(i, userModel);
                notifyItemChanged(i);
                z = true;
            }
        }
        if (!z) {
            addItem(userModel);
        }
    }

    public List<UserModel> getList() {
        return this.dataList;
    }
}
