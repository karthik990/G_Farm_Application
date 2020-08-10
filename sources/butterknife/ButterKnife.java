package butterknife;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ButterKnife {
    static final Map<Class<?>, Constructor<? extends Unbinder>> BINDINGS = new LinkedHashMap();
    private static final String TAG = "ButterKnife";
    private static boolean debug = false;

    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    public static void setDebug(boolean z) {
        debug = z;
    }

    public static Unbinder bind(Activity activity) {
        return bind((Object) activity, activity.getWindow().getDecorView());
    }

    public static Unbinder bind(View view) {
        return bind((Object) view, view);
    }

    public static Unbinder bind(Dialog dialog) {
        return bind((Object) dialog, dialog.getWindow().getDecorView());
    }

    public static Unbinder bind(Object obj, Activity activity) {
        return bind(obj, activity.getWindow().getDecorView());
    }

    public static Unbinder bind(Object obj, Dialog dialog) {
        return bind(obj, dialog.getWindow().getDecorView());
    }

    public static Unbinder bind(Object obj, View view) {
        String str = "Unable to invoke ";
        Class cls = obj.getClass();
        if (debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("Looking up binding for ");
            sb.append(cls.getName());
            Log.d(TAG, sb.toString());
        }
        Constructor findBindingConstructorForClass = findBindingConstructorForClass(cls);
        if (findBindingConstructorForClass == null) {
            return Unbinder.EMPTY;
        }
        try {
            return (Unbinder) findBindingConstructorForClass.newInstance(new Object[]{obj, view});
        } catch (IllegalAccessException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(findBindingConstructorForClass);
            throw new RuntimeException(sb2.toString(), e);
        } catch (InstantiationException e2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(findBindingConstructorForClass);
            throw new RuntimeException(sb3.toString(), e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unable to create binding instance.", cause);
            }
        }
    }

    private static Constructor<? extends Unbinder> findBindingConstructorForClass(Class<?> cls) {
        Constructor<? extends Unbinder> constructor;
        Constructor<? extends Unbinder> constructor2 = (Constructor) BINDINGS.get(cls);
        String str = TAG;
        if (constructor2 != null || BINDINGS.containsKey(cls)) {
            if (debug) {
                Log.d(str, "HIT: Cached in binding map.");
            }
            return constructor2;
        }
        String name = cls.getName();
        if (name.startsWith("android.") || name.startsWith("java.") || name.startsWith("androidx.")) {
            if (debug) {
                Log.d(str, "MISS: Reached framework class. Abandoning search.");
            }
            return null;
        }
        try {
            ClassLoader classLoader = cls.getClassLoader();
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append("_ViewBinding");
            constructor = classLoader.loadClass(sb.toString()).getConstructor(new Class[]{cls, View.class});
            if (debug) {
                Log.d(str, "HIT: Loaded binding class and constructor.");
            }
        } catch (ClassNotFoundException unused) {
            if (debug) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Not found. Trying superclass ");
                sb2.append(cls.getSuperclass().getName());
                Log.d(str, sb2.toString());
            }
            constructor = findBindingConstructorForClass(cls.getSuperclass());
        } catch (NoSuchMethodException e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unable to find binding constructor for ");
            sb3.append(name);
            throw new RuntimeException(sb3.toString(), e);
        }
        BINDINGS.put(cls, constructor);
        return constructor;
    }
}
