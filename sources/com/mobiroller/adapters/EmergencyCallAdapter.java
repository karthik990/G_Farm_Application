package com.mobiroller.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.viewholders.forms.PhoneViewHolder;
import java.util.ArrayList;

public class EmergencyCallAdapter extends Adapter<PhoneViewHolder> {
    private Activity activity;
    private ArrayList<TableItemsModel> dataList;
    private LocalizationHelper localizationHelper;
    private SharedPrefHelper sharedPrefHelper;
    private int textColor = -1;

    public EmergencyCallAdapter(Activity activity2, ArrayList<TableItemsModel> arrayList, SharedPrefHelper sharedPrefHelper2, LocalizationHelper localizationHelper2) {
        this.activity = activity2;
        this.dataList = arrayList;
        this.sharedPrefHelper = sharedPrefHelper2;
        this.localizationHelper = localizationHelper2;
        if (JSONStorage.getNavigationModel() != null && JSONStorage.getNavigationModel().getMenuTextColor() != null) {
            this.textColor = JSONStorage.getNavigationModel().getMenuTextColor().getColor();
        }
    }

    public PhoneViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PhoneViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_form_phone, viewGroup, false), this.sharedPrefHelper.getActionBarColor());
    }

    public void onBindViewHolder(PhoneViewHolder phoneViewHolder, int i) {
        phoneViewHolder.bindEmergency((TableItemsModel) this.dataList.get(i), this.localizationHelper, this.activity, this.textColor);
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public ArrayList<TableItemsModel> getItems() {
        return this.dataList;
    }
}
