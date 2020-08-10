package com.google.android.gms.internal.gtm;

import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.internal.gtm.zzbn;
import java.io.IOException;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

class zzbo<T extends zzbn> extends zzam {
    private zzbp<T> zzyn;

    public zzbo(zzap zzap, zzbp<T> zzbp) {
        super(zzap);
        this.zzyn = zzbp;
    }

    public final T zzq(int i) {
        try {
            return zza(zzcm().zzdc().getResources().getXml(i));
        } catch (NotFoundException e) {
            zzd("inflate() called with unknown resourceId", e);
            return null;
        }
    }

    private final T zza(XmlResourceParser xmlResourceParser) {
        try {
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (xmlResourceParser.getEventType() == 2) {
                    String lowerCase = xmlResourceParser.getName().toLowerCase(Locale.US);
                    boolean equals = lowerCase.equals("screenname");
                    String str = PostalAddressParser.USER_ADDRESS_NAME_KEY;
                    if (equals) {
                        String attributeValue = xmlResourceParser.getAttributeValue(null, str);
                        String trim = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue) && !TextUtils.isEmpty(trim)) {
                            this.zzyn.zzb(attributeValue, trim);
                        }
                    } else if (lowerCase.equals("string")) {
                        String attributeValue2 = xmlResourceParser.getAttributeValue(null, str);
                        String trim2 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue2) && trim2 != null) {
                            this.zzyn.zzc(attributeValue2, trim2);
                        }
                    } else if (lowerCase.equals("bool")) {
                        String attributeValue3 = xmlResourceParser.getAttributeValue(null, str);
                        String trim3 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue3) && !TextUtils.isEmpty(trim3)) {
                            try {
                                this.zzyn.zza(attributeValue3, Boolean.parseBoolean(trim3));
                            } catch (NumberFormatException e) {
                                zzc("Error parsing bool configuration value", trim3, e);
                            }
                        }
                    } else if (lowerCase.equals("integer")) {
                        String attributeValue4 = xmlResourceParser.getAttributeValue(null, str);
                        String trim4 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty(attributeValue4) && !TextUtils.isEmpty(trim4)) {
                            try {
                                this.zzyn.zzb(attributeValue4, Integer.parseInt(trim4));
                            } catch (NumberFormatException e2) {
                                zzc("Error parsing int configuration value", trim4, e2);
                            }
                        }
                    }
                }
                eventType = xmlResourceParser.next();
            }
        } catch (IOException | XmlPullParserException e3) {
            zze("Error parsing tracker configuration file", e3);
        }
        return this.zzyn.zzel();
    }
}
