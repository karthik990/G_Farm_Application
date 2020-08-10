package com.firebase.p037ui.auth.p038ui.phone;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.firebase.p037ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p037ui.auth.data.model.PhoneNumber;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p037ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest.Builder;

/* renamed from: com.firebase.ui.auth.ui.phone.CheckPhoneHandler */
public class CheckPhoneHandler extends AuthViewModelBase<PhoneNumber> {
    public CheckPhoneHandler(Application application) {
        super(application);
    }

    public void fetchCredential() {
        setResult(Resource.forFailure(new PendingIntentRequiredException(Credentials.getClient((Context) getApplication()).getHintPickerIntent(new Builder().setPhoneNumberIdentifierSupported(true).build()), 101)));
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 101 && i2 == -1) {
            String formatUsingCurrentCountry = PhoneNumberUtils.formatUsingCurrentCountry(((Credential) intent.getParcelableExtra(Credential.EXTRA_KEY)).getId(), getApplication());
            if (formatUsingCurrentCountry != null) {
                setResult(Resource.forSuccess(PhoneNumberUtils.getPhoneNumber(formatUsingCurrentCountry)));
            }
        }
    }
}
