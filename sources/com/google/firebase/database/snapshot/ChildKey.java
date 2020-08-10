package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ChildKey implements Comparable<ChildKey> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ChildKey INFO_CHILD_KEY = new ChildKey(".info");
    private static final ChildKey MAX_KEY = new ChildKey("[MAX_KEY]");
    private static final ChildKey MIN_KEY = new ChildKey("[MIN_KEY]");
    private static final ChildKey PRIORITY_CHILD_KEY = new ChildKey(".priority");
    /* access modifiers changed from: private */
    public final String key;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class IntegerChildKey extends ChildKey {
        private final int intValue;

        /* access modifiers changed from: protected */
        public boolean isInt() {
            return true;
        }

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return ChildKey.super.compareTo((ChildKey) obj);
        }

        IntegerChildKey(String str, int i) {
            super(str);
            this.intValue = i;
        }

        /* access modifiers changed from: protected */
        public int intValue() {
            return this.intValue;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("IntegerChildName(\"");
            sb.append(this.key);
            sb.append("\")");
            return sb.toString();
        }
    }

    /* access modifiers changed from: protected */
    public int intValue() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean isInt() {
        return false;
    }

    public static ChildKey getMinName() {
        return MIN_KEY;
    }

    public static ChildKey getMaxName() {
        return MAX_KEY;
    }

    public static ChildKey getPriorityKey() {
        return PRIORITY_CHILD_KEY;
    }

    public static ChildKey getInfoKey() {
        return INFO_CHILD_KEY;
    }

    private ChildKey(String str) {
        this.key = str;
    }

    public String asString() {
        return this.key;
    }

    public boolean isPriorityChildName() {
        return equals(PRIORITY_CHILD_KEY);
    }

    public int compareTo(ChildKey childKey) {
        if (this == childKey) {
            return 0;
        }
        ChildKey childKey2 = MIN_KEY;
        if (this != childKey2) {
            ChildKey childKey3 = MAX_KEY;
            if (childKey != childKey3) {
                if (childKey == childKey2 || this == childKey3) {
                    return 1;
                }
                if (isInt()) {
                    if (!childKey.isInt()) {
                        return -1;
                    }
                    int compareInts = Utilities.compareInts(intValue(), childKey.intValue());
                    if (compareInts == 0) {
                        compareInts = Utilities.compareInts(this.key.length(), childKey.key.length());
                    }
                    return compareInts;
                } else if (childKey.isInt()) {
                    return 1;
                } else {
                    return this.key.compareTo(childKey.key);
                }
            }
        }
        return -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChildKey(\"");
        sb.append(this.key);
        sb.append("\")");
        return sb.toString();
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ChildKey)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return this.key.equals(((ChildKey) obj).key);
    }

    public static ChildKey fromString(String str) {
        Integer tryParseInt = Utilities.tryParseInt(str);
        if (tryParseInt != null) {
            return new IntegerChildKey(str, tryParseInt.intValue());
        }
        if (str.equals(".priority")) {
            return PRIORITY_CHILD_KEY;
        }
        return new ChildKey(str);
    }
}
