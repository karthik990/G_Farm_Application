package com.google.firebase.storage.internal;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.storage.network.NetworkRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpHost;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class Util {
    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final int MAXIMUM_TOKEN_WAIT_TIME_MS = 30000;
    public static final int NETWORK_UNAVAILABLE = -2;
    private static final String TAG = "StorageUtil";

    public static long parseDateTime(String str) {
        if (str == null) {
            return 0;
        }
        String replaceAll = str.replaceAll("Z$", "-0000");
        try {
            return new SimpleDateFormat(ISO_8601_FORMAT, Locale.getDefault()).parse(replaceAll).getTime();
        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to parse datetime:");
            sb.append(replaceAll);
            Log.w(TAG, sb.toString(), e);
            return 0;
        }
    }

    public static boolean equals(Object obj, Object obj2) {
        return Objects.equal(obj, obj2);
    }

    private static String getAuthority() throws RemoteException {
        return NetworkRequest.getAuthority();
    }

    public static Uri normalize(FirebaseApp firebaseApp, String str) throws UnsupportedEncodingException {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str3 = "gs://";
        if (str.toLowerCase().startsWith(str3)) {
            String preserveSlashEncode = SlashUtil.preserveSlashEncode(SlashUtil.normalizeSlashes(str.substring(5)));
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(preserveSlashEncode);
            return Uri.parse(sb.toString());
        }
        Uri parse = Uri.parse(str);
        String scheme = parse.getScheme();
        String str4 = TAG;
        if (scheme == null || (!equals(scheme.toLowerCase(), HttpHost.DEFAULT_SCHEME_NAME) && !equals(scheme.toLowerCase(), "https"))) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("FirebaseStorage is unable to support the scheme:");
            sb2.append(scheme);
            Log.w(str4, sb2.toString());
            throw new IllegalArgumentException("Uri scheme");
        }
        try {
            int indexOf = parse.getAuthority().toLowerCase().indexOf(getAuthority());
            String slashize = SlashUtil.slashize(parse.getEncodedPath());
            String str5 = "Firebase Storage URLs must point to an object in your Storage Bucket. Please obtain a URL using the Firebase Console or getDownloadUrl().";
            if (indexOf == 0) {
                String str6 = "/";
                if (slashize.startsWith(str6)) {
                    int indexOf2 = slashize.indexOf("/b/", 0);
                    int i = indexOf2 + 3;
                    int indexOf3 = slashize.indexOf(str6, i);
                    int indexOf4 = slashize.indexOf("/o/", 0);
                    if (indexOf2 == -1 || indexOf3 == -1) {
                        Log.w(str4, str5);
                        throw new IllegalArgumentException(str5);
                    }
                    str2 = slashize.substring(i, indexOf3);
                    slashize = indexOf4 != -1 ? slashize.substring(indexOf4 + 3) : "";
                    Preconditions.checkNotEmpty(str2, "No bucket specified");
                    return new Builder().scheme("gs").authority(str2).encodedPath(slashize).build();
                }
            }
            if (indexOf > 1) {
                str2 = parse.getAuthority().substring(0, indexOf - 1);
                Preconditions.checkNotEmpty(str2, "No bucket specified");
                return new Builder().scheme("gs").authority(str2).encodedPath(slashize).build();
            }
            Log.w(str4, str5);
            throw new IllegalArgumentException(str5);
        } catch (RemoteException unused) {
            throw new UnsupportedEncodingException("Could not parse Url because the Storage network layer did not load");
        }
    }

    public static String getCurrentAuthToken(InternalAuthProvider internalAuthProvider) {
        String str;
        String str2 = TAG;
        if (internalAuthProvider != null) {
            try {
                str = ((GetTokenResult) Tasks.await(internalAuthProvider.getAccessToken(false), 30000, TimeUnit.MILLISECONDS)).getToken();
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("error getting token ");
                sb.append(e);
                Log.e(str2, sb.toString());
            }
        } else {
            str = null;
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        Log.w(str2, "no auth token for request");
        return null;
    }
}
