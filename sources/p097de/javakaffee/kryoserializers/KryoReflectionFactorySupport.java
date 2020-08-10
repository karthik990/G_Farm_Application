package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import sun.reflect.ReflectionFactory;

/* renamed from: de.javakaffee.kryoserializers.KryoReflectionFactorySupport */
public class KryoReflectionFactorySupport extends Kryo {
    private static final Object[] INITARGS = new Object[0];
    private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();
    private static final Map<Class<?>, Constructor<?>> _constructors = new ConcurrentHashMap();

    public Serializer<?> getDefaultSerializer(Class cls) {
        Serializer<?> defaultSerializer = super.getDefaultSerializer(cls);
        if (defaultSerializer instanceof FieldSerializer) {
            ((FieldSerializer) defaultSerializer).setIgnoreSyntheticFields(false);
        }
        return defaultSerializer;
    }

    public <T> T newInstance(Class<T> cls) {
        if (cls != null) {
            Constructor constructor = (Constructor) _constructors.get(cls);
            if (constructor == null) {
                constructor = getNoArgsConstructor(cls);
                if (constructor == null) {
                    constructor = newConstructorForSerialization(cls);
                }
                _constructors.put(cls, constructor);
            }
            return newInstanceFrom(constructor);
        }
        throw new IllegalArgumentException("type cannot be null.");
    }

    private static Object newInstanceFrom(Constructor<?> constructor) {
        try {
            return constructor.newInstance(INITARGS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstanceFromReflectionFactory(Class<T> cls) {
        Constructor constructor = (Constructor) _constructors.get(cls);
        if (constructor == null) {
            constructor = newConstructorForSerialization(cls);
            _constructors.put(cls, constructor);
        }
        return newInstanceFrom(constructor);
    }

    private static <T> Constructor<?> newConstructorForSerialization(Class<T> cls) {
        try {
            Constructor<?> newConstructorForSerialization = REFLECTION_FACTORY.newConstructorForSerialization(cls, Object.class.getDeclaredConstructor(new Class[0]));
            newConstructorForSerialization.setAccessible(true);
            return newConstructorForSerialization;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<?> getNoArgsConstructor(Class<?> cls) {
        Constructor<?>[] constructors;
        for (Constructor<?> constructor : cls.getConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return null;
    }
}
