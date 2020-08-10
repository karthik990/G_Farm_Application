package com.google.android.youtube.player;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.google.android.youtube.player.internal.C2774ab;
import com.google.android.youtube.player.internal.C2837y;

public enum YouTubeInitializationResult {
    SUCCESS,
    INTERNAL_ERROR,
    UNKNOWN_ERROR,
    SERVICE_MISSING,
    SERVICE_VERSION_UPDATE_REQUIRED,
    SERVICE_DISABLED,
    SERVICE_INVALID,
    ERROR_CONNECTING_TO_SERVICE,
    CLIENT_LIBRARY_UPDATE_REQUIRED,
    NETWORK_ERROR,
    DEVELOPER_KEY_INVALID,
    INVALID_APPLICATION_SIGNATURE;

    /* renamed from: com.google.android.youtube.player.YouTubeInitializationResult$1 */
    static /* synthetic */ class C27631 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1617a = null;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.google.android.youtube.player.YouTubeInitializationResult[] r0 = com.google.android.youtube.player.YouTubeInitializationResult.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1617a = r0
                int[] r0 = f1617a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.android.youtube.player.YouTubeInitializationResult r1 = com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_MISSING     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1617a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.android.youtube.player.YouTubeInitializationResult r1 = com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_DISABLED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f1617a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.android.youtube.player.YouTubeInitializationResult r1 = com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.YouTubeInitializationResult.C27631.<clinit>():void");
        }
    }

    /* renamed from: com.google.android.youtube.player.YouTubeInitializationResult$a */
    private static final class C2764a implements OnClickListener {

        /* renamed from: a */
        private final Activity f1618a;

        /* renamed from: b */
        private final Intent f1619b;

        /* renamed from: c */
        private final int f1620c;

        public C2764a(Activity activity, Intent intent, int i) {
            this.f1618a = (Activity) C2774ab.m1495a(activity);
            this.f1619b = (Intent) C2774ab.m1495a(intent);
            this.f1620c = ((Integer) C2774ab.m1495a(Integer.valueOf(i))).intValue();
        }

        public final void onClick(DialogInterface dialogInterface, int i) {
            try {
                this.f1618a.startActivityForResult(this.f1619b, this.f1620c);
                dialogInterface.dismiss();
            } catch (ActivityNotFoundException e) {
                C2837y.m1746a("Can't perform resolution for YouTubeInitalizationError", (Throwable) e);
            }
        }
    }

    public final Dialog getErrorDialog(Activity activity, int i) {
        return getErrorDialog(activity, i, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.app.Dialog getErrorDialog(android.app.Activity r6, int r7, android.content.DialogInterface.OnCancelListener r8) {
        /*
            r5 = this;
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            r0.<init>(r6)
            if (r8 == 0) goto L_0x000a
            r0.setOnCancelListener(r8)
        L_0x000a:
            int[] r8 = com.google.android.youtube.player.YouTubeInitializationResult.C27631.f1617a
            int r1 = r5.ordinal()
            r8 = r8[r1]
            r1 = 3
            r2 = 2
            r3 = 1
            if (r8 == r3) goto L_0x0026
            if (r8 == r2) goto L_0x001d
            if (r8 == r1) goto L_0x0026
            r8 = 0
            goto L_0x002e
        L_0x001d:
            java.lang.String r8 = com.google.android.youtube.player.internal.C2838z.m1750a(r6)
            android.content.Intent r8 = com.google.android.youtube.player.internal.C2838z.m1748a(r8)
            goto L_0x002e
        L_0x0026:
            java.lang.String r8 = com.google.android.youtube.player.internal.C2838z.m1750a(r6)
            android.content.Intent r8 = com.google.android.youtube.player.internal.C2838z.m1754b(r8)
        L_0x002e:
            com.google.android.youtube.player.YouTubeInitializationResult$a r4 = new com.google.android.youtube.player.YouTubeInitializationResult$a
            r4.<init>(r6, r8, r7)
            com.google.android.youtube.player.internal.m r7 = new com.google.android.youtube.player.internal.m
            r7.<init>(r6)
            int[] r6 = com.google.android.youtube.player.YouTubeInitializationResult.C27631.f1617a
            int r8 = r5.ordinal()
            r6 = r6[r8]
            if (r6 == r3) goto L_0x008d
            if (r6 == r2) goto L_0x007e
            if (r6 == r1) goto L_0x0067
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Unexpected errorReason: "
            java.lang.String r8 = r5.name()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            int r0 = r8.length()
            if (r0 == 0) goto L_0x005d
            java.lang.String r7 = r7.concat(r8)
            goto L_0x0063
        L_0x005d:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r7)
            r7 = r8
        L_0x0063:
            r6.<init>(r7)
            throw r6
        L_0x0067:
            java.lang.String r6 = r7.f1680h
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f1681i
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.f1682j
        L_0x0075:
            android.app.AlertDialog$Builder r6 = r6.setPositiveButton(r7, r4)
            android.app.AlertDialog r6 = r6.create()
            return r6
        L_0x007e:
            java.lang.String r6 = r7.f1677e
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f1678f
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.f1679g
            goto L_0x0075
        L_0x008d:
            java.lang.String r6 = r7.f1674b
            android.app.AlertDialog$Builder r6 = r0.setTitle(r6)
            java.lang.String r8 = r7.f1675c
            android.app.AlertDialog$Builder r6 = r6.setMessage(r8)
            java.lang.String r7 = r7.f1676d
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.YouTubeInitializationResult.getErrorDialog(android.app.Activity, int, android.content.DialogInterface$OnCancelListener):android.app.Dialog");
    }

    public final boolean isUserRecoverableError() {
        int i = C27631.f1617a[ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
