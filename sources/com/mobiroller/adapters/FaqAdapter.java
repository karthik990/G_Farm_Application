package com.mobiroller.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.util.ImageManager;
import java.util.ArrayList;

public class FaqAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public ArrayList<TableItemsModel> dataList;
    /* access modifiers changed from: private */
    public int mActionBarColor = UtilManager.sharedPrefHelper().getActionBarColor();
    private ScreenHelper screenHelper;
    private ScreenModel screenModel;

    private class ContentViewHolder extends ViewHolder {
        TextView title;

        ContentViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.faq_item_question);
        }
    }

    public FaqAdapter(Activity activity2, ArrayList<TableItemsModel> arrayList, ScreenModel screenModel2, ScreenHelper screenHelper2, SharedPrefHelper sharedPrefHelper, LocalizationHelper localizationHelper) {
        this.activity = activity2;
        this.dataList = arrayList;
        this.screenModel = screenModel2;
        this.screenHelper = screenHelper2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ContentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_faq_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
        contentViewHolder.title.setText(UtilManager.localizationHelper().getLocalizedTitle(((TableItemsModel) this.dataList.get(i)).getQuestion()));
        viewHolder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = LayoutInflater.from(FaqAdapter.this.activity).inflate(R.layout.popup_content_faq, null, false);
                EditText editText = (EditText) inflate.findViewById(R.id.faq_popup_answer);
                ((TextView) inflate.findViewById(R.id.faq_popup_question)).setText(UtilManager.localizationHelper().getLocalizedTitle(((TableItemsModel) FaqAdapter.this.dataList.get(i)).getQuestion()));
                inflate.findViewById(R.id.faq_popup_title_layout).setBackgroundColor(FaqAdapter.this.mActionBarColor);
                ((TextView) inflate.findViewById(R.id.faq_pop_close_text_view)).setTextColor(FaqAdapter.this.mActionBarColor);
                editText.setText(UtilManager.localizationHelper().getLocalizedTitle(((TableItemsModel) FaqAdapter.this.dataList.get(i)).getAnswer()));
                final MaterialDialog show = new Builder(FaqAdapter.this.activity).title((CharSequence) UtilManager.localizationHelper().getLocalizedTitle(((TableItemsModel) FaqAdapter.this.dataList.get(i)).getQuestion())).content((CharSequence) UtilManager.localizationHelper().getLocalizedTitle(((TableItemsModel) FaqAdapter.this.dataList.get(i)).getAnswer())).positiveText((int) R.string.close).positiveColor(FaqAdapter.this.mActionBarColor).titleColor(FaqAdapter.this.mActionBarColor).cancelable(true).show();
                inflate.findViewById(R.id.faq_popup_close_layout).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        show.dismiss();
                    }
                });
            }
        });
        if (this.screenModel.getTableFontName() != null) {
            contentViewHolder.title.setTypeface(this.screenHelper.getFontFromAsset(this.screenModel.getTableFontName()));
        }
        if (this.screenModel.getTableTextColor() != null) {
            ScreenHelper screenHelper2 = this.screenHelper;
            contentViewHolder.title.setTextColor(ScreenHelper.setColorUnselected(this.screenModel.getTableTextColor()));
        }
        ImageManager.loadBackgroundImage(this.screenModel.getTableCellBackground().getImageURL(), viewHolder.itemView);
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public ArrayList<TableItemsModel> getItems() {
        return this.dataList;
    }
}
