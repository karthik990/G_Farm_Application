package com.google.android.exoplayer2;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioFocusRequest.Builder;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Handler;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

final class AudioFocusManager {
    private static final int AUDIO_FOCUS_STATE_HAVE_FOCUS = 1;
    private static final int AUDIO_FOCUS_STATE_LOSS_TRANSIENT = 2;
    private static final int AUDIO_FOCUS_STATE_LOSS_TRANSIENT_DUCK = 3;
    private static final int AUDIO_FOCUS_STATE_LOST_FOCUS = -1;
    private static final int AUDIO_FOCUS_STATE_NO_FOCUS = 0;
    public static final int PLAYER_COMMAND_DO_NOT_PLAY = -1;
    public static final int PLAYER_COMMAND_PLAY_WHEN_READY = 1;
    public static final int PLAYER_COMMAND_WAIT_FOR_CALLBACK = 0;
    private static final String TAG = "AudioFocusManager";
    private static final float VOLUME_MULTIPLIER_DEFAULT = 1.0f;
    private static final float VOLUME_MULTIPLIER_DUCK = 0.2f;
    private AudioAttributes audioAttributes;
    private AudioFocusRequest audioFocusRequest;
    private int audioFocusState;
    private final AudioManager audioManager;
    private int focusGain;
    private final AudioFocusListener focusListener;
    private final PlayerControl playerControl;
    private boolean rebuildAudioFocusRequest;
    private float volumeMultiplier = 1.0f;

    private class AudioFocusListener implements OnAudioFocusChangeListener {
        private final Handler eventHandler;

        public AudioFocusListener(Handler handler) {
            this.eventHandler = handler;
        }

        public /* synthetic */ void lambda$onAudioFocusChange$0$AudioFocusManager$AudioFocusListener(int i) {
            AudioFocusManager.this.handleAudioFocusChange(i);
        }

        public void onAudioFocusChange(int i) {
            this.eventHandler.post(new Runnable(i) {
                private final /* synthetic */ int f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    AudioFocusListener.this.lambda$onAudioFocusChange$0$AudioFocusManager$AudioFocusListener(this.f$1);
                }
            });
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerCommand {
    }

    public interface PlayerControl {
        void executePlayerCommand(int i);

        void setVolumeMultiplier(float f);
    }

    private int handleIdle(boolean z) {
        return z ? 1 : -1;
    }

    public AudioFocusManager(Context context, Handler handler, PlayerControl playerControl2) {
        this.audioManager = (AudioManager) context.getApplicationContext().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.playerControl = playerControl2;
        this.focusListener = new AudioFocusListener(handler);
        this.audioFocusState = 0;
    }

    public float getVolumeMultiplier() {
        return this.volumeMultiplier;
    }

    public int setAudioAttributes(AudioAttributes audioAttributes2, boolean z, int i) {
        int i2;
        if (!Util.areEqual(this.audioAttributes, audioAttributes2)) {
            this.audioAttributes = audioAttributes2;
            this.focusGain = convertAudioAttributesToFocusGain(audioAttributes2);
            int i3 = this.focusGain;
            Assertions.checkArgument(i3 == 1 || i3 == 0, "Automatic handling of audio focus is only available for USAGE_MEDIA and USAGE_GAME.");
            if (z && (i == 2 || i == 3)) {
                return requestAudioFocus();
            }
        }
        if (i == 1) {
            i2 = handleIdle(z);
        } else {
            i2 = handlePrepare(z);
        }
        return i2;
    }

    public int handlePrepare(boolean z) {
        if (z) {
            return requestAudioFocus();
        }
        return -1;
    }

    public int handleSetPlayWhenReady(boolean z, int i) {
        if (!z) {
            abandonAudioFocus();
            return -1;
        }
        return i == 1 ? handleIdle(z) : requestAudioFocus();
    }

    public void handleStop() {
        abandonAudioFocus(true);
    }

    /* access modifiers changed from: 0000 */
    public OnAudioFocusChangeListener getFocusListener() {
        return this.focusListener;
    }

    private int requestAudioFocus() {
        int i;
        int i2 = 1;
        if (this.focusGain == 0) {
            if (this.audioFocusState != 0) {
                abandonAudioFocus(true);
            }
            return 1;
        }
        if (this.audioFocusState == 0) {
            if (Util.SDK_INT >= 26) {
                i = requestAudioFocusV26();
            } else {
                i = requestAudioFocusDefault();
            }
            this.audioFocusState = i == 1 ? 1 : 0;
        }
        int i3 = this.audioFocusState;
        if (i3 == 0) {
            return -1;
        }
        if (i3 == 2) {
            i2 = 0;
        }
        return i2;
    }

