package com.google.android.exoplayer2.p041ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import androidx.core.app.NotificationCompat.BigTextStyle;
import androidx.core.app.NotificationCompat.Builder;

/* renamed from: com.google.android.exoplayer2.ui.DownloadNotificationHelper */
public final class DownloadNotificationHelper {
    private static final int NULL_STRING_ID = 0;
    private final Context context;
    private final Builder notificationBuilder;

    public DownloadNotificationHelper(Context context2, String str) {
        Context applicationContext = context2.getApplicationContext();
        this.context = applicationContext;
        this.notificationBuilder = new Builder(applicationContext, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.Notification buildProgressNotification(int r20, android.app.PendingIntent r21, java.lang.String r22, java.util.List<com.google.android.exoplayer2.offline.Download> r23) {
        /*
            r19 = this;
            r0 = 1
            r1 = 0
            r2 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 1
            r8 = 0
        L_0x000a:
            int r9 = r23.size()
            if (r2 >= r9) goto L_0x004b
            r9 = r23
            java.lang.Object r10 = r9.get(r2)
            com.google.android.exoplayer2.offline.Download r10 = (com.google.android.exoplayer2.offline.Download) r10
            int r11 = r10.state
            r12 = 5
            if (r11 != r12) goto L_0x001f
            r4 = 1
            goto L_0x0048
        L_0x001f:
            int r11 = r10.state
            r12 = 7
            if (r11 == r12) goto L_0x002a
            int r11 = r10.state
            r12 = 2
            if (r11 == r12) goto L_0x002a
            goto L_0x0048
        L_0x002a:
            float r3 = r10.getPercentDownloaded()
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r11 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r11 == 0) goto L_0x0036
            float r5 = r5 + r3
            r7 = 0
        L_0x0036:
            long r10 = r10.getBytesDownloaded()
            r12 = 0
            int r3 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r3 <= 0) goto L_0x0042
            r3 = 1
            goto L_0x0043
        L_0x0042:
            r3 = 0
        L_0x0043:
            r3 = r3 | r8
            int r6 = r6 + 1
            r8 = r3
            r3 = 1
        L_0x0048:
            int r2 = r2 + 1
            goto L_0x000a
        L_0x004b:
            if (r3 == 0) goto L_0x0051
            int r2 = com.google.android.exoplayer2.p041ui.C2489R.C2493string.exo_download_downloading
        L_0x004f:
            r13 = r2
            goto L_0x0057
        L_0x0051:
            if (r4 == 0) goto L_0x0056
            int r2 = com.google.android.exoplayer2.p041ui.C2489R.C2493string.exo_download_removing
            goto L_0x004f
        L_0x0056:
            r13 = 0
        L_0x0057:
            if (r3 == 0) goto L_0x0066
            float r2 = (float) r6
            float r5 = r5 / r2
            int r2 = (int) r5
            if (r7 == 0) goto L_0x0061
            if (r8 == 0) goto L_0x0061
            goto L_0x0062
        L_0x0061:
            r0 = 0
        L_0x0062:
            r16 = r0
            r15 = r2
            goto L_0x0069
        L_0x0066:
            r15 = 0
            r16 = 1
        L_0x0069:
            r14 = 100
            r17 = 1
            r18 = 0
            r9 = r19
            r10 = r20
            r11 = r21
            r12 = r22
            android.app.Notification r0 = r9.buildNotification(r10, r11, r12, r13, r14, r15, r16, r17, r18)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p041ui.DownloadNotificationHelper.buildProgressNotification(int, android.app.PendingIntent, java.lang.String, java.util.List):android.app.Notification");
    }

    public Notification buildDownloadCompletedNotification(int i, PendingIntent pendingIntent, String str) {
        return buildEndStateNotification(i, pendingIntent, str, C2489R.C2493string.exo_download_completed);
    }

    public Notification buildDownloadFailedNotification(int i, PendingIntent pendingIntent, String str) {
        return buildEndStateNotification(i, pendingIntent, str, C2489R.C2493string.exo_download_failed);
    }

    private Notification buildEndStateNotification(int i, PendingIntent pendingIntent, String str, int i2) {
        return buildNotification(i, pendingIntent, str, i2, 0, 0, false, false, true);
    }

    private Notification buildNotification(int i, PendingIntent pendingIntent, String str, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        CharSequence charSequence;
        this.notificationBuilder.setSmallIcon(i);
        Builder builder = this.notificationBuilder;
        BigTextStyle bigTextStyle = null;
        if (i2 == 0) {
            charSequence = null;
        } else {
            charSequence = this.context.getResources().getString(i2);
        }
        builder.setContentTitle(charSequence);
        this.notificationBuilder.setContentIntent(pendingIntent);
        Builder builder2 = this.notificationBuilder;
        if (str != null) {
            bigTextStyle = new BigTextStyle().bigText(str);
        }
        builder2.setStyle(bigTextStyle);
        this.notificationBuilder.setProgress(i3, i4, z);
        this.notificationBuilder.setOngoing(z2);
        this.notificationBuilder.setShowWhen(z3);
        return this.notificationBuilder.build();
    }
}
