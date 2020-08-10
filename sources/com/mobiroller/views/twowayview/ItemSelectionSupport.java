package com.mobiroller.views.twowayview;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Checkable;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.mobiroller.mobi942763453128.R;

public class ItemSelectionSupport {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    public static final int INVALID_POSITION = -1;
    private static final String STATE_KEY_CHECKED_COUNT = "checkedCount";
    private static final String STATE_KEY_CHECKED_ID_STATES = "checkedIdStates";
    private static final String STATE_KEY_CHECKED_STATES = "checkedStates";
    private static final String STATE_KEY_CHOICE_MODE = "choiceMode";
    /* access modifiers changed from: private */
    public int mCheckedCount;
    /* access modifiers changed from: private */
    public CheckedIdStates mCheckedIdStates;
    /* access modifiers changed from: private */
    public CheckedStates mCheckedStates;
    /* access modifiers changed from: private */
    public ChoiceMode mChoiceMode = ChoiceMode.NONE;
    /* access modifiers changed from: private */
    public final RecyclerView mRecyclerView;
    private final TouchListener mTouchListener;

    private static class CheckedIdStates extends LongSparseArray<Integer> implements Parcelable {
        public static final Creator<CheckedIdStates> CREATOR = new Creator<CheckedIdStates>() {
            public CheckedIdStates createFromParcel(Parcel parcel) {
                return new CheckedIdStates(parcel);
            }

            public CheckedIdStates[] newArray(int i) {
                return new CheckedIdStates[i];
            }
        };

        public int describeContents() {
            return 0;
        }

        public CheckedIdStates() {
        }

