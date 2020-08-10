package com.mobiroller.adapters.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedList.Callback;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatDialogUserEvent;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.chat.LogModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.viewholders.chat.ChatLogViewHolder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatLogAdapter extends Adapter<ViewHolder> {
    private Activity context;
    private SortedList<LogModel> dataList;

    public ChatLogAdapter(Activity activity, final RecyclerView recyclerView) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.dataList = new SortedList<>(LogModel.class, new Callback<LogModel>() {
            public int compare(LogModel logModel, LogModel logModel2) {
                if (logModel == null || logModel2 == null) {
                    return 1;
                }
                return logModel.compareTo(logModel2);
            }

            public void onInserted(int i, int i2) {
                ChatLogAdapter.this.notifyItemRangeInserted(i, i2);
            }

            public void onRemoved(int i, int i2) {
                ChatLogAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            public void onMoved(int i, int i2) {
                ChatLogAdapter.this.notifyItemMoved(i, i2);
                ChatLogAdapter.this.notifyItemChanged(i2);
                if (i2 == 0) {
                    try {
                        if (((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0) {
                            recyclerView.smoothScrollToPosition(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onChanged(int i, int i2) {
                ChatLogAdapter.this.notifyItemRangeChanged(i, i2);
            }

            public boolean areContentsTheSame(LogModel logModel, LogModel logModel2) {
                return logModel.getTimeStamp() == logModel2.getTimeStamp();
            }

            public boolean areItemsTheSame(LogModel logModel, LogModel logModel2) {
                return logModel.getTimeStamp() == logModel2.getTimeStamp();
            }
        });
        this.context = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ChatLogViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_log_list_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ChatLogViewHolder) viewHolder).bind((LogModel) this.dataList.get(i), this.context);
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void addItem(LogModel logModel) {
        this.dataList.add(logModel);
        EventBus.getDefault().post(new UserListEmptyEvent());
    }

    public void checkItem(LogModel logModel) {
        boolean z = false;
        for (int i = 0; i < this.dataList.size(); i++) {
            if (logModel.getTimeStamp() == ((LogModel) this.dataList.get(i)).getTimeStamp()) {
                z = true;
            }
        }
        if (!z) {
            addItem(logModel);
        }
    }

    public void removeItem(LogModel logModel) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (((LogModel) this.dataList.get(i)).key.equalsIgnoreCase(logModel.key)) {
                this.dataList.removeItemAt(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    @Subscribe
    public void notifyUser(ChatDialogUserEvent chatDialogUserEvent) {
        ChatUserModel userModel = chatDialogUserEvent.getUserModel();
        if (userModel != null) {
            for (int i = 0; i < this.dataList.size(); i++) {
                LogModel logModel = (LogModel) this.dataList.get(i);
                if (logModel.receiverUser == null && userModel.uid.equalsIgnoreCase(logModel.getReceiverUid())) {
                    logModel.receiverUser = userModel;
                    notifyItemChanged(i);
                } else if (logModel.authUser == null && userModel.uid.equalsIgnoreCase(logModel.getAuthUid())) {
                    logModel.authUser = userModel;
                    notifyItemChanged(i);
                }
            }
        }
    }
}
