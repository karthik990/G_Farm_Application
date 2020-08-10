package com.mobiroller.services;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.System;
import androidx.core.app.NotificationCompat.BigTextStyle;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationCompat.InboxStyle;
import com.anjlab.android.iab.p020v3.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.formats.UnifiedNativeAdAssetNames;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.SplashApp;
import com.mobiroller.activities.chat.ChatAdminActivity;
import com.mobiroller.activities.preview.PreviewNotificationHandler;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.ChatNotificationManagerHelper;
import com.mobiroller.helpers.EncryptionHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FCMNotificationModel;
import com.mobiroller.models.FCMNotificationModel.Action;
import com.mobiroller.models.FCMNotificationModel.Button;
import com.mobiroller.models.PreviewNotificationModel;
import com.mobiroller.util.NotificationActionUtil;
import com.mobiroller.util.NotificationUtil;
import com.mobiroller.util.WakeLocker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.LogFactory;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;
import p043io.reactivex.annotations.SchedulerSupport;
import p101me.leolin.shortcutbadger.ShortcutBadger;

public class FCMMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMMessagingService";
    private ChatNotificationManagerHelper chatNotificationManagerHelper;
    private EncryptionHelper encryptionHelper = new EncryptionHelper();
    private LocalizationHelper mLocalizationHelper;
    private InboxStyle notification;
    private SharedPrefHelper sharedPrefHelper;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        this.sharedPrefHelper = new SharedPrefHelper(MobiRollerApplication.app);
        this.mLocalizationHelper = new LocalizationHelper();
        if (DynamicConstants.MobiRoller_Stage) {
            handleDataForPreview(remoteMessage);
        } else {
            handleData(remoteMessage);
        }
    }

    private int getRandomInt() {
        return new Random().nextInt(1234557) + 10;
    }

    private Intent getInfoIntent(int i) {
        if (i == 1) {
            if (!MobiRollerApplication.isAppBackgroundChat()) {
                Intent intent = new Intent(MobiRollerApplication.app, SplashApp.class);
                intent.putExtra("chatIntentReport", ChatConstants.CHAT_ADMIN_REPORT_INTENT);
                return intent;
            }
            Intent intent2 = new Intent(MobiRollerApplication.app, ChatAdminActivity.class);
            intent2.putExtra("panel", ChatConstants.ADMIN_PANEL);
            intent2.putExtra("to", "report");
            return intent2;
        } else if (!MobiRollerApplication.isAppBackgroundChat()) {
            return new Intent(MobiRollerApplication.app, SplashApp.class);
        } else {
            return new Intent(MobiRollerApplication.app, RestoreAppActivity.class);
        }
    }

    public void sendNotification(String str, int i) {
        applyBadger();
        NotificationManager notificationManager = (NotificationManager) MobiRollerApplication.app.getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            getNotificationChannelChat();
        }
        Builder contentText = new Builder(MobiRollerApplication.app, UnifiedNativeAdAssetNames.ASSET_HEADLINE).setSmallIcon(R.drawable.icon).setContentTitle(MobiRollerApplication.app.getResources().getString(R.string.app_name)).setSound(System.DEFAULT_NOTIFICATION_URI).setStyle(new BigTextStyle().bigText(str)).setColor(Color.parseColor("#3B6064")).setAutoCancel(true).setContentText(str);
        if (!this.sharedPrefHelper.getNotificationSound()) {
            contentText.setDefaults(0);
            contentText.setSound(null);
        }
        contentText.setContentIntent(PendingIntent.getActivity(MobiRollerApplication.app, getRandomInt(), getInfoIntent(i), 1073741824));
        WakeLocker.acquire(MobiRollerApplication.app);
        notificationManager.notify(getRandomInt(), contentText.build());
        WakeLocker.release();
    }

    private void applyBadger() {
        try {
            int unreadNotificationCount = this.sharedPrefHelper.getUnreadNotificationCount() + 1;
            this.sharedPrefHelper.setUnreadNotificationCount(unreadNotificationCount);
            if (ShortcutBadger.isBadgeCounterSupported(MobiRollerApplication.app)) {
                ShortcutBadger.applyCount(MobiRollerApplication.app, unreadNotificationCount);
            }
        } catch (Exception unused) {
        }
    }

    private Uri getNotificationSound() {
        return System.DEFAULT_NOTIFICATION_URI;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.app.Notification getMyActivityNotification(java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
            r6 = this;
            com.mobiroller.helpers.EncryptionHelper r0 = r6.encryptionHelper
            java.lang.String r8 = r0.decryptMessage(r8)
            android.content.Intent r0 = new android.content.Intent
            com.mobiroller.MobiRollerApplication r1 = com.mobiroller.MobiRollerApplication.app
            java.lang.Class<com.mobiroller.activities.chat.ChatNotificationHandlerActivity> r2 = com.mobiroller.activities.chat.ChatNotificationHandlerActivity.class
            r0.<init>(r1, r2)
            com.mobiroller.models.chat.ChatNotificationModel r1 = new com.mobiroller.models.chat.ChatNotificationModel
            r1.<init>()
            r1.setFirebaseToken(r12)
            r1.setScreenId(r13)
            r1.setReceiver(r9)
            r1.setReceiverUid(r11)
            r1.setSenderUid(r10)
            java.lang.String r11 = "Chat_Notification_Model"
            r0.putExtra(r11, r1)
            r11 = 67108864(0x4000000, float:1.5046328E-36)
            r0.addFlags(r11)
            com.mobiroller.MobiRollerApplication r11 = com.mobiroller.MobiRollerApplication.app
            long r1 = java.lang.System.currentTimeMillis()
            int r12 = (int) r1
            r1 = 134217728(0x8000000, float:3.85186E-34)
            android.app.PendingIntent r11 = android.app.PendingIntent.getActivity(r11, r12, r0, r1)
            r12 = 2
            android.net.Uri r0 = android.media.RingtoneManager.getDefaultUri(r12)
            androidx.core.app.NotificationCompat$InboxStyle r1 = new androidx.core.app.NotificationCompat$InboxStyle
            r1.<init>()
            r2 = 1
            org.greenrobot.eventbus.EventBus r3 = org.greenrobot.eventbus.EventBus.getDefault()     // Catch:{ Exception -> 0x0052 }
            com.mobiroller.models.events.ChatIsReadEvent r4 = new com.mobiroller.models.events.ChatIsReadEvent     // Catch:{ Exception -> 0x0052 }
            r4.<init>(r10, r2, r13)     // Catch:{ Exception -> 0x0052 }
            r3.post(r4)     // Catch:{ Exception -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r13 = move-exception
            r13.printStackTrace()
        L_0x0056:
            r1.setBigContentTitle(r9)
            com.mobiroller.helpers.ChatNotificationManagerHelper r9 = r6.chatNotificationManagerHelper
            java.lang.String r9 = r9.getMessageViaUserUid(r10)
            java.lang.String r13 = ""
            boolean r3 = r9.equalsIgnoreCase(r13)
            r4 = 0
            if (r3 != 0) goto L_0x008c
            java.lang.String r3 = ","
            boolean r5 = r9.contains(r3)
            if (r5 == 0) goto L_0x008c
            java.lang.String[] r9 = r9.split(r3)
            r12 = 0
        L_0x0075:
            int r13 = r9.length
            if (r12 >= r13) goto L_0x0080
            r13 = r9[r12]
            r1.addLine(r13)
            int r12 = r12 + 1
            goto L_0x0075
        L_0x0080:
            r1.addLine(r8)
            com.mobiroller.helpers.ChatNotificationManagerHelper r12 = r6.chatNotificationManagerHelper
            r12.saveMessageViaUserUid(r10, r8)
            int r9 = r9.length
            int r12 = r9 + 1
            goto L_0x00a7
        L_0x008c:
            boolean r13 = r9.equalsIgnoreCase(r13)
            if (r13 != 0) goto L_0x009e
            r1.addLine(r9)
            r1.addLine(r8)
            com.mobiroller.helpers.ChatNotificationManagerHelper r9 = r6.chatNotificationManagerHelper
            r9.saveMessageViaUserUid(r10, r8)
            goto L_0x00a7
        L_0x009e:
            r1.addLine(r8)
            com.mobiroller.helpers.ChatNotificationManagerHelper r9 = r6.chatNotificationManagerHelper
            r9.saveMessageViaUserUid(r10, r8)
            r12 = 1
        L_0x00a7:
            if (r12 == r2) goto L_0x00c6
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r12)
            java.lang.String r9 = " "
            r8.append(r9)
            com.mobiroller.MobiRollerApplication r9 = com.mobiroller.MobiRollerApplication.app
            r10 = 2131821210(0x7f11029a, float:1.9275157E38)
            java.lang.String r9 = r9.getString(r10)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
        L_0x00c6:
            int r9 = android.os.Build.VERSION.SDK_INT
            r10 = 26
            if (r9 < r10) goto L_0x00cf
            r6.getNotificationChannelChat()
        L_0x00cf:
            androidx.core.app.NotificationCompat$Builder r9 = new androidx.core.app.NotificationCompat$Builder
            com.mobiroller.MobiRollerApplication r10 = com.mobiroller.MobiRollerApplication.app
            java.lang.String r13 = "3001"
            r9.<init>(r10, r13)
            r10 = 2131231138(0x7f0801a2, float:1.8078349E38)
            androidx.core.app.NotificationCompat$Builder r9 = r9.setSmallIcon(r10)
            androidx.core.app.NotificationCompat$Builder r7 = r9.setContentTitle(r7)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setContentText(r8)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setStyle(r1)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setNumber(r12)
            java.lang.String r8 = "#3B6064"
            int r8 = android.graphics.Color.parseColor(r8)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setColor(r8)
            java.lang.String r8 = "msg"
            androidx.core.app.NotificationCompat$Builder r7 = r7.setCategory(r8)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setAutoCancel(r2)
            r8 = -1
            androidx.core.app.NotificationCompat$Builder r7 = r7.setDefaults(r8)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setPriority(r2)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setSound(r0)
            long[] r8 = new long[r4]
            androidx.core.app.NotificationCompat$Builder r7 = r7.setVibrate(r8)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setContentIntent(r11)
            android.app.Notification r7 = r7.build()
            int r8 = r7.flags
            r8 = r8 | 16
            r7.flags = r8
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.services.FCMMessagingService.getMyActivityNotification(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):android.app.Notification");
    }

    private void updateNotification(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        WakeLocker.acquire(MobiRollerApplication.app);
        Notification myActivityNotification = getMyActivityNotification(str, str2, str3, str4, str5, str6, str7);
        NotificationManager notificationManager = (NotificationManager) MobiRollerApplication.app.getSystemService("notification");
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str7);
        notificationManager.notify(sb.toString().hashCode(), myActivityNotification);
        WakeLocker.release();
    }

    private void getNotificationChannel(FCMNotificationModel fCMNotificationModel) {
        String str;
        String str2;
        NotificationManager notificationManager = (NotificationManager) MobiRollerApplication.app.getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            String string = MobiRollerApplication.app.getString(R.string.notification_announcements);
            String str3 = "";
            if (fCMNotificationModel.getCategory() == null || fCMNotificationModel.getCategory().equals(str3)) {
                str = string;
                str2 = "3000";
            } else {
                str2 = fCMNotificationModel.getCategory();
                str = fCMNotificationModel.getCategory();
            }
            int i = 3;
            if (!fCMNotificationModel.getPriority().equals(str3)) {
                i = Integer.parseInt(fCMNotificationModel.getPriority());
            }
            NotificationChannel notificationChannel = new NotificationChannel(str2, str, i);
            notificationChannel.setDescription(str3);
            notificationChannel.enableLights(true);
            notificationChannel.setSound(getNotificationSound(), new AudioAttributes.Builder().setContentType(4).setUsage(5).build());
            notificationChannel.setLightColor(this.sharedPrefHelper.getActionBarColor());
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void getNotificationChannelChat() {
        NotificationManager notificationManager = (NotificationManager) MobiRollerApplication.app.getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(UnifiedNativeAdAssetNames.ASSET_HEADLINE, MobiRollerApplication.app.getString(R.string.notification_chats), 3);
            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(this.sharedPrefHelper.getActionBarColor());
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public String getRoomType(String str, String str2) {
        int hashCode = str.hashCode();
        int hashCode2 = str2.hashCode();
        String str3 = EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR;
        if (hashCode > hashCode2) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str3);
            sb.append(str2);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str3);
        sb2.append(str);
        return sb2.toString();
    }

    private void handleData(RemoteMessage remoteMessage) {
        String str = "title";
        String str2 = "receiver_uid";
        if (remoteMessage.getData().containsKey(str)) {
            String str3 = "fcm_token";
            if (remoteMessage.getData().containsKey(str3)) {
                String str4 = "screen_id";
                if (remoteMessage.getData().containsKey(str4)) {
                    String str5 = (String) remoteMessage.getData().get(str);
                    String str6 = (String) remoteMessage.getData().get("text");
                    String str7 = (String) remoteMessage.getData().get("username");
                    String str8 = (String) remoteMessage.getData().get(ChatConstants.ARG_USER_INFO_UID);
                    String str9 = (String) remoteMessage.getData().get(str2);
                    String str10 = (String) remoteMessage.getData().get(str3);
                    String str11 = (String) remoteMessage.getData().get(str4);
                    this.chatNotificationManagerHelper = new ChatNotificationManagerHelper(MobiRollerApplication.app, str11);
                    if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(str9)) {
                        applyBadger();
                        if (MobiRollerApplication.openChatId == null) {
                            updateNotification(str5, str6, str7, str8, str9, str10, str11);
                            return;
                        } else if (MobiRollerApplication.openChatId.equalsIgnoreCase(getRoomType(str8, str9))) {
                            this.chatNotificationManagerHelper.getMessageViaUserUid(str8);
                            return;
                        } else {
                            updateNotification(str5, str6, str7, str8, str9, str10, str11);
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
        String str12 = "notification_type";
        if (remoteMessage.getData().containsKey(str12) && String.valueOf(remoteMessage.getData().containsKey(str12)).equals(ChatConstants.ARG_USER_INFO)) {
            String str13 = (String) remoteMessage.getData().get(str2);
            String str14 = "info_type";
            int parseInt = remoteMessage.getData().containsKey(str14) ? Integer.parseInt((String) remoteMessage.getData().get(str14)) : 0;
            if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(str13)) {
                if (parseInt != 0) {
                    sendNotification(MobiRollerApplication.app.getString(ChatConstants.getPushMessage(parseInt)), parseInt);
                } else {
                    sendNotification((String) remoteMessage.getData().get(str), 0);
                }
            }
        } else if (remoteMessage.getData().containsKey("alert") || remoteMessage.getData().containsKey(TtmlNode.TAG_BODY)) {
            handleFCMNotificationData(remoteMessage);
        }
    }

    private void handleDataForPreview(RemoteMessage remoteMessage) {
        PreviewNotificationModel previewNotificationModel = new PreviewNotificationModel();
        String str = "title";
        if (remoteMessage.getData().containsKey(str)) {
            Map data = remoteMessage.getData();
            String str2 = Constants.RESPONSE_DESCRIPTION;
            if (data.containsKey(str2)) {
                previewNotificationModel.title = this.mLocalizationHelper.getLocalizedTitlePreview((String) remoteMessage.getData().get(str));
                previewNotificationModel.description = this.mLocalizationHelper.getLocalizedTitlePreview((String) remoteMessage.getData().get(str2));
                String str3 = "web_url";
                if (remoteMessage.getData().containsKey(str3)) {
                    previewNotificationModel.webUrl = this.mLocalizationHelper.getLocalizedTitlePreview((String) remoteMessage.getData().get(str3));
                }
                String str4 = "image_url";
                if (remoteMessage.getData().containsKey(str4)) {
                    previewNotificationModel.imageUrl = this.mLocalizationHelper.getLocalizedTitlePreview((String) remoteMessage.getData().get(str4));
                }
                String str5 = "external";
                if (remoteMessage.getData().containsKey(str5) && ((String) remoteMessage.getData().get(str5)).equalsIgnoreCase("true")) {
                    previewNotificationModel.external = true;
                }
                remoteMessage.getData().containsKey(str5);
                sendNotificationForPreview(previewNotificationModel);
            }
        }
    }

    private void sendNotificationForPreview(final PreviewNotificationModel previewNotificationModel) {
        Intent intent = new Intent(MobiRollerApplication.app, PreviewNotificationHandler.class);
        if (previewNotificationModel.webUrl != null) {
            intent.putExtra(com.mobiroller.constants.Constants.MobiRoller_Preview_Notification_Model, previewNotificationModel);
        }
        intent.addFlags(67108864);
        final Notification.Builder contentIntent = new Notification.Builder(MobiRollerApplication.app).setSmallIcon(R.drawable.icon).setContentTitle(previewNotificationModel.title).setContentText(previewNotificationModel.description).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setContentIntent(PendingIntent.getActivity(MobiRollerApplication.app, getRandomInt(), intent, 1073741824));
        if (previewNotificationModel.imageUrl != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Glide.with(MobiRollerApplication.app.getApplicationContext()).asBitmap().load(previewNotificationModel.imageUrl).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            WakeLocker.acquire(MobiRollerApplication.app.getApplicationContext());
                            contentIntent.setLargeIcon(bitmap).setStyle(new BigPictureStyle().bigPicture(bitmap));
                            ((NotificationManager) MobiRollerApplication.app.getSystemService("notification")).notify(FCMMessagingService.this.createID(), contentIntent.build());
                            WakeLocker.release();
                        }
                    });
                }
            });
            return;
        }
        WakeLocker.acquire(MobiRollerApplication.app.getApplicationContext());
        ((NotificationManager) MobiRollerApplication.app.getSystemService("notification")).notify(createID(), contentIntent.build());
        WakeLocker.release();
    }

    public int createID() {
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(new Date()));
    }

    private void handleFCMNotificationData(RemoteMessage remoteMessage) {
        FCMNotificationModel fCMNotificationModel = new FCMNotificationModel();
        Map data = remoteMessage.getData();
        String str = TtmlNode.ATTR_ID;
        if (data.containsKey(str)) {
            fCMNotificationModel.setId((String) data.get(str));
        }
        String str2 = "title";
        if (data.containsKey(str2)) {
            fCMNotificationModel.setTitle((String) data.get(str2));
        }
        String str3 = "action";
        if (data.containsKey(str3)) {
            fCMNotificationModel.setAction((Action) new Gson().fromJson((String) data.get(str3), Action.class));
        }
        String str4 = TtmlNode.TAG_BODY;
        if (data.containsKey(str4)) {
            fCMNotificationModel.setBody((String) data.get(str4));
        }
        String str5 = "alert";
        if (data.containsKey(str5)) {
            fCMNotificationModel.setAlert((String) data.get(str5));
        }
        String str6 = "accountScreenID";
        if (data.containsKey(str6)) {
            fCMNotificationModel.setAccountScreenID((String) data.get(str6));
        }
        String str7 = "bigPictureUrl";
        if (data.containsKey(str7)) {
            fCMNotificationModel.setBigPictureUrl((String) data.get(str7));
        }
        String str8 = "smallIcon";
        if (data.containsKey(str8)) {
            fCMNotificationModel.setSmallIcon((String) data.get(str8));
        }
        String str9 = "largeIcon";
        if (data.containsKey(str9)) {
            fCMNotificationModel.setLargeIcon((String) data.get(str9));
        }
        String str10 = "category";
        if (data.containsKey(str10)) {
            fCMNotificationModel.setCategory((String) data.get(str10));
        }
        String str11 = com.mobiroller.constants.Constants.KEY_SCREEN_TYPE;
        if (data.containsKey(str11)) {
            fCMNotificationModel.setScreenType((String) data.get(str11));
        }
        String str12 = TtmlNode.ATTR_TTS_COLOR;
        if (data.containsKey(str12)) {
            fCMNotificationModel.setColor((String) data.get(str12));
        }
        String str13 = LogFactory.PRIORITY_KEY;
        if (data.containsKey(str13)) {
            fCMNotificationModel.setPriority((String) data.get(str13));
        }
        String str14 = "saveNotification";
        if (data.containsKey(str14) && ((String) data.get(str14)).equalsIgnoreCase("false")) {
            fCMNotificationModel.saveNotification = false;
        }
        String str15 = "buttons";
        if (data.containsKey(str15)) {
            fCMNotificationModel.setButtons((List) new Gson().fromJson((String) data.get(str15), new TypeToken<List<Button>>() {
            }.getType()));
        }
        sendNotification(fCMNotificationModel);
    }

    public void sendNotification(FCMNotificationModel fCMNotificationModel) {
        Notification.Builder builder;
        applyBadger();
        if (VERSION.SDK_INT >= 26) {
            getNotificationChannel(fCMNotificationModel);
        }
        String str = "";
        if (VERSION.SDK_INT < 26) {
            builder = new Notification.Builder(MobiRollerApplication.app);
        } else if (!fCMNotificationModel.getCategory().equals(str)) {
            builder = new Notification.Builder(MobiRollerApplication.app, fCMNotificationModel.getCategory());
        } else {
            builder = new Notification.Builder(MobiRollerApplication.app, "3000");
        }
        builder.setSmallIcon(R.drawable.icon).setContentTitle(fCMNotificationModel.getTitle(MobiRollerApplication.app)).setSound(getNotificationSound()).setStyle(new Notification.BigTextStyle().bigText(fCMNotificationModel.getBody())).setAutoCancel(true).setContentText(fCMNotificationModel.getBody());
        if (NotificationActionUtil.isValidAction(MobiRollerApplication.app, fCMNotificationModel.getAction())) {
            builder.setContentIntent(getPendingIntent(fCMNotificationModel.getAction(), fCMNotificationModel.getTitle(MobiRollerApplication.app), fCMNotificationModel.getBody(), fCMNotificationModel.getId()));
            NotificationUtil.addNotification(fCMNotificationModel, (Context) MobiRollerApplication.app);
        } else if (fCMNotificationModel.getAccountScreenID().equals(str) || fCMNotificationModel.getScreenType().equals(str)) {
            builder.setContentIntent(getPendingIntent(null, fCMNotificationModel.getTitle(MobiRollerApplication.app), fCMNotificationModel.getBody(), fCMNotificationModel.getId()));
            NotificationUtil.addNotification(fCMNotificationModel, (Context) MobiRollerApplication.app);
        } else {
            builder.setContentIntent(getPendingIntent(new Action(fCMNotificationModel.getAccountScreenID(), com.mobiroller.constants.Constants.NOTIFICATION_TYPE_CONTENT, fCMNotificationModel.getScreenType()), fCMNotificationModel.getTitle(MobiRollerApplication.app), fCMNotificationModel.getBody(), fCMNotificationModel.getId()));
            NotificationUtil.addNotification(fCMNotificationModel, (Context) MobiRollerApplication.app);
        }
        if (fCMNotificationModel.getPriority().equals(str) || VERSION.SDK_INT >= 26) {
            builder.setPriority(0);
        } else {
            try {
                builder.setPriority(Integer.parseInt(fCMNotificationModel.getPriority()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (VERSION.SDK_INT >= 21 && !fCMNotificationModel.getColor().equals(str)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("#");
                sb.append(fCMNotificationModel.getColor());
                builder.setColor(Color.parseColor(sb.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (VERSION.SDK_INT >= 26) {
            builder.setColorized(true);
        }
        if (fCMNotificationModel.getButtons().size() != 0 && VERSION.SDK_INT >= 20) {
            for (int i = 0; i < fCMNotificationModel.getButtons().size(); i++) {
                if (NotificationActionUtil.isValidAction(MobiRollerApplication.app, ((Button) fCMNotificationModel.getButtons().get(i)).getAction())) {
                    builder.addAction(new Notification.Action.Builder(0, ((Button) fCMNotificationModel.getButtons().get(i)).getText(), getPendingIntent(((Button) fCMNotificationModel.getButtons().get(i)).getAction(), fCMNotificationModel.getTitle(MobiRollerApplication.app), fCMNotificationModel.getBody(), fCMNotificationModel.getId())).build());
                }
            }
        }
        if (!this.sharedPrefHelper.getNotificationSound()) {
            builder.setDefaults(0);
            builder.setSound(null);
        }
        if (VERSION.SDK_INT >= 21 && !fCMNotificationModel.getCategory().equals(str)) {
            builder.setCategory(fCMNotificationModel.getCategory());
        }
        if (!fCMNotificationModel.getBigPictureUrl().equals(str)) {
            try {
                builder.setStyle(new BigPictureStyle().bigPicture((Bitmap) Glide.with((Context) MobiRollerApplication.app).asBitmap().load(fCMNotificationModel.getBigPictureUrl()).submit().get()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (!fCMNotificationModel.getLargeIcon().equals(str)) {
            try {
                builder.setLargeIcon((Bitmap) Glide.with((Context) MobiRollerApplication.app).asBitmap().load(fCMNotificationModel.getLargeIcon()).submit().get());
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        if (!fCMNotificationModel.getSmallIcon().equals(str) && VERSION.SDK_INT >= 23) {
            try {
                builder.setSmallIcon(Icon.createWithBitmap((Bitmap) Glide.with((Context) MobiRollerApplication.app).asBitmap().load(fCMNotificationModel.getSmallIcon()).submit().get()));
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        WakeLocker.acquire(MobiRollerApplication.app);
        ((NotificationManager) MobiRollerApplication.app.getSystemService("notification")).notify(fCMNotificationModel.getId().hashCode(), builder.build());
        WakeLocker.release();
    }

    private PendingIntent getPendingIntent(Action action, String str, String str2, String str3) {
        Intent intent = new Intent(MobiRollerApplication.app, FCMNotificationHandler.class);
        if (action == null) {
            action = new Action();
            action.setType(SchedulerSupport.NONE);
        }
        intent.putExtra("action", action);
        intent.putExtra("title", str);
        intent.putExtra(com.mobiroller.constants.Constants.NOTIFICATION_CONTENT, str2);
        intent.putExtra("notificationId", str3);
        intent.setFlags(536870912);
        return PendingIntent.getActivity(MobiRollerApplication.app, getRandomInt(), intent, 134217728);
    }
}
