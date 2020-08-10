package com.afollestad.materialdialogs.prefs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.preference.MultiSelectListPreference;
import android.preference.Preference.BaseSavedState;
import android.util.AttributeSet;
import android.view.View;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackMultiChoice;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import java.util.ArrayList;
import java.util.HashSet;

public class MaterialMultiSelectListPreference extends MultiSelectListPreference {
    private Context context;
    private MaterialDialog mDialog;

    /* renamed from: com.afollestad.materialdialogs.prefs.MaterialMultiSelectListPreference$3 */
    static /* synthetic */ class C08683 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$DialogAction = new int[DialogAction.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.afollestad.materialdialogs.DialogAction[] r0 = com.afollestad.materialdialogs.DialogAction.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$afollestad$materialdialogs$DialogAction = r0
                int[] r0 = $SwitchMap$com$afollestad$materialdialogs$DialogAction     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.afollestad.materialdialogs.DialogAction r1 = com.afollestad.materialdialogs.DialogAction.NEUTRAL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$afollestad$materialdialogs$DialogAction     // Catch:{ NoSuchFieldError -> 0x001f }
                com.afollestad.materialdialogs.DialogAction r1 = com.afollestad.materialdialogs.DialogAction.NEGATIVE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.prefs.MaterialMultiSelectListPreference.C08683.<clinit>():void");
        }
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Bundle dialogBundle;
        boolean isDialogShowing;

        SavedState(Parcel parcel) {
            super(parcel);
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.isDialogShowing = z;
            this.dialogBundle = parcel.readBundle();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isDialogShowing ? 1 : 0);
            parcel.writeBundle(this.dialogBundle);
        }
    }

    public MaterialMultiSelectListPreference(Context context2) {
        super(context2);
        init(context2, null);
    }

    public MaterialMultiSelectListPreference(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2, attributeSet);
    }

    public MaterialMultiSelectListPreference(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2, attributeSet);
    }

    public MaterialMultiSelectListPreference(Context context2, AttributeSet attributeSet, int i, int i2) {
        super(context2, attributeSet, i, i2);
        init(context2, attributeSet);
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        super.setEntries(charSequenceArr);
        MaterialDialog materialDialog = this.mDialog;
        if (materialDialog != null) {
            materialDialog.setItems(charSequenceArr);
        }
    }

    private void init(Context context2, AttributeSet attributeSet) {
        this.context = context2;
        PrefUtil.setLayoutResource(context2, this, attributeSet);
        if (VERSION.SDK_INT <= 10) {
            setWidgetLayoutResource(0);
        }
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    /* access modifiers changed from: protected */
    public void showDialog(Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        for (String str : getValues()) {
            if (findIndexOfValue(str) >= 0) {
                arrayList.add(Integer.valueOf(findIndexOfValue(str)));
            }
        }
        Builder dismissListener = new Builder(this.context).title(getDialogTitle()).icon(getDialogIcon()).negativeText(getNegativeButtonText()).positiveText(getPositiveButtonText()).onAny(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                int i = C08683.$SwitchMap$com$afollestad$materialdialogs$DialogAction[dialogAction.ordinal()];
                if (i == 1) {
                    MaterialMultiSelectListPreference.this.onClick(materialDialog, -3);
                } else if (i != 2) {
                    MaterialMultiSelectListPreference.this.onClick(materialDialog, -1);
                } else {
                    MaterialMultiSelectListPreference.this.onClick(materialDialog, -2);
                }
            }
        }).items(getEntries()).itemsCallbackMultiChoice((Integer[]) arrayList.toArray(new Integer[arrayList.size()]), new ListCallbackMultiChoice() {
            public boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
                MaterialMultiSelectListPreference.this.onClick(null, -1);
                materialDialog.dismiss();
                HashSet hashSet = new HashSet();
                for (Integer intValue : numArr) {
                    hashSet.add(MaterialMultiSelectListPreference.this.getEntryValues()[intValue.intValue()].toString());
                }
                if (MaterialMultiSelectListPreference.this.callChangeListener(hashSet)) {
                    MaterialMultiSelectListPreference.this.setValues(hashSet);
                }
                return true;
            }
        }).dismissListener(this);
        View onCreateDialogView = onCreateDialogView();
        if (onCreateDialogView != null) {
            onBindDialogView(onCreateDialogView);
            dismissListener.customView(onCreateDialogView, false);
        } else {
            dismissListener.content(getDialogMessage());
        }
        PrefUtil.registerOnActivityDestroyListener(this, this);
        this.mDialog = dismissListener.build();
        if (bundle != null) {
            this.mDialog.onRestoreInstanceState(bundle);
        }
        this.mDialog.show();
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        PrefUtil.unregisterOnActivityDestroyListener(this, this);
    }

    public void onActivityDestroy() {
        super.onActivityDestroy();
        MaterialDialog materialDialog = this.mDialog;
        if (materialDialog != null && materialDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Dialog dialog = getDialog();
        if (dialog == null || !dialog.isShowing()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.isDialogShowing = true;
        savedState.dialogBundle = dialog.onSaveInstanceState();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.isDialogShowing) {
            showDialog(savedState.dialogBundle);
        }
    }
}
