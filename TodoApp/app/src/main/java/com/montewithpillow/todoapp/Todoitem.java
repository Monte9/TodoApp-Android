package com.montewithpillow.todoapp;

import java.io.Serializable;

/**
 * Created by montewithpillow on 6/21/16.
 */
public class Todoitem implements Serializable {
    private Long id;
    public String text;
    public int priority;

    public Todoitem(String text, int priority) {
        this(null, text, priority);
    }

    public Todoitem(Long id, String text, int priority) {
        setId(id);
        setText(text);
        setPriority(priority);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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
