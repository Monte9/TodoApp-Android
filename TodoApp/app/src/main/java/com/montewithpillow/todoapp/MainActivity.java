package com.montewithpillow.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

        reloadListView();

        //check for deleting item
        Boolean delete = getIntent().getBooleanExtra("delete", false);
        if (delete) {
            int id = getIntent().getIntExtra("id", 0);
            databaseHelper.deleteItems(String.valueOf(id));
            reloadListView();
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                todoItem = todoAdapter.getItem(pos);
                i.putExtra("id", todoItem.getId());
                i.putExtra("text", todoItem.getText());
                i.putExtra("priority", todoItem.getPriority());
                startActivityForResult(i, 20);

                //databaseHelper.deleteItems(String.valueOf(todoItem.getId()));
                //reloadListView();
            }
        });
    }

    public void reloadListView() {
        todoAdapter.clear();
        Cursor res = databaseHelper.getItems();
        while (res.moveToNext()) {
            todoItem = new Todoitem(res.getInt(0), res.getString(1), res.getInt(2));
            todoAdapter.add(todoItem);
        }
        todoAdapter.notifyDataSetChanged();
    }

    //handles the result of the sub-activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("here?");
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            int priority = data.getIntExtra("priority", 0);
            Boolean add = data.getBooleanExtra("add", false);
            if (!add) {
                System.out.println("here in not add?");
                long id = data.getIntExtra("id", 0);
                String position = String.valueOf(id);
                databaseHelper.updateData(position, text, priority);
            } else {
                System.out.println("here in add?");
                databaseHelper.addTodoitem(text, priority);
            }
            reloadListView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("add", true);
        startActivityForResult(i, 20);
        return super.onOptionsItemSelected(item);
    }
}