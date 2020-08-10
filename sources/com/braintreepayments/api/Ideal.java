package com.braintreepayments.api;

import android.content.Context;
import androidx.work.WorkRequest;
import com.braintreepayments.api.exceptions.ConfigurationException;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.BraintreeSharedPreferences;
import com.braintreepayments.api.models.BraintreePaymentResult;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.IdealBank;
import com.braintreepayments.api.models.IdealRequest;
import com.braintreepayments.api.models.IdealResult;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class Ideal {
    private static final String ASSET_SERVER_REDIRECT_PATH = "/mobile/ideal-redirect/0.0.0/index.html?redirect_url=";
    protected static final String IDEAL_RESULT_ID = "com.braintreepayments.api.Ideal.IDEAL_RESULT_ID";
    protected static final int MAX_POLLING_DELAY = 10000;
    protected static final int MAX_POLLING_RETRIES = 10;
    protected static final int MIN_POLLING_DELAY = 1000;
    protected static final int MIN_POLLING_RETRIES = 0;

    private interface IdealStatusListener {
        void onFailure(Exception exc);

        void onSuccess(IdealResult idealResult);
    }

    @Deprecated
    public static void fetchIssuingBanks(final BraintreeFragment braintreeFragment, final BraintreeResponseListener<List<IdealBank>> braintreeResponseListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(final Configuration configuration) {
                ConfigurationException access$000 = Ideal.checkIdealEnabled(configuration);
                if (access$000 != null) {
                    braintreeFragment.postCallback((Exception) access$000);
                    return;
                }
                braintreeFragment.getBraintreeApiHttpClient().get("/issuers/ideal", new HttpResponseCallback() {
                    public void success(String str) {
                        braintreeFragment.sendAnalyticsEvent("ideal.load.succeeded");
                        try {
                            List fromJson = IdealBank.fromJson(configuration, str);
                            if (braintreeResponseListener != null) {
                                braintreeResponseListener.onResponse(fromJson);
                            }
                        } catch (JSONException e) {
                            failure(e);
                        }
                    }

                    public void failure(Exception exc) {
                        braintreeFragment.sendAnalyticsEvent("ideal.load.failed");
                        braintreeFragment.postCallback(exc);
                    }
                });
            }
        });
    }

    @Deprecated
    public static void startPayment(final BraintreeFragment braintreeFragment, final IdealRequest idealRequest, final BraintreeResponseListener<IdealResult> braintreeResponseListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                braintreeFragment.sendAnalyticsEvent("ideal.start-payment.selected");
                ConfigurationException access$000 = Ideal.checkIdealEnabled(configuration);
                if (access$000 != null) {
                    braintreeFragment.postCallback((Exception) access$000);
                    braintreeFragment.sendAnalyticsEvent("ideal.start-payment.invalid-configuration");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(configuration.getIdealConfiguration().getAssetsUrl());
                sb.append(Ideal.ASSET_SERVER_REDIRECT_PATH);
                sb.append(braintreeFragment.getReturnUrlScheme());
                sb.append("://");
                String str = "/ideal-payments";
                braintreeFragment.getBraintreeApiHttpClient().post(str, idealRequest.build(URI.create(sb.toString()).toString(), configuration.getIdealConfiguration().getRouteId()), new HttpResponseCallback() {
                    public void success(String str) {
                        try {
                            IdealResult fromJson = IdealResult.fromJson(str);
                            BraintreeSharedPreferences.putString(braintreeFragment.getApplicationContext(), Ideal.IDEAL_RESULT_ID, fromJson.getId());
                            if (braintreeResponseListener != null) {
                                braintreeResponseListener.onResponse(fromJson);
                            }
                            braintreeFragment.browserSwitch((int) BraintreeRequestCodes.IDEAL, new JSONObject(str).getJSONObject("data").getString("approval_url"));
                            braintreeFragment.sendAnalyticsEvent("ideal.webswitch.initiate.succeeded");
                        } catch (JSONException e) {
                            failure(e);
                        }
                    }

                    public void failure(Exception exc) {
                        braintreeFragment.sendAnalyticsEvent("ideal.webswitch.initiate.failed");
                        braintreeFragment.postCallback(exc);
                    }
                });
            }
        });
    }

    @Deprecated
    public static void pollForCompletion(BraintreeFragment braintreeFragment, String str, int i, long j) throws InvalidArgumentException {
        if (j < 1000 || j > WorkRequest.MIN_BACKOFF_MILLIS || i < 0 || i > 10) {
            throw new InvalidArgumentException("Failed to begin polling: retries must be between0 and 10, delay must be between1000 and 10000.\n");
        }
        pollForCompletion(braintreeFragment, str, i, j, 0);
    }

    /* access modifiers changed from: private */
    public static void pollForCompletion(BraintreeFragment braintreeFragment, String str, int i, long j, int i2) {
        final BraintreeFragment braintreeFragment2 = braintreeFragment;
        final int i3 = i2;
        final int i4 = i;
        final String str2 = str;
        final long j2 = j;
        C10573 r0 = new IdealStatusListener() {
            public void onSuccess(IdealResult idealResult) {
                String status = idealResult.getStatus();
                if ("COMPLETE".equals(status)) {
                    braintreeFragment2.postCallback((BraintreePaymentResult) idealResult);
                } else if (!"PENDING".equals(status) || i3 >= i4) {
                    braintreeFragment2.postCallback((BraintreePaymentResult) idealResult);
                } else {
                    Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                        public void run() {
                            Ideal.pollForCompletion(braintreeFragment2, str2, i4, j2, i3 + 1);
                        }
                    }, j2, TimeUnit.MILLISECONDS);
                }
            }

            public void onFailure(Exception exc) {
                braintreeFragment2.postCallback(exc);
            }
        };
        checkTransactionStatus(braintreeFragment, str, r0);
    }

    @Deprecated
    static void onActivityResult(final BraintreeFragment braintreeFragment, int i) {
        if (i == -1) {
            braintreeFragment.sendAnalyticsEvent("ideal.webswitch.succeeded");
            Context applicationContext = braintreeFragment.getApplicationContext();
            String str = IDEAL_RESULT_ID;
            String string = BraintreeSharedPreferences.getString(applicationContext, str);
            BraintreeSharedPreferences.remove(braintreeFragment.getApplicationContext(), str);
            checkTransactionStatus(braintreeFragment, string, new IdealStatusListener() {
                public void onSuccess(IdealResult idealResult) {
                    braintreeFragment.postCallback((BraintreePaymentResult) idealResult);
                }

                public void onFailure(Exception exc) {
                    braintreeFragment.postCallback(exc);
                }
            });
        } else if (i == 0) {
            braintreeFragment.sendAnalyticsEvent("ideal.webswitch.canceled");
        }
    }

    private static void checkTransactionStatus(BraintreeFragment braintreeFragment, String str, final IdealStatusListener idealStatusListener) {
        braintreeFragment.getBraintreeApiHttpClient().get(String.format("/ideal-payments/%s/status", new Object[]{str}), new HttpResponseCallback() {
            public void success(String str) {
                try {
                    idealStatusListener.onSuccess(IdealResult.fromJson(str));
                } catch (JSONException e) {
                    idealStatusListener.onFailure(e);
                }
            }

            public void failure(Exception exc) {
                idealStatusListener.onFailure(exc);
            }
        });
    }

    /* access modifiers changed from: private */
    public static ConfigurationException checkIdealEnabled(Configuration configuration) {
        if (!configuration.getBraintreeApiConfiguration().isEnabled()) {
            return new ConfigurationException("Your access is restricted and cannot use this part of the Braintree API.");
        }
        if (!configuration.getIdealConfiguration().isEnabled()) {
            return new ConfigurationException("iDEAL is not enabled for this merchant.");
        }
        return null;
    }
}
