package com.mobiroller.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobiroller.adapters.bottomsheet.BottomSheetAdapter;
import com.mobiroller.interfaces.bottomsheet.ActionListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.bottomsheet.ActionModel;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import java.util.List;

public class ActionPickerBottomSheet extends BottomSheetDialogFragment {
    List<ActionModel> actionList;
    ActionListener actionListener;
    BottomSheetAdapter adapter;
    Behavior behavior;
    private BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetCallback() {
        public void onSlide(View view, float f) {
        }

        public void onStateChanged(View view, int i) {
            if (i == 5) {
                ActionPickerBottomSheet.this.dismiss();
            }
        }
    };
    @BindView(2131363022)
    RecyclerView recyclerView;
    String title;
    Unbinder unbinder;

    public static class Builder {
        private List<ActionModel> actionList;
        private ActionListener actionListener;
        private String title;

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setActions(List<ActionModel> list) {
            this.actionList = list;
            return this;
        }

        public Builder setActionListener(ActionListener actionListener2) {
            this.actionListener = actionListener2;
            return this;
        }

        public ActionPickerBottomSheet build() {
            ActionPickerBottomSheet actionPickerBottomSheet = new ActionPickerBottomSheet();
            actionPickerBottomSheet.title = this.title;
            actionPickerBottomSheet.actionList = this.actionList;
            actionPickerBottomSheet.actionListener = this.actionListener;
            return actionPickerBottomSheet;
        }

        public ActionPickerBottomSheet show(FragmentManager fragmentManager, String str) {
            ActionPickerBottomSheet build = build();
            build.show(fragmentManager, str);
            return build;
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return super.onCreateDialog(bundle);
    }

    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.bottom_sheet_actions_view, null);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        dialog.setContentView(inflate);
        this.behavior = ((LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        Behavior behavior2 = this.behavior;
        if (behavior2 != null && (behavior2 instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior2).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
            ((BottomSheetBehavior) this.behavior).setHideable(false);
        }
        this.adapter = new BottomSheetAdapter(this.actionList);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(this.adapter);
        ItemClickSupport.addTo(this.recyclerView).setOnItemClickListener(new OnItemClickListener() {
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                if (ActionPickerBottomSheet.this.actionListener != null) {
                    ActionPickerBottomSheet.this.actionListener.actionSelected(i, (ActionModel) ActionPickerBottomSheet.this.actionList.get(i));
                }
            }
        });
    }
}
