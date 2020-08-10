package com.ogaclejapan.smarttablayout.utils.v13;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import com.ogaclejapan.smarttablayout.utils.PagerItems;

public class FragmentPagerItems extends PagerItems<FragmentPagerItem> {

    public static class Creator {
        private final FragmentPagerItems items;

        public Creator(Context context) {
            this.items = new FragmentPagerItems(context);
        }

        public Creator add(int i, Class<? extends Fragment> cls) {
            return add(FragmentPagerItem.m2045of(this.items.getContext().getString(i), cls));
        }

        public Creator add(int i, Class<? extends Fragment> cls, Bundle bundle) {
            return add(FragmentPagerItem.m2046of((CharSequence) this.items.getContext().getString(i), cls, bundle));
        }

        public Creator add(int i, float f, Class<? extends Fragment> cls) {
            return add(FragmentPagerItem.m2043of((CharSequence) this.items.getContext().getString(i), f, cls));
        }

        public Creator add(int i, float f, Class<? extends Fragment> cls, Bundle bundle) {
            return add(FragmentPagerItem.m2044of(this.items.getContext().getString(i), f, cls, bundle));
        }

        public Creator add(CharSequence charSequence, Class<? extends Fragment> cls) {
            return add(FragmentPagerItem.m2045of(charSequence, cls));
        }

        public Creator add(CharSequence charSequence, Class<? extends Fragment> cls, Bundle bundle) {
            return add(FragmentPagerItem.m2046of(charSequence, cls, bundle));
        }

        public Creator add(FragmentPagerItem fragmentPagerItem) {
            this.items.add(fragmentPagerItem);
            return this;
        }

        public FragmentPagerItems create() {
            return this.items;
        }
    }

    public FragmentPagerItems(Context context) {
        super(context);
    }

    public static Creator with(Context context) {
        return new Creator(context);
    }
}
