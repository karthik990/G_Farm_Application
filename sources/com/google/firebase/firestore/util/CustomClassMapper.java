package com.google.firebase.firestore.util;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.ThrowOnExtraProperties;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class CustomClassMapper {
    private static final int MAX_DEPTH = 500;
    private static final ConcurrentMap<Class<?>, BeanMapper<?>> mappers = new ConcurrentHashMap();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class BeanMapper<T> {
        /* access modifiers changed from: private */
        public final Class<T> clazz;
        private final Constructor<T> constructor;
        private final Map<String, Field> fields = new HashMap();
        private final Map<String, Method> getters = new HashMap();
        private final Map<String, String> properties = new HashMap();
        private final HashSet<String> serverTimestamps = new HashSet<>();
        private final Map<String, Method> setters = new HashMap();
        private final boolean throwOnUnknownProperties;
        private final boolean warnOnUnknownProperties;

        BeanMapper(Class<T> cls) {
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
                        applyGetterAnnotations(method);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Found conflicting getters for name ");
                        sb.append(method.getName());
                        sb.append(" on class ");
                        sb.append(cls.getName());
                        throw new RuntimeException(sb.toString());
                    }
                }
            }
            for (Field field : cls.getFields()) {
                if (shouldIncludeField(field)) {
                    addProperty(propertyName(field));
                    applyFieldAnnotations(field);
                }
            }
            Class<T> cls2 = cls;
            do {
                for (Method method2 : cls2.getDeclaredMethods()) {
                    if (shouldIncludeSetter(method2)) {
                        String propertyName2 = propertyName(method2);
                        String str = (String) this.properties.get(propertyName2.toLowerCase(Locale.US));
                        if (str == null) {
                            continue;
                        } else if (str.equals(propertyName2)) {
                            Method method3 = (Method) this.setters.get(propertyName2);
                            if (method3 == null) {
                                method2.setAccessible(true);
                                this.setters.put(propertyName2, method2);
                                applySetterAnnotations(method2);
                            } else if (!isSetterOverride(method2, method3)) {
                                if (cls2 == cls) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("Class ");
                                    sb2.append(cls.getName());
                                    sb2.append(" has multiple setter overloads with name ");
                                    sb2.append(method2.getName());
                                    throw new RuntimeException(sb2.toString());
                                }
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("Found conflicting setters with name: ");
                                sb3.append(method2.getName());
                                sb3.append(" (conflicts with ");
                                sb3.append(method3.getName());
                                sb3.append(" defined on ");
                                sb3.append(method3.getDeclaringClass().getName());
                                sb3.append(")");
                                throw new RuntimeException(sb3.toString());
                            }
                        } else {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Found setter on ");
                            sb4.append(cls2.getName());
                            sb4.append(" with invalid case-sensitive name: ");
                            sb4.append(method2.getName());
                            throw new RuntimeException(sb4.toString());
                        }
                    }
                }
                for (Field field2 : cls2.getDeclaredFields()) {
                    String propertyName3 = propertyName(field2);
                    if (this.properties.containsKey(propertyName3.toLowerCase(Locale.US)) && !this.fields.containsKey(propertyName3)) {
                        field2.setAccessible(true);
                        this.fields.put(propertyName3, field2);
                        applyFieldAnnotations(field2);
                    }
                }
                cls2 = cls2.getSuperclass();
                if (cls2 == null) {
                    break;
                }
            } while (!cls2.equals(Object.class));
            if (this.properties.isEmpty()) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("No properties to serialize found on class ");
                sb5.append(cls.getName());
                throw new RuntimeException(sb5.toString());
            }
        }

        private void addProperty(String str) {
            String str2 = (String) this.properties.put(str.toLowerCase(Locale.US), str);
            if (str2 != null && !str.equals(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Found two getters or fields with conflicting case sensitivity for property: ");
                sb.append(str.toLowerCase(Locale.US));
                throw new RuntimeException(sb.toString());
            }
        }

        /* access modifiers changed from: 0000 */
        public T deserialize(Map<String, Object> map, ErrorPath errorPath) {
            return deserialize(map, Collections.emptyMap(), errorPath);
        }

        /* access modifiers changed from: 0000 */
        public T deserialize(Map<String, Object> map, Map<TypeVariable<Class<T>>, Type> map2, ErrorPath errorPath) {
            Constructor<T> constructor2 = this.constructor;
            if (constructor2 != null) {
                T newInstance = ApiUtil.newInstance(constructor2);
                for (Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    ErrorPath child = errorPath.child(str);
                    if (this.setters.containsKey(str)) {
                        Method method = (Method) this.setters.get(str);
                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        if (genericParameterTypes.length == 1) {
                            ApiUtil.invoke(method, newInstance, CustomClassMapper.deserializeToType(entry.getValue(), resolveType(genericParameterTypes[0], map2), child));
                        } else {
                            throw CustomClassMapper.deserializeError(child, "Setter does not have exactly one parameter");
                        }
                    } else if (this.fields.containsKey(str)) {
                        Field field = (Field) this.fields.get(str);
                        try {
                            field.set(newInstance, CustomClassMapper.deserializeToType(entry.getValue(), resolveType(field.getGenericType(), map2), child));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("No setter/field for ");
                        sb.append(str);
                        sb.append(" found on class ");
                        sb.append(this.clazz.getName());
                        String sb2 = sb.toString();
                        if (this.properties.containsKey(str.toLowerCase(Locale.US))) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append(" (fields/setters are case sensitive!)");
                            sb2 = sb3.toString();
                        }
                        if (this.throwOnUnknownProperties) {
                            throw new RuntimeException(sb2);
                        } else if (this.warnOnUnknownProperties) {
                            Logger.warn(CustomClassMapper.class.getSimpleName(), "%s", sb2);
                        }
                    }
                }
                return newInstance;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Class ");
            sb4.append(this.clazz.getName());
            sb4.append(" does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped");
            throw CustomClassMapper.deserializeError(errorPath, sb4.toString());
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

        /* access modifiers changed from: 0000 */
        public Map<String, Object> serialize(T t, ErrorPath errorPath) {
            Object obj;
            Object obj2;
            if (this.clazz.isAssignableFrom(t.getClass())) {
                HashMap hashMap = new HashMap();
                for (String str : this.properties.values()) {
                    if (this.getters.containsKey(str)) {
                        obj = ApiUtil.invoke((Method) this.getters.get(str), t, new Object[0]);
                    } else {
                        Field field = (Field) this.fields.get(str);
                        if (field != null) {
                            try {
                                obj = field.get(t);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Bean property without field or getter: ");
                            sb.append(str);
                            throw new IllegalStateException(sb.toString());
                        }
                    }
                    if (!this.serverTimestamps.contains(str) || obj != null) {
                        obj2 = CustomClassMapper.serialize(obj, errorPath.child(str));
                    } else {
                        obj2 = FieldValue.serverTimestamp();
                    }
                    hashMap.put(str, obj2);
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

        private void applyFieldAnnotations(Field field) {
            if (field.isAnnotationPresent(ServerTimestamp.class)) {
                Class<Timestamp> type = field.getType();
                if (type == Date.class || type == Timestamp.class) {
                    this.serverTimestamps.add(propertyName(field));
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Field ");
                sb.append(field.getName());
                sb.append(" is annotated with @ServerTimestamp but is ");
                sb.append(type);
                sb.append(" instead of Date or Timestamp.");
                throw new IllegalArgumentException(sb.toString());
            }
        }

        private void applyGetterAnnotations(Method method) {
            if (method.isAnnotationPresent(ServerTimestamp.class)) {
                Class<Timestamp> returnType = method.getReturnType();
                if (returnType == Date.class || returnType == Timestamp.class) {
                    this.serverTimestamps.add(propertyName(method));
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Method ");
                sb.append(method.getName());
                sb.append(" is annotated with @ServerTimestamp but returns ");
                sb.append(returnType);
                sb.append(" instead of Date or Timestamp.");
                throw new IllegalArgumentException(sb.toString());
            }
        }

        private void applySetterAnnotations(Method method) {
            if (method.isAnnotationPresent(ServerTimestamp.class)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Method ");
                sb.append(method.getName());
                sb.append(" is annotated with @ServerTimestamp but should not be. @ServerTimestamp can only be applied to fields and getters, not setters.");
                throw new IllegalArgumentException(sb.toString());
            }
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
            CustomClassMapper.hardAssert(method.getDeclaringClass().isAssignableFrom(method2.getDeclaringClass()), "Expected override from a base class");
            String str = "Expected void return type";
            CustomClassMapper.hardAssert(method.getReturnType().equals(Void.TYPE), str);
            CustomClassMapper.hardAssert(method2.getReturnType().equals(Void.TYPE), str);
            Class[] parameterTypes = method.getParameterTypes();
            Class[] parameterTypes2 = method2.getParameterTypes();
            String str2 = "Expected exactly one parameter";
            CustomClassMapper.hardAssert(parameterTypes.length == 1, str2);
            CustomClassMapper.hardAssert(parameterTypes2.length == 1, str2);
            if (!method.getName().equals(method2.getName()) || !parameterTypes[0].equals(parameterTypes2[0])) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: private */
        public static String propertyName(Field field) {
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

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ErrorPath {
        static final ErrorPath EMPTY = new ErrorPath(null, null, 0);
        private final int length;
        private final String name;
        private final ErrorPath parent;

        ErrorPath(ErrorPath errorPath, String str, int i) {
            this.parent = errorPath;
            this.name = str;
            this.length = i;
        }

        /* access modifiers changed from: 0000 */
        public int getLength() {
            return this.length;
        }

        /* access modifiers changed from: 0000 */
        public ErrorPath child(String str) {
            return new ErrorPath(this, str, this.length + 1);
        }

        public String toString() {
            int i = this.length;
            if (i == 0) {
                return "";
            }
            if (i == 1) {
                return this.name;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.parent.toString());
            sb.append(".");
            sb.append(this.name);
            return sb.toString();
        }
    }

    private static void hardAssert(boolean z) {
        hardAssert(z, "Internal inconsistency");
    }

    /* access modifiers changed from: private */
    public static void hardAssert(boolean z, String str) {
        if (!z) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hard assert failed: ");
            sb.append(str);
            throw new RuntimeException(sb.toString());
        }
    }

    public static Object convertToPlainJavaTypes(Object obj) {
        return serialize(obj);
    }

    public static Map<String, Object> convertToPlainJavaTypes(Map<?, Object> map) {
        Object serialize = serialize(map);
        hardAssert(serialize instanceof Map);
        return (Map) serialize;
    }

    public static <T> T convertToCustomClass(Object obj, Class<T> cls) {
        return deserializeToClass(obj, cls, ErrorPath.EMPTY);
    }

    private static <T> Object serialize(T t) {
        return serialize(t, ErrorPath.EMPTY);
    }

    /* access modifiers changed from: private */
    public static <T> Object serialize(T t, ErrorPath errorPath) {
        if (errorPath.getLength() > 500) {
            throw serializeError(errorPath, "Exceeded maximum depth of 500, which likely indicates there's an object cycle");
        } else if (t == null) {
            return null;
        } else {
            if (t instanceof Number) {
                if ((t instanceof Long) || (t instanceof Integer) || (t instanceof Double) || (t instanceof Float)) {
                    return t;
                }
                throw serializeError(errorPath, String.format("Numbers of type %s are not supported, please use an int, long, float or double", new Object[]{t.getClass().getSimpleName()}));
            } else if ((t instanceof String) || (t instanceof Boolean)) {
                return t;
            } else {
                if (t instanceof Character) {
                    throw serializeError(errorPath, "Characters are not supported, please use Strings");
                } else if (t instanceof Map) {
                    HashMap hashMap = new HashMap();
                    for (Entry entry : ((Map) t).entrySet()) {
                        Object key = entry.getKey();
                        if (key instanceof String) {
                            String str = (String) key;
                            hashMap.put(str, serialize(entry.getValue(), errorPath.child(str)));
                        } else {
                            throw serializeError(errorPath, "Maps with non-string keys are not supported");
                        }
                    }
                    return hashMap;
                } else if (t instanceof Collection) {
                    if (t instanceof List) {
                        List list = (List) t;
                        ArrayList arrayList = new ArrayList(list.size());
                        for (int i = 0; i < list.size(); i++) {
                            Object obj = list.get(i);
                            StringBuilder sb = new StringBuilder();
                            sb.append("[");
                            sb.append(i);
                            sb.append("]");
                            arrayList.add(serialize(obj, errorPath.child(sb.toString())));
                        }
                        return arrayList;
                    }
                    throw serializeError(errorPath, "Serializing Collections is not supported, please use Lists instead");
                } else if (t.getClass().isArray()) {
                    throw serializeError(errorPath, "Serializing Arrays is not supported, please use Lists instead");
                } else if (t instanceof Enum) {
                    String name = ((Enum) t).name();
                    try {
                        return BeanMapper.propertyName(t.getClass().getField(name));
                    } catch (NoSuchFieldException unused) {
                        return name;
                    }
                } else {
                    if (!(t instanceof Date) && !(t instanceof Timestamp) && !(t instanceof GeoPoint) && !(t instanceof Blob) && !(t instanceof DocumentReference) && !(t instanceof FieldValue)) {
                        t = loadOrCreateBeanMapperForClass(t.getClass()).serialize(t, errorPath);
                    }
                    return t;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static <T> T deserializeToType(Object obj, Type type, ErrorPath errorPath) {
        if (obj == null) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            return deserializeToParameterizedType(obj, (ParameterizedType) type, errorPath);
        }
        if (type instanceof Class) {
            return deserializeToClass(obj, (Class) type, errorPath);
        }
        boolean z = true;
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            if (wildcardType.getLowerBounds().length <= 0) {
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length <= 0) {
                    z = false;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected type bounds on wildcard ");
                sb.append(type);
                hardAssert(z, sb.toString());
                return deserializeToType(obj, upperBounds[0], errorPath);
            }
            throw deserializeError(errorPath, "Generic lower-bounded wildcard types are not supported");
        } else if (type instanceof TypeVariable) {
            Type[] bounds = ((TypeVariable) type).getBounds();
            if (bounds.length <= 0) {
                z = false;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unexpected type bounds on type variable ");
            sb2.append(type);
            hardAssert(z, sb2.toString());
            return deserializeToType(obj, bounds[0], errorPath);
        } else if (type instanceof GenericArrayType) {
            throw deserializeError(errorPath, "Generic Arrays are not supported, please use Lists instead");
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unknown type encountered: ");
            sb3.append(type);
            throw deserializeError(errorPath, sb3.toString());
        }
    }

    private static <T> T deserializeToClass(Object obj, Class<T> cls, ErrorPath errorPath) {
        if (obj == null) {
            return null;
        }
        if (cls.isPrimitive() || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls)) {
            return deserializeToPrimitive(obj, cls, errorPath);
        }
        if (String.class.isAssignableFrom(cls)) {
            return convertString(obj, errorPath);
        }
        if (Date.class.isAssignableFrom(cls)) {
            return convertDate(obj, errorPath);
        }
        if (Timestamp.class.isAssignableFrom(cls)) {
            return convertTimestamp(obj, errorPath);
        }
        if (Blob.class.isAssignableFrom(cls)) {
            return convertBlob(obj, errorPath);
        }
        if (GeoPoint.class.isAssignableFrom(cls)) {
            return convertGeoPoint(obj, errorPath);
        }
        if (DocumentReference.class.isAssignableFrom(cls)) {
            return convertDocumentReference(obj, errorPath);
        }
        if (cls.isArray()) {
            throw deserializeError(errorPath, "Converting to Arrays is not supported, please use Lists instead");
        } else if (cls.getTypeParameters().length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Class ");
            sb.append(cls.getName());
            sb.append(" has generic type parameters, please use GenericTypeIndicator instead");
            throw deserializeError(errorPath, sb.toString());
        } else if (cls.equals(Object.class)) {
            return obj;
        } else {
            if (cls.isEnum()) {
                return deserializeToEnum(obj, cls, errorPath);
            }
            return convertBean(obj, cls, errorPath);
        }
    }

    private static <T> T deserializeToParameterizedType(Object obj, ParameterizedType parameterizedType, ErrorPath errorPath) {
        Class cls = (Class) parameterizedType.getRawType();
        int i = 0;
        if (List.class.isAssignableFrom(cls)) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List list = (List) obj;
                T arrayList = new ArrayList(list.size());
                while (i < list.size()) {
                    Object obj2 = list.get(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    sb.append(i);
                    sb.append("]");
                    arrayList.add(deserializeToType(obj2, type, errorPath.child(sb.toString())));
                    i++;
                }
                return arrayList;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Expected a List, but got a ");
            sb2.append(obj.getClass());
            throw deserializeError(errorPath, sb2.toString());
        } else if (Map.class.isAssignableFrom(cls)) {
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            Type type3 = parameterizedType.getActualTypeArguments()[1];
            if (type2.equals(String.class)) {
                Map expectMap = expectMap(obj, errorPath);
                T hashMap = new HashMap();
                for (Entry entry : expectMap.entrySet()) {
                    hashMap.put((String) entry.getKey(), deserializeToType(entry.getValue(), type3, errorPath.child((String) entry.getKey())));
                }
                return hashMap;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Only Maps with string keys are supported, but found Map with key type ");
            sb3.append(type2);
            throw deserializeError(errorPath, sb3.toString());
        } else if (!Collection.class.isAssignableFrom(cls)) {
            Map expectMap2 = expectMap(obj, errorPath);
            BeanMapper loadOrCreateBeanMapperForClass = loadOrCreateBeanMapperForClass(cls);
            HashMap hashMap2 = new HashMap();
            TypeVariable[] typeParameters = loadOrCreateBeanMapperForClass.clazz.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == typeParameters.length) {
                while (i < typeParameters.length) {
                    hashMap2.put(typeParameters[i], actualTypeArguments[i]);
                    i++;
                }
                return loadOrCreateBeanMapperForClass.deserialize(expectMap2, hashMap2, errorPath);
            }
            throw new IllegalStateException("Mismatched lengths for type variables and actual types");
        } else {
            throw deserializeError(errorPath, "Collections are not supported, please use Lists instead");
        }
    }

    private static <T> T deserializeToPrimitive(Object obj, Class<T> cls, ErrorPath errorPath) {
        if (Integer.class.isAssignableFrom(cls) || Integer.TYPE.isAssignableFrom(cls)) {
            return convertInteger(obj, errorPath);
        }
        if (Boolean.class.isAssignableFrom(cls) || Boolean.TYPE.isAssignableFrom(cls)) {
            return convertBoolean(obj, errorPath);
        }
        if (Double.class.isAssignableFrom(cls) || Double.TYPE.isAssignableFrom(cls)) {
            return convertDouble(obj, errorPath);
        }
        if (Long.class.isAssignableFrom(cls) || Long.TYPE.isAssignableFrom(cls)) {
            return convertLong(obj, errorPath);
        }
        if (Float.class.isAssignableFrom(cls) || Float.TYPE.isAssignableFrom(cls)) {
            return Float.valueOf(convertDouble(obj, errorPath).floatValue());
        }
        throw deserializeError(errorPath, String.format("Deserializing values to %s is not supported", new Object[]{cls.getSimpleName()}));
    }

    private static <T> T deserializeToEnum(Object obj, Class<T> cls, ErrorPath errorPath) {
        if (obj instanceof String) {
            String str = (String) obj;
            Field[] fields = cls.getFields();
            int length = fields.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    Field field = fields[i];
                    if (field.isEnumConstant() && str.equals(BeanMapper.propertyName(field))) {
                        str = field.getName();
                        break;
                    }
                    i++;
                }
            }
            try {
                return Enum.valueOf(cls, str);
            } catch (IllegalArgumentException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not find enum value of ");
                sb.append(cls.getName());
                sb.append(" for value \"");
                sb.append(str);
                sb.append("\"");
                throw deserializeError(errorPath, sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Expected a String while deserializing to enum ");
            sb2.append(cls);
            sb2.append(" but got a ");
            sb2.append(obj.getClass());
            throw deserializeError(errorPath, sb2.toString());
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

    private static Map<String, Object> expectMap(Object obj, ErrorPath errorPath) {
        if (obj instanceof Map) {
            return (Map) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected a Map while deserializing, but got a ");
        sb.append(obj.getClass());
        throw deserializeError(errorPath, sb.toString());
    }

    private static Integer convertInteger(Object obj, ErrorPath errorPath) {
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
            throw deserializeError(errorPath, sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Failed to convert a value of type ");
        sb2.append(obj.getClass().getName());
        sb2.append(" to int");
        throw deserializeError(errorPath, sb2.toString());
    }

    private static Long convertLong(Object obj, ErrorPath errorPath) {
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
            throw deserializeError(errorPath, sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Failed to convert a value of type ");
        sb2.append(obj.getClass().getName());
        sb2.append(" to long");
        throw deserializeError(errorPath, sb2.toString());
    }

    private static Double convertDouble(Object obj, ErrorPath errorPath) {
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
            throw deserializeError(errorPath, sb.toString());
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to convert a value of type ");
            sb2.append(obj.getClass().getName());
            sb2.append(" to double");
            throw deserializeError(errorPath, sb2.toString());
        }
    }

    private static Boolean convertBoolean(Object obj, ErrorPath errorPath) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to boolean");
        throw deserializeError(errorPath, sb.toString());
    }

    private static String convertString(Object obj, ErrorPath errorPath) {
        if (obj instanceof String) {
            return (String) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to String");
        throw deserializeError(errorPath, sb.toString());
    }

    private static Date convertDate(Object obj, ErrorPath errorPath) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toDate();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to Date");
        throw deserializeError(errorPath, sb.toString());
    }

    private static Timestamp convertTimestamp(Object obj, ErrorPath errorPath) {
        if (obj instanceof Timestamp) {
            return (Timestamp) obj;
        }
        if (obj instanceof Date) {
            return new Timestamp((Date) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to Timestamp");
        throw deserializeError(errorPath, sb.toString());
    }

    private static Blob convertBlob(Object obj, ErrorPath errorPath) {
        if (obj instanceof Blob) {
            return (Blob) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to Blob");
        throw deserializeError(errorPath, sb.toString());
    }

    private static GeoPoint convertGeoPoint(Object obj, ErrorPath errorPath) {
        if (obj instanceof GeoPoint) {
            return (GeoPoint) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to GeoPoint");
        throw deserializeError(errorPath, sb.toString());
    }

    private static DocumentReference convertDocumentReference(Object obj, ErrorPath errorPath) {
        if (obj instanceof DocumentReference) {
            return (DocumentReference) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to convert value of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to DocumentReference");
        throw deserializeError(errorPath, sb.toString());
    }

    private static <T> T convertBean(Object obj, Class<T> cls, ErrorPath errorPath) {
        BeanMapper loadOrCreateBeanMapperForClass = loadOrCreateBeanMapperForClass(cls);
        if (obj instanceof Map) {
            return loadOrCreateBeanMapperForClass.deserialize(expectMap(obj, errorPath), errorPath);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't convert object of type ");
        sb.append(obj.getClass().getName());
        sb.append(" to type ");
        sb.append(cls.getName());
        throw deserializeError(errorPath, sb.toString());
    }

    private static IllegalArgumentException serializeError(ErrorPath errorPath, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not serialize object. ");
        sb.append(str);
        String sb2 = sb.toString();
        if (errorPath.getLength() > 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(" (found in field '");
            sb3.append(errorPath.toString());
            sb3.append("')");
            sb2 = sb3.toString();
        }
        return new IllegalArgumentException(sb2);
    }

    /* access modifiers changed from: private */
    public static RuntimeException deserializeError(ErrorPath errorPath, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Could not deserialize object. ");
        sb.append(str);
        String sb2 = sb.toString();
        if (errorPath.getLength() > 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(" (found in field '");
            sb3.append(errorPath.toString());
            sb3.append("')");
            sb2 = sb3.toString();
        }
        return new RuntimeException(sb2);
    }
}
