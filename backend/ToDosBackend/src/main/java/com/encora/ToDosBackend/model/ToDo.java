package com.encora.ToDosBackend.model;

public class ToDo {
    private Long id;
    private String text;
    private String dueDate; //Checar si es asi el tipo de datos
    private boolean status;
    private String doneDate;
    private int priority;

    public ToDo() {
    }

    public ToDo(Long id, String text, String dueDate, boolean status, String doneDate, int priority) {
        this.id = id;
        this.text = text;
        this.dueDate = dueDate;
        this.status = status;
        this.doneDate = doneDate;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}