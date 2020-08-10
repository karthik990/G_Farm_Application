package com.nightonke.boommenu.Piece;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.nightonke.boommenu.C4514R;
import com.nightonke.boommenu.Util;

final class Ham extends BoomPiece {
    public Ham(Context context) {
        super(context);
    }

    public void init(int i, float f) {
        Drawable drawable = Util.getDrawable(this, C4514R.C4516drawable.piece, null);
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(f);
        Util.setDrawable(this, drawable);
    }

    public void setColor(int i) {
        ((GradientDrawable) getBackground()).setColor(i);
    }

    public void setColorRes(int i) {
        setColor(Util.getColor(getContext(), i));
    }
}
