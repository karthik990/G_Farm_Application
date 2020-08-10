package com.firebase.p037ui.auth.util.data;

import android.text.TextUtils;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.firebase.ui.auth.util.data.ProviderUtils */
public final class ProviderUtils {
    private static final String GITHUB_IDENTITY = "https://github.com";
    private static final String PHONE_IDENTITY = "https://phone.firebase";

    private ProviderUtils() {
        throw new AssertionError("No instance for you!");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.firebase.auth.AuthCredential getAuthCredential(com.firebase.p037ui.auth.IdpResponse r5) {
        /*
            java.lang.String r0 = r5.getProviderType()
            int r1 = r0.hashCode()
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r1) {
                case -1830313082: goto L_0x002d;
                case -1536293812: goto L_0x0023;
                case -364826023: goto L_0x0019;
                case 1985010934: goto L_0x000f;
                default: goto L_0x000e;
            }
        L_0x000e:
            goto L_0x0038
        L_0x000f:
            java.lang.String r1 = "github.com"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 3
            goto L_0x0039
        L_0x0019:
            java.lang.String r1 = "facebook.com"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 1
            goto L_0x0039
        L_0x0023:
            java.lang.String r1 = "google.com"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 0
            goto L_0x0039
        L_0x002d:
            java.lang.String r1 = "twitter.com"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0038
            r0 = 2
            goto L_0x0039
        L_0x0038:
            r0 = -1
        L_0x0039:
            r1 = 0
            if (r0 == 0) goto L_0x0062
            if (r0 == r4) goto L_0x0059
            if (r0 == r3) goto L_0x004c
            if (r0 == r2) goto L_0x0043
            return r1
        L_0x0043:
            java.lang.String r5 = r5.getIdpToken()
            com.google.firebase.auth.AuthCredential r5 = com.google.firebase.auth.GithubAuthProvider.getCredential(r5)
            return r5
        L_0x004c:
            java.lang.String r0 = r5.getIdpToken()
            java.lang.String r5 = r5.getIdpSecret()
            com.google.firebase.auth.AuthCredential r5 = com.google.firebase.auth.TwitterAuthProvider.getCredential(r0, r5)
            return r5
        L_0x0059:
            java.lang.String r5 = r5.getIdpToken()
            com.google.firebase.auth.AuthCredential r5 = com.google.firebase.auth.FacebookAuthProvider.getCredential(r5)
            return r5
        L_0x0062:
            java.lang.String r5 = r5.getIdpToken()
            com.google.firebase.auth.AuthCredential r5 = com.google.firebase.auth.GoogleAuthProvider.getCredential(r5, r1)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.util.data.ProviderUtils.getAuthCredential(com.firebase.ui.auth.IdpResponse):com.google.firebase.auth.AuthCredential");
    }

    public static String idpResponseToAccountType(IdpResponse idpResponse) {
        if (idpResponse == null) {
            return null;
        }
        return providerIdToAccountType(idpResponse.getProviderType());
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String signInMethodToProviderId(java.lang.String r8) {
        /*
            int r0 = r8.hashCode()
            java.lang.String r1 = "emailLink"
            java.lang.String r2 = "github.com"
            java.lang.String r3 = "password"
            java.lang.String r4 = "phone"
            java.lang.String r5 = "facebook.com"
            java.lang.String r6 = "google.com"
            java.lang.String r7 = "twitter.com"
            switch(r0) {
                case -1830313082: goto L_0x0047;
                case -1536293812: goto L_0x003f;
                case -364826023: goto L_0x0037;
                case 106642798: goto L_0x002f;
                case 1216985755: goto L_0x0027;
                case 1985010934: goto L_0x001f;
                case 2120171958: goto L_0x0017;
                default: goto L_0x0016;
            }
        L_0x0016:
            goto L_0x004f
        L_0x0017:
            boolean r0 = r8.equals(r1)
            if (r0 == 0) goto L_0x004f
            r0 = 6
            goto L_0x0050
        L_0x001f:
            boolean r0 = r8.equals(r2)
            if (r0 == 0) goto L_0x004f
            r0 = 3
            goto L_0x0050
        L_0x0027:
            boolean r0 = r8.equals(r3)
            if (r0 == 0) goto L_0x004f
            r0 = 5
            goto L_0x0050
        L_0x002f:
            boolean r0 = r8.equals(r4)
            if (r0 == 0) goto L_0x004f
            r0 = 4
            goto L_0x0050
        L_0x0037:
            boolean r0 = r8.equals(r5)
            if (r0 == 0) goto L_0x004f
            r0 = 1
            goto L_0x0050
        L_0x003f:
            boolean r0 = r8.equals(r6)
            if (r0 == 0) goto L_0x004f
            r0 = 0
            goto L_0x0050
        L_0x0047:
            boolean r0 = r8.equals(r7)
            if (r0 == 0) goto L_0x004f
            r0 = 2
            goto L_0x0050
        L_0x004f:
            r0 = -1
        L_0x0050:
            switch(r0) {
                case 0: goto L_0x0070;
                case 1: goto L_0x006f;
                case 2: goto L_0x006e;
                case 3: goto L_0x006d;
                case 4: goto L_0x006c;
                case 5: goto L_0x006b;
                case 6: goto L_0x006a;
                default: goto L_0x0053;
            }
        L_0x0053:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unknown method: "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r8 = r1.toString()
            r0.<init>(r8)
            throw r0
        L_0x006a:
            return r1
        L_0x006b:
            return r3
        L_0x006c:
            return r4
        L_0x006d:
            return r2
        L_0x006e:
            return r7
        L_0x006f:
            return r5
        L_0x0070:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.util.data.ProviderUtils.signInMethodToProviderId(java.lang.String):java.lang.String");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String providerIdToAccountType(java.lang.String r5) {
        /*
            int r0 = r5.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r0) {
                case -1830313082: goto L_0x003e;
                case -1536293812: goto L_0x0034;
                case -364826023: goto L_0x002a;
                case 106642798: goto L_0x0020;
                case 1216985755: goto L_0x0016;
                case 1985010934: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0049
        L_0x000c:
            java.lang.String r0 = "github.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0049
            r5 = 3
            goto L_0x004a
        L_0x0016:
            java.lang.String r0 = "password"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0049
            r5 = 5
            goto L_0x004a
        L_0x0020:
            java.lang.String r0 = "phone"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0049
            r5 = 4
            goto L_0x004a
        L_0x002a:
            java.lang.String r0 = "facebook.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0049
            r5 = 1
            goto L_0x004a
        L_0x0034:
            java.lang.String r0 = "google.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0049
            r5 = 0
            goto L_0x004a
        L_0x003e:
            java.lang.String r0 = "twitter.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0049
            r5 = 2
            goto L_0x004a
        L_0x0049:
            r5 = -1
        L_0x004a:
            if (r5 == 0) goto L_0x0062
            if (r5 == r4) goto L_0x005f
            if (r5 == r3) goto L_0x005c
            if (r5 == r2) goto L_0x0059
            if (r5 == r1) goto L_0x0056
            r5 = 0
            return r5
        L_0x0056:
            java.lang.String r5 = "https://phone.firebase"
            return r5
        L_0x0059:
            java.lang.String r5 = "https://github.com"
            return r5
        L_0x005c:
            java.lang.String r5 = "https://twitter.com"
            return r5
        L_0x005f:
            java.lang.String r5 = "https://www.facebook.com"
            return r5
        L_0x0062:
            java.lang.String r5 = "https://accounts.google.com"
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.util.data.ProviderUtils.providerIdToAccountType(java.lang.String):java.lang.String");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String accountTypeToProviderId(java.lang.String r5) {
        /*
            int r0 = r5.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r0) {
                case -1534095099: goto L_0x0034;
                case -1294469354: goto L_0x002a;
                case -376862683: goto L_0x0020;
                case 746549591: goto L_0x0016;
                case 1721158175: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x003e
        L_0x000c:
            java.lang.String r0 = "https://www.facebook.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x003e
            r5 = 1
            goto L_0x003f
        L_0x0016:
            java.lang.String r0 = "https://twitter.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x003e
            r5 = 2
            goto L_0x003f
        L_0x0020:
            java.lang.String r0 = "https://accounts.google.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x003e
            r5 = 0
            goto L_0x003f
        L_0x002a:
            java.lang.String r0 = "https://phone.firebase"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x003e
            r5 = 4
            goto L_0x003f
        L_0x0034:
            java.lang.String r0 = "https://github.com"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x003e
            r5 = 3
            goto L_0x003f
        L_0x003e:
            r5 = -1
        L_0x003f:
            if (r5 == 0) goto L_0x0058
            if (r5 == r4) goto L_0x0055
            if (r5 == r3) goto L_0x0051
            if (r5 == r2) goto L_0x004e
            if (r5 == r1) goto L_0x004b
            r5 = 0
            return r5
        L_0x004b:
            java.lang.String r5 = "phone"
            return r5
        L_0x004e:
            java.lang.String r5 = "github.com"
            return r5
        L_0x0051:
            java.lang.String r5 = "twitter.com"
            return r5
        L_0x0055:
            java.lang.String r5 = "facebook.com"
            return r5
        L_0x0058:
            java.lang.String r5 = "google.com"
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.util.data.ProviderUtils.accountTypeToProviderId(java.lang.String):java.lang.String");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String providerIdToProviderName(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -1830313082: goto L_0x0044;
                case -1536293812: goto L_0x003a;
                case -364826023: goto L_0x0030;
                case 106642798: goto L_0x0026;
                case 1216985755: goto L_0x001c;
                case 1985010934: goto L_0x0012;
                case 2120171958: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x004f
        L_0x0008:
            java.lang.String r0 = "emailLink"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 6
            goto L_0x0050
        L_0x0012:
            java.lang.String r0 = "github.com"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 3
            goto L_0x0050
        L_0x001c:
            java.lang.String r0 = "password"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 5
            goto L_0x0050
        L_0x0026:
            java.lang.String r0 = "phone"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 4
            goto L_0x0050
        L_0x0030:
            java.lang.String r0 = "facebook.com"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 1
            goto L_0x0050
        L_0x003a:
            java.lang.String r0 = "google.com"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 0
            goto L_0x0050
        L_0x0044:
            java.lang.String r0 = "twitter.com"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x004f
            r1 = 2
            goto L_0x0050
        L_0x004f:
            r1 = -1
        L_0x0050:
            switch(r1) {
                case 0: goto L_0x008c;
                case 1: goto L_0x0081;
                case 2: goto L_0x0076;
                case 3: goto L_0x006b;
                case 4: goto L_0x0060;
                case 5: goto L_0x0055;
                case 6: goto L_0x0055;
                default: goto L_0x0053;
            }
        L_0x0053:
            r1 = 0
            return r1
        L_0x0055:
            android.content.Context r1 = com.firebase.p037ui.auth.AuthUI.getApplicationContext()
            int r0 = com.firebase.p037ui.auth.C1330R.C1335string.fui_idp_name_email
            java.lang.String r1 = r1.getString(r0)
            return r1
        L_0x0060:
            android.content.Context r1 = com.firebase.p037ui.auth.AuthUI.getApplicationContext()
            int r0 = com.firebase.p037ui.auth.C1330R.C1335string.fui_idp_name_phone
            java.lang.String r1 = r1.getString(r0)
            return r1
        L_0x006b:
            android.content.Context r1 = com.firebase.p037ui.auth.AuthUI.getApplicationContext()
            int r0 = com.firebase.p037ui.auth.C1330R.C1335string.fui_idp_name_github
            java.lang.String r1 = r1.getString(r0)
            return r1
        L_0x0076:
            android.content.Context r1 = com.firebase.p037ui.auth.AuthUI.getApplicationContext()
            int r0 = com.firebase.p037ui.auth.C1330R.C1335string.fui_idp_name_twitter
            java.lang.String r1 = r1.getString(r0)
            return r1
        L_0x0081:
            android.content.Context r1 = com.firebase.p037ui.auth.AuthUI.getApplicationContext()
            int r0 = com.firebase.p037ui.auth.C1330R.C1335string.fui_idp_name_facebook
            java.lang.String r1 = r1.getString(r0)
            return r1
        L_0x008c:
            android.content.Context r1 = com.firebase.p037ui.auth.AuthUI.getApplicationContext()
            int r0 = com.firebase.p037ui.auth.C1330R.C1335string.fui_idp_name_google
            java.lang.String r1 = r1.getString(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.util.data.ProviderUtils.providerIdToProviderName(java.lang.String):java.lang.String");
    }

    public static IdpConfig getConfigFromIdps(List<IdpConfig> list, String str) {
        for (IdpConfig idpConfig : list) {
            if (idpConfig.getProviderId().equals(str)) {
                return idpConfig;
            }
        }
        return null;
    }

    public static IdpConfig getConfigFromIdpsOrThrow(List<IdpConfig> list, String str) {
        IdpConfig configFromIdps = getConfigFromIdps(list, str);
        if (configFromIdps != null) {
            return configFromIdps;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Provider ");
        sb.append(str);
        sb.append(" not found.");
        throw new IllegalStateException(sb.toString());
    }

    public static Task<List<String>> fetchSortedProviders(FirebaseAuth firebaseAuth, final FlowParameters flowParameters, String str) {
        if (TextUtils.isEmpty(str)) {
            return Tasks.forException(new NullPointerException("Email cannot be empty"));
        }
        return firebaseAuth.fetchSignInMethodsForEmail(str).continueWithTask(new Continuation<SignInMethodQueryResult, Task<List<String>>>() {
            public Task<List<String>> then(Task<SignInMethodQueryResult> task) {
                List<String> signInMethods = ((SignInMethodQueryResult) task.getResult()).getSignInMethods();
                if (signInMethods == null) {
                    signInMethods = new ArrayList<>();
                }
                ArrayList arrayList = new ArrayList(flowParameters.providers.size());
                for (IdpConfig providerId : flowParameters.providers) {
                    arrayList.add(providerId.getProviderId());
                }
                ArrayList arrayList2 = new ArrayList(signInMethods.size());
                for (String signInMethodToProviderId : signInMethods) {
                    String signInMethodToProviderId2 = ProviderUtils.signInMethodToProviderId(signInMethodToProviderId);
                    if (arrayList.contains(signInMethodToProviderId2)) {
                        arrayList2.add(0, signInMethodToProviderId2);
                    }
                }
                if (task.isSuccessful() && arrayList2.isEmpty() && !signInMethods.isEmpty()) {
                    return Tasks.forException(new FirebaseUiException(3));
                }
                reorderPriorities(arrayList2);
                return Tasks.forResult(arrayList2);
            }

            private void reorderPriorities(List<String> list) {
                changePriority(list, "password", true);
                changePriority(list, "google.com", true);
                changePriority(list, "emailLink", false);
            }

            private void changePriority(List<String> list, String str, boolean z) {
                if (!list.remove(str)) {
                    return;
                }
                if (z) {
                    list.add(0, str);
                } else {
                    list.add(str);
                }
            }
        });
    }

    public static Task<String> fetchTopProvider(FirebaseAuth firebaseAuth, FlowParameters flowParameters, String str) {
        return fetchSortedProviders(firebaseAuth, flowParameters, str).continueWithTask(new Continuation<List<String>, Task<String>>() {
            public Task<String> then(Task<List<String>> task) {
                if (!task.isSuccessful()) {
                    return Tasks.forException(task.getException());
                }
                List list = (List) task.getResult();
                if (list.isEmpty()) {
                    return Tasks.forResult(null);
                }
                return Tasks.forResult(list.get(0));
            }
        });
    }
}
