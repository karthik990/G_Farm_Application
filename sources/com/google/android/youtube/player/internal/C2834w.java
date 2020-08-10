package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import com.google.android.youtube.player.internal.C2780d.C2781a;
import java.lang.reflect.InvocationTargetException;

/* renamed from: com.google.android.youtube.player.internal.w */
public final class C2834w {

    /* renamed from: com.google.android.youtube.player.internal.w$a */
    public static final class C2835a extends Exception {
        public C2835a(String str) {
            super(str);
        }

        public C2835a(String str, Throwable th) {
            super(str, th);
        }
    }

    /* renamed from: a */
    private static IBinder m1741a(Class<?> cls, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, boolean z) throws C2835a {
        try {
            return (IBinder) cls.getConstructor(new Class[]{IBinder.class, IBinder.class, IBinder.class, Boolean.TYPE}).newInstance(new Object[]{iBinder, iBinder2, iBinder3, Boolean.valueOf(z)});
        } catch (NoSuchMethodException e) {
            String str = "Could not find the right constructor for ";
            String valueOf = String.valueOf(cls.getName());
            throw new C2835a(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
        } catch (InvocationTargetException e2) {
            String str2 = "Exception thrown by invoked constructor in ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new C2835a(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e2);
        } catch (InstantiationException e3) {
            String str3 = "Unable to instantiate the dynamic class ";
            String valueOf3 = String.valueOf(cls.getName());
            throw new C2835a(valueOf3.length() != 0 ? str3.concat(valueOf3) : new String(str3), e3);
        } catch (IllegalAccessException e4) {
            String str4 = "Unable to call the default constructor of ";
            String valueOf4 = String.valueOf(cls.getName());
            throw new C2835a(valueOf4.length() != 0 ? str4.concat(valueOf4) : new String(str4), e4);
        }
    }

    /* renamed from: a */
    private static IBinder m1742a(ClassLoader classLoader, String str, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, boolean z) throws C2835a {
        try {
            return m1741a(classLoader.loadClass(str), iBinder, iBinder2, iBinder3, z);
        } catch (ClassNotFoundException e) {
            String str2 = "Unable to find dynamic class ";
            String valueOf = String.valueOf(str);
            throw new C2835a(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        }
    }

    /* renamed from: a */
    public static C2780d m1743a(Activity activity, IBinder iBinder, boolean z) throws C2835a {
        C2774ab.m1495a(activity);
        C2774ab.m1495a(iBinder);
        Context b = C2838z.m1753b((Context) activity);
        if (b != null) {
            return C2781a.m1550a(m1742a(b.getClassLoader(), "com.google.android.youtube.api.jar.client.RemoteEmbeddedPlayer", C2833v.m1739a(b).asBinder(), C2833v.m1739a(activity).asBinder(), iBinder, z));
        }
        throw new C2835a("Could not create remote context");
    }
}
