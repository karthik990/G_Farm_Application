package permissions.dispatcher;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.legacy.app.FragmentCompat;

public final class PermissionUtils {
    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);

    static {
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", Integer.valueOf(14));
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", Integer.valueOf(20));
        SimpleArrayMap<String, Integer> simpleArrayMap = MIN_SDK_PERMISSIONS;
        Integer valueOf = Integer.valueOf(16);
        simpleArrayMap.put("android.permission.READ_CALL_LOG", valueOf);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", valueOf);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", Integer.valueOf(9));
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", valueOf);
        SimpleArrayMap<String, Integer> simpleArrayMap2 = MIN_SDK_PERMISSIONS;
        Integer valueOf2 = Integer.valueOf(23);
        simpleArrayMap2.put("android.permission.SYSTEM_ALERT_WINDOW", valueOf2);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", valueOf2);
    }

    private PermissionUtils() {
    }

    public static boolean verifyPermissions(int... iArr) {
        if (iArr.length == 0) {
            return false;
        }
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean permissionExists(String str) {
        Integer num = (Integer) MIN_SDK_PERMISSIONS.get(str);
        return num == null || VERSION.SDK_INT >= num.intValue();
    }

    public static boolean hasSelfPermissions(Context context, String... strArr) {
        for (String str : strArr) {
            if (permissionExists(str) && !hasSelfPermission(context, str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasSelfPermission(Context context, String str) {
        if (VERSION.SDK_INT >= 23) {
            if ("Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
                return hasSelfPermissionForXiaomi(context, str);
            }
        }
        boolean z = false;
        try {
            if (PermissionChecker.checkSelfPermission(context, str) == 0) {
                z = true;
            }
        } catch (RuntimeException unused) {
        }
        return z;
    }

    private static boolean hasSelfPermissionForXiaomi(Context context, String str) {
        String permissionToOp = AppOpsManagerCompat.permissionToOp(str);
        boolean z = true;
        if (permissionToOp == null) {
            return true;
        }
        if (!(AppOpsManagerCompat.noteOp(context, permissionToOp, Process.myUid(), context.getPackageName()) == 0 && PermissionChecker.checkSelfPermission(context, str) == 0)) {
            z = false;
        }
        return z;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... strArr) {
        for (String shouldShowRequestPermissionRationale : strArr) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, shouldShowRequestPermissionRationale)) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String... strArr) {
        for (String shouldShowRequestPermissionRationale : strArr) {
            if (fragment.shouldShowRequestPermissionRationale(shouldShowRequestPermissionRationale)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean shouldShowRequestPermissionRationale(android.app.Fragment fragment, String... strArr) {
        for (String shouldShowRequestPermissionRationale : strArr) {
            if (FragmentCompat.shouldShowRequestPermissionRationale(fragment, shouldShowRequestPermissionRationale)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static void requestPermissions(android.app.Fragment fragment, String[] strArr, int i) {
        FragmentCompat.requestPermissions(fragment, strArr, i);
    }
}
