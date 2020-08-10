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
import com.mobiroller.models.chat.ReportModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.viewholders.chat.ChatReportViewHolder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatReportAdapter extends Adapter<ViewHolder> {
    private Activity context;
    private SortedList<ReportModel> dataList;

    public ChatReportAdapter(Activity activity, final RecyclerView recyclerView) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.dataList = new SortedList<>(ReportModel.class, new Callback<ReportModel>() {
            public int compare(ReportModel reportModel, ReportModel reportModel2) {
                if (reportModel == null || reportModel2 == null) {
                    return 1;
                }
                return reportModel.compareTo(reportModel2);
            }

            public void onInserted(int i, int i2) {
                ChatReportAdapter.this.notifyItemRangeInserted(i, i2);
            }

            public void onRemoved(int i, int i2) {
                ChatReportAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            public void onMoved(int i, int i2) {
                ChatReportAdapter.this.notifyItemMoved(i, i2);
                ChatReportAdapter.this.notifyItemChanged(i2);
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
                ChatReportAdapter.this.notifyItemRangeChanged(i, i2);
            }

            public boolean areContentsTheSame(ReportModel reportModel, ReportModel reportModel2) {
                return reportModel.getReport().equalsIgnoreCase(reportModel2.getReport());
            }

            public boolean areItemsTheSame(ReportModel reportModel, ReportModel reportModel2) {
                return reportModel.getReport().equalsIgnoreCase(reportModel2.getReport());
            }
        });
        this.context = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ChatReportViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_report_list_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ChatReportViewHolder) viewHolder).bind((ReportModel) this.dataList.get(i), this.context);
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void addItem(ReportModel reportModel) {
        this.dataList.add(reportModel);
        EventBus.getDefault().post(new UserListEmptyEvent());
    }

    public void checkItem(ReportModel reportModel) {
        boolean z = false;
        for (int i = 0; i < this.dataList.size(); i++) {
            if (reportModel.getTimeStamp() == ((ReportModel) this.dataList.get(i)).getTimeStamp()) {
                z = true;
            }
        }
        if (!z) {
            addItem(reportModel);
        }
    }

    public void removeItem(ReportModel reportModel) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if (((ReportModel) this.dataList.get(i)).key.equalsIgnoreCase(reportModel.key)) {
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
                ReportModel reportModel = (ReportModel) this.dataList.get(i);
                if (reportModel.receiverUser == null && userModel.uid.equalsIgnoreCase(reportModel.getReportedUid())) {
                    reportModel.receiverUser = userModel;
                    notifyItemChanged(i);
                } else if (reportModel.senderUser == null && userModel.uid.equalsIgnoreCase(reportModel.getSenderUid())) {
                    reportModel.senderUser = userModel;
                    notifyItemChanged(i);
                }
            }
        }
    }
}
