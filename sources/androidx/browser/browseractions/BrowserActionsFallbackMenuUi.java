package androidx.browser.browseractions;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.browser.C0227R;
import androidx.core.widget.TextViewCompat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.protocol.HTTP;

@Deprecated
class BrowserActionsFallbackMenuUi implements OnItemClickListener {
    private static final String TAG = "BrowserActionskMenuUi";
    private BrowserActionsFallbackMenuDialog mBrowserActionsDialog;
    final Context mContext;
    private final List<BrowserActionItem> mMenuItems;
    BrowserActionsFallMenuUiListener mMenuUiListener;
    final Uri mUri;

    interface BrowserActionsFallMenuUiListener {
        void onMenuShown(View view);
    }

    BrowserActionsFallbackMenuUi(Context context, Uri uri, List<BrowserActionItem> list) {
        this.mContext = context;
        this.mUri = uri;
        this.mMenuItems = buildFallbackMenuItemList(list);
    }

    /* access modifiers changed from: 0000 */
    public void setMenuUiListener(BrowserActionsFallMenuUiListener browserActionsFallMenuUiListener) {
        this.mMenuUiListener = browserActionsFallMenuUiListener;
    }

    private List<BrowserActionItem> buildFallbackMenuItemList(List<BrowserActionItem> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BrowserActionItem(this.mContext.getString(C0227R.C0231string.fallback_menu_item_open_in_browser), buildOpenInBrowserAction()));
        arrayList.add(new BrowserActionItem(this.mContext.getString(C0227R.C0231string.fallback_menu_item_copy_link), buildCopyAction()));
        arrayList.add(new BrowserActionItem(this.mContext.getString(C0227R.C0231string.fallback_menu_item_share_link), buildShareAction()));
        arrayList.addAll(list);
        return arrayList;
    }

    private PendingIntent buildOpenInBrowserAction() {
        return PendingIntent.getActivity(this.mContext, 0, new Intent("android.intent.action.VIEW", this.mUri), 0);
    }

    private PendingIntent buildShareAction() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", this.mUri.toString());
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        return PendingIntent.getActivity(this.mContext, 0, intent, 0);
    }

    private Runnable buildCopyAction() {
        return new Runnable() {
            public void run() {
                ((ClipboardManager) BrowserActionsFallbackMenuUi.this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("url", BrowserActionsFallbackMenuUi.this.mUri.toString()));
                Toast.makeText(BrowserActionsFallbackMenuUi.this.mContext, BrowserActionsFallbackMenuUi.this.mContext.getString(C0227R.C0231string.copy_toast_msg), 0).show();
            }
        };
    }

    public void displayMenu() {
        final View inflate = LayoutInflater.from(this.mContext).inflate(C0227R.layout.browser_actions_context_menu_page, null);
        this.mBrowserActionsDialog = new BrowserActionsFallbackMenuDialog(this.mContext, initMenuView(inflate));
        this.mBrowserActionsDialog.setContentView(inflate);
        if (this.mMenuUiListener != null) {
            this.mBrowserActionsDialog.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface dialogInterface) {
                    if (BrowserActionsFallbackMenuUi.this.mMenuUiListener == null) {
                        Log.e(BrowserActionsFallbackMenuUi.TAG, "Cannot trigger menu item listener, it is null");
                    } else {
                        BrowserActionsFallbackMenuUi.this.mMenuUiListener.onMenuShown(inflate);
                    }
                }
            });
        }
        this.mBrowserActionsDialog.show();
    }

    private BrowserActionsFallbackMenuView initMenuView(View view) {
        BrowserActionsFallbackMenuView browserActionsFallbackMenuView = (BrowserActionsFallbackMenuView) view.findViewById(C0227R.C0230id.browser_actions_menu_view);
        final TextView textView = (TextView) view.findViewById(C0227R.C0230id.browser_actions_header_text);
        textView.setText(this.mUri.toString());
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (TextViewCompat.getMaxLines(textView) == Integer.MAX_VALUE) {
                    textView.setMaxLines(1);
                    textView.setEllipsize(TruncateAt.END);
                    return;
                }
                textView.setMaxLines(Integer.MAX_VALUE);
                textView.setEllipsize(null);
            }
        });
        ListView listView = (ListView) view.findViewById(C0227R.C0230id.browser_actions_menu_items);
        listView.setAdapter(new BrowserActionsFallbackMenuAdapter(this.mMenuItems, this.mContext));
        listView.setOnItemClickListener(this);
        return browserActionsFallbackMenuView;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        BrowserActionItem browserActionItem = (BrowserActionItem) this.mMenuItems.get(i);
        PendingIntent action = browserActionItem.getAction();
        String str = TAG;
        if (action != null) {
            try {
                browserActionItem.getAction().send();
            } catch (CanceledException e) {
                Log.e(str, "Failed to send custom item action", e);
            }
        } else if (browserActionItem.getRunnableAction() != null) {
            browserActionItem.getRunnableAction().run();
        }
        BrowserActionsFallbackMenuDialog browserActionsFallbackMenuDialog = this.mBrowserActionsDialog;
        if (browserActionsFallbackMenuDialog == null) {
            Log.e(str, "Cannot dismiss dialog, it has already been dismissed.");
        } else {
            browserActionsFallbackMenuDialog.dismiss();
        }
    }
}
