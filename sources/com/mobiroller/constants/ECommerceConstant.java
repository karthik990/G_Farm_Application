package com.mobiroller.constants;

import android.graphics.Color;
import com.mobiroller.mobi942763453128.R;
import java.util.HashMap;
import java.util.Map;

public class ECommerceConstant {
    public static final String AGREEMENT_URL = "agreementUrl";
    public static final String APPROVED = "Approved";
    public static final String CANCELED = "Canceled";
    public static final String CANCEL_REQUESTED = "CancelRequested";
    public static final String DELIVERED = "Delivered";
    public static final Map<String, Integer> INVALID_CART_ITEM_STATUS = new HashMap<String, Integer>() {
        {
            put(ECommerceConstant.SHOPPING_CART_NOT_ENOUGH_STOCK, Integer.valueOf(R.string.e_commerce_shopping_cart_invalid_product_left));
            put(ECommerceConstant.SHOPPING_CART_OUT_OF_STOCK, Integer.valueOf(R.string.e_commerce_shopping_cart_invalid_out_of_stock));
            put(ECommerceConstant.SHOPPING_CART_OVER_PUBLISHMENT_DATE, Integer.valueOf(R.string.e_commerce_shopping_cart_invalid_publishment_end));
            put(ECommerceConstant.SHOPPING_CART_PRODUCT_NOT_FOUND, Integer.valueOf(R.string.e_commerce_shopping_cart_invalid_removed_basket));
        }
    };
    public static final String IS_PAYMENT_SUCCESS = "isPaymentSuccess";
    public static final String MAKE_ORDER_MODEL = "makeOrderModel";
    public static final String ONLINE = "Online";
    public static final String ONLINE3DS = "Online3DS";
    public static final String ONLINE_PAYMENT_3D_HTML = "onlinePayment3dHtml";
    public static final int ONLINE_PAYMENT_3D_HTML_REQUEST_CODE = 1003;
    public static final String ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED = "paymentFailed";
    public static final String ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED_STATUS_CODE = "paymentFailedStatusCode";
    public static final String ONLINE_PAYMENT_3D_HTML_REQUEST_SUCCESS = "paymentSuccess";
    public static final String ORDER_DETAIL_MODEL = "orderDetailModel";
    public static final String ORDER_FAILED_RESPONSE_MODEL = "orderFailedResponseModel";
    public static final String ORDER_FAILED_STATUS_CODE = "orderFailedStatusCode";
    public static final String ORDER_ID = "orderId";
    public static final String ORDER_INNER_RESPONSE_MODEL = "orderInnerResponseModel";
    public static final String ORDER_RESPONSE_MODEL = "orderResponseModel";
    public static final Map<String, Integer> ORDER_STATUS = new HashMap<String, Integer>() {
        {
            put(ECommerceConstant.WAITING_PAYMENT, Integer.valueOf(R.string.e_commerce_order_status_waiting_payment));
            put(ECommerceConstant.WAITING_APPROVAL, Integer.valueOf(R.string.e_commerce_order_status_waiting_approval));
            put(ECommerceConstant.APPROVED, Integer.valueOf(R.string.e_commerce_order_status_approved));
            put(ECommerceConstant.WAITING_SUPPLYING, Integer.valueOf(R.string.e_commerce_order_status_waiting_supplying));
            put(ECommerceConstant.PREPARING, Integer.valueOf(R.string.e_commerce_order_status_preparing));
            put(ECommerceConstant.SHIPPED, Integer.valueOf(R.string.e_commerce_order_status_shipped));
            put(ECommerceConstant.DELIVERED, Integer.valueOf(R.string.e_commerce_order_status_delivered));
            put(ECommerceConstant.CANCEL_REQUESTED, Integer.valueOf(R.string.e_commerce_order_status_cancel_requested));
            put(ECommerceConstant.CANCELED, Integer.valueOf(R.string.e_commerce_order_status_canceled));
            put(ECommerceConstant.REFUNDED, Integer.valueOf(R.string.e_commerce_order_status_refunded));
            put(ECommerceConstant.PAYMENT_FAILED, Integer.valueOf(R.string.e_commerce_order_status_payment_failed));
        }
    };
    public static final Map<String, Integer> ORDER_STATUS_ICONS = new HashMap<String, Integer>() {
        {
            put(ECommerceConstant.WAITING_PAYMENT, Integer.valueOf(R.drawable.ic_e_commerce_order_waiting_payment_icon));
            put(ECommerceConstant.WAITING_APPROVAL, Integer.valueOf(R.drawable.ic_e_commerce_order_waiting_approval_icon));
            put(ECommerceConstant.APPROVED, Integer.valueOf(R.drawable.ic_e_commerce_order_approved_icon));
            put(ECommerceConstant.WAITING_SUPPLYING, Integer.valueOf(R.drawable.ic_e_commerce_order_waiting_supplying_icon));
            put(ECommerceConstant.PREPARING, Integer.valueOf(R.drawable.ic_e_ecommerce_order_preparing_icon));
            put(ECommerceConstant.SHIPPED, Integer.valueOf(R.drawable.ic_e_commerce_order_shipped_icon));
            put(ECommerceConstant.DELIVERED, Integer.valueOf(R.drawable.ic_e_commerce_order_delivered_icon));
            Integer valueOf = Integer.valueOf(R.drawable.ic_e_commerce_order_payment_failed_icon);
            put(ECommerceConstant.CANCEL_REQUESTED, valueOf);
            put(ECommerceConstant.CANCELED, Integer.valueOf(R.drawable.ic_e_commerce_order_cancelled_icon));
            put(ECommerceConstant.REFUNDED, Integer.valueOf(R.drawable.ic_e_commerce_order_refunded_icon));
            put(ECommerceConstant.PAYMENT_FAILED, valueOf);
        }
    };
    public static final Map<String, Integer> ORDER_STATUS_RED = new HashMap<String, Integer>() {
        {
            String str = "#F8E7D8";
            put(ECommerceConstant.WAITING_APPROVAL, Integer.valueOf(Color.parseColor(str)));
            put(ECommerceConstant.WAITING_PAYMENT, Integer.valueOf(Color.parseColor(str)));
            put(ECommerceConstant.WAITING_SUPPLYING, Integer.valueOf(Color.parseColor(str)));
            put(ECommerceConstant.PREPARING, Integer.valueOf(Color.parseColor(str)));
            put(ECommerceConstant.SHIPPED, Integer.valueOf(Color.parseColor(str)));
            String str2 = "#D8F5E5";
            put(ECommerceConstant.APPROVED, Integer.valueOf(Color.parseColor(str2)));
            put(ECommerceConstant.DELIVERED, Integer.valueOf(Color.parseColor(str2)));
            String str3 = "#FEE6E3";
            put(ECommerceConstant.REFUNDED, Integer.valueOf(Color.parseColor(str3)));
            put(ECommerceConstant.CANCEL_REQUESTED, Integer.valueOf(Color.parseColor(str3)));
            put(ECommerceConstant.CANCELED, Integer.valueOf(Color.parseColor(str3)));
            put(ECommerceConstant.PAYMENT_FAILED, Integer.valueOf(Color.parseColor(str3)));
        }
    };
    public static final String PAYMENT_FAILED = "PaymentFailed";
    public static final String PAYMENT_SETTINGS_MODEL = "paymentSettingsModel";
    public static final Map<String, Integer> PAYMENT_STATUS_CODES = new HashMap<String, Integer>() {
        {
            put("P100", Integer.valueOf(R.string.e_commerce_result_pay_pal_currency_not_supported));
            put("800", Integer.valueOf(R.string.e_commerce_result_credit_card_not_enough_balance));
            put("801", Integer.valueOf(R.string.e_commerce_result_credit_card_process_did_not_approved));
            put("900", Integer.valueOf(R.string.e_commerce_result_credit_card_invalid_payment_info));
            put("901", Integer.valueOf(R.string.e_commerce_result_credit_card_lost_or_stolen));
            put("902", Integer.valueOf(R.string.e_commerce_result_credit_card_expired_card));
            put("903", Integer.valueOf(R.string.e_commerce_result_credit_card_invalid_cvc));
            put("904", Integer.valueOf(R.string.e_commerce_result_credit_card_requires_three_d));
            put("905", Integer.valueOf(R.string.e_commerce_result_credit_card_installment_not_allowed));
            put("906", Integer.valueOf(R.string.e_commerce_result_credit_card_too_many_invalid));
            put("907", Integer.valueOf(R.string.e_commerce_result_credit_card_not_enough_limit));
            put("1100", Integer.valueOf(R.string.e_commerce_result_credit_card_invalid_enum));
            put("1101", Integer.valueOf(R.string.e_commerce_result_credit_card_already_done_before));
        }
    };
    public static final Map<String, Integer> PAYMENT_TYPES = new HashMap<String, Integer>() {
        {
            put(ECommerceConstant.PAY_AT_DOOR, Integer.valueOf(R.string.e_commerce_payment_method_selection_pay_at_door));
            Integer valueOf = Integer.valueOf(R.string.e_commerce_payment_method_selection_credit_card);
            put(ECommerceConstant.ONLINE, valueOf);
            put(ECommerceConstant.ONLINE3DS, valueOf);
            put(ECommerceConstant.TRANSFER, Integer.valueOf(R.string.e_commerce_payment_method_selection_transfer));
        }
    };
    public static final String PAYPAL = "PayPal";
    public static final int PAYPAL_REQUEST_CODE = 1004;
    public static final String PAY_AT_DOOR = "PayAtDoor";
    public static final String PAY_PAL_REQUEST_FAILED_STATUS_CODE = "payPalPaymentFailedStatusCode";
    public static final String PAY_PAL_REQUEST_SUCCESS = "payPalPaymentSuccess";
    public static final String PREPARING = "Preparing";
    public static final String PRODUCT_DETAIL_MODEL = "productDetailModel";
    public static final String PRODUCT_FEATURED_IMAGE = "productFeaturedImage";
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_IMAGE_LIST = "productImageList";
    public static final String PRODUCT_IMAGE_LIST_POSITION = "productImageListPosition";
    public static final String PRODUCT_LIST_MODEL = "productListModel";
    public static final String PRODUCT_MAX_QUANTITY_PER_ORDER_EXCEEDED = "MaxQuantityPerOrderExceeded";
    public static final String PRODUCT_MAX_STOCK_EXCEEDED = "MaxStockExceeded";
    public static final String PRODUCT_TITLE = "productTitle";
    public static final String REFUNDED = "Refunded";
    public static final String SEARCHED_KEYWORD = "searchedKeyword";
    public static final String SEARCH_SUGGESTIONS = "searchSuggestions";
    public static final String SHIPPED = "Shipped";
    public static final String SHOPPING_CART = "shoppingCart";
    public static final String SHOPPING_CART_NOT_ENOUGH_STOCK = "NotEnoughStock";
    public static final String SHOPPING_CART_OUT_OF_STOCK = "OutOfStock";
    public static final String SHOPPING_CART_OVER_PUBLISHMENT_DATE = "OverPublishmentDate";
    public static final String SHOPPING_CART_PRODUCT_NOT_FOUND = "ProductNotFound";
    public static final String TRANSFER = "Transfer";
    public static final String WAITING_APPROVAL = "WaitingApproval";
    public static final String WAITING_PAYMENT = "WaitingPayment";
    public static final String WAITING_SUPPLYING = "WaitingForSupplying";
}
