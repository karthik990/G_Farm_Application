package com.mobiroller.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.fragments.aveFormViewFragment;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.viewholders.forms.AddressViewHolder;
import com.mobiroller.viewholders.forms.CheckViewHolder;
import com.mobiroller.viewholders.forms.DateViewHolder;
import com.mobiroller.viewholders.forms.EmailViewHolder;
import com.mobiroller.viewholders.forms.FormBaseViewHolder;
import com.mobiroller.viewholders.forms.ImagePickerViewHolder;
import com.mobiroller.viewholders.forms.LabelViewHolder;
import com.mobiroller.viewholders.forms.PhoneViewHolder;
import com.mobiroller.viewholders.forms.SelectionViewHolder;
import com.mobiroller.viewholders.forms.StartRatingViewHolder;
import com.mobiroller.viewholders.forms.SubmitViewHolder;
import com.mobiroller.viewholders.forms.TextAreaViewHolder;
import com.mobiroller.viewholders.forms.TimeViewHolder;
import java.util.ArrayList;

public class FormAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_ADDRESS = 9;
    private static final int TYPE_CHECK = 1;
    private static final int TYPE_DATE = 4;
    private static final int TYPE_EMAIL = 8;
    private static final int TYPE_IMAGE = 11;
    private static final int TYPE_LABEL = 6;
    private static final int TYPE_PHONE = 7;
    private static final int TYPE_SELECTION = 2;
    private static final int TYPE_STAR_RATING = 3;
    private static final int TYPE_SUBMIT = 10;
    private static final int TYPE_TEXT_AREA = 0;
    private static final int TYPE_TIME = 5;
    private int actionBarColor;
    private Activity activity;
    private int color;
    private aveFormViewFragment fragment;
    private ArrayList<Object> tableItemsModels;

    public FormAdapter(Activity activity2, ArrayList<Object> arrayList, int i, int i2, aveFormViewFragment aveformviewfragment) {
        this.activity = activity2;
        this.tableItemsModels = arrayList;
        this.color = i;
        this.actionBarColor = i2;
        this.fragment = aveformviewfragment;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                return new TextAreaViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_text_area, viewGroup, false));
            case 1:
                return new CheckViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_check, viewGroup, false));
            case 2:
                return new SelectionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_selection, viewGroup, false));
            case 3:
                return new StartRatingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_star_rating, viewGroup, false));
            case 4:
                return new DateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_date, viewGroup, false));
            case 5:
                return new TimeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_time, viewGroup, false));
            case 6:
                return new LabelViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_label, viewGroup, false), this.actionBarColor);
            case 7:
                return new PhoneViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_phone, viewGroup, false), this.actionBarColor);
            case 8:
                return new EmailViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_email, viewGroup, false), this.actionBarColor);
            case 9:
                return new AddressViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_address, viewGroup, false), this.actionBarColor);
            case 10:
                return new SubmitViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_submit, viewGroup, false), this.color);
            case 11:
                return new ImagePickerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_image_picker, viewGroup, false), this.color, this.fragment);
            default:
                return null;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (this.tableItemsModels.get(i) instanceof TableItemsModel) {
            ((FormBaseViewHolder) viewHolder).bind((TableItemsModel) this.tableItemsModels.get(i), UtilManager.localizationHelper(), this.activity, this.color);
            if (viewHolder instanceof ImagePickerViewHolder) {
                ((ImagePickerViewHolder) viewHolder).setOrder(i);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0034, code lost:
        if (r14.equals("formImage") != false) goto L_0x009f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getItemViewType(int r14) {
        /*
            r13 = this;
            java.util.ArrayList<java.lang.Object> r0 = r13.tableItemsModels
            java.lang.Object r0 = r0.get(r14)
            boolean r0 = r0 instanceof com.mobiroller.models.TableItemsModel
            r1 = 10
            if (r0 == 0) goto L_0x00b1
            java.util.ArrayList<java.lang.Object> r0 = r13.tableItemsModels
            java.lang.Object r14 = r0.get(r14)
            com.mobiroller.models.TableItemsModel r14 = (com.mobiroller.models.TableItemsModel) r14
            java.lang.String r14 = r14.getType()
            int r0 = r14.hashCode()
            r2 = 9
            r3 = 6
            r4 = 8
            r5 = 7
            r6 = 5
            r7 = 4
            r8 = 3
            r9 = 2
            r10 = 1
            r11 = 0
            r12 = -1
            switch(r0) {
                case -1715965556: goto L_0x0094;
                case -1147692044: goto L_0x0089;
                case -1004197030: goto L_0x007f;
                case 3076014: goto L_0x0075;
                case 3560141: goto L_0x006b;
                case 94627080: goto L_0x0061;
                case 96619420: goto L_0x0057;
                case 102727412: goto L_0x004c;
                case 106642798: goto L_0x0042;
                case 1750277775: goto L_0x0038;
                case 1789200119: goto L_0x002e;
                default: goto L_0x002c;
            }
        L_0x002c:
            goto L_0x009e
        L_0x002e:
            java.lang.String r0 = "formImage"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            goto L_0x009f
        L_0x0038:
            java.lang.String r0 = "starRating"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 3
            goto L_0x009f
        L_0x0042:
            java.lang.String r0 = "phone"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 6
            goto L_0x009f
        L_0x004c:
            java.lang.String r0 = "label"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 8
            goto L_0x009f
        L_0x0057:
            java.lang.String r0 = "email"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 7
            goto L_0x009f
        L_0x0061:
            java.lang.String r0 = "check"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 1
            goto L_0x009f
        L_0x006b:
            java.lang.String r0 = "time"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 5
            goto L_0x009f
        L_0x0075:
            java.lang.String r0 = "date"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 4
            goto L_0x009f
        L_0x007f:
            java.lang.String r0 = "textArea"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 0
            goto L_0x009f
        L_0x0089:
            java.lang.String r0 = "address"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 9
            goto L_0x009f
        L_0x0094:
            java.lang.String r0 = "selection"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x009e
            r1 = 2
            goto L_0x009f
        L_0x009e:
            r1 = -1
        L_0x009f:
            switch(r1) {
                case 0: goto L_0x00af;
                case 1: goto L_0x00ae;
                case 2: goto L_0x00ad;
                case 3: goto L_0x00ac;
                case 4: goto L_0x00ab;
                case 5: goto L_0x00aa;
                case 6: goto L_0x00a9;
                case 7: goto L_0x00a8;
                case 8: goto L_0x00a7;
                case 9: goto L_0x00a6;
                case 10: goto L_0x00a3;
                default: goto L_0x00a2;
            }
        L_0x00a2:
            goto L_0x00b0
        L_0x00a3:
            r14 = 11
            return r14
        L_0x00a6:
            return r2
        L_0x00a7:
            return r3
        L_0x00a8:
            return r4
        L_0x00a9:
            return r5
        L_0x00aa:
            return r6
        L_0x00ab:
            return r7
        L_0x00ac:
            return r8
        L_0x00ad:
            return r9
        L_0x00ae:
            return r10
        L_0x00af:
            return r11
        L_0x00b0:
            return r12
        L_0x00b1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.adapters.FormAdapter.getItemViewType(int):int");
    }

    public int getItemCount() {
        return this.tableItemsModels.size();
    }

    public ArrayList<Object> getItems() {
        return this.tableItemsModels;
    }
}
