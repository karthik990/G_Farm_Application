package com.google.firebase.database.core.utilities;

import android.util.Base64;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.RepoInfo;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Marker;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Utilities {
    private static final char[] HEX_CHARACTERS = "0123456789abcdef".toCharArray();

    public static int compareInts(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public static int compareLongs(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    public static ParsedUrl parseUrl(String str) throws DatabaseException {
        String str2 = "/";
        try {
            int indexOf = str.indexOf("//");
            if (indexOf != -1) {
                int i = indexOf + 2;
                int indexOf2 = str.substring(i).indexOf(str2);
                if (indexOf2 != -1) {
                    int i2 = indexOf2 + i;
                    String[] split = str.substring(i2).split(str2, -1);
                    StringBuilder sb = new StringBuilder();
                    for (int i3 = 0; i3 < split.length; i3++) {
                        if (!split[i3].equals("")) {
                            sb.append(str2);
                            sb.append(URLEncoder.encode(split[i3], "UTF-8"));
                        }
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str.substring(0, i2));
                    sb2.append(sb.toString());
                    str = sb2.toString();
                }
                URI uri = new URI(str);
                String replace = uri.getPath().replace(Marker.ANY_NON_NULL_MARKER, " ");
                Validation.validateRootPathString(replace);
                Path path = new Path(replace);
                String scheme = uri.getScheme();
                RepoInfo repoInfo = new RepoInfo();
                repoInfo.host = uri.getHost().toLowerCase();
                int port = uri.getPort();
                if (port != -1) {
                    repoInfo.secure = scheme.equals("https");
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(repoInfo.host);
                    sb3.append(":");
                    sb3.append(port);
                    repoInfo.host = sb3.toString();
                } else {
                    repoInfo.secure = true;
                }
                repoInfo.namespace = repoInfo.host.split("\\.", -1)[0].toLowerCase();
                repoInfo.internalHost = repoInfo.host;
                ParsedUrl parsedUrl = new ParsedUrl();
                parsedUrl.path = path;
                parsedUrl.repoInfo = repoInfo;
                return parsedUrl;
            }
            throw new URISyntaxException(str, "Invalid scheme specified");
        } catch (URISyntaxException e) {
            throw new DatabaseException("Invalid Firebase Database url specified", e);
        } catch (UnsupportedEncodingException e2) {
            throw new DatabaseException("Failed to URLEncode the path", e2);
        }
    }

    public static String[] splitIntoFrames(String str, int i) {
        int i2 = 0;
        if (str.length() <= i) {
            return new String[]{str};
        }
        ArrayList arrayList = new ArrayList();
        while (i2 < str.length()) {
            int i3 = i2 + i;
            arrayList.add(str.substring(i2, Math.min(i3, str.length())));
            i2 = i3;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String sha1HexDigest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            return Base64.encodeToString(instance.digest(), 2);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Missing SHA-1 MessageDigest provider.", e);
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 encoding is required for Firebase Database to run!");
        }
    }

    public static String stringHashV2Representation(String str) {
        String replace = str.indexOf(92) != -1 ? str.replace("\\", "\\\\") : str;
        if (str.indexOf(34) != -1) {
            replace = replace.replace("\"", "\\\"");
        }
        StringBuilder sb = new StringBuilder();
        sb.append('\"');
        sb.append(replace);
        sb.append('\"');
        return sb.toString();
    }

    public static String doubleToHashString(double d) {
        StringBuilder sb = new StringBuilder(16);
        long doubleToLongBits = Double.doubleToLongBits(d);
        for (int i = 7; i >= 0; i--) {
            int i2 = (int) ((doubleToLongBits >>> (i * 8)) & 255);
            int i3 = (i2 >> 4) & 15;
            int i4 = i2 & 15;
            sb.append(HEX_CHARACTERS[i3]);
            sb.append(HEX_CHARACTERS[i4]);
        }
        return sb.toString();
    }

    public static Integer tryParseInt(String str) {
        if (str.length() > 11 || str.length() == 0) {
            return null;
        }
        int i = 0;
        boolean z = true;
        if (str.charAt(0) != '-') {
            z = false;
        } else if (str.length() == 1) {
            return null;
        } else {
            i = 1;
        }
        long j = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return null;
            }
            j = (j * 10) + ((long) (charAt - '0'));
            i++;
        }
        if (z) {
            long j2 = -j;
            if (j2 < -2147483648L) {
                return null;
            }
            return Integer.valueOf((int) j2);
        } else if (j > 2147483647L) {
            return null;
        } else {
            return Integer.valueOf((int) j);
        }
    }

    public static <C> C castOrNull(Object obj, Class<C> cls) {
        if (cls.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        return null;
    }

    public static <C> C getOrNull(Object obj, String str, Class<C> cls) {
        if (obj == null) {
            return null;
        }
        Object obj2 = ((Map) castOrNull(obj, Map.class)).get(str);
        if (obj2 != null) {
            return castOrNull(obj2, cls);
        }
        return null;
    }

    public static void hardAssert(boolean z) {
        hardAssert(z, "");
    }

    public static void hardAssert(boolean z, String str) {
        if (!z) {
            StringBuilder sb = new StringBuilder();
            sb.append("hardAssert failed: ");
            sb.append(str);
            throw new AssertionError(sb.toString());
        }
    }

    public static Pair<Task<Void>, CompletionListener> wrapOnComplete(CompletionListener completionListener) {
        if (completionListener != null) {
            return new Pair<>(null, completionListener);
        }
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        return new Pair<>(taskCompletionSource.getTask(), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    TaskCompletionSource.this.setException(databaseError.toException());
                } else {
                    TaskCompletionSource.this.setResult(null);
                }
            }
        });
    }
}
