package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.nightonke.boommenu.BMBShadow;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton.Builder;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.C4514R;
import com.nightonke.boommenu.Util;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BoomButton extends FrameLayout {
    protected boolean ableToHighlight = true;
    protected FrameLayout button;
    protected int buttonCornerRadius;
    protected ButtonEnum buttonEnum = ButtonEnum.Unknown;
    protected int buttonHeight;
    protected int buttonRadius;
    protected int buttonWidth;
    public PointF centerPoint;
    protected boolean containsSubText;
    protected Context context;
    protected TruncateAt ellipsize;
    protected int highlightedColor;
    protected int highlightedColorRes = 0;
    protected Drawable highlightedImageDrawable;
    protected int highlightedImageRes = 0;
    protected String highlightedText;
    protected int highlightedTextColor;
    protected int highlightedTextColorRes = 0;
    protected int highlightedTextRes = 0;
    protected ImageView image;
    protected Rect imagePadding = null;
    protected Rect imageRect = null;
    protected int index = -1;
    protected boolean isRound;
    protected boolean lastStateIsNormal = true;
    protected ViewGroup layout;
    protected InnerOnBoomButtonClickListener listener;
    protected int maxLines;
    protected StateListDrawable nonRippleBitmapDrawable;
    protected GradientDrawable nonRippleGradientDrawable;
    protected int normalColor;
    protected int normalColorRes = 0;
    protected Drawable normalImageDrawable;
    protected int normalImageRes = 0;
    protected String normalText;
    protected int normalTextColor;
    protected int normalTextColorRes = 0;
    protected int normalTextRes = 0;
    protected OnBMClickListener onBMClickListener;
    protected Integer pieceColor = null;
    protected int pieceColorRes = 0;
    protected RippleDrawable rippleDrawable;
    protected boolean rippleEffect = true;
    protected boolean rippleEffectWorks = true;
    protected boolean rotateImage;
    protected boolean rotateText;
    protected BMBShadow shadow;
    protected int shadowColor;
    protected int shadowCornerRadius = 0;
    protected boolean shadowEffect = true;
    protected int shadowOffsetX = 0;
    protected int shadowOffsetY = 0;
    protected int shadowRadius = 0;
    protected TruncateAt subEllipsize;
    protected String subHighlightedText;
    protected int subHighlightedTextColor;
    protected int subHighlightedTextColorRes = 0;
    protected int subHighlightedTextRes = 0;
    protected int subMaxLines;
    protected String subNormalText;
    protected int subNormalTextColor;
    protected int subNormalTextColorRes = 0;
    protected int subNormalTextRes = 0;
    protected TextView subText;
    protected int subTextGravity;
    protected Rect subTextPadding = null;
    protected Rect subTextRect = null;
    protected int subTextSize;
    protected Typeface subTypeface;
    protected String subUnableText;
    protected int subUnableTextColor;
    protected int subUnableTextColorRes = 0;
    protected int subUnableTextRes = 0;
    protected TextView text;
    protected int textGravity;
    protected int textHeight;
    protected Rect textPadding = null;
    protected Rect textRect = null;
    protected int textSize;
    protected int textTopMargin;
    protected int textWidth;
    /* access modifiers changed from: private */
    public boolean touchable = false;
    protected int trueRadius;
    protected Typeface typeface;
    protected boolean unable = false;
    protected int unableColor;
    protected int unableColorRes = 0;
    protected Drawable unableImageDrawable;
    protected int unableImageRes = 0;
    protected String unableText;
    protected int unableTextColor;
    protected int unableTextColorRes = 0;
    protected int unableTextRes = 0;

    public abstract int contentHeight();

    public abstract int contentWidth();

    public abstract ArrayList<View> goneViews();

    public abstract ArrayList<View> rotateViews();

    public abstract void setRotateAnchorPoints();

    public abstract void setSelfScaleAnchorPoints();

    /* access modifiers changed from: protected */
    public abstract void toHighlighted();

    /* access modifiers changed from: protected */
    public abstract void toNormal();

    public abstract int trueHeight();

    public abstract int trueWidth();

    public abstract ButtonEnum type();

    protected BoomButton(Context context2) {
        super(context2);
    }

    /* access modifiers changed from: protected */
    public void initAttrs(BoomButtonBuilder boomButtonBuilder) {
        this.index = boomButtonBuilder.index;
        this.listener = boomButtonBuilder.listener;
        this.onBMClickListener = boomButtonBuilder.onBMClickListener;
        this.rotateImage = boomButtonBuilder.rotateImage;
        this.rotateText = boomButtonBuilder.rotateText;
        this.containsSubText = boomButtonBuilder.containsSubText;
        this.pieceColor = boomButtonBuilder.pieceColor;
        this.pieceColorRes = boomButtonBuilder.pieceColorRes;
        this.shadowEffect = boomButtonBuilder.shadowEffect;
        if (this.shadowEffect) {
            this.shadowOffsetX = boomButtonBuilder.shadowOffsetX;
            this.shadowOffsetY = boomButtonBuilder.shadowOffsetY;
            this.shadowRadius = boomButtonBuilder.shadowRadius;
            this.shadowCornerRadius = boomButtonBuilder.shadowCornerRadius;
            this.shadowColor = boomButtonBuilder.shadowColor;
        }
        this.normalImageRes = boomButtonBuilder.normalImageRes;
        this.highlightedImageRes = boomButtonBuilder.highlightedImageRes;
        this.unableImageRes = boomButtonBuilder.unableImageRes;
        this.normalImageDrawable = boomButtonBuilder.normalImageDrawable;
        this.highlightedImageDrawable = boomButtonBuilder.highlightedImageDrawable;
        this.unableImageDrawable = boomButtonBuilder.unableImageDrawable;
        this.imageRect = boomButtonBuilder.imageRect;
        this.imagePadding = boomButtonBuilder.imagePadding;
        this.normalText = boomButtonBuilder.normalText;
        this.normalTextRes = boomButtonBuilder.normalTextRes;
        this.highlightedText = boomButtonBuilder.highlightedText;
        this.highlightedTextRes = boomButtonBuilder.highlightedTextRes;
        this.unableText = boomButtonBuilder.unableText;
        this.unableTextRes = boomButtonBuilder.unableTextRes;
        this.normalTextColor = boomButtonBuilder.normalTextColor;
        this.normalTextColorRes = boomButtonBuilder.normalTextColorRes;
        this.highlightedTextColor = boomButtonBuilder.highlightedTextColor;
        this.highlightedTextColorRes = boomButtonBuilder.highlightedTextColorRes;
        this.unableTextColor = boomButtonBuilder.unableTextColor;
        this.unableTextColorRes = boomButtonBuilder.unableTextColorRes;
        this.textRect = boomButtonBuilder.textRect;
        this.textPadding = boomButtonBuilder.textPadding;
        this.typeface = boomButtonBuilder.typeface;
        this.maxLines = boomButtonBuilder.maxLines;
        this.textGravity = boomButtonBuilder.textGravity;
        this.ellipsize = boomButtonBuilder.ellipsize;
        this.textSize = boomButtonBuilder.textSize;
        this.subNormalText = boomButtonBuilder.subNormalText;
        this.subNormalTextRes = boomButtonBuilder.subNormalTextRes;
        this.subHighlightedText = boomButtonBuilder.subHighlightedText;
        this.subHighlightedTextRes = boomButtonBuilder.subHighlightedTextRes;
        this.subUnableText = boomButtonBuilder.subUnableText;
        this.subUnableTextRes = boomButtonBuilder.subUnableTextRes;
        this.subNormalTextColor = boomButtonBuilder.subNormalTextColor;
        this.subNormalTextColorRes = boomButtonBuilder.subNormalTextColorRes;
        this.subHighlightedTextColor = boomButtonBuilder.subHighlightedTextColor;
        this.subHighlightedTextColorRes = boomButtonBuilder.subHighlightedTextColorRes;
        this.subUnableTextColor = boomButtonBuilder.subUnableTextColor;
        this.subUnableTextColorRes = boomButtonBuilder.subUnableTextColorRes;
        this.subTextRect = boomButtonBuilder.subTextRect;
        this.subTextPadding = boomButtonBuilder.subTextPadding;
        this.subTypeface = boomButtonBuilder.subTypeface;
        this.subMaxLines = boomButtonBuilder.subMaxLines;
        this.subTextGravity = boomButtonBuilder.subTextGravity;
        this.subEllipsize = boomButtonBuilder.subEllipsize;
        this.subTextSize = boomButtonBuilder.subTextSize;
        this.rippleEffect = boomButtonBuilder.rippleEffect;
        this.normalColor = boomButtonBuilder.normalColor;
        this.normalColorRes = boomButtonBuilder.normalColorRes;
        this.highlightedColor = boomButtonBuilder.highlightedColor;
        this.highlightedColorRes = boomButtonBuilder.highlightedColorRes;
        this.unableColor = boomButtonBuilder.unableColor;
        this.unableColorRes = boomButtonBuilder.unableColorRes;
        this.unable = boomButtonBuilder.unable;
        this.buttonRadius = boomButtonBuilder.buttonRadius;
        this.buttonWidth = boomButtonBuilder.buttonWidth;
        this.buttonHeight = boomButtonBuilder.buttonHeight;
        this.isRound = boomButtonBuilder.isRound;
        if (this.buttonEnum != ButtonEnum.SimpleCircle && this.buttonEnum != ButtonEnum.TextInsideCircle && this.buttonEnum != ButtonEnum.TextOutsideCircle) {
            this.buttonCornerRadius = boomButtonBuilder.buttonCornerRadius;
        } else if (this.isRound) {
            this.buttonCornerRadius = boomButtonBuilder.buttonRadius;
        } else {
            this.buttonCornerRadius = boomButtonBuilder.buttonCornerRadius;
        }
        this.buttonCornerRadius = boomButtonBuilder.buttonCornerRadius;
        this.rippleEffectWorks = this.rippleEffect && VERSION.SDK_INT >= 21;
        this.textTopMargin = boomButtonBuilder.textTopMargin;
        this.textWidth = boomButtonBuilder.textWidth;
        this.textHeight = boomButtonBuilder.textHeight;
        if (boomButtonBuilder instanceof Builder) {
            int i = this.buttonRadius;
            int i2 = (i * 2) + (this.shadowOffsetX * 2);
            int i3 = this.shadowRadius;
            int i4 = i2 + (i3 * 2);
            int i5 = this.textWidth;
            if (i5 > i4) {
                int i6 = this.shadowOffsetY;
                int i7 = i6 + i3 + (i * 2);
                int i8 = this.textTopMargin;
                this.textRect = new Rect(0, i7 + i8, i5, i6 + i3 + (i * 2) + i8 + this.textHeight);
            } else {
                int i9 = (i4 - i5) / 2;
                int i10 = this.shadowOffsetY;
                int i11 = i10 + i3 + (i * 2);
                int i12 = this.textTopMargin;
                this.textRect = new Rect(i9, i11 + i12, ((i4 - i5) / 2) + i5, i10 + i3 + (i * 2) + i12 + this.textHeight);
            }
            int i13 = this.shadowOffsetX;
            int i14 = this.shadowRadius;
            int i15 = i13 + i14;
            int i16 = this.buttonRadius;
            this.trueRadius = (int) (Util.distance(new Point(i15 + i16, this.shadowOffsetY + i14 + i16), new Point(this.textRect.right, this.textRect.bottom)) + 1.0f);
            int i17 = this.textWidth;
            if (i17 > i4) {
                Rect rect = this.textRect;
                int i18 = this.trueRadius;
                rect.offset(i18 - (i17 / 2), i18 - ((this.shadowOffsetY + this.shadowRadius) + this.buttonRadius));
                return;
            }
            Rect rect2 = this.textRect;
            int i19 = this.trueRadius;
            int i20 = this.shadowOffsetX;
            int i21 = this.shadowRadius;
            int i22 = i20 + i21;
            int i23 = this.buttonRadius;
            rect2.offset(i19 - (i22 + i23), i19 - ((this.shadowOffsetY + i21) + i23));
        }
    }

    /* access modifiers changed from: protected */
    public void initTextOutsideCircleButtonLayout() {
        this.layout = (ViewGroup) findViewById(C4514R.C4517id.layout);
        int i = this.trueRadius;
        this.layout.setLayoutParams(new LayoutParams(i * 2, i * 2));
    }

    /* access modifiers changed from: protected */
    public void initShadow(int i) {
        if (this.shadowEffect) {
            this.shadow = (BMBShadow) findViewById(C4514R.C4517id.shadow);
            this.shadow.setShadowOffsetX(this.shadowOffsetX);
            this.shadow.setShadowOffsetY(this.shadowOffsetY);
            this.shadow.setShadowColor(this.shadowColor);
            this.shadow.setShadowRadius(this.shadowRadius);
            this.shadow.setShadowCornerRadius(i);
        }
    }

    /* access modifiers changed from: protected */
    public void initImage() {
        this.image = new ImageView(this.context);
        updateImageRect();
        updateImagePadding();
        this.button.addView(this.image);
        this.lastStateIsNormal = false;
        toNormal();
    }

    /* access modifiers changed from: protected */
    public void initText(ViewGroup viewGroup) {
        this.text = new TextView(this.context);
        updateTextRect();
        updateTextPadding();
        Typeface typeface2 = this.typeface;
        if (typeface2 != null) {
            this.text.setTypeface(typeface2);
        }
        this.text.setMaxLines(this.maxLines);
        this.text.setTextSize(2, (float) this.textSize);
        this.text.setGravity(this.textGravity);
        this.text.setEllipsize(this.ellipsize);
        if (this.ellipsize == TruncateAt.MARQUEE) {
            this.text.setSingleLine(true);
            this.text.setMarqueeRepeatLimit(-1);
            this.text.setHorizontallyScrolling(true);
            this.text.setFocusable(true);
            this.text.setFocusableInTouchMode(true);
            this.text.setFreezesText(true);
        }
        viewGroup.addView(this.text);
    }

    /* access modifiers changed from: protected */
    public void initSubText(ViewGroup viewGroup) {
        if (this.containsSubText) {
            this.subText = new TextView(this.context);
            updateSubTextRect();
            updateSubTextPadding();
            Typeface typeface2 = this.subTypeface;
            if (typeface2 != null) {
                this.subText.setTypeface(typeface2);
            }
            this.subText.setMaxLines(this.maxLines);
            this.subText.setTextSize(2, (float) this.subTextSize);
            this.subText.setGravity(this.subTextGravity);
            this.subText.setEllipsize(this.subEllipsize);
            if (this.subEllipsize == TruncateAt.MARQUEE) {
                this.subText.setSingleLine(true);
                this.subText.setMarqueeRepeatLimit(-1);
                this.subText.setHorizontallyScrolling(true);
                this.subText.setFocusable(true);
                this.subText.setFocusableInTouchMode(true);
                this.subText.setFreezesText(true);
            }
            viewGroup.addView(this.subText);
        }
    }

    /* access modifiers changed from: protected */
    public void initCircleButtonDrawable() {
        GradientDrawable gradientDrawable;
        if (this.rippleEffectWorks) {
            if (this.isRound) {
                gradientDrawable = Util.getOvalDrawable(this.button, this.unable ? unableColor() : normalColor());
            } else {
                gradientDrawable = Util.getRectangleDrawable(this.button, this.buttonCornerRadius, this.unable ? unableColor() : normalColor());
            }
            RippleDrawable rippleDrawable2 = new RippleDrawable(ColorStateList.valueOf(highlightedColor()), gradientDrawable, null);
            Util.setDrawable(this.button, rippleDrawable2);
            this.rippleDrawable = rippleDrawable2;
            return;
        }
        if (this.isRound) {
            this.nonRippleBitmapDrawable = Util.getOvalStateListBitmapDrawable(this.button, this.buttonRadius, normalColor(), highlightedColor(), unableColor());
        } else {
            this.nonRippleBitmapDrawable = Util.getRectangleStateListBitmapDrawable(this.button, this.buttonWidth, this.buttonHeight, this.buttonCornerRadius, normalColor(), highlightedColor(), unableColor());
        }
        if (isNeededColorAnimation()) {
            this.nonRippleGradientDrawable = Util.getOvalDrawable(this.button, this.unable ? unableColor() : normalColor());
        }
        Util.setDrawable(this.button, this.nonRippleBitmapDrawable);
    }

    /* access modifiers changed from: protected */
    public void initCircleButton() {
        this.button = (FrameLayout) findViewById(C4514R.C4517id.button);
        LayoutParams layoutParams = (LayoutParams) this.button.getLayoutParams();
        int i = this.buttonRadius;
        layoutParams.width = i * 2;
        layoutParams.height = i * 2;
        this.button.setLayoutParams(layoutParams);
        this.button.setEnabled(!this.unable);
        this.button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (BoomButton.this.touchable) {
                    if (BoomButton.this.listener != null) {
                        BoomButton.this.listener.onButtonClick(BoomButton.this.index, BoomButton.this);
                    }
                    if (BoomButton.this.onBMClickListener != null) {
                        BoomButton.this.onBMClickListener.onBoomButtonClick(BoomButton.this.index);
                    }
                }
            }
        });
        initCircleButtonDrawable();
        this.button.setOnTouchListener(new OnTouchListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
                if (r4 != 3) goto L_0x0067;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean onTouch(android.view.View r4, android.view.MotionEvent r5) {
                /*
                    r3 = this;
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    boolean r4 = r4.touchable
                    r0 = 0
                    if (r4 != 0) goto L_0x000a
                    return r0
                L_0x000a:
                    int r4 = r5.getAction()
                    r1 = 1
                    if (r4 == 0) goto L_0x0047
                    if (r4 == r1) goto L_0x003f
                    r1 = 2
                    if (r4 == r1) goto L_0x001a
                    r5 = 3
                    if (r4 == r5) goto L_0x003f
                    goto L_0x0067
                L_0x001a:
                    android.graphics.PointF r4 = new android.graphics.PointF
                    float r1 = r5.getX()
                    float r5 = r5.getY()
                    r4.<init>(r1, r5)
                    com.nightonke.boommenu.BoomButtons.BoomButton r5 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    android.widget.FrameLayout r5 = r5.button
                    boolean r4 = com.nightonke.boommenu.Util.pointInView(r4, r5)
                    if (r4 == 0) goto L_0x0037
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.toHighlighted()
                    goto L_0x0067
                L_0x0037:
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.ableToHighlight = r0
                    r4.toNormal()
                    goto L_0x0067
                L_0x003f:
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.ableToHighlight = r0
                    r4.toNormal()
                    goto L_0x0067
                L_0x0047:
                    android.graphics.PointF r4 = new android.graphics.PointF
                    float r2 = r5.getX()
                    float r5 = r5.getY()
                    r4.<init>(r2, r5)
                    com.nightonke.boommenu.BoomButtons.BoomButton r5 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    android.widget.FrameLayout r5 = r5.button
                    boolean r4 = com.nightonke.boommenu.Util.pointInView(r4, r5)
                    if (r4 == 0) goto L_0x0067
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.toHighlighted()
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.ableToHighlight = r1
                L_0x0067:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.BoomButtons.BoomButton.C44902.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initHamButtonDrawable() {
        if (this.rippleEffectWorks) {
            RippleDrawable rippleDrawable2 = new RippleDrawable(ColorStateList.valueOf(highlightedColor()), Util.getRectangleDrawable(this.button, this.buttonCornerRadius, this.unable ? unableColor() : normalColor()), null);
            Util.setDrawable(this.button, rippleDrawable2);
            this.rippleDrawable = rippleDrawable2;
            return;
        }
        this.nonRippleBitmapDrawable = Util.getRectangleStateListBitmapDrawable(this.button, this.buttonWidth, this.buttonHeight, this.buttonCornerRadius, normalColor(), highlightedColor(), unableColor());
        if (isNeededColorAnimation()) {
            this.nonRippleGradientDrawable = Util.getRectangleDrawable(this.button, this.buttonCornerRadius, this.unable ? unableColor() : normalColor());
        }
        Util.setDrawable(this.button, this.nonRippleBitmapDrawable);
    }

    /* access modifiers changed from: protected */
    public void initHamButton() {
        this.button = (FrameLayout) findViewById(C4514R.C4517id.button);
        LayoutParams layoutParams = (LayoutParams) this.button.getLayoutParams();
        layoutParams.width = this.buttonWidth;
        layoutParams.height = this.buttonHeight;
        this.button.setLayoutParams(layoutParams);
        this.button.setEnabled(!this.unable);
        this.button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (BoomButton.this.touchable) {
                    if (BoomButton.this.listener != null) {
                        BoomButton.this.listener.onButtonClick(BoomButton.this.index, BoomButton.this);
                    }
                    if (BoomButton.this.onBMClickListener != null) {
                        BoomButton.this.onBMClickListener.onBoomButtonClick(BoomButton.this.index);
                    }
                }
            }
        });
        initHamButtonDrawable();
        this.button.setOnTouchListener(new OnTouchListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
                if (r4 != 3) goto L_0x0067;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean onTouch(android.view.View r4, android.view.MotionEvent r5) {
                /*
                    r3 = this;
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    boolean r4 = r4.touchable
                    r0 = 0
                    if (r4 != 0) goto L_0x000a
                    return r0
                L_0x000a:
                    int r4 = r5.getAction()
                    r1 = 1
                    if (r4 == 0) goto L_0x0047
                    if (r4 == r1) goto L_0x003f
                    r1 = 2
                    if (r4 == r1) goto L_0x001a
                    r5 = 3
                    if (r4 == r5) goto L_0x003f
                    goto L_0x0067
                L_0x001a:
                    android.graphics.PointF r4 = new android.graphics.PointF
                    float r1 = r5.getX()
                    float r5 = r5.getY()
                    r4.<init>(r1, r5)
                    com.nightonke.boommenu.BoomButtons.BoomButton r5 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    android.widget.FrameLayout r5 = r5.button
                    boolean r4 = com.nightonke.boommenu.Util.pointInView(r4, r5)
                    if (r4 == 0) goto L_0x0037
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.toHighlighted()
                    goto L_0x0067
                L_0x0037:
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.ableToHighlight = r0
                    r4.toNormal()
                    goto L_0x0067
                L_0x003f:
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.ableToHighlight = r0
                    r4.toNormal()
                    goto L_0x0067
                L_0x0047:
                    android.graphics.PointF r4 = new android.graphics.PointF
                    float r2 = r5.getX()
                    float r5 = r5.getY()
                    r4.<init>(r2, r5)
                    com.nightonke.boommenu.BoomButtons.BoomButton r5 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    android.widget.FrameLayout r5 = r5.button
                    boolean r4 = com.nightonke.boommenu.Util.pointInView(r4, r5)
                    if (r4 == 0) goto L_0x0067
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.toHighlighted()
                    com.nightonke.boommenu.BoomButtons.BoomButton r4 = com.nightonke.boommenu.BoomButtons.BoomButton.this
                    r4.ableToHighlight = r1
                L_0x0067:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.BoomButtons.BoomButton.C44924.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
    }

    public LayoutParams place(int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = new LayoutParams(i3, i4);
        layoutParams.leftMargin = i;
        layoutParams.topMargin = i2;
        setLayoutParams(layoutParams);
        return layoutParams;
    }

    /* access modifiers changed from: 0000 */
    public void updateImage() {
        if (this.lastStateIsNormal) {
            toNormalImage();
        } else {
            toHighlightedImage();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateText() {
        if (this.lastStateIsNormal) {
            toNormalText();
        } else {
            toHighlightedText();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateSubText() {
        if (this.lastStateIsNormal) {
            toNormalSubText();
        } else {
            toHighlightedSubText();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateImageRect() {
        LayoutParams layoutParams = new LayoutParams(this.imageRect.right - this.imageRect.left, this.imageRect.bottom - this.imageRect.top);
        layoutParams.leftMargin = this.imageRect.left;
        if (VERSION.SDK_INT >= 17) {
            layoutParams.setMarginStart(this.imageRect.left);
        }
        layoutParams.topMargin = this.imageRect.top;
        ImageView imageView = this.image;
        if (imageView != null) {
            imageView.setLayoutParams(layoutParams);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateImagePadding() {
        Rect rect = this.imagePadding;
        if (rect != null) {
            ImageView imageView = this.image;
            if (imageView != null) {
                imageView.setPadding(rect.left, this.imagePadding.top, this.imagePadding.right, this.imagePadding.bottom);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateTextRect() {
        LayoutParams layoutParams = new LayoutParams(this.textRect.right - this.textRect.left, this.textRect.bottom - this.textRect.top);
        layoutParams.leftMargin = this.textRect.left;
        if (VERSION.SDK_INT >= 17) {
            layoutParams.setMarginStart(this.textRect.left);
        }
        layoutParams.topMargin = this.textRect.top;
        TextView textView = this.text;
        if (textView != null) {
            textView.setLayoutParams(layoutParams);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateTextPadding() {
        Rect rect = this.textPadding;
        if (rect != null) {
            TextView textView = this.text;
            if (textView != null) {
                textView.setPadding(rect.left, this.textPadding.top, this.textPadding.right, this.textPadding.bottom);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateSubTextRect() {
        LayoutParams layoutParams = new LayoutParams(this.subTextRect.right - this.subTextRect.left, this.subTextRect.bottom - this.subTextRect.top);
        layoutParams.leftMargin = this.subTextRect.left;
        if (VERSION.SDK_INT >= 17) {
            layoutParams.setMarginStart(this.subTextRect.left);
        }
        layoutParams.topMargin = this.subTextRect.top;
        TextView textView = this.subText;
        if (textView != null) {
            textView.setLayoutParams(layoutParams);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateSubTextPadding() {
        Rect rect = this.subTextPadding;
        if (rect != null) {
            TextView textView = this.subText;
            if (textView != null) {
                textView.setPadding(rect.left, this.subTextPadding.top, this.subTextPadding.right, this.subTextPadding.bottom);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateButtonDrawable() {
        if (this.buttonEnum == ButtonEnum.SimpleCircle || this.buttonEnum == ButtonEnum.TextInsideCircle || this.buttonEnum == ButtonEnum.TextOutsideCircle) {
            initCircleButtonDrawable();
        } else {
            initHamButtonDrawable();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateUnable() {
        if (this.rippleEffectWorks) {
            updateButtonDrawable();
        }
        this.button.setEnabled(!this.unable);
        if (this.lastStateIsNormal) {
            toNormalImage();
            toNormalText();
            toNormalSubText();
            return;
        }
        toHighlightedImage();
        toHighlightedText();
        toHighlightedSubText();
    }

    /* access modifiers changed from: protected */
    public void toNormalImage() {
        if (this.unable) {
            Util.setDrawable(this.image, this.unableImageRes, this.unableImageDrawable);
        } else {
            Util.setDrawable(this.image, this.normalImageRes, this.normalImageDrawable);
        }
    }

    /* access modifiers changed from: protected */
    public void toHighlightedImage() {
        if (this.unable) {
            Util.setDrawable(this.image, this.unableImageRes, this.unableImageDrawable);
        } else {
            Util.setDrawable(this.image, this.highlightedImageRes, this.highlightedImageDrawable);
        }
    }

    /* access modifiers changed from: protected */
    public void toNormalText() {
        if (this.unable) {
            Util.setText(this.text, this.unableTextRes, this.unableText);
            Util.setTextColor(this.text, this.unableTextColorRes, this.unableTextColor);
            return;
        }
        Util.setText(this.text, this.normalTextRes, this.normalText);
        Util.setTextColor(this.text, this.normalTextColorRes, this.normalTextColor);
    }

    /* access modifiers changed from: protected */
    public void toHighlightedText() {
        if (this.unable) {
            Util.setText(this.text, this.unableTextRes, this.unableText);
            Util.setTextColor(this.text, this.unableTextColorRes, this.unableTextColor);
            return;
        }
        Util.setText(this.text, this.highlightedTextRes, this.highlightedText);
        Util.setTextColor(this.text, this.highlightedTextColorRes, this.highlightedTextColor);
    }

    /* access modifiers changed from: protected */
    public void toNormalSubText() {
        if (this.unable) {
            Util.setText(this.subText, this.subUnableTextRes, this.subUnableText);
            Util.setTextColor(this.subText, this.subUnableTextColorRes, this.subUnableTextColor);
            return;
        }
        Util.setText(this.subText, this.subNormalTextRes, this.subNormalText);
        Util.setTextColor(this.subText, this.subNormalTextColorRes, this.subNormalTextColor);
    }

    /* access modifiers changed from: protected */
    public void toHighlightedSubText() {
        if (this.unable) {
            Util.setText(this.subText, this.subUnableTextRes, this.subUnableText);
            Util.setTextColor(this.subText, this.subUnableTextColorRes, this.subUnableTextColor);
            return;
        }
        Util.setText(this.subText, this.subHighlightedTextRes, this.subHighlightedText);
        Util.setTextColor(this.subText, this.subHighlightedTextColorRes, this.subHighlightedTextColor);
    }

    public int pieceColor() {
        if (this.pieceColor != null || this.pieceColorRes != 0) {
            Integer num = this.pieceColor;
            if (num == null) {
                return Util.getColor(this.context, this.pieceColorRes);
            }
            return Util.getColor(this.context, this.pieceColorRes, num.intValue());
        } else if (this.unable) {
            return unableColor();
        } else {
            return normalColor();
        }
    }

    public int buttonColor() {
        if (this.unable) {
            return unableColor();
        }
        return normalColor();
    }

    public boolean isNeededColorAnimation() {
        Integer valueOf = Integer.valueOf(pieceColor());
        boolean z = true;
        if (this.unable) {
            if (valueOf.compareTo(Integer.valueOf(unableColor())) == 0) {
                z = false;
            }
            return z;
        }
        if (valueOf.compareTo(Integer.valueOf(normalColor())) == 0) {
            z = false;
        }
        return z;
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.unable = !z;
    }

    public void willShow() {
        this.touchable = false;
        if (this.rippleEffectWorks || !isNeededColorAnimation()) {
            updateButtonDrawable();
        } else {
            Util.setDrawable(this.button, this.nonRippleGradientDrawable);
        }
    }

    public void didShow() {
        this.touchable = true;
        if (!this.rippleEffectWorks && isNeededColorAnimation()) {
            Util.setDrawable(this.button, this.nonRippleBitmapDrawable);
        }
        TextView textView = this.text;
        if (textView != null) {
            textView.setSelected(true);
        }
        TextView textView2 = this.subText;
        if (textView2 != null) {
            textView2.setSelected(true);
        }
    }

    public void willHide() {
        this.touchable = false;
        if (!this.rippleEffectWorks && isNeededColorAnimation()) {
            Util.setDrawable(this.button, this.nonRippleGradientDrawable);
        }
    }

    public void didHide() {
        TextView textView = this.text;
        if (textView != null) {
            textView.setSelected(false);
        }
        TextView textView2 = this.subText;
        if (textView2 != null) {
            textView2.setSelected(false);
        }
    }

    public void hideAllGoneViews() {
        Iterator it = goneViews().iterator();
        while (it.hasNext()) {
            ((View) it.next()).setAlpha(0.0f);
        }
    }

    public boolean prepareColorTransformAnimation() {
        String str = "Background drawable is null!";
        if (this.rippleEffectWorks) {
            if (this.rippleDrawable == null) {
                throw new RuntimeException(str);
            }
        } else if (this.nonRippleGradientDrawable == null) {
            throw new RuntimeException(str);
        }
        return this.rippleEffectWorks;
    }

    /* access modifiers changed from: protected */
    public void setNonRippleButtonColor(int i) {
        this.nonRippleGradientDrawable.setColor(i);
    }

    /* access modifiers changed from: protected */
    public void setRippleButtonColor(int i) {
        ((GradientDrawable) this.rippleDrawable.getDrawable(0)).setColor(i);
    }

    public void setClickable(boolean z) {
        super.setClickable(z);
        this.button.setClickable(z);
    }

    /* access modifiers changed from: protected */
    public int normalColor() {
        return Util.getColor(this.context, this.normalColorRes, this.normalColor);
    }

    /* access modifiers changed from: protected */
    public int highlightedColor() {
        return Util.getColor(this.context, this.highlightedColorRes, this.highlightedColor);
    }

    /* access modifiers changed from: protected */
    public int unableColor() {
        return Util.getColor(this.context, this.unableColorRes, this.unableColor);
    }

    public ViewGroup getLayout() {
        return this.layout;
    }

    public FrameLayout getButton() {
        return this.button;
    }

    public BMBShadow getShadow() {
        return this.shadow;
    }

    public ImageView getImageView() {
        return this.image;
    }

    public TextView getTextView() {
        return this.text;
    }

    public TextView getSubTextView() {
        return this.subText;
    }
}
