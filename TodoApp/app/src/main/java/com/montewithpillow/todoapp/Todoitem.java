package com.montewithpillow.todoapp;

import java.io.Serializable;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class Todoitem implements Serializable {
    private int id;
    public String text;
    public String priority;
    public String dueDate;

    public Todoitem(String text, String priority, String dueDate) {
        this(0, text, priority, dueDate);
    }

    public Todoitem(int id, String text, String priority, String dueDate) {
        setId(id);
        setText(text);
        setPriority(priority);
        setDueDate(dueDate);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPriority() { return priority; }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

}
