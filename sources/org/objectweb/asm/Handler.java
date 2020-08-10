package org.objectweb.asm;

class Handler {

    /* renamed from: a */
    Label f4662a;

    /* renamed from: b */
    Label f4663b;

    /* renamed from: c */
    Label f4664c;

    /* renamed from: d */
    String f4665d;

    /* renamed from: e */
    int f4666e;

    /* renamed from: f */
    Handler f4667f;

    Handler() {
    }

    /* renamed from: a */
    static Handler m4209a(Handler handler, Label label, Label label2) {
        if (handler == null) {
            return null;
        }
        handler.f4667f = m4209a(handler.f4667f, label, label2);
        int i = handler.f4662a.f4679c;
        int i2 = handler.f4663b.f4679c;
        int i3 = label.f4679c;
        int i4 = label2 == null ? Integer.MAX_VALUE : label2.f4679c;
        if (i3 < i2 && i4 > i) {
            if (i3 <= i) {
                if (i4 >= i2) {
                    handler = handler.f4667f;
                } else {
                    handler.f4662a = label2;
                }
            } else if (i4 >= i2) {
                handler.f4663b = label;
            } else {
                Handler handler2 = new Handler();
                handler2.f4662a = label2;
                handler2.f4663b = handler.f4663b;
                handler2.f4664c = handler.f4664c;
                handler2.f4665d = handler.f4665d;
                handler2.f4666e = handler.f4666e;
                handler2.f4667f = handler.f4667f;
                handler.f4663b = label;
                handler.f4667f = handler2;
            }
        }
        return handler;
    }
}
