package com.google.android.exoplayer2.p041ui;

import android.content.Context;
import android.util.AttributeSet;

@Deprecated
/* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView */
public class PlaybackControlView extends PlayerControlView {
    @Deprecated
    public static final ControlDispatcher DEFAULT_CONTROL_DISPATCHER = new DefaultControlDispatcher();

    @Deprecated
    /* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView$ControlDispatcher */
    public interface ControlDispatcher extends com.google.android.exoplayer2.ControlDispatcher {
    }

    @Deprecated
    /* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView$DefaultControlDispatcher */
    private static final class DefaultControlDispatcher extends com.google.android.exoplayer2.DefaultControlDispatcher implements ControlDispatcher {
        private DefaultControlDispatcher() {
        }
    }

    public PlaybackControlView(Context context) {
        super(context);
    }

    public PlaybackControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PlaybackControlView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public PlaybackControlView(Context context, AttributeSet attributeSet, int i, AttributeSet attributeSet2) {
        super(context, attributeSet, i, attributeSet2);
    }
}
