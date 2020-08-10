package com.mobiroller.activities.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.p009v4.media.session.PlaybackStateCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.mobiroller.activities.ConnectionRequired;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.user.UserUpdateAdapter;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.FirebaseUserHelper;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.ProfileUpdateEvent;
import com.mobiroller.models.response.APIError;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ImageManager;
import com.mobiroller.viewholders.forms.FormBaseViewHolder;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateActivity extends AveActivity {
    public static final String FILL_NAME = "fill_name";
    private FirebaseChatHelper firebaseChatHelper;
    /* access modifiers changed from: private */
    public boolean hasImage = false;
    private boolean imageChanged = false;
    /* access modifiers changed from: private */
    public String imageId;
    /* access modifiers changed from: private */
    public boolean imageIsLoaded = false;
    /* access modifiers changed from: private */
    public boolean imageIsMandatory = false;
    private Uri imageResultUri;
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    NetworkHelper networkHelper;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131363276)
    MobirollerToolbar toolbarTop;
    /* access modifiers changed from: private */
    public int userNameOrder = -1;
    @BindView(2131363356)
    ImageView userOpenGalleryImageView;
    /* access modifiers changed from: private */
    public List<UserProfileElement> userProfileItems;
    /* access modifiers changed from: private */
    public UserUpdateAdapter userUpdateAdapter;
    @BindView(2131363352)
    RelativeLayout userUpdateForm;
    @BindView(2131363353)
    RelativeLayout userUpdateHeader;
    @BindView(2131363355)
    RecyclerView userUpdateList;
    @BindView(2131363357)
    CircleImageView userUpdateProfileImage;

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_update);
        ButterKnife.bind((Activity) this);
        this.firebaseChatHelper = new FirebaseChatHelper(getApplicationContext());
        if (!this.networkHelper.isConnected()) {
            Intent intent = new Intent(this, ConnectionRequired.class);
            intent.putExtra("intent", getIntent());
            startActivity(intent);
            finish();
        }
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        ToolbarHelper.setToolbar(this, this.toolbarTop);
        this.toolbarHelper.setToolbarTitle(this, getString(R.string.profile));
        closeKeyboard();
        getUserProfileItems();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void getUserProfileItems() {
        Call userProfileElements = new RequestHelper().getApplyzeUserAPIService().getUserProfileElements(getString(R.string.applyze_api_key), getString(R.string.applyze_app_key));
        this.progressViewHelper.show();
        userProfileElements.enqueue(new Callback<List<UserProfileElement>>() {
            public void onResponse(Call<List<UserProfileElement>> call, Response<List<UserProfileElement>> response) {
                if (response.body() != null) {
                    UserUpdateActivity.this.userProfileItems = (List) response.body();
                    Collections.sort(UserUpdateActivity.this.userProfileItems);
                    int i = 0;
                    while (i < UserUpdateActivity.this.userProfileItems.size()) {
                        if (((UserProfileElement) UserUpdateActivity.this.userProfileItems.get(i)).type.equalsIgnoreCase("photo")) {
                            UserUpdateActivity userUpdateActivity = UserUpdateActivity.this;
                            userUpdateActivity.imageId = ((UserProfileElement) userUpdateActivity.userProfileItems.get(i)).f2188id;
                            UserUpdateActivity userUpdateActivity2 = UserUpdateActivity.this;
                            userUpdateActivity2.imageIsMandatory = ((UserProfileElement) userUpdateActivity2.userProfileItems.get(i)).mandotory;
                            UserUpdateActivity.this.userProfileItems.remove(i);
                            UserUpdateActivity.this.hasImage = true;
                            i--;
                            UserUpdateActivity.this.userUpdateHeader.setVisibility(0);
                        } else if (((UserProfileElement) UserUpdateActivity.this.userProfileItems.get(i)).type.equalsIgnoreCase("nameSurname")) {
                            UserUpdateActivity.this.userNameOrder = i;
                        }
                        i++;
                    }
                    UserUpdateActivity.this.setAdapterItems();
                }
                UserUpdateActivity.this.progressViewHelper.dismiss();
            }

            public void onFailure(Call<List<UserProfileElement>> call, Throwable th) {
                UserUpdateActivity.this.progressViewHelper.dismiss();
            }
        });
    }

    public void setAdapterItems() {
        if (getIntent().hasExtra(FILL_NAME)) {
            new Builder(this).content((int) R.string.fill_required_fields).positiveText((int) R.string.OK).show();
        }
        if (this.hasImage) {
            this.userUpdateProfileImage.setBorderColor(this.sharedPrefHelper.getActionBarColor());
            this.userOpenGalleryImageView.setColorFilter(this.sharedPrefHelper.getActionBarColor());
            if (!UserHelper.getUserImageUrl().equalsIgnoreCase("")) {
                Glide.with((FragmentActivity) this).load(UserHelper.getUserImageUrl()).addListener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        UserUpdateActivity.this.userUpdateProfileImage.setImageDrawable(ContextCompat.getDrawable(UserUpdateActivity.this, R.drawable.ic_person_white_24dp));
                        UserUpdateActivity.this.imageIsLoaded = false;
                        return false;
                    }

                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        UserUpdateActivity.this.imageIsLoaded = true;
                        return false;
                    }
                }).into((ImageView) this.userUpdateProfileImage);
            }
        } else {
            this.userUpdateHeader.setVisibility(8);
        }
        this.userUpdateAdapter = new UserUpdateAdapter(this, this.userProfileItems, this.localizationHelper, Color.parseColor("#505050"));
        this.userUpdateList.setAdapter(this.userUpdateAdapter);
        this.userUpdateList.setLayoutManager(new LinearLayoutManager(this));
        this.userUpdateList.setNestedScrollingEnabled(false);
        this.userUpdateAdapter.notifyDataSetChanged();
        this.userUpdateList.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < UserUpdateActivity.this.userUpdateAdapter.getItemCount(); i++) {
                    if (((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getId() != null) {
                        SharedPreferences sharedPrefs = UserUpdateActivity.this.sharedPrefHelper.getSharedPrefs(UserUpdateActivity.this);
                        StringBuilder sb = new StringBuilder();
                        sb.append(UserUpdateActivity.this.sharedPrefHelper.getUserId());
                        String str = "_id_";
                        sb.append(str);
                        sb.append(((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getId());
                        if (sharedPrefs.contains(sb.toString())) {
                            FormBaseViewHolder formBaseViewHolder = (FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i);
                            SharedPreferences sharedPrefs2 = UserUpdateActivity.this.sharedPrefHelper.getSharedPrefs(UserUpdateActivity.this);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(UserUpdateActivity.this.sharedPrefHelper.getUserId());
                            sb2.append(str);
                            sb2.append(((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getId());
                            formBaseViewHolder.setValue(sharedPrefs2.getString(sb2.toString(), ""));
                        }
                    }
                }
            }
        }, 150);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.toolbarTop.inflateMenu(R.menu.note_add_menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_save) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (isValid()) {
            if (this.networkHelper.isConnected()) {
                updateProfile();
            } else {
                DialogUtil.showNoConnectionInfo(this);
            }
        }
        return true;
    }

    public boolean isValid() {
        closeKeyboard();
        for (int i = 0; i < this.userUpdateAdapter.getItemCount(); i++) {
            if (!((FormBaseViewHolder) this.userUpdateList.findViewHolderForAdapterPosition(i)).isValid()) {
                return false;
            }
        }
        if (!this.imageIsMandatory || this.imageIsLoaded) {
            return true;
        }
        DialogUtil.showDialog(this, getString(R.string.image_required));
        return false;
    }

    public void updateProfile() {
        HashMap hashMap = new HashMap();
        hashMap.put("apiKey", createPartFromString(getString(R.string.applyze_api_key)));
        hashMap.put("appKey", createPartFromString(getString(R.string.applyze_app_key)));
        hashMap.put("device", createPartFromString(this.sharedPrefHelper.getDeviceModel()));
        hashMap.put(TtmlNode.ATTR_ID, createPartFromString(UserHelper.getUserId()));
        hashMap.put("sessionKey", createPartFromString(UserHelper.getUserSessionToken()));
        hashMap.put("lang", createPartFromString(LocaleHelper.getLocale().toUpperCase()));
        hashMap.put("udid", createPartFromString(this.sharedPrefHelper.getDeviceId()));
        for (int i = 0; i < this.userUpdateAdapter.getItemCount(); i++) {
            hashMap.put(((FormBaseViewHolder) this.userUpdateList.findViewHolderForAdapterPosition(i)).getId(), createPartFromString(((FormBaseViewHolder) this.userUpdateList.findViewHolderForAdapterPosition(i)).getValue()));
        }
        this.progressViewHelper.show();
        closeKeyboard();
        ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService();
        if (!this.hasImage || !this.imageChanged || getImage() == null) {
            applyzeUserAPIService.updateUser(hashMap).enqueue(new Callback<UserLoginResponse>() {
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    UserUpdateActivity.this.progressViewHelper.dismiss();
                    if (response.isSuccessful()) {
                        for (int i = 0; i < UserUpdateActivity.this.userUpdateAdapter.getItemCount(); i++) {
                            if (!(UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i) == null || ((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getValue() == null)) {
                                Editor edit = UserUpdateActivity.this.sharedPrefHelper.getSharedPrefs(UserUpdateActivity.this).edit();
                                StringBuilder sb = new StringBuilder();
                                sb.append(UserUpdateActivity.this.sharedPrefHelper.getUserId());
                                sb.append("_id_");
                                sb.append(((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getId());
                                edit.putString(sb.toString(), ((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getValue()).apply();
                            }
                        }
                        for (UserProfileElement userProfileElement : ((UserLoginResponse) response.body()).profileValues) {
                            if (userProfileElement.type.equalsIgnoreCase("photo")) {
                                UserHelper.setUserImageUrl(userProfileElement.value);
                            }
                        }
                        UserUpdateActivity.this.saveInfo((UserLoginResponse) response.body());
                        if (!UserUpdateActivity.this.sharedPrefHelper.getChatValidated() || !UserUpdateActivity.this.sharedPrefHelper.getIsChatActive() || !UserUpdateActivity.this.sharedPrefHelper.getUserIsAvailableForChat() || FirebaseAuth.getInstance() == null || FirebaseAuth.getInstance().getCurrentUser() == null) {
                            UserUpdateActivity.this.showSuccessDialog();
                        }
                        if (UserUpdateActivity.this.userUpdateList != null && UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(UserUpdateActivity.this.userNameOrder) != null) {
                            EventBus.getDefault().post(new ProfileUpdateEvent(((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(UserUpdateActivity.this.userNameOrder)).getValue()));
                            return;
                        }
                        return;
                    }
                    APIError parseError = ErrorUtils.parseError(response);
                    if (!UserUpdateActivity.this.isFinishing()) {
                        new Builder(UserUpdateActivity.this).content((CharSequence) parseError.message()).positiveText((int) R.string.OK).show();
                    }
                }

                public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                    UserUpdateActivity.this.progressViewHelper.dismiss();
                    UserUpdateActivity.this.showErrorDialog();
                }
            });
        } else {
            applyzeUserAPIService.updateUser(hashMap, getImage()).enqueue(new Callback<UserLoginResponse>() {
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    String str;
                    if (response.isSuccessful()) {
                        for (int i = 0; i < UserUpdateActivity.this.userUpdateAdapter.getItemCount(); i++) {
                            if (!(UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i) == null || ((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getValue() == null)) {
                                Editor edit = UserUpdateActivity.this.sharedPrefHelper.getSharedPrefs(UserUpdateActivity.this).edit();
                                StringBuilder sb = new StringBuilder();
                                sb.append(UserUpdateActivity.this.sharedPrefHelper.getUserId());
                                sb.append("_id_");
                                sb.append(((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getId());
                                edit.putString(sb.toString(), ((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(i)).getValue()).apply();
                            }
                        }
                        for (UserProfileElement userProfileElement : ((UserLoginResponse) response.body()).profileValues) {
                            if (userProfileElement.type.equalsIgnoreCase("photo")) {
                                UserHelper.setUserImageUrl(userProfileElement.value);
                            }
                        }
                        UserUpdateActivity.this.saveInfo((UserLoginResponse) response.body());
                        if (!UserUpdateActivity.this.sharedPrefHelper.getChatValidated() || !UserUpdateActivity.this.sharedPrefHelper.getIsChatActive() || !UserUpdateActivity.this.sharedPrefHelper.getUserIsAvailableForChat() || FirebaseAuth.getInstance() == null || FirebaseAuth.getInstance().getCurrentUser() == null) {
                            UserUpdateActivity.this.showSuccessDialog();
                        }
                        EventBus.getDefault().post(new ProfileUpdateEvent(UserHelper.getUserImageUrl(), ((FormBaseViewHolder) UserUpdateActivity.this.userUpdateList.findViewHolderForAdapterPosition(UserUpdateActivity.this.userNameOrder)).getValue()));
                    } else {
                        try {
                            str = ErrorUtils.parseError(response).message();
                        } catch (Exception unused) {
                            str = UserUpdateActivity.this.getString(R.string.common_error);
                        }
                        if (!UserUpdateActivity.this.isFinishing()) {
                            new Builder(UserUpdateActivity.this).content((CharSequence) str).positiveText((int) R.string.OK).show();
                        }
                    }
                    UserUpdateActivity.this.progressViewHelper.dismiss();
                }

                public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                    UserUpdateActivity.this.progressViewHelper.dismiss();
                    UserUpdateActivity.this.showErrorDialog();
                }
            });
        }
    }

    public Part getImage() {
        try {
            return Part.createFormData(this.imageId, new File(this.imageResultUri.getPath()).getName(), RequestBody.create(MediaType.parse("image/*"), getImageByte()));
        } catch (Exception unused) {
            return null;
        }
    }

    public byte[] getImageByte() {
        if (this.userUpdateProfileImage.getDrawable() == null) {
            return null;
        }
        Bitmap bitmap = ((BitmapDrawable) this.userUpdateProfileImage.getDrawable()).getBitmap();
        if (bitmap.getHeight() > 1024 || bitmap.getWidth() > 1024) {
            bitmap = ImageManager.resize(bitmap, 1024, 1024);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 70, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private RequestBody createPartFromString(String str) {
        if (str == null) {
            return null;
        }
        return RequestBody.create(MultipartBody.FORM, str);
    }

    @OnClick({2131363354})
    public void openGalleryIntent() {
        UserUpdateActivityPermissionsDispatcher.openGalleryWithPermissionCheck(this);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        UserUpdateActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    public void openGallery() {
        CropImage.activity().setAspectRatio(1, 1).setFixAspectRatio(true).setGuidelines(Guidelines.ON).start(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 203) {
            ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1) {
                this.imageResultUri = activityResult.getUri();
                Uri uri = this.imageResultUri;
                if (uri == null) {
                    return;
                }
                if (Integer.parseInt(String.valueOf(new File(uri.getPath()).length() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) > 1024) {
                    Toast.makeText(this, getResources().getString(R.string.image_size_validation), 0).show();
                    return;
                }
                this.userUpdateProfileImage.setImageURI(this.imageResultUri);
                this.imageChanged = true;
                this.imageIsLoaded = true;
            } else if (i2 == 204) {
                activityResult.getError();
            }
        }
    }

    public void saveInfo(UserLoginResponse userLoginResponse) {
        UserHelper.saveLoginCredentials(this, userLoginResponse);
        if (this.sharedPrefHelper.getChatValidated() && this.sharedPrefHelper.getIsChatActive() && this.sharedPrefHelper.getUserIsAvailableForChat() && FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUserHelper.updateUserProfile(UserHelper.getUserName(), Uri.parse(UserHelper.getUserImageUrl()), false, this);
            this.firebaseChatHelper.updateUser(userLoginResponse);
        }
        showSuccessDialog();
    }

    /* access modifiers changed from: private */
    public void showSuccessDialog() {
        if (!isFinishing()) {
            new Builder(this).content((int) R.string.profile_updated).positiveText((int) R.string.OK).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    UserUpdateActivity.this.finish();
                }
            }).show();
        }
    }

    /* access modifiers changed from: private */
    public void showErrorDialog() {
        if (!isFinishing()) {
            DialogUtil.showDialog(this, getString(R.string.common_error));
        }
    }
}
