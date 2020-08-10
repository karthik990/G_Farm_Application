package com.twitter.sdk.android.core.internal;

import android.text.TextUtils;
import com.twitter.sdk.android.core.models.User;

public final class UserUtils {

    /* renamed from: com.twitter.sdk.android.core.internal.UserUtils$1 */
    static /* synthetic */ class C52201 {

        /* renamed from: $SwitchMap$com$twitter$sdk$android$core$internal$UserUtils$AvatarSize */
        static final /* synthetic */ int[] f3654xd21c2ed = new int[AvatarSize.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.twitter.sdk.android.core.internal.UserUtils$AvatarSize[] r0 = com.twitter.sdk.android.core.internal.UserUtils.AvatarSize.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3654xd21c2ed = r0
                int[] r0 = f3654xd21c2ed     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.twitter.sdk.android.core.internal.UserUtils$AvatarSize r1 = com.twitter.sdk.android.core.internal.UserUtils.AvatarSize.NORMAL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3654xd21c2ed     // Catch:{ NoSuchFieldError -> 0x001f }
                com.twitter.sdk.android.core.internal.UserUtils$AvatarSize r1 = com.twitter.sdk.android.core.internal.UserUtils.AvatarSize.BIGGER     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3654xd21c2ed     // Catch:{ NoSuchFieldError -> 0x002a }
                com.twitter.sdk.android.core.internal.UserUtils$AvatarSize r1 = com.twitter.sdk.android.core.internal.UserUtils.AvatarSize.MINI     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3654xd21c2ed     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.twitter.sdk.android.core.internal.UserUtils$AvatarSize r1 = com.twitter.sdk.android.core.internal.UserUtils.AvatarSize.ORIGINAL     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f3654xd21c2ed     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.twitter.sdk.android.core.internal.UserUtils$AvatarSize r1 = com.twitter.sdk.android.core.internal.UserUtils.AvatarSize.REASONABLY_SMALL     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.twitter.sdk.android.core.internal.UserUtils.C52201.<clinit>():void");
        }
    }

    public enum AvatarSize {
        NORMAL("_normal"),
        BIGGER("_bigger"),
        MINI("_mini"),
        ORIGINAL("_original"),
        REASONABLY_SMALL("_reasonably_small");
        
        private final String suffix;

        private AvatarSize(String str) {
            this.suffix = str;
        }

        /* access modifiers changed from: 0000 */
        public String getSuffix() {
            return this.suffix;
        }
    }

    private UserUtils() {
    }

    public static String getProfileImageUrlHttps(User user, AvatarSize avatarSize) {
        if (user == null || user.profileImageUrlHttps == null) {
            return null;
        }
        String str = user.profileImageUrlHttps;
        if (!(avatarSize == null || str == null)) {
            int i = C52201.f3654xd21c2ed[avatarSize.ordinal()];
            if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
                return str;
            }
            str = str.replace(AvatarSize.NORMAL.getSuffix(), avatarSize.getSuffix());
        }
        return str;
    }

    public static CharSequence formatScreenName(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return "";
        }
        if (charSequence.charAt(0) == '@') {
            return charSequence;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(charSequence);
        return sb.toString();
    }
}
