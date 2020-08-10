package com.firebase.p037ui.auth.util.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.User;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.firebase.ui.auth.util.data.EmailLinkPersistenceManager */
public class EmailLinkPersistenceManager {
    private static final Set<String> KEYS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{KEY_EMAIL, KEY_PROVIDER, KEY_IDP_TOKEN, KEY_IDP_SECRET})));
    private static final String KEY_ANONYMOUS_USER_ID = "com.firebase.ui.auth.data.client.auid";
    private static final String KEY_EMAIL = "com.firebase.ui.auth.data.client.email";
    private static final String KEY_IDP_SECRET = "com.firebase.ui.auth.data.client.idpSecret";
    private static final String KEY_IDP_TOKEN = "com.firebase.ui.auth.data.client.idpToken";
    private static final String KEY_PROVIDER = "com.firebase.ui.auth.data.client.provider";
    private static final String KEY_SESSION_ID = "com.firebase.ui.auth.data.client.sid";
    private static final String SHARED_PREF_NAME = "com.firebase.ui.auth.util.data.EmailLinkPersistenceManager";
    private static final EmailLinkPersistenceManager instance = new EmailLinkPersistenceManager();

    /* renamed from: com.firebase.ui.auth.util.data.EmailLinkPersistenceManager$SessionRecord */
    public static class SessionRecord {
        private String mAnonymousUserId;
        private String mEmail;
        private IdpResponse mIdpResponseForLinking;
        private String mSessionId;

        public SessionRecord(String str, String str2) {
            Preconditions.checkNotNull(str);
            this.mSessionId = str;
            this.mAnonymousUserId = str2;
        }

        public String getSessionId() {
            return this.mSessionId;
        }

        public String getEmail() {
            return this.mEmail;
        }

        public SessionRecord setEmail(String str) {
            this.mEmail = str;
            return this;
        }

        public IdpResponse getIdpResponseForLinking() {
            return this.mIdpResponseForLinking;
        }

        public SessionRecord setIdpResponseForLinking(IdpResponse idpResponse) {
            this.mIdpResponseForLinking = idpResponse;
            return this;
        }

        public String getAnonymousUserId() {
            return this.mAnonymousUserId;
        }
    }

    public static EmailLinkPersistenceManager getInstance() {
        return instance;
    }

    public void saveEmail(Context context, String str, String str2, String str3) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(str);
        Editor edit = context.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
        edit.putString(KEY_EMAIL, str);
        edit.putString(KEY_ANONYMOUS_USER_ID, str3);
        edit.putString(KEY_SESSION_ID, str2);
        edit.apply();
    }

    public void saveIdpResponseForLinking(Context context, IdpResponse idpResponse) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(idpResponse);
        Editor edit = context.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
        edit.putString(KEY_EMAIL, idpResponse.getEmail());
        edit.putString(KEY_PROVIDER, idpResponse.getProviderType());
        edit.putString(KEY_IDP_TOKEN, idpResponse.getIdpToken());
        edit.putString(KEY_IDP_SECRET, idpResponse.getIdpSecret());
        edit.apply();
    }

    public SessionRecord retrieveSessionRecord(Context context) {
        Preconditions.checkNotNull(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SessionRecord sessionRecord = null;
        String string = sharedPreferences.getString(KEY_EMAIL, null);
        String string2 = sharedPreferences.getString(KEY_SESSION_ID, null);
        if (!(string == null || string2 == null)) {
            String string3 = sharedPreferences.getString(KEY_ANONYMOUS_USER_ID, null);
            String string4 = sharedPreferences.getString(KEY_PROVIDER, null);
            String string5 = sharedPreferences.getString(KEY_IDP_TOKEN, null);
            String string6 = sharedPreferences.getString(KEY_IDP_SECRET, null);
            sessionRecord = new SessionRecord(string2, string3).setEmail(string);
            if (!(string4 == null || string5 == null)) {
                sessionRecord.setIdpResponseForLinking(new Builder(new User.Builder(string4, string).build()).setToken(string5).setSecret(string6).setNewUser(false).build());
            }
        }
        return sessionRecord;
    }

    public void clearAllData(Context context) {
        Preconditions.checkNotNull(context);
        Editor edit = context.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
        for (String remove : KEYS) {
            edit.remove(remove);
        }
        edit.apply();
    }
}
