package org.junit.internal;

import java.util.ArrayList;
import java.util.List;

public class ArrayComparisonFailure extends AssertionError {
    private static final long serialVersionUID = 1;
    private final List<Integer> fIndices = new ArrayList();
    private final String fMessage;

    public ArrayComparisonFailure(String str, AssertionError assertionError, int i) {
        this.fMessage = str;
        initCause(assertionError);
        addDimension(i);
    }

    public void addDimension(int i) {
        this.fIndices.add(0, Integer.valueOf(i));
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        String str = this.fMessage;
        if (str != null) {
            sb.append(str);
        }
        sb.append("arrays first differed at element ");
        for (Integer intValue : this.fIndices) {
            int intValue2 = intValue.intValue();
            sb.append("[");
            sb.append(intValue2);
            sb.append("]");
        }
        sb.append("; ");
        sb.append(getCause().getMessage());
        return sb.toString();
    }

    public String toString() {
        return getMessage();
    }
}
