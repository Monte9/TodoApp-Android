package com.montewithpillow.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Todoitem> todoItemList;
    TodoCursorAdapter todoAdapter;
    ListView lvItems;
    TodoItemDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Todoitem todoItem;

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get singleton instance of database
        databaseHelper = TodoItemDatabaseHelper.getInstance(this);
        db = databaseHelper.getWritableDatabase();

        todoItemList = new ArrayList<>();
        // Find ListView to populate
        lvItems = (ListView) findViewById(R.id.lvItems);
        // Setup cursor adapter using cursor from last step
        todoAdapter = new TodoCursorAdapter(this, R.layout.item_todo, todoItemList);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);

        populateArrayItems();

    
    }

    //add items to the list
    public void addItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        databaseHelper.addTodoitem(itemText, 1);
        etNewItem.setText("");
        populateArrayItems();
    }

    public void populateArrayItems() {
        todoAdapter.clear();
        Cursor res = databaseHelper.getItems();
        while (res.moveToNext()) {
            todoItem = new Todoitem(res.getLong(0), res.getString(1), res.getInt(2));
            todoAdapter.add(todoItem);
        }
        todoAdapter.notifyDataSetChanged();
    }
//
//
//
//    //handles the result of the sub-activity
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // REQUEST_CODE is defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            // Extract text & pos value from result extras
//            String text = data.getExtras().getString("editText");
//            int pos = data.getIntExtra("pos", 0);
//
//            //remove item in that position
//            items.remove(pos);
//           // itemsAdapter.notifyDataSetChanged();
//
//            //insert updated text in the position
//          //  itemsAdapter.insert(text, pos);
//        }
//    }
}