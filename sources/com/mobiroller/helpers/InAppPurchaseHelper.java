package com.mobiroller.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;
import com.anjlab.android.iab.p020v3.BillingProcessor;
import com.anjlab.android.iab.p020v3.PurchaseState;
import com.anjlab.android.iab.p020v3.TransactionDetails;
import com.braintreepayments.api.models.BinData;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.InAppPurchaseProduct;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class InAppPurchaseHelper {

    public static final class Builder {
        private Context mContext;

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public void build() {
            Context context = this.mContext;
            if (context != null) {
                InAppPurchaseHelper.init(context);
                return;
            }
            throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
        }
    }

    static class Security {
        private static final String KEY_FACTORY_ALGORITHM = "RSA";
        private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
        private static final String TAG = "IABUtil/Security";

        public boolean verifyPurchase(String str, String str2, String str3) {
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3)) {
                return verify(generatePublicKey(str), str2, str3);
            }
            Log.e(TAG, "Purchase verification failed: missing data.");
            return false;
        }

        private PublicKey generatePublicKey(String str) {
            try {
                return KeyFactory.getInstance(KEY_FACTORY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e2) {
                Log.e(TAG, "Invalid key specification.");
                throw new IllegalArgumentException(e2);
            }
        }

        private boolean verify(PublicKey publicKey, String str, String str2) {
            String str3 = TAG;
            try {
                byte[] decode = Base64.decode(str2, 0);
                try {
                    Signature instance = Signature.getInstance(SIGNATURE_ALGORITHM);
                    instance.initVerify(publicKey);
                    instance.update(str.getBytes());
                    if (instance.verify(decode)) {
                        return true;
                    }
                    Log.e(str3, "Signature verification failed.");
                    return false;
                } catch (NoSuchAlgorithmException unused) {
                    Log.e(str3, "NoSuchAlgorithmException.");
                    return false;
                } catch (InvalidKeyException unused2) {
                    Log.e(str3, "Invalid key specification.");
                    return false;
                } catch (SignatureException unused3) {
                    Log.e(str3, "Signature exception.");
                    return false;
                }
            } catch (IllegalArgumentException unused4) {
                Log.e(str3, "Base64 decoding failed.");
                return false;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void init(Context context) {
    }

    public static boolean checkScreenIsInPurchaseList(String str) {
        InAppPurchaseModel inAppPurchaseModel = JSONStorage.getInAppPurchaseModel();
        if (!(inAppPurchaseModel == null || inAppPurchaseModel.products == null)) {
            List<InAppPurchaseProduct> list = inAppPurchaseModel.products;
            for (int i = 0; i < list.size(); i++) {
                if (((InAppPurchaseProduct) list.get(i)).screenList != null) {
                    for (int i2 = 0; i2 < ((InAppPurchaseProduct) list.get(i)).screenList.size(); i2++) {
                        if (((String) ((InAppPurchaseProduct) list.get(i)).screenList.get(i2)).equalsIgnoreCase(str)) {
                            return true;
                        }
                    }
                    continue;
                }
            }
        }
        return false;
    }

    public static List<InAppPurchaseProduct> getScreenProductList(String str) {
        ArrayList arrayList = new ArrayList();
        List<InAppPurchaseProduct> list = JSONStorage.getInAppPurchaseModel().products;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (((InAppPurchaseProduct) list.get(i)).screenList != null) {
                    for (int i2 = 0; i2 < ((InAppPurchaseProduct) list.get(i)).screenList.size(); i2++) {
                        if (((String) ((InAppPurchaseProduct) list.get(i)).screenList.get(i2)).equalsIgnoreCase(str)) {
                            arrayList.add(list.get(i));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static String getInAppPurchaseLicenseKey() {
        return JSONStorage.getInAppPurchaseModel().androidLicenseKey;
    }

    public static boolean checkIabAvailable(Context context) {
        if (BillingProcessor.isIabServiceAvailable(context)) {
            return true;
        }
        Toast.makeText(context, context.getString(R.string.in_app_purchase_not_available), 0).show();
        return false;
    }

    public static String getPeriodString(Context context, String str) {
        boolean equalsIgnoreCase = str.equalsIgnoreCase("P1W");
        String str2 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
        if (equalsIgnoreCase) {
            return context.getString(R.string.in_app_purchase_period_week, new Object[]{str2});
        } else if (str.equalsIgnoreCase("P1M")) {
            return context.getString(R.string.in_app_purchase_period_month, new Object[]{str2});
        } else if (str.equalsIgnoreCase("P3M")) {
            return context.getString(R.string.in_app_purchase_period_month, new Object[]{ExifInterface.GPS_MEASUREMENT_3D});
        } else if (!str.equalsIgnoreCase("P6M")) {
            return str.equalsIgnoreCase("P1Y") ? context.getString(R.string.in_app_purchase_period_yearly) : BinData.UNKNOWN;
        } else {
            return context.getString(R.string.in_app_purchase_period_month, new Object[]{"6"});
        }
    }

    public static boolean checkIsInAppPurchaseValid(Context context) {
        return (!UtilManager.sharedPrefHelper().getIsInAppPurchaseActive() || JSONStorage.getInAppPurchaseModel() == null || JSONStorage.getInAppPurchaseModel().androidLicenseKey == null) ? false : true;
    }

    public static boolean verifyTransaction(String str, String str2) {
        return new Security().verifyPurchase(getInAppPurchaseLicenseKey(), str, str2);
    }

    public static boolean isScreenPurchased(BillingProcessor billingProcessor, String str) {
        billingProcessor.loadOwnedPurchasesFromGoogle();
        List screenProductList = getScreenProductList(str);
        int i = 0;
        while (i < screenProductList.size()) {
            if (((InAppPurchaseProduct) screenProductList.get(i)).oneTimeProductId != null && billingProcessor.isPurchased(((InAppPurchaseProduct) screenProductList.get(i)).oneTimeProductId)) {
                TransactionDetails purchaseTransactionDetails = billingProcessor.getPurchaseTransactionDetails(((InAppPurchaseProduct) screenProductList.get(i)).oneTimeProductId);
                if (purchaseTransactionDetails == null || purchaseTransactionDetails.purchaseInfo.purchaseData.purchaseState != PurchaseState.PurchasedSuccessfully) {
                    return false;
                }
                return true;
            } else if (((InAppPurchaseProduct) screenProductList.get(i)).subscriptionProductId != null && ((InAppPurchaseProduct) screenProductList.get(i)).subscriptionProductId.length != 0 && billingProcessor.isSubscribed(((InAppPurchaseProduct) screenProductList.get(i)).subscriptionProductId[0])) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }
}
