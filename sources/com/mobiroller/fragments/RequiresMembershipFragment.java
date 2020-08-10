package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.mobiroller.mobi942763453128.R;

public class RequiresMembershipFragment extends Fragment {
    TextView errorText;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_requires_membership, viewGroup, false);
        this.errorText = (TextView) inflate.findViewById(R.id.empty_view_text);
        if (getArguments() != null && getArguments().containsKey("invalidAccount")) {
            this.errorText.setText(R.string.chat_action_invalid_account);
        }
        return inflate;
    }
}
