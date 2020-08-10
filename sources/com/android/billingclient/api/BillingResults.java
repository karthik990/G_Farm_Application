package com.android.billingclient.api;

final class BillingResults {
    static final BillingResult API_VERSION_NOT_V3 = BillingResult.newBuilder().setResponseCode(3).setDebugMessage("Google Play In-app Billing API version is less than 3").build();
    static final BillingResult API_VERSION_NOT_V9 = BillingResult.newBuilder().setResponseCode(3).setDebugMessage("Google Play In-app Billing API version is less than 9").build();
    static final BillingResult BILLING_UNAVAILABLE = BillingResult.newBuilder().setResponseCode(3).setDebugMessage("Billing service unavailable on device.").build();
    static final BillingResult CLIENT_CONNECTING = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("Client is already in the process of connecting to billing service.").build();
    static final BillingResult EMPTY_SKU_LIST = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("The list of SKUs can't be empty.").build();
    static final BillingResult EMPTY_SKU_TYPE = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("SKU type can't be empty.").build();
    static final BillingResult EXTRA_PARAMS_NOT_SUPPORTED = BillingResult.newBuilder().setResponseCode(-2).setDebugMessage("Client does not support extra params.").build();
    static final BillingResult FEATURE_NOT_SUPPORTED = BillingResult.newBuilder().setResponseCode(-2).setDebugMessage("Client does not support the feature.").build();
    static final BillingResult GET_PURCHASE_HISTORY_NOT_SUPPORTED = BillingResult.newBuilder().setResponseCode(-2).setDebugMessage("Client does not support get purchase history.").build();
    static final BillingResult INTERNAL_ERROR = BillingResult.newBuilder().setResponseCode(6).setDebugMessage("An internal error occurred.").build();
    static final BillingResult INVALID_PURCHASE_TOKEN = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("Invalid purchase token.").build();
    static final BillingResult ITEM_UNAVAILABLE = BillingResult.newBuilder().setResponseCode(4).setDebugMessage("Item is unavailable for purchase.").build();
    static final BillingResult NULL_SKU = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("SKU can't be null.").build();
    static final BillingResult NULL_SKU_TYPE = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("SKU type can't be null.").build();

    /* renamed from: OK */
    static final BillingResult f83OK = BillingResult.newBuilder().setResponseCode(0).build();
    static final BillingResult SERVICE_DISCONNECTED = BillingResult.newBuilder().setResponseCode(-1).setDebugMessage("Service connection is disconnected.").build();
    static final BillingResult SERVICE_TIMEOUT = BillingResult.newBuilder().setResponseCode(-3).setDebugMessage("Timeout communicating with service.").build();
    static final BillingResult SUBSCRIPTIONS_NOT_SUPPORTED = BillingResult.newBuilder().setResponseCode(-2).setDebugMessage("Client doesn't support subscriptions.").build();
    static final BillingResult SUBSCRIPTIONS_UPDATE_NOT_SUPPORTED = BillingResult.newBuilder().setResponseCode(-2).setDebugMessage("Client doesn't support subscriptions update.").build();
    static final BillingResult UNKNOWN_FEATURE = BillingResult.newBuilder().setResponseCode(5).setDebugMessage("Unknown feature").build();

    BillingResults() {
    }
}
