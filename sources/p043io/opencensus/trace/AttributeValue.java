package p043io.opencensus.trace;

import p043io.opencensus.common.Function;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.AttributeValue */
public abstract class AttributeValue {

    /* renamed from: io.opencensus.trace.AttributeValue$AttributeValueBoolean */
    static abstract class AttributeValueBoolean extends AttributeValue {
        /* access modifiers changed from: 0000 */
        public abstract Boolean getBooleanValue();

        AttributeValueBoolean() {
        }

        static AttributeValue create(Boolean bool) {
            return new AutoValue_AttributeValue_AttributeValueBoolean((Boolean) C5887Utils.checkNotNull(bool, "booleanValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function2.apply(getBooleanValue());
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function2.apply(getBooleanValue());
        }
    }

    /* renamed from: io.opencensus.trace.AttributeValue$AttributeValueDouble */
    static abstract class AttributeValueDouble extends AttributeValue {
        /* access modifiers changed from: 0000 */
        public abstract Double getDoubleValue();

        AttributeValueDouble() {
        }

        static AttributeValue create(Double d) {
            return new AutoValue_AttributeValue_AttributeValueDouble((Double) C5887Utils.checkNotNull(d, "doubleValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function4.apply(getDoubleValue());
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function4.apply(getDoubleValue());
        }
    }

    /* renamed from: io.opencensus.trace.AttributeValue$AttributeValueLong */
    static abstract class AttributeValueLong extends AttributeValue {
        /* access modifiers changed from: 0000 */
        public abstract Long getLongValue();

        AttributeValueLong() {
        }

        static AttributeValue create(Long l) {
            return new AutoValue_AttributeValue_AttributeValueLong((Long) C5887Utils.checkNotNull(l, "longValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function3.apply(getLongValue());
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function3.apply(getLongValue());
        }
    }

    /* renamed from: io.opencensus.trace.AttributeValue$AttributeValueString */
    static abstract class AttributeValueString extends AttributeValue {
        /* access modifiers changed from: 0000 */
        public abstract String getStringValue();

        AttributeValueString() {
        }

        static AttributeValue create(String str) {
            return new AutoValue_AttributeValue_AttributeValueString((String) C5887Utils.checkNotNull(str, "stringValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function.apply(getStringValue());
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function.apply(getStringValue());
        }
    }

    @Deprecated
    public abstract <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4);

    public abstract <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5);

    public static AttributeValue stringAttributeValue(String str) {
        return AttributeValueString.create(str);
    }

    public static AttributeValue booleanAttributeValue(boolean z) {
        return AttributeValueBoolean.create(Boolean.valueOf(z));
    }

    public static AttributeValue longAttributeValue(long j) {
        return AttributeValueLong.create(Long.valueOf(j));
    }

    public static AttributeValue doubleAttributeValue(double d) {
        return AttributeValueDouble.create(Double.valueOf(d));
    }

    AttributeValue() {
    }
}
