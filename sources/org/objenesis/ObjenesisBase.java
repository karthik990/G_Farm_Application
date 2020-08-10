package org.objenesis;

import java.util.concurrent.ConcurrentHashMap;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.strategy.InstantiatorStrategy;

public class ObjenesisBase implements Objenesis {
    protected ConcurrentHashMap<String, ObjectInstantiator<?>> cache;
    protected final InstantiatorStrategy strategy;

    public ObjenesisBase(InstantiatorStrategy instantiatorStrategy) {
        this(instantiatorStrategy, true);
    }

    public ObjenesisBase(InstantiatorStrategy instantiatorStrategy, boolean z) {
        if (instantiatorStrategy != null) {
            this.strategy = instantiatorStrategy;
            this.cache = z ? new ConcurrentHashMap<>() : null;
            return;
        }
        throw new IllegalArgumentException("A strategy can't be null");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" using ");
        sb.append(this.strategy.getClass().getName());
        sb.append(this.cache == null ? " without" : " with");
        sb.append(" caching");
        return sb.toString();
    }

    public <T> T newInstance(Class<T> cls) {
        return getInstantiatorOf(cls).newInstance();
    }

    public <T> ObjectInstantiator<T> getInstantiatorOf(Class<T> cls) {
        if (!cls.isPrimitive()) {
            ConcurrentHashMap<String, ObjectInstantiator<?>> concurrentHashMap = this.cache;
            if (concurrentHashMap == null) {
                return this.strategy.newInstantiatorOf(cls);
            }
            ObjectInstantiator<T> objectInstantiator = (ObjectInstantiator) concurrentHashMap.get(cls.getName());
            if (objectInstantiator == null) {
                objectInstantiator = this.strategy.newInstantiatorOf(cls);
                ObjectInstantiator<T> objectInstantiator2 = (ObjectInstantiator) this.cache.putIfAbsent(cls.getName(), objectInstantiator);
                if (objectInstantiator2 != null) {
                    objectInstantiator = objectInstantiator2;
                }
            }
            return objectInstantiator;
        }
        throw new IllegalArgumentException("Primitive types can't be instantiated in Java");
    }
}
