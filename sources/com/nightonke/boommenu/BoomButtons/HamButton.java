package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.C4514R;
import com.nightonke.boommenu.Util;
import java.util.ArrayList;

public class HamButton extends BoomButton {

    public static class Builder extends BoomButtonWithTextBuilder<Builder> {
        public Builder() {
            this.imageRect = new Rect(0, 0, Util.dp2px(60.0f), Util.dp2px(60.0f));
            this.textRect = new Rect(Util.dp2px(70.0f), Util.dp2px(10.0f), Util.dp2px(280.0f), Util.dp2px(40.0f));
            this.textGravity = 8388627;
            this.textSize = 15;
        }

        public Builder containsSubText(boolean z) {
            this.containsSubText = z;
            return this;
        }

        public Builder subNormalText(String str) {
            if (this.subNormalText == null || !this.subNormalText.equals(str)) {
                this.subNormalText = str;
                BoomButton button = button();
                if (button != null) {
                    button.subNormalText = str;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subNormalTextRes(int i) {
            if (this.subNormalTextRes != i) {
                this.subNormalTextRes = i;
                BoomButton button = button();
                if (button != null) {
                    button.subNormalTextRes = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subHighlightedText(String str) {
            if (this.subHighlightedText == null || !this.subHighlightedText.equals(str)) {
                this.subHighlightedText = str;
                BoomButton button = button();
                if (button != null) {
                    button.subHighlightedText = str;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subHighlightedTextRes(int i) {
            if (this.subHighlightedTextRes != i) {
                this.subHighlightedTextRes = i;
                BoomButton button = button();
                if (button != null) {
                    button.subHighlightedTextRes = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subUnableText(String str) {
            if (this.subUnableText == null || !this.subUnableText.equals(str)) {
                this.subUnableText = str;
                BoomButton button = button();
                if (button != null) {
                    button.subUnableText = str;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subUnableTextRes(int i) {
            if (this.subUnableTextRes != i) {
                this.subUnableTextRes = i;
                BoomButton button = button();
                if (button != null) {
                    button.subUnableTextRes = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subNormalTextColor(int i) {
            if (this.subNormalTextColor != i) {
                this.subNormalTextColor = i;
                BoomButton button = button();
                if (button != null) {
                    button.subNormalTextColor = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subNormalTextColorRes(int i) {
            if (this.subNormalTextColorRes != i) {
                this.subNormalTextColorRes = i;
                BoomButton button = button();
                if (button != null) {
                    button.subNormalTextColorRes = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subHighlightedTextColor(int i) {
            if (this.subHighlightedTextColor != i) {
                this.subHighlightedTextColor = i;
                BoomButton button = button();
                if (button != null) {
                    button.subHighlightedTextColor = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subHighlightedTextColorRes(int i) {
            if (this.subHighlightedTextColorRes != i) {
                this.subHighlightedTextColorRes = i;
                BoomButton button = button();
                if (button != null) {
                    button.subHighlightedTextColorRes = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subUnableTextColor(int i) {
            if (this.subUnableTextColor != i) {
                this.subUnableTextColor = i;
                BoomButton button = button();
                if (button != null) {
                    button.subUnableTextColor = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subUnableTextColorRes(int i) {
            if (this.subUnableTextColorRes != i) {
                this.subUnableTextColorRes = i;
                BoomButton button = button();
                if (button != null) {
                    button.subUnableTextColorRes = i;
                    button.updateSubText();
                }
            }
            return this;
        }

        public Builder subTextRect(Rect rect) {
            if (this.subTextRect != rect) {
                this.subTextRect = rect;
                BoomButton button = button();
                if (button != null) {
                    button.subTextRect = rect;
                    button.updateSubTextRect();
                }
            }
            return this;
        }

        public Builder subTextPadding(Rect rect) {
            if (this.subTextPadding != rect) {
                this.subTextPadding = rect;
                BoomButton button = button();
                if (button != null) {
                    button.subTextPadding = rect;
                    button.updateSubTextPadding();
                }
            }
            return this;
        }

        public Builder subTypeface(Typeface typeface) {
            this.subTypeface = typeface;
            return this;
        }

        public Builder subMaxLines(int i) {
            this.subMaxLines = i;
            return this;
        }

        public Builder subTextGravity(int i) {
            this.subTextGravity = i;
            return this;
        }

        public Builder subEllipsize(TruncateAt truncateAt) {
            this.subEllipsize = truncateAt;
            return this;
        }

        public Builder subTextSize(int i) {
            this.subTextSize = i;
            return this;
        }

        public Builder buttonWidth(int i) {
            this.buttonWidth = i;
            return this;
        }

        public Builder buttonHeight(int i) {
            this.buttonHeight = i;
            return this;
        }

        public Builder buttonCornerRadius(int i) {
            this.buttonCornerRadius = i;
            return this;
        }

        public int getButtonWidth() {
            return this.buttonWidth;
        }

        public int getButtonHeight() {
            return this.buttonHeight;
        }

        public HamButton build(Context context) {
            HamButton hamButton = new HamButton(this, context);
            weakReferenceButton(hamButton);
            return hamButton;
        }
    }

    public void setRotateAnchorPoints() {
    }

    public void setSelfScaleAnchorPoints() {
    }

    private HamButton(Builder builder, Context context) {
        super(context);
        this.context = context;
        this.buttonEnum = ButtonEnum.Ham;
        init(builder);
    }

    private void init(Builder builder) {
        LayoutInflater.from(this.context).inflate(C4514R.layout.bmb_ham_button, this, true);
        initAttrs(builder);
        initShadow(builder.shadowCornerRadius);
        initHamButton();
        initText(this.button);
        initSubText(this.button);
        initImage();
        this.centerPoint = new PointF((((float) this.buttonWidth) / 2.0f) + ((float) this.shadowRadius) + ((float) this.shadowOffsetX), (((float) this.buttonHeight) / 2.0f) + ((float) this.shadowRadius) + ((float) this.shadowOffsetY));
    }

    private void initAttrs(Builder builder) {
        super.initAttrs(builder);
    }

    public ButtonEnum type() {
        return ButtonEnum.Ham;
    }

    public ArrayList<View> goneViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(this.image);
        arrayList.add(this.text);
        if (this.subText != null) {
            arrayList.add(this.subText);
        }
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
        return this.buttonWidth + (this.shadowRadius * 2) + (this.shadowOffsetX * 2);
    }

    public int trueHeight() {
        return this.buttonHeight + (this.shadowRadius * 2) + (this.shadowOffsetY * 2);
    }

    public int contentWidth() {
        return this.buttonWidth;
    }

    public int contentHeight() {
        return this.buttonHeight;
    }

    public void toHighlighted() {
        if (this.lastStateIsNormal && this.ableToHighlight) {
            toHighlightedImage();
            toHighlightedText();
            toHighlightedSubText();
            this.lastStateIsNormal = false;
        }
    }

    public void toNormal() {
        if (!this.lastStateIsNormal) {
            toNormalImage();
            toNormalText();
            toNormalSubText();
            this.lastStateIsNormal = true;
        }
    }
}
