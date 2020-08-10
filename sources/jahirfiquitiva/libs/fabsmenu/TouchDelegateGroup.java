package jahirfiquitiva.libs.fabsmenu;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import java.util.ArrayList;

public class TouchDelegateGroup extends TouchDelegate {
    private static final Rect USELESS_HACKY_RECT = new Rect();
    private TouchDelegate mCurrentTouchDelegate;
    private boolean mEnabled;
    private final ArrayList<TouchDelegate> mTouchDelegates = new ArrayList<>();

    public TouchDelegateGroup(View view) {
        super(USELESS_HACKY_RECT, view);
    }

    public void addTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegates.add(touchDelegate);
    }

    public void removeTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegates.remove(touchDelegate);
        if (this.mCurrentTouchDelegate.equals(touchDelegate)) {
            this.mCurrentTouchDelegate = null;
        }
    }

    public void clearTouchDelegates() {
        this.mTouchDelegates.clear();
        this.mCurrentTouchDelegate = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        if (r0 != 3) goto L_0x003d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.mEnabled
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r7.getAction()
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x0020
            if (r0 == r3) goto L_0x001a
            r4 = 2
            if (r0 == r4) goto L_0x0017
            r4 = 3
            if (r0 == r4) goto L_0x001a
            goto L_0x003d
        L_0x0017:
            android.view.TouchDelegate r2 = r6.mCurrentTouchDelegate
            goto L_0x003d
        L_0x001a:
            android.view.TouchDelegate r0 = r6.mCurrentTouchDelegate
            r6.mCurrentTouchDelegate = r2
            r2 = r0
            goto L_0x003d
        L_0x0020:
            r0 = 0
        L_0x0021:
            java.util.ArrayList<android.view.TouchDelegate> r4 = r6.mTouchDelegates
            int r4 = r4.size()
            if (r0 >= r4) goto L_0x003d
            java.util.ArrayList<android.view.TouchDelegate> r4 = r6.mTouchDelegates
            java.lang.Object r4 = r4.get(r0)
            android.view.TouchDelegate r4 = (android.view.TouchDelegate) r4
            boolean r5 = r4.onTouchEvent(r7)
            if (r5 == 0) goto L_0x003a
            r6.mCurrentTouchDelegate = r4
            return r3
        L_0x003a:
            int r0 = r0 + 1
            goto L_0x0021
        L_0x003d:
            if (r2 == 0) goto L_0x0046
            boolean r7 = r2.onTouchEvent(r7)
            if (r7 == 0) goto L_0x0046
            r1 = 1
        L_0x0046:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: jahirfiquitiva.libs.fabsmenu.TouchDelegateGroup.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }
}
