package com.mobiroller.activities.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OneTimeWorkRequest.Builder;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.models.ecommerce.ECommerceResponse;
import com.mobiroller.workers.PayPalRequestWorker;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayPalActivity extends AppCompatActivity implements PaymentMethodNonceCreatedListener, BraintreeCancelListener, BraintreeErrorListener {
    public static final String PAYPAL_AMOUNT_KEY = "PayPalAmountIntentExtra";
    public static final String PAYPAL_AUTH_TOKEN_KEY = "PayPalAuthTokenIntentExtra";
    public static final String PAYPAL_CURRENCY_CODE_KEY = "PayPalCurrencyCodeIntentExtra";
    public static final String PAYPAL_DISPLAY_NAME_KEY = "PayPalDisplayNameIntentExtra";
    public static final String PAYPAL_NONCE_KEY = "PayPalNonceIntentExtra";
    public static final String PAYPAL_ORDER_ID_KEY = "PayPalOrderIdIntentExtra";
    private double amount;
    private String currencyCode;
    private String displayName;
    private String errorCode = "0";
    private BraintreeFragment mBraintreeFragment;
    private String orderId;
    ProgressViewHelper progressViewHelper;
    private String token;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        Intent intent = getIntent();
        String str = PAYPAL_AUTH_TOKEN_KEY;
        if (intent.hasExtra(str)) {
            Intent intent2 = getIntent();
            String str2 = PAYPAL_ORDER_ID_KEY;
            if (intent2.hasExtra(str2)) {
                Intent intent3 = getIntent();
                String str3 = PAYPAL_CURRENCY_CODE_KEY;
                if (intent3.hasExtra(str3)) {
                    Intent intent4 = getIntent();
                    String str4 = PAYPAL_AMOUNT_KEY;
                    if (intent4.hasExtra(str4)) {
                        this.token = getIntent().getStringExtra(str);
                        this.orderId = getIntent().getStringExtra(str2);
                        this.currencyCode = getIntent().getStringExtra(str3);
                        this.displayName = getIntent().getStringExtra(PAYPAL_DISPLAY_NAME_KEY);
                        this.amount = getIntent().getDoubleExtra(str4, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
                        this.mBraintreeFragment = BraintreeFragment.newInstance(this, this.token);
                        PayPal.requestOneTimePayment(this.mBraintreeFragment, new PayPalRequest(String.valueOf(this.amount)).displayName(this.displayName).currencyCode(this.currencyCode).intent(PayPalRequest.INTENT_AUTHORIZE));
                    }
                }
            }
        }
        failed();
        try {
            this.mBraintreeFragment = BraintreeFragment.newInstance(this, this.token);
            PayPal.requestOneTimePayment(this.mBraintreeFragment, new PayPalRequest(String.valueOf(this.amount)).displayName(this.displayName).currencyCode(this.currencyCode).intent(PayPalRequest.INTENT_AUTHORIZE));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            failed();
        }
    }

    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        queueSuccessRequest(paymentMethodNonce.getNonce());
        Intent intent = new Intent();
        intent.putExtra(ECommerceConstant.PAY_PAL_REQUEST_SUCCESS, true);
        setResult(-1, intent);
        finish();
    }

    public void onCancel(int i) {
        failed();
    }

    public void onError(Exception exc) {
        String str = "paymentResource";
        if (exc instanceof ErrorWithResponse) {
            try {
                JSONObject jSONObject = new JSONObject(((ErrorWithResponse) exc).getErrorResponse());
                if (!(jSONObject.getJSONObject(str) == null || jSONObject.getJSONObject(str).getString("errorName") == null)) {
                    this.errorCode = "P100";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        exc.printStackTrace();
        failed();
    }

    private void queueSuccessRequest(String str) {
        WorkManager.getInstance(MobiRollerApplication.app).enqueue((WorkRequest) (OneTimeWorkRequest) ((Builder) ((Builder) ((Builder) new Builder(PayPalRequestWorker.class).setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())).setInputData(new Data.Builder().putString(PAYPAL_ORDER_ID_KEY, this.orderId).putString(PAYPAL_NONCE_KEY, str).build())).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)).build());
    }

    private void failed() {
        if (this.orderId == null) {
            this.orderId = "";
        }
        new ECommerceRequestHelper().getAPIService().failurePayment(this.orderId).enqueue(new Callback<ECommerceResponse>() {
            public void onFailure(Call<ECommerceResponse> call, Throwable th) {
            }

            public void onResponse(Call<ECommerceResponse> call, Response<ECommerceResponse> response) {
            }
        });
        Intent intent = new Intent();
        intent.putExtra(ECommerceConstant.PAY_PAL_REQUEST_SUCCESS, false);
        intent.putExtra(ECommerceConstant.PAY_PAL_REQUEST_FAILED_STATUS_CODE, this.errorCode);
        setResult(-1, intent);
        finish();
    }
}
