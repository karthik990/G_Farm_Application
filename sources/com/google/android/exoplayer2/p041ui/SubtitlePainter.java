package com.google.android.exoplayer2.p041ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* renamed from: com.google.android.exoplayer2.ui.SubtitlePainter */
final class SubtitlePainter {
    private static final float INNER_PADDING_RATIO = 0.125f;
    private static final String TAG = "SubtitlePainter";
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private int backgroundColor;
    private Rect bitmapRect;
    private float bottomPaddingFraction;
    private Bitmap cueBitmap;
    private float cueBitmapHeight;
    private float cueLine;
    private int cueLineAnchor;
    private int cueLineType;
    private float cuePosition;
    private int cuePositionAnchor;
    private float cueSize;
    private CharSequence cueText;
    private Alignment cueTextAlignment;
    private float cueTextSizePx;
    private float defaultTextSizePx;
    private int edgeColor;
    private int edgeType;
    private int foregroundColor;
    private final float outlineWidth;
    private final Paint paint;
    private int parentBottom;
    private int parentLeft;
    private int parentRight;
    private int parentTop;
    private final float shadowOffset;
    private final float shadowRadius;
    private final float spacingAdd;
    private final float spacingMult;
    private StaticLayout textLayout;
    private int textLeft;
    private int textPaddingX;
    private final TextPaint textPaint = new TextPaint();
    private int textTop;
    private int windowColor;

