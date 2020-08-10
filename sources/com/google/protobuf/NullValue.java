package com.google.protobuf;

import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public enum NullValue implements EnumLite {
    NULL_VALUE(0),
    UNRECOGNIZED(-1);
    
    public static final int NULL_VALUE_VALUE = 0;
    private static final EnumLiteMap<NullValue> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new EnumLiteMap<NullValue>() {
            public NullValue findValueByNumber(int i) {
                return NullValue.forNumber(i);
            }
        };
    }

    public final int getNumber() {
        return this.value;
    }

    @Deprecated
    public static NullValue valueOf(int i) {
        return forNumber(i);
    }

    public static NullValue forNumber(int i) {
        if (i != 0) {
            return null;
        }
        return NULL_VALUE;
    }

    public static EnumLiteMap<NullValue> internalGetValueMap() {
        return internalValueMap;
    }

    private NullValue(int i) {
        this.value = i;
    }
}
