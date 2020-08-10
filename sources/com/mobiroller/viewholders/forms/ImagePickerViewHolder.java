package com.mobiroller.viewholders.forms;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.fragments.aveFormViewFragment;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.events.FormImageEvent;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.SquareImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ImagePickerViewHolder extends FormBaseViewHolder {
    private int actionBarColor;
    private Activity activity;
    private aveFormViewFragment fragment;
    @BindView(2131362426)
    SquareImageView image;
    @BindView(2131362427)
    RelativeLayout imageMainLayout;
    private ImageView imageView;
    private LocalizationHelper localizationHelper;
    private int order;
    @BindView(2131362428)
    ImageView removeImageView;
    private TableItemsModel tableItemsModel;
    @BindView(2131362450)
    TextView title;
    private UserProfileElement userProfileItem;

    public String getValue() {
        return null;
    }

    public boolean isImage() {
        return true;
    }

    public void setValue(String str) {
    }

    public ImagePickerViewHolder(View view, int i, aveFormViewFragment aveformviewfragment) {
        super(view);
        this.fragment = aveformviewfragment;
        this.actionBarColor = i;
        ButterKnife.bind((Object) this, view);
        EventBus.getDefault().register(this);
    }

    public void bind(TableItemsModel tableItemsModel2, LocalizationHelper localizationHelper2, Activity activity2, int i) {
        this.tableItemsModel = tableItemsModel2;
        this.localizationHelper = localizationHelper2;
        this.activity = activity2;
        this.tableItemsModel = tableItemsModel2;
        this.title.setText(localizationHelper2.getLocalizedTitle(tableItemsModel2.getTitle()));
        this.imageMainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImagePickerViewHolder.this.openGallery();
            }
        });
        this.removeImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImagePickerViewHolder.this.clear();
            }
        });
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        this.imageView = new ImageView(activity2);
        this.imageView.setLayoutParams(layoutParams);
        this.imageView.setPadding(8, 8, 8, 8);
        LayoutParams layoutParams2 = (LayoutParams) this.imageView.getLayoutParams();
        layoutParams2.addRule(13, -1);
        this.imageView.setLayoutParams(layoutParams2);
        this.imageView.setBackgroundColor(Color.parseColor("#A6AFB8"));
        this.imageView.setImageResource(R.drawable.icon_add);
        this.imageMainLayout.addView(this.imageView);
    }

    public void bind(UserProfileElement userProfileElement, LocalizationHelper localizationHelper2, Activity activity2, int i) {
        this.userProfileItem = userProfileElement;
    }

    public String getId() {
        TableItemsModel tableItemsModel2 = this.tableItemsModel;
        if (tableItemsModel2 != null) {
            return tableItemsModel2.getId();
        }
        return this.userProfileItem.f2188id;
    }

    public byte[] getImage() {
        if (this.image.getDrawable() == null) {
            return null;
        }
        Bitmap bitmap = ((BitmapDrawable) this.image.getDrawable()).getBitmap();
        if (bitmap.getHeight() > 1024 || bitmap.getWidth() > 1024) {
            bitmap = ImageManager.resize(bitmap, 1024, 1024);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 70, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public boolean isValid() {
        if (!this.tableItemsModel.getMandatory().equalsIgnoreCase("yes") || this.image.getDrawable() != null) {
            this.title.setText(this.localizationHelper.getLocalizedTitle(this.tableItemsModel.getTitle()));
            return true;
        }
        TextView textView = this.title;
        StringBuilder sb = new StringBuilder();
        sb.append(this.localizationHelper.getLocalizedTitle(this.tableItemsModel.getTitle()));
        sb.append(" - <font color='red'>");
        sb.append(this.activity.getResources().getString(R.string.required_field));
        sb.append("</font>");
        textView.setText(Html.fromHtml(sb.toString()));
        return false;
    }

    public void clear() {
        this.image.setImageDrawable(null);
        this.imageView.setVisibility(0);
        this.removeImageView.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void openGallery() {
        this.fragment.getActivity().startActivityForResult(CropImage.activity().setGuidelines(Guidelines.ON).getIntent(this.activity), this.order);
    }

    public void setOrder(int i) {
        this.order = i;
    }

    @Subscribe
    public void onFormImageEvent(FormImageEvent formImageEvent) {
        if (formImageEvent.getId() == this.order && formImageEvent.getImagePath() != null) {
            if (Integer.parseInt(String.valueOf(new File(formImageEvent.getImagePath().getPath()).length() / 2592)) > 2592) {
                Activity activity2 = this.activity;
                Toast.makeText(activity2, activity2.getResources().getString(R.string.image_size_validation), 0).show();
                return;
            }
            this.image.setImageURI(formImageEvent.getImagePath());
            this.imageView.setVisibility(8);
            this.removeImageView.setVisibility(0);
        }
    }
}
