package com.google.firebase.firestore.model.value;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ServerTimestampValue extends FieldValue {
    private final Timestamp localWriteTime;
    @Nullable
    private final FieldValue previousValue;

    /* renamed from: com.google.firebase.firestore.model.value.ServerTimestampValue$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36591 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior */
        static final /* synthetic */ int[] f2036xf626b9ac = new int[ServerTimestampBehavior.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.google.firebase.firestore.model.value.FieldValueOptions$ServerTimestampBehavior[] r0 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2036xf626b9ac = r0
                int[] r0 = f2036xf626b9ac     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.model.value.FieldValueOptions$ServerTimestampBehavior r1 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.PREVIOUS     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2036xf626b9ac     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.model.value.FieldValueOptions$ServerTimestampBehavior r1 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.ESTIMATE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2036xf626b9ac     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.model.value.FieldValueOptions$ServerTimestampBehavior r1 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.NONE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.model.value.ServerTimestampValue.C36591.<clinit>():void");
        }
    }

    public int typeOrder() {
        return 3;
    }

    @Nullable
    public Object value() {
        return null;
    }

    public ServerTimestampValue(Timestamp timestamp, @Nullable FieldValue fieldValue) {
        this.localWriteTime = timestamp;
        this.previousValue = fieldValue;
    }

    @Nullable
    public Object value(FieldValueOptions fieldValueOptions) {
        int i = C36591.f2036xf626b9ac[fieldValueOptions.getServerTimestampBehavior().ordinal()];
        Object obj = null;
        if (i == 1) {
            FieldValue fieldValue = this.previousValue;
            if (fieldValue != null) {
                obj = fieldValue.value(fieldValueOptions);
            }
            return obj;
        } else if (i == 2) {
            return new TimestampValue(this.localWriteTime).value(fieldValueOptions);
        } else {
            if (i == 3) {
                return null;
            }
            throw Assert.fail("Unexpected case for ServerTimestampBehavior: %s", fieldValueOptions.getServerTimestampBehavior().name());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ServerTimestamp localTime=");
        sb.append(this.localWriteTime.toString());
        sb.append(">");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ServerTimestampValue) && this.localWriteTime.equals(((ServerTimestampValue) obj).localWriteTime);
    }

    public int hashCode() {
        return this.localWriteTime.hashCode();
    }

    public int compareTo(FieldValue fieldValue) {
        if (fieldValue instanceof ServerTimestampValue) {
            return this.localWriteTime.compareTo(((ServerTimestampValue) fieldValue).localWriteTime);
        }
        if (fieldValue instanceof TimestampValue) {
            return 1;
        }
        return defaultCompareTo(fieldValue);
    }
}
