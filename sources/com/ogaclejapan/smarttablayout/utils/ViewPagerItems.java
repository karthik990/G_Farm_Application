package com.ogaclejapan.smarttablayout.utils;

import android.content.Context;

public class ViewPagerItems extends PagerItems<ViewPagerItem> {

    public static class Creator {
        private final ViewPagerItems items;

        public Creator(Context context) {
            this.items = new ViewPagerItems(context);
        }

        public Creator add(int i, int i2) {
            return add(ViewPagerItem.m2041of(this.items.getContext().getString(i), i2));
        }

        public Creator add(int i, float f, int i2) {
            return add(ViewPagerItem.m2040of(this.items.getContext().getString(i), f, i2));
        }

        public Creator add(CharSequence charSequence, int i) {
            return add(ViewPagerItem.m2041of(charSequence, i));
        }

        public Creator add(ViewPagerItem viewPagerItem) {
            this.items.add(viewPagerItem);
            return this;
        }

        public ViewPagerItems create() {
            return this.items;
        }
    }

    public ViewPagerItems(Context context) {
        super(context);
    }

    public static Creator with(Context context) {
        return new Creator(context);
    }
}
