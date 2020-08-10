package com.nightonke.boommenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.nightonke.boommenu.Animation.AnimationManager;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.Animation.Ease;
import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.Animation.HideRgbEvaluator;
import com.nightonke.boommenu.Animation.OrderEnum;
import com.nightonke.boommenu.Animation.Rotate3DAnimation;
import com.nightonke.boommenu.Animation.ShareLinesView;
import com.nightonke.boommenu.Animation.ShowRgbEvaluator;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.BoomButtonBuilder;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceManager;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.InnerOnBoomButtonClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton.Builder;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.Piece.BoomPiece;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Piece.PiecePlaceManager;
import java.util.ArrayList;
import java.util.Iterator;

public class BoomMenuButton extends FrameLayout implements InnerOnBoomButtonClickListener {
    protected static final String TAG = "BoomMenuButton";
    /* access modifiers changed from: private */
    public boolean ableToStartDragging = false;
    /* access modifiers changed from: private */
    public int animatingViewNumber = 0;
    private boolean autoBoom;
    private boolean autoBoomImmediately;
    private boolean autoHide;
    /* access modifiers changed from: private */
    public BackgroundView background;
    private boolean backgroundEffect;
    private ArrayList<BoomButtonBuilder> boomButtonBuilders = new ArrayList<>();
    private ArrayList<BoomButton> boomButtons = new ArrayList<>();
    private BoomEnum boomEnum;
    private boolean boomInWholeScreen;
    /* access modifiers changed from: private */
    public BoomStateEnum boomStateEnum = BoomStateEnum.DidReboom;
    private float bottomHamButtonTopMargin;
    /* access modifiers changed from: private */
    public FrameLayout button;
    private float buttonBottomMargin;
    private ButtonEnum buttonEnum = ButtonEnum.Unknown;
    private float buttonHorizontalMargin;
    private float buttonInclinedMargin;
    private float buttonLeftMargin;
    private ButtonPlaceAlignmentEnum buttonPlaceAlignmentEnum;
    private ButtonPlaceEnum buttonPlaceEnum = ButtonPlaceEnum.Unknown;
    private int buttonRadius;
    private float buttonRightMargin;
    private float buttonTopMargin;
    private float buttonVerticalMargin;
    private boolean cacheOptimization;
    private boolean cancelable;
    private Context context;
    private ArrayList<PointF> customButtonPlacePositions = new ArrayList<>();
    private ArrayList<PointF> customPiecePlacePositions = new ArrayList<>();
    private int dimColor;
    private float dotRadius;
    /* access modifiers changed from: private */
    public boolean draggable;
    private Rect edgeInsetsInParentView;
    private ArrayList<PointF> endPositions;
    private int frames;
    private float hamButtonHeight;
    private float hamButtonWidth;
    private float hamHeight;
    private float hamWidth;
    private long hideDelay;
    private long hideDuration;
    private EaseEnum hideMoveEaseEnum;
    private EaseEnum hideRotateEaseEnum;
    private EaseEnum hideScaleEaseEnum;
    private int highlightedColor;
    private boolean inFragment;
    private boolean inList;
    private boolean isBackPressListened = true;
    /* access modifiers changed from: private */
    public boolean isDragging = false;
    private boolean isOrientationAdaptable;
    /* access modifiers changed from: private */
    public boolean isOrientationChanged = false;
    /* access modifiers changed from: private */
    public float lastMotionX = -1.0f;
    /* access modifiers changed from: private */
    public float lastMotionY = -1.0f;
    /* access modifiers changed from: private */
    public int lastOrientation = -1;
    private int lastReboomIndex = -1;
    private Runnable layoutJobsRunnable;
    /* access modifiers changed from: private */
    public boolean needToCalculateStartPositions = true;
    private boolean needToCreateButtons = true;
    private boolean needToLayout = true;
    private int normalColor;
    /* access modifiers changed from: private */
    public OnBoomListener onBoomListener;
    private OrderEnum orderEnum;
    private OrientationEventListener orientationEventListener;
    private float pieceCornerRadius = -1.0f;
    private float pieceHorizontalMargin;
    private float pieceInclinedMargin;
    private PiecePlaceEnum piecePlaceEnum = PiecePlaceEnum.Unknown;
    private ArrayList<RectF> piecePositions;
    private float pieceVerticalMargin;
    private ArrayList<BoomPiece> pieces;
    private boolean rippleEffect;
    private int rotateDegree;
    /* access modifiers changed from: private */
    public BMBShadow shadow;
    private int shadowColor;
    private boolean shadowEffect;
    private int shadowOffsetX;
    private int shadowOffsetY;
    private int shadowRadius;
    private int shareLine1Color;
    private int shareLine2Color;
    private float shareLineLength;
    private float shareLineWidth;
    private ShareLinesView shareLinesView;
    private long showDelay;
    private long showDuration;
    private EaseEnum showMoveEaseEnum;
    private EaseEnum showRotateEaseEnum;
    private EaseEnum showScaleEaseEnum;
    private float simpleCircleButtonRadius;
    /* access modifiers changed from: private */
    public float startPositionX;
    /* access modifiers changed from: private */
    public float startPositionY;
    private ArrayList<PointF> startPositions;
    private float textInsideCircleButtonRadius;
    private float textOutsideCircleButtonHeight;
    private float textOutsideCircleButtonWidth;
    private int unableColor;
    private boolean use3DTransformAnimation;

