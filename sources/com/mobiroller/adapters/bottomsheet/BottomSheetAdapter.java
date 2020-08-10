package com.mobiroller.adapters.bottomsheet;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.bottomsheet.ActionModel;
import com.mobiroller.viewholders.bottomsheet.ActionViewHolder;
import java.util.List;

public class BottomSheetAdapter extends Adapter<ViewHolder> {
    private List<ActionModel> dataList;

    public BottomSheetAdapter(List<ActionModel> list) {
        this.dataList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ActionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bottom_action_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ActionViewHolder) viewHolder).bind((ActionModel) this.dataList.get(i));
    }

    public int getItemCount() {
        return this.dataList.size();
    }
}
