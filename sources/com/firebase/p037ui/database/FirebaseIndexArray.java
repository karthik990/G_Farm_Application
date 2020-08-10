package com.firebase.p037ui.database;

import android.util.Log;
import com.firebase.p037ui.common.ChangeEventType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.firebase.ui.database.FirebaseIndexArray */
public class FirebaseIndexArray<T> extends ObservableSnapshotArray<T> implements ChangeEventListener {
    private static final String TAG = "FirebaseIndexArray";
    private final DatabaseReference mDataRef;
    /* access modifiers changed from: private */
    public final List<DataSnapshot> mDataSnapshots = new ArrayList();
    private boolean mHasPendingMoveOrDelete;
    private final FirebaseArray<String> mKeySnapshots;
    /* access modifiers changed from: private */
    public final List<String> mKeysWithPendingUpdate = new ArrayList();
    private final Map<DatabaseReference, ValueEventListener> mRefs = new HashMap();

    /* renamed from: com.firebase.ui.database.FirebaseIndexArray$2 */
    static /* synthetic */ class C14242 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$ui$common$ChangeEventType = new int[ChangeEventType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.firebase.ui.common.ChangeEventType[] r0 = com.firebase.p037ui.common.ChangeEventType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$firebase$ui$common$ChangeEventType = r0
                int[] r0 = $SwitchMap$com$firebase$ui$common$ChangeEventType     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.ADDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$firebase$ui$common$ChangeEventType     // Catch:{ NoSuchFieldError -> 0x001f }
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.MOVED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$firebase$ui$common$ChangeEventType     // Catch:{ NoSuchFieldError -> 0x002a }
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.CHANGED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$firebase$ui$common$ChangeEventType     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.REMOVED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.database.FirebaseIndexArray.C14242.<clinit>():void");
        }
    }

    /* renamed from: com.firebase.ui.database.FirebaseIndexArray$DataRefListener */
    private final class DataRefListener implements ValueEventListener {
        private int currentIndex;

        public DataRefListener(int i) {
            this.currentIndex = i;
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            int access$000 = FirebaseIndexArray.this.returnOrFindIndexForKey(this.currentIndex, key);
            this.currentIndex = access$000;
            if (dataSnapshot.getValue() != null) {
                if (FirebaseIndexArray.this.isKeyAtIndex(key, access$000)) {
                    FirebaseIndexArray.this.mDataSnapshots.set(access$000, dataSnapshot);
                    FirebaseIndexArray.this.notifyOnChildChanged(ChangeEventType.CHANGED, dataSnapshot, access$000, -1);
                } else {
                    FirebaseIndexArray.this.mDataSnapshots.add(access$000, dataSnapshot);
                    FirebaseIndexArray.this.notifyOnChildChanged(ChangeEventType.ADDED, dataSnapshot, access$000, -1);
                }
            } else if (FirebaseIndexArray.this.isKeyAtIndex(key, access$000)) {
                FirebaseIndexArray.this.mDataSnapshots.remove(access$000);
                FirebaseIndexArray.this.notifyOnChildChanged(ChangeEventType.REMOVED, dataSnapshot, access$000, -1);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Key not found at ref: ");
                sb.append(dataSnapshot.getRef());
                Log.w(FirebaseIndexArray.TAG, sb.toString());
            }
            FirebaseIndexArray.this.mKeysWithPendingUpdate.remove(key);
            if (FirebaseIndexArray.this.mKeysWithPendingUpdate.isEmpty()) {
                FirebaseIndexArray.this.notifyOnDataChanged();
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            FirebaseIndexArray.this.notifyOnError(databaseError);
        }
    }

    public FirebaseIndexArray(Query query, DatabaseReference databaseReference, SnapshotParser<T> snapshotParser) {
        super(snapshotParser);
        this.mDataRef = databaseReference;
        this.mKeySnapshots = new FirebaseArray<>(query, new SnapshotParser<String>() {
            public String parseSnapshot(DataSnapshot dataSnapshot) {
                return dataSnapshot.getKey();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        super.onCreate();
        this.mKeySnapshots.addChangeEventListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mKeySnapshots.removeChangeEventListener(this);
        for (DatabaseReference databaseReference : this.mRefs.keySet()) {
            databaseReference.removeEventListener((ValueEventListener) this.mRefs.get(databaseReference));
        }
        this.mRefs.clear();
    }

    public void onChildChanged(ChangeEventType changeEventType, DataSnapshot dataSnapshot, int i, int i2) {
        int i3 = C14242.$SwitchMap$com$firebase$ui$common$ChangeEventType[changeEventType.ordinal()];
        if (i3 == 1) {
            onKeyAdded(dataSnapshot, i);
        } else if (i3 == 2) {
            onKeyMoved(dataSnapshot, i, i2);
        } else if (i3 != 3 && i3 == 4) {
            onKeyRemoved(dataSnapshot, i);
        }
    }

    public void onDataChanged() {
        if (this.mHasPendingMoveOrDelete || this.mKeySnapshots.isEmpty()) {
            notifyOnDataChanged();
            this.mHasPendingMoveOrDelete = false;
        }
    }

    public void onError(DatabaseError databaseError) {
        Log.e(TAG, "A fatal error occurred retrieving the necessary keys to populate your adapter.");
    }

    /* access modifiers changed from: protected */
    public List<DataSnapshot> getSnapshots() {
        return this.mDataSnapshots;
    }

    /* access modifiers changed from: private */
    public int returnOrFindIndexForKey(int i, String str) {
        if (isKeyAtIndex(str, i)) {
            return i;
        }
        int size = size();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size && i3 < this.mKeySnapshots.size()) {
            String str2 = (String) this.mKeySnapshots.get(i3);
            if (str.equals(str2)) {
                break;
            }
            if (((DataSnapshot) this.mDataSnapshots.get(i2)).getKey().equals(str2)) {
                i2++;
            }
            i3++;
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public boolean isKeyAtIndex(String str, int i) {
        return i >= 0 && i < size() && ((DataSnapshot) this.mDataSnapshots.get(i)).getKey().equals(str);
    }

    private void onKeyAdded(DataSnapshot dataSnapshot, int i) {
        String key = dataSnapshot.getKey();
        DatabaseReference child = this.mDataRef.child(key);
        this.mKeysWithPendingUpdate.add(key);
        this.mRefs.put(child, child.addValueEventListener(new DataRefListener(i)));
    }

    private void onKeyMoved(DataSnapshot dataSnapshot, int i, int i2) {
        String key = dataSnapshot.getKey();
        if (isKeyAtIndex(key, i2)) {
            DataSnapshot dataSnapshot2 = (DataSnapshot) this.mDataSnapshots.remove(i2);
            int returnOrFindIndexForKey = returnOrFindIndexForKey(i, key);
            this.mHasPendingMoveOrDelete = true;
            this.mDataSnapshots.add(returnOrFindIndexForKey, dataSnapshot2);
            notifyOnChildChanged(ChangeEventType.MOVED, dataSnapshot2, returnOrFindIndexForKey, i2);
        }
    }

    private void onKeyRemoved(DataSnapshot dataSnapshot, int i) {
        String key = dataSnapshot.getKey();
        ValueEventListener valueEventListener = (ValueEventListener) this.mRefs.remove(this.mDataRef.getRef().child(key));
        if (valueEventListener != null) {
            this.mDataRef.child(key).removeEventListener(valueEventListener);
        }
        int returnOrFindIndexForKey = returnOrFindIndexForKey(i, key);
        if (isKeyAtIndex(key, returnOrFindIndexForKey)) {
            DataSnapshot dataSnapshot2 = (DataSnapshot) this.mDataSnapshots.remove(returnOrFindIndexForKey);
            this.mHasPendingMoveOrDelete = true;
            notifyOnChildChanged(ChangeEventType.REMOVED, dataSnapshot2, returnOrFindIndexForKey, -1);
        }
    }
}
