package com.example.myapplication1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextDescription;
    private Spinner spinnerTaskType;
    private Button buttonAddTask;
    private TaskDatabaseHelper dbHelper; // إضافة المرجعية لقاعدة البيانات

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // ربط العناصر بالـ XML
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerTaskType = findViewById(R.id.spinnerTaskType);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        // إعداد قاعدة البيانات
        dbHelper = new TaskDatabaseHelper(this);

        // إعداد الـ Spinner
        String[] taskTypes = {"Work", "Personal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskType.setAdapter(adapter);

        // إعداد الـ OnClickListener للزر
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editTextDescription.getText().toString();
                String taskType = spinnerTaskType.getSelectedItem().toString();

                if (description.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Please enter a task description", Toast.LENGTH_SHORT).show();
                    return;
                }

                // إضافة المهمة إلى قاعدة البيانات
                dbHelper.addTask(description, taskType);

                // عرض رسالة
                Toast.makeText(AddTaskActivity.this, "Task Added: " + description + " (" + taskType + ")", Toast.LENGTH_SHORT).show();

                // العودة إلى الشاشة الرئيسية أو تحديث البيانات
                finish();
            }
        });
        
    }
}
