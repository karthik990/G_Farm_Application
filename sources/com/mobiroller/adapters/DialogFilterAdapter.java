package com.mobiroller.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.viewholders.user.PopupCountryViewHolder;
import java.util.ArrayList;
import java.util.Iterator;

public class DialogFilterAdapter extends Adapter<ViewHolder> implements Filterable {
    /* access modifiers changed from: private */
    public ArrayList<Object> dataList;
    /* access modifiers changed from: private */
    public ArrayList<Object> dataListFiltered;

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public DialogFilterAdapter(ArrayList<Object> arrayList) {
        this.dataListFiltered = arrayList;
        this.dataList = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PopupCountryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_dialog_list_filter_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((PopupCountryViewHolder) viewHolder).bindText(this.dataListFiltered.get(i).toString());
    }

    public int getItemCount() {
        return this.dataListFiltered.size();
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence.toString().isEmpty()) {
                    DialogFilterAdapter dialogFilterAdapter = DialogFilterAdapter.this;
                    dialogFilterAdapter.dataListFiltered = dialogFilterAdapter.dataList;
                } else {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = DialogFilterAdapter.this.dataList.iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        if (next.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            arrayList.add(next);
                        }
                    }
                    DialogFilterAdapter.this.dataListFiltered = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = DialogFilterAdapter.this.dataListFiltered;
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                DialogFilterAdapter.this.dataListFiltered = (ArrayList) filterResults.values;
                DialogFilterAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public Object getItemAtPosition(int i) {
        return this.dataListFiltered.get(i);
    }
}
