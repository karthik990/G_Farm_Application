package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.preview.C4290R;

public class MobirollerEmptyView extends ConstraintLayout {
    private int backgroundImageTintColor;
    @BindView(2131362322)
    ImageView backgroundImageView;
    private String description;
    @BindView(2131362323)
    MobirollerTextView descriptionTextView;
    private Drawable icon;
    @BindView(2131362326)
    ImageView imageView;
    private boolean isOval;
    @BindView(2131362798)
    ImageView noContentImageView;
    private boolean showNoContent;
    private String title;
    @BindView(2131362332)
    MobirollerTextView titleTextView;

    public MobirollerEmptyView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerEmptyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerEmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerEmptyView, 0, 0);
        try {
            this.backgroundImageTintColor = obtainStyledAttributes.getInteger(1, 0);
            this.title = obtainStyledAttributes.getString(5);
            this.description = obtainStyledAttributes.getString(2);
            this.icon = AppCompatResources.getDrawable(getContext(), obtainStyledAttributes.getResourceId(3, -1));
            this.isOval = obtainStyledAttributes.getBoolean(0, false);
            this.showNoContent = obtainStyledAttributes.getBoolean(4, false);
        } finally {
            inflate(getContext(), R.layout.mobiroller_empty_view, this);
            ButterKnife.bind((View) this);
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        setTitle(this.title);
        setDescription(this.description);
        Drawable drawable = this.icon;
        if (drawable != null) {
            this.imageView.setImageDrawable(drawable);
        }
        if (this.isOval) {
            this.backgroundImageView.setImageResource(R.drawable.circle_gray_background);
        }
        if (this.showNoContent) {
            this.noContentImageView.setVisibility(0);
        } else {
            this.noContentImageView.setVisibility(8);
        }
        if (VERSION.SDK_INT >= 21) {
            this.backgroundImageView.setImageTintList(ColorStateList.valueOf(this.backgroundImageTintColor));
        }
    }

    public void setTitle(String str) {
        this.titleTextView.setText(str);
    }

    public void setDescription(String str) {
        if (str != null) {
            this.descriptionTextView.setText(str);
            this.descriptionTextView.setVisibility(0);
            return;
        }
        this.descriptionTextView.setVisibility(8);
    }

    public String getDescription() {
        return this.description;
    }
}
