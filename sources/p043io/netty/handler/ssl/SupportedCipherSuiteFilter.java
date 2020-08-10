package p043io.netty.handler.ssl;

/* renamed from: io.netty.handler.ssl.SupportedCipherSuiteFilter */
public final class SupportedCipherSuiteFilter implements CipherSuiteFilter {
    public static final SupportedCipherSuiteFilter INSTANCE = new SupportedCipherSuiteFilter();

    private SupportedCipherSuiteFilter() {
    }

    /* JADX WARNING: type inference failed for: r4v3, types: [java.lang.Iterable] */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /*  JADX ERROR: UnsupportedOperationException in pass: LoopRegionVisitor
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:438)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.fixIterableType(LoopRegionVisitor.java:334)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkIterableForEach(LoopRegionVisitor.java:270)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:75)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:59)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1083)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1083)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:53)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    public java.lang.String[] filterCipherSuites(java.lang.Iterable<java.lang.String> r4, java.util.List<java.lang.String> r5, java.util.Set<java.lang.String> r6) {
        /*
            r3 = this;
            if (r5 == 0) goto L_0x004e
            if (r6 == 0) goto L_0x0046
            if (r4 != 0) goto L_0x0013
            java.util.ArrayList r4 = new java.util.ArrayList
            int r0 = r5.size()
            r4.<init>(r0)
            r2 = r5
            r5 = r4
            r4 = r2
            goto L_0x001c
        L_0x0013:
            java.util.ArrayList r5 = new java.util.ArrayList
            int r0 = r6.size()
            r5.<init>(r0)
        L_0x001c:
            java.util.Iterator r4 = r4.iterator()
        L_0x0020:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x0039
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x002f
            goto L_0x0039
        L_0x002f:
            boolean r1 = r6.contains(r0)
            if (r1 == 0) goto L_0x0020
            r5.add(r0)
            goto L_0x0020
        L_0x0039:
            int r4 = r5.size()
            java.lang.String[] r4 = new java.lang.String[r4]
            java.lang.Object[] r4 = r5.toArray(r4)
            java.lang.String[] r4 = (java.lang.String[]) r4
            return r4
        L_0x0046:
            java.lang.NullPointerException r4 = new java.lang.NullPointerException
            java.lang.String r5 = "supportedCiphers"
            r4.<init>(r5)
            throw r4
        L_0x004e:
            java.lang.NullPointerException r4 = new java.lang.NullPointerException
            java.lang.String r5 = "defaultCiphers"
            r4.<init>(r5)
            goto L_0x0057
        L_0x0056:
            throw r4
        L_0x0057:
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SupportedCipherSuiteFilter.filterCipherSuites(java.lang.Iterable, java.util.List, java.util.Set):java.lang.String[]");
    }
}
