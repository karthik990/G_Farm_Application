package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class ToggleImageButton extends ImageButton {
    private static final int[] STATE_TOGGLED_ON = {C5234R.attr.state_toggled_on};
    String contentDescriptionOff;
    String contentDescriptionOn;
    boolean isToggledOn;
    final boolean toggleOnClick;

    public ToggleImageButton(Context context) {
        this(context, null);
    }

    public ToggleImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ToggleImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArray = null;
        try {
            typedArray = context.getTheme().obtainStyledAttributes(attributeSet, C5234R.styleable.ToggleImageButton, i, 0);
            String string = typedArray.getString(C5234R.styleable.ToggleImageButton_contentDescriptionOn);
            String string2 = typedArray.getString(C5234R.styleable.ToggleImageButton_contentDescriptionOff);
            if (string == null) {
                string = (String) getContentDescription();
            }
            this.contentDescriptionOn = string;
            if (string2 == null) {
                string2 = (String) getContentDescription();
            }
            this.contentDescriptionOff = string2;
            this.toggleOnClick = typedArray.getBoolean(C5234R.styleable.ToggleImageButton_toggleOnClick, true);
            setToggledOn(false);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (this.isToggledOn) {
            mergeDrawableStates(onCreateDrawableState, STATE_TOGGLED_ON);
        }
        return onCreateDrawableState;
    }

    public boolean performClick() {
        if (this.toggleOnClick) {
            toggle();
        }
        return super.performClick();
    }

    public void setToggledOn(boolean z) {
        this.isToggledOn = z;
        setContentDescription(z ? this.contentDescriptionOn : this.contentDescriptionOff);
        refreshDrawableState();
    }

    public void toggle() {
        setToggledOn(!this.isToggledOn);
    }

    public boolean isToggledOn() {
        return this.isToggledOn;
    }
}
