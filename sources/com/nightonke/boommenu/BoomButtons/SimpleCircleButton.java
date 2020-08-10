package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.C4514R;
import java.util.ArrayList;

public class SimpleCircleButton extends BoomButton {

    public static class Builder extends BoomButtonBuilder<Builder> {
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

        public int getButtonRadius() {
            return this.buttonRadius;
        }

        public SimpleCircleButton build(Context context) {
            SimpleCircleButton simpleCircleButton = new SimpleCircleButton(this, context);
            weakReferenceButton(simpleCircleButton);
            return simpleCircleButton;
        }
    }

    public void setSelfScaleAnchorPoints() {
    }

    private SimpleCircleButton(Builder builder, Context context) {
        super(context);
        this.context = context;
        this.buttonEnum = ButtonEnum.SimpleCircle;
        init(builder);
    }

    private void init(Builder builder) {
        LayoutInflater.from(this.context).inflate(C4514R.layout.bmb_simple_circle_button, this, true);
        initAttrs(builder);
        if (this.isRound) {
            initShadow(this.buttonRadius + this.shadowRadius);
        } else {
            initShadow(this.shadowCornerRadius);
        }
        initCircleButton();
        initImage();
        this.centerPoint = new PointF((float) (this.buttonRadius + this.shadowRadius + this.shadowOffsetX), (float) (this.buttonRadius + this.shadowRadius + this.shadowOffsetY));
    }

    private void initAttrs(Builder builder) {
        super.initAttrs(builder);
    }

    public ButtonEnum type() {
        return ButtonEnum.SimpleCircle;
    }

    public ArrayList<View> goneViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(this.image);
        return arrayList;
    }

    public ArrayList<View> rotateViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        if (this.rotateImage) {
            arrayList.add(this.image);
        }
        return arrayList;
    }

    public int trueWidth() {
        return (this.buttonRadius * 2) + (this.shadowRadius * 2) + (this.shadowOffsetX * 2);
    }

    public int trueHeight() {
        return (this.buttonRadius * 2) + (this.shadowRadius * 2) + (this.shadowOffsetY * 2);
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
            this.lastStateIsNormal = false;
        }
    }

    public void toNormal() {
        if (!this.lastStateIsNormal) {
            toNormalImage();
            this.lastStateIsNormal = true;
        }
    }

    public void setRotateAnchorPoints() {
        this.image.setPivotX((float) (this.buttonRadius - this.imageRect.left));
        this.image.setPivotY((float) (this.buttonRadius - this.imageRect.top));
    }
}
