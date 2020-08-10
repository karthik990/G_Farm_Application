package com.mobiroller.helpers;

import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.WebResourceResponse;
import com.google.common.net.HttpHeaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Marker;

public class WebviewResourceMappingHelper {
    private static WebviewResourceMappingHelper instance;
    private List<LocalAssetMapModel> localAssetMapModelList;
    private List<String> overridableExtensions = new ArrayList(Arrays.asList(new String[]{"js", "css", "png", "jpg", "woff", "ttf", "eot", "ico"}));

    private class LocalAssetMapModel {
        String asset_url;
        String url;

        private LocalAssetMapModel() {
        }
    }

    private WebviewResourceMappingHelper() {
    }

    public static WebviewResourceMappingHelper getInstance() {
        if (instance == null) {
            instance = new WebviewResourceMappingHelper();
        }
        return instance;
    }

    public String getLocalFilePath(String str, Context context) {
        String localFileNameForUrl = getLocalFileNameForUrl(str);
        return (localFileNameForUrl == null || localFileNameForUrl.isEmpty() || !fileExists(localFileNameForUrl, context)) ? "" : getFileFullPath(localFileNameForUrl, context);
    }

    public String getLocalFileNameForUrl(String str) {
        String[] split = str.split("/");
        return split.length > 0 ? split[split.length - 1] : "";
    }

    private boolean fileExists(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append("/cart/");
        sb.append(str);
        return new File(sb.toString()).exists();
    }

    private String getFileFullPath(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append("/cart/");
        sb.append(str);
        return sb.toString();
    }

    public List<String> getOverridableExtensions() {
        return this.overridableExtensions;
    }

    public String getFileExt(String str) {
        return str.substring(str.lastIndexOf(".") + 1, str.length());
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getMimeType(java.lang.String r2) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case 3401: goto L_0x004e;
                case 98819: goto L_0x0044;
                case 100618: goto L_0x003a;
                case 104085: goto L_0x0030;
                case 105441: goto L_0x0026;
                case 111145: goto L_0x001c;
                case 115174: goto L_0x0012;
                case 3655064: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0058
        L_0x0008:
            java.lang.String r0 = "woff"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 5
            goto L_0x0059
        L_0x0012:
            java.lang.String r0 = "ttf"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 6
            goto L_0x0059
        L_0x001c:
            java.lang.String r0 = "png"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 2
            goto L_0x0059
        L_0x0026:
            java.lang.String r0 = "jpg"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 3
            goto L_0x0059
        L_0x0030:
            java.lang.String r0 = "ico"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 4
            goto L_0x0059
        L_0x003a:
            java.lang.String r0 = "eot"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 7
            goto L_0x0059
        L_0x0044:
            java.lang.String r0 = "css"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 0
            goto L_0x0059
        L_0x004e:
            java.lang.String r0 = "js"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0058
            r2 = 1
            goto L_0x0059
        L_0x0058:
            r2 = -1
        L_0x0059:
            switch(r2) {
                case 0: goto L_0x006e;
                case 1: goto L_0x006b;
                case 2: goto L_0x0068;
                case 3: goto L_0x0065;
                case 4: goto L_0x0062;
                case 5: goto L_0x005f;
                case 6: goto L_0x005f;
                case 7: goto L_0x005f;
                default: goto L_0x005c;
            }
        L_0x005c:
            java.lang.String r2 = ""
            goto L_0x0070
        L_0x005f:
            java.lang.String r2 = "application/x-font-opentype"
            goto L_0x0070
        L_0x0062:
            java.lang.String r2 = "image/x-icon"
            goto L_0x0070
        L_0x0065:
            java.lang.String r2 = "image/jpeg"
            goto L_0x0070
        L_0x0068:
            java.lang.String r2 = "image/png"
            goto L_0x0070
        L_0x006b:
            java.lang.String r2 = "text/javascript"
            goto L_0x0070
        L_0x006e:
            java.lang.String r2 = "text/css"
        L_0x0070:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.WebviewResourceMappingHelper.getMimeType(java.lang.String):java.lang.String");
    }

    public static WebResourceResponse getWebResourceResponseFromFile(String str, String str2, String str3) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        if (VERSION.SDK_INT < 21) {
            return new WebResourceResponse(str2, str3, fileInputStream);
        }
        HashMap hashMap = new HashMap();
        hashMap.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, Marker.ANY_MARKER);
        WebResourceResponse webResourceResponse = new WebResourceResponse(str2, str3, 200, "OK", hashMap, fileInputStream);
        return webResourceResponse;
    }
}
