package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.util.Util;

public abstract class BasePlayer implements Player {
    protected final Window window = new Window();

    protected static final class ListenerHolder {
        public final EventListener listener;
        private boolean released;

        public ListenerHolder(EventListener eventListener) {
            this.listener = eventListener;
        }

        public void release() {
            this.released = true;
        }

        public void invoke(ListenerInvocation listenerInvocation) {
            if (!this.released) {
                listenerInvocation.invokeListener(this.listener);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.listener.equals(((ListenerHolder) obj).listener);
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }

    protected interface ListenerInvocation {
        void invokeListener(EventListener eventListener);
    }

    public final boolean isPlaying() {
        return getPlaybackState() == 3 && getPlayWhenReady() && getPlaybackSuppressionReason() == 0;
    }

    public final void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public final void seekToDefaultPosition(int i) {
        seekTo(i, C1996C.TIME_UNSET);
    }

    public final void seekTo(long j) {
        seekTo(getCurrentWindowIndex(), j);
    }

    public final boolean hasPrevious() {
        return getPreviousWindowIndex() != -1;
    }

    public final void previous() {
        int previousWindowIndex = getPreviousWindowIndex();
        if (previousWindowIndex != -1) {
            seekToDefaultPosition(previousWindowIndex);
        }
    }

    public final boolean hasNext() {
        return getNextWindowIndex() != -1;
    }

    public final void next() {
        int nextWindowIndex = getNextWindowIndex();
        if (nextWindowIndex != -1) {
            seekToDefaultPosition(nextWindowIndex);
        }
    }

    public final void stop() {
        stop(false);
    }

    public final int getNextWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        return currentTimeline.getNextWindowIndex(getCurrentWindowIndex(), getRepeatModeForNavigation(), getShuffleModeEnabled());
    }

    public final int getPreviousWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        return currentTimeline.getPreviousWindowIndex(getCurrentWindowIndex(), getRepeatModeForNavigation(), getShuffleModeEnabled());
    }

    public final Object getCurrentTag() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return null;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.window).tag;
    }

    public final Object getCurrentManifest() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return null;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.window).manifest;
    }

    public final int getBufferedPercentage() {
        long bufferedPosition = getBufferedPosition();
        long duration = getDuration();
        if (bufferedPosition == C1996C.TIME_UNSET || duration == C1996C.TIME_UNSET) {
            return 0;
        }
        if (duration == 0) {
            return 100;
        }
        return Util.constrainValue((int) ((bufferedPosition * 100) / duration), 0, 100);
    }

    public final boolean isCurrentWindowDynamic() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    public final boolean isCurrentWindowLive() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isLive;
    }

    public final boolean isCurrentWindowSeekable() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    public final long getContentDuration() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C1996C.TIME_UNSET;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
    }

    private int getRepeatModeForNavigation() {
        int repeatMode = getRepeatMode();
        if (repeatMode == 1) {
            return 0;
        }
        return repeatMode;
    }
}
