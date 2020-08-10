package com.mobiroller.fragments;

import android.app.SearchManager;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.mobiroller.adapters.FavoriteAdapter;
import com.mobiroller.mobi942763453128.R;

public class aveFavoriteViewFragment extends BaseModuleFragment {
    @BindView(2131362398)
    ImageView emptyImageView;
    @BindView(2131362399)
    TextView emptyTextView;
    @BindView(2131362333)
    RelativeLayout emptyView;
    /* access modifiers changed from: private */
    public String lastSearchedQuery;
    /* access modifiers changed from: private */
    public FavoriteAdapter mFavoriteAdapter;
    @BindView(2131362403)
    RelativeLayout mainLayout;
    @BindView(2131362404)
    RelativeLayout overlayLayout;
    @BindView(2131362405)
    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_favorite, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        setHasOptionsMenu(true);
        return inflate;
    }

    public void onResume() {
        super.onResume();
        setFavoriteAdapter();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }

    private void setFavoriteAdapter() {
        this.mFavoriteAdapter = new FavoriteAdapter(getActivity(), this.menuHelper);
        this.recyclerView.setAdapter(this.mFavoriteAdapter);
        this.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        if (this.mFavoriteAdapter.getItemCount() == 0) {
            this.recyclerView.setVisibility(8);
            this.emptyView.setVisibility(0);
            this.emptyImageView.setColorFilter(this.sharedPrefHelper.getActionBarColor(), Mode.SRC_ATOP);
            this.emptyTextView.setTextColor(this.sharedPrefHelper.getActionBarColor());
        } else {
            this.emptyView.setVisibility(8);
            this.recyclerView.setVisibility(0);
        }
        if (this.lastSearchedQuery != null) {
            this.mFavoriteAdapter.getFilter().filter(this.lastSearchedQuery);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Event.SEARCH);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        ((ImageView) searchView.findViewById(R.id.search_button)).setColorFilter(getResources().getColor(R.color.white));
        EditText editText = (EditText) searchView.findViewById(R.id.search_src_text);
        editText.setTextColor(getResources().getColor(R.color.white));
        editText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String str) {
                aveFavoriteViewFragment.this.lastSearchedQuery = str;
                aveFavoriteViewFragment.this.mFavoriteAdapter.getFilter().filter(str);
                return false;
            }

            public boolean onQueryTextChange(String str) {
                aveFavoriteViewFragment.this.lastSearchedQuery = str;
                aveFavoriteViewFragment.this.mFavoriteAdapter.getFilter().filter(str);
                return false;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return menuItem.getItemId() == R.id.action_search || super.onOptionsItemSelected(menuItem);
    }
}
