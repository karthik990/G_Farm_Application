package com.wdullaer.materialdatetimepicker.time;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.C5272Utils;
import com.wdullaer.materialdatetimepicker.HapticFeedbackController;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout.OnValueSelectedListener;
import com.wdullaer.materialdatetimepicker.time.Timepoint.TYPE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class TimePickerDialog extends DialogFragment implements OnValueSelectedListener, TimePickerController {

    /* renamed from: AM */
    public static final int f3682AM = 0;
    public static final int HOUR_INDEX = 0;
    private static final String KEY_ACCENT = "accent";
    private static final String KEY_CANCEL_COLOR = "cancel_color";
    private static final String KEY_CANCEL_RESID = "cancel_resid";
    private static final String KEY_CANCEL_STRING = "cancel_string";
    private static final String KEY_CURRENT_ITEM_SHOWING = "current_item_showing";
    private static final String KEY_DISMISS = "dismiss";
    private static final String KEY_ENABLE_MINUTES = "enable_minutes";
    private static final String KEY_ENABLE_SECONDS = "enable_seconds";
    private static final String KEY_INITIAL_TIME = "initial_time";
    private static final String KEY_IN_KB_MODE = "in_kb_mode";
    private static final String KEY_IS_24_HOUR_VIEW = "is_24_hour_view";
    private static final String KEY_MAX_TIME = "max_time";
    private static final String KEY_MIN_TIME = "min_time";
    private static final String KEY_OK_COLOR = "ok_color";
    private static final String KEY_OK_RESID = "ok_resid";
    private static final String KEY_OK_STRING = "ok_string";
    private static final String KEY_SELECTABLE_TIMES = "selectable_times";
    private static final String KEY_THEME_DARK = "theme_dark";
    private static final String KEY_THEME_DARK_CHANGED = "theme_dark_changed";
    private static final String KEY_TITLE = "dialog_title";
    private static final String KEY_TYPED_TIMES = "typed_times";
    private static final String KEY_VERSION = "version";
    private static final String KEY_VIBRATE = "vibrate";
    public static final int MINUTE_INDEX = 1;

    /* renamed from: PM */
    public static final int f3683PM = 1;
    private static final int PULSE_ANIMATOR_DELAY = 300;
    public static final int SECOND_INDEX = 2;
    private static final String TAG = "TimePickerDialog";
    private int mAccentColor = -1;
    private boolean mAllowAutoAdvance;
    private int mAmKeyCode;
    private View mAmPmLayout;
    private String mAmText;
    private TextView mAmTextView;
    private OnTimeSetListener mCallback;
    private Button mCancelButton;
    private int mCancelColor;
    private int mCancelResid;
    private String mCancelString;
    private String mDeletedKeyFormat;
    private boolean mDismissOnPause;
    private String mDoublePlaceholderText;
    private boolean mEnableMinutes;
    private boolean mEnableSeconds;
    private HapticFeedbackController mHapticFeedbackController;
    private String mHourPickerDescription;
    private TextView mHourSpaceView;
    private TextView mHourView;
    /* access modifiers changed from: private */
    public boolean mInKbMode;
    private Timepoint mInitialTime;
    private boolean mIs24HourMode;
    private Node mLegalTimesTree;
    private Timepoint mMaxTime;
    private Timepoint mMinTime;
    private String mMinutePickerDescription;
    private TextView mMinuteSpaceView;
    private TextView mMinuteView;
    private Button mOkButton;
    private int mOkColor;
    private int mOkResid;
    private String mOkString;
    private OnCancelListener mOnCancelListener;
    private OnDismissListener mOnDismissListener;
    private char mPlaceholderText;
    private int mPmKeyCode;
    private String mPmText;
    private TextView mPmTextView;
    private String mSecondPickerDescription;
    private TextView mSecondSpaceView;
    private TextView mSecondView;
    private String mSelectHours;
    private String mSelectMinutes;
    private String mSelectSeconds;
    private Timepoint[] mSelectableTimes;
    private int mSelectedColor;
    private boolean mThemeDark;
    private boolean mThemeDarkChanged;
    /* access modifiers changed from: private */
    public RadialPickerLayout mTimePicker;
    private String mTitle;
    private ArrayList<Integer> mTypedTimes;
    private int mUnselectedColor;
    private Version mVersion;
    private boolean mVibrate;

    private class KeyboardListener implements OnKeyListener {
        private KeyboardListener() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 1) {
                return TimePickerDialog.this.processKeyUp(i);
            }
            return false;
        }
    }

    private static class Node {
        private ArrayList<Node> mChildren = new ArrayList<>();
        private int[] mLegalKeys;

        public Node(int... iArr) {
            this.mLegalKeys = iArr;
        }

        public void addChild(Node node) {
            this.mChildren.add(node);
        }

        public boolean containsKey(int i) {
            for (int i2 : this.mLegalKeys) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public Node canReach(int i) {
            ArrayList<Node> arrayList = this.mChildren;
            if (arrayList == null) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Node node = (Node) it.next();
                if (node.containsKey(i)) {
                    return node;
                }
            }
            return null;
        }
    }

    public interface OnTimeSetListener {
        void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3);
    }

    public enum Version {
        VERSION_1,
        VERSION_2
    }

    private static int getValFromKeyCode(int i) {
        switch (i) {
            case 7:
                return 0;
            case 8:
                return 1;
            case 9:
                return 2;
            case 10:
                return 3;
            case 11:
                return 4;
            case 12:
                return 5;
            case 13:
                return 6;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
            default:
                return -1;
        }
    }

    public static TimePickerDialog newInstance(OnTimeSetListener onTimeSetListener, int i, int i2, int i3, boolean z) {
        TimePickerDialog timePickerDialog = new TimePickerDialog();
        timePickerDialog.initialize(onTimeSetListener, i, i2, i3, z);
        return timePickerDialog;
    }

    public static TimePickerDialog newInstance(OnTimeSetListener onTimeSetListener, int i, int i2, boolean z) {
        return newInstance(onTimeSetListener, i, i2, 0, z);
    }

    public static TimePickerDialog newInstance(OnTimeSetListener onTimeSetListener, boolean z) {
        Calendar instance = Calendar.getInstance();
        return newInstance(onTimeSetListener, instance.get(11), instance.get(12), z);
    }

    public void initialize(OnTimeSetListener onTimeSetListener, int i, int i2, int i3, boolean z) {
        this.mCallback = onTimeSetListener;
        this.mInitialTime = new Timepoint(i, i2, i3);
        this.mIs24HourMode = z;
        this.mInKbMode = false;
        this.mTitle = "";
        this.mThemeDark = false;
        this.mThemeDarkChanged = false;
        this.mAccentColor = -1;
        this.mVibrate = true;
        this.mDismissOnPause = false;
        this.mEnableSeconds = false;
        this.mEnableMinutes = true;
        this.mOkResid = C5266R.C5270string.mdtp_ok;
        this.mOkColor = -1;
        this.mCancelResid = C5266R.C5270string.mdtp_cancel;
        this.mCancelColor = -1;
        this.mVersion = VERSION.SDK_INT < 23 ? Version.VERSION_1 : Version.VERSION_2;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setThemeDark(boolean z) {
        this.mThemeDark = z;
        this.mThemeDarkChanged = true;
    }

    public void setAccentColor(String str) {
        this.mAccentColor = Color.parseColor(str);
    }

    public void setAccentColor(int i) {
        this.mAccentColor = Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    public void setOkColor(String str) {
        this.mOkColor = Color.parseColor(str);
    }

    public void setOkColor(int i) {
        this.mOkColor = Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    public void setCancelColor(String str) {
        this.mCancelColor = Color.parseColor(str);
    }

    public void setCancelColor(int i) {
        this.mCancelColor = Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    public boolean isThemeDark() {
        return this.mThemeDark;
    }

    public boolean is24HourMode() {
        return this.mIs24HourMode;
    }

    public int getAccentColor() {
        return this.mAccentColor;
    }

    public void vibrate(boolean z) {
        this.mVibrate = z;
    }

    public void dismissOnPause(boolean z) {
        this.mDismissOnPause = z;
    }

    public void enableSeconds(boolean z) {
        if (z) {
            this.mEnableMinutes = true;
        }
        this.mEnableSeconds = z;
    }

    public void enableMinutes(boolean z) {
        if (!z) {
            this.mEnableSeconds = false;
        }
        this.mEnableMinutes = z;
    }

    public void setMinTime(int i, int i2, int i3) {
        setMinTime(new Timepoint(i, i2, i3));
    }

    public void setMinTime(Timepoint timepoint) {
        Timepoint timepoint2 = this.mMaxTime;
        if (timepoint2 == null || timepoint.compareTo(timepoint2) <= 0) {
            this.mMinTime = timepoint;
            return;
        }
        throw new IllegalArgumentException("Minimum time must be smaller than the maximum time");
    }

    public void setMaxTime(int i, int i2, int i3) {
        setMaxTime(new Timepoint(i, i2, i3));
    }

    public void setMaxTime(Timepoint timepoint) {
        Timepoint timepoint2 = this.mMinTime;
        if (timepoint2 == null || timepoint.compareTo(timepoint2) >= 0) {
            this.mMaxTime = timepoint;
            return;
        }
        throw new IllegalArgumentException("Maximum time must be greater than the minimum time");
    }

    public void setSelectableTimes(Timepoint[] timepointArr) {
        this.mSelectableTimes = timepointArr;
        Arrays.sort(this.mSelectableTimes);
    }

    public void setTimeInterval(int i, int i2, int i3) {
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        while (i4 < 24) {
            int i5 = 0;
            while (i5 < 60) {
                int i6 = 0;
                while (i6 < 60) {
                    arrayList.add(new Timepoint(i4, i5, i6));
                    i6 += i3;
                }
                i5 += i2;
            }
            i4 += i;
        }
        setSelectableTimes((Timepoint[]) arrayList.toArray(new Timepoint[arrayList.size()]));
    }

    public void setTimeInterval(int i, int i2) {
        setTimeInterval(i, i2, 1);
    }

    public void setTimeInterval(int i) {
        setTimeInterval(i, 1);
    }

    public void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
        this.mCallback = onTimeSetListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setStartTime(int i, int i2, int i3) {
        this.mInitialTime = roundToNearest(new Timepoint(i, i2, i3));
        this.mInKbMode = false;
    }

    public void setStartTime(int i, int i2) {
        setStartTime(i, i2, 0);
    }

    public void setOkText(String str) {
        this.mOkString = str;
    }

    public void setOkText(int i) {
        this.mOkString = null;
        this.mOkResid = i;
    }

    public void setCancelText(String str) {
        this.mCancelString = str;
    }

    public void setCancelText(int i) {
        this.mCancelString = null;
        this.mCancelResid = i;
    }

    public void setVersion(Version version) {
        this.mVersion = version;
    }

    public Version getVersion() {
        return this.mVersion;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            String str = KEY_INITIAL_TIME;
            if (bundle.containsKey(str)) {
                String str2 = KEY_IS_24_HOUR_VIEW;
                if (bundle.containsKey(str2)) {
                    this.mInitialTime = (Timepoint) bundle.getParcelable(str);
                    this.mIs24HourMode = bundle.getBoolean(str2);
                    this.mInKbMode = bundle.getBoolean(KEY_IN_KB_MODE);
                    this.mTitle = bundle.getString(KEY_TITLE);
                    this.mThemeDark = bundle.getBoolean(KEY_THEME_DARK);
                    this.mThemeDarkChanged = bundle.getBoolean(KEY_THEME_DARK_CHANGED);
                    this.mAccentColor = bundle.getInt(KEY_ACCENT);
                    this.mVibrate = bundle.getBoolean(KEY_VIBRATE);
                    this.mDismissOnPause = bundle.getBoolean(KEY_DISMISS);
                    this.mSelectableTimes = (Timepoint[]) bundle.getParcelableArray(KEY_SELECTABLE_TIMES);
                    this.mMinTime = (Timepoint) bundle.getParcelable(KEY_MIN_TIME);
                    this.mMaxTime = (Timepoint) bundle.getParcelable(KEY_MAX_TIME);
                    this.mEnableSeconds = bundle.getBoolean(KEY_ENABLE_SECONDS);
                    this.mEnableMinutes = bundle.getBoolean(KEY_ENABLE_MINUTES);
                    this.mOkResid = bundle.getInt(KEY_OK_RESID);
                    this.mOkString = bundle.getString(KEY_OK_STRING);
                    this.mOkColor = bundle.getInt(KEY_OK_COLOR);
                    this.mCancelResid = bundle.getInt(KEY_CANCEL_RESID);
                    this.mCancelString = bundle.getString(KEY_CANCEL_STRING);
                    this.mCancelColor = bundle.getInt(KEY_CANCEL_COLOR);
                    this.mVersion = (Version) bundle.getSerializable("version");
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x048f  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0495  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x04a0  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x04a6  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x04b3  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x04da  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x04e8  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x01b6  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x01ea  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x01fd  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0206  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x020c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0247  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0276  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x027c  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0364  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0430  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0441  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x045c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View onCreateView(android.view.LayoutInflater r12, android.view.ViewGroup r13, android.os.Bundle r14) {
        /*
            r11 = this;
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$Version r0 = r11.mVersion
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$Version r1 = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version.VERSION_1
            if (r0 != r1) goto L_0x0009
            int r0 = com.wdullaer.materialdatetimepicker.C5266R.layout.mdtp_time_picker_dialog
            goto L_0x000b
        L_0x0009:
            int r0 = com.wdullaer.materialdatetimepicker.C5266R.layout.mdtp_time_picker_dialog_v2
        L_0x000b:
            r1 = 0
            android.view.View r12 = r12.inflate(r0, r13, r1)
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$KeyboardListener r13 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$KeyboardListener
            r0 = 0
            r13.<init>()
            int r0 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_time_picker_dialog
            android.view.View r0 = r12.findViewById(r0)
            r0.setOnKeyListener(r13)
            int r0 = r11.mAccentColor
            r2 = -1
            if (r0 != r2) goto L_0x002e
            android.app.Activity r0 = r11.getActivity()
            int r0 = com.wdullaer.materialdatetimepicker.C5272Utils.getAccentColorFromThemeIfAvailable(r0)
            r11.mAccentColor = r0
        L_0x002e:
            boolean r0 = r11.mThemeDarkChanged
            if (r0 != 0) goto L_0x003e
            android.app.Activity r0 = r11.getActivity()
            boolean r3 = r11.mThemeDark
            boolean r0 = com.wdullaer.materialdatetimepicker.C5272Utils.isDarkTheme(r0, r3)
            r11.mThemeDark = r0
        L_0x003e:
            android.content.res.Resources r0 = r11.getResources()
            android.app.Activity r3 = r11.getActivity()
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_hour_picker_description
            java.lang.String r4 = r0.getString(r4)
            r11.mHourPickerDescription = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_select_hours
            java.lang.String r4 = r0.getString(r4)
            r11.mSelectHours = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_minute_picker_description
            java.lang.String r4 = r0.getString(r4)
            r11.mMinutePickerDescription = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_select_minutes
            java.lang.String r4 = r0.getString(r4)
            r11.mSelectMinutes = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_second_picker_description
            java.lang.String r4 = r0.getString(r4)
            r11.mSecondPickerDescription = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_select_seconds
            java.lang.String r4 = r0.getString(r4)
            r11.mSelectSeconds = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5267color.mdtp_white
            int r4 = androidx.core.content.ContextCompat.getColor(r3, r4)
            r11.mSelectedColor = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5267color.mdtp_accent_color_focused
            int r4 = androidx.core.content.ContextCompat.getColor(r3, r4)
            r11.mUnselectedColor = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_hours
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mHourView = r4
            android.widget.TextView r4 = r11.mHourView
            r4.setOnKeyListener(r13)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_hour_space
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mHourSpaceView = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_minutes_space
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mMinuteSpaceView = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_minutes
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mMinuteView = r4
            android.widget.TextView r4 = r11.mMinuteView
            r4.setOnKeyListener(r13)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_seconds_space
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mSecondSpaceView = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_seconds
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mSecondView = r4
            android.widget.TextView r4 = r11.mSecondView
            r4.setOnKeyListener(r13)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_am_label
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mAmTextView = r4
            android.widget.TextView r4 = r11.mAmTextView
            r4.setOnKeyListener(r13)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_pm_label
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11.mPmTextView = r4
            android.widget.TextView r4 = r11.mPmTextView
            r4.setOnKeyListener(r13)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_ampm_layout
            android.view.View r4 = r12.findViewById(r4)
            r11.mAmPmLayout = r4
            java.text.DateFormatSymbols r4 = new java.text.DateFormatSymbols
            r4.<init>()
            java.lang.String[] r4 = r4.getAmPmStrings()
            r5 = r4[r1]
            r11.mAmText = r5
            r5 = 1
            r4 = r4[r5]
            r11.mPmText = r4
            com.wdullaer.materialdatetimepicker.HapticFeedbackController r4 = new com.wdullaer.materialdatetimepicker.HapticFeedbackController
            android.app.Activity r6 = r11.getActivity()
            r4.<init>(r6)
            r11.mHapticFeedbackController = r4
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r4 = r11.mTimePicker
            if (r4 == 0) goto L_0x012f
            com.wdullaer.materialdatetimepicker.time.Timepoint r6 = new com.wdullaer.materialdatetimepicker.time.Timepoint
            int r4 = r4.getHours()
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r7 = r11.mTimePicker
            int r7 = r7.getMinutes()
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r8 = r11.mTimePicker
            int r8 = r8.getSeconds()
            r6.<init>(r4, r7, r8)
            r11.mInitialTime = r6
        L_0x012f:
            com.wdullaer.materialdatetimepicker.time.Timepoint r4 = r11.mInitialTime
            com.wdullaer.materialdatetimepicker.time.Timepoint r4 = r11.roundToNearest(r4)
            r11.mInitialTime = r4
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_time_picker
            android.view.View r4 = r12.findViewById(r4)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r4 = (com.wdullaer.materialdatetimepicker.time.RadialPickerLayout) r4
            r11.mTimePicker = r4
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r4 = r11.mTimePicker
            r4.setOnValueSelectedListener(r11)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r4 = r11.mTimePicker
            r4.setOnKeyListener(r13)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r4 = r11.mTimePicker
            android.app.Activity r6 = r11.getActivity()
            com.wdullaer.materialdatetimepicker.time.Timepoint r7 = r11.mInitialTime
            boolean r8 = r11.mIs24HourMode
            r4.initialize(r6, r11, r7, r8)
            if (r14 == 0) goto L_0x0167
            java.lang.String r4 = "current_item_showing"
            boolean r6 = r14.containsKey(r4)
            if (r6 == 0) goto L_0x0167
            int r4 = r14.getInt(r4)
            goto L_0x0168
        L_0x0167:
            r4 = 0
        L_0x0168:
            r11.setCurrentItemShowing(r4, r1, r5, r5)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r4 = r11.mTimePicker
            r4.invalidate()
            android.widget.TextView r4 = r11.mHourView
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$1 r6 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$1
            r6.<init>()
            r4.setOnClickListener(r6)
            android.widget.TextView r4 = r11.mMinuteView
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$2 r6 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$2
            r6.<init>()
            r4.setOnClickListener(r6)
            android.widget.TextView r4 = r11.mSecondView
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$3 r6 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$3
            r6.<init>()
            r4.setOnClickListener(r6)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_ok
            android.view.View r4 = r12.findViewById(r4)
            android.widget.Button r4 = (android.widget.Button) r4
            r11.mOkButton = r4
            android.widget.Button r4 = r11.mOkButton
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$4 r6 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$4
            r6.<init>()
            r4.setOnClickListener(r6)
            android.widget.Button r4 = r11.mOkButton
            r4.setOnKeyListener(r13)
            android.widget.Button r13 = r11.mOkButton
            java.lang.String r4 = "Roboto-Medium"
            android.graphics.Typeface r6 = com.wdullaer.materialdatetimepicker.TypefaceHelper.get(r3, r4)
            r13.setTypeface(r6)
            java.lang.String r13 = r11.mOkString
            if (r13 == 0) goto L_0x01bc
            android.widget.Button r6 = r11.mOkButton
            r6.setText(r13)
            goto L_0x01c3
        L_0x01bc:
            android.widget.Button r13 = r11.mOkButton
            int r6 = r11.mOkResid
            r13.setText(r6)
        L_0x01c3:
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_cancel
            android.view.View r13 = r12.findViewById(r13)
            android.widget.Button r13 = (android.widget.Button) r13
            r11.mCancelButton = r13
            android.widget.Button r13 = r11.mCancelButton
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$5 r6 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$5
            r6.<init>()
            r13.setOnClickListener(r6)
            android.widget.Button r13 = r11.mCancelButton
            android.graphics.Typeface r4 = com.wdullaer.materialdatetimepicker.TypefaceHelper.get(r3, r4)
            r13.setTypeface(r4)
            java.lang.String r13 = r11.mCancelString
            if (r13 == 0) goto L_0x01ea
            android.widget.Button r4 = r11.mCancelButton
            r4.setText(r13)
            goto L_0x01f1
        L_0x01ea:
            android.widget.Button r13 = r11.mCancelButton
            int r4 = r11.mCancelResid
            r13.setText(r4)
        L_0x01f1:
            android.widget.Button r13 = r11.mCancelButton
            boolean r4 = r11.isCancelable()
            r6 = 8
            if (r4 == 0) goto L_0x01fd
            r4 = 0
            goto L_0x01ff
        L_0x01fd:
            r4 = 8
        L_0x01ff:
            r13.setVisibility(r4)
            boolean r13 = r11.mIs24HourMode
            if (r13 == 0) goto L_0x020c
            android.view.View r13 = r11.mAmPmLayout
            r13.setVisibility(r6)
            goto L_0x0243
        L_0x020c:
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$6 r13 = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog$6
            r13.<init>()
            android.widget.TextView r4 = r11.mAmTextView
            r4.setVisibility(r6)
            android.widget.TextView r4 = r11.mPmTextView
            r4.setVisibility(r1)
            android.view.View r4 = r11.mAmPmLayout
            r4.setOnClickListener(r13)
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$Version r13 = r11.mVersion
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog$Version r4 = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version.VERSION_2
            if (r13 != r4) goto L_0x0239
            android.widget.TextView r13 = r11.mAmTextView
            java.lang.String r4 = r11.mAmText
            r13.setText(r4)
            android.widget.TextView r13 = r11.mPmTextView
            java.lang.String r4 = r11.mPmText
            r13.setText(r4)
            android.widget.TextView r13 = r11.mAmTextView
            r13.setVisibility(r1)
        L_0x0239:
            com.wdullaer.materialdatetimepicker.time.Timepoint r13 = r11.mInitialTime
            boolean r13 = r13.isAM()
            r13 = r13 ^ r5
            r11.updateAmPmDisplay(r13)
        L_0x0243:
            boolean r13 = r11.mEnableSeconds
            if (r13 != 0) goto L_0x0255
            android.widget.TextView r13 = r11.mSecondView
            r13.setVisibility(r6)
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator_seconds
            android.view.View r13 = r12.findViewById(r13)
            r13.setVisibility(r6)
        L_0x0255:
            boolean r13 = r11.mEnableMinutes
            if (r13 != 0) goto L_0x0267
            android.widget.TextView r13 = r11.mMinuteSpaceView
            r13.setVisibility(r6)
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r13 = r12.findViewById(r13)
            r13.setVisibility(r6)
        L_0x0267:
            android.content.res.Resources r13 = r11.getResources()
            android.content.res.Configuration r13 = r13.getConfiguration()
            int r13 = r13.orientation
            r4 = 2
            if (r13 != r4) goto L_0x0276
            r13 = 1
            goto L_0x0277
        L_0x0276:
            r13 = 0
        L_0x0277:
            r7 = 13
            r8 = -2
            if (r13 == 0) goto L_0x0364
            boolean r13 = r11.mEnableMinutes
            r9 = 14
            if (r13 != 0) goto L_0x02ad
            boolean r13 = r11.mEnableSeconds
            if (r13 != 0) goto L_0x02ad
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            int r7 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_center_view
            r13.addRule(r4, r7)
            r13.addRule(r9)
            android.widget.TextView r4 = r11.mHourSpaceView
            r4.setLayoutParams(r13)
            boolean r13 = r11.mIs24HourMode
            if (r13 == 0) goto L_0x03f0
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_hour_space
            r13.addRule(r5, r4)
            android.view.View r4 = r11.mAmPmLayout
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x02ad:
            boolean r13 = r11.mEnableSeconds
            if (r13 != 0) goto L_0x02cf
            boolean r13 = r11.mIs24HourMode
            if (r13 == 0) goto L_0x02cf
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r9)
            int r7 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_center_view
            r13.addRule(r4, r7)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x02cf:
            boolean r13 = r11.mEnableSeconds
            r10 = 3
            if (r13 != 0) goto L_0x0300
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r9)
            int r9 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_center_view
            r13.addRule(r4, r9)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r4.setLayoutParams(r13)
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r7)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_center_view
            r13.addRule(r10, r4)
            android.view.View r4 = r11.mAmPmLayout
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x0300:
            boolean r13 = r11.mIs24HourMode
            if (r13 == 0) goto L_0x032b
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r9)
            int r9 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_seconds_space
            r13.addRule(r4, r9)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r4.setLayoutParams(r13)
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r7)
            android.widget.TextView r4 = r11.mSecondSpaceView
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x032b:
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r7)
            android.widget.TextView r7 = r11.mSecondSpaceView
            r7.setLayoutParams(r13)
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r9)
            int r7 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_seconds_space
            r13.addRule(r4, r7)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r4.setLayoutParams(r13)
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r9)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_seconds_space
            r13.addRule(r10, r4)
            android.view.View r4 = r11.mAmPmLayout
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x0364:
            boolean r13 = r11.mIs24HourMode
            if (r13 == 0) goto L_0x0384
            boolean r13 = r11.mEnableSeconds
            if (r13 != 0) goto L_0x0384
            boolean r13 = r11.mEnableMinutes
            if (r13 == 0) goto L_0x0384
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r7)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x0384:
            boolean r13 = r11.mEnableMinutes
            if (r13 != 0) goto L_0x03b3
            boolean r13 = r11.mEnableSeconds
            if (r13 != 0) goto L_0x03b3
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r7)
            android.widget.TextView r4 = r11.mHourSpaceView
            r4.setLayoutParams(r13)
            boolean r13 = r11.mIs24HourMode
            if (r13 != 0) goto L_0x03f0
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_hour_space
            r13.addRule(r5, r4)
            r4 = 4
            int r7 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_hour_space
            r13.addRule(r4, r7)
            android.view.View r4 = r11.mAmPmLayout
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x03b3:
            boolean r13 = r11.mEnableSeconds
            if (r13 == 0) goto L_0x03f0
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_separator
            android.view.View r13 = r12.findViewById(r13)
            android.widget.RelativeLayout$LayoutParams r4 = new android.widget.RelativeLayout$LayoutParams
            r4.<init>(r8, r8)
            int r9 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_minutes_space
            r4.addRule(r1, r9)
            r9 = 15
            r4.addRule(r9, r2)
            r13.setLayoutParams(r4)
            boolean r13 = r11.mIs24HourMode
            if (r13 != 0) goto L_0x03e1
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            r13.addRule(r7)
            android.widget.TextView r4 = r11.mMinuteSpaceView
            r4.setLayoutParams(r13)
            goto L_0x03f0
        L_0x03e1:
            android.widget.RelativeLayout$LayoutParams r13 = new android.widget.RelativeLayout$LayoutParams
            r13.<init>(r8, r8)
            int r4 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_center_view
            r13.addRule(r5, r4)
            android.widget.TextView r4 = r11.mMinuteSpaceView
            r4.setLayoutParams(r13)
        L_0x03f0:
            r11.mAllowAutoAdvance = r5
            com.wdullaer.materialdatetimepicker.time.Timepoint r13 = r11.mInitialTime
            int r13 = r13.getHour()
            r11.setHour(r13, r5)
            com.wdullaer.materialdatetimepicker.time.Timepoint r13 = r11.mInitialTime
            int r13 = r13.getMinute()
            r11.setMinute(r13)
            com.wdullaer.materialdatetimepicker.time.Timepoint r13 = r11.mInitialTime
            int r13 = r13.getSecond()
            r11.setSecond(r13)
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_time_placeholder
            java.lang.String r13 = r0.getString(r13)
            r11.mDoublePlaceholderText = r13
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5270string.mdtp_deleted_key
            java.lang.String r13 = r0.getString(r13)
            r11.mDeletedKeyFormat = r13
            java.lang.String r13 = r11.mDoublePlaceholderText
            char r13 = r13.charAt(r1)
            r11.mPlaceholderText = r13
            r11.mPmKeyCode = r2
            r11.mAmKeyCode = r2
            r11.generateLegalTimesTree()
            boolean r13 = r11.mInKbMode
            if (r13 == 0) goto L_0x0441
            java.lang.String r13 = "typed_times"
            java.util.ArrayList r13 = r14.getIntegerArrayList(r13)
            r11.mTypedTimes = r13
            r11.tryStartingKbMode(r2)
            android.widget.TextView r13 = r11.mHourView
            r13.invalidate()
            goto L_0x044c
        L_0x0441:
            java.util.ArrayList<java.lang.Integer> r13 = r11.mTypedTimes
            if (r13 != 0) goto L_0x044c
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r11.mTypedTimes = r13
        L_0x044c:
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_time_picker_header
            android.view.View r13 = r12.findViewById(r13)
            android.widget.TextView r13 = (android.widget.TextView) r13
            java.lang.String r14 = r11.mTitle
            boolean r14 = r14.isEmpty()
            if (r14 != 0) goto L_0x046c
            r13.setVisibility(r1)
            java.lang.String r14 = r11.mTitle
            java.util.Locale r0 = java.util.Locale.getDefault()
            java.lang.String r14 = r14.toUpperCase(r0)
            r13.setText(r14)
        L_0x046c:
            int r14 = r11.mAccentColor
            int r14 = com.wdullaer.materialdatetimepicker.C5272Utils.darkenColor(r14)
            r13.setBackgroundColor(r14)
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_time_display_background
            android.view.View r13 = r12.findViewById(r13)
            int r14 = r11.mAccentColor
            r13.setBackgroundColor(r14)
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_time_display
            android.view.View r13 = r12.findViewById(r13)
            int r14 = r11.mAccentColor
            r13.setBackgroundColor(r14)
            int r13 = r11.mOkColor
            if (r13 == r2) goto L_0x0495
            android.widget.Button r14 = r11.mOkButton
            r14.setTextColor(r13)
            goto L_0x049c
        L_0x0495:
            android.widget.Button r13 = r11.mOkButton
            int r14 = r11.mAccentColor
            r13.setTextColor(r14)
        L_0x049c:
            int r13 = r11.mCancelColor
            if (r13 == r2) goto L_0x04a6
            android.widget.Button r14 = r11.mCancelButton
            r14.setTextColor(r13)
            goto L_0x04ad
        L_0x04a6:
            android.widget.Button r13 = r11.mCancelButton
            int r14 = r11.mAccentColor
            r13.setTextColor(r14)
        L_0x04ad:
            android.app.Dialog r13 = r11.getDialog()
            if (r13 != 0) goto L_0x04bc
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_done_background
            android.view.View r13 = r12.findViewById(r13)
            r13.setVisibility(r6)
        L_0x04bc:
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5267color.mdtp_circle_background
            int r13 = androidx.core.content.ContextCompat.getColor(r3, r13)
            int r14 = com.wdullaer.materialdatetimepicker.C5266R.C5267color.mdtp_background_color
            int r14 = androidx.core.content.ContextCompat.getColor(r3, r14)
            int r0 = com.wdullaer.materialdatetimepicker.C5266R.C5267color.mdtp_light_gray
            int r0 = androidx.core.content.ContextCompat.getColor(r3, r0)
            int r1 = com.wdullaer.materialdatetimepicker.C5266R.C5267color.mdtp_light_gray
            int r1 = androidx.core.content.ContextCompat.getColor(r3, r1)
            com.wdullaer.materialdatetimepicker.time.RadialPickerLayout r2 = r11.mTimePicker
            boolean r3 = r11.mThemeDark
            if (r3 == 0) goto L_0x04db
            r13 = r1
        L_0x04db:
            r2.setBackgroundColor(r13)
            int r13 = com.wdullaer.materialdatetimepicker.C5266R.C5269id.mdtp_time_picker_dialog
            android.view.View r13 = r12.findViewById(r13)
            boolean r1 = r11.mThemeDark
            if (r1 == 0) goto L_0x04e9
            r14 = r0
        L_0x04e9:
            r13.setBackgroundColor(r14)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wdullaer.materialdatetimepicker.time.TimePickerDialog.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle):android.view.View");
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            viewGroup.addView(onCreateView(getActivity().getLayoutInflater(), viewGroup, null));
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Dialog onCreateDialog = super.onCreateDialog(bundle);
        onCreateDialog.requestWindowFeature(1);
        return onCreateDialog;
    }

    public void onResume() {
        super.onResume();
        this.mHapticFeedbackController.start();
    }

    public void onPause() {
        super.onPause();
        this.mHapticFeedbackController.stop();
        if (this.mDismissOnPause) {
            dismiss();
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        OnCancelListener onCancelListener = this.mOnCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialogInterface);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void tryVibrate() {
        if (this.mVibrate) {
            this.mHapticFeedbackController.tryVibrate();
        }
    }

    private void updateAmPmDisplay(int i) {
        if (this.mVersion == Version.VERSION_2) {
            if (i == 0) {
                this.mAmTextView.setTextColor(this.mSelectedColor);
                this.mPmTextView.setTextColor(this.mUnselectedColor);
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mAmText);
                return;
            }
            this.mAmTextView.setTextColor(this.mUnselectedColor);
            this.mPmTextView.setTextColor(this.mSelectedColor);
            C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mPmText);
        } else if (i == 0) {
            this.mPmTextView.setText(this.mAmText);
            C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mAmText);
            this.mPmTextView.setContentDescription(this.mAmText);
        } else if (i == 1) {
            this.mPmTextView.setText(this.mPmText);
            C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mPmText);
            this.mPmTextView.setContentDescription(this.mPmText);
        } else {
            this.mPmTextView.setText(this.mDoublePlaceholderText);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        RadialPickerLayout radialPickerLayout = this.mTimePicker;
        if (radialPickerLayout != null) {
            bundle.putParcelable(KEY_INITIAL_TIME, radialPickerLayout.getTime());
            bundle.putBoolean(KEY_IS_24_HOUR_VIEW, this.mIs24HourMode);
            bundle.putInt(KEY_CURRENT_ITEM_SHOWING, this.mTimePicker.getCurrentItemShowing());
            bundle.putBoolean(KEY_IN_KB_MODE, this.mInKbMode);
            if (this.mInKbMode) {
                bundle.putIntegerArrayList(KEY_TYPED_TIMES, this.mTypedTimes);
            }
            bundle.putString(KEY_TITLE, this.mTitle);
            bundle.putBoolean(KEY_THEME_DARK, this.mThemeDark);
            bundle.putBoolean(KEY_THEME_DARK_CHANGED, this.mThemeDarkChanged);
            bundle.putInt(KEY_ACCENT, this.mAccentColor);
            bundle.putBoolean(KEY_VIBRATE, this.mVibrate);
            bundle.putBoolean(KEY_DISMISS, this.mDismissOnPause);
            bundle.putParcelableArray(KEY_SELECTABLE_TIMES, this.mSelectableTimes);
            bundle.putParcelable(KEY_MIN_TIME, this.mMinTime);
            bundle.putParcelable(KEY_MAX_TIME, this.mMaxTime);
            bundle.putBoolean(KEY_ENABLE_SECONDS, this.mEnableSeconds);
            bundle.putBoolean(KEY_ENABLE_MINUTES, this.mEnableMinutes);
            bundle.putInt(KEY_OK_RESID, this.mOkResid);
            bundle.putString(KEY_OK_STRING, this.mOkString);
            bundle.putInt(KEY_OK_COLOR, this.mOkColor);
            bundle.putInt(KEY_CANCEL_RESID, this.mCancelResid);
            bundle.putString(KEY_CANCEL_STRING, this.mCancelString);
            bundle.putInt(KEY_CANCEL_COLOR, this.mCancelColor);
            bundle.putSerializable("version", this.mVersion);
        }
    }

    public void onValueSelected(Timepoint timepoint) {
        setHour(timepoint.getHour(), false);
        RadialPickerLayout radialPickerLayout = this.mTimePicker;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mHourPickerDescription);
        String str = ": ";
        sb.append(str);
        sb.append(timepoint.getHour());
        radialPickerLayout.setContentDescription(sb.toString());
        setMinute(timepoint.getMinute());
        RadialPickerLayout radialPickerLayout2 = this.mTimePicker;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mMinutePickerDescription);
        sb2.append(str);
        sb2.append(timepoint.getMinute());
        radialPickerLayout2.setContentDescription(sb2.toString());
        setSecond(timepoint.getSecond());
        RadialPickerLayout radialPickerLayout3 = this.mTimePicker;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mSecondPickerDescription);
        sb3.append(str);
        sb3.append(timepoint.getSecond());
        radialPickerLayout3.setContentDescription(sb3.toString());
        if (!this.mIs24HourMode) {
            updateAmPmDisplay(timepoint.isAM() ^ true ? 1 : 0);
        }
    }

    public void advancePicker(int i) {
        if (this.mAllowAutoAdvance) {
            String str = ". ";
            if (i == 0 && this.mEnableMinutes) {
                setCurrentItemShowing(1, true, true, false);
                StringBuilder sb = new StringBuilder();
                sb.append(this.mSelectHours);
                sb.append(str);
                sb.append(this.mTimePicker.getMinutes());
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, sb.toString());
            } else if (i == 1 && this.mEnableSeconds) {
                setCurrentItemShowing(2, true, true, false);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mSelectMinutes);
                sb2.append(str);
                sb2.append(this.mTimePicker.getSeconds());
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, sb2.toString());
            }
        }
    }

    public void enablePicker() {
        if (!isTypedTimeFullyLegal()) {
            this.mTypedTimes.clear();
        }
        finishKbMode(true);
    }

    public boolean isOutOfRange(Timepoint timepoint) {
        Timepoint timepoint2 = this.mMinTime;
        if (timepoint2 != null && timepoint2.compareTo(timepoint) > 0) {
            return true;
        }
        Timepoint timepoint3 = this.mMaxTime;
        if (timepoint3 != null && timepoint3.compareTo(timepoint) < 0) {
            return true;
        }
        Timepoint[] timepointArr = this.mSelectableTimes;
        if (timepointArr != null) {
            return !Arrays.asList(timepointArr).contains(timepoint);
        }
        return false;
    }

    public boolean isOutOfRange(Timepoint timepoint, int i) {
        if (timepoint == null) {
            return false;
        }
        if (i == 0) {
            Timepoint timepoint2 = this.mMinTime;
            if (timepoint2 != null && timepoint2.getHour() > timepoint.getHour()) {
                return true;
            }
            Timepoint timepoint3 = this.mMaxTime;
            if (timepoint3 != null && timepoint3.getHour() + 1 <= timepoint.getHour()) {
                return true;
            }
            Timepoint[] timepointArr = this.mSelectableTimes;
            if (timepointArr == null) {
                return false;
            }
            for (Timepoint hour : timepointArr) {
                if (hour.getHour() == timepoint.getHour()) {
                    return false;
                }
            }
            return true;
        } else if (i != 1) {
            return isOutOfRange(timepoint);
        } else {
            Timepoint timepoint4 = this.mMinTime;
            if (timepoint4 != null && new Timepoint(timepoint4.getHour(), this.mMinTime.getMinute()).compareTo(timepoint) > 0) {
                return true;
            }
            Timepoint timepoint5 = this.mMaxTime;
            if (timepoint5 != null && new Timepoint(timepoint5.getHour(), this.mMaxTime.getMinute(), 59).compareTo(timepoint) < 0) {
                return true;
            }
            Timepoint[] timepointArr2 = this.mSelectableTimes;
            if (timepointArr2 == null) {
                return false;
            }
            for (Timepoint timepoint6 : timepointArr2) {
                if (timepoint6.getHour() == timepoint.getHour() && timepoint6.getMinute() == timepoint.getMinute()) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean isAmDisabled() {
        Timepoint timepoint = new Timepoint(12);
        Timepoint timepoint2 = this.mMinTime;
        if (timepoint2 != null && timepoint2.compareTo(timepoint) > 0) {
            return true;
        }
        Timepoint[] timepointArr = this.mSelectableTimes;
        if (timepointArr == null) {
            return false;
        }
        for (Timepoint compareTo : timepointArr) {
            if (compareTo.compareTo(timepoint) < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isPmDisabled() {
        Timepoint timepoint = new Timepoint(12);
        Timepoint timepoint2 = this.mMaxTime;
        if (timepoint2 != null && timepoint2.compareTo(timepoint) < 0) {
            return true;
        }
        Timepoint[] timepointArr = this.mSelectableTimes;
        if (timepointArr == null) {
            return false;
        }
        for (Timepoint compareTo : timepointArr) {
            if (compareTo.compareTo(timepoint) >= 0) {
                return false;
            }
        }
        return true;
    }

    private Timepoint roundToNearest(Timepoint timepoint) {
        return roundToNearest(timepoint, null);
    }

    public Timepoint roundToNearest(Timepoint timepoint, TYPE type) {
        Timepoint timepoint2 = this.mMinTime;
        if (timepoint2 != null && timepoint2.compareTo(timepoint) > 0) {
            return this.mMinTime;
        }
        Timepoint timepoint3 = this.mMaxTime;
        if (timepoint3 != null && timepoint3.compareTo(timepoint) < 0) {
            return this.mMaxTime;
        }
        Timepoint[] timepointArr = this.mSelectableTimes;
        if (timepointArr == null) {
            return timepoint;
        }
        Timepoint timepoint4 = timepoint;
        int i = Integer.MAX_VALUE;
        for (Timepoint timepoint5 : timepointArr) {
            if ((type != TYPE.HOUR || timepoint5.getHour() == timepoint.getHour()) && (type != TYPE.MINUTE || timepoint5.getHour() == timepoint.getHour() || timepoint5.getMinute() == timepoint.getMinute())) {
                if (type != TYPE.SECOND) {
                    int abs = Math.abs(timepoint5.compareTo(timepoint));
                    if (abs >= i) {
                        break;
                    }
                    timepoint4 = timepoint5;
                    i = abs;
                } else {
                    return timepoint;
                }
            }
        }
        return timepoint4;
    }

    private void setHour(int i, boolean z) {
        String str = "%d";
        if (this.mIs24HourMode) {
            str = "%02d";
        } else {
            i %= 12;
            if (i == 0) {
                i = 12;
            }
        }
        String format = String.format(str, new Object[]{Integer.valueOf(i)});
        this.mHourView.setText(format);
        this.mHourSpaceView.setText(format);
        if (z) {
            C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, format);
        }
    }

    private void setMinute(int i) {
        if (i == 60) {
            i = 0;
        }
        String format = String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(i)});
        C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, format);
        this.mMinuteView.setText(format);
        this.mMinuteSpaceView.setText(format);
    }

    private void setSecond(int i) {
        if (i == 60) {
            i = 0;
        }
        String format = String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(i)});
        C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, format);
        this.mSecondView.setText(format);
        this.mSecondSpaceView.setText(format);
    }

    /* access modifiers changed from: private */
    public void setCurrentItemShowing(int i, boolean z, boolean z2, boolean z3) {
        TextView textView;
        this.mTimePicker.setCurrentItemShowing(i, z);
        String str = ": ";
        if (i == 0) {
            int hours = this.mTimePicker.getHours();
            if (!this.mIs24HourMode) {
                hours %= 12;
            }
            RadialPickerLayout radialPickerLayout = this.mTimePicker;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mHourPickerDescription);
            sb.append(str);
            sb.append(hours);
            radialPickerLayout.setContentDescription(sb.toString());
            if (z3) {
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mSelectHours);
            }
            textView = this.mHourView;
        } else if (i != 1) {
            int seconds = this.mTimePicker.getSeconds();
            RadialPickerLayout radialPickerLayout2 = this.mTimePicker;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mSecondPickerDescription);
            sb2.append(str);
            sb2.append(seconds);
            radialPickerLayout2.setContentDescription(sb2.toString());
            if (z3) {
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mSelectSeconds);
            }
            textView = this.mSecondView;
        } else {
            int minutes = this.mTimePicker.getMinutes();
            RadialPickerLayout radialPickerLayout3 = this.mTimePicker;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.mMinutePickerDescription);
            sb3.append(str);
            sb3.append(minutes);
            radialPickerLayout3.setContentDescription(sb3.toString());
            if (z3) {
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mSelectMinutes);
            }
            textView = this.mMinuteView;
        }
        int i2 = i == 0 ? this.mSelectedColor : this.mUnselectedColor;
        int i3 = i == 1 ? this.mSelectedColor : this.mUnselectedColor;
        int i4 = i == 2 ? this.mSelectedColor : this.mUnselectedColor;
        this.mHourView.setTextColor(i2);
        this.mMinuteView.setTextColor(i3);
        this.mSecondView.setTextColor(i4);
        ObjectAnimator pulseAnimator = C5272Utils.getPulseAnimator(textView, 0.85f, 1.1f);
        if (z2) {
            pulseAnimator.setStartDelay(300);
        }
        pulseAnimator.start();
    }

    /* access modifiers changed from: private */
    public boolean processKeyUp(int i) {
        String str;
        if (i == 111 || i == 4) {
            if (isCancelable()) {
                dismiss();
            }
            return true;
        }
        if (i == 61) {
            if (this.mInKbMode) {
                if (isTypedTimeFullyLegal()) {
                    finishKbMode(true);
                }
                return true;
            }
        } else if (i == 66) {
            if (this.mInKbMode) {
                if (!isTypedTimeFullyLegal()) {
                    return true;
                }
                finishKbMode(false);
            }
            OnTimeSetListener onTimeSetListener = this.mCallback;
            if (onTimeSetListener != null) {
                onTimeSetListener.onTimeSet(this, this.mTimePicker.getHours(), this.mTimePicker.getMinutes(), this.mTimePicker.getSeconds());
            }
            dismiss();
            return true;
        } else if (i == 67) {
            if (this.mInKbMode && !this.mTypedTimes.isEmpty()) {
                int deleteLastTypedKey = deleteLastTypedKey();
                if (deleteLastTypedKey == getAmOrPmKeyCode(0)) {
                    str = this.mAmText;
                } else if (deleteLastTypedKey == getAmOrPmKeyCode(1)) {
                    str = this.mPmText;
                } else {
                    str = String.format("%d", new Object[]{Integer.valueOf(getValFromKeyCode(deleteLastTypedKey))});
                }
                C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, String.format(this.mDeletedKeyFormat, new Object[]{str}));
                updateDisplay(true);
            }
        } else if (i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || (!this.mIs24HourMode && (i == getAmOrPmKeyCode(0) || i == getAmOrPmKeyCode(1)))) {
            if (this.mInKbMode) {
                if (addKeyIfLegal(i)) {
                    updateDisplay(false);
                }
                return true;
            } else if (this.mTimePicker == null) {
                Log.e(TAG, "Unable to initiate keyboard mode, TimePicker was null.");
                return true;
            } else {
                this.mTypedTimes.clear();
                tryStartingKbMode(i);
                return true;
            }
        }
        return false;
    }

    private void tryStartingKbMode(int i) {
        if (!this.mTimePicker.trySettingInputEnabled(false)) {
            return;
        }
        if (i == -1 || addKeyIfLegal(i)) {
            this.mInKbMode = true;
            this.mOkButton.setEnabled(false);
            updateDisplay(false);
        }
    }

    private boolean addKeyIfLegal(int i) {
        int i2 = (!this.mEnableMinutes || this.mEnableSeconds) ? 6 : 4;
        if (!this.mEnableMinutes && !this.mEnableSeconds) {
            i2 = 2;
        }
        if ((this.mIs24HourMode && this.mTypedTimes.size() == i2) || (!this.mIs24HourMode && isTypedTimeFullyLegal())) {
            return false;
        }
        this.mTypedTimes.add(Integer.valueOf(i));
        if (!isTypedTimeLegalSoFar()) {
            deleteLastTypedKey();
            return false;
        }
        C5272Utils.tryAccessibilityAnnounce(this.mTimePicker, String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(getValFromKeyCode(i))}));
        if (isTypedTimeFullyLegal()) {
            if (!this.mIs24HourMode && this.mTypedTimes.size() <= i2 - 1) {
                ArrayList<Integer> arrayList = this.mTypedTimes;
                arrayList.add(arrayList.size() - 1, Integer.valueOf(7));
                ArrayList<Integer> arrayList2 = this.mTypedTimes;
                arrayList2.add(arrayList2.size() - 1, Integer.valueOf(7));
            }
            this.mOkButton.setEnabled(true);
        }
        return true;
    }

    private boolean isTypedTimeLegalSoFar() {
        Node node = this.mLegalTimesTree;
        Iterator it = this.mTypedTimes.iterator();
        while (it.hasNext()) {
            node = node.canReach(((Integer) it.next()).intValue());
            if (node == null) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isTypedTimeFullyLegal() {
        boolean z = false;
        if (this.mIs24HourMode) {
            int[] enteredTime = getEnteredTime(null);
            if (enteredTime[0] >= 0 && enteredTime[1] >= 0 && enteredTime[1] < 60 && enteredTime[2] >= 0 && enteredTime[2] < 60) {
                z = true;
            }
            return z;
        }
        if (this.mTypedTimes.contains(Integer.valueOf(getAmOrPmKeyCode(0))) || this.mTypedTimes.contains(Integer.valueOf(getAmOrPmKeyCode(1)))) {
            z = true;
        }
        return z;
    }

    private int deleteLastTypedKey() {
        ArrayList<Integer> arrayList = this.mTypedTimes;
        int intValue = ((Integer) arrayList.remove(arrayList.size() - 1)).intValue();
        if (!isTypedTimeFullyLegal()) {
            this.mOkButton.setEnabled(false);
        }
        return intValue;
    }

    /* access modifiers changed from: private */
    public void finishKbMode(boolean z) {
        this.mInKbMode = false;
        if (!this.mTypedTimes.isEmpty()) {
            int[] enteredTime = getEnteredTime(null);
            this.mTimePicker.setTime(new Timepoint(enteredTime[0], enteredTime[1], enteredTime[2]));
            if (!this.mIs24HourMode) {
                this.mTimePicker.setAmOrPm(enteredTime[3]);
            }
            this.mTypedTimes.clear();
        }
        if (z) {
            updateDisplay(false);
            this.mTimePicker.trySettingInputEnabled(true);
        }
    }

    private void updateDisplay(boolean z) {
        String str;
        String str2;
        String str3;
        int i = 0;
        Boolean valueOf = Boolean.valueOf(false);
        if (z || !this.mTypedTimes.isEmpty()) {
            Boolean[] boolArr = {valueOf, valueOf, valueOf};
            int[] enteredTime = getEnteredTime(boolArr);
            String str4 = "%02d";
            String str5 = "%2d";
            String str6 = boolArr[0].booleanValue() ? str4 : str5;
            String str7 = boolArr[1].booleanValue() ? str4 : str5;
            if (!boolArr[1].booleanValue()) {
                str4 = str5;
            }
            if (enteredTime[0] == -1) {
                str = this.mDoublePlaceholderText;
            } else {
                str = String.format(str6, new Object[]{Integer.valueOf(enteredTime[0])}).replace(' ', this.mPlaceholderText);
            }
            if (enteredTime[1] == -1) {
                str2 = this.mDoublePlaceholderText;
            } else {
                str2 = String.format(str7, new Object[]{Integer.valueOf(enteredTime[1])}).replace(' ', this.mPlaceholderText);
            }
            if (enteredTime[2] == -1) {
                str3 = this.mDoublePlaceholderText;
            } else {
                str3 = String.format(str4, new Object[]{Integer.valueOf(enteredTime[1])}).replace(' ', this.mPlaceholderText);
            }
            this.mHourView.setText(str);
            this.mHourSpaceView.setText(str);
            this.mHourView.setTextColor(this.mUnselectedColor);
            this.mMinuteView.setText(str2);
            this.mMinuteSpaceView.setText(str2);
            this.mMinuteView.setTextColor(this.mUnselectedColor);
            this.mSecondView.setText(str3);
            this.mSecondSpaceView.setText(str3);
            this.mSecondView.setTextColor(this.mUnselectedColor);
            if (!this.mIs24HourMode) {
                updateAmPmDisplay(enteredTime[3]);
                return;
            }
            return;
        }
        int hours = this.mTimePicker.getHours();
        int minutes = this.mTimePicker.getMinutes();
        int seconds = this.mTimePicker.getSeconds();
        setHour(hours, true);
        setMinute(minutes);
        setSecond(seconds);
        if (!this.mIs24HourMode) {
            if (hours >= 12) {
                i = 1;
            }
            updateAmPmDisplay(i);
        }
        setCurrentItemShowing(this.mTimePicker.getCurrentItemShowing(), true, true, true);
        this.mOkButton.setEnabled(true);
    }

    private int[] getEnteredTime(Boolean[] boolArr) {
        int i;
        int i2;
        boolean z = this.mIs24HourMode;
        Boolean valueOf = Boolean.valueOf(true);
        if (z || !isTypedTimeFullyLegal()) {
            i2 = 1;
            i = -1;
        } else {
            ArrayList<Integer> arrayList = this.mTypedTimes;
            int intValue = ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
            int i3 = intValue == getAmOrPmKeyCode(0) ? 0 : intValue == getAmOrPmKeyCode(1) ? 1 : -1;
            i = i3;
            i2 = 2;
        }
        int i4 = this.mEnableSeconds ? 2 : 0;
        int i5 = -1;
        int i6 = -1;
        int i7 = 0;
        for (int i8 = i2; i8 <= this.mTypedTimes.size(); i8++) {
            ArrayList<Integer> arrayList2 = this.mTypedTimes;
            int valFromKeyCode = getValFromKeyCode(((Integer) arrayList2.get(arrayList2.size() - i8)).intValue());
            if (this.mEnableSeconds) {
                if (i8 == i2) {
                    i7 = valFromKeyCode;
                } else if (i8 == i2 + 1) {
                    i7 += valFromKeyCode * 10;
                    if (boolArr != null && valFromKeyCode == 0) {
                        boolArr[2] = valueOf;
                    }
                }
            }
            if (this.mEnableMinutes) {
                int i9 = i2 + i4;
                if (i8 == i9) {
                    i6 = valFromKeyCode;
                } else if (i8 == i9 + 1) {
                    i6 += valFromKeyCode * 10;
                    if (boolArr != null && valFromKeyCode == 0) {
                        boolArr[1] = valueOf;
                    }
                } else if (i8 != i9 + 2) {
                    if (i8 == i9 + 3) {
                        i5 += valFromKeyCode * 10;
                        if (boolArr != null && valFromKeyCode == 0) {
                            boolArr[0] = valueOf;
                        }
                    }
                }
            } else {
                int i10 = i2 + i4;
                if (i8 != i10) {
                    if (i8 == i10 + 1) {
                        i5 += valFromKeyCode * 10;
                        if (boolArr != null && valFromKeyCode == 0) {
                            boolArr[0] = valueOf;
                        }
                    }
                }
            }
            i5 = valFromKeyCode;
        }
        return new int[]{i5, i6, i7, i};
    }

    private int getAmOrPmKeyCode(int i) {
        if (this.mAmKeyCode == -1 || this.mPmKeyCode == -1) {
            KeyCharacterMap load = KeyCharacterMap.load(-1);
            int i2 = 0;
            while (true) {
                if (i2 >= Math.max(this.mAmText.length(), this.mPmText.length())) {
                    break;
                }
                char charAt = this.mAmText.toLowerCase(Locale.getDefault()).charAt(i2);
                char charAt2 = this.mPmText.toLowerCase(Locale.getDefault()).charAt(i2);
                if (charAt != charAt2) {
                    KeyEvent[] events = load.getEvents(new char[]{charAt, charAt2});
                    if (events == null || events.length != 4) {
                        Log.e(TAG, "Unable to find keycodes for AM and PM.");
                    } else {
                        this.mAmKeyCode = events[0].getKeyCode();
                        this.mPmKeyCode = events[2].getKeyCode();
                    }
                } else {
                    i2++;
                }
            }
        }
        if (i == 0) {
            return this.mAmKeyCode;
        }
        if (i == 1) {
            return this.mPmKeyCode;
        }
        return -1;
    }

    private void generateLegalTimesTree() {
        this.mLegalTimesTree = new Node(new int[0]);
        if (!this.mEnableMinutes && this.mIs24HourMode) {
            Node node = new Node(7, 8);
            this.mLegalTimesTree.addChild(node);
            node.addChild(new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
            Node node2 = new Node(9);
            this.mLegalTimesTree.addChild(node2);
            node2.addChild(new Node(7, 8, 9, 10));
        } else if (this.mEnableMinutes || this.mIs24HourMode) {
            if (this.mIs24HourMode) {
                Node node3 = new Node(7, 8, 9, 10, 11, 12);
                Node node4 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
                node3.addChild(node4);
                if (this.mEnableSeconds) {
                    Node node5 = new Node(7, 8, 9, 10, 11, 12);
                    node5.addChild(new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
                    node4.addChild(node5);
                }
                Node node6 = new Node(7, 8);
                this.mLegalTimesTree.addChild(node6);
                Node node7 = new Node(7, 8, 9, 10, 11, 12);
                node6.addChild(node7);
                node7.addChild(node3);
                node7.addChild(new Node(13, 14, 15, 16));
                Node node8 = new Node(13, 14, 15, 16);
                node6.addChild(node8);
                node8.addChild(node3);
                Node node9 = new Node(9);
                this.mLegalTimesTree.addChild(node9);
                Node node10 = new Node(7, 8, 9, 10);
                node9.addChild(node10);
                node10.addChild(node3);
                Node node11 = new Node(11, 12);
                node9.addChild(node11);
                node11.addChild(node4);
                Node node12 = new Node(10, 11, 12, 13, 14, 15, 16);
                this.mLegalTimesTree.addChild(node12);
                node12.addChild(node3);
            } else {
                Node node13 = new Node(getAmOrPmKeyCode(0), getAmOrPmKeyCode(1));
                Node node14 = new Node(7, 8, 9, 10, 11, 12);
                Node node15 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
                node15.addChild(node13);
                node14.addChild(node15);
                Node node16 = new Node(8);
                this.mLegalTimesTree.addChild(node16);
                node16.addChild(node13);
                Node node17 = new Node(7, 8, 9);
                node16.addChild(node17);
                node17.addChild(node13);
                Node node18 = new Node(7, 8, 9, 10, 11, 12);
                node17.addChild(node18);
                node18.addChild(node13);
                Node node19 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
                node18.addChild(node19);
                node19.addChild(node13);
                if (this.mEnableSeconds) {
                    node19.addChild(node14);
                }
                Node node20 = new Node(13, 14, 15, 16);
                node17.addChild(node20);
                node20.addChild(node13);
                if (this.mEnableSeconds) {
                    node20.addChild(node14);
                }
                Node node21 = new Node(10, 11, 12);
                node16.addChild(node21);
                Node node22 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
                node21.addChild(node22);
                node22.addChild(node13);
                if (this.mEnableSeconds) {
                    node22.addChild(node14);
                }
                Node node23 = new Node(9, 10, 11, 12, 13, 14, 15, 16);
                this.mLegalTimesTree.addChild(node23);
                node23.addChild(node13);
                Node node24 = new Node(7, 8, 9, 10, 11, 12);
                node23.addChild(node24);
                Node node25 = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
                node24.addChild(node25);
                node25.addChild(node13);
                if (this.mEnableSeconds) {
                    node25.addChild(node14);
                }
            }
        } else {
            Node node26 = new Node(getAmOrPmKeyCode(0), getAmOrPmKeyCode(1));
            Node node27 = new Node(8);
            this.mLegalTimesTree.addChild(node27);
            node27.addChild(node26);
            Node node28 = new Node(7, 8, 9);
            node27.addChild(node28);
            node28.addChild(node26);
            Node node29 = new Node(9, 10, 11, 12, 13, 14, 15, 16);
            this.mLegalTimesTree.addChild(node29);
            node29.addChild(node26);
        }
    }

    public void notifyOnDateListener() {
        OnTimeSetListener onTimeSetListener = this.mCallback;
        if (onTimeSetListener != null) {
            onTimeSetListener.onTimeSet(this, this.mTimePicker.getHours(), this.mTimePicker.getMinutes(), this.mTimePicker.getSeconds());
        }
    }

    public Timepoint getSelectedTime() {
        return this.mTimePicker.getTime();
    }
}
