package com.firebase.p037ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.login.LoginManager;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.remote.TwitterSignInHandler;
import com.firebase.p037ui.auth.util.CredentialUtils;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.GoogleApiUtils;
import com.firebase.p037ui.auth.util.Preconditions;
import com.firebase.p037ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p037ui.auth.util.data.ProviderAvailability;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest.Builder;
import com.google.android.gms.auth.api.credentials.CredentialRequestResponse;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.twitter.sdk.android.core.TwitterCore;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.slf4j.Marker;

/* renamed from: com.firebase.ui.auth.AuthUI */
public final class AuthUI {
    public static final String ANONYMOUS_PROVIDER = "anonymous";
    public static final String EMAIL_LINK_PROVIDER = "emailLink";
    private static final IdentityHashMap<FirebaseApp, AuthUI> INSTANCES = new IdentityHashMap<>();
    public static final int NO_LOGO = -1;
    public static final Set<String> SOCIAL_PROVIDERS;
    public static final Set<String> SUPPORTED_PROVIDERS;
    public static final String TAG = "AuthUI";
    public static final String UNCONFIGURED_CONFIG_VALUE = "CHANGE-ME";
    private static Context sApplicationContext;
    /* access modifiers changed from: private */
    public final FirebaseApp mApp;
    /* access modifiers changed from: private */
    public final FirebaseAuth mAuth = FirebaseAuth.getInstance(this.mApp);

    /* renamed from: com.firebase.ui.auth.AuthUI$AuthIntentBuilder */
    private abstract class AuthIntentBuilder<T extends AuthIntentBuilder> {
        boolean mAlwaysShowProviderChoice;
        AuthMethodPickerLayout mAuthMethodPickerLayout;
        boolean mEnableCredentials;
        boolean mEnableHints;
        int mLogo;
        String mPrivacyPolicyUrl;
        final List<IdpConfig> mProviders;
        int mTheme;
        String mTosUrl;

        /* access modifiers changed from: protected */
        public abstract FlowParameters getFlowParams();

        private AuthIntentBuilder() {
            this.mProviders = new ArrayList();
            this.mLogo = -1;
            this.mTheme = AuthUI.getDefaultTheme();
            this.mAlwaysShowProviderChoice = false;
            this.mEnableCredentials = true;
            this.mEnableHints = true;
            this.mAuthMethodPickerLayout = null;
        }

        public T setTheme(int i) {
            this.mTheme = Preconditions.checkValidStyle(AuthUI.this.mApp.getApplicationContext(), i, "theme identifier is unknown or not a style definition", new Object[0]);
            return this;
        }

        public T setLogo(int i) {
            this.mLogo = i;
            return this;
        }

        @Deprecated
        public T setTosUrl(String str) {
            this.mTosUrl = str;
            return this;
        }

        @Deprecated
        public T setPrivacyPolicyUrl(String str) {
            this.mPrivacyPolicyUrl = str;
            return this;
        }

        public T setTosAndPrivacyPolicyUrls(String str, String str2) {
            Preconditions.checkNotNull(str, "tosUrl cannot be null", new Object[0]);
            Preconditions.checkNotNull(str2, "privacyPolicyUrl cannot be null", new Object[0]);
            this.mTosUrl = str;
            this.mPrivacyPolicyUrl = str2;
            return this;
        }

