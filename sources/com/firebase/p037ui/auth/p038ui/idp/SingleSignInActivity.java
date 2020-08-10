package com.firebase.p037ui.auth.p038ui.idp;

import android.content.Context;
import android.content.Intent;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.p038ui.InvisibleActivityBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p037ui.auth.viewmodel.idp.SocialProviderResponseHandler;

/* renamed from: com.firebase.ui.auth.ui.idp.SingleSignInActivity */
public class SingleSignInActivity extends InvisibleActivityBase {
    /* access modifiers changed from: private */
    public SocialProviderResponseHandler mHandler;
    private ProviderSignInBase<?> mProvider;

    public static Intent createIntent(Context context, FlowParameters flowParameters, User user) {
        return createBaseIntent(context, SingleSignInActivity.class, flowParameters).putExtra(ExtraConstants.USER, user);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0077, code lost:
        if (r0.equals("google.com") != false) goto L_0x0086;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r10) {
        /*
            r9 = this;
            super.onCreate(r10)
            android.content.Intent r10 = r9.getIntent()
            com.firebase.ui.auth.data.model.User r10 = com.firebase.p037ui.auth.data.model.User.getUser(r10)
            java.lang.String r0 = r10.getProviderId()
            com.firebase.ui.auth.data.model.FlowParameters r1 = r9.getFlowParams()
            java.util.List<com.firebase.ui.auth.AuthUI$IdpConfig> r1 = r1.providers
            com.firebase.ui.auth.AuthUI$IdpConfig r1 = com.firebase.p037ui.auth.util.data.ProviderUtils.getConfigFromIdps(r1, r0)
            r2 = 0
            r3 = 3
            if (r1 != 0) goto L_0x003b
            com.firebase.ui.auth.FirebaseUiException r10 = new com.firebase.ui.auth.FirebaseUiException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "Provider not enabled: "
            r1.append(r4)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r10.<init>(r3, r0)
            android.content.Intent r10 = com.firebase.p037ui.auth.IdpResponse.getErrorIntent(r10)
            r9.finish(r2, r10)
            return
        L_0x003b:
            androidx.lifecycle.ViewModelProvider r4 = androidx.lifecycle.ViewModelProviders.m78of(r9)
            java.lang.Class<com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler> r5 = com.firebase.p037ui.auth.viewmodel.idp.SocialProviderResponseHandler.class
            androidx.lifecycle.ViewModel r5 = r4.get(r5)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r5 = (com.firebase.p037ui.auth.viewmodel.idp.SocialProviderResponseHandler) r5
            r9.mHandler = r5
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r5 = r9.mHandler
            com.firebase.ui.auth.data.model.FlowParameters r6 = r9.getFlowParams()
            r5.init(r6)
            r5 = -1
            int r6 = r0.hashCode()
            r7 = 2
            r8 = 1
            switch(r6) {
                case -1830313082: goto L_0x007a;
                case -1536293812: goto L_0x0071;
                case -364826023: goto L_0x0067;
                case 1985010934: goto L_0x005d;
                default: goto L_0x005c;
            }
        L_0x005c:
            goto L_0x0085
        L_0x005d:
            java.lang.String r2 = "github.com"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0085
            r2 = 3
            goto L_0x0086
        L_0x0067:
            java.lang.String r2 = "facebook.com"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0085
            r2 = 1
            goto L_0x0086
        L_0x0071:
            java.lang.String r6 = "google.com"
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0085
            goto L_0x0086
        L_0x007a:
            java.lang.String r2 = "twitter.com"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0085
            r2 = 2
            goto L_0x0086
        L_0x0085:
            r2 = -1
        L_0x0086:
            if (r2 == 0) goto L_0x00d0
            if (r2 == r8) goto L_0x00c2
            if (r2 == r7) goto L_0x00b3
            if (r2 != r3) goto L_0x009c
            java.lang.Class<com.firebase.ui.auth.viewmodel.ProviderSignInBase<com.firebase.ui.auth.AuthUI$IdpConfig>> r10 = com.firebase.p037ui.auth.data.remote.GitHubSignInHandlerBridge.HANDLER_CLASS
            androidx.lifecycle.ViewModel r10 = r4.get(r10)
            com.firebase.ui.auth.viewmodel.ProviderSignInBase r10 = (com.firebase.p037ui.auth.viewmodel.ProviderSignInBase) r10
            r10.init(r1)
            r9.mProvider = r10
            goto L_0x00e6
        L_0x009c:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid provider id: "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r10.<init>(r0)
            throw r10
        L_0x00b3:
            java.lang.Class<com.firebase.ui.auth.data.remote.TwitterSignInHandler> r10 = com.firebase.p037ui.auth.data.remote.TwitterSignInHandler.class
            androidx.lifecycle.ViewModel r10 = r4.get(r10)
            com.firebase.ui.auth.data.remote.TwitterSignInHandler r10 = (com.firebase.p037ui.auth.data.remote.TwitterSignInHandler) r10
            r0 = 0
            r10.init(r0)
            r9.mProvider = r10
            goto L_0x00e6
        L_0x00c2:
            java.lang.Class<com.firebase.ui.auth.data.remote.FacebookSignInHandler> r10 = com.firebase.p037ui.auth.data.remote.FacebookSignInHandler.class
            androidx.lifecycle.ViewModel r10 = r4.get(r10)
            com.firebase.ui.auth.data.remote.FacebookSignInHandler r10 = (com.firebase.p037ui.auth.data.remote.FacebookSignInHandler) r10
            r10.init(r1)
            r9.mProvider = r10
            goto L_0x00e6
        L_0x00d0:
            java.lang.Class<com.firebase.ui.auth.data.remote.GoogleSignInHandler> r0 = com.firebase.p037ui.auth.data.remote.GoogleSignInHandler.class
            androidx.lifecycle.ViewModel r0 = r4.get(r0)
            com.firebase.ui.auth.data.remote.GoogleSignInHandler r0 = (com.firebase.p037ui.auth.data.remote.GoogleSignInHandler) r0
            com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params r2 = new com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params
            java.lang.String r10 = r10.getEmail()
            r2.<init>(r1, r10)
            r0.init(r2)
            r9.mProvider = r0
        L_0x00e6:
            com.firebase.ui.auth.viewmodel.ProviderSignInBase<?> r10 = r9.mProvider
            androidx.lifecycle.LiveData r10 = r10.getOperation()
            com.firebase.ui.auth.ui.idp.SingleSignInActivity$1 r0 = new com.firebase.ui.auth.ui.idp.SingleSignInActivity$1
            r0.<init>(r9)
            r10.observe(r9, r0)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r10 = r9.mHandler
            androidx.lifecycle.LiveData r10 = r10.getOperation()
            com.firebase.ui.auth.ui.idp.SingleSignInActivity$2 r0 = new com.firebase.ui.auth.ui.idp.SingleSignInActivity$2
            r0.<init>(r9)
            r10.observe(r9, r0)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r10 = r9.mHandler
            androidx.lifecycle.LiveData r10 = r10.getOperation()
            java.lang.Object r10 = r10.getValue()
            if (r10 != 0) goto L_0x0113
            com.firebase.ui.auth.viewmodel.ProviderSignInBase<?> r10 = r9.mProvider
            r10.startSignIn(r9)
        L_0x0113:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.p038ui.idp.SingleSignInActivity.onCreate(android.os.Bundle):void");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mHandler.onActivityResult(i, i2, intent);
        this.mProvider.onActivityResult(i, i2, intent);
    }
}
