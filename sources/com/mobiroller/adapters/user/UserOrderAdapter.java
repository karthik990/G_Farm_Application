package com.mobiroller.adapters.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.Order;
import com.mobiroller.viewholders.user.OrderViewHolder;
import java.util.List;

public class UserOrderAdapter extends Adapter<ViewHolder> {
    AppCompatActivity context;
    List<Order> dataList;

    public UserOrderAdapter(AppCompatActivity appCompatActivity, List<Order> list) {
        this.context = appCompatActivity;
        this.dataList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new OrderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_list_item, viewGroup, false), this.context);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((OrderViewHolder) viewHolder).bind((Order) this.dataList.get(i));
    }

    public int getItemCount() {
        return this.dataList.size();
    }
}
