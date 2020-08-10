package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.mobiroller.activities.AveAddNoteActivity;
import com.mobiroller.activities.ImagePagerActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.GalleryModel;
import com.mobiroller.util.InterstitialAdsUtil;
import java.io.File;
import java.util.ArrayList;

public class NoteImageAdapter extends Adapter<NoteImageViewHolder> {
    /* access modifiers changed from: private */
    public Activity context;
    private InterstitialAdsUtil interstitialAdsUtil;
    /* access modifiers changed from: private */
    public ArrayList<String> noteImageList;

    class NoteImageViewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout mainLayout;
        ImageView remove;

        NoteImageViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.note_image);
            this.mainLayout = (RelativeLayout) view.findViewById(R.id.note_image_main_layout);
            this.remove = (ImageView) view.findViewById(R.id.note_image_remove);
        }
    }

    public int getItemViewType(int i) {
        return i == 0 ? 0 : 1;
    }

    public NoteImageAdapter(Activity activity, ArrayList<String> arrayList) {
        this.context = activity;
        this.noteImageList = arrayList;
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) activity);
    }

    public NoteImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NoteImageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_note_image, viewGroup, false));
    }

    public void onBindViewHolder(final NoteImageViewHolder noteImageViewHolder, final int i) {
        if (i == 0) {
            noteImageViewHolder.image.setBackgroundColor(Color.parseColor("#CCCCCC"));
            noteImageViewHolder.image.setImageResource(17170445);
            noteImageViewHolder.mainLayout.setGravity(17);
            noteImageViewHolder.remove.setVisibility(8);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            ImageView imageView = new ImageView(this.context);
            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(8, 8, 8, 8);
            LayoutParams layoutParams2 = (LayoutParams) imageView.getLayoutParams();
            layoutParams2.addRule(13, -1);
            imageView.setLayoutParams(layoutParams2);
            imageView.setBackgroundColor(Color.parseColor("#A6AFB8"));
            imageView.setImageResource(R.drawable.icon_add);
            noteImageViewHolder.mainLayout.addView(imageView);
            noteImageViewHolder.mainLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (NoteImageAdapter.this.getItemCount() != 8) {
                        ((AveAddNoteActivity) NoteImageAdapter.this.context).askStoragePermission();
                    } else {
                        Toast.makeText(NoteImageAdapter.this.context, NoteImageAdapter.this.context.getString(R.string.more_than_seven), 0).show();
                    }
                }
            });
            return;
        }
        String str = (String) this.noteImageList.get(i - 1);
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                Glide.with(this.context).load(file).into(noteImageViewHolder.image);
            }
            noteImageViewHolder.remove.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NoteImageAdapter.this.noteImageList.remove(i - 1);
                    NoteImageAdapter.this.notifyDataSetChanged();
                }
            });
            noteImageViewHolder.image.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    NoteImageAdapter.this.startImagePagerActivity(i - 1, noteImageViewHolder.image);
                }
            });
        }
    }

    public int getItemCount() {
        return this.noteImageList.size() + 1;
    }

    public ArrayList<String> getNoteImageList() {
        return this.noteImageList;
    }

    /* access modifiers changed from: private */
    public void startImagePagerActivity(int i, ImageView imageView) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.noteImageList.size(); i2++) {
            arrayList.add(new GalleryModel(new File((String) this.noteImageList.get(i2))));
        }
        Intent intent = new Intent(this.context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.TOOLBAR_TITLE, "title");
        intent.putExtra("imageList", arrayList);
        intent.putExtra(Constants.KEY_RSS_POSITION, i);
        intent.putExtra("isDownloadable", false);
        if (VERSION.SDK_INT >= 21) {
            this.interstitialAdsUtil.checkInterstitialAds(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this.context, imageView, "galleryImage").toBundle());
            return;
        }
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }
}
