package com.myprojects.android.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myprojects.android.todolist.database.ToDoBaseHelper;
import com.myprojects.android.todolist.database.ToDoCursorWrapper;
import com.myprojects.android.todolist.database.ToDoDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.myprojects.android.todolist.database.ToDoDbSchema.*;

public class ToDoLab {

    private static ToDoLab sToDoLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static ToDoLab get(Context context) {
        if (sToDoLab == null) {
            sToDoLab = new ToDoLab(context);
        }
        return sToDoLab;
    }

    private ToDoLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ToDoBaseHelper(mContext).getWritableDatabase();
    }

    public void addToDo(ToDo toDo) {
        ContentValues values = getContentValues(toDo);
        mDatabase.insert(ToDoTable.NAME, null, values);
    }

    public void deleteToDo(ToDo toDo) {
        String uuidString = toDo.getId().toString();
        ContentValues values = getContentValues(toDo);
        mDatabase.delete(ToDoTable.NAME,
                ToDoTable.Cols.UUID + "=?",
                new String[]{uuidString});
    }

    public void updateToDo(ToDo toDo) {
        String uuidString = toDo.getId().toString();
        ContentValues values = getContentValues(toDo);
        mDatabase.update(ToDoTable.NAME, values,
                ToDoTable.Cols.UUID + "=?",
                new String[]{uuidString});
    }

    public List<ToDo> getToDoList() {
        List<ToDo> toDoList = new ArrayList<>();

        ToDoCursorWrapper cursor = queryToDo(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                toDoList.add(cursor.getTodo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return toDoList;
    }

    public ToDo getToDo(UUID id) {
        ToDoCursorWrapper cursor = queryToDo(
                ToDoTable.Cols.UUID + "=?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTodo();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(ToDo toDo) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, toDo.getPhotoFilename());
    }

    private ToDoCursorWrapper queryToDo(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ToDoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ToDoCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(ToDo toDo) {
        ContentValues values = new ContentValues();
        values.put(ToDoTable.Cols.UUID, toDo.getId().toString());
        values.put(ToDoTable.Cols.TITLE, toDo.getTitle());
        values.put(ToDoTable.Cols.DESCRIPTION, toDo.getDescription());
        values.put(ToDoTable.Cols.SOLVED, toDo.isSolved() ? 1 : 0);
        return values;
    }
}
