package com.example.pwmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "login_database";
    private static final String LOGINS_TABLE = "logins_table";
    private static final String COL_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    SQLiteDatabase db;

    public LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String createTable = "CREATE TABLE " + LOGINS_TABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_EMAIL +
                 " TEXT, " + COL_USERNAME + " TEXT, " + COL_PASSWORD + " TEXT)";
         db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGINS_TABLE);
        onCreate(db);
    }

    public void insertAccount(Contact c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, c.getEmail());
        values.put(COL_USERNAME, c.getUsername());
        values.put(COL_PASSWORD, c.getPassword());
        db.insert(LOGINS_TABLE, null, values);
    }

    public String searchPass(String username){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + LOGINS_TABLE + " WHERE " + COL_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        String pass = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
        return pass;
    }
}
