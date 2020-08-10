package com.mobiroller.helpers;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.ImageManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComponentHelper {
    /* access modifiers changed from: private */
    public int lineCount = 0;
    /* access modifiers changed from: private */
    public ScreenHelper screenHelper;
    SharedPrefHelper sharedPrefHelper;

    public ComponentHelper(ScreenHelper screenHelper2, SharedPrefHelper sharedPrefHelper2) {
        this.screenHelper = screenHelper2;
        this.sharedPrefHelper = sharedPrefHelper2;
    }

    public void setMainImage(Activity activity, ImageView imageView, ScreenModel screenModel) {
        if (screenModel != null && screenModel.getMainImageName() != null && screenModel.getMainImageName().getImageURL() != null) {
            ImageManager.loadImageView(activity, screenModel.getMainImageName().getImageURL(), imageView);
        }
    }

    public void setRadioTitleText(Activity activity, TextView textView, ScreenModel screenModel) {
        String contentText = screenModel.getContentText();
        textView.invalidate();
        StringBuilder sb = new StringBuilder();
        String str = "<";
        sb.append(str);
        sb.append(LocaleHelper.getLocale().toUpperCase());
        String str2 = ">";
        sb.append(str2);
        if (contentText.contains(sb.toString())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(LocaleHelper.getLocale().toUpperCase());
            sb2.append(str2);
            String[] split = contentText.split(sb2.toString());
            contentText = split[split.length - 2];
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(this.sharedPrefHelper.getDefaultLang().toUpperCase());
            sb3.append(str2);
            if (contentText.contains(sb3.toString())) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(this.sharedPrefHelper.getDefaultLang().toUpperCase());
                sb4.append(str2);
                String[] split2 = contentText.split(sb4.toString());
                contentText = split2[split2.length - 2];
            }
        }
        setTextView(textView, contentText, activity);
        if (!screenModel.getContentText().equals("null")) {
            ScreenHelper screenHelper2 = this.screenHelper;
            textView.setTextColor(ScreenHelper.setColorUnselected(screenModel.getContentTextColor()));
        }
    }

    public void setMainTextView(Activity activity, TextView textView, ScreenModel screenModel) {
        String contentText = screenModel.getContentText();
        List singletonList = Collections.singletonList("");
        if (this.sharedPrefHelper.getLocaleCodes() != null) {
            singletonList = Arrays.asList(this.sharedPrefHelper.getLocaleCodes().split(","));
        }
        textView.invalidate();
        StringBuilder sb = new StringBuilder();
        String str = "<";
        sb.append(str);
        sb.append(LocaleHelper.getLocale().toUpperCase());
        String str2 = ">";
        sb.append(str2);
        if (contentText.contains(sb.toString())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(LocaleHelper.getLocale().toUpperCase());
            sb2.append(str2);
            String[] split = contentText.split(sb2.toString());
            contentText = split[split.length - 2];
        } else if (!singletonList.contains(LocaleHelper.getLocale().toUpperCase())) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(this.sharedPrefHelper.getDefaultLang().toUpperCase());
            sb3.append(str2);
            if (contentText.contains(sb3.toString())) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(this.sharedPrefHelper.getDefaultLang().toUpperCase());
                sb4.append(str2);
                String[] split2 = contentText.split(sb4.toString());
                contentText = split2[split2.length - 2];
            }
        } else {
            return;
        }
        setTextView(textView, contentText, activity);
        int lineCount2 = textView.getLineCount() * textView.getLineHeight();
        if (screenModel.getContentFontSize() != 0.0f) {
            textView.setTextSize(screenModel.getContentFontSize());
        }
        ScreenHelper screenHelper2 = this.screenHelper;
        if (lineCount2 > ScreenHelper.getHeightForDevice(screenModel.getContentTextHeight(), activity) || lineCount2 == 0) {
            ScreenHelper screenHelper3 = this.screenHelper;
            textView.setHeight(ScreenHelper.getHeightForDevice(screenModel.getContentTextHeight(), activity));
            this.lineCount = screenModel.getContentTextHeight();
        }
        if (screenModel.getContentTextBackColor() != null) {
            ScreenHelper screenHelper4 = this.screenHelper;
            textView.setBackgroundColor(ScreenHelper.setColorUnselected(screenModel.getContentTextBackColor()));
        }
        int i = 0;
        if (screenModel.getContentTextColor() != null) {
            ScreenHelper screenHelper5 = this.screenHelper;
            i = ScreenHelper.setColorUnselected(screenModel.getContentTextColor());
        }
        textView.setTextColor(i);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.getHeight();
    }

    private void setTextView(final TextView textView, String str, final Activity activity) {
        textView.setText(str);
        textView.post(new Runnable() {
            public void run() {
                if (ComponentHelper.this.lineCount == 0) {
                    while (textView.getLineCount() == 0) {
                        if (textView.getText().toString().isEmpty()) {
                            break;
                        }
                    }
                    ComponentHelper.this.lineCount = textView.getLineCount();
                    TextView textView = textView;
                    int access$000 = ComponentHelper.this.lineCount * textView.getLineHeight();
                    ComponentHelper.this.screenHelper;
                    textView.setHeight(access$000 + ScreenHelper.getHeightForDevice(10, activity));
                }
            }
        });
    }
}
