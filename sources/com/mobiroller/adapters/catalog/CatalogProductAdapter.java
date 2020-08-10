package com.mobiroller.adapters.catalog;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.CategoryItemModel;
import com.mobiroller.viewholders.catalog.CatalogProductViewHolder;
import java.util.List;

public class CatalogProductAdapter extends Adapter<ViewHolder> {
    private List<CategoryItemModel> categoryModelsFiltered;
    private LocalizationHelper localizationHelper = UtilManager.localizationHelper();

    public CatalogProductAdapter(List<CategoryItemModel> list) {
        this.categoryModelsFiltered = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CatalogProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalog_product_list_item, viewGroup, false), this.localizationHelper);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((CatalogProductViewHolder) viewHolder).bind((CategoryItemModel) this.categoryModelsFiltered.get(i));
    }

    public int getItemCount() {
        return this.categoryModelsFiltered.size();
    }

    public void setItems(List<CategoryItemModel> list) {
        this.categoryModelsFiltered = list;
        notifyDataSetChanged();
    }
}
