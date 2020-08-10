package org.jdom2;

import org.jdom2.internal.ArrayCopy;

final class StringBin {
    private static final int DEFAULTCAP = 1023;
    private static final int GROW = 4;
    private static final int MAXBUCKET = 64;
    private String[][] buckets;
    private int[] lengths;
    private int mask;

    public StringBin() {
        this(DEFAULTCAP);
    }

    public StringBin(int i) {
        int i2 = 0;
        this.mask = 0;
        if (i >= 0) {
            int i3 = i - 1;
            if (i3 < DEFAULTCAP) {
                i3 = DEFAULTCAP;
            }
            int i4 = i3 / 3;
            while (i4 != 0) {
                i4 >>>= 1;
                i2++;
            }
            this.mask = (1 << i2) - 1;
            this.buckets = new String[(this.mask + 1)][];
            this.lengths = new int[this.buckets.length];
            return;
        }
        throw new IllegalArgumentException("Can not have a negative capacity");
    }

    private final int locate(int i, String str, String[] strArr, int i2) {
        int i3;
        int compareTo;
        int compareTo2;
        int i4 = i2 - 1;
        int i5 = 0;
        while (true) {
            if (i5 > i4) {
                i3 = -i5;
                break;
            }
            int i6 = (i5 + i4) >>> 1;
            if (strArr[i6].hashCode() > i) {
                i4 = i6 - 1;
            } else if (strArr[i6].hashCode() < i) {
                i5 = i6 + 1;
            } else {
                int compareTo3 = str.compareTo(strArr[i6]);
                if (compareTo3 == 0) {
                    return i6;
                }
                if (compareTo3 >= 0) {
                    do {
                        i6++;
                        if (i6 > i4 || strArr[i6].hashCode() != i) {
                            break;
                        }
                        compareTo = str.compareTo(strArr[i6]);
                        if (compareTo == 0) {
                            return i6;
                        }
                    } while (compareTo >= 0);
                } else {
                    do {
                        i6--;
                        if (i6 < i5 || strArr[i6].hashCode() != i) {
                            i6++;
                        } else {
                            compareTo2 = str.compareTo(strArr[i6]);
                            if (compareTo2 == 0) {
                                return i6;
                            }
                        }
                    } while (compareTo2 <= 0);
                    i6++;
                }
                i3 = -i6;
            }
        }
        return i3 - 1;
    }

    public String reuse(String str) {
        if (str == null) {
            return null;
        }
        int hashCode = str.hashCode();
        int i = ((hashCode >>> 16) ^ hashCode) & this.mask;
        int i2 = this.lengths[i];
        if (i2 == 0) {
            String compact = compact(str);
            String[][] strArr = this.buckets;
            strArr[i] = new String[4];
            strArr[i][0] = compact;
            this.lengths[i] = 1;
            return compact;
        }
        String[] strArr2 = this.buckets[i];
        int i3 = (-locate(hashCode, str, strArr2, i2)) - 1;
        if (i3 < 0) {
            return strArr2[(-i3) - 1];
        }
        if (i2 >= 64) {
            rehash();
            return reuse(str);
        }
        if (i2 == strArr2.length) {
            strArr2 = (String[]) ArrayCopy.copyOf((E[]) strArr2, i2 + 4);
            this.buckets[i] = strArr2;
        }
        System.arraycopy(strArr2, i3, strArr2, i3 + 1, i2 - i3);
        String compact2 = compact(str);
        strArr2[i3] = compact2;
        int[] iArr = this.lengths;
        iArr[i] = iArr[i] + 1;
        return compact2;
    }

    private void rehash() {
        String[][] strArr = this.buckets;
        this.mask = ((this.mask + 1) << 2) - 1;
        this.buckets = new String[(this.mask + 1)][];
        this.lengths = new int[this.buckets.length];
        for (String[] strArr2 : strArr) {
            if (strArr2 != null) {
                for (String str : strArr2) {
                    if (str == null) {
                        break;
                    }
                    int hashCode = str.hashCode();
                    int i = (hashCode ^ (hashCode >>> 16)) & this.mask;
                    int[] iArr = this.lengths;
                    int i2 = iArr[i];
                    if (i2 == 0) {
                        String[][] strArr3 = this.buckets;
                        strArr3[i] = new String[((strArr2.length + 4) / 4)];
                        strArr3[i][0] = str;
                    } else {
                        String[][] strArr4 = this.buckets;
                        if (strArr4[i].length == i2) {
                            strArr4[i] = (String[]) ArrayCopy.copyOf((E[]) strArr4[i], iArr[i] + 4);
                        }
                        this.buckets[i][i2] = str;
                    }
                    int[] iArr2 = this.lengths;
                    iArr2[i] = iArr2[i] + 1;
                }
            }
        }
    }

    private static final String compact(String str) {
        return new String(str.toCharArray());
    }

    public int size() {
        int i = 0;
        for (int i2 : this.lengths) {
            i += i2;
        }
        return i;
    }
}
