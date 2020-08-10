package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runners.model.FrameworkMember;
import org.junit.runners.model.TestClass;

public class RuleMemberValidator {
    public static final RuleMemberValidator CLASS_RULE_METHOD_VALIDATOR = classRuleValidatorBuilder().forMethods().withValidator(new DeclaringClassMustBePublic()).withValidator(new MemberMustBeStatic()).withValidator(new MemberMustBePublic()).withValidator(new MethodMustBeATestRule()).build();
    public static final RuleMemberValidator CLASS_RULE_VALIDATOR = classRuleValidatorBuilder().withValidator(new DeclaringClassMustBePublic()).withValidator(new MemberMustBeStatic()).withValidator(new MemberMustBePublic()).withValidator(new FieldMustBeATestRule()).build();
    public static final RuleMemberValidator RULE_METHOD_VALIDATOR = testRuleValidatorBuilder().forMethods().withValidator(new MemberMustBeNonStaticOrAlsoClassRule()).withValidator(new MemberMustBePublic()).withValidator(new MethodMustBeARule()).build();
    public static final RuleMemberValidator RULE_VALIDATOR = testRuleValidatorBuilder().withValidator(new MemberMustBeNonStaticOrAlsoClassRule()).withValidator(new MemberMustBePublic()).withValidator(new FieldMustBeARule()).build();
    private final Class<? extends Annotation> annotation;
    private final boolean methods;
    private final List<RuleValidator> validatorStrategies;

    private static class Builder {
        /* access modifiers changed from: private */
        public final Class<? extends Annotation> annotation;
        /* access modifiers changed from: private */
        public boolean methods;
        /* access modifiers changed from: private */
        public final List<RuleValidator> validators;

        private Builder(Class<? extends Annotation> cls) {
            this.annotation = cls;
            this.methods = false;
            this.validators = new ArrayList();
        }

        /* access modifiers changed from: 0000 */
        public Builder forMethods() {
            this.methods = true;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public Builder withValidator(RuleValidator ruleValidator) {
            this.validators.add(ruleValidator);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public RuleMemberValidator build() {
            return new RuleMemberValidator(this);
        }
    }

    private static final class DeclaringClassMustBePublic implements RuleValidator {
        private DeclaringClassMustBePublic() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!isDeclaringClassPublic(frameworkMember)) {
                list.add(new ValidationError(frameworkMember, cls, "must be declared in a public class."));
            }
        }

        private boolean isDeclaringClassPublic(FrameworkMember<?> frameworkMember) {
            return Modifier.isPublic(frameworkMember.getDeclaringClass().getModifiers());
        }
    }

    private static final class FieldMustBeARule implements RuleValidator {
        private FieldMustBeARule() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!RuleMemberValidator.isRuleType(frameworkMember)) {
                list.add(new ValidationError(frameworkMember, cls, "must implement MethodRule or TestRule."));
            }
        }
    }

    private static final class FieldMustBeATestRule implements RuleValidator {
        private FieldMustBeATestRule() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!RuleMemberValidator.isTestRule(frameworkMember)) {
                list.add(new ValidationError(frameworkMember, cls, "must implement TestRule."));
            }
        }
    }

    private static final class MemberMustBeNonStaticOrAlsoClassRule implements RuleValidator {
        private MemberMustBeNonStaticOrAlsoClassRule() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            boolean access$1200 = RuleMemberValidator.isMethodRule(frameworkMember);
            boolean z = frameworkMember.getAnnotation(ClassRule.class) != null;
            if (!frameworkMember.isStatic()) {
                return;
            }
            if (access$1200 || !z) {
                list.add(new ValidationError(frameworkMember, cls, RuleMemberValidator.isMethodRule(frameworkMember) ? "must not be static." : "must not be static or it must be annotated with @ClassRule."));
            }
        }
    }

    private static final class MemberMustBePublic implements RuleValidator {
        private MemberMustBePublic() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!frameworkMember.isPublic()) {
                list.add(new ValidationError(frameworkMember, cls, "must be public."));
            }
        }
    }

    private static final class MemberMustBeStatic implements RuleValidator {
        private MemberMustBeStatic() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!frameworkMember.isStatic()) {
                list.add(new ValidationError(frameworkMember, cls, "must be static."));
            }
        }
    }

    private static final class MethodMustBeARule implements RuleValidator {
        private MethodMustBeARule() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!RuleMemberValidator.isRuleType(frameworkMember)) {
                list.add(new ValidationError(frameworkMember, cls, "must return an implementation of MethodRule or TestRule."));
            }
        }
    }

    private static final class MethodMustBeATestRule implements RuleValidator {
        private MethodMustBeATestRule() {
        }

        public void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list) {
            if (!RuleMemberValidator.isTestRule(frameworkMember)) {
                list.add(new ValidationError(frameworkMember, cls, "must return an implementation of TestRule."));
            }
        }
    }

    interface RuleValidator {
        void validate(FrameworkMember<?> frameworkMember, Class<? extends Annotation> cls, List<Throwable> list);
    }

    RuleMemberValidator(Builder builder) {
        this.annotation = builder.annotation;
        this.methods = builder.methods;
        this.validatorStrategies = builder.validators;
    }

    public void validate(TestClass testClass, List<Throwable> list) {
        for (FrameworkMember validateMember : this.methods ? testClass.getAnnotatedMethods(this.annotation) : testClass.getAnnotatedFields(this.annotation)) {
            validateMember(validateMember, list);
        }
    }

    private void validateMember(FrameworkMember<?> frameworkMember, List<Throwable> list) {
        for (RuleValidator validate : this.validatorStrategies) {
            validate.validate(frameworkMember, this.annotation, list);
        }
    }

    private static Builder classRuleValidatorBuilder() {
        return new Builder(ClassRule.class);
    }

    private static Builder testRuleValidatorBuilder() {
        return new Builder(Rule.class);
    }

    /* access modifiers changed from: private */
    public static boolean isRuleType(FrameworkMember<?> frameworkMember) {
        return isMethodRule(frameworkMember) || isTestRule(frameworkMember);
    }

    /* access modifiers changed from: private */
    public static boolean isTestRule(FrameworkMember<?> frameworkMember) {
        return TestRule.class.isAssignableFrom(frameworkMember.getType());
    }

    /* access modifiers changed from: private */
    public static boolean isMethodRule(FrameworkMember<?> frameworkMember) {
        return MethodRule.class.isAssignableFrom(frameworkMember.getType());
    }
}
