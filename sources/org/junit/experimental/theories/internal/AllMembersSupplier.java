package org.junit.experimental.theories.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Assume;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class AllMembersSupplier extends ParameterSupplier {
    private final TestClass clazz;

    static class MethodParameterValue extends PotentialAssignment {
        private final FrameworkMethod method;

        private MethodParameterValue(FrameworkMethod frameworkMethod) {
            this.method = frameworkMethod;
        }

        public Object getValue() throws CouldNotGenerateValueException {
            boolean z = false;
            try {
                return this.method.invokeExplosively(null, new Object[0]);
            } catch (IllegalArgumentException unused) {
                throw new RuntimeException("unexpected: argument length is checked");
            } catch (IllegalAccessException unused2) {
                throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
            } catch (Throwable th) {
                DataPoint dataPoint = (DataPoint) this.method.getAnnotation(DataPoint.class);
                if (dataPoint == null || !AllMembersSupplier.isAssignableToAnyOf(dataPoint.ignoredExceptions(), th)) {
                    z = true;
                }
                Assume.assumeTrue(z);
                throw new CouldNotGenerateValueException(th);
            }
        }

        public String getDescription() throws CouldNotGenerateValueException {
            return this.method.getName();
        }
    }

    public AllMembersSupplier(TestClass testClass) {
        this.clazz = testClass;
    }

    public List<PotentialAssignment> getValueSources(ParameterSignature parameterSignature) throws Throwable {
        ArrayList arrayList = new ArrayList();
        addSinglePointFields(parameterSignature, arrayList);
        addMultiPointFields(parameterSignature, arrayList);
        addSinglePointMethods(parameterSignature, arrayList);
        addMultiPointMethods(parameterSignature, arrayList);
        return arrayList;
    }

    private void addMultiPointMethods(ParameterSignature parameterSignature, List<PotentialAssignment> list) throws Throwable {
        for (FrameworkMethod frameworkMethod : getDataPointsMethods(parameterSignature)) {
            Class returnType = frameworkMethod.getReturnType();
            if ((returnType.isArray() && parameterSignature.canPotentiallyAcceptType(returnType.getComponentType())) || Iterable.class.isAssignableFrom(returnType)) {
                try {
                    addDataPointsValues(returnType, parameterSignature, frameworkMethod.getName(), list, frameworkMethod.invokeExplosively(null, new Object[0]));
                } catch (Throwable th) {
                    DataPoints dataPoints = (DataPoints) frameworkMethod.getAnnotation(DataPoints.class);
                    if (dataPoints == null || !isAssignableToAnyOf(dataPoints.ignoredExceptions(), th)) {
                        throw th;
                    }
                    return;
                }
            }
        }
    }

    private void addSinglePointMethods(ParameterSignature parameterSignature, List<PotentialAssignment> list) {
        for (FrameworkMethod frameworkMethod : getSingleDataPointMethods(parameterSignature)) {
            if (parameterSignature.canAcceptType(frameworkMethod.getType())) {
                list.add(new MethodParameterValue(frameworkMethod));
            }
        }
    }

    private void addMultiPointFields(ParameterSignature parameterSignature, List<PotentialAssignment> list) {
        for (Field field : getDataPointsFields(parameterSignature)) {
            addDataPointsValues(field.getType(), parameterSignature, field.getName(), list, getStaticFieldValue(field));
        }
    }

    private void addSinglePointFields(ParameterSignature parameterSignature, List<PotentialAssignment> list) {
        for (Field field : getSingleDataPointFields(parameterSignature)) {
            Object staticFieldValue = getStaticFieldValue(field);
            if (parameterSignature.canAcceptValue(staticFieldValue)) {
                list.add(PotentialAssignment.forValue(field.getName(), staticFieldValue));
            }
        }
    }

    private void addDataPointsValues(Class<?> cls, ParameterSignature parameterSignature, String str, List<PotentialAssignment> list, Object obj) {
        if (cls.isArray()) {
            addArrayValues(parameterSignature, str, list, obj);
        } else if (Iterable.class.isAssignableFrom(cls)) {
            addIterableValues(parameterSignature, str, list, (Iterable) obj);
        }
    }

    private void addArrayValues(ParameterSignature parameterSignature, String str, List<PotentialAssignment> list, Object obj) {
        for (int i = 0; i < Array.getLength(obj); i++) {
            Object obj2 = Array.get(obj, i);
            if (parameterSignature.canAcceptValue(obj2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("[");
                sb.append(i);
                sb.append("]");
                list.add(PotentialAssignment.forValue(sb.toString(), obj2));
            }
        }
    }

    private void addIterableValues(ParameterSignature parameterSignature, String str, List<PotentialAssignment> list, Iterable<?> iterable) {
        int i = 0;
        for (Object next : iterable) {
            if (parameterSignature.canAcceptValue(next)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("[");
                sb.append(i);
                sb.append("]");
                list.add(PotentialAssignment.forValue(sb.toString(), next));
            }
            i++;
        }
    }

    private Object getStaticFieldValue(Field field) {
        try {
            return field.get(null);
        } catch (IllegalArgumentException unused) {
            throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
        } catch (IllegalAccessException unused2) {
            throw new RuntimeException("unexpected: getFields returned an inaccessible field");
        }
    }

    /* access modifiers changed from: private */
    public static boolean isAssignableToAnyOf(Class<?>[] clsArr, Object obj) {
        for (Class<?> isAssignableFrom : clsArr) {
            if (isAssignableFrom.isAssignableFrom(obj.getClass())) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Collection<FrameworkMethod> getDataPointsMethods(ParameterSignature parameterSignature) {
        return this.clazz.getAnnotatedMethods(DataPoints.class);
    }

    /* access modifiers changed from: protected */
    public Collection<Field> getSingleDataPointFields(ParameterSignature parameterSignature) {
        List<FrameworkField> annotatedFields = this.clazz.getAnnotatedFields(DataPoint.class);
        ArrayList arrayList = new ArrayList();
        for (FrameworkField field : annotatedFields) {
            arrayList.add(field.getField());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Collection<Field> getDataPointsFields(ParameterSignature parameterSignature) {
        List<FrameworkField> annotatedFields = this.clazz.getAnnotatedFields(DataPoints.class);
        ArrayList arrayList = new ArrayList();
        for (FrameworkField field : annotatedFields) {
            arrayList.add(field.getField());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Collection<FrameworkMethod> getSingleDataPointMethods(ParameterSignature parameterSignature) {
        return this.clazz.getAnnotatedMethods(DataPoint.class);
    }
}
