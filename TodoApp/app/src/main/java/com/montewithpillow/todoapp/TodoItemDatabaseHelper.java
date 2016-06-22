package com.montewithpillow.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class TodoItemDatabaseHelper extends SQLiteOpenHelper {
    private static TodoItemDatabaseHelper sInstance;

    // Database CONST
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ITEMS = "items";
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_TEXT = "text";
    private static final String KEY_ITEM_PRIORITY = "priority";

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private TodoItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Singleton pattern for Databasehelper
    public static synchronized TodoItemDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new TodoItemDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_ITEM_TEXT + " TEXT," + //Text of the todo item
                KEY_ITEM_PRIORITY + " INTEGER" + //Priority of the todo item
                ")";

        db.execSQL(CREATE_ITEMS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }

    //CRUD ACTIONS

    // Insert a post into the database
    public void addTodoitem(String text, Integer priority) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_TEXT, text);
        values.put(KEY_ITEM_PRIORITY, priority);
        db.insert(TABLE_ITEMS, null, values);
    }

    // Insert or update item in the database
    public void updateData(String id, String text, Integer priority) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_TEXT, text);
        values.put(KEY_ITEM_PRIORITY, priority);
        db.update(TABLE_ITEMS, values, "id = ?", new String[]{id});
    }

    public void deleteItems(String id) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        System.out.println("Delete this: " + id);
        db.delete(TABLE_ITEMS, "id = ?", new String[]{id});
    }

    public Cursor getItems() {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery("select * from " + TABLE_ITEMS, null);
    }
}
