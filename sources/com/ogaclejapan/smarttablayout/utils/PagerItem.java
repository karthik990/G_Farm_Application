package com.ogaclejapan.smarttablayout.utils;

public abstract class PagerItem {
    protected static final float DEFAULT_WIDTH = 1.0f;
    private final CharSequence title;
    private final float width;

    protected PagerItem(CharSequence charSequence, float f) {
        this.title = charSequence;
        this.width = f;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public float getWidth() {
        return this.width;
    }
}
