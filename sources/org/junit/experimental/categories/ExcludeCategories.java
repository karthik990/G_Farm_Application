package org.junit.experimental.categories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.experimental.categories.Categories.CategoryFilter;
import org.junit.runner.manipulation.Filter;

public final class ExcludeCategories extends CategoryFilterFactory {

    private static class ExcludesAny extends CategoryFilter {
        public ExcludesAny(List<Class<?>> list) {
            this((Set<Class<?>>) new HashSet<Class<?>>(list));
        }

        public ExcludesAny(Set<Class<?>> set) {
            super(true, null, true, set);
        }

        public String describe() {
            StringBuilder sb = new StringBuilder();
            sb.append("excludes ");
            sb.append(super.describe());
            return sb.toString();
        }
    }

    /* access modifiers changed from: protected */
    public Filter createFilter(List<Class<?>> list) {
        return new ExcludesAny(list);
    }
}
