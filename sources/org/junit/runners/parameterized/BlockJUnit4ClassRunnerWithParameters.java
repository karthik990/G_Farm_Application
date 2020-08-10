package org.junit.runners.parameterized;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class BlockJUnit4ClassRunnerWithParameters extends BlockJUnit4ClassRunner {
    private final String name;
    private final Object[] parameters;

    /* access modifiers changed from: protected */
    public Annotation[] getRunnerAnnotations() {
        return new Annotation[0];
    }

    public BlockJUnit4ClassRunnerWithParameters(TestWithParameters testWithParameters) throws InitializationError {
        super(testWithParameters.getTestClass().getJavaClass());
        this.parameters = testWithParameters.getParameters().toArray(new Object[testWithParameters.getParameters().size()]);
        this.name = testWithParameters.getName();
    }

    public Object createTest() throws Exception {
        if (fieldsAreAnnotated()) {
            return createTestUsingFieldInjection();
        }
        return createTestUsingConstructorInjection();
    }

    private Object createTestUsingConstructorInjection() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(this.parameters);
    }

    private Object createTestUsingFieldInjection() throws Exception {
        List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
        if (annotatedFieldsByParameter.size() == this.parameters.length) {
            Object newInstance = getTestClass().getJavaClass().newInstance();
            for (FrameworkField field : annotatedFieldsByParameter) {
                Field field2 = field.getField();
                int value = ((Parameter) field2.getAnnotation(Parameter.class)).value();
                try {
                    field2.set(newInstance, this.parameters[value]);
                } catch (IllegalArgumentException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(getTestClass().getName());
                    sb.append(": Trying to set ");
                    sb.append(field2.getName());
                    sb.append(" with the value ");
                    sb.append(this.parameters[value]);
                    sb.append(" that is not the right type (");
                    sb.append(this.parameters[value].getClass().getSimpleName());
                    sb.append(" instead of ");
                    sb.append(field2.getType().getSimpleName());
                    sb.append(").");
                    throw new Exception(sb.toString(), e);
                }
            }
            return newInstance;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Wrong number of parameters and @Parameter fields. @Parameter fields counted: ");
        sb2.append(annotatedFieldsByParameter.size());
        sb2.append(", available parameters: ");
        sb2.append(this.parameters.length);
        sb2.append(".");
        throw new Exception(sb2.toString());
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return this.name;
    }

    /* access modifiers changed from: protected */
    public String testName(FrameworkMethod frameworkMethod) {
        StringBuilder sb = new StringBuilder();
        sb.append(frameworkMethod.getName());
        sb.append(getName());
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void validateConstructor(List<Throwable> list) {
        validateOnlyOneConstructor(list);
        if (fieldsAreAnnotated()) {
            validateZeroArgConstructor(list);
        }
    }

    /* access modifiers changed from: protected */
    public void validateFields(List<Throwable> list) {
        super.validateFields(list);
        if (fieldsAreAnnotated()) {
            List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
            int[] iArr = new int[annotatedFieldsByParameter.size()];
            for (FrameworkField field : annotatedFieldsByParameter) {
                int value = ((Parameter) field.getField().getAnnotation(Parameter.class)).value();
                if (value < 0 || value > annotatedFieldsByParameter.size() - 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid @Parameter value: ");
                    sb.append(value);
                    sb.append(". @Parameter fields counted: ");
                    sb.append(annotatedFieldsByParameter.size());
                    sb.append(". Please use an index between 0 and ");
                    sb.append(annotatedFieldsByParameter.size() - 1);
                    sb.append(".");
                    list.add(new Exception(sb.toString()));
                } else {
                    iArr[value] = iArr[value] + 1;
                }
            }
            for (int i = 0; i < iArr.length; i++) {
                int i2 = iArr[i];
                String str = "@Parameter(";
                if (i2 == 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(i);
                    sb2.append(") is never used.");
                    list.add(new Exception(sb2.toString()));
                } else if (i2 > 1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(i);
                    sb3.append(") is used more than once (");
                    sb3.append(i2);
                    sb3.append(").");
                    list.add(new Exception(sb3.toString()));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Statement classBlock(RunNotifier runNotifier) {
        return childrenInvoker(runNotifier);
    }

    private List<FrameworkField> getAnnotatedFieldsByParameter() {
        return getTestClass().getAnnotatedFields(Parameter.class);
    }

    private boolean fieldsAreAnnotated() {
        return !getAnnotatedFieldsByParameter().isEmpty();
    }
}
