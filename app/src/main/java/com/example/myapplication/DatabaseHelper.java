package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EmployeeDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMPLOYEE_NUMBER = "employee_number";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMPLOYEE_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        onCreate(db);
    }

    public long insertEmployeeNumber(String employeeNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NUMBER, employeeNumber);
        long id = db.insert(TABLE_EMPLOYEES, null, values);
        db.close();
        return id;
    }

    public List<String> getAllEmployeeNumbers() {
        List<String> employeeNumberList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_EMPLOYEE_NUMBER + " FROM " + TABLE_EMPLOYEES;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String employeeNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_NUMBER));
                employeeNumberList.add(employeeNumber);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employeeNumberList;
    }
}