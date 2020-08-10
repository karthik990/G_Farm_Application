package com.mobiroller.adapters.ecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.BankAccount;
import com.mobiroller.models.events.BankSelectedEvent;
import com.mobiroller.viewholders.ecommerce.BankAccountViewHolder;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class BankListAdapter extends Adapter<ViewHolder> {
    Context context;
    List<BankAccount> data;
    private int selectedPosition = -1;

    public BankListAdapter(Context context2, List<BankAccount> list) {
        this.context = context2;
        this.data = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new BankAccountViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_e_commerce_bank_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        BankAccountViewHolder bankAccountViewHolder = (BankAccountViewHolder) viewHolder;
        bankAccountViewHolder.bind((BankAccount) this.data.get(i));
        bankAccountViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BankListAdapter.this.setSelectedPosition(i);
            }
        });
    }

    public int getItemCount() {
        return this.data.size();
    }

    public void addItems(List<BankAccount> list) {
        int size = this.data.size();
        notifyItemRangeChanged(size, this.data.size() - size);
    }

    /* access modifiers changed from: private */
    public void setSelectedPosition(int i) {
        int i2 = this.selectedPosition;
        if (i2 != i) {
            if (i2 >= 0) {
                ((BankAccount) this.data.get(i2)).isSelected = false;
            }
            ((BankAccount) this.data.get(i)).isSelected = true;
            this.selectedPosition = i;
            EventBus.getDefault().post(new BankSelectedEvent());
            notifyDataSetChanged();
        }
    }

    public void clearSelectedPosition() {
        int i = this.selectedPosition;
        if (i >= 0) {
            ((BankAccount) this.data.get(i)).isSelected = false;
        }
        this.selectedPosition = -1;
        notifyDataSetChanged();
    }

    public BankAccount getSelectedBank() {
        List<BankAccount> list = this.data;
        if (!(list == null || this.selectedPosition == -1)) {
            int size = list.size();
            int i = this.selectedPosition;
            if (size > i) {
                return (BankAccount) this.data.get(i);
            }
        }
        return null;
    }
}
