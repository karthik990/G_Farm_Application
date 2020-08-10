package com.flurry.sdk;

import org.objectweb.asm.Opcodes;

/* renamed from: com.flurry.sdk.ch */
public final class C1661ch {

    /* renamed from: a */
    final C1662a f816a;

    /* renamed from: b */
    private int f817b = m677b();

    /* renamed from: com.flurry.sdk.ch$a */
    public enum C1662a {
        TEN_SEC(10),
        THIRTY_SEC(30),
        THREE_MIN(Opcodes.GETFIELD),
        ABANDON(0);
        

        /* renamed from: e */
        int f823e;

        private C1662a(int i) {
            this.f823e = i;
        }

        /* renamed from: a */
        public final C1662a mo16354a() {
            if (ordinal() == values().length - 1) {
                return this;
            }
            return values()[ordinal() + 1];
        }
    }

    public C1661ch(C1662a aVar) {
        this.f816a = aVar;
    }

    /* renamed from: b */
    private static int m677b() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /* renamed from: a */
    public final int mo16353a() {
        return (this.f816a.f823e + this.f817b) - m677b();
    }
}
