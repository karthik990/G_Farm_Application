package org.junit.validator;

import java.util.Collections;
import java.util.List;
import org.junit.runners.model.TestClass;

public class PublicClassValidator implements TestClassValidator {
    private static final List<Exception> NO_VALIDATION_ERRORS = Collections.emptyList();

    public List<Exception> validateTestClass(TestClass testClass) {
        if (testClass.isPublic()) {
            return NO_VALIDATION_ERRORS;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The class ");
        sb.append(testClass.getName());
        sb.append(" is not public.");
        return Collections.singletonList(new Exception(sb.toString()));
    }
}
