package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.C4514R;
import java.util.ArrayList;

public class TextOutsideCircleButton extends BoomButton {

    public static class Builder extends BoomButtonWithTextBuilder<Builder> {
        public Builder rotateText(boolean z) {
            this.rotateText = z;
            return this;
        }

        public Builder textTopMargin(int i) {
            if (i < 0) {
                i = 0;
            }
            this.textTopMargin = i;
            return this;
        }

        public Builder textWidth(int i) {
            this.textWidth = i;
            return this;
        }

        public Builder textHeight(int i) {
            this.textHeight = i;
            return this;
        }

        public Builder buttonRadius(int i) {
            this.buttonRadius = i;
            return this;
        }

        public Builder buttonCornerRadius(int i) {
            this.buttonCornerRadius = i;
            return this;
        }

        public Builder isRound(boolean z) {
            this.isRound = z;
            return this;
        }

        public int getButtonContentWidth() {
            int i = this.buttonRadius * 2;
            return this.textRect != null ? Math.max(i, this.textWidth) : i;
        }

        public int getButtonContentHeight() {
            int i = this.buttonRadius * 2;
            return this.textRect != null ? Math.max(i, (this.textRect.bottom - this.shadowOffsetY) - this.shadowRadius) : i;
        }

        public int getButtonRadius() {
            return this.buttonRadius;
        }

        public TextOutsideCircleButton build(Context context) {
            TextOutsideCircleButton textOutsideCircleButton = new TextOutsideCircleButton(this, context);
            weakReferenceButton(textOutsideCircleButton);
            return textOutsideCircleButton;
        }
    }

    public void setSelfScaleAnchorPoints() {
    }

    private TextOutsideCircleButton(Builder builder, Context context) {
        super(context);
        this.context = context;
        this.buttonEnum = ButtonEnum.TextOutsideCircle;
        init(builder);
    }

    private void init(Builder builder) {
        LayoutInflater.from(this.context).inflate(C4514R.layout.bmb_text_outside_circle_button, this, true);
        initAttrs(builder);
        initTextOutsideCircleButtonLayout();
        if (this.isRound) {
            initShadow(this.buttonRadius + this.shadowRadius);
        } else {
            initShadow(this.shadowCornerRadius);
        }
        initCircleButton();
        initText(this.layout);
        initImage();
        this.centerPoint = new PointF((float) this.trueRadius, (float) this.trueRadius);
    }

    private void initAttrs(Builder builder) {
        super.initAttrs(builder);
    }

    public ButtonEnum type() {
        return ButtonEnum.TextOutsideCircle;
    }

    public ArrayList<View> goneViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(this.image);
        arrayList.add(this.text);
        return arrayList;
    }

    public ArrayList<View> rotateViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        if (this.rotateImage) {
            arrayList.add(this.image);
        }
        if (this.rotateText) {
            arrayList.add(this.text);
        }
        return arrayList;
    }

    public int trueWidth() {
        return this.trueRadius * 2;
    }

    public int trueHeight() {
        return this.trueRadius * 2;
    }

    public int contentWidth() {
        return this.buttonRadius * 2;
    }

    public int contentHeight() {
        return this.buttonRadius * 2;
    }

    public void toHighlighted() {
        if (this.lastStateIsNormal && this.ableToHighlight) {
            toHighlightedImage();
            toHighlightedText();
            this.lastStateIsNormal = false;
        }
    }

    public void toNormal() {
        if (!this.lastStateIsNormal) {
            toNormalImage();
            toNormalText();
            this.lastStateIsNormal = true;
        }
    }

    public void setRotateAnchorPoints() {
        this.image.setPivotX((float) (this.buttonRadius - this.imageRect.left));
        this.image.setPivotY((float) (this.buttonRadius - this.imageRect.top));
        this.text.setPivotX((float) (this.trueRadius - this.textRect.left));
        this.text.setPivotY((float) (this.trueRadius - this.textRect.top));
    }
}
