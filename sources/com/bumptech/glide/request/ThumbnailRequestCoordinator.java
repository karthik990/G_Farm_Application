package com.bumptech.glide.request;

import com.bumptech.glide.request.RequestCoordinator.RequestState;

public class ThumbnailRequestCoordinator implements RequestCoordinator, Request {
    private volatile Request full;
    private RequestState fullState = RequestState.CLEARED;
    private boolean isRunningDuringBegin;
    private final RequestCoordinator parent;
    private final Object requestLock;
    private volatile Request thumb;
    private RequestState thumbState = RequestState.CLEARED;

    public ThumbnailRequestCoordinator(Object obj, RequestCoordinator requestCoordinator) {
        this.requestLock = obj;
        this.parent = requestCoordinator;
    }

    public void setRequests(Request request, Request request2) {
        this.full = request;
        this.thumb = request2;
    }

    public boolean canSetImage(Request request) {
        boolean z;
        synchronized (this.requestLock) {
            z = parentCanSetImage() && (request.equals(this.full) || this.fullState != RequestState.SUCCESS);
        }
        return z;
    }

    private boolean parentCanSetImage() {
        RequestCoordinator requestCoordinator = this.parent;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    public boolean canNotifyStatusChanged(Request request) {
        boolean z;
        synchronized (this.requestLock) {
            z = parentCanNotifyStatusChanged() && request.equals(this.full) && !isAnyResourceSet();
        }
        return z;
    }

    public boolean canNotifyCleared(Request request) {
        boolean z;
        synchronized (this.requestLock) {
            z = parentCanNotifyCleared() && request.equals(this.full) && this.fullState != RequestState.PAUSED;
        }
        return z;
    }

    private boolean parentCanNotifyCleared() {
        RequestCoordinator requestCoordinator = this.parent;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    private boolean parentCanNotifyStatusChanged() {
        RequestCoordinator requestCoordinator = this.parent;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    public boolean isAnyResourceSet() {
        boolean z;
        synchronized (this.requestLock) {
            if (!this.thumb.isAnyResourceSet()) {
                if (!this.full.isAnyResourceSet()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onRequestSuccess(com.bumptech.glide.request.Request r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.requestLock
            monitor-enter(r0)
            com.bumptech.glide.request.Request r1 = r2.thumb     // Catch:{ all -> 0x002d }
            boolean r3 = r3.equals(r1)     // Catch:{ all -> 0x002d }
            if (r3 == 0) goto L_0x0011
            com.bumptech.glide.request.RequestCoordinator$RequestState r3 = com.bumptech.glide.request.RequestCoordinator.RequestState.SUCCESS     // Catch:{ all -> 0x002d }
            r2.thumbState = r3     // Catch:{ all -> 0x002d }
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return
        L_0x0011:
            com.bumptech.glide.request.RequestCoordinator$RequestState r3 = com.bumptech.glide.request.RequestCoordinator.RequestState.SUCCESS     // Catch:{ all -> 0x002d }
            r2.fullState = r3     // Catch:{ all -> 0x002d }
            com.bumptech.glide.request.RequestCoordinator r3 = r2.parent     // Catch:{ all -> 0x002d }
            if (r3 == 0) goto L_0x001e
            com.bumptech.glide.request.RequestCoordinator r3 = r2.parent     // Catch:{ all -> 0x002d }
            r3.onRequestSuccess(r2)     // Catch:{ all -> 0x002d }
        L_0x001e:
            com.bumptech.glide.request.RequestCoordinator$RequestState r3 = r2.thumbState     // Catch:{ all -> 0x002d }
            boolean r3 = r3.isComplete()     // Catch:{ all -> 0x002d }
            if (r3 != 0) goto L_0x002b
            com.bumptech.glide.request.Request r3 = r2.thumb     // Catch:{ all -> 0x002d }
            r3.clear()     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            return
        L_0x002d:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.ThumbnailRequestCoordinator.onRequestSuccess(com.bumptech.glide.request.Request):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onRequestFailed(com.bumptech.glide.request.Request r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.requestLock
            monitor-enter(r0)
            com.bumptech.glide.request.Request r1 = r2.full     // Catch:{ all -> 0x0020 }
            boolean r3 = r3.equals(r1)     // Catch:{ all -> 0x0020 }
            if (r3 != 0) goto L_0x0011
            com.bumptech.glide.request.RequestCoordinator$RequestState r3 = com.bumptech.glide.request.RequestCoordinator.RequestState.FAILED     // Catch:{ all -> 0x0020 }
            r2.thumbState = r3     // Catch:{ all -> 0x0020 }
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return
        L_0x0011:
            com.bumptech.glide.request.RequestCoordinator$RequestState r3 = com.bumptech.glide.request.RequestCoordinator.RequestState.FAILED     // Catch:{ all -> 0x0020 }
            r2.fullState = r3     // Catch:{ all -> 0x0020 }
            com.bumptech.glide.request.RequestCoordinator r3 = r2.parent     // Catch:{ all -> 0x0020 }
            if (r3 == 0) goto L_0x001e
            com.bumptech.glide.request.RequestCoordinator r3 = r2.parent     // Catch:{ all -> 0x0020 }
            r3.onRequestFailed(r2)     // Catch:{ all -> 0x0020 }
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return
        L_0x0020:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.ThumbnailRequestCoordinator.onRequestFailed(com.bumptech.glide.request.Request):void");
    }

    public RequestCoordinator getRoot() {
        RequestCoordinator root;
        synchronized (this.requestLock) {
            root = this.parent != null ? this.parent.getRoot() : this;
        }
        return root;
    }

    public void begin() {
        synchronized (this.requestLock) {
            this.isRunningDuringBegin = true;
            try {
                if (!(this.fullState == RequestState.SUCCESS || this.thumbState == RequestState.RUNNING)) {
                    this.thumbState = RequestState.RUNNING;
                    this.thumb.begin();
                }
                if (this.isRunningDuringBegin && this.fullState != RequestState.RUNNING) {
                    this.fullState = RequestState.RUNNING;
                    this.full.begin();
                }
            } finally {
                this.isRunningDuringBegin = false;
            }
        }
    }

    public void clear() {
        synchronized (this.requestLock) {
            this.isRunningDuringBegin = false;
            this.fullState = RequestState.CLEARED;
            this.thumbState = RequestState.CLEARED;
            this.thumb.clear();
            this.full.clear();
        }
    }

    public void pause() {
        synchronized (this.requestLock) {
            if (!this.thumbState.isComplete()) {
                this.thumbState = RequestState.PAUSED;
                this.thumb.pause();
            }
            if (!this.fullState.isComplete()) {
                this.fullState = RequestState.PAUSED;
                this.full.pause();
            }
        }
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.requestLock) {
            z = this.fullState == RequestState.RUNNING;
        }
        return z;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.requestLock) {
            z = this.fullState == RequestState.SUCCESS;
        }
        return z;
    }

    public boolean isCleared() {
        boolean z;
        synchronized (this.requestLock) {
            z = this.fullState == RequestState.CLEARED;
        }
        return z;
    }

    public boolean isEquivalentTo(Request request) {
        if (!(request instanceof ThumbnailRequestCoordinator)) {
            return false;
        }
        ThumbnailRequestCoordinator thumbnailRequestCoordinator = (ThumbnailRequestCoordinator) request;
        if (this.full == null) {
            if (thumbnailRequestCoordinator.full != null) {
                return false;
            }
        } else if (!this.full.isEquivalentTo(thumbnailRequestCoordinator.full)) {
            return false;
        }
        if (this.thumb == null) {
            if (thumbnailRequestCoordinator.thumb != null) {
                return false;
            }
        } else if (!this.thumb.isEquivalentTo(thumbnailRequestCoordinator.thumb)) {
            return false;
        }
        return true;
    }
}
