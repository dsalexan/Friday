package com.monday.dsalexan.friday;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.monday.dsalexan.friday.db.DatabaseContract;
import com.monday.dsalexan.friday.db.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseHelper DATABASE_HELPER;

    private ListView mTaskListView;
    private TaskArrayAdapter taskAdapter;

    private ExpandableListView tasks_list;
    private ExpandableTaskArrayAdapter expandableTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DATABASE_HELPER = new DatabaseHelper(this);

        //mTaskListView = (ListView)findViewById(R.id.list_todo);
        tasks_list = (ExpandableListView)findViewById(R.id.tasks_list);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d(TAG, "Add a new task");
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                DATABASE_HELPER.addTask(task, "2017-07-06 20:00:00.000", 1, Task.Status.ACTIVE.getValue());
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(View view) {
        Log.d(TAG, "Removing a task");
        TextView taskTextView = (TextView)((View)view.getParent()).findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        DATABASE_HELPER.deleteTask(task);
        updateUI();
    }

    public void addReminder(View view){
        TextView taskTextView = (TextView)((View)view.getParent()).findViewById(R.id.task_title);
        final Integer taskId = (Integer)taskTextView.getTag();

        Log.d(TAG, "Add a new reminder");
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new reminder")
                .setView(taskEditText)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date = String.valueOf(taskEditText.getText());
                        DATABASE_HELPER.addReminder(taskId, date, null, null);
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void updateUI() {
        final ArrayList<Task> taskList = DATABASE_HELPER.listAllTasks();

        if (expandableTaskAdapter == null) {
            expandableTaskAdapter = new ExpandableTaskArrayAdapter(this,
                    R.layout.item_todo,
                    R.layout.item_reminder,
                    R.id.task_title,
                    R.id.reminder_date,
                    taskList);

            tasks_list.setAdapter(expandableTaskAdapter);

            /*taskAdapter = new TaskArrayAdapter(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    R.id.task_reminders,
                    R.layout.item_reminder,
                    taskList);
            mTaskListView.setAdapter(taskAdapter);*/
        } else {
            /*taskAdapter.clear();
            taskAdapter.addAll(taskList);
            taskAdapter.notifyDataSetChanged();*/
            expandableTaskAdapter.clear();
            expandableTaskAdapter.addAll(taskList);
            expandableTaskAdapter.notifyDataSetChanged();
        }
    }
}
