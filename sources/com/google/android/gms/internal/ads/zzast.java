package com.google.android.gms.internal.ads;

import android.net.http.SslError;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import javax.annotation.ParametersAreNonnullByDefault;
import org.objectweb.asm.Opcodes;

@zzadh
@ParametersAreNonnullByDefault
final class zzast extends WebViewClient {
    private final zzasx zzdfc;
    private final zzatb zzdfd;
    private final zzasz zzdfe;
    private final zzata zzdff;
    private final zzatc zzdfg = new zzatc();

    zzast(zzasx zzasx, zzatb zzatb, zzasz zzasz, zzata zzata) {
        this.zzdfc = zzasx;
        this.zzdfd = zzatb;
        this.zzdfe = zzasz;
        this.zzdff = zzata;
    }

    private final boolean zzf(zzasu zzasu) {
        return this.zzdfc.zza(zzasu);
    }

    private final WebResourceResponse zzg(zzasu zzasu) {
        return this.zzdfd.zzd(zzasu);
    }

    public final void onLoadResource(WebView webView, String str) {
        if (str != null) {
            String str2 = "Loading resource: ";
            String valueOf = String.valueOf(str);
            zzakb.m1419v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            this.zzdfe.zzb(new zzasu(str));
        }
    }

    public final void onPageFinished(WebView webView, String str) {
        if (str != null) {
            this.zzdff.zzc(new zzasu(str));
        }
    }

    public final void onReceivedError(WebView webView, int i, String str, String str2) {
        this.zzdfg.zze(i, str2);
    }

    public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        this.zzdfg.zzb(sslError);
    }

    public final WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        if (webResourceRequest == null || webResourceRequest.getUrl() == null) {
            return null;
        }
        return zzg(new zzasu(webResourceRequest));
    }

    public final WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        if (str == null) {
            return null;
        }
        return zzg(new zzasu(str));
    }

    public final boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (!(keyCode == 79 || keyCode == 222)) {
            switch (keyCode) {
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                    break;
                default:
                    switch (keyCode) {
                        case Opcodes.IAND /*126*/:
                        case Opcodes.LAND /*127*/:
                        case 128:
                        case 129:
                        case 130:
                            break;
                        default:
                            return false;
                    }
            }
        }
        return true;
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        if (webResourceRequest == null || webResourceRequest.getUrl() == null) {
            return false;
        }
        return zzf(new zzasu(webResourceRequest));
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str == null) {
            return false;
        }
        return zzf(new zzasu(str));
    }
}
