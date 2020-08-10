package com.mobiroller.adapters.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedList.Callback;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.viewholders.chat.ChatBannedUserViewHolder;
import com.mobiroller.viewholders.chat.ChatUserViewHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class ChatUserAdapter extends Adapter<ViewHolder> {
    private Activity context;
    private SortedList<ChatUserModel> dataList;
    private List<ChatUserModel> dataListCopy;
    private InterstitialAdsUtil interstitialAdsUtil;
    private boolean isBannedUsersActivity;

    public ChatUserAdapter(Activity activity, final RecyclerView recyclerView, boolean z) {
        this.isBannedUsersActivity = z;
        this.interstitialAdsUtil = new InterstitialAdsUtil(activity);
        this.dataList = new SortedList<>(ChatUserModel.class, new Callback<ChatUserModel>() {
            public int compare(ChatUserModel chatUserModel, ChatUserModel chatUserModel2) {
                return chatUserModel.compareTo(chatUserModel2);
            }

            public void onInserted(int i, int i2) {
                boolean z = i == 0 && ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0;
                ChatUserAdapter.this.notifyItemRangeInserted(i, i2);
                if (z) {
                    recyclerView.scrollToPosition(0);
                }
            }

            public void onRemoved(int i, int i2) {
                ChatUserAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            public void onMoved(int i, int i2) {
                boolean z = i2 == 0 && ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0;
                ChatUserAdapter.this.notifyItemMoved(i, i2);
                ChatUserAdapter.this.notifyItemChanged(i2);
                if (z) {
                    recyclerView.scrollToPosition(0);
                }
            }

            public void onChanged(int i, int i2) {
                boolean z = i == 0 && ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0;
                ChatUserAdapter.this.notifyItemRangeChanged(i, i2);
                if (z) {
                    recyclerView.scrollToPosition(0);
                }
            }

            public boolean areContentsTheSame(ChatUserModel chatUserModel, ChatUserModel chatUserModel2) {
                return chatUserModel.getName().equals(chatUserModel2.getName());
            }

            public boolean areItemsTheSame(ChatUserModel chatUserModel, ChatUserModel chatUserModel2) {
                return chatUserModel.uid == chatUserModel2.uid;
            }
        });
        this.context = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_user_list_item, viewGroup, false);
        if (!this.isBannedUsersActivity) {
            return new ChatUserViewHolder(inflate, this.interstitialAdsUtil);
        }
        return new ChatBannedUserViewHolder(inflate);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (!this.isBannedUsersActivity) {
            ((ChatUserViewHolder) viewHolder).bind((ChatUserModel) this.dataList.get(i), this.context);
        } else {
            ((ChatBannedUserViewHolder) viewHolder).bind((ChatUserModel) this.dataList.get(i), this.context);
        }
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void addItem(ChatUserModel chatUserModel) {
        this.dataList.add(chatUserModel);
        this.dataListCopy = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            this.dataListCopy.add(this.dataList.get(i));
        }
        EventBus.getDefault().post(new UserListEmptyEvent());
    }

    public void checkItem(ChatUserModel chatUserModel) {
        if (chatUserModel != null && chatUserModel.uid != null && chatUserModel.getName() != null) {
            boolean z = false;
            for (int i = 0; i < this.dataList.size(); i++) {
                if (chatUserModel.uid.equalsIgnoreCase(((ChatUserModel) this.dataList.get(i)).uid)) {
                    this.dataList.updateItemAt(i, chatUserModel);
                    z = true;
                }
            }
            if (!z) {
                addItem(chatUserModel);
            }
            this.dataListCopy = new ArrayList();
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                this.dataListCopy.add(this.dataList.get(i2));
            }
        }
    }

    public void filter(String str) {
        if (this.dataListCopy != null) {
            SortedList<ChatUserModel> sortedList = this.dataList;
            if (sortedList != null) {
                sortedList.clear();
                if (str.isEmpty()) {
                    this.dataList.addAll((Collection<T>) this.dataListCopy);
                } else {
                    String lowerCase = str.toLowerCase();
                    for (ChatUserModel chatUserModel : this.dataListCopy) {
                        if (chatUserModel.name.toLowerCase().contains(lowerCase) || (chatUserModel.username != null && chatUserModel.username.contains(lowerCase))) {
                            this.dataList.add(chatUserModel);
                        }
                    }
                }
                notifyDataSetChanged();
                EventBus.getDefault().post(new UserListEmptyEvent());
            }
        }
    }

    public void removeFilter() {
        if (this.dataListCopy != null) {
            SortedList<ChatUserModel> sortedList = this.dataList;
            if (sortedList != null) {
                sortedList.clear();
                this.dataList.addAll((Collection<T>) this.dataListCopy);
                notifyDataSetChanged();
                EventBus.getDefault().post(new UserListEmptyEvent());
            }
        }
    }

    public int getOnlineCount() {
        if (this.dataList == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (((ChatUserModel) this.dataList.get(i2)).isOnline != null && ((ChatUserModel) this.dataList.get(i2)).isOnline.equalsIgnoreCase("n")) {
                i++;
            }
        }
        return i;
    }

    public void removeItem(ChatUserModel chatUserModel) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (((ChatUserModel) this.dataList.get(i)).uid.equalsIgnoreCase(chatUserModel.uid)) {
                this.dataList.removeItemAt(i);
            }
        }
    }
}
