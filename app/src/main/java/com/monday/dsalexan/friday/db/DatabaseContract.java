package com.monday.dsalexan.friday.db;

import android.provider.BaseColumns;

/**
 * Created by Danilo on 01/07/2017.
 */

public class DatabaseContract {
    public static final String DB_NAME = "com.monday.dsalexan.friday.db";
    public static final int DB_VERSION = 7;

    private DatabaseContract() {}

    public class TasksTable implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DATE = "date"; //ISO8601 string = "YYYY-MM-DD HH:MM:SS.SSS"
        public static final String COL_TASK_IMAGE = "image";
        public static final String COL_TASK_AUDIO = "audio";
        public static final String COL_TASK_NOTE = "note";
        public static final String COL_TASK_LOCATION = "location";
        public static final String COL_TASK_CATEGORY = "category";
        public static final String COL_TASK_STATUS = "status";
    }

    public class TaskStatusTable implements BaseColumns{
        public static final String TABLE = "task_status";

        public static final String COL_TASKSTATUS_TITLE = "title";
    }

    public class BooksTable implements BaseColumns{
        public static final String TABLE = "books";

        public static final String COL_BOOKS_TITLE = "title";
    }

    public class CategoriesTable implements BaseColumns{
        public static final String TABLE = "categories";

        public static final String COL_CATEGORY_TITLE = "title";
        public static final String COL_CATEGORY_PRIO = "prio";
        public static final String COL_CATEGORY_BOOK = "book";
    }


}