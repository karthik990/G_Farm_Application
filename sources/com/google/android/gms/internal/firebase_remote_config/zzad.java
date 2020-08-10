package com.google.android.gms.internal.firebase_remote_config;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public final class zzad {
    private final int statusCode;
    private final zzz zzag;
    private int zzbe;
    private boolean zzbf;
    private InputStream zzbr;
    private final String zzbs;
    private final String zzbt;
    private zzaj zzbu;
    private final String zzbv;
    private final zzaa zzbw;
    private boolean zzbx;

    zzad(zzaa zzaa, zzaj zzaj) throws IOException {
        StringBuilder sb;
        this.zzbw = zzaa;
        this.zzbe = zzaa.zzw();
        this.zzbf = zzaa.zzx();
        this.zzbu = zzaj;
        this.zzbs = zzaj.getContentEncoding();
        int statusCode2 = zzaj.getStatusCode();
        boolean z = false;
        if (statusCode2 < 0) {
            statusCode2 = 0;
        }
        this.statusCode = statusCode2;
        String reasonPhrase = zzaj.getReasonPhrase();
        this.zzbv = reasonPhrase;
        Logger logger = zzah.zzbz;
        if (this.zzbf && logger.isLoggable(Level.CONFIG)) {
            z = true;
        }
        zzz zzz = null;
        if (z) {
            sb = new StringBuilder();
            sb.append("-------------- RESPONSE --------------");
            sb.append(zzcm.zzgh);
            String zzaj2 = zzaj.zzaj();
            if (zzaj2 != null) {
                sb.append(zzaj2);
            } else {
                sb.append(this.statusCode);
                if (reasonPhrase != null) {
                    sb.append(' ');
                    sb.append(reasonPhrase);
                }
            }
            sb.append(zzcm.zzgh);
        } else {
            sb = null;
        }
        zzaa.zzz().zza(zzaj, z ? sb : null);
        String contentType = zzaj.getContentType();
        if (contentType == null) {
            contentType = zzaa.zzz().getContentType();
        }
        this.zzbt = contentType;
        if (contentType != null) {
            zzz = new zzz(contentType);
        }
        this.zzag = zzz;
        if (z) {
            logger.logp(Level.CONFIG, "com.google.api.client.http.HttpResponse", "<init>", sb.toString());
        }
    }

    public final String getContentType() {
        return this.zzbt;
    }

    public final zzx zzy() {
        return this.zzbw.zzz();
    }

    public final boolean zzae() {
        int i = this.statusCode;
        return i >= 200 && i < 300;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final String getStatusMessage() {
        return this.zzbv;
    }

    public final InputStream getContent() throws IOException {
        if (!this.zzbx) {
            InputStream content = this.zzbu.getContent();
            if (content != null) {
                try {
                    String str = this.zzbs;
                    if (str != null && str.contains(HttpRequest.ENCODING_GZIP)) {
                        content = new GZIPInputStream(content);
                    }
                    Logger logger = zzah.zzbz;
                    if (this.zzbf && logger.isLoggable(Level.CONFIG)) {
                        content = new zzcd(content, logger, Level.CONFIG, this.zzbe);
                    }
                    this.zzbr = content;
                } catch (EOFException unused) {
                    content.close();
                } catch (Throwable th) {
                    content.close();
                    throw th;
                }
            }
            this.zzbx = true;
        }
        return this.zzbr;
    }

    public final void ignore() throws IOException {
        InputStream content = getContent();
        if (content != null) {
            content.close();
        }
    }

    public final void disconnect() throws IOException {
        ignore();
        this.zzbu.disconnect();
    }

    public final <T> T zza(Class<T> cls) throws IOException {
        int i = this.statusCode;
        boolean z = true;
        if (this.zzbw.getRequestMethod().equals("HEAD") || i / 100 == 1 || i == 204 || i == 304) {
            ignore();
            z = false;
        }
        if (!z) {
            return null;
        }
        return this.zzbw.zzab().zza(getContent(), zzag(), cls);
    }

    /* JADX INFO: finally extract failed */
    public final String zzaf() throws IOException {
        InputStream content = getContent();
        if (content == null) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            zzds.checkNotNull(content);
            zzds.checkNotNull(byteArrayOutputStream);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = content.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    content.close();
                    return byteArrayOutputStream.toString(zzag().name());
                }
            }
        } catch (Throwable th) {
            content.close();
            throw th;
        }
    }

    private final Charset zzag() {
        zzz zzz = this.zzag;
        return (zzz == null || zzz.zzs() == null) ? zzbp.ISO_8859_1 : this.zzag.zzs();
    }
}
