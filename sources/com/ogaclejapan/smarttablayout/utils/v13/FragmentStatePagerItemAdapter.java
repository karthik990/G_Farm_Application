package com.ogaclejapan.smarttablayout.utils.v13;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.ViewGroup;
import androidx.collection.SparseArrayCompat;
import androidx.legacy.app.FragmentStatePagerAdapter;
import java.lang.ref.WeakReference;

public class FragmentStatePagerItemAdapter extends FragmentStatePagerAdapter {
    private final SparseArrayCompat<WeakReference<Fragment>> holder;
    private final FragmentPagerItems pages;

    public FragmentStatePagerItemAdapter(FragmentManager fragmentManager, FragmentPagerItems fragmentPagerItems) {
        super(fragmentManager);
        this.pages = fragmentPagerItems;
        this.holder = new SparseArrayCompat<>(fragmentPagerItems.size());
    }

    public int getCount() {
        return this.pages.size();
    }

    public Fragment getItem(int i) {
        return getPagerItem(i).instantiate(this.pages.getContext(), i);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Object instantiateItem = super.instantiateItem(viewGroup, i);
        if (instantiateItem instanceof Fragment) {
            this.holder.put(i, new WeakReference((Fragment) instantiateItem));
        }
        return instantiateItem;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        this.holder.remove(i);
        super.destroyItem(viewGroup, i, obj);
    }

    public CharSequence getPageTitle(int i) {
        return getPagerItem(i).getTitle();
    }

    public float getPageWidth(int i) {
        return getPagerItem(i).getWidth();
    }

    public Fragment getPage(int i) {
        WeakReference weakReference = (WeakReference) this.holder.get(i);
        if (weakReference != null) {
            return (Fragment) weakReference.get();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public FragmentPagerItem getPagerItem(int i) {
        return (FragmentPagerItem) this.pages.get(i);
    }
}
