package com.monday.dsalexan.friday.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import com.monday.dsalexan.friday.R;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DB_NAME, null, DatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        String addConstants;

        // BOOKS
        createTable = "CREATE TABLE " + DatabaseContract.BooksTable.TABLE + " ( " +
                DatabaseContract.BooksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.BooksTable.COL_BOOKS_TITLE + " TEXT NOT NULL" + ");";
        db.execSQL(createTable);

        addConstants = "INSERT INTO " + DatabaseContract.BooksTable.TABLE + "(" +
                DatabaseContract.BooksTable.COL_BOOKS_TITLE + ") VALUES (" +
                "\"Personal\"" + ");";
        db.execSQL(addConstants);

        // CATEGORIES
        createTable = "CREATE TABLE " + DatabaseContract.CategoriesTable.TABLE + " ( " +
                DatabaseContract.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.CategoriesTable.COL_CATEGORY_TITLE + " TEXT NOT NULL," +
                DatabaseContract.CategoriesTable.COL_CATEGORY_PRIO + " INTEGER NOT NULL," +
                DatabaseContract.CategoriesTable.COL_CATEGORY_BOOK + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + DatabaseContract.CategoriesTable.COL_CATEGORY_BOOK + ") REFERENCES " + DatabaseContract.BooksTable.TABLE + "(" + DatabaseContract.BooksTable._ID + ")"+ ");";
        db.execSQL(createTable);

        addConstants = "INSERT INTO " + DatabaseContract.CategoriesTable.TABLE + "(" +
                DatabaseContract.CategoriesTable.COL_CATEGORY_TITLE + "," +
                DatabaseContract.CategoriesTable.COL_CATEGORY_PRIO + "," +
                DatabaseContract.CategoriesTable.COL_CATEGORY_BOOK + ") VALUES (" +
                "\"Urgent and Important\", 0, 0" + ");";
        db.execSQL(addConstants);

        // TASKS
        createTable = "CREATE TABLE " + DatabaseContract.TasksTable.TABLE + " ( " +
                DatabaseContract.TasksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.TasksTable.COL_TASK_TITLE + " TEXT NOT NULL," +
                DatabaseContract.TasksTable.COL_TASK_DATE + " TEXT NOT NULL," +
                DatabaseContract.TasksTable.COL_TASK_IMAGE + " TEXT," +
                DatabaseContract.TasksTable.COL_TASK_AUDIO + " TEXT," +
                DatabaseContract.TasksTable.COL_TASK_NOTE + " TEXT," +
                DatabaseContract.TasksTable.COL_TASK_LOCATION + " TEXT," +
                DatabaseContract.TasksTable.COL_TASK_CATEGORY + " INTEGER NOT NULL," +
                DatabaseContract.TasksTable.COL_TASK_STATUS + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + DatabaseContract.TasksTable.COL_TASK_CATEGORY + ") REFERENCES " + DatabaseContract.CategoriesTable.TABLE + "(" + DatabaseContract.CategoriesTable._ID + ")," +
                "FOREIGN KEY(" + DatabaseContract.TasksTable.COL_TASK_STATUS + ") REFERENCES " + DatabaseContract.TaskStatusTable.TABLE + "(" + DatabaseContract.TaskStatusTable._ID + ")"+ ");";
        db.execSQL(createTable);

        // TASK STATUS
        createTable = "CREATE TABLE " + DatabaseContract.TaskStatusTable.TABLE + " ( " +
                DatabaseContract.TaskStatusTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.TaskStatusTable.COL_TASKSTATUS_TITLE + " TEXT NOT NULL" + ");";
        db.execSQL(createTable);

        addConstants = "INSERT INTO " + DatabaseContract.TaskStatusTable.TABLE + "(" +
                DatabaseContract.TaskStatusTable.COL_TASKSTATUS_TITLE + ") VALUES (" +
                "\"ACTIVE\"" + ");";
        db.execSQL(addConstants);

        addConstants = "INSERT INTO " + DatabaseContract.TaskStatusTable.TABLE + "(" +
                DatabaseContract.TaskStatusTable.COL_TASKSTATUS_TITLE + ") VALUES (" +
                "\"EXPIRED\"" + ");";
        db.execSQL(addConstants);

        addConstants = "INSERT INTO " + DatabaseContract.TaskStatusTable.TABLE + "(" +
                DatabaseContract.TaskStatusTable.COL_TASKSTATUS_TITLE + ") VALUES (" +
                "\"REMOVED\"" + ");";
        db.execSQL(addConstants);

        addConstants = "INSERT INTO " + DatabaseContract.TaskStatusTable.TABLE + "(" +
                DatabaseContract.TaskStatusTable.COL_TASKSTATUS_TITLE + ") VALUES (" +
                "\"FOCUSED\"" + ");";
        db.execSQL(addConstants);/**/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TasksTable.TABLE);
        onCreate(db);
    }

    /* INSERTS */
    public void addTask(String title, String date, int category, int status){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TasksTable.COL_TASK_TITLE, title);
        values.put(DatabaseContract.TasksTable.COL_TASK_DATE, date);
        values.put(DatabaseContract.TasksTable.COL_TASK_CATEGORY, category);
        values.put(DatabaseContract.TasksTable.COL_TASK_STATUS, status);

        db.insertWithOnConflict(DatabaseContract.TasksTable.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_ABORT);
        db.close();
    }

    /* DELETES */
    public void deleteTask(String title){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DatabaseContract.TasksTable.TABLE,
                DatabaseContract.TasksTable.COL_TASK_TITLE + " = ?",
                new String[]{title});
        db.close();
    }

    /* FINDS */
    public ArrayList<String> listAllTasks(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DatabaseContract.TasksTable.TABLE,
                new String[]{DatabaseContract.TasksTable._ID, DatabaseContract.TasksTable.COL_TASK_TITLE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DatabaseContract.TasksTable.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        cursor.close();
        db.close();

        return taskList;
    }
}