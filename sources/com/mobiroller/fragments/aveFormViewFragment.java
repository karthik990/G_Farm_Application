package com.mobiroller.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import androidx.browser.trusted.sharing.ShareTarget;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.adapters.FormAdapter;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RxJavaRequestHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.SubmitModel;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.events.FormImageEvent;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ImageManager;
import com.mobiroller.viewholders.forms.FormBaseViewHolder;
import com.mobiroller.viewholders.forms.SubmitViewHolder;
import com.mobiroller.views.CustomHorizontalScrollView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aveFormViewFragment extends BaseModuleFragment {
    /* access modifiers changed from: private */
    public FormAdapter adapter;
    /* access modifiers changed from: private */
    public ArrayList<Object> dataList = new ArrayList<>();
    @BindView(2131362451)
    RelativeLayout formLayout;
    @BindView(2131362452)
    RelativeLayout formLayoutOverlay;
    @BindView(2131362453)
    RecyclerView formList;
    @BindView(2131362454)
    RelativeLayout formMainLayout;
    private boolean hasImage = false;
    @BindView(2131362429)
    ImageView imgView;
    private boolean isSubmitAvailable;
    @BindView(2131362456)
    TextView mFormText;
    private Parcelable mListState;
    ProgressViewHelper progressViewHelper;
    @BindView(2131362455)
    CustomHorizontalScrollView scrollView;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_form, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        return inflate;
    }

    private void runThread() {
        Iterator it = this.screenModel.getTableItems().iterator();
        while (it.hasNext()) {
            if (((TableItemsModel) it.next()).getType().equalsIgnoreCase("formImage")) {
                this.hasImage = true;
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (aveFormViewFragment.this.adapter == null) {
                    aveFormViewFragment.this.dataList.addAll(aveFormViewFragment.this.screenModel.getTableItems());
                    aveFormViewFragment aveformviewfragment = aveFormViewFragment.this;
                    aveformviewfragment.setFormAdapter(aveformviewfragment.dataList, false);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (this.formLayout != null) {
            this.bannerHelper.addBannerAd(this.formMainLayout, this.formLayoutOverlay);
        }
        runThread();
    }

    @Subscribe
    public void subscribeSubmitModel(SubmitModel submitModel) {
        submitForm();
    }

    private void loadUi() {
        try {
            if (this.isSubmitAvailable) {
                ImageManager.loadBackgroundImageFromImageModel(this.formLayoutOverlay, this.screenModel.getBackgroundImageName());
                this.scrollView.setBackgroundColor(0);
                int applyDimension = (int) TypedValue.applyDimension(1, 13.0f, getResources().getDisplayMetrics());
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.formList.getLayoutParams();
                marginLayoutParams.setMargins(applyDimension, applyDimension, applyDimension, 0);
                this.formList.setLayoutParams(marginLayoutParams);
                LayoutParams layoutParams = (LayoutParams) this.scrollView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, applyDimension);
                this.scrollView.setLayoutParams(layoutParams);
            } else {
                this.scrollView.setBackgroundColor(-1);
            }
            if (this.screenModel.getMainImageName() != null) {
                this.componentHelper.setMainImage(getActivity(), this.imgView, this.screenModel);
                this.imgView.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    /* access modifiers changed from: private */
    public void submitForm() {
        if (!NetworkHelper.isConnected(MobiRollerApplication.app)) {
            DialogUtil.showNoConnectionInfo(getActivity());
            return;
        }
        int i = 0;
        boolean z = true;
        for (int i2 = 0; i2 < this.adapter.getItemCount() - 1; i2++) {
            if (!((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i2)).isValid()) {
                z = false;
            }
        }
        closeKeyboard();
        final CircularProgressButton submitButton = ((SubmitViewHolder) this.formList.findViewHolderForAdapterPosition(this.adapter.getItemCount() - 1)).getSubmitButton();
        if (z && !this.hasImage) {
            submitButton.startAnimation();
            HashMap hashMap = new HashMap();
            while (i < this.adapter.getItemCount() - 1) {
                hashMap.put(((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getId(), ((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getValue());
                i++;
            }
            new RxJavaRequestHelper(getActivity(), this.sharedPrefHelper).getAPIService().sendForm(hashMap).enqueue(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    submitButton.revertAnimation();
                    new Builder(aveFormViewFragment.this.getActivity()).title((int) R.string.success).content((int) R.string.form_success).positiveText((CharSequence) aveFormViewFragment.this.getString(R.string.OK)).show();
                    for (int i = 0; i < aveFormViewFragment.this.adapter.getItemCount() - 1; i++) {
                        ((FormBaseViewHolder) aveFormViewFragment.this.formList.findViewHolderForAdapterPosition(i)).clear();
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable th) {
                    submitButton.revertAnimation();
                    new Builder(aveFormViewFragment.this.getActivity()).title((int) R.string.failed).content((int) R.string.form_failed).positiveText((int) R.string.OK).negativeText((int) R.string.try_again).onNegative(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            aveFormViewFragment.this.submitForm();
                        }
                    }).show();
                }
            });
        } else if (z) {
            submitButton.startAnimation();
            HashMap hashMap2 = new HashMap();
            ArrayList arrayList = new ArrayList();
            while (i < this.adapter.getItemCount() - 1) {
                if (!((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).isImage()) {
                    hashMap2.put(((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getId(), RequestBody.create(MediaType.parse(ShareTarget.ENCODING_TYPE_MULTIPART), ((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getValue()));
                } else if (((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getImage() != null) {
                    arrayList.add(Part.createFormData(((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getId(), "photo.png", RequestBody.create(MediaType.parse("image/*"), ((FormBaseViewHolder) this.formList.findViewHolderForAdapterPosition(i)).getImage())));
                }
                i++;
            }
            new RxJavaRequestHelper(getActivity(), this.sharedPrefHelper).getAPIService().sendFormWithImages(hashMap2, arrayList).enqueue(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    submitButton.revertAnimation();
                    new Builder(aveFormViewFragment.this.getActivity()).title((int) R.string.success).content((int) R.string.form_success).positiveText((CharSequence) aveFormViewFragment.this.getString(R.string.OK)).show();
                    for (int i = 0; i < aveFormViewFragment.this.adapter.getItemCount() - 1; i++) {
                        ((FormBaseViewHolder) aveFormViewFragment.this.formList.findViewHolderForAdapterPosition(i)).clear();
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable th) {
                    submitButton.revertAnimation();
                    new Builder(aveFormViewFragment.this.getActivity()).title((int) R.string.failed).content((int) R.string.form_failed).positiveText((int) R.string.OK).negativeText((int) R.string.try_again).onNegative(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            aveFormViewFragment.this.submitForm();
                        }
                    }).show();
                }
            });
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StringBuilder sb = new StringBuilder();
        sb.append(this.screenId);
        sb.append("list");
        bundle.putSerializable(sb.toString(), this.adapter.getItems());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.screenId);
        sb2.append("isSubmitAvailable");
        bundle.putBoolean(sb2.toString(), this.isSubmitAvailable);
        this.mListState = this.formList.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable(String.valueOf(this.screenId), this.mListState);
    }

    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            this.mListState = bundle.getParcelable(String.valueOf(this.screenId));
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(this.screenId));
            sb.append("isSubmitAvailable");
            this.isSubmitAvailable = bundle.getBoolean(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(String.valueOf(this.screenId));
            sb2.append("list");
            setFormAdapter((ArrayList) bundle.getSerializable(sb2.toString()), true);
            this.formList.getLayoutManager().onRestoreInstanceState(this.mListState);
        }
    }

    /* access modifiers changed from: private */
    public void setFormAdapter(ArrayList<Object> arrayList, boolean z) {
        validateFormFields();
        if (z) {
            this.dataList.addAll(arrayList);
        }
        if (!z && this.screenModel.getSubmitAvailable().equalsIgnoreCase("YES")) {
            this.isSubmitAvailable = true;
            this.dataList.add(new SubmitModel());
        }
        if (!this.screenModel.getContentText().isEmpty()) {
            if (this.isSubmitAvailable || z) {
                this.mFormText.setText(this.localizationHelper.getLocalizedTitle(this.screenModel.getContentText()));
                this.mFormText.setVisibility(0);
            } else {
                TableItemsModel tableItemsModel = new TableItemsModel();
                tableItemsModel.setType("address");
                tableItemsModel.setValue(this.screenModel.getContentText());
                this.dataList.add(0, tableItemsModel);
            }
        }
        loadUi();
        if (this.isSubmitAvailable) {
            String str = "#505050";
            FormAdapter formAdapter = new FormAdapter(getActivity(), this.dataList, Color.parseColor(str), Color.parseColor(str), this);
            this.adapter = formAdapter;
        } else {
            FormAdapter formAdapter2 = new FormAdapter(getActivity(), this.dataList, this.sharedPrefHelper.getActionBarColor(), this.sharedPrefHelper.getActionBarColor(), this);
            this.adapter = formAdapter2;
        }
        this.formList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.formList.setAdapter(this.adapter);
        this.formList.setNestedScrollingEnabled(false);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (intent != null) {
            ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (activityResult != null && i2 == -1) {
                EventBus.getDefault().post(new FormImageEvent(i, activityResult.getUri()));
            } else if (i2 == 204) {
                activityResult.getError();
            }
        }
    }

    private void validateFormFields() {
        ArrayList arrayList = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.form_fields_validation)));
        if (this.screenModel.getTableItems() != null && this.screenModel.getTableItems().size() != 0) {
            Iterator it = this.screenModel.getTableItems().iterator();
            while (it.hasNext()) {
                TableItemsModel tableItemsModel = (TableItemsModel) it.next();
                if (!arrayList.contains(tableItemsModel.getType())) {
                    this.screenModel.getTableItems().remove(tableItemsModel);
                }
            }
        }
    }
}
