package com.stfalcon.chatkit.commons;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.core.content.ContextCompat;
import com.stfalcon.chatkit.C2363R;

public abstract class Style {
    protected AttributeSet attrs;
    protected Context context;
    protected Resources resources;

    protected Style(Context context2, AttributeSet attributeSet) {
        this.context = context2;
        this.resources = context2.getResources();
        this.attrs = attributeSet;
    }

    /* access modifiers changed from: protected */
    public final int getSystemAccentColor() {
        return getSystemColor(C2363R.attr.colorAccent);
    }

    /* access modifiers changed from: protected */
    public final int getSystemPrimaryColor() {
        return getSystemColor(C2363R.attr.colorPrimary);
    }

    /* access modifiers changed from: protected */
    public final int getSystemPrimaryDarkColor() {
        return getSystemColor(C2363R.attr.colorPrimaryDark);
    }

    /* access modifiers changed from: protected */
    public final int getSystemPrimaryTextColor() {
        return getSystemColor(16842806);
    }

    /* access modifiers changed from: protected */
    public final int getSystemHintColor() {
        return getSystemColor(16842906);
    }

    /* access modifiers changed from: protected */
    public final int getSystemColor(int i) {
        TypedValue typedValue = new TypedValue();
        TypedArray obtainStyledAttributes = this.context.obtainStyledAttributes(typedValue.data, new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    /* access modifiers changed from: protected */
    public final int getDimension(int i) {
        return this.resources.getDimensionPixelSize(i);
    }

    /* access modifiers changed from: protected */
    public final int getColor(int i) {
        return ContextCompat.getColor(this.context, i);
    }

    /* access modifiers changed from: protected */
    public final Drawable getDrawable(int i) {
        return ContextCompat.getDrawable(this.context, i);
    }

    /* access modifiers changed from: protected */
    public final Drawable getVectorDrawable(int i) {
        return ContextCompat.getDrawable(this.context, i);
    }
}
