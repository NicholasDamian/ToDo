package com.example.nicholashall.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nicholashall on 10/28/16.
 */

public class ToDoDBAdapter {
    private static final String DATABASE_NAME = "todobook.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TODO_TABLE = "todo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    private String[] allColumns = { COLUMN_ID, COLUMN_NAME, COLUMN_MESSAGE, COLUMN_CATEGORY, COLUMN_DATE};

    public static final String CREATE_TABLE_TODO = "create table " + TODO_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_DATE + ");";

    private SQLiteDatabase sqlDB;
    private Context context;
    private TodoDbHelper todoDbHelper;

    public ToDoDBAdapter(Context ctx){
        context = ctx;
    }

    public ToDoDBAdapter open() throws android.database.SQLException {
        todoDbHelper = new TodoDbHelper(context);
        sqlDB = todoDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        todoDbHelper.close();
    }

    public ToDo createToDo(String name, String message, ToDo.Category category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_CATEGORY, category.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+ "");


        long insertId = sqlDB.insert(TODO_TABLE, null, values);

        Cursor cursor = sqlDB.query(TODO_TABLE,
                allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        ToDo newToDo = cursorToDo(cursor);
        close();
        return newToDo;
    }



    public long deleteToDo(long idToDelete){
        return sqlDB.delete(TODO_TABLE,COLUMN_ID + " = " + idToDelete,null);
    }


    public long UpdateToDo(long idToUpdate, String newName, String newMessage, ToDo.Category newCategory){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_MESSAGE, newMessage);
        values.put(COLUMN_CATEGORY, newCategory.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+ "");

        return sqlDB.update(TODO_TABLE, values, COLUMN_ID + "==" + idToUpdate, null);

    }



    public ArrayList<ToDo> getAllToDos(){
        ArrayList<ToDo> toDos= new ArrayList<>();

//        grab all of the information in our database for the todos in it
        Cursor cursor = sqlDB.query(TODO_TABLE, allColumns,null,null,null,null,null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            ToDo toDo = cursorToDo(cursor);
            toDos.add(toDo);
        }

        cursor.close();

        return toDos;
    }


    private ToDo cursorToDo(Cursor cursor){
        ToDo newToDo = new ToDo( cursor.getString(1), cursor.getString(2),
                ToDo.Category.valueOf(cursor.getString(3)),cursor.getLong(0),cursor.getLong(4));
        return newToDo;
    }


    private static class TodoDbHelper extends SQLiteOpenHelper{

        TodoDbHelper(Context ctx){
            super(ctx,DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
//            create todo table
            db.execSQL(CREATE_TABLE_TODO);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TodoDbHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
//            destroys data
            db.execSQL("DROP TABLE IF EXISTS" + TODO_TABLE);
            onCreate(db);
        }
    }
}
