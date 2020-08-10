package p101me.zhanghai.android.materialratingbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.appcompat.content.res.AppCompatResources;
import p101me.zhanghai.android.materialratingbar.internal.ThemeUtils;

/* renamed from: me.zhanghai.android.materialratingbar.MaterialRatingDrawable */
public class MaterialRatingDrawable extends LayerDrawable {
    public MaterialRatingDrawable(Context context) {
        super(new Drawable[]{createLayerDrawable(C6022R.C6024drawable.mrb_star_border_icon_black_36dp, false, context), createClippedLayerDrawable(C6022R.C6024drawable.mrb_star_border_icon_black_36dp, true, context), createClippedLayerDrawable(C6022R.C6024drawable.mrb_star_icon_black_36dp, true, context)});
        setId(0, 16908288);
        setId(1, 16908303);
        setId(2, 16908301);
    }

    private static Drawable createLayerDrawable(int i, boolean z, Context context) {
        int colorFromAttrRes = ThemeUtils.getColorFromAttrRes(z ? C6022R.attr.colorControlActivated : C6022R.attr.colorControlNormal, context);
        TileDrawable tileDrawable = new TileDrawable(AppCompatResources.getDrawable(context, i));
        tileDrawable.setTint(colorFromAttrRes);
        return tileDrawable;
    }

    private static Drawable createClippedLayerDrawable(int i, boolean z, Context context) {
        return new ClipDrawableCompat(createLayerDrawable(i, z, context), 3, 1);
    }

    public float getTileRatio() {
        Drawable drawable = getTileDrawableByLayerId(16908301).getDrawable();
        return ((float) drawable.getIntrinsicWidth()) / ((float) drawable.getIntrinsicHeight());
    }

    public void setStarCount(int i) {
        getTileDrawableByLayerId(16908288).setTileCount(i);
        getTileDrawableByLayerId(16908303).setTileCount(i);
        getTileDrawableByLayerId(16908301).setTileCount(i);
    }

    private TileDrawable getTileDrawableByLayerId(int i) {
        Drawable findDrawableByLayerId = findDrawableByLayerId(i);
        if (i == 16908288) {
            return (TileDrawable) findDrawableByLayerId;
        }
        if (i == 16908301 || i == 16908303) {
            return (TileDrawable) ((ClipDrawableCompat) findDrawableByLayerId).getDrawable();
        }
        throw new RuntimeException();
    }
}
