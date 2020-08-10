package com.mobiroller.adapters.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatDialogUserEvent;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.models.events.RemoveDialogEvent;
import com.mobiroller.models.events.UnarchiveDialogEvent;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.viewholders.chat.ChatArchivedDialogViewHolder;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatArchivedDialogAdapter extends Adapter<ViewHolder> {
    private Activity context;
    private List<Object> dataList;
    private InterstitialAdsUtil interstitialAdsUtil;

    public ChatArchivedDialogAdapter(List<Object> list, Activity activity) {
        this.dataList = list;
        this.context = activity;
        this.interstitialAdsUtil = new InterstitialAdsUtil(activity);
        EventBus.getDefault().register(this);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ChatArchivedDialogViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_dialog_list_item, viewGroup, false), this.interstitialAdsUtil);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ChatArchivedDialogViewHolder) viewHolder).bind((ChatModel) this.dataList.get(i), this.context);
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    @Subscribe
    public void notifyUser(ChatDialogUserEvent chatDialogUserEvent) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.dataList.get(i) instanceof ChatModel) {
                if (chatDialogUserEvent.getUserModel().uid.equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getSenderUid())) {
                    ((ChatModel) this.dataList.get(i)).setReceiverName(chatDialogUserEvent.getUserModel().getName());
                    ((ChatModel) this.dataList.get(i)).setReceiverImageUrl(chatDialogUserEvent.getUserModel().imageUrl);
                    ((ChatModel) this.dataList.get(i)).setOnline(chatDialogUserEvent.getUserModel().isOnline);
                    notifyItemChanged(i);
                    return;
                } else if (chatDialogUserEvent.getUserModel().uid.equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getReceiverUid())) {
                    ((ChatModel) this.dataList.get(i)).setSenderName(chatDialogUserEvent.getUserModel().getName());
                    ((ChatModel) this.dataList.get(i)).setSenderImageUrl(chatDialogUserEvent.getUserModel().imageUrl);
                    ((ChatModel) this.dataList.get(i)).setOnline(chatDialogUserEvent.getUserModel().isOnline);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    @Subscribe
    public void removeItem(UnarchiveDialogEvent unarchiveDialogEvent) {
        int i = 0;
        while (true) {
            if (i >= this.dataList.size()) {
                break;
            } else if (((ChatModel) this.dataList.get(i)).getRealReceiverUid().equalsIgnoreCase(unarchiveDialogEvent.uid)) {
                this.dataList.remove(i);
                notifyItemRemoved(i);
                break;
            } else {
                i++;
            }
        }
        if (this.context != null && getItemCount() == 0) {
            EventBus.getDefault().post(new RemoveDialogEvent("-1"));
        }
    }

    @Subscribe
    public void removeItem(RemoveDialogEvent removeDialogEvent) {
        String str = "-1";
        if (!removeDialogEvent.uid.equalsIgnoreCase(str)) {
            int i = 0;
            while (true) {
                if (i < this.dataList.size()) {
                    if ((this.dataList.get(i) instanceof ChatModel) && removeDialogEvent.uid.equalsIgnoreCase(((ChatModel) this.dataList.get(i)).getRealReceiverUid())) {
                        this.dataList.remove(i);
                        notifyItemRemoved(i);
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (this.context != null && getItemCount() == 0) {
                EventBus.getDefault().post(new RemoveDialogEvent(str));
            }
        }
    }
}
