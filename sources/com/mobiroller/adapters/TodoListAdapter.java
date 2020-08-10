package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TodoModel;
import com.mobiroller.models.TodoModel.Color;
import com.mobiroller.util.TodoUtil;
import java.util.ArrayList;

public class TodoListAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public Color checkedColor;
    public Context context;
    /* access modifiers changed from: private */
    public ArrayList<TodoModel> listData;
    /* access modifiers changed from: private */
    public View positiveAction;
    private String screenId;
    /* access modifiers changed from: private */
    public String taskTitle;

    static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        ToggleButton buttonView;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.buttonView = (ToggleButton) view.findViewById(R.id.listbutton);
            this.textView = (TextView) view.findViewById(R.id.listtext);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public TodoListAdapter(Activity activity, ArrayList arrayList, String str) {
        this.context = activity;
        this.listData = arrayList;
        this.screenId = str;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_todo, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.buttonView.setTag(this.listData.get(i));
        viewHolder.textView.setTag(this.listData.get(i));
        viewHolder.buttonView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TodoListAdapter.this.completeTask(i);
            }
        });
        if (((TodoModel) this.listData.get(i)).getColor() == Color.RED) {
            viewHolder.buttonView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_selector_red, 0, 0, 0);
        } else if (((TodoModel) this.listData.get(i)).getColor() == Color.YELLOW) {
            viewHolder.buttonView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_selector_yellow, 0, 0, 0);
        } else if (((TodoModel) this.listData.get(i)).getColor() == Color.GREEN) {
            viewHolder.buttonView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_selector_green, 0, 0, 0);
        }
        viewHolder.buttonView.setChecked(((TodoModel) this.listData.get(i)).getChecked());
        viewHolder.textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TodoListAdapter.this.updateTask(i);
            }
        });
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        ToggleButton toggleButton = viewHolder.buttonView;
        double d = (double) (displayMetrics.density * 16.0f);
        Double.isNaN(d);
        toggleButton.setCompoundDrawablePadding((int) (d + 0.5d));
        viewHolder.textView.setText(((TodoModel) this.listData.get(i)).getText());
        if (((TodoModel) this.listData.get(i)).getChecked()) {
            viewHolder.textView.setTypeface(null, 0);
            viewHolder.textView.setPaintFlags(viewHolder.buttonView.getPaintFlags() | 16);
            viewHolder.textView.setTextColor(-7829368);
            return;
        }
        viewHolder.textView.setPaintFlags(viewHolder.buttonView.getPaintFlags() & -17);
        viewHolder.textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textView.setTypeface(null, 1);
    }

    public int getItemCount() {
        return this.listData.size();
    }

    public void completeTask(int i) {
        if (i < 0) {
            System.out.println("Weird index?");
            return;
        }
        ((TodoModel) this.listData.get(i)).toggleChecked();
        this.listData = new ArrayList<>(TodoUtil.sort((TodoModel) this.listData.get(i), this.listData));
        updateList(this.listData);
    }

    public void updateList(ArrayList<TodoModel> arrayList) {
        TodoUtil.updateDb(arrayList, this.context, this.screenId);
        this.listData = arrayList;
        notifyDataSetChanged();
    }

    public void updateTask(int i) {
        final TodoModel todoModel = (TodoModel) this.listData.get(i);
        this.taskTitle = todoModel.getText();
        this.checkedColor = todoModel.getColor();
        final Color color = todoModel.getColor();
        if (i < 0) {
            System.out.println("Weird index?");
            return;
        }
        String str = "#2196F3";
        MaterialDialog build = new Builder(this.context).title((int) R.string.action_update_task).customView((int) R.layout.addtodo, false).negativeText((int) R.string.cancel).positiveText((int) R.string.action_update).negativeColor(android.graphics.Color.parseColor(str)).positiveColor(android.graphics.Color.parseColor(str)).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                todoModel.setText(TodoListAdapter.this.taskTitle);
                todoModel.setColor(TodoListAdapter.this.checkedColor);
                if (!color.equals(TodoListAdapter.this.checkedColor)) {
                    TodoListAdapter todoListAdapter = TodoListAdapter.this;
                    todoListAdapter.listData = new ArrayList(TodoUtil.sort(todoModel, todoListAdapter.listData));
                }
                Toast.makeText(TodoListAdapter.this.context, TodoListAdapter.this.context.getString(R.string.todo_updated), 0).show();
                TodoListAdapter todoListAdapter2 = TodoListAdapter.this;
                todoListAdapter2.updateList(todoListAdapter2.listData);
            }
        }).build();
        this.positiveAction = build.getActionButton(DialogAction.POSITIVE);
        this.positiveAction.setEnabled(true);
        EditText editText = (EditText) build.getCustomView().findViewById(R.id.task_title);
        editText.append(this.taskTitle);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TodoListAdapter.this.taskTitle = charSequence.toString();
                TodoListAdapter.this.positiveAction.setEnabled(TodoListAdapter.this.taskTitle.trim().length() > 0 && TodoListAdapter.this.checkedColor != null);
            }
        });
        RadioGroup radioGroup = (RadioGroup) build.getCustomView().findViewById(R.id.task_priority);
        if (this.checkedColor == Color.RED) {
            radioGroup.check(R.id.task_priority_red);
        } else if (this.checkedColor == Color.YELLOW) {
            radioGroup.check(R.id.task_priority_yellow);
        } else if (this.checkedColor == Color.GREEN) {
            radioGroup.check(R.id.task_priority_green);
        }
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.findViewById(i)).isChecked()) {
                    if (i == R.id.task_priority_red) {
                        TodoListAdapter.this.checkedColor = Color.RED;
                    } else if (i == R.id.task_priority_yellow) {
                        TodoListAdapter.this.checkedColor = Color.YELLOW;
                    } else if (i == R.id.task_priority_green) {
                        TodoListAdapter.this.checkedColor = Color.GREEN;
                    } else {
                        TodoListAdapter.this.checkedColor = null;
                    }
                    TodoListAdapter.this.positiveAction.setEnabled(TodoListAdapter.this.taskTitle.trim().length() > 0 && TodoListAdapter.this.checkedColor != null);
                }
            }
        });
        build.getWindow().setSoftInputMode(4);
        build.show();
        this.positiveAction.setEnabled(false);
    }

    public void insert(TodoModel todoModel) {
        this.listData.add(todoModel);
        updateList(new ArrayList(TodoUtil.sort(todoModel, this.listData)));
    }

    public ArrayList<TodoModel> getList() {
        return this.listData;
    }

    public void removeItem(int i) {
        this.listData.remove(i);
        notifyItemRemoved(i);
        notifyDataSetChanged();
    }
}
