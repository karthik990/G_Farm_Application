package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import androidx.work.Logger;

public class PackageManagerHelper {
    private static final String TAG = Logger.tagWithPrefix("PackageManagerHelper");

    private PackageManagerHelper() {
    }

    public static void setComponentEnabled(Context context, Class<?> cls, boolean z) {
        String str = "enabled";
        String str2 = "disabled";
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, cls.getName()), z ? 1 : 2, 1);
            Logger logger = Logger.get();
            String str3 = TAG;
            String str4 = "%s %s";
            Object[] objArr = new Object[2];
            objArr[0] = cls.getName();
            objArr[1] = z ? str : str2;
            logger.debug(str3, String.format(str4, objArr), new Throwable[0]);
        } catch (Exception e) {
            Logger logger2 = Logger.get();
            String str5 = TAG;
            Object[] objArr2 = new Object[2];
            objArr2[0] = cls.getName();
            if (!z) {
                str = str2;
            }
            objArr2[1] = str;
            logger2.debug(str5, String.format("%s could not be %s", objArr2), e);
        }
    }

    public static boolean isComponentExplicitlyEnabled(Context context, Class<?> cls) {
        return isComponentExplicitlyEnabled(context, cls.getName());
    }

    public static boolean isComponentExplicitlyEnabled(Context context, String str) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, str)) == 1;
    }
}
