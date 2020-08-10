package com.mobiroller.views.dialogs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.mobiroller.mobi942763453128.R;

public class RatingDialog extends AppCompatDialog implements OnRatingBarChangeListener, OnClickListener {
    private static final String SESSION_COUNT = "session_count";
    private static final String SHOW_NEVER = "show_never";
    private String MyPrefs = "RatingDialog";
    private Builder builder;
    /* access modifiers changed from: private */
    public Context context;
    private EditText etFeedback;
    private LinearLayout feedbackButtons;
    private ImageView ivIcon;
    private RatingBar ratingBar;
    private LinearLayout ratingButtons;
    private int session;
    private SharedPreferences sharedpreferences;
    private float threshold;
    private boolean thresholdPassed = true;
    private TextView tvCancel;
    private TextView tvFeedback;
    private TextView tvNegative;
    private TextView tvPositive;
    private TextView tvSubmit;
    private TextView tvTitle;

    public static class Builder {
        /* access modifiers changed from: private */
        public String cancelText;
        private final Context context;
        /* access modifiers changed from: private */
        public Drawable drawable;
        /* access modifiers changed from: private */
        public int feedBackTextColor;
        /* access modifiers changed from: private */
        public String feedbackFormHint;
        /* access modifiers changed from: private */
        public String formTitle;
        /* access modifiers changed from: private */
        public int negativeBackgroundColor;
        /* access modifiers changed from: private */
        public String negativeText;
        /* access modifiers changed from: private */
        public int negativeTextColor;
        /* access modifiers changed from: private */
        public int positiveBackgroundColor;
        /* access modifiers changed from: private */
        public String positiveText;
        /* access modifiers changed from: private */
        public int positiveTextColor;
        /* access modifiers changed from: private */
        public int ratingBarColor;
        /* access modifiers changed from: private */
        public RatingDialogFormListener ratingDialogFormListener;
        /* access modifiers changed from: private */
        public RatingDialogListener ratingDialogListener;
        /* access modifiers changed from: private */
        public RatingThresholdClearedListener ratingThresholdClearedListener;
        /* access modifiers changed from: private */
        public RatingThresholdFailedListener ratingThresholdFailedListener;
        /* access modifiers changed from: private */
        public int session = 1;
        /* access modifiers changed from: private */
        public String submitText;
        /* access modifiers changed from: private */
        public float threshold = 1.0f;
        /* access modifiers changed from: private */
        public String title;
        /* access modifiers changed from: private */
        public int titleTextColor;

        public interface RatingDialogFormListener {
            void onFormSubmitted(String str, int i);
        }

        public interface RatingDialogListener {
            void onRatingSelected(float f, boolean z);
        }

        public interface RatingThresholdClearedListener {
            void onThresholdCleared(RatingDialog ratingDialog, float f, boolean z);
        }

        public interface RatingThresholdFailedListener {
            void onThresholdFailed(RatingDialog ratingDialog, float f, boolean z);
        }

        public Builder(Context context2) {
            this.context = context2;
            initText();
        }

        private void initText() {
            this.title = this.context.getString(R.string.rating_dialog_experience);
            this.positiveText = this.context.getString(R.string.rating_dialog_remind_later);
            this.negativeText = this.context.getString(R.string.rating_dialog_never);
            this.formTitle = this.context.getString(R.string.rating_dialog_feedback_title);
            this.submitText = this.context.getString(R.string.rating_dialog_submit);
            this.cancelText = this.context.getString(R.string.rating_dialog_cancel);
            this.feedbackFormHint = this.context.getString(R.string.rating_dialog_suggestions);
        }

        public Builder session(int i) {
            this.session = i;
            return this;
        }

        public Builder threshold(float f) {
            this.threshold = f;
            return this;
        }

        public Builder title(String str) {
            this.title = str;
            return this;
        }

        public Builder icon(Drawable drawable2) {
            this.drawable = drawable2;
            return this;
        }

        public Builder positiveButtonText(String str) {
            this.positiveText = str;
            return this;
        }

        public Builder negativeButtonText(String str) {
            this.negativeText = str;
            return this;
        }

        public Builder titleTextColor(int i) {
            this.titleTextColor = i;
            return this;
        }

