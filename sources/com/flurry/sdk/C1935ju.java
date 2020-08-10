package com.flurry.sdk;

import com.flurry.sdk.C1756ex.C1758a;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.flurry.sdk.ju */
public final class C1935ju extends C1766f implements C1934jt {

    /* renamed from: a */
    public C1938jv f1389a = null;

    /* renamed from: b */
    public C1931jq f1390b;

    public C1935ju(C1931jq jqVar) {
        super("VNodeFileProcessor", C1756ex.m904a(C1758a.DATA_PROCESSOR));
        this.f1390b = jqVar;
    }

    /* renamed from: a */
    public final void mo16523a(final List<File> list) {
        if (list != null && list.size() != 0) {
            runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    StringBuilder sb = new StringBuilder("Number of files already pending: in VNodeListener ");
                    sb.append(list.size());
                    C1685cy.m754a(2, "VNodeFileProcessor", sb.toString());
                    ArrayList arrayList = new ArrayList();
                    for (File file : list) {
                        if (file.exists()) {
                            arrayList.add(file.getAbsolutePath());
                        }
                    }
                    if (C1935ju.this.f1390b != null) {
                        C1935ju.this.f1390b.mo16457a(arrayList);
                    }
                }
            });
        }
    }

    /* renamed from: a */
    public final void mo16522a(String str) {
        String b = C1776ff.m943b();
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append(File.separator);
        sb.append(str);
        File file = new File(sb.toString());
        if (file.exists()) {
            mo16523a(Arrays.asList(new File[]{file}));
        }
    }
}
