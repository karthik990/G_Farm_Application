package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.module.DCSubject;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

class SyndCategoryListFacade extends AbstractList<SyndCategory> {
    private final List<DCSubject> subjects;

    public SyndCategoryListFacade() {
        this(new ArrayList());
    }

    public SyndCategoryListFacade(List<DCSubject> list) {
        this.subjects = list;
    }

    public SyndCategory get(int i) {
        return new SyndCategoryImpl((DCSubject) this.subjects.get(i));
    }

    public int size() {
        return this.subjects.size();
    }

    public SyndCategory set(int i, SyndCategory syndCategory) {
        SyndCategoryImpl syndCategoryImpl = (SyndCategoryImpl) syndCategory;
        DCSubject dCSubject = (DCSubject) this.subjects.set(i, syndCategoryImpl != null ? syndCategoryImpl.getSubject() : null);
        if (dCSubject != null) {
            return new SyndCategoryImpl(dCSubject);
        }
        return null;
    }

    public void add(int i, SyndCategory syndCategory) {
        SyndCategoryImpl syndCategoryImpl = (SyndCategoryImpl) syndCategory;
        this.subjects.add(i, syndCategoryImpl != null ? syndCategoryImpl.getSubject() : null);
    }

    public SyndCategory remove(int i) {
        DCSubject dCSubject = (DCSubject) this.subjects.remove(i);
        if (dCSubject != null) {
            return new SyndCategoryImpl(dCSubject);
        }
        return null;
    }

    public static List<DCSubject> convertElementsSyndCategoryToSubject(List<SyndCategory> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SyndCategoryImpl syndCategoryImpl = (SyndCategoryImpl) list.get(i);
            arrayList.add(syndCategoryImpl != null ? syndCategoryImpl.getSubject() : null);
        }
        return arrayList;
    }
}
