package com.mobiroller.adapters.catalog;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.CategoryModel;
import com.mobiroller.viewholders.catalog.CatalogCategoryViewHolder;
import java.util.List;

public class CatalogCategoryAdapter extends Adapter<ViewHolder> {
    private List<CategoryModel> categoryModelsFiltered;
    private LocalizationHelper localizationHelper = UtilManager.localizationHelper();

    public CatalogCategoryAdapter(List<CategoryModel> list) {
        this.categoryModelsFiltered = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CatalogCategoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalog_category_list_item, viewGroup, false), this.localizationHelper);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((CatalogCategoryViewHolder) viewHolder).bind((CategoryModel) this.categoryModelsFiltered.get(i));
    }

    public int getItemCount() {
        return this.categoryModelsFiltered.size();
    }

    public void setItems(List<CategoryModel> list) {
        this.categoryModelsFiltered = list;
        notifyDataSetChanged();
    }
}
