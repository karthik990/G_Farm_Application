package org.junit.internal;

import org.junit.Assert;

public class ExactComparisonCriteria extends ComparisonCriteria {
    /* access modifiers changed from: protected */
    public void assertElementsEqual(Object obj, Object obj2) {
        Assert.assertEquals(obj, obj2);
    }
}
