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
import com.mobiroller.models.CategoryModel;
import com.mobiroller.util.ImageManager;

public class CatalogCategoryViewHolder extends ViewHolder {
    @BindView(2131362531)
    ImageView image;
    private LocalizationHelper localizationHelper;
    @BindView(2131363258)
    TextView title;
    @BindView(2131363262)
    TextView titleDescription;

    public CatalogCategoryViewHolder(View view, LocalizationHelper localizationHelper2) {
        super(view);
        this.localizationHelper = localizationHelper2;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(CategoryModel categoryModel) {
        if (categoryModel.getCategoryThumbImage() == null || categoryModel.getCategoryThumbImage().getImageURL() == null) {
            Glide.with((View) this.image).load(Integer.valueOf(R.drawable.no_image)).into(this.image);
        } else {
            ImageManager.loadImageView(categoryModel.getCategoryThumbImage().getImageURL(), this.image);
        }
        String str = "";
        if (categoryModel.getCategoryTitle() == null || categoryModel.getCategoryTitle().equalsIgnoreCase(str)) {
            this.title.setVisibility(8);
        } else {
            this.title.setText(this.localizationHelper.getLocalizedTitle(categoryModel.getCategoryTitle()));
        }
        if (categoryModel.getCategorySubTitle() == null || categoryModel.getCategorySubTitle().equalsIgnoreCase(str)) {
            this.titleDescription.setVisibility(8);
        } else {
            this.titleDescription.setText(this.localizationHelper.getLocalizedTitle(categoryModel.getCategorySubTitle()));
        }
    }
}
