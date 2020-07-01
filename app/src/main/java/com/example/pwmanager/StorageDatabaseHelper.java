package com.example.pwmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class StorageDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "accounts_database";
    private static final String ACCOUNTS_TABLE = "accounts_table";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "name";
    private static final String COL_PASSWORD = "password";
    SQLiteDatabase db;

    public StorageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + ACCOUNTS_TABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_USERNAME +
                " TEXT, " + COL_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
        onCreate(db);
    }

    public void insertAccount(String name, String pass){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, name);
        values.put(COL_PASSWORD, pass);
        db.insert(ACCOUNTS_TABLE, null, values);
    }

    public String searchPass(String name){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + COL_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        cursor.moveToFirst();
        String pass = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
        return pass;
    }

    //Returns a list of all accounts in DB
    public ArrayList getAccounts() {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<InputAccount> accounts = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + ACCOUNTS_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            accounts.add(new InputAccount(cursor.getString(cursor.getColumnIndex(COL_USERNAME)), cursor.getString(cursor.getColumnIndex(COL_PASSWORD))));
            cursor.moveToNext();
        }
        return accounts;
    }
}
