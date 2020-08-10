package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.Collection;

public class GoogleAccountCredential implements HttpRequestInitializer {
    private final GoogleAccountManager accountManager;
    private String accountName;
    private BackOff backOff;
    final Context context;
    final String scope;
    private Account selectedAccount;
    private Sleeper sleeper = Sleeper.DEFAULT;

    class RequestHandler implements HttpExecuteInterceptor, HttpUnsuccessfulResponseHandler {
        boolean received401;
        String token;

        RequestHandler() {
        }

        public void intercept(HttpRequest httpRequest) throws IOException {
            try {
                this.token = GoogleAccountCredential.this.getToken();
                HttpHeaders headers = httpRequest.getHeaders();
                StringBuilder sb = new StringBuilder();
                sb.append("Bearer ");
                sb.append(this.token);
                headers.setAuthorization(sb.toString());
            } catch (GooglePlayServicesAvailabilityException e) {
                throw new GooglePlayServicesAvailabilityIOException(e);
            } catch (UserRecoverableAuthException e2) {
                throw new UserRecoverableAuthIOException(e2);
            } catch (GoogleAuthException e3) {
                throw new GoogleAuthIOException(e3);
            }
        }

        public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean z) throws IOException {
            try {
                if (httpResponse.getStatusCode() != 401 || this.received401) {
                    return false;
                }
                this.received401 = true;
                GoogleAuthUtil.clearToken(GoogleAccountCredential.this.context, this.token);
                return true;
            } catch (GoogleAuthException e) {
                throw new GoogleAuthIOException(e);
            }
        }
    }

    public GoogleAccountCredential(Context context2, String str) {
        this.accountManager = new GoogleAccountManager(context2);
        this.context = context2;
        this.scope = str;
    }

    public static GoogleAccountCredential usingOAuth2(Context context2, Collection<String> collection) {
        Preconditions.checkArgument(collection != null && collection.iterator().hasNext());
        StringBuilder sb = new StringBuilder();
        sb.append("oauth2: ");
        sb.append(Joiner.m1763on(' ').join(collection));
        return new GoogleAccountCredential(context2, sb.toString());
    }

    public static GoogleAccountCredential usingAudience(Context context2, String str) {
        Preconditions.checkArgument(str.length() != 0);
        StringBuilder sb = new StringBuilder();
        sb.append("audience:");
        sb.append(str);
        return new GoogleAccountCredential(context2, sb.toString());
    }

    public final GoogleAccountCredential setSelectedAccountName(String str) {
        this.selectedAccount = this.accountManager.getAccountByName(str);
        if (this.selectedAccount == null) {
            str = null;
        }
        this.accountName = str;
        return this;
    }

    public final GoogleAccountCredential setSelectedAccount(Account account) {
        String str;
        this.selectedAccount = account;
        if (account == null) {
            str = null;
        } else {
            str = account.name;
        }
        this.accountName = str;
        return this;
    }

    public void initialize(HttpRequest httpRequest) {
        RequestHandler requestHandler = new RequestHandler();
        httpRequest.setInterceptor(requestHandler);
        httpRequest.setUnsuccessfulResponseHandler(requestHandler);
    }

    public final Context getContext() {
        return this.context;
    }

    public final String getScope() {
        return this.scope;
    }

    public final GoogleAccountManager getGoogleAccountManager() {
        return this.accountManager;
    }

    public final Account[] getAllAccounts() {
        return this.accountManager.getAccounts();
    }

    public final Account getSelectedAccount() {
        return this.selectedAccount;
    }

    public BackOff getBackOff() {
        return this.backOff;
    }

    public GoogleAccountCredential setBackOff(BackOff backOff2) {
        this.backOff = backOff2;
        return this;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    public final GoogleAccountCredential setSleeper(Sleeper sleeper2) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper2);
        return this;
    }

    public final String getSelectedAccountName() {
        return this.accountName;
    }

    public final Intent newChooseAccountIntent() {
        return AccountPicker.newChooseAccountIntent(this.selectedAccount, null, new String[]{"com.google"}, true, null, null, null, null);
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0007 */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0007 A[LOOP:0: B:3:0x0007->B:15:0x0007, LOOP_START, SYNTHETIC, Splitter:B:3:0x0007] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getToken() throws java.io.IOException, com.google.android.gms.auth.GoogleAuthException {
        /*
            r3 = this;
            com.google.api.client.util.BackOff r0 = r3.backOff
            if (r0 == 0) goto L_0x0007
            r0.reset()
        L_0x0007:
            android.content.Context r0 = r3.context     // Catch:{ IOException -> 0x0012 }
            java.lang.String r1 = r3.accountName     // Catch:{ IOException -> 0x0012 }
            java.lang.String r2 = r3.scope     // Catch:{ IOException -> 0x0012 }
            java.lang.String r0 = com.google.android.gms.auth.GoogleAuthUtil.getToken(r0, r1, r2)     // Catch:{ IOException -> 0x0012 }
            return r0
        L_0x0012:
            r0 = move-exception
            com.google.api.client.util.BackOff r1 = r3.backOff     // Catch:{ InterruptedException -> 0x0007 }
            if (r1 == 0) goto L_0x0022
            com.google.api.client.util.Sleeper r1 = r3.sleeper     // Catch:{ InterruptedException -> 0x0007 }
            com.google.api.client.util.BackOff r2 = r3.backOff     // Catch:{ InterruptedException -> 0x0007 }
            boolean r1 = com.google.api.client.util.BackOffUtils.next(r1, r2)     // Catch:{ InterruptedException -> 0x0007 }
            if (r1 == 0) goto L_0x0022
            goto L_0x0007
        L_0x0022:
            goto L_0x0024
        L_0x0023:
            throw r0
        L_0x0024:
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential.getToken():java.lang.String");
    }
}
