package com.mobiroller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.mobiroller.helpers.TimeHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NoteModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends Adapter<NoteViewHolder> {
    private Context context;
    private ArrayList<NoteModel> noteModelArrayList;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    class NoteViewHolder extends ViewHolder {
        TextView date;
        TextView description;
        LinearLayout foregroundView;
        View noteColor;
        ImageView noteImage;
        LinearLayout textLayout;
        TextView title;

        NoteViewHolder(View view) {
            super(view);
            this.noteColor = view.findViewById(R.id.note_color);
            this.textLayout = (LinearLayout) view.findViewById(R.id.note_text_layout);
            this.foregroundView = (LinearLayout) view.findViewById(R.id.foregroundView);
            this.title = (TextView) view.findViewById(R.id.note_title);
            this.date = (TextView) view.findViewById(R.id.note_date);
            this.description = (TextView) view.findViewById(R.id.note_description);
            this.noteImage = (ImageView) view.findViewById(R.id.note_image);
        }
    }

    public NoteAdapter(Context context2, ArrayList<NoteModel> arrayList) {
        this.context = context2;
        this.noteModelArrayList = arrayList;
    }

    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NoteViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_note, viewGroup, false));
    }

    public void onBindViewHolder(NoteViewHolder noteViewHolder, int i) {
        NoteModel noteModel = (NoteModel) this.noteModelArrayList.get(i);
        noteViewHolder.description.setText(noteModel.getDescription());
        noteViewHolder.title.setText(noteModel.getTitle());
        String str = " ";
        if (noteModel.isUpdated()) {
            TextView textView = noteViewHolder.date;
            StringBuilder sb = new StringBuilder();
            sb.append(this.context.getString(R.string.updated_at));
            sb.append(str);
            sb.append(TimeHelper.getTimeAgo(noteModel.getUpdated_at().longValue(), this.context));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = noteViewHolder.date;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.context.getString(R.string.created_at));
            sb2.append(str);
            sb2.append(TimeHelper.getTimeAgo(noteModel.getCreated_at().longValue(), this.context));
            textView2.setText(sb2.toString());
        }
        noteViewHolder.noteColor.setBackgroundColor(Color.parseColor(noteModel.getColor()));
        if (!noteModel.getFirstImagePath().equalsIgnoreCase("")) {
            File file = new File(noteModel.getFirstImagePath());
            if (file.exists()) {
                Glide.with(this.context).load(file).into(noteViewHolder.noteImage);
            } else {
                noteViewHolder.noteImage.setVisibility(8);
                noteViewHolder.textLayout.setPadding(30, 0, 10, 0);
            }
        } else {
            noteViewHolder.noteImage.setVisibility(8);
            noteViewHolder.textLayout.setPadding(30, 0, 10, 0);
        }
        if (this.selectedItems.get(i, false)) {
            noteViewHolder.foregroundView.setBackgroundColor(Color.parseColor("#FFEEB8"));
        } else {
            noteViewHolder.foregroundView.setBackgroundColor(-1);
        }
    }

    public int getItemCount() {
        return this.noteModelArrayList.size();
    }

    public ArrayList<NoteModel> getList() {
        return this.noteModelArrayList;
    }

    public void removeItem(int i) {
        this.noteModelArrayList.remove(i);
        notifyItemRemoved(i);
    }

    public int getSelectedItemCount() {
        return this.selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        ArrayList arrayList = new ArrayList(this.selectedItems.size());
        for (int i = 0; i < this.selectedItems.size(); i++) {
            arrayList.add(Integer.valueOf(this.selectedItems.keyAt(i)));
        }
        return arrayList;
    }

    public void clearSelections() {
        this.selectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleSelection(int i) {
        if (this.selectedItems.get(i, false)) {
            this.selectedItems.delete(i);
        } else {
            this.selectedItems.put(i, true);
        }
        notifyDataSetChanged();
    }
}
