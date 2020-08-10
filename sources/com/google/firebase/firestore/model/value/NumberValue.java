package com.google.firebase.firestore.model.value;

import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class NumberValue extends FieldValue {
    public int typeOrder() {
        return 2;
    }

    public int compareTo(FieldValue fieldValue) {
        if (!(fieldValue instanceof NumberValue)) {
            return defaultCompareTo(fieldValue);
        }
        String str = "Unknown NumberValue: %s";
        if (this instanceof DoubleValue) {
            double internalValue = ((DoubleValue) this).getInternalValue();
            if (fieldValue instanceof DoubleValue) {
                return Util.compareDoubles(internalValue, ((DoubleValue) fieldValue).getInternalValue());
            }
            Assert.hardAssert(fieldValue instanceof IntegerValue, str, fieldValue);
            return Util.compareMixed(internalValue, ((IntegerValue) fieldValue).getInternalValue());
        }
        Assert.hardAssert(this instanceof IntegerValue, str, this);
        long internalValue2 = ((IntegerValue) this).getInternalValue();
        if (fieldValue instanceof IntegerValue) {
            return Util.compareLongs(internalValue2, ((IntegerValue) fieldValue).getInternalValue());
        }
        Assert.hardAssert(fieldValue instanceof DoubleValue, str, fieldValue);
        return Util.compareMixed(((DoubleValue) fieldValue).getInternalValue(), internalValue2) * -1;
    }
}
