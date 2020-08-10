package org.junit.runner;

import org.junit.internal.Classes;
import org.junit.runner.FilterFactory.FilterNotCreatedException;
import org.junit.runner.manipulation.Filter;

class FilterFactories {
    FilterFactories() {
    }

    public static Filter createFilterFromFilterSpec(Request request, String str) throws FilterNotCreatedException {
        String[] strArr;
        Description description = request.getRunner().getDescription();
        String str2 = "=";
        if (str.contains(str2)) {
            strArr = str.split(str2, 2);
        } else {
            strArr = new String[]{str, ""};
        }
        return createFilter(strArr[0], new FilterFactoryParams(description, strArr[1]));
    }

    public static Filter createFilter(String str, FilterFactoryParams filterFactoryParams) throws FilterNotCreatedException {
        return createFilterFactory(str).createFilter(filterFactoryParams);
    }

    public static Filter createFilter(Class<? extends FilterFactory> cls, FilterFactoryParams filterFactoryParams) throws FilterNotCreatedException {
        return createFilterFactory(cls).createFilter(filterFactoryParams);
    }

    static FilterFactory createFilterFactory(String str) throws FilterNotCreatedException {
        try {
            return createFilterFactory(Classes.getClass(str).asSubclass(FilterFactory.class));
        } catch (Exception e) {
            throw new FilterNotCreatedException(e);
        }
    }

    static FilterFactory createFilterFactory(Class<? extends FilterFactory> cls) throws FilterNotCreatedException {
        try {
            return (FilterFactory) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            throw new FilterNotCreatedException(e);
        }
    }
}
