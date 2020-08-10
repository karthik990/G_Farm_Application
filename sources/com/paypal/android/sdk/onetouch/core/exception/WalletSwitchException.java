package com.paypal.android.sdk.onetouch.core.exception;

public class WalletSwitchException extends Exception {
    public WalletSwitchException(String str) {
        super(str);
    }

    @Deprecated
    public WalletSwitchException(Throwable th) {
        super(th);
    }
}
