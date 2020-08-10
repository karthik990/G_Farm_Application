package com.mobiroller.adapters.ecommerce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.OrderProduct;
import com.mobiroller.viewholders.ecommerce.OrderProductViewHolder;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailProductListAdapter extends Adapter<ViewHolder> {
    Activity context;
    List<OrderProduct> data;
    ECommerceRequestHelper eCommerceRequestHelper = new ECommerceRequestHelper();
    ProgressViewHelper progressViewHelper;

    public OrderDetailProductListAdapter(Activity activity, List<OrderProduct> list) {
        this.context = activity;
        this.data = list;
        this.progressViewHelper = new ProgressViewHelper(activity);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new OrderProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_ecommerce_order_product_list_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((OrderProductViewHolder) viewHolder).bind((OrderProduct) this.data.get(i));
    }

    public int getItemCount() {
        return this.data.size();
    }

    public void addItems(List<OrderProduct> list) {
        int size = this.data.size();
        this.data.addAll(list);
        notifyItemRangeChanged(size, this.data.size() - size);
    }

    public void deleteAll() {
        this.data = new ArrayList();
        notifyDataSetChanged();
    }
}