    /* renamed from: com.nightonke.boommenu.BoomMenuButton$12 */
    static /* synthetic */ class C450212 {
        static final /* synthetic */ int[] $SwitchMap$com$nightonke$boommenu$BoomStateEnum = new int[BoomStateEnum.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$nightonke$boommenu$ButtonEnum = new int[ButtonEnum.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(23:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0066 */
        static {
            /*
                com.nightonke.boommenu.BoomStateEnum[] r0 = com.nightonke.boommenu.BoomStateEnum.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$nightonke$boommenu$BoomStateEnum = r0
                r0 = 1
                int[] r1 = $SwitchMap$com$nightonke$boommenu$BoomStateEnum     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.nightonke.boommenu.BoomStateEnum r2 = com.nightonke.boommenu.BoomStateEnum.DidReboom     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$com$nightonke$boommenu$BoomStateEnum     // Catch:{ NoSuchFieldError -> 0x001f }
                com.nightonke.boommenu.BoomStateEnum r3 = com.nightonke.boommenu.BoomStateEnum.DidBoom     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = $SwitchMap$com$nightonke$boommenu$BoomStateEnum     // Catch:{ NoSuchFieldError -> 0x002a }
                com.nightonke.boommenu.BoomStateEnum r4 = com.nightonke.boommenu.BoomStateEnum.WillBoom     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = $SwitchMap$com$nightonke$boommenu$BoomStateEnum     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.nightonke.boommenu.BoomStateEnum r5 = com.nightonke.boommenu.BoomStateEnum.WillReboom     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                com.nightonke.boommenu.ButtonEnum[] r4 = com.nightonke.boommenu.ButtonEnum.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                $SwitchMap$com$nightonke$boommenu$ButtonEnum = r4
                int[] r4 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x0048 }
                com.nightonke.boommenu.ButtonEnum r5 = com.nightonke.boommenu.ButtonEnum.SimpleCircle     // Catch:{ NoSuchFieldError -> 0x0048 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0048 }
                r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x0048 }
            L_0x0048:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x0052 }
                com.nightonke.boommenu.ButtonEnum r4 = com.nightonke.boommenu.ButtonEnum.TextInsideCircle     // Catch:{ NoSuchFieldError -> 0x0052 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0052 }
                r0[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0052 }
            L_0x0052:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x005c }
                com.nightonke.boommenu.ButtonEnum r1 = com.nightonke.boommenu.ButtonEnum.TextOutsideCircle     // Catch:{ NoSuchFieldError -> 0x005c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005c }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005c }
            L_0x005c:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x0066 }
                com.nightonke.boommenu.ButtonEnum r1 = com.nightonke.boommenu.ButtonEnum.Ham     // Catch:{ NoSuchFieldError -> 0x0066 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0066 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0066 }
            L_0x0066:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x0071 }
                com.nightonke.boommenu.ButtonEnum r1 = com.nightonke.boommenu.ButtonEnum.Unknown     // Catch:{ NoSuchFieldError -> 0x0071 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0071 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0071 }
            L_0x0071:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.BoomMenuButton.C450212.<clinit>():void");
        }
    }

    private void ____________________________Animation() {
    }

    private void ____________________________Builders_and_Buttons() {
    }

    private void ____________________________Fade_Views() {
    }

    private void ____________________________Getters_and_Setters() {
    }

    private void ____________________________Initialization() {
    }

    private void ____________________________Place_Pieces() {
    }

    private void ____________________________Support_Methods() {
    }

    private void ____________________________Touch() {
    }

    public BoomMenuButton(Context context2) {
        super(context2);
        init(context2, null);
    }

    public BoomMenuButton(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2, attributeSet);
    }

    public BoomMenuButton(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2, attributeSet);
    }

    private void init(Context context2, AttributeSet attributeSet) {
        this.context = context2;
        LayoutInflater.from(context2).inflate(C4514R.layout.bmb, this, true);
        initAttrs(context2, attributeSet);
        initShadow();
        initButton();
    }

    private void initAttrs(Context context2, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, C4514R.styleable.BoomMenuButton, 0, 0);
        if (obtainStyledAttributes != null) {
            try {
                this.cacheOptimization = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_cacheOptimization, C4514R.bool.default_bmb_cacheOptimization);
                this.boomInWholeScreen = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_boomInWholeScreen, C4514R.bool.default_bmb_boomInWholeScreen);
                this.inList = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_inList, C4514R.bool.default_bmb_inList);
                this.inFragment = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_inFragment, C4514R.bool.default_bmb_inFragment);
                this.isBackPressListened = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_backPressListened, C4514R.bool.default_bmb_backPressListened);
                this.isOrientationAdaptable = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_orientationAdaptable, C4514R.bool.default_bmb_orientationAdaptable);
                this.shadowEffect = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shadowEffect, C4514R.bool.default_bmb_shadow_effect);
                this.shadowRadius = Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shadowRadius, C4514R.dimen.default_bmb_shadow_radius);
                this.shadowOffsetX = Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shadowOffsetX, C4514R.dimen.default_bmb_shadow_offset_x);
                this.shadowOffsetY = Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shadowOffsetY, C4514R.dimen.default_bmb_shadow_offset_y);
                this.shadowColor = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shadowColor, C4514R.C4515color.default_bmb_shadow_color);
                this.buttonRadius = Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonRadius, C4514R.dimen.default_bmb_button_radius);
                this.buttonEnum = ButtonEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonEnum, C4514R.integer.default_bmb_button_enum));
                this.backgroundEffect = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_backgroundEffect, C4514R.bool.default_bmb_background_effect);
                this.rippleEffect = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_rippleEffect, C4514R.bool.default_bmb_ripple_effect);
                this.normalColor = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_normalColor, C4514R.C4515color.default_bmb_normal_color);
                this.highlightedColor = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_highlightedColor, C4514R.C4515color.default_bmb_highlighted_color);
                if (this.highlightedColor == 0) {
                    this.highlightedColor = Util.getDarkerColor(this.normalColor);
                }
                this.unableColor = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_unableColor, C4514R.C4515color.default_bmb_unable_color);
                if (this.unableColor == 0) {
                    this.unableColor = Util.getLighterColor(this.normalColor);
                }
                this.draggable = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_draggable, C4514R.bool.default_bmb_draggable);
                this.edgeInsetsInParentView = new Rect(0, 0, 0, 0);
                this.edgeInsetsInParentView.left = Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_edgeInsetsLeft, C4514R.dimen.default_bmb_edgeInsetsLeft);
                this.edgeInsetsInParentView.top = Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_edgeInsetsTop, C4514R.dimen.default_bmb_edgeInsetsTop);
                this.edgeInsetsInParentView.right = Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_edgeInsetsRight, C4514R.dimen.default_bmb_edgeInsetsRight);
                this.edgeInsetsInParentView.bottom = Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_edgeInsetsBottom, C4514R.dimen.default_bmb_edgeInsetsBottom);
                this.dotRadius = (float) Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_dotRadius, C4514R.dimen.default_bmb_dotRadius);
                this.hamWidth = (float) Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hamWidth, C4514R.dimen.default_bmb_hamWidth);
                this.hamHeight = (float) Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hamHeight, C4514R.dimen.default_bmb_hamHeight);
                this.pieceCornerRadius = (float) Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_pieceCornerRadius, C4514R.dimen.default_bmb_pieceCornerRadius);
                this.pieceHorizontalMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_pieceHorizontalMargin, C4514R.dimen.default_bmb_pieceHorizontalMargin);
                this.pieceVerticalMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_pieceVerticalMargin, C4514R.dimen.default_bmb_pieceVerticalMargin);
                this.pieceInclinedMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_pieceInclinedMargin, C4514R.dimen.default_bmb_pieceInclinedMargin);
                this.shareLineLength = (float) Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_sharedLineLength, C4514R.dimen.default_bmb_sharedLineLength);
                this.shareLine1Color = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shareLine1Color, C4514R.C4515color.default_bmb_shareLine1Color);
                this.shareLine2Color = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shareLine2Color, C4514R.C4515color.default_bmb_shareLine2Color);
                this.shareLineWidth = (float) Util.getDimenSize(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_shareLineWidth, C4514R.dimen.default_bmb_shareLineWidth);
                this.piecePlaceEnum = PiecePlaceEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_piecePlaceEnum, C4514R.integer.default_bmb_pieceEnum));
                this.dimColor = Util.getColor(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_dimColor, C4514R.C4515color.default_bmb_dimColor);
                this.showDuration = (long) Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_showDuration, C4514R.integer.default_bmb_showDuration);
                this.showDelay = (long) Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_showDelay, C4514R.integer.default_bmb_showDelay);
                this.hideDuration = (long) Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hideDuration, C4514R.integer.default_bmb_hideDuration);
                this.hideDelay = (long) Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hideDelay, C4514R.integer.default_bmb_hideDelay);
                this.cancelable = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_cancelable, C4514R.bool.default_bmb_cancelable);
                this.autoHide = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_autoHide, C4514R.bool.default_bmb_autoHide);
                this.orderEnum = OrderEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_orderEnum, C4514R.integer.default_bmb_orderEnum));
                this.frames = Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_frames, C4514R.integer.default_bmb_frames);
                this.boomEnum = BoomEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_boomEnum, C4514R.integer.default_bmb_boomEnum));
                this.showMoveEaseEnum = EaseEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_showMoveEaseEnum, C4514R.integer.default_bmb_showMoveEaseEnum));
                this.showScaleEaseEnum = EaseEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_showScaleEaseEnum, C4514R.integer.default_bmb_showScaleEaseEnum));
                this.showRotateEaseEnum = EaseEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_showRotateEaseEnum, C4514R.integer.default_bmb_showRotateEaseEnum));
                this.hideMoveEaseEnum = EaseEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hideMoveEaseEnum, C4514R.integer.default_bmb_hideMoveEaseEnum));
                this.hideScaleEaseEnum = EaseEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hideScaleEaseEnum, C4514R.integer.default_bmb_hideScaleEaseEnum));
                this.hideRotateEaseEnum = EaseEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_hideRotateEaseEnum, C4514R.integer.default_bmb_hideRotateEaseEnum));
                this.rotateDegree = Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_rotateDegree, C4514R.integer.default_bmb_rotateDegree);
                this.use3DTransformAnimation = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_use3DTransformAnimation, C4514R.bool.default_bmb_use3DTransformAnimation);
                this.autoBoom = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_autoBoom, C4514R.bool.default_bmb_autoBoom);
                this.autoBoomImmediately = Util.getBoolean(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_autoBoomImmediately, C4514R.bool.default_bmb_autoBoomImmediately);
                this.buttonPlaceEnum = ButtonPlaceEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonPlaceEnum, C4514R.integer.default_bmb_buttonPlaceEnum));
                this.buttonPlaceAlignmentEnum = ButtonPlaceAlignmentEnum.getEnum(Util.getInt(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonPlaceAlignmentEnum, C4514R.integer.default_bmb_buttonPlaceAlignmentEnum));
                this.buttonHorizontalMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonHorizontalMargin, C4514R.dimen.default_bmb_buttonHorizontalMargin);
                this.buttonVerticalMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonVerticalMargin, C4514R.dimen.default_bmb_buttonVerticalMargin);
                this.buttonInclinedMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonInclinedMargin, C4514R.dimen.default_bmb_buttonInclinedMargin);
                this.buttonTopMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonTopMargin, C4514R.dimen.default_bmb_buttonTopMargin);
                this.buttonBottomMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonBottomMargin, C4514R.dimen.default_bmb_buttonBottomMargin);
                this.buttonLeftMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonLeftMargin, C4514R.dimen.default_bmb_buttonLeftMargin);
                this.buttonRightMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_buttonRightMargin, C4514R.dimen.default_bmb_buttonRightMargin);
                this.bottomHamButtonTopMargin = (float) Util.getDimenOffset(obtainStyledAttributes, C4514R.styleable.BoomMenuButton_bmb_bottomHamButtonTopMargin, C4514R.dimen.default_bmb_bottomHamButtonTopMargin);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    private void initShadow() {
        if (this.shadow == null) {
            this.shadow = (BMBShadow) findViewById(C4514R.C4517id.shadow);
        }
        boolean z = this.shadowEffect && this.backgroundEffect && !this.inList;
        this.shadow.setShadowEffect(z);
        if (z) {
            this.shadow.setShadowOffsetX(this.shadowOffsetX);
            this.shadow.setShadowOffsetY(this.shadowOffsetY);
            this.shadow.setShadowColor(this.shadowColor);
            this.shadow.setShadowRadius(this.shadowRadius);
            this.shadow.setShadowCornerRadius(this.shadowRadius + this.buttonRadius);
            return;
        }
        this.shadow.clearShadow();
    }

    private void initButton() {
        if (this.button == null) {
            this.button = (FrameLayout) findViewById(C4514R.C4517id.button);
        }
        this.button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BoomMenuButton.this.boom();
            }
        });
        initDraggableTouchListener();
        setButtonSize();
        setButtonBackground();
    }

    private void setButtonSize() {
        LayoutParams layoutParams = (LayoutParams) this.button.getLayoutParams();
        int i = this.buttonRadius;
        layoutParams.width = i * 2;
        layoutParams.height = i * 2;
        this.button.setLayoutParams(layoutParams);
    }

    private void setButtonBackground() {
        if (!this.backgroundEffect || this.inList) {
            if (VERSION.SDK_INT >= 21) {
                Util.setDrawable(this.button, Util.getSystemDrawable(this.context, 16843868));
            } else {
                Util.setDrawable(this.button, Util.getSystemDrawable(this.context, 16843534));
            }
        } else if (!this.rippleEffect || VERSION.SDK_INT < 21) {
            Util.setDrawable(this.button, Util.getOvalStateListBitmapDrawable(this.button, this.buttonRadius, this.normalColor, this.highlightedColor, this.unableColor));
        } else {
            Util.setDrawable(this.button, new RippleDrawable(ColorStateList.valueOf(this.highlightedColor), Util.getOvalDrawable(this.button, this.normalColor), null));
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (4 != i || !this.isBackPressListened || (this.boomStateEnum != BoomStateEnum.WillBoom && this.boomStateEnum != BoomStateEnum.DidBoom)) {
            return super.onKeyDown(i, keyEvent);
        }
        reboom();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.isOrientationChanged) {
            reLayoutAfterOrientationChanged();
        }
        if (this.needToLayout) {
            if (this.inList) {
                delayToDoLayoutJobs();
            } else {
                doLayoutJobs();
            }
        }
        this.needToLayout = false;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        checkAutoBoom();
    }

    /* access modifiers changed from: private */
    public void doLayoutJobs() {
        if (!uninitializedBoomButtons()) {
            clearPieces();
            createPieces();
            placeShareLinesView();
            placePieces();
            placePiecesAtPositions();
            calculateStartPositions(false);
            setShareLinesViewData();
        }
    }

    private void clearPieces() {
        ArrayList<BoomPiece> arrayList = this.pieces;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.button.removeView((BoomPiece) it.next());
            }
        }
        ArrayList<BoomPiece> arrayList2 = this.pieces;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
    }

    private void createPieces() {
        calculatePiecePositions();
        int pieceNumber = pieceNumber();
        this.pieces = new ArrayList<>(pieceNumber);
        for (int i = 0; i < pieceNumber; i++) {
            this.pieces.add(PiecePlaceManager.createPiece(this, (BoomButtonBuilder) this.boomButtonBuilders.get(i)));
        }
    }

    private void placePieces() {
        ArrayList arrayList;
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            arrayList = AnimationManager.getOrderIndex(OrderEnum.DEFAULT, this.pieces.size());
        } else {
            arrayList = AnimationManager.getOrderIndex(this.orderEnum, this.pieces.size());
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            this.button.addView((View) this.pieces.get(((Integer) arrayList.get(size)).intValue()));
        }
    }

    private void placePiecesAtPositions() {
        int pieceNumber = pieceNumber();
        for (int i = 0; i < pieceNumber; i++) {
            ((BoomPiece) this.pieces.get(i)).place((RectF) this.piecePositions.get(i));
        }
    }

    private void calculatePiecePositions() {
        int i = C450212.$SwitchMap$com$nightonke$boommenu$ButtonEnum[this.buttonEnum.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
                this.piecePositions = PiecePlaceManager.getShareDotPositions(this, new Point(this.button.getWidth(), this.button.getHeight()), this.boomButtonBuilders.size());
            } else {
                this.piecePositions = PiecePlaceManager.getDotPositions(this, new Point(this.button.getWidth(), this.button.getHeight()));
            }
        } else if (i == 4) {
            this.piecePositions = PiecePlaceManager.getHamPositions(this, new Point(this.button.getWidth(), this.button.getHeight()));
        } else if (i == 5) {
            throw new RuntimeException("The button-enum is unknown!");
        }
    }

    private void initDraggableTouchListener() {
        FrameLayout frameLayout = this.button;
        if (frameLayout != null) {
            if (!this.draggable) {
                frameLayout.setOnTouchListener(null);
            } else {
                frameLayout.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int actionMasked = motionEvent.getActionMasked();
                        if (actionMasked != 0) {
                            if (actionMasked != 1) {
                                if (actionMasked == 2) {
                                    if (Math.abs(BoomMenuButton.this.lastMotionX - motionEvent.getRawX()) > 10.0f || Math.abs(BoomMenuButton.this.lastMotionY - motionEvent.getRawY()) > 10.0f) {
                                        BoomMenuButton.this.ableToStartDragging = true;
                                    }
                                    if (!BoomMenuButton.this.draggable || !BoomMenuButton.this.ableToStartDragging) {
                                        BoomMenuButton.this.ableToStartDragging = false;
                                    } else {
                                        BoomMenuButton.this.isDragging = true;
                                        if (BoomMenuButton.this.shadow != null) {
                                            BoomMenuButton.this.setX(motionEvent.getRawX() + BoomMenuButton.this.startPositionX);
                                            BoomMenuButton.this.setY(motionEvent.getRawY() + BoomMenuButton.this.startPositionY);
                                        }
                                    }
                                } else if (actionMasked == 3 && BoomMenuButton.this.isDragging) {
                                    BoomMenuButton.this.ableToStartDragging = false;
                                    BoomMenuButton.this.isDragging = false;
                                    BoomMenuButton.this.needToCalculateStartPositions = true;
                                    BoomMenuButton.this.preventDragOutside();
                                    return true;
                                }
                            } else if (BoomMenuButton.this.isDragging) {
                                BoomMenuButton.this.ableToStartDragging = false;
                                BoomMenuButton.this.isDragging = false;
                                BoomMenuButton.this.needToCalculateStartPositions = true;
                                BoomMenuButton.this.preventDragOutside();
                                BoomMenuButton.this.button.setPressed(false);
                                return true;
                            }
                        } else if (BoomMenuButton.this.draggable) {
                            BoomMenuButton boomMenuButton = BoomMenuButton.this;
                            boomMenuButton.startPositionX = boomMenuButton.getX() - motionEvent.getRawX();
                            BoomMenuButton boomMenuButton2 = BoomMenuButton.this;
                            boomMenuButton2.startPositionY = boomMenuButton2.getY() - motionEvent.getRawY();
                            BoomMenuButton.this.lastMotionX = motionEvent.getRawX();
                            BoomMenuButton.this.lastMotionY = motionEvent.getRawY();
                        }
                        return false;
                    }
                });
            }
        }
    }

    public boolean isAnimating() {
        return this.animatingViewNumber != 0;
    }

    public boolean isBoomed() {
        return this.boomStateEnum == BoomStateEnum.DidBoom;
    }

    public boolean isReBoomed() {
        return this.boomStateEnum == BoomStateEnum.DidReboom;
    }

    public void boom() {
        innerBoom(false);
    }

    public void boomImmediately() {
        innerBoom(true);
    }

    private void innerBoom(boolean z) {
        if (!isAnimating() && this.boomStateEnum == BoomStateEnum.DidReboom) {
            ExceptionManager.judge(this, this.boomButtonBuilders);
            this.boomStateEnum = BoomStateEnum.WillBoom;
            OnBoomListener onBoomListener2 = this.onBoomListener;
            if (onBoomListener2 != null) {
                onBoomListener2.onBoomWillShow();
            }
            calculateStartPositions(false);
            createButtons();
            dimBackground(z);
            startBoomAnimations(z);
            startBoomAnimationForFadeViews(z);
            if (this.isBackPressListened) {
                setFocusable(true);
                setFocusableInTouchMode(true);
                requestFocus();
            }
        }
    }

    public void reboom() {
        innerReboom(false);
    }

    public void reboomImmediately() {
        innerReboom(true);
    }

    private void innerReboom(boolean z) {
        if (!isAnimating() && this.boomStateEnum == BoomStateEnum.DidBoom) {
            this.boomStateEnum = BoomStateEnum.WillReboom;
            OnBoomListener onBoomListener2 = this.onBoomListener;
            if (onBoomListener2 != null) {
                onBoomListener2.onBoomWillHide();
            }
            lightBackground(z);
            startReboomAnimations(z);
            startReboomAnimationForFadeViews(z);
            if (this.isBackPressListened) {
                setFocusable(false);
                setFocusableInTouchMode(false);
            }
        }
    }

    private void dimBackground(boolean z) {
        long j;
        createBackground();
        Util.setVisibility(0, this.background);
        if (z) {
            j = 1;
        } else {
            j = this.showDuration + (this.showDelay * ((long) (this.pieces.size() - 1)));
        }
        this.background.dim(j, new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                BoomMenuButton.this.boomStateEnum = BoomStateEnum.DidBoom;
                if (BoomMenuButton.this.onBoomListener != null) {
                    BoomMenuButton.this.onBoomListener.onBoomDidShow();
                }
            }
        });
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.animate((Object) this.shareLinesView, "showProcess", 0, j, (TimeInterpolator) Ease.getInstance(EaseEnum.Linear), 0.0f, 1.0f);
        }
    }

    private void lightBackground(boolean z) {
        long j;
        createBackground();
        if (z) {
            j = 1;
        } else {
            j = this.hideDuration + (this.hideDelay * ((long) (this.pieces.size() - 1)));
        }
        long j2 = j;
        this.background.light(j2, null);
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.animate((Object) this.shareLinesView, "hideProcess", 0, j2, (TimeInterpolator) Ease.getInstance(EaseEnum.Linear), 0.0f, 1.0f);
        }
    }

    /* access modifiers changed from: private */
    public void finishReboomAnimations() {
        if (!isAnimating()) {
            this.boomStateEnum = BoomStateEnum.DidReboom;
            OnBoomListener onBoomListener2 = this.onBoomListener;
            if (onBoomListener2 != null) {
                onBoomListener2.onBoomDidHide();
            }
            this.background.setVisibility(8);
            clearViews(false);
        }
    }

    private void startBoomAnimations(boolean z) {
        ArrayList arrayList;
        BackgroundView backgroundView = this.background;
        if (backgroundView != null) {
            backgroundView.removeAllViews();
        }
        calculateEndPositions();
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            arrayList = AnimationManager.getOrderIndex(OrderEnum.DEFAULT, this.pieces.size());
        } else {
            arrayList = AnimationManager.getOrderIndex(this.orderEnum, this.pieces.size());
        }
        int i = this.lastReboomIndex;
        if (i != -1 && this.use3DTransformAnimation) {
            this.boomButtons.set(i, ((BoomButtonBuilder) this.boomButtonBuilders.get(i)).innerListener(this).index(this.lastReboomIndex).build(this.context));
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            int intValue = ((Integer) arrayList.get(size)).intValue();
            BoomButton boomButton = (BoomButton) this.boomButtons.get(intValue);
            PointF pointF = new PointF(((PointF) this.startPositions.get(intValue)).x - boomButton.centerPoint.x, ((PointF) this.startPositions.get(intValue)).y - boomButton.centerPoint.y);
            putBoomButtonInBackground(boomButton, pointF);
            startEachBoomAnimation((BoomPiece) this.pieces.get(intValue), boomButton, pointF, (PointF) this.endPositions.get(intValue), size, z);
        }
    }

    private void startReboomAnimations(boolean z) {
        ArrayList arrayList;
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            arrayList = AnimationManager.getOrderIndex(OrderEnum.REVERSE, this.pieces.size());
        } else {
            arrayList = AnimationManager.getOrderIndex(this.orderEnum, this.pieces.size());
        }
        this.lastReboomIndex = ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((BoomButton) this.boomButtons.get(((Integer) it.next()).intValue())).bringToFront();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            int intValue = ((Integer) arrayList.get(i)).intValue();
            BoomButton boomButton = (BoomButton) this.boomButtons.get(intValue);
            PointF pointF = new PointF(((PointF) this.startPositions.get(intValue)).x - boomButton.centerPoint.x, ((PointF) this.startPositions.get(intValue)).y - boomButton.centerPoint.y);
            startEachReboomAnimation((BoomPiece) this.pieces.get(intValue), boomButton, (PointF) this.endPositions.get(intValue), pointF, i, z);
        }
    }

    private void startEachBoomAnimation(BoomPiece boomPiece, BoomButton boomButton, PointF pointF, PointF pointF2, int i, boolean z) {
        if (isBatterySaveModeTurnOn()) {
            final BoomPiece boomPiece2 = boomPiece;
            final BoomButton boomButton2 = boomButton;
            final PointF pointF3 = pointF;
            final PointF pointF4 = pointF2;
            final int i2 = i;
            final boolean z2 = z;
            C45054 r1 = new Runnable() {
                public void run() {
                    BoomMenuButton.this.innerStartEachBoomAnimation(boomPiece2, boomButton2, pointF3, pointF4, i2, z2);
                }
            };
            post(r1);
            return;
        }
        innerStartEachBoomAnimation(boomPiece, boomButton, pointF, pointF2, i, z);
    }

    /* access modifiers changed from: private */
    public void innerStartEachBoomAnimation(BoomPiece boomPiece, BoomButton boomButton, PointF pointF, PointF pointF2, int i, boolean z) {
        long j;
        int i2;
        final BoomButton boomButton2 = boomButton;
        PointF pointF3 = pointF;
        this.animatingViewNumber++;
        int i3 = this.frames;
        float[] fArr = new float[(i3 + 1)];
        float[] fArr2 = new float[(i3 + 1)];
        float width = (((float) boomPiece.getWidth()) * 1.0f) / ((float) boomButton.contentWidth());
        float height = (((float) boomPiece.getHeight()) * 1.0f) / ((float) boomButton.contentHeight());
        long j2 = 1;
        if (z) {
            j = 1;
        } else {
            j = this.showDelay * ((long) i);
        }
        if (!z) {
            j2 = this.showDuration;
        }
        long j3 = j2;
        boomButton.setSelfScaleAnchorPoints();
        boomButton2.setScaleX(width);
        boomButton2.setScaleY(height);
        boomButton.hideAllGoneViews();
        float f = height;
        AnimationManager.calculateShowXY(this.boomEnum, new PointF((float) this.background.getLayoutParams().width, (float) this.background.getLayoutParams().height), Ease.getInstance(this.showMoveEaseEnum), this.frames, pointF, pointF2, fArr, fArr2);
        if (!boomButton.isNeededColorAnimation()) {
            i2 = 2;
        } else if (boomButton.prepareColorTransformAnimation()) {
            BoomButton boomButton3 = boomButton;
            i2 = 2;
            AnimationManager.animate((Object) boomButton3, "rippleButtonColor", j, j3, (TypeEvaluator) ShowRgbEvaluator.getInstance(), boomButton.pieceColor(), boomButton.buttonColor());
        } else {
            i2 = 2;
            BoomButton boomButton4 = boomButton;
            AnimationManager.animate((Object) boomButton4, "nonRippleButtonColor", j, j3, (TypeEvaluator) ShowRgbEvaluator.getInstance(), boomButton.pieceColor(), boomButton.buttonColor());
        }
        BoomButton boomButton5 = boomButton;
        long j4 = j;
        long j5 = j3;
        AnimationManager.animate((Object) boomButton5, "x", j4, j5, (TimeInterpolator) new LinearInterpolator(), fArr);
        AnimationManager.animate((Object) boomButton5, "y", j4, j5, (TimeInterpolator) new LinearInterpolator(), fArr2);
        Ease instance = Ease.getInstance(this.showRotateEaseEnum);
        float[] fArr3 = new float[i2];
        fArr3[0] = 0.0f;
        fArr3[1] = (float) this.rotateDegree;
        AnimationManager.rotate(boomButton, j, j3, instance, fArr3);
        float[] fArr4 = new float[i2];
        // fill-array-data instruction
        fArr4[0] = 0;
        fArr4[1] = 1065353216;
        AnimationManager.animate("alpha", j, j3, fArr4, (TimeInterpolator) Ease.getInstance(EaseEnum.Linear), boomButton.goneViews());
        Ease instance2 = Ease.getInstance(this.showScaleEaseEnum);
        float[] fArr5 = new float[i2];
        fArr5[0] = width;
        fArr5[1] = 1.0f;
        long j6 = j;
        float[] fArr6 = fArr5;
        long j7 = j3;
        AnimationManager.animate((Object) boomButton, "scaleX", j6, j7, (TimeInterpolator) instance2, fArr6);
        Ease instance3 = Ease.getInstance(this.showScaleEaseEnum);
        final BoomPiece boomPiece2 = boomPiece;
        C45065 r8 = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                Util.setVisibility(4, boomPiece2);
                Util.setVisibility(0, boomButton2);
                boomButton2.willShow();
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                boomButton2.didShow();
                BoomMenuButton.this.animatingViewNumber = BoomMenuButton.this.animatingViewNumber - 1;
            }
        };
        float[] fArr7 = new float[i2];
        fArr7[0] = f;
        fArr7[1] = 1.0f;
        AnimationManager.animate((Object) boomButton, "scaleY", j6, j7, (TimeInterpolator) instance3, (AnimatorListenerAdapter) r8, fArr7);
        if (this.use3DTransformAnimation) {
            Rotate3DAnimation rotate3DAnimation = AnimationManager.getRotate3DAnimation(fArr, fArr2, j, j3, boomButton);
            rotate3DAnimation.set(boomButton2, pointF3.x, pointF3.y);
            boomButton2.setCameraDistance(0.0f);
            boomButton2.startAnimation(rotate3DAnimation);
        }
    }

    private void startEachReboomAnimation(BoomPiece boomPiece, BoomButton boomButton, PointF pointF, PointF pointF2, int i, boolean z) {
        long j;
        int i2;
        final BoomButton boomButton2 = boomButton;
        PointF pointF3 = pointF2;
        this.animatingViewNumber++;
        int i3 = this.frames;
        float[] fArr = new float[(i3 + 1)];
        float[] fArr2 = new float[(i3 + 1)];
        float width = (((float) boomPiece.getWidth()) * 1.0f) / ((float) boomButton.contentWidth());
        float height = (((float) boomPiece.getHeight()) * 1.0f) / ((float) boomButton.contentHeight());
        long j2 = 1;
        if (z) {
            j = 1;
        } else {
            j = this.hideDelay * ((long) i);
        }
        if (!z) {
            j2 = this.hideDuration;
        }
        long j3 = j2;
        AnimationManager.calculateHideXY(this.boomEnum, new PointF((float) this.background.getLayoutParams().width, (float) this.background.getLayoutParams().height), Ease.getInstance(this.hideMoveEaseEnum), this.frames, pointF, pointF2, fArr, fArr2);
        if (!boomButton.isNeededColorAnimation()) {
            i2 = 2;
        } else if (boomButton.prepareColorTransformAnimation()) {
            BoomButton boomButton3 = boomButton;
            i2 = 2;
            AnimationManager.animate((Object) boomButton3, "rippleButtonColor", j, j3, (TypeEvaluator) HideRgbEvaluator.getInstance(), boomButton.buttonColor(), boomButton.pieceColor());
        } else {
            i2 = 2;
            BoomButton boomButton4 = boomButton;
            AnimationManager.animate((Object) boomButton4, "nonRippleButtonColor", j, j3, (TypeEvaluator) HideRgbEvaluator.getInstance(), boomButton.buttonColor(), boomButton.pieceColor());
        }
        BoomButton boomButton5 = boomButton;
        long j4 = j;
        long j5 = j3;
        AnimationManager.animate((Object) boomButton5, "x", j4, j5, (TimeInterpolator) new LinearInterpolator(), fArr);
        AnimationManager.animate((Object) boomButton5, "y", j4, j5, (TimeInterpolator) new LinearInterpolator(), fArr2);
        Ease instance = Ease.getInstance(this.hideRotateEaseEnum);
        float[] fArr3 = new float[i2];
        fArr3[0] = 0.0f;
        fArr3[1] = (float) (-this.rotateDegree);
        AnimationManager.rotate(boomButton, j, j3, instance, fArr3);
        float[] fArr4 = new float[i2];
        // fill-array-data instruction
        fArr4[0] = 1065353216;
        fArr4[1] = 0;
        AnimationManager.animate("alpha", j, j3, fArr4, (TimeInterpolator) Ease.getInstance(EaseEnum.Linear), boomButton.goneViews());
        Ease instance2 = Ease.getInstance(this.hideScaleEaseEnum);
        float[] fArr5 = new float[i2];
        fArr5[0] = 1.0f;
        fArr5[1] = width;
        long j6 = j;
        float[] fArr6 = fArr5;
        long j7 = j3;
        AnimationManager.animate((Object) boomButton, "scaleX", j6, j7, (TimeInterpolator) instance2, fArr6);
        Ease instance3 = Ease.getInstance(this.hideScaleEaseEnum);
        final BoomPiece boomPiece2 = boomPiece;
        C45076 r8 = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                boomButton2.willHide();
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                Util.setVisibility(0, boomPiece2);
                Util.setVisibility(4, boomButton2);
                boomButton2.didHide();
                BoomMenuButton.this.animatingViewNumber = BoomMenuButton.this.animatingViewNumber - 1;
                BoomMenuButton.this.finishReboomAnimations();
            }
        };
        float[] fArr7 = new float[i2];
        fArr7[0] = 1.0f;
        fArr7[1] = height;
        AnimationManager.animate((Object) boomButton, "scaleY", j6, j7, (TimeInterpolator) instance3, (AnimatorListenerAdapter) r8, fArr7);
        if (this.use3DTransformAnimation) {
            Rotate3DAnimation rotate3DAnimation = AnimationManager.getRotate3DAnimation(fArr, fArr2, j, j3, boomButton);
            rotate3DAnimation.set(boomButton2, pointF3.x, pointF3.y);
            boomButton2.setCameraDistance(0.0f);
            boomButton2.startAnimation(rotate3DAnimation);
        }
    }

    private void createBackground() {
        if (this.background == null) {
            this.background = new BackgroundView(this.context, this);
        }
    }

    /* access modifiers changed from: protected */
    public ViewGroup getParentView() {
        if (!this.boomInWholeScreen) {
            return (ViewGroup) getParent();
        }
        Activity scanForActivity = Util.scanForActivity(this.context);
        if (scanForActivity == null) {
            return (ViewGroup) getParent();
        }
        return (ViewGroup) scanForActivity.getWindow().getDecorView();
    }

    private void clearBackground() {
        Util.setVisibility(8, this.background);
        if (!this.cacheOptimization || this.inList || this.inFragment) {
            this.background.removeAllViews();
            ((ViewGroup) this.background.getParent()).removeView(this.background);
            this.background = null;
        }
    }

    private void createButtons() {
        if (this.needToCreateButtons) {
            this.needToCreateButtons = false;
            this.boomButtons = new ArrayList<>(this.pieces.size());
            this.pieces.size();
            for (int i = 0; i < this.boomButtonBuilders.size(); i++) {
                this.boomButtons.add(((BoomButtonBuilder) this.boomButtonBuilders.get(i)).innerListener(this).index(i).build(this.context));
            }
            int i2 = C450212.$SwitchMap$com$nightonke$boommenu$ButtonEnum[this.buttonEnum.ordinal()];
            if (i2 == 1) {
                this.simpleCircleButtonRadius = (float) ((Builder) this.boomButtonBuilders.get(0)).getButtonRadius();
            } else if (i2 == 2) {
                this.textInsideCircleButtonRadius = (float) ((TextInsideCircleButton.Builder) this.boomButtonBuilders.get(0)).getButtonRadius();
            } else if (i2 == 3) {
                this.textOutsideCircleButtonWidth = (float) ((TextOutsideCircleButton.Builder) this.boomButtonBuilders.get(0)).getButtonContentWidth();
                this.textOutsideCircleButtonHeight = (float) ((TextOutsideCircleButton.Builder) this.boomButtonBuilders.get(0)).getButtonContentHeight();
            } else if (i2 == 4) {
                this.hamButtonWidth = (float) ((HamButton.Builder) this.boomButtonBuilders.get(0)).getButtonWidth();
                this.hamButtonHeight = (float) ((HamButton.Builder) this.boomButtonBuilders.get(0)).getButtonHeight();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onBackgroundClicked() {
        if (!isAnimating()) {
            OnBoomListener onBoomListener2 = this.onBoomListener;
            if (onBoomListener2 != null) {
                onBoomListener2.onBackgroundClick();
            }
            if (this.cancelable) {
                reboom();
            }
        }
    }

    /* access modifiers changed from: private */
    public void calculateStartPositions(boolean z) {
        if (z || this.needToCalculateStartPositions || this.inList || this.inFragment) {
            if (!z) {
                this.needToCalculateStartPositions = false;
            }
            this.startPositions = new ArrayList<>(pieceNumber());
            int[] iArr = new int[2];
            getParentView().getLocationOnScreen(iArr);
            for (int i = 0; i < this.pieces.size(); i++) {
                PointF pointF = new PointF();
                int[] iArr2 = new int[2];
                this.button.getLocationOnScreen(iArr2);
                pointF.x = ((((float) iArr2[0]) + ((RectF) this.piecePositions.get(i)).left) - ((float) iArr[0])) + ((float) (((BoomPiece) this.pieces.get(i)).getLayoutParams().width / 2));
                pointF.y = ((((float) iArr2[1]) + ((RectF) this.piecePositions.get(i)).top) - ((float) iArr[1])) + ((float) (((BoomPiece) this.pieces.get(i)).getLayoutParams().height / 2));
                this.startPositions.add(pointF);
            }
        }
    }

    /* access modifiers changed from: private */
    public void calculateEndPositions() {
        Point point = new Point(this.background.getLayoutParams().width, this.background.getLayoutParams().height);
        int i = C450212.$SwitchMap$com$nightonke$boommenu$ButtonEnum[this.buttonEnum.ordinal()];
        if (i == 1) {
            this.endPositions = ButtonPlaceManager.getPositions(point, this.simpleCircleButtonRadius, this.boomButtonBuilders.size(), this);
        } else if (i == 2) {
            this.endPositions = ButtonPlaceManager.getPositions(point, this.textInsideCircleButtonRadius, this.boomButtonBuilders.size(), this);
        } else if (i == 3) {
            this.endPositions = ButtonPlaceManager.getPositions(point, this.textOutsideCircleButtonWidth, this.textOutsideCircleButtonHeight, this.boomButtonBuilders.size(), this);
        } else if (i == 4) {
            this.endPositions = ButtonPlaceManager.getPositions(point, this.hamButtonWidth, this.hamButtonHeight, this.boomButtonBuilders.size(), this);
        }
        for (int i2 = 0; i2 < this.boomButtons.size(); i2++) {
            ((PointF) this.endPositions.get(i2)).offset(-((BoomButton) this.boomButtons.get(i2)).centerPoint.x, -((BoomButton) this.boomButtons.get(i2)).centerPoint.y);
        }
    }

    private BoomButton putBoomButtonInBackground(BoomButton boomButton, PointF pointF) {
        createBackground();
        boomButton.place((int) pointF.x, (int) pointF.y, boomButton.trueWidth(), boomButton.trueHeight());
        boomButton.setVisibility(4);
        this.background.addView(boomButton);
        return boomButton;
    }

    private void clearViews(boolean z) {
        if (z || !this.cacheOptimization || this.inList || this.inFragment) {
            clearButtons();
            clearBackground();
        }
    }

    private void clearButtons() {
        this.needToCreateButtons = true;
        if (this.background != null) {
            Iterator it = this.boomButtons.iterator();
            while (it.hasNext()) {
                this.background.removeView((BoomButton) it.next());
            }
        }
        this.boomButtons.clear();
    }

    private float buttonMaxHeight() {
        float f;
        int i = C450212.$SwitchMap$com$nightonke$boommenu$ButtonEnum[this.buttonEnum.ordinal()];
        if (i == 1) {
            f = this.simpleCircleButtonRadius;
        } else if (i == 2) {
            f = this.textInsideCircleButtonRadius;
        } else if (i == 3) {
            return this.textOutsideCircleButtonHeight;
        } else {
            if (i != 4) {
                return 0.0f;
            }
            return this.hamButtonHeight;
        }
        return f * 2.0f;
    }

    /* access modifiers changed from: private */
    public void preventDragOutside() {
        boolean z;
        float f;
        float x = getX();
        float y = getY();
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (x < ((float) this.edgeInsetsInParentView.left)) {
            x = (float) this.edgeInsetsInParentView.left;
            z = true;
        } else {
            z = false;
        }
        if (y < ((float) this.edgeInsetsInParentView.top)) {
            y = (float) this.edgeInsetsInParentView.top;
            z = true;
        }
        if (x > ((float) ((viewGroup.getWidth() - this.edgeInsetsInParentView.right) - getWidth()))) {
            x = (float) ((viewGroup.getWidth() - this.edgeInsetsInParentView.right) - getWidth());
            z = true;
        }
        if (y > ((float) ((viewGroup.getHeight() - this.edgeInsetsInParentView.bottom) - getHeight()))) {
            f = (float) ((viewGroup.getHeight() - this.edgeInsetsInParentView.bottom) - getHeight());
            z = true;
        } else {
            f = y;
        }
        if (z) {
            float[] fArr = {getX(), x};
            AnimationManager.animate((Object) this, "x", 0, 300, (TimeInterpolator) Ease.getInstance(EaseEnum.EaseOutBack), fArr);
            AnimationManager.animate((Object) this, "y", 0, 300, (TimeInterpolator) Ease.getInstance(EaseEnum.EaseOutBack), getY(), f);
        }
    }

    private void toLayout() {
        if (!this.needToLayout) {
            this.needToLayout = true;
            if (VERSION.SDK_INT < 18) {
                requestLayout();
            } else if (!isInLayout()) {
                requestLayout();
            }
        }
    }

    private void delayToDoLayoutJobs() {
        if (this.layoutJobsRunnable == null) {
            this.layoutJobsRunnable = new Runnable() {
                public void run() {
                    BoomMenuButton.this.doLayoutJobs();
                }
            };
        }
        post(this.layoutJobsRunnable);
    }

    private int pieceNumber() {
        if (this.piecePlaceEnum.equals(PiecePlaceEnum.Unknown)) {
            return 0;
        }
        if (this.piecePlaceEnum.equals(PiecePlaceEnum.Share)) {
            return this.boomButtonBuilders.size();
        }
        if (this.piecePlaceEnum.equals(PiecePlaceEnum.Custom)) {
            return this.customPiecePlacePositions.size();
        }
        return this.piecePlaceEnum.pieceNumber();
    }

    public void onButtonClick(int i, BoomButton boomButton) {
        if (!isAnimating()) {
            OnBoomListener onBoomListener2 = this.onBoomListener;
            if (onBoomListener2 != null) {
                onBoomListener2.onClicked(i, boomButton);
            }
            if (this.autoHide) {
                reboom();
            }
        }
    }

    private void placeShareLinesView() {
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            ShareLinesView shareLinesView2 = this.shareLinesView;
            if (shareLinesView2 != null) {
                this.button.removeView(shareLinesView2);
            }
            this.shareLinesView = new ShareLinesView(this.context);
            this.shareLinesView.setLine1Color(this.shareLine1Color);
            this.shareLinesView.setLine2Color(this.shareLine2Color);
            this.shareLinesView.setLineWidth(this.shareLineWidth);
            this.button.addView(this.shareLinesView);
            this.shareLinesView.place(0, 0, this.button.getWidth(), this.button.getHeight());
            return;
        }
        ShareLinesView shareLinesView3 = this.shareLinesView;
        if (shareLinesView3 != null) {
            this.button.removeView(shareLinesView3);
        }
    }

    private void setShareLinesViewData() {
        if (this.piecePlaceEnum == PiecePlaceEnum.Share) {
            this.shareLinesView.setData(this.piecePositions, this);
        }
    }

    private boolean uninitializedBoomButtons() {
        return this.buttonEnum.equals(ButtonEnum.Unknown) || this.piecePlaceEnum.equals(PiecePlaceEnum.Unknown) || this.buttonPlaceEnum.equals(ButtonPlaceEnum.Unknown);
    }

    private boolean isBatterySaveModeTurnOn() {
        return VERSION.SDK_INT >= 21 && ((PowerManager) getContext().getSystemService("power")).isPowerSaveMode();
    }

    private void checkAutoBoom() {
        if (this.autoBoomImmediately) {
            boomImmediately();
        } else if (this.autoBoom) {
            boom();
        }
        this.autoBoom = false;
        this.autoBoomImmediately = false;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isOrientationAdaptable) {
            initOrientationListener();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OrientationEventListener orientationEventListener2 = this.orientationEventListener;
        if (orientationEventListener2 != null) {
            orientationEventListener2.disable();
        }
    }

    private void initOrientationListener() {
        if (this.orientationEventListener == null) {
            this.orientationEventListener = new OrientationEventListener(this.context) {
                public void onOrientationChanged(int i) {
                    if (!(i == BoomMenuButton.this.lastOrientation || BoomMenuButton.this.lastOrientation == -1)) {
                        BoomMenuButton.this.isOrientationChanged = true;
                    }
                    BoomMenuButton.this.lastOrientation = i;
                }
            };
        }
        if (this.orientationEventListener.canDetectOrientation()) {
            this.orientationEventListener.enable();
        }
    }

    private void reLayoutAfterOrientationChanged() {
        post(new Runnable() {
            public void run() {
                if (BoomMenuButton.this.background != null) {
                    BoomMenuButton.this.background.reLayout(BoomMenuButton.this);
                }
                boolean z = true;
                BoomMenuButton.this.calculateStartPositions(true);
                BoomMenuButton.this.calculateEndPositions();
                int i = C450212.$SwitchMap$com$nightonke$boommenu$BoomStateEnum[BoomMenuButton.this.boomStateEnum.ordinal()];
                if (i == 1) {
                    return;
                }
                if (i == 2) {
                    BoomMenuButton.this.placeButtons();
                } else if (i == 3 || i == 4) {
                    BoomMenuButton boomMenuButton = BoomMenuButton.this;
                    if (boomMenuButton.boomStateEnum != BoomStateEnum.WillBoom) {
                        z = false;
                    }
                    boomMenuButton.stopAllAnimations(z);
                    BoomMenuButton.this.placeButtons();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void placeButtons() {
        for (int i = 0; i < this.boomButtons.size(); i++) {
            BoomButton boomButton = (BoomButton) this.boomButtons.get(i);
            PointF pointF = (PointF) this.endPositions.get(i);
            boomButton.setX(pointF.x);
            boomButton.setY(pointF.y);
        }
    }

    /* access modifiers changed from: private */
    public void stopAllAnimations(boolean z) {
        BackgroundView backgroundView = this.background;
        if (backgroundView != null) {
            backgroundView.clearAnimation();
        }
        Iterator it = this.boomButtons.iterator();
        while (it.hasNext()) {
            ((BoomButton) it.next()).clearAnimation();
        }
    }

    public void addBuilder(BoomButtonBuilder boomButtonBuilder) {
        this.boomButtonBuilders.add(boomButtonBuilder);
        toLayout();
    }

    public void setBuilder(int i, BoomButtonBuilder boomButtonBuilder) {
        this.boomButtonBuilders.set(i, boomButtonBuilder);
        toLayout();
    }

    public void setBuilders(ArrayList<BoomButtonBuilder> arrayList) {
        this.boomButtonBuilders = arrayList;
        toLayout();
    }

    public BoomButtonBuilder getBuilder(int i) {
        ArrayList<BoomButtonBuilder> arrayList = this.boomButtonBuilders;
        if (arrayList == null || i < 0 || i >= arrayList.size()) {
            return null;
        }
        return (BoomButtonBuilder) this.boomButtonBuilders.get(i);
    }

    public void removeBuilder(BoomButtonBuilder boomButtonBuilder) {
        this.boomButtonBuilders.remove(boomButtonBuilder);
        toLayout();
    }

    public void removeBuilder(int i) {
        this.boomButtonBuilders.remove(i);
        toLayout();
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.button.setEnabled(z);
        setButtonBackground();
    }

    public void setEnable(int i, boolean z) {
        if (i >= 0) {
            ArrayList<BoomButtonBuilder> arrayList = this.boomButtonBuilders;
            if (arrayList != null && i < arrayList.size()) {
                ((BoomButtonBuilder) this.boomButtonBuilders.get(i)).setUnable(!z);
            }
            ArrayList<BoomButton> arrayList2 = this.boomButtons;
            if (arrayList2 != null && i < arrayList2.size()) {
                ((BoomButton) this.boomButtons.get(i)).setEnabled(z);
            }
        }
    }

    public void clearBuilders() {
        this.boomButtonBuilders.clear();
    }

    public ArrayList<BoomButtonBuilder> getBuilders() {
        return this.boomButtonBuilders;
    }

    public BoomButton getBoomButton(int i) {
        ArrayList<BoomButton> arrayList = this.boomButtons;
        if (arrayList == null || i < 0 || i >= arrayList.size()) {
            return null;
        }
        return (BoomButton) this.boomButtons.get(i);
    }

    public ArrayList<BoomButton> getBoomButtons() {
        return this.boomButtons;
    }

    private void startBoomAnimationForFadeViews(boolean z) {
        AnimationManager.animate("alpha", 0, z ? 1 : this.showDuration + (this.showDelay * ((long) (this.pieces.size() - 1))), new float[]{1.0f, 0.0f}, (TimeInterpolator) new TimeInterpolator() {
            public float getInterpolation(float f) {
                return Math.min(f * 2.0f, 1.0f);
            }
        }, getFadeViews());
    }

    private void startReboomAnimationForFadeViews(boolean z) {
        AnimationManager.animate("alpha", 0, z ? 1 : this.hideDuration + (this.hideDelay * ((long) (this.pieces.size() - 1))), new float[]{0.0f, 1.0f}, (TimeInterpolator) new TimeInterpolator() {
            public float getInterpolation(float f) {
                if (((double) f) <= 0.5d) {
                    return 0.0f;
                }
                return Math.min((f - 0.5f) * 2.0f, 1.0f);
            }
        }, getFadeViews());
    }

    private ArrayList<View> getFadeViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (!(childAt == this.shadow || childAt == this.button || childAt == this.shareLinesView)) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    public boolean isCacheOptimization() {
        return this.cacheOptimization;
    }

    public void setCacheOptimization(boolean z) {
        this.cacheOptimization = z;
    }

    public boolean isBoomInWholeScreen() {
        return this.boomInWholeScreen;
    }

    public void setBoomInWholeScreen(boolean z) {
        this.boomInWholeScreen = z;
    }

    public boolean isInList() {
        return this.inList;
    }

    public void setInList(boolean z) {
        this.inList = z;
    }

    public boolean isInFragment() {
        return this.inFragment;
    }

    public void setInFragment(boolean z) {
        this.inFragment = z;
    }

    public boolean isBackPressListened() {
        return this.isBackPressListened;
    }

    public void setBackPressListened(boolean z) {
        this.isBackPressListened = z;
    }

    public boolean isShadowEffect() {
        return this.shadowEffect;
    }

    public void setShadowEffect(boolean z) {
        if (this.shadowEffect != z) {
            this.shadowEffect = z;
            initShadow();
        }
    }

    public int getShadowOffsetX() {
        return this.shadowOffsetX;
    }

    public void setShadowOffsetX(int i) {
        if (this.shadowOffsetX != i) {
            this.shadowOffsetX = i;
            initShadow();
        }
    }

    public int getShadowOffsetY() {
        return this.shadowOffsetY;
    }

    public void setShadowOffsetY(int i) {
        if (this.shadowOffsetY != i) {
            this.shadowOffsetY = i;
            initShadow();
        }
    }

    public int getShadowRadius() {
        return this.shadowRadius;
    }

    public void setShadowRadius(int i) {
        if (this.shadowRadius != i) {
            this.shadowRadius = i;
            initShadow();
        }
    }

    public int getShadowColor() {
        return this.shadowColor;
    }

    public void setShadowColor(int i) {
        if (this.shadowColor != i) {
            this.shadowColor = i;
            initShadow();
        }
    }

    public int getButtonRadius() {
        return this.buttonRadius;
    }

    public void setButtonRadius(int i) {
        if (this.buttonRadius != i) {
            this.buttonRadius = i;
            initButton();
            toLayout();
        }
    }

    public ButtonEnum getButtonEnum() {
        return this.buttonEnum;
    }

    public void setButtonEnum(ButtonEnum buttonEnum2) {
        if (!this.buttonEnum.equals(buttonEnum2)) {
            this.buttonEnum = buttonEnum2;
            clearPieces();
            clearBuilders();
            clearButtons();
            toLayout();
        }
    }

    public boolean isBackgroundEffect() {
        return this.backgroundEffect;
    }

    public void setBackgroundEffect(boolean z) {
        if (this.backgroundEffect != z) {
            this.backgroundEffect = z;
            setButtonBackground();
            toLayout();
        }
    }

    public boolean isRippleEffect() {
        return this.rippleEffect;
    }

    public void setRippleEffect(boolean z) {
        if (this.rippleEffect != z) {
            this.rippleEffect = z;
            setButtonBackground();
            toLayout();
        }
    }

    public int getNormalColor() {
        return this.normalColor;
    }

    public void setNormalColor(int i) {
        if (this.normalColor != i) {
            this.normalColor = i;
            setButtonBackground();
            toLayout();
        }
    }

    public int getHighlightedColor() {
        return this.highlightedColor;
    }

    public void setHighlightedColor(int i) {
        if (this.highlightedColor != i) {
            this.highlightedColor = i;
            setButtonBackground();
            toLayout();
        }
    }

    public int getUnableColor() {
        return this.unableColor;
    }

    public void setUnableColor(int i) {
        if (this.unableColor != i) {
            this.unableColor = i;
            setButtonBackground();
            toLayout();
        }
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setDraggable(boolean z) {
        if (this.draggable != z) {
            this.draggable = z;
            initDraggableTouchListener();
        }
    }

    public Rect getEdgeInsetsInParentView() {
        return this.edgeInsetsInParentView;
    }

    public void setEdgeInsetsInParentView(Rect rect) {
        if (!this.edgeInsetsInParentView.equals(rect)) {
            this.edgeInsetsInParentView = rect;
            preventDragOutside();
        }
    }

    public float getDotRadius() {
        return this.dotRadius;
    }

    public void setDotRadius(float f) {
        if (this.dotRadius != f) {
            this.dotRadius = f;
            toLayout();
        }
    }

    public float getHamWidth() {
        return this.hamWidth;
    }

    public void setHamWidth(float f) {
        if (this.hamWidth != f) {
            this.hamWidth = f;
            toLayout();
        }
    }

    public float getHamHeight() {
        return this.hamHeight;
    }

    public void setHamHeight(int i) {
        float f = (float) i;
        if (this.hamHeight != f) {
            this.hamHeight = f;
            toLayout();
        }
    }

    public float getPieceCornerRadius() {
        return this.pieceCornerRadius;
    }

    public void setPieceCornerRadius(float f) {
        if (this.pieceCornerRadius != f) {
            this.pieceCornerRadius = f;
            toLayout();
        }
    }

    public float getPieceHorizontalMargin() {
        return this.pieceHorizontalMargin;
    }

    public void setPieceHorizontalMargin(float f) {
        if (this.pieceHorizontalMargin != f) {
            this.pieceHorizontalMargin = f;
            toLayout();
        }
    }

    public float getPieceVerticalMargin() {
        return this.pieceVerticalMargin;
    }

    public void setPieceVerticalMargin(float f) {
        if (this.pieceVerticalMargin != f) {
            this.pieceVerticalMargin = f;
            toLayout();
        }
    }

    public float getPieceInclinedMargin() {
        return this.pieceInclinedMargin;
    }

    public void setPieceInclinedMargin(float f) {
        if (this.pieceInclinedMargin != f) {
            this.pieceInclinedMargin = f;
            toLayout();
        }
    }

    public float getShareLineLength() {
        return this.shareLineLength;
    }

    public void setShareLineLength(float f) {
        if (this.shareLineLength != f) {
            this.shareLineLength = f;
            toLayout();
        }
    }

    public int getShareLine1Color() {
        return this.shareLine1Color;
    }

    public void setShareLine1Color(int i) {
        if (this.shareLine1Color != i) {
            this.shareLine1Color = i;
            ShareLinesView shareLinesView2 = this.shareLinesView;
            if (shareLinesView2 != null) {
                shareLinesView2.setLine1Color(i);
                this.shareLinesView.invalidate();
            }
        }
    }

    public int getShareLine2Color() {
        return this.shareLine2Color;
    }

    public void setShareLine2Color(int i) {
        if (this.shareLine2Color != i) {
            this.shareLine2Color = i;
            ShareLinesView shareLinesView2 = this.shareLinesView;
            if (shareLinesView2 != null) {
                shareLinesView2.setLine2Color(i);
                this.shareLinesView.invalidate();
            }
        }
    }

    public float getShareLineWidth() {
        return this.shareLineWidth;
    }

    public void setShareLineWidth(float f) {
        if (this.shareLineWidth != f) {
            this.shareLineWidth = f;
            ShareLinesView shareLinesView2 = this.shareLinesView;
            if (shareLinesView2 != null) {
                shareLinesView2.setLineWidth(f);
                this.shareLinesView.invalidate();
            }
        }
    }

    public PiecePlaceEnum getPiecePlaceEnum() {
        return this.piecePlaceEnum;
    }

    public void setPiecePlaceEnum(PiecePlaceEnum piecePlaceEnum2) {
        this.piecePlaceEnum = piecePlaceEnum2;
        clearPieces();
        toLayout();
    }

    public ArrayList<PointF> getCustomPiecePlacePositions() {
        return this.customPiecePlacePositions;
    }

    public void setCustomPiecePlacePositions(ArrayList<PointF> arrayList) {
        if (!this.customPiecePlacePositions.equals(arrayList)) {
            this.customPiecePlacePositions = arrayList;
            clearPieces();
            toLayout();
        }
    }

    public OnBoomListener getOnBoomListener() {
        return this.onBoomListener;
    }

    public void setOnBoomListener(OnBoomListener onBoomListener2) {
        this.onBoomListener = onBoomListener2;
    }

    public int getDimColor() {
        return this.dimColor;
    }

    public void setDimColor(int i) {
        if (this.dimColor != i) {
            this.dimColor = i;
            if (this.boomStateEnum == BoomStateEnum.DidBoom) {
                BackgroundView backgroundView = this.background;
                if (backgroundView != null) {
                    backgroundView.setBackgroundColor(i);
                }
            }
        }
    }

    public void setDelay(long j) {
        setShowDelay(j);
        setHideDelay(j);
    }

    public void setDuration(long j) {
        setShowDuration(j);
        setHideDuration(j);
    }

    public long getShowDelay() {
        return this.showDelay;
    }

    public void setShowDelay(long j) {
        this.showDelay = j;
        setShareLinesViewData();
    }

    public long getShowDuration() {
        return this.showDuration;
    }

    public void setShowDuration(long j) {
        if (this.showDuration != j) {
            this.showDuration = Math.max(1, j);
            setShareLinesViewData();
        }
    }

    public long getHideDelay() {
        return this.hideDelay;
    }

    public void setHideDelay(long j) {
        this.hideDelay = j;
        setShareLinesViewData();
    }

    public long getHideDuration() {
        return this.hideDuration;
    }

    public void setHideDuration(long j) {
        if (this.hideDuration != j) {
            this.hideDuration = Math.max(1, j);
            setShareLinesViewData();
        }
    }

    public boolean isCancelable() {
        return this.cancelable;
    }

    public void setCancelable(boolean z) {
        this.cancelable = z;
    }

    public boolean isAutoHide() {
        return this.autoHide;
    }

    public void setAutoHide(boolean z) {
        this.autoHide = z;
    }

    public OrderEnum getOrderEnum() {
        return this.orderEnum;
    }

    public void setOrderEnum(OrderEnum orderEnum2) {
        this.orderEnum = orderEnum2;
    }

    public int getFrames() {
        return this.frames;
    }

    public void setFrames(int i) {
        this.frames = i;
    }

    public BoomEnum getBoomEnum() {
        return this.boomEnum;
    }

    public void setBoomEnum(BoomEnum boomEnum2) {
        this.boomEnum = boomEnum2;
    }

    public void setShowEaseEnum(EaseEnum easeEnum) {
        setShowMoveEaseEnum(easeEnum);
        setShowScaleEaseEnum(easeEnum);
        setShowRotateEaseEnum(easeEnum);
    }

    public EaseEnum getShowMoveEaseEnum() {
        return this.showMoveEaseEnum;
    }

    public void setShowMoveEaseEnum(EaseEnum easeEnum) {
        this.showMoveEaseEnum = easeEnum;
    }

    public EaseEnum getShowScaleEaseEnum() {
        return this.showScaleEaseEnum;
    }

    public void setShowScaleEaseEnum(EaseEnum easeEnum) {
        this.showScaleEaseEnum = easeEnum;
    }

    public EaseEnum getShowRotateEaseEnum() {
        return this.showRotateEaseEnum;
    }

    public void setShowRotateEaseEnum(EaseEnum easeEnum) {
        this.showRotateEaseEnum = easeEnum;
    }

    public void setHideEaseEnum(EaseEnum easeEnum) {
        setHideMoveEaseEnum(easeEnum);
        setHideScaleEaseEnum(easeEnum);
        setHideRotateEaseEnum(easeEnum);
    }

    public EaseEnum getHideMoveEaseEnum() {
        return this.hideMoveEaseEnum;
    }

    public void setHideMoveEaseEnum(EaseEnum easeEnum) {
        this.hideMoveEaseEnum = easeEnum;
    }

    public EaseEnum getHideScaleEaseEnum() {
        return this.hideScaleEaseEnum;
    }

    public void setHideScaleEaseEnum(EaseEnum easeEnum) {
        this.hideScaleEaseEnum = easeEnum;
    }

    public EaseEnum getHideRotateEaseEnum() {
        return this.hideRotateEaseEnum;
    }

    public void setHideRotateEaseEnum(EaseEnum easeEnum) {
        this.hideRotateEaseEnum = easeEnum;
    }

    public int getRotateDegree() {
        return this.rotateDegree;
    }

    public void setUse3DTransformAnimation(boolean z) {
        this.use3DTransformAnimation = z;
    }

    public boolean isUse3DTransformAnimation() {
        return this.use3DTransformAnimation;
    }

    public void setAutoBoom(boolean z) {
        this.autoBoom = z;
    }

    public boolean isAutoBoom() {
        return this.autoBoom;
    }

    public void setAutoBoomImmediately(boolean z) {
        this.autoBoomImmediately = z;
    }

    public boolean isAutoBoomImmediately() {
        return this.autoBoomImmediately;
    }

    public void setRotateDegree(int i) {
        this.rotateDegree = i;
    }

    public ButtonPlaceEnum getButtonPlaceEnum() {
        return this.buttonPlaceEnum;
    }

    public void setButtonPlaceEnum(ButtonPlaceEnum buttonPlaceEnum2) {
        this.buttonPlaceEnum = buttonPlaceEnum2;
        clearButtons();
        this.needToCalculateStartPositions = true;
    }

    public ArrayList<PointF> getCustomButtonPlacePositions() {
        return this.customButtonPlacePositions;
    }

    public void setCustomButtonPlacePositions(ArrayList<PointF> arrayList) {
        this.customButtonPlacePositions = arrayList;
        clearButtons();
        this.needToCalculateStartPositions = true;
    }

    public ButtonPlaceAlignmentEnum getButtonPlaceAlignmentEnum() {
        return this.buttonPlaceAlignmentEnum;
    }

    public void setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum buttonPlaceAlignmentEnum2) {
        this.buttonPlaceAlignmentEnum = buttonPlaceAlignmentEnum2;
    }

    public float getButtonHorizontalMargin() {
        return this.buttonHorizontalMargin;
    }

    public void setButtonHorizontalMargin(float f) {
        this.buttonHorizontalMargin = f;
    }

    public float getButtonVerticalMargin() {
        return this.buttonVerticalMargin;
    }

    public void setButtonVerticalMargin(float f) {
        this.buttonVerticalMargin = f;
    }

    public float getButtonInclinedMargin() {
        return this.buttonInclinedMargin;
    }

    public void setButtonInclinedMargin(float f) {
        this.buttonInclinedMargin = f;
    }

    public float getButtonTopMargin() {
        return this.buttonTopMargin;
    }

    public void setButtonTopMargin(float f) {
        this.buttonTopMargin = f;
    }

    public float getButtonBottomMargin() {
        return this.buttonBottomMargin;
    }

    public void setButtonBottomMargin(float f) {
        this.buttonBottomMargin = f;
    }

    public float getButtonLeftMargin() {
        return this.buttonLeftMargin;
    }

    public void setButtonLeftMargin(float f) {
        this.buttonLeftMargin = f;
    }

    public float getButtonRightMargin() {
        return this.buttonRightMargin;
    }

    public void setButtonRightMargin(float f) {
        this.buttonRightMargin = f;
    }

    public float getBottomHamButtonTopMargin() {
        return this.bottomHamButtonTopMargin;
    }

    public void setBottomHamButtonTopMargin(float f) {
        this.bottomHamButtonTopMargin = f;
    }

    public boolean isOrientationAdaptable() {
        return this.isOrientationAdaptable;
    }

    public void setOrientationAdaptable(boolean z) {
        this.isOrientationAdaptable = z;
        if (this.isOrientationAdaptable) {
            initOrientationListener();
        }
    }
}
