package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.IntegerValue;
import com.google.firebase.firestore.model.value.NumberValue;
import com.google.firebase.firestore.util.Assert;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class NumericIncrementTransformOperation implements TransformOperation {
    private NumberValue operand;

    private long safeIncrement(long j, long j2) {
        long j3 = j + j2;
        return ((j ^ j3) & (j2 ^ j3)) >= 0 ? j3 : j3 >= 0 ? Long.MIN_VALUE : Long.MAX_VALUE;
    }

    public FieldValue applyToRemoteDocument(FieldValue fieldValue, FieldValue fieldValue2) {
        return fieldValue2;
    }

    public boolean isIdempotent() {
        return false;
    }

    public NumericIncrementTransformOperation(NumberValue numberValue) {
        this.operand = numberValue;
    }

    public FieldValue applyToLocalView(FieldValue fieldValue, Timestamp timestamp) {
        boolean z = fieldValue instanceof IntegerValue;
        if (z && (this.operand instanceof IntegerValue)) {
            return IntegerValue.valueOf(Long.valueOf(safeIncrement(((IntegerValue) fieldValue).getInternalValue(), operandAsLong())));
        }
        if (z) {
            double internalValue = (double) ((IntegerValue) fieldValue).getInternalValue();
            double operandAsDouble = operandAsDouble();
            Double.isNaN(internalValue);
            return DoubleValue.valueOf(Double.valueOf(internalValue + operandAsDouble));
        } else if (fieldValue instanceof DoubleValue) {
            return DoubleValue.valueOf(Double.valueOf(((DoubleValue) fieldValue).getInternalValue() + operandAsDouble()));
        } else {
            return this.operand;
        }
    }

    private double operandAsDouble() {
        NumberValue numberValue = this.operand;
        if (numberValue instanceof DoubleValue) {
            return ((DoubleValue) numberValue).getInternalValue();
        }
        if (numberValue instanceof IntegerValue) {
            return (double) ((IntegerValue) numberValue).getInternalValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected 'operand' to be of Number type, but was ");
        sb.append(this.operand.getClass().getCanonicalName());
        throw Assert.fail(sb.toString(), new Object[0]);
    }

    private long operandAsLong() {
        NumberValue numberValue = this.operand;
        if (numberValue instanceof DoubleValue) {
            return (long) ((DoubleValue) numberValue).getInternalValue();
        }
        if (numberValue instanceof IntegerValue) {
            return ((IntegerValue) numberValue).getInternalValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected 'operand' to be of Number type, but was ");
        sb.append(this.operand.getClass().getCanonicalName());
        throw Assert.fail(sb.toString(), new Object[0]);
    }

    public FieldValue getOperand() {
        return this.operand;
    }
}
