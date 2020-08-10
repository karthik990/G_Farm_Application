package com.mobiroller.fragments;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.metadata.icy.IcyInfo;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.VolumeButtonEvent;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.ScrollingTextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class aveRadioBroadcastViewFragment extends BaseModuleFragment implements EventListener, MetadataOutput {
    /* access modifiers changed from: private */
    public AudioManager audioManager = null;
    @BindView(2131362019)
    LinearLayout broadcastLayout;
    @BindView(2131363018)
    ImageButton playButton;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363015)
    ScrollingTextView radioBroadcastText;
    @BindView(2131363016)
    TextView radioTitle;
    @BindView(2131363361)
    RelativeLayout relativeLayout;
    @BindView(2131363388)
    ImageButton volumeMuteButton;
    @BindView(2131363389)
    SeekBar volumeSeekbar;
    @BindView(2131363390)
    ImageButton volumeUpButton;

    public /* synthetic */ void onLoadingChanged(boolean z) {
        CC.$default$onLoadingChanged(this, z);
    }

    public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        CC.$default$onPlaybackParametersChanged(this, playbackParameters);
    }

    public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
        CC.$default$onPlaybackSuppressionReasonChanged(this, i);
    }

    public /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
        CC.$default$onPlayerStateChanged(this, z, i);
    }

    public /* synthetic */ void onPositionDiscontinuity(int i) {
        CC.$default$onPositionDiscontinuity(this, i);
    }

    public /* synthetic */ void onRepeatModeChanged(int i) {
        CC.$default$onRepeatModeChanged(this, i);
    }

    public /* synthetic */ void onSeekProcessed() {
        CC.$default$onSeekProcessed(this);
    }

    public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
        CC.$default$onShuffleModeEnabledChanged(this, z);
    }

    public /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
        CC.$default$onTimelineChanged(this, timeline, i);
    }

    @Deprecated
    public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
        CC.$default$onTimelineChanged(this, timeline, obj, i);
    }

    public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.radio_broadcast_view, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        try {
            loadUi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return inflate;
    }

    public void onResume() {
        super.onResume();
        if (this.relativeLayout != null) {
            this.bannerHelper.addBannerAd(this.relativeLayout, this.broadcastLayout);
        }
    }

    private void loadUi() {
        if (this.networkHelper.isConnected()) {
            getActivity().setVolumeControlStream(3);
            ImageManager.loadBackgroundImageFromImageModel(this.broadcastLayout, this.screenModel.getBackgroundImageName());
            if (this.screenModel.getContentText().isEmpty() || this.screenModel.getContentText() == null) {
                this.radioTitle.setVisibility(4);
            } else {
                this.componentHelper.setRadioTitleText(getActivity(), this.radioTitle, this.screenModel);
                this.radioTitle.setVisibility(0);
                this.radioTitle.setSelected(true);
            }
            this.audioManager = (AudioManager) getActivity().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            this.volumeSeekbar.setMax(this.audioManager.getStreamMaxVolume(3));
            this.volumeSeekbar.setProgress(this.audioManager.getStreamVolume(3));
            this.volumeUpButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    aveRadioBroadcastViewFragment.this.audioManager.setStreamVolume(3, aveRadioBroadcastViewFragment.this.audioManager.getStreamMaxVolume(3), 0);
                    aveRadioBroadcastViewFragment.this.volumeSeekbar.setProgress(aveRadioBroadcastViewFragment.this.audioManager.getStreamMaxVolume(3));
                }
            });
            this.volumeMuteButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    aveRadioBroadcastViewFragment.this.audioManager.setStreamVolume(3, 0, 0);
                    aveRadioBroadcastViewFragment.this.volumeSeekbar.setProgress(0);
                }
            });
            this.volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    aveRadioBroadcastViewFragment.this.audioManager.setStreamVolume(3, i, 0);
                }
            });
            if (UtilManager.radioHelper().isOwner(this.screenModel.getRadioBroadcastLink(), this.screenId)) {
                UtilManager.radioHelper().setEventListener(this);
                UtilManager.radioHelper().setMetadataOutputListener(this);
                onIsPlayingChanged(UtilManager.radioHelper().getPlayerStatus(this.screenModel.getRadioBroadcastLink(), this.screenId));
                String contentText = UtilManager.radioHelper().getContentText(this.screenModel.getRadioBroadcastLink(), this.screenId);
                ScrollingTextView scrollingTextView = this.radioBroadcastText;
                if (contentText == null) {
                    contentText = "";
                }
                scrollingTextView.setText(contentText);
            }
        }
    }

    public void onIsPlayingChanged(boolean z) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ImageButton imageButton = this.playButton;
            int i = R.drawable.stream_pause_128;
            FragmentActivity activity = getActivity();
            imageButton.setImageDrawable(z ? ContextCompat.getDrawable(activity, R.drawable.stream_pause_128) : ContextCompat.getDrawable(activity, R.drawable.stream_play_128));
            ImageButton imageButton2 = this.playButton;
            if (!z) {
                i = R.drawable.stream_play_128;
            }
            imageButton2.setTag(Integer.valueOf(i));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVolumeButtonEvent(VolumeButtonEvent volumeButtonEvent) {
        onKeyDown(volumeButtonEvent.getKeyCode());
    }

    private boolean onKeyDown(int i) {
        if (i == 24) {
            this.volumeSeekbar.setProgress(this.volumeSeekbar.getProgress() + 1);
            return true;
        }
        if (i == 25) {
            this.volumeSeekbar.setProgress(this.volumeSeekbar.getProgress() - 1);
        }
        return true;
    }

    @OnClick({2131363018})
    public void onClickPlayButton() {
        if (UtilManager.radioHelper().getIsPlayWhenReady()) {
            UtilManager.radioHelper().setPlayWhenReady();
            return;
        }
        UtilManager.radioHelper().setEventListener(this);
        UtilManager.radioHelper().setMetadataOutputListener(this);
        UtilManager.radioHelper().play(this.screenModel.getRadioBroadcastLink(), this.screenId, this.screenModel.getTitle());
    }

    public void onMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Entry entry = metadata.get(i);
            if ((entry instanceof IcyInfo) && entry != null) {
                IcyInfo icyInfo = (IcyInfo) entry;
                if (icyInfo.title != null) {
                    ScrollingTextView scrollingTextView = this.radioBroadcastText;
                    if (scrollingTextView != null) {
                        scrollingTextView.setText(icyInfo.title);
                    }
                    UtilManager.radioHelper().setContentText(icyInfo.title);
                }
            }
        }
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), R.string.common_error, 0).show();
        }
    }
}