    private void abandonAudioFocus() {
        abandonAudioFocus(false);
    }

    private void abandonAudioFocus(boolean z) {
        if (this.focusGain != 0 || this.audioFocusState != 0) {
            if (this.focusGain != 1 || this.audioFocusState == -1 || z) {
                if (Util.SDK_INT >= 26) {
                    abandonAudioFocusV26();
                } else {
                    abandonAudioFocusDefault();
                }
                this.audioFocusState = 0;
            }
        }
    }

    private int requestAudioFocusDefault() {
        return this.audioManager.requestAudioFocus(this.focusListener, Util.getStreamTypeForAudioUsage(((AudioAttributes) Assertions.checkNotNull(this.audioAttributes)).usage), this.focusGain);
    }

    private int requestAudioFocusV26() {
        if (this.audioFocusRequest == null || this.rebuildAudioFocusRequest) {
            AudioFocusRequest audioFocusRequest2 = this.audioFocusRequest;
            this.audioFocusRequest = (audioFocusRequest2 == null ? new Builder(this.focusGain) : new Builder(audioFocusRequest2)).setAudioAttributes(((AudioAttributes) Assertions.checkNotNull(this.audioAttributes)).getAudioAttributesV21()).setWillPauseWhenDucked(willPauseWhenDucked()).setOnAudioFocusChangeListener(this.focusListener).build();
            this.rebuildAudioFocusRequest = false;
        }
        return this.audioManager.requestAudioFocus(this.audioFocusRequest);
    }

    private void abandonAudioFocusDefault() {
        this.audioManager.abandonAudioFocus(this.focusListener);
    }

    private void abandonAudioFocusV26() {
        AudioFocusRequest audioFocusRequest2 = this.audioFocusRequest;
        if (audioFocusRequest2 != null) {
            this.audioManager.abandonAudioFocusRequest(audioFocusRequest2);
        }
    }

    private boolean willPauseWhenDucked() {
        AudioAttributes audioAttributes2 = this.audioAttributes;
        return audioAttributes2 != null && audioAttributes2.contentType == 1;
    }

    private static int convertAudioAttributesToFocusGain(AudioAttributes audioAttributes2) {
        if (audioAttributes2 == null) {
            return 0;
        }
        int i = audioAttributes2.usage;
        String str = TAG;
        switch (i) {
            case 0:
                Log.m1396w(str, "Specify a proper usage in the audio attributes for audio focus handling. Using AUDIOFOCUS_GAIN by default.");
                return 1;
            case 1:
            case 14:
                return 1;
            case 2:
            case 4:
                return 2;
            case 3:
                return 0;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
                break;
            case 11:
                if (audioAttributes2.contentType == 1) {
                    return 2;
                }
                break;
            case 16:
                if (Util.SDK_INT >= 19) {
                    return 4;
                }
                return 2;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unidentified audio usage: ");
                sb.append(audioAttributes2.usage);
                Log.m1396w(str, sb.toString());
                return 0;
        }
        return 3;
    }

    /* access modifiers changed from: private */
    public void handleAudioFocusChange(int i) {
        if (i != -3) {
            if (i == -2) {
                this.audioFocusState = 2;
            } else if (i == -1) {
                this.audioFocusState = -1;
            } else if (i != 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown focus change type: ");
                sb.append(i);
                Log.m1396w(TAG, sb.toString());
                return;
            } else {
                this.audioFocusState = 1;
            }
        } else if (willPauseWhenDucked()) {
            this.audioFocusState = 2;
        } else {
            this.audioFocusState = 3;
        }
        int i2 = this.audioFocusState;
        if (i2 == -1) {
            this.playerControl.executePlayerCommand(-1);
            abandonAudioFocus(true);
        } else if (i2 != 0) {
            if (i2 == 1) {
                this.playerControl.executePlayerCommand(1);
            } else if (i2 == 2) {
                this.playerControl.executePlayerCommand(0);
            } else if (i2 != 3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unknown audio focus state: ");
                sb2.append(this.audioFocusState);
                throw new IllegalStateException(sb2.toString());
            }
        }
        float f = this.audioFocusState == 3 ? VOLUME_MULTIPLIER_DUCK : 1.0f;
        if (this.volumeMultiplier != f) {
            this.volumeMultiplier = f;
            this.playerControl.setVolumeMultiplier(f);
        }
    }
}
