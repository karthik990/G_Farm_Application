package com.android.billingclient.api;

import java.util.Collections;
import java.util.List;

class BillingClientNativeCallback implements AcknowledgePurchaseResponseListener, BillingClientStateListener, ConsumeResponseListener, PriceChangeConfirmationListener, PurchaseHistoryResponseListener, PurchasesUpdatedListener, RewardResponseListener, SkuDetailsResponseListener {
    private final long futureHandle;

    public static native void nativeOnAcknowledgePurchaseResponse(int i, String str, long j);

    public static native void nativeOnBillingServiceDisconnected();

    public static native void nativeOnBillingSetupFinished(int i, String str, long j);

    public static native void nativeOnConsumePurchaseResponse(int i, String str, String str2, long j);

    public static native void nativeOnPriceChangeConfirmationResult(int i, String str, long j);

    public static native void nativeOnPurchaseHistoryResponse(int i, String str, PurchaseHistoryRecord[] purchaseHistoryRecordArr, long j);

    public static native void nativeOnPurchasesUpdated(int i, String str, Purchase[] purchaseArr);

    public static native void nativeOnQueryPurchasesResponse(int i, String str, Purchase[] purchaseArr, long j);

    public static native void nativeOnRewardResponse(int i, String str, long j);

    public static native void nativeOnSkuDetailsResponse(int i, String str, SkuDetails[] skuDetailsArr, long j);

    BillingClientNativeCallback() {
        this.futureHandle = 0;
    }

    BillingClientNativeCallback(long j) {
        this.futureHandle = j;
    }

    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        nativeOnSkuDetailsResponse(billingResult.getResponseCode(), billingResult.getDebugMessage(), (SkuDetails[]) list.toArray(new SkuDetails[list.size()]), this.futureHandle);
    }

    public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
        nativeOnAcknowledgePurchaseResponse(billingResult.getResponseCode(), billingResult.getDebugMessage(), this.futureHandle);
    }

    public void onBillingSetupFinished(BillingResult billingResult) {
        nativeOnBillingSetupFinished(billingResult.getResponseCode(), billingResult.getDebugMessage(), this.futureHandle);
    }

    public void onBillingServiceDisconnected() {
        nativeOnBillingServiceDisconnected();
    }

    public void onConsumeResponse(BillingResult billingResult, String str) {
        nativeOnConsumePurchaseResponse(billingResult.getResponseCode(), billingResult.getDebugMessage(), str, this.futureHandle);
    }

    public void onPriceChangeConfirmationResult(BillingResult billingResult) {
        nativeOnPriceChangeConfirmationResult(billingResult.getResponseCode(), billingResult.getDebugMessage(), this.futureHandle);
    }

    public void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        nativeOnPurchaseHistoryResponse(billingResult.getResponseCode(), billingResult.getDebugMessage(), (PurchaseHistoryRecord[]) list.toArray(new PurchaseHistoryRecord[list.size()]), this.futureHandle);
    }

    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        nativeOnPurchasesUpdated(billingResult.getResponseCode(), billingResult.getDebugMessage(), (Purchase[]) list.toArray(new Purchase[list.size()]));
    }

    public void onRewardResponse(BillingResult billingResult) {
        nativeOnRewardResponse(billingResult.getResponseCode(), billingResult.getDebugMessage(), this.futureHandle);
    }

    /* access modifiers changed from: 0000 */
    public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        nativeOnQueryPurchasesResponse(billingResult.getResponseCode(), billingResult.getDebugMessage(), (Purchase[]) list.toArray(new Purchase[list.size()]), this.futureHandle);
    }
}
