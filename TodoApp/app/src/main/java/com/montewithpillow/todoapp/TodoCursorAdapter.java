package com.montewithpillow.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class TodoCursorAdapter extends ArrayAdapter<Todoitem> {
    private int layoutResouce;

    public TodoCursorAdapter(Context context, int layoutResource, List<Todoitem> TodoItemList) {
        super(context, layoutResource, TodoItemList);
        this.layoutResouce = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResouce, null);
        }

        Todoitem newItem = getItem(position);

        if (newItem != null) {
            // Find fields to populate in inflated template
            TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
            TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
            tvBody.setText(newItem.getText());
            tvPriority.setText(String.valueOf(newItem.getPriority()));
        }
        return view;
    }
}
