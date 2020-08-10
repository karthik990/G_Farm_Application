package com.google.android.gms.auth.api.signin.internal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

public class SignInHubActivity extends FragmentActivity {
    private static boolean zzbt = false;
    private boolean zzbu = false;
    private SignInConfiguration zzbv;
    private boolean zzbw;
    /* access modifiers changed from: private */
    public int zzbx;
    /* access modifiers changed from: private */
    public Intent zzby;

    private class zzc implements LoaderCallbacks<Void> {
        private zzc() {
        }

        public final void onLoaderReset(Loader<Void> loader) {
        }

        public final Loader<Void> onCreateLoader(int i, Bundle bundle) {
            return new zze(SignInHubActivity.this, GoogleApiClient.getAllClients());
        }

        public final /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            SignInHubActivity signInHubActivity = SignInHubActivity.this;
            signInHubActivity.setResult(signInHubActivity.zzbx, SignInHubActivity.this.zzby);
            SignInHubActivity.this.finish();
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        String action = intent.getAction();
        if ("com.google.android.gms.auth.NO_IMPL".equals(action)) {
            zzc((int) GoogleSignInStatusCodes.SIGN_IN_FAILED);
            return;
        }
        String str = "com.google.android.gms.auth.GOOGLE_SIGN_IN";
        String str2 = "AuthSignInClient";
        if (action.equals(str) || action.equals("com.google.android.gms.auth.APPAUTH_SIGN_IN")) {
            String str3 = "config";
            this.zzbv = (SignInConfiguration) intent.getBundleExtra(str3).getParcelable(str3);
            if (this.zzbv == null) {
                Log.e(str2, "Activity started with invalid configuration.");
                setResult(0);
                finish();
                return;
            }
            if (!(bundle == null)) {
                this.zzbw = bundle.getBoolean("signingInGoogleApiClients");
                if (this.zzbw) {
                    this.zzbx = bundle.getInt("signInResultCode");
                    this.zzby = (Intent) bundle.getParcelable("signInResultData");
                    zzn();
                }
            } else if (zzbt) {
                setResult(0);
                zzc((int) GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS);
            } else {
                zzbt = true;
                Intent intent2 = new Intent(action);
                if (action.equals(str)) {
                    intent2.setPackage("com.google.android.gms");
                } else {
                    intent2.setPackage(getPackageName());
                }
                intent2.putExtra(str3, this.zzbv);
                try {
                    startActivityForResult(intent2, 40962);
                } catch (ActivityNotFoundException unused) {
                    this.zzbu = true;
                    Log.w(str2, "Could not launch sign in Intent. Google Play Service is probably being updated...");
                    zzc(17);
                }
            }
        } else {
            String str4 = "Unknown action: ";
            String valueOf = String.valueOf(intent.getAction());
            Log.e(str2, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("signingInGoogleApiClients", this.zzbw);
        if (this.zzbw) {
            bundle.putInt("signInResultCode", this.zzbx);
            bundle.putParcelable("signInResultData", this.zzby);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!this.zzbu) {
            setResult(0);
            if (i == 40962) {
                if (intent != null) {
                    String str = GoogleSignInApi.EXTRA_SIGN_IN_ACCOUNT;
                    SignInAccount signInAccount = (SignInAccount) intent.getParcelableExtra(str);
                    if (signInAccount == null || signInAccount.getGoogleSignInAccount() == null) {
                        String str2 = "errorCode";
                        if (intent.hasExtra(str2)) {
                            int intExtra = intent.getIntExtra(str2, 8);
                            if (intExtra == 13) {
                                intExtra = GoogleSignInStatusCodes.SIGN_IN_CANCELLED;
                            }
                            zzc(intExtra);
                            return;
                        }
                    } else {
                        GoogleSignInAccount googleSignInAccount = signInAccount.getGoogleSignInAccount();
                        zzp.zzd(this).zzc(this.zzbv.zzm(), googleSignInAccount);
                        intent.removeExtra(str);
                        intent.putExtra("googleSignInAccount", googleSignInAccount);
                        this.zzbw = true;
                        this.zzbx = i2;
                        this.zzby = intent;
                        zzn();
                        return;
                    }
                }
                zzc(8);
            }
        }
    }

    private final void zzn() {
        getSupportLoaderManager().initLoader(0, null, new zzc());
        zzbt = false;
    }

    private final void zzc(int i) {
        Status status = new Status(i);
        Intent intent = new Intent();
        intent.putExtra("googleSignInStatus", status);
        setResult(0, intent);
        finish();
        zzbt = false;
    }
}
