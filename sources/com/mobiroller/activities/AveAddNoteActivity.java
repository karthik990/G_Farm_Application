package com.mobiroller.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog.Builder;
import com.afollestad.materialdialogs.color.ColorChooserDialog.ColorCallback;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.NoteColorAdapter;
import com.mobiroller.adapters.NoteImageAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NoteModel;
import com.mobiroller.util.NoteUtil;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.util.ArrayList;
import java.util.Arrays;

public class AveAddNoteActivity extends AveActivity implements ColorCallback {
    public static final String NOTE_MODEL = "NoteModel";
    public static final String NOTE_MODEL_IMAGES = "NoteModelImages";
    public static final String NOTE_MODEL_POSITION = "NoteModelPosition";
    public static final int PICK_IMAGE_MULTIPLE = 1;
    @BindView(2131362804)
    RecyclerView colorRecyclerView;
    @BindView(2131362171)
    TextView colorTextview;
    @BindView(2131362806)
    EditText description;
    @BindView(2131362812)
    RecyclerView imageRecyclerView;
    @BindView(2131362544)
    TextView imagesTextview;
    private boolean isUpdate = false;
    /* access modifiers changed from: private */
    public NoteColorAdapter noteColorAdapter;
    /* access modifiers changed from: private */
    public ArrayList<String> noteColorList;
    private NoteImageAdapter noteImageAdapter;
    /* access modifiers changed from: private */
    public NoteModel noteModel = new NoteModel();
    private int noteModelPosition = -1;
    private String screenId;
    @BindView(2131362817)
    EditText title;
    @BindView(2131363276)
    MobirollerToolbar toolbarTop;

