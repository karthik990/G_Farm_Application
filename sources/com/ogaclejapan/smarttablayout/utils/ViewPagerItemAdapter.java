package com.ogaclejapan.smarttablayout.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.SparseArrayCompat;
import androidx.viewpager.widget.PagerAdapter;
import java.lang.ref.WeakReference;

public class ViewPagerItemAdapter extends PagerAdapter {
    private final SparseArrayCompat<WeakReference<View>> holder;
    private final LayoutInflater inflater;
    private final ViewPagerItems pages;

    public boolean isViewFromObject(View view, Object obj) {
        return obj == view;
    }

    public ViewPagerItemAdapter(ViewPagerItems viewPagerItems) {
        this.pages = viewPagerItems;
        this.holder = new SparseArrayCompat<>(viewPagerItems.size());
        this.inflater = LayoutInflater.from(viewPagerItems.getContext());
    }

    public int getCount() {
        return this.pages.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View initiate = getPagerItem(i).initiate(this.inflater, viewGroup);
        viewGroup.addView(initiate);
        this.holder.put(i, new WeakReference(initiate));
        return initiate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        this.holder.remove(i);
        viewGroup.removeView((View) obj);
    }

    public CharSequence getPageTitle(int i) {
        return getPagerItem(i).getTitle();
    }

    public float getPageWidth(int i) {
        return getPagerItem(i).getWidth();
    }

    public View getPage(int i) {
        WeakReference weakReference = (WeakReference) this.holder.get(i);
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public ViewPagerItem getPagerItem(int i) {
        return (ViewPagerItem) this.pages.get(i);
    }
}
