package com.google.common.collect;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Hashing {

    /* renamed from: C1 */
    private static final long f1960C1 = -862048943;

    /* renamed from: C2 */
    private static final long f1961C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 1073741824;

    private Hashing() {
    }

    static int smear(int i) {
        return (int) (((long) Integer.rotateLeft((int) (((long) i) * f1960C1), 15)) * f1961C2);
    }

    static int smearedHash(@NullableDecl Object obj) {
        return smear(obj == null ? 0 : obj.hashCode());
    }

    static int closedTableSize(int i, double d) {
        int max = Math.max(i, 2);
        int highestOneBit = Integer.highestOneBit(max);
        double d2 = (double) highestOneBit;
        Double.isNaN(d2);
        if (max <= ((int) (d * d2))) {
            return highestOneBit;
        }
        int i2 = highestOneBit << 1;
        if (i2 <= 0) {
            i2 = 1073741824;
        }
        return i2;
    }

    static boolean needsResizing(int i, int i2, double d) {
        double d2 = (double) i;
        double d3 = (double) i2;
        Double.isNaN(d3);
        return d2 > d * d3 && i2 < 1073741824;
    }
}
