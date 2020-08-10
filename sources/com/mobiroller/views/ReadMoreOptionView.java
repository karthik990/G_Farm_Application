package com.mobiroller.views;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;

public class ReadMoreOptionView {
    private static final String TAG = ReadMoreOptionView.class.getSimpleName();
    public static final int TYPE_CHARACTER = 2;
    public static final int TYPE_LINE = 1;
    private Context context;
    /* access modifiers changed from: private */
    public boolean expandAnimation;
    /* access modifiers changed from: private */
    public boolean labelUnderLine;
    private String lessLabel;
    /* access modifiers changed from: private */
    public int lessLabelColor;
    /* access modifiers changed from: private */
    public String moreLabel;
    /* access modifiers changed from: private */
    public int moreLabelColor;
    /* access modifiers changed from: private */
    public int textLength;
    /* access modifiers changed from: private */
    public int textLengthType;

    public static class Builder {
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public boolean expandAnimation;
        /* access modifiers changed from: private */
        public boolean labelUnderLine;
        /* access modifiers changed from: private */
        public String lessLabel = "read less";
        /* access modifiers changed from: private */
        public int lessLabelColor;
        /* access modifiers changed from: private */
        public String moreLabel = "read more";
        /* access modifiers changed from: private */
        public int moreLabelColor;
        /* access modifiers changed from: private */
        public int textLength = 100;
        /* access modifiers changed from: private */
        public int textLengthType = 2;

        public Builder(Context context2) {
            String str = "#ff00ff";
            this.moreLabelColor = Color.parseColor(str);
            this.lessLabelColor = Color.parseColor(str);
            this.labelUnderLine = false;
            this.expandAnimation = false;
            this.context = context2;
        }

        public Builder textLength(int i, int i2) {
            this.textLength = i;
            this.textLengthType = i2;
            return this;
        }

        public Builder moreLabel(String str) {
            this.moreLabel = str;
            return this;
        }

        public Builder lessLabel(String str) {
            this.lessLabel = str;
            return this;
        }

        public Builder moreLabelColor(int i) {
            this.moreLabelColor = i;
            return this;
        }

        public Builder lessLabelColor(int i) {
            this.lessLabelColor = i;
            return this;
        }

        public Builder labelUnderLine(boolean z) {
            this.labelUnderLine = z;
            return this;
        }

        public Builder expandAnimation(boolean z) {
            this.expandAnimation = z;
            return this;
        }

        public ReadMoreOptionView build() {
            return new ReadMoreOptionView(this);
        }
    }

    private ReadMoreOptionView(Builder builder) {
        this.context = builder.context;
        this.textLength = builder.textLength;
        this.textLengthType = builder.textLengthType;
        this.moreLabel = builder.moreLabel;
        this.lessLabel = builder.lessLabel;
        this.moreLabelColor = builder.moreLabelColor;
        this.lessLabelColor = builder.lessLabelColor;
        this.labelUnderLine = builder.labelUnderLine;
        this.expandAnimation = builder.expandAnimation;
    }

    public void addReadMoreTo(final TextView textView, final String str) {
        if (this.textLengthType != 2) {
            textView.setLines(this.textLength);
            textView.setText(str);
        } else if (str.length() <= this.textLength) {
            textView.setText(str);
            return;
        }
        textView.post(new Runnable() {
            public void run() {
                int access$900 = ReadMoreOptionView.this.textLength;
                if (ReadMoreOptionView.this.textLengthType == 1) {
                    try {
                        if (textView.getLayout().getLineCount() <= ReadMoreOptionView.this.textLength) {
                            textView.setText(str);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    access$900 = str.substring(textView.getLayout().getLineStart(0), textView.getLayout().getLineEnd(ReadMoreOptionView.this.textLength - 1)).length() - ((ReadMoreOptionView.this.moreLabel.length() + 4) + (((MarginLayoutParams) textView.getLayoutParams()).rightMargin / 6));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str.substring(0, access$900));
                sb.append("... ");
                sb.append(ReadMoreOptionView.this.moreLabel);
                SpannableString spannableString = new SpannableString(sb.toString());
                spannableString.setSpan(new ClickableSpan() {
                    public void onClick(View view) {
                        ReadMoreOptionView.this.addReadLess(textView, str);
                    }

                    public void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setUnderlineText(ReadMoreOptionView.this.labelUnderLine);
                        textPaint.setColor(ReadMoreOptionView.this.moreLabelColor);
                    }
                }, spannableString.length() - ReadMoreOptionView.this.moreLabel.length(), spannableString.length(), 33);
                if (VERSION.SDK_INT >= 16 && ReadMoreOptionView.this.expandAnimation) {
                    LayoutTransition layoutTransition = new LayoutTransition();
                    layoutTransition.enableTransitionType(4);
                    ((ViewGroup) textView.getParent()).setLayoutTransition(layoutTransition);
                }
                textView.setText(spannableString);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
    }

    /* access modifiers changed from: private */
    public void addReadLess(final TextView textView, final String str) {
        textView.setMaxLines(Integer.MAX_VALUE);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" ");
        sb.append(this.lessLabel);
        SpannableString spannableString = new SpannableString(sb.toString());
        spannableString.setSpan(new ClickableSpan() {
            public void onClick(View view) {
                new Handler().post(new Runnable() {
                    public void run() {
                        ReadMoreOptionView.this.addReadMoreTo(textView, str);
                    }
                });
            }

            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(ReadMoreOptionView.this.labelUnderLine);
                textPaint.setColor(ReadMoreOptionView.this.lessLabelColor);
            }
        }, spannableString.length() - this.lessLabel.length(), spannableString.length(), 33);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
