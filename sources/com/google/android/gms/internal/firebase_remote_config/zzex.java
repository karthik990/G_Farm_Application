package com.google.android.gms.internal.firebase_remote_config;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public final class zzex {
    public static Map<String, String> zza(Context context, int i) {
        String str = "FirebaseRemoteConfig";
        HashMap hashMap = new HashMap();
        try {
            Resources resources = context.getResources();
            if (resources == null) {
                Log.e(str, "Could not find the resources of the current context while trying to set defaults from an XML.");
                return hashMap;
            }
            XmlResourceParser xml = resources.getXml(i);
            if (xml == null) {
                Log.e(str, "The XML referenced by the resourceId could not be found while trying to set defaults from an XML.");
                return hashMap;
            }
            String str2 = null;
            Object obj = null;
            Object obj2 = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 2) {
                    str2 = xml.getName();
                } else if (eventType == 3) {
                    if (xml.getName().equals("entry")) {
                        if (obj == null || obj2 == null) {
                            Log.w(str, "An entry in the defaults XML has an invalid key and/or value tag.");
                        } else {
                            hashMap.put(obj, obj2);
                        }
                        obj = null;
                        obj2 = null;
                    }
                    str2 = null;
                } else if (eventType == 4 && str2 != null) {
                    char c = 65535;
                    int hashCode = str2.hashCode();
                    if (hashCode != 106079) {
                        if (hashCode == 111972721) {
                            if (str2.equals(Param.VALUE)) {
                                c = 1;
                            }
                        }
                    } else if (str2.equals("key")) {
                        c = 0;
                    }
                    if (c == 0) {
                        obj = xml.getText();
                    } else if (c != 1) {
                        Log.w(str, "Encountered an unexpected tag while parsing the defaults XML.");
                    } else {
                        obj2 = xml.getText();
                    }
                }
            }
            return hashMap;
        } catch (IOException | XmlPullParserException e) {
            Log.e(str, "Encountered an error while parsing the defaults XML file.", e);
        }
    }
}
