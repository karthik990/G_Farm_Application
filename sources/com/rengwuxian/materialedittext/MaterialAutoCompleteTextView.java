package com.rengwuxian.materialedittext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.view.ViewCompat;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.rengwuxian.materialedittext.validation.METLengthChecker;
import com.rengwuxian.materialedittext.validation.METValidator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Marker;

public class MaterialAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    public static final int FLOATING_LABEL_HIGHLIGHT = 2;
    public static final int FLOATING_LABEL_NONE = 0;
    public static final int FLOATING_LABEL_NORMAL = 1;
    private Typeface accentTypeface;
    /* access modifiers changed from: private */
    public boolean autoValidate;
    private int baseColor;
    private int bottomEllipsisSize;
    private float bottomLines;
    ObjectAnimator bottomLinesAnimator;
    private int bottomSpacing;
    private int bottomTextSize;
    private boolean charactersCountValid;
    private boolean checkCharactersCountAtBeginning;
    private Bitmap[] clearButtonBitmaps;
    private boolean clearButtonClicking;
    private boolean clearButtonTouched;
    private float currentBottomLines;
    private int errorColor;
    private int extraPaddingBottom;
    private int extraPaddingLeft;
    private int extraPaddingRight;
    private int extraPaddingTop;
    private boolean firstShown;
    private boolean floatingLabelAlwaysShown;
    private boolean floatingLabelAnimating;
    /* access modifiers changed from: private */
    public boolean floatingLabelEnabled;
    private float floatingLabelFraction;
    private int floatingLabelPadding;
    /* access modifiers changed from: private */
    public boolean floatingLabelShown;
    private CharSequence floatingLabelText;
    private int floatingLabelTextColor;
    private int floatingLabelTextSize;
    private ArgbEvaluator focusEvaluator = new ArgbEvaluator();
    private float focusFraction;
    private String helperText;
    private boolean helperTextAlwaysShown;
    private int helperTextColor = -1;
    private boolean hideUnderline;
    /* access modifiers changed from: private */
    public boolean highlightFloatingLabel;
    private Bitmap[] iconLeftBitmaps;
    private int iconOuterHeight;
    private int iconOuterWidth;
    private int iconPadding;
    private Bitmap[] iconRightBitmaps;
    private int iconSize;
    OnFocusChangeListener innerFocusChangeListener;
    private int innerPaddingBottom;
    private int innerPaddingLeft;
    private int innerPaddingRight;
    private int innerPaddingTop;
    ObjectAnimator labelAnimator;
    ObjectAnimator labelFocusAnimator;
    private METLengthChecker lengthChecker;
    private int maxCharacters;
    private int minBottomLines;
    private int minBottomTextLines;
    private int minCharacters;
    OnFocusChangeListener outerFocusChangeListener;
    Paint paint = new Paint(1);
    private int primaryColor;
    private boolean showClearButton;
    private boolean singleLineEllipsis;
    private String tempErrorText;
    private ColorStateList textColorHintStateList;
    private ColorStateList textColorStateList;
    StaticLayout textLayout;
    TextPaint textPaint = new TextPaint(1);
    private Typeface typeface;
    private int underlineColor;
    /* access modifiers changed from: private */
    public boolean validateOnFocusLost;
    private List<METValidator> validators;

    public @interface FloatingLabelType {
    }

    public MaterialAutoCompleteTextView(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0085, code lost:
        r8.getTheme().resolveAttribute(r3, r1, true);
        r1 = r1.data;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0096, code lost:
        throw new java.lang.RuntimeException("colorPrimary not found");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0097, code lost:
        r1 = r7.baseColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r3 = getResources().getIdentifier("colorPrimary", "attr", getContext().getPackageName());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0083, code lost:
        if (r3 != 0) goto L_0x0085;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x006f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(android.content.Context r8, android.util.AttributeSet r9) {
        /*
            r7 = this;
            r0 = 32
            int r1 = r7.getPixel(r0)
            r7.iconSize = r1
            r1 = 48
            int r1 = r7.getPixel(r1)
            r7.iconOuterWidth = r1
            int r0 = r7.getPixel(r0)
            r7.iconOuterHeight = r0
            android.content.res.Resources r0 = r7.getResources()
            int r1 = com.rengwuxian.materialedittext.C4613R.dimen.inner_components_spacing
            int r0 = r0.getDimensionPixelSize(r1)
            r7.bottomSpacing = r0
            android.content.res.Resources r0 = r7.getResources()
            int r1 = com.rengwuxian.materialedittext.C4613R.dimen.bottom_ellipsis_height
            int r0 = r0.getDimensionPixelSize(r1)
            r7.bottomEllipsisSize = r0
            int[] r0 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText
            android.content.res.TypedArray r0 = r8.obtainStyledAttributes(r9, r0)
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_textColor
            android.content.res.ColorStateList r1 = r0.getColorStateList(r1)
            r7.textColorStateList = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_textColorHint
            android.content.res.ColorStateList r1 = r0.getColorStateList(r1)
            r7.textColorHintStateList = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_baseColor
            r2 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            int r1 = r0.getColor(r1, r2)
            r7.baseColor = r1
            android.util.TypedValue r1 = new android.util.TypedValue
            r1.<init>()
            r2 = 1
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x006f }
            r4 = 21
            if (r3 < r4) goto L_0x0067
            android.content.res.Resources$Theme r3 = r8.getTheme()     // Catch:{ Exception -> 0x006f }
            r4 = 16843827(0x1010433, float:2.369657E-38)
            r3.resolveAttribute(r4, r1, r2)     // Catch:{ Exception -> 0x006f }
            int r1 = r1.data     // Catch:{ Exception -> 0x006f }
            goto L_0x0099
        L_0x0067:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x006f }
            java.lang.String r4 = "SDK_INT less than LOLLIPOP"
            r3.<init>(r4)     // Catch:{ Exception -> 0x006f }
            throw r3     // Catch:{ Exception -> 0x006f }
        L_0x006f:
            android.content.res.Resources r3 = r7.getResources()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r4 = "colorPrimary"
            java.lang.String r5 = "attr"
            android.content.Context r6 = r7.getContext()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ Exception -> 0x0097 }
            int r3 = r3.getIdentifier(r4, r5, r6)     // Catch:{ Exception -> 0x0097 }
            if (r3 == 0) goto L_0x008f
            android.content.res.Resources$Theme r4 = r8.getTheme()     // Catch:{ Exception -> 0x0097 }
            r4.resolveAttribute(r3, r1, r2)     // Catch:{ Exception -> 0x0097 }
            int r1 = r1.data     // Catch:{ Exception -> 0x0097 }
            goto L_0x0099
        L_0x008f:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x0097 }
            java.lang.String r3 = "colorPrimary not found"
            r1.<init>(r3)     // Catch:{ Exception -> 0x0097 }
            throw r1     // Catch:{ Exception -> 0x0097 }
        L_0x0097:
            int r1 = r7.baseColor
        L_0x0099:
            int r3 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_primaryColor
            int r1 = r0.getColor(r3, r1)
            r7.primaryColor = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabel
            r3 = 0
            int r1 = r0.getInt(r1, r3)
            r7.setFloatingLabelInternal(r1)
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_errorColor
            java.lang.String r4 = "#e7492E"
            int r4 = android.graphics.Color.parseColor(r4)
            int r1 = r0.getColor(r1, r4)
            r7.errorColor = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_minCharacters
            int r1 = r0.getInt(r1, r3)
            r7.minCharacters = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_maxCharacters
            int r1 = r0.getInt(r1, r3)
            r7.maxCharacters = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_singleLineEllipsis
            boolean r1 = r0.getBoolean(r1, r3)
            r7.singleLineEllipsis = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_helperText
            java.lang.String r1 = r0.getString(r1)
            r7.helperText = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_helperTextColor
            r4 = -1
            int r1 = r0.getColor(r1, r4)
            r7.helperTextColor = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_minBottomTextLines
            int r1 = r0.getInt(r1, r3)
            r7.minBottomTextLines = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_accentTypeface
            java.lang.String r1 = r0.getString(r1)
            if (r1 == 0) goto L_0x0105
            boolean r5 = r7.isInEditMode()
            if (r5 != 0) goto L_0x0105
            android.graphics.Typeface r1 = r7.getCustomTypeface(r1)
            r7.accentTypeface = r1
            android.text.TextPaint r1 = r7.textPaint
            android.graphics.Typeface r5 = r7.accentTypeface
            r1.setTypeface(r5)
        L_0x0105:
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_typeface
            java.lang.String r1 = r0.getString(r1)
            if (r1 == 0) goto L_0x011e
            boolean r5 = r7.isInEditMode()
            if (r5 != 0) goto L_0x011e
            android.graphics.Typeface r1 = r7.getCustomTypeface(r1)
            r7.typeface = r1
            android.graphics.Typeface r1 = r7.typeface
            r7.setTypeface(r1)
        L_0x011e:
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabelText
            java.lang.String r1 = r0.getString(r1)
            r7.floatingLabelText = r1
            java.lang.CharSequence r1 = r7.floatingLabelText
            if (r1 != 0) goto L_0x0130
            java.lang.CharSequence r1 = r7.getHint()
            r7.floatingLabelText = r1
        L_0x0130:
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabelPadding
            int r5 = r7.bottomSpacing
            int r1 = r0.getDimensionPixelSize(r1, r5)
            r7.floatingLabelPadding = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabelTextSize
            android.content.res.Resources r5 = r7.getResources()
            int r6 = com.rengwuxian.materialedittext.C4613R.dimen.floating_label_text_size
            int r5 = r5.getDimensionPixelSize(r6)
            int r1 = r0.getDimensionPixelSize(r1, r5)
            r7.floatingLabelTextSize = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabelTextColor
            int r1 = r0.getColor(r1, r4)
            r7.floatingLabelTextColor = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabelAnimating
            boolean r1 = r0.getBoolean(r1, r2)
            r7.floatingLabelAnimating = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_bottomTextSize
            android.content.res.Resources r5 = r7.getResources()
            int r6 = com.rengwuxian.materialedittext.C4613R.dimen.bottom_text_size
            int r5 = r5.getDimensionPixelSize(r6)
            int r1 = r0.getDimensionPixelSize(r1, r5)
            r7.bottomTextSize = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_hideUnderline
            boolean r1 = r0.getBoolean(r1, r3)
            r7.hideUnderline = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_underlineColor
            int r1 = r0.getColor(r1, r4)
            r7.underlineColor = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_autoValidate
            boolean r1 = r0.getBoolean(r1, r3)
            r7.autoValidate = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_iconLeft
            int r1 = r0.getResourceId(r1, r4)
            android.graphics.Bitmap[] r1 = r7.generateIconBitmaps(r1)
            r7.iconLeftBitmaps = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_iconRight
            int r1 = r0.getResourceId(r1, r4)
            android.graphics.Bitmap[] r1 = r7.generateIconBitmaps(r1)
            r7.iconRightBitmaps = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_clearButton
            boolean r1 = r0.getBoolean(r1, r3)
            r7.showClearButton = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.C4615drawable.met_ic_clear
            android.graphics.Bitmap[] r1 = r7.generateIconBitmaps(r1)
            r7.clearButtonBitmaps = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_iconPadding
            r4 = 16
            int r5 = r7.getPixel(r4)
            int r1 = r0.getDimensionPixelSize(r1, r5)
            r7.iconPadding = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_floatingLabelAlwaysShown
            boolean r1 = r0.getBoolean(r1, r3)
            r7.floatingLabelAlwaysShown = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_helperTextAlwaysShown
            boolean r1 = r0.getBoolean(r1, r3)
            r7.helperTextAlwaysShown = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_validateOnFocusLost
            boolean r1 = r0.getBoolean(r1, r3)
            r7.validateOnFocusLost = r1
            int r1 = com.rengwuxian.materialedittext.C4613R.styleable.MaterialEditText_met_checkCharactersCountAtBeginning
            boolean r1 = r0.getBoolean(r1, r2)
            r7.checkCharactersCountAtBeginning = r1
            r0.recycle()
            r0 = 5
            int[] r0 = new int[r0]
            r0 = {16842965, 16842966, 16842967, 16842968, 16842969} // fill-array
            android.content.res.TypedArray r8 = r8.obtainStyledAttributes(r9, r0)
            int r9 = r8.getDimensionPixelSize(r3, r3)
            int r0 = r8.getDimensionPixelSize(r2, r9)
            r7.innerPaddingLeft = r0
            r0 = 2
            int r0 = r8.getDimensionPixelSize(r0, r9)
            r7.innerPaddingTop = r0
            r0 = 3
            int r0 = r8.getDimensionPixelSize(r0, r9)
            r7.innerPaddingRight = r0
            r0 = 4
            int r9 = r8.getDimensionPixelSize(r0, r9)
            r7.innerPaddingBottom = r9
            r8.recycle()
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 0
            if (r8 < r4) goto L_0x0214
            r7.setBackground(r9)
            goto L_0x0217
        L_0x0214:
            r7.setBackgroundDrawable(r9)
        L_0x0217:
            boolean r8 = r7.singleLineEllipsis
            if (r8 == 0) goto L_0x0225
            android.text.method.TransformationMethod r8 = r7.getTransformationMethod()
            r7.setSingleLine()
            r7.setTransformationMethod(r8)
        L_0x0225:
            r7.initMinBottomLines()
            r7.initPadding()
            r7.initText()
            r7.initFloatingLabel()
            r7.initTextWatcher()
            r7.checkCharactersCount()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rengwuxian.materialedittext.MaterialAutoCompleteTextView.init(android.content.Context, android.util.AttributeSet):void");
    }

    private void initText() {
        if (!TextUtils.isEmpty(getText())) {
            Editable text = getText();
            setText(null);
            resetHintTextColor();
            setText(text);
            setSelection(text.length());
            this.floatingLabelFraction = 1.0f;
            this.floatingLabelShown = true;
        } else {
            resetHintTextColor();
        }
        resetTextColor();
    }

    private void initTextWatcher() {
        addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                MaterialAutoCompleteTextView.this.checkCharactersCount();
                if (MaterialAutoCompleteTextView.this.autoValidate) {
                    MaterialAutoCompleteTextView.this.validate();
                } else {
                    MaterialAutoCompleteTextView.this.setError(null);
                }
                MaterialAutoCompleteTextView.this.postInvalidate();
            }
        });
    }

    private Typeface getCustomTypeface(String str) {
        return Typeface.createFromAsset(getContext().getAssets(), str);
    }

    public void setIconLeft(int i) {
        this.iconLeftBitmaps = generateIconBitmaps(i);
        initPadding();
    }

    public void setIconLeft(Drawable drawable) {
        this.iconLeftBitmaps = generateIconBitmaps(drawable);
        initPadding();
    }

    public void setIconLeft(Bitmap bitmap) {
        this.iconLeftBitmaps = generateIconBitmaps(bitmap);
        initPadding();
    }

    public void setIconRight(int i) {
        this.iconRightBitmaps = generateIconBitmaps(i);
        initPadding();
    }

    public void setIconRight(Drawable drawable) {
        this.iconRightBitmaps = generateIconBitmaps(drawable);
        initPadding();
    }

    public void setIconRight(Bitmap bitmap) {
        this.iconRightBitmaps = generateIconBitmaps(bitmap);
        initPadding();
    }

    public boolean isShowClearButton() {
        return this.showClearButton;
    }

    public void setShowClearButton(boolean z) {
        this.showClearButton = z;
        correctPaddings();
    }

    private Bitmap[] generateIconBitmaps(int i) {
        if (i == -1) {
            return null;
        }
        Options options = new Options();
        int i2 = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), i, options);
        int max = Math.max(options.outWidth, options.outHeight);
        int i3 = this.iconSize;
        if (max > i3) {
            i2 = max / i3;
        }
        options.inSampleSize = i2;
        options.inJustDecodeBounds = false;
        return generateIconBitmaps(BitmapFactory.decodeResource(getResources(), i, options));
    }

    private Bitmap[] generateIconBitmaps(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        int i = this.iconSize;
        return generateIconBitmaps(Bitmap.createScaledBitmap(createBitmap, i, i, false));
    }

    private Bitmap[] generateIconBitmaps(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap[] bitmapArr = new Bitmap[4];
        Bitmap scaleIcon = scaleIcon(bitmap);
        bitmapArr[0] = scaleIcon.copy(Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmapArr[0]);
        int i = this.baseColor;
        canvas.drawColor((Colors.isLight(i) ? ViewCompat.MEASURED_STATE_MASK : -1979711488) | (i & ViewCompat.MEASURED_SIZE_MASK), Mode.SRC_IN);
        bitmapArr[1] = scaleIcon.copy(Config.ARGB_8888, true);
        new Canvas(bitmapArr[1]).drawColor(this.primaryColor, Mode.SRC_IN);
        bitmapArr[2] = scaleIcon.copy(Config.ARGB_8888, true);
        Canvas canvas2 = new Canvas(bitmapArr[2]);
        int i2 = this.baseColor;
        canvas2.drawColor((Colors.isLight(i2) ? 1275068416 : 1107296256) | (16777215 & i2), Mode.SRC_IN);
        bitmapArr[3] = scaleIcon.copy(Config.ARGB_8888, true);
        new Canvas(bitmapArr[3]).drawColor(this.errorColor, Mode.SRC_IN);
        return bitmapArr;
    }

    private Bitmap scaleIcon(Bitmap bitmap) {
        int i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        int i2 = this.iconSize;
        if (max == i2) {
            return bitmap;
        }
        if (max > i2) {
            if (width > i2) {
                int i3 = i2;
                i2 = (int) (((float) i2) * (((float) height) / ((float) width)));
                i = i3;
            } else {
                i = (int) (((float) i2) * (((float) width) / ((float) height)));
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, i, i2, false);
        }
        return bitmap;
    }

    public float getFloatingLabelFraction() {
        return this.floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float f) {
        this.floatingLabelFraction = f;
        invalidate();
    }

    public float getFocusFraction() {
        return this.focusFraction;
    }

    public void setFocusFraction(float f) {
        this.focusFraction = f;
        invalidate();
    }

    public float getCurrentBottomLines() {
        return this.currentBottomLines;
    }

    public void setCurrentBottomLines(float f) {
        this.currentBottomLines = f;
        initPadding();
    }

    public boolean isFloatingLabelAlwaysShown() {
        return this.floatingLabelAlwaysShown;
    }

    public void setFloatingLabelAlwaysShown(boolean z) {
        this.floatingLabelAlwaysShown = z;
        invalidate();
    }

    public boolean isHelperTextAlwaysShown() {
        return this.helperTextAlwaysShown;
    }

    public void setHelperTextAlwaysShown(boolean z) {
        this.helperTextAlwaysShown = z;
        invalidate();
    }

    public Typeface getAccentTypeface() {
        return this.accentTypeface;
    }

    public void setAccentTypeface(Typeface typeface2) {
        this.accentTypeface = typeface2;
        this.textPaint.setTypeface(typeface2);
        postInvalidate();
    }

    public boolean isHideUnderline() {
        return this.hideUnderline;
    }

    public void setHideUnderline(boolean z) {
        this.hideUnderline = z;
        initPadding();
        postInvalidate();
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public void setUnderlineColor(int i) {
        this.underlineColor = i;
        postInvalidate();
    }

    public CharSequence getFloatingLabelText() {
        return this.floatingLabelText;
    }

    public void setFloatingLabelText(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = getHint();
        }
        this.floatingLabelText = charSequence;
        postInvalidate();
    }

    public int getFloatingLabelTextSize() {
        return this.floatingLabelTextSize;
    }

    public void setFloatingLabelTextSize(int i) {
        this.floatingLabelTextSize = i;
        initPadding();
    }

    public int getFloatingLabelTextColor() {
        return this.floatingLabelTextColor;
    }

    public void setFloatingLabelTextColor(int i) {
        this.floatingLabelTextColor = i;
        postInvalidate();
    }

    public int getBottomTextSize() {
        return this.bottomTextSize;
    }

    public void setBottomTextSize(int i) {
        this.bottomTextSize = i;
        initPadding();
    }

    private int getPixel(int i) {
        return Density.dp2px(getContext(), (float) i);
    }

    private void initPadding() {
        this.extraPaddingTop = this.floatingLabelEnabled ? this.floatingLabelTextSize + this.floatingLabelPadding : this.floatingLabelPadding;
        this.textPaint.setTextSize((float) this.bottomTextSize);
        FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        this.extraPaddingBottom = ((int) ((fontMetrics.descent - fontMetrics.ascent) * this.currentBottomLines)) + (this.hideUnderline ? this.bottomSpacing : this.bottomSpacing * 2);
        int i = 0;
        this.extraPaddingLeft = this.iconLeftBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding;
        if (this.iconRightBitmaps != null) {
            i = this.iconPadding + this.iconOuterWidth;
        }
        this.extraPaddingRight = i;
        correctPaddings();
    }

    private void initMinBottomLines() {
        int i = 0;
        boolean z = this.minCharacters > 0 || this.maxCharacters > 0 || this.singleLineEllipsis || this.tempErrorText != null || this.helperText != null;
        int i2 = this.minBottomTextLines;
        if (i2 > 0) {
            i = i2;
        } else if (z) {
            i = 1;
        }
        this.minBottomLines = i;
        this.currentBottomLines = (float) i;
    }

    @Deprecated
    public final void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
    }

    public void setPaddings(int i, int i2, int i3, int i4) {
        this.innerPaddingTop = i2;
        this.innerPaddingBottom = i4;
        this.innerPaddingLeft = i;
        this.innerPaddingRight = i3;
        correctPaddings();
    }

    private void correctPaddings() {
        int buttonsCount = this.iconOuterWidth * getButtonsCount();
        int i = 0;
        if (!isRTL()) {
            i = buttonsCount;
            buttonsCount = 0;
        }
        super.setPadding(this.innerPaddingLeft + this.extraPaddingLeft + buttonsCount, this.innerPaddingTop + this.extraPaddingTop, this.innerPaddingRight + this.extraPaddingRight + i, this.innerPaddingBottom + this.extraPaddingBottom);
    }

    private int getButtonsCount() {
        return isShowClearButton() ? 1 : 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.firstShown) {
            this.firstShown = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            adjustBottomLines();
        }
    }

    private boolean adjustBottomLines() {
        int i;
        if (getWidth() == 0) {
            return false;
        }
        this.textPaint.setTextSize((float) this.bottomTextSize);
        if (this.tempErrorText == null && this.helperText == null) {
            i = this.minBottomLines;
        } else {
            Alignment alignment = ((getGravity() & 5) == 5 || isRTL()) ? Alignment.ALIGN_OPPOSITE : (getGravity() & 3) == 3 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_CENTER;
            Alignment alignment2 = alignment;
            String str = this.tempErrorText;
            if (str == null) {
                str = this.helperText;
            }
            StaticLayout staticLayout = new StaticLayout(str, this.textPaint, (((getWidth() - getBottomTextLeftOffset()) - getBottomTextRightOffset()) - getPaddingLeft()) - getPaddingRight(), alignment2, 1.0f, 0.0f, true);
            this.textLayout = staticLayout;
            i = Math.max(this.textLayout.getLineCount(), this.minBottomTextLines);
        }
        float f = (float) i;
        if (this.bottomLines != f) {
            getBottomLinesAnimator(f).start();
        }
        this.bottomLines = f;
        return true;
    }

    public int getInnerPaddingTop() {
        return this.innerPaddingTop;
    }

    public int getInnerPaddingBottom() {
        return this.innerPaddingBottom;
    }

    public int getInnerPaddingLeft() {
        return this.innerPaddingLeft;
    }

    public int getInnerPaddingRight() {
        return this.innerPaddingRight;
    }

    private void initFloatingLabel() {
        addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!MaterialAutoCompleteTextView.this.floatingLabelEnabled) {
                    return;
                }
                if (editable.length() == 0) {
                    if (MaterialAutoCompleteTextView.this.floatingLabelShown) {
                        MaterialAutoCompleteTextView.this.floatingLabelShown = false;
                        MaterialAutoCompleteTextView.this.getLabelAnimator().reverse();
                    }
                } else if (!MaterialAutoCompleteTextView.this.floatingLabelShown) {
                    MaterialAutoCompleteTextView.this.floatingLabelShown = true;
                    MaterialAutoCompleteTextView.this.getLabelAnimator().start();
                }
            }
        });
        this.innerFocusChangeListener = new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (MaterialAutoCompleteTextView.this.floatingLabelEnabled && MaterialAutoCompleteTextView.this.highlightFloatingLabel) {
                    if (z) {
                        MaterialAutoCompleteTextView.this.getLabelFocusAnimator().start();
                    } else {
                        MaterialAutoCompleteTextView.this.getLabelFocusAnimator().reverse();
                    }
                }
                if (MaterialAutoCompleteTextView.this.validateOnFocusLost && !z) {
                    MaterialAutoCompleteTextView.this.validate();
                }
                if (MaterialAutoCompleteTextView.this.outerFocusChangeListener != null) {
                    MaterialAutoCompleteTextView.this.outerFocusChangeListener.onFocusChange(view, z);
                }
            }
        };
        super.setOnFocusChangeListener(this.innerFocusChangeListener);
    }

    public boolean isValidateOnFocusLost() {
        return this.validateOnFocusLost;
    }

    public void setValidateOnFocusLost(boolean z) {
        this.validateOnFocusLost = z;
    }

    public void setBaseColor(int i) {
        if (this.baseColor != i) {
            this.baseColor = i;
        }
        initText();
        postInvalidate();
    }

    public void setPrimaryColor(int i) {
        this.primaryColor = i;
        postInvalidate();
    }

    public void setMetTextColor(int i) {
        this.textColorStateList = ColorStateList.valueOf(i);
        resetTextColor();
    }

    public void setMetTextColor(ColorStateList colorStateList) {
        this.textColorStateList = colorStateList;
        resetTextColor();
    }

    private void resetTextColor() {
        ColorStateList colorStateList = this.textColorStateList;
        if (colorStateList == null) {
            int[][] iArr = {new int[]{16842910}, EMPTY_STATE_SET};
            int i = this.baseColor;
            this.textColorStateList = new ColorStateList(iArr, new int[]{(i & ViewCompat.MEASURED_SIZE_MASK) | -553648128, (i & ViewCompat.MEASURED_SIZE_MASK) | 1140850688});
            setTextColor(this.textColorStateList);
            return;
        }
        setTextColor(colorStateList);
    }

    public void setMetHintTextColor(int i) {
        this.textColorHintStateList = ColorStateList.valueOf(i);
        resetHintTextColor();
    }

    public void setMetHintTextColor(ColorStateList colorStateList) {
        this.textColorHintStateList = colorStateList;
        resetHintTextColor();
    }

    private void resetHintTextColor() {
        ColorStateList colorStateList = this.textColorHintStateList;
        if (colorStateList == null) {
            setHintTextColor((this.baseColor & ViewCompat.MEASURED_SIZE_MASK) | 1140850688);
        } else {
            setHintTextColor(colorStateList);
        }
    }

    private void setFloatingLabelInternal(int i) {
        if (i == 1) {
            this.floatingLabelEnabled = true;
            this.highlightFloatingLabel = false;
        } else if (i != 2) {
            this.floatingLabelEnabled = false;
            this.highlightFloatingLabel = false;
        } else {
            this.floatingLabelEnabled = true;
            this.highlightFloatingLabel = true;
        }
    }

    public void setFloatingLabel(int i) {
        setFloatingLabelInternal(i);
        initPadding();
    }

    public int getFloatingLabelPadding() {
        return this.floatingLabelPadding;
    }

    public void setFloatingLabelPadding(int i) {
        this.floatingLabelPadding = i;
        postInvalidate();
    }

    public boolean isFloatingLabelAnimating() {
        return this.floatingLabelAnimating;
    }

    public void setFloatingLabelAnimating(boolean z) {
        this.floatingLabelAnimating = z;
    }

    public void setSingleLineEllipsis() {
        setSingleLineEllipsis(true);
    }

    public void setSingleLineEllipsis(boolean z) {
        this.singleLineEllipsis = z;
        initMinBottomLines();
        initPadding();
        postInvalidate();
    }

    public int getMaxCharacters() {
        return this.maxCharacters;
    }

    public void setMaxCharacters(int i) {
        this.maxCharacters = i;
        initMinBottomLines();
        initPadding();
        postInvalidate();
    }

    public int getMinCharacters() {
        return this.minCharacters;
    }

    public void setMinCharacters(int i) {
        this.minCharacters = i;
        initMinBottomLines();
        initPadding();
        postInvalidate();
    }

    public int getMinBottomTextLines() {
        return this.minBottomTextLines;
    }

    public void setMinBottomTextLines(int i) {
        this.minBottomTextLines = i;
        initMinBottomLines();
        initPadding();
        postInvalidate();
    }

    public boolean isAutoValidate() {
        return this.autoValidate;
    }

    public void setAutoValidate(boolean z) {
        this.autoValidate = z;
        if (z) {
            validate();
        }
    }

    public int getErrorColor() {
        return this.errorColor;
    }

    public void setErrorColor(int i) {
        this.errorColor = i;
        postInvalidate();
    }

    public void setHelperText(CharSequence charSequence) {
        this.helperText = charSequence == null ? null : charSequence.toString();
        if (adjustBottomLines()) {
            postInvalidate();
        }
    }

    public String getHelperText() {
        return this.helperText;
    }

    public int getHelperTextColor() {
        return this.helperTextColor;
    }

    public void setHelperTextColor(int i) {
        this.helperTextColor = i;
        postInvalidate();
    }

    public void setError(CharSequence charSequence) {
        this.tempErrorText = charSequence == null ? null : charSequence.toString();
        if (adjustBottomLines()) {
            postInvalidate();
        }
    }

    public CharSequence getError() {
        return this.tempErrorText;
    }

    private boolean isInternalValid() {
        return this.tempErrorText == null && isCharactersCountValid();
    }

    @Deprecated
    public boolean isValid(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile(str).matcher(getText()).matches();
    }

    @Deprecated
    public boolean validate(String str, CharSequence charSequence) {
        boolean isValid = isValid(str);
        if (!isValid) {
            setError(charSequence);
        }
        postInvalidate();
        return isValid;
    }

    public boolean validateWith(METValidator mETValidator) {
        Editable text = getText();
        boolean isValid = mETValidator.isValid(text, text.length() == 0);
        if (!isValid) {
            setError(mETValidator.getErrorMessage());
        }
        postInvalidate();
        return isValid;
    }

    public boolean validate() {
        List<METValidator> list = this.validators;
        if (list == null || list.isEmpty()) {
            return true;
        }
        Editable text = getText();
        boolean z = text.length() == 0;
        Iterator it = this.validators.iterator();
        boolean z2 = true;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            METValidator mETValidator = (METValidator) it.next();
            if (!z2 || !mETValidator.isValid(text, z)) {
                z2 = false;
                continue;
            } else {
                z2 = true;
                continue;
            }
            if (!z2) {
                setError(mETValidator.getErrorMessage());
                break;
            }
        }
        if (z2) {
            setError(null);
        }
        postInvalidate();
        return z2;
    }

    public boolean hasValidators() {
        List<METValidator> list = this.validators;
        return list != null && !list.isEmpty();
    }

    public MaterialAutoCompleteTextView addValidator(METValidator mETValidator) {
        if (this.validators == null) {
            this.validators = new ArrayList();
        }
        this.validators.add(mETValidator);
        return this;
    }

    public void clearValidators() {
        List<METValidator> list = this.validators;
        if (list != null) {
            list.clear();
        }
    }

    public List<METValidator> getValidators() {
        return this.validators;
    }

    public void setLengthChecker(METLengthChecker mETLengthChecker) {
        this.lengthChecker = mETLengthChecker;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        if (this.innerFocusChangeListener == null) {
            super.setOnFocusChangeListener(onFocusChangeListener);
        } else {
            this.outerFocusChangeListener = onFocusChangeListener;
        }
    }

    /* access modifiers changed from: private */
    public ObjectAnimator getLabelAnimator() {
        if (this.labelAnimator == null) {
            this.labelAnimator = ObjectAnimator.ofFloat((Object) this, "floatingLabelFraction", 0.0f, 1.0f);
        }
        this.labelAnimator.setDuration(this.floatingLabelAnimating ? 300 : 0);
        return this.labelAnimator;
    }

    /* access modifiers changed from: private */
    public ObjectAnimator getLabelFocusAnimator() {
        if (this.labelFocusAnimator == null) {
            this.labelFocusAnimator = ObjectAnimator.ofFloat((Object) this, "focusFraction", 0.0f, 1.0f);
        }
        return this.labelFocusAnimator;
    }

    private ObjectAnimator getBottomLinesAnimator(float f) {
        ObjectAnimator objectAnimator = this.bottomLinesAnimator;
        if (objectAnimator == null) {
            this.bottomLinesAnimator = ObjectAnimator.ofFloat((Object) this, "currentBottomLines", f);
        } else {
            objectAnimator.cancel();
            this.bottomLinesAnimator.setFloatValues(f);
        }
        return this.bottomLinesAnimator;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        Canvas canvas2 = canvas;
        int scrollX = getScrollX() + (this.iconLeftBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding);
        int scrollX2 = getScrollX() + (this.iconRightBitmaps == null ? getWidth() : (getWidth() - this.iconOuterWidth) - this.iconPadding);
        int scrollY = (getScrollY() + getHeight()) - getPaddingBottom();
        this.paint.setAlpha(255);
        Bitmap[] bitmapArr = this.iconLeftBitmaps;
        if (bitmapArr != null) {
            char c = !isInternalValid() ? 3 : !isEnabled() ? 2 : hasFocus() ? (char) 1 : 0;
            Bitmap bitmap = bitmapArr[c];
            int i4 = scrollX - this.iconPadding;
            int i5 = this.iconOuterWidth;
            int width = (i4 - i5) + ((i5 - bitmap.getWidth()) / 2);
            int i6 = this.bottomSpacing + scrollY;
            int i7 = this.iconOuterHeight;
            canvas2.drawBitmap(bitmap, (float) width, (float) ((i6 - i7) + ((i7 - bitmap.getHeight()) / 2)), this.paint);
        }
        Bitmap[] bitmapArr2 = this.iconRightBitmaps;
        if (bitmapArr2 != null) {
            char c2 = !isInternalValid() ? 3 : !isEnabled() ? 2 : hasFocus() ? (char) 1 : 0;
            Bitmap bitmap2 = bitmapArr2[c2];
            int width2 = this.iconPadding + scrollX2 + ((this.iconOuterWidth - bitmap2.getWidth()) / 2);
            int i8 = this.bottomSpacing + scrollY;
            int i9 = this.iconOuterHeight;
            canvas2.drawBitmap(bitmap2, (float) width2, (float) ((i8 - i9) + ((i9 - bitmap2.getHeight()) / 2)), this.paint);
        }
        if (hasFocus() && this.showClearButton && !TextUtils.isEmpty(getText())) {
            this.paint.setAlpha(255);
            if (isRTL()) {
                i3 = scrollX;
            } else {
                i3 = scrollX2 - this.iconOuterWidth;
            }
            Bitmap bitmap3 = this.clearButtonBitmaps[0];
            int width3 = i3 + ((this.iconOuterWidth - bitmap3.getWidth()) / 2);
            int i10 = this.bottomSpacing + scrollY;
            int i11 = this.iconOuterHeight;
            canvas2.drawBitmap(bitmap3, (float) width3, (float) ((i10 - i11) + ((i11 - bitmap3.getHeight()) / 2)), this.paint);
        }
        int i12 = -1;
        if (!this.hideUnderline) {
            int i13 = scrollY + this.bottomSpacing;
            if (!isInternalValid()) {
                this.paint.setColor(this.errorColor);
                i2 = i13;
                canvas.drawRect((float) scrollX, (float) i13, (float) scrollX2, (float) (getPixel(2) + i13), this.paint);
            } else {
                i2 = i13;
                if (!isEnabled()) {
                    Paint paint2 = this.paint;
                    int i14 = this.underlineColor;
                    if (i14 == -1) {
                        i14 = (this.baseColor & ViewCompat.MEASURED_SIZE_MASK) | 1140850688;
                    }
                    paint2.setColor(i14);
                    float pixel = (float) getPixel(1);
                    float f = 0.0f;
                    while (f < ((float) getWidth())) {
                        float f2 = ((float) scrollX) + f;
                        float f3 = pixel;
                        canvas.drawRect(f2, (float) i2, f2 + pixel, (float) (getPixel(1) + i2), this.paint);
                        f += f3 * 3.0f;
                        pixel = f3;
                    }
                } else if (hasFocus()) {
                    this.paint.setColor(this.primaryColor);
                    canvas.drawRect((float) scrollX, (float) i2, (float) scrollX2, (float) (i2 + getPixel(2)), this.paint);
                } else {
                    Paint paint3 = this.paint;
                    int i15 = this.underlineColor;
                    if (i15 == -1) {
                        i15 = (this.baseColor & ViewCompat.MEASURED_SIZE_MASK) | 503316480;
                    }
                    paint3.setColor(i15);
                    canvas.drawRect((float) scrollX, (float) i2, (float) scrollX2, (float) (i2 + getPixel(1)), this.paint);
                }
            }
            scrollY = i2;
        }
        this.textPaint.setTextSize((float) this.bottomTextSize);
        FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        float f4 = (-fontMetrics.ascent) - fontMetrics.descent;
        float f5 = ((float) this.bottomTextSize) + fontMetrics.ascent + fontMetrics.descent;
        if ((hasFocus() && hasCharactersCounter()) || !isCharactersCountValid()) {
            this.textPaint.setColor(isCharactersCountValid() ? (this.baseColor & ViewCompat.MEASURED_SIZE_MASK) | 1140850688 : this.errorColor);
            String charactersCounterText = getCharactersCounterText();
            canvas2.drawText(charactersCounterText, isRTL() ? (float) scrollX : ((float) scrollX2) - this.textPaint.measureText(charactersCounterText), ((float) (this.bottomSpacing + scrollY)) + f4, this.textPaint);
        }
        if (this.textLayout != null && (this.tempErrorText != null || ((this.helperTextAlwaysShown || hasFocus()) && !TextUtils.isEmpty(this.helperText)))) {
            TextPaint textPaint2 = this.textPaint;
            if (this.tempErrorText != null) {
                i = this.errorColor;
            } else {
                i = this.helperTextColor;
                if (i == -1) {
                    i = (this.baseColor & ViewCompat.MEASURED_SIZE_MASK) | 1140850688;
                }
            }
            textPaint2.setColor(i);
            canvas.save();
            if (isRTL()) {
                canvas2.translate((float) (scrollX2 - this.textLayout.getWidth()), ((float) (this.bottomSpacing + scrollY)) - f5);
            } else {
                canvas2.translate((float) (getBottomTextLeftOffset() + scrollX), ((float) (this.bottomSpacing + scrollY)) - f5);
            }
            this.textLayout.draw(canvas2);
            canvas.restore();
        }
        if (this.floatingLabelEnabled && !TextUtils.isEmpty(this.floatingLabelText)) {
            this.textPaint.setTextSize((float) this.floatingLabelTextSize);
            TextPaint textPaint3 = this.textPaint;
            ArgbEvaluator argbEvaluator = this.focusEvaluator;
            float f6 = this.focusFraction;
            int i16 = this.floatingLabelTextColor;
            if (i16 == -1) {
                i16 = (this.baseColor & ViewCompat.MEASURED_SIZE_MASK) | 1140850688;
            }
            textPaint3.setColor(((Integer) argbEvaluator.evaluate(f6, Integer.valueOf(i16), Integer.valueOf(this.primaryColor))).intValue());
            float measureText = this.textPaint.measureText(this.floatingLabelText.toString());
            int i17 = ((getGravity() & 5) == 5 || isRTL()) ? (int) (((float) scrollX2) - measureText) : (getGravity() & 3) == 3 ? scrollX : ((int) (((float) getInnerPaddingLeft()) + ((((float) ((getWidth() - getInnerPaddingLeft()) - getInnerPaddingRight())) - measureText) / 2.0f))) + scrollX;
            int i18 = this.floatingLabelPadding;
            float f7 = 1.0f;
            int scrollY2 = (int) ((((float) ((this.innerPaddingTop + this.floatingLabelTextSize) + i18)) - (((float) i18) * (this.floatingLabelAlwaysShown ? 1.0f : this.floatingLabelFraction))) + ((float) getScrollY()));
            float f8 = (this.floatingLabelAlwaysShown ? 1.0f : this.floatingLabelFraction) * 255.0f * ((this.focusFraction * 0.74f) + 0.26f);
            int i19 = this.floatingLabelTextColor;
            if (i19 == -1) {
                f7 = ((float) Color.alpha(i19)) / 256.0f;
            }
            this.textPaint.setAlpha((int) (f8 * f7));
            canvas2.drawText(this.floatingLabelText.toString(), (float) i17, (float) scrollY2, this.textPaint);
        }
        if (hasFocus() && this.singleLineEllipsis && getScrollX() != 0) {
            this.paint.setColor(isInternalValid() ? this.primaryColor : this.errorColor);
            float f9 = (float) (scrollY + this.bottomSpacing);
            if (isRTL()) {
                scrollX = scrollX2;
            }
            if (!isRTL()) {
                i12 = 1;
            }
            int i20 = this.bottomEllipsisSize;
            canvas2.drawCircle((float) (((i12 * i20) / 2) + scrollX), ((float) (i20 / 2)) + f9, (float) (i20 / 2), this.paint);
            int i21 = this.bottomEllipsisSize;
            canvas2.drawCircle((float) ((((i12 * i21) * 5) / 2) + scrollX), ((float) (i21 / 2)) + f9, (float) (i21 / 2), this.paint);
            int i22 = this.bottomEllipsisSize;
            canvas2.drawCircle((float) (scrollX + (((i12 * i22) * 9) / 2)), f9 + ((float) (i22 / 2)), (float) (i22 / 2), this.paint);
        }
        super.onDraw(canvas);
    }

    private boolean isRTL() {
        boolean z = false;
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (getResources().getConfiguration().getLayoutDirection() == 1) {
            z = true;
        }
        return z;
    }

    private int getBottomTextLeftOffset() {
        return isRTL() ? getCharactersCounterWidth() : getBottomEllipsisWidth();
    }

    private int getBottomTextRightOffset() {
        return isRTL() ? getBottomEllipsisWidth() : getCharactersCounterWidth();
    }

    private int getCharactersCounterWidth() {
        if (hasCharactersCounter()) {
            return (int) this.textPaint.measureText(getCharactersCounterText());
        }
        return 0;
    }

    private int getBottomEllipsisWidth() {
        if (this.singleLineEllipsis) {
            return (this.bottomEllipsisSize * 5) + getPixel(4);
        }
        return 0;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        if (r0 > r3) goto L_0x002a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkCharactersCount() {
        /*
            r4 = this;
            boolean r0 = r4.firstShown
            r1 = 1
            if (r0 != 0) goto L_0x0009
            boolean r0 = r4.checkCharactersCountAtBeginning
            if (r0 == 0) goto L_0x000f
        L_0x0009:
            boolean r0 = r4.hasCharactersCounter()
            if (r0 != 0) goto L_0x0012
        L_0x000f:
            r4.charactersCountValid = r1
            goto L_0x002d
        L_0x0012:
            android.text.Editable r0 = r4.getText()
            r2 = 0
            if (r0 != 0) goto L_0x001b
            r0 = 0
            goto L_0x001f
        L_0x001b:
            int r0 = r4.checkLength(r0)
        L_0x001f:
            int r3 = r4.minCharacters
            if (r0 < r3) goto L_0x002a
            int r3 = r4.maxCharacters
            if (r3 <= 0) goto L_0x002b
            if (r0 > r3) goto L_0x002a
            goto L_0x002b
        L_0x002a:
            r1 = 0
        L_0x002b:
            r4.charactersCountValid = r1
        L_0x002d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rengwuxian.materialedittext.MaterialAutoCompleteTextView.checkCharactersCount():void");
    }

    public boolean isCharactersCountValid() {
        return this.charactersCountValid;
    }

    private boolean hasCharactersCounter() {
        return this.minCharacters > 0 || this.maxCharacters > 0;
    }

    private String getCharactersCounterText() {
        int i;
        StringBuilder sb;
        StringBuilder sb2;
        int i2;
        StringBuilder sb3;
        String str = " / ";
        if (this.minCharacters <= 0) {
            if (isRTL()) {
                sb3 = new StringBuilder();
                sb3.append(this.maxCharacters);
                sb3.append(str);
                i2 = checkLength(getText());
            } else {
                sb3 = new StringBuilder();
                sb3.append(checkLength(getText()));
                sb3.append(str);
                i2 = this.maxCharacters;
            }
            sb3.append(i2);
            return sb3.toString();
        } else if (this.maxCharacters <= 0) {
            boolean isRTL = isRTL();
            String str2 = Marker.ANY_NON_NULL_MARKER;
            if (isRTL) {
                sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(this.minCharacters);
                sb2.append(str);
                sb2.append(checkLength(getText()));
            } else {
                sb2 = new StringBuilder();
                sb2.append(checkLength(getText()));
                sb2.append(str);
                sb2.append(this.minCharacters);
                sb2.append(str2);
            }
            return sb2.toString();
        } else {
            String str3 = "-";
            if (isRTL()) {
                sb = new StringBuilder();
                sb.append(this.maxCharacters);
                sb.append(str3);
                sb.append(this.minCharacters);
                sb.append(str);
                i = checkLength(getText());
            } else {
                sb = new StringBuilder();
                sb.append(checkLength(getText()));
                sb.append(str);
                sb.append(this.minCharacters);
                sb.append(str3);
                i = this.maxCharacters;
            }
            sb.append(i);
            return sb.toString();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.singleLineEllipsis || getScrollX() <= 0 || motionEvent.getAction() != 0 || motionEvent.getX() >= ((float) getPixel(20)) || motionEvent.getY() <= ((float) ((getHeight() - this.extraPaddingBottom) - this.innerPaddingBottom)) || motionEvent.getY() >= ((float) (getHeight() - this.innerPaddingBottom))) {
            if (hasFocus() && this.showClearButton) {
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action == 1) {
                        if (this.clearButtonClicking) {
                            if (!TextUtils.isEmpty(getText())) {
                                setText(null);
                            }
                            this.clearButtonClicking = false;
                        }
                        if (this.clearButtonTouched) {
                            this.clearButtonTouched = false;
                            return true;
                        }
                        this.clearButtonTouched = false;
                    } else if (action != 2) {
                        if (action == 3) {
                            this.clearButtonTouched = false;
                            this.clearButtonClicking = false;
                        }
                    }
                } else if (insideClearButton(motionEvent)) {
                    this.clearButtonTouched = true;
                    this.clearButtonClicking = true;
                    return true;
                }
                if (this.clearButtonClicking && !insideClearButton(motionEvent)) {
                    this.clearButtonClicking = false;
                }
                if (this.clearButtonTouched) {
                    return true;
                }
            }
            return super.onTouchEvent(motionEvent);
        }
        setSelection(0);
        return false;
    }

    private boolean insideClearButton(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int scrollX = getScrollX() + (this.iconLeftBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding);
        int scrollX2 = getScrollX() + (this.iconRightBitmaps == null ? getWidth() : (getWidth() - this.iconOuterWidth) - this.iconPadding);
        if (!isRTL()) {
            scrollX = scrollX2 - this.iconOuterWidth;
        }
        int scrollY = ((getScrollY() + getHeight()) - getPaddingBottom()) + this.bottomSpacing;
        int i = this.iconOuterHeight;
        int i2 = scrollY - i;
        if (x < ((float) scrollX) || x >= ((float) (scrollX + this.iconOuterWidth)) || y < ((float) i2) || y >= ((float) (i2 + i))) {
            return false;
        }
        return true;
    }

    private int checkLength(CharSequence charSequence) {
        METLengthChecker mETLengthChecker = this.lengthChecker;
        if (mETLengthChecker == null) {
            return charSequence.length();
        }
        return mETLengthChecker.getLength(charSequence);
    }
}
