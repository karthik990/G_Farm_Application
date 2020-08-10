package com.wdullaer.materialdatetimepicker.time;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;
import com.wdullaer.materialdatetimepicker.time.Timepoint.TYPE;
import java.util.Calendar;
import java.util.Locale;

public class RadialPickerLayout extends FrameLayout implements OnTouchListener {

    /* renamed from: AM */
    private static final int f3680AM = 0;
    private static final int HOUR_INDEX = 0;
    private static final int HOUR_VALUE_TO_DEGREES_STEP_SIZE = 30;
    private static final int MINUTE_INDEX = 1;
    private static final int MINUTE_VALUE_TO_DEGREES_STEP_SIZE = 6;

    /* renamed from: PM */
    private static final int f3681PM = 1;
    private static final int SECOND_INDEX = 2;
    private static final int SECOND_VALUE_TO_DEGREES_STEP_SIZE = 6;
    private static final String TAG = "RadialPickerLayout";
    private static final int VISIBLE_DEGREES_STEP_SIZE = 30;
    private final int TAP_TIMEOUT;
    private final int TOUCH_SLOP;
    private AccessibilityManager mAccessibilityManager;
    /* access modifiers changed from: private */
    public AmPmCirclesView mAmPmCirclesView;
    private CircleView mCircleView;
    /* access modifiers changed from: private */
    public TimePickerController mController;
    private int mCurrentItemShowing;
    /* access modifiers changed from: private */
    public Timepoint mCurrentTime;
    /* access modifiers changed from: private */
    public boolean mDoingMove;
    private boolean mDoingTouch;
    /* access modifiers changed from: private */
    public int mDownDegrees;
    private float mDownX;
    private float mDownY;
    private View mGrayBox;
    private Handler mHandler = new Handler();
    private RadialSelectorView mHourRadialSelectorView;
    private RadialTextsView mHourRadialTextsView;
    private boolean mInputEnabled;
    /* access modifiers changed from: private */
    public boolean mIs24HourMode;
    /* access modifiers changed from: private */
    public int mIsTouchingAmOrPm = -1;
    /* access modifiers changed from: private */
    public Timepoint mLastValueSelected;
    /* access modifiers changed from: private */
    public OnValueSelectedListener mListener;
    private RadialSelectorView mMinuteRadialSelectorView;
    private RadialTextsView mMinuteRadialTextsView;
    private RadialSelectorView mSecondRadialSelectorView;
    private RadialTextsView mSecondRadialTextsView;
    private int[] mSnapPrefer30sMap;
    private boolean mTimeInitialized;
    private AnimatorSet mTransition;

    public interface OnValueSelectedListener {
        void advancePicker(int i);

        void enablePicker();

        void onValueSelected(Timepoint timepoint);
    }

