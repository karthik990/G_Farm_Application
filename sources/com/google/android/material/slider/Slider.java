package com.google.android.material.slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.RangeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.C2556R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Slider extends View {
    /* access modifiers changed from: private */
    public static final int DEF_STYLE_RES = C2556R.C2562style.Widget_MaterialComponents_Slider;
    private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value must be equal to valueFrom plus a multiple of stepSize when using stepSize";
    private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize must be 0, or a factor of the valueFrom-valueTo range";
    private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value must be greater or equal to valueFrom, and lower or equal to valueTo";
    private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom must be smaller than valueTo";
    private static final String EXCEPTION_ILLEGAL_VALUE_TO = "valueTo must be greater than valueFrom";
    private static final int HALO_ALPHA = 63;
    public static final int LABEL_FLOATING = 0;
    public static final int LABEL_GONE = 2;
    public static final int LABEL_WITHIN_BOUNDS = 1;
    private static final String TAG = Slider.class.getSimpleName();
    private static final double THRESHOLD = 1.0E-4d;
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    private AccessibilityEventSender accessibilityEventSender;
    /* access modifiers changed from: private */
    public final AccessibilityHelper accessibilityHelper;
    private final AccessibilityManager accessibilityManager;
    private int activeThumbIdx;
    private final Paint activeTicksPaint;
    private final Paint activeTrackPaint;
    private final List<OnChangeListener> changeListeners;
    private boolean dirtyConfig;
    private int focusedThumbIdx;
    private boolean forceDrawCompatHalo;
    private LabelFormatter formatter;
    private ColorStateList haloColor;
    private final Paint haloPaint;
    private int haloRadius;
    private final Paint inactiveTicksPaint;
    private final Paint inactiveTrackPaint;
    private boolean isLongPress;
    private int labelBehavior;
    private final TooltipDrawableFactory labelMaker;
    private int labelPadding;
    private final List<TooltipDrawable> labels;
    private MotionEvent lastEvent;
    private final int scaledTouchSlop;
    private float stepSize;
    private final MaterialShapeDrawable thumbDrawable;
    private boolean thumbIsPressed;
    private final Paint thumbPaint;
    /* access modifiers changed from: private */
    public int thumbRadius;
    private ColorStateList tickColorActive;
    private ColorStateList tickColorInactive;
    private float[] ticksCoordinates;
    private float touchDownX;
    private final List<OnSliderTouchListener> touchListeners;
    private float touchPosition;
    private ColorStateList trackColorActive;
    private ColorStateList trackColorInactive;
    private int trackHeight;
    /* access modifiers changed from: private */
    public int trackSidePadding;
    private int trackTop;
    /* access modifiers changed from: private */
    public int trackWidth;
    /* access modifiers changed from: private */
    public float valueFrom;
    /* access modifiers changed from: private */
    public float valueTo;
    /* access modifiers changed from: private */
    public ArrayList<Float> values;
    private int widgetHeight;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LabelBehavior {
    }

    private class AccessibilityEventSender implements Runnable {
        int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        /* access modifiers changed from: 0000 */
        public void setVirtualViewId(int i) {
            this.virtualViewId = i;
        }

        public void run() {
            Slider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    private class AccessibilityHelper extends ExploreByTouchHelper {
        Rect bounds = new Rect();

        AccessibilityHelper() {
            super(Slider.this);
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float f, float f2) {
            for (int i = 0; i < Slider.this.getValues().size(); i++) {
                updateBoundsForVirturalViewId(i);
                if (this.bounds.contains((int) f, (int) f2)) {
                    return i;
                }
            }
            return -1;
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(List<Integer> list) {
            for (int i = 0; i < Slider.this.getValues().size(); i++) {
                list.add(Integer.valueOf(i));
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.addAction(AccessibilityActionCompat.ACTION_SET_PROGRESS);
            float floatValue = ((Float) Slider.this.getValues().get(i)).floatValue();
            if (Slider.this.isEnabled()) {
                if (floatValue > Slider.this.valueFrom) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (floatValue < Slider.this.valueTo) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
            accessibilityNodeInfoCompat.setRangeInfo(RangeInfoCompat.obtain(1, Slider.this.valueFrom, Slider.this.valueTo, floatValue));
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            StringBuilder sb = new StringBuilder();
            if (Slider.this.getContentDescription() != null) {
                sb.append(Slider.this.getContentDescription());
                sb.append(",");
            }
            if (Slider.this.values.size() > 1) {
                Context context = Slider.this.getContext();
                int i2 = C2556R.C2561string.mtrl_slider_range_content_description;
                Slider slider = Slider.this;
                Slider slider2 = Slider.this;
                sb.append(context.getString(i2, new Object[]{slider.formatValue(slider.getMinimumValue()), slider2.formatValue(slider2.getMaximumValue())}));
            }
            accessibilityNodeInfoCompat.setContentDescription(sb.toString());
            updateBoundsForVirturalViewId(i);
            accessibilityNodeInfoCompat.setBoundsInParent(this.bounds);
        }

        private void updateBoundsForVirturalViewId(int i) {
            int access$900 = Slider.this.trackSidePadding;
            Slider slider = Slider.this;
            int access$1000 = access$900 + ((int) (slider.normalizeValue(((Float) slider.getValues().get(i)).floatValue()) * ((float) Slider.this.trackWidth)));
            int access$1200 = Slider.this.calculateTop();
            this.bounds.set(access$1000 - Slider.this.thumbRadius, access$1200 - Slider.this.thumbRadius, access$1000 + Slider.this.thumbRadius, access$1200 + Slider.this.thumbRadius);
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (!Slider.this.isEnabled()) {
                return false;
            }
            if (i2 == 4096 || i2 == 8192) {
                float access$1600 = Slider.this.calculateStepIncrement(20);
                if (i2 == 8192) {
                    access$1600 = -access$1600;
                }
                if (ViewCompat.getLayoutDirection(Slider.this) == 1) {
                    access$1600 = -access$1600;
                }
                float clamp = MathUtils.clamp(((Float) Slider.this.values.get(i)).floatValue() + access$1600, Slider.this.valueFrom, Slider.this.valueTo);
                if (!Slider.this.snapThumbToValue(i, clamp)) {
                    return false;
                }
                Slider.this.updateHaloHotspot();
                Slider.this.postInvalidate();
                if (Slider.this.values.indexOf(Float.valueOf(clamp)) != i) {
                    sendEventForVirtualView(Slider.this.values.indexOf(Float.valueOf(clamp)), 8);
                } else {
                    invalidateVirtualView(i);
                }
                return true;
            }
            if (i2 == 16908349 && bundle != null) {
                String str = AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE;
                if (bundle.containsKey(str)) {
                    if (Slider.this.snapThumbToValue(i, bundle.getFloat(str))) {
                        Slider.this.updateHaloHotspot();
                        Slider.this.postInvalidate();
                        invalidateVirtualView(i);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static final class BasicLabelFormatter implements LabelFormatter {
        private static final int BILLION = 1000000000;
        private static final int MILLION = 1000000;
        private static final int THOUSAND = 1000;
        private static final long TRILLION = 1000000000000L;

        public String getFormattedValue(float f) {
            if (f >= 1.0E12f) {
                return String.format(Locale.US, "%.1fT", new Object[]{Float.valueOf(f / 1.0E12f)});
            } else if (f >= 1.0E9f) {
                return String.format(Locale.US, "%.1fB", new Object[]{Float.valueOf(f / 1.0E9f)});
            } else if (f >= 1000000.0f) {
                return String.format(Locale.US, "%.1fM", new Object[]{Float.valueOf(f / 1000000.0f)});
            } else if (f >= 1000.0f) {
                return String.format(Locale.US, "%.1fK", new Object[]{Float.valueOf(f / 1000.0f)});
            } else {
                return String.format(Locale.US, "%.0f", new Object[]{Float.valueOf(f)});
            }
        }
    }

    public interface LabelFormatter {
        String getFormattedValue(float f);
    }

    public interface OnChangeListener {
        void onValueChange(Slider slider, float f, boolean z);
    }

    public interface OnSliderTouchListener {
        void onStartTrackingTouch(Slider slider);

        void onStopTrackingTouch(Slider slider);
    }

    static class SliderState extends BaseSavedState {
        public static final Creator<SliderState> CREATOR = new Creator<SliderState>() {
            public SliderState createFromParcel(Parcel parcel) {
                return new SliderState(parcel);
            }

            public SliderState[] newArray(int i) {
                return new SliderState[i];
            }
        };
        boolean hasFocus;
        float stepSize;
        float valueFrom;
        float valueTo;
        ArrayList<Float> values;

        SliderState(Parcelable parcelable) {
            super(parcelable);
        }

        private SliderState(Parcel parcel) {
            super(parcel);
            this.valueFrom = parcel.readFloat();
            this.valueTo = parcel.readFloat();
            this.values = new ArrayList<>();
            parcel.readList(this.values, Float.class.getClassLoader());
            this.stepSize = parcel.readFloat();
            this.hasFocus = parcel.createBooleanArray()[0];
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.valueFrom);
            parcel.writeFloat(this.valueTo);
            parcel.writeList(this.values);
            parcel.writeFloat(this.stepSize);
            parcel.writeBooleanArray(new boolean[]{this.hasFocus});
        }
    }

    private interface TooltipDrawableFactory {
        TooltipDrawable createTooltipDrawable();
    }

    public Slider(Context context) {
        this(context, null);
    }

    public Slider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C2556R.attr.sliderStyle);
    }

    public Slider(Context context, final AttributeSet attributeSet, final int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, DEF_STYLE_RES), attributeSet, i);
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.thumbIsPressed = false;
        this.values = new ArrayList<>();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.isLongPress = false;
        this.thumbDrawable = new MaterialShapeDrawable();
        Context context2 = getContext();
        this.inactiveTrackPaint = new Paint();
        this.inactiveTrackPaint.setStyle(Style.STROKE);
        this.inactiveTrackPaint.setStrokeCap(Cap.ROUND);
        this.activeTrackPaint = new Paint();
        this.activeTrackPaint.setStyle(Style.STROKE);
        this.activeTrackPaint.setStrokeCap(Cap.ROUND);
        this.thumbPaint = new Paint(1);
        this.thumbPaint.setStyle(Style.FILL);
        this.thumbPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.haloPaint = new Paint(1);
        this.haloPaint.setStyle(Style.FILL);
        this.inactiveTicksPaint = new Paint();
        this.inactiveTicksPaint.setStyle(Style.STROKE);
        this.inactiveTicksPaint.setStrokeCap(Cap.ROUND);
        this.activeTicksPaint = new Paint();
        this.activeTicksPaint.setStyle(Style.STROKE);
        this.activeTicksPaint.setStrokeCap(Cap.ROUND);
        loadResources(context2.getResources());
        this.labelMaker = new TooltipDrawableFactory() {
            public TooltipDrawable createTooltipDrawable() {
                TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(Slider.this.getContext(), attributeSet, C2556R.styleable.Slider, i, Slider.DEF_STYLE_RES, new int[0]);
                TooltipDrawable access$100 = Slider.parseLabelDrawable(Slider.this.getContext(), obtainStyledAttributes);
                obtainStyledAttributes.recycle();
                return access$100;
            }
        };
        processAttributes(context2, attributeSet, i);
        setFocusable(true);
        this.thumbDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        this.accessibilityHelper = new AccessibilityHelper();
        ViewCompat.setAccessibilityDelegate(this, this.accessibilityHelper);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
    }

    private void loadResources(Resources resources) {
        this.widgetHeight = resources.getDimensionPixelSize(C2556R.dimen.mtrl_slider_widget_height);
        this.trackSidePadding = resources.getDimensionPixelOffset(C2556R.dimen.mtrl_slider_track_side_padding);
        this.trackTop = resources.getDimensionPixelOffset(C2556R.dimen.mtrl_slider_track_top);
        this.labelPadding = resources.getDimensionPixelSize(C2556R.dimen.mtrl_slider_label_padding);
    }

    private void processAttributes(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, C2556R.styleable.Slider, i, DEF_STYLE_RES, new int[0]);
        this.valueFrom = obtainStyledAttributes.getFloat(C2556R.styleable.Slider_android_valueFrom, 0.0f);
        this.valueTo = obtainStyledAttributes.getFloat(C2556R.styleable.Slider_android_valueTo, 1.0f);
        setValue(obtainStyledAttributes.getFloat(C2556R.styleable.Slider_android_value, this.valueFrom));
        this.stepSize = obtainStyledAttributes.getFloat(C2556R.styleable.Slider_android_stepSize, 0.0f);
        boolean hasValue = obtainStyledAttributes.hasValue(C2556R.styleable.Slider_trackColor);
        int i2 = hasValue ? C2556R.styleable.Slider_trackColor : C2556R.styleable.Slider_trackColorInactive;
        int i3 = hasValue ? C2556R.styleable.Slider_trackColor : C2556R.styleable.Slider_trackColorActive;
        ColorStateList colorStateList = MaterialResources.getColorStateList(context, obtainStyledAttributes, i2);
        if (colorStateList == null) {
            colorStateList = AppCompatResources.getColorStateList(context, C2556R.C2557color.material_slider_inactive_track_color);
        }
        setTrackColorInactive(colorStateList);
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(context, obtainStyledAttributes, i3);
        if (colorStateList2 == null) {
            colorStateList2 = AppCompatResources.getColorStateList(context, C2556R.C2557color.material_slider_active_track_color);
        }
        setTrackColorActive(colorStateList2);
        this.thumbDrawable.setFillColor(MaterialResources.getColorStateList(context, obtainStyledAttributes, C2556R.styleable.Slider_thumbColor));
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(context, obtainStyledAttributes, C2556R.styleable.Slider_haloColor);
        if (colorStateList3 == null) {
            colorStateList3 = AppCompatResources.getColorStateList(context, C2556R.C2557color.material_slider_halo_color);
        }
        setHaloColor(colorStateList3);
        boolean hasValue2 = obtainStyledAttributes.hasValue(C2556R.styleable.Slider_tickColor);
        int i4 = hasValue2 ? C2556R.styleable.Slider_tickColor : C2556R.styleable.Slider_tickColorInactive;
        int i5 = hasValue2 ? C2556R.styleable.Slider_tickColor : C2556R.styleable.Slider_tickColorActive;
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(context, obtainStyledAttributes, i4);
        if (colorStateList4 == null) {
            colorStateList4 = AppCompatResources.getColorStateList(context, C2556R.C2557color.material_slider_inactive_tick_marks_color);
        }
        setTickColorInactive(colorStateList4);
        ColorStateList colorStateList5 = MaterialResources.getColorStateList(context, obtainStyledAttributes, i5);
        if (colorStateList5 == null) {
            colorStateList5 = AppCompatResources.getColorStateList(context, C2556R.C2557color.material_slider_active_tick_marks_color);
        }
        setTickColorActive(colorStateList5);
        setThumbRadius(obtainStyledAttributes.getDimensionPixelSize(C2556R.styleable.Slider_thumbRadius, 0));
        setHaloRadius(obtainStyledAttributes.getDimensionPixelSize(C2556R.styleable.Slider_haloRadius, 0));
        setThumbElevation(obtainStyledAttributes.getDimension(C2556R.styleable.Slider_thumbElevation, 0.0f));
        setTrackHeight(obtainStyledAttributes.getDimensionPixelSize(C2556R.styleable.Slider_trackHeight, 0));
        this.labelBehavior = obtainStyledAttributes.getInt(C2556R.styleable.Slider_labelBehavior, 0);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: private */
    public static TooltipDrawable parseLabelDrawable(Context context, TypedArray typedArray) {
        return TooltipDrawable.createFromAttributes(context, null, 0, typedArray.getResourceId(C2556R.styleable.Slider_labelStyle, C2556R.C2562style.Widget_MaterialComponents_Tooltip));
    }

    private void validateValueFrom() {
        if (this.valueFrom >= this.valueTo) {
            throw new IllegalStateException(EXCEPTION_ILLEGAL_VALUE_FROM);
        }
    }

    private void validateValueTo() {
        if (this.valueTo <= this.valueFrom) {
            throw new IllegalStateException(EXCEPTION_ILLEGAL_VALUE_TO);
        }
    }

    private void validateStepSize() {
        float f = this.stepSize;
        if (f > 0.0f && ((double) (((this.valueTo - this.valueFrom) / f) % 1.0f)) > THRESHOLD) {
            throw new IllegalStateException(EXCEPTION_ILLEGAL_STEP_SIZE);
        }
    }

    private void validateValues() {
        Iterator it = this.values.iterator();
        while (it.hasNext()) {
            Float f = (Float) it.next();
            if (f.floatValue() < this.valueFrom || f.floatValue() > this.valueTo) {
                throw new IllegalStateException(EXCEPTION_ILLEGAL_VALUE);
            } else if (this.stepSize > 0.0f && ((double) (((this.valueFrom - f.floatValue()) / this.stepSize) % 1.0f)) > THRESHOLD) {
                throw new IllegalStateException(EXCEPTION_ILLEGAL_DISCRETE_VALUE);
            }
        }
    }

    private void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            validateValueFrom();
            validateValueTo();
            validateStepSize();
            validateValues();
            this.dirtyConfig = false;
        }
    }

    public float getValueFrom() {
        return this.valueFrom;
    }

    public void setValueFrom(float f) {
        this.valueFrom = f;
        this.dirtyConfig = true;
        postInvalidate();
    }

    public float getValueTo() {
        return this.valueTo;
    }

    public void setValueTo(float f) {
        this.valueTo = f;
        this.dirtyConfig = true;
        postInvalidate();
    }

    public float getValue() {
        if (this.values.size() <= 1) {
            return ((Float) this.values.get(0)).floatValue();
        }
        throw new IllegalStateException("More than one value is set on the Slider. Use getValues() instead.");
    }

    public List<Float> getValues() {
        return new ArrayList(this.values);
    }

    public void setValue(float f) {
        setValues(Float.valueOf(f));
    }

    public void setValues(Float... fArr) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, fArr);
        setValuesInternal(arrayList);
    }

    public void setValues(List<Float> list) {
        setValuesInternal(new ArrayList(list));
    }

    private void setValuesInternal(ArrayList<Float> arrayList) {
        if (!arrayList.isEmpty()) {
            Collections.sort(arrayList);
            if (this.values.size() != arrayList.size() || !this.values.equals(arrayList)) {
                this.values = arrayList;
                this.dirtyConfig = true;
                this.focusedThumbIdx = 0;
                updateHaloHotspot();
                createLabelPool();
                dispatchOnChangedProgramatically();
                postInvalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    private void createLabelPool() {
        if (this.labels.size() > this.values.size()) {
            this.labels.subList(this.values.size(), this.labels.size()).clear();
        }
        while (this.labels.size() < this.values.size()) {
            this.labels.add(this.labelMaker.createTooltipDrawable());
        }
        int i = 1;
        if (this.labels.size() == 1) {
            i = 0;
        }
        for (TooltipDrawable strokeWidth : this.labels) {
            strokeWidth.setStrokeWidth((float) i);
        }
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException(EXCEPTION_ILLEGAL_STEP_SIZE);
        } else if (this.stepSize != f) {
            this.stepSize = f;
            this.dirtyConfig = true;
            postInvalidate();
        }
    }

    public float getMaximumValue() {
        ArrayList<Float> arrayList = this.values;
        return ((Float) arrayList.get(arrayList.size() - 1)).floatValue();
    }

    public float getMinimumValue() {
        return ((Float) this.values.get(0)).floatValue();
    }

    public int getFocusedThumbIndex() {
        return this.focusedThumbIdx;
    }

    public void setFocusedThumbIndex(int i) {
        if (i < 0 || i >= this.values.size()) {
            throw new IllegalArgumentException("index out of range");
        }
        this.focusedThumbIdx = i;
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
        postInvalidate();
    }

    public int getActiveThumbIndex() {
        return this.activeThumbIdx;
    }

    public void addOnChangeListener(OnChangeListener onChangeListener) {
        this.changeListeners.add(onChangeListener);
    }

    public void removeOnChangeListener(OnChangeListener onChangeListener) {
        this.changeListeners.remove(onChangeListener);
    }

    public void clearOnChangeListeners() {
        this.changeListeners.clear();
    }

    public void addOnSliderTouchListener(OnSliderTouchListener onSliderTouchListener) {
        this.touchListeners.add(onSliderTouchListener);
    }

    public void removeOnSliderTouchListener(OnSliderTouchListener onSliderTouchListener) {
        this.touchListeners.remove(onSliderTouchListener);
    }

    public void clearOnSliderTouchListeners() {
        this.touchListeners.clear();
    }

    public boolean hasLabelFormatter() {
        return this.formatter != null;
    }

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        this.formatter = labelFormatter;
    }

    public float getThumbElevation() {
        return this.thumbDrawable.getElevation();
    }

    public void setThumbElevation(float f) {
        this.thumbDrawable.setElevation(f);
    }

    public void setThumbElevationResource(int i) {
        setThumbElevation(getResources().getDimension(i));
    }

    public int getThumbRadius() {
        return this.thumbRadius;
    }

    public void setThumbRadius(int i) {
        if (i != this.thumbRadius) {
            this.thumbRadius = i;
            this.thumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, (float) this.thumbRadius).build());
            MaterialShapeDrawable materialShapeDrawable = this.thumbDrawable;
            int i2 = this.thumbRadius;
            materialShapeDrawable.setBounds(0, 0, i2 * 2, i2 * 2);
            postInvalidate();
        }
    }

    public void setThumbRadiusResource(int i) {
        setThumbRadius(getResources().getDimensionPixelSize(i));
    }

    public int getHaloRadius() {
        return this.haloRadius;
    }

    public void setHaloRadius(int i) {
        if (i != this.haloRadius) {
            this.haloRadius = i;
            if (!shouldDrawCompatHalo()) {
                Drawable background = getBackground();
                if (background instanceof RippleDrawable) {
                    DrawableUtils.setRippleDrawableRadius((RippleDrawable) background, this.haloRadius);
                }
            } else {
                postInvalidate();
            }
        }
    }

    public void setHaloRadiusResource(int i) {
        setHaloRadius(getResources().getDimensionPixelSize(i));
    }

    public int getLabelBehavior() {
        return this.labelBehavior;
    }

    public void setLabelBehavior(int i) {
        if (this.labelBehavior != i) {
            this.labelBehavior = i;
            requestLayout();
        }
    }

    public int getTrackSidePadding() {
        return this.trackSidePadding;
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public int getTrackHeight() {
        return this.trackHeight;
    }

    public void setTrackHeight(int i) {
        if (this.trackHeight != i) {
            this.trackHeight = i;
            invalidateTrack();
            postInvalidate();
        }
    }

    public ColorStateList getHaloColor() {
        return this.haloColor;
    }

    public void setHaloColor(ColorStateList colorStateList) {
        if (!colorStateList.equals(this.haloColor)) {
            this.haloColor = colorStateList;
            if (!shouldDrawCompatHalo()) {
                Drawable background = getBackground();
                if (background instanceof RippleDrawable) {
                    ((RippleDrawable) background).setColor(colorStateList);
                }
            } else {
                this.haloPaint.setColor(getColorForState(colorStateList));
                this.haloPaint.setAlpha(63);
                invalidate();
            }
        }
    }

    public ColorStateList getThumbColor() {
        return this.thumbDrawable.getFillColor();
    }

    public void setThumbColor(ColorStateList colorStateList) {
        this.thumbDrawable.setFillColor(colorStateList);
    }

    public ColorStateList getTickColor() {
        if (this.tickColorInactive.equals(this.tickColorActive)) {
            return this.tickColorActive;
        }
        throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
    }

    public void setTickColor(ColorStateList colorStateList) {
        setTickColorInactive(colorStateList);
        setTickColorActive(colorStateList);
    }

    public ColorStateList getTickColorActive() {
        return this.tickColorActive;
    }

    public void setTickColorActive(ColorStateList colorStateList) {
        if (!colorStateList.equals(this.tickColorActive)) {
            this.tickColorActive = colorStateList;
            this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
            invalidate();
        }
    }

    public ColorStateList getTickColorInactive() {
        return this.tickColorInactive;
    }

    public void setTickColorInactive(ColorStateList colorStateList) {
        if (!colorStateList.equals(this.tickColorInactive)) {
            this.tickColorInactive = colorStateList;
            this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
            invalidate();
        }
    }

    public ColorStateList getTrackColor() {
        if (this.trackColorInactive.equals(this.trackColorActive)) {
            return this.trackColorActive;
        }
        throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
    }

    public void setTrackColor(ColorStateList colorStateList) {
        setTrackColorInactive(colorStateList);
        setTrackColorActive(colorStateList);
    }

    public ColorStateList getTrackColorActive() {
        return this.trackColorActive;
    }

    public void setTrackColorActive(ColorStateList colorStateList) {
        if (!colorStateList.equals(this.trackColorActive)) {
            this.trackColorActive = colorStateList;
            this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
            invalidate();
        }
    }

    public ColorStateList getTrackColorInactive() {
        return this.trackColorInactive;
    }

    public void setTrackColorInactive(ColorStateList colorStateList) {
        if (!colorStateList.equals(this.trackColorInactive)) {
            this.trackColorInactive = colorStateList;
            this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
            invalidate();
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setLayerType(z ? 0 : 2, null);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (TooltipDrawable relativeToView : this.labels) {
            relativeToView.setRelativeToView(ViewUtils.getContentView(this));
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
        if (accessibilityEventSender2 != null) {
            removeCallbacks(accessibilityEventSender2);
        }
        for (TooltipDrawable tooltipDrawable : this.labels) {
            ViewOverlayImpl contentViewOverlay = ViewUtils.getContentViewOverlay(this);
            if (contentViewOverlay != null) {
                contentViewOverlay.remove(tooltipDrawable);
                tooltipDrawable.detachView(ViewUtils.getContentView(this));
            }
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = this.widgetHeight;
        int i4 = 0;
        if (this.labelBehavior == 1) {
            i4 = ((TooltipDrawable) this.labels.get(0)).getIntrinsicHeight();
        }
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(i3 + i4, 1073741824));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.trackWidth = i - (this.trackSidePadding * 2);
        if (this.stepSize > 0.0f) {
            calculateTicksCoordinates();
        }
        updateHaloHotspot();
    }

    private void calculateTicksCoordinates() {
        validateConfigurationIfDirty();
        int min = Math.min((int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f), (this.trackWidth / (this.trackHeight * 2)) + 1);
        float[] fArr = this.ticksCoordinates;
        if (fArr == null || fArr.length != min * 2) {
            this.ticksCoordinates = new float[(min * 2)];
        }
        float f = ((float) this.trackWidth) / ((float) (min - 1));
        for (int i = 0; i < min * 2; i += 2) {
            float[] fArr2 = this.ticksCoordinates;
            fArr2[i] = ((float) this.trackSidePadding) + (((float) (i / 2)) * f);
            fArr2[i + 1] = (float) calculateTop();
        }
    }

    /* access modifiers changed from: private */
    public void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                int normalizeValue = (int) ((normalizeValue(((Float) this.values.get(this.focusedThumbIdx)).floatValue()) * ((float) this.trackWidth)) + ((float) this.trackSidePadding));
                int calculateTop = calculateTop();
                int i = this.haloRadius;
                DrawableCompat.setHotspotBounds(background, normalizeValue - i, calculateTop - i, normalizeValue + i, calculateTop + i);
            }
        }
    }

    /* access modifiers changed from: private */
    public int calculateTop() {
        int i = this.trackTop;
        int i2 = 0;
        if (this.labelBehavior == 1) {
            i2 = ((TooltipDrawable) this.labels.get(0)).getIntrinsicHeight();
        }
        return i + i2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            validateConfigurationIfDirty();
            if (this.stepSize > 0.0f) {
                calculateTicksCoordinates();
            }
        }
        super.onDraw(canvas);
        int calculateTop = calculateTop();
        drawInactiveTrack(canvas, this.trackWidth, calculateTop);
        if (getMaximumValue() > this.valueFrom) {
            drawActiveTrack(canvas, this.trackWidth, calculateTop);
        }
        if (this.stepSize > 0.0f) {
            drawTicks(canvas);
        }
        if ((this.thumbIsPressed || isFocused()) && isEnabled()) {
            maybeDrawHalo(canvas, this.trackWidth, calculateTop);
            if (this.activeThumbIdx != -1) {
                ensureLabels();
            }
        }
        drawThumbs(canvas, this.trackWidth, calculateTop);
    }

    private float[] getActiveRange() {
        float normalizeValue = normalizeValue(this.values.size() == 1 ? this.valueFrom : getMinimumValue());
        float normalizeValue2 = normalizeValue(getMaximumValue());
        if (ViewCompat.getLayoutDirection(this) == 1) {
            return new float[]{normalizeValue2, normalizeValue};
        }
        return new float[]{normalizeValue, normalizeValue2};
    }

    private void drawInactiveTrack(Canvas canvas, int i, int i2) {
        float[] activeRange = getActiveRange();
        int i3 = this.trackSidePadding;
        float f = (float) i;
        float f2 = ((float) i3) + (activeRange[1] * f);
        if (f2 < ((float) (i3 + i))) {
            float f3 = (float) i2;
            canvas.drawLine(f2, f3, (float) (i3 + i), f3, this.inactiveTrackPaint);
        }
        int i4 = this.trackSidePadding;
        float f4 = ((float) i4) + (activeRange[0] * f);
        if (f4 > ((float) i4)) {
            float f5 = (float) i2;
            canvas.drawLine((float) i4, f5, f4, f5, this.inactiveTrackPaint);
        }
    }

    /* access modifiers changed from: private */
    public float normalizeValue(float f) {
        float f2 = this.valueFrom;
        float f3 = (f - f2) / (this.valueTo - f2);
        return ViewCompat.getLayoutDirection(this) == 1 ? 1.0f - f3 : f3;
    }

    private void drawActiveTrack(Canvas canvas, int i, int i2) {
        float[] activeRange = getActiveRange();
        int i3 = this.trackSidePadding;
        float f = (float) i;
        float f2 = (float) i2;
        Canvas canvas2 = canvas;
        float f3 = f2;
        canvas2.drawLine(((float) i3) + (activeRange[0] * f), f3, ((float) i3) + (activeRange[1] * f), f2, this.activeTrackPaint);
    }

    private void drawTicks(Canvas canvas) {
        float[] activeRange = getActiveRange();
        int pivotIndex = pivotIndex(this.ticksCoordinates, activeRange[0]);
        int pivotIndex2 = pivotIndex(this.ticksCoordinates, activeRange[1]);
        int i = pivotIndex * 2;
        canvas.drawPoints(this.ticksCoordinates, 0, i, this.inactiveTicksPaint);
        int i2 = pivotIndex2 * 2;
        canvas.drawPoints(this.ticksCoordinates, i, i2 - i, this.activeTicksPaint);
        float[] fArr = this.ticksCoordinates;
        canvas.drawPoints(fArr, i2, fArr.length - i2, this.inactiveTicksPaint);
    }

    private void drawThumbs(Canvas canvas, int i, int i2) {
        if (!isEnabled()) {
            Iterator it = this.values.iterator();
            while (it.hasNext()) {
                canvas.drawCircle(((float) this.trackSidePadding) + (normalizeValue(((Float) it.next()).floatValue()) * ((float) i)), (float) i2, (float) this.thumbRadius, this.thumbPaint);
            }
        }
        Iterator it2 = this.values.iterator();
        while (it2.hasNext()) {
            Float f = (Float) it2.next();
            canvas.save();
            int normalizeValue = this.trackSidePadding + ((int) (normalizeValue(f.floatValue()) * ((float) i)));
            int i3 = this.thumbRadius;
            canvas.translate((float) (normalizeValue - i3), (float) (i2 - i3));
            this.thumbDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void maybeDrawHalo(Canvas canvas, int i, int i2) {
        if (shouldDrawCompatHalo()) {
            int normalizeValue = (int) (((float) this.trackSidePadding) + (normalizeValue(((Float) this.values.get(this.focusedThumbIdx)).floatValue()) * ((float) i)));
            if (VERSION.SDK_INT < 28) {
                int i3 = this.haloRadius;
                canvas.clipRect((float) (normalizeValue - i3), (float) (i2 - i3), (float) (normalizeValue + i3), (float) (i3 + i2), Op.UNION);
            }
            canvas.drawCircle((float) normalizeValue, (float) i2, (float) this.haloRadius, this.haloPaint);
        }
    }

    private boolean shouldDrawCompatHalo() {
        return this.forceDrawCompatHalo || VERSION.SDK_INT < 21 || !(getBackground() instanceof RippleDrawable);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        float x = motionEvent.getX();
        this.touchPosition = (x - ((float) this.trackSidePadding)) / ((float) this.trackWidth);
        this.touchPosition = Math.max(0.0f, this.touchPosition);
        this.touchPosition = Math.min(1.0f, this.touchPosition);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.touchDownX = x;
            if (!isInScrollingContainer()) {
                getParent().requestDisallowInterceptTouchEvent(true);
                if (pickActiveThumb()) {
                    requestFocus();
                    this.thumbIsPressed = true;
                    snapTouchPosition();
                    updateHaloHotspot();
                    invalidate();
                    onStartTrackingTouch();
                }
            }
        } else if (actionMasked == 1) {
            this.thumbIsPressed = false;
            MotionEvent motionEvent2 = this.lastEvent;
            if (motionEvent2 != null && motionEvent2.getActionMasked() == 0 && this.lastEvent.getX() == motionEvent.getX() && this.lastEvent.getY() == motionEvent.getY()) {
                pickActiveThumb();
            }
            if (this.activeThumbIdx != -1) {
                snapTouchPosition();
                this.activeThumbIdx = -1;
            }
            for (TooltipDrawable remove : this.labels) {
                ViewUtils.getContentViewOverlay(this).remove(remove);
            }
            onStopTrackingTouch();
            invalidate();
        } else if (actionMasked == 2) {
            if (!this.thumbIsPressed) {
                if (Math.abs(x - this.touchDownX) < ((float) this.scaledTouchSlop)) {
                    return false;
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                onStartTrackingTouch();
            }
            if (pickActiveThumb()) {
                this.thumbIsPressed = true;
                snapTouchPosition();
                updateHaloHotspot();
                invalidate();
            }
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(motionEvent);
        return true;
    }

    private static int pivotIndex(float[] fArr, float f) {
        return Math.round(f * ((float) ((fArr.length / 2) - 1)));
    }

    private double snapPosition(float f) {
        float f2 = this.stepSize;
        if (f2 <= 0.0f) {
            return (double) f;
        }
        int i = (int) ((this.valueTo - this.valueFrom) / f2);
        double round = (double) Math.round(f * ((float) i));
        double d = (double) i;
        Double.isNaN(round);
        Double.isNaN(d);
        return round / d;
    }

    private boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float valueOfTouchPosition = getValueOfTouchPosition();
        float valueToX = valueToX(valueOfTouchPosition);
        float min = Math.min(valueToX, this.touchDownX);
        float max = Math.max(valueToX, this.touchDownX);
        this.activeThumbIdx = 0;
        float abs = Math.abs(((Float) this.values.get(this.activeThumbIdx)).floatValue() - valueOfTouchPosition);
        int i = 0;
        while (i < this.values.size()) {
            float abs2 = Math.abs(((Float) this.values.get(i)).floatValue() - valueOfTouchPosition);
            float valueToX2 = valueToX(((Float) this.values.get(i)).floatValue());
            float abs3 = Math.abs(valueToX2 - valueToX);
            float abs4 = Math.abs(valueToX(((Float) this.values.get(this.activeThumbIdx)).floatValue()) - valueToX);
            if (min >= valueToX2 || max <= valueToX2) {
                int i2 = this.scaledTouchSlop;
                if (abs3 >= ((float) i2) || abs4 >= ((float) i2) || ((double) Math.abs(abs3 - abs4)) <= THRESHOLD) {
                    if (abs2 < abs) {
                        this.activeThumbIdx = i;
                        abs = abs2;
                    }
                    i++;
                } else {
                    this.activeThumbIdx = -1;
                    return false;
                }
            } else {
                this.activeThumbIdx = i;
                return true;
            }
        }
        return true;
    }

    private boolean snapTouchPosition() {
        return snapActiveThumbToValue(getValueOfTouchPosition());
    }

    private boolean snapActiveThumbToValue(float f) {
        return snapThumbToValue(this.activeThumbIdx, f);
    }

    /* access modifiers changed from: private */
    public boolean snapThumbToValue(int i, float f) {
        if (((double) Math.abs(f - ((Float) this.values.get(i)).floatValue())) < THRESHOLD) {
            return false;
        }
        this.values.set(i, Float.valueOf(f));
        Collections.sort(this.values);
        if (i == this.activeThumbIdx) {
            i = this.values.indexOf(Float.valueOf(f));
        }
        this.activeThumbIdx = i;
        this.focusedThumbIdx = i;
        dispatchOnChangedFromUser(i);
        return true;
    }

    private float getValueOfTouchPosition() {
        double snapPosition = snapPosition(this.touchPosition);
        if (ViewCompat.getLayoutDirection(this) == 1) {
            snapPosition = 1.0d - snapPosition;
        }
        float f = this.valueTo;
        float f2 = this.valueFrom;
        double d = (double) (f - f2);
        Double.isNaN(d);
        double d2 = snapPosition * d;
        double d3 = (double) f2;
        Double.isNaN(d3);
        return (float) (d2 + d3);
    }

    private float valueToX(float f) {
        return (normalizeValue(f) * ((float) this.trackWidth)) + ((float) this.trackSidePadding);
    }

    private void ensureLabels() {
        if (this.labelBehavior != 2) {
            Iterator it = this.labels.iterator();
            for (int i = 0; i < this.values.size() && it.hasNext(); i++) {
                if (i != this.focusedThumbIdx) {
                    setValueForLabel((TooltipDrawable) it.next(), ((Float) this.values.get(i)).floatValue());
                }
            }
            if (it.hasNext()) {
                setValueForLabel((TooltipDrawable) it.next(), ((Float) this.values.get(this.focusedThumbIdx)).floatValue());
                return;
            }
            throw new IllegalStateException("Not enough labels to display all the values");
        }
    }

    /* access modifiers changed from: private */
    public String formatValue(float f) {
        if (hasLabelFormatter()) {
            return this.formatter.getFormattedValue(f);
        }
        return String.format(((float) ((int) f)) == f ? "%.0f" : "%.2f", new Object[]{Float.valueOf(f)});
    }

    private void setValueForLabel(TooltipDrawable tooltipDrawable, float f) {
        tooltipDrawable.setText(formatValue(f));
        int normalizeValue = (this.trackSidePadding + ((int) (normalizeValue(f) * ((float) this.trackWidth)))) - (tooltipDrawable.getIntrinsicWidth() / 2);
        int calculateTop = calculateTop() - (this.labelPadding + this.thumbRadius);
        tooltipDrawable.setBounds(normalizeValue, calculateTop - tooltipDrawable.getIntrinsicHeight(), tooltipDrawable.getIntrinsicWidth() + normalizeValue, calculateTop);
        Rect rect = new Rect(tooltipDrawable.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        tooltipDrawable.setBounds(rect);
        ViewUtils.getContentViewOverlay(this).add(tooltipDrawable);
    }

    private void invalidateTrack() {
        this.inactiveTrackPaint.setStrokeWidth((float) this.trackHeight);
        this.activeTrackPaint.setStrokeWidth((float) this.trackHeight);
        this.inactiveTicksPaint.setStrokeWidth(((float) this.trackHeight) / 2.0f);
        this.activeTicksPaint.setStrokeWidth(((float) this.trackHeight) / 2.0f);
    }

    private boolean isInScrollingContainer() {
        for (ViewParent parent = getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    private void dispatchOnChangedProgramatically() {
        for (OnChangeListener onChangeListener : this.changeListeners) {
            Iterator it = this.values.iterator();
            while (it.hasNext()) {
                onChangeListener.onValueChange(this, ((Float) it.next()).floatValue(), false);
            }
        }
    }

    private void dispatchOnChangedFromUser(int i) {
        for (OnChangeListener onValueChange : this.changeListeners) {
            onValueChange.onValueChange(this, ((Float) this.values.get(i)).floatValue(), true);
        }
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 != null && accessibilityManager2.isEnabled()) {
            scheduleAccessibilityEventSender(i);
        }
    }

    private void onStartTrackingTouch() {
        for (OnSliderTouchListener onStartTrackingTouch : this.touchListeners) {
            onStartTrackingTouch.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        for (OnSliderTouchListener onStopTrackingTouch : this.touchListeners) {
            onStopTrackingTouch.onStopTrackingTouch(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        for (TooltipDrawable tooltipDrawable : this.labels) {
            if (tooltipDrawable.isStateful()) {
                tooltipDrawable.setState(getDrawableState());
            }
        }
        if (this.thumbDrawable.isStateful()) {
            this.thumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    private int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    /* access modifiers changed from: 0000 */
    public void forceDrawCompatHalo(boolean z) {
        this.forceDrawCompatHalo = z;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (isEnabled()) {
            if (this.values.size() == 1) {
                this.activeThumbIdx = 0;
            }
            if (this.activeThumbIdx != -1) {
                this.isLongPress |= keyEvent.isLongPress();
                Float calculateIncrementForKey = calculateIncrementForKey(keyEvent, i);
                if (calculateIncrementForKey != null) {
                    if (ViewCompat.getLayoutDirection(this) == 1) {
                        calculateIncrementForKey = Float.valueOf(-calculateIncrementForKey.floatValue());
                    }
                    if (snapActiveThumbToValue(MathUtils.clamp(((Float) this.values.get(this.activeThumbIdx)).floatValue() + calculateIncrementForKey.floatValue(), this.valueFrom, this.valueTo))) {
                        updateHaloHotspot();
                        postInvalidate();
                    }
                    return true;
                }
            } else if (i != 61) {
                if (i != 66) {
                    if (i != 69) {
                        if (i != 81) {
                            switch (i) {
                                case 21:
                                    break;
                                case 22:
                                    break;
                                case 23:
                                    break;
                            }
                        }
                        moveFocus(1);
                        return true;
                    }
                    moveFocus(-1);
                    return true;
                }
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                return true;
            } else if (keyEvent.hasNoModifiers()) {
                moveFocus(1);
                return true;
            } else if (!keyEvent.isShiftPressed()) {
                return false;
            } else {
                moveFocus(-1);
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.isLongPress = false;
        return super.onKeyUp(i, keyEvent);
    }

    private void moveFocus(int i) {
        this.focusedThumbIdx += i;
        this.focusedThumbIdx = MathUtils.clamp(this.focusedThumbIdx, 0, this.values.size() - 1);
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = this.focusedThumbIdx;
        }
        updateHaloHotspot();
        postInvalidate();
    }

    private Float calculateIncrementForKey(KeyEvent keyEvent, int i) {
        float calculateStepIncrement = this.isLongPress ? calculateStepIncrement(20) : calculateStepIncrement();
        if (i != 21) {
            if (i != 22) {
                if (i != 61) {
                    if (i != 81) {
                        if (i != 69) {
                            if (i != 70) {
                                return null;
                            }
                        }
                    }
                } else if (keyEvent.isShiftPressed()) {
                    return Float.valueOf(-calculateStepIncrement);
                } else {
                    return Float.valueOf(calculateStepIncrement);
                }
            }
            return Float.valueOf(calculateStepIncrement);
        }
        calculateStepIncrement = -calculateStepIncrement;
        return Float.valueOf(calculateStepIncrement);
    }

    private float calculateStepIncrement() {
        float f = this.stepSize;
        if (f == 0.0f) {
            return 1.0f;
        }
        return f;
    }

    /* access modifiers changed from: private */
    public float calculateStepIncrement(int i) {
        float calculateStepIncrement = calculateStepIncrement();
        float f = (this.valueTo - this.valueFrom) / calculateStepIncrement;
        float f2 = (float) i;
        if (f <= f2) {
            return calculateStepIncrement;
        }
        return ((float) Math.round(f / f2)) * calculateStepIncrement;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z) {
            this.activeThumbIdx = -1;
            for (TooltipDrawable remove : this.labels) {
                ViewUtils.getContentViewOverlay(this).remove(remove);
            }
            this.accessibilityHelper.requestKeyboardFocusForVirtualView(Integer.MIN_VALUE);
            return;
        }
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.accessibilityHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    private void scheduleAccessibilityEventSender(int i) {
        AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
        if (accessibilityEventSender2 == null) {
            this.accessibilityEventSender = new AccessibilityEventSender();
        } else {
            removeCallbacks(accessibilityEventSender2);
        }
        this.accessibilityEventSender.setVirtualViewId(i);
        postDelayed(this.accessibilityEventSender, 200);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList<>(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        SliderState sliderState = (SliderState) parcelable;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        this.values = sliderState.values;
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
        dispatchOnChangedProgramatically();
    }
}
