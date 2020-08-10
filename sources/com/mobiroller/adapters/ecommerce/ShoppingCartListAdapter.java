package com.mobiroller.adapters.ecommerce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.interfaces.ecommerce.ShoppingCartListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ShoppingCartItem;
import com.mobiroller.viewholders.ecommerce.ShoppingCartViewHolder;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartListAdapter extends Adapter<ViewHolder> {
    Activity context;
    List<ShoppingCartItem> data;
    ECommerceRequestHelper eCommerceRequestHelper = new ECommerceRequestHelper();
    ProgressViewHelper progressViewHelper;
    ShoppingCartListener shoppingCartListener;

    public ShoppingCartListAdapter(Activity activity, List<ShoppingCartItem> list, ShoppingCartListener shoppingCartListener2) {
        this.context = activity;
        this.data = list;
        this.progressViewHelper = new ProgressViewHelper(activity);
        this.shoppingCartListener = shoppingCartListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ShoppingCartViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_e_commerce_shopping_cart_item, viewGroup, false), this.shoppingCartListener);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ShoppingCartViewHolder) viewHolder).bind((ShoppingCartItem) this.data.get(i));
    }

    public int getItemCount() {
        return this.data.size();
    }

    public void addItems(List<ShoppingCartItem> list) {
        int size = this.data.size();
        this.data.addAll(list);
        notifyItemRangeChanged(size, this.data.size() - size);
    }

    public void deleteAll() {
        this.data = new ArrayList();
        notifyDataSetChanged();
    }
}
