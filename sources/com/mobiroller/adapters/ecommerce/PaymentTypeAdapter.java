package com.mobiroller.adapters.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.SupportedPaymentType;
import com.mobiroller.viewholders.ecommerce.PaymentTypeViewHolder;
import java.util.List;

public class PaymentTypeAdapter extends Adapter<ViewHolder> {
    List<SupportedPaymentType> data;

    public PaymentTypeAdapter(List<SupportedPaymentType> list) {
        this.data = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PaymentTypeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_e_commerce_payment_type, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PaymentTypeViewHolder paymentTypeViewHolder = (PaymentTypeViewHolder) viewHolder;
        paymentTypeViewHolder.bind((SupportedPaymentType) this.data.get(i));
        paymentTypeViewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    public int getItemCount() {
        return this.data.size();
    }
}
