package org.objenesis.instantiator.sun;

import java.io.NotSerializableException;
import java.lang.reflect.Constructor;
import org.objenesis.ObjenesisException;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.instantiator.SerializationInstantiatorHelper;
import org.objenesis.instantiator.annotations.Instantiator;
import org.objenesis.instantiator.annotations.Typology;

@Instantiator(Typology.SERIALIZATION)
public class SunReflectionFactorySerializationInstantiator<T> implements ObjectInstantiator<T> {
    private final Constructor<T> mungedConstructor;

    public SunReflectionFactorySerializationInstantiator(Class<T> cls) {
        try {
            this.mungedConstructor = SunReflectionFactoryHelper.newConstructorForSerialization(cls, SerializationInstantiatorHelper.getNonSerializableSuperClass(cls).getConstructor(null));
            this.mungedConstructor.setAccessible(true);
        } catch (NoSuchMethodException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(cls);
            sb.append(" has no suitable superclass constructor");
            throw new ObjenesisException((Throwable) new NotSerializableException(sb.toString()));
        }
    }

    public T newInstance() {
        try {
            return this.mungedConstructor.newInstance(null);
        } catch (Exception e) {
            throw new ObjenesisException((Throwable) e);
        }
    }
}
