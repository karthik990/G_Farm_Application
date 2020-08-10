package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.reflectasm.MethodAccess;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BeanSerializer<T> extends Serializer<T> {
    static final Object[] noArgs = new Object[0];
    Object access;
    private CachedProperty[] properties;

    class CachedProperty<X> {
        Method getMethod;
        int getterAccessIndex;
        String name;
        Serializer serializer;
        Method setMethod;
        Class setMethodType;
        int setterAccessIndex;

        CachedProperty() {
        }

        public String toString() {
            return this.name;
        }

        /* access modifiers changed from: 0000 */
        public Object get(Object obj) throws IllegalAccessException, InvocationTargetException {
            if (BeanSerializer.this.access != null) {
                return ((MethodAccess) BeanSerializer.this.access).invoke(obj, this.getterAccessIndex, new Object[0]);
            }
            return this.getMethod.invoke(obj, BeanSerializer.noArgs);
        }

        /* access modifiers changed from: 0000 */
        public void set(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
            if (BeanSerializer.this.access != null) {
                ((MethodAccess) BeanSerializer.this.access).invoke(obj, this.setterAccessIndex, obj2);
                return;
            }
            this.setMethod.invoke(obj, new Object[]{obj2});
        }
    }

    public BeanSerializer(Kryo kryo, Class cls) {
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(cls).getPropertyDescriptors();
            Arrays.sort(propertyDescriptors, new Comparator<PropertyDescriptor>() {
                public int compare(PropertyDescriptor propertyDescriptor, PropertyDescriptor propertyDescriptor2) {
                    return propertyDescriptor.getName().compareTo(propertyDescriptor2.getName());
                }
            });
            ArrayList arrayList = new ArrayList(propertyDescriptors.length);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name = propertyDescriptor.getName();
                if (!name.equals("class")) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (!(readMethod == null || writeMethod == null)) {
                        Serializer serializer = null;
                        Class returnType = readMethod.getReturnType();
                        if (kryo.isFinal(returnType)) {
                            serializer = kryo.getRegistration(returnType).getSerializer();
                        }
                        CachedProperty cachedProperty = new CachedProperty();
                        cachedProperty.name = name;
                        cachedProperty.getMethod = readMethod;
                        cachedProperty.setMethod = writeMethod;
                        cachedProperty.serializer = serializer;
                        cachedProperty.setMethodType = writeMethod.getParameterTypes()[0];
                        arrayList.add(cachedProperty);
                    }
                }
            }
            this.properties = (CachedProperty[]) arrayList.toArray(new CachedProperty[arrayList.size()]);
            try {
                this.access = MethodAccess.get(cls);
                for (CachedProperty cachedProperty2 : this.properties) {
                    cachedProperty2.getterAccessIndex = ((MethodAccess) this.access).getIndex(cachedProperty2.getMethod.getName(), cachedProperty2.getMethod.getParameterTypes());
                    cachedProperty2.setterAccessIndex = ((MethodAccess) this.access).getIndex(cachedProperty2.setMethod.getName(), cachedProperty2.setMethod.getParameterTypes());
                }
            } catch (Throwable unused) {
            }
        } catch (IntrospectionException e) {
            throw new KryoException("Error getting bean info.", e);
        }
    }

    public void write(Kryo kryo, Output output, T t) {
        String str = ")";
        String str2 = " (";
        Class cls = t.getClass();
        int length = this.properties.length;
        int i = 0;
        while (i < length) {
            CachedProperty cachedProperty = this.properties[i];
            try {
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Write property: ");
                    sb.append(cachedProperty);
                    sb.append(str2);
                    sb.append(cls.getName());
                    sb.append(str);
                    Log.trace("kryo", sb.toString());
                }
                Object obj = cachedProperty.get(t);
                Serializer serializer = cachedProperty.serializer;
                if (serializer != null) {
                    kryo.writeObjectOrNull(output, obj, serializer);
                } else {
                    kryo.writeClassAndObject(output, obj);
                }
                i++;
            } catch (IllegalAccessException e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error accessing getter method: ");
                sb2.append(cachedProperty);
                sb2.append(str2);
                sb2.append(cls.getName());
                sb2.append(str);
                throw new KryoException(sb2.toString(), e);
            } catch (InvocationTargetException e2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error invoking getter method: ");
                sb3.append(cachedProperty);
                sb3.append(str2);
                sb3.append(cls.getName());
                sb3.append(str);
                throw new KryoException(sb3.toString(), e2);
            } catch (KryoException e3) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(cachedProperty);
                sb4.append(str2);
                sb4.append(cls.getName());
                sb4.append(str);
                e3.addTrace(sb4.toString());
                throw e3;
            } catch (RuntimeException e4) {
                KryoException kryoException = new KryoException((Throwable) e4);
                StringBuilder sb5 = new StringBuilder();
                sb5.append(cachedProperty);
                sb5.append(str2);
                sb5.append(cls.getName());
                sb5.append(str);
                kryoException.addTrace(sb5.toString());
                throw kryoException;
            }
        }
    }

    public T read(Kryo kryo, Input input, Class<T> cls) {
        Object obj;
        String str = ")";
        String str2 = " (";
        T newInstance = kryo.newInstance(cls);
        kryo.reference(newInstance);
        int length = this.properties.length;
        int i = 0;
        while (i < length) {
            CachedProperty cachedProperty = this.properties[i];
            try {
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Read property: ");
                    sb.append(cachedProperty);
                    sb.append(str2);
                    sb.append(newInstance.getClass());
                    sb.append(str);
                    Log.trace("kryo", sb.toString());
                }
                Serializer serializer = cachedProperty.serializer;
                if (serializer != null) {
                    obj = kryo.readObjectOrNull(input, cachedProperty.setMethodType, serializer);
                } else {
                    obj = kryo.readClassAndObject(input);
                }
                cachedProperty.set(newInstance, obj);
                i++;
            } catch (IllegalAccessException e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error accessing setter method: ");
                sb2.append(cachedProperty);
                sb2.append(str2);
                sb2.append(newInstance.getClass().getName());
                sb2.append(str);
                throw new KryoException(sb2.toString(), e);
            } catch (InvocationTargetException e2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error invoking setter method: ");
                sb3.append(cachedProperty);
                sb3.append(str2);
                sb3.append(newInstance.getClass().getName());
                sb3.append(str);
                throw new KryoException(sb3.toString(), e2);
            } catch (KryoException e3) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(cachedProperty);
                sb4.append(str2);
                sb4.append(newInstance.getClass().getName());
                sb4.append(str);
                e3.addTrace(sb4.toString());
                throw e3;
            } catch (RuntimeException e4) {
                KryoException kryoException = new KryoException((Throwable) e4);
                StringBuilder sb5 = new StringBuilder();
                sb5.append(cachedProperty);
                sb5.append(str2);
                sb5.append(newInstance.getClass().getName());
                sb5.append(str);
                kryoException.addTrace(sb5.toString());
                throw kryoException;
            }
        }
        return newInstance;
    }

    public T copy(Kryo kryo, T t) {
        String str = ")";
        String str2 = " (";
        T newInstance = kryo.newInstance(t.getClass());
        int length = this.properties.length;
        int i = 0;
        while (i < length) {
            CachedProperty cachedProperty = this.properties[i];
            try {
                cachedProperty.set(newInstance, cachedProperty.get(t));
                i++;
            } catch (KryoException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(cachedProperty);
                sb.append(str2);
                sb.append(newInstance.getClass().getName());
                sb.append(str);
                e.addTrace(sb.toString());
                throw e;
            } catch (RuntimeException e2) {
                KryoException kryoException = new KryoException((Throwable) e2);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(cachedProperty);
                sb2.append(str2);
                sb2.append(newInstance.getClass().getName());
                sb2.append(str);
                kryoException.addTrace(sb2.toString());
                throw kryoException;
            } catch (Exception e3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error copying bean property: ");
                sb3.append(cachedProperty);
                sb3.append(str2);
                sb3.append(newInstance.getClass().getName());
                sb3.append(str);
                throw new KryoException(sb3.toString(), e3);
            }
        }
        return newInstance;
    }
}
