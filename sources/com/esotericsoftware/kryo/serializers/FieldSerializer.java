package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.NotNull;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializerUnsafeUtil.Factory;
import com.esotericsoftware.kryo.util.IntArray;
import com.esotericsoftware.kryo.util.ObjectMap;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.reflectasm.FieldAccess;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class FieldSerializer<T> extends Serializer<T> implements Comparator<CachedField> {
    static CachedFieldFactory asmFieldFactory;
    static CachedFieldFactory objectFieldFactory;
    static Method sortFieldsByOffsetMethod;
    static boolean unsafeAvailable = true;
    static CachedFieldFactory unsafeFieldFactory;
    static Class<?> unsafeUtilClass;
    Object access;
    private FieldSerializerAnnotationsUtil annotationsUtil;
    final Class componentType;
    protected final FieldSerializerConfig config;
    private CachedField[] fields;
    private Class[] generics;
    private Generics genericsScope;
    private FieldSerializerGenericsUtil genericsUtil;
    private boolean hasObjectFields;
    final Kryo kryo;
    protected HashSet<CachedField> removedFields;
    private CachedField[] transientFields;
    final Class type;
    final TypeVariable[] typeParameters;
    private FieldSerializerUnsafeUtil unsafeUtil;
    private boolean useMemRegions;
    private boolean varIntsEnabled;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Bind {
        Class<? extends Serializer> value();
    }

    public static abstract class CachedField<X> {
        FieldAccess access;
        int accessIndex = -1;
        boolean canBeNull;
        Field field;
        long offset = -1;
        Serializer serializer;
        Class valueClass;
        boolean varIntsEnabled = true;

        public abstract void copy(Object obj, Object obj2);

        public abstract void read(Input input, Object obj);

        public abstract void write(Output output, Object obj);

        public void setClass(Class cls) {
            this.valueClass = cls;
            this.serializer = null;
        }

        public void setClass(Class cls, Serializer serializer2) {
            this.valueClass = cls;
            this.serializer = serializer2;
        }

        public void setSerializer(Serializer serializer2) {
            this.serializer = serializer2;
        }

        public Serializer getSerializer() {
            return this.serializer;
        }

        public void setCanBeNull(boolean z) {
            this.canBeNull = z;
        }

        public Field getField() {
            return this.field;
        }

        public String toString() {
            return this.field.getName();
        }
    }

    public interface CachedFieldFactory {
        CachedField createCachedField(Class cls, Field field, FieldSerializer fieldSerializer);
    }

    public interface CachedFieldNameStrategy {
        public static final CachedFieldNameStrategy DEFAULT = new CachedFieldNameStrategy() {
            public String getName(CachedField cachedField) {
                return cachedField.field.getName();
            }
        };
        public static final CachedFieldNameStrategy EXTENDED = new CachedFieldNameStrategy() {
            public String getName(CachedField cachedField) {
                StringBuilder sb = new StringBuilder();
                sb.append(cachedField.field.getDeclaringClass().getSimpleName());
                sb.append(".");
                sb.append(cachedField.field.getName());
                return sb.toString();
            }
        };

        String getName(CachedField cachedField);
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Optional {
        String value();
    }

    /* access modifiers changed from: protected */
    public void initializeCachedFields() {
    }

    static {
        try {
            unsafeUtilClass = FieldSerializer.class.getClassLoader().loadClass("com.esotericsoftware.kryo.util.UnsafeUtil");
            Method method = unsafeUtilClass.getMethod("unsafe", new Class[0]);
            sortFieldsByOffsetMethod = unsafeUtilClass.getMethod("sortFieldsByOffset", new Class[]{List.class});
            if (method.invoke(null, new Object[0]) != null) {
            }
        } catch (Throwable unused) {
            if (Log.TRACE) {
                Log.trace("kryo", "sun.misc.Unsafe is unavailable.");
            }
        }
    }

    public FieldSerializer(Kryo kryo2, Class cls) {
        this(kryo2, cls, null);
    }

    public FieldSerializer(Kryo kryo2, Class cls, Class[] clsArr) {
        this(kryo2, cls, clsArr, kryo2.getFieldSerializerConfig().clone());
    }

    protected FieldSerializer(Kryo kryo2, Class cls, Class[] clsArr, FieldSerializerConfig fieldSerializerConfig) {
        this.fields = new CachedField[0];
        this.transientFields = new CachedField[0];
        this.removedFields = new HashSet<>();
        this.useMemRegions = false;
        this.hasObjectFields = false;
        this.varIntsEnabled = true;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Optimize ints: ");
            sb.append(this.varIntsEnabled);
            Log.trace("kryo", sb.toString());
        }
        this.config = fieldSerializerConfig;
        this.kryo = kryo2;
        this.type = cls;
        this.generics = clsArr;
        this.typeParameters = cls.getTypeParameters();
        TypeVariable[] typeVariableArr = this.typeParameters;
        if (typeVariableArr == null || typeVariableArr.length == 0) {
            this.componentType = cls.getComponentType();
        } else {
            this.componentType = null;
        }
        this.genericsUtil = new FieldSerializerGenericsUtil(this);
        this.unsafeUtil = Factory.getInstance(this);
        this.annotationsUtil = new FieldSerializerAnnotationsUtil(this);
        rebuildCachedFields();
    }

    /* access modifiers changed from: protected */
    public void rebuildCachedFields() {
        rebuildCachedFields(false);
    }

    /* access modifiers changed from: protected */
    public void rebuildCachedFields(boolean z) {
        List list;
        List list2;
        if (Log.TRACE && this.generics != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Generic type parameters: ");
            sb.append(Arrays.toString(this.generics));
            Log.trace("kryo", sb.toString());
        }
        if (this.type.isInterface()) {
            this.fields = new CachedField[0];
            return;
        }
        this.hasObjectFields = false;
        if (this.config.isOptimizedGenerics()) {
            this.genericsScope = this.genericsUtil.buildGenericsScope(this.type, this.generics);
            if (this.genericsScope != null) {
                this.kryo.getGenericsResolver().pushScope(this.type, this.genericsScope);
            }
        }
        IntArray intArray = new IntArray();
        if (!z) {
            List arrayList = new ArrayList();
            for (Class<Object> cls = this.type; cls != Object.class; cls = cls.getSuperclass()) {
                Field[] declaredFields = cls.getDeclaredFields();
                if (declaredFields != null) {
                    for (Field field : declaredFields) {
                        if (!Modifier.isStatic(field.getModifiers())) {
                            arrayList.add(field);
                        }
                    }
                }
            }
            ObjectMap context = this.kryo.getContext();
            if (this.useMemRegions && !this.config.isUseAsm() && unsafeAvailable) {
                try {
                    arrayList = Arrays.asList((Field[]) sortFieldsByOffsetMethod.invoke(null, new Object[]{arrayList}));
                } catch (Exception e) {
                    throw new RuntimeException("Cannot invoke UnsafeUtil.sortFieldsByOffset()", e);
                }
            }
            list = buildValidFields(false, arrayList, context, intArray);
            list2 = buildValidFields(true, arrayList, context, intArray);
            if (this.config.isUseAsm() && !Util.IS_ANDROID && Modifier.isPublic(this.type.getModifiers()) && intArray.indexOf(1) != -1) {
                try {
                    this.access = FieldAccess.get(this.type);
                } catch (RuntimeException unused) {
                }
            }
        } else {
            list = buildValidFieldsFromCachedFields(this.fields, intArray);
            list2 = buildValidFieldsFromCachedFields(this.transientFields, intArray);
        }
        ArrayList arrayList2 = new ArrayList(list.size());
        ArrayList arrayList3 = new ArrayList(list2.size());
        createCachedFields(intArray, list, arrayList2, 0);
        createCachedFields(intArray, list2, arrayList3, list.size());
        Collections.sort(arrayList2, this);
        this.fields = (CachedField[]) arrayList2.toArray(new CachedField[arrayList2.size()]);
        Collections.sort(arrayList3, this);
        this.transientFields = (CachedField[]) arrayList3.toArray(new CachedField[arrayList3.size()]);
        initializeCachedFields();
        if (this.genericsScope != null) {
            this.kryo.getGenericsResolver().popScope();
        }
        if (!z) {
            Iterator it = this.removedFields.iterator();
            while (it.hasNext()) {
                removeField((CachedField) it.next());
            }
        }
        this.annotationsUtil.processAnnotatedFields(this);
    }

    private List<Field> buildValidFieldsFromCachedFields(CachedField[] cachedFieldArr, IntArray intArray) {
        ArrayList arrayList = new ArrayList(cachedFieldArr.length);
        for (CachedField cachedField : cachedFieldArr) {
            arrayList.add(cachedField.field);
            intArray.add(cachedField.accessIndex > -1 ? 1 : 0);
        }
        return arrayList;
    }

    private List<Field> buildValidFields(boolean z, List<Field> list, ObjectMap objectMap, IntArray intArray) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Field field = (Field) list.get(i);
            int modifiers = field.getModifiers();
            if (Modifier.isTransient(modifiers) == z && !Modifier.isStatic(modifiers) && (!field.isSynthetic() || !this.config.isIgnoreSyntheticFields())) {
                int i2 = 1;
                if (!field.isAccessible()) {
                    if (this.config.isSetFieldsAsAccessible()) {
                        try {
                            field.setAccessible(true);
                        } catch (AccessControlException unused) {
                        }
                    }
                }
                Optional optional = (Optional) field.getAnnotation(Optional.class);
                if (optional == null || objectMap.containsKey(optional.value())) {
                    arrayList.add(field);
                    if (Modifier.isFinal(modifiers) || !Modifier.isPublic(modifiers) || !Modifier.isPublic(field.getType().getModifiers())) {
                        i2 = 0;
                    }
                    intArray.add(i2);
                }
            }
        }
        return arrayList;
    }

    private void createCachedFields(IntArray intArray, List<Field> list, List<CachedField> list2, int i) {
        if (this.config.isUseAsm() || !this.useMemRegions) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Field field = (Field) list.get(i2);
                int i3 = -1;
                if (this.access != null && intArray.get(i + i2) == 1) {
                    i3 = ((FieldAccess) this.access).getIndex(field.getName());
                }
                list2.add(newCachedField(field, list2.size(), i3));
            }
            return;
        }
        this.unsafeUtil.createUnsafeCacheFieldsAndRegions(list, list2, i, intArray);
    }

    public void setGenerics(Kryo kryo2, Class[] clsArr) {
        if (this.config.isOptimizedGenerics()) {
            this.generics = clsArr;
            TypeVariable[] typeVariableArr = this.typeParameters;
            if (typeVariableArr != null && typeVariableArr.length > 0) {
                rebuildCachedFields(true);
            }
        }
    }

    public Class[] getGenerics() {
        return this.generics;
    }

    /* access modifiers changed from: 0000 */
    public CachedField newCachedField(Field field, int i, int i2) {
        CachedField cachedField;
        boolean z = true;
        Class[] clsArr = {field.getType()};
        Type genericType = this.config.isOptimizedGenerics() ? field.getGenericType() : null;
        if (!this.config.isOptimizedGenerics() || genericType == clsArr[0]) {
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Field ");
                sb.append(field.getName());
                sb.append(": ");
                sb.append(clsArr[0]);
                Log.trace("kryo", sb.toString());
            }
            cachedField = newMatchingCachedField(field, i2, clsArr[0], genericType, null);
        } else {
            cachedField = this.genericsUtil.newCachedFieldOfGenericType(field, i2, clsArr, genericType);
        }
        if (cachedField instanceof ObjectField) {
            this.hasObjectFields = true;
        }
        cachedField.field = field;
        cachedField.varIntsEnabled = this.varIntsEnabled;
        if (!this.config.isUseAsm()) {
            cachedField.offset = this.unsafeUtil.getObjectFieldOffset(field);
        }
        cachedField.access = (FieldAccess) this.access;
        cachedField.accessIndex = i2;
        if (!this.config.isFieldsCanBeNull() || clsArr[0].isPrimitive() || field.isAnnotationPresent(NotNull.class)) {
            z = false;
        }
        cachedField.canBeNull = z;
        if (this.kryo.isFinal(clsArr[0]) || this.config.isFixedFieldTypes()) {
            cachedField.valueClass = clsArr[0];
        }
        return cachedField;
    }

    /* access modifiers changed from: 0000 */
    public CachedField newMatchingCachedField(Field field, int i, Class cls, Type type2, Class[] clsArr) {
        if (i != -1) {
            return getAsmFieldFactory().createCachedField(cls, field, this);
        }
        if (!this.config.isUseAsm()) {
            return getUnsafeFieldFactory().createCachedField(cls, field, this);
        }
        CachedField createCachedField = getObjectFieldFactory().createCachedField(cls, field, this);
        if (!this.config.isOptimizedGenerics()) {
            return createCachedField;
        }
        if (clsArr != null) {
            ((ObjectField) createCachedField).generics = clsArr;
            return createCachedField;
        } else if (type2 == null) {
            return createCachedField;
        } else {
            Class[] generics2 = FieldSerializerGenericsUtil.getGenerics(type2, this.kryo);
            ((ObjectField) createCachedField).generics = generics2;
            if (!Log.TRACE) {
                return createCachedField;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Field generics: ");
            sb.append(Arrays.toString(generics2));
            Log.trace("kryo", sb.toString());
            return createCachedField;
        }
    }

    private CachedFieldFactory getAsmFieldFactory() {
        if (asmFieldFactory == null) {
            asmFieldFactory = new AsmCachedFieldFactory();
        }
        return asmFieldFactory;
    }

    private CachedFieldFactory getObjectFieldFactory() {
        if (objectFieldFactory == null) {
            objectFieldFactory = new ObjectCachedFieldFactory();
        }
        return objectFieldFactory;
    }

    private CachedFieldFactory getUnsafeFieldFactory() {
        if (unsafeFieldFactory == null) {
            try {
                unsafeFieldFactory = (CachedFieldFactory) getClass().getClassLoader().loadClass("com.esotericsoftware.kryo.serializers.UnsafeCachedFieldFactory").newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create UnsafeFieldFactory", e);
            }
        }
        return unsafeFieldFactory;
    }

    public int compare(CachedField cachedField, CachedField cachedField2) {
        return getCachedFieldName(cachedField).compareTo(getCachedFieldName(cachedField2));
    }

    public void setFieldsCanBeNull(boolean z) {
        this.config.setFieldsCanBeNull(z);
        rebuildCachedFields();
    }

    public void setFieldsAsAccessible(boolean z) {
        this.config.setFieldsAsAccessible(z);
        rebuildCachedFields();
    }

    public void setIgnoreSyntheticFields(boolean z) {
        this.config.setIgnoreSyntheticFields(z);
        rebuildCachedFields();
    }

    public void setFixedFieldTypes(boolean z) {
        this.config.setFixedFieldTypes(z);
        rebuildCachedFields();
    }

    public void setUseAsm(boolean z) {
        this.config.setUseAsm(z);
        rebuildCachedFields();
    }

    public void setCopyTransient(boolean z) {
        this.config.setCopyTransient(z);
    }

    public void setSerializeTransient(boolean z) {
        this.config.setSerializeTransient(z);
    }

    public void setOptimizedGenerics(boolean z) {
        this.config.setOptimizedGenerics(z);
        rebuildCachedFields();
    }

    public void write(Kryo kryo2, Output output, T t) {
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("FieldSerializer.write fields of class: ");
            sb.append(t.getClass().getName());
            Log.trace("kryo", sb.toString());
        }
        if (this.config.isOptimizedGenerics()) {
            if (!(this.typeParameters == null || this.generics == null)) {
                rebuildCachedFields();
            }
            if (this.genericsScope != null) {
                kryo2.getGenericsResolver().pushScope(this.type, this.genericsScope);
            }
        }
        for (CachedField write : this.fields) {
            write.write(output, t);
        }
        if (this.config.isSerializeTransient()) {
            for (CachedField write2 : this.transientFields) {
                write2.write(output, t);
            }
        }
        if (this.config.isOptimizedGenerics() && this.genericsScope != null) {
            kryo2.getGenericsResolver().popScope();
        }
    }

    public T read(Kryo kryo2, Input input, Class<T> cls) {
        try {
            if (this.config.isOptimizedGenerics()) {
                if (!(this.typeParameters == null || this.generics == null)) {
                    rebuildCachedFields();
                }
                if (this.genericsScope != null) {
                    kryo2.getGenericsResolver().pushScope(cls, this.genericsScope);
                }
            }
            T create = create(kryo2, input, cls);
            kryo2.reference(create);
            for (CachedField read : this.fields) {
                read.read(input, create);
            }
            if (this.config.isSerializeTransient()) {
                for (CachedField read2 : this.transientFields) {
                    read2.read(input, create);
                }
            }
            return create;
        } finally {
            if (!(!this.config.isOptimizedGenerics() || this.genericsScope == null || kryo2.getGenericsResolver() == null)) {
                kryo2.getGenericsResolver().popScope();
            }
        }
    }

    /* access modifiers changed from: protected */
    public T create(Kryo kryo2, Input input, Class<T> cls) {
        return kryo2.newInstance(cls);
    }

    public CachedField getField(String str) {
        CachedField[] cachedFieldArr;
        for (CachedField cachedField : this.fields) {
            if (getCachedFieldName(cachedField).equals(str)) {
                return cachedField;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Field \"");
        sb.append(str);
        sb.append("\" not found on class: ");
        sb.append(this.type.getName());
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public String getCachedFieldName(CachedField cachedField) {
        return this.config.getCachedFieldNameStrategy().getName(cachedField);
    }

    public void removeField(String str) {
        int i = 0;
        while (true) {
            CachedField[] cachedFieldArr = this.fields;
            if (i < cachedFieldArr.length) {
                CachedField cachedField = cachedFieldArr[i];
                if (getCachedFieldName(cachedField).equals(str)) {
                    CachedField[] cachedFieldArr2 = this.fields;
                    CachedField[] cachedFieldArr3 = new CachedField[(cachedFieldArr2.length - 1)];
                    System.arraycopy(cachedFieldArr2, 0, cachedFieldArr3, 0, i);
                    System.arraycopy(this.fields, i + 1, cachedFieldArr3, i, cachedFieldArr3.length - i);
                    this.fields = cachedFieldArr3;
                    this.removedFields.add(cachedField);
                    return;
                }
                i++;
            } else {
                int i2 = 0;
                while (true) {
                    CachedField[] cachedFieldArr4 = this.transientFields;
                    if (i2 < cachedFieldArr4.length) {
                        CachedField cachedField2 = cachedFieldArr4[i2];
                        if (getCachedFieldName(cachedField2).equals(str)) {
                            CachedField[] cachedFieldArr5 = this.transientFields;
                            CachedField[] cachedFieldArr6 = new CachedField[(cachedFieldArr5.length - 1)];
                            System.arraycopy(cachedFieldArr5, 0, cachedFieldArr6, 0, i2);
                            System.arraycopy(this.transientFields, i2 + 1, cachedFieldArr6, i2, cachedFieldArr6.length - i2);
                            this.transientFields = cachedFieldArr6;
                            this.removedFields.add(cachedField2);
                            return;
                        }
                        i2++;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Field \"");
                        sb.append(str);
                        sb.append("\" not found on class: ");
                        sb.append(this.type.getName());
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }
        }
    }

    public void removeField(CachedField cachedField) {
        int i = 0;
        while (true) {
            CachedField[] cachedFieldArr = this.fields;
            if (i < cachedFieldArr.length) {
                CachedField cachedField2 = cachedFieldArr[i];
                if (cachedField2 == cachedField) {
                    CachedField[] cachedFieldArr2 = new CachedField[(cachedFieldArr.length - 1)];
                    System.arraycopy(cachedFieldArr, 0, cachedFieldArr2, 0, i);
                    System.arraycopy(this.fields, i + 1, cachedFieldArr2, i, cachedFieldArr2.length - i);
                    this.fields = cachedFieldArr2;
                    this.removedFields.add(cachedField2);
                    return;
                }
                i++;
            } else {
                int i2 = 0;
                while (true) {
                    CachedField[] cachedFieldArr3 = this.transientFields;
                    if (i2 < cachedFieldArr3.length) {
                        CachedField cachedField3 = cachedFieldArr3[i2];
                        if (cachedField3 == cachedField) {
                            CachedField[] cachedFieldArr4 = new CachedField[(cachedFieldArr3.length - 1)];
                            System.arraycopy(cachedFieldArr3, 0, cachedFieldArr4, 0, i2);
                            System.arraycopy(this.transientFields, i2 + 1, cachedFieldArr4, i2, cachedFieldArr4.length - i2);
                            this.transientFields = cachedFieldArr4;
                            this.removedFields.add(cachedField3);
                            return;
                        }
                        i2++;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Field \"");
                        sb.append(cachedField);
                        sb.append("\" not found on class: ");
                        sb.append(this.type.getName());
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }
        }
    }

    public CachedField[] getFields() {
        return this.fields;
    }

    public CachedField[] getTransientFields() {
        return this.transientFields;
    }

    public Class getType() {
        return this.type;
    }

    public Kryo getKryo() {
        return this.kryo;
    }

    public boolean getUseAsmEnabled() {
        return this.config.isUseAsm();
    }

    public boolean getUseMemRegions() {
        return this.useMemRegions;
    }

    public boolean getCopyTransient() {
        return this.config.isCopyTransient();
    }

    public boolean getSerializeTransient() {
        return this.config.isSerializeTransient();
    }

    /* access modifiers changed from: protected */
    public T createCopy(Kryo kryo2, T t) {
        return kryo2.newInstance(t.getClass());
    }

    public T copy(Kryo kryo2, T t) {
        T createCopy = createCopy(kryo2, t);
        kryo2.reference(createCopy);
        if (this.config.isCopyTransient()) {
            for (CachedField copy : this.transientFields) {
                copy.copy(t, createCopy);
            }
        }
        for (CachedField copy2 : this.fields) {
            copy2.copy(t, createCopy);
        }
        return createCopy;
    }

    /* access modifiers changed from: 0000 */
    public final Generics getGenericsScope() {
        return this.genericsScope;
    }
}
