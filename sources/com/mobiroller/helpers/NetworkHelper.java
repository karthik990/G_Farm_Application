package com.mobiroller.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.mobiroller.mobi942763453128.R;

public class NetworkHelper {
    private Context context;

    public NetworkHelper(Context context2) {
        this.context = context2;
    }

    public boolean isConnected() {
        return ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    public static boolean isConnected(Context context2) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context2.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                for (NetworkInfo state : allNetworkInfo) {
                    if (state.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean connectionRequiredModule(String str, Context context2) {
        for (String equalsIgnoreCase : context2.getResources().getStringArray(R.array.connection_required_activities)) {
            if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