    public SubtitlePainter(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, new int[]{16843287, 16843288}, 0, 0);
        this.spacingAdd = (float) obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.spacingMult = obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.recycle();
        float round = (float) Math.round((((float) context.getResources().getDisplayMetrics().densityDpi) * 2.0f) / 160.0f);
        this.outlineWidth = round;
        this.shadowRadius = round;
        this.shadowOffset = round;
        this.textPaint.setAntiAlias(true);
        this.textPaint.setSubpixelText(true);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Style.FILL);
    }

    public void draw(Cue cue, boolean z, boolean z2, CaptionStyleCompat captionStyleCompat, float f, float f2, float f3, Canvas canvas, int i, int i2, int i3, int i4) {
        Cue cue2 = cue;
        boolean z3 = z;
        boolean z4 = z2;
        CaptionStyleCompat captionStyleCompat2 = captionStyleCompat;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Canvas canvas2 = canvas;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        boolean z5 = cue2.bitmap == null;
        int i9 = ViewCompat.MEASURED_STATE_MASK;
        if (z5) {
            if (!TextUtils.isEmpty(cue2.text)) {
                i9 = (!cue2.windowColorSet || !z3) ? captionStyleCompat2.windowColor : cue2.windowColor;
            } else {
                return;
            }
        }
        if (areCharSequencesEqual(this.cueText, cue2.text) && Util.areEqual(this.cueTextAlignment, cue2.textAlignment) && this.cueBitmap == cue2.bitmap && this.cueLine == cue2.line && this.cueLineType == cue2.lineType && Util.areEqual(Integer.valueOf(this.cueLineAnchor), Integer.valueOf(cue2.lineAnchor)) && this.cuePosition == cue2.position && Util.areEqual(Integer.valueOf(this.cuePositionAnchor), Integer.valueOf(cue2.positionAnchor)) && this.cueSize == cue2.size && this.cueBitmapHeight == cue2.bitmapHeight && this.applyEmbeddedStyles == z3 && this.applyEmbeddedFontSizes == z4 && this.foregroundColor == captionStyleCompat2.foregroundColor && this.backgroundColor == captionStyleCompat2.backgroundColor && this.windowColor == i9 && this.edgeType == captionStyleCompat2.edgeType && this.edgeColor == captionStyleCompat2.edgeColor && Util.areEqual(this.textPaint.getTypeface(), captionStyleCompat2.typeface) && this.defaultTextSizePx == f4 && this.cueTextSizePx == f5 && this.bottomPaddingFraction == f6 && this.parentLeft == i5 && this.parentTop == i6 && this.parentRight == i7 && this.parentBottom == i8) {
            drawLayout(canvas, z5);
            return;
        }
        Canvas canvas3 = canvas;
        this.cueText = cue2.text;
        this.cueTextAlignment = cue2.textAlignment;
        this.cueBitmap = cue2.bitmap;
        this.cueLine = cue2.line;
        this.cueLineType = cue2.lineType;
        this.cueLineAnchor = cue2.lineAnchor;
        this.cuePosition = cue2.position;
        this.cuePositionAnchor = cue2.positionAnchor;
        this.cueSize = cue2.size;
        this.cueBitmapHeight = cue2.bitmapHeight;
        this.applyEmbeddedStyles = z3;
        this.applyEmbeddedFontSizes = z4;
        this.foregroundColor = captionStyleCompat2.foregroundColor;
        this.backgroundColor = captionStyleCompat2.backgroundColor;
        this.windowColor = i9;
        this.edgeType = captionStyleCompat2.edgeType;
        this.edgeColor = captionStyleCompat2.edgeColor;
        this.textPaint.setTypeface(captionStyleCompat2.typeface);
        this.defaultTextSizePx = f4;
        this.cueTextSizePx = f5;
        this.bottomPaddingFraction = f6;
        this.parentLeft = i5;
        this.parentTop = i6;
        this.parentRight = i7;
        this.parentBottom = i8;
        if (z5) {
            Assertions.checkNotNull(this.cueText);
            setupTextLayout();
        } else {
            Assertions.checkNotNull(this.cueBitmap);
            setupBitmapLayout();
        }
        drawLayout(canvas3, z5);
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r19v0, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r19v1 */
    /* JADX WARNING: type inference failed for: r19v2 */
    /* JADX WARNING: type inference failed for: r9v12 */
    /* JADX WARNING: type inference failed for: r9v17 */
    /* JADX WARNING: type inference failed for: r9v18 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    @org.checkerframework.checker.nullness.qual.RequiresNonNull({"cueText"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setupTextLayout() {
        /*
            r26 = this;
            r0 = r26
            java.lang.CharSequence r1 = r0.cueText
            int r2 = r0.parentRight
            int r3 = r0.parentLeft
            int r2 = r2 - r3
            int r3 = r0.parentBottom
            int r4 = r0.parentTop
            int r3 = r3 - r4
            android.text.TextPaint r4 = r0.textPaint
            float r5 = r0.defaultTextSizePx
            r4.setTextSize(r5)
            float r4 = r0.defaultTextSizePx
            r5 = 1040187392(0x3e000000, float:0.125)
            float r4 = r4 * r5
            r5 = 1056964608(0x3f000000, float:0.5)
            float r4 = r4 + r5
            int r4 = (int) r4
            int r5 = r4 * 2
            int r6 = r2 - r5
            float r7 = r0.cueSize
            r8 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            int r9 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r9 == 0) goto L_0x0030
            float r6 = (float) r6
            float r6 = r6 * r7
            int r6 = (int) r6
        L_0x0030:
            java.lang.String r7 = "SubtitlePainter"
            if (r6 > 0) goto L_0x003a
            java.lang.String r1 = "Skipped drawing subtitle cue (insufficient space)"
            com.google.android.exoplayer2.util.Log.m1396w(r7, r1)
            return
        L_0x003a:
            boolean r9 = r0.applyEmbeddedStyles
            r10 = 16711680(0xff0000, float:2.3418052E-38)
            r17 = 0
            r15 = 0
            if (r9 != 0) goto L_0x0048
            java.lang.String r1 = r1.toString()
            goto L_0x0098
        L_0x0048:
            boolean r9 = r0.applyEmbeddedFontSizes
            if (r9 != 0) goto L_0x007d
            android.text.SpannableStringBuilder r9 = new android.text.SpannableStringBuilder
            r9.<init>(r1)
            int r1 = r9.length()
            java.lang.Class<android.text.style.AbsoluteSizeSpan> r11 = android.text.style.AbsoluteSizeSpan.class
            java.lang.Object[] r11 = r9.getSpans(r15, r1, r11)
            android.text.style.AbsoluteSizeSpan[] r11 = (android.text.style.AbsoluteSizeSpan[]) r11
            java.lang.Class<android.text.style.RelativeSizeSpan> r12 = android.text.style.RelativeSizeSpan.class
            java.lang.Object[] r1 = r9.getSpans(r15, r1, r12)
            android.text.style.RelativeSizeSpan[] r1 = (android.text.style.RelativeSizeSpan[]) r1
            int r12 = r11.length
            r13 = 0
        L_0x0067:
            if (r13 >= r12) goto L_0x0071
            r14 = r11[r13]
            r9.removeSpan(r14)
            int r13 = r13 + 1
            goto L_0x0067
        L_0x0071:
            int r11 = r1.length
            r12 = 0
        L_0x0073:
            if (r12 >= r11) goto L_0x0097
            r13 = r1[r12]
            r9.removeSpan(r13)
            int r12 = r12 + 1
            goto L_0x0073
        L_0x007d:
            float r9 = r0.cueTextSizePx
            int r9 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            if (r9 <= 0) goto L_0x0098
            android.text.SpannableStringBuilder r9 = new android.text.SpannableStringBuilder
            r9.<init>(r1)
            android.text.style.AbsoluteSizeSpan r1 = new android.text.style.AbsoluteSizeSpan
            float r11 = r0.cueTextSizePx
            int r11 = (int) r11
            r1.<init>(r11)
            int r11 = r9.length()
            r9.setSpan(r1, r15, r11, r10)
        L_0x0097:
            r1 = r9
        L_0x0098:
            int r9 = r0.backgroundColor
            int r9 = android.graphics.Color.alpha(r9)
            if (r9 <= 0) goto L_0x00b6
            android.text.SpannableStringBuilder r9 = new android.text.SpannableStringBuilder
            r9.<init>(r1)
            android.text.style.BackgroundColorSpan r1 = new android.text.style.BackgroundColorSpan
            int r11 = r0.backgroundColor
            r1.<init>(r11)
            int r11 = r9.length()
            r9.setSpan(r1, r15, r11, r10)
            r19 = r9
            goto L_0x00b8
        L_0x00b6:
            r19 = r1
        L_0x00b8:
            android.text.Layout$Alignment r1 = r0.cueTextAlignment
            if (r1 != 0) goto L_0x00be
            android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_CENTER
        L_0x00be:
            r22 = r1
            android.text.StaticLayout r1 = new android.text.StaticLayout
            android.text.TextPaint r11 = r0.textPaint
            float r14 = r0.spacingMult
            float r13 = r0.spacingAdd
            r16 = 1
            r9 = r1
            r10 = r19
            r12 = r6
            r18 = r13
            r13 = r22
            r15 = r18
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)
            r0.textLayout = r1
            android.text.StaticLayout r1 = r0.textLayout
            int r1 = r1.getHeight()
            android.text.StaticLayout r9 = r0.textLayout
            int r9 = r9.getLineCount()
            r10 = 0
            r11 = 0
        L_0x00e7:
            if (r10 >= r9) goto L_0x00fc
            android.text.StaticLayout r12 = r0.textLayout
            float r12 = r12.getLineWidth(r10)
            double r12 = (double) r12
            double r12 = java.lang.Math.ceil(r12)
            int r12 = (int) r12
            int r11 = java.lang.Math.max(r12, r11)
            int r10 = r10 + 1
            goto L_0x00e7
        L_0x00fc:
            float r9 = r0.cueSize
            int r9 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            if (r9 == 0) goto L_0x0105
            if (r11 >= r6) goto L_0x0105
            goto L_0x0106
        L_0x0105:
            r6 = r11
        L_0x0106:
            int r6 = r6 + r5
            float r5 = r0.cuePosition
            r9 = 1
            r10 = 2
            int r11 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r11 == 0) goto L_0x0134
            float r2 = (float) r2
            float r2 = r2 * r5
            int r2 = java.lang.Math.round(r2)
            int r5 = r0.parentLeft
            int r2 = r2 + r5
            int r5 = r0.cuePositionAnchor
            if (r5 == r9) goto L_0x0122
            if (r5 == r10) goto L_0x0120
            goto L_0x0126
        L_0x0120:
            int r2 = r2 - r6
            goto L_0x0126
        L_0x0122:
            int r2 = r2 * 2
            int r2 = r2 - r6
            int r2 = r2 / r10
        L_0x0126:
            int r5 = r0.parentLeft
            int r2 = java.lang.Math.max(r2, r5)
            int r6 = r6 + r2
            int r5 = r0.parentRight
            int r5 = java.lang.Math.min(r6, r5)
            goto L_0x013b
        L_0x0134:
            int r2 = r2 - r6
            int r2 = r2 / r10
            int r5 = r0.parentLeft
            int r2 = r2 + r5
            int r5 = r2 + r6
        L_0x013b:
            int r21 = r5 - r2
            if (r21 > 0) goto L_0x0145
            java.lang.String r1 = "Skipped drawing subtitle cue (invalid horizontal positioning)"
            com.google.android.exoplayer2.util.Log.m1396w(r7, r1)
            return
        L_0x0145:
            float r5 = r0.cueLine
            int r6 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r6 == 0) goto L_0x01a0
            int r6 = r0.cueLineType
            if (r6 != 0) goto L_0x015a
            float r3 = (float) r3
            float r3 = r3 * r5
            int r3 = java.lang.Math.round(r3)
            int r5 = r0.parentTop
        L_0x0158:
            int r3 = r3 + r5
            goto L_0x0185
        L_0x015a:
            android.text.StaticLayout r3 = r0.textLayout
            r5 = 0
            int r3 = r3.getLineBottom(r5)
            android.text.StaticLayout r6 = r0.textLayout
            int r5 = r6.getLineTop(r5)
            int r3 = r3 - r5
            float r5 = r0.cueLine
            int r6 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r6 < 0) goto L_0x0178
            float r3 = (float) r3
            float r5 = r5 * r3
            int r3 = java.lang.Math.round(r5)
            int r5 = r0.parentTop
            goto L_0x0158
        L_0x0178:
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r5 + r6
            float r3 = (float) r3
            float r5 = r5 * r3
            int r3 = java.lang.Math.round(r5)
            int r5 = r0.parentBottom
            goto L_0x0158
        L_0x0185:
            int r5 = r0.cueLineAnchor
            if (r5 != r10) goto L_0x018b
            int r3 = r3 - r1
            goto L_0x0191
        L_0x018b:
            if (r5 != r9) goto L_0x0191
            int r3 = r3 * 2
            int r3 = r3 - r1
            int r3 = r3 / r10
        L_0x0191:
            int r5 = r3 + r1
            int r6 = r0.parentBottom
            if (r5 <= r6) goto L_0x019a
            int r3 = r6 - r1
            goto L_0x01ab
        L_0x019a:
            int r1 = r0.parentTop
            if (r3 >= r1) goto L_0x01ab
            r3 = r1
            goto L_0x01ab
        L_0x01a0:
            int r5 = r0.parentBottom
            int r5 = r5 - r1
            float r1 = (float) r3
            float r3 = r0.bottomPaddingFraction
            float r1 = r1 * r3
            int r1 = (int) r1
            int r3 = r5 - r1
        L_0x01ab:
            android.text.StaticLayout r1 = new android.text.StaticLayout
            android.text.TextPaint r5 = r0.textPaint
            float r6 = r0.spacingMult
            float r7 = r0.spacingAdd
            r25 = 1
            r18 = r1
            r20 = r5
            r23 = r6
            r24 = r7
            r18.<init>(r19, r20, r21, r22, r23, r24, r25)
            r0.textLayout = r1
            r0.textLeft = r2
            r0.textTop = r3
            r0.textPaddingX = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p041ui.SubtitlePainter.setupTextLayout():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005e  */
    @org.checkerframework.checker.nullness.qual.RequiresNonNull({"cueBitmap"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setupBitmapLayout() {
        /*
            r7 = this;
            android.graphics.Bitmap r0 = r7.cueBitmap
            int r1 = r7.parentRight
            int r2 = r7.parentLeft
            int r1 = r1 - r2
            int r3 = r7.parentBottom
            int r4 = r7.parentTop
            int r3 = r3 - r4
            float r2 = (float) r2
            float r1 = (float) r1
            float r5 = r7.cuePosition
            float r5 = r5 * r1
            float r2 = r2 + r5
            float r4 = (float) r4
            float r3 = (float) r3
            float r5 = r7.cueLine
            float r5 = r5 * r3
            float r4 = r4 + r5
            float r5 = r7.cueSize
            float r1 = r1 * r5
            int r1 = java.lang.Math.round(r1)
            float r5 = r7.cueBitmapHeight
            r6 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 == 0) goto L_0x0032
            float r3 = r3 * r5
            int r0 = java.lang.Math.round(r3)
            goto L_0x0044
        L_0x0032:
            float r3 = (float) r1
            int r5 = r0.getHeight()
            float r5 = (float) r5
            int r0 = r0.getWidth()
            float r0 = (float) r0
            float r5 = r5 / r0
            float r3 = r3 * r5
            int r0 = java.lang.Math.round(r3)
        L_0x0044:
            int r3 = r7.cuePositionAnchor
            r5 = 1
            r6 = 2
            if (r3 != r6) goto L_0x004d
            float r3 = (float) r1
        L_0x004b:
            float r2 = r2 - r3
            goto L_0x0053
        L_0x004d:
            if (r3 != r5) goto L_0x0053
            int r3 = r1 / 2
            float r3 = (float) r3
            goto L_0x004b
        L_0x0053:
            int r2 = java.lang.Math.round(r2)
            int r3 = r7.cueLineAnchor
            if (r3 != r6) goto L_0x005e
            float r3 = (float) r0
        L_0x005c:
            float r4 = r4 - r3
            goto L_0x0064
        L_0x005e:
            if (r3 != r5) goto L_0x0064
            int r3 = r0 / 2
            float r3 = (float) r3
            goto L_0x005c
        L_0x0064:
            int r3 = java.lang.Math.round(r4)
            android.graphics.Rect r4 = new android.graphics.Rect
            int r1 = r1 + r2
            int r0 = r0 + r3
            r4.<init>(r2, r3, r1, r0)
            r7.bitmapRect = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p041ui.SubtitlePainter.setupBitmapLayout():void");
    }

    private void drawLayout(Canvas canvas, boolean z) {
        if (z) {
            drawTextLayout(canvas);
            return;
        }
        Assertions.checkNotNull(this.bitmapRect);
        Assertions.checkNotNull(this.cueBitmap);
        drawBitmapLayout(canvas);
    }

    private void drawTextLayout(Canvas canvas) {
        int i;
        StaticLayout staticLayout = this.textLayout;
        if (staticLayout != null) {
            int save = canvas.save();
            canvas.translate((float) this.textLeft, (float) this.textTop);
            if (Color.alpha(this.windowColor) > 0) {
                this.paint.setColor(this.windowColor);
                canvas.drawRect((float) (-this.textPaddingX), 0.0f, (float) (staticLayout.getWidth() + this.textPaddingX), (float) staticLayout.getHeight(), this.paint);
            }
            int i2 = this.edgeType;
            boolean z = true;
            if (i2 == 1) {
                this.textPaint.setStrokeJoin(Join.ROUND);
                this.textPaint.setStrokeWidth(this.outlineWidth);
                this.textPaint.setColor(this.edgeColor);
                this.textPaint.setStyle(Style.FILL_AND_STROKE);
                staticLayout.draw(canvas);
            } else if (i2 == 2) {
                TextPaint textPaint2 = this.textPaint;
                float f = this.shadowRadius;
                float f2 = this.shadowOffset;
                textPaint2.setShadowLayer(f, f2, f2, this.edgeColor);
            } else if (i2 == 3 || i2 == 4) {
                if (this.edgeType != 3) {
                    z = false;
                }
                int i3 = -1;
                if (z) {
                    i = -1;
                } else {
                    i = this.edgeColor;
                }
                if (z) {
                    i3 = this.edgeColor;
                }
                float f3 = this.shadowRadius / 2.0f;
                this.textPaint.setColor(this.foregroundColor);
                this.textPaint.setStyle(Style.FILL);
                float f4 = -f3;
                this.textPaint.setShadowLayer(this.shadowRadius, f4, f4, i);
                staticLayout.draw(canvas);
                this.textPaint.setShadowLayer(this.shadowRadius, f3, f3, i3);
            }
            this.textPaint.setColor(this.foregroundColor);
            this.textPaint.setStyle(Style.FILL);
            staticLayout.draw(canvas);
            this.textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            canvas.restoreToCount(save);
        }
    }

    @RequiresNonNull({"cueBitmap", "bitmapRect"})
    private void drawBitmapLayout(Canvas canvas) {
        canvas.drawBitmap(this.cueBitmap, null, this.bitmapRect, null);
    }

    private static boolean areCharSequencesEqual(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence == charSequence2 || (charSequence != null && charSequence.equals(charSequence2));
    }
}
