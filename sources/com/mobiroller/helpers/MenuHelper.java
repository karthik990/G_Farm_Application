package com.mobiroller.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.activities.chat.ChatAdminActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.constants.ModuleConstants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FCMNotificationModel.Action;
import com.mobiroller.models.IntroModel;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.DialogCallBack;
import com.mobiroller.util.NotificationActionUtil;
import com.mobiroller.util.ServerUtilities;
import com.mobiroller.views.dialogs.RatingDialog;
import com.mobiroller.views.dialogs.RatingDialog.Builder.RatingDialogFormListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import p043io.reactivex.annotations.SchedulerSupport;

public class MenuHelper {
    /* access modifiers changed from: private */
    public String RegId;
    ApiRequestManager apiRequestManager;
    private MobiRollerApplication app;
    /* access modifiers changed from: private */
    public Activity context;
    private IntroModel introObject = null;
    private JSONParser jParserNew;
    /* access modifiers changed from: private */
    public String language;
    private String[] languageArray;
    private String[] languageCodeArray;
    private String languageCodes;
    /* access modifiers changed from: private */
    public String[] localeCodes;
    private LocalizationHelper localizationHelper;
    private NetworkHelper networkHelper;
    ProgressViewHelper progressViewHelper;
    private ScreenHelper screenHelper;
    private ScreenModel screenModel;
    /* access modifiers changed from: private */
    public SharedPrefHelper sharedPrefHelper;

    @Inject
    public MenuHelper(Activity activity, NetworkHelper networkHelper2, JSONParser jSONParser, SharedPrefHelper sharedPrefHelper2, ApiRequestManager apiRequestManager2, LocalizationHelper localizationHelper2, MobiRollerApplication mobiRollerApplication, ScreenHelper screenHelper2) {
        this.context = activity;
        this.networkHelper = networkHelper2;
        this.jParserNew = jSONParser;
        this.sharedPrefHelper = sharedPrefHelper2;
        this.apiRequestManager = apiRequestManager2;
        this.localizationHelper = localizationHelper2;
        this.app = mobiRollerApplication;
        this.screenHelper = screenHelper2;
    }

    public MenuHelper(Activity activity) {
        this.context = activity;
        this.networkHelper = UtilManager.networkHelper();
        this.jParserNew = new JSONParser();
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.apiRequestManager = new ApiRequestManager();
        this.localizationHelper = UtilManager.localizationHelper();
        this.app = MobiRollerApplication.app;
        this.screenHelper = new ScreenHelper(activity);
    }

