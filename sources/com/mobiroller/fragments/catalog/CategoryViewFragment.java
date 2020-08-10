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
import com.mobiroller.adapters.catalog.CatalogCategoryAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.CategoryModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class CategoryViewFragment extends Fragment {
    private CatalogCategoryAdapter adapter;
    @BindView(2131362333)
    CardView emptyView;
    /* access modifiers changed from: private */
    public ArrayList<CategoryModel> filteredListModels;
    @BindView(2131362540)
    ConstraintLayout imageLayout;
    @BindView(2131362617)
    RecyclerView list;
    private LocalizationHelper localizationHelper;
    @BindView(2131362648)
    ImageView mainImage;
    private ScreenModel screenModel;
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
            String str = Constants.KEY_SCREEN_ID;
            if (arguments.containsKey(str)) {
                String string = getArguments().getString(str);
                if (JSONStorage.containsScreen(string)) {
                    this.screenModel = JSONStorage.getScreenModel(string);
                    this.screenModel.localizeCategoryModels(this.localizationHelper);
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
                }
                loadUi();
            }
        }
        return inflate;
    }

    private void loadUi() {
        if (this.screenModel.getCatalogImageName() == null || this.screenModel.getCatalogImageName().getImageURL() == null) {
            this.imageLayout.setVisibility(8);
        } else {
            Glide.with((Fragment) this).load(this.screenModel.getCatalogImageName().getImageURL()).into(this.mainImage);
        }
        if (this.screenModel.getTitle() != null) {
            this.title.setText(this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
        }
        if (this.screenModel.getSubTitle() != null) {
            this.titleDescription.setText(this.localizationHelper.getLocalizedTitle(this.screenModel.getSubTitle()));
        }
        if (this.screenModel.getTableCategories() != null) {
            this.filteredListModels = this.screenModel.getTableCategories();
            for (int i = 0; i < this.filteredListModels.size(); i++) {
                ((CategoryModel) this.filteredListModels.get(i)).localizeCategoryItemModels(this.localizationHelper);
            }
            this.adapter = new CatalogCategoryAdapter(this.filteredListModels);
            this.list.setAdapter(this.adapter);
            this.list.setLayoutManager(new LinearLayoutManager(getActivity()));
            this.list.setHasFixedSize(true);
            this.list.setItemViewCacheSize(20);
            ItemClickSupport.addTo(this.list).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                    ProductListViewFragment productListViewFragment = new ProductListViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.KEY_CATALOG_CATEGORY, (Serializable) CategoryViewFragment.this.filteredListModels.get(i));
                    productListViewFragment.setArguments(bundle);
                    FragmentTransaction beginTransaction = CategoryViewFragment.this.getParentFragment().getChildFragmentManager().beginTransaction();
                    beginTransaction.setCustomAnimations(R.anim.right_to_left_enter_300, R.anim.right_to_left_exit_300, R.anim.left_to_right_enter_300, R.anim.left_to_right_exit_300);
                    beginTransaction.addToBackStack("categoryModel").replace(R.id.child_container, productListViewFragment).commitAllowingStateLoss();
                }
            });
            this.searchView.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    CategoryViewFragment.this.filter(editable.toString());
                    CategoryViewFragment.this.setEmptyView();
                }
            });
        }
        this.searchView.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    CategoryViewFragment.this.searchView.clearFocus();
                    CategoryViewFragment.this.getParentFragment().getView().requestFocus();
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void setEmptyView() {
        CatalogCategoryAdapter catalogCategoryAdapter = this.adapter;
        if (catalogCategoryAdapter == null || catalogCategoryAdapter.getItemCount() == 0) {
            this.emptyView.setVisibility(0);
            this.list.setVisibility(8);
            return;
        }
        this.emptyView.setVisibility(8);
        this.list.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void filter(String str) {
        if (str.toLowerCase().isEmpty()) {
            this.filteredListModels = this.screenModel.getTableCategories();
        } else {
            ArrayList<CategoryModel> arrayList = new ArrayList<>();
            Iterator it = this.screenModel.getTableCategories().iterator();
            while (it.hasNext()) {
                CategoryModel categoryModel = (CategoryModel) it.next();
                if (categoryModel.getCategoryTitle().toLowerCase().contains(str)) {
                    arrayList.add(categoryModel);
                } else if (categoryModel.getCategorySubTitle().toLowerCase().contains(str)) {
                    arrayList.add(categoryModel);
                }
            }
            this.filteredListModels = arrayList;
        }
        this.adapter.setItems(this.filteredListModels);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
