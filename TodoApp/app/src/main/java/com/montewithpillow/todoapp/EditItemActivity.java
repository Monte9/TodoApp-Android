package com.montewithpillow.todoapp;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class EditItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Todoitem todoitem;
    static Boolean add = false;
    static String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //set the text as the current todo item
        add = getIntent().getBooleanExtra("add", false);
        if (!add) {
            String editText = getIntent().getStringExtra("text");
            int id = getIntent().getIntExtra("id", 0);
            String priority = getIntent().getStringExtra("priority");
            String dueDate = getIntent().getStringExtra("dueDate");

            int priorityValue = priorityToInt(priority);
            todoitem = new Todoitem(id, editText, priority, dueDate);

            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setProgress(priorityValue);

            EditText editText1 = (EditText) findViewById(R.id.editText);
            editText1.setText(editText);

            TextView dueDateText = (TextView) findViewById(R.id.ddTextView);
            dueDateText.setText(dueDate);
        }
    }

    private int priorityToInt(String priority) {
        switch (priority) {
            case "low":
                return 0;
            case "Normal":
                return 1;
            case "HIGH":
                return 2;
            default:
                return 0;
        }
    }

    private String priorityToString(int priority) {
        switch (priority) {
            case 0:
                return "low";
            case 1:
                return "Normal";
            case 2:
                return "HIGH";
            default:
                return "low";
        }
    }

    public void onSave(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        String itemText = editText.getText().toString();
        int priorityValue = seekBar.getProgress();

        String priorityString = priorityToString(priorityValue);

        Intent data = new Intent();
        if (add) {
            todoitem = new Todoitem(itemText, "HIGH", "Due @");
            data.putExtra("add", true);
            data.putExtra("text", itemText);
            data.putExtra("dueDate", date);
            data.putExtra("priority", priorityString);
        } else {
            todoitem.setText(itemText);
            data.putExtra("add", false);
            data.putExtra("id", todoitem.getId());
            data.putExtra("text", itemText);
            data.putExtra("dueDate", date);
            data.putExtra("priority", priorityString);
        }

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
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

    public void onCancel(View view) {
        Intent data = new Intent();
        // Activity finished ok, return the data
        setResult(RESULT_CANCELED, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView dateTextView = (TextView) findViewById(R.id.ddTextView);
        dateTextView.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
        date = monthOfYear + "/" + dayOfMonth + "/" + year;
    }
}
