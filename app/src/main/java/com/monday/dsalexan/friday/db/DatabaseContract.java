package com.monday.dsalexan.friday.db;

import android.provider.BaseColumns;

/**
 * Created by Danilo on 01/07/2017.
 */

public class DatabaseContract {
    public static final String DB_NAME = "com.monday.dsalexan.friday.db";
    public static final int DB_VERSION = 1;

    private DatabaseContract() {}

    public class TasksTable implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}