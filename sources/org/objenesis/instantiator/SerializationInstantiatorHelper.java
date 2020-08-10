package org.objenesis.instantiator;

import java.io.Serializable;

public class SerializationInstantiatorHelper {
    public static <T> Class<? super T> getNonSerializableSuperClass(Class<T> cls) {
        while (Serializable.class.isAssignableFrom(cls)) {
            cls = cls.getSuperclass();
            if (cls == null) {
                throw new Error("Bad class hierarchy: No non-serializable parents");
            }
        }
        return cls;
    }
}
