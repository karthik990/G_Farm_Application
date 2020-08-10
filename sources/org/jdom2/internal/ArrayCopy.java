package org.jdom2.internal;

import java.lang.reflect.Array;

public final class ArrayCopy {
    private ArrayCopy() {
    }

    public static final <E> E[] copyOf(E[] eArr, int i) {
        E[] eArr2 = (Object[]) Array.newInstance(eArr.getClass().getComponentType(), i);
        if (i >= eArr.length) {
            i = eArr.length;
        }
        System.arraycopy(eArr, 0, eArr2, 0, i);
        return eArr2;
    }

    public static final <E> E[] copyOfRange(E[] eArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            E[] eArr2 = (Object[]) Array.newInstance(eArr.getClass().getComponentType(), i3);
            if (i + i3 > eArr.length) {
                i3 = eArr.length - i;
            }
            System.arraycopy(eArr, i, eArr2, 0, i3);
            return eArr2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("From(");
        sb.append(i);
        sb.append(") > To (");
        sb.append(i2);
        sb.append(")");
        throw new IllegalArgumentException(sb.toString());
    }

    public static final char[] copyOf(char[] cArr, int i) {
        char[] cArr2 = new char[i];
        if (i >= cArr.length) {
            i = cArr.length;
        }
        System.arraycopy(cArr, 0, cArr2, 0, i);
        return cArr2;
    }

    public static final int[] copyOf(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        if (i >= iArr.length) {
            i = iArr.length;
        }
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    public static final boolean[] copyOf(boolean[] zArr, int i) {
        boolean[] zArr2 = new boolean[i];
        if (i >= zArr.length) {
            i = zArr.length;
        }
        System.arraycopy(zArr, 0, zArr2, 0, i);
        return zArr2;
    }
}
