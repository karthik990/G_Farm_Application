package org.junit.runners.model;

import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.runners.model.FrameworkMember;

public abstract class FrameworkMember<T extends FrameworkMember<T>> implements Annotatable {
    public abstract Class<?> getDeclaringClass();

    /* access modifiers changed from: protected */
    public abstract int getModifiers();

    public abstract String getName();

    public abstract Class<?> getType();

    /* access modifiers changed from: 0000 */
    public abstract boolean isShadowedBy(T t);

    /* access modifiers changed from: 0000 */
    public boolean isShadowedBy(List<T> list) {
        for (T isShadowedBy : list) {
            if (isShadowedBy(isShadowedBy)) {
                return true;
            }
        }
        return false;
    }

    public boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }
}
