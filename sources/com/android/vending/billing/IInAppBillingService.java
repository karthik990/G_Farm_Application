package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IInAppBillingService extends IInterface {

    public static abstract class Stub extends Binder implements IInAppBillingService {
        private static final String DESCRIPTOR = "com.android.vending.billing.IInAppBillingService";
        static final int TRANSACTION_acknowledgePurchaseExtraParams = 902;
        static final int TRANSACTION_consumePurchase = 5;
        static final int TRANSACTION_consumePurchaseExtraParams = 12;
        static final int TRANSACTION_getBuyIntent = 3;
        static final int TRANSACTION_getBuyIntentExtraParams = 8;
        static final int TRANSACTION_getBuyIntentToReplaceSkus = 7;
        static final int TRANSACTION_getPurchaseHistory = 9;
        static final int TRANSACTION_getPurchases = 4;
        static final int TRANSACTION_getPurchasesExtraParams = 11;
        static final int TRANSACTION_getSkuDetails = 2;
        static final int TRANSACTION_getSkuDetailsExtraParams = 901;
        static final int TRANSACTION_getSubscriptionManagementIntent = 801;
        static final int TRANSACTION_isBillingSupported = 1;
        static final int TRANSACTION_isBillingSupportedExtraParams = 10;
        static final int TRANSACTION_stub = 6;

        private static class Proxy implements IInAppBillingService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public int isBillingSupported(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getSkuDetails(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getBuyIntent(int i, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getPurchases(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int consumePurchase(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int stub(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getBuyIntentToReplaceSkus(int i, String str, List<String> list, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getBuyIntentExtraParams(int i, String str, String str2, String str3, String str4, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getPurchaseHistory(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int isBillingSupportedExtraParams(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getSubscriptionManagementIntent(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_getSubscriptionManagementIntent, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getPurchasesExtraParams(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle consumePurchaseExtraParams(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getSkuDetailsExtraParams(int i, String str, String str2, Bundle bundle, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_getSkuDetailsExtraParams, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle acknowledgePurchaseExtraParams(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_acknowledgePurchaseExtraParams, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInAppBillingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IInAppBillingService)) {
                return new Proxy(iBinder);
            }
            return (IInAppBillingService) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Bundle bundle = null;
            String str = DESCRIPTOR;
            if (i == TRANSACTION_getSubscriptionManagementIntent) {
                parcel.enforceInterface(str);
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                Bundle subscriptionManagementIntent = getSubscriptionManagementIntent(readInt, readString, readString2, readString3, bundle);
                parcel2.writeNoException();
                if (subscriptionManagementIntent != null) {
                    parcel2.writeInt(1);
                    subscriptionManagementIntent.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            } else if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            } else if (i == TRANSACTION_getSkuDetailsExtraParams) {
                parcel.enforceInterface(str);
                int readInt2 = parcel.readInt();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                Bundle bundle2 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                Bundle skuDetailsExtraParams = getSkuDetailsExtraParams(readInt2, readString4, readString5, bundle2, bundle);
                parcel2.writeNoException();
                if (skuDetailsExtraParams != null) {
                    parcel2.writeInt(1);
                    skuDetailsExtraParams.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            } else if (i != TRANSACTION_acknowledgePurchaseExtraParams) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(str);
                        int isBillingSupported = isBillingSupported(parcel.readInt(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(isBillingSupported);
                        return true;
                    case 2:
                        parcel.enforceInterface(str);
                        int readInt3 = parcel.readInt();
                        String readString6 = parcel.readString();
                        String readString7 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle skuDetails = getSkuDetails(readInt3, readString6, readString7, bundle);
                        parcel2.writeNoException();
                        if (skuDetails != null) {
                            parcel2.writeInt(1);
                            skuDetails.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(str);
                        Bundle buyIntent = getBuyIntent(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        if (buyIntent != null) {
                            parcel2.writeInt(1);
                            buyIntent.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 4:
                        parcel.enforceInterface(str);
                        Bundle purchases = getPurchases(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        if (purchases != null) {
                            parcel2.writeInt(1);
                            purchases.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 5:
                        parcel.enforceInterface(str);
                        int consumePurchase = consumePurchase(parcel.readInt(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(consumePurchase);
                        return true;
                    case 6:
                        parcel.enforceInterface(str);
                        int stub = stub(parcel.readInt(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(stub);
                        return true;
                    case 7:
                        parcel.enforceInterface(str);
                        Bundle buyIntentToReplaceSkus = getBuyIntentToReplaceSkus(parcel.readInt(), parcel.readString(), parcel.createStringArrayList(), parcel.readString(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        if (buyIntentToReplaceSkus != null) {
                            parcel2.writeInt(1);
                            buyIntentToReplaceSkus.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 8:
                        parcel.enforceInterface(str);
                        int readInt4 = parcel.readInt();
                        String readString8 = parcel.readString();
                        String readString9 = parcel.readString();
                        String readString10 = parcel.readString();
                        String readString11 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle buyIntentExtraParams = getBuyIntentExtraParams(readInt4, readString8, readString9, readString10, readString11, bundle);
                        parcel2.writeNoException();
                        if (buyIntentExtraParams != null) {
                            parcel2.writeInt(1);
                            buyIntentExtraParams.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 9:
                        parcel.enforceInterface(str);
                        int readInt5 = parcel.readInt();
                        String readString12 = parcel.readString();
                        String readString13 = parcel.readString();
                        String readString14 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle purchaseHistory = getPurchaseHistory(readInt5, readString12, readString13, readString14, bundle);
                        parcel2.writeNoException();
                        if (purchaseHistory != null) {
                            parcel2.writeInt(1);
                            purchaseHistory.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 10:
                        parcel.enforceInterface(str);
                        int readInt6 = parcel.readInt();
                        String readString15 = parcel.readString();
                        String readString16 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int isBillingSupportedExtraParams = isBillingSupportedExtraParams(readInt6, readString15, readString16, bundle);
                        parcel2.writeNoException();
                        parcel2.writeInt(isBillingSupportedExtraParams);
                        return true;
                    case 11:
                        parcel.enforceInterface(str);
                        int readInt7 = parcel.readInt();
                        String readString17 = parcel.readString();
                        String readString18 = parcel.readString();
                        String readString19 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle purchasesExtraParams = getPurchasesExtraParams(readInt7, readString17, readString18, readString19, bundle);
                        parcel2.writeNoException();
                        if (purchasesExtraParams != null) {
                            parcel2.writeInt(1);
                            purchasesExtraParams.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 12:
                        parcel.enforceInterface(str);
                        int readInt8 = parcel.readInt();
                        String readString20 = parcel.readString();
                        String readString21 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle consumePurchaseExtraParams = consumePurchaseExtraParams(readInt8, readString20, readString21, bundle);
                        parcel2.writeNoException();
                        if (consumePurchaseExtraParams != null) {
                            parcel2.writeInt(1);
                            consumePurchaseExtraParams.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel.enforceInterface(str);
                int readInt9 = parcel.readInt();
                String readString22 = parcel.readString();
                String readString23 = parcel.readString();
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                Bundle acknowledgePurchaseExtraParams = acknowledgePurchaseExtraParams(readInt9, readString22, readString23, bundle);
                parcel2.writeNoException();
                if (acknowledgePurchaseExtraParams != null) {
                    parcel2.writeInt(1);
                    acknowledgePurchaseExtraParams.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            }
        }
    }

    Bundle acknowledgePurchaseExtraParams(int i, String str, String str2, Bundle bundle) throws RemoteException;

    int consumePurchase(int i, String str, String str2) throws RemoteException;

    Bundle consumePurchaseExtraParams(int i, String str, String str2, Bundle bundle) throws RemoteException;

    Bundle getBuyIntent(int i, String str, String str2, String str3, String str4) throws RemoteException;

    Bundle getBuyIntentExtraParams(int i, String str, String str2, String str3, String str4, Bundle bundle) throws RemoteException;

    Bundle getBuyIntentToReplaceSkus(int i, String str, List<String> list, String str2, String str3, String str4) throws RemoteException;

    Bundle getPurchaseHistory(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle getPurchases(int i, String str, String str2, String str3) throws RemoteException;

    Bundle getPurchasesExtraParams(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle getSkuDetails(int i, String str, String str2, Bundle bundle) throws RemoteException;

    Bundle getSkuDetailsExtraParams(int i, String str, String str2, Bundle bundle, Bundle bundle2) throws RemoteException;

    Bundle getSubscriptionManagementIntent(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    int isBillingSupported(int i, String str, String str2) throws RemoteException;

    int isBillingSupportedExtraParams(int i, String str, String str2, Bundle bundle) throws RemoteException;

    int stub(int i, String str, String str2) throws RemoteException;
}
