package com.montewithpillow.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Confirm Delete");
        alertDialog.setMessage("Are you sure you want to delete this Todo Item?");

        //cancel button
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );

        //yes button - to confirm delete
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(EditItemActivity.this, MainActivity.class);
                        i.putExtra("id", todoitem.getId());
                        i.putExtra("delete", true);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Todo item deleted!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        alertDialog.show();
        return super.onOptionsItemSelected(item);
    }
}
