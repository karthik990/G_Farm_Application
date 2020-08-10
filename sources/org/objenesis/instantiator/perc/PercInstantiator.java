package org.objenesis.instantiator.perc;

import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.instantiator.annotations.Instantiator;
import org.objenesis.instantiator.annotations.Typology;

@Instantiator(Typology.STANDARD)
public class PercInstantiator<T> implements ObjectInstantiator<T> {
    private final Method newInstanceMethod;
    private final Object[] typeArgs = {null, Boolean.FALSE};

    public PercInstantiator(Class<T> cls) {
        this.typeArgs[0] = cls;
        try {
            this.newInstanceMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Boolean.TYPE});
            this.newInstanceMethod.setAccessible(true);
        } catch (RuntimeException e) {
            throw new ObjenesisException((Throwable) e);
        } catch (NoSuchMethodException e2) {
            throw new ObjenesisException((Throwable) e2);
        }
    }

    public T newInstance() {
        try {
            return this.newInstanceMethod.invoke(null, this.typeArgs);
        } catch (Exception e) {
            throw new ObjenesisException((Throwable) e);
        }
    }
}
