package com.mobiroller.adapters.user;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.viewholders.forms.AddressViewHolder;
import com.mobiroller.viewholders.forms.CheckViewHolder;
import com.mobiroller.viewholders.forms.DateViewHolder;
import com.mobiroller.viewholders.forms.EmailViewHolder;
import com.mobiroller.viewholders.forms.FormBaseViewHolder;
import com.mobiroller.viewholders.forms.LabelViewHolder;
import com.mobiroller.viewholders.forms.SelectionViewHolder;
import com.mobiroller.viewholders.forms.StartRatingViewHolder;
import com.mobiroller.viewholders.forms.SubmitViewHolder;
import com.mobiroller.viewholders.forms.TextAreaViewHolder;
import com.mobiroller.viewholders.forms.TimeViewHolder;
import com.mobiroller.viewholders.updateprofile.TextViewHolder;
import java.util.List;

public class UserUpdateAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_ADDRESS = 9;
    private static final int TYPE_CHECK = 1;
    private static final int TYPE_DATE = 4;
    private static final int TYPE_EMAIL = 8;
    private static final int TYPE_LABEL = 6;
    private static final int TYPE_PHONE = 7;
    private static final int TYPE_SELECTION = 2;
    private static final int TYPE_STAR_RATING = 3;
    private static final int TYPE_SUBMIT = 11;
    private static final int TYPE_TEXT = 10;
    private static final int TYPE_TEXT_AREA = 0;
    private static final int TYPE_TIME = 5;
    private int actionBarColor;
    private Activity activity;
    private int color;
    private LocalizationHelper localizationHelper;
    private List<UserProfileElement> tableItemsModels;

    public UserUpdateAdapter(Activity activity2, List<UserProfileElement> list, LocalizationHelper localizationHelper2, int i) {
        this.activity = activity2;
        this.tableItemsModels = list;
        this.localizationHelper = localizationHelper2;
        this.color = i;
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
                return new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_text, viewGroup, false));
            case 8:
                return new EmailViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_email, viewGroup, false), this.actionBarColor);
            case 9:
                return new AddressViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_address, viewGroup, false), this.actionBarColor);
            case 10:
                return new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_text, viewGroup, false));
            case 11:
                return new SubmitViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_submit, viewGroup, false), this.color);
            default:
                return null;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((FormBaseViewHolder) viewHolder).bind((UserProfileElement) this.tableItemsModels.get(i), this.localizationHelper, this.activity, this.color);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getItemViewType(int r14) {
        /*
            r13 = this;
            java.util.List<com.mobiroller.models.response.UserProfileElement> r0 = r13.tableItemsModels
            java.lang.Object r14 = r0.get(r14)
            com.mobiroller.models.response.UserProfileElement r14 = (com.mobiroller.models.response.UserProfileElement) r14
            java.lang.String r14 = r14.subType
            int r0 = r14.hashCode()
            r1 = 10
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
                case -1715965556: goto L_0x008a;
                case -1147692044: goto L_0x007f;
                case -1004197030: goto L_0x0075;
                case 3076014: goto L_0x006b;
                case 3556653: goto L_0x0060;
                case 3560141: goto L_0x0056;
                case 94627080: goto L_0x004c;
                case 96619420: goto L_0x0042;
                case 102727412: goto L_0x0037;
                case 106642798: goto L_0x002d;
                case 1750277775: goto L_0x0022;
                default: goto L_0x0020;
            }
        L_0x0020:
            goto L_0x0094
        L_0x0022:
            java.lang.String r0 = "starRating"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 3
            goto L_0x0095
        L_0x002d:
            java.lang.String r0 = "phone"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 6
            goto L_0x0095
        L_0x0037:
            java.lang.String r0 = "label"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 8
            goto L_0x0095
        L_0x0042:
            java.lang.String r0 = "email"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 7
            goto L_0x0095
        L_0x004c:
            java.lang.String r0 = "check"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 1
            goto L_0x0095
        L_0x0056:
            java.lang.String r0 = "time"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 5
            goto L_0x0095
        L_0x0060:
            java.lang.String r0 = "text"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 10
            goto L_0x0095
        L_0x006b:
            java.lang.String r0 = "date"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 4
            goto L_0x0095
        L_0x0075:
            java.lang.String r0 = "textArea"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 0
            goto L_0x0095
        L_0x007f:
            java.lang.String r0 = "address"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 9
            goto L_0x0095
        L_0x008a:
            java.lang.String r0 = "selection"
            boolean r14 = r14.equals(r0)
            if (r14 == 0) goto L_0x0094
            r14 = 2
            goto L_0x0095
        L_0x0094:
            r14 = -1
        L_0x0095:
            switch(r14) {
                case 0: goto L_0x00a3;
                case 1: goto L_0x00a2;
                case 2: goto L_0x00a1;
                case 3: goto L_0x00a0;
                case 4: goto L_0x009f;
                case 5: goto L_0x009e;
                case 6: goto L_0x009d;
                case 7: goto L_0x009c;
                case 8: goto L_0x009b;
                case 9: goto L_0x009a;
                case 10: goto L_0x0099;
                default: goto L_0x0098;
            }
        L_0x0098:
            return r12
        L_0x0099:
            return r1
        L_0x009a:
            return r2
        L_0x009b:
            return r3
        L_0x009c:
            return r4
        L_0x009d:
            return r5
        L_0x009e:
            return r6
        L_0x009f:
            return r7
        L_0x00a0:
            return r8
        L_0x00a1:
            return r9
        L_0x00a2:
            return r10
        L_0x00a3:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.adapters.user.UserUpdateAdapter.getItemViewType(int):int");
    }

    public int getItemCount() {
        return this.tableItemsModels.size();
    }
}
