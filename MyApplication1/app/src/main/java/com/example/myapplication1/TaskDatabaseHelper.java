package com.example.myapplication1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_database";
    private static final int DATABASE_VERSION = 1;

    // اسم الجدول
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IS_COMPLETED = "is_completed";
    public static final String COLUMN_IS_HELD = "is_held";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_IS_COMPLETED + " INTEGER,"
                + COLUMN_IS_HELD + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // إضافة مهمة جديدة
    public void addTask(String description, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_IS_COMPLETED, 0); // 0 تعني أن المهمة غير مكتملة
        values.put(COLUMN_IS_HELD, 0); // 0 تعني أن المهمة ليست معلقة
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // الحصول على عدد المهام حسب النوع
    public int getTaskCountByType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_TYPE + " = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{type});
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // الحصول على عدد المهام المكتملة
    public int getCompletedTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_IS_COMPLETED + " = 1";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // الحصول على عدد المهام المعلقة
    public int getHeldTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_IS_HELD + " = 1";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // الحصول على جميع المهام
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);

        // تحقق من أن الـ Cursor يحتوي على الأعمدة المطلوبة
        if (cursor != null && cursor.moveToFirst()) {
            int columnIdIndex = cursor.getColumnIndex(COLUMN_ID);
            int columnDescriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int columnTypeIndex = cursor.getColumnIndex(COLUMN_TYPE);
            int columnIsCompletedIndex = cursor.getColumnIndex(COLUMN_IS_COMPLETED);
            int columnIsHeldIndex = cursor.getColumnIndex(COLUMN_IS_HELD);

            // تحقق من أن جميع الأعمدة موجودة
            if (columnIdIndex == -1 || columnDescriptionIndex == -1 || columnTypeIndex == -1 ||
                    columnIsCompletedIndex == -1 || columnIsHeldIndex == -1) {
                cursor.close();
                return taskList; // أو يمكنك التعامل مع هذه الحالة بطريقة مختلفة (مثل إظهار رسالة خطأ)
            }

            do {
                int id = cursor.getInt(columnIdIndex);
                String description = cursor.getString(columnDescriptionIndex);
                String type = cursor.getString(columnTypeIndex);
                boolean isCompleted = cursor.getInt(columnIsCompletedIndex) == 1; // تحويل من int إلى boolean
                boolean isHeld = cursor.getInt(columnIsHeldIndex) == 1; // تحويل من int إلى boolean

                // التعديل هنا
                Task task = new Task(id, description, isCompleted == false, isHeld == true, type);


                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }
    // تحديث حالة المهمة (Completed أو Held)
    public void updateTaskStatus(int taskId, boolean isCompleted, boolean isHeld) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_COMPLETED, isCompleted ? 1 : 0);
        values.put(COLUMN_IS_HELD, isHeld ? 1 : 0);

        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    // حذف المهمة من قاعدة البيانات
    public void deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }

}
