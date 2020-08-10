package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.Filter.Operator;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.util.Assert;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class RelationFilter extends Filter {
    private final FieldPath field;
    private final Operator operator;
    private final FieldValue value;

    /* renamed from: com.google.firebase.firestore.core.RelationFilter$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36471 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = new int[Operator.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.google.firebase.firestore.core.Filter$Operator[] r0 = com.google.firebase.firestore.core.Filter.Operator.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = r0
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.EQUAL     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.core.RelationFilter.C36471.<clinit>():void");
        }
    }

    RelationFilter(FieldPath fieldPath, Operator operator2, FieldValue fieldValue) {
        this.field = fieldPath;
        this.operator = operator2;
        this.value = fieldValue;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public FieldPath getField() {
        return this.field;
    }

    public FieldValue getValue() {
        return this.value;
    }

    public boolean matches(Document document) {
        boolean z = true;
        if (this.field.isKeyField()) {
            Object value2 = this.value.value();
            Assert.hardAssert(value2 instanceof DocumentKey, "Comparing on key, but filter value not a DocumentKey", new Object[0]);
            if (this.operator == Operator.ARRAY_CONTAINS) {
                z = false;
            }
            Assert.hardAssert(z, "ARRAY_CONTAINS queries don't make sense on document keys.", new Object[0]);
            return matchesComparison(DocumentKey.comparator().compare(document.getKey(), (DocumentKey) value2));
        }
        if (document.getField(this.field) == null || !matchesValue(document.getField(this.field))) {
            z = false;
        }
        return z;
    }

    private boolean matchesValue(FieldValue fieldValue) {
        boolean z = true;
        if (this.operator == Operator.ARRAY_CONTAINS) {
            if (!(fieldValue instanceof ArrayValue) || !((ArrayValue) fieldValue).getInternalValue().contains(this.value)) {
                z = false;
            }
            return z;
        }
        if (this.value.typeOrder() != fieldValue.typeOrder() || !matchesComparison(fieldValue.compareTo(this.value))) {
            z = false;
        }
        return z;
    }

    private boolean matchesComparison(int i) {
        int i2 = C36471.$SwitchMap$com$google$firebase$firestore$core$Filter$Operator[this.operator.ordinal()];
        boolean z = false;
        if (i2 == 1) {
            if (i < 0) {
                z = true;
            }
            return z;
        } else if (i2 == 2) {
            if (i <= 0) {
                z = true;
            }
            return z;
        } else if (i2 == 3) {
            if (i == 0) {
                z = true;
            }
            return z;
        } else if (i2 == 4) {
            if (i > 0) {
                z = true;
            }
            return z;
        } else if (i2 == 5) {
            if (i >= 0) {
                z = true;
            }
            return z;
        } else {
            throw Assert.fail("Unknown operator: %s", this.operator);
        }
    }

    public boolean isInequality() {
        return (this.operator == Operator.EQUAL || this.operator == Operator.ARRAY_CONTAINS) ? false : true;
    }

    public String getCanonicalId() {
        StringBuilder sb = new StringBuilder();
        sb.append(getField().canonicalString());
        sb.append(getOperator().toString());
        sb.append(getValue().toString());
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.field.canonicalString());
        String str = " ";
        sb.append(str);
        sb.append(this.operator);
        sb.append(str);
        sb.append(this.value);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RelationFilter)) {
            return false;
        }
        RelationFilter relationFilter = (RelationFilter) obj;
        if (this.operator != relationFilter.operator || !this.field.equals(relationFilter.field) || !this.value.equals(relationFilter.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((((1147 + this.operator.hashCode()) * 31) + this.field.hashCode()) * 31) + this.value.hashCode();
    }
}
