package com.mobiroller.jcplayer;

import android.content.BroadcastReceiver;
import com.mobiroller.models.events.StartMedia;
import com.mobiroller.models.events.StopMedia;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class JcPlayerNotificationReceiver extends BroadcastReceiver {
    private boolean isStopByAds = false;

    public JcPlayerNotificationReceiver() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:34|35|36|37|64) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:40|41|42|43|66) */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0089, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008a, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0096, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0097, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x0085 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x0092 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r8, android.content.Intent r9) {
        /*
            r7 = this;
            com.mobiroller.jcplayer.JcAudioPlayer r0 = com.mobiroller.jcplayer.JcAudioPlayer.getInstance()
            java.lang.String r1 = "ACTION"
            boolean r2 = r9.hasExtra(r1)
            if (r2 == 0) goto L_0x0011
            java.lang.String r9 = r9.getStringExtra(r1)
            goto L_0x0013
        L_0x0011:
            java.lang.String r9 = ""
        L_0x0013:
            r1 = -1
            int r2 = r9.hashCode()
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r2) {
                case -491148553: goto L_0x0048;
                case 2392819: goto L_0x003e;
                case 2458420: goto L_0x0034;
                case 75902422: goto L_0x002a;
                case 2012838315: goto L_0x0020;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x0051
        L_0x0020:
            java.lang.String r2 = "DELETE"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0051
            r1 = 4
            goto L_0x0051
        L_0x002a:
            java.lang.String r2 = "PAUSE"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0051
            r1 = 1
            goto L_0x0051
        L_0x0034:
            java.lang.String r2 = "PLAY"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0051
            r1 = 0
            goto L_0x0051
        L_0x003e:
            java.lang.String r2 = "NEXT"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0051
            r1 = 2
            goto L_0x0051
        L_0x0048:
            java.lang.String r2 = "PREVIOUS"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0051
            r1 = 3
        L_0x0051:
            if (r1 == 0) goto L_0x00a9
            if (r1 == r6) goto L_0x009b
            if (r1 == r5) goto L_0x008e
            if (r1 == r4) goto L_0x0081
            if (r1 == r3) goto L_0x005c
            goto L_0x00b4
        L_0x005c:
            if (r0 == 0) goto L_0x0067
            boolean r9 = r0.isPlaying()     // Catch:{ Exception -> 0x007c }
            if (r9 == 0) goto L_0x0067
            r0.stopAudio()     // Catch:{ Exception -> 0x007c }
        L_0x0067:
            android.content.Intent r9 = new android.content.Intent     // Catch:{ Exception -> 0x007c }
            java.lang.Class<com.mobiroller.jcplayer.JcPlayerService> r0 = com.mobiroller.jcplayer.JcPlayerService.class
            r9.<init>(r8, r0)     // Catch:{ Exception -> 0x007c }
            r8.stopService(r9)     // Catch:{ Exception -> 0x007c }
            android.content.Intent r9 = new android.content.Intent     // Catch:{ Exception -> 0x007c }
            java.lang.Class<com.mobiroller.jcplayer.JcNotificationPlayerService> r0 = com.mobiroller.jcplayer.JcNotificationPlayerService.class
            r9.<init>(r8, r0)     // Catch:{ Exception -> 0x007c }
            r8.stopService(r9)     // Catch:{ Exception -> 0x007c }
            goto L_0x00b4
        L_0x007c:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x00b4
        L_0x0081:
            r0.previousAudio()     // Catch:{ Exception -> 0x0085 }
            goto L_0x00b4
        L_0x0085:
            r0.continueAudio()     // Catch:{ AudioListNullPointerException -> 0x0089 }
            goto L_0x00b4
        L_0x0089:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x00b4
        L_0x008e:
            r0.nextAudio()     // Catch:{ AudioListNullPointerException -> 0x0092 }
            goto L_0x00b4
        L_0x0092:
            r0.continueAudio()     // Catch:{ AudioListNullPointerException -> 0x0096 }
            goto L_0x00b4
        L_0x0096:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x00b4
        L_0x009b:
            if (r0 == 0) goto L_0x00b4
            r0.pauseAudio()     // Catch:{ Exception -> 0x00a4 }
            r0.updateNotification()     // Catch:{ Exception -> 0x00a4 }
            goto L_0x00b4
        L_0x00a4:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x00b4
        L_0x00a9:
            r0.continueAudio()     // Catch:{ Exception -> 0x00b0 }
            r0.updateNotification()     // Catch:{ Exception -> 0x00b0 }
            goto L_0x00b4
        L_0x00b0:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.jcplayer.JcPlayerNotificationReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    @Subscribe
    public void onPostStopMedia(StopMedia stopMedia) {
        JcAudioPlayer instance = JcAudioPlayer.getInstance();
        if (instance != null) {
            try {
                this.isStopByAds = true;
                instance.pauseAudio();
                instance.updateNotification();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onPostStartMedia(StartMedia startMedia) {
        if (this.isStopByAds) {
            JcAudioPlayer instance = JcAudioPlayer.getInstance();
            try {
                instance.continueAudio();
                instance.updateNotification();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.isStopByAds = false;
        }
    }
}
