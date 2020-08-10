package com.google.api.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ClassInfo {
    private static final ConcurrentMap<Class<?>, ClassInfo> CACHE = new ConcurrentHashMap();
    private static final ConcurrentMap<Class<?>, ClassInfo> CACHE_IGNORE_CASE = new ConcurrentHashMap();
    private final Class<?> clazz;
    private final boolean ignoreCase;
    private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap = new IdentityHashMap<>();
    final List<String> names;

    /* renamed from: of */
    public static ClassInfo m1759of(Class<?> cls) {
        return m1760of(cls, false);
    }

    /* renamed from: of */
    public static ClassInfo m1760of(Class<?> cls, boolean z) {
        if (cls == null) {
            return null;
        }
        ConcurrentMap<Class<?>, ClassInfo> concurrentMap = z ? CACHE_IGNORE_CASE : CACHE;
        ClassInfo classInfo = (ClassInfo) concurrentMap.get(cls);
        if (classInfo == null) {
            classInfo = new ClassInfo(cls, z);
            ClassInfo classInfo2 = (ClassInfo) concurrentMap.putIfAbsent(cls, classInfo);
            if (classInfo2 != null) {
                classInfo = classInfo2;
            }
        }
        return classInfo;
    }

    public Class<?> getUnderlyingClass() {
        return this.clazz;
    }

    public final boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public FieldInfo getFieldInfo(String str) {
        if (str != null) {
            if (this.ignoreCase) {
                str = str.toLowerCase(Locale.US);
            }
            str = str.intern();
        }
        return (FieldInfo) this.nameToFieldInfoMap.get(str);
    }

    public Field getField(String str) {
        FieldInfo fieldInfo = getFieldInfo(str);
        if (fieldInfo == null) {
            return null;
        }
        return fieldInfo.getField();
    }

    public boolean isEnum() {
        return this.clazz.isEnum();
    }

    public Collection<String> getNames() {
        return this.names;
    }

    private ClassInfo(Class<?> cls, boolean z) {
        Field[] declaredFields;
        List<String> list;
        Field field;
        this.clazz = cls;
        this.ignoreCase = z;
        boolean z2 = !z || !cls.isEnum();
        StringBuilder sb = new StringBuilder();
        sb.append("cannot ignore case on an enum: ");
        sb.append(cls);
        Preconditions.checkArgument(z2, sb.toString());
        TreeSet treeSet = new TreeSet(new Comparator<String>() {
            public int compare(String str, String str2) {
                if (Objects.equal(str, str2)) {
                    return 0;
                }
                if (str == null) {
                    return -1;
                }
                if (str2 == null) {
                    return 1;
                }
                return str.compareTo(str2);
            }
        });
        for (Field field2 : cls.getDeclaredFields()) {
            FieldInfo of = FieldInfo.m1762of(field2);
            if (of != null) {
                String name = of.getName();
                if (z) {
                    name = name.toLowerCase(Locale.US).intern();
                }
                FieldInfo fieldInfo = (FieldInfo) this.nameToFieldInfoMap.get(name);
                boolean z3 = fieldInfo == null;
                Object[] objArr = new Object[4];
                objArr[0] = z ? "case-insensitive " : "";
                objArr[1] = name;
                objArr[2] = field2;
                if (fieldInfo == null) {
                    field = null;
                } else {
                    field = fieldInfo.getField();
                }
                objArr[3] = field;
                Preconditions.checkArgument(z3, "two fields have the same %sname <%s>: %s and %s", objArr);
                this.nameToFieldInfoMap.put(name, of);
                treeSet.add(name);
            }
        }
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            ClassInfo of2 = m1760of(superclass, z);
            treeSet.addAll(of2.names);
            for (Entry entry : of2.nameToFieldInfoMap.entrySet()) {
                String str = (String) entry.getKey();
                if (!this.nameToFieldInfoMap.containsKey(str)) {
                    this.nameToFieldInfoMap.put(str, entry.getValue());
                }
            }
        }
        if (treeSet.isEmpty()) {
            list = Collections.emptyList();
        } else {
            list = Collections.unmodifiableList(new ArrayList(treeSet));
        }
        this.names = list;
    }

    public Collection<FieldInfo> getFieldInfos() {
        return Collections.unmodifiableCollection(this.nameToFieldInfoMap.values());
    }
}
