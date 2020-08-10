package p043io.netty.util;

/* renamed from: io.netty.util.AttributeKey */
public final class AttributeKey<T> extends AbstractConstant<AttributeKey<T>> {
    private static final ConstantPool<AttributeKey<Object>> pool = new ConstantPool<AttributeKey<Object>>() {
        /* access modifiers changed from: protected */
        public AttributeKey<Object> newConstant(int i, String str) {
            return new AttributeKey<>(i, str);
        }
    };

    public static <T> AttributeKey<T> valueOf(String str) {
        return (AttributeKey) pool.valueOf(str);
    }

    public static boolean exists(String str) {
        return pool.exists(str);
    }

    public static <T> AttributeKey<T> newInstance(String str) {
        return (AttributeKey) pool.newInstance(str);
    }

    public static <T> AttributeKey<T> valueOf(Class<?> cls, String str) {
        return (AttributeKey) pool.valueOf(cls, str);
    }

    private AttributeKey(int i, String str) {
        super(i, str);
    }
}
