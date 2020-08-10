package com.applyze;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ApplyzeAnalyticsService extends Service {
    private static final String TAG = "ApplyzeAnalyticsSS";
    private ScreenLockListener mReceiver;
    private List<Session> sessionList;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.ANSWER");
        this.mReceiver = new ScreenLockListener();
        registerReceiver(this.mReceiver, intentFilter);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        ServiceUtil.sendSessions(this);
        this.sessionList = getSessionList();
        return 1;
    }

    /* access modifiers changed from: protected */
    public List<Session> getSessionList() {
        return ServiceUtil.getSessionList(this);
    }

    /* access modifiers changed from: 0000 */
    public void saveSessionList() {
        ServiceUtil.setSessionList(this, this.sessionList);
    }

    /* access modifiers changed from: 0000 */
    public Session getCurrentSession() {
        List<Session> list = this.sessionList;
        if (list == null || list.size() == 0) {
            return null;
        }
        List<Session> list2 = this.sessionList;
        return (Session) list2.get(list2.size() - 1);
    }

    private void initSession(String str, String str2, boolean z) {
        this.sessionList = getSessionList();
        if (getCurrentSession() == null || getCurrentSession().getTotalDuration() != 0) {
            Session session = new Session(this);
            ServiceUtil.resetHealthCheckConditions(this);
            ServiceUtil.runHealthCheck(this);
            session.setApiKey(str);
            session.setAppKey(str2);
            session.setOnGoing(z);
            this.sessionList.add(session);
            saveSessionList();
            ServiceUtil.checkLocationPermissionGranted(this);
        }
    }

    private void setSessionToken(String str) {
        getCurrentSession().setToken(str);
        saveSessionList();
    }

    private void addScreenDisplay(ScreenDisplay screenDisplay) {
        getCurrentSession().getScreenDisplays().add(screenDisplay);
        saveSessionList();
    }

    /* access modifiers changed from: 0000 */
    public void finishSession() {
        this.sessionList = getSessionList();
        if (this.sessionList.size() != 0) {
            if (getCurrentSession().getTotalDuration() == 0) {
                getCurrentSession().setEndDate(ServiceUtil.getDate());
                getCurrentSession().setTotalDuration(ServiceUtil.validateSessionDuration(getCurrentSession(), this).getTotalDuration());
            }
            saveSessionList();
        }
    }

    private void sendSessions() {
        ServiceUtil.sendSessions(this);
    }

    public void onTaskRemoved(Intent intent) {
        finishSession();
        sendSessions();
    }

    private void setUserLocationInfo(double d, double d2) {
        getCurrentSession().setLatitude(d);
        getCurrentSession().setLongitude(d2);
        saveSessionList();
    }

    private void setCountryAndCity(String str, String str2) {
        getCurrentSession().setCountry(str);
        getCurrentSession().setCity(str2);
        saveSessionList();
    }

    private void setMetaData(IpApiModel ipApiModel) {
        getCurrentSession().setMetaData(ipApiModel);
        saveSessionList();
    }

    private void addCustomField(String str, String str2) {
        getCurrentSession().addCustomField(str, str2);
        saveSessionList();
    }

    public void onDestroy() {
        finishSession();
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    @Subscribe
    public void onPostSessionStart(SessionStart sessionStart) {
        initSession(sessionStart.apiKey, sessionStart.appKey, sessionStart.onGoing);
    }

    @Subscribe
    public void onPostCustomField(CustomField customField) {
        addCustomField(customField.getKey(), customField.getValue());
    }

    @Subscribe
    public void onPostIpApiModel(IpApiModel ipApiModel) {
        if (getCurrentSession() != null) {
            setMetaData(ipApiModel);
            setCountryAndCity(ipApiModel.getCountry(), ipApiModel.getCity());
        }
    }

    @Subscribe
    public void onPostUserLocationEvent(UserLocationEvent userLocationEvent) {
        if (getCurrentSession() != null) {
            setUserLocationInfo(userLocationEvent.latitude, userLocationEvent.longitude);
        }
    }

    @Subscribe
    public void onPostTokenEvent(TokenEvent tokenEvent) {
        setSessionToken(tokenEvent.token);
    }

    @Subscribe
    public void onPostScreenDisplay(ScreenDisplay screenDisplay) {
        addScreenDisplay(screenDisplay);
    }

    @org.greenrobot.eventbus.Subscribe
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPostCustomUserEvent(com.applyze.CustomUserEvent r8) {
        /*
            r7 = this;
            java.lang.String r0 = r8.key
            int r1 = r0.hashCode()
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r1) {
                case -1249512767: goto L_0x0042;
                case -1209402980: goto L_0x0038;
                case -1192969641: goto L_0x002e;
                case -266666762: goto L_0x0023;
                case 96619420: goto L_0x0019;
                case 1330852282: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x004c
        L_0x000f:
            java.lang.String r1 = "fullName"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 4
            goto L_0x004d
        L_0x0019:
            java.lang.String r1 = "email"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 5
            goto L_0x004d
        L_0x0023:
            java.lang.String r1 = "userName"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 0
            goto L_0x004d
        L_0x002e:
            java.lang.String r1 = "phoneNumber"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 3
            goto L_0x004d
        L_0x0038:
            java.lang.String r1 = "birthYear"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 1
            goto L_0x004d
        L_0x0042:
            java.lang.String r1 = "gender"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 2
            goto L_0x004d
        L_0x004c:
            r0 = -1
        L_0x004d:
            if (r0 == 0) goto L_0x008c
            if (r0 == r6) goto L_0x0082
            if (r0 == r5) goto L_0x0078
            if (r0 == r4) goto L_0x006e
            if (r0 == r3) goto L_0x0064
            if (r0 == r2) goto L_0x005a
            goto L_0x0095
        L_0x005a:
            com.applyze.Session r0 = r7.getCurrentSession()
            java.lang.String r8 = r8.value
            r0.setEmail(r8)
            goto L_0x0095
        L_0x0064:
            com.applyze.Session r0 = r7.getCurrentSession()
            java.lang.String r8 = r8.value
            r0.setFullName(r8)
            goto L_0x0095
        L_0x006e:
            com.applyze.Session r0 = r7.getCurrentSession()
            java.lang.String r8 = r8.value
            r0.setPhone(r8)
            goto L_0x0095
        L_0x0078:
            com.applyze.Session r0 = r7.getCurrentSession()
            java.lang.String r8 = r8.value
            r0.setGender(r8)
            goto L_0x0095
        L_0x0082:
            com.applyze.Session r0 = r7.getCurrentSession()
            java.lang.String r8 = r8.value
            r0.setBirthyear(r8)
            goto L_0x0095
        L_0x008c:
            com.applyze.Session r0 = r7.getCurrentSession()
            java.lang.String r8 = r8.value
            r0.setUserName(r8)
        L_0x0095:
            r7.saveSessionList()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.applyze.ApplyzeAnalyticsService.onPostCustomUserEvent(com.applyze.CustomUserEvent):void");
    }

    @Subscribe
    public void onPostEndSessionEvent(EndSessionEvent endSessionEvent) {
        finishSession();
        sendSessions();
    }
}
