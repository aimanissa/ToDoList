package com.myprojects.android.todolist.database;

public class ToDoDbSchema {

    public static final class ToDoTable {
        public static final String NAME = "todo";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String SOLVED = "solved";
        }
    }
}
