package com.mobiroller.viewholders.catalog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.CategoryItemModel;
import com.mobiroller.util.ImageManager;

public class CatalogProductViewHolder extends ViewHolder {
    @BindView(2131362531)
    ImageView image;
    private LocalizationHelper localizationHelper;
    @BindView(2131362224)
    TextView price;
    @BindView(2131363258)
    TextView title;
    @BindView(2131363262)
    TextView titleDescription;

    public CatalogProductViewHolder(View view, LocalizationHelper localizationHelper2) {
        super(view);
        this.localizationHelper = localizationHelper2;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(CategoryItemModel categoryItemModel) {
        if (categoryItemModel.getTableImages() == null || categoryItemModel.getTableImages().get(0) == null) {
            Glide.with((View) this.image).load(Integer.valueOf(R.drawable.no_image)).into(this.image);
        } else {
            ImageManager.loadImageView((String) categoryItemModel.getTableImages().get(0), this.image);
        }
        String str = "";
        if (categoryItemModel.getItemTitle() == null || categoryItemModel.getItemTitle().equalsIgnoreCase(str)) {
            this.title.setVisibility(8);
        } else {
            this.title.setText(this.localizationHelper.getLocalizedTitle(categoryItemModel.getItemTitle()));
        }
        if (categoryItemModel.getItemDescription() == null || categoryItemModel.getItemDescription().equalsIgnoreCase(str)) {
            this.titleDescription.setVisibility(8);
        } else {
            this.titleDescription.setText(this.localizationHelper.getLocalizedTitle(categoryItemModel.getItemSubTitle()));
        }
        if (categoryItemModel.getItemPrice() == null || categoryItemModel.getItemPrice().equalsIgnoreCase(str) || categoryItemModel.getCurrency() == null || categoryItemModel.getCurrency().equalsIgnoreCase(str)) {
            this.price.setVisibility(8);
            return;
        }
        this.price.setText(String.format("%s %s", new Object[]{categoryItemModel.getItemPrice(), categoryItemModel.getCurrency()}));
    }
}
