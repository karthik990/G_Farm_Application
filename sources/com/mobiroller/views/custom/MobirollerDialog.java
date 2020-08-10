package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.mobiroller.enums.MobirollerDialogType;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;

public class MobirollerDialog {

    public interface DialogListCallback {
        void onSelect(int i);
    }

    public static final class Builder {
        private Adapter mAdapter;
        /* access modifiers changed from: private */
        public boolean mAutoDismiss = true;
        private String mButtonText;
        /* access modifiers changed from: private */
        public DialogButtonCallback mCallback;
        private int mColor;
        private Context mContext;
        private String mDescription;
        private boolean mHasDivider;
        private int mIconResource;
        /* access modifiers changed from: private */
        public DialogListCallback mListCallback;
        private String mNegativeButtonText;
        private Spannable mSpannable;
        private String mTitle;
        private MobirollerDialogType mType;

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder setType(MobirollerDialogType mobirollerDialogType) {
            this.mType = mobirollerDialogType;
            return this;
        }

        public Builder setIconResource(int i) {
            this.mIconResource = i;
            return this;
        }

        public Builder setColor(int i) {
            this.mColor = i;
            return this;
        }

        public Builder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public Builder setDescription(String str) {
            this.mDescription = str;
            return this;
        }

        public Builder setDescription(Spannable spannable) {
            this.mSpannable = spannable;
            return this;
        }

        public Builder setButtonText(String str) {
            this.mButtonText = str;
            return this;
        }

        public Builder setNegativeButtonText(String str) {
            this.mNegativeButtonText = str;
            return this;
        }

        public Builder setListener(DialogButtonCallback dialogButtonCallback) {
            this.mCallback = dialogButtonCallback;
            return this;
        }

        public Builder setListSelectionListener(DialogListCallback dialogListCallback) {
            this.mListCallback = dialogListCallback;
            return this;
        }

        public Builder setAdapter(Adapter adapter) {
            this.mAdapter = adapter;
            return this;
        }

        public Builder setHasDivider(boolean z) {
            this.mHasDivider = z;
            return this;
        }

        public Builder setAutoDismiss(boolean z) {
            this.mAutoDismiss = z;
            return this;
        }

        public MaterialDialog show() {
            final MaterialDialog materialDialog;
            if (this.mContext != null) {
                if (this.mType == MobirollerDialogType.BASIC) {
                    materialDialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(this.mContext).customView((int) R.layout.mobiroller_dialog_basic, false).build();
                    setBasic(materialDialog);
                } else if (this.mType == MobirollerDialogType.BUTTON) {
                    materialDialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(this.mContext).customView((int) R.layout.mobiroller_dialog_button, false).build();
                    setBasic(materialDialog);
                } else {
                    materialDialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(this.mContext).customView((int) R.layout.mobiroller_dialog_list, false).stackingBehavior(StackingBehavior.NEVER).build();
                    setList(materialDialog);
                }
                materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                ImageView imageView = (ImageView) materialDialog.getCustomView().findViewById(R.id.icon_image_view);
                RelativeLayout relativeLayout = (RelativeLayout) materialDialog.getCustomView().findViewById(R.id.icon_layout);
                TextView textView = (TextView) materialDialog.getCustomView().findViewById(R.id.description_text_view);
                ((TextView) materialDialog.getCustomView().findViewById(R.id.title_text_view)).setText(this.mTitle);
                String str = this.mDescription;
                if (str != null) {
                    textView.setText(str);
                } else {
                    Spannable spannable = this.mSpannable;
                    if (spannable != null) {
                        textView.setText(spannable);
                    } else {
                        textView.setVisibility(8);
                    }
                }
                int i = this.mIconResource;
                if (i != 0) {
                    imageView.setImageResource(i);
                }
                if (this.mType == MobirollerDialogType.LIST_WITH_BUTTON) {
                    MobirollerButton mobirollerButton = (MobirollerButton) materialDialog.getCustomView().findViewById(R.id.confirm_button);
                    mobirollerButton.setVisibility(0);
                    mobirollerButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (Builder.this.mCallback != null) {
                                Builder.this.mCallback.onClickButton();
                            }
                            if (Builder.this.mAutoDismiss) {
                                materialDialog.dismiss();
                            }
                        }
                    });
                } else {
                    MobirollerButton mobirollerButton2 = (MobirollerButton) materialDialog.getCustomView().findViewById(R.id.confirm_button);
                    if (mobirollerButton2 != null) {
                        mobirollerButton2.setVisibility(8);
                    }
                }
                if (this.mColor != 0 && VERSION.SDK_INT >= 21) {
                    relativeLayout.setBackgroundTintList(ColorStateList.valueOf(this.mColor));
                }
                if (this.mType == MobirollerDialogType.BUTTON) {
                    MobirollerButton mobirollerButton3 = (MobirollerButton) materialDialog.getCustomView().findViewById(R.id.negative_button);
                    mobirollerButton3.setVisibility(0);
                    mobirollerButton3.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            materialDialog.dismiss();
                        }
                    });
                }
                textView.setMovementMethod(new ScrollingMovementMethod());
                materialDialog.show();
                return materialDialog;
            }
            throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
        }

        private void setBasic(final MaterialDialog materialDialog) {
            MobirollerButton mobirollerButton = (MobirollerButton) materialDialog.getCustomView().findViewById(R.id.button);
            mobirollerButton.setText(this.mButtonText);
            mobirollerButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (Builder.this.mCallback != null) {
                        Builder.this.mCallback.onClickButton();
                    }
                    materialDialog.dismiss();
                }
            });
        }

        private void setList(final MaterialDialog materialDialog) {
            RecyclerView recyclerView = (RecyclerView) materialDialog.getCustomView().findViewById(R.id.list);
            Adapter adapter = this.mAdapter;
            if (adapter != null) {
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                        if (Builder.this.mListCallback != null) {
                            Builder.this.mListCallback.onSelect(i);
                        }
                        materialDialog.dismiss();
                    }
                });
                if (this.mHasDivider) {
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation()));
                }
            }
        }
    }

    public interface DialogButtonCallback {
        void onClickButton();
    }
}
