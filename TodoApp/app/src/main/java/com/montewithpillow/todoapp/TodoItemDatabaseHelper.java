package com.montewithpillow.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class TodoItemDatabaseHelper extends SQLiteOpenHelper {
    private static TodoItemDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

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

    // Table Names
    private static final String TABLE_ITEMS = "items";

    // Item Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_TEXT = "text";
    private static final String KEY_ITEM_PRIORITY = "priority";

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
    public void addTodoitem(Todoitem todoitem) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_TEXT, todoitem.text);
            values.put(KEY_ITEM_PRIORITY, todoitem.priority);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("Error while trying to add post to database");
            //Log.d(Tag, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update todoitem in the database
    public void update(Todoitem todoitem) {
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_TEXT, todoitem.text);
            values.put(KEY_ITEM_PRIORITY, todoitem.priority);

            String selection = KEY_ITEM_ID + " = ?";
            String[] selectionArgs = {String.valueOf(todoitem.getId())};

            int count = db.update(
                    TABLE_ITEMS,
                    values,
                    selection,
                    selectionArgs);
            System.out.println("Update successful.. count is: " + count);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("Error while trying to add post to database");
            //Log.d(Tag, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Delete
    public void delete(Todoitem todoitem) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = KEY_ITEM_ID + " = ?";
        String[] selectionArgs = {String.valueOf(todoitem.getId())};

        int rowsAffected = db.delete(TABLE_ITEMS, selection, selectionArgs);
        System.out.println("Row deleted!. Aye!");
    }

    // Read all
    public List<Todoitem> list() {
        List<Todoitem> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Build an array to specify the columns of the table you want your query to return
        String[] projection = {
                KEY_ITEM_ID,
                KEY_ITEM_TEXT,
                KEY_ITEM_PRIORITY
        };

        String sortOrder = KEY_ITEM_PRIORITY + " DESC";

        // Query the table and return a cursor
        Cursor c = db.query(
                TABLE_ITEMS,                            // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );

        while (c.moveToNext()) {
            String text = c.getString(c.getColumnIndex(KEY_ITEM_TEXT));
            int priority = c.getInt(c.getColumnIndex(KEY_ITEM_PRIORITY));
            long id = c.getLong(c.getColumnIndex(KEY_ITEM_ID));
            list.add(new Todoitem(id, text, priority));
        }
        System.out.println("Got all items from db");
        c.close();
        return list;
    }

    public Cursor cursor() {
        SQLiteDatabase db = getReadableDatabase();

        // Build an array to specify the columns of the table you want your query to return
        String[] projection = {
                "rowid _id",
                KEY_ITEM_ID,
                KEY_ITEM_TEXT,
                KEY_ITEM_PRIORITY
        };

        String sortOrder = KEY_ITEM_PRIORITY + " DESC";

        // Query the table and return a cursor
        Cursor c = db.query(
                TABLE_ITEMS,                            // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                           // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );
        return c;
    }
}
