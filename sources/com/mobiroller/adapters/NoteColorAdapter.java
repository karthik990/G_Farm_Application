package com.mobiroller.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;
import java.util.ArrayList;

public class NoteColorAdapter extends Adapter<NoteColorViewHolder> {
    Activity activity;
    private boolean flag;
    private ArrayList<String> noteColorList = new ArrayList<>();
    private int selectedColor;

    class NoteColorViewHolder extends ViewHolder {
        CircleImageView color;
        ImageView selected;

        NoteColorViewHolder(View view) {
            super(view);
            this.selected = (ImageView) view.findViewById(R.id.circle_note_selected);
            this.color = (CircleImageView) view.findViewById(R.id.circle_note);
        }
    }

    public NoteColorAdapter(ArrayList<String> arrayList, String str, Activity activity2) {
        this.flag = false;
        this.noteColorList = arrayList;
        this.activity = activity2;
        if (arrayList.contains(str)) {
            this.flag = true;
        }
        if (this.flag) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (((String) arrayList.get(i)).equalsIgnoreCase(str)) {
                    this.selectedColor = i;
                }
            }
        }
    }

    public NoteColorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NoteColorViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_note_color, viewGroup, false));
    }

    public void onBindViewHolder(NoteColorViewHolder noteColorViewHolder, int i) {
        if (this.selectedColor == i) {
            noteColorViewHolder.selected.setVisibility(0);
        } else {
            noteColorViewHolder.selected.setVisibility(4);
        }
        noteColorViewHolder.color.setColorFilter(Color.parseColor((String) this.noteColorList.get(i)));
    }

    public int getItemCount() {
        return this.noteColorList.size();
    }

    public void updateSelected(int i) {
        this.selectedColor = i;
        notifyDataSetChanged();
    }
}
