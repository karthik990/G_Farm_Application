package butterknife.internal;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import java.util.Arrays;
import java.util.List;

/* renamed from: butterknife.internal.Utils */
public final class C0812Utils {
    private static final TypedValue VALUE = new TypedValue();

    public static Drawable getTintedDrawable(Context context, int i, int i2) {
        if (context.getTheme().resolveAttribute(i2, VALUE, true)) {
            Drawable wrap = DrawableCompat.wrap(ContextCompat.getDrawable(context, i).mutate());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, VALUE.resourceId));
            return wrap;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Required tint color attribute with name ");
        sb.append(context.getResources().getResourceEntryName(i2));
        sb.append(" and attribute ID ");
        sb.append(i2);
        sb.append(" was not found.");
        throw new NotFoundException(sb.toString());
    }

    public static float getFloat(Context context, int i) {
        TypedValue typedValue = VALUE;
        context.getResources().getValue(i, typedValue, true);
        if (typedValue.type == 4) {
            return typedValue.getFloat();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Resource ID #0x");
        sb.append(Integer.toHexString(i));
        sb.append(" type #0x");
        sb.append(Integer.toHexString(typedValue.type));
        sb.append(" is not valid");
        throw new NotFoundException(sb.toString());
    }

    @SafeVarargs
    public static <T> T[] arrayFilteringNull(T... tArr) {
        int i = 0;
        for (T t : tArr) {
            if (t != null) {
                int i2 = i + 1;
                tArr[i] = t;
                i = i2;
            }
        }
        return i == r0 ? tArr : Arrays.copyOf(tArr, i);
    }

    @SafeVarargs
    public static <T> List<T> listFilteringNull(T... tArr) {
        return new ImmutableList(arrayFilteringNull(tArr));
    }

    public static <T> T findOptionalViewAsType(View view, int i, String str, Class<T> cls) {
        return castView(view.findViewById(i), i, str, cls);
    }

    public static View findRequiredView(View view, int i, String str) {
        View findViewById = view.findViewById(i);
        if (findViewById != null) {
            return findViewById;
        }
        String resourceEntryName = getResourceEntryName(view, i);
        StringBuilder sb = new StringBuilder();
        sb.append("Required view '");
        sb.append(resourceEntryName);
        sb.append("' with ID ");
        sb.append(i);
        sb.append(" for ");
        sb.append(str);
        sb.append(" was not found. If this view is optional add '@Nullable' (fields) or '@Optional' (methods) annotation.");
        throw new IllegalStateException(sb.toString());
    }

    public static <T> T findRequiredViewAsType(View view, int i, String str, Class<T> cls) {
        return castView(findRequiredView(view, i, str), i, str, cls);
    }

    public static <T> T castView(View view, int i, String str, Class<T> cls) {
        try {
            return cls.cast(view);
        } catch (ClassCastException e) {
            String resourceEntryName = getResourceEntryName(view, i);
            StringBuilder sb = new StringBuilder();
            sb.append("View '");
            sb.append(resourceEntryName);
            sb.append("' with ID ");
            sb.append(i);
            sb.append(" for ");
            sb.append(str);
            sb.append(" was of the wrong type. See cause for more info.");
            throw new IllegalStateException(sb.toString(), e);
        }
    }

    public static <T> T castParam(Object obj, String str, int i, String str2, int i2, Class<T> cls) {
        try {
            return cls.cast(obj);
        } catch (ClassCastException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Parameter #");
            sb.append(i + 1);
            String str3 = " of method '";
            sb.append(str3);
            sb.append(str);
            sb.append("' was of the wrong type for parameter #");
            sb.append(i2 + 1);
            sb.append(str3);
            sb.append(str2);
            sb.append("'. See cause for more info.");
            throw new IllegalStateException(sb.toString(), e);
        }
    }

    private static String getResourceEntryName(View view, int i) {
        if (view.isInEditMode()) {
            return "<unavailable while editing>";
        }
        return view.getContext().getResources().getResourceEntryName(i);
    }

    private C0812Utils() {
        throw new AssertionError("No instances.");
    }
}