        private CheckedIdStates(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt > 0) {
                for (int i = 0; i < readInt; i++) {
                    put(parcel.readLong(), Integer.valueOf(parcel.readInt()));
                }
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            int size = size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeLong(keyAt(i2));
                parcel.writeInt(((Integer) valueAt(i2)).intValue());
            }
        }
    }

    private static class CheckedStates extends SparseBooleanArray implements Parcelable {
        public static final Creator<CheckedStates> CREATOR = new Creator<CheckedStates>() {
            public CheckedStates createFromParcel(Parcel parcel) {
                return new CheckedStates(parcel);
            }

            public CheckedStates[] newArray(int i) {
                return new CheckedStates[i];
            }
        };
        private static final int FALSE = 0;
        private static final int TRUE = 1;

        public int describeContents() {
            return 0;
        }

        public CheckedStates() {
        }

        private CheckedStates(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt > 0) {
                for (int i = 0; i < readInt; i++) {
                    int readInt2 = parcel.readInt();
                    boolean z = true;
                    if (parcel.readInt() != 1) {
                        z = false;
                    }
                    put(readInt2, z);
                }
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            int size = size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(keyAt(i2));
                parcel.writeInt(valueAt(i2) ? 1 : 0);
            }
        }
    }

    public enum ChoiceMode {
        NONE,
        SINGLE,
        MULTIPLE
    }

    private class TouchListener extends ClickItemTouchListener {
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        /* access modifiers changed from: 0000 */
        public boolean performItemLongClick(RecyclerView recyclerView, View view, int i, long j) {
            return true;
        }

        TouchListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        /* access modifiers changed from: 0000 */
        public boolean performItemClick(RecyclerView recyclerView, View view, int i, long j) {
            Adapter adapter = ItemSelectionSupport.this.mRecyclerView.getAdapter();
            boolean z = true;
            if (ItemSelectionSupport.this.mChoiceMode == ChoiceMode.MULTIPLE) {
                boolean z2 = !ItemSelectionSupport.this.mCheckedStates.get(i, false);
                ItemSelectionSupport.this.mCheckedStates.put(i, z2);
                if (ItemSelectionSupport.this.mCheckedIdStates != null && adapter.hasStableIds()) {
                    if (z2) {
                        ItemSelectionSupport.this.mCheckedIdStates.put(adapter.getItemId(i), Integer.valueOf(i));
                    } else {
                        ItemSelectionSupport.this.mCheckedIdStates.delete(adapter.getItemId(i));
                    }
                }
                if (z2) {
                    ItemSelectionSupport.this.mCheckedCount = ItemSelectionSupport.this.mCheckedCount + 1;
                } else {
                    ItemSelectionSupport.this.mCheckedCount = ItemSelectionSupport.this.mCheckedCount - 1;
                }
            } else if (ItemSelectionSupport.this.mChoiceMode != ChoiceMode.SINGLE) {
                z = false;
            } else if (!ItemSelectionSupport.this.mCheckedStates.get(i, false)) {
                ItemSelectionSupport.this.mCheckedStates.clear();
                ItemSelectionSupport.this.mCheckedStates.put(i, true);
                if (ItemSelectionSupport.this.mCheckedIdStates != null && adapter.hasStableIds()) {
                    ItemSelectionSupport.this.mCheckedIdStates.clear();
                    ItemSelectionSupport.this.mCheckedIdStates.put(adapter.getItemId(i), Integer.valueOf(i));
                }
                ItemSelectionSupport.this.mCheckedCount = 1;
            } else if (ItemSelectionSupport.this.mCheckedStates.size() == 0 || !ItemSelectionSupport.this.mCheckedStates.valueAt(0)) {
                ItemSelectionSupport.this.mCheckedCount = 0;
            }
            if (z) {
                ItemSelectionSupport.this.updateOnScreenCheckedViews();
            }
            return false;
        }
    }

    private ItemSelectionSupport(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mTouchListener = new TouchListener(recyclerView);
        recyclerView.addOnItemTouchListener(this.mTouchListener);
    }

    /* access modifiers changed from: private */
    public void updateOnScreenCheckedViews() {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRecyclerView.getChildAt(i);
            setViewChecked(childAt, this.mCheckedStates.get(this.mRecyclerView.getChildPosition(childAt)));
        }
    }

    public int getCheckedItemCount() {
        return this.mCheckedCount;
    }

    public boolean isItemChecked(int i) {
        if (this.mChoiceMode != ChoiceMode.NONE) {
            CheckedStates checkedStates = this.mCheckedStates;
            if (checkedStates != null) {
                return checkedStates.get(i);
            }
        }
        return false;
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == ChoiceMode.SINGLE) {
            CheckedStates checkedStates = this.mCheckedStates;
            if (checkedStates != null && checkedStates.size() == 1) {
                return this.mCheckedStates.keyAt(0);
            }
        }
        return -1;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != ChoiceMode.NONE) {
            return this.mCheckedStates;
        }
        return null;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == ChoiceMode.NONE || this.mCheckedIdStates == null || this.mRecyclerView.getAdapter() == null) {
            return new long[0];
        }
        int size = this.mCheckedIdStates.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = this.mCheckedIdStates.keyAt(i);
        }
        return jArr;
    }

    public void setItemChecked(int i, boolean z) {
        if (this.mChoiceMode != ChoiceMode.NONE) {
            Adapter adapter = this.mRecyclerView.getAdapter();
            if (this.mChoiceMode == ChoiceMode.MULTIPLE) {
                boolean z2 = this.mCheckedStates.get(i);
                this.mCheckedStates.put(i, z);
                if (this.mCheckedIdStates != null && adapter.hasStableIds()) {
                    if (z) {
                        this.mCheckedIdStates.put(adapter.getItemId(i), Integer.valueOf(i));
                    } else {
                        this.mCheckedIdStates.delete(adapter.getItemId(i));
                    }
                }
                if (z2 != z) {
                    if (z) {
                        this.mCheckedCount++;
                    } else {
                        this.mCheckedCount--;
                    }
                }
            } else {
                boolean z3 = this.mCheckedIdStates != null && adapter.hasStableIds();
                if (z || isItemChecked(i)) {
                    this.mCheckedStates.clear();
                    if (z3) {
                        this.mCheckedIdStates.clear();
                    }
                }
                if (z) {
                    this.mCheckedStates.put(i, true);
                    if (z3) {
                        this.mCheckedIdStates.put(adapter.getItemId(i), Integer.valueOf(i));
                    }
                    this.mCheckedCount = 1;
                } else if (this.mCheckedStates.size() == 0 || !this.mCheckedStates.valueAt(0)) {
                    this.mCheckedCount = 0;
                }
            }
            updateOnScreenCheckedViews();
        }
    }

    public void setViewChecked(View view, boolean z) {
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(z);
        } else if (VERSION.SDK_INT >= 11) {
            view.setActivated(z);
        }
    }

    public void clearChoices() {
        CheckedStates checkedStates = this.mCheckedStates;
        if (checkedStates != null) {
            checkedStates.clear();
        }
        CheckedIdStates checkedIdStates = this.mCheckedIdStates;
        if (checkedIdStates != null) {
            checkedIdStates.clear();
        }
        this.mCheckedCount = 0;
        updateOnScreenCheckedViews();
    }

    public ChoiceMode getChoiceMode() {
        return this.mChoiceMode;
    }

    public void setChoiceMode(ChoiceMode choiceMode) {
        if (this.mChoiceMode != choiceMode) {
            this.mChoiceMode = choiceMode;
            if (this.mChoiceMode != ChoiceMode.NONE) {
                if (this.mCheckedStates == null) {
                    this.mCheckedStates = new CheckedStates();
                }
                Adapter adapter = this.mRecyclerView.getAdapter();
                if (this.mCheckedIdStates == null && adapter != null && adapter.hasStableIds()) {
                    this.mCheckedIdStates = new CheckedIdStates();
                }
            }
        }
    }

    public void onAdapterDataChanged() {
        boolean z;
        Adapter adapter = this.mRecyclerView.getAdapter();
        if (this.mChoiceMode != ChoiceMode.NONE && adapter != null && adapter.hasStableIds()) {
            int itemCount = adapter.getItemCount();
            this.mCheckedStates.clear();
            int i = 0;
            while (i < this.mCheckedIdStates.size()) {
                long keyAt = this.mCheckedIdStates.keyAt(i);
                int intValue = ((Integer) this.mCheckedIdStates.valueAt(i)).intValue();
                if (keyAt != adapter.getItemId(intValue)) {
                    int max = Math.max(0, intValue - 20);
                    int min = Math.min(intValue + 20, itemCount);
                    while (true) {
                        if (max >= min) {
                            z = false;
                            break;
                        } else if (keyAt == adapter.getItemId(max)) {
                            this.mCheckedStates.put(max, true);
                            this.mCheckedIdStates.setValueAt(i, Integer.valueOf(max));
                            z = true;
                            break;
                        } else {
                            max++;
                        }
                    }
                    if (!z) {
                        this.mCheckedIdStates.delete(keyAt);
                        this.mCheckedCount--;
                        i--;
                    }
                } else {
                    this.mCheckedStates.put(intValue, true);
                }
                i++;
            }
        }
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_KEY_CHOICE_MODE, this.mChoiceMode.ordinal());
        bundle.putParcelable(STATE_KEY_CHECKED_STATES, this.mCheckedStates);
        bundle.putParcelable(STATE_KEY_CHECKED_ID_STATES, this.mCheckedIdStates);
        bundle.putInt(STATE_KEY_CHECKED_COUNT, this.mCheckedCount);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        this.mChoiceMode = ChoiceMode.values()[bundle.getInt(STATE_KEY_CHOICE_MODE)];
        this.mCheckedStates = (CheckedStates) bundle.getParcelable(STATE_KEY_CHECKED_STATES);
        this.mCheckedIdStates = (CheckedIdStates) bundle.getParcelable(STATE_KEY_CHECKED_ID_STATES);
        this.mCheckedCount = bundle.getInt(STATE_KEY_CHECKED_COUNT);
    }

    public static ItemSelectionSupport addTo(RecyclerView recyclerView) {
        ItemSelectionSupport from = from(recyclerView);
        if (from != null) {
            return from;
        }
        ItemSelectionSupport itemSelectionSupport = new ItemSelectionSupport(recyclerView);
        recyclerView.setTag(R.id.twowayview_item_selection_support, itemSelectionSupport);
        return itemSelectionSupport;
    }

    public static void removeFrom(RecyclerView recyclerView) {
        ItemSelectionSupport from = from(recyclerView);
        if (from != null) {
            from.clearChoices();
            recyclerView.removeOnItemTouchListener(from.mTouchListener);
            recyclerView.setTag(R.id.twowayview_item_selection_support, null);
        }
    }

    public static ItemSelectionSupport from(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return null;
        }
        return (ItemSelectionSupport) recyclerView.getTag(R.id.twowayview_item_selection_support);
    }
}
