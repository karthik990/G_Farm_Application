package org.junit.validator;

import java.util.concurrent.ConcurrentHashMap;

public class AnnotationValidatorFactory {
    private static final ConcurrentHashMap<ValidateWith, AnnotationValidator> VALIDATORS_FOR_ANNOTATION_TYPES = new ConcurrentHashMap<>();

    public AnnotationValidator createAnnotationValidator(ValidateWith validateWith) {
        AnnotationValidator annotationValidator = (AnnotationValidator) VALIDATORS_FOR_ANNOTATION_TYPES.get(validateWith);
        if (annotationValidator != null) {
            return annotationValidator;
        }
        Class value = validateWith.value();
        if (value != null) {
            try {
                VALIDATORS_FOR_ANNOTATION_TYPES.putIfAbsent(validateWith, (AnnotationValidator) value.newInstance());
                return (AnnotationValidator) VALIDATORS_FOR_ANNOTATION_TYPES.get(validateWith);
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Exception received when creating AnnotationValidator class ");
                sb.append(value.getName());
                throw new RuntimeException(sb.toString(), e);
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Can't create validator, value is null in annotation ");
            sb2.append(validateWith.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }
}
