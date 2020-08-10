package com.mobiroller.util;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ApplyzeResponse;
import com.mobiroller.models.ecommerce.OrderFailedResponse;
import com.mobiroller.models.response.APIError;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        try {
            return (APIError) new Gson().fromJson(response.errorBody().string(), APIError.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new APIError();
        }
    }

    public static OrderFailedResponse parseErrorECommerceOrder(Response response) {
        try {
            return (OrderFailedResponse) new Gson().fromJson(response.errorBody().string(), OrderFailedResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new OrderFailedResponse();
        }
    }

    public static ApplyzeResponse parseErrorApplyze(Response response) {
        try {
            return (ApplyzeResponse) new Gson().fromJson(response.errorBody().string(), ApplyzeResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApplyzeResponse();
        }
    }

    public static void showErrorToast(Context context) {
        if (!UtilManager.networkHelper().isConnected()) {
            Toast.makeText(context, R.string.please_check_your_internet_connection, 0).show();
        } else {
            Toast.makeText(context, R.string.common_error, 0).show();
        }
    }
}
