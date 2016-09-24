package com.sss.projectx.Others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class Database {

    DatabaseHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public Database(Context context){
        helper = new DatabaseHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void insertMyPost(ArrayList<Detail> lists, boolean clearPrevious) {
        if (clearPrevious) {
            deleteAll();
        }
        String sql = "INSERT INTO " + DatabaseHelper.TABLE_NAME_MYPOST + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        //compile statement and start a transaction
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        sqLiteDatabase.beginTransaction();

        for (int i = 0; i < lists.size(); i++) {
            Detail current = lists.get(i);
            statement.clearBindings();

            statement.bindString(2, current.profile);
            statement.bindString(3, current.activation_time);
            statement.bindString(4, current.delay_time);
            statement.bindString(5, String.valueOf(current.light));
            statement.bindString(6, String.valueOf(current.proximity));
            statement.bindString(7, String.valueOf(current.power));
            statement.bindString(8, String.valueOf(current.unlock));
            statement.bindString(9, String.valueOf(current.sound));
            statement.bindString(10, String.valueOf(current.email));
            statement.bindString(11, String.valueOf(current.sms));
            statement.bindString(12, String.valueOf(current.call));
            statement.execute();
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public ArrayList<Detail> getAllMyPosts() {
        ArrayList<Detail> currentData = new ArrayList<>();
        String[] columns = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_PROFILE,
                DatabaseHelper.COLUMN_AT,
                DatabaseHelper.COLUMN_DT,
                DatabaseHelper.COLUMN_LIGHT,
                DatabaseHelper.COLUMN_PROXIMITY,
                DatabaseHelper.COLUMN_POWER,
                DatabaseHelper.COLUMN_UNLOCK,
                DatabaseHelper.COLUMN_SOUND,
                DatabaseHelper.COLUMN_EMAIL,DatabaseHelper.COLUMN_SMS,
                DatabaseHelper.COLUMN_CALL

        };
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_MYPOST, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Detail current = new Detail();
                current.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                current.profile = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROFILE));
                current.activation_time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AT));
                current.delay_time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DT));
                current.light = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LIGHT)));
                current.proximity = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROXIMITY)));
                current.power = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_POWER)));
                current.unlock = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNLOCK)));
                current.sound = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOUND)));
                current.email = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
                current.sms = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SMS)));
                current.call = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALL)));
                currentData.add(current);
            }
            cursor.close();
        }

        return currentData;
    }

    public int getLastId() {
        int id = 1;
        String[] columns = {
                DatabaseHelper.COLUMN_ID,DatabaseHelper.COLUMN_PROFILE,
                DatabaseHelper.COLUMN_AT,DatabaseHelper.COLUMN_DT,DatabaseHelper.COLUMN_LIGHT,
                DatabaseHelper.COLUMN_PROXIMITY,DatabaseHelper.COLUMN_POWER,
                DatabaseHelper.COLUMN_UNLOCK, DatabaseHelper.COLUMN_SOUND,DatabaseHelper.COLUMN_EMAIL,DatabaseHelper.COLUMN_SMS,
                DatabaseHelper.COLUMN_CALL
        };
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_MYPOST, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToLast();
            id = cursor.getInt(0);
            //cursor.close();
        }
        Log.e("gotID", "my id is " + id);
        return id;
    }

    public void deleteAll() {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_MYPOST, null, null);
    }

    public void updateDatabase(int foreignKey,String what_to_update, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(what_to_update, status);//DatabaseHelper.COLUMN_STATUS
        sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_MYPOST, contentValues, DatabaseHelper.COLUMN_ID + "=" + foreignKey, null);//
        Log.e("UPDATE", "database updated to " + status);
    }

    public void deleteDatabase(int id) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_MYPOST, DatabaseHelper.COLUMN_ID + "=" + id, null);
    }

    public class DatabaseHelper extends SQLiteOpenHelper{

        private Context mContext;
        private static final String DB_NAME = "database_db";
        private static final int DB_VERSION = 3;

        public static final String TABLE_NAME_MYPOST = "table101";
        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_PROFILE = "profile";
        public static final String COLUMN_AT = "activation_time";
        public static final String COLUMN_DT = "delay_time";
        public static final String COLUMN_LIGHT = "light";
        public static final String COLUMN_PROXIMITY = "proximity";
        public static final String COLUMN_POWER = "power";
        public static final String COLUMN_UNLOCK = "unlock";
        public static final String COLUMN_SOUND = "sound";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_SMS = "sms";
        public static final String COLUMN_CALL = "call";

        private static final String CREATE_TABLE_MYPOST = "CREATE TABLE " + TABLE_NAME_MYPOST + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PROFILE + " TEXT," +
                COLUMN_AT + " TEXT," +
                COLUMN_DT + " TEXT," +
                COLUMN_LIGHT + " TEXT," +
                COLUMN_PROXIMITY + " TEXT," +
                COLUMN_POWER + " TEXT," +
                COLUMN_UNLOCK + " TEXT," +
                COLUMN_SOUND + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_SMS + " TEXT," +
                COLUMN_CALL + " TEXT" +
                ");";

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_MYPOST);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_NAME_MYPOST + " IF EXISTS;");
                onCreate(sqLiteDatabase);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }
}
