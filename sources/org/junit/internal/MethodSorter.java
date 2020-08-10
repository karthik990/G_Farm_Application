package org.junit.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.FixMethodOrder;

public class MethodSorter {
    public static final Comparator<Method> DEFAULT = new Comparator<Method>() {
        public int compare(Method method, Method method2) {
            int hashCode = method.getName().hashCode();
            int hashCode2 = method2.getName().hashCode();
            if (hashCode == hashCode2) {
                return MethodSorter.NAME_ASCENDING.compare(method, method2);
            }
            return hashCode < hashCode2 ? -1 : 1;
        }
    };
    public static final Comparator<Method> NAME_ASCENDING = new Comparator<Method>() {
        public int compare(Method method, Method method2) {
            int compareTo = method.getName().compareTo(method2.getName());
            if (compareTo != 0) {
                return compareTo;
            }
            return method.toString().compareTo(method2.toString());
        }
    };

    public static Method[] getDeclaredMethods(Class<?> cls) {
        Comparator sorter = getSorter((FixMethodOrder) cls.getAnnotation(FixMethodOrder.class));
        Method[] declaredMethods = cls.getDeclaredMethods();
        if (sorter != null) {
            Arrays.sort(declaredMethods, sorter);
        }
        return declaredMethods;
    }

    private MethodSorter() {
    }

    private static Comparator<Method> getSorter(FixMethodOrder fixMethodOrder) {
        if (fixMethodOrder == null) {
            return DEFAULT;
        }
        return fixMethodOrder.value().getComparator();
    }
}