        public Builder positiveButtonTextColor(int i) {
            this.positiveTextColor = i;
            return this;
        }

        public Builder negativeButtonTextColor(int i) {
            this.negativeTextColor = i;
            return this;
        }

        public Builder positiveButtonBackgroundColor(int i) {
            this.positiveBackgroundColor = i;
            return this;
        }

        public Builder negativeButtonBackgroundColor(int i) {
            this.negativeBackgroundColor = i;
            return this;
        }

        public Builder onThresholdCleared(RatingThresholdClearedListener ratingThresholdClearedListener2) {
            this.ratingThresholdClearedListener = ratingThresholdClearedListener2;
            return this;
        }

        public Builder onThresholdFailed(RatingThresholdFailedListener ratingThresholdFailedListener2) {
            this.ratingThresholdFailedListener = ratingThresholdFailedListener2;
            return this;
        }

        public Builder onRatingChanged(RatingDialogListener ratingDialogListener2) {
            this.ratingDialogListener = ratingDialogListener2;
            return this;
        }

        public Builder onRatingBarFormSumbit(RatingDialogFormListener ratingDialogFormListener2) {
            this.ratingDialogFormListener = ratingDialogFormListener2;
            return this;
        }

        public Builder formTitle(String str) {
            this.formTitle = str;
            return this;
        }

        public Builder formHint(String str) {
            this.feedbackFormHint = str;
            return this;
        }

        public Builder formSubmitText(String str) {
            this.submitText = str;
            return this;
        }

        public Builder formCancelText(String str) {
            this.cancelText = str;
            return this;
        }

        public Builder ratingBarColor(int i) {
            this.ratingBarColor = i;
            return this;
        }

        public Builder feedbackTextColor(int i) {
            this.feedBackTextColor = i;
            return this;
        }

        public RatingDialog build() {
            return new RatingDialog(this.context, this);
        }
    }

    public RatingDialog(Context context2, Builder builder2) {
        super(context2);
        this.context = context2;
        this.builder = builder2;
        this.session = builder2.session;
        this.threshold = builder2.threshold;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView((int) R.layout.dialog_rating);
        this.tvTitle = (TextView) findViewById(R.id.dialog_rating_title);
        this.tvNegative = (TextView) findViewById(R.id.dialog_rating_button_negative);
        this.tvPositive = (TextView) findViewById(R.id.dialog_rating_button_positive);
        this.tvFeedback = (TextView) findViewById(R.id.dialog_rating_feedback_title);
        this.tvSubmit = (TextView) findViewById(R.id.dialog_rating_button_feedback_submit);
        this.tvCancel = (TextView) findViewById(R.id.dialog_rating_button_feedback_cancel);
        this.ratingBar = (RatingBar) findViewById(R.id.dialog_rating_rating_bar);
        this.ivIcon = (ImageView) findViewById(R.id.dialog_rating_icon);
        this.etFeedback = (EditText) findViewById(R.id.dialog_rating_feedback);
        this.ratingButtons = (LinearLayout) findViewById(R.id.dialog_rating_buttons);
        this.feedbackButtons = (LinearLayout) findViewById(R.id.dialog_rating_feedback_buttons);
        init();
    }

