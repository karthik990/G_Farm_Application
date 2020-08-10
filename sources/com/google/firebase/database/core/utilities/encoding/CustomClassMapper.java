package com.google.firebase.database.core.utilities.encoding;

import android.util.Log;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.core.utilities.Utilities;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class CustomClassMapper {
    private static final String LOG_TAG = "ClassMapper";
    private static final ConcurrentMap<Class<?>, BeanMapper<?>> mappers = new ConcurrentHashMap();

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class BeanMapper<T> {
        /* access modifiers changed from: private */
        public final Class<T> clazz;
        private final Constructor<T> constructor;
        private final Map<String, Field> fields = new HashMap();
        private final Map<String, Method> getters = new HashMap();
        private final Map<String, String> properties = new HashMap();
        private final Map<String, Method> setters = new HashMap();
        private final boolean throwOnUnknownProperties;
        private final boolean warnOnUnknownProperties;

        public BeanMapper(Class<T> cls) {
            Constructor<T> constructor2;
            Method[] methods;
            Field[] fields2;
            Method[] declaredMethods;
            Field[] declaredFields;
            this.clazz = cls;
            this.throwOnUnknownProperties = cls.isAnnotationPresent(ThrowOnExtraProperties.class);
            this.warnOnUnknownProperties = !cls.isAnnotationPresent(IgnoreExtraProperties.class);
            try {
                constructor2 = cls.getDeclaredConstructor(new Class[0]);
                constructor2.setAccessible(true);
            } catch (NoSuchMethodException unused) {
                constructor2 = null;
            }
            this.constructor = constructor2;
            for (Method method : cls.getMethods()) {
                if (shouldIncludeGetter(method)) {
                    String propertyName = propertyName(method);
                    addProperty(propertyName);
                    method.setAccessible(true);
                    if (!this.getters.containsKey(propertyName)) {
                        this.getters.put(propertyName, method);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Found conflicting getters for name: ");
                        sb.append(method.getName());
                        throw new DatabaseException(sb.toString());
                    }
                }
            }
            for (Field field : cls.getFields()) {
                if (shouldIncludeField(field)) {
                    addProperty(propertyName(field));
                }
            }
            Class<T> cls2 = cls;
            do {
                for (Method method2 : cls2.getDeclaredMethods()) {
                    if (shouldIncludeSetter(method2)) {
                        String propertyName2 = propertyName(method2);
                        String str = (String) this.properties.get(propertyName2.toLowerCase());
                        if (str == null) {
                            continue;
                        } else if (str.equals(propertyName2)) {
                            Method method3 = (Method) this.setters.get(propertyName2);
                            if (method3 == null) {
                                method2.setAccessible(true);
                                this.setters.put(propertyName2, method2);
                            } else if (!isSetterOverride(method2, method3)) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("Found a conflicting setters with name: ");
                                sb2.append(method2.getName());
                                sb2.append(" (conflicts with ");
                                sb2.append(method3.getName());
                                sb2.append(" defined on ");
                                sb2.append(method3.getDeclaringClass().getName());
                                sb2.append(")");
                                throw new DatabaseException(sb2.toString());
                            }
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Found setter with invalid case-sensitive name: ");
                            sb3.append(method2.getName());
                            throw new DatabaseException(sb3.toString());
                        }
                    }
                }
                for (Field field2 : cls2.getDeclaredFields()) {
                    String propertyName3 = propertyName(field2);
                    if (this.properties.containsKey(propertyName3.toLowerCase()) && !this.fields.containsKey(propertyName3)) {
                        field2.setAccessible(true);
                        this.fields.put(propertyName3, field2);
                    }
                }
                cls2 = cls2.getSuperclass();
                if (cls2 == null) {
                    break;
                }
            } while (!cls2.equals(Object.class));
            if (this.properties.isEmpty()) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("No properties to serialize found on class ");
                sb4.append(cls.getName());
                throw new DatabaseException(sb4.toString());
            }
        }

        private void addProperty(String str) {
            String str2 = (String) this.properties.put(str.toLowerCase(), str);
            if (str2 != null && !str.equals(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Found two getters or fields with conflicting case sensitivity for property: ");
                sb.append(str.toLowerCase());
                throw new DatabaseException(sb.toString());
            }
        }

        public T deserialize(Map<String, Object> map) {
            return deserialize(map, Collections.emptyMap());
        }

        public T deserialize(Map<String, Object> map, Map<TypeVariable<Class<T>>, Type> map2) {
            Constructor<T> constructor2 = this.constructor;
            if (constructor2 != null) {
                try {
                    T newInstance = constructor2.newInstance(new Object[0]);
                    for (Entry entry : map.entrySet()) {
                        String str = (String) entry.getKey();
                        if (this.setters.containsKey(str)) {
                            Method method = (Method) this.setters.get(str);
                            Type[] genericParameterTypes = method.getGenericParameterTypes();
                            if (genericParameterTypes.length == 1) {
                                try {
                                    method.invoke(newInstance, new Object[]{CustomClassMapper.deserializeToType(entry.getValue(), resolveType(genericParameterTypes[0], map2))});
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                } catch (InvocationTargetException e2) {
                                    throw new RuntimeException(e2);
                                }
                            } else {
                                throw new IllegalStateException("Setter does not have exactly one parameter");
                            }
                        } else if (this.fields.containsKey(str)) {
                            Field field = (Field) this.fields.get(str);
                            try {
                                field.set(newInstance, CustomClassMapper.deserializeToType(entry.getValue(), resolveType(field.getGenericType(), map2)));
                            } catch (IllegalAccessException e3) {
                                throw new RuntimeException(e3);
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("No setter/field for ");
                            sb.append(str);
                            sb.append(" found on class ");
                            sb.append(this.clazz.getName());
                            String sb2 = sb.toString();
                            if (this.properties.containsKey(str.toLowerCase())) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(sb2);
                                sb3.append(" (fields/setters are case sensitive!)");
                                sb2 = sb3.toString();
                            }
                            if (this.throwOnUnknownProperties) {
                                throw new DatabaseException(sb2);
                            } else if (this.warnOnUnknownProperties) {
                                Log.w(CustomClassMapper.LOG_TAG, sb2);
                            }
                        }
                    }
                    return newInstance;
                } catch (InstantiationException e4) {
                    throw new RuntimeException(e4);
                } catch (IllegalAccessException e5) {
                    throw new RuntimeException(e5);
                } catch (InvocationTargetException e6) {
                    throw new RuntimeException(e6);
                }
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Class ");
                sb4.append(this.clazz.getName());
                sb4.append(" does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped.");
                throw new DatabaseException(sb4.toString());
            }
        }

        private Type resolveType(Type type, Map<TypeVariable<Class<T>>, Type> map) {
            if (!(type instanceof TypeVariable)) {
                return type;
            }
            Type type2 = (Type) map.get(type);
            if (type2 != null) {
                return type2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Could not resolve type ");
            sb.append(type);
            throw new IllegalStateException(sb.toString());
        }

        public Map<String, Object> serialize(T t) {
            Object obj;
            if (this.clazz.isAssignableFrom(t.getClass())) {
                HashMap hashMap = new HashMap();
                for (String str : this.properties.values()) {
                    if (this.getters.containsKey(str)) {
                        try {
                            obj = ((Method) this.getters.get(str)).invoke(t, new Object[0]);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e2) {
                            throw new RuntimeException(e2);
                        }
                    } else {
                        Field field = (Field) this.fields.get(str);
                        if (field != null) {
                            try {
                                obj = field.get(t);
                            } catch (IllegalAccessException e3) {
                                throw new RuntimeException(e3);
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Bean property without field or getter:");
                            sb.append(str);
                            throw new IllegalStateException(sb.toString());
                        }
                    }
                    hashMap.put(str, CustomClassMapper.serialize(obj));
                }
                return hashMap;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Can't serialize object of class ");
            sb2.append(t.getClass());
            sb2.append(" with BeanMapper for class ");
            sb2.append(this.clazz);
            throw new IllegalArgumentException(sb2.toString());
        }

        private static boolean shouldIncludeGetter(Method method) {
            if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && !method.getDeclaringClass().equals(Object.class) && Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()) && !method.getReturnType().equals(Void.TYPE) && method.getParameterTypes().length == 0 && !method.isAnnotationPresent(Exclude.class)) {
                return true;
            }
            return false;
        }

        private static boolean shouldIncludeSetter(Method method) {
            if (method.getName().startsWith("set") && !method.getDeclaringClass().equals(Object.class) && !Modifier.isStatic(method.getModifiers()) && method.getReturnType().equals(Void.TYPE) && method.getParameterTypes().length == 1 && !method.isAnnotationPresent(Exclude.class)) {
                return true;
            }
            return false;
        }

        private static boolean shouldIncludeField(Field field) {
            if (!field.getDeclaringClass().equals(Object.class) && Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers()) && !field.isAnnotationPresent(Exclude.class)) {
                return true;
            }
            return false;
        }

        private static boolean isSetterOverride(Method method, Method method2) {
            Utilities.hardAssert(method.getDeclaringClass().isAssignableFrom(method2.getDeclaringClass()), "Expected override from a base class");
            String str = "Expected void return type";
            Utilities.hardAssert(method.getReturnType().equals(Void.TYPE), str);
            Utilities.hardAssert(method2.getReturnType().equals(Void.TYPE), str);
            Class[] parameterTypes = method.getParameterTypes();
            Class[] parameterTypes2 = method2.getParameterTypes();
            String str2 = "Expected exactly one parameter";
            Utilities.hardAssert(parameterTypes.length == 1, str2);
            Utilities.hardAssert(parameterTypes2.length == 1, str2);
            if (!method.getName().equals(method2.getName()) || !parameterTypes[0].equals(parameterTypes2[0])) {
                return false;
            }
            return true;
        }

        private static String propertyName(Field field) {
            String annotatedName = annotatedName(field);
            return annotatedName != null ? annotatedName : field.getName();
        }

        private static String propertyName(Method method) {
            String annotatedName = annotatedName(method);
            return annotatedName != null ? annotatedName : serializedName(method.getName());
        }

        private static String annotatedName(AccessibleObject accessibleObject) {
            if (accessibleObject.isAnnotationPresent(PropertyName.class)) {
                return ((PropertyName) accessibleObject.getAnnotation(PropertyName.class)).value();
            }
            return null;
        }

        private static String serializedName(String str) {
            String[] strArr;
            int i = 0;
            String str2 = null;
            for (String str3 : new String[]{"get", "set", "is"}) {
                if (str.startsWith(str3)) {
                    str2 = str3;
                }
            }
            if (str2 != null) {
                char[] charArray = str.substring(str2.length()).toCharArray();
                while (i < charArray.length && Character.isUpperCase(charArray[i])) {
                    charArray[i] = Character.toLowerCase(charArray[i]);
                    i++;
                }
                return new String(charArray);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown Bean prefix for method: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static Object convertToPlainJavaTypes(Object obj) {
        return serialize(obj);
    }

    public static Map<String, Object> convertToPlainJavaTypes(Map<String, Object> map) {
        Object serialize = serialize(map);
        Utilities.hardAssert(serialize instanceof Map);
        return (Map) serialize;
    }

    public static <T> T convertToCustomClass(Object obj, Class<T> cls) {
        return deserializeToClass(obj, cls);
    }

    public static <T> T convertToCustomClass(Object obj, GenericTypeIndicator<T> genericTypeIndicator) {
        Type genericSuperclass = genericTypeIndicator.getClass().getGenericSuperclass();
        String str = "Not a direct subclass of GenericTypeIndicator: ";
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            if (parameterizedType.getRawType().equals(GenericTypeIndicator.class)) {
                return deserializeToType(obj, parameterizedType.getActualTypeArguments()[0]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(genericSuperclass);
            throw new DatabaseException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(genericSuperclass);
        throw new DatabaseException(sb2.toString());
    }

    /* access modifiers changed from: private */
    public static <T> Object serialize(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof Number) {
            if ((t instanceof Float) || (t instanceof Double)) {
                Number number = (Number) t;
                double doubleValue = number.doubleValue();
                if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d || Math.floor(doubleValue) != doubleValue) {
                    return Double.valueOf(doubleValue);
                }
                return Long.valueOf(number.longValue());
            } else if ((t instanceof Long) || (t instanceof Integer)) {
                return t;
            } else {
                throw new DatabaseException(String.format("Numbers of type %s are not supported, please use an int, long, float or double", new Object[]{t.getClass().getSimpleName()}));
            }
        } else if ((t instanceof String) || (t instanceof Boolean)) {
            return t;
        } else {
            if (t instanceof Character) {
                throw new DatabaseException("Characters are not supported, please use Strings");
            } else if (t instanceof Map) {
                HashMap hashMap = new HashMap();
                for (Entry entry : ((Map) t).entrySet()) {
                    Object key = entry.getKey();
                    if (key instanceof String) {
                        hashMap.put((String) key, serialize(entry.getValue()));
                    } else {
                        throw new DatabaseException("Maps with non-string keys are not supported");
                    }
                }
                return hashMap;
            } else if (t instanceof Collection) {
                if (t instanceof List) {
                    List<Object> list = (List) t;
                    ArrayList arrayList = new ArrayList(list.size());
                    for (Object serialize : list) {
                        arrayList.add(serialize(serialize));
                    }
                    return arrayList;
                }
                throw new DatabaseException("Serializing Collections is not supported, please use Lists instead");
            } else if (t.getClass().isArray()) {
                throw new DatabaseException("Serializing Arrays is not supported, please use Lists instead");
            } else if (t instanceof Enum) {
                return ((Enum) t).name();
            } else {
                return loadOrCreateBeanMapperForClass(t.getClass()).serialize(t);
            }
        }
    }

    /* access modifiers changed from: private */
    public static <T> T deserializeToType(Object obj, Type type) {
        if (obj == null) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            return deserializeToParameterizedType(obj, (ParameterizedType) type);
        }
        if (type instanceof Class) {
            return deserializeToClass(obj, (Class) type);
        }
        if (type instanceof WildcardType) {
            throw new DatabaseException("Generic wildcard types are not supported");
        } else if (type instanceof GenericArrayType) {
            throw new DatabaseException("Generic Arrays are not supported, please use Lists instead");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown type encountered: ");
            sb.append(type);
            throw new IllegalStateException(sb.toString());
        }
    }

    private static <T> T deserializeToClass(Object obj, Class<T> cls) {
        if (obj == null) {
            return null;
        }
        if (cls.isPrimitive() || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls)) {
            return deserializeToPrimitive(obj, cls);
        }
        if (String.class.isAssignableFrom(cls)) {
            return convertString(obj);
        }
        if (cls.isArray()) {
            throw new DatabaseException("Converting to Arrays is not supported, please use Listsinstead");
        } else if (cls.getTypeParameters().length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Class ");
            sb.append(cls.getName());
            sb.append(" has generic type parameters, please use GenericTypeIndicator instead");
            throw new DatabaseException(sb.toString());
        } else if (cls.equals(Object.class)) {
            return obj;
        } else {
            if (cls.isEnum()) {
                return deserializeToEnum(obj, cls);
            }
            return convertBean(obj, cls);
        }
    }

    private static <T> T deserializeToParameterizedType(Object obj, ParameterizedType parameterizedType) {
        Class cls = (Class) parameterizedType.getRawType();
        if (List.class.isAssignableFrom(cls)) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                T arrayList = new ArrayList(list.size());
                for (Object deserializeToType : list) {
                    arrayList.add(deserializeToType(deserializeToType, type));
                }
                return arrayList;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Expected a List while deserializing, but got a ");
            sb.append(obj.getClass());
            throw new DatabaseException(sb.toString());
        } else if (Map.class.isAssignableFrom(cls)) {
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            Type type3 = parameterizedType.getActualTypeArguments()[1];
            if (type2.equals(String.class)) {
                Map expectMap = expectMap(obj);
                T hashMap = new HashMap();
                for (Entry entry : expectMap.entrySet()) {
                    hashMap.put((String) entry.getKey(), deserializeToType(entry.getValue(), type3));
                }
                return hashMap;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Only Maps with string keys are supported, but found Map with key type ");
            sb2.append(type2);
            throw new DatabaseException(sb2.toString());
        } else if (!Collection.class.isAssignableFrom(cls)) {
            Map expectMap2 = expectMap(obj);
            BeanMapper loadOrCreateBeanMapperForClass = loadOrCreateBeanMapperForClass(cls);
            HashMap hashMap2 = new HashMap();
            TypeVariable[] typeParameters = loadOrCreateBeanMapperForClass.clazz.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == typeParameters.length) {
                for (int i = 0; i < typeParameters.length; i++) {
                    hashMap2.put(typeParameters[i], actualTypeArguments[i]);
                }
                return loadOrCreateBeanMapperForClass.deserialize(expectMap2, hashMap2);
            }
            throw new IllegalStateException("Mismatched lengths for type variables and actual types");
        } else {
            throw new DatabaseException("Collections are not supported, please use Lists instead");
        }
    }

    private static <T> T deserializeToPrimitive(Object obj, Class<T> cls) {
        if (Integer.class.isAssignableFrom(cls) || Integer.TYPE.isAssignableFrom(cls)) {
            return convertInteger(obj);
        }
        if (Boolean.class.isAssignableFrom(cls) || Boolean.TYPE.isAssignableFrom(cls)) {
            return convertBoolean(obj);
        }
        if (Double.class.isAssignableFrom(cls) || Double.TYPE.isAssignableFrom(cls)) {
            return convertDouble(obj);
        }
        if (Long.class.isAssignableFrom(cls) || Long.TYPE.isAssignableFrom(cls)) {
            return convertLong(obj);
        }
        if (Float.class.isAssignableFrom(cls) || Float.TYPE.isAssignableFrom(cls)) {
            return Float.valueOf(convertDouble(obj).floatValue());
        }
        throw new DatabaseException(String.format("Deserializing values to %s is not supported", new Object[]{cls.getSimpleName()}));
    }

    private static <T> T deserializeToEnum(Object obj, Class<T> cls) {
        if (obj instanceof String) {
            String str = (String) obj;
            try {
                return Enum.valueOf(cls, str);
            } catch (IllegalArgumentException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not find enum value of ");
                sb.append(cls.getName());
                sb.append(" for value \"");
                sb.append(str);
                sb.append("\"");
                throw new DatabaseException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Expected a String while deserializing to enum ");
            sb2.append(cls);
            sb2.append(" but got a ");
            sb2.append(obj.getClass());
            throw new DatabaseException(sb2.toString());
        }
    }

    private static <T> BeanMapper<T> loadOrCreateBeanMapperForClass(Class<T> cls) {
        BeanMapper<T> beanMapper = (BeanMapper) mappers.get(cls);
        if (beanMapper != null) {
            return beanMapper;
        }
        BeanMapper<T> beanMapper2 = new BeanMapper<>(cls);
        mappers.put(cls, beanMapper2);
        return beanMapper2;
    }

    private static Map<String, Object> expectMap(Object obj) {
        if (obj instanceof Map) {
            return (Map) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected a Map while deserializing, but got a ");
        sb.append(obj.getClass());
        throw new DatabaseException(sb.toString());
    }

    private static Integer convertInteger(Object obj) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            Number number = (Number) obj;
            double doubleValue = number.doubleValue();
            if (doubleValue >= -2.147483648E9d && doubleValue <= 2.147483647E9d) {
                return Integer.valueOf(number.intValue());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Numeric value out of 32-bit integer range: ");
            sb.append(doubleValue);
            sb.append(". Did you mean to use a long or double instead of an int?");
            throw new DatabaseException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Failed to convert a value of type ");
        sb2.append(obj.getClass().getName());
        sb2.append(" to int");
        throw new DatabaseException(sb2.toString());
    }

    private static Long convertLong(Object obj) {
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).longValue());
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.doubleValue() >= -9.223372036854776E18d && d.doubleValue() <= 9.223372036854776E18d) {
                return Long.valueOf(d.longValue());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Numeric value out of 64-bit long range: ");
            sb.append(d);
            sb.append(". Did you mean to use a double instead of a long?");
            throw new DatabaseException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Failed to convert a value of type ");
        sb2.append(obj.getClass().getName());
        sb2.append(" to long");
        throw new DatabaseException(sb2.toString());
    }

    private static Double convertDouble(Object obj) {
        if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        }
        if (obj instanceof Long) {
            Long l = (Long) obj;
            Double valueOf = Double.valueOf(l.doubleValue());
            if (valueOf.longValue() == l.longValue()) {
                return valueOf;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Loss of precision while converting number to double: ");
            sb.append(obj);
            sb.append(". Did you mean to use a 64-bit long instead?");
            throw new DatabaseException(sb.toString());
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to convert a value of type ");
            sb2.append(obj.getClass().getName());
            sb2.append(" to double");
            throw new DatabaseException(sb2.toString());
        }
    }

    private static Boolean convertBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to boolean");
        throw new DatabaseException(sb.toString());
    }

    private static String convertString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to String");
        throw new DatabaseException(sb.toString());
    }

    private static <T> T convertBean(Object obj, Class<T> cls) {
        BeanMapper loadOrCreateBeanMapperForClass = loadOrCreateBeanMapperForClass(cls);
        if (obj instanceof Map) {
            return loadOrCreateBeanMapperForClass.deserialize(expectMap(obj));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't convert object of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to type ");
        sb.append(cls.getName());
        throw new DatabaseException(sb.toString());
    }
}