    public void onColorChooserDismissed(ColorChooserDialog colorChooserDialog) {
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_add_note);
        ButterKnife.bind((Activity) this);
        setMobirollerToolbar(this.toolbarTop);
        this.screenId = getIntent().getStringExtra(Constants.KEY_SCREEN_ID);
        setToolbarContext(getString(R.string.add_note));
        checkData();
        loadUI();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void loadUI() {
        this.description.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                AveAddNoteActivity.this.noteModel.setDescription(charSequence.toString());
            }
        });
        this.title.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                AveAddNoteActivity.this.noteModel.setTitle(charSequence.toString());
            }
        });
        this.imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        this.colorRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.colorRecyclerView.setHasFixedSize(true);
        setColorList();
        ItemClickSupport.addTo(this.colorRecyclerView).setOnItemClickListener(new OnItemClickListener() {
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                if (i == 0) {
                    AveAddNoteActivity.this.onCustomColor();
                    return;
                }
                AveAddNoteActivity.this.noteModel.setColor((String) AveAddNoteActivity.this.noteColorList.get(i));
                AveAddNoteActivity.this.noteColorAdapter.updateSelected(i);
            }
        });
        loadModel();
    }

    private void checkData() {
        Intent intent = getIntent();
        String str = NOTE_MODEL;
        if (intent.hasExtra(str)) {
            Intent intent2 = getIntent();
            String str2 = NOTE_MODEL_POSITION;
            if (intent2.hasExtra(str2)) {
                this.noteModel = (NoteModel) getIntent().getSerializableExtra(str);
                this.noteModelPosition = getIntent().getIntExtra(str2, -1);
                this.isUpdate = true;
                setToolbarContext(getString(R.string.update_note));
            }
        }
        Intent intent3 = getIntent();
        String str3 = NOTE_MODEL_IMAGES;
        if (intent3.hasExtra(str3)) {
            this.noteModel.setImagePaths(getIntent().getStringArrayListExtra(str3));
        } else {
            this.noteModel.setImagePaths(new ArrayList());
        }
    }

    private void loadModel() {
        this.title.setText(this.noteModel.getTitle());
        this.description.setText(this.noteModel.getDescription());
        RecyclerView recyclerView = this.imageRecyclerView;
        NoteImageAdapter noteImageAdapter2 = new NoteImageAdapter(this, this.noteModel.getImagePaths());
        this.noteImageAdapter = noteImageAdapter2;
        recyclerView.setAdapter(noteImageAdapter2);
        RecyclerView recyclerView2 = this.colorRecyclerView;
        NoteColorAdapter noteColorAdapter2 = new NoteColorAdapter(this.noteColorList, this.noteModel.getColor(), getParent());
        this.noteColorAdapter = noteColorAdapter2;
        recyclerView2.setAdapter(noteColorAdapter2);
        if (this.noteModel.getColor().equalsIgnoreCase("")) {
            this.noteColorAdapter.updateSelected(1);
            this.noteModel.setColor("#F44336");
        }
    }

    /* access modifiers changed from: private */
    public void onCustomColor() {
        new Builder(this, R.string.app_name).doneButton(R.string.OK).cancelButton(R.string.cancel).backButton(R.string.back).customButton(R.string.action_custom).dynamicButtonColor(true).show();
    }

    public void onColorSelection(ColorChooserDialog colorChooserDialog, int i) {
        this.noteModel.setColor(String.format("#%06X", new Object[]{Integer.valueOf(i & ViewCompat.MEASURED_SIZE_MASK)}));
        this.noteColorList.set(0, this.noteModel.getColor());
        this.noteColorAdapter.updateSelected(0);
        this.noteColorAdapter.notifyDataSetChanged();
    }

    private void setColorList() {
        this.noteColorList = new ArrayList<>();
        String str = "#E91E63";
        this.noteColorList.addAll(Arrays.asList(new String[]{"#FFFFFF", "#F44336", "#673AB7", "#03A9F4", "#4CAF50", "#FFEB3B", "#607D8B", str, str, "#3F51B5", "#00BCD4", "#8BC34A", "#FFC107", "#795548", "#9C27B0", "#2196F3", "#009688", "#CDDC39", "#FF9800"}));
    }

    private void setToolbarContext(String str) {
        ToolbarHelper.setToolbar(this, (Toolbar) findViewById(R.id.toolbar_top));
        getSupportActionBar().setTitle((CharSequence) str);
    }

    private void addNote() {
        this.noteModel.setImagePaths(this.noteImageAdapter.getNoteImageList());
        NoteUtil.addModel(this, this.noteModel, this.screenId);
        if (!this.noteModel.getTitle().isEmpty() || !this.noteModel.getDescription().isEmpty() || this.noteModel.getImagePaths().size() > 0) {
            Toast.makeText(this, getResources().getString(R.string.note_added), 1).show();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        AveAddNoteActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    private void updateNote() {
        this.noteModel.setUpdated_at();
        this.noteModel.setImagePaths(this.noteImageAdapter.getNoteImageList());
        NoteUtil.updateModel(this, this.noteModel, this.noteModelPosition, this.screenId);
        if (!this.noteModel.getTitle().isEmpty() || !this.noteModel.getDescription().isEmpty() || this.noteModel.getImagePaths().size() > 0) {
            Toast.makeText(this, getResources().getString(R.string.note_updated), 1).show();
        }
    }

    private boolean checkModelIsEmpty() {
        String str = "";
        return this.noteModel.getTitle().equalsIgnoreCase(str) && this.noteModel.getDescription().equalsIgnoreCase(str) && this.noteModel.getImagePaths().size() == 0;
    }

    public void askStoragePermission() {
        AveAddNoteActivityPermissionsDispatcher.openGalleryWithPermissionCheck(this);
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (VERSION.SDK_INT >= 18) {
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && i2 == -1 && intent != null) {
            try {
                ArrayList arrayList = new ArrayList();
                if (intent.getData() != null) {
                    String path = getPath(intent.getData());
                    arrayList.add(path);
                    if (path != null && this.noteModel.getImagePaths().size() < 7) {
                        this.noteModel.getImagePaths().add(path);
                        this.noteImageAdapter.notifyItemInserted(this.noteModel.getImagePaths().size());
                    }
                } else if (VERSION.SDK_INT >= 16 && intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    ArrayList arrayList2 = new ArrayList();
                    for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                        Uri uri = clipData.getItemAt(i3).getUri();
                        arrayList2.add(uri);
                        String path2 = getPath(uri);
                        arrayList.add(path2);
                        if (path2 != null && this.noteModel.getImagePaths().size() < 7) {
                            this.noteModel.getImagePaths().add(path2);
                            this.noteImageAdapter.notifyItemInserted(this.noteModel.getImagePaths().size());
                        }
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), 1).show();
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", 1).show();
        }
        super.onActivityResult(i, i2, intent);
    }

    private String getPath(Uri uri) {
        String str = "_data";
        if (VERSION.SDK_INT < 19) {
            String str2 = null;
            CursorLoader cursorLoader = new CursorLoader(this, uri, new String[]{str}, null, null, null);
            Cursor loadInBackground = cursorLoader.loadInBackground();
            if (loadInBackground != null) {
                int columnIndexOrThrow = loadInBackground.getColumnIndexOrThrow(str);
                loadInBackground.moveToFirst();
                str2 = loadInBackground.getString(columnIndexOrThrow);
            }
            return str2;
        } else if (uri.getHost().contains("com.android.providers.media")) {
            String[] strArr = {str};
            String[] strArr2 = strArr;
            Cursor query = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, strArr2, "_id=?", new String[]{DocumentsContract.getDocumentId(uri).split(":")[1]}, null);
            String string = query.moveToFirst() ? query.getString(query.getColumnIndex(strArr[0])) : "";
            query.close();
            return string;
        } else {
            Cursor query2 = getContentResolver().query(uri, new String[]{str}, null, null, null);
            int columnIndexOrThrow2 = query2.getColumnIndexOrThrow(str);
            query2.moveToFirst();
            return query2.getString(columnIndexOrThrow2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getToolbar().inflateMenu(R.menu.note_add_menu);
        return super.onCreateOptionsMenu(getToolbar().getMenu());
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_save) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.isUpdate) {
            updateNote();
        } else if (!checkModelIsEmpty()) {
            addNote();
        }
        finish();
        return true;
    }
}
