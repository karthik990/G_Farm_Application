package com.mobiroller.fragments.preview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.fragments.BaseFragment;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;

public class NotSupportedFragment extends BaseFragment {
    private int mModuleName = R.string.chat_module;
    @BindView(2131362802)
    TextView notSupportedText;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_preview_not_supported, viewGroup, false);
        ButterKnife.bind((Object) this, inflate);
        int i = this.mModuleName;
        if (i == 0) {
            this.notSupportedText.setText(getString(R.string.info_module_not_supported_on_preview, getString(i)));
        }
        return inflate;
    }

    public void setModule(int i) {
        this.mModuleName = i;
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }
}
