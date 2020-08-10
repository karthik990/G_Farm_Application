package com.flurry.sdk;

import com.flurry.sdk.C1655cc.C1656a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import javax.net.ssl.SSLException;

/* renamed from: com.flurry.sdk.cp */
public abstract class C1671cp implements Runnable {

    /* renamed from: a */
    protected String f840a;

    /* renamed from: b */
    protected C1655cc f841b;

    /* renamed from: c */
    protected String f842c;

    /* renamed from: d */
    protected String f843d;

    /* renamed from: e */
    protected String f844e;

    /* renamed from: f */
    protected String f845f;

    /* renamed from: g */
    protected String f846g;

    /* renamed from: h */
    public String f847h;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract InputStream mo16373a() throws IOException;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo16374a(String str) {
        return true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract void mo16375b();

    /* renamed from: c */
    public boolean mo16376c() {
        return true;
    }

    public void run() {
        String str = "Null InputStream";
        String str2 = "Transport";
        this.f841b = C1655cc.f795a;
        InputStream inputStream = null;
        try {
            inputStream = mo16373a();
            if (this.f841b != C1655cc.f795a) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        C1685cy.m755a(5, str2, e.getMessage(), e);
                    }
                }
                mo16375b();
            } else if (inputStream == null) {
                C1685cy.m762b(str2, str);
                this.f841b = new C1655cc(C1656a.IO, str);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        C1685cy.m755a(5, str2, e2.getMessage(), e2);
                    }
                }
                mo16375b();
            } else {
                ReadableByteChannel newChannel = Channels.newChannel(inputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                WritableByteChannel newChannel2 = Channels.newChannel(byteArrayOutputStream);
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(16384);
                while (true) {
                    if (newChannel.read(allocateDirect) < 0) {
                        if (allocateDirect.position() <= 0) {
                            break;
                        }
                    }
                    allocateDirect.flip();
                    newChannel2.write(allocateDirect);
                    allocateDirect.compact();
                }
                byteArrayOutputStream.flush();
                if (!mo16374a(byteArrayOutputStream.toString())) {
                    this.f841b = new C1655cc(C1656a.AUTHENTICATE, "Signature Error.");
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                            C1685cy.m755a(5, str2, e3.getMessage(), e3);
                        }
                    }
                    mo16375b();
                    return;
                }
                this.f847h = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        C1685cy.m755a(5, str2, e4.getMessage(), e4);
                    }
                }
                mo16375b();
            }
        } catch (MalformedURLException e5) {
            this.f841b = new C1655cc(C1656a.OTHER, e5.toString());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e6) {
                    C1685cy.m755a(5, str2, e6.getMessage(), e6);
                }
            }
            mo16375b();
        } catch (SSLException e7) {
            C1685cy.m757a(str2, e7.getMessage(), (Throwable) e7);
            this.f841b = new C1655cc(C1656a.UNKNOWN_CERTIFICATE, e7.toString());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e8) {
                    C1685cy.m755a(5, str2, e8.getMessage(), e8);
                }
            }
            mo16375b();
        } catch (IOException e9) {
            C1685cy.m757a(str2, e9.getMessage(), (Throwable) e9);
            this.f841b = new C1655cc(C1656a.IO, e9.toString());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e10) {
                    C1685cy.m755a(5, str2, e10.getMessage(), e10);
                }
            }
            mo16375b();
        } catch (Exception e11) {
            this.f841b = new C1655cc(C1656a.OTHER, e11.toString());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e12) {
                    C1685cy.m755a(5, str2, e12.getMessage(), e12);
                }
            }
            mo16375b();
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e13) {
                    C1685cy.m755a(5, str2, e13.getMessage(), e13);
                }
            }
            mo16375b();
            throw th;
        }
    }

    /* renamed from: d */
    public final String mo16377d() {
        return this.f842c;
    }

    /* renamed from: e */
    public final String mo16378e() {
        return this.f843d;
    }

    /* renamed from: f */
    public final String mo16379f() {
        return this.f845f;
    }

    /* renamed from: g */
    public final String mo16380g() {
        return this.f846g;
    }

    /* renamed from: h */
    public final C1655cc mo16381h() {
        return this.f841b;
    }
}
