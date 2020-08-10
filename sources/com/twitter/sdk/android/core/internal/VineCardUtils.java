package com.twitter.sdk.android.core.internal;

import com.twitter.sdk.android.core.models.Card;
import com.twitter.sdk.android.core.models.ImageValue;
import com.twitter.sdk.android.core.models.UserValue;

public class VineCardUtils {
    public static final String PLAYER_CARD = "player";
    public static final String VINE_CARD = "vine";
    public static final long VINE_USER_ID = 586671909;

    private VineCardUtils() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0012, code lost:
        if (VINE_CARD.equals(r2.name) != false) goto L_0x0014;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isVine(com.twitter.sdk.android.core.models.Card r2) {
        /*
            java.lang.String r0 = r2.name
            java.lang.String r1 = "player"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0014
            java.lang.String r0 = r2.name
            java.lang.String r1 = "vine"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x001c
        L_0x0014:
            boolean r2 = isVineUser(r2)
            if (r2 == 0) goto L_0x001c
            r2 = 1
            goto L_0x001d
        L_0x001c:
            r2 = 0
        L_0x001d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.twitter.sdk.android.core.internal.VineCardUtils.isVine(com.twitter.sdk.android.core.models.Card):boolean");
    }

    private static boolean isVineUser(Card card) {
        UserValue userValue = (UserValue) card.bindingValues.get("site");
        if (userValue != null) {
            try {
                if (Long.parseLong(userValue.idStr) == VINE_USER_ID) {
                    return true;
                }
            } catch (NumberFormatException unused) {
            }
        }
        return false;
    }

    public static String getPublisherId(Card card) {
        return ((UserValue) card.bindingValues.get("site")).idStr;
    }

    public static String getStreamUrl(Card card) {
        return (String) card.bindingValues.get("player_stream_url");
    }

    public static ImageValue getImageValue(Card card) {
        return (ImageValue) card.bindingValues.get("player_image");
    }
}
