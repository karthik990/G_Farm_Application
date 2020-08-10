package com.google.android.youtube.player.internal;

import android.os.IBinder;
import com.google.android.youtube.player.internal.C2830u.C2831a;
import java.lang.reflect.Field;

/* renamed from: com.google.android.youtube.player.internal.v */
public final class C2833v<T> extends C2831a {

    /* renamed from: a */
    private final T f1735a;

    private C2833v(T t) {
        this.f1735a = t;
    }

    /* renamed from: a */
    public static <T> C2830u m1739a(T t) {
        return new C2833v(t);
    }

    /* renamed from: a */
    public static <T> T m1740a(C2830u uVar) {
        if (uVar instanceof C2833v) {
            return ((C2833v) uVar).f1735a;
        }
        IBinder asBinder = uVar.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        if (declaredFields.length == 1) {
            Field field = declaredFields[0];
            if (!field.isAccessible()) {
                field.setAccessible(true);
                try {
                    return field.get(asBinder);
                } catch (NullPointerException e) {
                    throw new IllegalArgumentException("Binder object is null.", e);
                } catch (IllegalArgumentException e2) {
                    throw new IllegalArgumentException("remoteBinder is the wrong class.", e2);
                } catch (IllegalAccessException e3) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", e3);
                }
            } else {
                throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
            }
        } else {
            throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
        }
    }
}
