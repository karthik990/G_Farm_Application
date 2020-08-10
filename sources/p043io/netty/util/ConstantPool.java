package p043io.netty.util;

import com.braintreepayments.api.models.PostalAddressParser;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.Constant;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.util.ConstantPool */
public abstract class ConstantPool<T extends Constant<T>> {
    private final ConcurrentMap<String, T> constants = PlatformDependent.newConcurrentHashMap();
    private final AtomicInteger nextId = new AtomicInteger(1);

    /* access modifiers changed from: protected */
    public abstract T newConstant(int i, String str);

    public T valueOf(Class<?> cls, String str) {
        if (cls == null) {
            throw new NullPointerException("firstNameComponent");
        } else if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(cls.getName());
            sb.append('#');
            sb.append(str);
            return valueOf(sb.toString());
        } else {
            throw new NullPointerException("secondNameComponent");
        }
    }

    public T valueOf(String str) {
        checkNotNullAndNotEmpty(str);
        return getOrCreate(str);
    }

    private T getOrCreate(String str) {
        Constant constant;
        Constant constant2 = (Constant) this.constants.get(str);
        if (constant2 == null) {
            T newConstant = newConstant(nextId(), str);
            constant = (Constant) this.constants.putIfAbsent(str, newConstant);
            if (constant == null) {
                return newConstant;
            }
        } else {
            constant = constant2;
        }
        return constant;
    }

    public boolean exists(String str) {
        checkNotNullAndNotEmpty(str);
        return this.constants.containsKey(str);
    }

    public T newInstance(String str) {
        checkNotNullAndNotEmpty(str);
        return createOrThrow(str);
    }

    private T createOrThrow(String str) {
        if (((Constant) this.constants.get(str)) == null) {
            T newConstant = newConstant(nextId(), str);
            if (((Constant) this.constants.putIfAbsent(str, newConstant)) == null) {
                return newConstant;
            }
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[]{str}));
    }

    private static String checkNotNullAndNotEmpty(String str) {
        ObjectUtil.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        if (!str.isEmpty()) {
            return str;
        }
        throw new IllegalArgumentException("empty name");
    }

    @Deprecated
    public final int nextId() {
        return this.nextId.getAndIncrement();
    }
}