    private void init() {
        Context context2;
        this.tvTitle.setText(this.builder.title);
        this.tvPositive.setText(this.builder.positiveText);
        this.tvNegative.setText(this.builder.negativeText);
        this.tvFeedback.setText(this.builder.formTitle);
        this.tvSubmit.setText(this.builder.submitText);
        this.tvCancel.setText(this.builder.cancelText);
        this.etFeedback.setHint(this.builder.feedbackFormHint);
        TypedValue typedValue = new TypedValue();
        this.context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int i = typedValue.data;
        TextView textView = this.tvTitle;
        int access$900 = this.builder.titleTextColor;
        int i2 = R.color.black;
        textView.setTextColor(access$900 != 0 ? ContextCompat.getColor(this.context, this.builder.titleTextColor) : ContextCompat.getColor(this.context, R.color.black));
        this.tvPositive.setTextColor(this.builder.positiveTextColor != 0 ? ContextCompat.getColor(this.context, this.builder.positiveTextColor) : i);
        this.tvNegative.setTextColor(this.builder.negativeTextColor != 0 ? ContextCompat.getColor(this.context, this.builder.negativeTextColor) : ContextCompat.getColor(this.context, R.color.grey_500));
        TextView textView2 = this.tvFeedback;
        if (this.builder.titleTextColor != 0) {
            context2 = this.context;
            i2 = this.builder.titleTextColor;
        } else {
            context2 = this.context;
        }
        textView2.setTextColor(ContextCompat.getColor(context2, i2));
        TextView textView3 = this.tvSubmit;
        if (this.builder.positiveTextColor != 0) {
            i = ContextCompat.getColor(this.context, this.builder.positiveTextColor);
        }
        textView3.setTextColor(i);
        this.tvCancel.setTextColor(this.builder.negativeTextColor != 0 ? ContextCompat.getColor(this.context, this.builder.negativeTextColor) : ContextCompat.getColor(this.context, R.color.grey_500));
        if (this.builder.feedBackTextColor != 0) {
            this.etFeedback.setTextColor(ContextCompat.getColor(this.context, this.builder.feedBackTextColor));
        }
        if (this.builder.positiveBackgroundColor != 0) {
            this.tvPositive.setBackgroundResource(this.builder.positiveBackgroundColor);
            this.tvSubmit.setBackgroundResource(this.builder.positiveBackgroundColor);
        }
        if (this.builder.negativeBackgroundColor != 0) {
            this.tvNegative.setBackgroundResource(this.builder.negativeBackgroundColor);
            this.tvCancel.setBackgroundResource(this.builder.negativeBackgroundColor);
        }
        if (this.builder.ratingBarColor != 0) {
            if (VERSION.SDK_INT > 19) {
                LayerDrawable layerDrawable = (LayerDrawable) this.ratingBar.getProgressDrawable();
                layerDrawable.getDrawable(2).setColorFilter(ContextCompat.getColor(this.context, this.builder.ratingBarColor), Mode.SRC_ATOP);
                layerDrawable.getDrawable(1).setColorFilter(ContextCompat.getColor(this.context, this.builder.ratingBarColor), Mode.SRC_ATOP);
                layerDrawable.getDrawable(0).setColorFilter(ContextCompat.getColor(this.context, R.color.grey_200), Mode.SRC_ATOP);
            } else {
                DrawableCompat.setTint(this.ratingBar.getProgressDrawable(), ContextCompat.getColor(this.context, this.builder.ratingBarColor));
            }
        }
        Drawable applicationIcon = this.context.getPackageManager().getApplicationIcon(this.context.getApplicationInfo());
        ImageView imageView = this.ivIcon;
        if (this.builder.drawable != null) {
            applicationIcon = this.builder.drawable;
        }
        imageView.setImageDrawable(applicationIcon);
        this.ratingBar.setOnRatingBarChangeListener(this);
        this.tvPositive.setOnClickListener(this);
        this.tvNegative.setOnClickListener(this);
        this.tvSubmit.setOnClickListener(this);
        this.tvCancel.setOnClickListener(this);
        if (this.session == 1) {
            this.tvNegative.setVisibility(8);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.dialog_rating_button_negative) {
            dismiss();
            showNever();
        } else if (view.getId() == R.id.dialog_rating_button_positive) {
            dismiss();
        } else if (view.getId() == R.id.dialog_rating_button_feedback_submit) {
            String trim = this.etFeedback.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                this.etFeedback.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake));
                return;
            }
            if (this.builder.ratingDialogFormListener != null) {
                this.builder.ratingDialogFormListener.onFormSubmitted(trim, getUserRating());
            }
            dismiss();
            showNever();
        } else if (view.getId() == R.id.dialog_rating_button_feedback_cancel) {
            dismiss();
            showAgain();
        }
    }

    public int getUserRating() {
        try {
            if (this.ratingBar != null) {
                return Math.round(this.ratingBar.getRating());
            }
            return 1;
        } catch (Exception unused) {
            return 1;
        }
    }

    public void onRatingChanged(RatingBar ratingBar2, float f, boolean z) {
        if (ratingBar2.getRating() >= this.threshold) {
            this.thresholdPassed = true;
            if (this.builder.ratingThresholdClearedListener == null) {
                setRatingThresholdClearedListener();
            }
            this.builder.ratingThresholdClearedListener.onThresholdCleared(this, ratingBar2.getRating(), this.thresholdPassed);
        } else {
            this.thresholdPassed = false;
            if (this.builder.ratingThresholdFailedListener == null) {
                setRatingThresholdFailedListener();
            }
            this.builder.ratingThresholdFailedListener.onThresholdFailed(this, ratingBar2.getRating(), this.thresholdPassed);
        }
        if (this.builder.ratingDialogListener != null) {
            this.builder.ratingDialogListener.onRatingSelected(ratingBar2.getRating(), this.thresholdPassed);
        }
        showNever();
    }

    private void setRatingThresholdClearedListener() {
        this.builder.ratingThresholdClearedListener = new RatingThresholdClearedListener() {
            public void onThresholdCleared(RatingDialog ratingDialog, float f, boolean z) {
                RatingDialog ratingDialog2 = RatingDialog.this;
                ratingDialog2.openPlaystore(ratingDialog2.context);
                RatingDialog.this.dismiss();
            }
        };
    }

    private void setRatingThresholdFailedListener() {
        this.builder.ratingThresholdFailedListener = new RatingThresholdFailedListener() {
            public void onThresholdFailed(RatingDialog ratingDialog, float f, boolean z) {
                RatingDialog.this.openForm();
            }
        };
    }

    /* access modifiers changed from: private */
    public void openForm() {
        this.tvFeedback.setVisibility(0);
        this.etFeedback.setVisibility(0);
        this.feedbackButtons.setVisibility(0);
        this.ratingButtons.setVisibility(8);
        this.ivIcon.setVisibility(8);
        this.tvTitle.setVisibility(8);
        this.ratingBar.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void openPlaystore(Context context2) {
        StringBuilder sb = new StringBuilder();
        sb.append("market://details?id=");
        sb.append(context2.getPackageName());
        try {
            context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context2, "Couldn't find PlayStore on this device", 0).show();
        }
    }

    public TextView getTitleTextView() {
        return this.tvTitle;
    }

    public TextView getPositiveButtonTextView() {
        return this.tvPositive;
    }

    public TextView getNegativeButtonTextView() {
        return this.tvNegative;
    }

    public TextView getFormTitleTextView() {
        return this.tvFeedback;
    }

    public TextView getFormSumbitTextView() {
        return this.tvSubmit;
    }

    public TextView getFormCancelTextView() {
        return this.tvCancel;
    }

    public ImageView getIconImageView() {
        return this.ivIcon;
    }

    public RatingBar getRatingBarView() {
        return this.ratingBar;
    }

    public void show() {
        if (checkIfSessionMatches(this.session)) {
            super.show();
        }
    }

    private boolean checkIfSessionMatches(int i) {
        if (i == 1) {
            return true;
        }
        this.sharedpreferences = this.context.getSharedPreferences(this.MyPrefs, 0);
        if (this.sharedpreferences.getBoolean(SHOW_NEVER, false)) {
            return false;
        }
        SharedPreferences sharedPreferences = this.sharedpreferences;
        String str = SESSION_COUNT;
        int i2 = sharedPreferences.getInt(str, 1);
        if (i == i2) {
            Editor edit = this.sharedpreferences.edit();
            edit.putInt(str, 1);
            edit.apply();
            return true;
        } else if (i > i2) {
            int i3 = i2 + 1;
            Editor edit2 = this.sharedpreferences.edit();
            edit2.putInt(str, i3);
            edit2.apply();
            return false;
        } else {
            Editor edit3 = this.sharedpreferences.edit();
            edit3.putInt(str, 2);
            edit3.apply();
            return false;
        }
    }

    private void showNever() {
        this.sharedpreferences = this.context.getSharedPreferences(this.MyPrefs, 0);
        Editor edit = this.sharedpreferences.edit();
        edit.putBoolean(SHOW_NEVER, true);
        edit.apply();
    }

    private void showAgain() {
        this.sharedpreferences = this.context.getSharedPreferences(this.MyPrefs, 0);
        Editor edit = this.sharedpreferences.edit();
        edit.putBoolean(SHOW_NEVER, false);
        edit.apply();
    }
}
