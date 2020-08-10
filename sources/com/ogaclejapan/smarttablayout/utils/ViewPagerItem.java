package com.ogaclejapan.smarttablayout.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerItem extends PagerItem {
    private final int resource;

    protected ViewPagerItem(CharSequence charSequence, float f, int i) {
        super(charSequence, f);
        this.resource = i;
    }

    /* renamed from: of */
    public static ViewPagerItem m2041of(CharSequence charSequence, int i) {
        return m2040of(charSequence, 1.0f, i);
    }

    /* renamed from: of */
    public static ViewPagerItem m2040of(CharSequence charSequence, float f, int i) {
        return new ViewPagerItem(charSequence, f, i);
    }

    public View initiate(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(this.resource, viewGroup, false);
    }
}
