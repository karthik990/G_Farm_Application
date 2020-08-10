package com.rometools.rome.feed.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class BeanIntrospector {
    private static final String BOOLEAN_GETTER = "is";
    private static final String GETTER = "get";
    private static final String SETTER = "set";
    private static final Map<Class<?>, PropertyDescriptor[]> introspected = new HashMap();

    private BeanIntrospector() {
    }

    private static synchronized PropertyDescriptor[] getPropertyDescriptors(Class<?> cls) {
        PropertyDescriptor[] propertyDescriptorArr;
        synchronized (BeanIntrospector.class) {
            propertyDescriptorArr = (PropertyDescriptor[]) introspected.get(cls);
            if (propertyDescriptorArr == null) {
                propertyDescriptorArr = getPDs(cls);
                introspected.put(cls, propertyDescriptorArr);
            }
        }
        return propertyDescriptorArr;
    }

    public static List<PropertyDescriptor> getPropertyDescriptorsWithGetters(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(cls);
        if (propertyDescriptors != null) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                boolean z = true;
                if (readMethod != null) {
                    boolean z2 = readMethod.getDeclaringClass() == Object.class;
                    if (readMethod.getParameterTypes().length != 0) {
                        z = false;
                    }
                    if (!z2 && z) {
                        arrayList.add(propertyDescriptor);
                    }
                }
            }
        }
        return arrayList;
    }

    public static List<PropertyDescriptor> getPropertyDescriptorsWithGettersAndSetters(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        for (PropertyDescriptor propertyDescriptor : getPropertyDescriptorsWithGetters(cls)) {
            if (propertyDescriptor.getWriteMethod() != null) {
                arrayList.add(propertyDescriptor);
            }
        }
        return arrayList;
    }

    private static PropertyDescriptor[] getPDs(Class<?> cls) {
        Method[] methods = cls.getMethods();
        List merge = merge(getPDs(methods, false), getPDs(methods, true));
        return (PropertyDescriptor[]) merge.toArray(new PropertyDescriptor[merge.size()]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0080 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Map<java.lang.String, com.rometools.rome.feed.impl.PropertyDescriptor> getPDs(java.lang.reflect.Method[] r11, boolean r12) {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            int r1 = r11.length
            r2 = 0
        L_0x0007:
            if (r2 >= r1) goto L_0x0083
            r3 = r11[r2]
            int r4 = r3.getModifiers()
            r5 = 1
            r4 = r4 & r5
            r6 = 0
            if (r4 == 0) goto L_0x0079
            java.lang.String r4 = r3.getName()
            java.lang.Class r7 = r3.getReturnType()
            java.lang.Class[] r8 = r3.getParameterTypes()
            int r8 = r8.length
            r9 = 3
            if (r12 == 0) goto L_0x0040
            java.lang.String r10 = "set"
            boolean r10 = r4.startsWith(r10)
            if (r10 == 0) goto L_0x0079
            java.lang.Class r10 = java.lang.Void.TYPE
            if (r7 != r10) goto L_0x0079
            if (r8 != r5) goto L_0x0079
            java.lang.String r4 = r4.substring(r9)
            java.lang.String r4 = decapitalize(r4)
            com.rometools.rome.feed.impl.PropertyDescriptor r5 = new com.rometools.rome.feed.impl.PropertyDescriptor
            r5.<init>(r4, r6, r3)
            goto L_0x007b
        L_0x0040:
            java.lang.String r5 = "get"
            boolean r5 = r4.startsWith(r5)
            if (r5 == 0) goto L_0x005c
            java.lang.Class r5 = java.lang.Void.TYPE
            if (r7 == r5) goto L_0x005c
            if (r8 != 0) goto L_0x005c
            java.lang.String r4 = r4.substring(r9)
            java.lang.String r4 = decapitalize(r4)
            com.rometools.rome.feed.impl.PropertyDescriptor r5 = new com.rometools.rome.feed.impl.PropertyDescriptor
            r5.<init>(r4, r3, r6)
            goto L_0x007b
        L_0x005c:
            java.lang.String r5 = "is"
            boolean r5 = r4.startsWith(r5)
            if (r5 == 0) goto L_0x0079
            java.lang.Class r5 = java.lang.Boolean.TYPE
            if (r7 != r5) goto L_0x0079
            if (r8 != 0) goto L_0x0079
            r5 = 2
            java.lang.String r4 = r4.substring(r5)
            java.lang.String r4 = decapitalize(r4)
            com.rometools.rome.feed.impl.PropertyDescriptor r5 = new com.rometools.rome.feed.impl.PropertyDescriptor
            r5.<init>(r4, r3, r6)
            goto L_0x007b
        L_0x0079:
            r4 = r6
            r5 = r4
        L_0x007b:
            if (r4 == 0) goto L_0x0080
            r0.put(r4, r5)
        L_0x0080:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0083:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rometools.rome.feed.impl.BeanIntrospector.getPDs(java.lang.reflect.Method[], boolean):java.util.Map");
    }

    private static List<PropertyDescriptor> merge(Map<String, PropertyDescriptor> map, Map<String, PropertyDescriptor> map2) {
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (String str : map.keySet()) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) map.get(str);
            PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) map2.get(str);
            if (propertyDescriptor2 != null) {
                hashSet.add(str);
                arrayList.add(new PropertyDescriptor(str, propertyDescriptor.getReadMethod(), propertyDescriptor2.getWriteMethod()));
            } else {
                arrayList.add(propertyDescriptor);
            }
        }
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.removeAll(hashSet);
        for (String str2 : hashSet2) {
            arrayList.add((PropertyDescriptor) map2.get(str2));
        }
        return arrayList;
    }

    private static String decapitalize(String str) {
        if (str.isEmpty() || (str.length() > 1 && Character.isUpperCase(str.charAt(1)))) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }
}
