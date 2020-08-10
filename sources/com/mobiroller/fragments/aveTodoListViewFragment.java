package com.mobiroller.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.adapters.TodoListAdapter;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TodoModel;
import com.mobiroller.models.TodoModel.Color;
import com.mobiroller.util.TodoUtil;
import com.mobiroller.views.SimpleDividerItemDecoration;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener.OnSwipeOptionsClickListener;
import java.util.ArrayList;

public class aveTodoListViewFragment extends BaseModuleFragment {
    /* access modifiers changed from: private */
    public TodoListAdapter adapter;
    Color checkedColor;
    @BindView(2131362333)
    RelativeLayout emptyView;
    @BindView(2131362379)
    RelativeLayout fabLayout;
    @BindView(2131362373)
    FloatingActionButton fab_add;
    /* access modifiers changed from: private */
    public LinearLayoutManager linearLayoutManager;
    @BindView(2131362651)
    LinearLayout mainview;
    private RecyclerTouchListener onTouchListener;
    View positiveAction;
    String taskTitle;
    /* access modifiers changed from: private */
    public ArrayList<TodoModel> tasks;
    @BindView(2131363266)
    ImageView todoEmptyImage;
    @BindView(2131363267)
    TextView todoEmptyText;
    @BindView(2131362628)
    RecyclerView todoListView;
    @BindView(2131363268)
    FrameLayout todo_frame_layout;
    @BindView(2131363269)
    RelativeLayout todo_layout;
    Unbinder unbinder;
    private View view;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        this.view = layoutInflater.inflate(R.layout.layout_todo, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, this.view);
        hideToolbar((Toolbar) this.view.findViewById(R.id.toolbar_top));
        this.tasks = new ArrayList<>();
        ArrayList<TodoModel> db = TodoUtil.getDb(getActivity(), this.screenId);
        if (db != null) {
            this.tasks = db;
        }
        this.fab_add.setBackgroundTintList(ColorStateList.valueOf(this.sharedPrefHelper.getActionBarColor()));
        this.fab_add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                aveTodoListViewFragment.this.addTask();
            }
        });
        this.linearLayoutManager = new LinearLayoutManager(getActivity());
        this.adapter = new TodoListAdapter(getActivity(), this.tasks, this.screenId);
        this.todoListView.setAdapter(this.adapter);
        this.todoListView.setLayoutManager(this.linearLayoutManager);
        this.todoListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), R.drawable.todo_line));
        this.onTouchListener = new RecyclerTouchListener(getActivity(), this.todoListView);
        this.onTouchListener.setIndependentViews(Integer.valueOf(R.id.backgroundView)).setSwipeOptionViews(Integer.valueOf(R.id.backgroundView)).setSwipeable(R.id.listwrapper, R.id.backgroundView, new OnSwipeOptionsClickListener() {
            public void onSwipeOptionClicked(int i, final int i2) {
                if (i == R.id.backgroundView) {
                    final TodoModel todoModel = (TodoModel) aveTodoListViewFragment.this.adapter.getList().get(i2);
                    new Builder(aveTodoListViewFragment.this.getActivity()).content((int) R.string.are_you_sure_delete).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            TodoUtil.removeFromList(todoModel, aveTodoListViewFragment.this.getActivity(), aveTodoListViewFragment.this.screenId);
                            Toast.makeText(aveTodoListViewFragment.this.getActivity(), aveTodoListViewFragment.this.getString(R.string.todo_delete), 0).show();
                            aveTodoListViewFragment.this.adapter.removeItem(i2);
                            if (aveTodoListViewFragment.this.adapter.getItemCount() == 0) {
                                aveTodoListViewFragment.this.checkIsEmpty();
                            }
                        }
                    }).show();
                }
            }
        });
        checkIsEmpty();
        return this.view;
    }

    public void addTask() {
        this.taskTitle = "";
        this.checkedColor = Color.YELLOW;
        final FragmentActivity activity = getActivity();
        String str = "#2196F3";
        MaterialDialog build = new Builder(getActivity()).title((int) R.string.action_add_task).titleColor(ContextCompat.getColor(getActivity(), R.color.todo_dialog_title_color)).icon(ContextCompat.getDrawable(getActivity(), R.drawable.icon_alert_add)).customView((int) R.layout.addtodo, false).negativeText((int) R.string.cancel).positiveText((int) R.string.action_add).negativeColor(android.graphics.Color.parseColor(str)).positiveColor(android.graphics.Color.parseColor(str)).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                TodoModel todoModel = new TodoModel();
                todoModel.setText(aveTodoListViewFragment.this.taskTitle);
                todoModel.setColor(aveTodoListViewFragment.this.checkedColor);
                todoModel.setChecked(false);
                Toast.makeText(aveTodoListViewFragment.this.getActivity(), aveTodoListViewFragment.this.getString(R.string.note_added), 0).show();
                aveTodoListViewFragment.this.adapter.insert(todoModel);
                View findViewByIndex = TodoUtil.findViewByIndex(aveTodoListViewFragment.this.tasks.indexOf(todoModel), aveTodoListViewFragment.this.todoListView, aveTodoListViewFragment.this.linearLayoutManager);
                aveTodoListViewFragment.this.checkIsEmpty();
                Animation loadAnimation = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
                if (findViewByIndex != null) {
                    findViewByIndex.startAnimation(loadAnimation);
                }
            }
        }).build();
        this.positiveAction = build.getActionButton(DialogAction.POSITIVE);
        ((EditText) build.getCustomView().findViewById(R.id.task_title)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                aveTodoListViewFragment.this.taskTitle = charSequence.toString();
                aveTodoListViewFragment.this.positiveAction.setEnabled(aveTodoListViewFragment.this.taskTitle.trim().length() > 0 && aveTodoListViewFragment.this.checkedColor != null);
            }
        });
        ((RadioGroup) build.getCustomView().findViewById(R.id.task_priority)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.findViewById(i)).isChecked()) {
                    if (i == R.id.task_priority_red) {
                        aveTodoListViewFragment.this.checkedColor = Color.RED;
                    } else if (i == R.id.task_priority_yellow) {
                        aveTodoListViewFragment.this.checkedColor = Color.YELLOW;
                    } else if (i == R.id.task_priority_green) {
                        aveTodoListViewFragment.this.checkedColor = Color.GREEN;
                    } else {
                        aveTodoListViewFragment.this.checkedColor = null;
                    }
                    aveTodoListViewFragment.this.positiveAction.setEnabled(aveTodoListViewFragment.this.taskTitle.trim().length() > 0 && aveTodoListViewFragment.this.checkedColor != null);
                }
            }
        });
        build.getWindow().setSoftInputMode(4);
        build.show();
        this.positiveAction.setEnabled(false);
    }

    public void checkIsEmpty() {
        if (this.adapter.getItemCount() == 0) {
            setEmptyVisible();
        } else {
            setListVisible();
        }
    }

    public void setEmptyVisible() {
        ((TextView) this.view.findViewById(R.id.todo_empty_text)).setTextColor(getStatusBarColor(this.sharedPrefHelper.getActionBarColor()));
        this.todoListView.setVisibility(8);
        this.emptyView.setVisibility(0);
        this.todo_layout.setBackgroundColor(getResources().getColor(R.color.empty_recycler_background_color));
    }

    public void setListVisible() {
        this.todoListView.setVisibility(0);
        this.emptyView.setVisibility(8);
        this.todo_layout.setBackgroundColor(getResources().getColor(R.color.filled_recycler_background_color));
    }

    public void onResume() {
        super.onResume();
        this.todoListView.addOnItemTouchListener(this.onTouchListener);
        if (this.todo_layout != null) {
            this.bannerHelper.addBannerAd(this.todo_layout, this.todo_frame_layout);
        }
    }

    public void onPause() {
        super.onPause();
        this.todoListView.removeOnItemTouchListener(this.onTouchListener);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
