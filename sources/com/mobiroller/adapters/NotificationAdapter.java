package com.mobiroller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NotificationModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationAdapter extends Adapter<NotificationViewHolder> {
    private Context context;
    private List<NotificationModel> notificationModels;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    class NotificationViewHolder extends ViewHolder {
        TextView date;
        ImageButton deleteButton;
        RelativeLayout foreground;
        ImageView isRead;
        TextView message;
        RelativeLayout notificationCirle;

        NotificationViewHolder(View view) {
            super(view);
            this.deleteButton = (ImageButton) view.findViewById(R.id.backgroundView);
            this.foreground = (RelativeLayout) view.findViewById(R.id.foregroundView);
            this.notificationCirle = (RelativeLayout) view.findViewById(R.id.notification_cirle);
            this.message = (TextView) view.findViewById(R.id.notification_message);
            this.date = (TextView) view.findViewById(R.id.notification_date);
            this.isRead = (ImageView) view.findViewById(R.id.notification_is_read);
        }
    }

    public NotificationAdapter(List<NotificationModel> list, Context context2) {
        this.notificationModels = list;
        this.context = context2;
    }

    public NotificationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NotificationViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list_item, viewGroup, false));
    }

    public void onBindViewHolder(NotificationViewHolder notificationViewHolder, int i) {
        NotificationModel notificationModel = (NotificationModel) this.notificationModels.get(i);
        notificationViewHolder.message.setText(notificationModel.getMessage());
        notificationViewHolder.date.setText(getFormattedDate(notificationModel.getDate()));
        if (notificationModel.isRead()) {
            notificationViewHolder.date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.forma_1, 0, 0, 0);
            notificationViewHolder.message.setTextColor(Color.parseColor("#838486"));
            notificationViewHolder.message.setTypeface(null, 0);
        } else {
            notificationViewHolder.date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.forma_1_yellow, 0, 0, 0);
            notificationViewHolder.message.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            notificationViewHolder.message.setTypeface(null, 1);
        }
        if (this.selectedItems.get(i, false)) {
            notificationViewHolder.foreground.setBackgroundColor(Color.parseColor("#FFEEB8"));
            notificationViewHolder.notificationCirle.setBackground(ContextCompat.getDrawable(this.context, R.drawable.notification_cirle_selected));
            notificationViewHolder.isRead.setVisibility(0);
        } else {
            notificationViewHolder.foreground.setBackgroundColor(-1);
            notificationViewHolder.notificationCirle.setBackground(ContextCompat.getDrawable(this.context, R.drawable.notification_cirle_unselected));
            notificationViewHolder.isRead.setVisibility(8);
        }
        notificationViewHolder.notificationCirle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    public int getItemCount() {
        return this.notificationModels.size();
    }

    public void toggleSelection(int i) {
        if (this.selectedItems.get(i, false)) {
            this.selectedItems.delete(i);
        } else {
            this.selectedItems.put(i, true);
        }
        notifyDataSetChanged();
    }

    public void clearSelections() {
        this.selectedItems.clear();
        notifyDataSetChanged();
    }

    public NotificationModel getItem(int i) {
        return (NotificationModel) this.notificationModels.get(i);
    }

    public void setRead(int i) {
        ((NotificationModel) this.notificationModels.get(i)).setRead();
        notifyItemChanged(i);
    }

    public void removeData(int i) {
        this.notificationModels.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, getItemCount());
    }

    public int getSelectedItemCount() {
        return this.selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        ArrayList arrayList = new ArrayList(this.selectedItems.size());
        for (int i = 0; i < this.selectedItems.size(); i++) {
            arrayList.add(Integer.valueOf(this.selectedItems.keyAt(i)));
        }
        return arrayList;
    }

    public List<NotificationModel> getAllData() {
        return this.notificationModels;
    }

    private String getFormattedDate(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        Calendar instance2 = Calendar.getInstance();
        String str = "HH:mm";
        String str2 = "  ";
        if (instance2.get(5) == instance.get(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.context.getString(R.string.date_today));
            sb.append(str2);
            sb.append(DateFormat.format(str, instance));
            return sb.toString();
        } else if (instance2.get(5) - instance.get(5) == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.context.getString(R.string.date_yesterday));
            sb2.append(str2);
            sb2.append(DateFormat.format(str, instance));
            return sb2.toString();
        } else if (instance2.get(1) == instance.get(1)) {
            return DateFormat.format("EEEE, MMMM d, h:mm", instance).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm", instance).toString();
        }
    }
}
