package com.crashlytics.android.answers;

import android.os.Bundle;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public class FirebaseAnalyticsEventMapper {
    private static final Set<String> EVENT_NAMES = new HashSet(Arrays.asList(new String[]{"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "screen_view", "firebase_extra_parameter"}));
    private static final String FIREBASE_LEVEL_NAME = "level_name";
    private static final String FIREBASE_METHOD = "method";
    private static final String FIREBASE_RATING = "rating";
    private static final String FIREBASE_SUCCESS = "success";

    public FirebaseAnalyticsEvent mapEvent(SessionEvent sessionEvent) {
        Bundle bundle;
        String str;
        boolean z = true;
        boolean z2 = Type.CUSTOM.equals(sessionEvent.type) && sessionEvent.customType != null;
        boolean z3 = Type.PREDEFINED.equals(sessionEvent.type) && sessionEvent.predefinedType != null;
        if (!z2 && !z3) {
            return null;
        }
        if (z3) {
            bundle = mapPredefinedEvent(sessionEvent);
        } else {
            bundle = new Bundle();
            if (sessionEvent.customAttributes != null) {
                mapCustomEventAttributes(bundle, sessionEvent.customAttributes);
            }
        }
        if (z3) {
            String str2 = (String) sessionEvent.predefinedAttributes.get("success");
            if (str2 == null || Boolean.parseBoolean(str2)) {
                z = false;
            }
            str = mapPredefinedEventName(sessionEvent.predefinedType, z);
        } else {
            str = mapCustomEventName(sessionEvent.customType);
        }
        Fabric.getLogger().mo64074d(Answers.TAG, "Logging event into firebase...");
        return new FirebaseAnalyticsEvent(str, bundle);
    }

    private String mapCustomEventName(String str) {
        if (str == null || str.length() == 0) {
            return "fabric_unnamed_event";
        }
        String str2 = "fabric_";
        if (EVENT_NAMES.contains(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            return sb.toString();
        }
        String replaceAll = str.replaceAll("[^\\p{Alnum}_]+", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        if (replaceAll.startsWith("ga_") || replaceAll.startsWith("google_") || replaceAll.startsWith("firebase_") || !Character.isLetter(replaceAll.charAt(0))) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(replaceAll);
            replaceAll = sb2.toString();
        }
        if (replaceAll.length() > 40) {
            replaceAll = replaceAll.substring(0, 40);
        }
        return replaceAll;
    }

    private String mapAttribute(String str) {
        if (str == null || str.length() == 0) {
            return "fabric_unnamed_parameter";
        }
        String replaceAll = str.replaceAll("[^\\p{Alnum}_]+", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        if (replaceAll.startsWith("ga_") || replaceAll.startsWith("google_") || replaceAll.startsWith("firebase_") || !Character.isLetter(replaceAll.charAt(0))) {
            StringBuilder sb = new StringBuilder();
            sb.append("fabric_");
            sb.append(replaceAll);
            replaceAll = sb.toString();
        }
        if (replaceAll.length() > 40) {
            replaceAll = replaceAll.substring(0, 40);
        }
        return replaceAll;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005e, code lost:
        if (r11.equals(r1) != false) goto L_0x00cd;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String mapPredefinedEventName(java.lang.String r11, boolean r12) {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r1 = "purchase"
            java.lang.String r2 = "signUp"
            r3 = -1
            r4 = 2
            r5 = 1
            java.lang.String r6 = "login"
            if (r12 == 0) goto L_0x004a
            int r12 = r11.hashCode()
            r7 = -902468296(0xffffffffca356d38, float:-2972494.0)
            if (r12 == r7) goto L_0x0031
            r7 = 103149417(0x625ef69, float:3.1208942E-35)
            if (r12 == r7) goto L_0x0029
            r7 = 1743324417(0x67e90501, float:2.2008074E24)
            if (r12 == r7) goto L_0x0021
            goto L_0x0039
        L_0x0021:
            boolean r12 = r11.equals(r1)
            if (r12 == 0) goto L_0x0039
            r12 = 0
            goto L_0x003a
        L_0x0029:
            boolean r12 = r11.equals(r6)
            if (r12 == 0) goto L_0x0039
            r12 = 2
            goto L_0x003a
        L_0x0031:
            boolean r12 = r11.equals(r2)
            if (r12 == 0) goto L_0x0039
            r12 = 1
            goto L_0x003a
        L_0x0039:
            r12 = -1
        L_0x003a:
            if (r12 == 0) goto L_0x0047
            if (r12 == r5) goto L_0x0044
            if (r12 == r4) goto L_0x0041
            goto L_0x004a
        L_0x0041:
            java.lang.String r11 = "failed_login"
            return r11
        L_0x0044:
            java.lang.String r11 = "failed_sign_up"
            return r11
        L_0x0047:
            java.lang.String r11 = "failed_ecommerce_purchase"
            return r11
        L_0x004a:
            int r12 = r11.hashCode()
            java.lang.String r7 = "share"
            java.lang.String r8 = "search"
            java.lang.String r9 = "invite"
            switch(r12) {
                case -2131650889: goto L_0x00c1;
                case -1183699191: goto L_0x00b8;
                case -938102371: goto L_0x00ae;
                case -906336856: goto L_0x00a6;
                case -902468296: goto L_0x009e;
                case -389087554: goto L_0x0094;
                case 23457852: goto L_0x008a;
                case 103149417: goto L_0x0081;
                case 109400031: goto L_0x0079;
                case 196004670: goto L_0x006e;
                case 1664021448: goto L_0x0062;
                case 1743324417: goto L_0x005a;
                default: goto L_0x0058;
            }
        L_0x0058:
            goto L_0x00cc
        L_0x005a:
            boolean r12 = r11.equals(r1)
            if (r12 == 0) goto L_0x00cc
            goto L_0x00cd
        L_0x0062:
            java.lang.String r12 = "startCheckout"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x00cc
            r0 = 2
            goto L_0x00cd
        L_0x006e:
            java.lang.String r12 = "levelStart"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x00cc
            r0 = 10
            goto L_0x00cd
        L_0x0079:
            boolean r12 = r11.equals(r7)
            if (r12 == 0) goto L_0x00cc
            r0 = 5
            goto L_0x00cd
        L_0x0081:
            boolean r12 = r11.equals(r6)
            if (r12 == 0) goto L_0x00cc
            r0 = 8
            goto L_0x00cd
        L_0x008a:
            java.lang.String r12 = "addToCart"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x00cc
            r0 = 1
            goto L_0x00cd
        L_0x0094:
            java.lang.String r12 = "contentView"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x00cc
            r0 = 3
            goto L_0x00cd
        L_0x009e:
            boolean r12 = r11.equals(r2)
            if (r12 == 0) goto L_0x00cc
            r0 = 7
            goto L_0x00cd
        L_0x00a6:
            boolean r12 = r11.equals(r8)
            if (r12 == 0) goto L_0x00cc
            r0 = 4
            goto L_0x00cd
        L_0x00ae:
            java.lang.String r12 = "rating"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x00cc
            r0 = 6
            goto L_0x00cd
        L_0x00b8:
            boolean r12 = r11.equals(r9)
            if (r12 == 0) goto L_0x00cc
            r0 = 9
            goto L_0x00cd
        L_0x00c1:
            java.lang.String r12 = "levelEnd"
            boolean r12 = r11.equals(r12)
            if (r12 == 0) goto L_0x00cc
            r0 = 11
            goto L_0x00cd
        L_0x00cc:
            r0 = -1
        L_0x00cd:
            switch(r0) {
                case 0: goto L_0x00ef;
                case 1: goto L_0x00ec;
                case 2: goto L_0x00e9;
                case 3: goto L_0x00e6;
                case 4: goto L_0x00e5;
                case 5: goto L_0x00e4;
                case 6: goto L_0x00e1;
                case 7: goto L_0x00dd;
                case 8: goto L_0x00dc;
                case 9: goto L_0x00db;
                case 10: goto L_0x00d8;
                case 11: goto L_0x00d5;
                default: goto L_0x00d0;
            }
        L_0x00d0:
            java.lang.String r11 = r10.mapCustomEventName(r11)
            return r11
        L_0x00d5:
            java.lang.String r11 = "level_end"
            return r11
        L_0x00d8:
            java.lang.String r11 = "level_start"
            return r11
        L_0x00db:
            return r9
        L_0x00dc:
            return r6
        L_0x00dd:
            java.lang.String r11 = "sign_up"
            return r11
        L_0x00e1:
            java.lang.String r11 = "rate_content"
            return r11
        L_0x00e4:
            return r7
        L_0x00e5:
            return r8
        L_0x00e6:
            java.lang.String r11 = "select_content"
            return r11
        L_0x00e9:
            java.lang.String r11 = "begin_checkout"
            return r11
        L_0x00ec:
            java.lang.String r11 = "add_to_cart"
            return r11
        L_0x00ef:
            java.lang.String r11 = "ecommerce_purchase"
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.answers.FirebaseAnalyticsEventMapper.mapPredefinedEventName(java.lang.String, boolean):java.lang.String");
    }

    private Bundle mapPredefinedEvent(SessionEvent sessionEvent) {
        Bundle bundle = new Bundle();
        boolean equals = ProductAction.ACTION_PURCHASE.equals(sessionEvent.predefinedType);
        String str = "itemType";
        String str2 = Param.ITEM_CATEGORY;
        String str3 = "itemName";
        String str4 = "itemId";
        String str5 = "itemPrice";
        String str6 = Param.VALUE;
        String str7 = Param.ITEM_NAME;
        String str8 = Param.ITEM_ID;
        String str9 = Param.CURRENCY;
        if (equals) {
            putString(bundle, str8, (String) sessionEvent.predefinedAttributes.get(str4));
            putString(bundle, str7, (String) sessionEvent.predefinedAttributes.get(str3));
            putString(bundle, str2, (String) sessionEvent.predefinedAttributes.get(str));
            putDouble(bundle, str6, mapPriceValue(sessionEvent.predefinedAttributes.get(str5)));
            putString(bundle, str9, (String) sessionEvent.predefinedAttributes.get(str9));
        } else {
            if ("addToCart".equals(sessionEvent.predefinedType)) {
                putString(bundle, str8, (String) sessionEvent.predefinedAttributes.get(str4));
                putString(bundle, str7, (String) sessionEvent.predefinedAttributes.get(str3));
                putString(bundle, str2, (String) sessionEvent.predefinedAttributes.get(str));
                putDouble(bundle, "price", mapPriceValue(sessionEvent.predefinedAttributes.get(str5)));
                putDouble(bundle, str6, mapPriceValue(sessionEvent.predefinedAttributes.get(str5)));
                putString(bundle, str9, (String) sessionEvent.predefinedAttributes.get(str9));
                bundle.putLong(Param.QUANTITY, 1);
            } else {
                if ("startCheckout".equals(sessionEvent.predefinedType)) {
                    putLong(bundle, Param.QUANTITY, Long.valueOf((long) ((Integer) sessionEvent.predefinedAttributes.get("itemCount")).intValue()));
                    putDouble(bundle, str6, mapPriceValue(sessionEvent.predefinedAttributes.get("totalPrice")));
                    putString(bundle, str9, (String) sessionEvent.predefinedAttributes.get(str9));
                } else {
                    boolean equals2 = "contentView".equals(sessionEvent.predefinedType);
                    String str10 = "contentName";
                    String str11 = "contentId";
                    String str12 = "contentType";
                    String str13 = Param.CONTENT_TYPE;
                    if (equals2) {
                        putString(bundle, str13, (String) sessionEvent.predefinedAttributes.get(str12));
                        putString(bundle, str8, (String) sessionEvent.predefinedAttributes.get(str11));
                        putString(bundle, str7, (String) sessionEvent.predefinedAttributes.get(str10));
                    } else {
                        if (Event.SEARCH.equals(sessionEvent.predefinedType)) {
                            putString(bundle, Param.SEARCH_TERM, (String) sessionEvent.predefinedAttributes.get("query"));
                        } else {
                            String str14 = "method";
                            if (Event.SHARE.equals(sessionEvent.predefinedType)) {
                                putString(bundle, str14, (String) sessionEvent.predefinedAttributes.get(str14));
                                putString(bundle, str13, (String) sessionEvent.predefinedAttributes.get(str12));
                                putString(bundle, str8, (String) sessionEvent.predefinedAttributes.get(str11));
                                putString(bundle, str7, (String) sessionEvent.predefinedAttributes.get(str10));
                            } else {
                                String str15 = sessionEvent.predefinedType;
                                String str16 = FIREBASE_RATING;
                                if (str16.equals(str15)) {
                                    putString(bundle, str16, String.valueOf(sessionEvent.predefinedAttributes.get(str16)));
                                    putString(bundle, str13, (String) sessionEvent.predefinedAttributes.get(str12));
                                    putString(bundle, str8, (String) sessionEvent.predefinedAttributes.get(str11));
                                    putString(bundle, str7, (String) sessionEvent.predefinedAttributes.get(str10));
                                } else {
                                    if ("signUp".equals(sessionEvent.predefinedType)) {
                                        putString(bundle, str14, (String) sessionEvent.predefinedAttributes.get(str14));
                                    } else {
                                        if ("login".equals(sessionEvent.predefinedType)) {
                                            putString(bundle, str14, (String) sessionEvent.predefinedAttributes.get(str14));
                                        } else {
                                            if ("invite".equals(sessionEvent.predefinedType)) {
                                                putString(bundle, str14, (String) sessionEvent.predefinedAttributes.get(str14));
                                            } else {
                                                if ("levelStart".equals(sessionEvent.predefinedType)) {
                                                    putString(bundle, "level_name", (String) sessionEvent.predefinedAttributes.get("levelName"));
                                                } else {
                                                    if ("levelEnd".equals(sessionEvent.predefinedType)) {
                                                        putDouble(bundle, Param.SCORE, mapDouble(sessionEvent.predefinedAttributes.get(Param.SCORE)));
                                                        putString(bundle, "level_name", (String) sessionEvent.predefinedAttributes.get("levelName"));
                                                        putInt(bundle, "success", mapBooleanValue((String) sessionEvent.predefinedAttributes.get("success")));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        mapCustomEventAttributes(bundle, sessionEvent.customAttributes);
        return bundle;
    }

    private void putLong(Bundle bundle, String str, Long l) {
        if (l != null) {
            bundle.putLong(str, l.longValue());
        }
    }

    private void putInt(Bundle bundle, String str, Integer num) {
        if (num != null) {
            bundle.putInt(str, num.intValue());
        }
    }

    private void putString(Bundle bundle, String str, String str2) {
        if (str2 != null) {
            bundle.putString(str, str2);
        }
    }

    private void putDouble(Bundle bundle, String str, Double d) {
        Double mapDouble = mapDouble(d);
        if (mapDouble != null) {
            bundle.putDouble(str, mapDouble.doubleValue());
        }
    }

    private Double mapDouble(Object obj) {
        String valueOf = String.valueOf(obj);
        if (valueOf == null) {
            return null;
        }
        return Double.valueOf(valueOf);
    }

    private Integer mapBooleanValue(String str) {
        if (str == null) {
            return null;
        }
        return Integer.valueOf(str.equals("true") ? 1 : 0);
    }

    private Double mapPriceValue(Object obj) {
        Long l = (Long) obj;
        if (l == null) {
            return null;
        }
        return Double.valueOf(new BigDecimal(l.longValue()).divide(AddToCartEvent.MICRO_CONSTANT).doubleValue());
    }

    private void mapCustomEventAttributes(Bundle bundle, Map<String, Object> map) {
        for (Entry entry : map.entrySet()) {
            Object value = entry.getValue();
            String mapAttribute = mapAttribute((String) entry.getKey());
            if (value instanceof String) {
                bundle.putString(mapAttribute, entry.getValue().toString());
            } else if (value instanceof Double) {
                bundle.putDouble(mapAttribute, ((Double) entry.getValue()).doubleValue());
            } else if (value instanceof Long) {
                bundle.putLong(mapAttribute, ((Long) entry.getValue()).longValue());
            } else if (value instanceof Integer) {
                bundle.putInt(mapAttribute, ((Integer) entry.getValue()).intValue());
            }
        }
    }
}
