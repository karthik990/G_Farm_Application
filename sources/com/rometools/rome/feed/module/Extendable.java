package com.rometools.rome.feed.module;

import java.util.List;

public interface Extendable {
    Module getModule(String str);

    List<Module> getModules();

    void setModules(List<Module> list);
}
