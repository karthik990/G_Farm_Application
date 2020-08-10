package com.mobiroller.adapters.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.response.UserProfileElement;
import java.util.List;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public class UserProfileAdapter extends Adapter<ViewHolder> {
    Context context;
    List<UserProfileElement> data;
    LocalizationHelper localizationHelper = UtilManager.localizationHelper();

    public class UserUpdateViewHolder extends ViewHolder {
        View divider;
        TextView title;
        TextView value;

        UserUpdateViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.value = (TextView) view.findViewById(R.id.value);
            this.divider = view.findViewById(R.id.divider);
        }
    }

    public UserProfileAdapter(List<UserProfileElement> list, Context context2) {
        this.data = list;
        this.context = context2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new UserUpdateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_personal_data_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        UserProfileElement userProfileElement = (UserProfileElement) this.data.get(i);
        if (userProfileElement.title.contains("<")) {
            ((UserUpdateViewHolder) viewHolder).title.setText(this.localizationHelper.getLocalizedTitle(userProfileElement.title));
        } else {
            ((UserUpdateViewHolder) viewHolder).title.setText(toCamelCase(userProfileElement.title));
        }
        if (userProfileElement.subType == null || !userProfileElement.subType.equalsIgnoreCase("check")) {
            ((UserUpdateViewHolder) viewHolder).value.setText(userProfileElement.value);
        } else if (userProfileElement.value.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) || userProfileElement.value.equalsIgnoreCase("yes")) {
            ((UserUpdateViewHolder) viewHolder).value.setText(this.context.getString(R.string.yes));
        } else {
            ((UserUpdateViewHolder) viewHolder).value.setText(this.context.getString(R.string.no));
        }
        if (i == getItemCount() - 1) {
            ((UserUpdateViewHolder) viewHolder).divider.setVisibility(8);
        }
    }

    static String toCamelCase(String str) {
        String[] split;
        String str2 = "";
        for (String str3 : str.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(toProperCase(str3));
            str2 = sb.toString();
        }
        return str2;
    }

    static String toProperCase(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 1).toUpperCase());
        sb.append(str.substring(1).toLowerCase());
        return sb.toString();
    }

    public int getItemCount() {
        return this.data.size();
    }
}
