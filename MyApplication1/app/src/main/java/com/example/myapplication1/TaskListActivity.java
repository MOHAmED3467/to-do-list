package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list); // تأكد من أن اسم الواجهة صحيح

        // ربط RecyclerView بـ XML
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        // ربط قاعدة البيانات
        dbHelper = new TaskDatabaseHelper(this);

        // استدعاء دالة لتحميل المهام من قاعدة البيانات
        loadTasks();

        // ربط الزر AddTask
        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }


    // دالة لتحميل المهام من قاعدة البيانات
    private void loadTasks() {
        List<Task> taskList = dbHelper.getAllTasks();

        if (taskList != null && !taskList.isEmpty()) {
            taskAdapter = new TaskAdapter(this, taskList, dbHelper); // تمرير dbHelper
            recyclerViewTasks.setAdapter(taskAdapter);
        } else {
            Toast.makeText(this, "No tasks found!", Toast.LENGTH_SHORT).show();
        }
    }

}
