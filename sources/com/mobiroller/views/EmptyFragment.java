package com.mobiroller.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.mobiroller.mobi942763453128.R;

public class EmptyFragment extends Fragment {
    Context context;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.empty_fragment, viewGroup, false);
        this.context = inflate.getContext();
        return inflate;
    }
}
