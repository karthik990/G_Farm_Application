package com.android.billingclient.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.billingclient.util.BillingHelper;

class BillingBroadcastManager {
    private static final String ACTION = "com.android.vending.billing.PURCHASES_UPDATED";
    private static final String TAG = "BillingBroadcastManager";
    private final Context mContext;
    /* access modifiers changed from: private */
    public final BillingBroadcastReceiver mReceiver;

    private class BillingBroadcastReceiver extends BroadcastReceiver {
        private boolean mIsRegistered;
        /* access modifiers changed from: private */
        public final PurchasesUpdatedListener mListener;

        private BillingBroadcastReceiver(PurchasesUpdatedListener purchasesUpdatedListener) {
            this.mListener = purchasesUpdatedListener;
        }

        public void register(Context context, IntentFilter intentFilter) {
            if (!this.mIsRegistered) {
                context.registerReceiver(BillingBroadcastManager.this.mReceiver, intentFilter);
                this.mIsRegistered = true;
            }
        }

        public void unRegister(Context context) {
            if (this.mIsRegistered) {
                context.unregisterReceiver(BillingBroadcastManager.this.mReceiver);
                this.mIsRegistered = false;
                return;
            }
            BillingHelper.logWarn(BillingBroadcastManager.TAG, "Receiver is not registered.");
        }

        public void onReceive(Context context, Intent intent) {
            this.mListener.onPurchasesUpdated(BillingHelper.getBillingResultFromIntent(intent, BillingBroadcastManager.TAG), BillingHelper.extractPurchases(intent.getExtras()));
        }
    }

    BillingBroadcastManager(Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        this.mContext = context;
        this.mReceiver = new BillingBroadcastReceiver(purchasesUpdatedListener);
    }

    /* access modifiers changed from: 0000 */
    public void registerReceiver() {
        this.mReceiver.register(this.mContext, new IntentFilter(ACTION));
    }

    /* access modifiers changed from: 0000 */
    public PurchasesUpdatedListener getListener() {
        return this.mReceiver.mListener;
    }

    /* access modifiers changed from: 0000 */
    public void destroy() {
        this.mReceiver.unRegister(this.mContext);
    }
}
