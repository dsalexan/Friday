package com.monday.dsalexan.friday.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import com.monday.dsalexan.friday.R;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DB_NAME, null, DatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + DatabaseContract.TasksTable.TABLE + " ( " +
                DatabaseContract.TasksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.TasksTable.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TasksTable.TABLE);
        onCreate(db);
    }

    public void insert(String task_title){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TasksTable.COL_TASK_TITLE, task_title);
        db.insertWithOnConflict(DatabaseContract.TasksTable.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void delete(String task_title){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DatabaseContract.TasksTable.TABLE,
                DatabaseContract.TasksTable.COL_TASK_TITLE + " = ?",
                new String[]{task_title});
        db.close();
    }

    public ArrayList<String> listAll(){
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