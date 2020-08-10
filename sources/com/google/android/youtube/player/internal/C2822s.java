package com.google.android.youtube.player.internal;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.internal.C2783e.C2784a;
import com.google.android.youtube.player.internal.C2786f.C2787a;
import com.google.android.youtube.player.internal.C2789g.C2790a;
import com.google.android.youtube.player.internal.C2792h.C2793a;
import java.util.List;

/* renamed from: com.google.android.youtube.player.internal.s */
public final class C2822s implements YouTubePlayer {

    /* renamed from: a */
    private C2776b f1724a;

    /* renamed from: b */
    private C2780d f1725b;

    public C2822s(C2776b bVar, C2780d dVar) {
        this.f1724a = (C2776b) C2774ab.m1496a(bVar, (Object) "connectionClient cannot be null");
        this.f1725b = (C2780d) C2774ab.m1496a(dVar, (Object) "embeddedPlayer cannot be null");
    }

    /* renamed from: a */
    public final View mo38183a() {
        try {
            return (View) C2833v.m1740a(this.f1725b.mo38107s());
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: a */
    public final void mo38184a(Configuration configuration) {
        try {
            this.f1725b.mo38067a(configuration);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: a */
    public final void mo38185a(boolean z) {
        try {
            this.f1725b.mo38076a(z);
            this.f1724a.mo38060a(z);
            this.f1724a.mo38166d();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: a */
    public final boolean mo38186a(int i, KeyEvent keyEvent) {
        try {
            return this.f1725b.mo38077a(i, keyEvent);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: a */
    public final boolean mo38187a(Bundle bundle) {
        try {
            return this.f1725b.mo38078a(bundle);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void addFullscreenControlFlag(int i) {
        try {
            this.f1725b.mo38089d(i);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: b */
    public final void mo38188b() {
        try {
            this.f1725b.mo38101m();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: b */
    public final void mo38189b(boolean z) {
        try {
            this.f1725b.mo38092e(z);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: b */
    public final boolean mo38190b(int i, KeyEvent keyEvent) {
        try {
            return this.f1725b.mo38085b(i, keyEvent);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: c */
    public final void mo38191c() {
        try {
            this.f1725b.mo38102n();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void cuePlaylist(String str) {
        cuePlaylist(str, 0, 0);
    }

    public final void cuePlaylist(String str, int i, int i2) {
        try {
            this.f1725b.mo38074a(str, i, i2);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void cueVideo(String str) {
        cueVideo(str, 0);
    }

    public final void cueVideo(String str, int i) {
        try {
            this.f1725b.mo38073a(str, i);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void cueVideos(List<String> list) {
        cueVideos(list, 0, 0);
    }

    public final void cueVideos(List<String> list, int i, int i2) {
        try {
            this.f1725b.mo38075a(list, i, i2);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: d */
    public final void mo38192d() {
        try {
            this.f1725b.mo38103o();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: e */
    public final void mo38193e() {
        try {
            this.f1725b.mo38104p();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: f */
    public final void mo38194f() {
        try {
            this.f1725b.mo38105q();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: g */
    public final void mo38195g() {
        try {
            this.f1725b.mo38100l();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final int getCurrentTimeMillis() {
        try {
            return this.f1725b.mo38096h();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final int getDurationMillis() {
        try {
            return this.f1725b.mo38097i();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final int getFullscreenControlFlags() {
        try {
            return this.f1725b.mo38098j();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    /* renamed from: h */
    public final Bundle mo38196h() {
        try {
            return this.f1725b.mo38106r();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final boolean hasNext() {
        try {
            return this.f1725b.mo38091d();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final boolean hasPrevious() {
        try {
            return this.f1725b.mo38093e();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final boolean isPlaying() {
        try {
            return this.f1725b.mo38088c();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void loadPlaylist(String str) {
        loadPlaylist(str, 0, 0);
    }

    public final void loadPlaylist(String str, int i, int i2) {
        try {
            this.f1725b.mo38082b(str, i, i2);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void loadVideo(String str) {
        loadVideo(str, 0);
    }

    public final void loadVideo(String str, int i) {
        try {
            this.f1725b.mo38081b(str, i);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void loadVideos(List<String> list) {
        loadVideos(list, 0, 0);
    }

    public final void loadVideos(List<String> list, int i, int i2) {
        try {
            this.f1725b.mo38083b(list, i, i2);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void next() {
        try {
            this.f1725b.mo38094f();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void pause() {
        try {
            this.f1725b.mo38079b();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void play() {
        try {
            this.f1725b.mo38065a();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void previous() {
        try {
            this.f1725b.mo38095g();
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void release() {
        mo38185a(true);
    }

    public final void seekRelativeMillis(int i) {
        try {
            this.f1725b.mo38080b(i);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void seekToMillis(int i) {
        try {
            this.f1725b.mo38066a(i);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setFullscreen(boolean z) {
        try {
            this.f1725b.mo38084b(z);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setFullscreenControlFlags(int i) {
        try {
            this.f1725b.mo38086c(i);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setManageAudioFocus(boolean z) {
        try {
            this.f1725b.mo38090d(z);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setOnFullscreenListener(final OnFullscreenListener onFullscreenListener) {
        try {
            this.f1725b.mo38068a((C2783e) new C2784a() {
                /* renamed from: a */
                public final void mo38110a(boolean z) {
                    onFullscreenListener.onFullscreen(z);
                }
            });
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setPlaybackEventListener(final PlaybackEventListener playbackEventListener) {
        try {
            this.f1725b.mo38069a((C2786f) new C2787a() {
                /* renamed from: a */
                public final void mo38114a() {
                    playbackEventListener.onPlaying();
                }

                /* renamed from: a */
                public final void mo38115a(int i) {
                    playbackEventListener.onSeekTo(i);
                }

                /* renamed from: a */
                public final void mo38116a(boolean z) {
                    playbackEventListener.onBuffering(z);
                }

                /* renamed from: b */
                public final void mo38117b() {
                    playbackEventListener.onPaused();
                }

                /* renamed from: c */
                public final void mo38118c() {
                    playbackEventListener.onStopped();
                }
            });
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setPlayerStateChangeListener(final PlayerStateChangeListener playerStateChangeListener) {
        try {
            this.f1725b.mo38070a((C2789g) new C2790a() {
                /* renamed from: a */
                public final void mo38122a() {
                    playerStateChangeListener.onLoading();
                }

                /* renamed from: a */
                public final void mo38123a(String str) {
                    playerStateChangeListener.onLoaded(str);
                }

                /* renamed from: b */
                public final void mo38124b() {
                    playerStateChangeListener.onAdStarted();
                }

                /* renamed from: b */
                public final void mo38125b(String str) {
                    ErrorReason errorReason;
                    try {
                        errorReason = ErrorReason.valueOf(str);
                    } catch (IllegalArgumentException | NullPointerException unused) {
                        errorReason = ErrorReason.UNKNOWN;
                    }
                    playerStateChangeListener.onError(errorReason);
                }

                /* renamed from: c */
                public final void mo38126c() {
                    playerStateChangeListener.onVideoStarted();
                }

                /* renamed from: d */
                public final void mo38127d() {
                    playerStateChangeListener.onVideoEnded();
                }
            });
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setPlayerStyle(PlayerStyle playerStyle) {
        try {
            this.f1725b.mo38072a(playerStyle.name());
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setPlaylistEventListener(final PlaylistEventListener playlistEventListener) {
        try {
            this.f1725b.mo38071a((C2792h) new C2793a() {
                /* renamed from: a */
                public final void mo38131a() {
                    playlistEventListener.onPrevious();
                }

                /* renamed from: b */
                public final void mo38132b() {
                    playlistEventListener.onNext();
                }

                /* renamed from: c */
                public final void mo38133c() {
                    playlistEventListener.onPlaylistEnded();
                }
            });
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }

    public final void setShowFullscreenButton(boolean z) {
        try {
            this.f1725b.mo38087c(z);
        } catch (RemoteException e) {
            throw new C2814q(e);
        }
    }
}
