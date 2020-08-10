package com.paypal.android.sdk.onetouch.core.config;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.braintreepayments.api.internal.AppHelper;
import com.braintreepayments.browserswitch.ChromeCustomTabs;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.enums.Protocol;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;
import com.paypal.android.sdk.onetouch.core.sdk.AppSwitchHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import org.slf4j.Marker;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public abstract class Recipe<T extends Recipe<T>> {
    private Protocol mProtocol;
    private Collection<String> mSupportedLocales = new HashSet();
    private RequestTarget mTarget;
    private String mTargetIntentAction;
    private List<String> mTargetPackagesInReversePriorityOrder = new ArrayList();

    /* access modifiers changed from: protected */
    public abstract T getThis();

    public T target(RequestTarget requestTarget) {
        this.mTarget = requestTarget;
        return getThis();
    }

    public T protocol(String str) {
        this.mProtocol = Protocol.getProtocol(str);
        return getThis();
    }

    public T targetPackage(String str) {
        this.mTargetPackagesInReversePriorityOrder.add(str);
        return getThis();
    }

    public List<String> getTargetPackagesInReversePriorityOrder() {
        return new ArrayList(this.mTargetPackagesInReversePriorityOrder);
    }

    public T supportedLocale(String str) {
        this.mSupportedLocales.add(str);
        return getThis();
    }

    public T targetIntentAction(String str) {
        this.mTargetIntentAction = str;
        return getThis();
    }

    public String getTargetIntentAction() {
        return this.mTargetIntentAction;
    }

    public RequestTarget getTarget() {
        return this.mTarget;
    }

    public boolean isValidAppTarget(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        if (!packageName.equals(packageName.toLowerCase(Locale.ROOT).replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, ""))) {
            return false;
        }
        for (String str : getTargetPackagesInReversePriorityOrder()) {
            boolean isIntentAvailable = AppHelper.isIntentAvailable(context, AppSwitchHelper.createBaseIntent(getTargetIntentAction(), str));
            boolean z = this.mSupportedLocales.isEmpty() || this.mSupportedLocales.contains(Locale.getDefault().toString());
            boolean isSignatureValid = AppSwitchHelper.isSignatureValid(context, str);
            if (isIntentAvailable && z && isSignatureValid) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidBrowserTarget(Context context, String str) {
        for (String isValidBrowserTarget : getTargetPackagesInReversePriorityOrder()) {
            if (isValidBrowserTarget(context, str, isValidBrowserTarget)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidBrowserTarget(Context context, String str, String str2) {
        return getBrowserIntent(context, str, str2).resolveActivity(context.getPackageManager()) != null;
    }

    public static Intent getBrowserIntent(Context context, String str, String str2) {
        Intent addFlags = new Intent("android.intent.action.VIEW", Uri.parse(str)).addFlags(268435456);
        if (!Marker.ANY_MARKER.equals(str2)) {
            addFlags.setPackage(str2);
        }
        return ChromeCustomTabs.addChromeCustomTabsExtras(context, addFlags);
    }

    public Protocol getProtocol() {
        return this.mProtocol;
    }
}
