package org.junit.experimental.categories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.experimental.categories.Categories.CategoryFilter;
import org.junit.runner.manipulation.Filter;

public final class IncludeCategories extends CategoryFilterFactory {

    private static class IncludesAny extends CategoryFilter {
        public IncludesAny(List<Class<?>> list) {
            this((Set<Class<?>>) new HashSet<Class<?>>(list));
        }

        public IncludesAny(Set<Class<?>> set) {
            super(true, set, true, null);
        }

        public String describe() {
            StringBuilder sb = new StringBuilder();
            sb.append("includes ");
            sb.append(super.describe());
            return sb.toString();
        }
    }

    /* access modifiers changed from: protected */
    public Filter createFilter(List<Class<?>> list) {
        return new IncludesAny(list);
    }
}
