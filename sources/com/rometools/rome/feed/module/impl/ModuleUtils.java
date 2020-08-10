package com.rometools.rome.feed.module.impl;

import com.rometools.rome.feed.module.Module;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ModuleUtils.class);

    private ModuleUtils() {
    }

    public static List<Module> cloneModules(List<Module> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Module module : list) {
            try {
                arrayList.add((Module) module.clone());
            } catch (Exception e) {
                String uri = module.getUri();
                Logger logger = LOG;
                StringBuilder sb = new StringBuilder();
                sb.append("Error while cloning module ");
                sb.append(uri);
                logger.error(sb.toString(), (Throwable) e);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Cloning modules ");
                sb2.append(uri);
                throw new RuntimeException(sb2.toString(), e);
            }
        }
        return arrayList;
    }

    public static Module getModule(List<Module> list, String str) {
        if (list != null) {
            for (Module module : list) {
                if (module.getUri().equals(str)) {
                    return module;
                }
            }
        }
        return null;
    }
}
