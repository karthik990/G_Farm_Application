package com.google.android.exoplayer2.p041ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.SubtitleView */
public final class SubtitleView extends View implements TextOutput {
    public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08f;
    public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533f;
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private float bottomPaddingFraction;
    private List<Cue> cues;
    private final List<SubtitlePainter> painters;
    private CaptionStyleCompat style;
    private float textSize;
    private int textSizeType;

    private float resolveTextSize(int i, float f, int i2, int i3) {
        float f2;
        if (i == 0) {
            f2 = (float) i3;
        } else if (i == 1) {
            f2 = (float) i2;
        } else if (i != 2) {
            return -3.4028235E38f;
        } else {
            return f;
        }
        return f * f2;
    }

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.painters = new ArrayList();
        this.textSizeType = 0;
        this.textSize = 0.0533f;
        this.applyEmbeddedStyles = true;
        this.applyEmbeddedFontSizes = true;
        this.style = CaptionStyleCompat.DEFAULT;
        this.bottomPaddingFraction = 0.08f;
    }

    public void onCues(List<Cue> list) {
        setCues(list);
    }

    public void setCues(List<Cue> list) {
        int i;
        if (this.cues != list) {
            this.cues = list;
            if (list == null) {
                i = 0;
            } else {
                i = list.size();
            }
            while (this.painters.size() < i) {
                this.painters.add(new SubtitlePainter(getContext()));
            }
            invalidate();
        }
    }

    public void setFixedTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        setTextSize(2, TypedValue.applyDimension(i, f, resources.getDisplayMetrics()));
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(((Util.SDK_INT < 19 || isInEditMode()) ? 1.0f : getUserCaptionFontScaleV19()) * 0.0533f);
    }

    public void setFractionalTextSize(float f) {
        setFractionalTextSize(f, false);
    }

    public void setFractionalTextSize(float f, boolean z) {
        setTextSize(z ? 1 : 0, f);
    }

    private void setTextSize(int i, float f) {
        if (this.textSizeType != i || this.textSize != f) {
            this.textSizeType = i;
            this.textSize = f;
            invalidate();
        }
    }

    public void setApplyEmbeddedStyles(boolean z) {
        if (this.applyEmbeddedStyles != z || this.applyEmbeddedFontSizes != z) {
            this.applyEmbeddedStyles = z;
            this.applyEmbeddedFontSizes = z;
            invalidate();
        }
    }

    public void setApplyEmbeddedFontSizes(boolean z) {
        if (this.applyEmbeddedFontSizes != z) {
            this.applyEmbeddedFontSizes = z;
            invalidate();
        }
    }

    public void setUserDefaultStyle() {
        setStyle((Util.SDK_INT < 19 || !isCaptionManagerEnabled() || isInEditMode()) ? CaptionStyleCompat.DEFAULT : getUserCaptionStyleV19());
    }

    public void setStyle(CaptionStyleCompat captionStyleCompat) {
        if (this.style != captionStyleCompat) {
            this.style = captionStyleCompat;
            invalidate();
        }
    }

    public void setBottomPaddingFraction(float f) {
        if (this.bottomPaddingFraction != f) {
            this.bottomPaddingFraction = f;
            invalidate();
        }
    }

    public void dispatchDraw(Canvas canvas) {
        List<Cue> list = this.cues;
        if (list != null && !list.isEmpty()) {
            int height = getHeight();
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int paddingBottom = height - getPaddingBottom();
            if (paddingBottom > paddingTop && width > paddingLeft) {
                int i = paddingBottom - paddingTop;
                float resolveTextSize = resolveTextSize(this.textSizeType, this.textSize, height, i);
                if (resolveTextSize > 0.0f) {
                    int size = list.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Cue cue = (Cue) list.get(i2);
                        float resolveCueTextSize = resolveCueTextSize(cue, height, i);
                        SubtitlePainter subtitlePainter = (SubtitlePainter) this.painters.get(i2);
                        boolean z = this.applyEmbeddedStyles;
                        boolean z2 = this.applyEmbeddedFontSizes;
                        CaptionStyleCompat captionStyleCompat = this.style;
                        int i3 = i2;
                        float f = resolveCueTextSize;
                        int i4 = size;
                        float f2 = this.bottomPaddingFraction;
                        int i5 = i;
                        int i6 = paddingBottom;
                        int i7 = width;
                        int i8 = paddingTop;
                        int i9 = paddingLeft;
                        subtitlePainter.draw(cue, z, z2, captionStyleCompat, resolveTextSize, f, f2, canvas, paddingLeft, paddingTop, i7, i6);
                        i2 = i3 + 1;
                        size = i4;
                        i = i5;
                        paddingBottom = i6;
                        width = i7;
                        paddingTop = i8;
                        paddingLeft = i9;
                    }
                }
            }
        }
    }

    private float resolveCueTextSize(Cue cue, int i, int i2) {
        if (cue.textSizeType == Integer.MIN_VALUE || cue.textSize == -3.4028235E38f) {
            return 0.0f;
        }
        return Math.max(resolveTextSize(cue.textSizeType, cue.textSize, i, i2), 0.0f);
    }

    private boolean isCaptionManagerEnabled() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).isEnabled();
    }

    private float getUserCaptionFontScaleV19() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).getFontScale();
    }

    private CaptionStyleCompat getUserCaptionStyleV19() {
        return CaptionStyleCompat.createFromCaptionStyle(((CaptioningManager) getContext().getSystemService("captioning")).getUserStyle());
    }
}
