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
    private static final String LOGINS_TABLE = "logins_table";
    private static final String COL_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_USERNAME = "name";
    private static final String COL_PASSWORD = "password";
    SQLiteDatabase db;

    public StorageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + ACCOUNTS_TABLE + "(" + COL_ID + " INTEGER, " + COL_USERNAME +
                " TEXT PRIMARY KEY, " + COL_PASSWORD + " TEXT, FOREIGN KEY (" + COL_ID + ") REFERENCES " + LOGINS_TABLE + "(" + COL_ID + "))";
        String createTable2 = "CREATE TABLE " + LOGINS_TABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_EMAIL +
                " TEXT, " + COL_USERNAME + " TEXT, " + COL_PASSWORD + " TEXT)";
        db.execSQL(createTable2);
        db.execSQL(createTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOGINS_TABLE);
        onCreate(db);
    }

    public void storeAccount(String name, String pass, int id){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_USERNAME, name);
        values.put(COL_PASSWORD, pass);
        db.insert(ACCOUNTS_TABLE, null, values);
    }


    //Returns a list of all accounts in DB
    public ArrayList getAccounts(int id) {
        //Get Readable Database
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        ArrayList<InputAccount> accounts = new ArrayList<>();
        //Perform Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + COL_ID + " = ?", new String[]{Integer.toString(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            accounts.add(new InputAccount(cursor.getString(cursor.getColumnIndex(COL_USERNAME)), cursor.getString(cursor.getColumnIndex(COL_PASSWORD))));
            cursor.moveToNext();
        }
        return accounts;
    }

    public void insertLogin(Contact c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, c.getEmail());
        values.put(COL_USERNAME, c.getUsername());
        values.put(COL_PASSWORD, c.getPassword());
        db.insert(LOGINS_TABLE, null, values);
    }

    public boolean checkPass(String user, String pass){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + LOGINS_TABLE + " WHERE " + COL_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user});
        try {
            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
            if (pass.equals(password)){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int getAccountID(String username){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + LOGINS_TABLE + " WHERE " + COL_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        return id;
    }
}
