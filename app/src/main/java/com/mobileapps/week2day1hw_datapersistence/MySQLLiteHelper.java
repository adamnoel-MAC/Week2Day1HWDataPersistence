package com.mobileapps.week2day1hw_datapersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Optional;

import static com.mobileapps.week2day1hw_datapersistence.Constants.DATABASE_VERSION;
import static com.mobileapps.week2day1hw_datapersistence.Constants.DATABASE_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.FIRST_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.LAST_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.PASSWORD;
import static com.mobileapps.week2day1hw_datapersistence.Constants.TABLE_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.USER_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.PHONE_NUMBER;
import static com.mobileapps.week2day1hw_datapersistence.Constants.EMAIL_ADDRESS;
import static com.mobileapps.week2day1hw_datapersistence.Constants.SKYPE_ID;
import static com.mobileapps.week2day1hw_datapersistence.Constants.FB_USERNAME;

public class MySQLLiteHelper extends SQLiteOpenHelper {

    public MySQLLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + USER_NAME + " TEXT PRIMARY KEY, "
                + PASSWORD + " TEXT, "
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + PHONE_NUMBER + " TEXT, "
                + EMAIL_ADDRESS + " TEXT, "
                + SKYPE_ID + " TEXT, "
                + FB_USERNAME + " TEXT"
                + ")";
        Log.d("TAG", "onCreate: " + createTableQuery);
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG", "onUpgrade: oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        if (oldVersion < 3){
            String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(dropTableQuery);
            onCreate(db);
        }
    }

    public boolean insertLogin(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(PASSWORD, password);
        boolean ret = true;
        try {
            db.insert(TABLE_NAME, null, contentValues);
        } catch (Exception s) {
            Log.d("TAG", "insertLogin: " + s.getMessage());
            ret = false;
        }
        return ret;
    }

    public boolean insertContact(String name, String password, String firstName,
                                 String lastName, String phoneNumber, String emailAddress,
                                 String skypeID, String fbUserName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(PASSWORD, password);
        contentValues.put(FIRST_NAME, firstName);
        contentValues.put(LAST_NAME, lastName);
        contentValues.put(PHONE_NUMBER, phoneNumber);
        contentValues.put(EMAIL_ADDRESS, emailAddress);
        contentValues.put(SKYPE_ID, skypeID);
        contentValues.put(FB_USERNAME, fbUserName);

        boolean ret = true;
        try {
            db.insert(TABLE_NAME, null, contentValues);
        } catch (SQLiteConstraintException s) {
            Log.d("SQL ERROR", "insertContact: " + s.getMessage());
            ret = false;
        }
        return ret;
    }

    public Cursor getUsersByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + USER_NAME + " = \"" + name + "\"";
        Log.d("TAG", "getUsersByName: " + sql);
        Cursor res = db.rawQuery(sql, null);
        return res;
    }
} // MySQLLiteHelper extends SQLLiteOpenHelper

