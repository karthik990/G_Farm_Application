package com.bumptech.glide.provider;

import androidx.collection.ArrayMap;
import com.bumptech.glide.util.MultiClassKey;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ModelToResourceClassCache {
    private final ArrayMap<MultiClassKey, List<Class<?>>> registeredResourceClassCache = new ArrayMap<>();
    private final AtomicReference<MultiClassKey> resourceClassKeyRef = new AtomicReference<>();

    public List<Class<?>> get(Class<?> cls, Class<?> cls2, Class<?> cls3) {
        List<Class<?>> list;
        MultiClassKey multiClassKey = (MultiClassKey) this.resourceClassKeyRef.getAndSet(null);
        if (multiClassKey == null) {
            multiClassKey = new MultiClassKey(cls, cls2, cls3);
        } else {
            multiClassKey.set(cls, cls2, cls3);
        }
        synchronized (this.registeredResourceClassCache) {
            list = (List) this.registeredResourceClassCache.get(multiClassKey);
        }
        this.resourceClassKeyRef.set(multiClassKey);
        return list;
    }

    public void put(Class<?> cls, Class<?> cls2, Class<?> cls3, List<Class<?>> list) {
        synchronized (this.registeredResourceClassCache) {
            this.registeredResourceClassCache.put(new MultiClassKey(cls, cls2, cls3), list);
        }
    }

    public void clear() {
        synchronized (this.registeredResourceClassCache) {
            this.registeredResourceClassCache.clear();
        }
    }
}
