package com.mobiroller.adapters.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.viewholders.user.AddressViewHolder;
import java.util.List;

public class UserAddressAdapter extends Adapter<ViewHolder> {
    AppCompatActivity context;
    List<Object> dataList;

    public UserAddressAdapter(AppCompatActivity appCompatActivity, List<Object> list) {
        this.context = appCompatActivity;
        this.dataList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AddressViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_address_item, viewGroup, false), this.context);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AddressViewHolder addressViewHolder = (AddressViewHolder) viewHolder;
        if (this.dataList.get(i) instanceof UserShippingAddressModel) {
            addressViewHolder.bindShipping((UserShippingAddressModel) this.dataList.get(i));
        } else {
            addressViewHolder.bindBilling((UserBillingAddressModel) this.dataList.get(i));
        }
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void add(Object obj) {
        this.dataList.add(obj);
        notifyDataSetChanged();
    }

    public boolean delete(String str) {
        int i = 0;
        while (i < this.dataList.size()) {
            if ((!(this.dataList.get(i) instanceof UserBillingAddressModel) || !((UserBillingAddressModel) this.dataList.get(i)).f2189id.equalsIgnoreCase(str)) && (!(this.dataList.get(i) instanceof UserShippingAddressModel) || !((UserShippingAddressModel) this.dataList.get(i)).f2189id.equalsIgnoreCase(str))) {
                i++;
            } else {
                this.dataList.remove(i);
                notifyItemRemoved(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(Object obj, String str) {
        int i = 0;
        while (i < this.dataList.size()) {
            if ((!(this.dataList.get(i) instanceof UserBillingAddressModel) || !((UserBillingAddressModel) this.dataList.get(i)).f2189id.equalsIgnoreCase(str)) && (!(this.dataList.get(i) instanceof UserShippingAddressModel) || !((UserShippingAddressModel) this.dataList.get(i)).f2189id.equalsIgnoreCase(str))) {
                i++;
            } else {
                this.dataList.set(i, obj);
                notifyItemChanged(i);
                return true;
            }
        }
        return false;
    }
}
