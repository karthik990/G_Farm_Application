package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.adapters.MP3ListAdapter;
import com.mobiroller.adapters.MP3ListAdapter.Mp3ContentViewHolder;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.jcplayer.JcAudio;
import com.mobiroller.jcplayer.JcPlayerView;
import com.mobiroller.jcplayer.JcPlayerView.JcPlayerViewServiceListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.Audio;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.events.MP3PositionEvent;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveMP3ViewFragment extends BaseModuleFragment implements JcPlayerViewServiceListener {
    private MP3ListAdapter adapter;
    private ArrayList<Audio> audioList = new ArrayList<>();
    @BindView(2131362531)
    ImageView image;
    /* access modifiers changed from: private */
    public ArrayList<JcAudio> jcAudios = new ArrayList<>();
    @BindView(2131362586)
    JcPlayerView jcplayerView;
    @BindView(2131362623)
    RecyclerView list;
    @BindView(2131362749)
    RelativeLayout mp3AdLayout;
    @BindView(2131362750)
    RelativeLayout mp3Layout;
    ProgressViewHelper progressViewHelper;
    private int selectedPosition = -1;

    public void onCompletedAudio(String str) {
    }

    public void onContinueAudio(String str) {
    }

    public void onPaused(String str) {
    }

    public void onPlaying(String str) {
    }

    public void onPreparedAudio(String str, int i, String str2) {
    }

    public void onTimeChanged(long j, String str) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_mp3, viewGroup, false);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        loadUi();
        loadAudioList();
        return inflate;
    }

    private void loadAudioList() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<Audio> arrayList = this.audioList;
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < this.audioList.size(); i++) {
                this.jcAudios.add(JcAudio.createFromURL(((Audio) this.audioList.get(i)).getTitle(), ((Audio) this.audioList.get(i)).getData()));
            }
            this.jcplayerView.registerServiceListener(this);
            this.jcplayerView.initPlaylist(this.jcAudios, this.screenId);
            this.adapter = new MP3ListAdapter(this.audioList, getActivity().getApplicationContext(), this.screenModel.getTableCellBackground1(), this.screenModel.getTableTextColor());
            this.list.setAdapter(this.adapter);
            this.list.setLayoutManager(new LinearLayoutManager(getActivity()));
            ItemClickSupport.addTo(this.list).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                    aveMP3ViewFragment.this.jcplayerView.playAudio((JcAudio) aveMP3ViewFragment.this.jcAudios.get(i));
                }
            });
        }
    }

    private void loadUi() {
        if (this.networkHelper.isConnected()) {
            try {
                ImageManager.loadBackgroundImageFromImageModel(this.mp3Layout, this.screenModel.getBackgroundImageName());
                if (this.screenModel.getMainImageName() != null) {
                    ImageManager.loadImageView(getActivity(), this.screenModel.getMainImageName().getImageURL(), this.image);
                    this.image.setScaleType(ScaleType.FIT_XY);
                    this.image.setVisibility(0);
                } else {
                    this.image.setVisibility(8);
                }
                ArrayList tableItems = this.screenModel.getTableItems();
                for (int i = 0; i < tableItems.size(); i++) {
                    TableItemsModel tableItemsModel = (TableItemsModel) tableItems.get(i);
                    this.audioList.add(new Audio(tableItemsModel.getFileURL(), tableItemsModel.getTitle()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTitle(String str, String str2) {
        int findItemFromTitle = findItemFromTitle(str);
        if (findItemFromTitle != -1) {
            setSelected(findItemFromTitle);
        }
    }

    public void setSelected(int i) {
        int i2 = this.selectedPosition;
        if (i2 != i) {
            if (i2 != -1) {
                this.adapter.getItemByPosition(i2).setSelected(false);
                this.adapter.notifyItemChanged(this.selectedPosition);
            }
            this.selectedPosition = i;
            this.adapter.getItemByPosition(this.selectedPosition).setSelected(true);
            this.adapter.notifyItemChanged(this.selectedPosition);
        }
    }

    private int findItemFromTitle(String str) {
        for (int i = 0; i < this.audioList.size(); i++) {
            if (str.equalsIgnoreCase(((Audio) this.audioList.get(i)).getTitle())) {
                return i;
            }
        }
        return -1;
    }

    @Subscribe
    public void onPostMp3PositionEvent(MP3PositionEvent mP3PositionEvent) {
        if (this.screenId.equalsIgnoreCase(mP3PositionEvent.getScreenId()) && mP3PositionEvent.getJcAudio() != null) {
            int findItemFromTitle = findItemFromTitle(mP3PositionEvent.getJcAudio().getTitle());
            if (findItemFromTitle != -1) {
                setSelected(findItemFromTitle);
            }
            if (!mP3PositionEvent.isPlaying()) {
                ((Mp3ContentViewHolder) this.list.findViewHolderForAdapterPosition(this.selectedPosition)).pauseAnimation();
            } else {
                ((Mp3ContentViewHolder) this.list.findViewHolderForAdapterPosition(this.selectedPosition)).playAnimation();
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.mp3Layout != null) {
            this.bannerHelper.addBannerAd(this.mp3Layout, this.mp3AdLayout);
        }
    }
}
