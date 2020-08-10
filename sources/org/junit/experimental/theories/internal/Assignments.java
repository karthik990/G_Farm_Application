package org.junit.experimental.theories.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.runners.model.TestClass;

public class Assignments {
    private final List<PotentialAssignment> assigned;
    private final TestClass clazz;
    private final List<ParameterSignature> unassigned;

    private Assignments(List<PotentialAssignment> list, List<ParameterSignature> list2, TestClass testClass) {
        this.unassigned = list2;
        this.assigned = list;
        this.clazz = testClass;
    }

    public static Assignments allUnassigned(Method method, TestClass testClass) {
        List signatures = ParameterSignature.signatures(testClass.getOnlyConstructor());
        signatures.addAll(ParameterSignature.signatures(method));
        return new Assignments(new ArrayList(), signatures, testClass);
    }

    public boolean isComplete() {
        return this.unassigned.size() == 0;
    }

    public ParameterSignature nextUnassigned() {
        return (ParameterSignature) this.unassigned.get(0);
    }

    public Assignments assignNext(PotentialAssignment potentialAssignment) {
        ArrayList arrayList = new ArrayList(this.assigned);
        arrayList.add(potentialAssignment);
        List<ParameterSignature> list = this.unassigned;
        return new Assignments(arrayList, list.subList(1, list.size()), this.clazz);
    }

    public Object[] getActualValues(int i, int i2) throws CouldNotGenerateValueException {
        Object[] objArr = new Object[(i2 - i)];
        for (int i3 = i; i3 < i2; i3++) {
            objArr[i3 - i] = ((PotentialAssignment) this.assigned.get(i3)).getValue();
        }
        return objArr;
    }

    public List<PotentialAssignment> potentialsForNextUnassigned() throws Throwable {
        ParameterSignature nextUnassigned = nextUnassigned();
        List<PotentialAssignment> valueSources = getSupplier(nextUnassigned).getValueSources(nextUnassigned);
        return valueSources.size() == 0 ? generateAssignmentsFromTypeAlone(nextUnassigned) : valueSources;
    }

    private List<PotentialAssignment> generateAssignmentsFromTypeAlone(ParameterSignature parameterSignature) {
        Class type = parameterSignature.getType();
        if (type.isEnum()) {
            return new EnumSupplier(type).getValueSources(parameterSignature);
        }
        if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
            return new BooleanSupplier().getValueSources(parameterSignature);
        }
        return Collections.emptyList();
    }

    private ParameterSupplier getSupplier(ParameterSignature parameterSignature) throws Exception {
        ParametersSuppliedBy parametersSuppliedBy = (ParametersSuppliedBy) parameterSignature.findDeepAnnotation(ParametersSuppliedBy.class);
        if (parametersSuppliedBy != null) {
            return buildParameterSupplierFromClass(parametersSuppliedBy.value());
        }
        return new AllMembersSupplier(this.clazz);
    }

    private ParameterSupplier buildParameterSupplierFromClass(Class<? extends ParameterSupplier> cls) throws Exception {
        Constructor[] constructors = cls.getConstructors();
        int length = constructors.length;
        int i = 0;
        while (i < length) {
            Constructor constructor = constructors[i];
            Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length != 1 || !parameterTypes[0].equals(TestClass.class)) {
                i++;
            } else {
                return (ParameterSupplier) constructor.newInstance(new Object[]{this.clazz});
            }
        }
        return (ParameterSupplier) cls.newInstance();
    }

    public Object[] getConstructorArguments() throws CouldNotGenerateValueException {
        return getActualValues(0, getConstructorParameterCount());
    }

    public Object[] getMethodArguments() throws CouldNotGenerateValueException {
        return getActualValues(getConstructorParameterCount(), this.assigned.size());
    }

    public Object[] getAllArguments() throws CouldNotGenerateValueException {
        return getActualValues(0, this.assigned.size());
    }

    private int getConstructorParameterCount() {
        return ParameterSignature.signatures(this.clazz.getOnlyConstructor()).size();
    }

    public Object[] getArgumentStrings(boolean z) throws CouldNotGenerateValueException {
        Object[] objArr = new Object[this.assigned.size()];
        for (int i = 0; i < objArr.length; i++) {
            objArr[i] = ((PotentialAssignment) this.assigned.get(i)).getDescription();
        }
        return objArr;
    }
}
