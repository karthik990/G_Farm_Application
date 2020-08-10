package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.enums.ColorEnum;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.preview.C4290R;
import com.mobiroller.util.ColorUtil;

public class MobirollerButton extends ConstraintLayout {
    private int backgroundColor;
    private boolean hasRadius;
    private Drawable icon;
    @BindView(2131362599)
    RelativeLayout layout;
    @BindView(2131362604)
    ImageView leftImageView;
    @BindView(2131362649)
    ConstraintLayout mainLayout;
    @BindView(2131363198)
    ImageView successIcon;
    private String text;
    private int textColor;
    @BindView(2131363248)
    TextView textView;
    private int themeColor;

    public MobirollerButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerButton, 0, 0);
        try {
            this.backgroundColor = obtainStyledAttributes.getInteger(0, 0);
            this.textColor = obtainStyledAttributes.getInteger(5, 0);
            this.hasRadius = obtainStyledAttributes.getBoolean(2, false);
            this.text = obtainStyledAttributes.getString(4);
            int resourceId = obtainStyledAttributes.getResourceId(3, -1);
            if (resourceId != -1) {
                this.icon = AppCompatResources.getDrawable(getContext(), resourceId);
            }
            this.themeColor = obtainStyledAttributes.getInteger(1, -1);
        } finally {
            inflate(getContext(), R.layout.mobiroller_button_layout, this);
            ButterKnife.bind((View) this);
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        if (VERSION.SDK_INT >= 23) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(16843868, typedValue, true);
            setForeground(ContextCompat.getDrawable(getContext(), typedValue.resourceId));
        }
        int i = this.themeColor;
        int i2 = ViewCompat.MEASURED_STATE_MASK;
        if (i != -1) {
            setBackgroundColor(ColorEnum.getResIdByResOrder(i));
            setTextColor(ColorUtil.isColorDark(ColorEnum.getResIdByResOrder(this.themeColor)) ? -1 : ViewCompat.MEASURED_STATE_MASK);
        } else {
            setBackgroundColor(this.backgroundColor);
            setTextColor(this.textColor);
        }
        setText(this.text);
        if (this.icon != null) {
            this.leftImageView.setVisibility(0);
            this.leftImageView.setImageDrawable(this.icon);
            ImageView imageView = this.leftImageView;
            if (ColorUtil.isColorDark(this.themeColor)) {
                i2 = -1;
            }
            ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(i2));
        }
    }

    public void setBackgroundColor(int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (this.hasRadius) {
            gradientDrawable.setCornerRadius(16.0f);
        }
        gradientDrawable.setColor(i);
        this.mainLayout.setBackground(gradientDrawable);
    }

    public void setText(String str) {
        this.textView.setText(str);
    }

    public void setTextAnimated(String str) {
        final String charSequence = this.textView.getText().toString();
        setClickable(false);
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down_ecommerce);
        loadAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                Animation loadAnimation = AnimationUtils.loadAnimation(MobirollerButton.this.getContext(), R.anim.slide_in_down_ecommerce);
                loadAnimation.setAnimationListener(new AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Animation loadAnimation = AnimationUtils.loadAnimation(MobirollerButton.this.getContext(), R.anim.slide_out_down_ecommerce);
                                loadAnimation.setAnimationListener(new AnimationListener() {
                                    public void onAnimationRepeat(Animation animation) {
                                    }

                                    public void onAnimationStart(Animation animation) {
                                    }

                                    public void onAnimationEnd(Animation animation) {
                                        MobirollerButton.this.textView.setText(charSequence);
                                        Animation loadAnimation = AnimationUtils.loadAnimation(MobirollerButton.this.getContext(), R.anim.slide_in_down_ecommerce);
                                        loadAnimation.setAnimationListener(new AnimationListener() {
                                            public void onAnimationRepeat(Animation animation) {
                                            }

                                            public void onAnimationStart(Animation animation) {
                                            }

                                            public void onAnimationEnd(Animation animation) {
                                                if (MobirollerButton.this.successIcon != null) {
                                                    MobirollerButton.this.successIcon.setVisibility(8);
                                                }
                                                MobirollerButton.this.setClickable(true);
                                            }
                                        });
                                        MobirollerButton.this.layout.startAnimation(loadAnimation);
                                        MobirollerButton.this.successIcon.setVisibility(8);
                                        MobirollerButton.this.layout.setVisibility(0);
                                    }
                                });
                                MobirollerButton.this.successIcon.startAnimation(loadAnimation);
                            }
                        }, 500);
                    }
                });
                MobirollerButton.this.layout.setVisibility(8);
                MobirollerButton.this.successIcon.setVisibility(0);
                MobirollerButton.this.successIcon.startAnimation(loadAnimation);
            }
        });
        this.layout.startAnimation(loadAnimation);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setOpacity(z);
    }

    public void setOpacity(boolean z) {
        if (!z) {
            setBackgroundColor(ColorUtil.getColorWithAlpha(ColorEnum.getResIdByResOrder(this.themeColor), 0.8f));
        } else {
            setBackgroundColor(ColorEnum.getResIdByResOrder(this.themeColor));
        }
    }

    public void setColor() {
        setBackgroundColor(ColorEnum.getResIdByResOrder(this.themeColor));
    }

    public void removeIcon() {
        ImageView imageView = this.leftImageView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }
}
