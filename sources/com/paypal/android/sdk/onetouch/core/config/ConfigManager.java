package com.paypal.android.sdk.onetouch.core.config;

import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.network.PayPalHttpClient;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigManager {
    private static final String CONFIGURATION_URL = "https://www.paypalobjects.com/webstatic/otc/otc-config.android.json";
    private static final int MINIMUM_TIME_BETWEEN_CONSECUTIVE_REQUESTS = -5;
    private static final int MINIMUM_TIME_BETWEEN_REFRESH = -4;
    private static final String PREFERENCES_CONFIG_FILE = "com.paypal.otc.config.file";
    private static final String PREFERENCES_CONFIG_IS_DEFAULT = "com.paypal.otc.config.isDefault";
    private static final String PREFERENCES_LAST_UPDATED = "com.paypal.otc.config.lastUpdated.timestamp";
    private final ContextInspector mContextInspector;
    private final PayPalHttpClient mHttpClient;
    private Date mLastInitiatedUpdate;
    private boolean mUseHardcodedConfig = false;

    public ConfigManager(ContextInspector contextInspector, PayPalHttpClient payPalHttpClient) {
        this.mContextInspector = contextInspector;
        this.mHttpClient = payPalHttpClient;
    }

    public void useHardcodedConfig(boolean z) {
        this.mUseHardcodedConfig = z;
        refreshConfiguration();
    }

    public void refreshConfiguration() {
        if (!this.mUseHardcodedConfig && requiresUpdate()) {
            this.mLastInitiatedUpdate = new Date();
            this.mHttpClient.get(CONFIGURATION_URL, new HttpResponseCallback() {
                public void failure(Exception exc) {
                }

                public void success(String str) {
                    try {
                        ConfigManager.this.setConfig(new JSONObject(str).toString(), false);
                    } catch (JSONException unused) {
                    }
                }
            });
        }
    }

    private boolean requiresUpdate() {
        Calendar instance = Calendar.getInstance();
        instance.add(11, -4);
        boolean before = new Date(this.mContextInspector.getLongPreference(PREFERENCES_LAST_UPDATED, 0)).before(instance.getTime());
        Calendar instance2 = Calendar.getInstance();
        instance2.add(13, -5);
        Date date = this.mLastInitiatedUpdate;
        boolean z = date != null && !date.before(instance2.getTime());
        boolean booleanPreference = this.mContextInspector.getBooleanPreference(PREFERENCES_CONFIG_IS_DEFAULT, true);
        if ((before || booleanPreference) && !z) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        r3 = true;
        r4 = r1;
        r1 = r0;
        r0 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0039, code lost:
        throw new java.lang.RuntimeException("could not parse default file");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r0 = getOtcConfiguration(r1);
        refreshConfiguration();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.paypal.android.sdk.onetouch.core.config.OtcConfiguration getConfig() {
        /*
            r5 = this;
            r5.refreshConfiguration()
            com.paypal.android.sdk.onetouch.core.base.ContextInspector r0 = r5.mContextInspector
            java.lang.String r1 = "com.paypal.otc.config.file"
            java.lang.String r0 = r0.getStringPreference(r1)
            java.lang.String r1 = "{\"os\":\"Android\",\"file_timestamp\":\"2016-03-10T21:15:00Z\",\"1.0\":{\"oauth2_recipes_in_decreasing_priority_order\":[{\"target\":\"wallet\",\"protocol\":\"3\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AE\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BG\",\"en_BH\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_CZ\",\"en_DE\",\"en_DK\",\"en_DZ\",\"en_EE\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_GR\",\"en_HK\",\"en_HU\",\"en_IE\",\"en_IL\",\"en_IT\",\"en_JO\",\"en_JP\",\"en_KW\",\"en_KZ\",\"en_LT\",\"en_LU\",\"en_LV\",\"en_MA\",\"en_MX\",\"en_MY\",\"en_NL\",\"en_NO\",\"en_NZ\",\"en_OM\",\"en_PH\",\"en_PL\",\"en_PT\",\"en_QA\",\"en_RO\",\"en_RU\",\"en_SA\",\"en_SE\",\"en_SG\",\"en_SI\",\"en_SK\",\"en_TN\",\"en_TR\",\"en_US\",\"en_YE\",\"es_AE\",\"es_AR\",\"es_BH\",\"es_CZ\",\"es_DZ\",\"es_EE\",\"es_ES\",\"es_FI\",\"es_GR\",\"es_HU\",\"es_JO\",\"es_KW\",\"es_KZ\",\"es_LT\",\"es_LU\",\"es_LV\",\"es_MA\",\"es_MX\",\"es_NZ\",\"es_OM\",\"es_PT\",\"es_QA\",\"es_RO\",\"es_SA\",\"es_SI\",\"es_SK\",\"es_TN\",\"es_US\",\"es_YE\",\"fr_AE\",\"fr_BE\",\"fr_BH\",\"fr_CA\",\"fr_CH\",\"fr_CZ\",\"fr_DZ\",\"fr_EE\",\"fr_FI\",\"fr_FR\",\"fr_GR\",\"fr_HU\",\"fr_JO\",\"fr_KW\",\"fr_KZ\",\"fr_LT\",\"fr_LU\",\"fr_LV\",\"fr_MA\",\"fr_NZ\",\"fr_OM\",\"fr_PT\",\"fr_QA\",\"fr_RO\",\"fr_SA\",\"fr_SI\",\"fr_SK\",\"fr_TN\",\"fr_US\",\"fr_YE\",\"it_IT\",\"iw_IL\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_EE\",\"ru_LT\",\"ru_LV\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_AE\",\"zh_BH\",\"zh_CN\",\"zh_CZ\",\"zh_DZ\",\"zh_EE\",\"zh_FI\",\"zh_GR\",\"zh_HK\",\"zh_HU\",\"zh_JO\",\"zh_KW\",\"zh_KZ\",\"zh_LT\",\"zh_LU\",\"zh_LV\",\"zh_MA\",\"zh_NZ\",\"zh_OM\",\"zh_PT\",\"zh_QA\",\"zh_RO\",\"zh_SA\",\"zh_SI\",\"zh_SK\",\"zh_TN\",\"zh_US\",\"zh_YE\"],\"scope\":[\"https://uri.paypal.com/services/payments/futurepayments\",\"email\",\"address\",\"phone\",\"openid\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.foundation.interapp.presentation.activity.FuturePaymentActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v3.TouchActivity\"},{\"target\":\"wallet\",\"protocol\":\"2\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AE\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BG\",\"en_BH\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_CZ\",\"en_DE\",\"en_DK\",\"en_DZ\",\"en_EE\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_GR\",\"en_HK\",\"en_HU\",\"en_IE\",\"en_IL\",\"en_IT\",\"en_JO\",\"en_JP\",\"en_KW\",\"en_KZ\",\"en_LT\",\"en_LU\",\"en_LV\",\"en_MA\",\"en_MX\",\"en_MY\",\"en_NL\",\"en_NO\",\"en_NZ\",\"en_OM\",\"en_PH\",\"en_PL\",\"en_PT\",\"en_QA\",\"en_RO\",\"en_RU\",\"en_SA\",\"en_SE\",\"en_SG\",\"en_SI\",\"en_SK\",\"en_TN\",\"en_TR\",\"en_US\",\"en_YE\",\"es_AE\",\"es_AR\",\"es_BH\",\"es_CZ\",\"es_DZ\",\"es_EE\",\"es_ES\",\"es_FI\",\"es_GR\",\"es_HU\",\"es_JO\",\"es_KW\",\"es_KZ\",\"es_LT\",\"es_LU\",\"es_LV\",\"es_MA\",\"es_MX\",\"es_NZ\",\"es_OM\",\"es_PT\",\"es_QA\",\"es_RO\",\"es_SA\",\"es_SI\",\"es_SK\",\"es_TN\",\"es_US\",\"es_YE\",\"fr_AE\",\"fr_BE\",\"fr_BH\",\"fr_CA\",\"fr_CH\",\"fr_CZ\",\"fr_DZ\",\"fr_EE\",\"fr_FI\",\"fr_FR\",\"fr_GR\",\"fr_HU\",\"fr_JO\",\"fr_KW\",\"fr_KZ\",\"fr_LT\",\"fr_LU\",\"fr_LV\",\"fr_MA\",\"fr_NZ\",\"fr_OM\",\"fr_PT\",\"fr_QA\",\"fr_RO\",\"fr_SA\",\"fr_SI\",\"fr_SK\",\"fr_TN\",\"fr_US\",\"fr_YE\",\"it_IT\",\"iw_IL\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_EE\",\"ru_LT\",\"ru_LV\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_AE\",\"zh_BH\",\"zh_CN\",\"zh_CZ\",\"zh_DZ\",\"zh_EE\",\"zh_FI\",\"zh_GR\",\"zh_HK\",\"zh_HU\",\"zh_JO\",\"zh_KW\",\"zh_KZ\",\"zh_LT\",\"zh_LU\",\"zh_LV\",\"zh_MA\",\"zh_NZ\",\"zh_OM\",\"zh_PT\",\"zh_QA\",\"zh_RO\",\"zh_SA\",\"zh_SI\",\"zh_SK\",\"zh_TN\",\"zh_US\",\"zh_YE\"],\"scope\":[\"https://uri.paypal.com/services/payments/futurepayments\",\"email\",\"address\",\"phone\",\"openid\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.foundation.interapp.presentation.activity.FuturePaymentActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v2.TouchActivity\"},{\"target\":\"wallet\",\"protocol\":\"1\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_DE\",\"en_DK\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_HK\",\"en_IE\",\"en_IT\",\"en_JP\",\"en_LU\",\"en_MX\",\"en_NL\",\"en_NO\",\"en_PL\",\"en_PT\",\"en_RU\",\"en_SE\",\"en_SG\",\"en_TR\",\"en_US\",\"es_AR\",\"es_ES\",\"es_FI\",\"es_LU\",\"es_MX\",\"es_PT\",\"es_US\",\"fr_BE\",\"fr_CA\",\"fr_CH\",\"fr_FI\",\"fr_FR\",\"fr_LU\",\"fr_PT\",\"fr_US\",\"it_IT\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_CN\",\"zh_FI\",\"zh_HK\",\"zh_LU\",\"zh_PT\",\"zh_US\"],\"scope\":[\"https://uri.paypal.com/services/payments/futurepayments\",\"email\",\"address\",\"phone\",\"openid\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.foundation.interapp.presentation.activity.FuturePaymentActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v1.TouchActivity\"},{\"target\":\"wallet\",\"protocol\":\"1\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_DE\",\"en_DK\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_HK\",\"en_IE\",\"en_IT\",\"en_JP\",\"en_LU\",\"en_MX\",\"en_NL\",\"en_NO\",\"en_PL\",\"en_PT\",\"en_RU\",\"en_SE\",\"en_SG\",\"en_TR\",\"en_US\",\"es_AR\",\"es_ES\",\"es_FI\",\"es_LU\",\"es_MX\",\"es_PT\",\"es_US\",\"fr_BE\",\"fr_CA\",\"fr_CH\",\"fr_FI\",\"fr_FR\",\"fr_LU\",\"fr_PT\",\"fr_US\",\"it_IT\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_CN\",\"zh_FI\",\"zh_HK\",\"zh_LU\",\"zh_PT\",\"zh_US\"],\"scope\":[\"https://uri.paypal.com/services/payments/futurepayments\",\"email\",\"address\",\"phone\",\"openid\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.lib.authenticator.activity.v1.TouchActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v1.TouchActivity\"},{\"target\":\"browser\",\"protocol\":\"3\",\"scope\":[\"https://uri.paypal.com/services/payments/futurepayments\",\"email\",\"address\",\"phone\",\"openid\"],\"endpoints\":{\"live\":{\"url\":\"https://checkout.paypal.com/one-touch-login/\",\"certificate\":\"MIIDzzCCAregAwIBAgIJAIHt9UbL9i3iMA0GCSqGSIb3DQEBCwUAMH4xCzAJBgNVBAYTAlVTMRMwEQYDVQQIDApDYWxpZm9ybmlhMREwDwYDVQQHDAhTYW4gSm9zZTEPMA0GA1UECgwGUGF5UGFsMRIwEAYDVQQLDAlCcmFpbnRyZWUxIjAgBgNVBAMMGVByb2R1Y3Rpb24gQnJvd3NlciBTd2l0Y2gwHhcNMTUwNDExMTc1MDI5WhcNMTcwNDEwMTc1MDI5WjB+MQswCQYDVQQGEwJVUzETMBEGA1UECAwKQ2FsaWZvcm5pYTERMA8GA1UEBwwIU2FuIEpvc2UxDzANBgNVBAoMBlBheVBhbDESMBAGA1UECwwJQnJhaW50cmVlMSIwIAYDVQQDDBlQcm9kdWN0aW9uIEJyb3dzZXIgU3dpdGNoMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt+Cc6GB3QDFCheiVoLlCY2FURFH0IjJfxsR8l5IKYVMf+GjbANvS/HtUYip4rfSjG4XlITpPkwO1cF/xaBwT3UBY9vviQlVMboVftlgxZ/iUjAlxKf28BE96bS53tObfTuHnrb+kneYQIZqWCg48mZSc5mR2gqhbs35GC4udH0EEoNIHFSvAcesUpQzR3MUU7PWRboClOvwEWvnbhmDHlB4oYaIrWxP+uTATv6cWferku77RtQJIobfSQvRVRCDqlAjbI/c7g06Fzje3P91zmWjNbKki0mu0hGFSkGLmzhL1Z0Fc83gxFB6YhTQOdc3fWabyKKD9z6rZcOLSW4w7UwIDAQABo1AwTjAdBgNVHQ4EFgQUfIDSwnyOKIDUJFAYuA0QQmeSt+gwHwYDVR0jBBgwFoAUfIDSwnyOKIDUJFAYuA0QQmeSt+gwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQsFAAOCAQEAKUw9L1PWBBD/tHDSatnWp6UNn7RtsMu+bJm5bj01pC7jX5gKm75wUKODrUnGAszRoRujsyJJrGKzbdShLlI5HLVh9cR1tpr3s/9W6DBkANPh0ClwH7t35dt0CwYH8acz7fqRfTjJfTccjnfPB5EFcpKIPv3ld8LbR/YlCWbrfUrzQM/K1FdZaK7elH/drRs+DEBFLp3Kqw3bWt4z06AV1rQVmY2yakaFippbrsrdwIL6uOqO+SfRUbMZg14Kk8WvaBgm4l6aV6dslnybfoJksnsBsmEie9nmTPiU9Z+cPuN/unjMBp4PnCTBKpc9l+PJqGq8HFnBlDBKw/NI8EepsQ==\"},\"mock\":{\"url\":\"https://checkout.paypal.com/one-touch-login/\",\"certificate\":\"MIIDoTCCAomgAwIBAgIJAMSW4aqkOaS8MA0GCSqGSIb3DQEBCwUAMGcxCzAJBgNVBAYTAlVTMREwDwYDVQQIDAhJbGxpbm9pczEQMA4GA1UEBwwHQ2hpY2FnbzESMBAGA1UECgwJQnJhaW50cmVlMR8wHQYDVQQLDBZTYW5kYm94IEJyb3dzZXIgU3dpdGNoMB4XDTE1MDQxMDE4MTMxOVoXDTIwMDQxMzE4MTMxOVowZzELMAkGA1UEBhMCVVMxETAPBgNVBAgMCElsbGlub2lzMRAwDgYDVQQHDAdDaGljYWdvMRIwEAYDVQQKDAlCcmFpbnRyZWUxHzAdBgNVBAsMFlNhbmRib3ggQnJvd3NlciBTd2l0Y2gwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDVjnC47BuUm0PKi5sUSi02wGL8zqVjRrdtt4YDyPQdELlitfv431Y7HpT6z/Xusu9/2mjlcvykGO1+okm6VDiNIaHMeez7Vv3To7J3ZCNR/WZDFyzbY7lniwISSoLMh7MF6fsOKYvc3nARez0Qs1Jp+fX+0DKCH926q4Z3OWYq3NHPrAy8E2QMzcG68XJeZGYEfVuu7SadHHkpvvSN2KSH5dNHxCknm1KpW4IoGguXepUlldmf1KorRX0DOQq/750XOP0rvh+xDH7EZSLgcCi810otzP4cPg/M5Fyj6lPAj4TZArFvqUOKnAvtri9LEAUP+/laB2mdL668au9kSV7hAgMBAAGjUDBOMB0GA1UdDgQWBBS1K42BCIULZ/QFkJQlj+Mnc7aG6jAfBgNVHSMEGDAWgBS1K42BCIULZ/QFkJQlj+Mnc7aG6jAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQBEduExXbyMtcmk6noLdoJdFtmcLPzCOmR1k1hGUDh41QWJgFcuZlM25F/qVKXSzLBRoc1ssEeUZl1AGyhOO49b/MfEPo/yVwcz254o9Pm4E9CvdqdO8mPpCrE/Pjr+7TNKyMVsktoN3B5V5K14+GQVOiSHgesEQaYq9cyxUclMs1QzyNHSe3gDN41FFDXiE7kj1h8oo4MhH7wcVv+9olOWEtawGVxSi/U9KVTmN5ShDJgTwuM74aSnRwwEj2bXEaDMbUXYXD/p4SPBq2a3ecvelzsYpWdwyF7iXoWCrPVLM46D8M4PeenTzq6efnX4mzgS/fOqI9grjS2R8btw9Idz\"},\"develop\":{\"url\":\"https://assets.staging.braintreepayments.com/one-touch-login/\",\"certificate\":\"MIIDOzCCAiOgAwIBAgIJAMlvCS4UtR7PMA0GCSqGSIb3DQEBBQUAMDQxCzAJBgNVBAYTAlVTMREwDwYDVQQIDAhJbGxpbm9pczESMBAGA1UECgwJQnJhaW50cmVlMB4XDTE1MDMyMDAxMTcyMVoXDTE2MDMxOTAxMTcyMVowNDELMAkGA1UEBhMCVVMxETAPBgNVBAgMCElsbGlub2lzMRIwEAYDVQQKDAlCcmFpbnRyZWUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDfCRhOGeMj4ci5Bbbs/x0G+PkbeL7iGEsX5UWQeA8oCWU8jpipFTC271Q0f5BQzXCN8L4LnwGvtm2cgAEivSBODo7XHsmxrFjKdQx1S7FIuFRKO18Uf8rIGmZHiJfhCbUEGilpwMt7hUMjjv2XDufPCMrJ8Yn2y/yDi5nhs7UsFhROm9oI2PyiJX01yR2ag8cPBb5Ahlwmj1yMWmSuHVnUN8T0rjIXyrBhxTAk3omQkQdHKj2w8afdrAcNUGi4yU/a5/pmb8tZpAa73OZVdOEQepJAAIRWXeS2BdKTkhfRJc7WEIlbi+9a2OdtM3OkIs+rZE7+WVT8XQoiLxpUd/wNAgMBAAGjUDBOMB0GA1UdDgQWBBQhbJ8DtuKFhGTsrvZ41Vw5jYbmazAfBgNVHSMEGDAWgBQhbJ8DtuKFhGTsrvZ41Vw5jYbmazAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4IBAQARg2wjhJanhKu1bw63+Xfj25OUa02jK+i4vhkWeuCGd5/kxA1dZMjBfSMxh484xBpaqRIOHvZmRpKcxCgci8xRbbJiaXrb1vIePTTi4lfU6cpfsnjMFCHDk8E/0AxIfOpQ0BSJY35WqB45xaIWBAY8lQ2pNfiPyK4kzajSOg+kbEKLmA0udYy8tsydt+88+R88rYKt4qDBo+Z5zgJ2fZvbAp99cBASHqMCoUoPb96YWEhaWhjArVGzgevpopKA9aOAFdndPKLbe6y29bbfLfQqat0B1fVmutCIHGIXtsPHQDe/cXJtoJk7HmD08++C9YvjxlSi8jxLb5nIA0QGI0yj\"}},\"packages\":[\"com.android.chrome\",\"*\"]}],\"checkout_recipes_in_decreasing_priority_order\":[{\"target\":\"wallet\",\"protocol\":\"3\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AE\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BG\",\"en_BH\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_CZ\",\"en_DE\",\"en_DK\",\"en_DZ\",\"en_EE\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_GR\",\"en_HK\",\"en_HU\",\"en_IE\",\"en_IL\",\"en_IT\",\"en_JO\",\"en_JP\",\"en_KW\",\"en_KZ\",\"en_LT\",\"en_LU\",\"en_LV\",\"en_MA\",\"en_MX\",\"en_MY\",\"en_NL\",\"en_NO\",\"en_NZ\",\"en_OM\",\"en_PH\",\"en_PL\",\"en_PT\",\"en_QA\",\"en_RO\",\"en_RU\",\"en_SA\",\"en_SE\",\"en_SG\",\"en_SI\",\"en_SK\",\"en_TN\",\"en_TR\",\"en_US\",\"en_YE\",\"es_AE\",\"es_AR\",\"es_BH\",\"es_CZ\",\"es_DZ\",\"es_EE\",\"es_ES\",\"es_FI\",\"es_GR\",\"es_HU\",\"es_JO\",\"es_KW\",\"es_KZ\",\"es_LT\",\"es_LU\",\"es_LV\",\"es_MA\",\"es_MX\",\"es_NZ\",\"es_OM\",\"es_PT\",\"es_QA\",\"es_RO\",\"es_SA\",\"es_SI\",\"es_SK\",\"es_TN\",\"es_US\",\"es_YE\",\"fr_AE\",\"fr_BE\",\"fr_BH\",\"fr_CA\",\"fr_CH\",\"fr_CZ\",\"fr_DZ\",\"fr_EE\",\"fr_FI\",\"fr_FR\",\"fr_GR\",\"fr_HU\",\"fr_JO\",\"fr_KW\",\"fr_KZ\",\"fr_LT\",\"fr_LU\",\"fr_LV\",\"fr_MA\",\"fr_NZ\",\"fr_OM\",\"fr_PT\",\"fr_QA\",\"fr_RO\",\"fr_SA\",\"fr_SI\",\"fr_SK\",\"fr_TN\",\"fr_US\",\"fr_YE\",\"it_IT\",\"iw_IL\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_EE\",\"ru_LT\",\"ru_LV\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_AE\",\"zh_BH\",\"zh_CN\",\"zh_CZ\",\"zh_DZ\",\"zh_EE\",\"zh_FI\",\"zh_GR\",\"zh_HK\",\"zh_HU\",\"zh_JO\",\"zh_KW\",\"zh_KZ\",\"zh_LT\",\"zh_LU\",\"zh_LV\",\"zh_MA\",\"zh_NZ\",\"zh_OM\",\"zh_PT\",\"zh_QA\",\"zh_RO\",\"zh_SA\",\"zh_SI\",\"zh_SK\",\"zh_TN\",\"zh_US\",\"zh_YE\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.foundation.interapp.presentation.activity.SinglePaymentNativeCheckoutActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v3.TouchActivity\"},{\"target\":\"wallet\",\"protocol\":\"2\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AE\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BG\",\"en_BH\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_CZ\",\"en_DE\",\"en_DK\",\"en_DZ\",\"en_EE\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_GR\",\"en_HK\",\"en_HU\",\"en_IE\",\"en_IL\",\"en_IT\",\"en_JO\",\"en_JP\",\"en_KW\",\"en_KZ\",\"en_LT\",\"en_LU\",\"en_LV\",\"en_MA\",\"en_MX\",\"en_MY\",\"en_NL\",\"en_NO\",\"en_NZ\",\"en_OM\",\"en_PH\",\"en_PL\",\"en_PT\",\"en_QA\",\"en_RO\",\"en_RU\",\"en_SA\",\"en_SE\",\"en_SG\",\"en_SI\",\"en_SK\",\"en_TN\",\"en_TR\",\"en_US\",\"en_YE\",\"es_AE\",\"es_AR\",\"es_BH\",\"es_CZ\",\"es_DZ\",\"es_EE\",\"es_ES\",\"es_FI\",\"es_GR\",\"es_HU\",\"es_JO\",\"es_KW\",\"es_KZ\",\"es_LT\",\"es_LU\",\"es_LV\",\"es_MA\",\"es_MX\",\"es_NZ\",\"es_OM\",\"es_PT\",\"es_QA\",\"es_RO\",\"es_SA\",\"es_SI\",\"es_SK\",\"es_TN\",\"es_US\",\"es_YE\",\"fr_AE\",\"fr_BE\",\"fr_BH\",\"fr_CA\",\"fr_CH\",\"fr_CZ\",\"fr_DZ\",\"fr_EE\",\"fr_FI\",\"fr_FR\",\"fr_GR\",\"fr_HU\",\"fr_JO\",\"fr_KW\",\"fr_KZ\",\"fr_LT\",\"fr_LU\",\"fr_LV\",\"fr_MA\",\"fr_NZ\",\"fr_OM\",\"fr_PT\",\"fr_QA\",\"fr_RO\",\"fr_SA\",\"fr_SI\",\"fr_SK\",\"fr_TN\",\"fr_US\",\"fr_YE\",\"it_IT\",\"iw_IL\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_EE\",\"ru_LT\",\"ru_LV\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_AE\",\"zh_BH\",\"zh_CN\",\"zh_CZ\",\"zh_DZ\",\"zh_EE\",\"zh_FI\",\"zh_GR\",\"zh_HK\",\"zh_HU\",\"zh_JO\",\"zh_KW\",\"zh_KZ\",\"zh_LT\",\"zh_LU\",\"zh_LV\",\"zh_MA\",\"zh_NZ\",\"zh_OM\",\"zh_PT\",\"zh_QA\",\"zh_RO\",\"zh_SA\",\"zh_SI\",\"zh_SK\",\"zh_TN\",\"zh_US\",\"zh_YE\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.foundation.interapp.presentation.activity.SinglePaymentNativeCheckoutActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v2.TouchActivity\"},{\"target\":\"wallet\",\"protocol\":\"2\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AE\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BG\",\"en_BH\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_CZ\",\"en_DE\",\"en_DK\",\"en_DZ\",\"en_EE\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_GR\",\"en_HK\",\"en_HU\",\"en_IE\",\"en_IL\",\"en_IT\",\"en_JO\",\"en_JP\",\"en_KW\",\"en_KZ\",\"en_LT\",\"en_LU\",\"en_LV\",\"en_MA\",\"en_MX\",\"en_MY\",\"en_NL\",\"en_NO\",\"en_NZ\",\"en_OM\",\"en_PH\",\"en_PL\",\"en_PT\",\"en_QA\",\"en_RO\",\"en_RU\",\"en_SA\",\"en_SE\",\"en_SG\",\"en_SI\",\"en_SK\",\"en_TN\",\"en_TR\",\"en_US\",\"en_YE\",\"es_AE\",\"es_AR\",\"es_BH\",\"es_CZ\",\"es_DZ\",\"es_EE\",\"es_ES\",\"es_FI\",\"es_GR\",\"es_HU\",\"es_JO\",\"es_KW\",\"es_KZ\",\"es_LT\",\"es_LU\",\"es_LV\",\"es_MA\",\"es_MX\",\"es_NZ\",\"es_OM\",\"es_PT\",\"es_QA\",\"es_RO\",\"es_SA\",\"es_SI\",\"es_SK\",\"es_TN\",\"es_US\",\"es_YE\",\"fr_AE\",\"fr_BE\",\"fr_BH\",\"fr_CA\",\"fr_CH\",\"fr_CZ\",\"fr_DZ\",\"fr_EE\",\"fr_FI\",\"fr_FR\",\"fr_GR\",\"fr_HU\",\"fr_JO\",\"fr_KW\",\"fr_KZ\",\"fr_LT\",\"fr_LU\",\"fr_LV\",\"fr_MA\",\"fr_NZ\",\"fr_OM\",\"fr_PT\",\"fr_QA\",\"fr_RO\",\"fr_SA\",\"fr_SI\",\"fr_SK\",\"fr_TN\",\"fr_US\",\"fr_YE\",\"it_IT\",\"iw_IL\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_EE\",\"ru_LT\",\"ru_LV\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_AE\",\"zh_BH\",\"zh_CN\",\"zh_CZ\",\"zh_DZ\",\"zh_EE\",\"zh_FI\",\"zh_GR\",\"zh_HK\",\"zh_HU\",\"zh_JO\",\"zh_KW\",\"zh_KZ\",\"zh_LT\",\"zh_LU\",\"zh_LV\",\"zh_MA\",\"zh_NZ\",\"zh_OM\",\"zh_PT\",\"zh_QA\",\"zh_RO\",\"zh_SA\",\"zh_SI\",\"zh_SK\",\"zh_TN\",\"zh_US\",\"zh_YE\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.lib.authenticator.activity.v2.TouchActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v2.TouchActivity\"},{\"target\":\"browser\",\"protocol\":\"0\",\"packages\":[\"com.android.chrome\",\"*\"]}],\"billing_agreement_recipes_in_decreasing_priority_order\":[{\"target\":\"wallet\",\"protocol\":\"3\",\"supported_locales\":[\"da_DK\",\"de_AT\",\"de_BE\",\"de_CH\",\"de_DE\",\"de_DK\",\"de_LU\",\"en_AE\",\"en_AR\",\"en_AT\",\"en_AU\",\"en_BE\",\"en_BG\",\"en_BH\",\"en_BR\",\"en_CA\",\"en_CH\",\"en_CN\",\"en_CZ\",\"en_DE\",\"en_DK\",\"en_DZ\",\"en_EE\",\"en_ES\",\"en_FI\",\"en_FR\",\"en_GB\",\"en_GR\",\"en_HK\",\"en_HU\",\"en_IE\",\"en_IL\",\"en_IT\",\"en_JO\",\"en_JP\",\"en_KW\",\"en_KZ\",\"en_LT\",\"en_LU\",\"en_LV\",\"en_MA\",\"en_MX\",\"en_MY\",\"en_NL\",\"en_NO\",\"en_NZ\",\"en_OM\",\"en_PH\",\"en_PL\",\"en_PT\",\"en_QA\",\"en_RO\",\"en_RU\",\"en_SA\",\"en_SE\",\"en_SG\",\"en_SI\",\"en_SK\",\"en_TN\",\"en_TR\",\"en_US\",\"en_YE\",\"es_AE\",\"es_AR\",\"es_BH\",\"es_CZ\",\"es_DZ\",\"es_EE\",\"es_ES\",\"es_FI\",\"es_GR\",\"es_HU\",\"es_JO\",\"es_KW\",\"es_KZ\",\"es_LT\",\"es_LU\",\"es_LV\",\"es_MA\",\"es_MX\",\"es_NZ\",\"es_OM\",\"es_PT\",\"es_QA\",\"es_RO\",\"es_SA\",\"es_SI\",\"es_SK\",\"es_TN\",\"es_US\",\"es_YE\",\"fr_AE\",\"fr_BE\",\"fr_BH\",\"fr_CA\",\"fr_CH\",\"fr_CZ\",\"fr_DZ\",\"fr_EE\",\"fr_FI\",\"fr_FR\",\"fr_GR\",\"fr_HU\",\"fr_JO\",\"fr_KW\",\"fr_KZ\",\"fr_LT\",\"fr_LU\",\"fr_LV\",\"fr_MA\",\"fr_NZ\",\"fr_OM\",\"fr_PT\",\"fr_QA\",\"fr_RO\",\"fr_SA\",\"fr_SI\",\"fr_SK\",\"fr_TN\",\"fr_US\",\"fr_YE\",\"it_IT\",\"iw_IL\",\"ja_JP\",\"nb_NO\",\"nl_BE\",\"nl_NL\",\"no_NO\",\"pl_PL\",\"pt_BR\",\"pt_PT\",\"ru_EE\",\"ru_LT\",\"ru_LV\",\"ru_RU\",\"se_SE\",\"sv_SE\",\"tr_TR\",\"zh_AE\",\"zh_BH\",\"zh_CN\",\"zh_CZ\",\"zh_DZ\",\"zh_EE\",\"zh_FI\",\"zh_GR\",\"zh_HK\",\"zh_HU\",\"zh_JO\",\"zh_KW\",\"zh_KZ\",\"zh_LT\",\"zh_LU\",\"zh_LV\",\"zh_MA\",\"zh_NZ\",\"zh_OM\",\"zh_PT\",\"zh_QA\",\"zh_RO\",\"zh_SA\",\"zh_SI\",\"zh_SK\",\"zh_TN\",\"zh_US\",\"zh_YE\"],\"packages\":[\"com.paypal.android.p2pmobile\"],\"component\":\"com.paypal.android.foundation.interapp.presentation.activity.SinglePaymentNativeCheckoutActivity\",\"intent_action\":\"com.paypal.android.lib.authenticator.activity.v3.TouchActivity\"},{\"target\":\"browser\",\"protocol\":\"0\",\"packages\":[\"com.android.chrome\",\"*\"]}]}}"
            r2 = 1
            if (r0 == 0) goto L_0x0017
            boolean r3 = r5.mUseHardcodedConfig
            if (r3 == 0) goto L_0x0015
            goto L_0x0017
        L_0x0015:
            r3 = 0
            goto L_0x0019
        L_0x0017:
            r0 = r1
            r3 = 1
        L_0x0019:
            com.paypal.android.sdk.onetouch.core.config.OtcConfiguration r1 = r5.getOtcConfiguration(r0)     // Catch:{ JSONException -> 0x001e }
            goto L_0x0029
        L_0x001e:
            com.paypal.android.sdk.onetouch.core.config.OtcConfiguration r0 = r5.getOtcConfiguration(r1)     // Catch:{ JSONException -> 0x0032 }
            r5.refreshConfiguration()     // Catch:{ JSONException -> 0x0032 }
            r3 = 1
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x0029:
            if (r3 == 0) goto L_0x0031
            r5.setConfig(r0, r2)
            r5.refreshConfiguration()
        L_0x0031:
            return r1
        L_0x0032:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "could not parse default file"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.paypal.android.sdk.onetouch.core.config.ConfigManager.getConfig():com.paypal.android.sdk.onetouch.core.config.OtcConfiguration");
    }

    private OtcConfiguration getOtcConfiguration(String str) throws JSONException {
        return new ConfigFileParser().getParsedConfig(new JSONObject(str));
    }

    /* access modifiers changed from: private */
    public void setConfig(String str, boolean z) {
        this.mContextInspector.setPreference(PREFERENCES_CONFIG_FILE, str);
        this.mContextInspector.setPreference(PREFERENCES_LAST_UPDATED, System.currentTimeMillis());
        this.mContextInspector.setPreference(PREFERENCES_CONFIG_IS_DEFAULT, z);
    }
}
