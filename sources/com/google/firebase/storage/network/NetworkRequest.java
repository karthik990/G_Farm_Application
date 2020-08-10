package com.google.firebase.storage.network;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.internal.SlashUtil;
import com.google.firebase.storage.network.connection.HttpURLConnectionFactory;
import com.google.firebase.storage.network.connection.HttpURLConnectionFactoryImpl;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public abstract class NetworkRequest {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    static final String DELETE = "DELETE";
    static final String GET = "GET";
    public static final int INITIALIZATION_EXCEPTION = -1;
    private static final int MAXIMUM_TOKEN_WAIT_TIME_MS = 30000;
    public static final int NETWORK_UNAVAILABLE = -2;
    static final String PATCH = "PATCH";
    static final String POST = "POST";
    static final String PUT = "PUT";
    private static final String TAG = "NetworkRequest";
    private static final String UTF_8 = "UTF-8";
    private static final String X_FIREBASE_GMPID = "x-firebase-gmpid";
    static HttpURLConnectionFactory connectionFactory = new HttpURLConnectionFactoryImpl();
    private static String gmsCoreVersion = null;
    public static String sNetworkRequestUrl = "https://firebasestorage.googleapis.com/v0";
    public static String sUploadUrl = "https://firebasestorage.googleapis.com/v0/b/";
    private HttpURLConnection connection;
    private Context context;
    protected Exception mException;
    protected final Uri mGsUri;
    private String rawStringResponse;
    private Map<String, String> requestHeaders = new HashMap();
    private int resultCode;
    private Map<String, List<String>> resultHeaders;
    private InputStream resultInputStream;
    private int resultingContentLength;

    /* access modifiers changed from: protected */
    public abstract String getAction();

    /* access modifiers changed from: protected */
    public JSONObject getOutputJSON() {
        return null;
    }

    /* access modifiers changed from: protected */
    public byte[] getOutputRaw() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getOutputRawSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public String getQueryParameters() throws UnsupportedEncodingException {
        return null;
    }

    public NetworkRequest(Uri uri, FirebaseApp firebaseApp) {
        Preconditions.checkNotNull(uri);
        Preconditions.checkNotNull(firebaseApp);
        this.mGsUri = uri;
        this.context = firebaseApp.getApplicationContext();
        setCustomHeader(X_FIREBASE_GMPID, firebaseApp.getOptions().getApplicationId());
    }

    public static String getAuthority() {
        return Uri.parse(sNetworkRequestUrl).getAuthority();
    }

    public static String getdefaultURL(Uri uri) {
        Preconditions.checkNotNull(uri);
        String pathWithoutBucket = getPathWithoutBucket(uri);
        StringBuilder sb = new StringBuilder();
        sb.append(sNetworkRequestUrl);
        sb.append("/b/");
        sb.append(uri.getAuthority());
        sb.append("/o/");
        sb.append(pathWithoutBucket != null ? SlashUtil.unSlashize(pathWithoutBucket) : "");
        return sb.toString();
    }

    public static String getPathWithoutBucket(Uri uri) {
        String encodedPath = uri.getEncodedPath();
        return (encodedPath == null || !encodedPath.startsWith("/")) ? encodedPath : encodedPath.substring(1);
    }

    /* access modifiers changed from: protected */
    public String getURL() {
        return getdefaultURL(this.mGsUri);
    }

    public String getPathWithoutBucket() {
        return getPathWithoutBucket(this.mGsUri);
    }

    public final void reset() {
        this.mException = null;
        this.resultCode = 0;
    }

    public void setCustomHeader(String str, String str2) {
        this.requestHeaders.put(str, str2);
    }

    public InputStream getStream() {
        return this.resultInputStream;
    }

    public JSONObject getResultBody() {
        if (TextUtils.isEmpty(this.rawStringResponse)) {
            return new JSONObject();
        }
        try {
            return new JSONObject(this.rawStringResponse);
        } catch (JSONException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("error parsing result into JSON:");
            sb.append(this.rawStringResponse);
            Log.e(TAG, sb.toString(), e);
            return new JSONObject();
        }
    }

    public void performRequestStart(String str) {
        if (this.mException != null) {
            this.resultCode = -1;
            return;
        }
        String str2 = TAG;
        String str3 = " ";
        if (Log.isLoggable(str2, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("sending network request ");
            sb.append(getAction());
            sb.append(str3);
            sb.append(getURL());
            Log.d(str2, sb.toString());
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            this.resultCode = -2;
            this.mException = new SocketException("Network subsystem is unavailable");
            return;
        }
        try {
            this.connection = createConnection();
            this.connection.setRequestMethod(getAction());
            constructMessage(this.connection, str);
            parseResponse(this.connection);
            if (Log.isLoggable(str2, 3)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("network request result ");
                sb2.append(this.resultCode);
                Log.d(str2, sb2.toString());
            }
        } catch (IOException e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("error sending network request ");
            sb3.append(getAction());
            sb3.append(str3);
            sb3.append(getURL());
            Log.w(str2, sb3.toString(), e);
            this.mException = e;
            this.resultCode = -2;
        }
    }

    public void performRequestEnd() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    private final void performRequest(String str) {
        performRequestStart(str);
        try {
            processResponseStream();
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("error sending network request ");
            sb.append(getAction());
            sb.append(" ");
            sb.append(getURL());
            Log.w(TAG, sb.toString(), e);
            this.mException = e;
            this.resultCode = -2;
        }
        performRequestEnd();
    }

    public void performRequest(String str, Context context2) {
        if (ensureNetworkAvailable(context2)) {
            performRequest(str);
        }
    }

    private boolean ensureNetworkAvailable(Context context2) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        this.mException = new SocketException("Network subsystem is unavailable");
        this.resultCode = -2;
        return false;
    }

    private HttpURLConnection createConnection() throws IOException {
        String str;
        String queryParameters = getQueryParameters();
        if (TextUtils.isEmpty(queryParameters)) {
            str = getURL();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(getURL());
            sb.append("?");
            sb.append(queryParameters);
            str = sb.toString();
        }
        return connectionFactory.createInstance(new URL(str));
    }

    private static String getGmsCoreVersion(Context context2) {
        if (gmsCoreVersion == null) {
            try {
                gmsCoreVersion = context2.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionName;
            } catch (NameNotFoundException e) {
                Log.e(TAG, "Unable to find gmscore in package manager", e);
            }
            if (gmsCoreVersion == null) {
                gmsCoreVersion = "[No Gmscore]";
            }
        }
        return gmsCoreVersion;
    }

    private void constructMessage(HttpURLConnection httpURLConnection, String str) throws IOException {
        int i;
        byte[] bArr;
        Preconditions.checkNotNull(httpURLConnection);
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = TAG;
        if (!isEmpty) {
            StringBuilder sb = new StringBuilder();
            sb.append("Firebase ");
            sb.append(str);
            httpURLConnection.setRequestProperty("Authorization", sb.toString());
        } else {
            Log.w(str2, "no auth token for request");
        }
        StringBuilder sb2 = new StringBuilder("Android/");
        String gmsCoreVersion2 = getGmsCoreVersion(this.context);
        if (!TextUtils.isEmpty(gmsCoreVersion2)) {
            sb2.append(gmsCoreVersion2);
        }
        httpURLConnection.setRequestProperty("X-Firebase-Storage-Version", sb2.toString());
        for (Entry entry : this.requestHeaders.entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        JSONObject outputJSON = getOutputJSON();
        if (outputJSON != null) {
            bArr = outputJSON.toString().getBytes("UTF-8");
            i = bArr.length;
        } else {
            bArr = getOutputRaw();
            i = getOutputRawSize();
            if (i == 0 && bArr != null) {
                i = bArr.length;
            }
        }
        String str3 = "Content-Length";
        if (bArr == null || bArr.length <= 0) {
            httpURLConnection.setRequestProperty(str3, "0");
        } else {
            if (outputJSON != null) {
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
            }
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty(str3, Integer.toString(i));
        }
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        if (bArr != null && bArr.length > 0) {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            if (outputStream != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                try {
                    bufferedOutputStream.write(bArr, 0, i);
                } finally {
                    bufferedOutputStream.close();
                }
            } else {
                Log.e(str2, "Unable to write to the http request!");
            }
        }
    }

    private void parseResponse(HttpURLConnection httpURLConnection) throws IOException {
        Preconditions.checkNotNull(httpURLConnection);
        this.resultCode = httpURLConnection.getResponseCode();
        this.resultHeaders = httpURLConnection.getHeaderFields();
        this.resultingContentLength = httpURLConnection.getContentLength();
        if (isResultSuccess()) {
            this.resultInputStream = httpURLConnection.getInputStream();
        } else {
            this.resultInputStream = httpURLConnection.getErrorStream();
        }
    }

    private void processResponseStream() throws IOException {
        if (isResultSuccess()) {
            parseSuccessulResponse(this.resultInputStream);
        } else {
            parseErrorResponse(this.resultInputStream);
        }
    }

    /* access modifiers changed from: protected */
    public void parseSuccessulResponse(InputStream inputStream) throws IOException {
        parseResponse(inputStream);
    }

    /* access modifiers changed from: protected */
    public void parseErrorResponse(InputStream inputStream) throws IOException {
        parseResponse(inputStream);
    }

    private void parseResponse(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                } finally {
                    bufferedReader.close();
                }
            }
        }
        this.rawStringResponse = sb.toString();
        if (!isResultSuccess()) {
            this.mException = new IOException(this.rawStringResponse);
        }
    }

    public String getRawResult() {
        return this.rawStringResponse;
    }

    public Map<String, String> getResultHeaders() {
        return this.requestHeaders;
    }

    public Exception getException() {
        return this.mException;
    }

    public Map<String, List<String>> getResultHeadersImpl() {
        return this.resultHeaders;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public boolean isResultSuccess() {
        int i = this.resultCode;
        return i >= 200 && i < 300;
    }

    /* access modifiers changed from: 0000 */
    public String getPostDataString(List<String> list, List<String> list2, boolean z) throws UnsupportedEncodingException {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (int i = 0; i < list.size(); i++) {
            if (z2) {
                z2 = false;
            } else {
                sb.append("&");
            }
            String str = "UTF-8";
            String str2 = (String) list.get(i);
            if (z) {
                str2 = URLEncoder.encode(str2, str);
            }
            sb.append(str2);
            sb.append("=");
            sb.append(z ? URLEncoder.encode((String) list2.get(i), str) : (String) list2.get(i));
        }
        return sb.toString();
    }

    public String getResultString(String str) {
        Map resultHeadersImpl = getResultHeadersImpl();
        if (resultHeadersImpl != null) {
            List list = (List) resultHeadersImpl.get(str);
            if (list != null && list.size() > 0) {
                return (String) list.get(0);
            }
        }
        return null;
    }

    public int getResultingContentLength() {
        return this.resultingContentLength;
    }

    public <TResult> void completeTask(TaskCompletionSource<TResult> taskCompletionSource, TResult tresult) {
        Exception exception = getException();
        if (!isResultSuccess() || exception != null) {
            taskCompletionSource.setException(StorageException.fromExceptionAndHttpCode(exception, getResultCode()));
        } else {
            taskCompletionSource.setResult(tresult);
        }
    }
}
