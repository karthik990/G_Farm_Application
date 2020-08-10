package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.C2556R;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialTextView extends AppCompatTextView {
    public MaterialTextView(Context context) {
        this(context, null);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, i2), attributeSet, i);
        Context context2 = getContext();
        if (canApplyTextAppearanceLineHeight(context2)) {
            Theme theme = context2.getTheme();
            if (!viewAttrsHasLineHeight(context2, theme, attributeSet, i, i2)) {
                int findViewAppearanceResourceId = findViewAppearanceResourceId(theme, attributeSet, i, i2);
                if (findViewAppearanceResourceId != -1) {
                    applyLineHeightFromViewAppearance(theme, findViewAppearanceResourceId);
                }
            }
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (canApplyTextAppearanceLineHeight(context)) {
            applyLineHeightFromViewAppearance(context.getTheme(), i);
        }
    }

    private void applyLineHeightFromViewAppearance(Theme theme, int i) {
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(i, C2556R.styleable.MaterialTextAppearance);
        int readFirstAvailableDimension = readFirstAvailableDimension(getContext(), obtainStyledAttributes, C2556R.styleable.MaterialTextAppearance_android_lineHeight, C2556R.styleable.MaterialTextAppearance_lineHeight);
        obtainStyledAttributes.recycle();
        if (readFirstAvailableDimension >= 0) {
            setLineHeight(readFirstAvailableDimension);
        }
    }

    private static boolean canApplyTextAppearanceLineHeight(Context context) {
        return MaterialAttributes.resolveBoolean(context, C2556R.attr.textAppearanceLineHeightEnabled, true);
    }

    private static int readFirstAvailableDimension(Context context, TypedArray typedArray, int... iArr) {
        int i = -1;
        for (int i2 = 0; i2 < iArr.length && i < 0; i2++) {
            i = MaterialResources.getDimensionPixelSize(context, typedArray, iArr[i2], -1);
        }
        return i;
    }

    private static boolean viewAttrsHasLineHeight(Context context, Theme theme, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, C2556R.styleable.MaterialTextView, i, i2);
        int readFirstAvailableDimension = readFirstAvailableDimension(context, obtainStyledAttributes, C2556R.styleable.MaterialTextView_android_lineHeight, C2556R.styleable.MaterialTextView_lineHeight);
        obtainStyledAttributes.recycle();
        if (readFirstAvailableDimension != -1) {
            return true;
        }
        return false;
    }

    private static int findViewAppearanceResourceId(Theme theme, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, C2556R.styleable.MaterialTextView, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(C2556R.styleable.MaterialTextView_android_textAppearance, -1);
        obtainStyledAttributes.recycle();
        return resourceId;
    }
}
