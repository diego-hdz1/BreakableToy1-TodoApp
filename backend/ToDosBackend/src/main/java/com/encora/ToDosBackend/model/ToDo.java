package com.encora.ToDosBackend.model;

public class ToDo implements Comparable<ToDo>{
    private Long id;
    private String text;
    private String dueDate; //Checar si es asi el tipo de datos
    private Boolean status;
    private String doneDate;
    private Integer priority;

    public ToDo() {
    }

    public ToDo(Long id, String text, String dueDate, Boolean status, String doneDate, Integer priority) {
        this.id = id;
        this.text = text;
        this.dueDate = dueDate;
        this.status = status;
        this.doneDate = doneDate;
        this.priority = priority;
    }


    @Override
    public int compareTo(ToDo o) {
        if(this.getPriority() > o.getPriority()) return -1;
        if(this.getPriority() < o.getPriority()) return 1;
        return 0;

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

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", status=" + status +
                ", doneDate='" + doneDate + '\'' +
                ", priority=" + priority +
                '}';
    }

}