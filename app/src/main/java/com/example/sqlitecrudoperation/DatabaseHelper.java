package com.example.sqlitecrudoperation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME= "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    public static final String COL_5 = "IMAGE";


    public DatabaseHelper( Context context) {

        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" create table "+TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT," +
                " SURNAME TEXT , MARKS INTEGER, IMAGE INTEGER) ");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }


// insert Data Method
    public boolean insertData(String name, String surname,String marks,int image)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        contentValues.put(COL_5,image);

       long success = db.insert(TABLE_NAME,null, contentValues);
        if(success==-1)
        {
            return false;
        }
        else {
            return  true;
        }


    }



    //Fetch Data Method


    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cur  = db.rawQuery("select * from "+TABLE_NAME,null);
        return cur;

    }

















    public boolean UpdateData(String id, String name, String surname,String marks)

    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id });


        return true;


    }


    public Integer deleteData(String Id)
    {

        SQLiteDatabase db =this.getWritableDatabase();


        return db.delete(TABLE_NAME,"ID = ?",new String[]{Id});
    }


}
