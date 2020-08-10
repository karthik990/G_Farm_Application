package com.mobiroller.workers;

import android.content.Context;
import androidx.work.ListenableWorker.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.mobiroller.activities.ecommerce.PayPalActivity;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.models.ecommerce.CompleteOrder;
import java.io.IOException;

public class PayPalRequestWorker extends Worker {
    public PayPalRequestWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    public Result doWork() {
        String string = getInputData().getString(PayPalActivity.PAYPAL_ORDER_ID_KEY);
        String string2 = getInputData().getString(PayPalActivity.PAYPAL_NONCE_KEY);
        CompleteOrder completeOrder = new CompleteOrder();
        completeOrder.orderId = string;
        completeOrder.nonce = string2;
        completeOrder.paymentType = ECommerceConstant.PAYPAL;
        try {
            if (new ECommerceRequestHelper().getAPIService().tryAgain(completeOrder).execute().isSuccessful()) {
                return Result.success();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
