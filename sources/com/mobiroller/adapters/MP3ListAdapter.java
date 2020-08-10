package com.mobiroller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.airbnb.lottie.LottieAnimationView;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.Audio;
import com.mobiroller.models.ColorModel;
import com.mobiroller.models.ImageModel;
import java.util.ArrayList;

public class MP3ListAdapter extends Adapter<Mp3ContentViewHolder> {
    /* access modifiers changed from: private */
    public ColorModel colorModel;
    public Context context;
    private ArrayList<Audio> data;
    /* access modifiers changed from: private */
    public ImageModel image;

    public class Mp3ContentViewHolder extends ViewHolder {
        LottieAnimationView arrow;
        View dividerView;
        TextView text;
        View view;

        Mp3ContentViewHolder(View view2) {
            super(view2);
            this.view = view2;
            if (!(MP3ListAdapter.this.image == null || MP3ListAdapter.this.image.getImageURL() == null)) {
                this.text = (TextView) view2.findViewById(R.id.content_list_title);
            }
            if (!(MP3ListAdapter.this.colorModel == null || MP3ListAdapter.this.colorModel.getColor() == 0)) {
                this.text.setTextColor(MP3ListAdapter.this.colorModel.getColor());
            }
            this.arrow = (LottieAnimationView) view2.findViewById(R.id.arrow_image);
            this.dividerView = view2.findViewById(R.id.divider);
            this.dividerView.setBackgroundColor(UtilManager.sharedPrefHelper().getActionBarColor());
        }

        /* access modifiers changed from: 0000 */
        public void setSelected() {
            this.arrow.setVisibility(0);
            this.arrow.setMinAndMaxProgress(0.0f, 0.03f);
            this.arrow.loop(true);
            if (!this.arrow.isAnimating()) {
                this.arrow.playAnimation();
            }
        }

        /* access modifiers changed from: 0000 */
        public void setUnselected() {
            this.arrow.setVisibility(8);
            this.arrow.cancelAnimation();
        }

        public void pauseAnimation() {
            this.arrow.pauseAnimation();
        }

        public void playAnimation() {
            this.arrow.playAnimation();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public MP3ListAdapter(ArrayList<Audio> arrayList, Context context2, ImageModel imageModel, ColorModel colorModel2) {
        this.data = arrayList;
        this.context = context2;
        this.image = imageModel;
        this.colorModel = colorModel2;
    }

    public int getCount() {
        return this.data.size();
    }

    public Audio getItemByPosition(int i) {
        return (Audio) this.data.get(i);
    }

    public Mp3ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Mp3ContentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mp3_list_item, viewGroup, false));
    }

    public void onBindViewHolder(Mp3ContentViewHolder mp3ContentViewHolder, int i) {
        Audio audio = (Audio) this.data.get(i);
        if (audio.isSelected()) {
            mp3ContentViewHolder.setSelected();
        } else {
            mp3ContentViewHolder.setUnselected();
        }
        mp3ContentViewHolder.text.setText(audio.getTitle());
    }

    public int getItemCount() {
        return this.data.size();
    }
}
