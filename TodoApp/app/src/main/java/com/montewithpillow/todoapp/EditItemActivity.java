package com.montewithpillow.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    Todoitem todoitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //set the text as the current todo item
        String editText = getIntent().getStringExtra("text");
        int id = getIntent().getIntExtra("id", 0);
        int priority = getIntent().getIntExtra("priority", 0);
        todoitem = new Todoitem(id, editText, priority);
        EditText editText1 = (EditText) findViewById(R.id.editText);
        editText1.setText(editText);
        int code = getIntent().getIntExtra("code", 0);
    }

    public void onSave(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String itemText = editText.getText().toString();
        todoitem.setText(itemText);

        // Prepare data intent
        Intent data = new Intent();

        // Pass relevant data back as a result
        data.putExtra("text", itemText);
        data.putExtra("id", todoitem.getId());
        data.putExtra("priority", todoitem.getPriority());

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
