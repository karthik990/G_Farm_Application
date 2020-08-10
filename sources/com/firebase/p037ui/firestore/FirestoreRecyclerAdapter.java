package com.firebase.p037ui.firestore;

import android.util.Log;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.firebase.p037ui.common.ChangeEventType;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

/* renamed from: com.firebase.ui.firestore.FirestoreRecyclerAdapter */
public abstract class FirestoreRecyclerAdapter<T, VH extends ViewHolder> extends Adapter<VH> implements ChangeEventListener, LifecycleObserver {
    private static final String TAG = "FirestoreRecycler";
    private final ObservableSnapshotArray<T> mSnapshots;

    /* renamed from: com.firebase.ui.firestore.FirestoreRecyclerAdapter$1 */
    static /* synthetic */ class C14471 {
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
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.CHANGED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$firebase$ui$common$ChangeEventType     // Catch:{ NoSuchFieldError -> 0x002a }
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.REMOVED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$firebase$ui$common$ChangeEventType     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.firebase.ui.common.ChangeEventType r1 = com.firebase.p037ui.common.ChangeEventType.MOVED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.firestore.FirestoreRecyclerAdapter.C14471.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onBindViewHolder(VH vh, int i, T t);

    public void onDataChanged() {
    }

    public FirestoreRecyclerAdapter(FirestoreRecyclerOptions<T> firestoreRecyclerOptions) {
        this.mSnapshots = firestoreRecyclerOptions.getSnapshots();
        if (firestoreRecyclerOptions.getOwner() != null) {
            firestoreRecyclerOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Event.ON_START)
    public void startListening() {
        if (!this.mSnapshots.isListening(this)) {
            this.mSnapshots.addChangeEventListener(this);
        }
    }

    @OnLifecycleEvent(Event.ON_STOP)
    public void stopListening() {
        this.mSnapshots.removeChangeEventListener(this);
        notifyDataSetChanged();
    }

    /* access modifiers changed from: 0000 */
    @OnLifecycleEvent(Event.ON_DESTROY)
    public void cleanup(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    public ObservableSnapshotArray<T> getSnapshots() {
        return this.mSnapshots;
    }

    public T getItem(int i) {
        return this.mSnapshots.get(i);
    }

    public int getItemCount() {
        if (this.mSnapshots.isListening(this)) {
            return this.mSnapshots.size();
        }
        return 0;
    }

    public void onChildChanged(ChangeEventType changeEventType, DocumentSnapshot documentSnapshot, int i, int i2) {
        int i3 = C14471.$SwitchMap$com$firebase$ui$common$ChangeEventType[changeEventType.ordinal()];
        if (i3 == 1) {
            notifyItemInserted(i);
        } else if (i3 == 2) {
            notifyItemChanged(i);
        } else if (i3 == 3) {
            notifyItemRemoved(i2);
        } else if (i3 == 4) {
            notifyItemMoved(i2, i);
        } else {
            throw new IllegalStateException("Incomplete case statement");
        }
    }

    public void onError(FirebaseFirestoreException firebaseFirestoreException) {
        Log.w(TAG, "onError", firebaseFirestoreException);
    }

    public void onBindViewHolder(VH vh, int i) {
        onBindViewHolder(vh, i, getItem(i));
    }
}
