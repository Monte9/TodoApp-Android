package com.montewithpillow.todoapp;

import java.io.Serializable;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class Todoitem implements Serializable {
    private int id;
    public String text;
    public String priority;

    public Todoitem(String text, String priority) {
        this(0, text, priority);
    }

    public Todoitem(int id, String text, String priority) {
        setId(id);
        setText(text);
        setPriority(priority);
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
