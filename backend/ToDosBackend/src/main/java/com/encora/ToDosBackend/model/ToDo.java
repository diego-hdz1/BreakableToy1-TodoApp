package com.encora.ToDosBackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ToDo {
    private Long id;
    private String text;
    private LocalDate dueDate;
    private Boolean status;
    private LocalDateTime doneDate;
    private Integer priority;
    private LocalDateTime creationDate;

    public ToDo() {
    }

    public ToDo(Long id, String text, LocalDate dueDate, Boolean status, LocalDateTime doneDate, Integer priority, LocalDateTime creationDate) {
        this.id = id;
        this.text = text;
        this.dueDate = dueDate;
        this.status = status;
        this.doneDate = doneDate;
        this.priority = priority;
        this.creationDate = creationDate;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public ToDo deepCopy(){
        return new ToDo(this.id, this.text, this.dueDate, this.status, this.doneDate, this.priority, this.creationDate);
    }

}