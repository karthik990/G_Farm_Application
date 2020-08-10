package com.nightonke.boommenu.Piece;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.nightonke.boommenu.C4514R;
import com.nightonke.boommenu.Util;

final class Dot extends BoomPiece {
    public Dot(Context context) {
        super(context);
    }

    public void init(int i, float f) {
        Drawable drawable;
        if (f < 0.0f) {
            drawable = Util.getDrawable(this, C4514R.C4516drawable.piece_dot, null).mutate();
        } else {
            drawable = Util.getDrawable(this, C4514R.C4516drawable.piece, null).mutate();
        }
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        gradientDrawable.setColor(i);
        if (f >= 0.0f) {
            gradientDrawable.setCornerRadius(f);
        }
        Util.setDrawable(this, drawable);
    }

    public void setColor(int i) {
        ((GradientDrawable) getBackground()).setColor(i);
    }

    public void setColorRes(int i) {
        setColor(Util.getColor(getContext(), i));
    }
}