        public T setAvailableProviders(List<IdpConfig> list) {
            Preconditions.checkNotNull(list, "idpConfigs cannot be null", new Object[0]);
            if (list.size() != 1 || !((IdpConfig) list.get(0)).getProviderId().equals(AuthUI.ANONYMOUS_PROVIDER)) {
                this.mProviders.clear();
                for (IdpConfig idpConfig : list) {
                    if (!this.mProviders.contains(idpConfig)) {
                        this.mProviders.add(idpConfig);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Each provider can only be set once. ");
                        sb.append(idpConfig.getProviderId());
                        sb.append(" was set twice.");
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
                return this;
            }
            throw new IllegalStateException("Sign in as guest cannot be the only sign in method. In this case, sign the user in anonymously your self; no UI is needed.");
        }

        public T setIsSmartLockEnabled(boolean z) {
            return setIsSmartLockEnabled(z, z);
        }

        public T setIsSmartLockEnabled(boolean z, boolean z2) {
            this.mEnableCredentials = z;
            this.mEnableHints = z2;
            return this;
        }

        public T setAuthMethodPickerLayout(AuthMethodPickerLayout authMethodPickerLayout) {
            this.mAuthMethodPickerLayout = authMethodPickerLayout;
            return this;
        }

        public T setAlwaysShowSignInMethodScreen(boolean z) {
            this.mAlwaysShowProviderChoice = z;
            return this;
        }

        public Intent build() {
            if (this.mProviders.isEmpty()) {
                this.mProviders.add(new EmailBuilder().build());
            }
            return KickoffActivity.createIntent(AuthUI.this.mApp.getApplicationContext(), getFlowParams());
        }
    }

    /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig */
    public static final class IdpConfig implements Parcelable {
        public static final Creator<IdpConfig> CREATOR = new Creator<IdpConfig>() {
            public IdpConfig createFromParcel(Parcel parcel) {
                return new IdpConfig(parcel);
            }

            public IdpConfig[] newArray(int i) {
                return new IdpConfig[i];
            }
        };
        private final Bundle mParams;
        private final String mProviderId;

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$AnonymousBuilder */
        public static final class AnonymousBuilder extends Builder {
            public AnonymousBuilder() {
                super(AuthUI.ANONYMOUS_PROVIDER);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$Builder */
        public static class Builder {
            private final Bundle mParams = new Bundle();
            /* access modifiers changed from: private */
            public String mProviderId;

            protected Builder(String str) {
                if (AuthUI.SUPPORTED_PROVIDERS.contains(str)) {
                    this.mProviderId = str;
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown provider: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }

            /* access modifiers changed from: protected */
            public final Bundle getParams() {
                return this.mParams;
            }

            /* access modifiers changed from: protected */
            public void setProviderId(String str) {
                this.mProviderId = str;
            }

            public IdpConfig build() {
                return new IdpConfig(this.mProviderId, this.mParams);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$EmailBuilder */
        public static final class EmailBuilder extends Builder {
            public EmailBuilder() {
                super("password");
            }

            public EmailBuilder setAllowNewAccounts(boolean z) {
                getParams().putBoolean(ExtraConstants.ALLOW_NEW_EMAILS, z);
                return this;
            }

            public EmailBuilder setRequireName(boolean z) {
                getParams().putBoolean(ExtraConstants.REQUIRE_NAME, z);
                return this;
            }

            public EmailBuilder enableEmailLinkSignIn() {
                setProviderId("emailLink");
                return this;
            }

            public EmailBuilder setActionCodeSettings(ActionCodeSettings actionCodeSettings) {
                getParams().putParcelable(ExtraConstants.ACTION_CODE_SETTINGS, actionCodeSettings);
                return this;
            }

            public EmailBuilder setForceSameDevice() {
                getParams().putBoolean(ExtraConstants.FORCE_SAME_DEVICE, true);
                return this;
            }

            public IdpConfig build() {
                if (this.mProviderId.equals("emailLink")) {
                    ActionCodeSettings actionCodeSettings = (ActionCodeSettings) getParams().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS);
                    Preconditions.checkNotNull(actionCodeSettings, "ActionCodeSettings cannot be null when using email link sign in.", new Object[0]);
                    if (!actionCodeSettings.canHandleCodeInApp()) {
                        throw new IllegalStateException("You must set canHandleCodeInApp in your ActionCodeSettings to true for Email-Link Sign-in.");
                    }
                }
                return super.build();
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$FacebookBuilder */
        public static final class FacebookBuilder extends Builder {
            private static final String TAG = "FacebookBuilder";

            public FacebookBuilder() {
                super("facebook.com");
                if (ProviderAvailability.IS_FACEBOOK_AVAILABLE) {
                    Preconditions.checkConfigured(AuthUI.getApplicationContext(), "Facebook provider unconfigured. Make sure to add a `facebook_application_id` string. See the docs for more info: https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#facebook", C1330R.C1335string.facebook_application_id);
                    if (AuthUI.getApplicationContext().getString(C1330R.C1335string.facebook_login_protocol_scheme).equals("fbYOUR_APP_ID")) {
                        Log.w(TAG, "Facebook provider unconfigured for Chrome Custom Tabs.");
                        return;
                    }
                    return;
                }
                throw new RuntimeException("Facebook provider cannot be configured without dependency. Did you forget to add 'com.facebook.android:facebook-login:VERSION' dependency?");
            }

            public FacebookBuilder setPermissions(List<String> list) {
                getParams().putStringArrayList(ExtraConstants.FACEBOOK_PERMISSIONS, new ArrayList(list));
                return this;
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$GitHubBuilder */
        public static final class GitHubBuilder extends Builder {
            public GitHubBuilder() {
                super("github.com");
                if (ProviderAvailability.IS_GITHUB_AVAILABLE) {
                    Preconditions.checkConfigured(AuthUI.getApplicationContext(), "GitHub provider unconfigured. Make sure to add your client id and secret. See the docs for more info: https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#github", C1330R.C1335string.firebase_web_host, C1330R.C1335string.github_client_id, C1330R.C1335string.github_client_secret);
                    return;
                }
                throw new RuntimeException("GitHub provider cannot be configured without dependency. Did you forget to add 'com.firebaseui:firebase-ui-auth-github:VERSION' dependency?");
            }

            public GitHubBuilder setPermissions(List<String> list) {
                getParams().putStringArrayList(ExtraConstants.GITHUB_PERMISSIONS, new ArrayList(list));
                return this;
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$GoogleBuilder */
        public static final class GoogleBuilder extends Builder {
            public GoogleBuilder() {
                super("google.com");
                Preconditions.checkConfigured(AuthUI.getApplicationContext(), "Check your google-services plugin configuration, the default_web_client_id string wasn't populated.", C1330R.C1335string.default_web_client_id);
            }

            public GoogleBuilder setScopes(List<String> list) {
                com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder builder = new com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);
                for (String scope : list) {
                    builder.requestScopes(new Scope(scope), new Scope[0]);
                }
                return setSignInOptions(builder.build());
            }

            public GoogleBuilder setSignInOptions(GoogleSignInOptions googleSignInOptions) {
                Bundle params = getParams();
                String str = ExtraConstants.GOOGLE_SIGN_IN_OPTIONS;
                Preconditions.checkUnset(params, "Cannot overwrite previously set sign-in options.", str);
                com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder builder = new com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(googleSignInOptions);
                builder.requestEmail().requestIdToken(AuthUI.getApplicationContext().getString(C1330R.C1335string.default_web_client_id));
                getParams().putParcelable(str, builder.build());
                return this;
            }

            public IdpConfig build() {
                if (!getParams().containsKey(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS)) {
                    setScopes(Collections.emptyList());
                }
                return super.build();
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$PhoneBuilder */
        public static final class PhoneBuilder extends Builder {
            public PhoneBuilder() {
                super("phone");
            }

            public PhoneBuilder setDefaultNumber(String str) {
                Bundle params = getParams();
                String str2 = ExtraConstants.PHONE;
                Preconditions.checkUnset(params, "Cannot overwrite previously set phone number", str2, ExtraConstants.COUNTRY_ISO, ExtraConstants.NATIONAL_NUMBER);
                if (PhoneNumberUtils.isValid(str)) {
                    getParams().putString(str2, str);
                    return this;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid phone number: ");
                sb.append(str);
                throw new IllegalStateException(sb.toString());
            }

            public PhoneBuilder setDefaultNumber(String str, String str2) {
                Bundle params = getParams();
                String str3 = ExtraConstants.COUNTRY_ISO;
                String str4 = ExtraConstants.NATIONAL_NUMBER;
                Preconditions.checkUnset(params, "Cannot overwrite previously set phone number", ExtraConstants.PHONE, str3, str4);
                if (PhoneNumberUtils.isValidIso(str)) {
                    getParams().putString(str3, str);
                    getParams().putString(str4, str2);
                    return this;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid country iso: ");
                sb.append(str);
                throw new IllegalStateException(sb.toString());
            }

            public PhoneBuilder setDefaultCountryIso(String str) {
                Bundle params = getParams();
                String str2 = ExtraConstants.COUNTRY_ISO;
                Preconditions.checkUnset(params, "Cannot overwrite previously set phone number", ExtraConstants.PHONE, str2, ExtraConstants.NATIONAL_NUMBER);
                if (PhoneNumberUtils.isValidIso(str)) {
                    getParams().putString(str2, str.toUpperCase(Locale.getDefault()));
                    return this;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid country iso: ");
                sb.append(str);
                throw new IllegalStateException(sb.toString());
            }

            public PhoneBuilder setWhitelistedCountries(List<String> list) {
                if (!getParams().containsKey(ExtraConstants.BLACKLISTED_COUNTRIES)) {
                    String str = "Invalid argument: Only non-%s whitelists are valid. To specify no whitelist, do not call this method.";
                    Preconditions.checkNotNull(list, String.format(str, new Object[]{"null"}), new Object[0]);
                    Preconditions.checkArgument(!list.isEmpty(), String.format(str, new Object[]{"empty"}));
                    addCountriesToBundle(list, ExtraConstants.WHITELISTED_COUNTRIES);
                    return this;
                }
                throw new IllegalStateException("You can either whitelist or blacklist country codes for phone authentication.");
            }

            public PhoneBuilder setBlacklistedCountries(List<String> list) {
                if (!getParams().containsKey(ExtraConstants.WHITELISTED_COUNTRIES)) {
                    String str = "Invalid argument: Only non-%s blacklists are valid. To specify no blacklist, do not call this method.";
                    Preconditions.checkNotNull(list, String.format(str, new Object[]{"null"}), new Object[0]);
                    Preconditions.checkArgument(!list.isEmpty(), String.format(str, new Object[]{"empty"}));
                    addCountriesToBundle(list, ExtraConstants.BLACKLISTED_COUNTRIES);
                    return this;
                }
                throw new IllegalStateException("You can either whitelist or blacklist country codes for phone authentication.");
            }

            public IdpConfig build() {
                validateInputs();
                return super.build();
            }

            private void addCountriesToBundle(List<String> list, String str) {
                ArrayList arrayList = new ArrayList();
                for (String upperCase : list) {
                    arrayList.add(upperCase.toUpperCase(Locale.getDefault()));
                }
                getParams().putStringArrayList(str, arrayList);
            }

            private void validateInputs() {
                ArrayList stringArrayList = getParams().getStringArrayList(ExtraConstants.WHITELISTED_COUNTRIES);
                ArrayList stringArrayList2 = getParams().getStringArrayList(ExtraConstants.BLACKLISTED_COUNTRIES);
                if (stringArrayList != null && stringArrayList2 != null) {
                    throw new IllegalStateException("You can either whitelist or blacklist country codes for phone authentication.");
                } else if (stringArrayList != null) {
                    validateInputs(stringArrayList, true);
                } else if (stringArrayList2 != null) {
                    validateInputs(stringArrayList2, false);
                }
            }

            private void validateInputs(List<String> list, boolean z) {
                validateCountryInput(list);
                validateDefaultCountryInput(list, z);
            }

            private void validateCountryInput(List<String> list) {
                for (String str : list) {
                    if (!PhoneNumberUtils.isValidIso(str) && !PhoneNumberUtils.isValid(str)) {
                        throw new IllegalArgumentException("Invalid input: You must provide a valid country iso (alpha-2) or code (e-164). e.g. 'us' or '+1'.");
                    }
                }
            }

            private void validateDefaultCountryInput(List<String> list, boolean z) {
                if (!getParams().containsKey(ExtraConstants.COUNTRY_ISO) && !getParams().containsKey(ExtraConstants.PHONE)) {
                    return;
                }
                if (!validateDefaultCountryIso(list, z) || !validateDefaultPhoneIsos(list, z)) {
                    throw new IllegalArgumentException("Invalid default country iso. Make sure it is either part of the whitelisted list or that you haven't blacklisted it.");
                }
            }

            private boolean validateDefaultCountryIso(List<String> list, boolean z) {
                return isValidDefaultIso(list, getDefaultIso(), z);
            }

            private boolean validateDefaultPhoneIsos(List<String> list, boolean z) {
                List<String> phoneIsosFromCode = getPhoneIsosFromCode();
                for (String isValidDefaultIso : phoneIsosFromCode) {
                    if (isValidDefaultIso(list, isValidDefaultIso, z)) {
                        return true;
                    }
                }
                return phoneIsosFromCode.isEmpty();
            }

            private boolean isValidDefaultIso(List<String> list, String str, boolean z) {
                boolean z2 = true;
                if (str == null) {
                    return true;
                }
                boolean containsCountryIso = containsCountryIso(list, str);
                if ((!containsCountryIso || !z) && (containsCountryIso || z)) {
                    z2 = false;
                }
                return z2;
            }

            private boolean containsCountryIso(List<String> list, String str) {
                String upperCase = str.toUpperCase(Locale.getDefault());
                for (String str2 : list) {
                    if (PhoneNumberUtils.isValidIso(str2)) {
                        if (str2.equals(upperCase)) {
                            return true;
                        }
                    } else if (PhoneNumberUtils.getCountryIsosFromCountryCode(str2).contains(upperCase)) {
                        return true;
                    }
                }
                return false;
            }

            private List<String> getPhoneIsosFromCode() {
                ArrayList arrayList = new ArrayList();
                String string = getParams().getString(ExtraConstants.PHONE);
                if (string != null) {
                    String str = Marker.ANY_NON_NULL_MARKER;
                    if (string.startsWith(str)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(PhoneNumberUtils.getPhoneNumber(string).getCountryCode());
                        List countryIsosFromCountryCode = PhoneNumberUtils.getCountryIsosFromCountryCode(sb.toString());
                        if (countryIsosFromCountryCode != null) {
                            arrayList.addAll(countryIsosFromCountryCode);
                        }
                    }
                }
                return arrayList;
            }

            private String getDefaultIso() {
                Bundle params = getParams();
                String str = ExtraConstants.COUNTRY_ISO;
                if (params.containsKey(str)) {
                    return getParams().getString(str);
                }
                return null;
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$TwitterBuilder */
        public static final class TwitterBuilder extends Builder {
            public TwitterBuilder() {
                super("twitter.com");
                if (ProviderAvailability.IS_TWITTER_AVAILABLE) {
                    Preconditions.checkConfigured(AuthUI.getApplicationContext(), "Twitter provider unconfigured. Make sure to add your key and secret. See the docs for more info: https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#twitter", C1330R.C1335string.twitter_consumer_key, C1330R.C1335string.twitter_consumer_secret);
                    return;
                }
                throw new RuntimeException("Twitter provider cannot be configured without dependency. Did you forget to add 'com.twitter.sdk.android:twitter-core:VERSION' dependency?");
            }
        }

        public int describeContents() {
            return 0;
        }

        private IdpConfig(String str, Bundle bundle) {
            this.mProviderId = str;
            this.mParams = new Bundle(bundle);
        }

        private IdpConfig(Parcel parcel) {
            this.mProviderId = parcel.readString();
            this.mParams = parcel.readBundle(getClass().getClassLoader());
        }

        public String getProviderId() {
            return this.mProviderId;
        }

        public Bundle getParams() {
            return new Bundle(this.mParams);
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mProviderId);
            parcel.writeBundle(this.mParams);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mProviderId.equals(((IdpConfig) obj).mProviderId);
        }

        public final int hashCode() {
            return this.mProviderId.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("IdpConfig{mProviderId='");
            sb.append(this.mProviderId);
            sb.append('\'');
            sb.append(", mParams=");
            sb.append(this.mParams);
            sb.append('}');
            return sb.toString();
        }
    }

    /* renamed from: com.firebase.ui.auth.AuthUI$SignInIntentBuilder */
    public final class SignInIntentBuilder extends AuthIntentBuilder<SignInIntentBuilder> {
        private String mEmailLink;
        private boolean mEnableAnonymousUpgrade;

        public /* bridge */ /* synthetic */ Intent build() {
            return super.build();
        }

        private SignInIntentBuilder() {
            super();
        }

        public SignInIntentBuilder setEmailLink(String str) {
            this.mEmailLink = str;
            return this;
        }

        public SignInIntentBuilder enableAnonymousUsersAutoUpgrade() {
            this.mEnableAnonymousUpgrade = true;
            validateEmailBuilderConfig();
            return this;
        }

        private void validateEmailBuilderConfig() {
            int i = 0;
            while (i < this.mProviders.size()) {
                IdpConfig idpConfig = (IdpConfig) this.mProviders.get(i);
                if (!idpConfig.getProviderId().equals("emailLink") || idpConfig.getParams().getBoolean(ExtraConstants.FORCE_SAME_DEVICE, true)) {
                    i++;
                } else {
                    throw new IllegalStateException("You must force the same device flow when using email link sign in with anonymous user upgrade");
                }
            }
        }

        /* access modifiers changed from: protected */
        public FlowParameters getFlowParams() {
            FlowParameters flowParameters = new FlowParameters(AuthUI.this.mApp.getName(), this.mProviders, this.mTheme, this.mLogo, this.mTosUrl, this.mPrivacyPolicyUrl, this.mEnableCredentials, this.mEnableHints, this.mEnableAnonymousUpgrade, this.mAlwaysShowProviderChoice, this.mEmailLink, this.mAuthMethodPickerLayout);
            return flowParameters;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.firebase.ui.auth.AuthUI$SupportedProvider */
    public @interface SupportedProvider {
    }

    static {
        String str = "google.com";
        String str2 = "facebook.com";
        String str3 = "twitter.com";
        String str4 = "github.com";
        SUPPORTED_PROVIDERS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{str, str2, str3, str4, "password", "phone", ANONYMOUS_PROVIDER, "emailLink"})));
        SOCIAL_PROVIDERS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{str, str2, str3, str4})));
    }

    private AuthUI(FirebaseApp firebaseApp) {
        this.mApp = firebaseApp;
        try {
            this.mAuth.setFirebaseUIVersion("5.0.0");
        } catch (Exception e) {
            Log.e(TAG, "Couldn't set the FUI version.", e);
        }
        this.mAuth.useAppLanguage();
    }

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static void setApplicationContext(Context context) {
        sApplicationContext = ((Context) Preconditions.checkNotNull(context, "App context cannot be null.", new Object[0])).getApplicationContext();
    }

    public static AuthUI getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    public static AuthUI getInstance(FirebaseApp firebaseApp) {
        AuthUI authUI;
        synchronized (INSTANCES) {
            authUI = (AuthUI) INSTANCES.get(firebaseApp);
            if (authUI == null) {
                authUI = new AuthUI(firebaseApp);
                INSTANCES.put(firebaseApp, authUI);
            }
        }
        return authUI;
    }

    public static boolean canHandleIntent(Intent intent) {
        if (intent == null || intent.getData() == null) {
            return false;
        }
        return FirebaseAuth.getInstance().isSignInWithEmailLink(intent.getData().toString());
    }

    public static int getDefaultTheme() {
        return C1330R.C1336style.FirebaseUI;
    }

    private static List<Credential> getCredentialsFromFirebaseUser(FirebaseUser firebaseUser) {
        if (TextUtils.isEmpty(firebaseUser.getEmail()) && TextUtils.isEmpty(firebaseUser.getPhoneNumber())) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (UserInfo userInfo : firebaseUser.getProviderData()) {
            if (!FirebaseAuthProvider.PROVIDER_ID.equals(userInfo.getProviderId())) {
                String providerIdToAccountType = ProviderUtils.providerIdToAccountType(userInfo.getProviderId());
                if (providerIdToAccountType == null) {
                    arrayList.add(CredentialUtils.buildCredentialOrThrow(firebaseUser, "pass", null));
                } else {
                    arrayList.add(CredentialUtils.buildCredentialOrThrow(firebaseUser, null, providerIdToAccountType));
                }
            }
        }
        return arrayList;
    }

    public Task<AuthResult> silentSignIn(Context context, List<IdpConfig> list) {
        final GoogleSignInOptions googleSignInOptions;
        if (this.mAuth.getCurrentUser() == null) {
            final Context applicationContext = context.getApplicationContext();
            String str = "google.com";
            IdpConfig configFromIdps = ProviderUtils.getConfigFromIdps(list, str);
            IdpConfig configFromIdps2 = ProviderUtils.getConfigFromIdps(list, "password");
            if (configFromIdps == null && configFromIdps2 == null) {
                throw new IllegalArgumentException("No supported providers were supplied. Add either Google or email support.");
            }
            String str2 = null;
            if (configFromIdps == null) {
                googleSignInOptions = null;
            } else {
                GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(applicationContext);
                if (lastSignedInAccount != null && lastSignedInAccount.getIdToken() != null) {
                    return this.mAuth.signInWithCredential(GoogleAuthProvider.getCredential(lastSignedInAccount.getIdToken(), null));
                }
                googleSignInOptions = (GoogleSignInOptions) configFromIdps.getParams().getParcelable(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS);
            }
            CredentialsClient credentialsClient = GoogleApiUtils.getCredentialsClient(context);
            Builder passwordLoginSupported = new Builder().setPasswordLoginSupported(configFromIdps2 != null);
            String[] strArr = new String[1];
            if (configFromIdps != null) {
                str2 = ProviderUtils.providerIdToAccountType(str);
            }
            strArr[0] = str2;
            return credentialsClient.request(passwordLoginSupported.setAccountTypes(strArr).build()).continueWithTask(new Continuation<CredentialRequestResponse, Task<AuthResult>>() {
                public Task<AuthResult> then(Task<CredentialRequestResponse> task) {
                    Credential credential = ((CredentialRequestResponse) task.getResult()).getCredential();
                    String id = credential.getId();
                    String password = credential.getPassword();
                    if (TextUtils.isEmpty(password)) {
                        return GoogleSignIn.getClient(applicationContext, new GoogleSignInOptions.Builder(googleSignInOptions).setAccountName(id).build()).silentSignIn().continueWithTask(new Continuation<GoogleSignInAccount, Task<AuthResult>>() {
                            public Task<AuthResult> then(Task<GoogleSignInAccount> task) {
                                return AuthUI.this.mAuth.signInWithCredential(GoogleAuthProvider.getCredential(((GoogleSignInAccount) task.getResult()).getIdToken(), null));
                            }
                        });
                    }
                    return AuthUI.this.mAuth.signInWithEmailAndPassword(id, password);
                }
            });
        }
        throw new IllegalArgumentException("User already signed in!");
    }

    public Task<Void> signOut(Context context) {
        return Tasks.whenAll((Task<?>[]) new Task[]{signOutIdps(context), GoogleApiUtils.getCredentialsClient(context).disableAutoSignIn().continueWith(new Continuation<Void, Void>() {
            public Void then(Task<Void> task) {
                Exception exception = task.getException();
                if (!(exception instanceof ApiException) || ((ApiException) exception).getStatusCode() != 16) {
                    return (Void) task.getResult();
                }
                Log.w(AuthUI.TAG, "Could not disable auto-sign in, maybe there are no SmartLock accounts available?", exception);
                return null;
            }
        })}).continueWith(new Continuation<Void, Void>() {
            public Void then(Task<Void> task) {
                task.getResult();
                AuthUI.this.mAuth.signOut();
                return null;
            }
        });
    }

    public Task<Void> delete(Context context) {
        final FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser == null) {
            return Tasks.forException(new FirebaseAuthInvalidUserException(String.valueOf(4), "No currently signed in user."));
        }
        final List credentialsFromFirebaseUser = getCredentialsFromFirebaseUser(currentUser);
        final CredentialsClient credentialsClient = GoogleApiUtils.getCredentialsClient(context);
        return signOutIdps(context).continueWithTask(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(Task<Void> task) {
                task.getResult();
                ArrayList arrayList = new ArrayList();
                for (Credential delete : credentialsFromFirebaseUser) {
                    arrayList.add(credentialsClient.delete(delete));
                }
                return Tasks.whenAll((Collection<? extends Task<?>>) arrayList).continueWith(new Continuation<Void, Void>() {
                    public Void then(Task<Void> task) {
                        Throwable th;
                        Exception exception = task.getException();
                        if (exception == null) {
                            th = null;
                        } else {
                            th = exception.getCause();
                        }
                        if (!(th instanceof ApiException) || ((ApiException) th).getStatusCode() != 16) {
                            return (Void) task.getResult();
                        }
                        return null;
                    }
                });
            }
        }).continueWithTask(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(Task<Void> task) {
                task.getResult();
                return currentUser.delete();
            }
        });
    }

    private Task<Void> signOutIdps(Context context) {
        if (ProviderAvailability.IS_FACEBOOK_AVAILABLE) {
            LoginManager.getInstance().logOut();
        }
        if (ProviderAvailability.IS_TWITTER_AVAILABLE) {
            TwitterSignInHandler.initializeTwitter();
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
        }
        return GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
    }

    public SignInIntentBuilder createSignInIntentBuilder() {
        return new SignInIntentBuilder();
    }
}
