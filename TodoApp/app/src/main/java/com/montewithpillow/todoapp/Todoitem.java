package com.montewithpillow.todoapp;

import java.io.Serializable;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class Todoitem implements Serializable {
    private int id;
    public String text;
    public int priority;

    public Todoitem(String text, int priority) {
        this(0, text, priority);
    }

    public Todoitem(int id, String text, int priority) {
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

    public int getPriority() {
        return priority;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
