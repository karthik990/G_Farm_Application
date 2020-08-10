package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;
import org.junit.runners.model.FrameworkMember;

class ValidationError extends Exception {
    public ValidationError(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, String str) {
        super(String.format("The @%s '%s' %s", new Object[]{cls.getSimpleName(), frameworkMember.getName(), str}));
    }
}
