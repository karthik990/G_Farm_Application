package com.paypal.android.sdk.onetouch.core.fpti;

import android.os.Handler;
import android.os.Looper;
import com.mobiroller.constants.ChatConstants;
import com.paypal.android.sdk.data.collector.InstallationIdentifier;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.base.DeviceInspector;
import com.paypal.android.sdk.onetouch.core.base.URLEncoderHelper;
import com.paypal.android.sdk.onetouch.core.enums.Protocol;
import com.paypal.android.sdk.onetouch.core.network.EnvironmentManager;
import com.paypal.android.sdk.onetouch.core.network.PayPalHttpClient;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import org.objectweb.asm.Opcodes;

public class FptiManager {
    private final ContextInspector mContextInspector;
    /* access modifiers changed from: private */
    public final PayPalHttpClient mHttpClient;
    private FptiToken mToken;

    public FptiManager(ContextInspector contextInspector, PayPalHttpClient payPalHttpClient) {
        this.mContextInspector = contextInspector;
        this.mHttpClient = payPalHttpClient;
    }

    public void trackFpti(TrackingPoint trackingPoint, String str, Map<String, String> map, Protocol protocol) {
        if (!EnvironmentManager.isMock(str)) {
            FptiToken fptiToken = this.mToken;
            if (fptiToken == null || !fptiToken.isValid()) {
                this.mToken = new FptiToken();
            }
            long currentTimeMillis = System.currentTimeMillis();
            String encode = URLEncoderHelper.encode(InstallationIdentifier.getInstallationGUID(this.mContextInspector.getContext()));
            StringBuilder sb = new StringBuilder();
            sb.append("mobile:otc:");
            sb.append(trackingPoint.getCd());
            String str2 = ":";
            sb.append(str2);
            String str3 = "";
            sb.append(protocol != null ? protocol.name() : str3);
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            String str4 = "Android:";
            sb3.append(str4);
            sb3.append(str);
            sb3.append(str2);
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb2);
            sb5.append(str2);
            sb5.append(sb4);
            if (trackingPoint.hasError()) {
                str3 = "|error";
            }
            sb5.append(str3);
            String sb6 = sb5.toString();
            HashMap hashMap = new HashMap(map);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(DeviceInspector.getApplicationInfoName(this.mContextInspector.getContext()));
            String str5 = "|";
            sb7.append(str5);
            String str6 = "2.21.0";
            sb7.append(str6);
            sb7.append(str5);
            sb7.append(this.mContextInspector.getContext().getPackageName());
            hashMap.put("apid", sb7.toString());
            String str7 = "otc";
            hashMap.put("bchn", str7);
            String str8 = "mobile";
            hashMap.put("bzsr", str8);
            hashMap.put("dsid", encode);
            hashMap.put(ChatConstants.ARG_USER_INFO_EMAIL, "im");
            hashMap.put("g", getGmtOffsetInMinutes());
            hashMap.put("lgin", "out");
            hashMap.put("mapv", str6);
            hashMap.put("mcar", DeviceInspector.getSimOperatorName(this.mContextInspector.getContext()));
            hashMap.put("mdvs", DeviceInspector.getDeviceName());
            hashMap.put("mosv", DeviceInspector.getOs());
            hashMap.put("page", sb6);
            hashMap.put("pgrp", sb2);
            hashMap.put("rsta", Locale.getDefault().toString());
            hashMap.put("srce", str7);
            hashMap.put("sv", str8);
            hashMap.put("t", Long.toString(currentTimeMillis - ((long) getGMTOffset())));
            StringBuilder sb8 = new StringBuilder();
            sb8.append(str4);
            sb8.append(str);
            sb8.append(str2);
            hashMap.put("vers", sb8.toString());
            hashMap.put("vid", this.mToken.mToken);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.accumulate("tracking_visitor_id", encode);
                jSONObject.accumulate("tracking_visit_id", this.mToken.mToken);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.accumulate("actor", jSONObject);
                jSONObject2.accumulate("channel", str8);
                jSONObject2.accumulate("tracking_event", Long.toString(currentTimeMillis));
                jSONObject2.accumulate("event_params", getEventParams(hashMap));
                sendRequest(new JSONObject().accumulate("events", jSONObject2).toString());
            } catch (JSONException unused) {
            }
        }
    }

    private JSONObject getEventParams(Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : map.keySet()) {
            jSONObject.accumulate(str, map.get(str));
        }
        return jSONObject;
    }

    /* access modifiers changed from: 0000 */
    public void sendRequest(final String str) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                FptiManager.this.mHttpClient.post("tracking/events", str, null);
            }
        }, (long) ((new Random().nextInt(Opcodes.ARRAYLENGTH) + 10) * 1000));
    }

    private int getGMTOffset() {
        return new GregorianCalendar().getTimeZone().getRawOffset();
    }

    private String getGmtOffsetInMinutes() {
        return Integer.toString((getGMTOffset() / 1000) / 60);
    }
}
