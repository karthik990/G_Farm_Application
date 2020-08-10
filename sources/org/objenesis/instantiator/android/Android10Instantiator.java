package org.objenesis.instantiator.android;

import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.instantiator.annotations.Instantiator;
import org.objenesis.instantiator.annotations.Typology;

@Instantiator(Typology.STANDARD)
public class Android10Instantiator<T> implements ObjectInstantiator<T> {
    private final Method newStaticMethod = getNewStaticMethod();
    private final Class<T> type;

    public Android10Instantiator(Class<T> cls) {
        this.type = cls;
    }

    public T newInstance() {
        try {
            return this.type.cast(this.newStaticMethod.invoke(null, new Object[]{this.type, Object.class}));
        } catch (Exception e) {
            throw new ObjenesisException((Throwable) e);
        }
    }

    private static Method getNewStaticMethod() {
        try {
            Method declaredMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (RuntimeException e) {
            throw new ObjenesisException((Throwable) e);
        } catch (NoSuchMethodException e2) {
            throw new ObjenesisException((Throwable) e2);
        }
    }
}
