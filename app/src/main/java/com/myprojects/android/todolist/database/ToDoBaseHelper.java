package com.myprojects.android.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.myprojects.android.todolist.ToDo;
import com.myprojects.android.todolist.database.ToDoDbSchema.ToDoTable;

public class ToDoBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "toDoBase.db";

    public ToDoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ToDoTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ToDoTable.Cols.UUID + ", " +
                ToDoTable.Cols.TITLE + ", " +
                ToDoTable.Cols.DESCRIPTION + ", " +
                ToDoTable.Cols.SOLVED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
