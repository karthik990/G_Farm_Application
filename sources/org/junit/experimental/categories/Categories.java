package org.junit.experimental.categories;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Categories extends Suite {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExcludeCategory {
        boolean matchAny() default true;

        Class<?>[] value() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface IncludeCategory {
        boolean matchAny() default true;

        Class<?>[] value() default {};
    }

    public static class CategoryFilter extends Filter {
        private final Set<Class<?>> excluded;
        private final boolean excludedAny;
        private final Set<Class<?>> included;
        private final boolean includedAny;

        public static CategoryFilter include(boolean z, Class<?>... clsArr) {
            if (!hasNull(clsArr)) {
                return categoryFilter(z, Categories.createSet(clsArr), true, null);
            }
            throw new NullPointerException("has null category");
        }

        public static CategoryFilter include(Class<?> cls) {
            return include(true, cls);
        }

        public static CategoryFilter include(Class<?>... clsArr) {
            return include(true, clsArr);
        }

        public static CategoryFilter exclude(boolean z, Class<?>... clsArr) {
            if (!hasNull(clsArr)) {
                return categoryFilter(true, null, z, Categories.createSet(clsArr));
            }
            throw new NullPointerException("has null category");
        }

        public static CategoryFilter exclude(Class<?> cls) {
            return exclude(true, cls);
        }

        public static CategoryFilter exclude(Class<?>... clsArr) {
            return exclude(true, clsArr);
        }

        public static CategoryFilter categoryFilter(boolean z, Set<Class<?>> set, boolean z2, Set<Class<?>> set2) {
            return new CategoryFilter(z, set, z2, set2);
        }

        protected CategoryFilter(boolean z, Set<Class<?>> set, boolean z2, Set<Class<?>> set2) {
            this.includedAny = z;
            this.excludedAny = z2;
            this.included = copyAndRefine(set);
            this.excluded = copyAndRefine(set2);
        }

        public String describe() {
            return toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("categories ");
            sb.append(this.included.isEmpty() ? "[all]" : this.included);
            if (!this.excluded.isEmpty()) {
                sb.append(" - ");
                sb.append(this.excluded);
            }
            return sb.toString();
        }

        public boolean shouldRun(Description description) {
            if (hasCorrectCategoryAnnotation(description)) {
                return true;
            }
            Iterator it = description.getChildren().iterator();
            while (it.hasNext()) {
                if (shouldRun((Description) it.next())) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasCorrectCategoryAnnotation(Description description) {
            Set categories = categories(description);
            if (categories.isEmpty()) {
                return this.included.isEmpty();
            }
            if (!this.excluded.isEmpty()) {
                if (this.excludedAny) {
                    if (matchesAnyParentCategories(categories, this.excluded)) {
                        return false;
                    }
                } else if (matchesAllParentCategories(categories, this.excluded)) {
                    return false;
                }
            }
            if (this.included.isEmpty()) {
                return true;
            }
            if (this.includedAny) {
                return matchesAnyParentCategories(categories, this.included);
            }
            return matchesAllParentCategories(categories, this.included);
        }

        private boolean matchesAnyParentCategories(Set<Class<?>> set, Set<Class<?>> set2) {
            for (Class access$100 : set2) {
                if (Categories.hasAssignableTo(set, access$100)) {
                    return true;
                }
            }
            return false;
        }

        private boolean matchesAllParentCategories(Set<Class<?>> set, Set<Class<?>> set2) {
            for (Class access$100 : set2) {
                if (!Categories.hasAssignableTo(set, access$100)) {
                    return false;
                }
            }
            return true;
        }

        private static Set<Class<?>> categories(Description description) {
            HashSet hashSet = new HashSet();
            Collections.addAll(hashSet, directCategories(description));
            Collections.addAll(hashSet, directCategories(parentDescription(description)));
            return hashSet;
        }

        private static Description parentDescription(Description description) {
            Class testClass = description.getTestClass();
            if (testClass == null) {
                return null;
            }
            return Description.createSuiteDescription(testClass);
        }

        private static Class<?>[] directCategories(Description description) {
            Class<?>[] clsArr;
            if (description == null) {
                return new Class[0];
            }
            Category category = (Category) description.getAnnotation(Category.class);
            if (category == null) {
                clsArr = new Class[0];
            } else {
                clsArr = category.value();
            }
            return clsArr;
        }

        private static Set<Class<?>> copyAndRefine(Set<Class<?>> set) {
            HashSet hashSet = new HashSet();
            if (set != null) {
                hashSet.addAll(set);
            }
            hashSet.remove(null);
            return hashSet;
        }

        private static boolean hasNull(Class<?>... clsArr) {
            if (clsArr == null) {
                return false;
            }
            for (Class<?> cls : clsArr) {
                if (cls == null) {
                    return true;
                }
            }
            return false;
        }
    }

    public Categories(Class<?> cls, RunnerBuilder runnerBuilder) throws InitializationError {
        super(cls, runnerBuilder);
        try {
            filter(CategoryFilter.categoryFilter(isAnyIncluded(cls), getIncludedCategory(cls), isAnyExcluded(cls), getExcludedCategory(cls)));
            assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
        } catch (NoTestsRemainException e) {
            throw new InitializationError((Throwable) e);
        }
    }

    private static Set<Class<?>> getIncludedCategory(Class<?> cls) {
        Class[] clsArr;
        IncludeCategory includeCategory = (IncludeCategory) cls.getAnnotation(IncludeCategory.class);
        if (includeCategory == null) {
            clsArr = null;
        } else {
            clsArr = includeCategory.value();
        }
        return createSet(clsArr);
    }

    private static boolean isAnyIncluded(Class<?> cls) {
        IncludeCategory includeCategory = (IncludeCategory) cls.getAnnotation(IncludeCategory.class);
        return includeCategory == null || includeCategory.matchAny();
    }

    private static Set<Class<?>> getExcludedCategory(Class<?> cls) {
        Class[] clsArr;
        ExcludeCategory excludeCategory = (ExcludeCategory) cls.getAnnotation(ExcludeCategory.class);
        if (excludeCategory == null) {
            clsArr = null;
        } else {
            clsArr = excludeCategory.value();
        }
        return createSet(clsArr);
    }

    private static boolean isAnyExcluded(Class<?> cls) {
        ExcludeCategory excludeCategory = (ExcludeCategory) cls.getAnnotation(ExcludeCategory.class);
        return excludeCategory == null || excludeCategory.matchAny();
    }

    private static void assertNoCategorizedDescendentsOfUncategorizeableParents(Description description) throws InitializationError {
        if (!canHaveCategorizedChildren(description)) {
            assertNoDescendantsHaveCategoryAnnotations(description);
        }
        Iterator it = description.getChildren().iterator();
        while (it.hasNext()) {
            assertNoCategorizedDescendentsOfUncategorizeableParents((Description) it.next());
        }
    }

    private static void assertNoDescendantsHaveCategoryAnnotations(Description description) throws InitializationError {
        Iterator it = description.getChildren().iterator();
        while (it.hasNext()) {
            Description description2 = (Description) it.next();
            if (description2.getAnnotation(Category.class) == null) {
                assertNoDescendantsHaveCategoryAnnotations(description2);
            } else {
                throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
            }
        }
    }

    private static boolean canHaveCategorizedChildren(Description description) {
        Iterator it = description.getChildren().iterator();
        while (it.hasNext()) {
            if (((Description) it.next()).getTestClass() == null) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean hasAssignableTo(Set<Class<?>> set, Class<?> cls) {
        for (Class isAssignableFrom : set) {
            if (cls.isAssignableFrom(isAssignableFrom)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static Set<Class<?>> createSet(Class<?>... clsArr) {
        HashSet hashSet = new HashSet();
        if (clsArr != null) {
            Collections.addAll(hashSet, clsArr);
        }
        return hashSet;
    }
}
