package com.flurry.sdk;

import com.flurry.sdk.C1756ex.C1758a;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.flurry.sdk.m */
public class C1942m<T> extends C1766f {
    protected Set<C1949o<T>> observers;

    public void refresh() {
    }

    public void start() {
    }

    protected C1942m(String str) {
        super(str, C1756ex.m904a(C1758a.PROVIDER));
        this.observers = null;
        this.observers = new HashSet();
    }

    public void subscribe(final C1949o<T> oVar) {
        if (oVar != null) {
            runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() {
                    C1942m.this.observers.add(oVar);
                }
            });
        }
    }

    public void unsubscribe(final C1949o<T> oVar) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1942m.this.observers.remove(oVar);
            }
        });
    }

    public void destroy() {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1942m.this.observers.clear();
            }
        });
    }

    public void notifyObservers(final T t) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                for (final C1949o oVar : C1942m.this.observers) {
                    C1942m.this.runAsync(new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() {
                            oVar.mo16242a(t);
                        }
                    });
                }
            }
        });
    }
}
