package jahirfiquitiva.libs.fabsmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.AttributeSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout.DefaultBehavior;

@DefaultBehavior(FABSnackbarBehavior.class)
public class MenuFAB extends TitleFAB {
    public String getTitle() {
        return null;
    }

    public MenuFAB(Context context) {
        super(context);
    }

    public MenuFAB(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MenuFAB(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setTitle(String str) {
        super.setTitle(null);
    }

    public void setImageBitmap(Bitmap bitmap) {
        throw new UnsupportedOperationException("Don't set the bitmap for menu button using this method. Use FABs Menu setMenuButtonIcon() method instead.");
    }

    public void setImageIcon(Icon icon) {
        throw new UnsupportedOperationException("This method is not available for now. Use FABs Menu setMenuButtonIcon() method instead.");
    }

    public void setImageURI(Uri uri) {
        throw new UnsupportedOperationException("Don't set the uri for menu button using this method. Use FABs Menu setMenuButtonIcon() method instead.");
    }

    public void setImageResource(int i) {
        throw new UnsupportedOperationException("Don't set the resource for menu button using this method. Use FABs Menu setMenuButtonIcon() method instead.");
    }
}
