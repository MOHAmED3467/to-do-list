package com.example.myapplication1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> taskList;
    private TaskDatabaseHelper dbHelper;

    public TaskAdapter(Context context, List<Task> taskList, TaskDatabaseHelper dbHelper) {
        this.context = context;
        this.taskList = taskList;
        this.dbHelper = dbHelper;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflating the task_item layout for each item in the RecyclerView
        View itemView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.descriptionTextView.setText(task.getDescription());
        holder.typeTextView.setText(task.getType());
        holder.completedTextView.setText(task.isCompleted() ? "Not Completed" : "Completed");
        holder.heldTextView.setText(task.isHolded() ? "Held" : "Not Held");

        holder.completedButton.setOnClickListener(v -> {
            task.setCompleted(true); // تحديث الحالة في الكائن Task
            dbHelper.updateTaskStatus(task.getId(), true, task.isHolded()); // تحديث قاعدة البيانات
            notifyItemChanged(position); // تحديث العنصر المحدد فقط
        });

        holder.heldButton.setOnClickListener(v -> {
            task.setHolded(true); // تحديث الحالة في الكائن Task
            dbHelper.updateTaskStatus(task.getId(), task.isCompleted(), true); // تحديث قاعدة البيانات
            notifyItemChanged(position); // تحديث العنصر المحدد فقط
        });


        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteTask(task.getId()); // حذف المهمة من قاعدة البيانات
            taskList.remove(position); // حذف المهمة من القائمة
            notifyItemRemoved(position); // إزالة العنصر المحدد
            notifyItemRangeChanged(position, taskList.size()); // تحديث باقي العناصر
        });

    }


    @Override
    public int getItemCount() {
        return taskList.size(); // Return the number of items in the list
    }

    // ViewHolder for each task item
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView descriptionTextView;
        public TextView typeTextView;
        public TextView completedTextView;
        public TextView heldTextView;
        public Button completedButton, heldButton, deleteButton;

        public TaskViewHolder(View view) {
            super(view);

            // Initialize the views from the task_item layout
            descriptionTextView = view.findViewById(R.id.textViewDescription);
            typeTextView = view.findViewById(R.id.textViewType);
            completedTextView = view.findViewById(R.id.textViewCompleted);
            heldTextView = view.findViewById(R.id.textViewHeld);
            completedButton = view.findViewById(R.id.buttonCompleted);
            heldButton = view.findViewById(R.id.buttonHeld);
            deleteButton = view.findViewById(R.id.buttonDelete);
        }
    }
}
