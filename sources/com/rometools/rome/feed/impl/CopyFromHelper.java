package com.rometools.rome.feed.impl;

import com.rometools.rome.feed.CopyFrom;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyFromHelper {
    private static final Set<Class<?>> BASIC_TYPES = new HashSet();
    private static final Logger LOG = LoggerFactory.getLogger(CopyFromHelper.class);
    private static final Object[] NO_PARAMS = new Object[0];
    private final Map<Class<? extends CopyFrom>, Class<?>> baseImplMap;
    private final Map<String, Class<?>> baseInterfaceMap;
    private final Class<? extends CopyFrom> beanInterfaceClass;

    static {
        BASIC_TYPES.add(Boolean.class);
        BASIC_TYPES.add(Byte.class);
        BASIC_TYPES.add(Character.class);
        BASIC_TYPES.add(Double.class);
        BASIC_TYPES.add(Float.class);
        BASIC_TYPES.add(Integer.class);
        BASIC_TYPES.add(Long.class);
        BASIC_TYPES.add(Short.class);
        BASIC_TYPES.add(String.class);
        BASIC_TYPES.add(Date.class);
    }

    public CopyFromHelper(Class<? extends CopyFrom> cls, Map<String, Class<?>> map, Map<Class<? extends CopyFrom>, Class<?>> map2) {
        this.beanInterfaceClass = cls;
        this.baseInterfaceMap = map;
        this.baseImplMap = map2;
    }

    public void copy(Object obj, Object obj2) {
        try {
            for (PropertyDescriptor propertyDescriptor : BeanIntrospector.getPropertyDescriptorsWithGettersAndSetters(this.beanInterfaceClass)) {
                String name = propertyDescriptor.getName();
                if (this.baseInterfaceMap.containsKey(name)) {
                    Object invoke = propertyDescriptor.getReadMethod().invoke(obj2, NO_PARAMS);
                    if (invoke != null) {
                        propertyDescriptor.getWriteMethod().invoke(obj, new Object[]{doCopy(invoke, (Class) this.baseInterfaceMap.get(name))});
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error while copying object", (Throwable) e);
            StringBuilder sb = new StringBuilder();
            sb.append("Could not do a copyFrom ");
            sb.append(e);
            throw new RuntimeException(sb.toString(), e);
        }
    }

    private CopyFrom createInstance(Class<? extends CopyFrom> cls) throws Exception {
        if (this.baseImplMap.get(cls) == null) {
            return null;
        }
        return (CopyFrom) ((Class) this.baseImplMap.get(cls)).newInstance();
    }

    private <T> T doCopy(T t, Class<?> cls) throws Exception {
        if (t == null) {
            return t;
        }
        Class cls2 = t.getClass();
        if (cls2.isArray()) {
            return doCopyArray((Object[]) t, cls);
        }
        if (t instanceof Collection) {
            return doCopyCollection((Collection) t, cls);
        }
        if (t instanceof Map) {
            return doCopyMap((Map) t, cls);
        }
        if (isBasicType(cls2)) {
            if (t instanceof Date) {
                return ((Date) t).clone();
            }
            return t;
        } else if (t instanceof CopyFrom) {
            CopyFrom copyFrom = (CopyFrom) t;
            CopyFrom createInstance = createInstance(copyFrom.getInterface());
            CopyFrom copyFrom2 = createInstance == null ? (CopyFrom) t.getClass().newInstance() : createInstance;
            copyFrom2.copyFrom(copyFrom);
            return copyFrom2;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("unsupported class for 'copyFrom' ");
            sb.append(t.getClass());
            throw new Exception(sb.toString());
        }
    }

    private <T> T[] doCopyArray(T[] tArr, Class<?> cls) throws Exception {
        Class componentType = tArr.getClass().getComponentType();
        int length = Array.getLength(tArr);
        T[] tArr2 = (Object[]) Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(tArr2, i, doCopy(Array.get(tArr, i), cls));
        }
        return tArr2;
    }

    private <T> Collection<T> doCopyCollection(Collection<T> collection, Class<?> cls) throws Exception {
        Collection<T> collection2;
        if (collection instanceof Set) {
            collection2 = new LinkedHashSet<>();
        } else {
            collection2 = new ArrayList<>();
        }
        for (T doCopy : collection) {
            collection2.add(doCopy(doCopy, cls));
        }
        return collection2;
    }

    private <S, T> Map<S, T> doCopyMap(Map<S, T> map, Class<?> cls) throws Exception {
        HashMap hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            hashMap.put(entry.getKey(), doCopy(entry.getValue(), cls));
        }
        return hashMap;
    }

    private boolean isBasicType(Class<?> cls) {
        return BASIC_TYPES.contains(cls);
    }
}
