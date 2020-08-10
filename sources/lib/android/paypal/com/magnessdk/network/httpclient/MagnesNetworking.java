package lib.android.paypal.com.magnessdk.network.httpclient;

import android.net.Uri;
import java.util.Map;

public interface MagnesNetworking {
    int execute(byte[] bArr);

    String getPayPalDebugId();

    byte[] getResponseContent();

    void setHeader(Map<String, String> map);

    void setUri(Uri uri);
}
