package com.mobiroller.models;

import com.mobiroller.helpers.LocalizationHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class CategoryModel implements Serializable {
    private String aveCatalogCategoryID;
    private ImageModel catagoryThumbImage;
    private ImageModel categoryImage;
    private ArrayList<CategoryItemModel> categoryItems;
    private String categorySubTitle;
    private String categoryTitle;

    public String getAveCatalogCategoryID() {
        return this.aveCatalogCategoryID;
    }

    public void setAveCatalogCategoryID(String str) {
        this.aveCatalogCategoryID = str;
    }

    public String getCategoryTitle() {
        return this.categoryTitle;
    }

    public void setCategoryTitle(String str) {
        this.categoryTitle = str;
    }

    public String getCategorySubTitle() {
        return this.categorySubTitle;
    }

    public void setCategorySubTitle(String str) {
        this.categorySubTitle = str;
    }

    public ImageModel getCategoryImage() {
        return this.categoryImage;
    }

    public void setCategoryImage(ImageModel imageModel) {
        this.categoryImage = imageModel;
    }

    public ImageModel getCategoryThumbImage() {
        return this.catagoryThumbImage;
    }

    public void setCategoryThumbImage(ImageModel imageModel) {
        this.catagoryThumbImage = imageModel;
    }

    public ArrayList<CategoryItemModel> getCategoryItems() {
        return this.categoryItems;
    }

    public void setCategoryItems(ArrayList<CategoryItemModel> arrayList) {
        this.categoryItems = arrayList;
    }

    public void localizeCategoryItemModels(LocalizationHelper localizationHelper) {
        ArrayList<CategoryItemModel> arrayList = this.categoryItems;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CategoryItemModel categoryItemModel = (CategoryItemModel) it.next();
                categoryItemModel.setItemTitle(localizationHelper.getLocalizedTitle(categoryItemModel.getItemTitle()));
                categoryItemModel.setItemSubTitle(localizationHelper.getLocalizedTitle(categoryItemModel.getItemSubTitle()));
                categoryItemModel.setItemDescription(localizationHelper.getLocalizedTitle(categoryItemModel.getItemDescription()));
            }
        }
    }
}
