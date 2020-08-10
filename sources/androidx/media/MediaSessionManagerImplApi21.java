package androidx.media;

import android.content.Context;
import com.mobiroller.preview.Manifest.permission;

class MediaSessionManagerImplApi21 extends MediaSessionManagerImplBase {
    MediaSessionManagerImplApi21(Context context) {
        super(context);
        this.mContext = context;
    }

    public boolean isTrustedForMediaControl(RemoteUserInfoImpl remoteUserInfoImpl) {
        return hasMediaControlPermission(remoteUserInfoImpl) || super.isTrustedForMediaControl(remoteUserInfoImpl);
    }

    private boolean hasMediaControlPermission(RemoteUserInfoImpl remoteUserInfoImpl) {
        return getContext().checkPermission(permission.MEDIA_CONTENT_CONTROL, remoteUserInfoImpl.getPid(), remoteUserInfoImpl.getUid()) == 0;
    }
}
