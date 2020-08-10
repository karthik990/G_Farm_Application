package org.junit.experimental.categories;

import java.util.ArrayList;
import java.util.List;
import org.junit.internal.Classes;
import org.junit.runner.FilterFactory;
import org.junit.runner.FilterFactory.FilterNotCreatedException;
import org.junit.runner.FilterFactoryParams;
import org.junit.runner.manipulation.Filter;

abstract class CategoryFilterFactory implements FilterFactory {
    /* access modifiers changed from: protected */
    public abstract Filter createFilter(List<Class<?>> list);

    CategoryFilterFactory() {
    }

    public Filter createFilter(FilterFactoryParams filterFactoryParams) throws FilterNotCreatedException {
        try {
            return createFilter(parseCategories(filterFactoryParams.getArgs()));
        } catch (ClassNotFoundException e) {
            throw new FilterNotCreatedException(e);
        }
    }

    private List<Class<?>> parseCategories(String str) throws ClassNotFoundException {
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split(",")) {
            arrayList.add(Classes.getClass(str2));
        }
        return arrayList;
    }
}
