package org.junit.validator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runners.model.Annotatable;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public final class AnnotationsValidator implements TestClassValidator {
    private static final List<AnnotatableValidator<?>> VALIDATORS = Arrays.asList(new AnnotatableValidator[]{new ClassValidator(), new MethodValidator(), new FieldValidator()});

    private static abstract class AnnotatableValidator<T extends Annotatable> {
        private static final AnnotationValidatorFactory ANNOTATION_VALIDATOR_FACTORY = new AnnotationValidatorFactory();

        /* access modifiers changed from: 0000 */
        public abstract Iterable<T> getAnnotatablesForTestClass(TestClass testClass);

        /* access modifiers changed from: 0000 */
        public abstract List<Exception> validateAnnotatable(AnnotationValidator annotationValidator, T t);

        private AnnotatableValidator() {
        }

        public List<Exception> validateTestClass(TestClass testClass) {
            ArrayList arrayList = new ArrayList();
            for (Annotatable validateAnnotatable : getAnnotatablesForTestClass(testClass)) {
                arrayList.addAll(validateAnnotatable(validateAnnotatable));
            }
            return arrayList;
        }

        private List<Exception> validateAnnotatable(T t) {
            ArrayList arrayList = new ArrayList();
            for (Annotation annotationType : t.getAnnotations()) {
                ValidateWith validateWith = (ValidateWith) annotationType.annotationType().getAnnotation(ValidateWith.class);
                if (validateWith != null) {
                    arrayList.addAll(validateAnnotatable(ANNOTATION_VALIDATOR_FACTORY.createAnnotationValidator(validateWith), t));
                }
            }
            return arrayList;
        }
    }

    private static class ClassValidator extends AnnotatableValidator<TestClass> {
        private ClassValidator() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public Iterable<TestClass> getAnnotatablesForTestClass(TestClass testClass) {
            return Collections.singletonList(testClass);
        }

        /* access modifiers changed from: 0000 */
        public List<Exception> validateAnnotatable(AnnotationValidator annotationValidator, TestClass testClass) {
            return annotationValidator.validateAnnotatedClass(testClass);
        }
    }

    private static class FieldValidator extends AnnotatableValidator<FrameworkField> {
        private FieldValidator() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public Iterable<FrameworkField> getAnnotatablesForTestClass(TestClass testClass) {
            return testClass.getAnnotatedFields();
        }

        /* access modifiers changed from: 0000 */
        public List<Exception> validateAnnotatable(AnnotationValidator annotationValidator, FrameworkField frameworkField) {
            return annotationValidator.validateAnnotatedField(frameworkField);
        }
    }

    private static class MethodValidator extends AnnotatableValidator<FrameworkMethod> {
        private MethodValidator() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public Iterable<FrameworkMethod> getAnnotatablesForTestClass(TestClass testClass) {
            return testClass.getAnnotatedMethods();
        }

        /* access modifiers changed from: 0000 */
        public List<Exception> validateAnnotatable(AnnotationValidator annotationValidator, FrameworkMethod frameworkMethod) {
            return annotationValidator.validateAnnotatedMethod(frameworkMethod);
        }
    }

    public List<Exception> validateTestClass(TestClass testClass) {
        ArrayList arrayList = new ArrayList();
        for (AnnotatableValidator validateTestClass : VALIDATORS) {
            arrayList.addAll(validateTestClass.validateTestClass(testClass));
        }
        return arrayList;
    }
}
