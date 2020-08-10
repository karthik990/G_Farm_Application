package com.firebase.p037ui.auth.p038ui.phone;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView.BufferType;
import com.firebase.p037ui.auth.C1330R;
import com.google.android.material.textfield.TextInputEditText;

/* renamed from: com.firebase.ui.auth.ui.phone.SpacedEditText */
public final class SpacedEditText extends TextInputEditText {
    private SpannableStringBuilder mOriginalText = new SpannableStringBuilder("");
    private float mProportion;

    public SpacedEditText(Context context) {
        super(context);
    }

    public SpacedEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttrs(context, attributeSet);
    }

    /* access modifiers changed from: 0000 */
    public void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1330R.styleable.SpacedEditText);
        this.mProportion = obtainStyledAttributes.getFloat(C1330R.styleable.SpacedEditText_spacingProportion, 1.0f);
        obtainStyledAttributes.recycle();
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        this.mOriginalText = new SpannableStringBuilder(charSequence);
        super.setText(getSpacedOutString(charSequence), BufferType.SPANNABLE);
    }

    public void setSelection(int i) {
        int min = Math.min(Math.max((i * 2) - 1, 0), (this.mOriginalText.length() * 2) - 1);
        try {
            super.setSelection(min);
        } catch (IndexOutOfBoundsException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            sb.append(", requestedIndex=");
            sb.append(i);
            sb.append(", newIndex= ");
            sb.append(min);
            sb.append(", originalText=");
            sb.append(this.mOriginalText);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    private SpannableStringBuilder getSpacedOutString(CharSequence charSequence) {
        int i;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int length = charSequence.length();
        int i2 = -1;
        int i3 = 0;
        while (true) {
            i = length - 1;
            if (i3 >= i) {
                break;
            }
            spannableStringBuilder.append(charSequence.charAt(i3));
            spannableStringBuilder.append(" ");
            i2 += 2;
            spannableStringBuilder.setSpan(new ScaleXSpan(this.mProportion), i2, i2 + 1, 33);
            i3++;
        }
        if (length != 0) {
            spannableStringBuilder.append(charSequence.charAt(i));
        }
        return spannableStringBuilder;
    }

    public Editable getUnspacedText() {
        return this.mOriginalText;
    }
}
