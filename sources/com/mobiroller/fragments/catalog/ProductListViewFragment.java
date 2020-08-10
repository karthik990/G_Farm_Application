package com.mobiroller.fragments.catalog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.adapters.catalog.CatalogProductAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.CategoryItemModel;
import com.mobiroller.models.CategoryModel;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductListViewFragment extends Fragment {
    private CatalogProductAdapter adapter;
    private CategoryModel catalogModel;
    @BindView(2131362333)
    CardView emptyView;
    /* access modifiers changed from: private */
    public ArrayList<CategoryItemModel> filteredListModels;
    @BindView(2131362540)
    ConstraintLayout imageLayout;
    @BindView(2131362617)
    RecyclerView list;
    private LocalizationHelper localizationHelper;
    @BindView(2131362648)
    ImageView mainImage;
    @BindView(2131363108)
    TextInputLayout searchTextInputLayout;
    @BindView(2131363110)
    TextInputEditText searchView;
    @BindView(2131363258)
    TextView title;
    @BindView(2131363262)
    TextView titleDescription;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.catalog_view_layout, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.localizationHelper = UtilManager.localizationHelper();
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = Constants.KEY_CATALOG_CATEGORY;
            if (arguments.containsKey(str)) {
                this.catalogModel = (CategoryModel) getArguments().getSerializable(str);
                loadUi();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) this.localizationHelper.getLocalizedTitle(this.catalogModel.getCategoryTitle()));
            }
        }
        return inflate;
    }

    private void loadUi() {
        if (this.catalogModel.getCategoryImage() == null || this.catalogModel.getCategoryImage().getImageURL() == null) {
            this.imageLayout.setVisibility(8);
        } else {
            Glide.with((Fragment) this).load(this.catalogModel.getCategoryImage().getImageURL()).into(this.mainImage);
        }
        if (this.catalogModel.getCategoryTitle() != null) {
            this.title.setText(this.localizationHelper.getLocalizedTitle(this.catalogModel.getCategoryTitle()));
        }
        if (this.catalogModel.getCategorySubTitle() != null) {
            this.titleDescription.setText(this.localizationHelper.getLocalizedTitle(this.catalogModel.getCategorySubTitle()));
        }
        if (this.catalogModel.getCategoryItems() != null) {
            this.filteredListModels = this.catalogModel.getCategoryItems();
            this.adapter = new CatalogProductAdapter(this.filteredListModels);
            this.list.setAdapter(this.adapter);
            this.list.setLayoutManager(new LinearLayoutManager(getActivity()));
            this.list.setHasFixedSize(true);
            this.list.setItemViewCacheSize(20);
            ItemClickSupport.addTo(this.list).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.KEY_CATALOG_CATEGORY_PRODUCT, (Serializable) ProductListViewFragment.this.filteredListModels.get(i));
                    productDetailFragment.setArguments(bundle);
                    FragmentTransaction beginTransaction = ProductListViewFragment.this.getParentFragment().getChildFragmentManager().beginTransaction();
                    beginTransaction.setCustomAnimations(R.anim.right_to_left_enter_300, R.anim.right_to_left_exit_300, R.anim.left_to_right_enter_300, R.anim.left_to_right_exit_300);
                    beginTransaction.addToBackStack("productModel").replace(R.id.child_container, productDetailFragment).commitAllowingStateLoss();
                }
            });
            this.searchView.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    ProductListViewFragment.this.filter(editable.toString());
                    ProductListViewFragment.this.setEmptyView();
                }
            });
        }
        this.searchView.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    ProductListViewFragment.this.searchView.clearFocus();
                    ProductListViewFragment.this.getParentFragment().getView().requestFocus();
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void setEmptyView() {
        ArrayList<CategoryItemModel> arrayList = this.filteredListModels;
        if (arrayList == null || arrayList.size() == 0) {
            this.emptyView.setVisibility(0);
            this.list.setVisibility(8);
            return;
        }
        this.emptyView.setVisibility(8);
        this.list.setVisibility(0);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    /* access modifiers changed from: private */
    public void filter(String str) {
        if (str.toLowerCase().isEmpty()) {
            this.filteredListModels = this.catalogModel.getCategoryItems();
        } else {
            ArrayList<CategoryItemModel> arrayList = new ArrayList<>();
            Iterator it = this.catalogModel.getCategoryItems().iterator();
            while (it.hasNext()) {
                CategoryItemModel categoryItemModel = (CategoryItemModel) it.next();
                if (categoryItemModel.getItemTitle().toLowerCase().contains(str)) {
                    arrayList.add(categoryItemModel);
                } else if (categoryItemModel.getItemDescription().toLowerCase().contains(str)) {
                    arrayList.add(categoryItemModel);
                } else if (categoryItemModel.getItemSubTitle().toLowerCase().contains(str)) {
                    arrayList.add(categoryItemModel);
                }
            }
            this.filteredListModels = arrayList;
        }
        this.adapter.setItems(this.filteredListModels);
    }
}
