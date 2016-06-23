package com.montewithpillow.todoapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import java.util.Calendar;
import android.support.v4.app.DialogFragment;

/**
 * Created by montewithpillow on 6/22/16.
 */
public class DatePickerFragment extends DialogFragment {
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Activity needs to implement this interface
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);

    }
}
