package org.junit.runners.model;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

class NoGenericTypeParametersValidator {
    private final Method method;

    NoGenericTypeParametersValidator(Method method2) {
        this.method = method2;
    }

    /* access modifiers changed from: 0000 */
    public void validate(List<Throwable> list) {
        for (Type validateNoTypeParameterOnType : this.method.getGenericParameterTypes()) {
            validateNoTypeParameterOnType(validateNoTypeParameterOnType, list);
        }
    }

    private void validateNoTypeParameterOnType(Type type, List<Throwable> list) {
        if (type instanceof TypeVariable) {
            StringBuilder sb = new StringBuilder();
            sb.append("Method ");
            sb.append(this.method.getName());
            sb.append("() contains unresolved type variable ");
            sb.append(type);
            list.add(new Exception(sb.toString()));
        } else if (type instanceof ParameterizedType) {
            validateNoTypeParameterOnParameterizedType((ParameterizedType) type, list);
        } else if (type instanceof WildcardType) {
            validateNoTypeParameterOnWildcardType((WildcardType) type, list);
        } else if (type instanceof GenericArrayType) {
            validateNoTypeParameterOnGenericArrayType((GenericArrayType) type, list);
        }
    }

    private void validateNoTypeParameterOnParameterizedType(ParameterizedType parameterizedType, List<Throwable> list) {
        for (Type validateNoTypeParameterOnType : parameterizedType.getActualTypeArguments()) {
            validateNoTypeParameterOnType(validateNoTypeParameterOnType, list);
        }
    }

    private void validateNoTypeParameterOnWildcardType(WildcardType wildcardType, List<Throwable> list) {
        for (Type validateNoTypeParameterOnType : wildcardType.getUpperBounds()) {
            validateNoTypeParameterOnType(validateNoTypeParameterOnType, list);
        }
        for (Type validateNoTypeParameterOnType2 : wildcardType.getLowerBounds()) {
            validateNoTypeParameterOnType(validateNoTypeParameterOnType2, list);
        }
    }

    private void validateNoTypeParameterOnGenericArrayType(GenericArrayType genericArrayType, List<Throwable> list) {
        validateNoTypeParameterOnType(genericArrayType.getGenericComponentType(), list);
    }
}
