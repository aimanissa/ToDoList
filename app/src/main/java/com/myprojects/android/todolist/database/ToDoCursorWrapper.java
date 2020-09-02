package com.myprojects.android.todolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.myprojects.android.todolist.ToDo;
import com.myprojects.android.todolist.database.ToDoDbSchema.ToDoTable;

import java.util.UUID;

public class ToDoCursorWrapper extends CursorWrapper {

    public ToDoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ToDo getTodo() {
        String uuidString = getString(getColumnIndex(ToDoTable.Cols.UUID));
        String title = getString(getColumnIndex(ToDoTable.Cols.TITLE));
        String description = getString(getColumnIndex(ToDoTable.Cols.DESCRIPTION));
        int isSolved = getInt(getColumnIndex(ToDoTable.Cols.SOLVED));

        ToDo toDo = new ToDo(UUID.fromString(uuidString));
        toDo.setTitle(title);
        toDo.setDescription(description);
        toDo.setSolved(isSolved != 0);
        return toDo;
    }
}
