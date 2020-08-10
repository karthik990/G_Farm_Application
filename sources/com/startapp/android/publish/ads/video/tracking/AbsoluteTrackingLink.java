package com.startapp.android.publish.ads.video.tracking;

import com.startapp.common.p042c.C2361e;
import java.io.Serializable;

@C2361e(mo20784c = true)
/* compiled from: StartAppSDK */
public class AbsoluteTrackingLink extends VideoTrackingLink implements Serializable {
    private static final long serialVersionUID = 1;
    private int videoOffsetMillis;

    public int getVideoOffsetMillis() {
        return this.videoOffsetMillis;
    }

    public void setVideoOffsetMillis(int i) {
        this.videoOffsetMillis = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", videoOffsetMillis=");
        sb.append(this.videoOffsetMillis);
        return sb.toString();
    }
}