    public RadialPickerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnTouchListener(this);
        this.TOUCH_SLOP = ViewConfiguration.get(context).getScaledTouchSlop();
        this.TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        this.mDoingMove = false;
        this.mCircleView = new CircleView(context);
        addView(this.mCircleView);
        this.mAmPmCirclesView = new AmPmCirclesView(context);
        addView(this.mAmPmCirclesView);
        this.mHourRadialSelectorView = new RadialSelectorView(context);
        addView(this.mHourRadialSelectorView);
        this.mMinuteRadialSelectorView = new RadialSelectorView(context);
        addView(this.mMinuteRadialSelectorView);
        this.mSecondRadialSelectorView = new RadialSelectorView(context);
        addView(this.mSecondRadialSelectorView);
        this.mHourRadialTextsView = new RadialTextsView(context);
        addView(this.mHourRadialTextsView);
        this.mMinuteRadialTextsView = new RadialTextsView(context);
        addView(this.mMinuteRadialTextsView);
        this.mSecondRadialTextsView = new RadialTextsView(context);
        addView(this.mSecondRadialTextsView);
        preparePrefer30sMap();
        this.mLastValueSelected = null;
        this.mInputEnabled = true;
        this.mGrayBox = new View(context);
        this.mGrayBox.setLayoutParams(new LayoutParams(-1, -1));
        this.mGrayBox.setBackgroundColor(ContextCompat.getColor(context, C5266R.C5267color.mdtp_transparent_black));
        this.mGrayBox.setVisibility(4);
        addView(this.mGrayBox);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mTimeInitialized = false;
    }

    public void setOnValueSelectedListener(OnValueSelectedListener onValueSelectedListener) {
        this.mListener = onValueSelectedListener;
    }

    public void initialize(Context context, TimePickerController timePickerController, Timepoint timepoint, boolean z) {
        char c;
        C52792 r18;
        C52781 r17;
        int i;
        String str;
        Context context2 = context;
        if (this.mTimeInitialized) {
            Log.e(TAG, "Time has already been initialized.");
            return;
        }
        this.mController = timePickerController;
        this.mIs24HourMode = this.mAccessibilityManager.isTouchExplorationEnabled() || z;
        this.mCircleView.initialize(context2, this.mController);
        this.mCircleView.invalidate();
        if (!this.mIs24HourMode && this.mController.getVersion() == Version.VERSION_1) {
            this.mAmPmCirclesView.initialize(context2, this.mController, timepoint.isAM() ^ true ? 1 : 0);
            this.mAmPmCirclesView.invalidate();
        }
        C52781 r10 = new SelectionValidator() {
            public boolean isValidSelection(int i) {
                return !RadialPickerLayout.this.mController.isOutOfRange(new Timepoint(RadialPickerLayout.this.mCurrentTime.getHour(), RadialPickerLayout.this.mCurrentTime.getMinute(), i), 2);
            }
        };
        C52792 r11 = new SelectionValidator() {
            public boolean isValidSelection(int i) {
                return !RadialPickerLayout.this.mController.isOutOfRange(new Timepoint(RadialPickerLayout.this.mCurrentTime.getHour(), i, RadialPickerLayout.this.mCurrentTime.getSecond()), 1);
            }
        };
        C52803 r6 = new SelectionValidator() {
            public boolean isValidSelection(int i) {
                Timepoint timepoint = new Timepoint(i, RadialPickerLayout.this.mCurrentTime.getMinute(), RadialPickerLayout.this.mCurrentTime.getSecond());
                if (!RadialPickerLayout.this.mIs24HourMode && RadialPickerLayout.this.getIsCurrentlyAmOrPm() == 1) {
                    timepoint.setPM();
                }
                if (!RadialPickerLayout.this.mIs24HourMode && RadialPickerLayout.this.getIsCurrentlyAmOrPm() == 0) {
                    timepoint.setAM();
                }
                return !RadialPickerLayout.this.mController.isOutOfRange(timepoint, 0);
            }
        };
        int[] iArr = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] iArr2 = {0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int[] iArr3 = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        int[] iArr4 = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        String[] strArr = new String[12];
        String[] strArr2 = new String[12];
        String[] strArr3 = new String[12];
        String[] strArr4 = new String[12];
        int i2 = 0;
        for (int i3 = 12; i2 < i3; i3 = 12) {
            String str2 = "%d";
            String str3 = "%02d";
            if (z) {
                r17 = r10;
                r18 = r11;
                i = 1;
                c = 0;
                str = String.format(Locale.getDefault(), str3, new Object[]{Integer.valueOf(iArr2[i2])});
            } else {
                r17 = r10;
                r18 = r11;
                i = 1;
                c = 0;
                str = String.format(Locale.getDefault(), str2, new Object[]{Integer.valueOf(iArr[i2])});
            }
            strArr[i2] = str;
            Locale locale = Locale.getDefault();
            Object[] objArr = new Object[i];
            objArr[c] = Integer.valueOf(iArr[i2]);
            strArr2[i2] = String.format(locale, str2, objArr);
            Locale locale2 = Locale.getDefault();
            Object[] objArr2 = new Object[i];
            objArr2[c] = Integer.valueOf(iArr3[i2]);
            strArr3[i2] = String.format(locale2, str3, objArr2);
            Locale locale3 = Locale.getDefault();
            Object[] objArr3 = new Object[i];
            objArr3[c] = Integer.valueOf(iArr4[i2]);
            strArr4[i2] = String.format(locale3, str3, objArr3);
            i2++;
            Context context3 = context;
            r10 = r17;
            r11 = r18;
        }
        C52781 r172 = r10;
        C52792 r182 = r11;
        this.mHourRadialTextsView.initialize(context, strArr, z ? strArr2 : null, this.mController, r6, true);
        RadialTextsView radialTextsView = this.mHourRadialTextsView;
        int hour = timepoint.getHour();
        if (!z) {
            hour = iArr[hour % 12];
        }
        radialTextsView.setSelection(hour);
        this.mHourRadialTextsView.invalidate();
        this.mMinuteRadialTextsView.initialize(context, strArr3, null, this.mController, r182, false);
        this.mMinuteRadialTextsView.setSelection(timepoint.getMinute());
        this.mMinuteRadialTextsView.invalidate();
        this.mSecondRadialTextsView.initialize(context, strArr4, null, this.mController, r172, false);
        this.mSecondRadialTextsView.setSelection(timepoint.getSecond());
        this.mSecondRadialTextsView.invalidate();
        this.mCurrentTime = timepoint;
        Context context4 = context;
        this.mHourRadialSelectorView.initialize(context4, this.mController, z, true, (timepoint.getHour() % 12) * 30, isHourInnerCircle(timepoint.getHour()));
        this.mMinuteRadialSelectorView.initialize(context4, this.mController, false, false, timepoint.getMinute() * 6, false);
        this.mSecondRadialSelectorView.initialize(context4, this.mController, false, false, timepoint.getSecond() * 6, false);
        this.mTimeInitialized = true;
    }

    public void setTime(Timepoint timepoint) {
        setItem(0, timepoint);
    }

    private void setItem(int i, Timepoint timepoint) {
        Timepoint roundToValidTime = roundToValidTime(timepoint, i);
        this.mCurrentTime = roundToValidTime;
        reselectSelector(roundToValidTime, false, i);
    }

    private boolean isHourInnerCircle(int i) {
        return this.mIs24HourMode && i <= 12 && i != 0;
    }

    public int getHours() {
        return this.mCurrentTime.getHour();
    }

    public int getMinutes() {
        return this.mCurrentTime.getMinute();
    }

    public int getSeconds() {
        return this.mCurrentTime.getSecond();
    }

    public Timepoint getTime() {
        return this.mCurrentTime;
    }

    private int getCurrentlyShowingValue() {
        int currentItemShowing = getCurrentItemShowing();
        if (currentItemShowing == 0) {
            return this.mCurrentTime.getHour();
        }
        if (currentItemShowing == 1) {
            return this.mCurrentTime.getMinute();
        }
        if (currentItemShowing != 2) {
            return -1;
        }
        return this.mCurrentTime.getSecond();
    }

    public int getIsCurrentlyAmOrPm() {
        if (this.mCurrentTime.isAM()) {
            return 0;
        }
        return this.mCurrentTime.isPM() ? 1 : -1;
    }

    public void setAmOrPm(int i) {
        this.mAmPmCirclesView.setAmOrPm(i);
        this.mAmPmCirclesView.invalidate();
        Timepoint timepoint = new Timepoint(this.mCurrentTime);
        if (i == 0) {
            timepoint.setAM();
        } else if (i == 1) {
            timepoint.setPM();
        }
        Timepoint roundToValidTime = roundToValidTime(timepoint, 0);
        reselectSelector(roundToValidTime, false, 0);
        this.mCurrentTime = roundToValidTime;
        this.mListener.onValueSelected(roundToValidTime);
    }

    private void preparePrefer30sMap() {
        this.mSnapPrefer30sMap = new int[361];
        int i = 0;
        int i2 = 1;
        int i3 = 8;
        for (int i4 = 0; i4 < 361; i4++) {
            this.mSnapPrefer30sMap[i4] = i;
            if (i2 == i3) {
                i += 6;
                int i5 = i == 360 ? 7 : i % 30 == 0 ? 14 : 4;
                i3 = i5;
                i2 = 1;
            } else {
                i2++;
            }
        }
    }

    private int snapPrefer30s(int i) {
        int[] iArr = this.mSnapPrefer30sMap;
        if (iArr == null) {
            return -1;
        }
        return iArr[i];
    }

    private static int snapOnly30s(int i, int i2) {
        int i3 = (i / 30) * 30;
        int i4 = i3 + 30;
        if (i2 != 1) {
            if (i2 == -1) {
                return i == i3 ? i3 - 30 : i3;
            }
            if (i - i3 < i4 - i) {
                return i3;
            }
        }
        return i4;
    }

    /* access modifiers changed from: private */
    public Timepoint roundToValidTime(Timepoint timepoint, int i) {
        if (i == 0) {
            return this.mController.roundToNearest(timepoint, null);
        }
        if (i != 1) {
            return this.mController.roundToNearest(timepoint, TYPE.MINUTE);
        }
        return this.mController.roundToNearest(timepoint, TYPE.HOUR);
    }

    /* access modifiers changed from: private */
    public void reselectSelector(Timepoint timepoint, boolean z, int i) {
        if (i == 0) {
            int hour = timepoint.getHour();
            boolean isHourInnerCircle = isHourInnerCircle(hour);
            int i2 = hour % 12;
            int i3 = (i2 * 360) / 12;
            if (!this.mIs24HourMode) {
                hour = i2;
            }
            if (!this.mIs24HourMode && hour == 0) {
                hour += 12;
            }
            this.mHourRadialSelectorView.setSelection(i3, isHourInnerCircle, z);
            this.mHourRadialTextsView.setSelection(hour);
            if (timepoint.getMinute() != this.mCurrentTime.getMinute()) {
                this.mMinuteRadialSelectorView.setSelection((timepoint.getMinute() * 360) / 60, isHourInnerCircle, z);
                this.mMinuteRadialTextsView.setSelection(timepoint.getMinute());
            }
            if (timepoint.getSecond() != this.mCurrentTime.getSecond()) {
                this.mSecondRadialSelectorView.setSelection((timepoint.getSecond() * 360) / 60, isHourInnerCircle, z);
                this.mSecondRadialTextsView.setSelection(timepoint.getSecond());
            }
        } else if (i == 1) {
            this.mMinuteRadialSelectorView.setSelection((timepoint.getMinute() * 360) / 60, false, z);
            this.mMinuteRadialTextsView.setSelection(timepoint.getMinute());
            if (timepoint.getSecond() != this.mCurrentTime.getSecond()) {
                this.mSecondRadialSelectorView.setSelection((timepoint.getSecond() * 360) / 60, false, z);
                this.mSecondRadialTextsView.setSelection(timepoint.getSecond());
            }
        } else if (i == 2) {
            this.mSecondRadialSelectorView.setSelection((timepoint.getSecond() * 360) / 60, false, z);
            this.mSecondRadialTextsView.setSelection(timepoint.getSecond());
        }
        int currentItemShowing = getCurrentItemShowing();
        if (currentItemShowing == 0) {
            this.mHourRadialSelectorView.invalidate();
            this.mHourRadialTextsView.invalidate();
        } else if (currentItemShowing == 1) {
            this.mMinuteRadialSelectorView.invalidate();
            this.mMinuteRadialTextsView.invalidate();
        } else if (currentItemShowing == 2) {
            this.mSecondRadialSelectorView.invalidate();
            this.mSecondRadialTextsView.invalidate();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0035, code lost:
        if (r8 == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0038, code lost:
        if (r7 == 0) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0041, code lost:
        if (r0 == 2) goto L_0x0043;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x007f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.wdullaer.materialdatetimepicker.time.Timepoint getTimeFromDegrees(int r7, boolean r8, boolean r9) {
        /*
            r6 = this;
            r0 = -1
            if (r7 != r0) goto L_0x0005
            r7 = 0
            return r7
        L_0x0005:
            int r0 = r6.getCurrentItemShowing()
            r1 = 2
            r2 = 0
            r3 = 1
            if (r9 != 0) goto L_0x0014
            if (r0 == r3) goto L_0x0012
            if (r0 != r1) goto L_0x0014
        L_0x0012:
            r9 = 1
            goto L_0x0015
        L_0x0014:
            r9 = 0
        L_0x0015:
            if (r9 == 0) goto L_0x001c
            int r7 = r6.snapPrefer30s(r7)
            goto L_0x0020
        L_0x001c:
            int r7 = snapOnly30s(r7, r2)
        L_0x0020:
            r9 = 6
            if (r0 == 0) goto L_0x0024
            goto L_0x0026
        L_0x0024:
            r9 = 30
        L_0x0026:
            r4 = 360(0x168, float:5.04E-43)
            if (r0 != 0) goto L_0x003d
            boolean r5 = r6.mIs24HourMode
            if (r5 == 0) goto L_0x0038
            if (r7 != 0) goto L_0x0033
            if (r8 == 0) goto L_0x0033
            goto L_0x003a
        L_0x0033:
            if (r7 != r4) goto L_0x0044
            if (r8 != 0) goto L_0x0044
            goto L_0x0043
        L_0x0038:
            if (r7 != 0) goto L_0x0044
        L_0x003a:
            r7 = 360(0x168, float:5.04E-43)
            goto L_0x0044
        L_0x003d:
            if (r7 != r4) goto L_0x0044
            if (r0 == r3) goto L_0x0043
            if (r0 != r1) goto L_0x0044
        L_0x0043:
            r7 = 0
        L_0x0044:
            int r9 = r7 / r9
            if (r0 != 0) goto L_0x0052
            boolean r5 = r6.mIs24HourMode
            if (r5 == 0) goto L_0x0052
            if (r8 != 0) goto L_0x0052
            if (r7 == 0) goto L_0x0052
            int r9 = r9 + 12
        L_0x0052:
            if (r0 == 0) goto L_0x007f
            if (r0 == r3) goto L_0x006d
            if (r0 == r1) goto L_0x005b
            com.wdullaer.materialdatetimepicker.time.Timepoint r7 = r6.mCurrentTime
            goto L_0x00ab
        L_0x005b:
            com.wdullaer.materialdatetimepicker.time.Timepoint r7 = new com.wdullaer.materialdatetimepicker.time.Timepoint
            com.wdullaer.materialdatetimepicker.time.Timepoint r8 = r6.mCurrentTime
            int r8 = r8.getHour()
            com.wdullaer.materialdatetimepicker.time.Timepoint r0 = r6.mCurrentTime
            int r0 = r0.getMinute()
            r7.<init>(r8, r0, r9)
            goto L_0x00ab
        L_0x006d:
            com.wdullaer.materialdatetimepicker.time.Timepoint r7 = new com.wdullaer.materialdatetimepicker.time.Timepoint
            com.wdullaer.materialdatetimepicker.time.Timepoint r8 = r6.mCurrentTime
            int r8 = r8.getHour()
            com.wdullaer.materialdatetimepicker.time.Timepoint r0 = r6.mCurrentTime
            int r0 = r0.getSecond()
            r7.<init>(r8, r9, r0)
            goto L_0x00ab
        L_0x007f:
            boolean r8 = r6.mIs24HourMode
            if (r8 != 0) goto L_0x008d
            int r8 = r6.getIsCurrentlyAmOrPm()
            if (r8 != r3) goto L_0x008d
            if (r7 == r4) goto L_0x008d
            int r9 = r9 + 12
        L_0x008d:
            boolean r8 = r6.mIs24HourMode
            if (r8 != 0) goto L_0x009a
            int r8 = r6.getIsCurrentlyAmOrPm()
            if (r8 != 0) goto L_0x009a
            if (r7 != r4) goto L_0x009a
            r9 = 0
        L_0x009a:
            com.wdullaer.materialdatetimepicker.time.Timepoint r7 = new com.wdullaer.materialdatetimepicker.time.Timepoint
            com.wdullaer.materialdatetimepicker.time.Timepoint r8 = r6.mCurrentTime
            int r8 = r8.getMinute()
            com.wdullaer.materialdatetimepicker.time.Timepoint r0 = r6.mCurrentTime
            int r0 = r0.getSecond()
            r7.<init>(r9, r8, r0)
        L_0x00ab:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wdullaer.materialdatetimepicker.time.RadialPickerLayout.getTimeFromDegrees(int, boolean, boolean):com.wdullaer.materialdatetimepicker.time.Timepoint");
    }

    private int getDegreesFromCoords(float f, float f2, boolean z, Boolean[] boolArr) {
        int currentItemShowing = getCurrentItemShowing();
        if (currentItemShowing == 0) {
            return this.mHourRadialSelectorView.getDegreesFromCoords(f, f2, z, boolArr);
        }
        if (currentItemShowing == 1) {
            return this.mMinuteRadialSelectorView.getDegreesFromCoords(f, f2, z, boolArr);
        }
        if (currentItemShowing != 2) {
            return -1;
        }
        return this.mSecondRadialSelectorView.getDegreesFromCoords(f, f2, z, boolArr);
    }

    public int getCurrentItemShowing() {
        int i = this.mCurrentItemShowing;
        if (i == 0 || i == 1 || i == 2) {
            return this.mCurrentItemShowing;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Current item showing was unfortunately set to ");
        sb.append(this.mCurrentItemShowing);
        Log.e(TAG, sb.toString());
        return -1;
    }

    public void setCurrentItemShowing(int i, boolean z) {
        if (i == 0 || i == 1 || i == 2) {
            int currentItemShowing = getCurrentItemShowing();
            this.mCurrentItemShowing = i;
            reselectSelector(getTime(), true, i);
            if (!z || i == currentItemShowing) {
                transitionWithoutAnimation(i);
            } else {
                ObjectAnimator[] objectAnimatorArr = new ObjectAnimator[4];
                if (i == 1 && currentItemShowing == 0) {
                    objectAnimatorArr[0] = this.mHourRadialTextsView.getDisappearAnimator();
                    objectAnimatorArr[1] = this.mHourRadialSelectorView.getDisappearAnimator();
                    objectAnimatorArr[2] = this.mMinuteRadialTextsView.getReappearAnimator();
                    objectAnimatorArr[3] = this.mMinuteRadialSelectorView.getReappearAnimator();
                } else if (i == 0 && currentItemShowing == 1) {
                    objectAnimatorArr[0] = this.mHourRadialTextsView.getReappearAnimator();
                    objectAnimatorArr[1] = this.mHourRadialSelectorView.getReappearAnimator();
                    objectAnimatorArr[2] = this.mMinuteRadialTextsView.getDisappearAnimator();
                    objectAnimatorArr[3] = this.mMinuteRadialSelectorView.getDisappearAnimator();
                } else if (i == 1 && currentItemShowing == 2) {
                    objectAnimatorArr[0] = this.mSecondRadialTextsView.getDisappearAnimator();
                    objectAnimatorArr[1] = this.mSecondRadialSelectorView.getDisappearAnimator();
                    objectAnimatorArr[2] = this.mMinuteRadialTextsView.getReappearAnimator();
                    objectAnimatorArr[3] = this.mMinuteRadialSelectorView.getReappearAnimator();
                } else if (i == 0 && currentItemShowing == 2) {
                    objectAnimatorArr[0] = this.mSecondRadialTextsView.getDisappearAnimator();
                    objectAnimatorArr[1] = this.mSecondRadialSelectorView.getDisappearAnimator();
                    objectAnimatorArr[2] = this.mHourRadialTextsView.getReappearAnimator();
                    objectAnimatorArr[3] = this.mHourRadialSelectorView.getReappearAnimator();
                } else if (i == 2 && currentItemShowing == 1) {
                    objectAnimatorArr[0] = this.mSecondRadialTextsView.getReappearAnimator();
                    objectAnimatorArr[1] = this.mSecondRadialSelectorView.getReappearAnimator();
                    objectAnimatorArr[2] = this.mMinuteRadialTextsView.getDisappearAnimator();
                    objectAnimatorArr[3] = this.mMinuteRadialSelectorView.getDisappearAnimator();
                } else if (i == 2 && currentItemShowing == 0) {
                    objectAnimatorArr[0] = this.mSecondRadialTextsView.getReappearAnimator();
                    objectAnimatorArr[1] = this.mSecondRadialSelectorView.getReappearAnimator();
                    objectAnimatorArr[2] = this.mHourRadialTextsView.getDisappearAnimator();
                    objectAnimatorArr[3] = this.mHourRadialSelectorView.getDisappearAnimator();
                }
                if (objectAnimatorArr[0] == null || objectAnimatorArr[1] == null || objectAnimatorArr[2] == null || objectAnimatorArr[3] == null) {
                    transitionWithoutAnimation(i);
                } else {
                    AnimatorSet animatorSet = this.mTransition;
                    if (animatorSet != null && animatorSet.isRunning()) {
                        this.mTransition.end();
                    }
                    this.mTransition = new AnimatorSet();
                    this.mTransition.playTogether(objectAnimatorArr);
                    this.mTransition.start();
                }
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("TimePicker does not support view at index ");
        sb.append(i);
        Log.e(TAG, sb.toString());
    }

    private void transitionWithoutAnimation(int i) {
        int i2 = 0;
        int i3 = i == 0 ? 1 : 0;
        int i4 = i == 1 ? 1 : 0;
        if (i == 2) {
            i2 = 1;
        }
        float f = (float) i3;
        this.mHourRadialTextsView.setAlpha(f);
        this.mHourRadialSelectorView.setAlpha(f);
        float f2 = (float) i4;
        this.mMinuteRadialTextsView.setAlpha(f2);
        this.mMinuteRadialSelectorView.setAlpha(f2);
        float f3 = (float) i2;
        this.mSecondRadialTextsView.setAlpha(f3);
        this.mSecondRadialSelectorView.setAlpha(f3);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
        if (r11 <= ((float) r7)) goto L_0x0153;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r10, android.view.MotionEvent r11) {
        /*
            r9 = this;
            float r10 = r11.getX()
            float r0 = r11.getY()
            r1 = 1
            java.lang.Boolean[] r2 = new java.lang.Boolean[r1]
            r3 = 0
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r3)
            r2[r3] = r4
            int r11 = r11.getAction()
            r4 = 0
            r5 = -1
            if (r11 == 0) goto L_0x0154
            java.lang.String r6 = "RadialPickerLayout"
            if (r11 == r1) goto L_0x00b8
            r7 = 2
            if (r11 == r7) goto L_0x0023
            goto L_0x0153
        L_0x0023:
            boolean r11 = r9.mInputEnabled
            if (r11 != 0) goto L_0x002d
            java.lang.String r10 = "Input was disabled, but received ACTION_MOVE."
            android.util.Log.e(r6, r10)
            return r1
        L_0x002d:
            float r11 = r9.mDownY
            float r11 = r0 - r11
            float r11 = java.lang.Math.abs(r11)
            float r6 = r9.mDownX
            float r6 = r10 - r6
            float r6 = java.lang.Math.abs(r6)
            boolean r7 = r9.mDoingMove
            if (r7 != 0) goto L_0x004f
            int r7 = r9.TOUCH_SLOP
            float r8 = (float) r7
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 > 0) goto L_0x004f
            float r6 = (float) r7
            int r11 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r11 > 0) goto L_0x004f
            goto L_0x0153
        L_0x004f:
            int r11 = r9.mIsTouchingAmOrPm
            if (r11 == 0) goto L_0x009b
            if (r11 != r1) goto L_0x0056
            goto L_0x009b
        L_0x0056:
            int r11 = r9.mDownDegrees
            if (r11 != r5) goto L_0x005c
            goto L_0x0153
        L_0x005c:
            r9.mDoingMove = r1
            android.os.Handler r11 = r9.mHandler
            r11.removeCallbacksAndMessages(r4)
            int r10 = r9.getDegreesFromCoords(r10, r0, r1, r2)
            if (r10 == r5) goto L_0x009a
            r11 = r2[r3]
            boolean r11 = r11.booleanValue()
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = r9.getTimeFromDegrees(r10, r11, r3)
            int r11 = r9.getCurrentItemShowing()
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = r9.roundToValidTime(r10, r11)
            int r11 = r9.getCurrentItemShowing()
            r9.reselectSelector(r10, r1, r11)
            if (r10 == 0) goto L_0x009a
            com.wdullaer.materialdatetimepicker.time.Timepoint r11 = r9.mLastValueSelected
            if (r11 == 0) goto L_0x008e
            boolean r11 = r11.equals(r10)
            if (r11 != 0) goto L_0x009a
        L_0x008e:
            com.wdullaer.materialdatetimepicker.time.TimePickerController r11 = r9.mController
            r11.tryVibrate()
            r9.mLastValueSelected = r10
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$OnValueSelectedListener r11 = r9.mListener
            r11.onValueSelected(r10)
        L_0x009a:
            return r1
        L_0x009b:
            android.os.Handler r11 = r9.mHandler
            r11.removeCallbacksAndMessages(r4)
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r11 = r9.mAmPmCirclesView
            int r10 = r11.getIsTouchingAmOrPm(r10, r0)
            int r11 = r9.mIsTouchingAmOrPm
            if (r10 == r11) goto L_0x0153
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r10 = r9.mAmPmCirclesView
            r10.setAmOrPmPressed(r5)
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r10 = r9.mAmPmCirclesView
            r10.invalidate()
            r9.mIsTouchingAmOrPm = r5
            goto L_0x0153
        L_0x00b8:
            boolean r11 = r9.mInputEnabled
            if (r11 != 0) goto L_0x00c7
            java.lang.String r10 = "Input was disabled, but received ACTION_UP."
            android.util.Log.d(r6, r10)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$OnValueSelectedListener r10 = r9.mListener
            r10.enablePicker()
            return r1
        L_0x00c7:
            android.os.Handler r11 = r9.mHandler
            r11.removeCallbacksAndMessages(r4)
            r9.mDoingTouch = r3
            int r11 = r9.mIsTouchingAmOrPm
            if (r11 == 0) goto L_0x0110
            if (r11 != r1) goto L_0x00d5
            goto L_0x0110
        L_0x00d5:
            int r11 = r9.mDownDegrees
            if (r11 == r5) goto L_0x010d
            boolean r11 = r9.mDoingMove
            int r10 = r9.getDegreesFromCoords(r10, r0, r11, r2)
            if (r10 == r5) goto L_0x010d
            r11 = r2[r3]
            boolean r11 = r11.booleanValue()
            boolean r0 = r9.mDoingMove
            r0 = r0 ^ r1
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = r9.getTimeFromDegrees(r10, r11, r0)
            int r11 = r9.getCurrentItemShowing()
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = r9.roundToValidTime(r10, r11)
            int r11 = r9.getCurrentItemShowing()
            r9.reselectSelector(r10, r3, r11)
            r9.mCurrentTime = r10
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$OnValueSelectedListener r11 = r9.mListener
            r11.onValueSelected(r10)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$OnValueSelectedListener r10 = r9.mListener
            int r11 = r9.getCurrentItemShowing()
            r10.advancePicker(r11)
        L_0x010d:
            r9.mDoingMove = r3
            return r1
        L_0x0110:
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r11 = r9.mAmPmCirclesView
            int r10 = r11.getIsTouchingAmOrPm(r10, r0)
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r11 = r9.mAmPmCirclesView
            r11.setAmOrPmPressed(r5)
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r11 = r9.mAmPmCirclesView
            r11.invalidate()
            int r11 = r9.mIsTouchingAmOrPm
            if (r10 != r11) goto L_0x0151
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r11 = r9.mAmPmCirclesView
            r11.setAmOrPm(r10)
            int r11 = r9.getIsCurrentlyAmOrPm()
            if (r11 == r10) goto L_0x0151
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = new com.wdullaer.materialdatetimepicker.time.Timepoint
            com.wdullaer.materialdatetimepicker.time.Timepoint r11 = r9.mCurrentTime
            r10.<init>(r11)
            int r11 = r9.mIsTouchingAmOrPm
            if (r11 != 0) goto L_0x013e
            r10.setAM()
            goto L_0x0143
        L_0x013e:
            if (r11 != r1) goto L_0x0143
            r10.setPM()
        L_0x0143:
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = r9.roundToValidTime(r10, r3)
            r9.reselectSelector(r10, r3, r3)
            r9.mCurrentTime = r10
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$OnValueSelectedListener r11 = r9.mListener
            r11.onValueSelected(r10)
        L_0x0151:
            r9.mIsTouchingAmOrPm = r5
        L_0x0153:
            return r3
        L_0x0154:
            boolean r11 = r9.mInputEnabled
            if (r11 != 0) goto L_0x0159
            return r1
        L_0x0159:
            r9.mDownX = r10
            r9.mDownY = r0
            r9.mLastValueSelected = r4
            r9.mDoingMove = r3
            r9.mDoingTouch = r1
            boolean r11 = r9.mIs24HourMode
            if (r11 != 0) goto L_0x017a
            com.wdullaer.materialdatetimepicker.time.TimePickerController r11 = r9.mController
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$Version r11 = r11.getVersion()
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$Version r4 = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version.VERSION_1
            if (r11 != r4) goto L_0x017a
            com.wdullaer.materialdatetimepicker.time.AmPmCirclesView r11 = r9.mAmPmCirclesView
            int r11 = r11.getIsTouchingAmOrPm(r10, r0)
            r9.mIsTouchingAmOrPm = r11
            goto L_0x017c
        L_0x017a:
            r9.mIsTouchingAmOrPm = r5
        L_0x017c:
            int r11 = r9.mIsTouchingAmOrPm
            if (r11 == 0) goto L_0x01c0
            if (r11 != r1) goto L_0x0183
            goto L_0x01c0
        L_0x0183:
            android.view.accessibility.AccessibilityManager r11 = r9.mAccessibilityManager
            boolean r11 = r11.isTouchExplorationEnabled()
            int r10 = r9.getDegreesFromCoords(r10, r0, r11, r2)
            r9.mDownDegrees = r10
            int r10 = r9.mDownDegrees
            r11 = r2[r3]
            boolean r11 = r11.booleanValue()
            com.wdullaer.materialdatetimepicker.time.Timepoint r10 = r9.getTimeFromDegrees(r10, r11, r3)
            com.wdullaer.materialdatetimepicker.time.TimePickerController r11 = r9.mController
            int r0 = r9.getCurrentItemShowing()
            boolean r10 = r11.isOutOfRange(r10, r0)
            if (r10 == 0) goto L_0x01a9
            r9.mDownDegrees = r5
        L_0x01a9:
            int r10 = r9.mDownDegrees
            if (r10 == r5) goto L_0x01d4
            com.wdullaer.materialdatetimepicker.time.TimePickerController r10 = r9.mController
            r10.tryVibrate()
            android.os.Handler r10 = r9.mHandler
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$5 r11 = new com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$5
            r11.<init>(r2)
            int r0 = r9.TAP_TIMEOUT
            long r2 = (long) r0
            r10.postDelayed(r11, r2)
            goto L_0x01d4
        L_0x01c0:
            com.wdullaer.materialdatetimepicker.time.TimePickerController r10 = r9.mController
            r10.tryVibrate()
            r9.mDownDegrees = r5
            android.os.Handler r10 = r9.mHandler
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$4 r11 = new com.wdullaer.materialdatetimepicker.time.RadialPickerLayout$4
            r11.<init>()
            int r0 = r9.TAP_TIMEOUT
            long r2 = (long) r0
            r10.postDelayed(r11, r2)
        L_0x01d4:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wdullaer.materialdatetimepicker.time.RadialPickerLayout.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public boolean trySettingInputEnabled(boolean z) {
        int i = 0;
        if (this.mDoingTouch && !z) {
            return false;
        }
        this.mInputEnabled = z;
        View view = this.mGrayBox;
        if (z) {
            i = 4;
        }
        view.setVisibility(i);
        return true;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (VERSION.SDK_INT >= 21) {
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_FORWARD);
        } else if (VERSION.SDK_INT >= 16) {
            accessibilityNodeInfo.addAction(4096);
            accessibilityNodeInfo.addAction(8192);
        } else {
            accessibilityNodeInfo.addAction(4096);
            accessibilityNodeInfo.addAction(8192);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        accessibilityEvent.getText().clear();
        Calendar instance = Calendar.getInstance();
        instance.set(10, getHours());
        instance.set(12, getMinutes());
        instance.set(13, getSeconds());
        accessibilityEvent.getText().add(DateUtils.formatDateTime(getContext(), instance.getTimeInMillis(), this.mIs24HourMode ? 129 : 1));
        return true;
    }

    public boolean performAccessibilityAction(int i, Bundle bundle) {
        int i2;
        Timepoint timepoint;
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        int i3 = VERSION.SDK_INT;
        int i4 = 0;
        int i5 = i == 4096 ? 1 : i == 8192 ? -1 : 0;
        if (i5 == 0) {
            return false;
        }
        int currentlyShowingValue = getCurrentlyShowingValue();
        int currentItemShowing = getCurrentItemShowing();
        int i6 = 6;
        if (currentItemShowing == 0) {
            i6 = 30;
            currentlyShowingValue %= 12;
        } else if (!(currentItemShowing == 1 || currentItemShowing == 2)) {
            i6 = 0;
        }
        int snapOnly30s = snapOnly30s(currentlyShowingValue * i6, i5) / i6;
        if (currentItemShowing != 0) {
            i2 = 55;
        } else if (this.mIs24HourMode) {
            i2 = 23;
        } else {
            i2 = 12;
            i4 = 1;
        }
        if (snapOnly30s > i2) {
            snapOnly30s = i4;
        } else if (snapOnly30s < i4) {
            snapOnly30s = i2;
        }
        if (currentItemShowing == 0) {
            timepoint = new Timepoint(snapOnly30s, this.mCurrentTime.getMinute(), this.mCurrentTime.getSecond());
        } else if (currentItemShowing == 1) {
            timepoint = new Timepoint(this.mCurrentTime.getHour(), snapOnly30s, this.mCurrentTime.getSecond());
        } else if (currentItemShowing != 2) {
            timepoint = this.mCurrentTime;
        } else {
            timepoint = new Timepoint(this.mCurrentTime.getHour(), this.mCurrentTime.getMinute(), snapOnly30s);
        }
        setItem(currentItemShowing, timepoint);
        this.mListener.onValueSelected(timepoint);
        return true;
    }
}
