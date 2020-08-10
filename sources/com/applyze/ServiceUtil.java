package com.applyze;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.braintreepayments.api.models.BinData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.constants.ChatConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ServiceUtil {
    static final String API_KEY = "AnalyticsAPIKey";
    static final String APP_KEY = "AnalyticsAPPKey";
    static final String BASE_LOG_SERVER_URL = "78.47.153.151";
    static final String BASE_SERVER_URL = "https://api.applyze.com";
    private static final String HEALTH_CHECK_COUNT_LOCATION = "healthCount";
    private static final int HEALTH_CHECK_FREQUENCY = 5;
    private static final int NETWORK_CLASS_2_G = 1;
    private static final int NETWORK_CLASS_3_G = 2;
    private static final int NETWORK_CLASS_4_G = 3;
    private static final int NETWORK_CLASS_UNKNOWN = 0;
    private static final String SESSION_LIST_LOCATION = "sessionListV1";
    private static Gson gson = new Gson();
    private static Type listType = new TypeToken<ArrayList<Session>>() {
    }.getType();
    private static FusedLocationProviderClient mFusedLocationClient;
    /* access modifiers changed from: private */
    public static ScheduledExecutorService scheduler;

    ServiceUtil() {
    }

    static boolean isAnalyticsServiceRunning(Context context) {
        for (RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (ApplyzeAnalyticsService.class.getName().equals(runningServiceInfo.service.getClassName()) && runningServiceInfo.process.equalsIgnoreCase(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    static String getDensityName(Context context) {
        double d = (double) context.getResources().getDisplayMetrics().density;
        if (d >= 4.0d) {
            return "xxxhdpi";
        }
        if (d >= 3.0d) {
            return "xxhdpi";
        }
        if (d >= 2.0d) {
            return "xhdpi";
        }
        if (d >= 1.5d) {
            return "hdpi";
        }
        return d >= 1.0d ? "mdpi" : "ldpi";
    }

    static String getDeviceResolution(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        StringBuilder sb = new StringBuilder();
        sb.append(i2);
        sb.append("x");
        sb.append(i);
        return sb.toString();
    }

    static List<Session> getSessionList(Context context) {
        String str = SESSION_LIST_LOCATION;
        try {
            if (getSharedPref(context).contains(str)) {
                return (List) gson.fromJson(getSharedPref(context).getString(str, ""), listType);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getSessionList ");
            sb.append(e.getMessage());
            AnalyticsLogger.m88e(sb.toString());
            getSharedPref(context).edit().remove("sessionList").apply();
            e.printStackTrace();
        }
        return new ArrayList();
    }

    static void setSessionList(Context context, List<Session> list) {
        String str = SESSION_LIST_LOCATION;
        try {
            Editor edit = getSharedPref(context).edit();
            if (list == null || list.size() == 0) {
                edit.remove(str).commit();
            } else {
                edit.putString(str, gson.toJson((Object) list)).apply();
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("setSessionList ");
            sb.append(e.getMessage());
            AnalyticsLogger.m88e(sb.toString());
            getSharedPref(context).edit().remove(str).apply();
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static int getHealthCheckCount(Context context) {
        SharedPreferences sharedPref = getSharedPref(context);
        String str = HEALTH_CHECK_COUNT_LOCATION;
        if (sharedPref.contains(str)) {
            return getSharedPref(context).getInt(str, 0);
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public static void setHealthCheckCount(Context context, int i) {
        getSharedPref(context).edit().putInt(HEALTH_CHECK_COUNT_LOCATION, i).apply();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("ApplyzeAnalytics", 0);
    }

    private static boolean validateSession(Session session) {
        return (session == null || session.getDate() == null || session.getApiKey() == null || session.getAppKey() == null || session.getUdid() == null || session.getTotalDuration() == 0 || session.getOsType() == null) ? false : true;
    }

    static Session validateSessionDuration(Session session, Context context) {
        int healthCheckCount = getHealthCheckCount(context);
        if ((healthCheckCount + 1) * 5 >= session.getTotalDuration() && session.getTotalDuration() >= (healthCheckCount - 1) * 5) {
            return session;
        }
        if (isOnline(context)) {
            StringBuilder sb = new StringBuilder();
            sb.append("ValidateSessionDuration was incorrect : ");
            sb.append(session.getTotalDuration());
            sb.append(" modified to :");
            sb.append(healthCheckCount * 5);
            AnalyticsLogger.m89w(sb.toString());
        }
        session.setEndDate(getDate());
        session.setTotalDuration(healthCheckCount * 5);
        return session;
    }

    static void sendSessionsFromListener(Context context) {
        List sessionList = getSessionList(context);
        if (sessionList.size() != 0 && !isAppOnForeground(context)) {
            sessionList.set(sessionList.size() - 1, validateSessionDuration((Session) sessionList.get(sessionList.size() - 1), context));
            setSessionList(context, sessionList);
            sendSessions(context);
        }
    }

    static void sendSessions(final Context context) {
        if (isServerAvailable(context)) {
            final Session session = null;
            final List sessionList = getSessionList(context);
            if (sessionList.size() != 0) {
                int i = 0;
                while (true) {
                    if (i >= sessionList.size()) {
                        break;
                    }
                    if (validateSession((Session) sessionList.get(i))) {
                        if (((Session) sessionList.get(i)).getTotalDuration() != 0) {
                            session = (Session) sessionList.get(i);
                            break;
                        }
                    } else {
                        sessionList.remove(i);
                        setSessionList(context, sessionList);
                    }
                    i++;
                }
                if (session != null && isServerAvailable(context)) {
                    RequestHelper.getInstance().getAPIService().sendSession(session).enqueue(new Callback<ResponseBody>() {
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200 || response.code() == 400) {
                                if (response.code() == 400) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("API 404 : ");
                                    sb.append(session.toString());
                                    AnalyticsLogger.m88e(sb.toString());
                                }
                                sessionList.remove(session);
                                ServiceUtil.setSessionList(context, sessionList);
                                ServiceUtil.sendSessions(context);
                            }
                        }

                        public void onFailure(Call<ResponseBody> call, Throwable th) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("IOException :");
                            sb.append(th.getMessage());
                            sb.append(" AppKey:");
                            sb.append(session.getAppKey());
                            sb.append(", ApiKey:");
                            sb.append(session.getApiKey());
                            AnalyticsLogger.m88e(sb.toString());
                        }
                    });
                }
            }
        }
    }

    static boolean checkRootMethod1() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    static boolean checkRootMethod2() {
        for (String file : new String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"}) {
            if (new File(file).exists()) {
                return true;
            }
        }
        return false;
    }

    static boolean checkRootMethod3() {
        boolean z = false;
        Process process = null;
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", ChatConstants.ARG_USERS_ROLES_SUPER_USER});
            if (new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine() != null) {
                z = true;
            }
            if (exec != null) {
                exec.destroy();
            }
            return z;
        } catch (Throwable unused) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }

    static String generateUdid() {
        StringBuilder sb = new StringBuilder();
        sb.append("rooted_");
        sb.append(UUID.randomUUID().toString());
        return sb.toString();
    }

    static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return BinData.UNKNOWN;
        }
    }

    static void checkLocationPermissionGranted(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.checkPermission("android.permission.ACCESS_COARSE_LOCATION", context.getPackageName()) == 0) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            setLocationListenerForLastKnown();
            return;
        }
        if (packageManager.checkPermission("android.permission.ACCESS_FINE_LOCATION", context.getPackageName()) == 0) {
            setLocationListenerForGPS(context);
        }
    }

    private static void setLocationListenerForLastKnown() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            public void onSuccess(Location location) {
                if (location != null) {
                    EventBus.getDefault().post(new UserLocationEvent(location.getLatitude(), location.getLongitude()));
                }
            }
        });
    }

    private static void setLocationListenerForGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Param.LOCATION);
        if (locationManager != null) {
            String str = "gps";
            if (locationManager.isProviderEnabled(str)) {
                getLastKnownLocation(str, context);
                return;
            }
            String str2 = "network";
            if (locationManager.isProviderEnabled(str2)) {
                getLastKnownLocation(str2, context);
            }
        }
    }

    private static void getLastKnownLocation(String str, Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Param.LOCATION);
        Location lastKnownLocation = locationManager != null ? locationManager.getLastKnownLocation(str) : null;
        if (lastKnownLocation != null) {
            EventBus.getDefault().post(new UserLocationEvent(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
        }
    }

    private static void setMetaData(IpApiModel ipApiModel) {
        EventBus.getDefault().post(ipApiModel);
    }

    static String getConnectionType(Context context) {
        NetworkInfo info = getInfo(context);
        if (info == null || !info.isConnected()) {
            return null;
        }
        int type = info.getType();
        String str = BinData.UNKNOWN;
        if (type == 0) {
            int networkClass = getNetworkClass(getNetworkType(context));
            if (networkClass == 0) {
                return str;
            }
            if (networkClass == 1) {
                return "2G";
            }
            if (networkClass == 2) {
                return "3G";
            }
            if (networkClass == 3) {
                return "4G";
            }
        } else if (type == 1) {
            return "WiFi";
        } else {
            if (type == 9) {
                return "Ethernet";
            }
        }
        return str;
    }

    private static NetworkInfo getInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    private static int getNetworkClassReflect(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getNetworkClass", new Class[]{Integer.TYPE});
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return ((Integer) declaredMethod.invoke(null, new Object[]{Integer.valueOf(i)})).intValue();
    }

    private static int getNetworkClass(int i) {
        try {
            return getNetworkClassReflect(i);
        } catch (Exception unused) {
            switch (i) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                case 16:
                    return 1;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                    return 2;
                case 13:
                case 18:
                    return 3;
                default:
                    return 0;
            }
        }
    }

    private static int getNetworkType(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
    }

    private static boolean isServerAvailable(Context context) {
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        return isOnline(context);
    }

    private static boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    static Date getDate() {
        String str = "yyyy-MMM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return new SimpleDateFormat(str).parse(simpleDateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    static void finishSession(Context context, boolean z) {
        List sessionList = getSessionList(context);
        if (sessionList.size() != 0) {
            Session session = (Session) sessionList.get(sessionList.size() - 1);
            if (session.getTotalDuration() == 0) {
                session.setEndDate(getDate());
                sessionList.set(sessionList.size() - 1, validateSessionDuration(session, context));
            }
            setSessionList(context, sessionList);
            sendSessions(context);
        }
    }

    static void resetHealthCheckConditions(Context context) {
        stopHealthCheck();
        setHealthCheckCount(context, 0);
    }

    static void runHealthCheck(final Context context) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (ServiceUtil.isAppOnForeground(context)) {
                    ServiceUtil.setHealthCheckCount(context, ServiceUtil.getHealthCheckCount(context) + 1);
                    return;
                }
                ServiceUtil.scheduler.shutdown();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private static void stopHealthCheck() {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static boolean isAppOnForeground(Context context) {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    static void screenState(Context context, boolean z) {
        if (!isAppOnForeground(context)) {
            finishSession(context, false);
            stopHealthCheck();
        } else if (z) {
            ApplyzeAnalytics.getInstance().startSession();
        } else {
            finishSession(context, false);
            stopHealthCheck();
        }
    }

    private static void initializeSSLContext(Context context) {
        try {
            SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            ProviderInstaller.installIfNeeded(context.getApplicationContext());
        } catch (GooglePlayServicesRepairableException e2) {
            e2.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e3) {
            e3.printStackTrace();
        }
    }
}
