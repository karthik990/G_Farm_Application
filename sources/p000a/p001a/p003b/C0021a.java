package p000a.p001a.p003b;

import p000a.p001a.C0073h;
import p000a.p001a.p003b.p005b.C0026b;
import p000a.p001a.p003b.p005b.C0032h;
import p000a.p001a.p007d.C0048b;

/* renamed from: a.a.b.a */
/* compiled from: StartAppSDK */
public final class C0021a {
    /* renamed from: a */
    public static final <T> Class<T> m23a(C0048b<T> bVar) {
        C0032h.m44b(bVar, "$receiver");
        Class a = ((C0026b) bVar).mo53a();
        String str = "null cannot be cast to non-null type java.lang.Class<T>";
        if (a.isPrimitive()) {
            String name = a.getName();
            if (name != null) {
                switch (name.hashCode()) {
                    case -1325958191:
                        if (name.equals("double")) {
                            a = Double.class;
                            break;
                        }
                        break;
                    case 104431:
                        if (name.equals("int")) {
                            a = Integer.class;
                            break;
                        }
                        break;
                    case 3039496:
                        if (name.equals("byte")) {
                            a = Byte.class;
                            break;
                        }
                        break;
                    case 3052374:
                        if (name.equals("char")) {
                            a = Character.class;
                            break;
                        }
                        break;
                    case 3327612:
                        if (name.equals("long")) {
                            a = Long.class;
                            break;
                        }
                        break;
                    case 3625364:
                        if (name.equals("void")) {
                            a = Void.class;
                            break;
                        }
                        break;
                    case 64711720:
                        if (name.equals("boolean")) {
                            a = Boolean.class;
                            break;
                        }
                        break;
                    case 97526364:
                        if (name.equals("float")) {
                            a = Float.class;
                            break;
                        }
                        break;
                    case 109413500:
                        if (name.equals("short")) {
                            a = Short.class;
                            break;
                        }
                        break;
                }
            }
            if (a != null) {
                return a;
            }
            throw new C0073h(str);
        } else if (a != null) {
            return a;
        } else {
            throw new C0073h(str);
        }
    }
}
