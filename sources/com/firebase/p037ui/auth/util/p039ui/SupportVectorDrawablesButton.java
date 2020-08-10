package com.firebase.p037ui.auth.util.p039ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.TextViewCompat;
import com.firebase.p037ui.auth.C1330R;

/* renamed from: com.firebase.ui.auth.util.ui.SupportVectorDrawablesButton */
public class SupportVectorDrawablesButton extends AppCompatButton {
    public SupportVectorDrawablesButton(Context context) {
        super(context);
    }

    public SupportVectorDrawablesButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initSupportVectorDrawablesAttrs(attributeSet);
    }

    public SupportVectorDrawablesButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initSupportVectorDrawablesAttrs(attributeSet);
    }

    private void initSupportVectorDrawablesAttrs(AttributeSet attributeSet) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1330R.styleable.SupportVectorDrawablesButton);
            Drawable drawable4 = null;
            if (VERSION.SDK_INT >= 21) {
                drawable3 = obtainStyledAttributes.getDrawable(C1330R.styleable.SupportVectorDrawablesButton_drawableStartCompat);
                Drawable drawable5 = obtainStyledAttributes.getDrawable(C1330R.styleable.SupportVectorDrawablesButton_drawableEndCompat);
                drawable = obtainStyledAttributes.getDrawable(C1330R.styleable.SupportVectorDrawablesButton_drawableTopCompat);
                drawable4 = obtainStyledAttributes.getDrawable(C1330R.styleable.SupportVectorDrawablesButton_drawableBottomCompat);
                drawable2 = drawable5;
            } else {
                int resourceId = obtainStyledAttributes.getResourceId(C1330R.styleable.SupportVectorDrawablesButton_drawableStartCompat, -1);
                int resourceId2 = obtainStyledAttributes.getResourceId(C1330R.styleable.SupportVectorDrawablesButton_drawableEndCompat, -1);
                int resourceId3 = obtainStyledAttributes.getResourceId(C1330R.styleable.SupportVectorDrawablesButton_drawableTopCompat, -1);
                int resourceId4 = obtainStyledAttributes.getResourceId(C1330R.styleable.SupportVectorDrawablesButton_drawableBottomCompat, -1);
                drawable3 = resourceId != -1 ? AppCompatResources.getDrawable(getContext(), resourceId) : null;
                drawable2 = resourceId2 != -1 ? AppCompatResources.getDrawable(getContext(), resourceId2) : null;
                drawable = resourceId3 != -1 ? AppCompatResources.getDrawable(getContext(), resourceId3) : null;
                if (resourceId4 != -1) {
                    drawable4 = AppCompatResources.getDrawable(getContext(), resourceId4);
                }
            }
            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds((TextView) this, drawable3, drawable, drawable2, drawable4);
            obtainStyledAttributes.recycle();
        }
    }
}
