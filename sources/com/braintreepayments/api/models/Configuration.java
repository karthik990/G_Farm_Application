package com.braintreepayments.api.models;

import com.braintreepayments.api.Json;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Configuration {
    private static final String ANALYTICS_KEY = "analytics";
    private static final String ASSETS_URL_KEY = "assetsUrl";
    private static final String BRAINTREE_API_KEY = "braintreeApi";
    private static final String CARD_KEY = "creditCards";
    private static final String CHALLENGES_KEY = "challenges";
    private static final String CLIENT_API_URL_KEY = "clientApiUrl";
    private static final String ENVIRONMENT_KEY = "environment";
    private static final String GOOGLE_PAYMENT_KEY = "androidPay";
    private static final String GRAPHQL_KEY = "graphQL";
    private static final String IDEAL_KEY = "ideal";
    private static final String KOUNT_KEY = "kount";
    private static final String MERCHANT_ACCOUNT_ID_KEY = "merchantAccountId";
    private static final String MERCHANT_ID_KEY = "merchantId";
    private static final String PAYPAL_ENABLED_KEY = "paypalEnabled";
    private static final String PAYPAL_KEY = "paypal";
    private static final String PAY_WITH_VENMO_KEY = "payWithVenmo";
    private static final String SAMSUNG_PAY_KEY = "samsungPay";
    private static final String THREE_D_SECURE_ENABLED_KEY = "threeDSecureEnabled";
    private static final String UNIONPAY_KEY = "unionPay";
    private static final String VISA_CHECKOUT_KEY = "visaCheckout";
    private AnalyticsConfiguration mAnalyticsConfiguration;
    private String mAssetsUrl;
    private BraintreeApiConfiguration mBraintreeApiConfiguration;
    private CardConfiguration mCardConfiguration;
    private final Set<String> mChallenges = new HashSet();
    private String mClientApiUrl;
    private String mConfigurationString;
    private String mEnvironment;
    private GooglePaymentConfiguration mGooglePaymentConfiguration;
    private GraphQLConfiguration mGraphQLConfiguration;
    private IdealConfiguration mIdealConfiguration;
    private KountConfiguration mKountConfiguration;
    private String mMerchantAccountId;
    private String mMerchantId;
    private PayPalConfiguration mPayPalConfiguration;
    private boolean mPaypalEnabled;
    private SamsungPayConfiguration mSamsungPayConfiguration;
    private boolean mThreeDSecureEnabled;
    private UnionPayConfiguration mUnionPayConfiguration;
    private VenmoConfiguration mVenmoConfiguration;
    private VisaCheckoutConfiguration mVisaCheckoutConfiguration;

    public static Configuration fromJson(String str) throws JSONException {
        return new Configuration(str);
    }

    protected Configuration(String str) throws JSONException {
        if (str != null) {
            this.mConfigurationString = str;
            JSONObject jSONObject = new JSONObject(str);
            this.mAssetsUrl = Json.optString(jSONObject, ASSETS_URL_KEY, "");
            this.mClientApiUrl = jSONObject.getString(CLIENT_API_URL_KEY);
            parseJsonChallenges(jSONObject.optJSONArray(CHALLENGES_KEY));
            this.mEnvironment = jSONObject.getString(ENVIRONMENT_KEY);
            this.mMerchantId = jSONObject.getString(MERCHANT_ID_KEY);
            this.mMerchantAccountId = Json.optString(jSONObject, MERCHANT_ACCOUNT_ID_KEY, null);
            this.mAnalyticsConfiguration = AnalyticsConfiguration.fromJson(jSONObject.optJSONObject("analytics"));
            this.mBraintreeApiConfiguration = BraintreeApiConfiguration.fromJson(jSONObject.optJSONObject(BRAINTREE_API_KEY));
            this.mCardConfiguration = CardConfiguration.fromJson(jSONObject.optJSONObject(CARD_KEY));
            this.mPaypalEnabled = jSONObject.optBoolean(PAYPAL_ENABLED_KEY, false);
            this.mPayPalConfiguration = PayPalConfiguration.fromJson(jSONObject.optJSONObject(PAYPAL_KEY));
            this.mGooglePaymentConfiguration = GooglePaymentConfiguration.fromJson(jSONObject.optJSONObject(GOOGLE_PAYMENT_KEY));
            this.mThreeDSecureEnabled = jSONObject.optBoolean(THREE_D_SECURE_ENABLED_KEY, false);
            this.mVenmoConfiguration = VenmoConfiguration.fromJson(jSONObject.optJSONObject(PAY_WITH_VENMO_KEY));
            this.mKountConfiguration = KountConfiguration.fromJson(jSONObject.optJSONObject(KOUNT_KEY));
            this.mUnionPayConfiguration = UnionPayConfiguration.fromJson(jSONObject.optJSONObject(UNIONPAY_KEY));
            this.mVisaCheckoutConfiguration = VisaCheckoutConfiguration.fromJson(jSONObject.optJSONObject(VISA_CHECKOUT_KEY));
            this.mIdealConfiguration = IdealConfiguration.fromJson(jSONObject.optJSONObject(IDEAL_KEY));
            this.mGraphQLConfiguration = GraphQLConfiguration.fromJson(jSONObject.optJSONObject(GRAPHQL_KEY));
            this.mSamsungPayConfiguration = SamsungPayConfiguration.fromJson(jSONObject.optJSONObject(SAMSUNG_PAY_KEY));
            return;
        }
        throw new JSONException("Configuration cannot be null");
    }

    public String toJson() {
        return this.mConfigurationString;
    }

    public String getAssetsUrl() {
        return this.mAssetsUrl;
    }

    public String getClientApiUrl() {
        return this.mClientApiUrl;
    }

    public boolean isCvvChallengePresent() {
        return this.mChallenges.contains("cvv");
    }

    public boolean isPostalCodeChallengePresent() {
        return this.mChallenges.contains(PostalAddressParser.POSTAL_CODE_UNDERSCORE_KEY);
    }

    public BraintreeApiConfiguration getBraintreeApiConfiguration() {
        return this.mBraintreeApiConfiguration;
    }

    public String getEnvironment() {
        return this.mEnvironment;
    }

    public CardConfiguration getCardConfiguration() {
        return this.mCardConfiguration;
    }

    public boolean isPayPalEnabled() {
        return this.mPaypalEnabled && this.mPayPalConfiguration.isEnabled();
    }

    public PayPalConfiguration getPayPal() {
        return this.mPayPalConfiguration;
    }

    public GooglePaymentConfiguration getGooglePayment() {
        return this.mGooglePaymentConfiguration;
    }

    @Deprecated
    public AndroidPayConfiguration getAndroidPay() {
        return getGooglePayment();
    }

    public boolean isThreeDSecureEnabled() {
        return this.mThreeDSecureEnabled;
    }

    public String getMerchantId() {
        return this.mMerchantId;
    }

    public String getMerchantAccountId() {
        return this.mMerchantAccountId;
    }

    public AnalyticsConfiguration getAnalytics() {
        return this.mAnalyticsConfiguration;
    }

    public VenmoConfiguration getPayWithVenmo() {
        return this.mVenmoConfiguration;
    }

    public UnionPayConfiguration getUnionPay() {
        return this.mUnionPayConfiguration;
    }

    public VisaCheckoutConfiguration getVisaCheckout() {
        return this.mVisaCheckoutConfiguration;
    }

    public KountConfiguration getKount() {
        return this.mKountConfiguration;
    }

    public IdealConfiguration getIdealConfiguration() {
        return this.mIdealConfiguration;
    }

    public GraphQLConfiguration getGraphQL() {
        return this.mGraphQLConfiguration;
    }

    public SamsungPayConfiguration getSamsungPay() {
        return this.mSamsungPayConfiguration;
    }

    private void parseJsonChallenges(JSONArray jSONArray) {
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                this.mChallenges.add(jSONArray.optString(i, ""));
            }
        }
    }
}
