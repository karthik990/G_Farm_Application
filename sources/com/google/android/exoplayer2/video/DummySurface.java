package com.google.android.exoplayer2.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EGLSurfaceTexture;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public final class DummySurface extends Surface {
    private static final String EXTENSION_PROTECTED_CONTENT = "EGL_EXT_protected_content";
    private static final String EXTENSION_SURFACELESS_CONTEXT = "EGL_KHR_surfaceless_context";
    private static final String TAG = "DummySurface";
    private static int secureMode;
    private static boolean secureModeInitialized;
    public final boolean secure;
    private final DummySurfaceThread thread;
    private boolean threadReleased;

    private static class DummySurfaceThread extends HandlerThread implements Callback {
        private static final int MSG_INIT = 1;
        private static final int MSG_RELEASE = 2;
        private EGLSurfaceTexture eglSurfaceTexture;
        private Handler handler;
        private Error initError;
        private RuntimeException initException;
        private DummySurface surface;

        public DummySurfaceThread() {
            super("dummySurface");
        }

        public DummySurface init(int i) {
            boolean z;
            start();
            this.handler = new Handler(getLooper(), this);
            this.eglSurfaceTexture = new EGLSurfaceTexture(this.handler);
            synchronized (this) {
                z = false;
                this.handler.obtainMessage(1, i, 0).sendToTarget();
                while (this.surface == null && this.initException == null && this.initError == null) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
            RuntimeException runtimeException = this.initException;
            if (runtimeException == null) {
                Error error = this.initError;
                if (error == null) {
                    return (DummySurface) Assertions.checkNotNull(this.surface);
                }
                throw error;
            }
            throw runtimeException;
        }

        public void release() {
            Assertions.checkNotNull(this.handler);
            this.handler.sendEmptyMessage(2);
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                try {
                    initInternal(message.arg1);
                    synchronized (this) {
                        notify();
                    }
                } catch (RuntimeException e) {
                    Log.m1393e(DummySurface.TAG, "Failed to initialize dummy surface", e);
                    this.initException = e;
                    synchronized (this) {
                        notify();
                    }
                } catch (Error e2) {
                    try {
                        Log.m1393e(DummySurface.TAG, "Failed to initialize dummy surface", e2);
                        this.initError = e2;
                        synchronized (this) {
                            notify();
                        }
                    } catch (Throwable th) {
                        synchronized (this) {
                            notify();
                            throw th;
                        }
                    }
                }
                return true;
            } else if (i != 2) {
                return true;
            } else {
                try {
                    releaseInternal();
                } catch (Throwable th2) {
                    quit();
                    throw th2;
                }
                quit();
                return true;
            }
        }

        private void initInternal(int i) {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.init(i);
            this.surface = new DummySurface(this, this.eglSurfaceTexture.getSurfaceTexture(), i != 0);
        }

        private void releaseInternal() {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.release();
        }
    }

    public static synchronized boolean isSecureSupported(Context context) {
        boolean z;
        synchronized (DummySurface.class) {
            z = true;
            if (!secureModeInitialized) {
                secureMode = Util.SDK_INT < 24 ? 0 : getSecureModeV24(context);
                secureModeInitialized = true;
            }
            if (secureMode == 0) {
                z = false;
            }
        }
        return z;
    }

    public static DummySurface newInstanceV17(Context context, boolean z) {
        assertApiLevel17OrHigher();
        int i = 0;
        Assertions.checkState(!z || isSecureSupported(context));
        DummySurfaceThread dummySurfaceThread = new DummySurfaceThread();
        if (z) {
            i = secureMode;
        }
        return dummySurfaceThread.init(i);
    }

    private DummySurface(DummySurfaceThread dummySurfaceThread, SurfaceTexture surfaceTexture, boolean z) {
        super(surfaceTexture);
        this.thread = dummySurfaceThread;
        this.secure = z;
    }

    public void release() {
        super.release();
        synchronized (this.thread) {
            if (!this.threadReleased) {
                this.thread.release();
                this.threadReleased = true;
            }
        }
    }

    private static void assertApiLevel17OrHigher() {
        if (Util.SDK_INT < 17) {
            throw new UnsupportedOperationException("Unsupported prior to API level 17");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0019, code lost:
        if ("XT1650".equals(com.google.android.exoplayer2.util.Util.MODEL) != false) goto L_0x001b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getSecureModeV24(android.content.Context r4) {
        /*
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r1 = 26
            r2 = 0
            if (r0 >= r1) goto L_0x001c
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            java.lang.String r3 = "samsung"
            boolean r0 = r3.equals(r0)
            if (r0 != 0) goto L_0x001b
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r3 = "XT1650"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x001c
        L_0x001b:
            return r2
        L_0x001c:
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            if (r0 >= r1) goto L_0x002d
            android.content.pm.PackageManager r4 = r4.getPackageManager()
            java.lang.String r0 = "android.hardware.vr.high_performance"
            boolean r4 = r4.hasSystemFeature(r0)
            if (r4 != 0) goto L_0x002d
            return r2
        L_0x002d:
            android.opengl.EGLDisplay r4 = android.opengl.EGL14.eglGetDisplay(r2)
            r0 = 12373(0x3055, float:1.7338E-41)
            java.lang.String r4 = android.opengl.EGL14.eglQueryString(r4, r0)
            if (r4 != 0) goto L_0x003a
            return r2
        L_0x003a:
            java.lang.String r0 = "EGL_EXT_protected_content"
            boolean r0 = r4.contains(r0)
            if (r0 != 0) goto L_0x0043
            return r2
        L_0x0043:
            java.lang.String r0 = "EGL_KHR_surfaceless_context"
            boolean r4 = r4.contains(r0)
            if (r4 == 0) goto L_0x004d
            r4 = 1
            goto L_0x004e
        L_0x004d:
            r4 = 2
        L_0x004e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.DummySurface.getSecureModeV24(android.content.Context):int");
    }
}
