package com.mobiroller.adapters.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.user.BaseAddressModel;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.viewholders.user.PopupAddressViewHolder;
import java.util.List;

public class UserPopupAddressAdapter extends Adapter<ViewHolder> {
    AppCompatActivity context;
    List<Object> dataList;
    private int selectedPosition = -1;

    public UserPopupAddressAdapter(AppCompatActivity appCompatActivity, List<Object> list) {
        this.context = appCompatActivity;
        this.dataList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PopupAddressViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_address_popup_item, viewGroup, false), this.context);
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        PopupAddressViewHolder popupAddressViewHolder = (PopupAddressViewHolder) viewHolder;
        if (this.dataList.get(i) instanceof UserShippingAddressModel) {
            popupAddressViewHolder.bindShipping((UserShippingAddressModel) this.dataList.get(i));
        } else {
            popupAddressViewHolder.bindBilling((UserBillingAddressModel) this.dataList.get(i));
        }
        popupAddressViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserPopupAddressAdapter.this.setSelectedPosition(i);
            }
        });
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public Object getSelectedAddress(int i) {
        if (i != -1) {
            return this.dataList.get(i);
        }
        return null;
    }

    public void setSelectedPosition(int i) {
        int i2 = this.selectedPosition;
        if (i2 != i) {
            if (i2 >= 0) {
                ((BaseAddressModel) this.dataList.get(i2)).isSelected = false;
            }
            ((BaseAddressModel) this.dataList.get(i)).isSelected = true;
            this.selectedPosition = i;
            notifyDataSetChanged();
        }
    }

    public void setSelectedPosition(BaseAddressModel baseAddressModel) {
        for (int i = 0; i < this.dataList.size(); i++) {
            if ((this.dataList.get(i) instanceof UserBillingAddressModel) && ((UserBillingAddressModel) this.dataList.get(i)).f2189id.equals(baseAddressModel.f2189id)) {
                ((UserBillingAddressModel) this.dataList.get(i)).isSelected = true;
                int i2 = this.selectedPosition;
                if (i2 != -1) {
                    ((UserBillingAddressModel) this.dataList.get(i2)).isSelected = false;
                }
                this.selectedPosition = i;
                notifyItemChanged(this.selectedPosition);
                notifyItemChanged(i);
            } else if ((this.dataList.get(i) instanceof UserShippingAddressModel) && ((UserShippingAddressModel) this.dataList.get(i)).f2189id.equals(baseAddressModel.f2189id)) {
                ((UserShippingAddressModel) this.dataList.get(i)).isSelected = true;
                int i3 = this.selectedPosition;
                if (i3 != -1) {
                    ((UserShippingAddressModel) this.dataList.get(i3)).isSelected = true;
                }
                notifyItemChanged(this.selectedPosition);
                notifyItemChanged(i);
                this.selectedPosition = i;
            }
        }
    }
}
