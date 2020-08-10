package com.google.android.exoplayer2.p041ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder */
public final class TrackSelectionDialogBuilder {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final DialogCallback callback;
    private final Context context;
    private boolean isDisabled;
    private final MappedTrackInfo mappedTrackInfo;
    private List<SelectionOverride> overrides;
    private final int rendererIndex;
    private boolean showDisableOption;
    private final CharSequence title;
    private TrackNameProvider trackNameProvider;

    /* renamed from: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder$DialogCallback */
    public interface DialogCallback {
        void onTracksSelected(boolean z, List<SelectionOverride> list);
    }

    public TrackSelectionDialogBuilder(Context context2, CharSequence charSequence, MappedTrackInfo mappedTrackInfo2, int i, DialogCallback dialogCallback) {
        this.context = context2;
        this.title = charSequence;
        this.mappedTrackInfo = mappedTrackInfo2;
        this.rendererIndex = i;
        this.callback = dialogCallback;
        this.overrides = Collections.emptyList();
    }

    public TrackSelectionDialogBuilder(Context context2, CharSequence charSequence, DefaultTrackSelector defaultTrackSelector, int i) {
        this.context = context2;
        this.title = charSequence;
        this.mappedTrackInfo = (MappedTrackInfo) Assertions.checkNotNull(defaultTrackSelector.getCurrentMappedTrackInfo());
        this.rendererIndex = i;
        TrackGroupArray trackGroups = this.mappedTrackInfo.getTrackGroups(i);
        Parameters parameters = defaultTrackSelector.getParameters();
        this.isDisabled = parameters.getRendererDisabled(i);
        SelectionOverride selectionOverride = parameters.getSelectionOverride(i, trackGroups);
        this.overrides = selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride);
        this.callback = new DialogCallback(parameters, i, trackGroups) {
            private final /* synthetic */ Parameters f$1;
            private final /* synthetic */ int f$2;
            private final /* synthetic */ TrackGroupArray f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void onTracksSelected(boolean z, List list) {
                TrackSelectionDialogBuilder.lambda$new$0(DefaultTrackSelector.this, this.f$1, this.f$2, this.f$3, z, list);
            }
        };
    }

    static /* synthetic */ void lambda$new$0(DefaultTrackSelector defaultTrackSelector, Parameters parameters, int i, TrackGroupArray trackGroupArray, boolean z, List list) {
        defaultTrackSelector.setParameters(TrackSelectionUtil.updateParametersWithOverride(parameters, i, trackGroupArray, z, list.isEmpty() ? null : (SelectionOverride) list.get(0)));
    }

    public TrackSelectionDialogBuilder setIsDisabled(boolean z) {
        this.isDisabled = z;
        return this;
    }

    public TrackSelectionDialogBuilder setOverride(SelectionOverride selectionOverride) {
        return setOverrides(selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride));
    }

    public TrackSelectionDialogBuilder setOverrides(List<SelectionOverride> list) {
        this.overrides = list;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean z) {
        this.allowAdaptiveSelections = z;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean z) {
        this.allowMultipleOverrides = z;
        return this;
    }

    public TrackSelectionDialogBuilder setShowDisableOption(boolean z) {
        this.showDisableOption = z;
        return this;
    }

    public TrackSelectionDialogBuilder setTrackNameProvider(TrackNameProvider trackNameProvider2) {
        this.trackNameProvider = trackNameProvider2;
        return this;
    }

    public AlertDialog build() {
        Builder builder = new Builder(this.context);
        View inflate = LayoutInflater.from(builder.getContext()).inflate(C2489R.layout.exo_track_selection_dialog, null);
        TrackSelectionView trackSelectionView = (TrackSelectionView) inflate.findViewById(C2489R.C2492id.exo_track_selection_view);
        trackSelectionView.setAllowMultipleOverrides(this.allowMultipleOverrides);
        trackSelectionView.setAllowAdaptiveSelections(this.allowAdaptiveSelections);
        trackSelectionView.setShowDisableOption(this.showDisableOption);
        TrackNameProvider trackNameProvider2 = this.trackNameProvider;
        if (trackNameProvider2 != null) {
            trackSelectionView.setTrackNameProvider(trackNameProvider2);
        }
        trackSelectionView.init(this.mappedTrackInfo, this.rendererIndex, this.isDisabled, this.overrides, null);
        return builder.setTitle(this.title).setView(inflate).setPositiveButton(17039370, new OnClickListener(trackSelectionView) {
            private final /* synthetic */ TrackSelectionView f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(DialogInterface dialogInterface, int i) {
                TrackSelectionDialogBuilder.this.lambda$build$1$TrackSelectionDialogBuilder(this.f$1, dialogInterface, i);
            }
        }).setNegativeButton(17039360, null).create();
    }

    public /* synthetic */ void lambda$build$1$TrackSelectionDialogBuilder(TrackSelectionView trackSelectionView, DialogInterface dialogInterface, int i) {
        this.callback.onTracksSelected(trackSelectionView.getIsDisabled(), trackSelectionView.getOverrides());
    }
}
