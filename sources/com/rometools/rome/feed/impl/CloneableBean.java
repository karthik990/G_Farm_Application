package com.rometools.rome.feed.impl;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneableBean implements Serializable, Cloneable {
    private static final Set<Class<?>> BASIC_TYPES = new HashSet();
    private static final Logger LOG = LoggerFactory.getLogger(CloneableBean.class);
    private static final Object[] NO_PARAMS = new Object[0];
    private static final Class<?>[] NO_PARAMS_DEF = new Class[0];
    private static final long serialVersionUID = 1;
    private Set<String> ignoreProperties;
    private final Object obj;

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
    }

    protected CloneableBean() {
        this.obj = this;
    }

    public CloneableBean(Object obj2) {
        this(obj2, null);
    }

    public CloneableBean(Object obj2, Set<String> set) {
        this.obj = obj2;
        if (set == null) {
            this.ignoreProperties = Collections.emptySet();
        } else {
            this.ignoreProperties = set;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return beanClone();
    }

    public Object beanClone() throws CloneNotSupportedException {
        String str = "Error while cloning bean";
        Class cls = this.obj.getClass();
        try {
            Object newInstance = cls.newInstance();
            for (PropertyDescriptor propertyDescriptor : BeanIntrospector.getPropertyDescriptorsWithGettersAndSetters(cls)) {
                if (!this.ignoreProperties.contains(propertyDescriptor.getName())) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    Object invoke = readMethod.invoke(this.obj, NO_PARAMS);
                    if (invoke != null) {
                        writeMethod.invoke(newInstance, new Object[]{doClone(invoke)});
                    }
                }
            }
            return newInstance;
        } catch (CloneNotSupportedException e) {
            LOG.error(str, (Throwable) e);
            throw e;
        } catch (Exception e2) {
            LOG.error(str, (Throwable) e2);
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot clone a ");
            sb.append(cls);
            sb.append(" object");
            throw new CloneNotSupportedException(sb.toString());
        }
    }

    private <T> T doClone(T t) throws Exception {
        if (t == null) {
            return t;
        }
        Class cls = t.getClass();
        if (cls.isArray()) {
            return cloneArray(t);
        }
        if (t instanceof Collection) {
            return cloneCollection((Collection) t);
        }
        if (t instanceof Map) {
            return cloneMap((Map) t);
        }
        if (isBasicType(cls)) {
            return t;
        }
        String str = "Cannot clone a ";
        if (t instanceof Cloneable) {
            Method method = cls.getMethod("clone", NO_PARAMS_DEF);
            if (Modifier.isPublic(method.getModifiers())) {
                return method.invoke(t, NO_PARAMS);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(t.getClass());
            sb.append(" object, clone() is not public");
            throw new CloneNotSupportedException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(cls.getName());
        sb2.append(" object");
        throw new CloneNotSupportedException(sb2.toString());
    }

    private <T> T cloneArray(T t) throws Exception {
        Class componentType = t.getClass().getComponentType();
        int length = Array.getLength(t);
        T newInstance = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(newInstance, i, doClone(Array.get(t, i)));
        }
        return newInstance;
    }

    private <T> Collection<T> cloneCollection(Collection<T> collection) throws Exception {
        Collection<T> collection2 = (Collection) collection.getClass().newInstance();
        for (T doClone : collection) {
            collection2.add(doClone(doClone));
        }
        return collection2;
    }

    private <K, V> Map<K, V> cloneMap(Map<K, V> map) throws Exception {
        Map<K, V> map2 = (Map) map.getClass().newInstance();
        for (Entry entry : map.entrySet()) {
            map2.put(doClone(entry.getKey()), doClone(entry.getValue()));
        }
        return map2;
    }

    private boolean isBasicType(Class<?> cls) {
        return BASIC_TYPES.contains(cls);
    }
}
