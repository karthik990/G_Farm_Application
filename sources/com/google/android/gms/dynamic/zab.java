package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;

final class zab implements zaa {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ DeferredLifecycleHelper zarj;
    private final /* synthetic */ Bundle zark;
    private final /* synthetic */ Bundle zarl;

    zab(DeferredLifecycleHelper deferredLifecycleHelper, Activity activity, Bundle bundle, Bundle bundle2) {
        this.zarj = deferredLifecycleHelper;
        this.val$activity = activity;
        this.zark = bundle;
        this.zarl = bundle2;
    }

    public final int getState() {
        return 0;
    }

    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000c: INVOKE  (wrap: com.google.android.gms.dynamic.LifecycleDelegate
              0x0002: INVOKE  (r4v2 com.google.android.gms.dynamic.LifecycleDelegate) = (wrap: com.google.android.gms.dynamic.DeferredLifecycleHelper
              0x0000: IGET  (r4v1 com.google.android.gms.dynamic.DeferredLifecycleHelper) = (r3v0 'this' com.google.android.gms.dynamic.zab A[THIS]) com.google.android.gms.dynamic.zab.zarj com.google.android.gms.dynamic.DeferredLifecycleHelper) com.google.android.gms.dynamic.DeferredLifecycleHelper.zab(com.google.android.gms.dynamic.DeferredLifecycleHelper):com.google.android.gms.dynamic.LifecycleDelegate type: STATIC), (wrap: android.app.Activity
              0x0006: IGET  (r0v0 android.app.Activity) = (r3v0 'this' com.google.android.gms.dynamic.zab A[THIS]) com.google.android.gms.dynamic.zab.val$activity android.app.Activity), (wrap: android.os.Bundle
              0x0008: IGET  (r1v0 android.os.Bundle) = (r3v0 'this' com.google.android.gms.dynamic.zab A[THIS]) com.google.android.gms.dynamic.zab.zark android.os.Bundle), (wrap: android.os.Bundle
              0x000a: IGET  (r2v0 android.os.Bundle) = (r3v0 'this' com.google.android.gms.dynamic.zab A[THIS]) com.google.android.gms.dynamic.zab.zarl android.os.Bundle) com.google.android.gms.dynamic.LifecycleDelegate.onInflate(android.app.Activity, android.os.Bundle, android.os.Bundle):void type: INTERFACE in method: com.google.android.gms.dynamic.zab.zaa(com.google.android.gms.dynamic.LifecycleDelegate):void, dex: classes2.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:316)
            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:262)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:225)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:76)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
            	at jadx.core.ProcessClass.process(ProcessClass.java:36)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
            Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: INVOKE  (r4v2 com.google.android.gms.dynamic.LifecycleDelegate) = (wrap: com.google.android.gms.dynamic.DeferredLifecycleHelper
              0x0000: IGET  (r4v1 com.google.android.gms.dynamic.DeferredLifecycleHelper) = (r3v0 'this' com.google.android.gms.dynamic.zab A[THIS]) com.google.android.gms.dynamic.zab.zarj com.google.android.gms.dynamic.DeferredLifecycleHelper) com.google.android.gms.dynamic.DeferredLifecycleHelper.zab(com.google.android.gms.dynamic.DeferredLifecycleHelper):com.google.android.gms.dynamic.LifecycleDelegate type: STATIC in method: com.google.android.gms.dynamic.zab.zaa(com.google.android.gms.dynamic.LifecycleDelegate):void, dex: classes2.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:245)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:88)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:682)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:239)
            	... 19 more
            Caused by: java.util.ConcurrentModificationException
            	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1714)
            	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1689)
            	at jadx.core.dex.instructions.args.SSAVar.removeUse(SSAVar.java:86)
            	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:90)
            	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:130)
            	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:134)
            	at jadx.core.codegen.InsnGen.inlineMethod(InsnGen.java:892)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:669)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:357)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
            	... 24 more
            */
        /*
            this = this;
            com.google.android.gms.dynamic.DeferredLifecycleHelper r4 = r3.zarj
            com.google.android.gms.dynamic.LifecycleDelegate r4 = r4.zarf
            android.app.Activity r0 = r3.val$activity
            android.os.Bundle r1 = r3.zark
            android.os.Bundle r2 = r3.zarl
            r4.onInflate(r0, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamic.zab.zaa(com.google.android.gms.dynamic.LifecycleDelegate):void");
    }
}
