package com.mobiroller.helpers;

import android.content.Context;
import com.mobiroller.mobi942763453128.R;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class FcmNotificationBuilder {
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String KEY_DATA = "data";
    private static final String KEY_FCM_TOKEN = "fcm_token";
    private static final String KEY_INFO_TYPE = "info_type";
    private static final String KEY_NOTIFICATION = "notification";
    private static final String KEY_NOTIFICATION_TYPE = "notification_type";
    private static final String KEY_RECEIVER_UID = "receiver_uid";
    private static final String KEY_SCREEN_ID = "screen_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TO = "to";
    private static final String KEY_UID = "uid";
    private static final String KEY_USERNAME = "username";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "FcmNotificationBuilder";
    private Context context;
    private String mFirebaseToken;
    private int mInfoType;
    private String mMessage;
    private String mNotificationType = "textMessage";
    private String mReceiverFirebaseToken;
    private String mReceiverUid;
    private String mScreenId;
    private String mTitle;
    private String mUid;
    private String mUsername;

    private FcmNotificationBuilder() {
    }

    public static FcmNotificationBuilder initialize() {
        return new FcmNotificationBuilder();
    }

    public FcmNotificationBuilder title(String str) {
        this.mTitle = str;
        return this;
    }

    public FcmNotificationBuilder infoType(int i) {
        this.mInfoType = i;
        return this;
    }

    public FcmNotificationBuilder context(Context context2) {
        this.context = context2;
        return this;
    }

    public FcmNotificationBuilder message(String str) {
        this.mMessage = str;
        return this;
    }

    public FcmNotificationBuilder notificationType(String str) {
        this.mNotificationType = str;
        return this;
    }

    public FcmNotificationBuilder username(String str) {
        this.mUsername = str;
        return this;
    }

    public FcmNotificationBuilder uid(String str) {
        this.mUid = str;
        return this;
    }

    public FcmNotificationBuilder receiverUid(String str) {
        this.mReceiverUid = str;
        return this;
    }

    public FcmNotificationBuilder screenId(String str) {
        this.mScreenId = str;
        return this;
    }

    public FcmNotificationBuilder firebaseToken(String str) {
        this.mFirebaseToken = str;
        return this;
    }

    public FcmNotificationBuilder receiverFirebaseToken(String str) {
        this.mReceiverFirebaseToken = str;
        return this;
    }

    public void send() {
        RequestBody requestBody;
        try {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, getValidJsonBody().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            requestBody = null;
        }
        Builder addHeader = new Builder().addHeader("Content-Type", "application/json");
        StringBuilder sb = new StringBuilder();
        sb.append("key=");
        sb.append(this.context.getString(R.string.firebase_gcm_server_api_key));
        new OkHttpClient().newCall(addHeader.addHeader("Authorization", sb.toString()).url(FCM_URL).post(requestBody).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException iOException) {
            }

            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    private JSONObject getValidJsonBody() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(KEY_TO, this.mReceiverFirebaseToken);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("title", this.mTitle);
        jSONObject2.put("text", this.mMessage);
        jSONObject2.put(KEY_USERNAME, this.mUsername);
        jSONObject2.put("uid", this.mUid);
        jSONObject2.put(KEY_RECEIVER_UID, this.mReceiverUid);
        jSONObject2.put(KEY_FCM_TOKEN, this.mFirebaseToken);
        jSONObject2.put("screen_id", this.mScreenId);
        jSONObject2.put(KEY_INFO_TYPE, this.mInfoType);
        jSONObject2.put(KEY_NOTIFICATION_TYPE, this.mNotificationType);
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }
}