    private void showIntro(final IntroModel introModel) {
        if (introModel != null) {
            try {
                if (introModel.getIntroMessageScreenID().equals("null")) {
                    if (introModel.getIntroMessageScreenID() == null) {
                        if (getLocalizedTitle(this.context, introModel.getIntroMessage()) != null) {
                            new Builder(this.context).title((int) R.string.app_name).content((CharSequence) getLocalizedTitle(this.context, introModel.getIntroMessage())).positiveText((CharSequence) this.context.getString(R.string.close)).icon(ContextCompat.getDrawable(this.context, R.drawable.icon)).show();
                        }
                    }
                }
                if (getLocalizedTitle(this.context, introModel.getIntroMessage()) != null) {
                    if (!getLocalizedTitle(this.context, introModel.getIntroMessage()).isEmpty()) {
                        if (introModel.getIntroMessageScreenType() == null || introModel.getIntroMessageScreenType().isEmpty() || introModel.getIntroMessageScreenID() == null || introModel.getIntroMessageScreenID().isEmpty()) {
                            new Builder(this.context).title((int) R.string.app_name).content((CharSequence) getLocalizedTitle(this.context, introModel.getIntroMessage())).positiveText((CharSequence) this.context.getString(R.string.close)).icon(ContextCompat.getDrawable(this.context, R.drawable.icon)).show();
                        } else {
                            this.context.runOnUiThread(new Runnable(introModel) {
                                final /* synthetic */ IntroModel val$introObject;

                                {
                                    this.val$introObject = r2;
                                }

                                public void run() {
                                    try {
                                        new Builder(MenuHelper.this.context).title((int) R.string.app_name).content((CharSequence) MenuHelper.this.getLocalizedTitle(MenuHelper.this.context, this.val$introObject.getIntroMessage())).positiveText((CharSequence) MenuHelper.this.context.getString(R.string.notification_dialog_positive_button)).negativeText(17039369).onPositive(new SingleButtonCallback() {
                                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                                try {
                                                    if (MenuHelper.this.checkActivity(MenuHelper.this.context, C42611.this.val$introObject.getIntroMessageScreenType())) {
                                                        ActivityHandler.startActivity(MenuHelper.this.context, introModel.getIntroMessageScreenID(), introModel.getIntroMessageScreenType(), introModel.introMessageSubScreenType, null, new ArrayList());
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).icon(ContextCompat.getDrawable(MenuHelper.this.context, R.drawable.icon)).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startActivityFromScreenModel(ScreenModel screenModel2, String str, String str2, String str3, boolean z) {
        ActivityHandler.startActivity(this.context, str3, str, str2, null, null);
    }

    public String getLocalizedTitle(Context context2, String str) {
        String str2 = "";
        List asList = Arrays.asList(new String[]{str2});
        if (this.sharedPrefHelper.getLocaleCodes() != null) {
            asList = Arrays.asList(this.sharedPrefHelper.getLocaleCodes().split(","));
        }
        StringBuilder sb = new StringBuilder();
        String str3 = "<";
        sb.append(str3);
        sb.append(LocaleHelper.getLocale().toUpperCase());
        String str4 = ">";
        sb.append(str4);
        String[] split = str.split(sb.toString());
        if (split.length <= 1) {
            if (asList.contains(LocaleHelper.getLocale().toUpperCase())) {
                return str2;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(this.sharedPrefHelper.getDefaultLang().toUpperCase());
            sb2.append(str4);
            split = str.split(sb2.toString());
        }
        int length = split.length - 2;
        return length > 0 ? split[length] : str;
    }

    public boolean checkActivity(Context context2, String str) {
        for (String equalsIgnoreCase : context2.getResources().getStringArray(R.array.activities)) {
            if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }

    public ScreenModel checkTableItems(ScreenModel screenModel2) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        String[] stringArray = this.context.getResources().getStringArray(R.array.activities);
        ArrayList tableItems = screenModel2.getTableItems();
        for (int i = 0; i < tableItems.size(); i++) {
            int length = stringArray.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = false;
                    break;
                }
                if (((TableItemsModel) tableItems.get(i)).getScreenType().equalsIgnoreCase(stringArray[i2])) {
                    z = true;
                    break;
                }
                i2++;
            }
            if (z) {
                arrayList.add(tableItems.get(i));
            }
        }
        screenModel2.setTableItems(arrayList);
        return screenModel2;
    }

    public NavigationModel checkNavItems(NavigationModel navigationModel) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        String[] stringArray = this.context.getResources().getStringArray(R.array.activities);
        ArrayList navigationItems = navigationModel.getNavigationItems();
        for (int i = 0; i < navigationItems.size(); i++) {
            int length = stringArray.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (!((NavigationItemModel) navigationItems.get(i)).getScreenType().equalsIgnoreCase(stringArray[i2])) {
                    i2++;
                } else if (!((NavigationItemModel) navigationItems.get(i)).getScreenType().equalsIgnoreCase(ModuleConstants.HTML_MODULE) || ((NavigationItemModel) navigationItems.get(i)).screenSubtype == null || !((NavigationItemModel) navigationItems.get(i)).screenSubtype.equalsIgnoreCase(ModuleConstants.WEATHER_MODULE)) {
                    z = true;
                }
            }
            z = false;
            if (z) {
                arrayList.add(navigationItems.get(i));
            }
        }
        navigationModel.setNavigationItems(arrayList);
        return navigationModel;
    }

    public void checkRateStatus() {
        if (this.sharedPrefHelper.getRateApp()) {
            RatingDialog build = new RatingDialog.Builder(this.context).title(this.context.getString(R.string.rating_dialog_experience)).positiveButtonText(this.context.getString(R.string.rating_dialog_remind_later)).negativeButtonText(this.context.getString(R.string.rating_dialog_never)).formTitle(this.context.getString(R.string.rating_dialog_feedback_title)).formHint(this.context.getString(R.string.rating_dialog_suggestions)).formSubmitText(this.context.getString(R.string.rating_dialog_submit)).formCancelText(this.context.getString(R.string.rating_dialog_cancel)).threshold(3.0f).session(3).onRatingBarFormSumbit(new RatingDialogFormListener() {
                public void onFormSubmitted(String str, int i) {
                    MenuHelper.this.apiRequestManager.sendFeedback(str, i);
                    Toast.makeText(MenuHelper.this.context, MenuHelper.this.context.getString(R.string.feedback_sent), 0).show();
                }
            }).build();
            build.setCancelable(false);
            build.setCanceledOnTouchOutside(false);
            build.show();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01e1, code lost:
        if (r1.equals("avePDFView") != false) goto L_0x01f5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.mobiroller.models.ScreenModel getFragment(com.mobiroller.models.NavigationItemModel r14, com.mobiroller.models.ScreenModel r15) throws com.mobiroller.util.exceptions.UserFriendlyException, com.mobiroller.util.exceptions.IntentException {
        /*
            com.mobiroller.views.EmptyFragment r0 = new com.mobiroller.views.EmptyFragment
            r0.<init>()
            boolean r1 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            java.lang.String r2 = "aveECommerceView"
            java.lang.String r3 = "aveChatView"
            if (r1 == 0) goto L_0x0047
            java.lang.String r1 = r14.getScreenType()
            boolean r1 = isSupportedForPreview(r1)
            if (r1 != 0) goto L_0x0047
            com.mobiroller.fragments.preview.NotSupportedFragment r0 = new com.mobiroller.fragments.preview.NotSupportedFragment
            r0.<init>()
            java.lang.String r1 = r14.getScreenType()
            boolean r1 = r1.equalsIgnoreCase(r3)
            if (r1 == 0) goto L_0x0030
            r14 = r0
            com.mobiroller.fragments.preview.NotSupportedFragment r14 = (com.mobiroller.fragments.preview.NotSupportedFragment) r14
            r1 = 2131820662(0x7f110076, float:1.9274045E38)
            r14.setModule(r1)
            goto L_0x0043
        L_0x0030:
            java.lang.String r14 = r14.getScreenType()
            boolean r14 = r14.equalsIgnoreCase(r2)
            if (r14 == 0) goto L_0x0043
            r14 = r0
            com.mobiroller.fragments.preview.NotSupportedFragment r14 = (com.mobiroller.fragments.preview.NotSupportedFragment) r14
            r1 = 2131820884(0x7f110154, float:1.9274496E38)
            r14.setModule(r1)
        L_0x0043:
            r15.setFragment(r0)
            return r15
        L_0x0047:
            java.lang.String r1 = r14.getScreenType()
            int r4 = r1.hashCode()
            r5 = 0
            r6 = -1
            r7 = 2
            r8 = 1
            r9 = 4
            r10 = 3
            switch(r4) {
                case -1889860823: goto L_0x012a;
                case -1771486603: goto L_0x011f;
                case -1550268295: goto L_0x0115;
                case -1223439691: goto L_0x010a;
                case -586554099: goto L_0x0101;
                case -442817369: goto L_0x00f6;
                case -312878216: goto L_0x00ec;
                case 330767803: goto L_0x00e1;
                case 429917810: goto L_0x00d7;
                case 923719348: goto L_0x00cc;
                case 951949356: goto L_0x00c1;
                case 961437749: goto L_0x00b7;
                case 1243960250: goto L_0x00ab;
                case 1475386597: goto L_0x009f;
                case 1643126201: goto L_0x0093;
                case 1733377831: goto L_0x0088;
                case 1798267217: goto L_0x007c;
                case 1807071320: goto L_0x0070;
                case 1854809030: goto L_0x0065;
                case 2018416945: goto L_0x005a;
                default: goto L_0x0058;
            }
        L_0x0058:
            goto L_0x0134
        L_0x005a:
            java.lang.String r2 = "aveMapView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 5
            goto L_0x0135
        L_0x0065:
            java.lang.String r2 = "aveTweetView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 6
            goto L_0x0135
        L_0x0070:
            java.lang.String r2 = "aveSettingsView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 15
            goto L_0x0135
        L_0x007c:
            java.lang.String r2 = "aveFavoriteView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 17
            goto L_0x0135
        L_0x0088:
            java.lang.String r2 = "aveRSSView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 2
            goto L_0x0135
        L_0x0093:
            java.lang.String r2 = "aveTodoListView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 12
            goto L_0x0135
        L_0x009f:
            java.lang.String r2 = "aveMP3View"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 11
            goto L_0x0135
        L_0x00ab:
            java.lang.String r2 = "aveYoutubeAdvancedView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 18
            goto L_0x0135
        L_0x00b7:
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 19
            goto L_0x0135
        L_0x00c1:
            java.lang.String r2 = "aveMainListView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 1
            goto L_0x0135
        L_0x00cc:
            java.lang.String r2 = "aveTvBroadcastView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 10
            goto L_0x0135
        L_0x00d7:
            java.lang.String r2 = "aveCustomScreenView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 4
            goto L_0x0135
        L_0x00e1:
            java.lang.String r2 = "aveRadioBroadcastView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 9
            goto L_0x0135
        L_0x00ec:
            java.lang.String r2 = "aveYoutubeView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 7
            goto L_0x0135
        L_0x00f6:
            java.lang.String r2 = "aveNoteView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 13
            goto L_0x0135
        L_0x0101:
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0134
            r1 = 16
            goto L_0x0135
        L_0x010a:
            java.lang.String r2 = "aveNotificationBoxView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 14
            goto L_0x0135
        L_0x0115:
            java.lang.String r2 = "aveFormView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 3
            goto L_0x0135
        L_0x011f:
            java.lang.String r2 = "avePhotoGalleryView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 8
            goto L_0x0135
        L_0x012a:
            java.lang.String r2 = "aveWebView"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0134
            r1 = 0
            goto L_0x0135
        L_0x0134:
            r1 = -1
        L_0x0135:
            switch(r1) {
                case 0: goto L_0x01b0;
                case 1: goto L_0x01aa;
                case 2: goto L_0x01a4;
                case 3: goto L_0x019e;
                case 4: goto L_0x0198;
                case 5: goto L_0x0192;
                case 6: goto L_0x018c;
                case 7: goto L_0x0186;
                case 8: goto L_0x0180;
                case 9: goto L_0x017a;
                case 10: goto L_0x0174;
                case 11: goto L_0x016e;
                case 12: goto L_0x0168;
                case 13: goto L_0x0162;
                case 14: goto L_0x015c;
                case 15: goto L_0x0156;
                case 16: goto L_0x014f;
                case 17: goto L_0x0148;
                case 18: goto L_0x0141;
                case 19: goto L_0x013a;
                default: goto L_0x0138;
            }
        L_0x0138:
            goto L_0x01b5
        L_0x013a:
            com.mobiroller.fragments.aveECommerceViewFragment r0 = new com.mobiroller.fragments.aveECommerceViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0141:
            com.mobiroller.fragments.aveYoutubeAdvancedViewFragment r0 = new com.mobiroller.fragments.aveYoutubeAdvancedViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0148:
            com.mobiroller.fragments.aveFavoriteViewFragment r0 = new com.mobiroller.fragments.aveFavoriteViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x014f:
            com.mobiroller.fragments.aveChatViewFragment r0 = new com.mobiroller.fragments.aveChatViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0156:
            com.mobiroller.fragments.aveSettingsViewFragment r0 = new com.mobiroller.fragments.aveSettingsViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x015c:
            com.mobiroller.fragments.aveNotificationBoxViewFragment r0 = new com.mobiroller.fragments.aveNotificationBoxViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0162:
            com.mobiroller.fragments.aveNoteViewFragment r0 = new com.mobiroller.fragments.aveNoteViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0168:
            com.mobiroller.fragments.aveTodoListViewFragment r0 = new com.mobiroller.fragments.aveTodoListViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x016e:
            com.mobiroller.fragments.aveMP3ViewFragment r0 = new com.mobiroller.fragments.aveMP3ViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0174:
            com.mobiroller.fragments.aveTVBroadcastViewFragment r0 = new com.mobiroller.fragments.aveTVBroadcastViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x017a:
            com.mobiroller.fragments.aveRadioBroadcastViewFragment r0 = new com.mobiroller.fragments.aveRadioBroadcastViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0180:
            com.mobiroller.fragments.avePhotoGalleryViewFragment r0 = new com.mobiroller.fragments.avePhotoGalleryViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0186:
            com.mobiroller.fragments.aveYoutubeViewFragment r0 = new com.mobiroller.fragments.aveYoutubeViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x018c:
            com.mobiroller.fragments.aveTweetViewFragment r0 = new com.mobiroller.fragments.aveTweetViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0192:
            com.mobiroller.fragments.aveMapViewFragment r0 = new com.mobiroller.fragments.aveMapViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x0198:
            com.mobiroller.fragments.aveCustomScreenViewFragment r0 = new com.mobiroller.fragments.aveCustomScreenViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x019e:
            com.mobiroller.fragments.aveFormViewFragment r0 = new com.mobiroller.fragments.aveFormViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x01a4:
            com.mobiroller.fragments.aveRSSViewFragment r0 = new com.mobiroller.fragments.aveRSSViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x01aa:
            com.mobiroller.fragments.aveMainListViewFragment r0 = new com.mobiroller.fragments.aveMainListViewFragment
            r0.<init>()
            goto L_0x01b5
        L_0x01b0:
            com.mobiroller.fragments.aveWebViewFragment r0 = new com.mobiroller.fragments.aveWebViewFragment
            r0.<init>()
        L_0x01b5:
            java.lang.String r1 = r14.screenSubtype
            java.lang.String r2 = "aveEmergencyCallView"
            java.lang.String r4 = "aveAboutUsView"
            java.lang.String r11 = "aveFAQView"
            java.lang.String r12 = "aveCatalogView"
            if (r1 == 0) goto L_0x021d
            java.lang.String r1 = r14.screenSubtype
            int r13 = r1.hashCode()
            switch(r13) {
                case -1243175570: goto L_0x01ec;
                case -843903509: goto L_0x01e4;
                case -483072569: goto L_0x01db;
                case 874838880: goto L_0x01d3;
                case 1879413220: goto L_0x01cb;
                default: goto L_0x01ca;
            }
        L_0x01ca:
            goto L_0x01f4
        L_0x01cb:
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x01f4
            r5 = 2
            goto L_0x01f5
        L_0x01d3:
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x01f4
            r5 = 4
            goto L_0x01f5
        L_0x01db:
            java.lang.String r13 = "avePDFView"
            boolean r1 = r1.equals(r13)
            if (r1 == 0) goto L_0x01f4
            goto L_0x01f5
        L_0x01e4:
            boolean r1 = r1.equals(r11)
            if (r1 == 0) goto L_0x01f4
            r5 = 1
            goto L_0x01f5
        L_0x01ec:
            boolean r1 = r1.equals(r12)
            if (r1 == 0) goto L_0x01f4
            r5 = 3
            goto L_0x01f5
        L_0x01f4:
            r5 = -1
        L_0x01f5:
            if (r5 == 0) goto L_0x0218
            if (r5 == r8) goto L_0x0212
            if (r5 == r7) goto L_0x020c
            if (r5 == r10) goto L_0x0206
            if (r5 == r9) goto L_0x0200
            goto L_0x021d
        L_0x0200:
            com.mobiroller.fragments.aveAboutUsViewFragment r0 = new com.mobiroller.fragments.aveAboutUsViewFragment
            r0.<init>()
            goto L_0x021d
        L_0x0206:
            com.mobiroller.fragments.aveCatalogViewFragment r0 = new com.mobiroller.fragments.aveCatalogViewFragment
            r0.<init>()
            goto L_0x021d
        L_0x020c:
            com.mobiroller.fragments.aveEmergencyCallViewFragment r0 = new com.mobiroller.fragments.aveEmergencyCallViewFragment
            r0.<init>()
            goto L_0x021d
        L_0x0212:
            com.mobiroller.fragments.aveFAQViewFragment r0 = new com.mobiroller.fragments.aveFAQViewFragment
            r0.<init>()
            goto L_0x021d
        L_0x0218:
            com.mobiroller.fragments.avePDFViewFragment r0 = new com.mobiroller.fragments.avePDFViewFragment
            r0.<init>()
        L_0x021d:
            java.lang.String r1 = r14.getScreenType()
            boolean r1 = r1.equalsIgnoreCase(r3)
            if (r1 == 0) goto L_0x0235
            boolean r1 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            if (r1 != 0) goto L_0x022c
            goto L_0x0235
        L_0x022c:
            com.mobiroller.util.exceptions.UserFriendlyException r14 = new com.mobiroller.util.exceptions.UserFriendlyException
            r15 = 2131821226(0x7f1102aa, float:1.927519E38)
            r14.<init>(r15)
            throw r14
        L_0x0235:
            java.lang.String r1 = r14.getScreenType()
            boolean r1 = r1.equalsIgnoreCase(r3)
            if (r1 == 0) goto L_0x0253
            com.mobiroller.helpers.SharedPrefHelper r1 = com.mobiroller.helpers.UtilManager.sharedPrefHelper()
            boolean r1 = r1.getIsChatVersionSupported()
            if (r1 == 0) goto L_0x024a
            goto L_0x0253
        L_0x024a:
            com.mobiroller.util.exceptions.UserFriendlyException r14 = new com.mobiroller.util.exceptions.UserFriendlyException
            r15 = 2131820658(0x7f110072, float:1.9274037E38)
            r14.<init>(r15)
            throw r14
        L_0x0253:
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "com.mobiroller.activities."
            r3.append(r5)
            java.lang.String r5 = r14.getScreenType()
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            java.lang.String r5 = "class"
            r1.putString(r5, r3)
            java.lang.String r3 = r14.getScreenType()
            java.lang.String r5 = "screenType"
            r1.putString(r5, r3)
            java.lang.String r3 = r14.screenSubtype
            java.lang.String r6 = "screenSubType"
            r1.putString(r6, r3)
            java.lang.String r3 = r14.getAccountScreenID()
            java.lang.String r7 = "screenId"
            r1.putString(r7, r3)
            java.util.ArrayList r3 = r14.getRoles()
            java.lang.String r7 = "roles"
            r1.putSerializable(r7, r3)
            if (r15 == 0) goto L_0x037a
            java.lang.String r3 = r14.getAccountScreenID()
            com.mobiroller.helpers.JSONStorage.putScreenModel(r3, r15)
            java.lang.String r3 = r14.getScreenType()
            java.lang.String r7 = "aveCallNowView"
            boolean r3 = r3.equals(r7)
            if (r3 == 0) goto L_0x02da
            java.lang.String r14 = r15.getPhoneNumber()
            if (r14 != 0) goto L_0x02b1
            goto L_0x034f
        L_0x02b1:
            android.content.Intent r14 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.DIAL"
            r14.<init>(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "tel:"
            r0.append(r1)
            java.lang.String r15 = r15.getPhoneNumber()
            r0.append(r15)
            java.lang.String r15 = r0.toString()
            android.net.Uri r15 = android.net.Uri.parse(r15)
            r14.setData(r15)
            com.mobiroller.util.exceptions.IntentException r15 = new com.mobiroller.util.exceptions.IntentException
            r15.<init>(r14)
            throw r15
        L_0x02da:
            java.lang.String r3 = r14.getScreenType()
            java.lang.String r7 = "aveShareView"
            boolean r3 = r3.equals(r7)
            if (r3 != 0) goto L_0x0356
            java.lang.String r3 = r14.getScreenType()
            java.lang.String r5 = "aveHtmlView"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0302
            java.lang.String r3 = r15.getScreenType()
            boolean r3 = r3.equalsIgnoreCase(r11)
            if (r3 == 0) goto L_0x0302
            com.mobiroller.fragments.aveFAQViewFragment r0 = new com.mobiroller.fragments.aveFAQViewFragment
            r0.<init>()
            goto L_0x034f
        L_0x0302:
            java.lang.String r3 = r14.getScreenType()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x031c
            java.lang.String r3 = r15.getScreenType()
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x031c
            com.mobiroller.fragments.aveEmergencyCallViewFragment r0 = new com.mobiroller.fragments.aveEmergencyCallViewFragment
            r0.<init>()
            goto L_0x034f
        L_0x031c:
            java.lang.String r2 = r14.getScreenType()
            boolean r2 = r2.equals(r5)
            if (r2 == 0) goto L_0x0336
            java.lang.String r2 = r15.getScreenType()
            boolean r2 = r2.equalsIgnoreCase(r12)
            if (r2 == 0) goto L_0x0336
            com.mobiroller.fragments.aveCatalogViewFragment r0 = new com.mobiroller.fragments.aveCatalogViewFragment
            r0.<init>()
            goto L_0x034f
        L_0x0336:
            java.lang.String r14 = r14.getScreenType()
            boolean r14 = r14.equals(r5)
            if (r14 == 0) goto L_0x034f
            java.lang.String r14 = r15.getScreenType()
            boolean r14 = r14.equalsIgnoreCase(r4)
            if (r14 == 0) goto L_0x034f
            com.mobiroller.fragments.aveAboutUsViewFragment r0 = new com.mobiroller.fragments.aveAboutUsViewFragment
            r0.<init>()
        L_0x034f:
            r0.setArguments(r1)
            r15.setFragment(r0)
            return r15
        L_0x0356:
            android.content.Intent r15 = new android.content.Intent
            com.mobiroller.MobiRollerApplication r0 = com.mobiroller.MobiRollerApplication.app
            java.lang.Class<com.mobiroller.activities.aveShareView> r1 = com.mobiroller.activities.aveShareView.class
            r15.<init>(r0, r1)
            java.lang.String r0 = r14.getScreenType()
            r15.putExtra(r5, r0)
            java.lang.String r0 = r14.screenSubtype
            r15.putExtra(r6, r0)
            java.lang.String r14 = r14.getAccountScreenID()
            java.lang.String r0 = "screenId"
            r15.putExtra(r0, r14)
            com.mobiroller.util.exceptions.IntentException r14 = new com.mobiroller.util.exceptions.IntentException
            r14.<init>(r15)
            throw r14
        L_0x037a:
            r14 = 0
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.MenuHelper.getFragment(com.mobiroller.models.NavigationItemModel, com.mobiroller.models.ScreenModel):com.mobiroller.models.ScreenModel");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.mobiroller.models.ScreenModel getFragment(com.mobiroller.models.NavigationItemModel r18, com.mobiroller.models.ScreenModel r19, android.app.Activity r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            com.mobiroller.views.EmptyFragment r4 = new com.mobiroller.views.EmptyFragment
            r4.<init>()
            boolean r5 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            if (r5 == 0) goto L_0x0024
            java.lang.String r5 = r18.getScreenType()
            boolean r5 = isSupportedForPreview(r5)
            if (r5 != 0) goto L_0x0024
            com.mobiroller.fragments.preview.NotSupportedFragment r1 = new com.mobiroller.fragments.preview.NotSupportedFragment
            r1.<init>()
            r2.setFragment(r1)
            return r2
        L_0x0024:
            java.lang.String r5 = r18.getScreenType()
            int r6 = r5.hashCode()
            r9 = 2
            r10 = 1
            r11 = 4
            r12 = 3
            java.lang.String r13 = "aveChatView"
            switch(r6) {
                case -1889860823: goto L_0x0109;
                case -1771486603: goto L_0x00fe;
                case -1550268295: goto L_0x00f4;
                case -1223439691: goto L_0x00e9;
                case -586554099: goto L_0x00e0;
                case -442817369: goto L_0x00d5;
                case -312878216: goto L_0x00cb;
                case 330767803: goto L_0x00c0;
                case 429917810: goto L_0x00b6;
                case 923719348: goto L_0x00ab;
                case 951949356: goto L_0x00a0;
                case 961437749: goto L_0x0094;
                case 1243960250: goto L_0x0088;
                case 1475386597: goto L_0x007c;
                case 1643126201: goto L_0x0070;
                case 1733377831: goto L_0x0065;
                case 1798267217: goto L_0x0059;
                case 1807071320: goto L_0x004d;
                case 1854809030: goto L_0x0042;
                case 2018416945: goto L_0x0037;
                default: goto L_0x0035;
            }
        L_0x0035:
            goto L_0x0113
        L_0x0037:
            java.lang.String r6 = "aveMapView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 5
            goto L_0x0114
        L_0x0042:
            java.lang.String r6 = "aveTweetView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 6
            goto L_0x0114
        L_0x004d:
            java.lang.String r6 = "aveSettingsView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 15
            goto L_0x0114
        L_0x0059:
            java.lang.String r6 = "aveFavoriteView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 17
            goto L_0x0114
        L_0x0065:
            java.lang.String r6 = "aveRSSView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 2
            goto L_0x0114
        L_0x0070:
            java.lang.String r6 = "aveTodoListView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 12
            goto L_0x0114
        L_0x007c:
            java.lang.String r6 = "aveMP3View"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 11
            goto L_0x0114
        L_0x0088:
            java.lang.String r6 = "aveYoutubeAdvancedView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 18
            goto L_0x0114
        L_0x0094:
            java.lang.String r6 = "aveECommerceView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 19
            goto L_0x0114
        L_0x00a0:
            java.lang.String r6 = "aveMainListView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 1
            goto L_0x0114
        L_0x00ab:
            java.lang.String r6 = "aveTvBroadcastView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 10
            goto L_0x0114
        L_0x00b6:
            java.lang.String r6 = "aveCustomScreenView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 4
            goto L_0x0114
        L_0x00c0:
            java.lang.String r6 = "aveRadioBroadcastView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 9
            goto L_0x0114
        L_0x00cb:
            java.lang.String r6 = "aveYoutubeView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 7
            goto L_0x0114
        L_0x00d5:
            java.lang.String r6 = "aveNoteView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 13
            goto L_0x0114
        L_0x00e0:
            boolean r5 = r5.equals(r13)
            if (r5 == 0) goto L_0x0113
            r5 = 16
            goto L_0x0114
        L_0x00e9:
            java.lang.String r6 = "aveNotificationBoxView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 14
            goto L_0x0114
        L_0x00f4:
            java.lang.String r6 = "aveFormView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 3
            goto L_0x0114
        L_0x00fe:
            java.lang.String r6 = "avePhotoGalleryView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 8
            goto L_0x0114
        L_0x0109:
            java.lang.String r6 = "aveWebView"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0113
            r5 = 0
            goto L_0x0114
        L_0x0113:
            r5 = -1
        L_0x0114:
            switch(r5) {
                case 0: goto L_0x018f;
                case 1: goto L_0x0189;
                case 2: goto L_0x0183;
                case 3: goto L_0x017d;
                case 4: goto L_0x0177;
                case 5: goto L_0x0171;
                case 6: goto L_0x016b;
                case 7: goto L_0x0165;
                case 8: goto L_0x015f;
                case 9: goto L_0x0159;
                case 10: goto L_0x0153;
                case 11: goto L_0x014d;
                case 12: goto L_0x0147;
                case 13: goto L_0x0141;
                case 14: goto L_0x013b;
                case 15: goto L_0x0135;
                case 16: goto L_0x012e;
                case 17: goto L_0x0127;
                case 18: goto L_0x0120;
                case 19: goto L_0x0119;
                default: goto L_0x0117;
            }
        L_0x0117:
            goto L_0x0194
        L_0x0119:
            com.mobiroller.fragments.aveECommerceViewFragment r4 = new com.mobiroller.fragments.aveECommerceViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0120:
            com.mobiroller.fragments.aveYoutubeAdvancedViewFragment r4 = new com.mobiroller.fragments.aveYoutubeAdvancedViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0127:
            com.mobiroller.fragments.aveFavoriteViewFragment r4 = new com.mobiroller.fragments.aveFavoriteViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x012e:
            com.mobiroller.fragments.aveChatViewFragment r4 = new com.mobiroller.fragments.aveChatViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0135:
            com.mobiroller.fragments.aveSettingsViewFragment r4 = new com.mobiroller.fragments.aveSettingsViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x013b:
            com.mobiroller.fragments.aveNotificationBoxViewFragment r4 = new com.mobiroller.fragments.aveNotificationBoxViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0141:
            com.mobiroller.fragments.aveNoteViewFragment r4 = new com.mobiroller.fragments.aveNoteViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0147:
            com.mobiroller.fragments.aveTodoListViewFragment r4 = new com.mobiroller.fragments.aveTodoListViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x014d:
            com.mobiroller.fragments.aveMP3ViewFragment r4 = new com.mobiroller.fragments.aveMP3ViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0153:
            com.mobiroller.fragments.aveTVBroadcastViewFragment r4 = new com.mobiroller.fragments.aveTVBroadcastViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0159:
            com.mobiroller.fragments.aveRadioBroadcastViewFragment r4 = new com.mobiroller.fragments.aveRadioBroadcastViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x015f:
            com.mobiroller.fragments.avePhotoGalleryViewFragment r4 = new com.mobiroller.fragments.avePhotoGalleryViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0165:
            com.mobiroller.fragments.aveYoutubeViewFragment r4 = new com.mobiroller.fragments.aveYoutubeViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x016b:
            com.mobiroller.fragments.aveTweetViewFragment r4 = new com.mobiroller.fragments.aveTweetViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0171:
            com.mobiroller.fragments.aveMapViewFragment r4 = new com.mobiroller.fragments.aveMapViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0177:
            com.mobiroller.fragments.aveCustomScreenViewFragment r4 = new com.mobiroller.fragments.aveCustomScreenViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x017d:
            com.mobiroller.fragments.aveFormViewFragment r4 = new com.mobiroller.fragments.aveFormViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0183:
            com.mobiroller.fragments.aveRSSViewFragment r4 = new com.mobiroller.fragments.aveRSSViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x0189:
            com.mobiroller.fragments.aveMainListViewFragment r4 = new com.mobiroller.fragments.aveMainListViewFragment
            r4.<init>()
            goto L_0x0194
        L_0x018f:
            com.mobiroller.fragments.aveWebViewFragment r4 = new com.mobiroller.fragments.aveWebViewFragment
            r4.<init>()
        L_0x0194:
            java.lang.String r5 = r1.screenSubtype
            java.lang.String r6 = "aveEmergencyCallView"
            java.lang.String r14 = "aveAboutUsView"
            java.lang.String r15 = "aveFAQView"
            java.lang.String r7 = "aveCatalogView"
            if (r5 == 0) goto L_0x01fd
            java.lang.String r5 = r1.screenSubtype
            int r16 = r5.hashCode()
            switch(r16) {
                case -1243175570: goto L_0x01cc;
                case -843903509: goto L_0x01c4;
                case -483072569: goto L_0x01ba;
                case 874838880: goto L_0x01b2;
                case 1879413220: goto L_0x01aa;
                default: goto L_0x01a9;
            }
        L_0x01a9:
            goto L_0x01d4
        L_0x01aa:
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x01d4
            r5 = 2
            goto L_0x01d5
        L_0x01b2:
            boolean r5 = r5.equals(r14)
            if (r5 == 0) goto L_0x01d4
            r5 = 4
            goto L_0x01d5
        L_0x01ba:
            java.lang.String r8 = "avePDFView"
            boolean r5 = r5.equals(r8)
            if (r5 == 0) goto L_0x01d4
            r5 = 0
            goto L_0x01d5
        L_0x01c4:
            boolean r5 = r5.equals(r15)
            if (r5 == 0) goto L_0x01d4
            r5 = 1
            goto L_0x01d5
        L_0x01cc:
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x01d4
            r5 = 3
            goto L_0x01d5
        L_0x01d4:
            r5 = -1
        L_0x01d5:
            if (r5 == 0) goto L_0x01f8
            if (r5 == r10) goto L_0x01f2
            if (r5 == r9) goto L_0x01ec
            if (r5 == r12) goto L_0x01e6
            if (r5 == r11) goto L_0x01e0
            goto L_0x01fd
        L_0x01e0:
            com.mobiroller.fragments.aveAboutUsViewFragment r4 = new com.mobiroller.fragments.aveAboutUsViewFragment
            r4.<init>()
            goto L_0x01fd
        L_0x01e6:
            com.mobiroller.fragments.aveCatalogViewFragment r4 = new com.mobiroller.fragments.aveCatalogViewFragment
            r4.<init>()
            goto L_0x01fd
        L_0x01ec:
            com.mobiroller.fragments.aveEmergencyCallViewFragment r4 = new com.mobiroller.fragments.aveEmergencyCallViewFragment
            r4.<init>()
            goto L_0x01fd
        L_0x01f2:
            com.mobiroller.fragments.aveFAQViewFragment r4 = new com.mobiroller.fragments.aveFAQViewFragment
            r4.<init>()
            goto L_0x01fd
        L_0x01f8:
            com.mobiroller.fragments.avePDFViewFragment r4 = new com.mobiroller.fragments.avePDFViewFragment
            r4.<init>()
        L_0x01fd:
            java.lang.String r5 = r18.getScreenType()
            boolean r5 = r5.equalsIgnoreCase(r13)
            if (r5 == 0) goto L_0x0222
            boolean r5 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            if (r5 == 0) goto L_0x0222
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            r1.<init>(r2)
            com.mobiroller.helpers.MenuHelper$3 r2 = new com.mobiroller.helpers.MenuHelper$3
            r2.<init>()
            r1.post(r2)
            com.mobiroller.models.ScreenModel r1 = new com.mobiroller.models.ScreenModel
            r1.<init>()
            return r1
        L_0x0222:
            java.lang.String r5 = r18.getScreenType()
            boolean r5 = r5.equalsIgnoreCase(r13)
            if (r5 == 0) goto L_0x024b
            com.mobiroller.helpers.SharedPrefHelper r5 = r0.sharedPrefHelper
            boolean r5 = r5.getIsChatVersionSupported()
            if (r5 != 0) goto L_0x024b
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            r1.<init>(r2)
            com.mobiroller.helpers.MenuHelper$4 r2 = new com.mobiroller.helpers.MenuHelper$4
            r2.<init>()
            r1.post(r2)
            com.mobiroller.models.ScreenModel r1 = new com.mobiroller.models.ScreenModel
            r1.<init>()
            return r1
        L_0x024b:
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "com.mobiroller.activities."
            r8.append(r9)
            java.lang.String r9 = r18.getScreenType()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            java.lang.String r9 = "class"
            r5.putString(r9, r8)
            java.lang.String r8 = r18.getScreenType()
            java.lang.String r9 = "screenType"
            r5.putString(r9, r8)
            java.lang.String r8 = r1.screenSubtype
            java.lang.String r10 = "screenSubType"
            r5.putString(r10, r8)
            java.lang.String r8 = r18.getAccountScreenID()
            java.lang.String r11 = "screenId"
            r5.putString(r11, r8)
            java.util.ArrayList r8 = r18.getRoles()
            java.lang.String r12 = "roles"
            r5.putSerializable(r12, r8)
            if (r2 == 0) goto L_0x0372
            java.lang.String r8 = r18.getAccountScreenID()
            com.mobiroller.helpers.JSONStorage.putScreenModel(r8, r2)
            java.lang.String r8 = r18.getScreenType()
            java.lang.String r12 = "aveCallNowView"
            boolean r8 = r8.equals(r12)
            if (r8 == 0) goto L_0x02d3
            java.lang.String r1 = r19.getPhoneNumber()
            if (r1 == 0) goto L_0x036b
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r4 = "android.intent.action.DIAL"
            r1.<init>(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "tel:"
            r4.append(r5)
            java.lang.String r2 = r19.getPhoneNumber()
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            android.net.Uri r2 = android.net.Uri.parse(r2)
            r1.setData(r2)
            r3.startActivity(r1)
            com.mobiroller.models.ScreenModel r1 = new com.mobiroller.models.ScreenModel
            r1.<init>()
            return r1
        L_0x02d3:
            java.lang.String r8 = r18.getScreenType()
            java.lang.String r12 = "aveShareView"
            boolean r8 = r8.equals(r12)
            if (r8 == 0) goto L_0x0302
            android.content.Intent r2 = new android.content.Intent
            java.lang.Class<com.mobiroller.activities.aveShareView> r4 = com.mobiroller.activities.aveShareView.class
            r2.<init>(r3, r4)
            java.lang.String r4 = r18.getScreenType()
            r2.putExtra(r9, r4)
            java.lang.String r4 = r1.screenSubtype
            r2.putExtra(r10, r4)
            java.lang.String r1 = r18.getAccountScreenID()
            r2.putExtra(r11, r1)
            r3.startActivity(r2)
            com.mobiroller.models.ScreenModel r1 = new com.mobiroller.models.ScreenModel
            r1.<init>()
            return r1
        L_0x0302:
            java.lang.String r3 = r18.getScreenType()
            java.lang.String r8 = "aveHtmlView"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x031e
            java.lang.String r3 = r19.getScreenType()
            boolean r3 = r3.equalsIgnoreCase(r15)
            if (r3 == 0) goto L_0x031e
            com.mobiroller.fragments.aveFAQViewFragment r4 = new com.mobiroller.fragments.aveFAQViewFragment
            r4.<init>()
            goto L_0x036b
        L_0x031e:
            java.lang.String r3 = r18.getScreenType()
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0338
            java.lang.String r3 = r19.getScreenType()
            boolean r3 = r3.equalsIgnoreCase(r6)
            if (r3 == 0) goto L_0x0338
            com.mobiroller.fragments.aveEmergencyCallViewFragment r4 = new com.mobiroller.fragments.aveEmergencyCallViewFragment
            r4.<init>()
            goto L_0x036b
        L_0x0338:
            java.lang.String r3 = r18.getScreenType()
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0352
            java.lang.String r3 = r19.getScreenType()
            boolean r3 = r3.equalsIgnoreCase(r7)
            if (r3 == 0) goto L_0x0352
            com.mobiroller.fragments.aveCatalogViewFragment r4 = new com.mobiroller.fragments.aveCatalogViewFragment
            r4.<init>()
            goto L_0x036b
        L_0x0352:
            java.lang.String r1 = r18.getScreenType()
            boolean r1 = r1.equals(r8)
            if (r1 == 0) goto L_0x036b
            java.lang.String r1 = r19.getScreenType()
            boolean r1 = r1.equalsIgnoreCase(r14)
            if (r1 == 0) goto L_0x036b
            com.mobiroller.fragments.aveAboutUsViewFragment r4 = new com.mobiroller.fragments.aveAboutUsViewFragment
            r4.<init>()
        L_0x036b:
            r4.setArguments(r5)
            r2.setFragment(r4)
            return r2
        L_0x0372:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.MenuHelper.getFragment(com.mobiroller.models.NavigationItemModel, com.mobiroller.models.ScreenModel, android.app.Activity):com.mobiroller.models.ScreenModel");
    }

    /* access modifiers changed from: private */
    public void checkPushAndIntro(boolean z, boolean z2) {
        if (z) {
            String stringExtra = this.context.getIntent().getStringExtra("title");
            if (stringExtra == null) {
                stringExtra = this.context.getString(R.string.app_name);
            }
            String stringExtra2 = this.context.getIntent().getStringExtra(Constants.NOTIFICATION_CONTENT);
            Action action = (Action) this.context.getIntent().getSerializableExtra("action");
            if (action != null) {
                if (action.getType().equals(Constants.NOTIFICATION_TYPE_CONTENT) || action.getType().equals(Constants.NOTIFICATION_TYPE_WEB)) {
                    Activity activity = this.context;
                    activity.startActivity(NotificationActionUtil.getActionIntent(activity, action, stringExtra, stringExtra2));
                } else if (action.getType().equals(SchedulerSupport.NONE) && stringExtra2 != null) {
                    new Builder(this.context).title((CharSequence) stringExtra).content((CharSequence) stringExtra2).positiveText((CharSequence) this.context.getString(R.string.OK)).icon(ContextCompat.getDrawable(this.context, R.drawable.icon)).show();
                }
            }
        } else if (z2) {
            try {
                this.introObject = this.sharedPrefHelper.getIntroMessage();
                if (this.introObject != null) {
                    if (this.introObject.getIntroMessageScreenID().equals("null")) {
                        if (this.introObject.getIntroMessageScreenID() == null) {
                            if (this.localizationHelper.getLocalizedTitle(this.introObject.getIntroMessage()) != null) {
                                new AlertDialog.Builder(this.context).setTitle(R.string.app_name).setMessage(this.localizationHelper.getLocalizedTitle(this.introObject.getIntroMessage())).setPositiveButton(17039370, new OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).setIcon(R.drawable.icon).show();
                            }
                        }
                    }
                    if (this.localizationHelper.getLocalizedTitle(this.introObject.getIntroMessage()) != null) {
                        showIntro(this.introObject);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.context.getIntent().hasExtra("chatIntentReport")) {
            Intent intent = new Intent(this.context, ChatAdminActivity.class);
            intent.putExtra("panel", ChatConstants.ADMIN_PANEL);
            intent.putExtra("to", "report");
            this.context.startActivity(intent);
        }
    }

    public void showErrorMessage(String str) {
        DialogUtil.getDialogWithCallBack(this.context, str, new DialogCallBack() {
            public void onClickPositive() {
                MenuHelper.this.context.finish();
            }
        }).cancelable(false).show();
    }

    public void sendToken() {
        if (this.networkHelper.isConnected() && !DynamicConstants.MobiRoller_Stage) {
            try {
                this.RegId = this.sharedPrefHelper.getString(Constants.MobiRoller_Preferences_FCM_Token, "");
                if (this.RegId.length() == 0) {
                    registerBackground();
                } else {
                    ServerUtilities.register(this.context, this.context.getResources().getString(R.string.mobiroller_username), this.sharedPrefHelper.getDeviceId(), LocaleHelper.getLocale().toUpperCase(), this.RegId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerBackground() {
        if (this.networkHelper.isConnected()) {
            FirebaseInstanceId.getInstance(FirebaseApp.getInstance("main")).getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                public void onComplete(Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Log.w(FirebaseMessaging.INSTANCE_ID_SCOPE, "getInstanceId failed", task.getException());
                        return;
                    }
                    MenuHelper.this.sharedPrefHelper.setFCMToken(((InstanceIdResult) task.getResult()).getToken());
                    ServerUtilities.register(MenuHelper.this.context, MenuHelper.this.context.getString(R.string.mobiroller_username), MenuHelper.this.sharedPrefHelper.getDeviceId(), LocaleHelper.getLocale().toUpperCase(), MenuHelper.this.RegId);
                }
            });
        }
    }

    public void showLanguageDialogAndCheckPushAndIntro() {
        int i;
        this.language = LocaleHelper.getLocale().toUpperCase();
        this.languageCodes = this.sharedPrefHelper.getLocaleCodes();
        String str = this.languageCodes;
        String str2 = ",";
        if (str == null) {
            this.localeCodes = new String[0];
        } else {
            this.localeCodes = str.split(str2);
        }
        List asList = Arrays.asList(new String[]{""});
        if (this.sharedPrefHelper.getLocaleCodes() != null) {
            asList = Arrays.asList(this.languageCodes.split(str2));
        }
        if (asList.contains(LocaleHelper.getLocale().toUpperCase())) {
            i = Arrays.asList(this.localeCodes).indexOf(this.language);
        } else {
            i = Arrays.asList(this.localeCodes).indexOf(this.sharedPrefHelper.getDefaultLang().toUpperCase());
        }
        String str3 = "isLocal";
        boolean booleanExtra = this.context.getIntent().hasExtra(str3) ? this.context.getIntent().getBooleanExtra(str3, false) : false;
        if (!this.sharedPrefHelper.getSelectLangOnStart() || this.localeCodes.length <= 1 || !this.sharedPrefHelper.getFirstTimeForLanguage() || DynamicConstants.MobiRoller_Stage || booleanExtra) {
            checkPushAndIntro(this.context.getIntent().getBooleanExtra("pushNotified", false), this.context.getIntent().getBooleanExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, false));
            return;
        }
        this.languageArray = this.context.getResources().getStringArray(R.array.supported_languages);
        this.languageCodeArray = this.context.getResources().getStringArray(R.array.supported_languages_language_codes);
        this.sharedPrefHelper.setFirstTimeForLanguage();
        new Builder(this.context).title((int) R.string.change_language).items((CharSequence[]) getLanguageList()).cancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                MenuHelper menuHelper = MenuHelper.this;
                menuHelper.checkPushAndIntro(menuHelper.context.getIntent().getBooleanExtra("pushNotified", false), MenuHelper.this.context.getIntent().getBooleanExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, false));
            }
        }).itemsCallbackSingleChoice(i, new ListCallbackSingleChoice() {
            public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                if (MenuHelper.this.language.equalsIgnoreCase(MenuHelper.this.localeCodes[i])) {
                    MenuHelper menuHelper = MenuHelper.this;
                    menuHelper.checkPushAndIntro(menuHelper.context.getIntent().getBooleanExtra("pushNotified", false), MenuHelper.this.context.getIntent().getBooleanExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, false));
                } else if (!DynamicConstants.MobiRoller_Stage) {
                    List asList = Arrays.asList(MenuHelper.this.context.getResources().getStringArray(R.array.supported_languages_language_codes));
                    Arrays.asList(MenuHelper.this.context.getResources().getStringArray(R.array.supported_languages_country_codes));
                    Locale locale = new Locale(MenuHelper.this.localeCodes[i], (String) asList.get(asList.indexOf(MenuHelper.this.localeCodes[i].toLowerCase())));
                    Locale.setDefault(locale);
                    Configuration configuration = MenuHelper.this.context.getResources().getConfiguration();
                    configuration.locale = locale;
                    MenuHelper.this.context.getBaseContext().getResources().updateConfiguration(configuration, MenuHelper.this.context.getBaseContext().getResources().getDisplayMetrics());
                    UtilManager.sharedPrefHelper().put(Constants.DISPLAY_LANGUAGE, Locale.getDefault().getLanguage().toLowerCase());
                    MenuHelper.this.sharedPrefHelper.languageSetByUser();
                    Intent launchIntentForPackage = MenuHelper.this.context.getBaseContext().getPackageManager().getLaunchIntentForPackage(MenuHelper.this.context.getBaseContext().getPackageName());
                    launchIntentForPackage.addFlags(67108864);
                    MenuHelper.this.context.finish();
                    MenuHelper.this.context.startActivity(launchIntentForPackage);
                } else {
                    Toast.makeText(MenuHelper.this.context, MenuHelper.this.context.getString(R.string.not_supported_on_preview), 0).show();
                }
                return true;
            }
        }).negativeText((int) R.string.cancel).positiveText((int) R.string.OK).show();
    }

    private String[] getLanguageList() {
        String[] strArr = new String[this.localeCodes.length];
        for (int i = 0; i < this.localeCodes.length; i++) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.languageCodeArray;
                if (i2 >= strArr2.length) {
                    break;
                } else if (strArr2[i2].equalsIgnoreCase(this.localeCodes[i])) {
                    strArr[i] = this.languageArray[i2];
                    break;
                } else {
                    i2++;
                }
            }
        }
        return strArr;
    }

    public int getTopSpace(int i, int i2) {
        ScreenHelper screenHelper2 = this.screenHelper;
        int deviceHeight = ScreenHelper.getDeviceHeight(this.context);
        int i3 = i2 * i;
        int i4 = i + 1;
        ScreenHelper screenHelper3 = this.screenHelper;
        return Math.round((float) (deviceHeight - (i3 + (i4 * ScreenHelper.getHeightForDevice(10, this.context)))));
    }

    public void onBackPressStatus() {
        if (this.sharedPrefHelper.getAskBeforeExit()) {
            new Builder(this.context).title((int) R.string.app_name).content((int) R.string.action_close_app).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    MenuHelper.this.context.finish();
                }
            }).show();
        } else {
            this.context.finish();
        }
    }

    private static boolean isSupportedForPreview(String str) {
        for (String equalsIgnoreCase : MobiRollerApplication.app.getResources().getStringArray(R.array.preview_not_supported_modules)) {
            if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                return false;
            }
        }
        return true;
    }
}
