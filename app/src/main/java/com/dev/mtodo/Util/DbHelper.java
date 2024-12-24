package com.dev.mtodo.Util;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dev.mtodo.Model.Todo;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static  final String DB_NAME = "TODO_DATABASE";
    private static  final String TABLE_TODOS = "TODOS";
    private static  final String COL_1 = "ID";
    private static  final String COL_2 = "CONTENT";
    private static  final String COL_3 = "STATUS";


    public DbHelper(@Nullable Context context ) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TODOS + "(ID INTEGER PRIMARY KEY , CONTENT TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        onCreate(db);
    }

    public void insertTask(Todo model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // TODO: 2024/12/24 Unimplemented
        values.put(COL_2 , model.get());
        values.put(COL_3 , 0);
        db.insert(TABLE_TODOS, null , values);
    }

    public void updateTask(int id , String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , task);
        db.update(TABLE_TODOS, values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateTodosStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3 , status);
        db.update(TABLE_TODOS, values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteFromTodos(int id ){
        db = this.getWritableDatabase();
        db.delete(TABLE_TODOS, "ID=?" , new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<Todo> getAllTodos(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<Todo> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_TODOS, null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        // TODO: 2024/12/24 Unimplemented!
                        Todo todo = new Todo();
                        todo.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        todo.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        todo.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        modelList.add(todo);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return modelList;
    }
}
