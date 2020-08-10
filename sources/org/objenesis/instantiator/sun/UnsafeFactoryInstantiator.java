package org.objenesis.instantiator.sun;

import java.lang.reflect.Field;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.instantiator.annotations.Instantiator;
import org.objenesis.instantiator.annotations.Typology;
import sun.misc.Unsafe;

@Instantiator(Typology.STANDARD)
public class UnsafeFactoryInstantiator<T> implements ObjectInstantiator<T> {
    private static Unsafe unsafe;
    private final Class<T> type;

    public UnsafeFactoryInstantiator(Class<T> cls) {
        if (unsafe == null) {
            try {
                Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
                declaredField.setAccessible(true);
                try {
                    unsafe = (Unsafe) declaredField.get(null);
                } catch (IllegalAccessException e) {
                    throw new ObjenesisException((Throwable) e);
                }
            } catch (NoSuchFieldException e2) {
                throw new ObjenesisException((Throwable) e2);
            }
        }
        this.type = cls;
    }

    public T newInstance() {
        try {
            return this.type.cast(unsafe.allocateInstance(this.type));
        } catch (InstantiationException e) {
            throw new ObjenesisException((Throwable) e);
        }
    }
}
