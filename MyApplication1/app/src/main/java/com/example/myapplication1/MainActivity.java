package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewWork, textViewPersonal, textViewCompleted, textViewHeld;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWork = findViewById(R.id.textView2);  // المربع الخاص بـ Work
        textViewPersonal = findViewById(R.id.textView3);
        textViewCompleted = findViewById(R.id.textView);
        textViewHeld = findViewById(R.id.textView4);

        dbHelper = new TaskDatabaseHelper(this);

        // تحديث العدادات
        int workCount = dbHelper.getTaskCountByType("Work");
        int personalCount = dbHelper.getTaskCountByType("Personal");
        int completedCount = dbHelper.getCompletedTaskCount();
        int heldCount = dbHelper.getHeldTaskCount();

        textViewWork.setText(String.valueOf(workCount));
        textViewPersonal.setText(String.valueOf(personalCount));
        textViewCompleted.setText(String.valueOf(completedCount));
        textViewHeld.setText(String.valueOf(heldCount));
        View buttonOpenTaskList = findViewById(R.id.button_open_task_list);

        // إضافة OnClickListener للزر
        buttonOpenTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // الانتقال إلى TaskListActivity
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });
    }
}
